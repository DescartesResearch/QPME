package de.tud.cs.simqpn.kernel.executor.parallel.jbarrier;

import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.executor.parallel.LP;

public class NewLP implements Runnable{
	
	LP lp;

	public NewLP(LP lp) {
		this.lp = lp;
	}

	@Override
	public void run() {
		try {
			lp.initializeWorkingVariables();
			while(true){
				lp.processSaveEvents();
				lp.waitForBarrier();
			}
		} catch (SimQPNException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
