package de.tud.cs.simqpn.kernel.executor.parallel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.PriorityBlockingQueue;

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
import de.tud.cs.simqpn.kernel.executor.Executor;
import de.tud.cs.simqpn.kernel.executor.QueueEvent;
import de.tud.cs.simqpn.kernel.executor.SequentialExecutor;
import de.tud.cs.simqpn.kernel.executor.TokenEvent;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;
import de.tud.cs.simqpn.kernel.random.RandomNumberGenerator;

/**
 * Logical Process
 */
public class LP implements Executor, Runnable {

	private static Logger log = Logger.getLogger(LP.class);
	private SimQPNConfiguration configuration;
	private double clock = 0;
	/** True if still in RampUp period (no measurements taken). */
	public boolean inRampUp;
	/** Clock at the end of RampUp, i.e. beginning of the measurement period. */
	public double endRampUpClock;
	/** Clock at the end of the run. */
	public double endRunClock;
	/** Duration of the measurement period (endRunClock - endRampUpClock). */
	public double msrmPrdLen;
	/** currentTimeMillis at the begin of the run (wall clock time). */
	public double beginRunWallClock;
	/** currentTimeMillis at the end of the run (wall clock time). */
	public double endRunWallClock;
	/** Total duration of the run in seconds. */

	public double runWallClockTime;

	private int id;

	/** List of LPs that can create events for this LP */
	private List<LP> predecessors = new ArrayList<LP>();
	/** List of LPs this LP can create events for */
	private List<LP> successors = new ArrayList<LP>();

	/**
	 * List of queue events scheduled for processing at specified points in
	 * time.
	 */
	// QueueEventList needs not to be synchronized
	private PriorityQueue<QueueEvent> eventList = new PriorityQueue<QueueEvent>(
			10, new Comparator<QueueEvent>() {
				public int compare(QueueEvent a, QueueEvent b) {
					return (a.time < b.time ? -1 : (a.time == b.time ? 0 : 1));
				}
			});

	private PriorityBlockingQueue<TokenEvent> incomingTokenList = new PriorityBlockingQueue<TokenEvent>(
			10, new Comparator<TokenEvent>() {
				public int compare(TokenEvent a, TokenEvent b) {
					return (a.getIncommingTime() < b.getIncommingTime() ? -1
							: (a.getIncommingTime() == b.getIncommingTime() ? 0
									: 1));
				}
			});

	private Place[] places;
	private Transition[] transitions;
	private Queue[] queues;

	// private Probe[] probes;
	private SimulatorProgress progressMonitor;

	private boolean[] transStatus; // Transition status: true = enabled, false =
	private int enTransCnt;
	private int[] enTransIDs;

	/**
	 * Flag indicating when we are still before the first heart beat (progress
	 * update). If true, the value of timeBtwHeartBeats is still measured, and
	 * set to 0.
	 */
	boolean beforeInitHeartBeat;

	/** Simulation run time of the next heart beat. */
	double nextHeartBeat;

	/**
	 * Determines How often progress updates are made (in logical simulation
	 * time units).
	 */
	double timeBtwHeartBeats;

	/** Random number generator for generating next transition to fire. */
	private EmpiricalWalker randTransGen;
	private Net net;

	private CyclicBarrier barrier;

	/** global stop criterion*/
	private StopCriterion stopCriterion;
	
	/** local stop criterion*/
	boolean finished = false;



	LP(Place[] places, Transition[] transitions, Queue[] queues, /**
	 * Probe[]
	 * probes,
	 */
	SimQPNConfiguration configuration, SimulatorProgress progressMonitor,
			int id, Net net) {
		this.id = id;
		this.net = net;
		this.places = places;
		this.transitions = transitions;
		this.queues = queues;
		// this.probes = probes;
		this.progressMonitor = progressMonitor;
		this.configuration = configuration;
	}

	/**
	 * TODO Work in Progress
	 * 
	 * @param lp_to_merge
	 */
	public void merge(LP lp_to_merge) {
		this.places = concat(places, lp_to_merge.getPlaces());
		this.transitions = concat(transitions, lp_to_merge.getTransitions());
		this.queues = concat(queues, lp_to_merge.getQueues());
	}

	private double nextEventTime;
	private double lbts;

	/**
	 * Should be similar to {@link SequentialExecutor#run()}
	 */
	public void run() {
		try {
			initializeWorkingVariables();

			// Note: we store totRunLen and rampUpLen in local variables to
			// improve performance of the while loop below.
			double totRunL = configuration.totRunLen;
			double rampUpL = configuration.rampUpLen;
			double nextChkAfter = configuration.timeBtwChkStops > 0 ? configuration.timeBtwChkStops
					: totRunL; // If secondsBtwChkStops is used, disable
								// checking of
								// stopping criterion
			// until beforeInitHeartBeat == false. By setting nextChkAfter =
			// totRunL
			// stopping criterion checking is disabled.

			beginRunWallClock = System.currentTimeMillis();

			/*
			 * The value of the last wall clock time measurement. Used for
			 * progress updates.
			 */
			long lastTimeMsrm = System.currentTimeMillis();

			double maxProgressInterval = progressMonitor
					.getMaxUpdateLogicalTimeInterval(configuration);
			long progressUpdateRate = progressMonitor
					.getMaxUpdateRealTimeInterval();

			System.out.println("LP" + id + " has been initialized");
			try {
				barrier.await();
			} catch (InterruptedException e) {
				log.error("",e);
			} catch (BrokenBarrierException e) {
				//log.error("",e);
			}

			/**
			 * ######################### MAIN LOOP ###################
			 */
			while (!checkStopCriterion()) {
				if (inRampUp && clock > rampUpL) {
					inRampUp = false;
					endRampUpClock = clock;
					if (configuration.getAnalMethod() == SimQPNConfiguration.AnalysisMethod.WELCH) {
						break;
					}
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

				// Process incoming tokens
				TokenEvent tkEvent;
				while (!incomingTokenList.isEmpty()) {
					if (incomingTokenList.peek().getIncommingTime() > lbts) {
						break;
					} else {
						tkEvent = incomingTokenList.poll();
						Place place = tkEvent.getPlace();

						if (clock < tkEvent.getIncommingTime()) {
							clock = tkEvent.getIncommingTime();
						}
						place.addTokens(tkEvent.getColor(),
								tkEvent.getNumber(), tkEvent.getTkCopyBuffer(),
								this);

						/**
						 * check if new token enabled transition
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
										System.out
												.println("LP"
														+ id
														+ ": \t\t enabled "
														+ transitions[localTransId].name);
										transStatus[localTransId] = true;
										enTransCnt++;
									}
								} else {
									System.out.println("NOT POSSIBLE");
									throw new SimQPNException();
								}
							}
						}
					}

				}

				// Step 1
				fireTransitions(enTransIDs, randTransGen, totRunL);

				// Step 2
				// Make sure all service completion events in PS
				// QPlaces have been scheduled
				// DOES NOT NEED SYNCHRONIZATION
				// actualize if something has changed
				for (Place p : places) {
					if (p instanceof QPlace) {
						Queue queue = ((QPlace) p).queue;
						if(!queue.areEventsUpToDate()){
						//if (queue.queueDiscip == QueuingDiscipline.PS
//								&& (!queue.areEventsUpToDate())) {
							// System.out.println("LP" + id + ":\t\t place "
							// + p.name + " updated jobs at "
							// + ((QPlace) p).queue.name);
							// +
							// " [NOTE: after firing, service times may change in PS]");
							try {
								queue.updateEvents(this);
							} catch (SimQPNException e) {
								log.error("",e);
							}
						}
					}
				}

				/*
				 * SYNCHRONIZATION
				 */
				QueueEvent event = eventList.peek();
				if (event != null) {
					nextEventTime = event.time;
				} else {
					nextEventTime = Double.MAX_VALUE;
				}
				if (lbts >= nextEventTime) {
					// System.out.println("LP" + id + " can process event"
					// + (int) nextEventTime + " smaller than LBTS"
					// + (int) lbts);
					// Step 3
					processEvent(eventList.peek());
					continue;
				}

				try {
					if (!checkStopCriterion()) {
						barrier.await();
					}
				} catch (InterruptedException e) {
					log.error("",e);
				} catch (BrokenBarrierException e) {
					//log.error("",e);
				}
				lbts = getLBTS();
				// System.out.println("LP" + id + " lbts" + lbts +
				// " | eventList size "+eventList.size());
				try {
					if (!checkStopCriterion()) {
						barrier.await();
					}
				} catch (InterruptedException e) {
					log.error("",e);
				} catch (BrokenBarrierException e) {
					//log.error("",e);
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
							// System.out.println("LP"+id+" is DONE DONE DONE DONE");
							progressMonitor.precisionCheck(id, done, null);
							//break; // exit while loop
							finished = true;
							stopCriterion.notifyFinishedLP();
						} else {
							progressMonitor.precisionCheck(id, done, probe.name); // TODO:
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
		} catch (SimQPNException ex) {
			System.out.println(ex);
		}
		progressMonitor.updateSimulationProgress(id, 100, 0, configuration,
				inRampUp);

		if (progressMonitor.isCanceled()) {
			progressMonitor
					.warning(id, ": The simulation was canceled by the user." // \n
							+ "The required precision may not have been reached!");
		} else {
			if (clock >= configuration.totRunLen) {
				if (configuration.stoppingRule != SimQPNConfiguration.FIXEDLEN) {
					progressMonitor
							.warning(id, "The simulation was stopped because of reaching max totalRunLen." // \n
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

		log.info("LP"+id+": " +"msrmPrdLen= " + msrmPrdLen + " totalRunLen= " + endRunClock
				+ " runWallClockTime=" + (int) (runWallClockTime / 60)
				+ " min (=" + runWallClockTime + " sec)");

		// Complete statistics collection (make sure this is done AFTER the
		// above statements)
		if (configuration.getAnalMethod() != SimQPNConfiguration.AnalysisMethod.WELCH) {
			try {
				for (int p = 0; p < places.length; p++)
					places[p].finish(configuration, runWallClockTime, clock);
				for (int q = 0; q < queues.length; q++)
					// NOTE: queues[*].finish() should be called after
					// places[*].finish()!
					queues[q].finish(configuration, runWallClockTime, clock);
				// for (int pr = 0; pr < probes.length; pr++)
				// probes[pr].finish(configuration, clock);
			} catch (SimQPNException e) {
				log.error("",e);
			}
		}
	}

	double getLBTS() {
		List<Integer> listOfVisited = new ArrayList<Integer>();
		Double lbts = getLBTS(listOfVisited);
		if (lbts == null) {
			QueueEvent event;
			event = eventList.peek();
			if (event != null) {
				return event.time;
			} else {
				return this.clock;
			}
		} else {
			return lbts;
		}
	}

	Double getLBTS(List<Integer> listOfVisitedLPs) {
		List<Double> list = new ArrayList<Double>();
		QueueEvent event;
		event = eventList.peek();
		for (LP pre : this.predecessors) {
			if (!listOfVisitedLPs.contains(pre.id)) {
				listOfVisitedLPs.add(pre.id);
				event = pre.eventList.peek();
				if (event != null) {
					list.add(event.time);
				} else {
					Double lbts = pre.getLBTS(listOfVisitedLPs);
					if (lbts != null) {
						list.add(lbts);
					}
				}
			}
		}
		if (list.isEmpty()) {
			return null;
		} else {
			return Collections.min(list);
		}

	}

	private void processEvent(QueueEvent ev) throws SimQPNException {
		if (ev.queue == null) {
			// null message
			clock = ev.time;
			log.error("Null message");
			throw new SimQPNException();
		} else {
//			System.out.println("LP" + id + ": " + ev.queue.name
//					+ " processed job at " + (int) ev.time + " of duration "
//					+ (int) (ev.time - clock) + " with lbts " + (int) lbts);

			// Advance simulation time
			clock = ev.time;

			QPlace qpl = (QPlace) ev.token.place;
			qpl.completeService(ev.token, this);
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
						synchronized (this) {
							if ((!transStatus[localTransId])) {
								// System.out.println("LP" + id
								// + ": \t\t enabled "
								// + transitions[localTransId].name
								// + "[due to queueEvent]");
								transStatus[localTransId] = true;
								enTransCnt++;
							}
						}
					}
				}

			}
			eventList.remove(ev);
		}
	}


	/**
	 * Returns true if the LP can finish
	 * 
	 * @return
	 */
	private boolean checkStopCriterion() {
		if (clock < configuration.totRunLen) {
			return false;
		} else {
			if (stopCriterion.hasSimulationFinished()) {
				return true;
			}
			if (!finished) {
				finished = true;
				stopCriterion.notifyFinishedLP();
			}
			return false;
		}
	}

	/**
	 * Step 1: Fire until no transitions are enabled.
	 * 
	 * @param enTransIDs
	 * @param randTransGen
	 * @param totRunL
	 * @throws SimQPNException
	 */
	private void fireTransitions(int[] enTransIDs,
			EmpiricalWalker randTransGen, double totRunL)
			throws SimQPNException {
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
							int localTransId = tr.id - transitions[0].id; // tr.id
							if (tr.enabled()) {
								if (localTransId >= 0
										&& localTransId < transitions.length) {
									synchronized (this) {
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
								} else {
									// TODO will be done at incomingTokenList
									// processing
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
		for (int i = 0; i < places.length; i++)
			places[i].init(getClock());
		for (int i = 0; i < transitions.length; i++)
			transitions[i].init();
		for (int i = 0; i < queues.length; i++)
			queues[i].init(configuration);
		enTransCnt = 0;
		enTransIDs = new int[transitions.length];

		// Create randTransGen
		double[] pdf = new double[transitions.length];
		for (int t = 0; t < transitions.length; t++) {
			pdf[t] = 1;
		}
		randTransGen = new EmpiricalWalker(pdf, Empirical.NO_INTERPOLATION,
				RandomNumberGenerator.nextRandNumGen());

		// Initialize transStatus and enTransCnt
		synchronized (this) {
			transStatus = new boolean[transitions.length];
			for (int i = 0; i < transitions.length; i++) {
				if (transitions[i].enabled()) {
					transStatus[i] = true;
					enTransCnt++;
				} else {
					transStatus[i] = false;
				}
			}
		}

		beforeInitHeartBeat = true;
		nextHeartBeat = 0.0;
		timeBtwHeartBeats = 0.0;
	}

	public static <T> T[] concat(T[] first, T[] second) {
		T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}
	
	public void setStopCriterion(StopCriterion stopCriterion) {
		this.stopCriterion = stopCriterion;
	}

	public void setBarrier(CyclicBarrier barrier) {
		this.barrier = barrier;
	}
	
	@Override
	public double getClock() {
		return clock;
	}
	
	@Override
	public synchronized void scheduleEvent(double serviceTime, Queue queue,
			Token token) {
		QueueEvent ev = new QueueEvent(clock + serviceTime, queue, token);
		eventList.add(ev);
		if (queue != null) {
			queue.onQueueEventScheduled(ev);
		}
	}

	@Override
	public synchronized void removeEvent(QueueEvent event) {
		eventList.remove(event);
	}

	@Override
	public SimQPNConfiguration getConfiguration() {
		return configuration;
	}

	@Override
	public SimulatorProgress getProgressMonitor() {
		return progressMonitor;
	}

	public double getLookahead() {
		double loookahead = 0.0;
		// sucessor.place.;
		return loookahead;
	}

	public int getId() {
		return id;
	}

	public Transition[] getTransitions() {
		return transitions;
	}

	public void setTransitions(Transition[] transitions) {
		this.transitions = transitions;
	}

	public Place[] getPlaces() {
		return places;
	}

	public void setPlaces(Place[] places) {
		this.places = places;
	}

	public List<LP> getSuccessors() {
		return successors;
	}

	public void addSuccessor(LP successor) {
		this.successors.add(successor);
	}

	public List<LP> getPredecessors() {
		return predecessors;
	}

	public void addPredecessor(LP executor) {
		this.predecessors.add(executor);
	}

	public Queue[] getQueues() {
		return queues;
	}

	public void setQueues(Queue[] queues) {
		this.queues = queues;
	}

	@Override
	public void addTokenEvent(TokenEvent tokenEvent) {
		incomingTokenList.add(tokenEvent);
		synchronized (this) {
			this.notify();
		}
	}

}
