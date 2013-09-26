package de.tud.cs.simqpn.kernel.executor.parallel.barrier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.management.MXBean;

import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.executor.parallel.LP;
import de.tud.cs.simqpn.kernel.executor.parallel.termination.StopController;

public class LookaheadMinReductionBarrierAction implements Runnable {

	private final StopController stopController;
	LP[] lps;
	private Thread[] threads;
	final int verbosityLevel;
	final int numLPs;
	private double[] clock;
	private boolean[] isInProgress;
	private boolean[] hasBeenCalculated;
	private double[] lookahead;
	private boolean inRampUp;
	private double rampUpLength;
	private double totRunLength;

	public LookaheadMinReductionBarrierAction(StopController stopController,
			LP[] lps, double rampUpLength, double totRunLength, int verbosityLevel) {
		this.stopController = stopController;
		this.lps = lps;
		this.verbosityLevel = verbosityLevel;
		this.numLPs = lps.length;
		this.clock = new double[numLPs];
		this.isInProgress = new boolean[numLPs];
		this.hasBeenCalculated = new boolean[numLPs];
		this.lookahead = new double[numLPs];
		this.inRampUp = true;
		this.rampUpLength = rampUpLength;
		this.totRunLength = totRunLength;
	}

	@Override
	public void run() {
		if (!stopController.hasSimulationFinished()) {
			double min = Double.MAX_VALUE;
			int id  = 0;
			for (int i = 0; i < numLPs; i++) {
				final LP lp = lps[i];
				if(lp.sucessorIds.length == 0){
					continue;
				}
//				try {
//					//lp.processTokenEventIfPossible();
//					lp.processSaveEvents();
//				} catch (Exception e) {
//				}
				double time = lp.getNextEventTime();
				if(min > time && time != 0.0){
					min = time;
					id = lp.getId();
				}
			}

			for (int i = 0; i < numLPs; i++) {
				if(i == id){
					lps[i].setTimeSaveToProcess(min);					
				}else{
					lps[i].setTimeSaveToProcess(min);
				}
			}
			if (inRampUp){
				double maxClock = getMaximumClockOfAllLP();
				if(maxClock > rampUpLength){
					for (int i = 0; i < numLPs; i++) {
						lps[i].setClock(maxClock);
					}
					System.out.println("END RAMP "+ maxClock);
					inRampUp = false;
					for(LP lp: lps){
						try {
							lp.startDataCollection(maxClock);
						} catch (SimQPNException e) {
							e.printStackTrace();
						}
					}
				}
			} else{
				double maxClock = getMaximumClockOfAllLP();
				if (maxClock > totRunLength){
					for(LP lp:lps){
						lp.setTimeSaveToProcess(maxClock);
					}
//					for(int i=0; i<100; i++){
//						for(LP lp:lps){
//							try {
//								lp.processSaveEventsWithPrecissionCheck();
//							} catch (SimQPNException e) {
//								e.printStackTrace();
//							}
//						}						
//					}
					finishSimulation();
				}
			}

//			for (int i = 0; i < numLPs; i++) {
//				final LP lp = lps[i];
//				if (lp.hasQueueEvent()) {
//					for(LP suc: lp.getSuccessorList()){
//						if(suc != lp){
//							suc.setTimeSaveToProcessAdvanced(lp.getClock());//+ lp.getLookahead());							
//						}else{
//							lp.setTimeSaveToProcess(lp.getClock() + lp.getLookahead());
//						}
//					}
//				}
//			}

			if (verbosityLevel > 1) {
				for (int i = 0; i < numLPs; i++) {
					System.out.println("LP" + lps[i].getId() + " clock"
							+ lps[i].getClock() + " | save"
							+ lps[i].getCurrentTimeSaveToProcess() + " next"
							+ lps[i].getNextEventTime() + " look"
							+ lps[i].getLookahead());
				}
			}

			if (verbosityLevel > 0) {
				System.out
						.println("------------------- barrier ---------------");
			}
		} else {
			System.out.println("ALL LPs collected enough data");
			finishSimulation();

		}
	}

	private void finishSimulation() {
		double maxClock = getMaximumClockOfAllLP();
		for (LP lp : lps) {
			lp.finish(maxClock);
		}
		stopController.finishSimulation();
	}

	private double getMaximumClockOfAllLP() {
		double maxClock = 0;
		for(LP lp: lps){
			if(lp.getClock() > maxClock){
				maxClock = lp.getClock();
			}
		}
		return maxClock;
	}

	/**
	 * @param threads
	 *            the threads to set
	 */
	public void setThreads(Thread[] threads) {
		this.threads = threads;
	}

}
