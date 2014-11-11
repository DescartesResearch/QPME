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

import de.tud.cs.simqpn.kernel.SimQPNConfiguration.AnalysisMethod;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.QPlace;

public class XMLWelch {
	private static Logger log = Logger.getLogger(XMLWelch.class);

	public static void configurePlaceStats(Place[] places, Element netXML,
			String configurationName) throws SimQPNException {
		Place pl;
		XPath xpathSelector = XMLHelper.createXPath("//place");
		List<Element> placeList = xpathSelector.selectNodes(netXML);

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
			if (isMeasuringSojurnTimes(pl)) {
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
	private static boolean isMeasuringSojurnTimes(Place pl) {
		return isMeasuringSojurnTimes(pl, AnalysisMethod.WELCH);
	}

	/** Returns is storing sojourn time observations is enabled for the place */
	public static boolean isMeasuringSojurnTimes(Place pl, AnalysisMethod analysisMethod) {
		return analysisMethod.equals(AnalysisMethod.WELCH) && pl.statsLevel >= 3;
	}


}
