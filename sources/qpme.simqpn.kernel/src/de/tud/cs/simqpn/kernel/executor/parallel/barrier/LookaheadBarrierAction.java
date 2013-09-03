package de.tud.cs.simqpn.kernel.executor.parallel.barrier;

import de.tud.cs.simqpn.kernel.executor.parallel.LP;
import de.tud.cs.simqpn.kernel.executor.parallel.termination.StopController;

public class LookaheadBarrierAction implements Runnable {

	StopController stopController;
	LP[] lps;
	private Thread[] threads;
	final int verbosityLevel;
	public LookaheadBarrierAction(StopController stopController, LP[] lps,
			int verbosityLevel) {
		this.stopController = stopController;
		this.lps = lps;
		this.verbosityLevel = verbosityLevel;
	}

	@Override
	public void run() {
		if (!stopController.hasSimulationFinished()) {
			 for(LP lp: lps){
				 lp.actualizeTimeSaveToProcess();
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
