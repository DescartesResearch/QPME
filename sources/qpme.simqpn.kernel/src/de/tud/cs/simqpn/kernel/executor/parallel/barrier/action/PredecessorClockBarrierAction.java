package de.tud.cs.simqpn.kernel.executor.parallel.barrier.action;

import de.tud.cs.simqpn.kernel.executor.parallel.LP;

public class PredecessorClockBarrierAction extends BarrierAction {

	public PredecessorClockBarrierAction(LP[] lps, int verbosityLevel) {
		super(lps, verbosityLevel);
	}

	@Override
	void setTimesSaveToProcess() {
		for (int i = 0; i < numlps; i++) {
			setLookahead(lps[i]);
		}

	}

	@Override
	void setLookahead(LP lp) {
		if (lp.getInPlaces().length == 0) {
			lp.setTimeSaveToProcess(10*lp.getNextEventTime() - 9*lp.getClock());;
		}else{
			double time;
			double min = Double.MAX_VALUE;
			for(LP pred : lp.getPredecessors()){
				time = pred.getClock();
				if (time < min && time != 0.0) {
					min = time;
				}
			}
			lp.setTimeSaveToProcess(min);		
			
		}
	}

}
