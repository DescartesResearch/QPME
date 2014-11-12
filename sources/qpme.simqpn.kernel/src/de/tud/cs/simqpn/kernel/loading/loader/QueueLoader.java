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
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.queue.FCFSQueue;
import de.tud.cs.simqpn.kernel.entities.queue.ISQueue;
import de.tud.cs.simqpn.kernel.entities.queue.PRIOQueue;
import de.tud.cs.simqpn.kernel.entities.queue.PSQueue;
import de.tud.cs.simqpn.kernel.entities.queue.Queue;
import de.tud.cs.simqpn.kernel.entities.queue.QueueingDiscipline;
import de.tud.cs.simqpn.kernel.entities.queue.RANDOMQueue;

public class QueueLoader {
	private static Logger log = Logger.getLogger(QueueLoader.class);

	public static Queue[] createQueues(List<Element> queueList)
			throws SimQPNException {
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

}
