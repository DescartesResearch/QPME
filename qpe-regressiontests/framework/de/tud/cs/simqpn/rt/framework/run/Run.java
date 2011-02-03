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
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.log4j.LogMF;
import org.apache.log4j.Logger;

import de.tud.cs.simqpn.rt.framework.results.RunInfo;
import de.tud.cs.simqpn.rt.framework.run.SimulationRunner.AnalysisMode;
import de.tud.cs.simqpn.rt.framework.run.SimulationRunner.StoppingRule;

/**
 * Starts and controls the execution of a simulation run.
 * 
 * @author Simon Spinner
 * 
 */
public abstract class Run implements Callable<RunInfo> {

	private static final Logger log = Logger.getLogger(Run.class);

	private static final int MAX_RETRIES = 5;

	protected int index;
	protected RunConfig config;
	protected File tmpDir;

	/**
	 * @param index
	 *            - The position in a sequence of repeated runs.
	 * @param config
	 *            - Run configuration.
	 * @param tmpDir
	 *            - Directory where temporary files are stored in.
	 */
	public Run(int index, RunConfig config, File tmpDir) {
		super();
		this.index = index;
		this.config = config;
		this.tmpDir = tmpDir;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
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

		while (true) {
			try {
				if (retries >= MAX_RETRIES) {
					log.error("Maximum number of retries reached. Run " + index
							+ " failed!");
					fail();
				}
				retries++;

				// No previous simqpn files should be lying around in the
				// directory
				// otherwise the wrong results might be taken in later steps
				deleteSimqpnResultFiles();

				DefaultExecutor executor = createExecutor();

				File consoleLog = new File(tmpDir, "consoleOutputRun" + index
						+ ".log");
				info.setConsoleLogFile(consoleLog);

				RunStreamHandler streamHandler = createStreamHandler(
						consoleLog, executor);

				try {

					long start = System.currentTimeMillis();
					int exitCode = executor.execute(cmd);
					info.setWallClockTime((double) (System.currentTimeMillis() - start));

					if (executor.isFailure(exitCode)) {
						LogMF.error(log, "Simulation exited with code: {0}",
								new Object[] { exitCode });
						continue;
					}

					if (config.isExpectError()) {
						fail("Error expected, but simulation finished correctly.");
					}
				} catch (ExecuteException ex) {
					if (!config.isExpectError()) {
						log.error("Simulation exited with error.", ex);
					}
				}

				if (!streamHandler.isPrecisionReached()) {
					LogMF.warn(
							log,
							"Simulation run {0} skipped to due insufficient precision. Retry...",
							index);
					continue;
				}
				info.setRunLength(streamHandler.getTotalRunLength());
				info.setOverflowFlag(streamHandler.hasOverflowOccurred());

				break;
			} catch (IOException ex) {
				log.error("Error running simulation.", ex);
				fail();
			}
		}

		LogMF.info(log, "Simulation run {0} finished", index);
		return info;
	}

	/**
	 * Deletes all *.simqpn files in the temporary directory.
	 */
	private void deleteSimqpnResultFiles() {
		FileFilter simqpnFilter = new SuffixFileFilter(".simqpn");
		for (File f : tmpDir.listFiles(simqpnFilter)) {
			if (!f.delete()) {
				LogMF.error(log, "Could not delete simqpn file: {0}",
						new Object[] { f.getName() });
				fail();
			}
		}
	}

	/**
	 * Creates an executor that can be used to spawn a process.
	 * @return DefaultExecutor
	 */
	private DefaultExecutor createExecutor() {
		ExecuteWatchdog watchdog = new ExecuteWatchdog(
				ExecuteWatchdog.INFINITE_TIMEOUT);
		DefaultExecutor executor = new DefaultExecutor();
		executor.setWatchdog(watchdog);
		executor.setWorkingDirectory(tmpDir);
		return executor;
	}

	/**
	 * Creates a stream handler that captures the output of the given executor
	 * and writes it to the specified log file.
	 * 
	 * @param consoleLog - File where the output of the process should be stored in.
	 * @param executor - The process that will be executed.
	 * @return newly created stream handler.
	 */
	private RunStreamHandler createStreamHandler(File consoleLog,
			DefaultExecutor executor) {
		RunStreamHandler streamHandler;
		if (config.getAnalysisMode() == AnalysisMode.BATCH_MEANS) {
			if (config.getStoppingRule() == StoppingRule.FIXED_LENGTH) {
				streamHandler = new RunStreamHandler(consoleLog, false, true,
						executor.getWatchdog());
			} else {
				streamHandler = new RunStreamHandler(consoleLog, true, true,
						executor.getWatchdog());
			}
		} else {
			streamHandler = new RunStreamHandler(consoleLog, false, false,
					executor.getWatchdog());
		}
		executor.setStreamHandler(streamHandler);
		return streamHandler;
	}

	/**
	 * Initializes variables for the creation of the command line.
	 * 
	 * @param files
	 *            - Map containing variable names and values.
	 * @throws IOException
	 */
	protected void prepareEnvironment(Map<String, Object> files)
			throws IOException {
		files.put("modelFile", config.getModel());
	}

	/**
	 * Create the command line used for calling SimQPN.
	 * 
	 * @return CommandLine
	 */
	protected CommandLine createCommandLine() {
		CommandLine cmd = new CommandLine(
				"C:\\Program Files\\Java\\jdk1.6.0_18\\bin\\java.exe");
		cmd.addArgument("-server");
		return cmd;
	}
}