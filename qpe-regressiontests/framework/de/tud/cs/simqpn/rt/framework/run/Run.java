/* ==============================================
 * QPME : Queueing Petri net Modeling Environment
 * ==============================================
 *
 * (c) Copyright 2003-2011, by Samuel Kounev and Contributors.
 * 
 * Project Info:   http://descartes.ipd.kit.edu/projects/qpme/
 *                 http://www.descartes-research.net/
 *    
 * All rights reserved. This software is made available under the terms of the 
 * Eclipse Public License (EPL) v1.0 as published by the Eclipse Foundation
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * This software is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the Eclipse Public License (EPL)
 * for more details.
 *
 * You should have received a copy of the Eclipse Public License (EPL)
 * along with this software; if not visit http://www.eclipse.org or write to
 * Eclipse Foundation, Inc., 308 SW First Avenue, Suite 110, Portland, 97204 USA
 * Email: license (at) eclipse.org 
 *  
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *                                
 * =============================================
 *
 * Original Author(s):  Simon Spinner
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ----------------------------------------------------------------------------------
 *  2011/01/21  Simon Spinner     Created.         
 * 
 */

package de.tud.cs.simqpn.rt.framework.run;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.ProcessDestroyer;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.log4j.LogMF;
import org.apache.log4j.Logger;

import de.tud.cs.simqpn.rt.framework.results.RunInfo;
import de.tud.cs.simqpn.rt.framework.run.SimulationRunner.AnalysisMode;
import de.tud.cs.simqpn.rt.framework.run.SimulationRunner.StoppingRule;

public abstract class Run implements Callable<RunInfo> {
	
	private static final Logger log = Logger.getLogger(Run.class);
	
	private static final int MAX_RETRIES = 5;
	
	protected int index;
	protected RunConfig config;
	protected File tmpDir;
	private ProcessDestroyer destroyer;
	
	public Run(int index, RunConfig config, File tmpDir, ProcessDestroyer destroyer) {
		super();
		this.index = index;
		this.config = config;
		this.tmpDir = tmpDir;
		this.destroyer = destroyer;
	}
	
	@Override
	public RunInfo call() throws Exception {
		LogMF.info(log, "Start simulation run {0}", index);
		
		RunInfo info = new RunInfo();
		info.setRunDirectory(tmpDir);
		info.setModelFile(config.getModel());
		
		int retries = 0;
		Map<String, Object> substitutions = new HashMap<String, Object>();
		prepareEnvironment(substitutions);
		CommandLine cmd = createCommandLine();
		cmd.setSubstitutionMap(substitutions);
		
		while(true) {
			try {
				if (retries >= MAX_RETRIES) {
					log.error("Maximum number of retries reached. Run " + index + " failed!");
					fail();
				}
				retries++;
				
				// No previous simqpn files should be lying around in the directory
				// otherwise the wrong results might be taken in later steps
				FileFilter simqpnFilter = new SuffixFileFilter(".simqpn");
				for (File f : tmpDir.listFiles(simqpnFilter)) {
					if (!f.delete()) {
						LogMF.error(log, "Could not delete simqpn file: {0}", new Object[] { f.getName() });
						fail();
					}
				}
				
				ExecuteWatchdog watchdog = new ExecuteWatchdog(ExecuteWatchdog.INFINITE_TIMEOUT);
				DefaultExecutor executor = new DefaultExecutor();
				executor.setWatchdog(watchdog);
				executor.setWorkingDirectory(tmpDir);
				
				File consoleLog = new File(tmpDir, "consoleOutputRun" + index + ".log");
				info.setConsoleLogFile(consoleLog);
				RunStreamHandler streamHandler;
				if (config.getAnalysisMode() == AnalysisMode.BATCH_MEANS) {
					if (config.getStoppingRule() == StoppingRule.FIXED_LENGTH) {
						streamHandler = new RunStreamHandler(consoleLog, false, true, watchdog);
					} else {
						streamHandler = new RunStreamHandler(consoleLog, true, true, watchdog);
					}
				} else {
					streamHandler = new RunStreamHandler(consoleLog, false, false, watchdog);
				}
				executor.setStreamHandler(streamHandler);
				executor.setProcessDestroyer(destroyer);

				try {
					int exitCode = executor.execute(cmd);					
	
					if (executor.isFailure(exitCode)) {
						LogMF.error(log, "Simulation exited with code: {0}", new Object[] { exitCode });
						continue;
					}
					
					if (config.isExpectError()) {
						fail("Error expected, but simulation finished correctly.");
					}
				} catch(ExecuteException ex) {
					if (!config.isExpectError()) {
						log.error("Simulation exited with error.", ex);
					}
				}
				
				if (!streamHandler.isPrecisionReached()) {
					LogMF.warn(log, "Simulation run {0} skipped to due insufficient precision. Retry...", index);
					continue;
				}				
				info.setRunLength(streamHandler.getTotalRunLength());
				info.setOverflowFlag(streamHandler.hasOverflowOccurred());
				
				break;
			} catch(IOException ex) {
				log.error("Error running simulation.", ex);
				fail();
			}
		}
		
		LogMF.info(log, "Simulation run {0} finished", index);
		return info;
	}
	
	protected void prepareEnvironment(Map<String, Object> files) throws IOException {
		files.put("modelFile", config.getModel());
	}
	
	protected CommandLine createCommandLine() { 
		return new CommandLine("java.exe");
	}
}