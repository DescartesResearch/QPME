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
 *  2013/08/15  Jürgen Walter     Created
 * 
 */
package de.tud.cs.simqpn.kernel.executor.parallel.barrier.termination;

import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;

/**
 * This class collects the reaching of local stop criteria to build a global stop criterion
 */
public class SimpleStopCriterionController implements StopController{
	
	private final int numLPs;
	private int readyTofinishLPs;
	private boolean hasFinished;
	private final SimulatorProgress progressMonitor;
	private boolean isReadyToFinish;
	
	/**
	 * Constructor
	 * @param numLPs The number of LPs that have to reach their local stop criterion
	 * @param barrier The barrier to be unlocked if simulation finished
	 */
	public SimpleStopCriterionController(int numLPs, SimulatorProgress progressMonitor) {
		this.progressMonitor = progressMonitor;
		this.numLPs = numLPs;
		this.readyTofinishLPs = 0;
		this.hasFinished = false;
		this.isReadyToFinish = false;
	}
	
	/**
	 * Returns if all LPs reached their local stop criterion
	 * @return 
	 */
	public boolean isReadyToFinish(){
		if(progressMonitor != null){
			if(progressMonitor.isCanceled()){
				isReadyToFinish = true;
			}
		}
		return isReadyToFinish;
	}
	
	/**
	 * Increments the counter for finished LPs
	 */
	@Override
	public synchronized void incrementReadyToFinishLPCounter(){
		readyTofinishLPs++;
		if(numLPs <= readyTofinishLPs){
			isReadyToFinish = true;
		}
	}
	
	@Override
	public synchronized void finishSimulation() {
		hasFinished = true;
	}
	
	public boolean hasFinished() {
		return hasFinished;
	}

	@Override
	public void setReadyToFinish() {
		isReadyToFinish = true;		
	}


}
