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
import de.tud.cs.simqpn.kernel.executor.sequential.SequentialExecutor;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;
import de.tud.cs.simqpn.kernel.persistency.StatsDocumentBuilder;
import de.tud.cs.simqpn.kernel.stats.Stats;

/** Method of non-overlapping batch means */
public class BatchMeans extends Analyzer {
	private static Logger log = Logger.getLogger(BatchMeans.class);

	private static SimulatorProgress progressMonitor;
	
	public BatchMeans() {};

	@Override
	public Stats[] analyze(Net net, SimQPNConfiguration configuration,
			SimulatorProgress monitor) throws SimQPNException {
		
		SimulatorResults results = runBatchMeans(net, configuration, monitor);

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
	private static SimulatorResults runBatchMeans(Net net,
			SimQPNConfiguration configuration, SimulatorProgress monitor)
			throws SimQPNException {
		progressMonitor = monitor;
		progressMonitor.startSimulation(configuration);

		Callable<Net> run;
		int verbosityLevel = 0;
		//run = new JBarrierExecutor(net, configuration,monitor,1, verbosityLevel);			
		//run = new CyclicBarrierExecutor(net, configuration,monitor,1, verbosityLevel);			
		//run = new PseudoParallelExecutor(net, configuration,monitor,1, verbosityLevel);			
		//run = new FlexibleParallelExecutor(net, configuration,monitor,1);			
		//run = new ParallelExecutor(net, configuration, monitor, 1);			
		run = new SequentialExecutor(net, configuration,monitor,1);

		try {
			net = run.call();
		} catch (Exception e) {
			log.error(""+e.getStackTrace(),e);
		}
		
		progressMonitor.finishSimulation();
		progressMonitor = null;
		return new SimulatorResults(net.getPlaces(), net.getQueues(),
				net.getProbes());
	}

	@Override
	public File writeToFile(Stats[] result, SimQPNConfiguration configuration, String outputFileName, Element  XMLNet, String configurationName) throws SimQPNException {
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
