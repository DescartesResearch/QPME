package de.tud.cs.simqpn.kernel.executor.parallel;

import java.util.concurrent.CyclicBarrier;

public class StopCriterion {
	
	int numLPs;
	int finishedLPs;
	CyclicBarrier barrier;
	public StopCriterion(int numLPs, CyclicBarrier barrier) {
		this.numLPs = numLPs;
		this.finishedLPs = 0;
		this.barrier = barrier;
	}
	
	boolean hasSimulationFinished(){
		if(numLPs <= finishedLPs){
			barrier.reset();
			return true;
		}
		return false;
	}
	
	synchronized void notifyFinishedLP(){
		finishedLPs++;
	}
	

}
