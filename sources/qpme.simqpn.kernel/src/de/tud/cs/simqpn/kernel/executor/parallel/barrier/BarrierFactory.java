package de.tud.cs.simqpn.kernel.executor.parallel.barrier;

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.executor.parallel.LP;
import de.tud.cs.simqpn.kernel.executor.parallel.barrier.action.BarrierAction;
import de.tud.cs.simqpn.kernel.executor.parallel.barrier.action.MinBarrierAction;
import de.tud.cs.simqpn.kernel.executor.parallel.barrier.action.PredecessorBarrierAction;
import de.tud.cs.simqpn.kernel.executor.parallel.barrier.action.PredecessorClockBarrierAction;
import edu.bonn.cs.net.jbarrier.barrier.Barrier;
import edu.bonn.cs.net.jbarrier.barrier.ButterflyBarrier;
import edu.bonn.cs.net.jbarrier.barrier.CentralBarrier;

public class BarrierFactory {
	private static Logger log = Logger.getLogger(BarrierFactory.class);
	
	public static Barrier createBarrier(LP[] lps, int verbosityLevel){
		Barrier barrier = null;
		BarrierAction barrierAction = BarrierFactory.createBarrierAction(lps, verbosityLevel);
		if (lps.length == 0){
			log.error("Number of LPs is 0. Number of LPs has to be larger than one.");
		}else if (lps.length == 1) {
			log.error("Number of LPs is 1. Number of LPs has to be larger than one.");
		}else if (isPowerOfTwo(lps.length) && lps.length > 2) {
			barrier = new ButterflyBarrier(lps.length, barrierAction);
		} else {
			barrier = new CentralBarrier(lps.length, barrierAction);
		}
		for (LP lp : lps) {
			lp.setBarrier(barrier);
			lp.setStopController(barrierAction.getStopController());
		}
		return barrier;
	}
	
	
	public static BarrierAction createBarrierAction(LP[] lps, int verbosityLevel){
		return new PredecessorClockBarrierAction(lps, verbosityLevel);
	}
	
	public static BarrierAction createPredecessorBarrierAction(LP[] lps, int verbosityLevel){
		return new PredecessorBarrierAction(lps, verbosityLevel);
	}
	
	public static BarrierAction createPredecessorClockBarrierAction(LP[] lps, int verbosityLevel){
		return new PredecessorClockBarrierAction(lps, verbosityLevel);
	}

	public static BarrierAction createMinBarrierAction(LP[] lps, int verbosityLevel){
		return new MinBarrierAction(lps, verbosityLevel);
	}

	private static boolean isPowerOfTwo(int number) {
		return (number & -number) == number;
	}

}
