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
 *  2014/03/10  Jürgen Walter     Created
 * 
 */
package de.tud.cs.simqpn.kernel.executor.parallel;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.executor.parallel.barrier.BarrierFactory;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;

public class JBarrierExecutor implements Callable<Net> {

	private static Logger log = Logger.getLogger(JBarrierExecutor.class);
	private Net net;
	private SimulatorProgress progressMonitor;
	private final int verbosityLevel;
	private final LP[] lps;

	/**
	 * Constructor
	 * 
	 * @param net
	 * @param configuration
	 * @param progressMonitor
	 * @param runID
	 */
	public JBarrierExecutor(LP[] lps, SimQPNConfiguration configuration,
			SimulatorProgress progressMonitor, int verbosityLevel) {
		this.lps = lps;
		this.progressMonitor = progressMonitor;
		this.verbosityLevel = verbosityLevel;
		BarrierFactory.createBarrier(lps, verbosityLevel, progressMonitor);
	}

	/**
	 * Simulates the passed net.
	 * 
	 * @throws SimQPNException
	 */
	public Net call() throws SimQPNException {
		
		Thread[] threads = new Thread[lps.length];
		
		for (int i = 0; i < lps.length; i++) {
			LPRunnable newLP = new LPRunnable(lps[i]);
			threads[i] = new Thread(newLP);
			threads[i].start();
		}
		for (int i = 0; i < lps.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				log.error("", e);
			}
		}

		return this.net;
	}

	private class LPRunnable implements Runnable {
		final LP lp;
		
		public LPRunnable(final LP lp) {
			this.lp = lp;
		}

		@Override
		public void run() {
			try {
				lp.initializeWorkingVariables();
				lp.waitForBarrier();
				while (!lp.getStopController().hasFinished()) {
					lp.processSaveEvents();
					lp.checkForPrecission();
					lp.waitForBarrier();
				}
				lp.finish();
			} catch (SimQPNException e) {
				log.error(e);
			}
		}
	}
	
}
