package de.tud.cs.simqpn.kernel.entities.queue;

import cern.jet.random.AbstractContinousDistribution;
import cern.jet.random.EmpiricalWalker;
import de.tud.cs.simqpn.kernel.executor.QueueEvent;

public class PSQueue extends MyQueue {

	public boolean eventsUpToDate; // PS queues: True if currently scheduled
	// events for this queue (if any)
	// reflect the latest token popolation of
	// the queue.
	public boolean eventScheduled; // PS queues: True if there is currently a
	// service completion event scheduled for
	// this queue.
	public QueueEvent nextEvent; // PS queues: Next service completion event
	// scheduled for this queue.
	public boolean expPS; // PS queues: true = Processor-Sharing with
	// exponential service times.
	// false = Processor-Sharing with non-exponential
	// service times.
	public int tkSchedPl; // PS queues: expPS==false: Queueing place containing
	// the next token scheduled to complete service.
	public int tkSchedCol; // PS queues: expPS==false: Color of the next token
	// scheduled to complete service.
	public int tkSchedPos; // PS queues: expPS==false: Index in
	// QPlace.queueTokArrivTS[tkSchedCol] and
	// QPlace.queueTokResidServTimes[tkSchedCol] of the
	// next token scheduled to complete service.
	public double lastEventClock; // PS queues: expPS==false: Time of the last
	// event scheduling, i.e. time of the last
	// event with effect on this queue.
	public int lastEventTkCnt; // PS queues: expPS==false: Token population at
	// the time of the last event scheduling.
	public AbstractContinousDistribution[] expRandServTimeGen; // PS queues:
	// expPS==true:
	// Random number
	// generators
	// for
	// generating
	// service
	// times.
	public EmpiricalWalker randColorGen; // PS queues: expPS==true: Random
	// number generator for generating
	// token colors.

}
