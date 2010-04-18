package de.tud.cs.simqpn.console;

import de.tud.cs.simqpn.kernel.Simulator;
import de.tud.cs.simqpn.kernel.SimulatorProgress;

import static de.tud.cs.simqpn.kernel.Simulator.*;

public class ConsoleSimulatorProgress implements SimulatorProgress {

	private double run = 0;
	private int numRuns;
	private int analysisMethod;

	@Override
	public void startSimulation(int analysisMethod, int stoppingCriteria, int numRuns) {
		this.numRuns = numRuns;
		this.analysisMethod = analysisMethod;

		switch(analysisMethod) {
		case Simulator.BATCH_MEANS:
			logln("---------------------------------------------");
			logln(" Starting Batch Means Method");
			logln("---------------------------------------------");
			break;
		case Simulator.REPL_DEL:
			logln("---------------------------------------------");
			logln(" Starting Multiple Replications (numRuns = " + numRuns + ")");
			logln("---------------------------------------------");
			break;
		case Simulator.WELCH:
			logln("---------------------------------------------");
			logln(" Starting Method of Welch (numRuns = " + numRuns + ")");
			logln("---------------------------------------------");
			break;
		}
	}

	@Override
	public void startSimulationRun(int number) {
		logln("Simulation run " + (number + 1) + "/" + numRuns + " started.");
		run = ((double)number) / numRuns;
	}

	@Override
	public void updateSimulationProgress(int progress) {
		if(analysisMethod != REPL_DEL) {
			logln("Progress: " + progress + "%");
		}
	}

	@Override
	public void finishWarmUp() {
		if(analysisMethod != WELCH) {
			logln("Warm up finished. Starting steady state analysis...");
		}
	}

	@Override
	public void finishSimulationRun() {
		logln("Simulation run finished.");
		logln();
	}

	@Override
	public void finishSimulation() {
		logln();
		logln();
		logln("Simulation finished.");
	}

	@Override
	public boolean isCanceled() {
		return false;
	}

	@Override
	public double getCheckInterval(double totRunLength) {
		return totRunLength / 20.0;
	}

	@Override
	public void finishPrecisionCheck(boolean done, String failedPlaceName) {
	}

	@Override
	public void startPrecisionCheck(int numTotalColors) {
	}

	@Override
	public void updatePrecisionCheckProgress(boolean passed, int passedColors) {
	}
}
