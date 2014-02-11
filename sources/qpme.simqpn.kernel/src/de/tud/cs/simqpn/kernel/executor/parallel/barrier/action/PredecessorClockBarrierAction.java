package de.tud.cs.simqpn.kernel.executor.parallel.barrier.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import de.tud.cs.simqpn.kernel.executor.parallel.LP;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;

public class PredecessorClockBarrierAction extends BarrierAction {

	public PredecessorClockBarrierAction(LP[] lps, int verbosityLevel, SimulatorProgress progressMonitor) {
		super(lps, verbosityLevel, progressMonitor);
	}

	@Override
	void setTimesSaveToProcess() {
		super.setTimesSaveToProcess();
		for (int i = 0; i < numlps; i++) {
			setLookahead(lps[i]);
		}

	}

	@Override
	void setLookahead(LP lp) {
		if (lp.getInPlaces().length == 0) {
			//lp.setTimeSaveToProcess(5*lp.getNextQueueEventTime() - 4*lp.getClock());;
			lp.setTimeSaveToProcess(lp.getLastQueueEventTime());
		}else{
			Collection<Double> predecessorClockValues= new ArrayList<Double>();
			for(LP pred : lp.getPredecessors()){
				predecessorClockValues.add(pred.getClock());
			}
			lp.setTimeSaveToProcess(Collections.min(predecessorClockValues));		
			
		}
	}

}
