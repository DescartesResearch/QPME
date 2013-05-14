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
 * Original Author(s):  Samuel Kounev
 * Contributor(s): Simon Spinner  
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
 *  2004/08/25  Samuel Kounev     Implemented support for FIFO (First-In-First-Out) departure 
 *                                discipline, i.e. arriving tokens become available for output 
 *                                transitions in the order of their arrival. Default is NORMAL, i.e.  
 *                                tokens become available immediately upon arrival.
 *  2006/10/14  Christofer Dutz   Added @SuppressWarnings("unchecked") and cleaned up 
 *                                imports to avoid warnings!
 *  2006/10/21  Samuel Kounev     Modified to use the Simulator.log() methods for output.                                
 *  2008/11/29  Samuel Kounev     Changed LinkedList tokens[c] to store Double objects instead of 
 *                                Token objects since only the arrival timestamps are needed. 
 *                                Renamed tokens to tokArrivTS.                                
 *  2008/11/29  Samuel Kounev     Changed depQueue to store Integer objects containing the colors 
 *                                of tokens in the order of their arrival since this is the only
 *                                information actually used in the code.
 *  2008/12/13  Samuel Kounev     Changed to store names of token colors that can reside in this place.                               
 *  2010/07/24	Simon Spinner	  Add token tracking support.
 */
package de.tud.cs.simqpn.kernel.entities;

import java.util.Arrays;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.SimQPNController;
import de.tud.cs.simqpn.kernel.stats.PlaceStats;
import de.tud.cs.simqpn.kernel.stats.Stats;
 
/**
 * Class Place
 *
 * @author Samuel Kounev
 * @version
 */

public class Place extends Node {
	
	// Supported departure disciplines (depDiscip):	
	public static final int NORMAL	= 0;	// Arriving tokens become available for output transitions immediately upon arrival.  
	public static final int FIFO	= 1;	// First-In-First-Out: Arriving tokens become available for output transitions in the order of their arrival.
	
	// Supported probe actions
	public static final int PROBE_ACTION_NONE = 0;
	public static final int PROBE_ACTION_START_ON_EXIT = 1;
	public static final int PROBE_ACTION_START_ON_ENTRY = 2;
	public static final int PROBE_ACTION_START_ON_ENTRY_AND_END_ON_EXIT = 3;
	public static final int PROBE_ACTION_START_ON_EXIT_AND_END_ON_ENTRY = 4;
	public static final int PROBE_ACTION_END_ON_EXIT = 5;
	public static final int PROBE_ACTION_END_ON_ENTRY = 6;
	public static final int PROBE_ACTION_TRANSFER = 7;
	
	private static Logger log = Logger.getLogger(Place.class);
	
	public int				numColors;
	public String[]			colors;			// Names of the colors that can reside in this Place.
	public int				statsLevel;		// Determines the amount of statistics to be gathered during the run.
	public int				depDiscip;		// Departure discipline.
	@SuppressWarnings("rawtypes")
	public LinkedList		depQueue;		// depDiscip = FIFO: Departure queue - stores the colors of tokens in the order of their arrival.	
	
	public Transition[]		inTrans;
	public Transition[]		outTrans;
	public int[]			tokenPop;	
	public int[]			availTokens;	// The token population currently available for output transitions.	
	public boolean			depReady;		// depDiscip=FIFO: true if a token is currently available for output transitions of the place 
	
	@SuppressWarnings("rawtypes")
	public LinkedList[]		tokArrivTS;		// statsLevel >= 3: Arrival timestamps of tokens in the place/depository.
	
	@SuppressWarnings("rawtypes")
	public LinkedList[]		tokens;			// individualTokens[color] = true: list of individual tokens.
	
	public boolean[]		individualTokens;		// individualTokens[color] specifies whether tokens of the specified color should be stored individually
	
	// Configuration of probes
	public int[][]			probeActions;
	public Probe[][]		probeInstrumentations;  // probeInstrumentations[numColors]: List of probes tracking tokens in this place
	
	public PlaceStats		placeStats;	 
	
	public Element			element;	

	/**
	 * 
	 * Constructor
	 *
	 * @param id          - global id of the place
	 * @param name        - name of the place
	 * @param colors      - names of the colors that can reside in this place
	 * @param numInTrans  - number of input transitions
	 * @param numOutTrans - number of output transitions
	 * @param numProbes	  - number of all probes in net
	 * @param statsLevel  - determines the amount of statistics to be gathered during the run
	 * @param depDiscip   - determines the departure discipline (order): NORMAL or FIFO
	 * @param element     - reference to the XML element representing the place
	 */
	@SuppressWarnings("rawtypes")
	public Place(int id, String name, String[] colors, int numInTrans, int numOutTrans, int numProbes, int statsLevel, int depDiscip, Element element, SimQPNController sim) throws SimQPNException {
		super(id, name);		
		this.colors			       = colors;	
		this.numColors		       = colors.length;	
		this.inTrans		       = new Transition[numInTrans];
		this.outTrans		       = new Transition[numOutTrans];
		this.tokenPop		       = new int[numColors];
		this.availTokens		   = new int[numColors]; 
		this.statsLevel		       = statsLevel;
		this.depDiscip		       = depDiscip;
		this.tokens			       = new LinkedList[numColors];
		this.individualTokens			   = new boolean[numColors];
		this.probeActions          = new int[numColors][numProbes];
		this.probeInstrumentations = new Probe[numColors][];
		this.element		       = element;

		
		for (int c = 0; c < numColors; c++)  { 
			this.tokenPop[c] 	= 0;
			this.availTokens[c]	= 0;
		}
		if (depDiscip == FIFO)	{			
			this.depQueue = new LinkedList();
			this.depReady = false;		
		}
		if (statsLevel > 0) {
			if (this instanceof QPlace)
				placeStats = new PlaceStats(id, name, Stats.QUE_PLACE_DEP, colors, statsLevel, sim);
			else
				placeStats = new PlaceStats(id, name, Stats.ORD_PLACE, colors, statsLevel, sim);				 			
			if (statsLevel >= 3) {
				this.tokArrivTS = new LinkedList[numColors];
				for (int c = 0; c < numColors; c++)
					this.tokArrivTS[c] = new LinkedList();
			}				
		}
		
		// By default tracking is disabled
		Arrays.fill(individualTokens, false);
		for (int c = 0; c < numColors; c++) {
			Arrays.fill(probeActions[c], PROBE_ACTION_NONE);
			probeInstrumentations[c] = new Probe[0];
		}
	}
	
	/**
	 * Method init - initializes the place
	 *  
	 * Note: make sure clock has been initialized before calling Place.init
	 * 
	 * @param 
	 * @return
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	public void init(double clock) throws SimQPNException {
		
		if (depDiscip == NORMAL)  {
			availTokens = tokenPop; //Note: from here on, availTokens and tokenPop point to the same array!
		}
		else if (depDiscip == FIFO)  {
			int totTkPop = 0;  			 
			int[] tkPop = new int[numColors];
			for (int c = 0; c < numColors; c++) { 
				tkPop[c] = tokenPop[c];
				totTkPop += tokenPop[c]; 				
			}
			if (totTkPop > 0)  {
				while (totTkPop > 0)  
					for (int c = 0; c < numColors; c++)   
						if (tkPop[c] > 0)  {
							depQueue.addLast(new Integer(c));
							tkPop[c]--; totTkPop--; 
						}						
				int nextCol = ((Integer) depQueue.removeFirst()).intValue();
				availTokens[nextCol]++; 
				depReady = true;
			}
		}
		else {
			log.error("Invalid depDiscip specified for place " + name);
			throw new SimQPNException();
		}
		 			
		if (statsLevel >= 3) {			
			for (int c = 0; c < numColors; c++) {
				for (int i = 0; i < tokenPop[c]; i++) {
					tokArrivTS[c].addLast(new Double(clock));
				}
			}
		}

		// Initialize token list for the initial marking
		for (int c = 0; c < numColors; c++) {
			int prC = probeInstrumentations[c].length;
			
			// Create timestamps for all probes associated with this place
			ProbeTimestamp[] timestamps = new ProbeTimestamp[prC];
			for (int pr = 0; pr < prC; pr++) {
				timestamps[pr] = new ProbeTimestamp(probeInstrumentations[c][pr].id, clock);
			}
			
			// Create tokens
			for (int i = 0; i < tokenPop[c]; i++) {
				if (individualTokens[c]) {
					tokens[c].addLast(new Token(this, c, timestamps));
				}
			}
		}
	}

	/**
	 * Method start
	 *  	    
	 * 	 
	 * @param 
	 * @return
	 * @exception
	 */
	public void start(SimQPNController sim) throws SimQPNException {	
		if (statsLevel > 0)	
			placeStats.start(tokenPop, sim);					
	}
	
	/**
	 * Method finish
	 *  	    
	 * 	 
	 * @param 
	 * @return
	 * @exception
	 */
	public void finish(SimQPNController sim) throws SimQPNException {
		// Complete statistics collection
		if (statsLevel > 0)	
			placeStats.finish(tokenPop, sim);					
	}

	/**
	 * Method report
	 *  	    
	 * @param 
	 * @return
	 * @exception
	 */
	public void report(SimQPNController sim) throws SimQPNException {
		if (statsLevel > 0) {
			placeStats.printReport(sim);					
			/* cdutz */ 
			placeStats.addReportMetaData(element, sim);
		}
	}

	/**
	 * Method addTokens - deposits N tokens of particular color
	 * 
	 * @param color - color of tokens
	 * @param count - number of tokens to deposit
	 * @param tokensToBeAdded - Tokens to be added or null if tracking=false.
	 * 							After the call all elements of this array are set to null.
	 * @return
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	public void addTokens(int color, int count, Token[] tokensToBeAdded, SimQPNController sim) throws SimQPNException {
		if (count <= 0) { // DEBUG
			log.error("Attempted to add nonpositive number of tokens to place " + name);
			throw new SimQPNException();
		}
				
		// Update Stats		
		if (statsLevel > 0) {
			placeStats.updateTkPopStats(color, tokenPop[color], count, sim.clock);						
			if (statsLevel >= 3) {
				for (int i = 0; i < count; i++) 
					tokArrivTS[color].addLast(new Double(sim.clock));
			}
		}
		// Now add tokens and update affected transitions
		tokenPop[color] += count;
		if(individualTokens[color]) {
			if (tokensToBeAdded == null) { // DEBUG
				log.error("Cannot add tokens to place " + name);
				throw new SimQPNException();
			}
			
			for (int i = 0; i < count; i++) {
				tokens[color].addLast(tokensToBeAdded[i]);
				tokensToBeAdded[i] = null;  // Note: set null to avoid dangling references.
			}
		}

		if (depDiscip == NORMAL)  {
			for (int i = 0; i < outTrans.length; i++)
				outTrans[i].updateState(id, color, tokenPop[color], count);
		}				
		else if (depDiscip == FIFO)  {			
			if (depReady) {
				for (int i=0; i < count; i++)  
					depQueue.addLast(new Integer(color));								
			}
			else {
				for (int i=0; i < (count-1); i++)
					depQueue.addLast(new Integer(color));
				availTokens[color]++; 
				depReady = true;
				for (int i = 0; i < outTrans.length; i++)
					outTrans[i].updateState(id, color, availTokens[color], 1);												
			}
		}
		else {
			log.error("Invalid depDiscip specified for place " + name);
			throw new SimQPNException();
		}
	}
	
	/**
	 * Method removeTokens - removes N tokens of particular color
	 * 
	 * @param color - color of tokens
	 * @param count - number of tokens to remove (must be = 1 if tracking enabled)	
	 * @param returnBuffer - an array used to store the removed tokens, if tracking enabled. (Condition: returnBuffer.length >= count) 
	 * @return removed tokens (if tracking is enabled)
	 * @exception
	 */
	public Token[] removeTokens(int color, int count, Token[] returnBuffer, SimQPNController sim) throws SimQPNException {
		/* //DEBUG
		if (count <= 0) { 
			Simulator.logln("Error: Attempted to remove nonpositive number of tokens from place " + name);
			throw new SimQPNException();
		}				
		if (availTokens[color] < count || tokenPop[color] < count) {
			Simulator.logln("Error: Attempted to remove more tokens from place " + name + " than are available!");
			throw new SimQPNException();
		}*/						
		// Update Stats
		if (statsLevel > 0) {
			placeStats.updateTkPopStats(color, tokenPop[color], (-1)*count, sim.clock);				
			if (statsLevel >= 3) {
				Double arrivTS;
				for (int i = 0; i < count; i++) {
					arrivTS = (Double) tokArrivTS[color].removeFirst();
					placeStats.updateSojTimeStats(color, sim.clock - arrivTS.doubleValue(), sim);
				}
			}				
		}
		// Now remove tokens and update affected transitions	
		tokenPop[color] -= count;
		if (individualTokens[color]) {
			if (returnBuffer.length < count) {
				log.error("Return buffer for removed tokens too small.");
				throw new SimQPNException();
			}
			for (int i = 0; i < count; i++) {
				returnBuffer[i] = (Token)tokens[color].removeFirst();
			}
		}

		if (depDiscip == NORMAL)  {
			for (int i = 0; i < outTrans.length; i++)
				outTrans[i].updateState(id, color, tokenPop[color], (-1)*count);						
		}				
		else if (depDiscip == FIFO)  {
			availTokens[color] -= count;
			for (int i = 0; i < outTrans.length; i++)
				outTrans[i].updateState(id, color, availTokens[color], (-1)*count);			
			if (depQueue.size() > 0) {
				int nextCol = ((Integer) depQueue.removeFirst()).intValue();
				availTokens[nextCol]++;
				depReady = true; // Left for clarity. Actually redundant since depReady should already be true.
				for (int i = 0; i < outTrans.length; i++)
					outTrans[i].updateState(id, nextCol, availTokens[nextCol], 1);										
			}
			else depReady = false;
		}
		else {
			log.error("Invalid depDiscip specified for place " + name);
			throw new SimQPNException();
		}
		
		return returnBuffer;		
	}
	
	/**
	 * Maps the name of a color to the index of the color in this place.
	 * @param color - name of the color
	 * @return index of the color (or -1 if color not defined in this place)
	 */
	public int getColorIndex(String color) {
		for (int c = 0; c < colors.length; c++) {
			if (colors[c].equals(color)) {
				return c;
			}
		}
		return -1;
	}
	
	/**
	 * Adds a probe instrumentation to this place.
	 * @param probe - the probe
	 * @param action - a PROBE_ACTION constant.
	 */
	@SuppressWarnings("rawtypes")
	public void addProbe(Probe probe, int action) {
		int probeId = probe.id;
		for (String trackedColor : probe.colors) {
			int c = getColorIndex(trackedColor);
			if (c >= 0) {
				probeActions[c][probeId] = action;
				if ((action != PROBE_ACTION_START_ON_EXIT) && (action != PROBE_ACTION_END_ON_ENTRY) 
						&& (action != PROBE_ACTION_START_ON_EXIT_AND_END_ON_ENTRY)) {
					individualTokens[c] = true;
					if (tokens[c] == null) {
						tokens[c] = new LinkedList();
					}
				}
				
				boolean found = false;
				for (int i = 0; i < probeInstrumentations[c].length; i++) {
					if (probeInstrumentations[c][i].id == probeId) {
						found = true;
						break;
					}
				}
				
				if (!found) {
					Probe[] p = new Probe[probeInstrumentations[c].length + 1];
					System.arraycopy(probeInstrumentations[c], 0, p, 0, probeInstrumentations[c].length);
					p[p.length - 1] = probe;
					probeInstrumentations[c] = p;
				}				
			}
		}
	}
			
}
