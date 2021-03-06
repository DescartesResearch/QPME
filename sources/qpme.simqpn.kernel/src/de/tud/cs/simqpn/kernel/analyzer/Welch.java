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
 * Original Author(s):  J?rgen Walter
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2013/??/??  J?rgen Walter     Created.
 * 
 */
package de.tud.cs.simqpn.kernel.analyzer;

import static de.tud.cs.simqpn.kernel.util.LogUtil.formatMultilineMessage;

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
import org.dom4j.XPath;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.stats.AggregateStats;
import de.tud.cs.simqpn.kernel.entities.stats.Stats;
import de.tud.cs.simqpn.kernel.executor.sequential.SequentialExecutor;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;

public class Welch extends Analyzer {
	private static Logger log = Logger.getLogger(Welch.class);
	private static SimulatorProgress progressMonitor;

	public Stats[] analyze(Net net, SimQPNConfiguration configuration,
			SimulatorProgress monitor, int verbosityLevel) throws SimQPNException {
//		XMLWelch.configurePlaceStats(net.getPlaces(), XMLDescription,
//					configurationName);

		return runWelchMtd(net, configuration, monitor);
	}

	/**
	 * Method runWelchMtd - runs the method of Welch
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	private static AggregateStats[] runWelchMtd(Net net,
			SimQPNConfiguration configuration, SimulatorProgress monitor)
			throws SimQPNException {
		progressMonitor = monitor;

		if (configuration.getNumRuns() < 5) {
			log.warn(formatMultilineMessage(
					"Number of runs for the method of Welch should be at least 5!",
					"Setting numRuns to 5."));
			configuration.setNumRuns(5);
		}
		configuration.setNumRuns(1);


		Place pl;
		AggregateStats[] aggrStats = initAggregateStats(configuration, net);

		progressMonitor.startSimulation(configuration);

		ExecutorService executorService = Executors.newFixedThreadPool(Runtime
				.getRuntime().availableProcessors());

		List<Callable<Net>> listOfRuns = new ArrayList<Callable<Net>>();
		for (int i = 0; i < configuration.getNumRuns(); i++) {
			Net netCopy = net.clone(configuration);

			Callable<Net> run = new SequentialExecutor(netCopy, configuration,
					monitor, i + 1);
			listOfRuns.add(run);

			if (progressMonitor.isCanceled())
				break;
		}
		List<Future<Net>> listOfSimulatedNets;
		try {
			listOfSimulatedNets = (List<Future<Net>>) executorService
					.invokeAll((List<? extends Callable<Net>>) listOfRuns);
			for (Future<Net> future : listOfSimulatedNets) {
				try {
					Net futureNet = future.get();
					for (int p = 0; p < net.getNumPlaces(); p++) {
						pl = futureNet.getPlace(p);
						if (pl.statsLevel > 0) {
							if (pl instanceof QPlace) {
								QPlace qPl = (QPlace) pl;
								aggrStats[p * 2].saveStats(
										qPl.qPlaceQueueStats, configuration);
								aggrStats[(p * 2) + 1].saveStats(
										qPl.placeStats, configuration);
							} else {
								aggrStats[p * 2].saveStats(pl.placeStats,
										configuration);
							}
						}
					}
				} catch (InterruptedException e) {
					log.error("", e);
				} catch (ExecutionException e2) {
					log.error("", e2);
				}
			}
		} catch (InterruptedException e1) {
			log.error("", e1);
		}
		executorService.shutdown();

		progressMonitor.finishSimulation();

//		for (int i = 0; i < 2 * net.getNumPlaces(); i++)
		for (int i = 0; i < aggrStats.length; i++)
			if (aggrStats[i] != null)
				aggrStats[i].finish(configuration);

		progressMonitor = null;

		return aggrStats;
	}

	private static AggregateStats[] initAggregateStats(
			SimQPNConfiguration configuration, Net net)
			throws SimQPNException {
		int numPlaces = net.getNumPlaces();
		Place[] places = net.getPlaces();
		Place pl;

		AggregateStats[] aggrStats = new AggregateStats[numPlaces * 2];
		// Note: aggrStats should be large enough to accomodate 2 stats per
		// place in case all places are queueing places.


		for (int p = 0; p < numPlaces; p++) {
			pl = places[p];
			if (pl.statsLevel > 0) {
				if (pl instanceof QPlace) {
					aggrStats[p * 2] = new AggregateStats(pl.id, pl.name,
							Stats.QUE_PLACE_QUEUE, pl.numColors, pl.statsLevel,
							configuration);
					aggrStats[(p * 2) + 1] = new AggregateStats(pl.id, pl.name,
							Stats.QUE_PLACE_DEP, pl.numColors, pl.statsLevel,
							configuration);
				} else {
					aggrStats[p * 2] = new AggregateStats(pl.id, pl.name,
							Stats.ORD_PLACE, pl.numColors, pl.statsLevel,
							configuration);
					aggrStats[(p * 2) + 1] = null;
				}
			} else {
				aggrStats[p * 2] = null;
				aggrStats[(p * 2) + 1] = null;
			}
		}
		return aggrStats;
	}

	@Override
	public File writeToFile(Stats[] result, SimQPNConfiguration configuration,
			String outputFileName, Element XML, String configurationName)
			throws SimQPNException {
		// TODO Implement a write to File method
		return null;
	}

}
