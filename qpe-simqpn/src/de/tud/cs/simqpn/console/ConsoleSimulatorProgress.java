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

public class ConsoleSimulatorProgress implements SimulatorProgress {

	private double run = 0;
	private int numRuns;
	private int analysisMethod;

	@Override
	public void startSimulation(int analysisMethod, int stoppingCriteria, int numRuns) {
		this.numRuns = numRuns;
		this.analysisMethod = analysisMethod;

		switch(analysisMethod) {
		case Simulator.BATCH_MEANS:
			logln("---------------------------------------------");
			logln(" Starting Batch Means Method");
			logln("---------------------------------------------");
			break;
		case Simulator.REPL_DEL:
			logln("---------------------------------------------");
			logln(" Starting Multiple Replications (numRuns = " + numRuns + ")");
			logln("---------------------------------------------");
			break;
		case Simulator.WELCH:
			logln("---------------------------------------------");
			logln(" Starting Method of Welch (numRuns = " + numRuns + ")");
			logln("---------------------------------------------");
			break;
		}
	}

	@Override
	public void startSimulationRun(int number) {
		logln("Simulation run " + (number + 1) + "/" + numRuns + " started.");
		run = ((double)number) / numRuns;
	}

	@Override
	public void updateSimulationProgress(int progress) {
		if(analysisMethod != REPL_DEL) {
			logln("Progress: " + progress + "%");
		}
	}

	@Override
	public void finishWarmUp() {
		if(analysisMethod != WELCH) {
			logln("Warm up finished. Starting steady state analysis...");
		}
	}

	@Override
	public void finishSimulationRun() {
		logln("Simulation run finished.");
		logln();
	}

	@Override
	public void finishSimulation() {
		logln();
		logln();
		logln("Simulation finished.");
	}

	@Override
	public boolean isCanceled() {
		return false;
	}

	@Override
	public double getCheckInterval(double totRunLength) {
		return totRunLength / 20.0;
	}

	@Override
	public void finishPrecisionCheck(boolean done, String failedPlaceName) {
	}

	@Override
	public void startPrecisionCheck(int numTotalColors) {
	}

	@Override
	public void updatePrecisionCheckProgress(boolean passed, int passedColors) {
	}
}
