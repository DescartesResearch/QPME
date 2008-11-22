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
 *  2003/08/??  Samuel Kounev     Created.
 *  ...
 *  2004/08/25  Samuel Kounev     Implemented support for multi-server PS queues (-/M/n-PS).                                 
 *  2005/12/13  Samuel Kounev     Modified to make it possible to switch off steady state statistics collection 
 *                                on a per color basis. Steady state statistics are collected for color c only 
 *                                when minBatches[c] > 0.                                 
 *  2006/10/14  Christofer Dutz   Added @SuppressWarnings("unchecked") and cleaned up 
 *                                imports to avoid warnings!
 *  2006/10/21  Samuel Kounev     Modified to use the Simulator.log() methods for output.                                
 * 
 */

package de.tud.cs.simqpn.kernel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import cern.jet.stat.Descriptive;
import cern.jet.stat.Probability;

/**
 * Class QueueStats
 *
 * 
 * @author Samuel Kounev
 * @version %I%, %G%
 */

public class QueueStats extends PlaceStats implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	public int			queueDiscip;	// Queueing discipline
	public int			numServers;		// FCFS queues: Number of servers in queueing station.			
	public double[]		meanServTimes;	// All times usually in milliseconds
	
	// ----------------------------------------------------------------------------------------------------
	//  STATISTICS
	// ----------------------------------------------------------------------------------------------------
	
	// StatsLevel 1 ---------------------------------------------------------------------------------------
	
	// StatsLevel 2 ---------------------------------------------------------------------------------------
	public double		areaQueUtil;		// Accumulated area under the curve for computing the expected  
											// overall queue utilization - fraction of time that there are 
											// tokens in the queue.		  
	public double		lastTkPopClock;		// Time of last token population change (over all colors).		
	public int			lastTotTkPop;		// Last queue total token population (over all colors).		

	public double		queueUtil;			// Overall queue utilization = (areaQueUtil / msrmPrdLen)
	
	// StatsLevel 3 ---------------------------------------------------------------------------------------		
	public boolean		indrStats;			// FCFS: Specifies if STs and TkPops should be estimated indirectly
	/* NOTE:
	 * if (indrStats == true)
	 *     the variables minST, maxST, sumST, sumSqST, numST, etc. inherited from PlaceStats
	 *     refer to delay times in the waiting area of the queue
	 * otherwise
	 *     they refer as usual to overall sojourn times in the queue!
	 * 
	 */
	public double[]		meanDT;					// Mean Delay Time = (sumST[c] / numST[c])
	public double[]		stDevDT;				// Delay Time Standard Deviation = Math.sqrt(Descriptive.sampleVariance(numST[c], sumST[c], sumSqST[c])) 
	public double[]		stdStateMeanDT;			// Steady State Mean Delay Time = (sumBMeansST[c] / numBatchesST[c])	
	public double[]		varStdStateMeanDT;		// Steady State Delay Time Variance = Descriptive.sampleVariance(numBatchesST[c], sumBMeansST[c], sumBMeansSqST[c]);	
	public double[]		stDevStdStateMeanDT;	// Steady State Delay Time Standard Deviation = Math.sqrt(varStdStateMeanDT[c]) 
	public double[]		ciHalfLenDT;			// Confidence Interval Half Length = Probability.studentTInverse(signLevST[c], numBatchesST[c] - 1) * Math.sqrt(varStdStateMeanDT[c] / numBatchesST[c]);		 
	
	// StatsLevel 4 ---------------------------------------------------------------------------------------
	
	// ----------------------------------------------------------------------------------------------------

	/**
	 * Constructor
	 *
	 * @param id            - global id of the place
	 * @param name          - name of the place
	 * @param numColors     - number of colors
	 * @param statsLevel    - determines the amount of statistics to be gathered during the run 
	 * @param queueDiscip   - queueing discipline
	 * @param numServers    - FCFS queues: number of servers in queueing station 
	 * @param meanServTimes - mean service times of tokens
	 */	
	public QueueStats(int id, String name, int numColors, int statsLevel, int queueDiscip, int numServers, double[] meanServTimes) throws SimQPNException {
		super(id, name, QUEUE, numColors, statsLevel);
		this.queueDiscip	= queueDiscip;
		this.numServers		= numServers;			
		this.meanServTimes  = meanServTimes;		
			
		//  statsLevel >= 1
				
		if (statsLevel >= 3) {
			// Make sure indrStats is false if queueDiscip != Queue.FCFS
			this.indrStats	= (queueDiscip == Queue.FCFS);		// indrStats is by default true for FCFS queues
			this.meanDT					=	new double[numColors];
			this.stDevDT				=	new double[numColors];			
			if (Simulator.analMethod == Simulator.BATCH_MEANS)  {
				this.stdStateMeanDT			=	new double[numColors];
				this.varStdStateMeanDT		=	new double[numColors];
				this.stDevStdStateMeanDT	=	new double[numColors];
				this.ciHalfLenDT			=	new double[numColors];
			}
		}
		
		if (statsLevel >= 4)  {
			String fileName = "";
			this.fileST = new PrintStream[numColors];
			for (int c = 0; c < numColors; c++) {					
				try {
					if (indrStats) 
						fileName = statsDir + fileSep + "RunStats-queue" + id + "-col" + c + "-DT.txt"; 
					else																		
						fileName = statsDir + fileSep + "RunStats-queue" + id + "-col" + c + "-ST.txt";											
					this.fileST[c] = new PrintStream(new FileOutputStream(fileName));
				}				
				catch (FileNotFoundException ex) {											
					Simulator.logln("Error - cannot open file: " + fileName);
					Simulator.logln();
					Simulator.log(ex);
					throw new SimQPNException(); 	
				}
			}													
		}										
	}

	/**
	 * Method init - should be called at the beginning of the measurement period (ideally the beginning of steady state)	    	 
	 * 
	 * @param tokenPop - current queue token population 
	 * @return
	 * @exception
	 */
	public void init(int[] tokenPop)  {
		super.init(tokenPop);
		
		if (statsLevel >= 2)  {			
			lastTotTkPop = 0;
			for (int c = 0; c < numColors; c++) 
				lastTotTkPop += tokenPop[c];			
			areaQueUtil	= 0;
			lastTkPopClock = Simulator.clock;			
		}		
		if (statsLevel >= 3)  {
			// Make sure indrStats is false if queueDiscip != Queue.FCFS
			indrStats = indrStats && (queueDiscip == Queue.FCFS);
		}
	}

	/**
	 * Method updateTkPopStats
	 *  	 
	 * @param color		- token color
	 * @param oldTkPop	- old token population
	 * @param delta		- difference between new and old token population
	 */
	public void updateTkPopStats(int color, int oldTkPop, int delta) {
		if (inRampUp) return;		
		super.updateTkPopStats(color, oldTkPop, delta);		
		if (statsLevel >= 2)  {						
			double elapsedTime = Simulator.clock - lastTkPopClock;
			if (elapsedTime > 0) {
				if (numServers > 1)				
					areaQueUtil += elapsedTime * (lastTotTkPop > numServers ? 1 : (((double) lastTotTkPop) / numServers));																						
				else areaQueUtil += elapsedTime * (lastTotTkPop > 0 ? 1 : 0);
				lastTkPopClock = Simulator.clock;				
			}			
			lastTotTkPop += delta;									
		}		
	}

	/**
	 * Method updateSojTimeStats
	 *  	 
	 * @param color		- token color
	 * @param sojTime	- sojourn time of token in queue	 
	 */
	public void updateSojTimeStats(int color, double sojTime)  {
		if (indrStats || (inRampUp && Simulator.analMethod != Simulator.WELCH)) return;
		super.updateSojTimeStats(color, sojTime);
	}

	/**
	 * Method updateDelayTimeStats (FCFS)
	 *  	 
	 * @param color		- token color
	 * @param delayTime	- delay time of token in waiting area of the queue
	 * 	 
	 */
	public void updateDelayTimeStats(int color, double delayTime)  {				
		if ((!indrStats) || (inRampUp && Simulator.analMethod != Simulator.WELCH)) return;
		super.updateSojTimeStats(color, delayTime);		
	}	
	
	/**
	 * Method processStats - processes gathered statistics (summarizes data)
	 *                        	 
	 */	
	public void processStats()  {		
		super.processStats();
		queueUtil = areaQueUtil / msrmPrdLen;		
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
			Simulator.logln("Overall Queue Util=" + queueUtil); 
											
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
