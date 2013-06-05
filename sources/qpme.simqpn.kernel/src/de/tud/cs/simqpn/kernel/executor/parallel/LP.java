package de.tud.cs.simqpn.kernel.executor.parallel;

import java.util.Comparator;
import java.util.PriorityQueue;

import cern.jet.random.Empirical;
import cern.jet.random.EmpiricalWalker;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Node;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.Queue;
import de.tud.cs.simqpn.kernel.entities.Transition;
import de.tud.cs.simqpn.kernel.entities.parallel.ParallelTransition;
import de.tud.cs.simqpn.kernel.executor.QueueEvent;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;
import de.tud.cs.simqpn.kernel.random.RandomNumberGenerator;

/**
 * LogicalProcess
 */
public class LP implements Runnable {

	private SimQPNConfiguration configuration;
	private double clock = 0;
	ParallelTransition[] outgoingTransitions;
	ParallelTransition[] incommingTransitions;
	int id;

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
	private SimulatorProgress progressMonitor;

	LP(Place[] places, Transition[] transitions,
			SimQPNConfiguration configuration,
			SimulatorProgress progressMonitor, int id) {
		this.id = id;
		this.places = places;
		this.transitions = transitions;
		this.progressMonitor = progressMonitor;
		this.configuration = configuration;
	}

	private boolean isSaveToProcess(double nextEventTime) {
		for (ParallelTransition trans : incommingTransitions) {
			LP predecessorLP = trans.getLP();
			if (predecessorLP.getTimeSaveToProcess() < nextEventTime) {
				return false;
			}
		}
		return true;
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

	/**
	 * Should be similar to Executor.run
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

			configuration.beginRunWallClock = System.currentTimeMillis();

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

			// while (clock < totRunL) {

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

					System.out.println("transition " + nextTrans.name
							+ " fired");
					//TODO FIX this
					nextTrans.fire(null); // Fire transition

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
			}

			// Step 2: Make sure all service completion events in PS QPlaces
			// have been scheduled
			// TODO

			// if (eventList.size() > 0) {
			// QueueEvent ev = eventList.poll();
			// while(!isSaveToProcess(ev.time)){
			// this.wait();
			// wait OR add to future list
			// }
			// Advance simulation time
			// clock = ev.time;

			// }
			// Step 3: Process next event in event list
			if (places != null) {
			
			}
			// Step 4: Heart Beat
			// }
		} catch (SimQPNException ex) {
			System.out.println(ex);
		}

		System.out.println("LP" + id + "hast finished");
	}

}
