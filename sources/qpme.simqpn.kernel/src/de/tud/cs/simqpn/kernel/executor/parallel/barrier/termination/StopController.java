package de.tud.cs.simqpn.kernel.executor.parallel.barrier.termination;

public interface StopController {

	public boolean isReadyToFinish();
	public void incrementReadyToFinishLPCounter();
	public void setReadyToFinish();
	
	public void finishSimulation();
	public boolean hasFinished();
}
