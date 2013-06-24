package de.tud.cs.simqpn.kernel.analyzer;

import static de.tud.cs.simqpn.kernel.util.LogUtil.formatDetailMessage;
import static de.tud.cs.simqpn.kernel.util.LogUtil.formatMultilineMessage;

import java.util.ArrayList;
import java.util.Iterator;
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
import de.tud.cs.simqpn.kernel.executor.SequentialExecutor;
import de.tud.cs.simqpn.kernel.loader.XMLHelper;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;
import de.tud.cs.simqpn.kernel.stats.AggregateStats;
import de.tud.cs.simqpn.kernel.stats.Stats;

public class Welch {// implements Analyzer {
	private static Logger log = Logger.getLogger(Welch.class);
	private static SimulatorProgress progressMonitor;

	// @Override
	public Stats[] analyze(Net net, SimQPNConfiguration configuration,
			SimulatorProgress monitor) throws SimQPNException {
		// runWelchMtd(net, configuration, monitor);
		throw (new SimQPNException());
	}

	public Stats[] analyze2(Net net, SimQPNConfiguration configuration,
			SimulatorProgress monitor, Element netXML, String configurationName)
			throws SimQPNException {
		return runWelchMtd(net, configuration, monitor, netXML,
				configurationName);
	}

	/**
	 * Method runWelchMtd - runs the method of Welch
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	private static AggregateStats[] runWelchMtd(Net net,
			SimQPNConfiguration configuration, SimulatorProgress monitor,
			Element netXML, String configurationName) throws SimQPNException {

		progressMonitor = monitor;

		if (configuration.getNumRuns() < 5) {
			log.warn(formatMultilineMessage(
					"Number of runs for the method of Welch should be at least 5!",
					"Setting numRuns to 5."));
			configuration.setNumRuns(5);
		}

		int numPlaces = net.getNumPlaces();
		Place[] places = net.getPlaces();

		XPath xpathSelector = XMLHelper.createXPath("//place");
		List<Element> placeList = xpathSelector.selectNodes(netXML);

		Place pl;
		AggregateStats[] aggrStats = initAggregateStats(configuration,
				numPlaces, places);

		progressMonitor.startSimulation(configuration);

		ExecutorService executorService = Executors.newFixedThreadPool(Runtime
				.getRuntime().availableProcessors());

		List<Callable<Net>> listOfRuns = new ArrayList<Callable<Net>>();
		for (int i = 0; i < configuration.getNumRuns(); i++) {
			Net netCopy = new Net(net, configuration);
			netCopy.finishCloning(net, configuration);

			Callable<Net> run = new SequentialExecutor(netCopy,
					configuration, monitor, i + 1);
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

					for (int p = 0; p < numPlaces; p++) {
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
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		executorService.shutdown();

		progressMonitor.finishSimulation();

		for (int i = 0; i < 2 * numPlaces; i++)
			if (aggrStats[i] != null)
				aggrStats[i].finish(configuration);

		progressMonitor = null;

		return aggrStats;
	}

	private static void config(Place[] places, List<Element> placeList,
			String configurationName) throws SimQPNException {
		XPath xpathSelector;
		Place pl;
		/*
		 * BEGIN-CONFIG ------------------------------------------------------
		 * ----------------------------------------------------------
		 * 
		 * minObsrvST - Minumum number of observations required maxObsrvST -
		 * Maximum number of observations considered (if <= 0 token color is not
		 * considered in the analysis).
		 * 
		 * ... places[p].placeStats.minObsrvST/maxObsrvST ... ((QPlace)
		 * places[p]).qPlaceQueueStats[c]/maxObsrvST[c]
		 * 
		 * Note: Places/QPlaces with (StatsLevel < 3) are not considered in the
		 * analysis! Note: If (maxObsrvST[c] <= 0) the respective token color is
		 * not considered in the analysis!
		 */

		// Iterate through all places.
		Iterator<Element> placeIterator = placeList.iterator();

		/*
		 * SDK-DEBUG: - Does placeList.iterator() always return places in the
		 * same order (matching the order in the places array)? CHRIS: It
		 * returns the Elements in the document order. If this is not changed,
		 * then it always returns them in the same order. - Note that XML in
		 * general does not guarantee any ordering of elements. CHRIS: In
		 * general there is no guarantee, but the dom implementation used does
		 * respect the order of elements. The only problems I encountered was
		 * when merging sets of nodes, but since I don't do that, there should
		 * be no problems.
		 */
		for (int p = 0; placeIterator.hasNext(); p++) {
			Element place = (Element) placeIterator.next();
			pl = places[p];
			// If the stats-level is 3 or higher set minObsrvST and
			// maxObsrvST for each
			// color-ref of the current place (depository and queue).
			// These values are used in WELCH method.
			if (pl.statsLevel >= 3) {
				xpathSelector = XMLHelper.createXPath("color-refs/color-ref");
				Iterator<Element> colorRefIterator = xpathSelector.selectNodes(
						place).iterator();
				for (int c = 0; colorRefIterator.hasNext(); c++) {
					Element colorRef = (Element) colorRefIterator.next();
					Element element = XMLHelper.getSettings(colorRef,
							configurationName);
					if (element == null
							|| element.attributeValue("minObsrv") == null
							|| element.attributeValue("maxObsrv") == null) {
						log.error(formatDetailMessage(
								"minObsrv/maxObsrv settings for the Method of Welch not found!",
								"place.id", place.attributeValue("id"),
								"place.name", place.attributeValue("name"),
								"color-num", Integer.toString(c),
								"colorRef.id", colorRef.attributeValue("id"),
								"colorRef.color-id",
								colorRef.attributeValue("color-id")));
						throw new SimQPNException();
					}
					pl.placeStats.minObsrvST[c] = Integer.parseInt(element
							.attributeValue("minObsrv"));
					pl.placeStats.maxObsrvST[c] = Integer.parseInt(element
							.attributeValue("maxObsrv"));
					log.debug("pl.placeStats.minObsrvST[" + c + "] = "
							+ pl.placeStats.minObsrvST[c]);
					log.debug("pl.placeStats.maxObsrvST[" + c + "] = "
							+ pl.placeStats.maxObsrvST[c]);
					if (pl instanceof QPlace) {
						QPlace qPl = (QPlace) pl;
						if (element.attributeValue("queueMinObsrv") == null
								|| element.attributeValue("queueMaxObsrv") == null) {
							log.error(formatDetailMessage(
									"queueMinObsrv/queueMaxObsrv settings for the Method of Welch not found!",
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"color-num", Integer.toString(c),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						qPl.qPlaceQueueStats.minObsrvST[c] = Integer
								.parseInt(element
										.attributeValue("queueMinObsrv"));
						qPl.qPlaceQueueStats.maxObsrvST[c] = Integer
								.parseInt(element
										.attributeValue("queueMaxObsrv"));
						log.debug("qPl.qPlaceQueueStats.minObsrvST[" + c
								+ "] = " + qPl.qPlaceQueueStats.minObsrvST[c]);
						log.debug("qPl.qPlaceQueueStats.maxObsrvST[" + c
								+ "] = " + qPl.qPlaceQueueStats.maxObsrvST[c]);
					}
				}
			}
		}
		// END-CONFIG
		// -----------------------------------------------------------------------------------------
	}

	private static AggregateStats[] initAggregateStats(
			SimQPNConfiguration configuration, int numPlaces, Place[] places)
			throws SimQPNException {
		AggregateStats[] aggrStats = new AggregateStats[numPlaces * 2];
		// Note: aggrStats should be large enough to accomodate 2 stats per
		// place in case all places are queueing places.

		Place pl;

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

}
