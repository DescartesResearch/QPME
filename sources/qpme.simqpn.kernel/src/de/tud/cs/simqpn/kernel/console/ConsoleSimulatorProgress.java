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
 * Original Author(s):  Samuel Kounev and Simon Spinner
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2008/04/17  Simon Spinner     Created.
 * 
 */

package de.tud.cs.simqpn.kernel.console;

import static de.tud.cs.simqpn.kernel.SimQPNConfiguration.WELCH;
import static de.tud.cs.simqpn.kernel.util.LogUtil.formatMultilineMessage;

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNController;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;

/**
 * Prints the simulation progress on the console. Used in standalone simulation mode.
 */
public class ConsoleSimulatorProgress implements SimulatorProgress {
	
	private static Logger log = Logger.getLogger(ConsoleSimulatorProgress.class);

	private int numRuns;
	
	/* (non-Javadoc)
	 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#startSimulation()
	 */
	@Override
	public void startSimulation() {
		this.numRuns = (SimQPNController.configuration.getAnalMethod() == SimQPNConfiguration.BATCH_MEANS) ? 1 :SimQPNController.configuration.getNumRuns();

		switch(SimQPNController.configuration.getAnalMethod()) {
		case SimQPNConfiguration.BATCH_MEANS:
			log.info(formatMultilineMessage(
					"---------------------------------------------",
					" Starting Batch Means Method",
					"---------------------------------------------"
					));
			break;
		case SimQPNConfiguration.REPL_DEL:
			log.info(formatMultilineMessage(
					"---------------------------------------------",
					" Starting Multiple Replications (numRuns = " + numRuns + ")",
					"---------------------------------------------"
					));
			break;
		case SimQPNConfiguration.WELCH:
			log.info(formatMultilineMessage(
					"---------------------------------------------",
					" Starting Method of Welch (numRuns = " + numRuns + ")",
					"---------------------------------------------"
					));
			break;
		}
	}

	/* (non-Javadoc)
	 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#startSimulationRun(int)
	 */
	@Override
	public void startSimulationRun(int number) {
		log.info("Simulation run " + number + "/" + numRuns + " started.");
	}

	/* (non-Javadoc)
	 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#updateSimulationProgress(double, long)
	 */
	@Override
	public void updateSimulationProgress(double progress, long elapsedTime) {
		log.info("Progress: " + Math.round(progress) + "%");
	}

	/* (non-Javadoc)
	 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#finishWarmUp()
	 */
	@Override
	public void finishWarmUp() {
		if(SimQPNController.configuration.getAnalMethod() != SimQPNConfiguration.WELCH) {
			log.info("Warm up finished. Starting steady state analysis...");
		}
	}

	/* (non-Javadoc)
	 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#finishSimulationRun()
	 */
	@Override
	public void finishSimulationRun() {
		log.info("Simulation run finished.");
	}

	/* (non-Javadoc)
	 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#finishSimulation()
	 */
	@Override
	public void finishSimulation() {
		log.info("Simulation finished.");
	}

	/* (non-Javadoc)
	 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#isCanceled()
	 */
	@Override
	public boolean isCanceled() {
		return false;
	}

	/* (non-Javadoc)
	 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#getMaxUpdateLogicalTimeInterval()
	 */
	@Override
	public double getMaxUpdateLogicalTimeInterval() {
		return SimQPNController.configuration.totRunLen / 20.0;
	}
	
	/* (non-Javadoc)
	 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#getMaxUpdateRealTimeInterval()
	 */
	@Override
	public long getMaxUpdateRealTimeInterval() {
		return 60 * 1000; // 60 sec
	}

	/* (non-Javadoc)
	 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#precisionCheck(boolean, java.lang.String)
	 */
	@Override
	public void precisionCheck(boolean done, String failedPlaceName) {
	}

	/* (non-Javadoc)
	 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#warning(java.lang.String)
	 */
	@Override
	public void warning(String message) {
		log.warn(message);
	}
}
