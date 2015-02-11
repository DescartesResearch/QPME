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
 *  2014/02/07  Jürgen Walter     Created
 * 
 */
package de.tud.cs.simqpn.kernel.executor.parallel.barrier;

import de.tud.cs.simqpn.kernel.executor.parallel.LP;
import de.tud.cs.simqpn.kernel.executor.parallel.barrier.action.BarrierAction;
import de.tud.cs.simqpn.kernel.executor.parallel.barrier.action.PredecessorClockBarrierAction;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;
import edu.bonn.cs.net.jbarrier.barrier.Barrier;
import edu.bonn.cs.net.jbarrier.barrier.ButterflyBarrier;
import edu.bonn.cs.net.jbarrier.barrier.CentralBarrier;

public class BarrierFactory {
	
	public static Barrier createBarrier(LP[] lps, int verbosityLevel, SimulatorProgress progressMonitor){
		Barrier barrier = null;
		BarrierAction barrierAction = BarrierFactory.createBarrierAction(lps, verbosityLevel, progressMonitor);

		if (isPowerOfTwo(lps.length) && lps.length > 2) {
			barrier = new ButterflyBarrier(lps.length, barrierAction);
		} else {
			barrier = new CentralBarrier(lps.length, barrierAction);
		}
		for (LP lp : lps) {
			lp.setBarrier(barrier);
			lp.setStopController(barrierAction.getStopController());
		}
		return barrier;
	}
	
	
	public static BarrierAction createBarrierAction(LP[] lps, int verbosityLevel, SimulatorProgress progressMonitor){
		return createPredecessorClockBarrierAction(lps, verbosityLevel, progressMonitor);
	}
	
	public static BarrierAction createPredecessorClockBarrierAction(LP[] lps, int verbosityLevel, SimulatorProgress progressMonitor){
		return new PredecessorClockBarrierAction(lps, verbosityLevel, progressMonitor);
	}

	private static boolean isPowerOfTwo(int number) {
		return (number & -number) == number;
	}

}
