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
 * Contributor(s):   
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
 *  2004/08/25  Samuel Kounev     Implemented support for multi-server PS queues (-/M/n-PS).                                 
 *  2005/12/13  Samuel Kounev     Modified to make it possible to switch off steady state statistics collection 
 *                                on a per color basis. Steady state statistics are collected for color c only 
 *                                when minBatches[c] > 0.                                 
 *  2006/10/14  Christofer Dutz   Added @SuppressWarnings("unchecked") and cleaned up 
 *                                imports to avoid warnings!
 *  2006/10/21  Samuel Kounev     Modified to use the Simulator.log() methods for output.
 *  2008/11/25  Samuel Kounev     Renamed from QueueStats to QPlaceQueueStats.
 *  2008/11/25  Samuel Kounev     Moved the queue-related logic into a separate class Queue. Queues can now be 
 *                                shared among multiple QPlaces.
 *  2008/12/13  Samuel Kounev     Changed to store names of token colors that can reside in this place.
 *  2008/12/15  Samuel Kounev     Added new statLevel 4 storing sojourn time histogram data.
 *  2008/12/16  Samuel Kounev     Moved lastTkPopClock and lastTotTkPop to PlaceStats where they are now needed
 *                                for estimating tkOcp. This way we avoid having them duplicated.
 * 
 */
package de.tud.cs.simqpn.kernel.entities.stats;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.apache.log4j.Logger;

import cern.jet.stat.Descriptive;
import cern.jet.stat.Probability;
import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.queue.QueueingDiscipline;
import de.tud.cs.simqpn.kernel.util.LogUtil.ReportLevel;

/**
 * Class QPlaceQueueStats
 *
 * 
 * @author Samuel Kounev
 * @version %I%, %G%
 */

public class QPlaceQueueStats extends PlaceStats implements java.io.Serializable {
	private static final long serialVersionUID = 3L;
	
	private static Logger log = Logger.getLogger(QPlaceQueueStats.class);

	// NOTE: The following data is duplicated here to make Stats objects self-contained. 
	public QueueingDiscipline queueDiscip;	// Queueing discipline
	public int			numServers;		// FCFS queues: Number of servers in queueing station.			
	public double[]		meanServTimes;	// All times usually in milliseconds
	
	// ----------------------------------------------------------------------------------------------------
	//  STATISTICS
	// ----------------------------------------------------------------------------------------------------
	
	// StatsLevel 1 ---------------------------------------------------------------------------------------
	
	// StatsLevel 2 ---------------------------------------------------------------------------------------
	public double		areaQueUtilQPl;		// Accumulated area under the curve for computing the expected  
											// queue utilization due to this place - fraction of time that 
											// there is a token of this place in the queue.		  				
	public double		queueUtilQPl;		// Utilization of the integrated queue due to this place = (areaQueUtilQPl / msrmPrdLen) - fraction of the available server resources that would be used if the queue was dedicated to this place.
	
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
	
	// ----------------------------------------------------------------------------------------------------

	/**
	 * Constructor
	 *
	 * @param id            - global id of the place
	 * @param name          - name of the place
	 * @param colors 	    - names of the colors that can reside in the respective location
	 * @param statsLevel    - determines the amount of statistics to be gathered during the run 
	 * @param queueDiscip   - queueing discipline
	 * @param numServers    - FCFS queues: number of servers in queueing station 
	 * @param meanServTimes - mean service times of tokens
	 */	
	public QPlaceQueueStats(int id, String name, String[] colors, int statsLevel, QueueingDiscipline queueDiscip, int numServers, double[] meanServTimes, SimQPNConfiguration configuration) throws SimQPNException {
		super(id, name, QUE_PLACE_QUEUE, colors, statsLevel, configuration);
		this.queueDiscip	= queueDiscip;
		this.numServers		= numServers;			
		this.meanServTimes  = meanServTimes;
			
		//  statsLevel >= 1
				
		if (statsLevel >= 3) {
			// Make sure indrStats is false if queueDiscip != Queue.FCFS
			this.indrStats	= (queueDiscip == QueueingDiscipline.FCFS);		// indrStats is by default true for FCFS queues
			this.meanDT					=	new double[numColors];
			this.stDevDT				=	new double[numColors];			
			if (configuration.getAnalMethod() == SimQPNConfiguration.AnalysisMethod.BATCH_MEANS)  {
				this.stdStateMeanDT			=	new double[numColors];
				this.varStdStateMeanDT		=	new double[numColors];
				this.stDevStdStateMeanDT	=	new double[numColors];
				this.ciHalfLenDT			=	new double[numColors];
			}
		}
		
		if (statsLevel >= 5)  {
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
					log.error("Cannot open file: " + fileName, ex);
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
	@Override
	public void init(int[] tokenPop, SimQPNConfiguration configuration, double clock) throws SimQPNException {
		super.init(tokenPop, configuration, clock);
		
		if (statsLevel >= 2)  			
			areaQueUtilQPl = 0;						
		
		if (statsLevel >= 3)  {
			// Make sure indrStats is false if queueDiscip != Queue.FCFS
			indrStats = indrStats && (queueDiscip == QueueingDiscipline.FCFS);
		}
	}

	/**
	 * Method updateTkPopStats
	 *  	 
	 * @param color		- token color
	 * @param oldTkPop	- old token population
	 * @param delta		- difference between new and old token population
	 */
	@Override
	public void updateTkPopStats(int color, int oldTkPop, int delta, double clock)  {
		if (inRampUp) return;						
		if (statsLevel >= 2)  {						
			double elapsedTime = clock - lastTkPopClock;
			if (elapsedTime > 0) {
				if (numServers > 1){	//NOTE: WATCH OUT with IS queues here! Below we assume that for such queues numServers == 0.				
					areaQueUtilQPl += elapsedTime * (lastTotTkPop > numServers ? 1 : (((double) lastTotTkPop) / numServers));
				}else{
					areaQueUtilQPl += elapsedTime * (lastTotTkPop > 0 ? 1 : 0);  
				}
				//NOTE: lastTkPopClock is updated in super.updateTkPopStats() below.			
			}			
			//NOTE: lastTotTkPop is updated in super.updateTkPopStats() below.			
		} 
		//NOTE: updateTkPopStats is called after the above since it updates lastTkPopClock and lastTotTkPop!
		super.updateTkPopStats(color, oldTkPop, delta, clock); 
	}

	/**
	 * Method updateSojTimeStats
	 *  	 
	 * @param color		- token color
	 * @param sojTime	- sojourn time of token in queue	 
	 */
	@Override
	public void updateSojTimeStats(int color, double sojTime, SimQPNConfiguration configuration) throws SimQPNException {
		if (indrStats || (inRampUp && configuration.getAnalMethod() != SimQPNConfiguration.AnalysisMethod.WELCH)) return;
		super.updateSojTimeStats(color, sojTime, configuration);
	}

	/**
	 * Method updateDelayTimeStats (FCFS)
	 *  	 
	 * @param color		- token color
	 * @param delayTime	- delay time of token in waiting area of the queue
	 * 	 
	 */
	public void updateDelayTimeStats(int color, double delayTime, SimQPNConfiguration configuration) throws SimQPNException {				
		if ((!indrStats) || (inRampUp && configuration.getAnalMethod() != SimQPNConfiguration.AnalysisMethod.WELCH)) return;
		super.updateSojTimeStats(color, delayTime, configuration);		
	}	
	
	/**
	 * Method processStats - processes gathered statistics (summarizes data)
	 *                        	 
	 */
	@Override
	public void processStats(SimQPNConfiguration configuration) throws SimQPNException {		
		super.processStats(configuration);
		if (statsLevel >= 2){
			queueUtilQPl = areaQueUtilQPl / msrmPrdLen;
		}
		if (statsLevel >= 3 && indrStats)  {
			for (int c = 0; c < numColors; c++)  {
				meanDT[c] 	= sumST[c] / numST[c];
				stDevDT[c]	= Math.sqrt(Descriptive.sampleVariance(numST[c], sumST[c], sumSqST[c]));
				meanST[c] 	= meanDT[c] + meanServTimes[c];				
				if (configuration.getAnalMethod() == SimQPNConfiguration.AnalysisMethod.BATCH_MEANS && minBatches[c] > 0)  {								
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
	 */
	@Override
	public void printReport(SimQPNConfiguration configuration) throws SimQPNException {
		
		if (!completed) {
			log.error("QPlaceQueueStats " + name + ": Attempting to access statistics before data collection has finished!");
			throw new SimQPNException();
		}
		
		StringBuilder report = new StringBuilder();

		report.append("for Queue of Queueing Place : ").append(name).append("----------------------------------------\n");
		report.append("\n");
		if (statsLevel >= 2) 
			report.append("queueUtilQPl=").append(queueUtilQPl).append("\n"); 
											
		for (int c = 0; c < numColors; c++) {
			report.append("\n");
			report.append("------------------ Color = ").append(colors[c]).append(" --------------------\n");
			
			report.append("arrivCnt=").append(arrivCnt[c]);
			report.append(" deptCnt=").append(deptCnt[c]).append("\n");	
			
			report.append("arrivThrPut=").append(arrivThrPut[c]);
			report.append(" deptThrPut=").append(deptThrPut[c]).append("\n");											
			if (statsLevel >= 2) {				
//				Simulator.logln("minTkPop=" + minTkPop[c] + " maxTkPop=" + maxTkPop[c]);
				report.append("meanTkPop=").append(meanTkPop[c]);
				report.append(" tkColOcp=").append(tkColOcp[c]).append("\n");				
			}
			if (statsLevel >= 3) {																			
				if (!indrStats) {										
					report.append("-----\n");
//					Simulator.logln("numST=" + numST[c] + " minST=" + minST[c] + " maxST=" + maxST[c]);										
					report.append("meanST=").append(meanST[c]);
					report.append(" stDevST=").append(stDevST[c]).append("\n");				
					if (configuration.getAnalMethod() == SimQPNConfiguration.AnalysisMethod.BATCH_MEANS && minBatches[c] > 0)  {	
						report.append("\n");
						report.append("Steady State Statistics: ");
						if (numBatchesST[c] >= minBatches[c])  {											 													
							report.append("numBatchesST=").append(numBatchesST[c]);
							report.append(" batchSizeST=").append(batchSizeST[c]);
							report.append(" stDevStdStateMeanST=").append(stDevStdStateMeanST[c]).append("\n");
							
							report.append(confLevelST[c]).append("% c.i. = ").append(stdStateMeanST[c])
								.append(" +/- ").append(ciHalfLenST[c]).append("\n");
						}
						else {													
							report.append("Only ").append(numBatchesST[c]).append(" batches collected!\n");
							report.append("Need at least ").append(minBatches[c]).append(" for steady state statistics.\n");
						} 
					}
				}
				else {
					report.append("-----\n");
					//Note: DT = delay time in the waiting area of the queue 
//					Simulator.logln("numDT=" + numST[c] + " minDT=" + minST[c] + " maxDT=" + maxST[c]);															
					double thrPut = deptThrPut[c];	// We assume steady state					 
					report.append("meanDT=").append(meanDT[c]).append(" stDevDT=").append(stDevDT[c]).append("\n");															
					report.append("Indirect estimate of meanST=").append(meanST[c]).append("\n");			
					report.append("Indirect estimate of meanTkPop=").append(thrPut * meanST[c]).append("\n");					
					if (configuration.getAnalMethod() == SimQPNConfiguration.AnalysisMethod.BATCH_MEANS && minBatches[c] > 0)  {					
						report.append("\n");
						report.append("Steady State Statistics: \n");
						if (numBatchesST[c] >= minBatches[c])  {																									
							report.append("numBatchesDT=").append(numBatchesST[c]);
							report.append(" batchSizeDT=").append(batchSizeST[c]);
							report.append(" stDevStdStateMeanDT=").append(stDevStdStateMeanDT[c]).append("\n");
							
							report.append(confLevelST[c]).append("% c.i.DT = ").append(stdStateMeanDT[c])
									.append(" +/- ").append(ciHalfLenDT[c]).append("\n");						
							report.append("Indirect Estimates: \n");						
							report.append(confLevelST[c]).append("% c.i.ST = ").append(stdStateMeanST[c])
									.append(" +/- ").append(ciHalfLenST[c]).append("\n");
							
							report.append("meanTkPop=").append(thrPut * stdStateMeanST[c]).append("\n");
						}
						else {							
							report.append("Only ").append(numBatchesST[c]).append(" batches collected!\n");
							report.append("Need at least ").append(minBatches[c]).append(" for steady state statistics.\n");
						}
					}
				}												
			}
			if (statsLevel >= 4) {
				report.append("-----\n");
				report.append("Histogram Data\n");
				for (int i = 1; i < 10; i++) 
					report.append("   ").append(i*10).append("% percentile=").append(histST[c].getPercentile((double) i / 10)).append("\n");
				report.append("   histogramMean=").append(histST[c].getMean()).append("\n");				
			}			
			if (statsLevel >= 5) {
				if (!indrStats)
					report.append("Token sojourn times dumped in ").append(statsDir).append("\n");
				else 
					report.append("Token delay times dumped in ").append(statsDir).append("\n");
			}
		}
		
		report.append("\n\n");
		
		// Output report
		log.log(ReportLevel.REPORT, report);
	}
	
}
