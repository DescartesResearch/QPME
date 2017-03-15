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
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import cern.jet.random.AbstractDistribution;
import de.tud.cs.simqpn.kernel.SimQPNException;

public abstract class DistributionCreator {
	
	protected static Logger log = Logger.getLogger(DistributionCreator.class);
	
	protected Element colorRef = null;
	
	protected DistributionCreator() {}
	
	public static DistributionCreator constructCreator(String distributionName, Element colorRef)
			throws SimQPNException {
		
		Distribution dist = Distribution.fromName(distributionName);
		DistributionCreator creator = dist.getCreator();
		
		creator.colorRef = colorRef;
		creator.loadParams();
		
		return creator;
	} 
	
	protected abstract void loadParams() throws SimQPNException;
	
	public abstract AbstractDistribution getDistribution() throws SimQPNException;

	public abstract double getMean();
	
	public abstract String getConstructionText();
	
	public abstract String getMeanComputationText();
	
	protected double loadDoubleParam(String paramName) throws SimQPNException {
		if (colorRef.attributeValue(paramName) == null) {
			throw new SimQPNException("Parameter \"" + paramName + "\" for " + this.getClass().getName() + " not set!");
		}
		
		return Double.parseDouble(colorRef.attributeValue(paramName));
	}
	
	protected double loadPositiveDoubleParam(String paramName) throws SimQPNException {
		double paramValue = this.loadDoubleParam(paramName);
		
		if (paramValue <= 0) {
			throw new SimQPNException("Invalid \"" + paramName + "\" parameter (" + paramValue + ") for " + this.getClass().getName() + "!");
		}
		
		return paramValue;
	}
	
	protected double loadAtLeastOneDoubleParam(String paramName) throws SimQPNException {
		double paramValue = this.loadDoubleParam(paramName);
		
		if (paramValue < 1) {
			throw new SimQPNException("Invalid \"" + paramName + "\" parameter (" + paramValue + ") for " + this.getClass().getName() + "!");
		}
		
		return paramValue;		
	}
	
	protected String loadStringParam(String paramName) throws SimQPNException {
		if (colorRef.attributeValue(paramName) == null) {
			throw new SimQPNException("Parameter \"" + paramName + "\" for " + this.getClass().getName() + " not set!");
		}
		
		return colorRef.attributeValue(paramName);
	}
	
	protected double[] loadDoublesFromFile(String paramName) throws SimQPNException {
		
		String doublesFilename = loadStringParam(paramName);
		File doublesFile = new File(doublesFilename);
		if (!doublesFile.exists()) {
			throw new SimQPNException("PDF file (" + doublesFilename + ") for " + this.getClass().getName() + " does not exist!");													
		}
		
		BufferedReader input = null;
		try {
			input = new BufferedReader(new FileReader(doublesFile));
			List<Double> doublesList = new ArrayList<Double>();

			// we read doubles from each line in the file
			// the format of a line is:
			//
			// double;double;double;double
			
			while (true) {
				String line = input.readLine();
				if (line == null) {
					break;
				}
				
				// ignore empty lines
				if (line.equals("")) {
					continue;
				}
				
				String[] doubles = line.split(";");
				for (int x = 0; x < doubles.length; x++) { 
					doublesList.add(Double.parseDouble(doubles[x]));
				}
			}

			if (doublesList.size() == 0) {
				throw new SimQPNException("No data in " + doublesFilename + " for " + this.getClass().getName() + "!");
			}
			
			// TODO: implement this in some better way
			// http://stackoverflow.com/questions/960431/how-to-convert-listinteger-to-int-in-java
			
			double result[] = new double[doublesList.size()];
			for (int i = 0; i < doublesList.size(); i++) {
				result[i] = doublesList.get(i);
			}
			
			return result;
		}
		catch (IOException ex) {
			throw new SimQPNException(ex.getMessage());													
		}
		finally {
			try {
				if (input != null) {
					input.close();	
				}
			}
			catch (IOException ex) {
				log.error("ERROR: Cannot close PDF file " + doublesFilename, ex);
				throw new SimQPNException();
			}
		}		
	}
}
