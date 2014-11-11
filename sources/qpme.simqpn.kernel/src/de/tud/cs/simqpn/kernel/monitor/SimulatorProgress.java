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
 *  2010/04/17  Simon Spinner     Created.
 * 
 */

package de.tud.cs.simqpn.kernel.monitor;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;

/**
 * Simulation front-ends need to implement this interface, in order to
 * monitor the progress of a simulation.
 */
public interface SimulatorProgress {

	/**
	 * If the user has requested to cancel the simulation, this flag is set.
	 * The simulation continuously queries this flag in order to get informed
	 * of a cancel operation.
	 * @return a boolean indicating whether the user has requested to cancel
	 * 				the currently running simulation.
	 */
	public boolean isCanceled();

	/**
	 * The maximum progress update interval defines (logical time) how many 
	 * logical simulation time units  are allowed at maximum between two updates.
	 * @return a double specifying the interval in simulation time
	 */
	public double getMaxUpdateLogicalTimeInterval(SimQPNConfiguration configuration);
	
	/**
	 * The maximum progress update interval (real time) defines how many
	 * wallclock time units are allowed at maximum between two updates.
	 * @return a long specifying the interval in milliseconds
	 */
	public long getMaxUpdateRealTimeInterval();

	/**
	 * Called when a simulation is started.
	 */
	public void startSimulation(SimQPNConfiguration configuration);

	/**
	 * Called at the beginning of each simulation run.
	 * @param number - a counter for the simulation run in the current simulation
	 * 					(0 < number <= numRuns).
	 */
	public void startSimulationRun(int number, SimQPNConfiguration configuration);

	/**
	 * Called after the warm up period finished.
	 */
	public void finishWarmUp(int lpID, SimQPNConfiguration configuration);

	/**
	 * Called in regular intervals to update the progress interval. The update
	 * is triggered at least every l simulation time units and every r real time
	 * units. l and r are specified by the value returned by the methods 
	 * getMaxUpdateLogicalTimeInterval and getMaxUpdateRealTimeInterval.
	 * @param progress - the current simulation run progress as percentage (0.0 <= progress <= 100.0).
	 * @param elapsedTime - the time elapsed since the last progress update in milliseconds.
	 */
	public void updateSimulationProgress(int lpID, double progress, long elapsedTime, SimQPNConfiguration configuration, boolean inRampUp);

	/**
	 * Called for each finished simulation run.
	 */
	public void finishSimulationRun();

	/**
	 * Called after the completion of a simulation.
	 */
	public void finishSimulation();

	/**
	 * Called after every precision check, if Simulator.stoppingRule != Simulator.FIXEDLEN.
	 * @param done - indicates whether the precision check succeeded.
	 * @param failedPlaceName - if not done, the name of the place that has not enough stats yet.
	 * 							Otherwise null.
	 */
	public void precisionCheck(int lpID, boolean done, String failedPlaceName);
	
	/**
	 * Called if the simulation reaches a possibly critical state. An implementation should
	 * show a warning to the user with the specified message.
	 * @param message - description of the warning.
	 */
	public void warning(int lpID, String message);

}
