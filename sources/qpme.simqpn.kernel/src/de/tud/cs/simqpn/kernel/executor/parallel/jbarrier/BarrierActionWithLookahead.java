package de.tud.cs.simqpn.kernel.executor.parallel.jbarrier;

import de.tud.cs.simqpn.kernel.executor.parallel.LP;
import de.tud.cs.simqpn.kernel.executor.parallel.termination.StopController;

public class BarrierActionWithLookahead implements Runnable{

	StopController stopController;
	LP[] lps;
	private Thread[] threads;
	
	public BarrierActionWithLookahead(StopController stopController, LP[] lps) {
		this.stopController = stopController;
		this.lps = lps;
	}
	
	@Override
	public void run() {
		if(!stopController.hasSimulationFinished()){
			for(LP lp: lps){
				lp.actualizeTimeSaveToProcess();
			}
		}else{
			for(Thread thread: threads){
				thread.interrupt();
			}
			for(LP lp: lps){
				lp.finish();
			}
		}
	}

	/**
	 * @param threads the threads to set
	 */
	public void setThreads(Thread[] threads) {
		this.threads = threads;
	}
	

}
