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

import java.util.InputMismatchException;

import de.tud.cs.simqpn.kernel.RandomNumberGenerator;
import de.tud.cs.simqpn.kernel.SimQPNException;

public class ContinuousEmpiricalCreator extends DistributionCreator {

	double pdf[] = null;
	String pdffilename = null;
	double values[] = null;
	String valuesfilename = null;

	@Override
	protected void loadParams() throws SimQPNException {
		pdf = this.loadDoublesFromFile("probabilitiesFile");
		pdffilename = getAbsoluteFilepath(this.loadStringParam("probabilitiesFile"));
		values = this.loadDoublesFromFile("valuesFile");
		valuesfilename = getAbsoluteFilepath(this.loadStringParam("valuesFile"));
		if (pdf.length != values.length) {
			throw new InputMismatchException("The length of the distribution and its corresponding values must match.");
		}
	}

	@Override
	public AbstractDistribution getDistribution() throws SimQPNException {
		return new AbstractDistributionWrapper(
				new ContinuousEmpirical(values, pdf, RandomNumberGenerator.nextRandNumGen()));
	}

	@Override
	public double getMean() {
		double mean = 0;
		for (int i = 0; i < pdf.length; i++) {
			double upperBound = values[i];
			double lowerBound = (i > 0) ? values[i - 1] : 0;
			double range = upperBound - lowerBound;
			double meanvalue = lowerBound + range / 2;
			mean += pdf[i] * meanvalue;
		}
		return mean;
	}

	@Override
	public String getConstructionText() {
		return "(" + pdffilename + "/" + valuesfilename + ")";
	}

	@Override
	public String getMeanComputationText() {
		return "Computing Mean.";
	}
}
