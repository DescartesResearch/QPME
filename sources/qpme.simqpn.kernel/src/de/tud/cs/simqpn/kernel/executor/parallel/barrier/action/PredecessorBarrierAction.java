package de.tud.cs.simqpn.kernel.executor.parallel.barrier.action;

import de.tud.cs.simqpn.kernel.executor.parallel.LP;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;

public class PredecessorBarrierAction extends BarrierAction {

	public PredecessorBarrierAction(LP[] lps, int verbosityLevel, SimulatorProgress progressMonitor) {
		super(lps, verbosityLevel, progressMonitor);
	}

	@Override
	void setTimesSaveToProcess() {
		for (int i = 0; i < numlps; i++) {
			setTimeSaveToProcess(lps[i]);
		}

	}

	@Override
	void setTimeSaveToProcess(LP lp) {
		double time;
		double min = Double.MAX_VALUE;
		for(LP pred : lp.getPredecessors()){
			time = pred.getNextEventTime();
			if (min > time && time != 0.0) {
				min = time;
			}
		}
		lp.setTimeSaveToProcess(min);		
	}

}
