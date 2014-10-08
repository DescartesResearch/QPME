package de.tud.cs.simqpn.kernel.executor.parallel;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.executor.parallel.barrier.BarrierFactory;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;

public class JBarrierExecutor implements Callable<Net> {

	private static Logger log = Logger.getLogger(JBarrierExecutor.class);
	private Net net;
	private SimulatorProgress progressMonitor;
	private final int verbosityLevel;
	private final LP[] lps;

	/**
	 * Constructor
	 * 
	 * @param net
	 * @param configuration
	 * @param progressMonitor
	 * @param runID
	 */
	public JBarrierExecutor(LP[] lps, SimQPNConfiguration configuration,
			SimulatorProgress progressMonitor, int verbosityLevel) {
		this.lps = lps;
		this.progressMonitor = progressMonitor;
		this.verbosityLevel = verbosityLevel;
	}

	/**
	 * Simulates the passed net.
	 * 
	 * @throws SimQPNException
	 */
	public Net call() throws SimQPNException {

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
		final LP lp;
		
		public InternalLP(final LP lp) {
			this.lp = lp;
		}

		@Override
		public void run() {
			try {
				lp.initializeWorkingVariables();
				lp.waitForBarrier();
				while (!lp.getStopController().hasFinished()) {
					lp.processSaveEvents();
					lp.checkForPrecission();
					lp.waitForBarrier();
				}
				lp.finish();
			} catch (SimQPNException e) {
				log.error(e);
			}
		}
	}
	
}
