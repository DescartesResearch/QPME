package de.tud.cs.simqpn.kernel.executor.sequential;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import cern.jet.random.Empirical;
import cern.jet.random.EmpiricalWalker;
import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.Probe;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.Token;
import de.tud.cs.simqpn.kernel.entities.Transition;
import de.tud.cs.simqpn.kernel.entities.queue.Queue;
import de.tud.cs.simqpn.kernel.entities.queue.QueuingDiscipline;
import de.tud.cs.simqpn.kernel.executor.Executor;
import de.tud.cs.simqpn.kernel.executor.QueueEvent;
import de.tud.cs.simqpn.kernel.executor.TokenEvent;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;
import de.tud.cs.simqpn.kernel.random.RandomNumberGenerator;
import edu.cornell.lassp.houle.RngPack.RandomElement;

public class SequentialExecutor implements Executor, Callable<Net>{

	private static Logger log = Logger.getLogger(SequentialExecutor.class);

	/** Simulation clock. Time is usually measured in milliseconds. */
	// Check if using double for time is really needed and if overhead is
	// tolerable. Consider switching to float.
	private double clock;

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
	/*
	 * WARNING: Watch out when defining the Comparator above: Equality should be
	 * treated explicitly!
	 * 
	 * Potential problems when using eventList.remove() because of the following
	 * bug http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6207984
	 * http://bugs.sun.com/bugdatabase/view_bug.do;jsessionid=859
	 * a6e381a7abfffffffff7e644d05a59d93?bug_id=6268068
	 * 
	 * On old JVMs that do not have the above bug fixed, if two events have the
	 * exact same time, the wrong one might be removed!
	 */

	public boolean inRampUp; // True if still in RampUp period (no measurements
								// taken).
	public double endRampUpClock; // Clock at the end of RampUp, i.e. beginning
									// of the measurement period.
	public double endRunClock; // Clock at the end of the run.
	public double msrmPrdLen; // Duration of the measurement period (endRunClock
								// - endRampUpClock).
	public double beginRunWallClock; // currentTimeMillis at the begin of the
										// run (wall clock time).
	public double endRunWallClock; // currentTimeMillis at the end of the run
									// (wall clock time).
	public double runWallClockTime; // Total duration of the run in seconds.

	/** True if simulation is currently running. */
	public static boolean simRunning;

	private SimQPNConfiguration configuration;

	private SimulatorProgress progressMonitor; // Progress monitoring

	private Net net;
	
	private int runID;
	
	RandomElement randomElement;

	public SequentialExecutor(Net net, SimQPNConfiguration configuration,
			SimulatorProgress progressMonitor, int runID) {
		this.net = net;
		this.configuration = configuration;
		this.progressMonitor = progressMonitor;
		this.runID = runID;
	}

	public SequentialExecutor(Net net, SimQPNConfiguration configuration, RandomElement randomElement,
			SimulatorProgress progressMonitor, int runID) {
		this.net = net;
		this.configuration = configuration;
		this.progressMonitor = progressMonitor;
		this.runID = runID;
		this.randomElement = randomElement;
	}

	
	/**
	 * Starts the simulation run.
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	@Override
	public Net call() throws Exception{
		progressMonitor.startSimulationRun(runID, configuration);
		
		initializeWorkingVariables();

		for (Place p : net.getPlaces()) {
			p.setExecutor(this);
		}
		for(Transition trans: net.getTransitions()){
			trans.setExecutor(this);			
		}

		boolean[] transStatus; // Transition status: true = enabled, false =
								// disabled
		int enTransCnt = 0;
		int[] enTransIDs = new int[net.getNumTrans()];

		EmpiricalWalker randTransGen; // Random number generator for
										// generating
										// next transition to fire.

		// Initialize transStatus and enTransCnt
		transStatus = new boolean[net.getNumTrans()];
		for (int i = 0; i < net.getNumTrans(); i++) {
			if (net.getTrans(i).enabled()) {
				transStatus[i] = true;
				enTransCnt++;
//				System.out.println("\t"+net.getTrans(i).name+" enabled");
			} else {
				transStatus[i] = false;
			}
		}


		
		// Create randTransGen
		double[] pdf = new double[net.getNumTrans()];
		for (int t = 0; t < net.getNumTrans(); t++)
			pdf[t] = 1;

		if(randomElement == null){
			randTransGen = new EmpiricalWalker(pdf, Empirical.NO_INTERPOLATION,
					RandomNumberGenerator.nextRandNumGen());			
		}else{
			randTransGen = new EmpiricalWalker(pdf, Empirical.NO_INTERPOLATION,
					randomElement);
			//randomElement == null; 
		}
		// Note: Here we use a default distribution. The actual distribution
		// is
		// set each time before using randTransGen.

		// Note: we store totRunLen and rampUpLen in local variables to
		// improve
		// performance of the while loop below.
		double totRunL = configuration.totRunLength;
		double rampUpL = configuration.rampUpLen;
		double nextChkAfter = configuration.timeBtwChkStops > 0 ? configuration.timeBtwChkStops
				: totRunL; // If secondsBtwChkStops is used, disable
							// checking of
							// stopping criterion
		// until beforeInitHeartBeat == false. By setting nextChkAfter =
		// totRunL
		// stopping criterion checking is disabled.
		/*
		 * ORIGINAL HEARTBEAT IMPLEMENTATION double nextChkAfter =
		 * timeBtwChkStops > 0 ? timeBtwChkStops : timeInitHeartBeat;
		 */

		beginRunWallClock = System.currentTimeMillis();

		/*
		 * Flag indicating when we are still before the first heart beat
		 * (progress update). If true, the value of timeBtwHeartBeats is still
		 * measured, and set to 0.
		 */
		boolean beforeInitHeartBeat = true;
		/* Simulation run time of the next heart beat. */
		double nextHeartBeat = 0.0;
		/*
		 * Determines How often progress updates are made (in logical simulation
		 * time units).
		 */
		double timeBtwHeartBeats = 0.0;
		/*
		 * The value of the last wall clock time measurement. Used for progress
		 * updates.
		 */
		long lastTimeMsrm = System.currentTimeMillis();
		double maxProgressInterval = progressMonitor
				.getMaxUpdateLogicalTimeInterval(configuration);
		long progressUpdateRate = progressMonitor
				.getMaxUpdateRealTimeInterval();		
		
		
		// BEGIN MAIN SIMULATION LOOP
		// ---------------------------------------------------------------------------------
		while (clock < totRunL) {

			if (inRampUp && clock > rampUpL) {
				inRampUp = false;
				endRampUpClock = clock;
				if (configuration.getAnalMethod() == SimQPNConfiguration.AnalysisMethod.WELCH)
					break;
				for (int p = 0; p < net.getNumPlaces(); p++){
					net.getPlace(p).start(configuration, clock);
				}
				for (int q = 0; q < net.getNumQueues(); q++){
					net.getQueue(q).start(clock);
				}
				for (int pr = 0; pr < net.getNumProbes(); pr++){
					net.getProbe(pr).start(configuration, clock);
				}

				progressMonitor.finishWarmUp(getId(), configuration);
			}
			
			// Step 1: Fire until no transitions are enabled.
			int fireCnt = 0;

			while (enTransCnt > 0) {

				Transition nextTrans; // transition to fire next

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
					nextTrans = net
							.getTrans(enTransIDs[randTransGen.nextInt()]);
				}

				nextTrans.fire(); // Fire transition

				// Update transStatus
				int p, t, nP, nT;
				Place pl;
				Transition tr;
				// Check if some transitions were disabled (newly-disabled
				// transitions)
				nP = nextTrans.inPlaces.length;
				for (p = 0; p < nP; p++) {
					pl = nextTrans.inPlaces[p];
					nT = pl.outTrans.length;
					for (t = 0; t < nT; t++) {
						tr = pl.outTrans[t];
						if ((!tr.enabled()) && transStatus[tr.id]) {
							transStatus[tr.id] = false;
							enTransCnt--;
						}
					}
				}
				// Check if some transitions were enabled (newly-enabled
				// transitions)
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

			// Step 2: Make sure all service completion events in PS QPlaces
			// have been scheduled
			for (int q = 0; q < net.getNumQueues(); q++)
				if (net.getQueue(q).queueDiscip == QueuingDiscipline.PS)
					net.getQueue(q).updateEvents(this);
			/*
			 * Alternative Code for (int p = 0; p < numPlaces; p++) if
			 * (places[p] instanceof QPlace) { QPlace qpl = (QPlace) places[p];
			 * if ((qpl.queue.queueDiscip == Queue.PS) &&
			 * (!qpl.queue.eventsUpToDate)) qpl.queue.updateEvents(); }
			 */

			// Step 3: Process next event in event list
			if (eventList.size() > 0) {
				QueueEvent ev = eventList.poll();
				// Event ev = (Event) eventList.remove(0); // Old LinkedList
				// implementation of the event list.

				// Advance simulation time
				clock = ev.time;

				QPlace qpl = (QPlace) ev.token.place;
				qpl.completeService(ev.token, this);

				// Check if some transitions were enabled and update
				// transStatus
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
			if (beforeInitHeartBeat) {
				long curTimeMsrm = System.currentTimeMillis();
				if (((curTimeMsrm - lastTimeMsrm) >= SimQPNConfiguration.MAX_INITIAL_HEARTBEAT)
						|| (clock >= maxProgressInterval)) {

					if (clock >= maxProgressInterval) {
						timeBtwHeartBeats = maxProgressInterval;
					} else {
						timeBtwHeartBeats = (clock / (curTimeMsrm - lastTimeMsrm))
								* progressUpdateRate;
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
				if (clock >= nextHeartBeat) {
					long curTimeMsrm = System.currentTimeMillis();
					progressMonitor.updateSimulationProgress(getId(), clock
							/ (totRunL - 1) * 100,
							(curTimeMsrm - lastTimeMsrm), configuration,
							inRampUp);
					lastTimeMsrm = curTimeMsrm;
					nextHeartBeat = clock + timeBtwHeartBeats;

					if (progressMonitor.isCanceled()) {
						clock = totRunL;
					}
				}
			}

			/*
			 * ORIGINAL HEARTBEAT IMPLEMENTATION if (runMode == NORMAL &&
			 * analMethod != REPL_DEL && clock > nextHeartBeat) { double
			 * elapsedSecs = (System.currentTimeMillis() - beginRunWallClock) /
			 * 1000; double clockTimePerSec = clock / elapsedSecs;
			 * log("Info: Simulation time = " + (long) clock +
			 * "  Elapsed wall clock time = "); if (nextHeartBeat ==
			 * timeInitHeartBeat) // check if this is the initial heart beat
			 * logln((int) elapsedSecs + " sec"); else logln((int) (elapsedSecs
			 * / 60) + " min"); nextHeartBeat = Simulator.clock +
			 * clockTimePerSec * (secsBtwHeartBeats + 10); // Make sure at least
			 * secsBtwHeartBeats seconds have elapsed at next heart beat }
			 */

			// Step 5: Check Stopping Criterion
			if (configuration.stoppingRule != SimQPNConfiguration.FIXEDLEN
					&& (!inRampUp) && clock > nextChkAfter) {
				double elapsedSecs = (System.currentTimeMillis() - beginRunWallClock) / 1000;
				double clockTimePerSec = clock / elapsedSecs;
				boolean done = true;
				Place pl = null;

				for (int p = 0; p < net.getNumPlaces(); p++) {
					pl = net.getPlace(p);
					if (pl.statsLevel >= 3) {
						if (!pl.placeStats.enoughStats(configuration)) {
							done = false;
							break;
						}
						if ((pl instanceof QPlace)
								&& !(((QPlace) pl).qPlaceQueueStats
										.enoughStats(configuration))) {
							done = false;
							break;
						}
					}
				}

				if (!done) {
					// The test already failed for a place.
					progressMonitor.precisionCheck(getId(), done, pl.name);
				} else {
					// Check also the probes, whether they have enough
					// samples
					Probe probe = null;
					for (int pr = 0; pr < net.getNumProbes(); pr++) {
						probe = net.getProbe(pr);
						if (probe.statsLevel >= 3) {
							if (!probe.probeStats.enoughStats(configuration)) {
								done = false;
								break;
							}
						}
					}
					if (done) {
						progressMonitor.precisionCheck(getId(), done, null);
						break; // exit while loop
					} else {
						progressMonitor.precisionCheck(getId(), done, probe.name); // TODO:
																			// distinguish
																			// between
																			// places
																			// and
																			// probes.
					}
				}

				if (configuration.timeBtwChkStops > 0)
					nextChkAfter = clock + configuration.timeBtwChkStops;
				else
					nextChkAfter = clock + clockTimePerSec
							* configuration.secondsBtwChkStops;
			}

		}

		// END MAIN SIMULATION LOOP
		// ---------------------------------------------------------------------------------
		progressMonitor.updateSimulationProgress(getId(), 100, 0, configuration,
				inRampUp);

		if (progressMonitor.isCanceled()) {
			progressMonitor
					.warning(getId(), "The simulation was canceled by the user.\n"
							+ "The required precision may not have been reached!");
		} else {
			if (clock >= totRunL) {
				if (configuration.stoppingRule != SimQPNConfiguration.FIXEDLEN) {
					progressMonitor
							.warning(getId(), "The simulation was stopped because of reaching max totalRunLen.\n"
									+ "The required precision may not have been reached!");
				} else
					log.info("STOPPING because max totalRunLen is reached!");
			}
		}

		endRunClock = clock;
		msrmPrdLen = endRunClock - endRampUpClock;
		endRunWallClock = System.currentTimeMillis();

		// total time elapsed in seconds
		runWallClockTime = (endRunWallClock - beginRunWallClock) / 1000;

		log.info("msrmPrdLen= " + msrmPrdLen + " totalRunLen= " + endRunClock
				+ " runWallClockTime=" + (int) (runWallClockTime / 60)
				+ " min (=" + runWallClockTime + " sec)");

		// Complete statistics collection (make sure this is done AFTER the
		// above statements)
		if (configuration.getAnalMethod() != SimQPNConfiguration.AnalysisMethod.WELCH) {
			for (int p = 0; p < net.getNumPlaces(); p++)
				net.getPlace(p).finish(configuration, runWallClockTime, clock);
			for (int q = 0; q < net.getNumQueues(); q++) {
				// NOTE: queues[*].finish() should be called after
				// places[*].finish()!
				net.getQueue(q).finish(configuration, runWallClockTime, clock);
			}
			for (int pr = 0; pr < net.getNumProbes(); pr++)
				net.getProbe(pr).finish(configuration, runWallClockTime, clock);
		}
		
		progressMonitor.finishSimulationRun();

		return net;
	}

	/**
	 * Method scheduleEvent - schedules an event
	 * 
	 * @param time
	 *            - Time at which the event should be processed
	 * @param queue
	 *            - Queue involved
	 * @param token
	 *            - Token that is to completes service
	 * @return
	 * @exception
	 */
	public void scheduleEvent(double serviceTime, Queue queue, Token token) {
		QueueEvent ev = new QueueEvent(clock + serviceTime, queue, token);
		eventList.add(ev);
		queue.onQueueEventScheduled(ev);
	}

	public double getClock() {
		return clock;
	}

	public SimQPNConfiguration getConfiguration() {
		return configuration;
	}

	public SimulatorProgress getProgressMonitor() {
		return progressMonitor;
	}

	@Override
	public void removeEvent(QueueEvent event) {
		eventList.remove(event);
	}

	private void initializeWorkingVariables() throws SimQPNException {
		inRampUp = true;
		endRampUpClock = 0;
		endRunClock = 0;
		msrmPrdLen = 0; // Set at the end of the run when the
						// actual length is known.
		beginRunWallClock = 0;
		endRunWallClock = 0;
		runWallClockTime = 0;

		clock = 0; // Note that it has been assumed throughout the code that
		// the simulation starts at virtual time 0.

		eventList.clear();

		// Make sure clock has been initialized before calling init below
		// Call places[i].init() first and then thans[i].init() and
		// queues[i].init()
		for (int i = 0; i < net.getNumProbes(); i++)
			net.getProbe(i).init();
		for (int i = 0; i < net.getNumPlaces(); i++)
			net.getPlace(i).init(clock);
		for (int i = 0; i < net.getNumTrans(); i++)
			net.getTrans(i).init();
		for (int i = 0; i < net.getNumQueues(); i++)
			net.getQueue(i).init(configuration);
	}

	@Override
	public int getId() {
		return -1;
	}

	@Override
	public void addTokenEvent(TokenEvent tokenEvent) {
		//not needed for sequential executor
	}

}
