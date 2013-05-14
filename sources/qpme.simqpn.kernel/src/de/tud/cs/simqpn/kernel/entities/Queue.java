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
 * Contributor(s):      Frederik Zipp, Simon Spinner  
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2008/11/22  Samuel Kounev     Created.
 *  2008/12/13  Samuel Kounev     Fixed a bug in updateEvents() for expPS==true.
 *  2009/02/13  Samuel Kounev     Changed eventList to use PriorityQueue instead of LinkedList
 *                                to speed up searches in the event list.
 *  2009/04/08  Samuel Kounev     Added a check in clearEvents() to make sure that events are removed from the event lists.
 *  2009/08/03  Frederik Zipp     Added xmlId property. 
 *  2009/05/05  Frederik Zipp     Support for central queues.
 *  2010/07/24  Simon Spinner	  Support for tracking tokens.
 * 
 */

package de.tud.cs.simqpn.kernel.entities;

import java.util.LinkedList;

import org.apache.log4j.Logger;

import cern.jet.random.AbstractContinousDistribution;
import cern.jet.random.Empirical;
import cern.jet.random.EmpiricalWalker;
import cern.jet.random.Exponential;
import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.SimQPNController;
import de.tud.cs.simqpn.kernel.executor.QueueEvent;
import de.tud.cs.simqpn.kernel.random.RandomNumberGenerator;
import de.tud.cs.simqpn.kernel.stats.QueueStats;

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
	
	private static Logger log = Logger.getLogger(Queue.class);

	public int 			id;					// Global id of the queue.
	public String		xmlId;				// XML ID 
	public String		name;				// Name of the queue.
	public int			queueDiscip;		// Queueing discipline.
		
	public QPlace[]		qPlaces;			// Queueing places this queue is part of.
	public int			totNumColors;		// Total number of token colors over all queueing places the queue is part of.
	int					statsLevel;			// The minimum statsLevel of all queueing places the queue is part of.
											// NOTE: we set statsLevel to the minimum here because currently some of statistics we compute are based on corresponding statistics from the QPlaces the queue is part of.   
	
	public int			numServers;			// FCFS queues: Number of servers in queueing station.
	public int			numBusyServers;		// FCFS queues: Number of currently busy servers.
	public LinkedList<Token>	waitingLine;		// FCFS queues: List of tokens waiting for service (waiting area of the queue).			 								
	
	public boolean		eventsUpToDate;		// PS queues: True if currently scheduled events for this queue (if any) 
											//            reflect the latest token popolation of the queue.											
	public boolean		eventScheduled;		// PS queues: True if there is currently a service completion event scheduled for this queue.
	public QueueEvent		nextEvent;			// PS queues: Next service completion event scheduled for this queue.
	public boolean 		expPS;				// PS queues: true  = Processor-Sharing with exponential service times.
											//            false = Processor-Sharing with non-exponential service times.	                                        
	public int			tkSchedPl;			// PS queues: expPS==false: Queueing place containing the next token scheduled to complete service.	
	public int			tkSchedCol;			// PS queues: expPS==false: Color of the next token scheduled to complete service.
	public int			tkSchedPos;			// PS queues: expPS==false: Index in QPlace.queueTokArrivTS[tkSchedCol] and QPlace.queueTokResidServTimes[tkSchedCol] of the next token scheduled to complete service.	
	public double		lastEventClock;		// PS queues: expPS==false: Time of the last event scheduling, i.e. time of the last event with effect on this queue.		
	public int			lastEventTkCnt;		// PS queues: expPS==false: Token population at the time of the last event scheduling.
	public AbstractContinousDistribution[]
	             		expRandServTimeGen;	// PS queues: expPS==true: Random number generators for generating service times.
	public EmpiricalWalker					
						randColorGen;		// PS queues: expPS==true: Random number generator for generating token colors.
	
	public QueueStats	queueStats;			// Object containing statistics for this queue.
	
	private long		tkPopulation;		// The current number of tokens residing in the queue
	private long		maxEpochPopulation;	// Overflow Detection: the maximum token population in the current epoch
	private long		totalMaxPopulation; // Overflow Detection: the total maximum token population in the queue
	private int			epochMsrmCnt;		// Overflow Detection: the number of measurements that were taken in the current epoch
	private long		epochLength = SimQPNConfiguration.OVERFLOW_DET_START_EPOCH_LENGTH;	// Overflow Detection: the length (number of measurements) of one epoch
	private long		maxPopulationAtRisingStart;
	private int			cntConsRisingEpoch; // Overflow Detection: the number of consecutive epochs in which the maximum population has grown
	private boolean		deactivateWarning = false;;
	
	/**
	 * Constructor
	 *
	 * @param id                  - global id of the queue
	 * @param name                - name of the queue
	 * @param queueDiscip         - queueing discipline
	 * @param numServers          - number of servers in queue
	 */	
	public Queue(int id, String xmlId, String name, int queueDiscip, int numServers) throws SimQPNException {

		this.id							= id;
		this.xmlId						= xmlId;
		this.name						= name;
		this.queueDiscip				= queueDiscip; 		
		this.numServers 				= numServers; 		
		this.qPlaces					= null;
		this.totNumColors				= 0;
		this.statsLevel					= 0;
		
		// FCFS Queues			
		if (queueDiscip == FCFS)  {			
			this.numBusyServers = 0;
			this.waitingLine 	= new LinkedList<Token>();			 
		}
		// PS Queues			
		if (queueDiscip == PS) {			 
			eventsUpToDate	= false;
			eventScheduled	= false;
			expPS		  	= false; // By default non-exponential queue is assumed.
		}
	}
		
	/**
	 * Method addQPlace - adds a queueing place to the list of queueing places this queue is part of.
	 * 
	 * NOTE:  
	 * 
	 * @param 
	 * @return
	 * @exception
	 */
	public void addQPlace(QPlace qPl) throws SimQPNException {		
		if (qPlaces == null)  { 
			qPlaces = new QPlace[1];
			qPlaces[0] = qPl;
		}
		else  {			
			QPlace[] qPlacesTMP = qPlaces;			
			qPlaces = new QPlace[qPlacesTMP.length+1];			
			System.arraycopy(qPlacesTMP, 0, qPlaces, 0, qPlacesTMP.length);			
			qPlaces[qPlaces.length-1] = qPl;
		}
	}
	
	/**
	 * Method init
	 * 
	 * NOTE: Should be called after the qPlaces array has been initialized before the run has started.
	 * 
	 * @param 
	 * @return
	 * @exception
	 */
	public void init(SimQPNController sim) throws SimQPNException  {
		statsLevel = 10; 
		
		if (qPlaces == null) {
			log.error("No qplaces associated with queue " + name);
			throw new SimQPNException();
		}
		
		for (int p = 0; p < qPlaces.length; p++)  { //NOTE: The two variables below are intentionally set here and not in addQPlace(), since the user might choose (although that's not recommended) to initialize qPlaces externally bypassing addQPlace().  
			totNumColors += qPlaces[p].numColors;		
			if (qPlaces[p].statsLevel < statsLevel) 
				statsLevel = qPlaces[p].statsLevel;
		}
		// PS Queues
		if (queueDiscip == PS && expPS)  {						
			expRandServTimeGen		= new Exponential[1];
			expRandServTimeGen[0]	= new Exponential(0, RandomNumberGenerator.nextRandNumGen());			
			double[] pdf = new double[totNumColors];
			for (int c = 0; c < totNumColors; c++) pdf[c] = 1;			
			randColorGen = new EmpiricalWalker(pdf, Empirical.NO_INTERPOLATION, RandomNumberGenerator.nextRandNumGen());							
		}		
		if (statsLevel > 0)  //NOTE: This is intentionally done here after qPlaces has been initialized!
			queueStats = new QueueStats(id, name, totNumColors, statsLevel, queueDiscip, numServers, this, sim);
	}
		
	/**
	 * Method start - Begin statistics collection 
	 * 	 
	 * @param 
	 * @return
	 * @exception
	 */
	public void start(double clock) throws SimQPNException {	
		if (statsLevel > 0)	
			queueStats.start(clock);					
	}
	
	/**
	 * Method finish - Complete statistics collection  	    
	 * 	 
	 * @param 
	 * @return
	 * @exception
	 */
	public void finish(double runWallClockTime, double clock) throws SimQPNException { 
		if (statsLevel > 0)	
			queueStats.finish(clock, runWallClockTime);					
	}
	
	/**
	 * Method report
	 *  	    
	 * @param 
	 * @return
	 * @exception
	 */
	public void report() throws SimQPNException  {
		if (statsLevel > 0) 
			queueStats.printReport();					
	}
		
	/**
	 * Method updateEvents (Used only for PS queues).
	 * Schedules next service completion event (if any) according to current token population.
	 * 
	 * JUERGEN: calls scheduleEvent()
	 * 
	 * @param 
	 * @return
	 * @exception
	 */
	public void updateEvents(SimQPNController sim) throws SimQPNException {
		if (eventsUpToDate) return;

		int totQueTokCnt = 0;
		for (int p=0, nC=0; p < qPlaces.length; p++)  {
			nC = qPlaces[p].numColors; 
			for (int c=0; c < nC; c++)  
				totQueTokCnt += qPlaces[p].queueTokenPop[c];			
		}
		
		if (totQueTokCnt > 0) {			
			if (expPS) {  // Exponential service times				
				double[] meanServRates = new double[totNumColors];
				double conc = 1;
				if (numServers > 1 && totQueTokCnt > 1)     // "-/M/n-PS" queues
					conc = (totQueTokCnt <= numServers) ? totQueTokCnt : numServers;															
				for (int p=0, nC=0, i=0; p < qPlaces.length; p++)  {
					nC = qPlaces[p].numColors; 
					for (int c=0; c < nC; c++)
						meanServRates[i++] = (((double) qPlaces[p].queueTokenPop[c]) / totQueTokCnt) * 
												((double) 1 / qPlaces[p].meanServTimes[c]) * conc;
				}				
				double totServRate = 0;
				for (int i=0; i < totNumColors; i++)  
					totServRate += meanServRates[i];				
				double[] pdf = new double[totNumColors];
				for (int i=0; i < totNumColors; i++)
					pdf[i] = meanServRates[i] / totServRate;
				
				((Exponential) expRandServTimeGen[0]).setState(totServRate);													
				randColorGen.setState2(pdf);			 
				double servTime = expRandServTimeGen[0].nextDouble();
				if (servTime < 0) servTime = 0;
				int color = randColorGen.nextInt();
			
				boolean done = false;
				for (int p=0, nC=0, i=0; p < qPlaces.length; p++)  {																			
					nC = qPlaces[p].numColors; 
					for (int c=0; c < nC; c++, i++)  {
						if (i == color) {
							if (qPlaces[p].queueTokens[c] != null) {
								sim.scheduleEvent(sim.clock + servTime, this, (Token) qPlaces[p].queueTokens[c].get(0));
							} else {
								sim.scheduleEvent(sim.clock + servTime, this, new Token(qPlaces[p], c));
							}
							done = true;
							break;
						}
					}
					if (done) break;
				}				
				eventScheduled = true;
			}
			else {  // Non-exponential service times
				// Find token with minimal residual service time (RST)
				double curRST, minRST = -1;				
				int numTk;
				for (int p=0, nC=0; p < qPlaces.length; p++)  {
					nC = qPlaces[p].numColors;					
					for (int c=0; c < nC; c++)  {
						numTk = qPlaces[p].queueTokenPop[c];  // = queueTokResidServTimes[c].size();
						if (numTk > 0) 
							for (int i = 0; i < numTk; i++) {
								curRST = qPlaces[p].queueTokResidServTimes[c].get(i);
								if (minRST == -1 || curRST < minRST) { 
									minRST = curRST;
									tkSchedPl = p; tkSchedCol = c; tkSchedPos = i;
								}							
							}						
					}
				}				
				if (minRST == -1)  { // DEBUG
					log.error("Illegal state in queue " + name);
					throw new SimQPNException();
				}				
				double servTime = minRST * totQueTokCnt;  // Default for "-/G/1-PS" queue 								
				if (numServers > 1 && totQueTokCnt > 1)   // "-/G/n-PS" queues 					
					servTime /= ((totQueTokCnt <= numServers) ? totQueTokCnt : numServers);
				if (qPlaces[tkSchedPl].queueTokens[tkSchedCol] != null) {
					sim.scheduleEvent(sim.clock + servTime, this, (Token) qPlaces[tkSchedPl].queueTokens[tkSchedCol].get(tkSchedPos));
				} else {
					sim.scheduleEvent(sim.clock + servTime, this, new Token(qPlaces[tkSchedPl], tkSchedCol));
				}
				lastEventClock = sim.clock;	
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
	public void clearEvents() throws SimQPNException {
		// Remove scheduled event from the event list. 
		// Note that a maximum of one event can be scheduled per PS QPlace at a time.
		
		/* WARNING: Potential problems with the use of eventList.remove() below because of the following bug:
		 * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6207984
		 * http://bugs.sun.com/bugdatabase/view_bug.do;jsessionid=859a6e381a7abfffffffff7e644d05a59d93?bug_id=6268068
		 * 
		 * On old JVMs that do not have the above bug fixed, if two events have the exact same time, the wrong one might be removed!   
		 */
		if (!SimQPNController.eventList.remove(nextEvent))  {		
			log.error("Failed to remove scheduled event from event list!");
			throw new SimQPNException();
		}			
		eventScheduled = false;
	
		/* Old LinkedList implementation of the event list.
		int i = Simulator.eventList.size() - 1;
		while (i >= 0) {
			Event ev = (Event) Simulator.eventList.get(i);
			if (ev.queue == this) {
				Simulator.eventList.remove(i); 
				break; 
			}
			else i--;				
		}
		eventScheduled = false;		
		*/
	}

	/**
	 * Method updateResidServTimes - updates token residual times.
	 * Used only for PS queues with non-exp service times.
	 * 
	 * @param 
	 * @return
	 * @exception
	 */
	public void updateResidServTimes(double clock) {		
		int numTk;
		double curRST;
		double timeServed = (clock - lastEventClock) / lastEventTkCnt;			// Default for "-/G/1-PS"		
		if (numServers > 1 && lastEventTkCnt > 1) 					
			timeServed *= ((lastEventTkCnt <= numServers) ? lastEventTkCnt : numServers);	// "-/G/n-PS" queues
		/* NOTE: Alternative code:
		double timeServed = Simulator.clock - lastEventClock;								
		if (numServers < lastEventTkCnt) 			
			timeServed *= ((double) numServers) / lastEventTkCnt; 
		*/		
		for (int p=0, nC=0; p < qPlaces.length; p++)  {
			nC = qPlaces[p].numColors; 
			for (int c=0; c < nC; c++)  {				
				numTk = qPlaces[p].queueTokResidServTimes[c].size();  // NOTE: don't use queueTokenPop[c] here! If tokens have been added, this would mess things up. 
				if (numTk > 0) 
					for (int i = 0; i < numTk; i++) {					
						curRST = qPlaces[p].queueTokResidServTimes[c].get(i) - timeServed;				
						qPlaces[p].queueTokResidServTimes[c].set(i, curRST);
					}										
			}
		}
	}

	//TODO: Consider Simulator.scheduleEvent() below for the case statsLevel < 3	

	/**
	 * Method addTokens - deposits N tokens of particular color
	 * 
	 * JUERGEN: calls scheduleEvent()
	 * 
	 * @param color - color of tokens
	 * @param count - number of tokens to deposit
	 * @param tokensToBeAdded - individual tokens (if tracking = true)
	 * @return
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	public void addTokens(QPlace qPl, int color, int count, Token[] tokensToBeAdded, SimQPNController sim) throws SimQPNException {	
		
		tkPopulation += count;
		
		// Overflow detection mechanism:
		// The following algorithm tries to determine an upper bound for the token
		// population in the queue. A number of measurements is grouped to epochs with dynamic
		// length. The length of an epoch is adjusted to the growth rate of the population.
		// If the maximum total population increases in several consecutive epochs a
		// warning is printed out.
		if (tkPopulation > maxEpochPopulation) maxEpochPopulation = tkPopulation;
		epochMsrmCnt++;
		
		if (sim.clock <= 1.0) {
			// Skip overflow detection at the beginning of the simulation.
			// No representative results can be determined during startup.
			cntConsRisingEpoch = 0;
			maxPopulationAtRisingStart = maxEpochPopulation;
		} else if (epochMsrmCnt >= epochLength) {
			// New maximum population?
			if (maxEpochPopulation > totalMaxPopulation) {
				totalMaxPopulation = maxEpochPopulation;
				cntConsRisingEpoch++;
			} else {
				// Maximum population of queue in current epoch is lower
				// than the total maximum. Reset consecutive epoch counter
				// and increase epoch length. If the population is growing indefinitely
				// (maybe with valleys) then there must be a value for epoch length, so that
				// in each epoch a new maximum is reached. Otherwise an upper bound is finally reached.
				cntConsRisingEpoch = 0;
				maxPopulationAtRisingStart = totalMaxPopulation;
				if ((epochLength * 2) <= SimQPNConfiguration.OVERFLOW_DET_MAX_EPOCH_LENGTH) {
					epochLength *= 2;
				}
			}
			maxEpochPopulation = 0;
			epochMsrmCnt = 0;
			
			if (totalMaxPopulation < SimQPNConfiguration.OVERFLOW_DET_DETECTION_THRESHOLD) {
				// If total population is below the detection threshold, do not
				// count consecutive rising epochs. Thus the overflow detection is
				// disabled if the queue population is low.
				maxPopulationAtRisingStart = totalMaxPopulation;
			} else {
				// Check whether an overflow warning is issued. The first
				// part of the condition is for fast growing queues, the second
				// part for slowly growing ones.
				if (((totalMaxPopulation > 10 * maxPopulationAtRisingStart) 
							&& (cntConsRisingEpoch > SimQPNConfiguration.OVERFLOW_DET_MIN_CONS_RISING_EPOCHS))
					|| ((cntConsRisingEpoch > SimQPNConfiguration.OVERFLOW_DET_MAX_CONS_RISING_EPOCHS) 
							&& (totalMaxPopulation > 2 * maxPopulationAtRisingStart))) {
						if (!deactivateWarning) {
							SimQPNController.progressMonitor.warning("Queue \"" + name + "\" is exceedingly growing. An overflow might occur.");
							deactivateWarning = true;
						}
						cntConsRisingEpoch = 0;
						maxPopulationAtRisingStart = totalMaxPopulation;
				}
			}
		}
		
		if (statsLevel >= 2) // NOTE: For statsLevel=1, we don't need to do anything since throughput data is calculated as sum of the throughputs of all QPlaces the Queue is part of.
			queueStats.updateTotTkPopStats(count, sim.clock);	 
		
		if (queueDiscip == IS) {
			// Schedule service completion events						
			for (int i = 0; i < count; i++) {
				double servTime = qPl.randServTimeGen[color].nextDouble();	
				if (servTime < 0) servTime = 0;
				Token tk = (tokensToBeAdded != null) ? tokensToBeAdded[i] : new Token(qPl, color);
				tk.arrivTS = sim.clock;
				sim.scheduleEvent(sim.clock + servTime, this, tk);								
			}								 								
		}
		else if (queueDiscip == FCFS) {
			int n = 0;
			while (n < count && numBusyServers < numServers) {
				// Schedule service completion event
				double servTime = qPl.randServTimeGen[color].nextDouble();
				if (servTime < 0) servTime = 0;
				Token tk = (tokensToBeAdded != null) ? tokensToBeAdded[n] : new Token(qPl, color);
				tk.arrivTS = sim.clock;
				sim.scheduleEvent(sim.clock + servTime, this, tk);
				numBusyServers++; n++;
				// Update Stats
				if (qPl.statsLevel >= 3)   
					qPl.qPlaceQueueStats.updateDelayTimeStats(color, 0, sim.configuration);																 
			}						
			while (n < count) {
				//  Place the rest of the tokens in the waitingLine
				Token tk = (tokensToBeAdded != null) ? tokensToBeAdded[n] : new Token(qPl, color);
				tk.arrivTS = sim.clock;
				waitingLine.addLast(tk);				
				n++;					
			}						
		}
		else if (queueDiscip == PS) {
			if (!expPS) {
				if (eventScheduled)	updateResidServTimes(sim.clock);	//NOTE: WATCH OUT! Method should be called before the new tokens have been added to queueTokResidServTimes!  
				for (int i = 0; i < count; i++)  {
					double servTime = qPl.randServTimeGen[color].nextDouble();
					if (servTime < 0) servTime = 0;
					qPl.queueTokResidServTimes[color].add(servTime);  			
				}				
			}
			if ((tokensToBeAdded != null) || (qPl.statsLevel >= 3)) {
				// if we get tokens from caller or we have to measure the sojourn times, store the individual tokens.
				for (int i = 0; i < count; i++) {
					Token tk = (tokensToBeAdded != null) ? tokensToBeAdded[i] : new Token(qPl, color);
					tk.arrivTS = sim.clock;
					qPl.queueTokens[color].add(tk);
				}
			}
			if (eventScheduled) clearEvents();	// NOTE: clearEvents() resets eventScheduled to false; 
			eventsUpToDate = false;
		}
		else {
			log.error("Invalid queueing discipline for QPlace " + name);
			throw new SimQPNException();
		}		
	}
	
	/**
	 * Method completeService - completes service of a token and schedules next waiting token for service.                      
	 * 
	 * JUERGEN: calls scheduleEvent()
	 *
	 * @param token - token completing service
	 * @return
	 * @exception
	 */
	public void completeService(Token token, SimQPNController sim) throws SimQPNException {
		
		tkPopulation--;

		if (statsLevel >= 2) // NOTE: For statsLevel=1, we don't need to do anything since throughput data is calculated as sum of the throughputs of all QPlaces the Queue is part of.
			queueStats.updateTotTkPopStats(-1, sim.clock);
		
		if (queueDiscip == IS) {
			// Nothing to do				 								
		}
		else if (queueDiscip == FCFS) {
			if (waitingLine.size() > 0) {
				Token tk = waitingLine.removeFirst();				
				QPlace qPl = (QPlace) tk.place;				
				double servTime = qPl.randServTimeGen[tk.color].nextDouble();	
				if (servTime < 0) servTime = 0;
				sim.scheduleEvent(sim.clock + servTime, this, tk);
				// Update stats				
				if (qPl.statsLevel >= 3)
					qPl.qPlaceQueueStats.updateDelayTimeStats(tk.color, sim.clock - tk.arrivTS, sim.configuration);				
			}
			else numBusyServers--;							
		}
		else if (queueDiscip == PS) {
			QPlace qPl = ((QPlace) token.place);
			if (!expPS) {
				if (qPlaces[tkSchedPl].queueTokens[tkSchedCol] != null)
					qPlaces[tkSchedPl].queueTokens[tkSchedCol].remove(tkSchedPos);
				qPlaces[tkSchedPl].queueTokResidServTimes[tkSchedCol].remove(tkSchedPos);
				updateResidServTimes(sim.clock); //NOTE: WATCH OUT! Method should be called after served token has been removed from queueTokResidServTimes!   
			}
			else if (qPl.queueTokens[token.color] != null)
				qPl.queueTokens[token.color].remove(0);			
			eventScheduled = false;
			eventsUpToDate = false;			
		} 
		else {
			log.error("Invalid queueing discipline for QPlace " + name);
			throw new SimQPNException();
		}		
	}
			
}
