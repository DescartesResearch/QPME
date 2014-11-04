/* ==============================================
 * QPME : Queueing Petri net Modeling Environment
 * ==============================================
 *
 * (c) Copyright 2003-2011, by Samuel Kounev and Contributors.
 * 
 * Project Info:   http://descartes.ipd.kit.edu/projects/qpme/
 *                 http://www.descartes-research.net/
 *    
 * All rights reserved. This software is made available under the terms of the 
 * Eclipse Public License (EPL) v1.0 as published by the Eclipse Foundation
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * This software is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the Eclipse Public License (EPL)
 * for more details.
 *
 * You should have received a copy of the Eclipse Public License (EPL)
 * along with this software; if not visit http://www.eclipse.org or write to
 * Eclipse Foundation, Inc., 308 SW First Avenue, Suite 110, Portland, 97204 USA
 * Email: license (at) eclipse.org 
 *  
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *                                
 * =============================================
 *
 * Original Author(s):  Jürgen Walter
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2014/02/04  Jürgen Walter     Created
 * 
 */
package de.tud.cs.simqpn.kernel.executor.parallel.barrier.action;

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.executor.parallel.LP;
import de.tud.cs.simqpn.kernel.executor.parallel.barrier.termination.SimpleStopCriterionController;
import de.tud.cs.simqpn.kernel.executor.parallel.barrier.termination.StopController;
import de.tud.cs.simqpn.kernel.executor.parallel.decomposition.RecursionDepthCalculator;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;

public abstract class BarrierAction implements Runnable {

	private static final Logger log = Logger.getLogger(BarrierAction.class);

	private final StopController stopController;
	protected final LP[] lps;
	protected final int numlps;
	private final double rampUpLength;
	private final int recursionDepth;
	private final int verbosityLevel;
	private boolean inRampUp;
	private double endRampUpClock;
	private int consistencyCounter;
	private SimulatorProgress progressMonitor;
	private double endRunClock = 0;

	public BarrierAction(LP[] lps, int verbosityLevel,
			SimulatorProgress progressMonitor) {
		this.stopController = new SimpleStopCriterionController(lps.length,
				progressMonitor);
		this.lps = lps;
		this.numlps = lps.length;
		this.rampUpLength = lps[0].getRampUpLength();
		this.recursionDepth = RecursionDepthCalculator.getRecursionDepth(lps);
		this.verbosityLevel = verbosityLevel;
		this.inRampUp = true;
		this.progressMonitor = progressMonitor;
		this.consistencyCounter = 0;
		this.endRunClock = 0;
	}
	
	@Override
	public void run() {
		if (stopController.isReadyToFinish()) {
			makeConsistentAndFinish();
		} else if (inRampUp) {
			if (consistencyCounter == 0) {
				double clock = getMaximumClockOfAllLP();
				if (clock > rampUpLength) {
					endRampUpClock = clock;
					consistencyCounter++;
				} else {
					setTimesSaveToProcess();
				}
			} else if (consistencyCounter > recursionDepth) {
				log.info("RampUp done at " + endRampUpClock);
				startDataCollection(endRampUpClock);
				inRampUp = false;
				consistencyCounter = 0;
				for(LP lp: lps){
					if(lp.isWorkloadGenerator()){
						lp.setTimeSaveToProcess(Double.MAX_VALUE);
					}
				}
				setTimesSaveToProcess();
			} else {
				setTimesSaveToProcessTowardsConsistency(endRampUpClock);
			}
		} else {
			setTimesSaveToProcess();
		}
	}

	public StopController getStopController() {
		return stopController;
	}
	
	abstract void setTimeSaveToProcess(LP lp);
	
	void setTimesSaveToProcess() {
		if (verbosityLevel > 1) {
			log.info("------- Barrier --------");
		}
		for(LP lp:lps){
			setTimeSaveToProcess(lp);
		}
	};
	
	private void makeConsistentAndFinish() {
		 endRunClock = (endRunClock == 0) ? getMaximumClockOfAllLP(): endRunClock;
		
		if (consistencyCounter >= recursionDepth) {
			finishSimulation(endRunClock);
		} else {
			setTimesSaveToProcessTowardsConsistency(endRunClock);
		}
	}

	private void finishSimulation(double endRunClock) {
		for (LP lp : lps) {
			lp.setClock(endRunClock);
		}
		stopController.finishSimulation();

		progressMonitor.updateSimulationProgress(-1, 100, 0,
				lps[0].getConfiguration(), inRampUp);

		if (progressMonitor.isCanceled()) {
			progressMonitor
					.warning(
							0,
							": The simulation was canceled by the user." // \n
									+ "The required precision may not have been reached!");
		} else {
			if (endRunClock >= lps[0].getTotRunLength()) {
				if (lps[0].getConfiguration().stoppingRule != SimQPNConfiguration.FIXEDLEN) {
					progressMonitor
							.warning(
									-1,
									"The simulation was stopped because of reaching max totalRunLen." // \n
											+ "The required precision may not have been reached!");
				} else {
					log.info("STOPPING because max totalRunLen is reached!");
				}
			}
		}
	}

	private boolean setTimesSaveToProcessTowardsConsistency(double clock) {
		consistencyCounter++;
		for (LP lp : lps) {
			if (lp.isWorkloadGenerator()) {
				lp.setTimeSaveToProcess(clock);
			} else {
				setTimeSaveToProcess(lp);
			}
		}
		return true;
	}

	private void startDataCollection(double endRampUpClock) {
		for (LP lp : lps) {
			lp.startDataCollection(endRampUpClock);
		}
	}

	private double getMaximumClockOfAllLP() {
		double maxClock = 0;
		for (LP lp : lps) {
			if (lp.getClock() > maxClock) {
				maxClock = lp.getClock();
			}
		}
		return maxClock;
	}

}
