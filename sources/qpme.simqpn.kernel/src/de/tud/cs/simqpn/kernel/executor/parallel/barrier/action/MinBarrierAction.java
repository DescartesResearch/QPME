package de.tud.cs.simqpn.kernel.executor.parallel.barrier.action;

import de.tud.cs.simqpn.kernel.executor.parallel.LP;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;

public class MinBarrierAction extends BarrierAction {

	@Deprecated
	public MinBarrierAction(LP[] lps, int verbosityLevel,SimulatorProgress progressMonitor) {
		super(lps, verbosityLevel, progressMonitor);
	}

	@Override
	void setTimesSaveToProcess() {
		double min = Double.MAX_VALUE;
		for (int i = 0; i < numlps; i++) {
			final LP lp = lps[i];
			if (!lp.hasSuccessor()) {
				continue;
			}
			double time = lp.getNextEventTime();
			if (min > time && time != 0.0) {
				min = time;
			}
		}

		for (int i = 0; i < numlps; i++) {
				lps[i].setTimeSaveToProcess(min);
		}
	}

	@Override
	void setTimeSaveToProcess(LP lp) {
		// TODO Auto-generated method stub		
	}

}
