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
 * Original Author(s):  J?rgen Walter
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2014/02/07  J?rgen Walter     Created
 * 
 */
package de.tud.cs.simqpn.kernel.executor.parallel.barrier.action;

import java.util.List;

import de.tud.cs.simqpn.kernel.executor.parallel.LP;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;

public class PredecessorClockBarrierAction extends BarrierAction {
	private final LP[] lpsWithPredecessors;

	public PredecessorClockBarrierAction(LP[] lps, int verbosityLevel,
			SimulatorProgress progressMonitor) {
		super(lps, verbosityLevel, progressMonitor);
		this.lpsWithPredecessors = getLPsWithPredecessors(lps);

	}

	@Override
	protected void setTimesSaveToProcess() {
		for (LP lp : lpsWithPredecessors) {
			setTimeSaveToProcess(lp);
		}
	}

	@Override
	public void setTimeSaveToProcess(LP lp) {
		double time;
		List<LP> predecessors = lp.getPredecessors();
		if(predecessors.isEmpty()){
			lp.setTimeSaveToProcess(Double.MAX_VALUE);
		}else{
			double min = predecessors.get(0).getClock();
			for(int i=1; i<lp.getPredecessors().size(); i++){
				time = predecessors.get(i).getClock();//pred.getNextEventTime();
				if (min > time) {
					min = time;
				}
			}
			lp.setTimeSaveToProcess(min);
		}
	}
	
	private static LP[] getLPsWithPredecessors(LP[] lps){
		int numberOfLPsWithPredecessors = lps.length;
		for(LP lp :lps){
			if(lp.isWorkloadGenerator()){
				numberOfLPsWithPredecessors--;
				lp.setTimeSaveToProcess(Double.MAX_VALUE);
			}
		}
		LP[] lpsWithPredecessors = new LP[numberOfLPsWithPredecessors];
		int i=0;
		for(LP lp:lps){
			if(!lp.isWorkloadGenerator()){
				lpsWithPredecessors[i++] = lp;
			}
		}
		return lpsWithPredecessors;
	}

}
