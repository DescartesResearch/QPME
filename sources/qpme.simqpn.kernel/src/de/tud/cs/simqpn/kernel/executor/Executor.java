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
 *  2013/05/13	Jürgen Walter	  Created
 * 
 */

package de.tud.cs.simqpn.kernel.executor;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.entities.Token;
import de.tud.cs.simqpn.kernel.entities.queue.Queue;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;
/**
 * Interface to communicate with the net entities during simulation.
 * @author Jürgen Walter
 *
 */
public interface Executor  {
	/**
	 * Returns executor id.
	 * @return id
	 */
	public int getId();

	/**
	 * Returns actual simulation clock.
	 * @return actual simulation clock
	 */
	public double getClock();
	
	/**
	 * Schedules new event.
	 * @param serviceTime	Service time of the event
	 * @param queue			Queue which schedules token
	 * @param token			Token which will be schedules
	 */
	public void scheduleEvent(double serviceTime, Queue queue, Token token);

	/**
	 * Removes queue event from executor internal list if.
	 * @param queueEvent	the queue event to be removed 
	 */
	public void removeEvent(QueueEvent queueEvent);

	/**
	 * Returns simulation settings.
	 * @return settings for simulation run.
	 */
	public SimQPNConfiguration getConfiguration();

	/**
	 * Returns monitor.
	 * @return monitor which shows progress
	 */
	public SimulatorProgress getProgressMonitor();

	/**
	 * Adds token to Executor.
	 * @param tokenEvent	token in combination with incoming time and other properties
	 */
	public void addTokenEvent(TokenEvent tokenEvent);
}
