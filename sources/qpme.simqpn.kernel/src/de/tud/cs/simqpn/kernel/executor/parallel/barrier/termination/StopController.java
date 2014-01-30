package de.tud.cs.simqpn.kernel.executor.parallel.barrier.termination;

public interface StopController {

	public boolean hasSimulationFinished();
	public void incrementFinishedLPCounter();
	public void finishSimulation();

}
