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
 *  2014/03/10  Jürgen Walter     Extracted from NetLoader
 * 
 */
package de.tud.cs.simqpn.kernel.loading.distributions;

import java.util.HashMap;
import java.util.Map;

import de.tud.cs.simqpn.kernel.SimQPNException;

public class DeterministicConcurrencyCreator extends DistributionCreator {

	double concurrencies[] = null;
	String concurrenciesFilename = null;
	double responseTimes[] = null;
	String responseTimesFilename = null;
	
	@Override
	protected void loadParams() throws SimQPNException {
		concurrencies = this.loadDoublesFromFile("concurrenciesFile");
		concurrenciesFilename = this.loadStringParam("concurrenciesFile");
		responseTimes = this.loadDoublesFromFile("responsetimesFile");
		responseTimesFilename = this.loadStringParam("responsetimesFile");
	}

	@Override
	public AbstractDistribution getDistribution()
			throws SimQPNException {
		String colorRefId = loadStringParam("id");
		Map<Integer, Double> concurrencyLevel = new HashMap<Integer, Double>();
		for (int i = 0; i < concurrencies.length; i++) {
			concurrencyLevel.put((int) (concurrencies[i]), responseTimes[i]);
		}
		return new DeterministicConcurrency(concurrencyLevel, colorRefId);
	}

	@Override
	public double getMean() {
		return -1.0;
	}

	@Override
	public String getConstructionText() {
		return "(" + concurrenciesFilename + "|" + responseTimesFilename + ")";
	}

	@Override
	public String getMeanComputationText() {
		return "Returning placeholder -1 for mean of DeterministicConcurrency Distribution";
	}
}
