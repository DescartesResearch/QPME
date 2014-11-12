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

import de.tud.cs.simqpn.kernel.executor.parallel.LP;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;

public class PredecessorBarrierAction extends BarrierAction {

	@Deprecated
	public PredecessorBarrierAction(LP[] lps, int verbosityLevel, SimulatorProgress progressMonitor) {
		super(lps, verbosityLevel, progressMonitor);
	}

	@Override
	void setTimesSaveToProcess() {
		for (int i = 0; i < numlps; i++) {
			setTimeSaveToProcess(lps[i]);
		}

	}

	@Override
	void setTimeSaveToProcess(LP lp) {
		double time;
		double min = Double.MAX_VALUE;
		for(LP pred : lp.getPredecessors()){
			time = pred.getNextEventTime();
			if (min > time && time != 0.0) {
				min = time;
			}
		}
		lp.setTimeSaveToProcess(min);		
	}

}
