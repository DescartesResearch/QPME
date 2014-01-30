package de.tud.cs.simqpn.kernel.executor.parallel.barrier;

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.executor.parallel.LP;
import edu.bonn.cs.net.jbarrier.barrier.Barrier;
import edu.bonn.cs.net.jbarrier.barrier.ButterflyBarrier;
import edu.bonn.cs.net.jbarrier.barrier.CentralBarrier;

public class BarrierUtil {
	private static Logger log = Logger.getLogger(BarrierUtil.class);
	Barrier barrier;
	
	public static Barrier createAndInitBarrier(LP[] lps, int verbosityLevel){
		Barrier barrier = null;
		LookaheadMinReductionBarrierAction barrierAction = new LookaheadMinReductionBarrierAction(
				lps, verbosityLevel);
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
	
	private static boolean isPowerOfTwo(int number) {
		return (number & -number) == number;
	}

}
