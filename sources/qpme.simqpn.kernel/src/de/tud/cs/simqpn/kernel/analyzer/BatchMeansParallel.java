package de.tud.cs.simqpn.kernel.analyzer;

import java.util.ArrayList;
import java.util.List;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.Queue;
import de.tud.cs.simqpn.kernel.executor.Executor;
import de.tud.cs.simqpn.kernel.executor.parallel.ParallelExecutor;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;
import de.tud.cs.simqpn.kernel.stats.Stats;

public class BatchMeansParallel implements Analyzer {

	private static SimulatorProgress progressMonitor;

	@Override
	public Stats[] analyze(Net net, SimQPNConfiguration configuration,
			SimulatorProgress monitor)
			throws SimQPNException {
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
				if (queue != null && queue.queueStats != null) {
					stats.add(queue.queueStats);
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

		ParallelExecutor parallelExecutor = new ParallelExecutor(net, configuration, monitor);
		progressMonitor.startSimulationRun(1, configuration);
		parallelExecutor.run();
		progressMonitor.finishSimulationRun();
		progressMonitor.finishSimulation();
		progressMonitor = null;
		return new SimulatorResults(net.getPlaces(), net.getQueues(),
				net.getProbes());
	}
}
