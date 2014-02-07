package de.tud.cs.simqpn.kernel.executor.parallel.barrier.action;

import de.tud.cs.simqpn.kernel.executor.parallel.LP;

public class MinBarrierAction extends BarrierAction {

	public MinBarrierAction(LP[] lps, int verbosityLevel) {
		super(lps, verbosityLevel);
	}

	@Override
	void setTimesSaveToProcess() {
		double min = Double.MAX_VALUE;
		for (int i = 0; i < numlps; i++) {
			final LP lp = lps[i];
			if (lp.sucessorIds.length == 0) {
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
	void setLookahead(LP lp) {
		// TODO Auto-generated method stub		
	}

}
