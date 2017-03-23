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

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.stream.DoubleStream;

import cern.jet.random.Uniform;
import cern.jet.random.engine.RandomEngine;

/**
 * An empirical distribution that maps discrete probabilites to entities.
 * 
 * @author Johannes
 *
 */
public class ContinuousEmpirical extends Uniform {

	private static final long serialVersionUID = -2393973131060840418L;

	private double[] values;

	private double[] cdf;

	public ContinuousEmpirical(double[] values, double[] pdf, RandomEngine randomEngine) {
		super(randomEngine);
		this.values = values;
		if (DoubleStream.of(pdf).sum() != 1) {
			throw new InputMismatchException("The cumulated probabilities must be exactly 1.");
		}
		// convert pdf to cdf
		cdf = new double[pdf.length];
		double sum = 0;
		for (int i = 0; i < cdf.length; i++) {
			sum += pdf[i];
			cdf[i] = sum;
		}

	}

	@Override
	public double nextDouble() {
		// TODO this search is linear in time, which might cause some
		// performance issues. If any problems occur, one could use a binary
		// search.

		// first random number gets us the bin we are in
		double d = super.nextDouble();
		Arrays.binarySearch(cdf, d);
		int i = 0;
		while (d > cdf[i]) {
			i++;
		}

		// now, we have to get the random number of our bin
		d = super.nextDouble();
		double upperBound = values[i];
		double lowerBound = (i > 0) ? values[i - 1] : 0;
		double offset = lowerBound;
		double scale = upperBound - lowerBound;
		return d * scale + offset;
	}

}