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
import de.tud.cs.simqpn.kernel.entities.queue.PSQueue;
import de.tud.cs.simqpn.kernel.entities.queue.Queue;
import de.tud.cs.simqpn.kernel.entities.queue.QueuingDiscipline;

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
			Element queue = (Element) queueList.get(i);

			QueuingDiscipline queueingStrategy = QueuingDiscipline.FCFS;

			String name = queue.attributeValue("name");
			if (queueNames.contains(name)) {
				log.error(formatDetailMessage(
						"Another queue definition with the same name does already exist!",
						"queue-num", Integer.toString(i), "queue.id",
						queue.attributeValue("id"), "queue.name", name));
				throw new SimQPNException();
			} else {
				queueNames.add(name);
			}

			String disciplineName = queue.attributeValue("strategy");
			try {
				queueingStrategy = QueuingDiscipline.valueOf(disciplineName);
			} catch (IllegalArgumentException e) {
				log.error(formatDetailMessage(
						"Invalid or missing \"strategy\" (queueing discipline) setting!",
						"queue-num", Integer.toString(i), "queue.id",
						queue.attributeValue("id"), "queue.name", name,
						"queue.strategy", queue.attributeValue("strategy")));
				throw new SimQPNException();
			}

			int numberOfServers = getNumberOfServers(i, queue,
					queueingStrategy, name);

			switch (queueingStrategy) {
			case IS:
				queues[i] = new ISQueue(i, // index
						queue.attributeValue("id"), // xml-id
						queue.attributeValue("name"), // name
						queueingStrategy, // queueing d
						numberOfServers // # servers
				);
				break;
			case FCFS:
				queues[i] = new FCFSQueue(i, // index
						queue.attributeValue("id"), // xml-id
						queue.attributeValue("name"), // name
						queueingStrategy, // queueing d
						numberOfServers // # servers
				);
				break;
			case PS:
				queues[i] = new PSQueue(i, // index
						queue.attributeValue("id"), // xml-id
						queue.attributeValue("name"), // name
						queueingStrategy, // queueing d
						numberOfServers // # servers
				);
				break;
			default:
				log.error(formatDetailMessage(
						"Invalid or missing \"strategy\" (queueing discipline) setting!",
						"queue-num", Integer.toString(i), "queue.id",
						queue.attributeValue("id"), "queue.name", name,
						"queue.strategy", queue.attributeValue("strategy")));
				// throw new SimQPNException(); //TODO uncomment
			}
			queueToIndexMap.put(queue, i);
			if (log.isDebugEnabled()) {
				log.debug("queues[" + i + "] = new Queue(" + i + ", '"
						+ queue.attributeValue("name") + "', "
						+ queueingStrategy + ", " + numberOfServers + ")");
			}
		}
		return queues;
	}

	private static int getNumberOfServers(int i, Element queue,
			QueuingDiscipline queueingStrategy, String name)
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
