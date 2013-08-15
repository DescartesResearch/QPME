package de.tud.cs.simqpn.kernel.executor.parallel.jbarrier;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.executor.parallel.LP;
import de.tud.cs.simqpn.kernel.executor.parallel.NetDecomposer;
import de.tud.cs.simqpn.kernel.executor.parallel.ParallelExecutor;
import de.tud.cs.simqpn.kernel.executor.parallel.termination.SimpleStopCriterionController;
import de.tud.cs.simqpn.kernel.executor.parallel.termination.StopController;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;
import edu.bonn.cs.net.jbarrier.barrier.Barrier;
import edu.bonn.cs.net.jbarrier.barrier.CentralBarrier;

public class JBarrierExecutor implements Callable<Net> {
	private static Logger log = Logger.getLogger(ParallelExecutor.class);

	private Net net;
	private SimQPNConfiguration configuration;
	private SimulatorProgress progressMonitor;
	private int runID;

	/**
	 * Constructor
	 * 
	 * @param net
	 * @param configuration
	 * @param progressMonitor
	 * @param runID
	 */
	public JBarrierExecutor(Net net, SimQPNConfiguration configuration,
			SimulatorProgress progressMonitor, int runID) {
		this.net = net;
		this.configuration = configuration;
		this.progressMonitor = progressMonitor;
		this.runID = runID;
	}

	/**
	 * Simulates the passed net.
	 * 
	 * @throws SimQPNException
	 */
	// Modifies net
	public Net call() throws SimQPNException {
		int verbosityLevel = 0;
		NetDecomposer decomposer = new NetDecomposer(net, configuration,
				progressMonitor, verbosityLevel);
		LP[] lps = decomposer.decomposeNetIntoLPs();
		System.out.println(NetDecomposer.lpDecompositionToString(lps));
		StopController stopCriterion = new SimpleStopCriterionController(
				lps.length);

		BarrierActionWithLookahead barrierAction = new BarrierActionWithLookahead(stopCriterion, lps, verbosityLevel);
		Barrier barrier = new CentralBarrier(lps.length, barrierAction);// ButterflyBarrier(lps.length,
																		// barrierAction);
		for (LP lp : lps) {
			lp.setBarrier(barrier);
			lp.setStopCriterion(stopCriterion);
		}

		Thread[] threads = new Thread[lps.length];
		for (int i = 0; i < lps.length; i++) {
			NewLP newLP = new NewLP(lps[i], stopCriterion);
			threads[i] = new Thread(newLP);
		}
		barrierAction.setThreads(threads);
		for (int i = 0; i < lps.length; i++) {
			threads[i].start();
		}

		for (int i = 0; i < lps.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				log.error("", e);
			}
		}

		return this.net;
	}

}
