package de.tud.cs.simqpn.kernel.executor.parallel;

import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;

public class PseudoParallelExecutor implements Callable<Net> {
	private static Logger log = Logger.getLogger(PseudoParallelExecutor.class);

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
	public PseudoParallelExecutor(Net net, SimQPNConfiguration configuration,
			SimulatorProgress progressMonitor, int runID) {
		this.net = net;
		this.configuration = configuration;
		this.progressMonitor = progressMonitor;
		this.runID = runID;
	}

	@Override
	public Net call() throws Exception {
		final int verbosityLevel = 0;
		NetDecomposer decomposer = new NetDecomposer(net, configuration, progressMonitor, verbosityLevel);
		LP[] lps = decomposer.decomposeNetIntoLPs();
		
		CyclicBarrier barrier = new CyclicBarrier(2);
		StopCriterionController stopCriterion = new StopCriterionController(
				lps.length, barrier);

		for(LP lp: lps){
			lp.setStopCriterion(stopCriterion);
			lp.initializeWorkingVariables();
		}
		System.out.println(NetDecomposer.lpDecompositionToString(lps));

		while(!stopCriterion.hasSimulationFinished()){
			for(LP lp: lps){
				lp.processSaveEvents();
			}
			for(LP lp: lps){
				lp.actualizeTimeSaveToProcess();
			}
			if(verbosityLevel>0){
				System.out.println(" ---Barrier----");
			}
		}
		for(LP lp: lps){
			lp.finish();
		}
		return net;
	}

}
