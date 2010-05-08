/* ==============================================
 * QPME : Queueing Petri net Modeling Environment
 * ==============================================
 *
 * (c) Copyright 2003-2010, by Samuel Kounev and Contributors.
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

package de.tud.cs.simqpn.console;

import de.tud.cs.simqpn.kernel.Simulator;
import de.tud.cs.simqpn.kernel.SimulatorProgress;

import static de.tud.cs.simqpn.kernel.Simulator.*;

/**
 * Prints the simulation progress on the console. Used in standalone simulation mode.
 */
public class ConsoleSimulatorProgress implements SimulatorProgress {

	private int numRuns;
	
	/* (non-Javadoc)
	 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#startSimulation()
	 */
	@Override
	public void startSimulation() {
		this.numRuns = (Simulator.analMethod == Simulator.BATCH_MEANS) ? 1 :Simulator.numRuns;

		logln("---------------------------------------------");
		switch(Simulator.analMethod) {
		case Simulator.BATCH_MEANS:
			logln(" Starting Batch Means Method");
			break;
		case Simulator.REPL_DEL:
			logln(" Starting Multiple Replications (numRuns = " + numRuns + ")");
			break;
		case Simulator.WELCH:
			logln(" Starting Method of Welch (numRuns = " + numRuns + ")");
			break;
		}
		logln("---------------------------------------------");
	}

	/* (non-Javadoc)
	 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#startSimulationRun(int)
	 */
	@Override
	public void startSimulationRun(int number) {
		logln("Simulation run " + number + "/" + numRuns + " started.");
	}

	/* (non-Javadoc)
	 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#updateSimulationProgress(double, long)
	 */
	@Override
	public void updateSimulationProgress(double progress, long elapsedTime) {
		logln("Progress: " + Math.round(progress) + "%");
	}

	/* (non-Javadoc)
	 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#finishWarmUp()
	 */
	@Override
	public void finishWarmUp() {
		if(Simulator.analMethod != WELCH) {
			logln("Warm up finished. Starting steady state analysis...");
		}
	}

	/* (non-Javadoc)
	 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#finishSimulationRun()
	 */
	@Override
	public void finishSimulationRun() {
		if (Simulator.clock >= Simulator.totRunLen)  {
			if (Simulator.stoppingRule != Simulator.FIXEDLEN)  {
				logln("WARNING: The simulation was stopped because of reaching max totalRunLen!");
				logln("         The required precision may not have been reached!");
			}
			else
				logln("Info: STOPPING because max totalRunLen is reached!");
		}
		
		logln("Simulation run finished.");
		logln();
	}

	/* (non-Javadoc)
	 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#finishSimulation()
	 */
	@Override
	public void finishSimulation() {
		logln();
		logln();
		logln("Simulation finished.");
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
		return Simulator.totRunLen / 20.0;
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
}
