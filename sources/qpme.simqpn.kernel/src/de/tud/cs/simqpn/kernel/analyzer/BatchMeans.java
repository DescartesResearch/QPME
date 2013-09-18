package de.tud.cs.simqpn.kernel.analyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.queue.Queue;
import de.tud.cs.simqpn.kernel.executor.parallel.FlexibleParallelExecutor;
import de.tud.cs.simqpn.kernel.executor.parallel.JBarrierExecutor;
import de.tud.cs.simqpn.kernel.executor.parallel.ParallelExecutor;
import de.tud.cs.simqpn.kernel.executor.parallel.PseudoParallelExecutor;
import de.tud.cs.simqpn.kernel.executor.sequential.SequentialExecutor;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;
import de.tud.cs.simqpn.kernel.stats.Stats;

public class BatchMeans implements Analyzer {
	private static Logger log = Logger.getLogger(BatchMeans.class);

	private static SimulatorProgress progressMonitor;
	
	/** Method of non-overlapping batch means */
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
		//run = new JBarrierExecutor(net, configuration,monitor,1);			
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
}
