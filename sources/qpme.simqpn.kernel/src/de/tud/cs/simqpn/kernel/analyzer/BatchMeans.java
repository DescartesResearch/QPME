package de.tud.cs.simqpn.kernel.analyzer;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import de.tud.cs.simqpn.kernel.SimQPNController;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.Queue;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;
import de.tud.cs.simqpn.kernel.stats.Stats;

public class BatchMeans implements Analyzer {

	private static SimulatorProgress progressMonitor;

	@Override
	public Stats[] analyze(Element XMLNet, String configuration,
			SimulatorProgress monitor, SimQPNController sim) throws SimQPNException {
		SimulatorResults results = runBatchMeans(XMLNet, configuration, monitor);

		List<Stats> stats = new ArrayList<Stats>();
		for (int p = 0; p < results.getPlaces().length; p++) {
			stats.add(results.getPlaces()[p].placeStats);
			if (results.getPlaces()[p] instanceof QPlace) {
				stats.add(((QPlace) results.getPlaces()[p]).qPlaceQueueStats);
			}
			results.getPlaces()[p].report(sim);
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
			results.getProbes()[pr].report(sim);
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
	private static SimulatorResults runBatchMeans(Element netXML,
			String configuration, SimulatorProgress monitor)
			throws SimQPNException {
		progressMonitor = monitor;
		progressMonitor.startSimulation();
		SimQPNController sim = new SimQPNController(netXML, configuration);
		System.out.println("[1] "+sim.configuration);
		sim.getReady(netXML, configuration);
		System.out.println("[2] "+sim.configuration);
		progressMonitor.startSimulationRun(1);
		sim = sim.run();
		progressMonitor.finishSimulationRun();
		progressMonitor.finishSimulation();
		progressMonitor = null;
		return new SimulatorResults(sim.getNet().getPlaces(), sim.getNet()
				.getQueues(), sim.getNet().getProbes());
	}
}
