package de.tud.cs.simqpn.kernel.executor.parallel.barrier;

import de.tud.cs.simqpn.kernel.executor.parallel.LP;

public class PredecessorBarrierAction extends BarrierAction {

	public PredecessorBarrierAction(LP[] lps, int verbosityLevel) {
		super(lps, verbosityLevel);
	}

	@Override
	void setTimesSaveToProcess() {
		for (int i = 0; i < numlps; i++) {
			final LP lp = lps[i];
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

	@Override
	void setLookahead(LP lp) {
		// TODO Auto-generated method stub
		
	}

}
