package de.tud.cs.simqpn.kernel.executor.parallel;

import java.util.Comparator;
import java.util.PriorityQueue;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.entities.parallel.ParallelTransition;
import de.tud.cs.simqpn.kernel.executor.QueueEvent;

/**
 * LogicalProcess
 */
public class LP {

	private SimQPNConfiguration configuration;
	private double clock = 0;
	ParallelTransition[] outgoingTransitions;
	ParallelTransition[] incommingTransitions;
	
	/**
	 * LP event list. Contains events scheduled for processing at
	 * specified points in time.
	 */
	public PriorityQueue<QueueEvent> eventList = new PriorityQueue<QueueEvent>(
			10, new Comparator<QueueEvent>() {
				public int compare(QueueEvent a, QueueEvent b) {
					return (a.time < b.time ? -1 : (a.time == b.time ? 0 : 1));
				}
			});
	
	private boolean isSaveToProcess(double nextEventTime){
		for(ParallelTransition trans: incommingTransitions){
			LP predecessorLP = trans.getLP();
			if(predecessorLP.getTimeSaveToProcess() < nextEventTime){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * A sucessor LP can ask how far it can process
	 * @return
	 */
	public double getTimeSaveToProcess() {
		return clock + getLookahead();
	}

	private double getLookahead() {
		double loookahead = 0;
		// sucessor.place.;
		return loookahead;
	}

	/**
	 * Should be similar to Executor.run
	 * @throws InterruptedException 
	 */
	void run() throws InterruptedException {
		double totRunL = configuration.totRunLen;
		double rampUpL = configuration.rampUpLen;
		double nextChkAfter = configuration.timeBtwChkStops > 0 ? configuration.timeBtwChkStops
				: totRunL;
		/*
		 * If secondsBtwChkStops is used, disable checking of stopping criterion
		 * until beforeInitHeartBeat == false. By setting nextChkAfter = totRunL
		 * stopping criterion checking is disabled.
		 */

		while (clock < totRunL) {
			// Step 1: Fire until no transitions are enabled.
			// Step 2: Make sure all service completion events in PS QPlaces
				// have been scheduled
			
			if (eventList.size() > 0) {
				QueueEvent ev = eventList.poll();
				while(!isSaveToProcess(ev.time)){
					this.wait();
					//wait OR add to future list
				}
				// Advance simulation time
				clock = ev.time;

			}
			// Step 3: Process next event in event list
			// Step 4: Heart Beat


		}
	}

}
