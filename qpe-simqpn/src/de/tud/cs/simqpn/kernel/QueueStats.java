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
 *  ----------  ----------------  ------------------------------------------------------------------------------
 *  2008/11/25  Samuel Kounev     Created.
 * 
 */

package de.tud.cs.simqpn.kernel;

/**
 * Class QueueStats
 *
 * 
 * @author Samuel Kounev
 * @version %I%, %G%
 */

public class QueueStats extends Stats implements java.io.Serializable {
	private static final long serialVersionUID = 154545454L;

	public int		queueDiscip;	// Queueing discipline.
	public int		numServers;		// FCFS queues: Number of servers in queueing station.			
	public Queue	queue;			// Associated Queue object.
	
	// ----------------------------------------------------------------------------------------------------
	//  STATISTICS
	// ----------------------------------------------------------------------------------------------------

	// StatsLevel 1 ---------------------------------------------------------------------------------------
	public double	totArrivThrPut;				// Total arrival throughput over all queueing places this queue is part of.
	public double	totDeptThrPut;				// Total departure throughput over all queueing places this queue is part of.
		
	// StatsLevel 2 ---------------------------------------------------------------------------------------
	public double	meanTotTkPop;				// Mean queue total token population.	
	public double	areaQueUtil;				// Accumulated area under the curve for computing the expected  
												// queue utilization - fraction of time that there is a token in the queue. 
	public double	lastTkPopClock;				// Time of last token population change (over all colors).		
	public int		lastTotTkPop;				// Last queue total token population (over all colors).		
	public double	queueUtil;					// Utilization of the queue = (areaQueUtil / msrmPrdLen).

	// StatsLevel 3 ---------------------------------------------------------------------------------------	
	public double	meanST;						// Mean Sojourn Time over all tokens visiting this queue.
	
	// StatsLevel 4 ---------------------------------------------------------------------------------------
	
	// ----------------------------------------------------------------------------------------------------

	/**
	 * Constructor
	 *
	 * @param id			- global id of the queue
	 * @param name			- name of the queue
	 * @param numColors		- total number of token colors over all queueing places the queue is part of
	 * @param statsLevel	- determines the amount of statistics to be gathered during the run
	 * @param queueDiscip	- queueing discipline
	 * @param numServers	- FCFS queues: number of servers in queueing station
	 * @param Queue			- reference to respective Queue object
	 * 
	 */	
	public QueueStats(int id, String name, int numColors, int statsLevel, int queueDiscip, int numServers, Queue queue) throws SimQPNException  {
		super(id, name, QUEUE, numColors, statsLevel);
		this.queueDiscip	= queueDiscip;
		this.numServers		= numServers;	
		this.queue			= queue;
	}

	/**
	 * Method init - should be called at the beginning of the measurement period (ideally the beginning of steady state)	    	 
	 * 
	 * @param 
	 * @return
	 * @exception
	 */
	public void init()  {		
		// statsLevel >= 1		
		totArrivThrPut				= 0;
		totDeptThrPut				= 0;
		if (statsLevel >= 2)  {			
			meanTotTkPop			= 0;
			areaQueUtil				= 0;
			lastTotTkPop 			= 0;		
			for (int p=0; p < queue.qPlaces.length; p++)
				for (int c=0; c < queue.qPlaces[p].numColors; c++)
					lastTotTkPop += queue.qPlaces[p].queueTokenPop[c];				
			lastTkPopClock = Simulator.clock;
		}
	}


	/**
	 * Method start - should be called at the end of RampUp to start taking measurements.	    	 
	 * 
	 * @param 
	 * @return
	 * @exception
	 */
	public void start() throws SimQPNException  {	
		init();
		inRampUp = false;
		endRampUpClock = Simulator.clock;	
	}

	/**
	 * Method finish - should be called at the end of the measurement period to
	 *                 complete statistics collection.
	 *  
	 * Note: Completes accumulated areas under the curve.   
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public void finish() throws SimQPNException  {		
		if (statsLevel >= 2) //NOTE: This makes sure areaQueUtil is complete!
			updateTotTkPopStats(0);
		endRunClock = Simulator.clock;
		msrmPrdLen = endRunClock - endRampUpClock;		
		runWallClockTime = Simulator.runWallClockTime;
		processStats(); 
	}
		
	/**
	 * Method updateTotTkPopStats
	 * 
	 * NOTE: Currently called only when statsLevel >= 2
	 * 
	 * @param delta     - difference between new and old token population
	 */
	public void updateTotTkPopStats(int delta) {
		if (inRampUp) return;		
		double elapsedTime = Simulator.clock - lastTkPopClock;
		if (elapsedTime > 0) {
			if (numServers > 1)	//NOTE: WATCH OUT with IS queues here! Below we assume that for such queues numServers == 0.				
				areaQueUtil += elapsedTime * (lastTotTkPop > numServers ? 1 : (((double) lastTotTkPop) / numServers));																						
			else areaQueUtil += elapsedTime * (lastTotTkPop > 0 ? 1 : 0);  
			lastTkPopClock = Simulator.clock;				
		}			
		lastTotTkPop += delta;									
	}

	/**
	 * Method processStats - processes gathered statistics (summarizes data)
	 *                        
	 * NOTE: Here we assume that (statsLevel <= queue.qPlaces[*].startsLevel)
	 * 
	 */	
	public void processStats()  {
		int totNumST = 0;
		for (int p = 0; p < queue.qPlaces.length; p++)
			for (int c = 0; c < queue.qPlaces[p].numColors; c++)  {				
				totArrivThrPut	+= queue.qPlaces[p].qPlaceQueueStats.arrivThrPut[c];				
				totDeptThrPut	+= queue.qPlaces[p].qPlaceQueueStats.deptThrPut[c];
				if (statsLevel >= 3)
					totNumST	+= queue.qPlaces[p].qPlaceQueueStats.numST[c];
			}		
		if (statsLevel >= 2)
			queueUtil = areaQueUtil / msrmPrdLen;			
		for (int p = 0; p < queue.qPlaces.length; p++)
			for (int c = 0; c < queue.qPlaces[p].numColors; c++)  {				
				if (statsLevel >= 2)  
					meanTotTkPop += queue.qPlaces[p].qPlaceQueueStats.meanTkPop[c];
				if (statsLevel >= 3)  
					meanST += ((double) queue.qPlaces[p].qPlaceQueueStats.numST[c] / totNumST) * queue.qPlaces[p].qPlaceQueueStats.meanST[c];  					            	                                             				
			}	
		completed = true;					
	}
	
	/**
	 * Method printReport - prints a summary of the collected statistics 
	 *  	 
	 */	
	public void printReport() throws SimQPNException {		
		if (!completed) {
			Simulator.logln("QueueStats " + name + " Error: Attempting to access statistics before data collection has finished!");
			throw new SimQPNException();
		}		
		Simulator.logln();
		Simulator.logln();
		Simulator.logln("REPORT for Queue : " + name + "----------------------------------------");
		Simulator.logln();		
		Simulator.logln("  Total arrival throughput          = " + totArrivThrPut);
		Simulator.logln("  Total departure throughput        = " + totDeptThrPut);					
		if (statsLevel >= 2)  { 
			Simulator.logln("  Mean total token population       = " + meanTotTkPop);
			Simulator.logln("  Queue utilization                 = " + queueUtil);			
		}
		if (statsLevel >= 3)  
			Simulator.logln("  Mean token residence time         = " + meanST);			 	
	}
	
}
