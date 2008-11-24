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
 *  2004/08/25  Samuel Kounev     Implemented support for FIFO departure discipline.
 *  2004/08/25  Samuel Kounev     Implemented support for multi-server PS queues (-/M/n-PS).
 *  2005/06/21  Samuel Kounev     Enhanced to support PS queues with non-exponential service times.
 *                                Currently configured by setting the expPS property in init(). 
 *                                Tested successfully on ispass-sc1 with exp service times.
 *  2005/06/22  Samuel Kounev     Moved initialization of service time distributions from init() 
 *                                to Simulator.getReady() as part of the model definition.
 *  2006/07/28  Samuel Kounev     Changed to truncate service time distributions to avoid negative
 *                                values for service times, i.e. "if (servTime < 0) servTime = 0;"
 *  2006/10/14  Christofer Dutz   Added @SuppressWarnings("unchecked") and cleaned up 
 *                                imports to avoid warnings!
 *  2006/10/21  Samuel Kounev     Modified to use the Simulator.log() methods for output.                                
 *                                
 */

package de.tud.cs.simqpn.kernel;

import java.util.LinkedList;

import org.dom4j.Element;

import cern.colt.list.AbstractDoubleList;
import cern.colt.list.DoubleArrayList;
import cern.jet.random.AbstractContinousDistribution;

/**
 * Class QueueingPlace
 *
 * Note: We use the term queue to refer to a queueing station including both the waiting area and the servers.
 * Note: We assume that in the beginning of the run, the queue is empty!
 * 
 * @author Samuel Kounev
 * @version %I%, %G%
 */

public class QueueingPlace extends Place {	
	public Queue		queue;				// Queue of the queueing place.
	
	public double[]		meanServTimes;		// Mean token service times at the queueing station (all times usually in milliseconds)
	public int[]		queueTokenPop;		// Number of tokens in the queueing station (queue), i.e. token population.
											// Note that for queueing places Place.tokenPop contains tokens in the depository.
	
	public LinkedList[]	queueTokens;		// PS queues: Tokens in the queueing station (queue).												
	public AbstractDoubleList[]
						residServTimes;		// PS queues: expPS==false: Residual service times.
	
	public AbstractContinousDistribution[]
						randServTimeGen;	// PS queues: Random number generators for generating service times.
	
	public QueueingPlaceStats	queueingPlaceStats;	
	
	public Element element;
			
	// TODO: queueTokens should only be used for statLevel == 3
	
	/**
	 * Constructor
	 *
	 * @param id          - global id of the place
	 * @param name        - name of the place
	 * @param numColors   - number of colors
	 * @param numInTrans  - number of input transitions
	 * @param numOutTrans - number of output transitions
	 * @param statsLevel  - determines the amount of statistics to be gathered during the run
	 * @param depDiscip   - determines the depository's departure discipline (order): NORMAL or FIFO
	 */
	public QueueingPlace(int id, String name, int numColors, int numInTrans, int numOutTrans, int statsLevel, int depDiscip, Queue queue, Element element) throws SimQPNException {		
		super(id, name, numColors, numInTrans, numOutTrans, statsLevel, depDiscip, element);
		
		this.queue						= queue;
		this.meanServTimes				= new double[numColors];
		for (int c = 0; c < numColors; c++) 
			this.meanServTimes[c]		= -1; // -1 means 'uninitialized'
		
		this.queueTokenPop 				= new int[numColors];
		for (int c = 0; c < numColors; c++) 
			this.queueTokenPop[c] 		= 0;
		
		if (statsLevel > 0) 
			queueingPlaceStats = new QueueingPlaceStats(id, name, numColors, statsLevel, queue.queueDiscip, queue.numServers, meanServTimes);
		
		// PS Queues			
		if (queue.queueDiscip == Queue.PS) {			 
			this.queueTokens = new LinkedList[numColors];
			for (int c = 0; c < numColors; c++)
				this.queueTokens[c]	= new LinkedList();							
		}	
	}
	
	/**
	 * Method init
	 * 
	 * @param 
	 * @return
	 * @exception
	 */
	public void init() throws SimQPNException {
		super.init();
		
		//SDK-TODO: This check might cause problems for some distributions where meanServTimes is not initialized!
		for (int c = 0; c < numColors; c++) 
			// Make sure that all meanServTimes have been initialized
			if (meanServTimes[c] < 0) {
				Simulator.logln("Error: meanServTimes[" + c + "] has not been initialized for QueueingPlace " + name);
				throw new SimQPNException(); 
			}			

		// PS Queues	
		if (queue.queueDiscip == Queue.PS && (!queue.expPS))  {							
			residServTimes 	= new DoubleArrayList[numColors];
			for (int c = 0; c < numColors; c++) 
				residServTimes[c] = new DoubleArrayList(100);  //SDK-TODO: See if 100 is optimal initial capacity. Note: The list is auto-expanding.
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
		if (statsLevel > 0)  {		
			// Start statistics collection
			queueingPlaceStats.start(queueTokenPop);
			super.start();
		}					
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
		if (statsLevel > 0)  {
			// Complete statistics collection
			queueingPlaceStats.finish(queueTokenPop);								
			super.finish();
		}
	}

	//TODO: Consider Simulator.scheduleEvent() below for the case statsLevel < 3	

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
			Simulator.logln("Error: Attempted to add nonpositive number of tokens to queue " + name);
			throw new SimQPNException();
		}
		
		// Update Stats	(below more...) (Note: watch out the order of this and next statement)
		if (statsLevel > 0)
			queueingPlaceStats.updateTkPopStats(color, queueTokenPop[color], count);																	
		 				
		queueTokenPop[color] += count;

		queue.addTokens(this, color, count);
	}
	
	/**
	 * Method completeService - completes service of a token, moves it to the depository 
	 *                          and schedules next waiting token for service.                      
	 *
	 * @param token - token completing service
	 * @return
	 * @exception
	 */
	public void completeService(Token token) throws SimQPNException {
		if (queueTokenPop[token.color] < 1) {
			Simulator.logln("Error: Attempted to remove a token from queue " + name + " which is empty!");
			throw new SimQPNException();
		}

		// Update stats (below more...) (Note: watch out the order of this and next statement)
		if (statsLevel > 0)  {
			queueingPlaceStats.updateTkPopStats(token.color, queueTokenPop[token.color], -1);
			if (statsLevel >= 3) 
				queueingPlaceStats.updateSojTimeStats(token.color, Simulator.clock - token.arrivalTS);
		}
		
		// Now remove token from queue and update queue state
		queueTokenPop[token.color]--;
				
		queue.completeService(token);
		
		// Finally move token to depository
		super.addTokens(token.color, 1);		
	}
		
	public void report() throws SimQPNException  {		
		if (statsLevel > 0) {
			queueingPlaceStats.printReport();
			super.report();					
		}
	}
	
}
