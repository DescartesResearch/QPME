package de.tud.cs.simqpn.kernel.analyzer;

import static de.tud.cs.simqpn.kernel.util.LogUtil.formatDetailMessage;
import static de.tud.cs.simqpn.kernel.util.LogUtil.formatMultilineMessage;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.XPath;

import de.tud.cs.simqpn.kernel.SimQPNController;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.loader.NetLoader;
import de.tud.cs.simqpn.kernel.loader.XMLHelper;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;
import de.tud.cs.simqpn.kernel.stats.AggregateStats;
import de.tud.cs.simqpn.kernel.stats.Stats;

public class Welch implements Analyzer {
	private static Logger log = Logger.getLogger(Welch.class);
	private static SimulatorProgress progressMonitor;


	@Override
	public Stats[] analyze(Element XMLNet, String configuration,
			SimulatorProgress monitor) throws SimQPNException {
		runWelchMtd(XMLNet, configuration, monitor);
		return null;
	}

	/**
	 * Method runWelchMtd - runs the method of Welch
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public static AggregateStats[] runWelchMtd(Element netXML,
			String configuration, SimulatorProgress monitor)
			throws SimQPNException {
		XPath xpathSelector = XMLHelper.createXPath("//place");
		List<Element> placeList = xpathSelector.selectNodes(netXML);

		progressMonitor = monitor;

		SimQPNController sim = new SimQPNController(netXML, configuration);
		
		if (sim.configuration.getNumRuns() < 5) {
			log.warn(formatMultilineMessage(
					"Number of runs for the method of Welch should be at least 5!",
					"Setting numRuns to 5."));
			sim.configuration.setNumRuns(5);
		}


		int numPlaces = sim.getNet().getNumPlaces();
		Place[] places = sim.getNet().getPlaces();
		// List placeList = sim.getNet().getPlaceList();

		AggregateStats[] aggrStats = new AggregateStats[numPlaces * 2];
		// Note: aggrStats should be large enough to accomodate 2 stats per
		// place in case all places are queueing places.

		Place pl;

		for (int p = 0; p < numPlaces; p++) {
			pl = places[p];
			if (pl.statsLevel > 0) {
				if (pl instanceof QPlace) {
					aggrStats[p * 2] = new AggregateStats(pl.id, pl.name,
							Stats.QUE_PLACE_QUEUE, pl.numColors, pl.statsLevel);
					aggrStats[(p * 2) + 1] = new AggregateStats(pl.id, pl.name,
							Stats.QUE_PLACE_DEP, pl.numColors, pl.statsLevel);
				} else {
					aggrStats[p * 2] = new AggregateStats(pl.id, pl.name,
							Stats.ORD_PLACE, pl.numColors, pl.statsLevel);
					aggrStats[(p * 2) + 1] = null;
				}
			} else {
				aggrStats[p * 2] = null;
				aggrStats[(p * 2) + 1] = null;
			}
		}

		progressMonitor.startSimulation();

		// Run replication loop
		for (int i = 0; i < sim.configuration.getNumRuns(); i++) {
			/*
			 * BEGIN-CONFIG
			 * ------------------------------------------------------
			 * ----------------------------------------------------------
			 * 
			 * minObsrvST - Minumum number of observations required maxObsrvST -
			 * Maximum number of observations considered (if <= 0 token color is
			 * not considered in the analysis).
			 * 
			 * ... places[p].placeStats.minObsrvST/maxObsrvST ... ((QPlace)
			 * places[p]).qPlaceQueueStats[c]/maxObsrvST[c]
			 * 
			 * Note: Places/QPlaces with (StatsLevel < 3) are not considered in
			 * the analysis! Note: If (maxObsrvST[c] <= 0) the respective token
			 * color is not considered in the analysis!
			 */

			// Iterate through all places.
			Iterator placeIterator = placeList.iterator();

			/*
			 * SDK-DEBUG: - Does placeList.iterator() always return places in
			 * the same order (matching the order in the places array)? CHRIS:
			 * It returns the Elements in the document order. If this is not
			 * changed, then it always returns them in the same order. - Note
			 * that XML in general does not guarantee any ordering of elements.
			 * CHRIS: In general there is no guarantee, but the dom
			 * implementation used does respect the order of elements. The only
			 * problems I encountered was when merging sets of nodes, but since
			 * I don't do that, there should be no problems.
			 */
			for (int p = 0; placeIterator.hasNext(); p++) {
				Element place = (Element) placeIterator.next();
				pl = places[p];
				// If the stats-level is 3 or higher set minObsrvST and
				// maxObsrvST for each
				// color-ref of the current place (depository and queue).
				// These values are used in WELCH method.
				if (pl.statsLevel >= 3) {
					xpathSelector = XMLHelper
							.createXPath("color-refs/color-ref");
					Iterator colorRefIterator = xpathSelector
							.selectNodes(place).iterator();
					for (int c = 0; colorRefIterator.hasNext(); c++) {
						Element colorRef = (Element) colorRefIterator.next();
						Element element = XMLHelper.getSettings(colorRef,
								configuration);
						if (element == null
								|| element.attributeValue("minObsrv") == null
								|| element.attributeValue("maxObsrv") == null) {
							log.error(formatDetailMessage(
									"minObsrv/maxObsrv settings for the Method of Welch not found!",
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"color-num", Integer.toString(c),
									"colorRef.id",
									colorRef.attributeValue("id"),
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
										"place.name",
										place.attributeValue("name"),
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
									+ "] = "
									+ qPl.qPlaceQueueStats.minObsrvST[c]);
							log.debug("qPl.qPlaceQueueStats.maxObsrvST[" + c
									+ "] = "
									+ qPl.qPlaceQueueStats.maxObsrvST[c]);
						}
					}
				}
			}
			// END-CONFIG
			// -----------------------------------------------------------------------------------------

			progressMonitor.startSimulationRun(i + 1);
			sim.run();
			progressMonitor.finishSimulationRun();

			for (int p = 0; p < numPlaces; p++) {
				pl = places[p];
				if (pl.statsLevel > 0) {
					if (pl instanceof QPlace) {
						QPlace qPl = (QPlace) pl;
						aggrStats[p * 2].saveStats(qPl.qPlaceQueueStats);
						aggrStats[(p * 2) + 1].saveStats(qPl.placeStats);
					} else {
						aggrStats[p * 2].saveStats(pl.placeStats);
					}
				}
			}

			if (progressMonitor.isCanceled())
				break;

			sim = new SimQPNController(netXML, configuration);
			places = sim.getNet().getPlaces();
		}

		progressMonitor.finishSimulation();

		for (int i = 0; i < 2 * numPlaces; i++)
			if (aggrStats[i] != null)
				aggrStats[i].finish();

		progressMonitor = null;

		return aggrStats;
	}

}
