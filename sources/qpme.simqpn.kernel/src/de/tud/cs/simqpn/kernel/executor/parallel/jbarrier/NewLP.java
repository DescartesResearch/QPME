package de.tud.cs.simqpn.kernel.executor.parallel.jbarrier;

import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.executor.parallel.LP;
import de.tud.cs.simqpn.kernel.executor.parallel.termination.StopController;

public class NewLP implements Runnable{
	
	LP lp;
	StopController stopCriterion;

	public NewLP(LP lp, StopController stopCriterion) {
		this.lp = lp;
		this.stopCriterion = stopCriterion;
	}

	@Override
	public void run() {
		try {
			lp.initializeWorkingVariables();
			while(!stopCriterion.hasSimulationFinished()){
				lp.processSaveEvents();
				lp.waitForBarrier();
			}
		} catch (SimQPNException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
