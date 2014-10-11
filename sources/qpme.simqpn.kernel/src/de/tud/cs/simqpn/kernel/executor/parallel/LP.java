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
 *  2013/07/22	Jürgen Walter	  Created
 * 
 */
package de.tud.cs.simqpn.kernel.executor.parallel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.PriorityBlockingQueue;

import org.apache.log4j.Logger;

import cern.jet.random.Empirical;
import cern.jet.random.EmpiricalWalker;
import cern.jet.random.engine.RandomEngine;
import de.tud.cs.simqpn.kernel.RandomNumberGenerator;
import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.Probe;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.Token;
import de.tud.cs.simqpn.kernel.entities.Transition;
import de.tud.cs.simqpn.kernel.entities.queue.Queue;
import de.tud.cs.simqpn.kernel.executor.Executor;
import de.tud.cs.simqpn.kernel.executor.QueueEvent;
import de.tud.cs.simqpn.kernel.executor.TokenEvent;
import de.tud.cs.simqpn.kernel.executor.parallel.barrier.termination.StopController;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;
import edu.bonn.cs.net.jbarrier.barrier.Barrier;

/**
 * This class represents a logical process (LP) which simulates a part of a net.
 * 
 * The LP processes tokens until a global stop criterion is reached.
 * 
 * The LP blocks if the next event is not safe to process. An event it save to
 * process if there can not arrive previous events from LPs predecessors.
 * 
 * @see ParallelExecutor
 */
public class LP implements Executor {

	/** logging to console. */
	private static Logger log = Logger.getLogger(LP.class);
	/** True if still in RampUp period (no measurements taken). */
	private boolean inRampUp;
	/** Clock at the end of RampUp, i.e. beginning of the measurement period. */
	private double endRampUpClock;
	/** Clock at the end of the run. */
	private double endRunClock;
	/** Duration of the measurement period (endRunClock - endRampUpClock). */
	private double msrmPrdLen;
	/** CurrentTimeMillis at the begin of the run (wall clock time). */
	private double beginRunWallClock;
	/** CurrentTimeMillis at the end of the run (wall clock time). */
	private double endRunWallClock;
	/** Total duration of the run in seconds. */
	private double runWallClockTime;
	/** Configuration for simulation run. */
	private SimQPNConfiguration configuration;
	/** Simulation clock, virtual time. */
	private volatile double clock = 0;
	/** Identification for the LP. Note: There is no control for id to be unique */
	private int id;
	/** List of LPs this LP can get tokens from. */
	private List<LP> predecessorList = new ArrayList<LP>();
	/** List of LPs this LP sends tokens to. */
	private List<LP> successorList = new ArrayList<LP>();

	/**
	 * List of queue events scheduled for processing at specified points in
	 * time.
	 */
	private PriorityQueue<QueueEvent> eventList = new PriorityQueue<QueueEvent>(
			10, new Comparator<QueueEvent>() {
				public int compare(QueueEvent a, QueueEvent b) {
					return (a.time < b.time ? -1 : (a.time == b.time ? 0 : 1));
				}
			});

	/*
	 * Note: EventList can be non-blocking as new {@link QueueEvents} are only
	 * inserted by this LP. Instead, incomingTokenList has to be blocking.
	 */

	/** List of Tokens with timestamp that arrived from other LPs. */
	private volatile java.util.Queue<TokenEvent> incomingTokenList;

	// private Probe[] probes;
	/** The places of the QPN this LP simulates. */
	private final Place[] places;
	/** The places through-out tokens can enter this LP */
	private Place[] inPlaces;
	/** The transitions of the QPN this LP simulates. */
	private final Transition[] transitions;
	/** The queues of the QPN this LP simulates. */
	private final Queue[] queues;
	/** The monitor which shows progress. */
	private final SimulatorProgress progressMonitor;
	/** Transition status. true = enabled, false = disabled. */
	private boolean[] transStatus;
	/** Number of currently enabled transitions. */
	private int enTransCnt;
	/**
	 * Array which contains ids of the enabled transitions. Used for (random)
	 * determination of the next transition to fire.
	 */
	private int[] enTransIDs;
	/**
	 * Flag indicating when we are still before the first heart beat (progress
	 * update). If true, the value of timeBtwHeartBeats is still measured, and
	 * set to 0.
	 */
	private boolean beforeInitHeartBeat;
	/** Simulation run time of the next heart beat. */
	private double nextHeartBeat;
	/**
	 * Determines How often progress updates are made (in logical simulation
	 * time units).
	 */
	private double timeBtwHeartBeats;
	/** Random number generator for generating next transition to fire. */
	private EmpiricalWalker randTransGen;
	/** The barrier at which this LPs waits until all other LPs arrive. */
	private Barrier barrier;
	/** Global stop criterion. */
	private StopController stopController;
	/** Local stop criterion. */
	private boolean hasFinished = false;
	/** Lower bound on incoming time stamps. */
	private double timeSaveToProcess;

	/** Sets debug output to console used for debug purpose. */
	private final int verbosityLevel;

	/** interval for heart beats */
	private double nextChkAfter;
	/**
	 * The value of the last wall clock time measurement. Used for progress
	 * updates.
	 */
	private long lastTimeMsrm = System.currentTimeMillis();

	private final double totRunLength;
	private final double rampUpLength;

	/** The maximum virtual time interval for progress updates */
	private final double maxProgressUpdateInterval;
	/** Time in milliseconds between progress updates */
	private final long progressUpdateRate;
	
	private long barrierWaitingTime = 0;
	private int emittedTokenCounter = 0;
	private boolean isCountingEmittedTokens = false;

	/**
	 * Constructor.
	 * 
	 * @param places
	 *            the places this LP simulates
	 * @param transitions
	 *            the transitions this LP simulates
	 * @param queues
	 *            the queues this LP simulates
	 * @param configuration
	 *            the simulation configuration
	 * @param progressMonitor
	 *            the monitor to which progress information is send
	 * @param id
	 *            the id of this LP
	 */
	public LP(Place[] places, Transition[] transitions, Queue[] queues,
			SimQPNConfiguration configuration,
			SimulatorProgress progressMonitor, int id, int verbosityLevel) {
		this.id = id;
		this.places = places;
		this.transitions = transitions;
		this.queues = queues;
		// TODO implement probes
		// this.probes = probes;
		this.progressMonitor = progressMonitor;
		this.configuration = configuration;
		this.maxProgressUpdateInterval = progressMonitor
				.getMaxUpdateLogicalTimeInterval(configuration);
		this.progressUpdateRate = progressMonitor
				.getMaxUpdateRealTimeInterval();
		this.totRunLength = configuration.totRunLength;
		this.rampUpLength = configuration.rampUpLen;
		this.verbosityLevel = verbosityLevel;
	}

	public void processSaveEvents() throws SimQPNException {
		boolean hasCompleted = false;
		if(isCountingEmittedTokens){
			while (emittedTokenCounter < 100 && !hasCompleted) {
				hasCompleted = performActions();
			}
			emittedTokenCounter = 0;
		}else{
			while(!hasCompleted){
				hasCompleted = performActions();		
			}
		}
		
		if (beforeInitHeartBeat) {
			determineMonitorUpdateRate();
		} else {
			updateProgressMonitor();
			if(verbosityLevel > 1){
				log.info("LP" + id + " updating progress at monitor");
			}
		}

		if (clock > totRunLength) {
			stopController.setReadyToFinish();
		}
	}
	
	private boolean performActions() throws SimQPNException {
		QueueEvent nextQueueEvent;
		TokenEvent nextTokenEvent;
		if ((nextQueueEvent = eventList.peek()) != null
				&& nextQueueEvent.time <= timeSaveToProcess) {
			while ((nextTokenEvent = incomingTokenList.peek()) != null
					&& nextTokenEvent.getTime() < (nextQueueEvent = eventList
							.peek()).time) {
				processNextTokenEvent();
				updateQueueEvents();
			}
			processNextQueueEvent();
			updateQueueEvents();
		} else if ((nextTokenEvent = incomingTokenList.peek()) != null
				&& nextTokenEvent.getTime() <= timeSaveToProcess) {
			processNextTokenEvent();
			updateQueueEvents();
		} else {
			fireTransitions();
			updateQueueEvents();
			return true;
		}
		fireTransitions();
		updateQueueEvents();
		return false;
	}

	public void startDataCollection(double endRampUpClock) {
		if (inRampUp && clock > rampUpLength) {
			inRampUp = false;
			this.endRampUpClock = endRampUpClock;
			// if (configuration.getAnalMethod() ==
			// SimQPNConfiguration.AnalysisMethod.WELCH) {
			// break;
			// }
			for (int p = 0; p < places.length; p++) {
				try {
					places[p].start(configuration, endRampUpClock);
				} catch (SimQPNException e) {
					log.info(e);
				}
			}
			for (Place p : places) {
				if (p instanceof QPlace) {
					try {
						((QPlace) p).queue.start(endRampUpClock);
					} catch (SimQPNException e) {
						log.info(e);
					}
				}
			}
			// for (int pr = 0; pr < net.getNumProbes(); pr++) TODO
			// net.getProbe(pr).start(this);

			progressMonitor.finishWarmUp(id, configuration);
		} else {
			log.warn("error startup data collection");
		}
	}

	/**
	 * Checks if simulation got enough statistics.
	 * 
	 * @param nextChkAfter
	 *            the clock simulation time has to exceed to test statistics
	 *            again
	 * @return the earliest time for next test for sufficient data
	 * @throws SimQPNException
	 *             if error during test for enough statistics in PlaceStats and
	 *             QPlaceStats
	 */
	public void checkForPrecission() throws SimQPNException {
		if (configuration.stoppingRule != SimQPNConfiguration.FIXEDLEN
				&& (!inRampUp) && clock > nextChkAfter && !hasFinished) {
			// log.info("LP" + id + " performed precission check");
			double elapsedSecs = (System.currentTimeMillis() - beginRunWallClock) / 1000;
			double clockTimePerSec = clock / elapsedSecs;
			boolean done = true;
			Place pl = null;

			for (int p = 0; p < places.length; p++) {
				pl = places[p];
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
				progressMonitor.precisionCheck(id, done, pl.name);
			} else {
				// Check also the probes, whether they have enough
				// samples
				Probe probe = null;
				// for (int pr = 0; pr < net.getNumProbes(); pr++) {
				// probe = net.getProbe(pr);
				// if (probe.statsLevel >= 3) {
				// if (!probe.probeStats.enoughStats(configuration)) {
				// done = false;
				// break;
				// }
				// }
				// }
				if (done) {
					// TODO
					// break; // exit while loop
					if (!hasFinished) {
						hasFinished = true;
						progressMonitor.precisionCheck(id, done, null);
						stopController.incrementReadyToFinishLPCounter();
						log.info("LP" + id + " "
								+ "has finished data collection");
					}
				} else {
					// TODO: distinguish between places and probes.
					progressMonitor.precisionCheck(id, done, probe.name);
				}
			}
			if (configuration.timeBtwChkStops > 0) {
				nextChkAfter = clock + configuration.timeBtwChkStops;
			} else {
				nextChkAfter = clock + clockTimePerSec
						* configuration.secondsBtwChkStops;
			}
		}
	}

	/**
	 * Waits until all LPs have entered the barrier.
	 */
	public void waitForBarrier() {
		if(verbosityLevel > 0 ){
			long nanos = System.nanoTime();
			barrier.await(this.id);
			barrierWaitingTime += System.nanoTime() - nanos;
		}else{
			barrier.await(this.id);			
		}
	}

	/**
	 * Clean up method after simulation run. Mostly data post processing...
	 */
	public void finish() {
		endRunClock = clock;
		msrmPrdLen = endRunClock - endRampUpClock;
		endRunWallClock = System.currentTimeMillis();

		// total time elapsed in seconds
		runWallClockTime = (endRunWallClock - beginRunWallClock) / 1000;

		if (verbosityLevel > 0) {
			log.info("LP" + id + ": " + "msrmPrdLen= " + msrmPrdLen
					+ " totalRunLen= " + endRunClock + " runWallClockTime="
					+ (int) (runWallClockTime / 60) + " min (=" + runWallClockTime
					+ " sec)"+ " barrierWait= " + (barrierWaitingTime/(1000000000.0))+" sec");
		}else{
			log.info("LP" + id + ": " + "msrmPrdLen= " + msrmPrdLen
					+ " totalRunLen= " + endRunClock + " runWallClockTime="
					+ (int) (runWallClockTime / 60) + " min (=" + runWallClockTime
					+ " sec)");
		}


		// Complete statistics collection (make sure this is done AFTER the
		// above statements)
		if (configuration.getAnalMethod() != SimQPNConfiguration.AnalysisMethod.WELCH) {
			try {
				for (int p = 0; p < places.length; p++) {
					places[p].finish(configuration, runWallClockTime, endRunClock);
				}
				for (int q = 0; q < queues.length; q++) {
					// NOTE: queues[*].finish() should be called after
					// places[*].finish()!
					queues[q].finish(configuration, runWallClockTime, endRunClock);
				}
				// for (int pr = 0; pr < probes.length; pr++)
				// probes[pr].finish(configuration, clock);
			} catch (SimQPNException e) {
				log.error("", e);
			}
		}
	}

	/**
	 * Processes event at its QPlace and removes event from event list.
	 * 
	 * @param event
	 *            the event to be processed
	 * @throws SimQPNException
	 *             if an error in the QPlace occurs
	 */
	private void processNextQueueEvent() throws SimQPNException {
		QueueEvent event = eventList.poll();

		if (verbosityLevel > 1) {
			log.info("LP" + id + " processed queue event (" + event.queue.name
					+ ") at " + event.time);
		}

		// Advance simulation time
		if (verbosityLevel > 0) {
			if (clock > event.time) {
				System.err.println("LP" + id
						+ " | ERROR: PROGRESSED EVENT EARLY| " + clock + "\t "
						+ event.time);
			}
		}
		clock = event.time;

		QPlace qpl = (QPlace) event.token.place;
		qpl.completeService(event.token, this);
		// Check if some transitions were enabled and
		// update
		// transStatus
		int t, nT;
		Transition tr;
		nT = qpl.outTrans.length;
		for (t = 0; t < nT; t++) {
			tr = qpl.outTrans[t];
			int localTransId = tr.id - transitions[0].id; // tr.id
			if (tr.enabled()) {
				if (localTransId >= 0 && localTransId < transitions.length) {
					if ((!transStatus[localTransId])) {
						transStatus[localTransId] = true;
						enTransCnt++;
					}
				}
			}

		}
	}

	private void processNextTokenEvent() throws SimQPNException {
		TokenEvent tkEvent = incomingTokenList.poll();
		Place place = tkEvent.getPlace();

		if (clock < tkEvent.getTime()) {
			clock = tkEvent.getTime();
		} else if (clock > tkEvent.getTime()) {
			log.warn("LP" + id + ": Time of incoming token < simulation time at place: "+place.name
					+ " \n\t" + tkEvent.getTime() + "(token time)\n\t" + clock
					+ "(simulation time)\n\t" + getTimeSaveToProcess()
					+ "(time save to process)");
		}
		place.addTokens(tkEvent.getColor(), tkEvent.getNumber(),
				tkEvent.getTkCopyBuffer(), this);
		if (verbosityLevel > 1) {
			log.info("LP" + id + " processed incoming token"
					+ tkEvent.getColor() + " at " + clock);
		}

		/**
		 * Check if the new token enabled a transition
		 */
		int nT, t;
		nT = place.outTrans.length;
		for (t = 0; t < nT; t++) {
			Transition trans = transitions[t];
			if (trans.enabled()) {
				int localTransId = trans.id - transitions[0].id;
				if (localTransId >= 0 && localTransId < transitions.length) {
					if (!transStatus[localTransId]) {
						transStatus[localTransId] = true;
						enTransCnt++;
					}
				} else {
					log.error("Error processing incoming tokens. Inconsistencies within transition array");
					throw new SimQPNException();
				}
			}
		}
	}

	/**
	 * Fires until no transitions are enabled.
	 * 
	 * @param totRunL
	 *            The maximum run length
	 * @throws SimQPNException
	 *             if next transition to fire is null or if the firing disables
	 *             a transition in another LP
	 */
	private void fireTransitions() throws SimQPNException {
		double[] pdf;
		int fireCnt = 0;
		while (enTransCnt > 0) {
			Transition nextTrans; // transition to fire next
			if (enTransCnt == 1) {
				nextTrans = null;
				for (int t = 0; t < transitions.length; t++) {
					if (transStatus[t]) {
						nextTrans = transitions[t];
						break;
					}
				}
			} else {
				// Choose transition to fire based on weights
				pdf = new double[enTransCnt];
				for (int t = 0, e = 0; t < transitions.length; t++) {
					if (transStatus[t]) {
						pdf[e] = transitions[t].transWeight;
						enTransIDs[e] = t;
						e++;
					}
				}
				randTransGen.setState2(pdf);
				nextTrans = transitions[enTransIDs[randTransGen.nextInt()]];
			}

			if (nextTrans != null) {
				nextTrans.fire();
				if (verbosityLevel > 1) {
					log.info("LP" + id + " fired transition " + nextTrans.name);
				}

				// Update transStatus
				int p, t, nP, nT;
				Place pl;
				Transition tr;

				/**
				 * Check if some transitions were disabled (newly-disabled
				 * transitions)
				 */
				nP = nextTrans.inPlaces.length;
				for (p = 0; p < nP; p++) {
					pl = nextTrans.inPlaces[p];
					nT = pl.outTrans.length;
					for (t = 0; t < nT; t++) {
						tr = pl.outTrans[t];
						int localTransId = tr.id - transitions[0].id;
						if ((!tr.enabled()) && transStatus[localTransId]) {
							if (localTransId >= 0
									|| localTransId < transitions.length) {
								transStatus[localTransId] = false;
								enTransCnt--;
							} else {
								throw new SimQPNException();
							}
						}
					}
				}

				/**
				 * Check if some transitions were enabled(newly-enabled
				 * transitions)
				 */
				nP = nextTrans.outPlaces.length;
				for (p = 0; p < nP; p++) {
					pl = nextTrans.outPlaces[p];
					nT = pl.outTrans.length;
					for (t = 0; t < nT; t++) {
						tr = pl.outTrans[t];
						int localTransId = tr.id - transitions[0].id;
						if (tr.enabled()) {
							if (localTransId >= 0
									&& localTransId < transitions.length) {
								if ((!transStatus[localTransId])) {
									if (verbosityLevel > 2) {
										log.info("LP"
												+ id
												+ ":\t\t enabled "
												+ transitions[localTransId].name
												+ " [due to firing]");
									}
									transStatus[localTransId] = true;
									enTransCnt++;
								}
							}
						}
					}
				}

				// If there are always transitions enabled,
				// this results in an infinite loop. Make it
				// possible for the user to cancel the simulation
				// anyway.
				if (fireCnt > 10000) { // OLD: 10000000
					if (progressMonitor.isCanceled()) {
						clock = totRunLength;
						break;
					}
					fireCnt = 0;
				} else {
					fireCnt++;
				}
			}
		}
	}

	/**
	 * Updates the queues. Makes sure all service completion events in PS
	 * QPlaces have been scheduled.
	 */
	private void updateQueueEvents() {
		for (Queue queue : queues) {
			queue.updateEvents(this);
		}
	}

	/**
	 * Initializes the variables before simulation run.
	 * 
	 * @throws SimQPNException
	 *             if initialization of queues or places fails
	 */
	public void initializeWorkingVariables() throws SimQPNException {
		inRampUp = true;
		endRampUpClock = 0;
		endRunClock = 0;
		msrmPrdLen = 0;
		beginRunWallClock = 0;
		endRunWallClock = 0;
		runWallClockTime = 0;
		clock = 0; // Note that it has been assumed throughout the code that the
					// simulation starts at virtual time 0.
		eventList.clear();
		// for (int i = 0; i < net.getNumProbes(); i++)
		// net.getProbe(i).init();
		for (int i = 0; i < places.length; i++) {
			places[i].init(getClock());
		}
		for (int i = 0; i < transitions.length; i++) {
			transitions[i].init();
		}
		for (int i = 0; i < queues.length; i++) {
			queues[i].init(configuration);
		}
		enTransCnt = 0;
		enTransIDs = new int[transitions.length];
		if (randTransGen == null) {
			createRandomTransGen();
		}
		// Initialize transStatus and enTransCnt
		transStatus = new boolean[transitions.length];
		for (int i = 0; i < transitions.length; i++) {
			if (transitions[i].enabled()) {
				transStatus[i] = true;
				enTransCnt++;
			} else {
				transStatus[i] = false;
			}
		}

		beforeInitHeartBeat = true;
		nextHeartBeat = 0.0;
		timeBtwHeartBeats = 0.0;

		/*
		 * If secondsBtwChkStops is used, disable checking of stopping criterion
		 * until beforeInitHeartBeat == false. By setting nextChkAfter = totRunL
		 * stopping criterion checking is disabled.
		 */
		nextChkAfter = configuration.timeBtwChkStops > 0 ? configuration.timeBtwChkStops
				: totRunLength;

		beginRunWallClock = System.currentTimeMillis();

		/** Pick non-self-sorting queues whenever possible*/
		if (predecessorList.size() <= 1) {
			/*
			 * If LP only have one predecessor, incoming events are ordered
			 * Hence, we do not need a priority queue.
			 * 
			 * NOTE: LinkedBlockingQueue has bad performance
			 * 
			 * ArrayBlockingQueue(maxNumberOfTokens) could be another option;
			 */
			// incomingTokenList = new ConcurrentLinkedQueue<TokenEvent>();
			// int maxNumberOfTokens =10;
			// incomingTokenList = new
			// ArrayBlockingQueue<TokenEvent>(maxNumberOfTokens);
			incomingTokenList = new ConcurrentLinkedQueue<TokenEvent>();
		} else {
			incomingTokenList = new PriorityBlockingQueue<TokenEvent>(10,
					new Comparator<TokenEvent>() {
						public int compare(TokenEvent a, TokenEvent b) {
							return (a.getTime() < b.getTime() ? -1
									: (a.getTime() == b.getTime() ? 0 : 1));
						}
					});
		}

	}

	private void createRandomTransGen(RandomEngine randomElement)
			throws SimQPNException {
		// Create randTransGen
		double[] pdf = new double[transitions.length];
		for (int t = 0; t < transitions.length; t++) {
			pdf[t] = 1;
		}
		if (pdf.length > 1) {
			randTransGen = new EmpiricalWalker(pdf, Empirical.NO_INTERPOLATION,
					randomElement);
		}
	}

	private void createRandomTransGen() throws SimQPNException {
		RandomEngine randomElement = RandomNumberGenerator.nextRandNumGen();
		createRandomTransGen(randomElement);
	}



	/**
	 * Updates progress monitor if the simulation progressed far enough since
	 * last update.
	 * 
	 * @param totRunLength
	 *            the length of the simulation run
	 * @param lastTimeMsrm
	 *            the time of the last monitor update
	 * @return the time of the last monitor update
	 */
	private void updateProgressMonitor() {
		if (clock >= nextHeartBeat) {
			long curTimeMsrm = System.currentTimeMillis();
			progressMonitor.updateSimulationProgress(id, clock
					/ (totRunLength - 1) * 100, (curTimeMsrm - lastTimeMsrm),
					configuration, inRampUp);
			lastTimeMsrm = curTimeMsrm;

			// nextHeartBeat = clock + timeBtwHeartBeats/(100*clock /
			// (totRunLength - 1));
			nextHeartBeat = clock + timeBtwHeartBeats;

			if (progressMonitor.isCanceled()) {
				clock = totRunLength;
			}
		}
	}

	/**
	 * Returns a heart beat rate.
	 * 
	 * @param totRunL
	 *            The total run length
	 * @param nextChkAfter
	 *            The clock at which a next
	 * @param lastTimeMsrm
	 * @param maxProgressInterval
	 * @param progressUpdateRate
	 * @return the heart beat rate
	 */
	private double determineMonitorUpdateRate() {
		long curTimeMsrm = System.currentTimeMillis();
		if (((curTimeMsrm - lastTimeMsrm) >= SimQPNConfiguration.MAX_INITIAL_HEARTBEAT)
				|| (clock >= maxProgressUpdateInterval)) {

			if (clock >= maxProgressUpdateInterval) {
				timeBtwHeartBeats = maxProgressUpdateInterval;
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
			clock = totRunLength;
		}
		return nextChkAfter;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addTokenEvent(TokenEvent tokenEvent) {
		incomingTokenList.add(tokenEvent);
	}

	public static LP merge(LP lp1, LP lp2) {
		Place[] places = concat(lp1.places, lp2.places);
		Transition[] transitions = concat(lp1.transitions, lp2.transitions);
		Queue[] queues = concat(lp1.queues, lp2.queues);
		LP lp = new LP(places, transitions, queues, lp1.configuration,
				lp1.progressMonitor, lp1.enTransCnt + lp2.enTransCnt, Math.max(
						lp1.verbosityLevel, lp2.verbosityLevel));
		lp.id = lp1.id;
		lp.setExecutorToEntities();
		return lp;
	}

	public void setExecutorToEntities() {
		for (Place place : this.places) {
			place.setExecutor(this);
		}
		for (Transition transition : this.transitions) {
			transition.setExecutor(this);
		}
	}

	/**
	 * Sets controller for global stop criterion.
	 * 
	 * @param stopCriterionController
	 *            the controller which manages local stop criteria
	 */
	public void setStopController(StopController stopCriterionController) {
		this.stopController = stopCriterionController;
	}

	public StopController getStopController() {
		return stopController;
	}

	/**
	 * Sets global barrier for LP synchronization.
	 * 
	 * @param barrier
	 *            the barrier LPs wait if they have no events save to process
	 */
	public void setBarrier(Barrier barrier) {
		this.barrier = barrier;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getClock() {
		return clock;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void scheduleEvent(double serviceTime, Queue queue, Token token) {
		QueueEvent ev = new QueueEvent(clock + serviceTime, queue, token);
		eventList.add(ev);
		if (queue != null) {
			queue.onQueueEventScheduled(ev);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeEvent(QueueEvent event) {
		eventList.remove(event);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SimQPNConfiguration getConfiguration() {
		return configuration;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SimulatorProgress getProgressMonitor() {
		return progressMonitor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Transition[] getTransitions() {
		return transitions;
	}

	public Place[] getPlaces() {
		return places;
	}

	public Queue[] getQueues() {
		return queues;
	}

	public List<LP> getSuccessors() {
		return getSuccessorList();
	}

	public void addSuccessor(LP successor) {
		this.getSuccessorList().add(successor);
	}

	public List<LP> getPredecessors() {
		return predecessorList;
	}

	public void addPredecessor(LP predecessor) {
		this.predecessorList.add(predecessor);
	}

	public void resetPredecessors() {
		this.predecessorList = new ArrayList<LP>();
	}

	public void resetInPlaces() {
		this.inPlaces = null;
	}
	
	public void setInPlaces(Place[] inPlaces) {
		this.inPlaces = inPlaces;
		if(inPlaces.length == 0){
			isCountingEmittedTokens = true;			
			emittedTokenCounter = 0;
		}
	}

	public String toShortString() {
		StringBuffer sb = new StringBuffer();
		sb.append("LP" + this.getId() + "\n");
		for (Place p : this.getPlaces()) {
			if (p instanceof QPlace) {
				sb.append("\t"
						+ p.name
						+ "(QPplace), queue "
						+ ((QPlace) p).queue.name
						+ "  "
						+ ((QPlace) p).queue.getClass().toString()
								.split("queue.")[1] + "\n");

			} else {
				sb.append("\t" + p.name + "(Place)" + "\n");
			}
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("LP" + this.getId() + "\n");
		for (Place p : this.getPlaces()) {
			if (p instanceof QPlace) {
				sb.append("\t"
						+ p.name
						+ "(QPplace), queue "
						+ ((QPlace) p).queue.name
						+ "  "
						+ ((QPlace) p).queue.getClass().toString()
								.split("queue.")[1] + "\n");

			} else {
				sb.append("\t" + p.name + "(Place)" + "\n");
			}
		}
		for (Transition t : this.getTransitions()) {
			sb.append("\t" + t.name + "(transition)" + " ID " + t.id + "\n");
		}

		sb.append("\tsuccessors: ");
		for (LP suc : this.getSuccessors()) {
			sb.append("LP" + suc.getId() + " ");
		}
		sb.append("\n");
		sb.append("\tpredecessors: ");
		for (LP pred : this.getPredecessors()) {
			sb.append("LP" + pred.getId() + " ");
		}
		sb.append("\n");

		sb.append("\tinPlaces: ");
		if (getInPlaces() != null) {
			for (Place inPlace : getInPlaces()) {
				sb.append(inPlace.name + " ");
			}
		}
		sb.append("\n");

		return sb.toString();
	}

	/**
	 * Couples two arrays. Method could be extracted to Utility class.
	 * 
	 * @param first
	 *            array number one
	 * @param second
	 *            array number two
	 * @param <T>
	 *            Type
	 * @return A merged array
	 */
	private static <T> T[] concat(T[] first, T[] second) {
		T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	public Place getNextEventPlace() {
		QueueEvent queueEvent = eventList.peek();
		if (queueEvent != null) {
			return queueEvent.queue.qPlaces[0];
		} else {
			return null;
		}
	}

	public double getLastQueueEventTime() {
		if (eventList.size() > 0) {
			// QueueEvent queueEvent =
			// ((Collection<QueueEvent>)eventList).getget(eventList.size()-1);
			QueueEvent queueEvent = Collections.max(eventList);
			return queueEvent.time;
		} else {
			return clock;
		}
	}

	public double getNextEventTime() {
		QueueEvent queueEvent = eventList.peek();
		TokenEvent tokenEvent = incomingTokenList.peek();
		if (queueEvent != null) {
			if (tokenEvent != null && tokenEvent.getTime() < queueEvent.time) {
				return tokenEvent.getTime();
			}
			return queueEvent.time;
		} else {
			if (tokenEvent != null) {
				if (verbosityLevel > 0) {
					log.info("LP" + id + ": lookahead" + getLookahead());
				}
				return tokenEvent.getTime(); // + getLookahead();
			}
			return 0.0;
		}
	}

	private int getLookahead() {
		return 0;
	}

	public boolean isWorkloadGenerator() {
		if (this.getInPlaces() == null) {
			log.warn("inPlaces not set");
			return true;
		}
		if (this.getInPlaces().length == 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasSuccessor() {
		return (successorList.size() != 0);
	}
	
	public double getMinimumClockOfPredecessors(){
		double minimumClockOfPredecessors = 0; 
		for (LP pred : getPredecessors()) {
			if(minimumClockOfPredecessors == 0){
				minimumClockOfPredecessors = pred.getClock();
			}
			minimumClockOfPredecessors = (pred.getClock() < minimumClockOfPredecessors) ? pred.getClock() : minimumClockOfPredecessors;
		}
		return minimumClockOfPredecessors;
	}

	/**
	 * @return the successorList
	 */
	public List<LP> getSuccessorList() {
		return successorList;
	}

	/**
	 * @param successorList
	 *            the successorList to set
	 */
	public void setSuccessorList(List<LP> successorList) {
		this.successorList = successorList;
	}

	public double getRampUpLength() {
		return rampUpLength;
	}

	public double getTotRunLength() {
		return totRunLength;
	}

	public void setClock(double clock) {
		this.clock = clock;
	}

	public Place[] getInPlaces() {
		return inPlaces;
	}

	public void setTimeSaveToProcess(double timeSaveToProcess) {
		this.timeSaveToProcess = timeSaveToProcess;
	}

	public double getTimeSaveToProcess() {
		return timeSaveToProcess;
	}
	
	public void incrementEmittedTokenCounter(){
		emittedTokenCounter++;
	}

	public boolean isCountingEmittedTokens() {
		return isCountingEmittedTokens;
	}

}
