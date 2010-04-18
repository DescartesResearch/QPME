package de.tud.cs.simqpn.kernel;

public interface SimulatorProgress {

	public boolean isCanceled();

	public double getCheckInterval(double totRunLength);

	public void startSimulation(int analysisMethod, int stoppingCriteria, int numRuns);

	public void startSimulationRun(int number);

	public void finishWarmUp();

	public void updateSimulationProgress(int progress);

	public void finishSimulationRun();

	public void finishSimulation();

	public void startPrecisionCheck(int numTotalColors);

	public void updatePrecisionCheckProgress(boolean passed, int passedColors);

	public void finishPrecisionCheck(boolean done, String failedPlaceName);

}
