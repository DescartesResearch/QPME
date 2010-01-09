/* ==============================================
 * QPME : Queueing Petri net Modeling Environment
 * ==============================================
 *
 * (c) Copyright 2003-2010, by Samuel Kounev and Contributors.
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
 * Original Author(s):  Samuel Kounev
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------
 *  2003/08/??  Samuel Kounev     Created.
 *  ...
 *  2004/08/25  Samuel Kounev     Implemented support for FIFO departure discipline.                                 
 *  2004/08/25  Samuel Kounev     Changed type of modeWeights from int[] to double[], so that 
 *                                arbitrary values for weights are supported.
 * 
 */
package de.tud.cs.simqpn.kernel;

import cern.colt.list.AbstractIntList;
import cern.colt.list.IntArrayList;
import cern.jet.random.Empirical;
import cern.jet.random.EmpiricalWalker;

/**
 * Class Transition
 *
 * @author Samuel Kounev
 * @version
 */

public class Transition extends Node {

	public int				numModes;
	public double			transWeight;
		
	public Place[]			inPlaces;
	public Place[]			outPlaces;	

	public int[][][]		inFunc;			// [mode, inPlace, color]
	public int[][][]		outFunc;		// [mode, outPlace, color]
	public double[]			modeWeights;	// [1..numModes] 
	
	public AbstractIntList	enModes;		// List of currently enabled modes		   
	
	public EmpiricalWalker	randModeGen;	// Random number generator for generating modes to fire

	/**
	 * Constructor
	 * 
	 * @param id            - global id of the transition
	 * @param name          - name of the transition
	 * @param numModes      - number of modes
	 * @param numInPlaces   - number of input places
	 * @param numOutPlaces  - number of output places
	 * @param transWeight   - transition weight	  
	 */
	public Transition(int id, String name, int numModes, int numInPlaces, int numOutPlaces, double transWeight) throws SimQPNException {
		super(id, name);		
		this.numModes		   = numModes;
		this.transWeight	   = transWeight;
		this.modeWeights	   = new double[numModes];
		this.inPlaces		   = new Place[numInPlaces];
		this.outPlaces		   = new Place[numOutPlaces];
		this.inFunc			   = new int[numModes][numInPlaces][];
		this.outFunc		   = new int[numModes][numOutPlaces][];
		this.enModes		   = new IntArrayList(numModes);
		// Create randModeGen
		double[] pdf = new double[numModes];			
		for (int m = 0; m < numModes; m++) pdf[m] = 1;
		this.randModeGen = new EmpiricalWalker(pdf, Empirical.NO_INTERPOLATION, Simulator.nextRandNumGen());
		// Note: Here we use a default distribution. The actual distribution is set each time before using randModeGen. 
	}
	
	/**
	 * Method init - checks for enabled modes and initializes enModes   
	 * 
	 * @param  	
	 * @return 
	 * @exception
	 */
	public void init() {		
		int m, p, c, nM, nP, nC;
		boolean enabled;
		Place pl;		
		nM = numModes;
		nP = inPlaces.length;
		enModes.clear();
		for (m = 0; m < nM; m++) {
			enabled = true;	
			for (p = 0; p < nP; p++) {
				pl = inPlaces[p];
				nC = pl.numColors;
				for (c = 0; enabled && (c < nC); c++)
					if (pl.availTokens[c] < inFunc[m][p][c]) { 
						enabled = false; break;
					} 											
				if (!enabled) break;
			}
			if (enabled) enModes.add(m);
		}				
	}
	
	/**
	 * Method updateState - updates enModes after a change in the token 
	 *                      population of an input place.
	 * Note: Must be called whenever changing token population in any 
	 *       of the input transitions. 
	 *       
	 * @param inPlaceId		- id of updated input place
	 * @param color			- color of updated token count
	 * @param newAvailTkCnt	- new available token count
	 * @param delta			- difference between new and old token count   	
	 * @return 
	 * @exception
	 */
	public void updateState(int inPlaceId, int color, int newAvailTkCnt, int delta)  {
										
		if (delta > 0) {	// CASE A: TOKENS HAVE BEEN ADDED
			if (enModes.size() == numModes) return;
			// Find index of updated input place
			int uInP = 0;
			while (inPlaces[uInP].id != inPlaceId) uInP++;
			// Check for newly enabled modes
			int m, p, c, nC;
			Place pl;
			boolean enabled;
			int nM = numModes;
			int nP = inPlaces.length;					
			for (m = 0; m < nM; m++)  {
				// only consider disabled modes that require tokens of 
				// the respective color
				if ((!enModes.contains(m)) && inFunc[m][uInP][color] > 0)  {					
					enabled = true;							
					for (p = 0; p < nP; p++)  {
						pl = inPlaces[p];
						nC = pl.numColors;						
						for (c = 0; enabled && (c < nC); c++)
							if (pl.availTokens[c] < inFunc[m][p][c])  { 
								enabled = false; break;
							}
						if (!enabled) break;
					}
					if (enabled) enModes.add(m);
				}
			}
		} 
		else  {		// CASE B: TOKENS HAVE BEEN REMOVED
			// Find index of updated input place
			int uInP = 0;
			while (inPlaces[uInP].id != inPlaceId) uInP++;
			// Check for newly disabled modes
			int i, m;			
			for (i = 0; i < enModes.size(); i++)  {
				m = enModes.get(i);
				if (newAvailTkCnt < inFunc[m][uInP][color]) enModes.remove(i--);			
			}
		}		
	}
	
	
	/**
	 * Method enabled 
	 * 
	 * @param  	
	 * @return boolean - true if at least one mode enabled, false otherwise
	 * @exception 
	 */
	public boolean enabled() {		
		return (enModes.size() > 0) ? true : false;
	}
  
	/**
	 * Method checkIfEnabled - checks if transition is enabled
	 * Note: does not rely on enModes
	 * 
	 * @param  	
	 * @return boolean - true if at least one mode enabled, false otherwise
	 * @exception 
	 */
	public boolean checkIfEnabled() {
		int m, p, c, nM, nP, nC;
		boolean enabled = false;		
		nM = numModes;
		nP = inPlaces.length;
		Place pl;
		for (m = 0; (!enabled) && (m < nM); m++) {
			enabled = true;	
			for (p = 0; p < nP; p++) {
				pl = inPlaces[p];
				nC = pl.numColors;
				for (c = 0; enabled && (c < nC); c++)
					if (pl.availTokens[c] < inFunc[m][p][c])  { 
						enabled = false; break;
					} 											
				if (!enabled) break;
			}
		}
		return enabled;
	}

	/**
	 * Method fire - fires in a mode chosen based on weights
	 * 
	 * @param	
	 * @return
	 * @exception
	 */
	public void fire() throws SimQPNException {

		// Choose mode to fire based on weights
		int enModesCnt = enModes.size();		
		double[] pdf = new double[enModesCnt];
		for (int m = 0; m < enModesCnt; m++) pdf[m] = modeWeights[enModes.get(m)]; 				
		randModeGen.setState2(pdf);
		int mode = enModes.get(randModeGen.nextInt());
		
		int p, c, nP, nC, n;
		Place pl;
		// Step 1: Remove input tokens
		nP = inPlaces.length;						
		for (p = 0; p < nP; p++) {
			pl = inPlaces[p];
			nC = pl.numColors;
			for (c = 0; c < nC; c++) {
				n = inFunc[mode][p][c];
				if (n != 0) pl.removeTokens(c, n); 							
			}			
		}	
		// Step 2: Deposit output tokens
		nP = outPlaces.length;								
		for (p = 0; p < nP; p++) {
			pl = outPlaces[p];
			nC = pl.numColors;
			for (c = 0; c < nC; c++) {
				n = outFunc[mode][p][c];
				if (n != 0) pl.addTokens(c, n); 							
			}			
		}
		
	} // end fire() 
	
}
