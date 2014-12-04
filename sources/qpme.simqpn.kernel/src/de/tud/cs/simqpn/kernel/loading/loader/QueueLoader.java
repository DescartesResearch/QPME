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
 *  2014/03/14  Jürgen Walter     Extracted from NetLoader
 * 
 */
package de.tud.cs.simqpn.kernel.loading.loader;

import static de.tud.cs.simqpn.kernel.util.LogUtil.formatDetailMessage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.XPath;

import cern.jet.random.AbstractContinousDistribution;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.queue.FCFSQueue;
import de.tud.cs.simqpn.kernel.entities.queue.ISQueue;
import de.tud.cs.simqpn.kernel.entities.queue.PRIOQueue;
import de.tud.cs.simqpn.kernel.entities.queue.PSQueue;
import de.tud.cs.simqpn.kernel.entities.queue.Queue;
import de.tud.cs.simqpn.kernel.entities.queue.QueueingDiscipline;
import de.tud.cs.simqpn.kernel.entities.queue.RANDOMQueue;
import de.tud.cs.simqpn.kernel.loading.NetLoader;
import de.tud.cs.simqpn.kernel.loading.XMLHelper;
import de.tud.cs.simqpn.kernel.loading.distributions.DistributionCreator;

public class QueueLoader {
	private static Logger log = Logger.getLogger(QueueLoader.class);

	public static Queue[] createQueues(Element netXML)
			throws SimQPNException {
		XPath xpathSelector = XMLHelper.createXPath("//queue");
		List<Element> queueList = xpathSelector.selectNodes(netXML);
			
		// Set for checking the uniqueness of queue names
		Map<Element, Integer> queueToIndexMap = new HashMap<Element, Integer>();

		int numQueues = queueList.size();
		Queue[] queues = new Queue[numQueues];
		HashSet<String> queueNames = new HashSet<String>();

		for (int i = 0; i < numQueues; i++) {
			Element xmlReferenceQueue = (Element) queueList.get(i);

			QueueingDiscipline queueingStrategy = QueueingDiscipline.FCFS;

			String name = xmlReferenceQueue.attributeValue("name");
			if (queueNames.contains(name)) {
				log.error(formatDetailMessage(
						"Another queue definition with the same name does already exist!",
						"queue-num", Integer.toString(i), "queue.id",
						xmlReferenceQueue.attributeValue("id"), "queue.name", name));
				throw new SimQPNException();
			} else {
				queueNames.add(name);
			}

			String disciplineName = xmlReferenceQueue.attributeValue("strategy");
			try {
				queueingStrategy = QueueingDiscipline.valueOf(disciplineName);
			} catch (IllegalArgumentException e) {
				log.error(formatDetailMessage(
						"Invalid or missing \"strategy\" (queueing discipline) setting!",
						"queue-num", Integer.toString(i), "queue.id",
						xmlReferenceQueue.attributeValue("id"), "queue.name", name,
						"queue.strategy", xmlReferenceQueue.attributeValue("strategy")));
				throw new SimQPNException();
			}

			int numberOfServers = getNumberOfServers(i, xmlReferenceQueue,
					queueingStrategy, name);

			switch (queueingStrategy) {
			case IS:
				queues[i] = new ISQueue(i, xmlReferenceQueue.attributeValue("id"),
						xmlReferenceQueue.attributeValue("name"), queueingStrategy,
						numberOfServers);
				break;
			case FCFS:
				queues[i] = new FCFSQueue(i, xmlReferenceQueue.attributeValue("id"),
						xmlReferenceQueue.attributeValue("name"), queueingStrategy,
						numberOfServers);
				break;
			case PS:
				queues[i] = new PSQueue(i, xmlReferenceQueue.attributeValue("id"),
						xmlReferenceQueue.attributeValue("name"), queueingStrategy,
						numberOfServers);
				break;
			case PRIO:
				queues[i] = new PRIOQueue(i, xmlReferenceQueue.attributeValue("id"),
						xmlReferenceQueue.attributeValue("name"), queueingStrategy,
						numberOfServers);
				break;
			case RANDOM:
				queues[i] = new RANDOMQueue(i, xmlReferenceQueue.attributeValue("id"),
						xmlReferenceQueue.attributeValue("name"), queueingStrategy,
						numberOfServers);
				break;

			default:
				log.error(formatDetailMessage(
						"Invalid or missing \"strategy\" (queueing discipline) setting!",
						"queue-num", Integer.toString(i), "queue.id",
						xmlReferenceQueue.attributeValue("id"), "queue.name", name,
						"queue.strategy", xmlReferenceQueue.attributeValue("strategy")));
				throw new SimQPNException();
			}
			queueToIndexMap.put(xmlReferenceQueue, i);
			if (log.isDebugEnabled()) {
				log.debug("queues[" + i + "] = new Queue(" + i + ", '"
						+ xmlReferenceQueue.attributeValue("name") + "', "
						+ queueingStrategy + ", " + numberOfServers + ")");
			}
		}
		return queues;
	}

	private static int getNumberOfServers(int i, Element queue,
			QueueingDiscipline queueingStrategy, String name)
			throws SimQPNException {
		if (queueingStrategy.hasServers()) {
			if (queue.attributeValue("number-of-servers") == null) {
				log.error(formatDetailMessage(
						"\"number-of-servers\" parameter not set!",
						"queue-num", Integer.toString(i), "queue.id",
						queue.attributeValue("id"), "queue.name", name));
				throw new SimQPNException();
			}
			return Integer.parseInt(queue.attributeValue("number-of-servers"));
		} else {
			return 0; // NOTE: This is assumed in
						// QPlaceQueueStats.updateTkPopStats()!
		}
	}

	
	public static void configureQueueServiceTimeDistributions(Net net, List<Element> placeList)
			throws SimQPNException {
		/*
		 * TODO: make the editor display the real names of the expected
		 * parameters after the user has chosen the distribution, e.g. for
		 * Exponential a single field labeled "lambda" should be displayed.
		 */

		log.debug("/////////////////////////////////////////////");
		log.debug("// Configure Queue Service Time Distributions (times in milliseconds)");
		Iterator<Element> placeIterator = placeList.iterator();
		for (int i = 0; placeIterator.hasNext(); i++) {
			Element place = placeIterator.next();
			if ("queueing-place".equals(place
					.attributeValue(NetLoader.XSI_TYPE_ATTRIBUTE))) {
				QPlace qPl = (QPlace) net.getPlace(i);

				if (qPl.queue.queueDiscip == QueueingDiscipline.PS) {
					((PSQueue) qPl.queue).expPS = false;
				}

				if (!(qPl.queue.queueDiscip == QueueingDiscipline.PS && ((PSQueue) qPl.queue).expPS)) {
					qPl.randServTimeGen = new AbstractContinousDistribution[qPl.numColors];
				}

				XPath xpathSelector = XMLHelper
						.createXPath("color-refs/color-ref");
				Iterator<Element> colorRefIterator = xpathSelector.selectNodes(
						place).iterator();
				for (int j = 0; colorRefIterator.hasNext(); j++) {
					Element colorRef = colorRefIterator.next();

					if (colorRef.attributeValue("distribution-function") == null) {
						log.error(formatDetailMessage(
								"Queueing place' \"distribution-function\" parameter not set!",
								"place-num", Integer.toString(i), "place.id",
								place.attributeValue("id"), "place.name",
								place.attributeValue("name"), "colorRef-num",
								Integer.toString(j), "colorRef.id",
								colorRef.attributeValue("id"),
								"colorRef.color-id",
								colorRef.attributeValue("color-id")));
						throw new SimQPNException();
					}
					String distributionFunction = colorRef
							.attributeValue("distribution-function");

					if (qPl.queue.queueDiscip == QueueingDiscipline.PS
							&& ((PSQueue) qPl.queue).expPS) {
						log.info("expPS parameter of a queueing place with PS scheduling strategy set to true!");
						if (!"Exponential".equals(distributionFunction)) {
							log.error(formatDetailMessage(
									"Distribution function is configured as \""
											+ distributionFunction
											+ "\". "
											+ "Distribution function must be set to \"Exponential\" since (expPS == true) !",
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}

						if (colorRef.attributeValue("lambda") == null) {
							log.error(formatDetailMessage(
									"Parameter \"lambda\" of Exponential distribution function not set!",
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						double lambda = Double.parseDouble(colorRef
								.attributeValue("lambda"));
						// Validate input parameters
						if (!(lambda > 0)) {
							log.error(formatDetailMessage(
									"Invalid \"lambda\" parameter of Exponential distribution!",
									"lambda", Double.toString(lambda),
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}

						qPl.meanServTimes[j] = (double) 1 / lambda;
						log.debug("((QPlace) places[" + i + "]).meanServTimes["
								+ j + "] = 1 / lambda = "
								+ qPl.meanServTimes[j]);
					}

					/*
					 * The code below does the following: - checks the chosen
					 * distribution function - checks that all required
					 * distribution input parameters have been set - validates
					 * the values of the distribution input parameters -
					 * initializes the random number generators for service
					 * times - initializes the meanServTimes array based on the
					 * chosen distribution
					 * 
					 * The actual values in the meanServTimes array are
					 * currently only used in three cases 1. QPlace.expPS ==
					 * true (Exponential distribution) 2.
					 * QPlace.qPlaceQueueStats.indrStats == true 3.
					 * distribution-function == Deterministic
					 * 
					 * Note: Service time distributions are truncated at 0 to
					 * avoid negative values for service times, i.e.
					 * "if (servTime < 0) servTime = 0;"
					 */

					try {
						DistributionCreator distribution = DistributionCreator
								.constructCreator(distributionFunction,
										colorRef);
						qPl.randServTimeGen[j] = distribution.getDistribution();
						log.debug("((QPlace) places[" + i
								+ "]).randServTimeGen[" + j + "] = new "
								+ distribution.getClass().getName()
								+ distribution.getConstructionText());
						qPl.meanServTimes[j] = distribution.getMean();
						log.debug("((QPlace) places[" + i + "]).meanServTimes["
								+ j + "] = "
								+ distribution.getMeanComputationText() + " = "
								+ qPl.meanServTimes[j]);
					} catch (SimQPNException e) {
						log.error(formatDetailMessage(e.getMessage(),
								"place-num", Integer.toString(i), "place.id",
								place.attributeValue("id"), "place.name",
								place.attributeValue("name"), "colorRef-num",
								Integer.toString(j), "colorRef.id",
								colorRef.attributeValue("id"),
								"colorRef.color-id",
								colorRef.attributeValue("color-id")));

						throw e;
					}
				}
			}
		}
	}

}
