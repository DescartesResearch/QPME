package de.tud.cs.simqpn.kernel.executor.parallel;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.executor.parallel.barrier.BarrierFactory;
import de.tud.cs.simqpn.kernel.executor.parallel.decomposition.NetDecomposer;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;

public class JBarrierExecutor implements Callable<Net> {

	private static Logger log = Logger.getLogger(JBarrierExecutor.class);
	private Net net;
	private SimQPNConfiguration configuration;
	private SimulatorProgress progressMonitor;
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
			SimulatorProgress progressMonitor, int verbosityLevel) {
		this.net = net;
		this.configuration = configuration;
		this.progressMonitor = progressMonitor;
		this.verbosityLevel = verbosityLevel;
	}

	/**
	 * Simulates the passed net.
	 * 
	 * @throws SimQPNException
	 */
	public Net call() throws SimQPNException {
		log.warn("No guaranties. Parallel simulation is still experimental and only applicable to open workloads.");
		LP[] lps = NetDecomposer.decomposeNetIntoLPs(net, configuration,
				progressMonitor, verbosityLevel);
		if(!NetDecomposer.hasDecompositionSucceded(lps, verbosityLevel)){
			return null;
		}
		
		BarrierFactory.createBarrier(lps, verbosityLevel, progressMonitor);
		
		Thread[] threads = new Thread[lps.length];
		//RandomElement randomElement = RandomNumberGenerator.nextRandNumGen();
		for (int i = 0; i < lps.length; i++) {
			InternalLP newLP = new InternalLP(lps[i]);
			//lps[i].createRandomTransGen(randomElement); //random
			threads[i] = new Thread(newLP);
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

	private class InternalLP implements Runnable {
		LP lp;

		public InternalLP(final LP lp) {
			this.lp = lp;
		}

		@Override
		public void run() {
			try {
				lp.initializeWorkingVariables();
				lp.waitForBarrier();
				while (!lp.getStopController().hasSimulationFinished()) {
					lp.processSaveEventsWithPrecissionCheck();
					lp.waitForBarrier();
				}
			} catch (SimQPNException e) {
				log.error(e);
			}

		}
	}
	
}
