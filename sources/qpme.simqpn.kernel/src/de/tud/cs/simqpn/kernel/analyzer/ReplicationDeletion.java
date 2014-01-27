package de.tud.cs.simqpn.kernel.analyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.executor.sequential.SequentialExecutor;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;
import de.tud.cs.simqpn.kernel.random.RandomNumberGenerator;
import de.tud.cs.simqpn.kernel.stats.AggregateStats;
import de.tud.cs.simqpn.kernel.stats.Stats;

public class ReplicationDeletion extends Analyzer {

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
		AggregateStats[] finishedAggrStats = runMultipleReplications(net,
				configuration, monitor, aggregateStats);
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
	private AggregateStats[] runMultipleReplications(Net net,
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

		if (!configuration.isParallel()) {
			for (int i = 0; i < configuration.getNumRuns(); i++) {
				runReplication(net, configuration, monitor, numPlaces, i);
				if (progressMonitor.isCanceled()){
					break;
				}
			}
		} else {
			ExecutorService executorService = Executors
					.newFixedThreadPool(Runtime.getRuntime()
							.availableProcessors());

			List<Callable<Net>> listOfRuns = new ArrayList<Callable<Net>>();
			for (int i = 0; i < configuration.getNumRuns(); i++) {
				Net netCopy = net.clone(configuration);

				Callable<Net> run = new SequentialExecutor(netCopy,
						configuration, RandomNumberGenerator.nextRandNumGen(),
						monitor, i + 1);
				listOfRuns.add(run);


			}
			List<Future<Net>> listOfSimulatedNets;
			try {
				listOfSimulatedNets = (List<Future<Net>>) executorService
						.invokeAll((List<? extends Callable<Net>>) listOfRuns);
				for (Future<Net> future : listOfSimulatedNets) {
					try {
						Net futureNet = future.get();
						putStatisticsIntoArray(configuration, numPlaces,
								futureNet);
					} catch (InterruptedException e) {
						log.error("", e);
					} catch (ExecutionException e2) {
						log.error("", e2);
					}

					if (progressMonitor.isCanceled()) {
						break;
					}
				}
			} catch (InterruptedException e1) {
				log.error("", e1);
			}
			executorService.shutdown();
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

	private void runReplication(Net net, SimQPNConfiguration configuration,
			SimulatorProgress monitor, int numPlaces, int i)
			throws SimQPNException {
		Net netCopy = net.clone(configuration);
		Callable<Net> executor = new SequentialExecutor(netCopy,
				configuration, monitor, i + 1);
		try {
			executor.call();
		} catch (Exception e) {
			log.error("", e);
		}

		putStatisticsIntoArray(configuration, numPlaces, netCopy);

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

	@Override
	public File writeToFile(Stats[] result, SimQPNConfiguration configuration,
			String outputFileName, Element XML, String configurationName)
			throws SimQPNException {
		// TODO Implement
		return null;
	}

}
