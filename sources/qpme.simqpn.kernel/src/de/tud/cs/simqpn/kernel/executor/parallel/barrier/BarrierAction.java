package de.tud.cs.simqpn.kernel.executor.parallel.barrier;

import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.executor.parallel.LP;
import de.tud.cs.simqpn.kernel.executor.parallel.barrier.termination.SimpleStopCriterionController;
import de.tud.cs.simqpn.kernel.executor.parallel.barrier.termination.StopController;

public abstract class BarrierAction implements Runnable {

	private final StopController stopController;
	protected final LP[] lps;
	protected final int numlps;
	private final double rampUpLength;
	private final double totRunLength;
	private boolean inRampUp;
	
	public BarrierAction(LP[] lps, int verbosityLevel) {
		this.stopController = new SimpleStopCriterionController(lps.length);
		this.lps = lps;
		this.numlps = lps.length;
		this.rampUpLength = lps[0].getRampUpLength();
		this.totRunLength = lps[0].getTotRunLength();
	}
	
	abstract void setTimesSaveToProcess();
	abstract void setLookahead(LP lp);

	@Override
	public void run() {
		if (!stopController.hasSimulationFinished()) {
			setTimesSaveToProcess();
			
			if (inRampUp) {
				startDataCollectionIfRampUpFinished();
			} else {
				double maxClock = getMaximumClockOfAllLP();
				if (maxClock > totRunLength) {
					for (LP lp : lps) {
						lp.setTimeSaveToProcess(maxClock);
					}
					finishSimulation();
				}
			}
		} else {
			finishSimulation();

		}
	}

	private void startDataCollectionIfRampUpFinished() {
		double maxClock = getMaximumClockOfAllLP();
		if (maxClock > rampUpLength) {
			for (int i = 0; i < numlps; i++) {
				setLookahead(lps[i]);
				lps[i].setClock(maxClock);
			}
			System.out.println("END RAMP " + maxClock);
			inRampUp = false;
			for (LP lp : lps) {
				try {
					lp.startDataCollection(maxClock);
				} catch (SimQPNException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
	private void finishSimulation() {
		double maxClock = getMaximumClockOfAllLP();
		for (LP lp : lps) {
			lp.finish(maxClock);
		}
		getStopController().finishSimulation();
	}

	private double getMaximumClockOfAllLP() {
		double maxClock = 0;
		for (LP lp : lps) {
			if (lp.getClock() > maxClock) {
				maxClock = lp.getClock();
			}
		}
		return maxClock;
	}

	public StopController getStopController() {
		return stopController;
	}
}
