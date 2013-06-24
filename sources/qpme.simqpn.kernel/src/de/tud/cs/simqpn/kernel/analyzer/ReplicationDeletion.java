package de.tud.cs.simqpn.kernel.analyzer;

import static de.tud.cs.simqpn.kernel.util.LogUtil.formatDetailMessage;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.XPath;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNController;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.executor.Executor;
import de.tud.cs.simqpn.kernel.executor.SequentialExecutor;
import de.tud.cs.simqpn.kernel.loader.XMLHelper;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;
import de.tud.cs.simqpn.kernel.stats.AggregateStats;
import de.tud.cs.simqpn.kernel.stats.Stats;

public class ReplicationDeletion implements Analyzer{

	private static SimulatorProgress progressMonitor;
	private static Logger log = Logger.getLogger(SimQPNController.class);

	@Override
	public Stats[] analyze(Net net, SimQPNConfiguration configuration,
			SimulatorProgress monitor) throws SimQPNException {
		log.error("Replication/Deletion actually not supported!");
		throw (new SimQPNException());
	}
	
	public Stats[] analyze2(Net net, SimQPNConfiguration configuration,
			SimulatorProgress monitor, Element netXML, String configurationName) throws SimQPNException {
		AggregateStats[] aggrStats = runMultRepl(net, configuration, monitor, netXML, configurationName);
		return aggrStats;
	}
	
	/**
	 * Method runMultRepl - runs multiple replications 
	 * Used in MULT_REPL mode as well as for the REPL/DEL method in NORMAL mode
	 *  
	 * MULT_REPL mode is used to study the behavior of analysis methods when run multiple times 
	 * 
	 * @param  	
	 * @return 
	 * @exception
	 */
	private static AggregateStats[] runMultRepl(Net net, SimQPNConfiguration configuration, SimulatorProgress monitor, Element netXML, String configurationName) throws SimQPNException {
		if (configuration.getNumRuns() <= 1) {
			log.error("numRuns should be > 1!");
			throw new SimQPNException();
		}				

		configuration.setUseStdStateStats(false);
		
		AggregateStats[] aggrStats = initStatsArray(net, configuration, netXML);
		int numPlaces = net.getNumPlaces();
		progressMonitor = monitor;
		progressMonitor.startSimulation(configuration);

		// Run replication loop
		for (int i = 0; i < configuration.getNumRuns(); i++) {
			progressMonitor.startSimulationRun(i + 1, configuration);

			Net netCopy = new Net(net, configuration);
			netCopy.finishCloning(net, configuration);
			Callable<Net> executor = new SequentialExecutor(netCopy, configuration, monitor, i+1);
			try {
				executor.call();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			progressMonitor.finishSimulationRun();

			putStatisticsIntoArray(configuration, aggrStats, numPlaces, netCopy);

			if (progressMonitor.isCanceled())
				break;
		}

		progressMonitor.finishSimulation();

		for (int i = 0; i < 2 * numPlaces; i++)
			if (aggrStats[i] != null)
				aggrStats[i].finish(configuration);
		
		progressMonitor = null;
		for (int i = 0; i < aggrStats.length; i++)
			if (aggrStats[i] != null)
				aggrStats[i].printReport(configuration);

		return aggrStats;
	}

	private static AggregateStats[] initStatsArray(Net net, SimQPNConfiguration configuration, Element netXML) throws SimQPNException{
		
		Place pl;
		int numPlaces = net.getNumPlaces();
		Place[] places = net.getPlaces();

		AggregateStats[] aggrStats = new AggregateStats[numPlaces * 2]; 
		// Note: aggrStats should be large enough to accomodate 2 stats per place in case all places are queueing places.
		

		for (int p = 0; p < numPlaces; p++) {
			pl = places[p];
			if (pl.statsLevel > 0) {
				if (pl instanceof QPlace) {
					aggrStats[p * 2] = new AggregateStats(pl.id, pl.name, Stats.QUE_PLACE_QUEUE, pl.numColors, pl.statsLevel, configuration);
					aggrStats[(p * 2) + 1] = new AggregateStats(pl.id, pl.name, Stats.QUE_PLACE_DEP, pl.numColors, pl.statsLevel, configuration);
				} else {
					aggrStats[p * 2] = new AggregateStats(pl.id, pl.name, Stats.ORD_PLACE, pl.numColors, pl.statsLevel, configuration);
					aggrStats[(p * 2) + 1] = null;
				}
			} else {
				aggrStats[p * 2] = null;
				aggrStats[(p * 2) + 1] = null;
			}
		}
		
		/*  CONFIG: 
		 *  Set signLevAvgST for AggregateStats here:
		 *  Should be configurable only for places with statsLevel >= 3 !
		 *
		 *  if (!(places[p] instanceof QPlace)) {								// ORDINARY PLACE
		 *     For every color c, set aggrStats[p * 2].signLevAvgST[c]                   
		 *  }
		 *  else {																		// QUEUEING PLACE
		 *     For every color c, set aggrStats[p * 2].signLevAvgST[c]                  //   - QUEUE
		 *     For every color c, set aggrStats[(p * 2) + 1].signLevAvgST[c]            //   - DEPOSITORY
		 *  }
		 *  
		 */		
		// Iterate through all places.
		/** NEW
		for(int p = 0; p<net.getNumPlaces(); p++){
			pl = net.getPlace(p);

		 */
		/** OLD: List placeList = sim.net.getPlaceList();*/
		XPath xpathSelector = XMLHelper.createXPath("//place");
		List<Element> placeList = xpathSelector.selectNodes(netXML);

		Iterator placeIterator = placeList.iterator();		
		for (int p = 0; placeIterator.hasNext(); p++) {
			Element place = (Element) placeIterator.next();
			pl = places[p];
			if (pl.statsLevel >= 3) {
				xpathSelector = XMLHelper.createXPath("color-refs/color-ref");
				Iterator colorRefIterator = xpathSelector.selectNodes(place).iterator();
				for (int c = 0; colorRefIterator.hasNext(); c++) {
					Element colorRef = (Element) colorRefIterator.next();
					Element element = XMLHelper.getSettings(colorRef, net.getConfigurationName());						
					if (pl instanceof QPlace) {													
						if(element == null || element.attributeValue("queueSignLevAvgST") == null) {								
							log.error(formatDetailMessage(
									"queueSignLevAvgST settings for the Replication/Deletion Method not found!",
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"), 
									"color-num", Integer.toString(c),
									"colorRef.id", colorRef.attributeValue("id"),
									"colorRef.color-id", colorRef.attributeValue("color-id")
									));
							throw new SimQPNException();							
						}								
						aggrStats[p * 2].signLevAvgST[c] = Double.parseDouble(element.attributeValue("queueSignLevAvgST"));
						log.debug("aggrStats[" + p * 2 + "].signLevAvgST[" + c + "] = " + aggrStats[p * 2].signLevAvgST[c] + " (queue)");
					}
					if (element == null || element.attributeValue("signLevAvgST") == null) {
						log.error(formatDetailMessage(
								"signLevAvgST settings for the Replication/Deletion Method not found!",
								"place.id", place.attributeValue("id"),
								"place.name", place.attributeValue("name"), 
								"color-num", Integer.toString(c),
								"colorRef.id", colorRef.attributeValue("id"),
								"colorRef.color-id", colorRef.attributeValue("color-id")
								));
						throw new SimQPNException();
					}						
					if (pl instanceof QPlace) {
						aggrStats[(p * 2) + 1].signLevAvgST[c] = Double.parseDouble(element.attributeValue("signLevAvgST"));
						log.debug("aggrStats[" + (p * 2) + 1 + "].signLevAvgST[" + c + "] = " + aggrStats[(p * 2) + 1].signLevAvgST[c] + " (depository)");						
					}
					else {
						aggrStats[p * 2].signLevAvgST[c] = Double.parseDouble(element.attributeValue("signLevAvgST"));
						log.debug("aggrStats[" + p * 2 + "].signLevAvgST[" + c + "] = " + aggrStats[p * 2].signLevAvgST[c] + " (ordinary place)");						
					}
				}
			}
		}
		return aggrStats;
	}

	private static void putStatisticsIntoArray(
			SimQPNConfiguration configuration, AggregateStats[] aggrStats,
			int numPlaces, Net netCopy) throws SimQPNException {
		for (int p = 0; p < numPlaces; p++) {
			Place pl = netCopy.getPlace(p);
			if (pl.statsLevel > 0) {
				if (pl instanceof QPlace) {
					QPlace qPl = (QPlace) pl;
					aggrStats[p * 2].saveStats(qPl.qPlaceQueueStats, configuration);
					aggrStats[(p * 2) + 1].saveStats(qPl.placeStats, configuration);
				} else {
					aggrStats[p * 2].saveStats(pl.placeStats, configuration);
				}
			}
		}
	}
	


}
