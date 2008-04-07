/*
 * Copyright (c) 2006 Samuel Kounev. All rights reserved.
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
 * Note: We assume that in the beginning of the run the queue is empty!
 * 
 * @author Samuel Kounev
 * @version %I%, %G%
 */

public class QueueingPlace extends Place {	
    
	// Supported Queueing Disciplines:	
	public static final int IS = 0;
	public static final int FCFS = 1;
	public static final int PS = 2;	
		
	public int			queueDiscip;		
	public double[]		meanServTimes;		
	
	public int[]		queueTokenPop;		 
											
	
	public int			numServers;			
	public int			numBusyServers;		
	public LinkedList	waitingLine;			 								
	
	public boolean		eventsUpToDate;		 
											
	public boolean		eventScheduled;		 
	public LinkedList[]	queueTokens;		
										
	public boolean 		expPS;				
	                                        
	public AbstractDoubleList[]
						residServTimes;		
	public int			tkSchedCol;			   
	public int			tkSchedPos;			
	public double		lastEventClock;		
	public int			lastEventTkCnt;		
	
	public AbstractContinousDistribution[]
						randServTimeGen;												
	public EmpiricalWalker					
						randColorGen;		
	
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
			if (meanServTimes[c] < 0) {
				Simulator.logln("Error: meanServTimes[" + c + "] has not been initialized for QueueingPlace " + name);
				throw new SimQPNException(); 
			}			
			
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
					residServTimes[c] = new DoubleArrayList(100);
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
			queueStats.finish(queueTokenPop);								
			super.finish();
		}
	}
	
	/**
	 * Method updateEvents 
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
			if (expPS) {
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
			else { 
				double curRST, minRST = -1;				
				int numTk;
				for (int c = 0; c < numColors; c++) {
					numTk = queueTokenPop[c];  
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
				double servTime = minRST * totQueTokCnt;  								
				if (numServers > 1 && totQueTokCnt > 1)   					
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
	 * Method clearEvents 
	 * 
	 * @param 
	 * @return
	 * @exception
	 */
	public void clearEvents() {		
		int i = Simulator.eventList.size() - 1;
		while (i >= 0) {
			Event ev = (Event) Simulator.eventList.get(i);
			if (ev.qPlace == this) {
				Simulator.eventList.remove(i); 
				break; 
			}
			else i--;				
		}
		eventScheduled = false;
	}

	/**
	 * Method updateResidServTimes 
	 * @param 
	 * @return
	 * @exception
	 */
	public void updateResidServTimes() {		
		int numTk;
		double curRST;
		for (int c = 0; c < numColors; c++) {
			numTk = residServTimes[c].size();  
			if (numTk > 0) 
				for (int i = 0; i < numTk; i++) {					
					curRST = residServTimes[c].get(i);										
					double timeServed = (Simulator.clock - lastEventClock) / lastEventTkCnt; 
					if (numServers > 1 && lastEventTkCnt > 1) 					
						timeServed *= ((lastEventTkCnt <= numServers) ? lastEventTkCnt : numServers);					
					curRST -= timeServed;					
					residServTimes[c].set(i, curRST);
				}						
		}		
	}
	
	/**
	 * Method addTokens
	 *  
	 */
	@SuppressWarnings("unchecked")
	public void addTokens(int color, int count) throws SimQPNException {	
		if (count <= 0) { // DEBUG: To be removed later
			Simulator.logln("Error: Attempted to add nonpositive number of tokens to queue " + name);
			throw new SimQPNException();
		}

		if (statsLevel > 0)
			queueStats.updateTkPopStats(color, queueTokenPop[color], count);																	
		 				
		queueTokenPop[color] += count;
		
		if (queueDiscip == IS) {					
			for (int i = 0; i < count; i++) {
				double servTime = randServTimeGen[color].nextDouble();	
				if (servTime < 0) servTime = 0;
				Simulator.scheduleEvent(Simulator.clock + servTime, this, new Token(Simulator.clock, color));								
			}								 								
		}
		else if (queueDiscip == FCFS) {
			int n = 0;
			while (n < count && numBusyServers < numServers) {
				double servTime = randServTimeGen[color].nextDouble();
				if (servTime < 0) servTime = 0;
				Simulator.scheduleEvent(Simulator.clock + servTime, this, new Token(Simulator.clock, color));
				numBusyServers++; n++;
				if (statsLevel >= 3)   
					queueStats.updateDelayTimeStats(color, 0);																 
			}						
			while (n < count) {					 
				waitingLine.addLast(new Token(Simulator.clock, color));				
				n++;					
			}						
		}
		else if (queueDiscip == PS) {
			if (!expPS) {
				if (eventScheduled)	updateResidServTimes(); 
				for (int i = 0; i < count; i++)  {
					double servTime = randServTimeGen[color].nextDouble();
					if (servTime < 0) servTime = 0;
					residServTimes[color].add(servTime);  			
				}				
			}														 						
			for (int i = 0; i < count; i++) 
				queueTokens[color].addLast(new Token(Simulator.clock, color));
			if (eventScheduled) clearEvents(); 
			eventsUpToDate = false;
		}
		else {
			Simulator.logln("Error: Invalid queueing discipline for QueueingPlace " + name);
			throw new SimQPNException();
		}		
	}
	
	/**
	 * Method completeService  
	 *                        
	 * 
	 * @return
	 * @exception
	 */
	public void completeService(Token token) throws SimQPNException {
		if (queueTokenPop[token.color] < 1) {
			Simulator.logln("Error: Attempted to remove a token from queue " + name + " which is empty!");
			throw new SimQPNException();
		}
		 
		if (statsLevel > 0)  {
			queueStats.updateTkPopStats(token.color, queueTokenPop[token.color], -1);
			if (statsLevel >= 3) 
				queueStats.updateSojTimeStats(token.color, Simulator.clock - token.arrivalTS);
		}
		
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
				updateResidServTimes();   
			}
			eventScheduled = false;
			eventsUpToDate = false;
		} 
		else {
			Simulator.logln("Error: Invalid queueing discipline for QueueingPlace " + name);
			throw new SimQPNException();
		}
		
		super.addTokens(token.color, 1);
		
	}
		
	public void report() throws SimQPNException  {		
		if (statsLevel > 0) {
			queueStats.printReport();
			super.report();					
		}
	}
	
}
