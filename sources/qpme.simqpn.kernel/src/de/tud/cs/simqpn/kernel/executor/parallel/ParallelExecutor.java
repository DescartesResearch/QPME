package de.tud.cs.simqpn.kernel.executor.parallel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.Transition;
import de.tud.cs.simqpn.kernel.entities.queue.Queue;
import de.tud.cs.simqpn.kernel.executor.parallel.termination.StopCriterionController;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;
import edu.bonn.cs.net.jbarrier.barrier.Barrier;
import edu.bonn.cs.net.jbarrier.barrier.ButterflyBarrier;
import edu.bonn.cs.net.jbarrier.barrier.CentralBarrier;

/**
 * This class decomposes a {@link Net} and runs its parts parallel by the use of
 * {@link LP}s
 * 
 * @author D
 * 
 */
public class ParallelExecutor implements Callable<Net> {
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
	public ParallelExecutor(Net net, SimQPNConfiguration configuration,
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
		NetDecomposer decomposer = new NetDecomposer(net, configuration, progressMonitor, verbosityLevel);
		LP[] lps = decomposer.decomposeNetIntoLPs();
		System.out.println(NetDecomposer.lpDecompositionToString(lps));

		Barrier barrier = new CentralBarrier(lps.length);
		StopCriterionController stopCriterion = null;
//				new StopCriterionController(
//				lps.length, barrier);
		for (LP lp : lps) {
			lp.setBarrier(barrier);
			lp.setStopCriterion(stopCriterion);
		}

		Thread[] threads = new Thread[lps.length];
		for (int i = 0; i < lps.length; i++) {
			threads[i] = new Thread(lps[i]);
		}
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
