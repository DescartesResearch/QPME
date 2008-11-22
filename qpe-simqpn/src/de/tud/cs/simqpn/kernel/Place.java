/*
 * Copyright (c) 2009 Samuel Kounev. All rights reserved.
 *    
 * The use, copying, modification or distribution of this software and its documentation for 
 * any purpose is NOT allowed without the written permission of the author.
 *  
 * This source code is provided as is, without any express or implied warranty.
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
 * 
 */

package de.tud.cs.simqpn.kernel;

import java.util.LinkedList;

import org.dom4j.Element;
 
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
	
	public int				numColors;
	public int				statsLevel;		// Determines the amount of statistics to be gathered during the run.
	public int				depDiscip;		// Departure discipline.
	public LinkedList		depQueue;		// Departure queue.	
	
	public Transition[]		inTrans;
	public Transition[]		outTrans;
	public int[]			tokenPop;	
	public int[]			availTokens;	// The token population currently available for output transitions.	
	public boolean			depReady;		// depDiscip=FIFO: true if a token is currently available for output transitions of the place 
 
	
	public LinkedList[]		tokens;
	
	public PlaceStats		placeStats;	 
	
	public Element			element;

	/**
	 * 
	 * Constructor
	 *
	 * @param id          - global id of the place
	 * @param name        - name of the place
	 * @param numColors   - number of colors
	 * @param numInTrans  - number of input transitions
	 * @param numOutTrans - number of output transitions
	 * @param statsLevel  - determines the amount of statistics to be gathered during the run
	 * @param depDiscip   - determines the departure discipline (order): NORMAL or FIFO
	 */
	public Place(int id, String name, int numColors, int numInTrans, int numOutTrans, int statsLevel, int depDiscip, Element element) throws SimQPNException {
		super(id, name);		
		this.numColors		= numColors;		
		this.inTrans		= new Transition[numInTrans];
		this.outTrans		= new Transition[numOutTrans];
		this.tokenPop		= new int[numColors];
		this.availTokens	= new int[numColors]; 
		this.statsLevel		= statsLevel;
		this.depDiscip		= depDiscip;
		this.element		= element;
		
		for (int c = 0; c < numColors; c++)  { 
			this.tokenPop[c] 	= 0;
			this.availTokens[c]	= 0;
		}
		if (depDiscip == FIFO)	{			
			this.depQueue = new LinkedList();
			this.depReady = false;		
		}
		if (statsLevel > 0) {
			if (this instanceof QueueingPlace)
				placeStats = new PlaceStats(id, name, Stats.DEPOSITORY, numColors, statsLevel);
			else
				placeStats = new PlaceStats(id, name, Stats.PLACE, numColors, statsLevel);				 			
			if (statsLevel >= 3) {
				this.tokens = new LinkedList[numColors];
				for (int c = 0; c < numColors; c++)
					this.tokens[c] = new LinkedList();
			}				
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
	public void init() throws SimQPNException {
		
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
							depQueue.addLast(new Token(this, Simulator.clock, c));
							tkPop[c]--; totTkPop--; 
						}						
				Token tk = (Token) depQueue.removeFirst();
				availTokens[tk.color]++; 
				depReady = true;
			}
		}
		else {
			Simulator.logln("Error: Invalid depDiscip specified for place " + name);
			throw new SimQPNException();
		}

		if (statsLevel > 0) 			
			if (statsLevel >= 3) {			
				for (int c = 0; c < numColors; c++)
					for (int i = 0; i < tokenPop[c]; i++)
						tokens[c].addLast(new Token(this, Simulator.clock, c));
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
	public void start() {	
		if (statsLevel > 0)	
			placeStats.start(tokenPop);					
	}
	
	/**
	 * Method finish
	 *  	    
	 * 	 
	 * @param 
	 * @return
	 * @exception
	 */
	public void finish() {
		// Complete statistics collection
		if (statsLevel > 0)	
			placeStats.finish(tokenPop);					
	}

	/**
	 * Method report
	 *  	    
	 * @param 
	 * @return
	 * @exception
	 */
	public void report() throws SimQPNException {
		if (statsLevel > 0) {
			placeStats.printReport();					
			/* cdutz */ 
			placeStats.addReportMetaData(element);
		}
	}

	/**
	 * Method addTokens - deposits N tokens of particular color
	 * 
	 * @param color - color of tokens
	 * @param count - number of tokens to deposit
	 * @return
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	public void addTokens(int color, int count) throws SimQPNException {
		if (count <= 0) { // DEBUG: To be removed later
			Simulator.logln("Error: Attempted to add nonpositive number of tokens to place " + name);
			throw new SimQPNException();
		}
				
		// Update Stats		
		if (statsLevel > 0) {
			placeStats.updateTkPopStats(color, tokenPop[color], count);						
			if (statsLevel >= 3) {
				for (int i = 0; i < count; i++) 
					tokens[color].addLast(new Token(this, Simulator.clock, color));
			}
		}
		// Now add tokens and update affected transitions
		tokenPop[color] += count;				

		if (depDiscip == NORMAL)  {
			for (int i = 0; i < outTrans.length; i++)
				outTrans[i].updateState(id, color, tokenPop[color], count);
		}				
		else if (depDiscip == FIFO)  {			
			if (depReady) {
				for (int i=0; i < count; i++)  
					depQueue.addLast(new Token(this, Simulator.clock, color));								
			}
			else {
				for (int i=0; i < (count-1); i++)
					depQueue.addLast(new Token(this, Simulator.clock, color));
				availTokens[color]++; 
				depReady = true;
				for (int i = 0; i < outTrans.length; i++)
					outTrans[i].updateState(id, color, availTokens[color], 1);												
			}
		}
		else {
			Simulator.logln("Error: Invalid depDiscip specified for place " + name);
			throw new SimQPNException();
		}
	}
	
	/**
	 * Method removeTokens - removes N tokens of particular color
	 * 
	 * @param color - color of tokens
	 * @param count - number of tokens to remove	
	 * @return
	 * @exception
	 */
	public void removeTokens(int color, int count) throws SimQPNException {
		/* //DEBUG: To be removed later...
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
			placeStats.updateTkPopStats(color, tokenPop[color], (-1)*count);				
			if (statsLevel >= 3) {
				Token tk;
				for (int i = 0; i < count; i++) {
					tk = (Token) tokens[color].removeFirst();
					placeStats.updateSojTimeStats(color, Simulator.clock - tk.arrivalTS);
				}
			}				
		}
		// Now remove tokens and update affected transitions	
		tokenPop[color] -= count;						

		if (depDiscip == NORMAL)  {
			for (int i = 0; i < outTrans.length; i++)
				outTrans[i].updateState(id, color, tokenPop[color], (-1)*count);						
		}				
		else if (depDiscip == FIFO)  {
			availTokens[color] -= count;
			for (int i = 0; i < outTrans.length; i++)
				outTrans[i].updateState(id, color, availTokens[color], (-1)*count);			
			if (depQueue.size() > 0) {
				Token tk = (Token) depQueue.removeFirst();
				availTokens[tk.color]++;
				depReady = true; // Left for clarity. Actually redundant since depReady should already be true.
				for (int i = 0; i < outTrans.length; i++)
					outTrans[i].updateState(id, tk.color, availTokens[tk.color], 1);										
			}
			else depReady = false;
		}
		else {
			Simulator.logln("Error: Invalid depDiscip specified for place " + name);
			throw new SimQPNException();
		}
		
	}
			
}
