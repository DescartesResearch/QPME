package de.tud.cs.simqpn.kernel.analyzer;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.executor.sequential.SequentialExecutor;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;
import de.tud.cs.simqpn.kernel.stats.AggregateStats;
import de.tud.cs.simqpn.kernel.stats.Stats;

public class ReplicationDeletion implements Analyzer {

	private static SimulatorProgress progressMonitor;
	private static Logger log = Logger.getLogger(ReplicationDeletion.class);
	private AggregateStats[] aggregateStats;

	/**
	 * Replication/Deletion Approach (Method of Independent Replications)
	 */
	public ReplicationDeletion(AggregateStats[] aggregateStats) {
		this.aggregateStats = aggregateStats;
	}

	@Override
	public Stats[] analyze(Net net, SimQPNConfiguration configuration,
			SimulatorProgress monitor) throws SimQPNException {
		AggregateStats[] finishedAggrStats = runMultRepl(net, configuration,
				monitor, aggregateStats);
		return finishedAggrStats;
	}

	/**
	 * Method runMultRepl - runs multiple replications Used in MULT_REPL mode as
	 * well as for the REPL/DEL method in NORMAL mode
	 * 
	 * MULT_REPL mode is used to study the behavior of analysis methods when run
	 * multiple times
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	private AggregateStats[] runMultRepl(Net net,
			SimQPNConfiguration configuration, SimulatorProgress monitor,
			AggregateStats[] aggregateStats) throws SimQPNException {
		if (configuration.getNumRuns() <= 1) {
			log.error("numRuns should be > 1!");
			throw new SimQPNException();
		}
		if (aggregateStats == null) {
			log.error("Aggregate stats array not initialized!");
			throw new SimQPNException();
		}
		int numPlaces = net.getNumPlaces();
		progressMonitor = monitor;
		progressMonitor.startSimulation(configuration);

		// Run replication loop
		for (int i = 0; i < configuration.getNumRuns(); i++) {
			Net netCopy = net.clone(configuration);
			Callable<Net> executor = new SequentialExecutor(netCopy,
					configuration, monitor, i + 1);
			try {
				executor.call();
			} catch (Exception e) {
				log.error("", e);
			}

			putStatisticsIntoArray(configuration, numPlaces, netCopy);

			if (progressMonitor.isCanceled())
				break;
		}
		progressMonitor.finishSimulation();

		for (int i = 0; i < 2 * numPlaces; i++) {
			if (aggregateStats[i] != null) {
				aggregateStats[i].finish(configuration);
			}
		}

		progressMonitor = null;
		for (int i = 0; i < aggregateStats.length; i++)
			if (aggregateStats[i] != null)
				aggregateStats[i].printReport(configuration);

		return aggregateStats;
	}

	private void putStatisticsIntoArray(SimQPNConfiguration configuration,
			int numPlaces, Net netCopy) throws SimQPNException {
		for (int p = 0; p < numPlaces; p++) {
			Place pl = netCopy.getPlace(p);
			if (pl.statsLevel > 0) {
				if (pl instanceof QPlace) {
					QPlace qPl = (QPlace) pl;
					aggregateStats[p * 2].saveStats(qPl.qPlaceQueueStats,
							configuration);
					aggregateStats[(p * 2) + 1].saveStats(qPl.placeStats,
							configuration);
				} else {
					aggregateStats[p * 2].saveStats(pl.placeStats,
							configuration);
				}
			}
		}
	}

}
