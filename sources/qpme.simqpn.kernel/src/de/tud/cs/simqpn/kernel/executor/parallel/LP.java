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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;

import cern.jet.random.Empirical;
import cern.jet.random.EmpiricalWalker;
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
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;
import de.tud.cs.simqpn.kernel.random.RandomNumberGenerator;

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
public class LP implements Executor, Runnable {

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
	private double clock = 0;
	/** Identification for the LP. Note: There is no control for id to be unique */
	private int id;
	/** List of LPs this LP can get tokens from. */
	private List<LP> predecessors = new ArrayList<LP>();
	/** List of LPs this LP sends tokens to. */
	private List<LP> successors = new ArrayList<LP>();

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
	 * inserted by this LP. Instead, incommingTokenList has to be blocking.
	 */

	/** List of Tokens with timestamp that arrived from other LPs. */
	private PriorityBlockingQueue<TokenEvent> incomingTokenList = new PriorityBlockingQueue<TokenEvent>(
			10, new Comparator<TokenEvent>() {
				public int compare(TokenEvent a, TokenEvent b) {
					return (a.getTime() < b.getTime() ? -1
							: (a.getTime() == b.getTime() ? 0 : 1));
				}
			});

	// private Probe[] probes;
	/** The places of the QPN this LP simulates. */
	private final Place[] places;
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
	/**
	 * The barrier at which this LPs waits until all other LPs arrive.
	 */
	private CyclicBarrier barrier;
	/** Global stop criterion. */
	private StopCriterionController stopCriterionController;
	/** Local stop criterion. */
	private boolean hasFinished = false;
	/** Lower bound on incoming time stamps. */
	private double timeSaveToProcess;
	/** Sets debug output to console used for debug purpose. */
	private int verbosityLevel = 0;//1; // 2

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
			SimulatorProgress progressMonitor, int id) {
		this.id = id;
		this.places = places;
		this.transitions = transitions;
		this.queues = queues;
		// TODO implement probes
		// this.probes = probes;
		this.progressMonitor = progressMonitor;
		this.configuration = configuration;
	}

	/**
	 * Performs simulation for the net elements belonging to this LP.
	 * 
	 * Should be similar to {@link SequentialExecutor#run()}
	 * 
	 * @see SequentialExecutor
	 */
	@Override
	public void run() {
		try {
			initializeWorkingVariables();

			// Note: we store totRunLen and rampUpLen in local variables to
			// improve performance of the while loop below.
			double totRunLength = configuration.totRunLen;
			double rampUpLength = configuration.rampUpLen;

			/** interval for heart beats */
			/*
			 * If secondsBtwChkStops is used, disable checking of stopping
			 * criterion until beforeInitHeartBeat == false. By setting
			 * nextChkAfter = totRunL stopping criterion checking is disabled.
			 */
			double nextChkAfter = configuration.timeBtwChkStops > 0 ? configuration.timeBtwChkStops
					: totRunLength;

			beginRunWallClock = System.currentTimeMillis();

			/*
			 * The value of the last wall clock time measurement. Used for
			 * progress updates.
			 */
			long lastTimeMsrm = System.currentTimeMillis();

			double maxProgressUpdateIntervalVirtualTime = progressMonitor
					.getMaxUpdateLogicalTimeInterval(configuration);
			long progressUpdateIntervalRealTime = progressMonitor
					.getMaxUpdateRealTimeInterval();

			waitForBarrier();

			/**
			 * ######################### MAIN SIMULATION LOOP #################
			 */
			while (!checkStopCriterion()) {
				startDataCollectionIfRampUpDone(rampUpLength);
				
				if (beforeInitHeartBeat) {
					nextChkAfter = determineMonitorUpdateRate(totRunLength,
							nextChkAfter, lastTimeMsrm,
							maxProgressUpdateIntervalVirtualTime,
							progressUpdateIntervalRealTime);
				} else {
					lastTimeMsrm = updateProgressMonitor(totRunLength,
							lastTimeMsrm);
				}

				nextChkAfter = checkForPrecission(nextChkAfter);


				processTokenEvents();

				fireTransitions(totRunLength);

				updateQueueEvents();

				QueueEvent nextEvent = eventList.peek();
				if (nextEvent != null && nextEvent.time <= timeSaveToProcess) {
					processQueueEvent(nextEvent);
					continue;
				}

				waitForBarrier();
				timeSaveToProcess = getTimeSaveToProcess() +300; //+300
				if (verbosityLevel == 1) {
					if (id == 1) {
						log.info("--------------Barrier-------------------");
					}
				}
				waitForBarrier();
			}
		} catch (SimQPNException ex) {
			log.error("Error during simulation run", ex);
		}
		finish();
	}

	/**
	 * Starts data collection if the clock exceeds rampUpLength.
	 * 
	 * @param rampUpLength
	 *            the clock value that determines end of ramp up
	 * @throws SimQPNException
	 *             if starting of places or queues fails
	 */
	private void startDataCollectionIfRampUpDone(double rampUpLength)
			throws SimQPNException {
		if (inRampUp && clock > rampUpLength) {
			inRampUp = false;
			endRampUpClock = clock;
			// if (configuration.getAnalMethod() ==
			// SimQPNConfiguration.AnalysisMethod.WELCH) {
			// break;
			// }
			for (int p = 0; p < places.length; p++) {
				places[p].start(configuration, clock);
			}
			for (Place p : places) {
				if (p instanceof QPlace) {
					((QPlace) p).queue.start(clock);
				}
			}
			// for (int pr = 0; pr < net.getNumProbes(); pr++) TODO
			// net.getProbe(pr).start(this);

			progressMonitor.finishWarmUp(id, configuration);
		}
	}

	/**
	 * Process incoming tokens from other LPs as long as they are save to
	 * process.
	 * 
	 * @throws SimQPNException
	 *             if the incomming token enables a transition which is not in
	 *             the LP
	 */
	private void processTokenEvents() throws SimQPNException {
		TokenEvent tkEvent;
		while (!incomingTokenList.isEmpty()) {
			if (incomingTokenList.peek().getTime() > timeSaveToProcess) {
				break;
			} else {
				tkEvent = incomingTokenList.poll();
				Place place = tkEvent.getPlace();

				if (clock < tkEvent.getTime()) {
					clock = tkEvent.getTime();
				}
				place.addTokens(tkEvent.getColor(), tkEvent.getNumber(),
						tkEvent.getTkCopyBuffer(), this);

				/**
				 * Check if the new token enabled a transition
				 */
				int nT, t;
				nT = place.outTrans.length;
				for (t = 0; t < nT; t++) {
					Transition trans = transitions[t];
					if (trans.enabled()) {
						int localTransId = trans.id - transitions[0].id;
						if (localTransId >= 0
								&& localTransId < transitions.length) {
							if (!transStatus[localTransId]) {
								transStatus[localTransId] = true;
								enTransCnt++;
							}
						} else {
							log.error("Error processing incomming tokens. Inconsistencies within transition array");
							throw new SimQPNException();
						}
					}
				}
			}

		}
	}

	/**
	 * Updates the queues. Makes sure all service completion events in PS
	 * QPlaces have been scheduled.
	 */
	private void updateQueueEvents() {
		for (Place p : places) {
			if (p instanceof QPlace) {
				Queue queue = ((QPlace) p).queue;
				queue.updateEvents(this);
			}
		}
	}

	/**
	 * Waits until all LPs have entered the barrier.
	 */
	private void waitForBarrier() {
		try {
			if (!checkStopCriterion()) {
				// barrier.await(500,TimeUnit.MILLISECONDS);
				barrier.await();
			}
		} catch (InterruptedException e) {
			log.error("", e);
		} catch (BrokenBarrierException e) {
			log.info("LP" + id + " left barrier");
			// } catch (TimeoutException e) {
			// log.info("LP"+id+" left barrier due to timeout");
		}
	}

	/**
	 * Returns a lower bound on time stamps of incoming TokenEvents.
	 * 
	 * @return the lower bound on timestamps for incoming TokenEvents
	 */
	private double getTimeSaveToProcess() {
		if (verbosityLevel > 1) {
			synchronized (LP.class) {
				List<Integer> listOfVisitedLPs = new ArrayList<Integer>();
				timeSaveToProcess = getTimeSaveToProcess(listOfVisitedLPs, "");
			}
		} else {
			List<Integer> listOfVisitedLPs = new ArrayList<Integer>();
			timeSaveToProcess = getTimeSaveToProcess(listOfVisitedLPs, "");
		}
		return timeSaveToProcess;
	}

	/**
	 * Returns a lower bound on time stamps of incoming events.
	 * 
	 * @param listOfVisitedLPs
	 *            List containing IDs of previously visited LPs
	 * @param string
	 *            string to format debug output
	 * @return the time up to this LP can process
	 * @see LP#getTimeSaveToProcess()
	 */
	private double getTimeSaveToProcess(List<Integer> listOfVisitedLPs,
			String string) {
		List<Double> list = new ArrayList<Double>();
		for (LP pre : this.predecessors) {
			if (!listOfVisitedLPs.contains(pre.id)) {
				if (!pre.eventList.isEmpty()) {
					//list.add(pre.getClock()+pre.getLookahead()); //BETTER
					list.add(pre.eventList.peek().time);
				} else {
					// copy list of visited
					List<Integer> newListOfVisitedLPs = new ArrayList<Integer>(
							listOfVisitedLPs.size() + 1);
					if (!listOfVisitedLPs.isEmpty()) {
						for (int i = 0; i < listOfVisitedLPs.size(); i++) {
							newListOfVisitedLPs.add(listOfVisitedLPs.get(i));
						}
					}
					// add pre to list of visited
					newListOfVisitedLPs.add(pre.id);
					double internalTimeSaveToProcess = pre
							.getTimeSaveToProcess(newListOfVisitedLPs,
									(string + "\t"));
					if (internalTimeSaveToProcess != 0.0) {
						list.add(internalTimeSaveToProcess + getLookahead());
					}
				}
			}
		}
		if (list.isEmpty()) {
			if (verbosityLevel > 1) {
				// log.error("no queue event in net.");
				log.info(string + "LP" + id + "[ ]");
			}
			return 0; // -100.0;
		} else {
			if (verbosityLevel > 1) {
				log.info(string + "LP" + id + " " + list);
				// log.info("LP" + id + " " + list);
			}
			return Collections.min(list);
		}

	}

	/**
	 * Returns the lookahead.
	 * 
	 * Note: Overly conservative: Assumes the queues to be parallel
	 * 
	 * @return Lookahead for this LP
	 */
	private double getLookahead() {
		List<Double> lookaheads = new ArrayList<Double>();
		for (Place place : places) {
			if (place.getClass().equals(QPlace.class)) {
				((QPlace)place).getLookahead();
			}
		}
		if (!lookaheads.isEmpty()) {
			return Collections.min(lookaheads);
		} else {
			return 0;
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
	private void processQueueEvent(QueueEvent event) throws SimQPNException {
		if (verbosityLevel > 0) {
			log.info("LP" + id + ": " + event.queue.name + " processed job at "
					+ (int) event.time + " of duration "
					+ (int) (event.time - clock) + " with lbts "
					+ (int) timeSaveToProcess);
		}

		// Advance simulation time
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
		eventList.remove(event);
	}

	/**
	 * Returns true if the LP can finish.
	 * 
	 * @return if the simulation within this LP can stop
	 */
	private boolean checkStopCriterion() {
		if (stopCriterionController.hasSimulationFinished()) {
			return true;
		}
		if (!hasFinished) {
			if (clock >= configuration.totRunLen) {
				hasFinished = true;
				stopCriterionController.incrementFinishedLPCounter();
			}
		}
		return false;

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
	private void fireTransitions(double totRunL) throws SimQPNException {
		double[] pdf;
		if (transitions != null) {
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
					nextTrans.fire(); // Fire
										// transition
										// System.out.println("LP" + id +
										// ": transition "
					// + nextTrans.name + " fired");

					// Update transStatus
					int p, t, nP, nT;
					Place pl;
					Transition tr;

					/**
					 * Check if some transitions were disabled (newly-disabled
					 * transitions)
					 * 
					 * NOTE: Only transitions of the same LP can be disabled
					 */
					nP = nextTrans.inPlaces.length;
					for (p = 0; p < nP; p++) {
						pl = nextTrans.inPlaces[p];
						nT = pl.outTrans.length;
						for (t = 0; t < nT; t++) {
							tr = pl.outTrans[t];
							int localTransId = tr.id - transitions[0].id; // ORRIG:
							// tr.id
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
										System.out
												.println("LP"
														+ id
														+ ":\t\t enabled "
														+ transitions[localTransId].name
														+ " [due to firing]");
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
							clock = totRunL;
							break;
						}
						fireCnt = 0;
					} else {
						fireCnt++;
					}
				} // end firing enabled transitions
			}
		}
	}

	/**
	 * Initializes the variables before simulation run.
	 * 
	 * @throws SimQPNException
	 *             if initialization of queues or places fails
	 */
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

		// Create randTransGen
		double[] pdf = new double[transitions.length];
		for (int t = 0; t < transitions.length; t++) {
			pdf[t] = 1;
		}
		if (pdf.length > 1) {
			randTransGen = new EmpiricalWalker(pdf, Empirical.NO_INTERPOLATION,
					RandomNumberGenerator.nextRandNumGen());
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
	}

	/**
	 * Clean up method after simulation run. data aufbereiten monitor...
	 */
	private void finish() {
		progressMonitor.updateSimulationProgress(id, 100, 0, configuration,
				inRampUp);

		if (progressMonitor.isCanceled()) {
			progressMonitor
					.warning(
							id,
							": The simulation was canceled by the user." // \n
									+ "The required precision may not have been reached!");
		} else {
			if (clock >= configuration.totRunLen) {
				if (configuration.stoppingRule != SimQPNConfiguration.FIXEDLEN) {
					progressMonitor
							.warning(
									id,
									"The simulation was stopped because of reaching max totalRunLen." // \n
											+ "The required precision may not have been reached!");
				} else {
					log.info("STOPPING because max totalRunLen is reached!");
				}
			}
		}

		endRunClock = clock;
		msrmPrdLen = endRunClock - endRampUpClock;
		endRunWallClock = System.currentTimeMillis();

		// total time elapsed in seconds
		runWallClockTime = (endRunWallClock - beginRunWallClock) / 1000;

		log.info("LP" + id + ": " + "msrmPrdLen= " + msrmPrdLen
				+ " totalRunLen= " + endRunClock + " runWallClockTime="
				+ (int) (runWallClockTime / 60) + " min (=" + runWallClockTime
				+ " sec)");

		// Complete statistics collection (make sure this is done AFTER the
		// above statements)
		if (configuration.getAnalMethod() != SimQPNConfiguration.AnalysisMethod.WELCH) {
			try {
				for (int p = 0; p < places.length; p++) {
					places[p].finish(configuration, runWallClockTime, clock);
				}
				for (int q = 0; q < queues.length; q++) {
					// NOTE: queues[*].finish() should be called after
					// places[*].finish()!
					queues[q].finish(configuration, runWallClockTime, clock);
				}
				// for (int pr = 0; pr < probes.length; pr++)
				// probes[pr].finish(configuration, clock);
			} catch (SimQPNException e) {
				log.error("", e);
			}
		}
	}

	/**
	 * Updates progress monitor if the simulation progressed far enough since
	 * last update.
	 * 
	 * @param totRunL
	 *            the length of the simulation run
	 * @param lastTimeMsrm
	 *            the time of the last monitor update
	 * @return the time of the last monitor update
	 */
	private long updateProgressMonitor(double totRunL, long lastTimeMsrm) {
		if (clock >= nextHeartBeat) {
			long curTimeMsrm = System.currentTimeMillis();
			progressMonitor.updateSimulationProgress(getId(), clock
					/ (totRunL - 1) * 100, (curTimeMsrm - lastTimeMsrm),
					configuration, inRampUp);
			lastTimeMsrm = curTimeMsrm;
			nextHeartBeat = clock + timeBtwHeartBeats;

			if (progressMonitor.isCanceled()) {
				clock = totRunL;
			}
		}
		return lastTimeMsrm;
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
	private double determineMonitorUpdateRate(double totRunL,
			double nextChkAfter, long lastTimeMsrm, double maxProgressInterval,
			long progressUpdateRate) {
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
		return nextChkAfter;
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
	private double checkForPrecission(double nextChkAfter)
			throws SimQPNException {
		if (configuration.stoppingRule != SimQPNConfiguration.FIXEDLEN
				&& (!inRampUp) && clock > nextChkAfter) {
			double elapsedSecs = (System.currentTimeMillis() - beginRunWallClock) / 1000;
			double clockTimePerSec = clock / elapsedSecs;
			boolean done = true;
			Place pl = null;

			// for (int p = 0; p < net.getNumPlaces(); p++) {
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
						stopCriterionController.incrementFinishedLPCounter();
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
		return nextChkAfter;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addTokenEvent(TokenEvent tokenEvent) {
		incomingTokenList.add(tokenEvent);
	}

	/**
	 * Sets controller for global stop criterion.
	 * 
	 * @param stopCriterionController
	 *            the controller which manages local stop criteria
	 */
	public void setStopCriterion(StopCriterionController stopCriterionController) {
		this.stopCriterionController = stopCriterionController;
	}

	/**
	 * Sets global barrier for LP synchronization.
	 * 
	 * @param barrier
	 *            the barrier LPs wait if they have no events save to process
	 */
	public void setBarrier(CyclicBarrier barrier) {
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
	public synchronized void scheduleEvent(double serviceTime, Queue queue,
			Token token) {
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
	public synchronized void removeEvent(QueueEvent event) {
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

	/**
	 * Returns transitions array.
	 * 
	 * @return the transitions of this LP.
	 */
	public Transition[] getTransitions() {
		return transitions;
	}

	/**
	 * Returns places array.
	 * 
	 * @return the places of this LP.
	 */
	public Place[] getPlaces() {
		return places;
	}

	/**
	 * Returns queues array.
	 * 
	 * @return the queues of this LP.
	 */
	public Queue[] getQueues() {
		return queues;
	}

	/**
	 * Returns a list of succeeding LPs.
	 * 
	 * @return LPs succeeding this LP
	 */
	public List<LP> getSuccessors() {
		return successors;
	}

	/**
	 * Appends a new successor to the list of successors.
	 * 
	 * @param successor
	 *            the LP to be added
	 */
	public void addSuccessor(LP successor) {
		this.successors.add(successor);
	}

	/**
	 * Returns a list of preceding LPs.
	 * 
	 * @return LPs preceding this LP
	 */
	public List<LP> getPredecessors() {
		return predecessors;
	}

	/**
	 * Appends a new predecessor to the list of predecessors.
	 * 
	 * @param predecessor
	 *            the LP to be added to predecessors
	 */
	public void addPredecessor(LP predecessor) {
		this.predecessors.add(predecessor);
	}
}
