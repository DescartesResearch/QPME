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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import de.tud.cs.simqpn.kernel.SimQPNException;

public class MARSCreator extends DistributionCreator {

	double constant;
	List<Double> coefficients = new LinkedList<Double>();
	List<Double> knots = new LinkedList<Double>();
	List<Integer> sides = new LinkedList<Integer>();
	List<String> colorIds = new LinkedList<String>();
	String marsFilename;
	
	@Override
	protected void loadParams() throws SimQPNException {
		marsFilename = this.loadStringParam("marsFile");
		loadMARSModelFromFile(marsFilename);
	}

	private void loadMARSModelFromFile(String fileName) {
		try (BufferedReader br = new BufferedReader(new FileReader(new File(fileName)))) {
		    String line = br.readLine();
		    constant = Double.valueOf(line);
		    while ((line = br.readLine()) != null) {
		       String[] segments = line.split(" ");
				coefficients.add(Double.valueOf(segments[0]));
				knots.add(Double.valueOf(segments[1]));
				sides.add(Integer.valueOf(segments[2]));
				colorIds.add(segments[3]);
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public AbstractDistribution getDistribution()
			throws SimQPNException {
		Double[] coefficientsArray = new Double[coefficients.size()];
		Double[] knotsArray = new Double[knots.size()];
		Integer[] sidesArray = new Integer[sides.size()];
		String[] colorArray = new String[colorIds.size()];
		return new MARS(constant, coefficients.toArray(coefficientsArray), knots.toArray(knotsArray),
				sides.toArray(sidesArray), colorIds.toArray(colorArray));
	}

	@Override
	public double getMean() {
		throw new IllegalStateException();
	}

	@Override
	public String getConstructionText() {
		return "(" + marsFilename + ")";
	}

	@Override
	public String getMeanComputationText() {
		throw new IllegalStateException();
	}
}
