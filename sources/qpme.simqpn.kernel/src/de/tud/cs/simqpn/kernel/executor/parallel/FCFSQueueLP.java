package de.tud.cs.simqpn.kernel.executor.parallel;

import java.util.Comparator;
import java.util.PriorityQueue;

import de.tud.cs.simqpn.kernel.executor.QueueEvent;

public class FCFSQueueLP{
	
	

	/**
	 * Global simulation event list. Contains events scheduled for processing at
	 * specified points in time.
	 */
	public PriorityQueue<QueueEvent> eventList = new PriorityQueue<QueueEvent>(
			10, new Comparator<QueueEvent>() {
				public int compare(QueueEvent a, QueueEvent b) {
					return (a.time < b.time ? -1 : (a.time == b.time ? 0 : 1));
				}
			});

	/** Simulation clock. Time is usually measured in milliseconds. */
	double clock = 0;

	public double getClock() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getLookahead() {
		// TODO Auto-generated method stub
		return 0;
	}

}
