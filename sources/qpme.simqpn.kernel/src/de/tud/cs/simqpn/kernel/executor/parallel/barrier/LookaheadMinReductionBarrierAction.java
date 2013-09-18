package de.tud.cs.simqpn.kernel.executor.parallel.barrier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.tud.cs.simqpn.kernel.executor.parallel.LP;
import de.tud.cs.simqpn.kernel.executor.parallel.termination.StopController;

public class LookaheadMinReductionBarrierAction implements Runnable {

	StopController stopController;
	LP[] lps;
	private Thread[] threads;
	final int verbosityLevel;
	final int numLPs;
	private double[] clock;
	private boolean[] isInProgress;
	private boolean[] hasBeenCalculated;
	private double[] lookahead;

	public LookaheadMinReductionBarrierAction(StopController stopController,
			LP[] lps, int verbosityLevel) {
		this.stopController = stopController;
		this.lps = lps;
		this.verbosityLevel = verbosityLevel;
		this.numLPs = lps.length;
		this.clock = new double[numLPs];
		this.isInProgress = new boolean[numLPs];
		this.hasBeenCalculated = new boolean[numLPs];
		this.lookahead = new double[numLPs];
	}

	@Override
	public void run() {
		if (!stopController.hasSimulationFinished()) {
			double min = Double.MAX_VALUE;
			int id  = 0;
			for (int i = 0; i < numLPs; i++) {
				final LP lp = lps[i];
				double time = lp.getNextEventTime();
				if(min > time && time != 0.0){
					min = time;
					id = lp.getId();
				}
			}

			//lps[id].setTimeSaveToProcessAdvanced(min);
			for (int i = 0; i < numLPs; i++) {
				if(i == id){
					lps[i].setTimeSaveToProcess(min);					
				}else{
					lps[i].setTimeSaveToProcess(min);
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
			for (LP lp : lps) {
				lp.finish();
			}
		}
	}

	/**
	 * @param threads
	 *            the threads to set
	 */
	public void setThreads(Thread[] threads) {
		this.threads = threads;
	}

}
