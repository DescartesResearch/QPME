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
 *  2014/03/10  J?rgen Walter     Extracted from NetLoader
 * 
 */
package de.tud.cs.simqpn.kernel.loading.distributions;

import cern.jet.random.StudentT;
import de.tud.cs.simqpn.kernel.RandomNumberGenerator;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.util.LogUtil;

public class StudentTCreator extends DistributionCreator {

	double freedom = -1;
	
	@Override
	protected void loadParams() throws SimQPNException {
		freedom = this.loadPositiveDoubleParam("freedom");
	}

	@Override
	public AbstractDistribution getDistribution()
			throws SimQPNException {
		return new AbstractDistributionWrapper(new StudentT(freedom, RandomNumberGenerator.nextRandNumGen()));
	}

	@Override
	public double getMean() {
		//NOTE: The mean of the StudentT distribution is 0 for freedom > 1, otherwise it is undefined.	
		if (freedom <= 1) {
			log.warn(LogUtil.formatDetailMessage(
					"meanServTimes for " + this.getClass().getName() + " not initialized! Might experience problems if indrStats is set to true!")); 
		}
		
		return 0;
	}

	@Override
	public String getConstructionText() {
		return "(" + freedom + ", Simulator.nextRandNumGen()";
	}

	@Override
	public String getMeanComputationText() {
		if (freedom <= 1) {
			return "Undefined, used 0.0";
		}
		
		return "0";
	}

}
