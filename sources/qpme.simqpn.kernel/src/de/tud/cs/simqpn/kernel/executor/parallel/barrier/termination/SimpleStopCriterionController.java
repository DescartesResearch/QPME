package de.tud.cs.simqpn.kernel.executor.parallel.barrier.termination;

/**
 * This class collects the reaching of local stop criteria to build a global stop criterion
 */
public class SimpleStopCriterionController implements StopController{
	
	private int numLPs;
	private int finishedLPs;
	private boolean hasFinished;
	
	/**
	 * Constructor
	 * @param numLPs The number of LPs that have to reach their local stop criterion
	 * @param barrier The barrier to be unlocked if simulation finished
	 */
	public SimpleStopCriterionController(int numLPs) {
		this.numLPs = numLPs;
		this.finishedLPs = 0;
		this.hasFinished = false;
	}
	
	/**
	 * Returns if all LPs reached their local stop criterion
	 * @return 
	 */
	public boolean hasSimulationFinished(){
		return hasFinished;
	}
	
	/**
	 * Increments the counter for finished LPs
	 */
	public synchronized void incrementFinishedLPCounter(){
		finishedLPs++;
//		hasFinished = true;
		if(numLPs <= finishedLPs){
			hasFinished = true;
		}
	}
	
	public int getNumFinishedLPs(){
		return finishedLPs;
	}
	
	@Override
	public void finishSimulation() {
		hasFinished = true;
	}


}
