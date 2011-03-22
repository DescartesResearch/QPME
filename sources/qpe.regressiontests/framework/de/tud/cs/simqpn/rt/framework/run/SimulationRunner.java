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
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2011/01/21  Simon Spinner     Created.         
 * 
 */

package de.tud.cs.simqpn.rt.framework.run;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.log4j.LogMF;
import org.apache.log4j.Logger;

import de.tud.cs.simqpn.rt.framework.ConsoleLogImport;
import de.tud.cs.simqpn.rt.framework.Log4jXmlLogImport;
import de.tud.cs.simqpn.rt.framework.ModelImport;
import de.tud.cs.simqpn.rt.framework.SimQPNResultFileImport;
import de.tud.cs.simqpn.rt.framework.WelchDumpImport;
import de.tud.cs.simqpn.rt.framework.results.RunInfo;
import de.tud.cs.simqpn.rt.framework.results.SimulationResults;
import de.tud.cs.simqpn.rt.framework.run.RunConfig.AnalysisMode;
import de.tud.cs.simqpn.rt.framework.run.RunConfig.Revision;

/**
 * Performs a number of repeated simulation runs parses their logs and collect
 * the results of each run.
 * 
 * @author Simon Spinner
 * 
 */
public class SimulationRunner {

	private static final String RUN_DIR_PREFIX = "Run";

	private static final String TMP_BASE_DIR = "./tmp/";

	private static final String HISTORIC_EXECUTABLE_R162 = "./historic-executables/simqpn-base-r162.jar";

	private static final String HISTORIC_EXECUTABLE_R100 = "./historic-executables/simqpn-base-r100.jar";

	private static final Logger log = Logger.getLogger(SimulationRunner.class);

	private Revision revisionToRun;
	private String testName;

	public SimulationRunner(Revision revisionToRun, String testName) {
		this.revisionToRun = revisionToRun;
		this.testName = testName;
	}

	/**
	 * Starts the configured number of simulation runs in parallel.
	 * 
	 * @param config - Describing the simulation run configuration.
	 * @return simulation results of the simulation runs.
	 */
	public SimulationResults run(RunConfig config) {

		File tmpDir = createTmpDir();
		SimulationResults results = initResults(config.getModel(),
				config.getAnalysisMode());

		// Distribute the simulation runs among the available processors/cores.
		ExecutorService service = Executors.newFixedThreadPool(Runtime
				.getRuntime().availableProcessors());
		ArrayList<Run> runs = initRuns(config, tmpDir);

		// Start simulation runs in parallel.
		try {
			List<Future<RunInfo>> futures = service.invokeAll(runs);
			for (Future<RunInfo> f : futures) {
				RunInfo info = f.get();
				results.getRunInfos().add(info);
			}
		} catch (InterruptedException e1) {
			log.error("Error running simulation.", e1);
		} catch (ExecutionException e) {
			log.error("Error running simulation.", e);
		}
		service.shutdownNow();

		// Collect results
		parseLogs(results);

		if (!config.isExpectError()) {
			parseResults(results, config.getAnalysisMode());
		}

		// cleanTmpDir(tmpDir);

		return results;
	}

	/**
	 * Delete all temporary files of the simulation runs.
	 * 
	 * @param tmpDir
	 */
	private void cleanTmpDir(File tmpDir) {
		FileFilter filter = new WildcardFileFilter(new String[] {
				"*.properties", "*.xml", "SimQPN_Output_*.log" });
		for (File f : tmpDir.listFiles(filter)) {
			f.delete();
		}
	}

	/**
	 * Parses the log files of all simulation runs for errors and warnings.
	 * 
	 * @param results
	 */
	private void parseLogs(SimulationResults results) {
		if (revisionToRun == Revision.TRUNK) {
			Log4jXmlLogImport logImport = new Log4jXmlLogImport();

			for (RunInfo info : results.getRunInfos()) {
				try {
					File xmlLog = new File(info.getRunDirectory(),
							"errorLog.xml");
					if (xmlLog.exists()) {
						logImport.execute(xmlLog, info);
					} else {
						log.error("Could not find xml log file.");
						fail();
					}
				} catch (Exception e) {
					log.error("Error reading xml log file.", e);
					fail();
				}
			}
		}
	}

	/**
	 * Parse the results files of all simulation runs and add them to the
	 * SimulationResults object.
	 * 
	 * @param results
	 * @param mode
	 */
	private void parseResults(SimulationResults results, AnalysisMode mode) {
		if (mode == AnalysisMode.WELCH) {
			// The method of welch dumps its results in a number of files. These
			// files are read here.
			WelchDumpImport welchImport = new WelchDumpImport();

			for (RunInfo info : results.getRunInfos()) {
				results.getWallClockTime().addSample(info.getWallClockTime());
				welchImport.execute(info, results);
			}
		} else if ((mode == AnalysisMode.BATCH_MEANS)
				&& ((revisionToRun == Revision.R162) || (revisionToRun == Revision.TRUNK))) {
			// *.simqpn files are only generated when using BATCHMEANS with
			// newer versions
			// of the simulator (Revision >= R162)
			SimQPNResultFileImport resultImport = new SimQPNResultFileImport();

			for (RunInfo info : results.getRunInfos()) {
				results.getWallClockTime().addSample(info.getWallClockTime());
				try {
					FileFilter simqpnFilter = new SuffixFileFilter(".simqpn");
					File[] resultFiles = info.getRunDirectory().listFiles(
							simqpnFilter);
					if (resultFiles.length < 1) {
						log.error("Simqpn result file not found.");
						fail();
					}
					if (resultFiles.length > 1) {
						log.error("Multiple simqpn result files found.");
						fail();
					}
					resultImport.execute(resultFiles[0], results);
				} catch (Exception e) {
					log.error("Error reading simqpn result file.", e);
					fail();
				}
			}
		} else {
			// Otherwise parse results from the console output.
			ConsoleLogImport consoleImport = new ConsoleLogImport();

			for (RunInfo info : results.getRunInfos()) {
				results.getWallClockTime().addSample(info.getWallClockTime());
				consoleImport.execute(info, results);
			}
		}
	}

	/**
	 * Initialize a list of simulation runs.
	 * 
	 * @param config
	 *            - Describes the configuration of the run.
	 * @param tmpDir
	 *            - Base temporary directory.
	 * @return list of run objects.
	 */
	private ArrayList<Run> initRuns(RunConfig config, File tmpDir) {
		ArrayList<Run> runs = new ArrayList<Run>(config.getRepeats());
		for (int i = 0; i < config.getRepeats(); i++) {

			// Create a separate tmp dir for each run, in order to be able to
			// distingiush between files of different runs
			File runDir = new File(tmpDir, RUN_DIR_PREFIX + i);
			if (!runDir.mkdir()) {
				LogMF.error(log,
						"Could not create temporary run directory for run {0}",
						new Object[] { i });
				fail();
			}
			switch (revisionToRun) {
			case R100:
				runs.add(new HistoricRun(i, new File(HISTORIC_EXECUTABLE_R100),
						config, runDir));
				break;
			case R162:
				runs.add(new HistoricRun(i, new File(HISTORIC_EXECUTABLE_R162),
						config, runDir));
				break;
			case TRUNK:
				runs.add(new TrunkRun(i, config, runDir));
				break;
			}
		}
		return runs;
	}

	/**
	 * Initializes the simulation results container with the structure of the
	 * net model if necessary.
	 * 
	 * @param modelFile
	 *            - QPN model file.
	 * @param mode
	 *            - Analysis mode.
	 * @return Newly created simulation results container.
	 */
	private SimulationResults initResults(File modelFile, AnalysisMode mode) {
		SimulationResults results = new SimulationResults();
		if ((revisionToRun == Revision.R100)
				|| (mode != AnalysisMode.BATCH_MEANS)) {
			// Import of the model structure is only necessary if parsing the
			// results from the console.
			ModelImport extractor = new ModelImport();
			try {
				extractor.initWithModelStructure(results, modelFile);
			} catch (Exception e) {
				log.error("Could not read model structure.", e);
				throw new RuntimeException();
			}
		}
		return results;
	}

	
	/**
	 * Creates a new base temporary directory.
	 * 
	 * @return Reference to the created temporary directory.
	 */
	private File createTmpDir() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd_HHmmssSSS");
		File tmpDir = new File(TMP_BASE_DIR + dateFormat.format(new Date())
				+ File.separator + testName);
		if (!tmpDir.mkdirs()) {
			log.error("Could not create temporary directory");
			fail();
		}
		return tmpDir;
	}
}
