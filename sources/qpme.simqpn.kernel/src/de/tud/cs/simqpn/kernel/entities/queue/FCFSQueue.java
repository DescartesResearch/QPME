package de.tud.cs.simqpn.kernel.entities.queue;

import java.util.LinkedList;

import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Token;

public class FCFSQueue extends MyQueue {

	public int numServers; // FCFS queues: Number of servers in queueing
	// station.
	public int numBusyServers; // FCFS queues: Number of currently busy servers.
	public LinkedList<Token> waitingLine; // FCFS queues: List of tokens waiting
	// for service (waiting area of the
	// queue).

	public FCFSQueue(int id, String xmlId, String name, int queueDiscip,
			int numServers) throws SimQPNException {
		super(id, xmlId, name, queueDiscip, numServers);
	}

}
