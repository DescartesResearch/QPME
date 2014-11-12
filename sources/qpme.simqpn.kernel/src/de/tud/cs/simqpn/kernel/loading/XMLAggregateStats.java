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
 * Original Author(s):  Jürgen Walter
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2013/07/24  Jürgen Walter     Created. Code has been taken from other classes during refactoring
 * 
 */
package de.tud.cs.simqpn.kernel.loading;

import static de.tud.cs.simqpn.kernel.util.LogUtil.formatDetailMessage;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.XPath;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.SimQPNConfiguration.AnalysisMethod;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.stats.AggregateStats;
import de.tud.cs.simqpn.kernel.entities.stats.Stats;

public class XMLAggregateStats {
	private static Logger log = Logger.getLogger(XMLAggregateStats.class);

	public static AggregateStats[] initStatsArray(Element XMLDescription, Net net,
			SimQPNConfiguration configuration)
			throws SimQPNException {

		Place pl;
		int numPlaces;
		Place[] places = null;
		AggregateStats[] aggrStats = null;
		if (configuration.getAnalMethod() == AnalysisMethod.REPL_DEL
				|| configuration.getAnalMethod() == AnalysisMethod.WELCH) {

			numPlaces = net.getNumPlaces();
			places = net.getPlaces();

			aggrStats = new AggregateStats[numPlaces * 2];
			// Note: aggrStats should be large enough to accomodate 2 stats per
			// place in case all places are queueing places.

			for (int p = 0; p < numPlaces; p++) {
				pl = places[p];
				if (pl.statsLevel > 0) {
					if (pl instanceof QPlace) {
						aggrStats[p * 2] = new AggregateStats(pl.id, pl.name,
								Stats.QUE_PLACE_QUEUE, pl.numColors,
								pl.statsLevel, configuration);
						aggrStats[(p * 2) + 1] = new AggregateStats(pl.id,
								pl.name, Stats.QUE_PLACE_DEP, pl.numColors,
								pl.statsLevel, configuration);
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
		}

		if (configuration.getAnalMethod() == AnalysisMethod.REPL_DEL) {

			/*
			 * CONFIG: Set signLevAvgST for AggregateStats here: Should be
			 * configurable only for places with statsLevel >= 3 !
			 * 
			 * if (!(places[p] instanceof QPlace)) { // ORDINARY PLACE For every
			 * color c, set aggrStats[p * 2].signLevAvgST[c] } else { //
			 * QUEUEING PLACE For every color c, set aggrStats[p *
			 * 2].signLevAvgST[c] // - QUEUE For every color c, set aggrStats[(p
			 * * 2) + 1].signLevAvgST[c] // - DEPOSITORY }
			 */
			// Iterate through all places.
			XPath xpathSelector = XMLHelper.createXPath("//place");
			List<Element> placeList = xpathSelector.selectNodes(XMLDescription);

			Iterator placeIterator = placeList.iterator();
			for (int p = 0; placeIterator.hasNext(); p++) {
				Element place = (Element) placeIterator.next();
				pl = places[p];
				if (pl.statsLevel >= 3) {
					xpathSelector = XMLHelper
							.createXPath("color-refs/color-ref");
					Iterator colorRefIterator = xpathSelector
							.selectNodes(place).iterator();
					for (int c = 0; colorRefIterator.hasNext(); c++) {
						Element colorRef = (Element) colorRefIterator.next();
						Element element = XMLHelper.getSettings(colorRef,
								net.getConfigurationName());
						if (pl instanceof QPlace) {
							if (element == null
									|| element
											.attributeValue("queueSignLevAvgST") == null) {
								log.error(formatDetailMessage(
										"queueSignLevAvgST settings for the Replication/Deletion Method not found!",
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
							aggrStats[p * 2].signLevAvgST[c] = Double
									.parseDouble(element
											.attributeValue("queueSignLevAvgST"));
							log.debug("aggrStats[" + p * 2 + "].signLevAvgST["
									+ c + "] = "
									+ aggrStats[p * 2].signLevAvgST[c]
									+ " (queue)");
						}
						if (element == null
								|| element.attributeValue("signLevAvgST") == null) {
							log.error(formatDetailMessage(
									"signLevAvgST settings for the Replication/Deletion Method not found!",
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"color-num", Integer.toString(c),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						if (pl instanceof QPlace) {
							aggrStats[(p * 2) + 1].signLevAvgST[c] = Double
									.parseDouble(element
											.attributeValue("signLevAvgST"));
							log.debug("aggrStats[" + (p * 2) + 1
									+ "].signLevAvgST[" + c + "] = "
									+ aggrStats[(p * 2) + 1].signLevAvgST[c]
									+ " (depository)");
						} else {
							aggrStats[p * 2].signLevAvgST[c] = Double
									.parseDouble(element
											.attributeValue("signLevAvgST"));
							log.debug("aggrStats[" + p * 2 + "].signLevAvgST["
									+ c + "] = "
									+ aggrStats[p * 2].signLevAvgST[c]
									+ " (ordinary place)");
						}
					}
				}
			}
		}
		return aggrStats;
	}

}
