package de.tud.cs.simqpn.kernel.executor.parallel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;

public class FlexibleParallelExecutor implements Callable<Net> {
	private static Logger log = Logger.getLogger(FlexibleParallelExecutor.class);

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
	public FlexibleParallelExecutor(Net net, SimQPNConfiguration configuration,
			SimulatorProgress progressMonitor, int runID) {
		this.net = net;
		this.configuration = configuration;
		this.progressMonitor = progressMonitor;
		this.runID = runID;
	}

	class ProcessSaveEvents implements Callable<Integer> {
		LP lp;

		public ProcessSaveEvents(LP lp) {
			this.lp = lp;
		}

		public Integer call() {
			try {
				lp.processSaveEvents();
			} catch (SimQPNException e) {
				e.printStackTrace();
			}
			return 1;
		}
	}
	
	class CalculateSaveToProcess implements Callable<Integer> {
		LP lp;

		public CalculateSaveToProcess(LP lp) {
			this.lp = lp;
		}

		public Integer call() {
			lp.actualizeTimeSaveToProcess();
			return 1;
		}
	}

	@Override
	public Net call() throws Exception {
		int verbosityLevel = 0;
		NetDecomposer decomposer = new NetDecomposer(net, configuration,
				progressMonitor, verbosityLevel);
		LP[] lps = decomposer.decomposeNetIntoLPs();

		CyclicBarrier barrier = new CyclicBarrier(2);
		StopCriterionController stopCriterion = new StopCriterionController(
				lps.length, barrier);

		for (LP lp : lps) {
			lp.setStopCriterion(stopCriterion);
			lp.initializeWorkingVariables();
		}
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		List<Callable<Integer>> eventProcessingTasks = new ArrayList<Callable<Integer>>();
		for (LP lp : lps) {
			Callable<Integer> eventProcessing = new ProcessSaveEvents(lp);
			eventProcessingTasks.add(eventProcessing);
		}
		List<Callable<Integer>> aktualizeServiceTimeTasks = new ArrayList<Callable<Integer>>();
		for (LP lp : lps) {
			Callable<Integer> serviceTimeActualization = new CalculateSaveToProcess(lp);
			aktualizeServiceTimeTasks.add(serviceTimeActualization);
		}

		

		while (!stopCriterion.hasSimulationFinished()) {
			executorService.invokeAll(eventProcessingTasks);
			executorService.invokeAll(aktualizeServiceTimeTasks);
			// System.out.println(" ---Barrier----");
		}
		for (LP lp : lps) {
			lp.finish();
		}
		return net;
	}

}
