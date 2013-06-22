package de.tud.cs.simqpn.kernel.executor.parallel;

import java.util.Comparator;
import java.util.PriorityQueue;

import org.apache.log4j.Logger;

import cern.jet.random.Empirical;
import cern.jet.random.EmpiricalWalker;
import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.Probe;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.Queue;
import de.tud.cs.simqpn.kernel.entities.Token;
import de.tud.cs.simqpn.kernel.entities.Transition;
import de.tud.cs.simqpn.kernel.executor.Executor;
import de.tud.cs.simqpn.kernel.executor.QueueEvent;
import de.tud.cs.simqpn.kernel.executor.SequentialExecutor;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;
import de.tud.cs.simqpn.kernel.random.RandomNumberGenerator;

/**
 * LogicalProcess
 */
public class LP implements Executor, Runnable {

	private static Logger log = Logger.getLogger(SequentialExecutor.class);
	private SimQPNConfiguration configuration;
	private double clock = 0;
	public boolean inRampUp;				// True if still in RampUp period (no measurements taken).
	public double endRampUpClock;		// Clock at the end of RampUp, i.e. beginning of the measurement period.
	public double endRunClock;			// Clock at the end of the run.
	public double msrmPrdLen;			// Duration of the measurement period (endRunClock - endRampUpClock).
	public double beginRunWallClock;		// currentTimeMillis at the begin of the run (wall clock time).
	public double endRunWallClock;		// currentTimeMillis at the end of the run (wall clock time).
	public double runWallClockTime;		// Total duration of the run in seconds.

	private int id;
	/** LP before this */
	private LP predecessor; // TODO make this a list
	/** LP after this */
	private LP successor;

	/**
	 * LP event list. Contains events scheduled for processing at specified
	 * points in time.
	 */
	public PriorityQueue<QueueEvent> eventList = new PriorityQueue<QueueEvent>(
			10, new Comparator<QueueEvent>() {
				public int compare(QueueEvent a, QueueEvent b) {
					return (a.time < b.time ? -1 : (a.time == b.time ? 0 : 1));
				}
			});
	private Place[] places;
	private Transition[] transitions;
	private Queue[] queues;
	// private Probe[] probes;
	private SimulatorProgress progressMonitor;

	LP(Place[] places, Transition[] transitions, Queue[] queues, /**
	 * Probe[]
	 * probes,
	 */
	SimQPNConfiguration configuration, SimulatorProgress progressMonitor, int id) {
		this.id = id;
		this.places = places;
		this.transitions = transitions;
		this.queues = queues;
		// this.probes = probes;
		this.progressMonitor = progressMonitor;
		this.configuration = configuration;
	}

	/**
	 * Should be similar to {@link SequentialExecutor#run()
	 */
	public void run() {
		try {
			boolean[] transStatus; // Transition status: true = enabled, false =
									// disabled
			int enTransCnt = 0;
			int[] enTransIDs = new int[transitions.length];

			EmpiricalWalker randTransGen; // Random number generator for
											// generating
											// next transition to fire.

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

			// Create randTransGen
			double[] pdf = new double[transitions.length];
			for (int t = 0; t < transitions.length; t++)
				pdf[t] = 1;

			randTransGen = new EmpiricalWalker(pdf, Empirical.NO_INTERPOLATION,
					RandomNumberGenerator.nextRandNumGen());

			// Note: Here we use a default distribution. The actual
			// distribution
			// is
			// set each time before using randTransGen.

			// Note: we store totRunLen and rampUpLen in local variables to
			// improve
			// performance of the while loop below.
			double totRunL = configuration.totRunLen;
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
			 * (progress update). If true, the value of timeBtwHeartBeats is
			 * still measured, and set to 0.
			 */
			boolean beforeInitHeartBeat = true;
			/* Simulation run time of the next heart beat. */
			double nextHeartBeat = 0.0;
			/*
			 * Determines How often progress updates are made (in logical
			 * simulation time units).
			 */
			double timeBtwHeartBeats = 0.0;
			/*
			 * The value of the last wall clock time measurement. Used for
			 * progress updates.
			 */
			long lastTimeMsrm = System.currentTimeMillis();
			double maxProgressInterval = progressMonitor
					.getMaxUpdateLogicalTimeInterval(configuration);
			long progressUpdateRate = progressMonitor
					.getMaxUpdateRealTimeInterval();

			int cnt = 0;

			while (clock < totRunL) {
				// for (int i = 0; i < 10; i++) {
				// for (int i = 0; i < 100000000; i++) {
				if (inRampUp && clock > rampUpL) {
					inRampUp = false;
					endRampUpClock = clock;
					if (configuration.getAnalMethod() == SimQPNConfiguration.AnalysisMethod.WELCH)
						break;
					for (int p = 0; p < places.length; p++)
						places[p].start(configuration, clock);
					for (Place p : places) {
						if (p instanceof QPlace) {
							((QPlace) p).queue.start(clock);
						}
					}
					// for (int pr = 0; pr < net.getNumProbes(); pr++) TODO
					// net.getProbe(pr).start(this);

					progressMonitor.finishWarmUp(configuration);
				}

				// Step 1: Fire until no transitions are enabled.
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
							nextTrans = transitions[enTransIDs[randTransGen
									.nextInt()]];
						}

						System.out.println("LP" + id + ": transition "
								+ nextTrans.name + " fired  | Thread "+Thread.currentThread());
						nextTrans.fire(); // Fire
																	// transition

						// Update transStatus
						int p, t, nP, nT;
						Place pl;
						Transition tr;
						// Check if some transitions were disabled
						// (newly-disabled
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
					synchronized (successor) {
						successor.notifyAll();
					}
				}

				// Step 2: Make sure all service completion events in PS QPlaces
				// have been scheduled
				for (Place p : places) {
					if (p instanceof QPlace) {
						Queue queue = ((QPlace) p).queue;
						if (queue.queueDiscip == Queue.PS
								&& (!queue.eventsUpToDate)) {
							System.out
									.println("LP"
											+ id
											+ ": place "
											+ p.name
											+ " updated jobs at "
											+ ((QPlace) p).queue.name
											+ " [NOTE: after fireing, service times may change in PS]");
							queue.updateEvents(this);
						}
					}
				}

				// Step 3: Process next event in event list

				// if (eventList.size() > 0) {
				// QueueEvent ev = eventList.poll();
				// while(!isSaveToProcess(ev.time)){
				// this.wait();
				// wait OR add to future list
				// }
				// Advance simulation time
				// clock = ev.time;
				// }
				if (places != null) {
					if (eventList.size() > 0) {
						QueueEvent ev = eventList.poll();
						synchronized (this) {
							try {
								while (!isSaveToProcess(ev.time)) {
									System.out.println("LP" + id
											+ " is waiting");
									this.wait();
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}

						System.out.println("LP" + id + ": " + ev.queue.name
								+ " processed job at " + ev.time);
						// Event ev = (Event) eventList.remove(0); // Old
						// LinkedList
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
						// } else {
						// log.error("QPN is not live.");
						// throw new SimQPNException();
					}

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
						progressMonitor.updateSimulationProgress(clock
								/ (totRunL - 1) * 100,
								(curTimeMsrm - lastTimeMsrm), configuration, inRampUp);
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
						progressMonitor.precisionCheck(done, pl.name);
					} else {
						// Check also the probes, whether they have enough
						// samples
						Probe probe = null;
						// TODO uncomment
						// for (int pr = 0; pr < net.getNumProbes(); pr++) {
						// probe = net.getProbe(pr);
						// if (probe.statsLevel >= 3) {
						// if (!probe.probeStats.enoughStats(this)) {
						// done = false;
						// break;
						// }
						// }
						// }
						if (done) {
							progressMonitor.precisionCheck(done, null);
							break; // exit while loop
						} else {
							progressMonitor.precisionCheck(done, probe.name); // TODO:
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

		progressMonitor.updateSimulationProgress(100, 0, configuration, inRampUp);

		if (progressMonitor.isCanceled()) {
			progressMonitor
					.warning("The simulation was canceled by the user.\n"
							+ "The required precision may not have been reached!");
		} else {
			if (clock >= configuration.totRunLen) {
				if (configuration.stoppingRule != SimQPNConfiguration.FIXEDLEN) {
					progressMonitor
							.warning("The simulation was stopped because of reaching max totalRunLen.\n"
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

		log.info("msrmPrdLen= " + msrmPrdLen + " totalRunLen= "
				+ endRunClock + " runWallClockTime="
				+ (int) (runWallClockTime / 60) + " min (="
				+ runWallClockTime + " sec)");

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
				e.printStackTrace();
			}
		}
		System.out.println("LP" + id + " has finished");
	}

	@Override
	public double getClock() {
		return clock;
	}

	@Override
	public void scheduleEvent(double serviceTime, Queue queue, Token token) {
		QueueEvent ev = new QueueEvent(clock + serviceTime, queue, token);
		eventList.add(ev);
		queue.nextEvent = ev;
	}

	@Override
	public void removeEvent(QueueEvent event) {
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

	private boolean isSaveToProcess(double nextEventTime) {
		return clock <= predecessor.getTimeSaveToProcess();
		// if (predecessorLP.getTimeSaveToProcess() < nextEventTime) {
		// return false;
		// }

		// for (ParallelTransition trans : incommingTransitions) {
		// LP predecessorLP = trans.getLP();
		// if (predecessorLP.getTimeSaveToProcess() < nextEventTime) {
		// return false;
		// }
		// }
		// return true;
	}

	/**
	 * A sucessor LP can ask how far it can process
	 * 
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

	public LP getSuccessor() {
		return successor;
	}

	public void setSuccessor(LP successor) {
		this.successor = successor;
	}

	public LP getPredecessor() {
		return predecessor;
	}

	public void setPredecessor(LP predecessor) {
		this.predecessor = predecessor;
	}

}
