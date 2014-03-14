package de.tud.cs.simqpn.kernel.executor.parallel.barrier.action;

import de.tud.cs.simqpn.kernel.executor.parallel.LP;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;

public class PredecessorClockBarrierAction extends BarrierAction {
	private final LP[] lpsWithPredecessors;

	public PredecessorClockBarrierAction(LP[] lps, int verbosityLevel,
			SimulatorProgress progressMonitor) {
		super(lps, verbosityLevel, progressMonitor);
		this.lpsWithPredecessors = getLPsWithPredecessors(lps);

	}

	@Override
	void setTimesSaveToProcess() {
		for (LP lp : lpsWithPredecessors) {
			setTimeSaveToProcess(lp);
		}

	}

	@Override
	void setTimeSaveToProcess(LP lp) {
		lp.setTimeSaveToProcess(lp.getMinimumClockOfPredecessors());
	}
	
	private static LP[] getLPsWithPredecessors(LP[] lps){
		int numberOfLPsWithPredecessors = lps.length;
		for(LP lp :lps){
			if(lp.isWorkloadGenerator()){
				numberOfLPsWithPredecessors--;
				lp.setTimeSaveToProcess(Double.MAX_VALUE);
			}
		}
		LP[] lpsWithPredecessors = new LP[numberOfLPsWithPredecessors];
		int i=0;
		for(LP lp:lps){
			if(!lp.isWorkloadGenerator()){
				lpsWithPredecessors[i++] = lp;
			}
		}
		return lpsWithPredecessors;
	}

}
