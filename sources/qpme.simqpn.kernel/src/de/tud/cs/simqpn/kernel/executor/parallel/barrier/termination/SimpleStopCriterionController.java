package de.tud.cs.simqpn.kernel.executor.parallel.barrier.termination;

import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;

/**
 * This class collects the reaching of local stop criteria to build a global stop criterion
 */
public class SimpleStopCriterionController implements StopController{
	
	private final int numLPs;
	private int readyTofinishLPs;
	private boolean hasFinished;
	private final SimulatorProgress progressMonitor;
	private boolean isReadyToFinish;
	
	/**
	 * Constructor
	 * @param numLPs The number of LPs that have to reach their local stop criterion
	 * @param barrier The barrier to be unlocked if simulation finished
	 */
	public SimpleStopCriterionController(int numLPs, SimulatorProgress progressMonitor) {
		this.progressMonitor = progressMonitor;
		this.numLPs = numLPs;
		this.readyTofinishLPs = 0;
		this.hasFinished = false;
		this.isReadyToFinish = false;
	}
	
	/**
	 * Returns if all LPs reached their local stop criterion
	 * @return 
	 */
	public boolean isReadyToFinish(){
		if(progressMonitor != null){
			if(progressMonitor.isCanceled()){
				isReadyToFinish = true;
			}
		}
		return isReadyToFinish;
	}
	
	/**
	 * Increments the counter for finished LPs
	 */
	@Override
	public synchronized void incrementReadyToFinishLPCounter(){
		readyTofinishLPs++;
		if(numLPs <= readyTofinishLPs){
			isReadyToFinish = true;
		}
	}
	
	@Override
	public synchronized void finishSimulation() {
		hasFinished = true;
	}
	
	public boolean hasFinished() {
		return hasFinished;
	}

	@Override
	public void setReadyToFinish() {
		isReadyToFinish = true;		
	}


}
