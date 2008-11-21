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
import cern.jet.random.Empirical;
import cern.jet.random.EmpiricalWalker;
import cern.jet.random.Exponential;

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
    
	// Supported Queueing Disciplines:	
	public static final int IS = 0;
	public static final int FCFS = 1;
	public static final int PS = 2;	
		
	public int			queueDiscip;		// Queueing discipline
	public double[]		meanServTimes;		// Mean token service times at the queueing station (all times usually in milliseconds)
	
	public int[]		queueTokenPop;		// Number of tokens in the queueing station (queue), i.e. token population.
											// Note that for queueing places Place.tokenPop contains tokens in the depository.										
	
	public int			numServers;			// FCFS queues: Number of servers in queueing station.
	public int			numBusyServers;		// FCFS queues: Number of currently busy servers.
	public LinkedList	waitingLine;		// FCFS queues: List of tokens waiting for service (waiting area of the queue).			 								
	
	public boolean		eventsUpToDate;		// PS queues: True if currently scheduled events for this queue (if any) 
											// reflect the latest token popolation of the queue.											
	public boolean		eventScheduled;		// PS queues: True if there is currently a service completion event scheduled for this queue.
	public LinkedList[]	queueTokens;		// PS queues: Tokens in the queueing station (queue).		
										
	public boolean 		expPS;				// PS queues: true  = Processor-Sharing with exponential service times.
											//            false = Processor-Sharing with non-exponential service times.	                                        
	public AbstractDoubleList[]
						residServTimes;		// PS queues: expPS==false: Residual service times.
	public int			tkSchedCol;			// PS queues: expPS==false: Color of the next token scheduled to complete service.
	public int			tkSchedPos;			// PS queues: expPS==false: Index in queueTokens[tkSchedCol] of the next token scheduled to complete service.
	public double		lastEventClock;		// PS queues: expPS==false: Time of the last event scheduling, i.e. time of the last event with effect on this queue.		
	public int			lastEventTkCnt;		// PS queues: expPS==false: Token population at the time of the last event scheduling.		
	
	public AbstractContinousDistribution[]
						randServTimeGen;	// PS queues: expPS==true: Random number generators for generating service times.
	public EmpiricalWalker					
						randColorGen;		// PS queues: expPS==true: Random number generator for generating token colors.
	
	public QueueStats	queueStats;	
	
	public Element element;
																													 
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
	 * @param queueDiscip - queueing discipline
	 * @param numServers  - number of servers in queueing station
	 */
	public QueueingPlace(int id, String name, int numColors, int numInTrans, int numOutTrans, int statsLevel, int depDiscip, int queueDiscip, int numServers, Element element) throws SimQPNException {		
		super(id, name, numColors, numInTrans, numOutTrans, statsLevel, depDiscip, element);	       		
		this.queueDiscip				= queueDiscip; 
		this.numServers 				= numServers; 
		this.meanServTimes				= new double[numColors];
		for (int c = 0; c < numColors; c++) 
			this.meanServTimes[c]		= -1; // -1 means 'uninitialized'
		
		this.queueTokenPop 				= new int[numColors];
		for (int c = 0; c < numColors; c++) 
			this.queueTokenPop[c] 		= 0;
		
		if (statsLevel > 0) 
			queueStats = new QueueStats(id, name, numColors, statsLevel, queueDiscip, numServers, meanServTimes);
		
		// FCFS Queues			
		if (queueDiscip == FCFS)  {			
			this.numBusyServers = 0;
			this.waitingLine 	= new LinkedList();			 
		}
		// PS Queues			
		if (queueDiscip == PS) {			 
			eventsUpToDate	= false;
			eventScheduled	= false;
			expPS		  	= false; // By default non-exponential queue is assumed.
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

		// PS Queues: final initializations	
		if (queueDiscip == PS)  {			
			if (expPS) {
				randServTimeGen = new Exponential[1];
				randServTimeGen[0]	= new Exponential(0, Simulator.nextRandNumGen());			
				double[] pdf = new double[numColors];
				for (int c = 0; c < numColors; c++) pdf[c] = 1;			
				randColorGen = new EmpiricalWalker(pdf, Empirical.NO_INTERPOLATION, Simulator.nextRandNumGen());				
			}
			else {				
				residServTimes 	= new DoubleArrayList[numColors];
				for (int c = 0; c < numColors; c++) 
					residServTimes[c] = new DoubleArrayList(100);  //SDK-TODO: See if 100 is optimal initial capacity. Note: The list is auto-expanding.
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
	public void start() {	
		if (statsLevel > 0)  {		
			// Start statistics collection
			queueStats.start(queueTokenPop);
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
			queueStats.finish(queueTokenPop);								
			super.finish();
		}
	}
	
	/**
	 * Method updateEvents (Used only for PS queues).
	 * Schedules next service completion event (if any) according to current token population.
	 * 
	 * @param 
	 * @return
	 * @exception
	 */
	public void updateEvents() throws SimQPNException {
		if (eventsUpToDate) return;
		
		int totQueTokCnt = 0;
		for (int c = 0; c < numColors; c++)  
			totQueTokCnt += queueTokenPop[c];
		
		if (totQueTokCnt > 0) {			
			if (expPS) {  // Exponential service times
				double[] meanServRates = new double[numColors];
				for (int c = 0; c < numColors; c++)
					meanServRates[c] = (((double) queueTokenPop[c]) / totQueTokCnt) * (1 / meanServTimes[c]);			
				if (numServers > 1 && totQueTokCnt > 1)  {  // "-/M/n-PS" queues
					double conc = (totQueTokCnt <= numServers) ? totQueTokCnt : numServers;
					for (int c = 0; c < numColors; c++)  															
						meanServRates[c] = meanServRates[c] * conc;
				}					
				double totServRate = 0;
				for (int c = 0; c < numColors; c++)  
					totServRate += meanServRates[c];
				
				double[] pdf = new double[numColors];
				for (int c = 0; c < numColors; c++)
					pdf[c] = meanServRates[c] / totServRate;
				
				((Exponential) randServTimeGen[0]).setState(totServRate);													
				randColorGen.setState2(pdf);			 
			
				double servTime = randServTimeGen[0].nextDouble();
				if (servTime < 0) servTime = 0;
				int color = randColorGen.nextInt();
			
				Simulator.scheduleEvent(Simulator.clock + servTime, this, new Token(((Token)queueTokens[color].getFirst()).arrivalTS, color));
				eventScheduled = true;
			}
			else {  // Non-exponential service times
				// Find token with minimal residual service time (RST)
				double curRST, minRST = -1;				
				int numTk;
				for (int c = 0; c < numColors; c++) {
					numTk = queueTokenPop[c];  // = residServTimes[c].size();
					if (numTk > 0) 
						for (int i = 0; i < numTk; i++) {
							curRST = residServTimes[c].get(i);
							if (minRST == -1 || curRST < minRST) { 
								minRST = curRST; 
								tkSchedCol = c; tkSchedPos = i;
							}							
						}						
				}
				if (minRST == -1)  { // DEBUG
					Simulator.logln("Error: Illegal state in queue " + name);
					throw new SimQPNException();
				}												
				double servTime = minRST * totQueTokCnt;  // Default for "-/G/1-PS" queue 								
				if (numServers > 1 && totQueTokCnt > 1)   // "-/G/n-PS" queues 					
					servTime /= ((totQueTokCnt <= numServers) ? totQueTokCnt : numServers);
				Simulator.scheduleEvent(Simulator.clock + servTime, this, new Token(((Token)queueTokens[tkSchedCol].get(tkSchedPos)).arrivalTS, tkSchedCol));
				lastEventClock = Simulator.clock;	
				lastEventTkCnt = totQueTokCnt;
				eventScheduled = true;
			}
		}		
		eventsUpToDate = true;
	}
	
	/**
	 * Method clearEvents (Used only for PS queues) - clears all scheduled service completion events for this queue.  
	 * 
	 * @param 
	 * @return
	 * @exception
	 */
	public void clearEvents() {
		// Remove scheduled event from the event list. 
		// Note that a maximum of one event can be scheduled per PS QueueingPlace at a time.
		int i = Simulator.eventList.size() - 1;
		while (i >= 0) {
			Event ev = (Event) Simulator.eventList.get(i);
			if (ev.qPlace == this) {
				Simulator.eventList.remove(i); 
//				System.out.println("DEBUG: Removing scheduled event for QueueingPlace"); 
				break; 
			}
			else i--;				
		}
		eventScheduled = false;
	}

	/**
	 * Method updateResidServTimes - updates token residual times.
	 * Used only for PS queues with non-exp service times.
	 * 
	 * @param 
	 * @return
	 * @exception
	 */
	public void updateResidServTimes() {		
		int numTk;
		double curRST;
		double timeServed = (Simulator.clock - lastEventClock) / lastEventTkCnt;			// Default for "-/G/1-PS"
		if (numServers > 1 && lastEventTkCnt > 1) 					
			timeServed *= ((lastEventTkCnt <= numServers) ? lastEventTkCnt : numServers);	// "-/G/n-PS" queues
		/* NOTE: Alternative code:
		double timeServed = Simulator.clock - lastEventClock;								
		if (numServers < lastEventTkCnt) 			
			timeServed *= ((double) numServers) / lastEventTkCnt; 
		*/													
		for (int c = 0; c < numColors; c++) {
			numTk = residServTimes[c].size();  // NOTE: don't use queueTokenPop[c] here! If tokens have been added, this would mess things up. 
			if (numTk > 0) 
				for (int i = 0; i < numTk; i++) {					
					curRST = residServTimes[c].get(i) - timeServed;				
					residServTimes[c].set(i, curRST);
				}						
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
			queueStats.updateTkPopStats(color, queueTokenPop[color], count);																	
		 				
		queueTokenPop[color] += count;
		
		if (queueDiscip == IS) {
			// Schedule service completion events						
			for (int i = 0; i < count; i++) {
				double servTime = randServTimeGen[color].nextDouble();	
				if (servTime < 0) servTime = 0;
				Simulator.scheduleEvent(Simulator.clock + servTime, this, new Token(Simulator.clock, color));								
			}								 								
		}
		else if (queueDiscip == FCFS) {
			int n = 0;
			while (n < count && numBusyServers < numServers) {
				// Schedule service completion event
				double servTime = randServTimeGen[color].nextDouble();
				if (servTime < 0) servTime = 0;
				Simulator.scheduleEvent(Simulator.clock + servTime, this, new Token(Simulator.clock, color));
				numBusyServers++; n++;
				// Update Stats
				if (statsLevel >= 3)   
					queueStats.updateDelayTimeStats(color, 0);																 
			}						
			while (n < count) {
				//  Place the rest of the tokens in the waitingLine
				waitingLine.addLast(new Token(Simulator.clock, color));				
				n++;					
			}						
		}
		else if (queueDiscip == PS) {
			if (!expPS) {
				if (eventScheduled)	updateResidServTimes();	//NOTE: WATCH OUT! Method should be called before the new tokens have been added to residServTimes!  
				for (int i = 0; i < count; i++)  {
					double servTime = randServTimeGen[color].nextDouble();
					if (servTime < 0) servTime = 0;
					residServTimes[color].add(servTime);	//SDK-TODO: Consider storing this info in queueTokens inside the Token objects; might be more efficient than using separate ArrayLists  			
				}				
			}														 						
			for (int i = 0; i < count; i++) 
				queueTokens[color].addLast(new Token(Simulator.clock, color));
			if (eventScheduled) clearEvents();	// NOTE: clearEvents() resets eventScheduled to false; 
			eventsUpToDate = false;
		}
		else {
			Simulator.logln("Error: Invalid queueing discipline for QueueingPlace " + name);
			throw new SimQPNException();
		}		
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
			queueStats.updateTkPopStats(token.color, queueTokenPop[token.color], -1);
			if (statsLevel >= 3) 
				queueStats.updateSojTimeStats(token.color, Simulator.clock - token.arrivalTS);
		}
		
		// Now remove token from queue and update queue state
		queueTokenPop[token.color]--;
				
		if (queueDiscip == IS) {
			// Nothing to do				 								
		}
		else if (queueDiscip == FCFS) {
			if (waitingLine.size() > 0) {
				Token tk = (Token) waitingLine.removeFirst();
				double servTime = randServTimeGen[tk.color].nextDouble();	
				if (servTime < 0) servTime = 0;
				Simulator.scheduleEvent(Simulator.clock + servTime, this, tk);
				// Update stats				
				if (statsLevel >= 3)
					queueStats.updateDelayTimeStats(tk.color, Simulator.clock - tk.arrivalTS);				
			}
			else numBusyServers--;							
		}
		else if (queueDiscip == PS) {
			if (expPS) {
				queueTokens[token.color].removeFirst();
			}
			else {
				queueTokens[tkSchedCol].remove(tkSchedPos);
				residServTimes[tkSchedCol].remove(tkSchedPos);
				updateResidServTimes(); //NOTE: WATCH OUT! Method should be called after served token has been removed from residServTimes!   
			}
			eventScheduled = false;
			eventsUpToDate = false;
		} 
		else {
			Simulator.logln("Error: Invalid queueing discipline for QueueingPlace " + name);
			throw new SimQPNException();
		}

		// Finally move token to depository
		super.addTokens(token.color, 1);
		
	}
		
	public void report() throws SimQPNException  {		
		if (statsLevel > 0) {
			queueStats.printReport();
			super.report();					
		}
	}
	
}
