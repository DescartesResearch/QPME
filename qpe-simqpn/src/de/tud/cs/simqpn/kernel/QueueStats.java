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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import cern.colt.list.DoubleArrayList;
import cern.jet.stat.Descriptive;
import cern.jet.stat.Probability;

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
	public double	meanTotTkPop;				// Mean Total Token Population.
	
	public double	areaQueUtil;				// Accumulated area under the curve for computing the expected  
												// queue utilization - fraction of time that there is a token in the queue. 
	public double	lastTkPopClock;				// Time of last token population change (over all colors).		
	public int		lastTotTkPop;				// Last queue total token population (over all colors).		
	public double	queueUtil;					// Utilization of the queue = (areaQueUtil / msrmPrdLen).

	// StatsLevel 3 ---------------------------------------------------------------------------------------
//	public double[] minST;						// Minimum observed token sojourn time.
//	public double[] maxST;						// Maximum observed token sojourn time.
	
	public double[] meanST;						// Mean Sojourn Time over all tokens visiting this queue.
	
	// StatsLevel 4 ---------------------------------------------------------------------------------------
	
	// ----------------------------------------------------------------------------------------------------

	/**
	 * Constructor
	 *
	 * @param id            - global id of the queue
	 * @param name          - name of the queue
	 * TODO
	 * @param Queue         - reference to respective Queue Object
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
			for (int p = 0; p < queue.qPlaces.length; p++)
				for (int c = 0; c < queue.qPlaces[p].numColors; c++)
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
/*		if (statsLevel >= 2)  {			TODO
			lastTotTkPop 			= 0;			
			for (int c = 0; c < numColors; c++) 
				lastTotTkPop += tokenPop[c];
			lastTkPopClock = Simulator.clock;
			
		}	
*/	
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
	public void finish() throws SimQPNException {		
		if (statsLevel >= 2) //NOTE: This makes sure areaQueUtil is complete.
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
	 */	
	public void processStats()  {		
		// statsLevel >= 1
		for (int p = 0; p < queue.qPlaces.length; p++)
			for (int c = 0; c < queue.qPlaces[p].numColors; c++)  {
				totArrivThrPut	= queue.qPlaces[p].;
				totDeptThrPut	= 	
			}			
		TODO
//		statlevel2
		meanTotTkPop
		queueUtil = areaQueUtil / msrmPrdLen;

//		statlevel3	
		meanST		
		
		
		if (statsLevel >= 3 && indrStats)  {
			for (int c = 0; c < numColors; c++)  {
				meanDT[c] 	= sumST[c] / numST[c];
				stDevDT[c]	= Math.sqrt(Descriptive.sampleVariance(numST[c], sumST[c], sumSqST[c]));
				meanST[c] 	= meanDT[c] + meanServTimes[c];				
				if (Simulator.analMethod == Simulator.BATCH_MEANS && minBatches[c] > 0)  {								
					// Steady State Statistics
					if (numBatchesST[c] >= minBatches[c])  {					
						stdStateMeanDT[c] = sumBMeansST[c] / numBatchesST[c];															
						varStdStateMeanDT[c] = Descriptive.sampleVariance(numBatchesST[c], sumBMeansST[c], sumBMeansSqST[c]);
						stDevStdStateMeanDT[c] = Math.sqrt(varStdStateMeanDT[c]);
						ciHalfLenDT[c] = Probability.studentTInverse(signLevST[c], numBatchesST[c] - 1) * Math.sqrt(varStdStateMeanDT[c] / numBatchesST[c]);
						ciHalfLenST[c] = ciHalfLenDT[c]; 								
						confLevelST[c] = (int) (100 * (1 - signLevST[c]));
						stdStateMeanST[c] = stdStateMeanDT[c] + meanServTimes[c];										
					}
					else stdStateStatsAv = false;
				}								
			}											 			
		}
		completed = true;					
	}
	
	/**
	 * Method printReport - prints a summary of the collected statistics 
	 *                      
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
		if (statsLevel >= 2) 
			Simulator.logln("Queue utilization due to this place = " + queueUtilQPl); 
											
		for (int c = 0; c < numColors; c++) {
			Simulator.logln();
			Simulator.logln("------------------ Color=" + c + " --------------------");				
			Simulator.logln("arrivCnt=" + arrivCnt[c] + " deptCnt=" + deptCnt[c]);			
			Simulator.logln("arrivThrPut=" + arrivThrPut[c] + " deptThrPut=" + deptThrPut[c]);											
			if (statsLevel >= 2) {				
//				Simulator.logln("minTkPop=" + minTkPop[c] + " maxTkPop=" + maxTkPop[c]);
				Simulator.logln("meanTkPop=" + meanTkPop[c] + " colUtil=" + colUtil[c]);				
			}
			if (statsLevel >= 3) {																			
				if (!indrStats) {										
					Simulator.logln("-----");
//					Simulator.logln("numST=" + numST[c] + " minST=" + minST[c] + " maxST=" + maxST[c]);										
					Simulator.logln("meanST=" + meanST[c] + " stDevST=" + stDevST[c]);				
					if (Simulator.analMethod == Simulator.BATCH_MEANS && minBatches[c] > 0)  {	
						Simulator.logln();
						Simulator.logln("Steady State Statistics: ");
						if (numBatchesST[c] >= minBatches[c])  {											 													
							Simulator.logln("numBatchesST=" + numBatchesST[c] + " batchSizeST=" + batchSizeST[c] + " stDevStdStateMeanST=" + stDevStdStateMeanST[c]);										
							Simulator.logln(confLevelST[c] + "% c.i. = " + stdStateMeanST[c] + " +/- " + ciHalfLenST[c]);
						}
						else {													
							Simulator.logln("Only " + numBatchesST[c] + " batches collected!");
							Simulator.logln("Need at least " + minBatches[c] + " for steady state statistics.");
						} 
					}
				}
				else {
					Simulator.logln("-----");
					//Note: DT = delay time in the waiting area of the queue 
//					Simulator.logln("numDT=" + numST[c] + " minDT=" + minST[c] + " maxDT=" + maxST[c]);															
					double thrPut = deptThrPut[c];	// We assume steady state					 
					Simulator.logln("meanDT=" + meanDT[c] + " stDevDT=" + stDevDT[c]);															
					Simulator.logln("Indirect estimate of meanST=" + meanST[c]);			
					Simulator.logln("Indirect estimate of meanTkPop=" + thrPut * meanST[c]);					
					if (Simulator.analMethod == Simulator.BATCH_MEANS && minBatches[c] > 0)  {					
						Simulator.logln();
						Simulator.logln("Steady State Statistics: ");
						if (numBatchesST[c] >= minBatches[c])  {																									
							Simulator.logln("numBatchesDT=" + numBatchesST[c] + " batchSizeDT=" + batchSizeST[c] + " stDevStdStateMeanDT=" + stDevStdStateMeanDT[c]);																								
							Simulator.logln(confLevelST[c] + "% c.i.DT = " + stdStateMeanDT[c] + " +/- " + ciHalfLenDT[c]);						
							Simulator.logln("Indirect Estimates: ");						
							Simulator.logln(confLevelST[c] + "% c.i.ST = " + stdStateMeanST[c] + " +/- " + ciHalfLenST[c]);
							Simulator.logln("meanTkPop=" + thrPut * stdStateMeanST[c]);
						}
						else {							
							Simulator.logln("Only " + numBatchesST[c] + " batches collected!");
							Simulator.logln("Need at least " + minBatches[c] + " for steady state statistics.");
						}
					}
				}												
			}
			if (statsLevel >= 4) {
				if (!indrStats)
					Simulator.logln("Token sojourn times dumped in " + statsDir);
				else 
					Simulator.logln("Token delay times dumped in " + statsDir);
			}
		}
	}
	
}
