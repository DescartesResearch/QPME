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
 *  2008/11/22  Samuel Kounev     Created.
 *                                
 */

package de.tud.cs.simqpn.kernel;

import java.util.LinkedList;

import cern.jet.random.AbstractContinousDistribution;
import cern.jet.random.Empirical;
import cern.jet.random.EmpiricalWalker;
import cern.jet.random.Exponential;

/**
 * Class Queue
 *
 * Note: We use the term queue to refer to a queueing station including both the waiting area and the servers.
 * Note: We assume that in the beginning of the run, the queue is empty!
 * 
 * @author Samuel Kounev
 * @version %I%, %G%
 */

public class Queue {	
    
	// Supported Queueing Disciplines:	
	public static final int IS = 0;
	public static final int FCFS = 1;
	public static final int PS = 2;	

	public int 			id;					// Global id of the queue.
	public String		name;				// Name of the queue.
	public int			queueDiscip;		// Queueing discipline.
		
	public QueueingPlace[] 
	                    queueingPlaces;		// Queueing places this queue is part of.
	public int			totNumColors;		// Total number of token colors over all queueing places the queue is part of. 
	
	public int			numServers;			// FCFS queues: Number of servers in queueing station.
	public int			numBusyServers;		// FCFS queues: Number of currently busy servers.
	public LinkedList	waitingLine;		// FCFS queues: List of tokens waiting for service (waiting area of the queue).			 								
	
	public boolean		eventsUpToDate;		// PS queues: True if currently scheduled events for this queue (if any) 
											//            reflect the latest token popolation of the queue.											
	public boolean		eventScheduled;		// PS queues: True if there is currently a service completion event scheduled for this queue.

	public boolean 		expPS;				// PS queues: true  = Processor-Sharing with exponential service times.
											//            false = Processor-Sharing with non-exponential service times.	                                        

	public int			tkSchedPl;			// PS queues: expPS==false: Queueing place containing the next token scheduled to complete service.	
	public int			tkSchedCol;			// PS queues: expPS==false: Color of the next token scheduled to complete service.
	public int			tkSchedPos;			// PS queues: expPS==false: Index in QueueingPlace.queueTokens[tkSchedCol] of the next token scheduled to complete service.	
	public double		lastEventClock;		// PS queues: expPS==false: Time of the last event scheduling, i.e. time of the last event with effect on this queue.		
	public int			lastEventTkCnt;		// PS queues: expPS==false: Token population at the time of the last event scheduling.
	public AbstractContinousDistribution[]
	             		expRandServTimeGen;	// PS queues: expPS==true: Random number generators for generating service times.
	public EmpiricalWalker					
						randColorGen;		// PS queues: expPS==true: Random number generator for generating token colors.
	
	
	/**
	 * Constructor
	 *
	 * @param id                  - global id of the queue
	 * @param name                - name of the queue
	 * @param queueDiscip         - queueing discipline
	 * @param numServers          - number of servers in queue
	 */
	public Queue(int id, String name, int queueDiscip, int numServers) throws SimQPNException {
		this.id							= id;
		this.name						= name;
		this.queueDiscip				= queueDiscip; 
		this.numServers 				= numServers; 		
		this.queueingPlaces				= null;
		
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
		}
	}
		
	/**
	 * Method addQueueingPlace
	 * 
	 * NOTE:  
	 * 
	 * @param 
	 * @return
	 * @exception
	 */
	public void addQueueingPlace(QueueingPlace qPl) throws SimQPNException {		
		if (queueingPlaces == null)  { 
			queueingPlaces = new QueueingPlace[1];
			queueingPlaces[0] = qPl;
		}
		else  {			
			QueueingPlace[]	queueingPlacesTMP = queueingPlaces;			
			queueingPlaces = new QueueingPlace[queueingPlacesTMP.length+1];			
			System.arraycopy(queueingPlacesTMP, 0, queueingPlaces, 0, queueingPlacesTMP.length);			
			queueingPlaces[queueingPlaces.length-1] = qPl;
		}
	}
	
	/**
	 * Method init
	 * 
	 * NOTE: Should be called after the queueingPlaces array has been initialized.
	 * 
	 * @param 
	 * @return
	 * @exception
	 */
	public void init() throws SimQPNException {
		for (int p = 0; p < queueingPlaces.length; p++) 
			totNumColors += queueingPlaces[p].numColors;

		// PS Queues: final initializations	
		if (queueDiscip == PS && expPS)  {						
			expRandServTimeGen		= new Exponential[1];
			expRandServTimeGen[0]	= new Exponential(0, Simulator.nextRandNumGen());			
			double[] pdf = new double[totNumColors];
			for (int c = 0; c < totNumColors; c++) pdf[c] = 1;			
			randColorGen = new EmpiricalWalker(pdf, Empirical.NO_INTERPOLATION, Simulator.nextRandNumGen());							
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
		for (int p=0, nC=0; p < queueingPlaces.length; p++)  {
			nC = queueingPlaces[p].numColors; 
			for (int c = 0; c < nC; c++)  
				totQueTokCnt += queueingPlaces[p].queueTokenPop[c];			
		}
		
		if (totQueTokCnt > 0) {			
			if (expPS) {  // Exponential service times				
				double[] meanServRates = new double[totNumColors];
				double conc = 1;
				if (numServers > 1 && totQueTokCnt > 1)     // "-/M/n-PS" queues
					conc = (totQueTokCnt <= numServers) ? totQueTokCnt : numServers;															
				for (int p=0, nC=0, i=0; p < queueingPlaces.length; p++)  {
					nC = queueingPlaces[p].numColors; 
					for (int c=0; c < nC; c++)
						meanServRates[i++] = (((double) queueingPlaces[p].queueTokenPop[c]) / totQueTokCnt) * 
												(1 / queueingPlaces[p].meanServTimes[c]) * conc;
				}				
				double totServRate = 0;
				for (int i = 0; i < totNumColors; i++)  
					totServRate += meanServRates[i];				
				double[] pdf = new double[totNumColors];
				for (int i = 0; i < totNumColors; i++)
					pdf[i] = meanServRates[i] / totServRate;
				
				((Exponential) expRandServTimeGen[0]).setState(totServRate);													
				randColorGen.setState2(pdf);			 
				double servTime = expRandServTimeGen[0].nextDouble();
				if (servTime < 0) servTime = 0;
				int color = randColorGen.nextInt();
			
				for (int p=0, nC=0, i=0; p < queueingPlaces.length; p++)  {																			
					nC = queueingPlaces[p].numColors; 
					for (int c = 0; c < nC; c++, i++)
						if (i == color) {
							Simulator.scheduleEvent(Simulator.clock + servTime, this, (Token) queueingPlaces[p].queueTokens[c].getFirst()); 
							break;
						}
					if (i == color) break;
				}				
				eventScheduled = true;
			}
			else {  // Non-exponential service times
				// Find token with minimal residual service time (RST)
				double curRST, minRST = -1;				
				int numTk;
				for (int p=0, nC=0; p < queueingPlaces.length; p++)  {
					nC = queueingPlaces[p].numColors;					
					for (int c=0; c < nC; c++)  {
						numTk = queueingPlaces[p].queueTokenPop[c];  // = residServTimes[c].size();
						if (numTk > 0) 
							for (int i = 0; i < numTk; i++) {
								curRST = queueingPlaces[p].residServTimes[c].get(i);
								if (minRST == -1 || curRST < minRST) { 
									minRST = curRST;
									tkSchedPl = p; tkSchedCol = c; tkSchedPos = i;
								}							
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
				Simulator.scheduleEvent(Simulator.clock + servTime, this, (Token) queueingPlaces[tkSchedPl].queueTokens[tkSchedCol].get(tkSchedPos));
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
			if (ev.queue == this) {
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
		for (int p=0, nC=0; p < queueingPlaces.length; p++)  {
			nC = queueingPlaces[p].numColors; 
			for (int c=0; c < nC; c++)  {				
				numTk = queueingPlaces[p].residServTimes[c].size();  // NOTE: don't use queueTokenPop[c] here! If tokens have been added, this would mess things up. 
				if (numTk > 0) 
					for (int i = 0; i < numTk; i++) {					
						curRST = queueingPlaces[p].residServTimes[c].get(i) - timeServed;				
						queueingPlaces[p].residServTimes[c].set(i, curRST);
					}										
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
	public void addTokens(QueueingPlace qPl, int color, int count) throws SimQPNException {				 				
				
		if (queueDiscip == IS) {
			// Schedule service completion events						
			for (int i = 0; i < count; i++) {
				double servTime = qPl.randServTimeGen[color].nextDouble();	
				if (servTime < 0) servTime = 0;
				Simulator.scheduleEvent(Simulator.clock + servTime, this, new Token(qPl, Simulator.clock, color));								
			}								 								
		}
		else if (queueDiscip == FCFS) {
			int n = 0;
			while (n < count && numBusyServers < numServers) {
				// Schedule service completion event
				double servTime = qPl.randServTimeGen[color].nextDouble();
				if (servTime < 0) servTime = 0;
				Simulator.scheduleEvent(Simulator.clock + servTime, this, new Token(qPl, Simulator.clock, color));
				numBusyServers++; n++;
				// Update Stats
				if (qPl.statsLevel >= 3)   
					qPl.queueingPlaceStats.updateDelayTimeStats(color, 0);																 
			}						
			while (n < count) {
				//  Place the rest of the tokens in the waitingLine
				waitingLine.addLast(new Token(qPl, Simulator.clock, color));				
				n++;					
			}						
		}
		else if (queueDiscip == PS) {
			if (!expPS) {
				if (eventScheduled)	updateResidServTimes();	//NOTE: WATCH OUT! Method should be called before the new tokens have been added to residServTimes!  
				for (int i = 0; i < count; i++)  {
					double servTime = qPl.randServTimeGen[color].nextDouble();
					if (servTime < 0) servTime = 0;
					qPl.residServTimes[color].add(servTime);	//SDK-TODO: Consider storing this info in queueTokens inside the Token objects; might be more efficient than using separate ArrayLists  			
				}				
			}														 						
			for (int i = 0; i < count; i++) 
				qPl.queueTokens[color].addLast(new Token(qPl, Simulator.clock, color));
			if (eventScheduled) clearEvents();	// NOTE: clearEvents() resets eventScheduled to false; 
			eventsUpToDate = false;
		}
		else {
			Simulator.logln("Error: Invalid queueing discipline for QueueingPlace " + name);
			throw new SimQPNException();
		}		
	}
	
	/**
	 * Method completeService - completes service of a token and schedules next waiting token for service.                      
	 *
	 * @param token - token completing service
	 * @return
	 * @exception
	 */
	public void completeService(Token token) throws SimQPNException {
				
		if (queueDiscip == IS) {
			// Nothing to do				 								
		}
		else if (queueDiscip == FCFS) {
			if (waitingLine.size() > 0) {
				Token tk = (Token) waitingLine.removeFirst();				
				QueueingPlace qPl = (QueueingPlace) tk.place;				
				double servTime = qPl.randServTimeGen[tk.color].nextDouble();	
				if (servTime < 0) servTime = 0;
				Simulator.scheduleEvent(Simulator.clock + servTime, this, tk);
				// Update stats				
				if (qPl.statsLevel >= 3)
					qPl.queueingPlaceStats.updateDelayTimeStats(tk.color, Simulator.clock - tk.arrivalTS);				
			}
			else numBusyServers--;							
		}
		else if (queueDiscip == PS) {
			QueueingPlace qPl = ((QueueingPlace) token.place);
			if (expPS) {
				qPl.queueTokens[token.color].removeFirst();
			}
			else {				
				queueingPlaces[tkSchedPl].queueTokens[tkSchedCol].remove(tkSchedPos); //TODO: Do we need tkSchedXXX?
				queueingPlaces[tkSchedPl].residServTimes[tkSchedCol].remove(tkSchedPos);
				updateResidServTimes(); //NOTE: WATCH OUT! Method should be called after served token has been removed from residServTimes!   
			}
			eventScheduled = false;
			eventsUpToDate = false;
		} 
		else {
			Simulator.logln("Error: Invalid queueing discipline for QueueingPlace " + name);
			throw new SimQPNException();
		}		
	}
			
}
