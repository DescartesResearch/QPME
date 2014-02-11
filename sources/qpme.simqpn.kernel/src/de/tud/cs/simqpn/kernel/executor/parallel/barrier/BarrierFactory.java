package de.tud.cs.simqpn.kernel.executor.parallel.barrier;

import de.tud.cs.simqpn.kernel.executor.parallel.LP;
import de.tud.cs.simqpn.kernel.executor.parallel.barrier.action.BarrierAction;
import de.tud.cs.simqpn.kernel.executor.parallel.barrier.action.MinBarrierAction;
import de.tud.cs.simqpn.kernel.executor.parallel.barrier.action.PredecessorBarrierAction;
import de.tud.cs.simqpn.kernel.executor.parallel.barrier.action.PredecessorClockBarrierAction;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;
import edu.bonn.cs.net.jbarrier.barrier.Barrier;
import edu.bonn.cs.net.jbarrier.barrier.ButterflyBarrier;
import edu.bonn.cs.net.jbarrier.barrier.CentralBarrier;

public class BarrierFactory {
	
	public static Barrier createBarrier(LP[] lps, int verbosityLevel, SimulatorProgress progressMonitor){
		Barrier barrier = null;
		BarrierAction barrierAction = BarrierFactory.createBarrierAction(lps, verbosityLevel, progressMonitor);

		if (isPowerOfTwo(lps.length) && lps.length > 2) {
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
	
	
	public static BarrierAction createBarrierAction(LP[] lps, int verbosityLevel, SimulatorProgress progressMonitor){
		return new PredecessorClockBarrierAction(lps, verbosityLevel, progressMonitor);
	}
	
	public static BarrierAction createPredecessorBarrierAction(LP[] lps, int verbosityLevel, SimulatorProgress progressMonitor){
		return new PredecessorBarrierAction(lps, verbosityLevel, progressMonitor);
	}
	
	public static BarrierAction createPredecessorClockBarrierAction(LP[] lps, int verbosityLevel, SimulatorProgress progressMonitor){
		return new PredecessorClockBarrierAction(lps, verbosityLevel, progressMonitor);
	}

	public static BarrierAction createMinBarrierAction(LP[] lps, int verbosityLevel, SimulatorProgress progressMonitor){
		return new MinBarrierAction(lps, verbosityLevel, progressMonitor);
	}

	private static boolean isPowerOfTwo(int number) {
		return (number & -number) == number;
	}

}
