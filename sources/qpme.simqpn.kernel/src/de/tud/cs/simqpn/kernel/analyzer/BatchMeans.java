package de.tud.cs.simqpn.kernel.analyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.queue.Queue;
import de.tud.cs.simqpn.kernel.entities.stats.Stats;
import de.tud.cs.simqpn.kernel.executor.parallel.JBarrierExecutor;
import de.tud.cs.simqpn.kernel.executor.parallel.LP;
import de.tud.cs.simqpn.kernel.executor.parallel.decomposition.NetDecomposer;
import de.tud.cs.simqpn.kernel.executor.sequential.SequentialExecutor;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;
import de.tud.cs.simqpn.kernel.persistency.StatsDocumentBuilder;

/** Method of non-overlapping batch means */
public class BatchMeans extends Analyzer {
	private static Logger log = Logger.getLogger(BatchMeans.class);

	private static SimulatorProgress progressMonitor;

	public BatchMeans() {
	};

	@Override
	public Stats[] analyze(Net net, SimQPNConfiguration configuration,
			SimulatorProgress monitor, int verbosityLevel) throws SimQPNException {

		SimulatorResults results = runBatchMeans(net, configuration, monitor, verbosityLevel);
		if (results == null) {
			return null;
		}

		List<Stats> stats = new ArrayList<Stats>();
		for (int p = 0; p < results.getPlaces().length; p++) {
			stats.add(results.getPlaces()[p].placeStats);
			if (results.getPlaces()[p] instanceof QPlace) {
				stats.add(((QPlace) results.getPlaces()[p]).qPlaceQueueStats);
			}
			results.getPlaces()[p].report(configuration);
		}
		if (results.getQueues() != null) {
			for (Queue queue : results.getQueues()) {
				if (queue != null && queue.getQueueStats() != null) {
					stats.add(queue.getQueueStats());
				}
			}
		}
		for (int pr = 0; pr < results.getProbes().length; pr++) {
			stats.add(results.getProbes()[pr].probeStats);
			results.getProbes()[pr].report(configuration);
		}
		return (Stats[]) stats.toArray(new Stats[stats.size()]);
	}

	/**
	 * Method runBatchMeans - implements the method of non-overlapping batch
	 * means
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	private SimulatorResults runBatchMeans(Net net,
			SimQPNConfiguration configuration, SimulatorProgress monitor, int verbosityLevel)
			throws SimQPNException {
		progressMonitor = monitor;
		progressMonitor.startSimulation(configuration);

		Callable<Net> run;

		if (!configuration.isParallel()) {
			run = new SequentialExecutor(net, configuration, monitor, 1);
		} else {
			//log.info("Parallel simulation is limited to open workloads");
			//log.warn("No guaranties. Parallel simulation is still experimental and only applicable to open workloads.");
			if (net.getProbes().length > 0) {
				log.info("Probes are not supportet for parallel simulation.");
				log.info("Started sequential simulation ...");
				run = new SequentialExecutor(net, configuration, monitor, 1);
			} else {
				log.info("Started decomposition for parallel simulation ...");
				LP[] lps = NetDecomposer.decomposeNetIntoLPs(net,
						configuration, progressMonitor, verbosityLevel);
				if (!NetDecomposer
						.hasDecompositionSucceded(lps, verbosityLevel)) {
					log.info("Decomposition for parallel simulation failed.");
					log.info("Started sequential simulation ...");
					run = new SequentialExecutor(net, configuration, monitor, 1);
				} else {
					log.info("Started parallel simulation ...");
					run = new JBarrierExecutor(lps, configuration, monitor,
							verbosityLevel);
				}
			}
		}
		try {
			net = run.call();
			if (net == null) {
				return null;
			}
		} catch (Exception e) {
			log.error("" + e.getStackTrace(), e);
		}


		progressMonitor.finishSimulation();
		progressMonitor = null;
		return new SimulatorResults(net.getPlaces(), net.getQueues(),
				net.getProbes());
	}

	@Override
	public File writeToFile(Stats[] result, SimQPNConfiguration configuration,
			String outputFileName, Element XMLNet, String configurationName)
			throws SimQPNException {
		File resultFile = null;
		// Skip stats document generation for WELCH and REPL_DEL since the
		// document builder does not support these methods yet.

		if ((result != null)
				&& (configuration.getAnalMethod() == SimQPNConfiguration.AnalysisMethod.BATCH_MEANS)) {
			StatsDocumentBuilder builder = new StatsDocumentBuilder(result,
					XMLNet, configurationName);
			Document statsDocument = builder.buildDocument(configuration);
			if (outputFileName != null) {
				resultFile = new File(outputFileName);
			} else {
				resultFile = new File(configuration.getStatsDir(),
						builder.getResultFileBaseName() + ".simqpn");
			}
			saveXmlToFile(statsDocument, resultFile);
		}
		return resultFile;
	}

}
