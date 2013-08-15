package de.tud.cs.simqpn.kernel.executor.parallel.jbarrier;

import java.util.concurrent.CyclicBarrier;

/**
 * This class collects the reaching of local stop criteria to build a global stop criterion
 */
public class SimpleStopCriterionController {
	
	private int numLPs;
	private int finishedLPs;

	/**
	 * Constructor
	 * @param numLPs The number of LPs that have to reach their local stop criterion
	 * @param barrier The barrier to be unlocked if simulation finished
	 */
	public SimpleStopCriterionController(int numLPs) {
		this.numLPs = numLPs;
		this.finishedLPs = 0;
	}
	
	/**
	 * Returns if all LPs reached their local stop criterion
	 * @return 
	 */
	boolean hasSimulationFinished(){
		if(numLPs <= finishedLPs){
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
