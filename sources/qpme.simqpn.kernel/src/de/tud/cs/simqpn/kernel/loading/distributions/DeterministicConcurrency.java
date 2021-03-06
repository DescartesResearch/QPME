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
 * Original Author(s):  Fabian Brosig
 * Contributor(s):      
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2013	    Fabian Brosig     Created.
 */
package de.tud.cs.simqpn.kernel.loading.distributions;

import java.util.Map;
import java.util.Map.Entry;

import de.tud.cs.simqpn.kernel.entities.QPlace;

public class DeterministicConcurrency implements AbstractDistribution {

	private Map<Integer, Double> concurrencyLevels;
	private Integer min = -1;
	private String colorRefId;
	
	public DeterministicConcurrency(Map<Integer, Double> concurrencyLevels, String colorRefId) {
		if (concurrencyLevels.get(0) != null)
			throw new IllegalArgumentException(
					"Concurrency level can not be 0, as no response time can be calculated without a request");

		this.concurrencyLevels = concurrencyLevels;
		this.colorRefId = colorRefId;

		for (Entry<Integer, Double> entry : this.concurrencyLevels.entrySet())
			if (entry.getKey() < min)
				min = entry.getKey();
	}

	@Override
	public double nextDouble(QPlace qplace, int color) {
		int concurrency = qplace.getQueueTokenPop()[color];
		if (concurrency < min)
			return concurrencyLevels.get(min);

		Double resp = concurrencyLevels.get(concurrency);
		if (resp == null)
			throw new IllegalStateException(
					"No values for concurrency level " + concurrency + " in the files for the colorRef " + colorRefId);
		return resp;
	}

}
