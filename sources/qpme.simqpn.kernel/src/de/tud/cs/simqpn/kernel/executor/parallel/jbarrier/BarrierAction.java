package de.tud.cs.simqpn.kernel.executor.parallel.jbarrier;

import de.tud.cs.simqpn.kernel.executor.parallel.termination.SimpleStopCriterionController;
import de.tud.cs.simqpn.kernel.executor.parallel.termination.StopController;

public class BarrierAction implements Runnable{

	StopController stopController;
	
	public BarrierAction(StopController stopController) {
		this.stopController = stopController;
	}
	
	@Override
	public void run() {
		if(!stopController.hasSimulationFinished()){
			//System.out.println("---barrier--");
		}else{
			System.out.println("---finished-- ");			
		}
	}
	

}
