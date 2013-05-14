package de.tud.cs.simqpn.kernel.executor;

import java.util.Comparator;
import java.util.PriorityQueue;

import org.apache.log4j.Logger;

import cern.jet.random.Empirical;
import cern.jet.random.EmpiricalWalker;
import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNController;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.Probe;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.Queue;
import de.tud.cs.simqpn.kernel.entities.Transition;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;
import de.tud.cs.simqpn.kernel.random.RandomNumberGenerator;

public class Executor {

	private static Logger log = Logger.getLogger(SimQPNController.class);

	// Check if using double for time is really needed and if overhead is
	// tolerable. Consider switching to float.
	private double clock; // Global simulation clock. Time is usually measured
							// in milliseconds.
	public static PriorityQueue<QueueEvent> eventList = // Global simulation
														// event list. Contains
														// events scheduled for
														// processing at
														// specified points in
														// time.
	new PriorityQueue<QueueEvent>(10, new Comparator<QueueEvent>() {
		public int compare(QueueEvent a, QueueEvent b) {
			return (a.time < b.time ? -1 : (a.time == b.time ? 0 : 1));
		}
	});

	
	public static boolean simRunning;			// True if simulation is currently running.

	
	public SimQPNConfiguration configuration;
	public static SimulatorProgress progressMonitor;	// Progress monitoring
	private Net net;

	/**
	 * Method run - starts the simulation run.
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public SimQPNController run() throws SimQPNException {
		// SimonSpinner: TEMP CHANGE
		//		try {
		//			System.in.read();
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		}
		// SimonSpinner: TEMP CHANGE
				
		boolean[] transStatus;					// Transition status: true = enabled, false = disabled
		int enTransCnt = 0;
		int[] enTransIDs = new int[net.getNumTrans()];
		
		EmpiricalWalker randTransGen;		// Random number generator for generating next transition to fire.

		// Initialize transStatus and enTransCnt 		
		transStatus = new boolean[net.getNumTrans()];
		for (int i = 0; i < net.getNumTrans(); i++) {
			if (net.getTrans(i).enabled()) {
				transStatus[i] = true;
				enTransCnt++;
			} else {
				transStatus[i] = false;
			}
		}
		
		// Create randTransGen
		double[] pdf = new double[net.getNumTrans()];
		for (int t = 0; t < net.getNumTrans(); t++)
			pdf[t] = 1;

		randTransGen = new EmpiricalWalker(pdf, Empirical.NO_INTERPOLATION, RandomNumberGenerator.nextRandNumGen());
		// Note: Here we use a default distribution. The actual distribution is set each time before using randTransGen. 		

		// Note: we store totRunLen and rampUpLen in local variables to improve performance of the while loop below.		
		double totRunL = configuration.totRunLen;
		double rampUpL = configuration.rampUpLen;
		double nextChkAfter = configuration.timeBtwChkStops > 0 ? configuration.timeBtwChkStops : totRunL; // If secondsBtwChkStops is used, disable checking of stopping criterion
																			   // until beforeInitHeartBeat == false. By setting nextChkAfter = totRunL
																			   // stopping criterion checking is disabled.
		/* ORIGINAL HEARTBEAT IMPLEMENTATION
		double nextChkAfter = timeBtwChkStops > 0 ? timeBtwChkStops : timeInitHeartBeat;
		*/

		configuration.beginRunWallClock = System.currentTimeMillis();


		boolean beforeInitHeartBeat = true;		// Flag indicating when we are still before the first heart beat (progress update).
												//   If true, the value of timeBtwHeartBeats is still measured, and set to 0.
		double nextHeartBeat = 0.0;				// Simulation run time of the next heart beat.
		double timeBtwHeartBeats = 0.0;			// How often progress updates are made (in logical simulation time units).
		long lastTimeMsrm = System.currentTimeMillis();		// The value of the last wall clock time measurement. Used for progress updates.
		double maxProgressInterval = progressMonitor.getMaxUpdateLogicalTimeInterval();
		long progressUpdateRate = progressMonitor.getMaxUpdateRealTimeInterval();
		
		// BEGIN MAIN SIMULATION LOOP ---------------------------------------------------------------------------------
		while (clock < totRunL) { 

			if (configuration.inRampUp && clock > rampUpL) {
				configuration.inRampUp = false;
				configuration.endRampUpClock = clock;
				if (configuration.getAnalMethod() == SimQPNConfiguration.WELCH)
					break;
				for (int p = 0; p < net.getNumPlaces(); p++)
					net.getPlace(p).start(this);
				for (int q = 0; q < net.getNumQueues(); q++)
					net.getQueue(q).start();
				for (int pr = 0; pr < net.getNumProbes(); pr++)
					net.getProbe(pr).start(this);

				progressMonitor.finishWarmUp();
			}

			// Step 1: Fire until no transitions are enabled.
			int fireCnt = 0;
			while (enTransCnt > 0) {				
				Transition nextTrans;		// transition to fire next

				if (enTransCnt == 1) {				
					nextTrans = null;
					for (int t = 0; t < net.getNumTrans(); t++) {
						if (transStatus[t]) {
							nextTrans = net.getTrans(t);
							break;
						}
					}
				} else {
					// Choose transition to fire based on weights
					pdf = new double[enTransCnt];					
					for (int t = 0, e = 0; t < net.getNumTrans(); t++) {
						if (transStatus[t]) {
							pdf[e] = net.getTrans(t).transWeight;
							enTransIDs[e] = t;
							e++;
						}
					}
					randTransGen.setState2(pdf);					
					nextTrans = net.getTrans(enTransIDs[randTransGen.nextInt()]);
				}

				nextTrans.fire(this);		// Fire transition

				// Update transStatus
				int p, t, nP, nT;
				Place pl;
				Transition tr;
				// Check if some transitions were disabled (newly-disabled transitions)
				nP = nextTrans.inPlaces.length;
				for (p = 0; p < nP; p++) {
					pl = nextTrans.inPlaces[p];
					nT = pl.outTrans.length;
					for (t = 0; t < nT; t++) {
						tr = pl.outTrans[t];						
						if ((!tr.enabled()) && transStatus[tr.id])	{
							transStatus[tr.id] = false;
							enTransCnt--;
						}
					}
				}
				// Check if some transitions were enabled (newly-enabled transitions)				
				nP = nextTrans.outPlaces.length;
				for (p = 0; p < nP; p++) {
					pl = nextTrans.outPlaces[p];
					nT = pl.outTrans.length;
					for (t = 0; t < nT; t++) {
						tr = pl.outTrans[t];						
						if (tr.enabled() && (!transStatus[tr.id])) {
							transStatus[tr.id] = true;
							enTransCnt++;
						}						
					}
				}
				
				// If there are always transitions enabled,
				// this results in an infinite loop. Make it
				// possible for the user to cancel the simulation
				// anyway.
				if (fireCnt > 10000000) {
					if (progressMonitor.isCanceled()) {
						clock = totRunL;
						break;
					}
					fireCnt = 0;
				} else {
					fireCnt++;
				}
			} // end firing enabled transitions

			// Step 2: Make sure all service completion events in PS QPlaces have been scheduled
			for (int q = 0; q < net.getNumQueues(); q++)
				if (net.getQueue(q).queueDiscip == Queue.PS && (!net.getQueue(q).eventsUpToDate))
					net.getQueue(q).updateEvents();
			/* Alternative Code
			for (int p = 0; p < numPlaces; p++)
				if (places[p] instanceof QPlace) {
					QPlace qpl = (QPlace) places[p];
					if ((qpl.queue.queueDiscip == Queue.PS) && (!qpl.queue.eventsUpToDate))
						qpl.queue.updateEvents();
				} 
			*/			

			// Step 3: Process next event in event list
			if (eventList.size() > 0) {
				QueueEvent ev = eventList.poll();
				// Event ev = (Event) eventList.remove(0); // Old LinkedList implementation of the event list.
								
				// Advance simulation time
				clock = ev.time;

				QPlace qpl = (QPlace) ev.token.place;
				qpl.completeService(ev.token, this);

				// Check if some transitions were enabled and update transStatus				
				int t, nT;
				Transition tr;
				nT = qpl.outTrans.length;
				for (t = 0; t < nT; t++) {
					tr = qpl.outTrans[t];					
					if (tr.enabled() && (!transStatus[tr.id])) {
						transStatus[tr.id] = true;
						enTransCnt++;
					}
				}
			} else {
				log.error("QPN is not live.");
				throw new SimQPNException();
			}

			// Step 4: Heart Beat
			if(beforeInitHeartBeat) {
				long curTimeMsrm = System.currentTimeMillis();
				if(((curTimeMsrm - lastTimeMsrm) >= SimQPNConfiguration.MAX_INITIAL_HEARTBEAT)
						|| (SimQPNController.clock >= maxProgressInterval)) {
					
					if(SimQPNController.clock >= maxProgressInterval) {
						timeBtwHeartBeats = maxProgressInterval;
					} else {
						timeBtwHeartBeats = (SimQPNController.clock / (curTimeMsrm - lastTimeMsrm)) * progressUpdateRate;
					}
					beforeInitHeartBeat = false;
					if (configuration.timeBtwChkStops == 0) {
						// enable checking of stopping criterion
						nextChkAfter = clock;
					}
				}
				
				if (progressMonitor.isCanceled()) {
					clock = totRunL;
				}
			} else {
				if(SimQPNController.clock >= nextHeartBeat) {
					long curTimeMsrm = System.currentTimeMillis();
					progressMonitor.updateSimulationProgress(clock / (totRunL - 1) * 100, (curTimeMsrm - lastTimeMsrm));					
					lastTimeMsrm = curTimeMsrm;
					nextHeartBeat = SimQPNController.clock + timeBtwHeartBeats;
					
					if (progressMonitor.isCanceled()) {
						clock = totRunL;
					}
				}
			}

			/* ORIGINAL HEARTBEAT IMPLEMENTATION
			if (runMode == NORMAL && analMethod != REPL_DEL && clock > nextHeartBeat) {
				double elapsedSecs = (System.currentTimeMillis() - beginRunWallClock) / 1000;
				double clockTimePerSec = clock / elapsedSecs;
				log("Info: Simulation time = " + (long) clock + "  Elapsed wall clock time = ");
				if (nextHeartBeat == timeInitHeartBeat)		// check if this is the initial heart beat
					logln((int) elapsedSecs + " sec");
				else
					logln((int) (elapsedSecs / 60) + " min");
				nextHeartBeat = Simulator.clock + clockTimePerSec * (secsBtwHeartBeats + 10);
				// Make sure at least secsBtwHeartBeats seconds have elapsed at next heart beat
			}
			*/

			// Step 5: Check Stopping Criterion
			if (configuration.stoppingRule != SimQPNConfiguration.FIXEDLEN && (!configuration.inRampUp) && clock > nextChkAfter) {
				double elapsedSecs = (System.currentTimeMillis() - configuration.beginRunWallClock) / 1000;				
				double clockTimePerSec = clock / elapsedSecs;	
				boolean done = true;
				Place pl = null;

				for (int p = 0; p < net.getNumPlaces(); p++) {
					pl = net.getPlace(p);
					if (pl.statsLevel >= 3) {
						if (!pl.placeStats.enoughStats(this)) {
							done = false;
							break;
						}
						if ((pl instanceof QPlace) && !(((QPlace) pl).qPlaceQueueStats.enoughStats(this))) {
							done = false;
							break;
						}
					}
				}

				if (!done) {
					// The test already failed for a place.
					progressMonitor.precisionCheck(done, pl.name);
				} else {
					// Check also the probes, whether they have enough samples
					Probe probe = null;
					for (int pr = 0; pr < net.getNumProbes(); pr++) {
						probe = net.getProbe(pr);
						if (probe.statsLevel >= 3) {
							if (!probe.probeStats.enoughStats(this)) {
								done = false;
								break;
							}
						}
					}
					if (done) {
						progressMonitor.precisionCheck(done, null);
						break; // exit while loop
					} else {
						progressMonitor.precisionCheck(done, probe.name); //TODO: distinguish between places and probes.
					}
				}

				if (configuration.timeBtwChkStops > 0)
					nextChkAfter = SimQPNController.clock + configuration.timeBtwChkStops;
				else
					nextChkAfter = SimQPNController.clock + clockTimePerSec * configuration.secondsBtwChkStops;
			}

		}

		// END MAIN SIMULATION LOOP ---------------------------------------------------------------------------------
		progressMonitor.updateSimulationProgress(100, 0);
		
		if (progressMonitor.isCanceled()) {
			progressMonitor.warning("The simulation was canceled by the user.\n"
					+ "The required precision may not have been reached!");
		} else {
			if (clock >= configuration.totRunLen)  {
				if (configuration.stoppingRule != SimQPNConfiguration.FIXEDLEN)  {
					progressMonitor.warning("The simulation was stopped because of reaching max totalRunLen.\n"
							+ "The required precision may not have been reached!");
				}
				else
					log.info("STOPPING because max totalRunLen is reached!");
			}
		}
		
		configuration.endRunClock = clock;
		configuration.msrmPrdLen = configuration.endRunClock - configuration.endRampUpClock;
		configuration.endRunWallClock = System.currentTimeMillis();
		configuration.runWallClockTime = (configuration.endRunWallClock - configuration.beginRunWallClock) / 1000;	// total time elapsed in seconds 
		
		log.info("msrmPrdLen= " + configuration.msrmPrdLen + " totalRunLen= " + configuration.endRunClock + " runWallClockTime=" + (int) (configuration.runWallClockTime / 60) + " min (=" + configuration.runWallClockTime + " sec)");

		
		// Complete statistics collection (make sure this is done AFTER the above statements)
		if (configuration.getAnalMethod() != SimQPNConfiguration.WELCH) {		
			for (int p = 0; p < net.getNumPlaces(); p++)
				net.getPlace(p).finish(this);
			for (int q = 0; q < net.getNumQueues(); q++)  //NOTE: queues[*].finish() should be called after places[*].finish()! 
				net.getQueue(q).finish(this);
			for (int pr = 0; pr < net.getNumProbes(); pr++)
				net.getProbe(pr).finish(this);
		}
		return this;
	} // end of run() method

}
