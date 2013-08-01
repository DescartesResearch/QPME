package de.tud.cs.simqpn.kernel.executor.parallel;

import java.util.concurrent.CyclicBarrier;

/**
 * This class collects the reaching of local stop criteria to build a global stop criterion
 */
public class StopCriterionController {
	
	private int numLPs;
	private int finishedLPs;
	private CyclicBarrier barrier;

	/**
	 * Constructor
	 * @param numLPs The number of LPs that have to reach their local stop criterion
	 * @param barrier The barrier to be unlocked if simulation finished
	 */
	public StopCriterionController(int numLPs, CyclicBarrier barrier) {
		this.numLPs = numLPs;
		this.finishedLPs = 0;
		this.barrier = barrier;
	}
	
	/**
	 * Returns if all LPs reached their local stop criterion
	 * @return 
	 */
	boolean hasSimulationFinished(){
		if(numLPs <= finishedLPs){
		//if(finishedLPs > 0){
		//if(finishedLPs > 1){
			barrier.reset();
			return true;
		}
		return false;
	}
	
	/**
	 * Increments the counter for finished LPs
	 */
	synchronized void incrementFinishedLPCounter(){
		finishedLPs++;
	}
	
	public int getNumFinishedLPs(){
		return finishedLPs;
	}
	

}
