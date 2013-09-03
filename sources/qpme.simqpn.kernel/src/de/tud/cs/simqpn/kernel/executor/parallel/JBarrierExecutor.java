package de.tud.cs.simqpn.kernel.executor.parallel;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.executor.parallel.barrier.LookaheadBarrierAction;
import de.tud.cs.simqpn.kernel.executor.parallel.barrier.LookaheadMinReductionBarrierAction;
import de.tud.cs.simqpn.kernel.executor.parallel.decomposition.NetDecomposer;
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
	private final int verbosityLevel;

	/**
	 * Constructor
	 * 
	 * @param net
	 * @param configuration
	 * @param progressMonitor
	 * @param runID
	 */
	public JBarrierExecutor(Net net, SimQPNConfiguration configuration,
			SimulatorProgress progressMonitor, int runID, int verbosityLevel) {
		this.net = net;
		this.configuration = configuration;
		this.progressMonitor = progressMonitor;
		this.runID = runID;
		this.verbosityLevel = verbosityLevel;
	}

	/**
	 * Simulates the passed net.
	 * 
	 * @throws SimQPNException
	 */
	// Modifies net
	public Net call() throws SimQPNException {
		NetDecomposer decomposer = new NetDecomposer(net, configuration,
				progressMonitor, verbosityLevel);
		LP[] lps = decomposer.decomposeNetIntoLPs();
		System.out.println(NetDecomposer.lpDecompositionToString(lps));
		StopController stopCriterion = new SimpleStopCriterionController(
				lps.length);

		LookaheadBarrierAction barrierAction = new LookaheadBarrierAction(stopCriterion, lps, verbosityLevel);
		//LookaheadMinReductionBarrierActionWith barrierAction = new LookaheadMinReductionBarrierActionWith(stopCriterion, lps, verbosityLevel);
		Barrier barrier = new CentralBarrier(lps.length, barrierAction);// ButterflyBarrier(lps.length,
																		// barrierAction);
		for (LP lp : lps) {
			lp.setBarrier(barrier);
			lp.setStopCriterion(stopCriterion);
		}
		
//		System.out.println("-><-><-");
//		for (int i=0; i<lps.length; i++) {
//			System.out.println(lps[i].getId());
//		}	
//		System.out.println("-><-><-");

		Thread[] threads = new Thread[lps.length];
		for (int i = 0; i < lps.length; i++) {
			InternalLP newLP = new InternalLP(lps[i], stopCriterion);
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
	
	private class InternalLP implements Runnable{
		
		LP lp;
		StopController stopCriterion;

		public InternalLP(LP lp, StopController stopCriterion) {
			this.lp = lp;
			this.stopCriterion = stopCriterion;
		}

		@Override
		public void run() {
			try {
				lp.initializeWorkingVariables();
				lp.waitForBarrier();
				while(!stopCriterion.hasSimulationFinished()){
					lp.processSaveEvents();
					lp.waitForBarrier();
				}
			} catch (SimQPNException e) {
				e.printStackTrace();
			}
			
		}
	}
}
