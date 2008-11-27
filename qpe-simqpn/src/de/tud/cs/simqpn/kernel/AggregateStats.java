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
 *  ----------  ----------------  ----------------------------------------------------------------------------------
 *  2003/08/??  Samuel Kounev     Created.
 *  2006/10/14  Christofer Dutz   Added @SuppressWarnings("unchecked") and cleaned up 
 *                                imports to avoid warnings!
 *  2006/10/21  Samuel Kounev     Modified to use the Simulator.log() methods for output.           
 * 
 */

package de.tud.cs.simqpn.kernel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;

import cern.colt.list.AbstractDoubleList;
import cern.colt.list.DoubleArrayList;
import cern.jet.stat.Descriptive;
import cern.jet.stat.Probability;
import drasys.or.prob.FDistribution;

/**
 * Class AggregateStats 
 * 
 * Aggregates statistics from multiple independent simulation runs.
 * NOTE: Currently QUEUE statisctics are not supported.   
 * Uses the replication/deletion method (see p.525 in Law/Kelton) 
 * Estimates coverage for sojourn time confidence intervals using the method on p.508 in Law/Kelton.  
 * 
 * @author Samuel Kounev
 * @version %I%, %G%
 */

public class AggregateStats extends Stats implements java.io.Serializable {	
	private static final long serialVersionUID = 166L;

	public int 			numRepls;				// Number of replications used for replication/deletion method or coverage analysis (these are runs actually used in the analysis).			
	public int 			numTooShortRepls;		// Number of replications that were discarded because of being too short.
												// In NORMAL:REPL_DEL:FIXEDLEN mode this means replications that were too short and didn't contain steady state data.
												// In CVRG_EST mode this means replications that were discarded because of being shorter than the required min run length.
		
	public double		sumRunLen;				// Sum of run lengths (needed for computing avgRunLen).
	public double		sumSqRunLen;			// Sum of run length squares (needed for computing stDevRunLen).
	public double		sumWallClockTime;		// Sum of run wall clock times (needed for computing avgWallClockTime).
	public double		sumSqWallClockTime;		// Sum of run wall clock time squares (needed for computing stDevWallClockTime).				
	public double		minRunLen;				// Minimum run length (simulated time).			
	public double		maxRunLen;				// Maximum run length (simulated time).			
	public double		avgRunLen;				// Average run length (simulated time).
	public double		stDevRunLen;			// Std deviation of run length (simulated time).
	public double		avgWallClockTime;		// Average run wall clock time.
	public double		stDevWallClockTime;		// St. deviation of run wall clock time.
			
	// -------------------------------------------------------------------------------------------------------------------
	//  STATISTICS
	// -------------------------------------------------------------------------------------------------------------------
	
	// StatsLevel 1 ------------------------------------------------------------------------------------------------------
	public double[]		sumArrivThrPut;			// Sum of average arrival throughputs (for meanArrivThrPut).		
	public double[]		sumDeptThrPut;			// Sum of average departure throughputs (for meanDeptThrPut).			
	public double[]		sumSqArrivThrPut;		// Sum of squares of average arrival throughputs (for stDevArrivThrPut).	
	public double[]		sumSqDeptThrPut;		// Sum of squares of average departure throughputs (for stDevDeptThrPut).				 		
	public double[]		meanArrivThrPut;	 	// Mean average arrival throughput.
	public double[]		meanDeptThrPut;			// Mean average departure throughput.
	public double[]		stDevArrivThrPut;	 	// Std. deviation of average arrival throughput.
	public double[]		stDevDeptThrPut;		// Std. deviation of average departure throughput.
		
	// StatsLevel 2 ------------------------------------------------------------------------------------------------------
	public double[]		sumAvgTkPop;			// Sum of average token populations (for meanAvgTkPop).		
	public double[]		sumColUtil;				// Sum of average color utilizations (for meanColUtil).
	public double[]		sumSqAvgTkPop;			// Sum of squares of average token populations (for stDevAvgTkPop).
	public double[]		sumSqColUtil;			// Sum of squares of average color utilizations (for stDevColUtil).
	public double[]		minAvgTkPop;			// Minimum average token population (TkPop).
	public double[]		maxAvgTkPop;			// Maximum average token population.
	public double[]		meanAvgTkPop;			// Mean average token population.
	public double[]		meanColUtil;			// Mean average color utilization.
	public double[]		stDevAvgTkPop;			// Std. deviation of average token population.
	public double[]		stDevColUtil;			// Std. deviation of average color utilization.
	public double		sumQueueUtilQPl;		// For Type=QUE_PLACE_QUEUE: Sum of queue utilizations due to the place.
	public double		sumSqQueueUtilQPl;		// For Type=QUE_PLACE_QUEUE: Sum of squares of queue utilizations due to the place.
	public double		meanQueueUtilQPl;		// For Type=QUE_PLACE_QUEUE: Mean queue utilization due to the place.
	public double		stDevQueueUtilQPl;		// For Type=QUE_PLACE_QUEUE: Std. deviation of queue utilization due to the place.
							
	// StatsLevel 3 ------------------------------------------------------------------------------------------------------
	public double[]		minAvgST;				// Minimum average token sojourn time.				
	public double[]		maxAvgST;				// Maxumum average token sojourn time.			
	public double[]		sumAvgST;				// Sum of average token sojourn times.
	public double[]		sumSqAvgST;				// Sum of average token sojourn time squares.
	public int[]		numAvgST;			 	// Number of average sojourn times observed from replications.
												//	 Note: when using mean soj. times from batch means method, 
												//   some replications may not have AvgST because of too few batches.
											   	
	public double[]		meanAvgST;				// Mean average sojourn time = (sumAvgST[c] / numAvgST[c]).			
	public double[]		varAvgST;				// Average sojourn time variation = Descriptive.sampleVariance(numAvgST[c], sumAvgST[c], sumSqAvgST[c]).
	public double[]		stDevAvgST;				// Average sojourn time standard deviation = Math.sqrt(varAvgST[c]).			
	public double[]		signLevAvgST;			// Required significance level, (1 - signLevAvgST) = the confidence level.
	public double[]		ciHalfLenAvgST;			// Confidence interval half length = Probability.studentTInverse(signLevAvgST[c], numAvgST[c] - 1) * Math.sqrt(varAvgST[c] / numAvgST[c]).		
	public int[]		confLevelAvgST;			// Confidence Level = (int) (100 * (1 - signLevAvgST[c]))

	// Used only for INIT_TRANS:WELCH (Method of Welch)
	public AbstractDoubleList[] 
						sumKthObsrvST;			// Sum of the k-th observations from the replications (used to compute avgKthObsrvST).																																													
	public AbstractDoubleList[] 
						avgKthObsrvST;			// Stores the average of the k-th observations from the replications.											

	// Used only when Simulator.analMethod==BATCH_MEANS && useStdStateStats==true
	public int[]		sumBatchSizesST;		// Sum of batch sizes used for the batch means method.	
	public int[]		avgBatchSizeST;			// Average batch size used for the batch means method.
	public int[]		sumNumBatchesST;		// Sum of number of batches collected in the replications.
	public int[]		avgNumBatchesST;		// Avgerage number of batches collected.

	// Used only when runMode==CVRG_EST
	public boolean		useFdistr;				// Confidence intervals for the true coverage can be constructed based on the F-distribution or the Normal distribution. See paper of Lee, Pawlikowski, McNickle [1999].
												// useFdistr specifies which estimates to use when checking if the required precision has been reached (the stopping rule).											   
	public double[]		trueAvgST;			 	// The true average sojourn time (used to estimate coverage). 
												// NOTE: Tokens with negative trueAvgST are not considered in the coverage analysis!											
	public int			reqMinBadCIs;			// Minumum number of 'bad' confidence intervals, i.e. not covering the true average sojourn time.		
	public boolean		enghBadCIs;				// true if at least reqMinBadCIs bad c.i. have been recorded.				
	public double		reqMinRunLen;			// Min run length = one st. dev. shorter than avg. run length at the point enough bad c.i. have been detected.
												// Runs shorter than reqMinRunLen are discarded to improve coverage estimation.
													 
	public int[]		numCvrgs;				// Number of runs in which the confidence interval covered the true average sojourn time.																																					
	public double[]		estCvrg;				// Estimated coverage of the confidence intervals for sojourn times.			
	public double		signLevCvrg;			// Required significance level for coverage confidence interval, (1 - signLevCvrg) = the confidence level.
	public double		reqCvrgAbsPrc;			// Required absolute precision for estimated coverage (max c.i. half length).					
	public double[]		ciHalfLenTrCvrg;		// Confidence interval for the true coverage based on the Normal distribution.
												// See p. 508 in Law/Kelton; p. 215 in Jain; p.385 in "Applied Statistics" from Revine, Ramsey, Smidt.
											
	public double[]		trCvrgLowerLimit;		// Lower limit for the true coverage based on the F distribution.	
	public double[]		trCvrgUpperLimit;		// Upper limit for the true coverage based on the F distribution.
	public LinkedList	replStats;				// PlaceStats/QPlaceQueueStats objects collected from run replications.
	public int 			numSavedRepls;			// Number of saved replications for coverage analysis.
										            													
	// StatsLevel 4 ------------------------------------------------------------------------------------------------------
	public PrintStream[] fileST;				// Level 4: File output streams for dumping sojourn times.
				
	/**
	 * Constructor
	 *
	 * @param id          - global id of the place
	 * @param name        - name of the node
	 * @param type		  - type of the statistics (ORD_PLACE, QUE_PLACE_QUEUE or QUE_PLACE_DEP) 	 
	 * @param numColors   - number of colors
	 * @param statsLevel  - determines the amount of statistics to be gathered during the run
	 */
	public AggregateStats(int id, String name, int type, int numColors, int statsLevel) throws SimQPNException {
		super(id, name, type, numColors, statsLevel);		

		this.numRepls			= 0;
		this.numTooShortRepls	= 0;			
		this.sumRunLen 			= 0;
		this.sumSqRunLen 		= 0;
		this.sumWallClockTime	= 0;
		this.sumSqWallClockTime	= 0;
		
		// StatsLevel >= 1 		
		this.sumArrivThrPut		= new double[numColors]; 
		this.sumDeptThrPut		= new double[numColors];		
		this.sumSqArrivThrPut	= new double[numColors];
		this.sumSqDeptThrPut	= new double[numColors];
		this.meanArrivThrPut	= new double[numColors];
		this.meanDeptThrPut		= new double[numColors];		
		this.stDevArrivThrPut	= new double[numColors];
		this.stDevDeptThrPut	= new double[numColors];	
		for (int c = 0; c < numColors; c++) {
			sumArrivThrPut[c]	= 0; sumDeptThrPut[c]	= 0; 
			sumSqArrivThrPut[c]	= 0; sumSqDeptThrPut[c]	= 0;															 
		}
		
		if (statsLevel >= 2) {
			this.sumAvgTkPop	= new double[numColors];
			this.sumColUtil		= new double[numColors];
			this.sumSqAvgTkPop	= new double[numColors];
			this.sumSqColUtil	= new double[numColors];						
			this.minAvgTkPop	= new double[numColors];
			this.maxAvgTkPop	= new double[numColors];
			this.meanAvgTkPop	= new double[numColors];			
			this.meanColUtil	= new double[numColors];
			this.stDevAvgTkPop	= new double[numColors];			
			this.stDevColUtil	= new double[numColors];			
			for (int c = 0; c < numColors; c++) {						
				sumAvgTkPop[c]	 = 0; sumColUtil[c]	  = 0;				
				sumSqAvgTkPop[c] = 0; sumSqColUtil[c] = 0;
			}
			if (type == QUE_PLACE_QUEUE) {
				sumQueueUtilQPl = 0; sumSqQueueUtilQPl = 0;										
			}
		}
		
		if (statsLevel >= 3)  {	
			this.minAvgST		= new double[numColors];			
			this.maxAvgST		= new double[numColors];			
			this.sumAvgST		= new double[numColors];
			this.sumSqAvgST		= new double[numColors];
			this.numAvgST		= new int[numColors]; 
			this.meanAvgST		= new double[numColors];			
			this.varAvgST		= new double[numColors];
			this.stDevAvgST		= new double[numColors];
			this.signLevAvgST	= new double[numColors];											
			this.ciHalfLenAvgST	= new double[numColors];
			this.confLevelAvgST	= new int[numColors];
			for (int c = 0; c < numColors; c++) {			
				sumAvgST[c]	= 0; sumSqAvgST[c] = 0; numAvgST[c] = 0;
			}			
			// DEFAULT-CONFIG: Default significance level for average sojourn time											
			for (int c = 0; c < numColors; c++) 
				this.signLevAvgST[c]	= 0.1; 			// e.g. 0.05 ---> 95% c.i.; 0.1 ---> 90%
			
			if (Simulator.analMethod == Simulator.WELCH)  {											
				this.sumKthObsrvST = new AbstractDoubleList[numColors];				
				this.avgKthObsrvST = new AbstractDoubleList[numColors];
				for (int c = 0; c < numColors; c++) 
					this.sumKthObsrvST[c] = null;  // used to detect colors that shouldn't be considered in the analysis	
			}											
			if (Simulator.analMethod == Simulator.BATCH_MEANS && Simulator.useStdStateStats)  {				
				this.sumBatchSizesST	= new int[numColors];
				this.avgBatchSizeST		= new int[numColors];
				this.sumNumBatchesST	= new int[numColors];
				this.avgNumBatchesST	= new int[numColors];
				for (int c = 0; c < numColors; c++)  {
					this.sumBatchSizesST[c] = 0;
					this.sumNumBatchesST[c] = 0;
				}					 				
			}					
			if (Simulator.runMode == Simulator.CVRG_EST)  {				
				this.trueAvgST			= new double[numColors];				
				this.numCvrgs			= new int[numColors];
				this.estCvrg			= new double[numColors];
				this.ciHalfLenTrCvrg	= new double[numColors];
				this.trCvrgLowerLimit	= new double[numColors];
				this.trCvrgUpperLimit	= new double[numColors];				
				this.replStats			= new LinkedList();				
				this.numSavedRepls		= 0;
				this.enghBadCIs			= false;				
				for (int c = 0; c < numColors; c++) numCvrgs[c] = 0;				
				// DEFAULT-CONFIG: Default parameters for coverage estimation procedure
				this.useFdistr			= true;
				this.reqCvrgAbsPrc		= 0.05;
				this.reqMinBadCIs		= 100;
				this.signLevCvrg		= 0.05;	 // e.g. 0.05 ---> 95% c.i.; 0.1 ---> 90%				
			}
		}

		if (statsLevel >= 4)  {	
			String fileName = "";
			this.fileST = new PrintStream[numColors];															
			for (int c = 0; c < numColors; c++)  {					
				try {
					if (type == ORD_PLACE)						
						fileName = statsDir + fileSep + "ReplicationStats-ord_place" + id + "-col" + c + "-ST.txt";
					else if (type == QUE_PLACE_DEP)
						fileName = statsDir + fileSep + "ReplicationStats-que_place_dep" + id + "-col" + c + "-ST.txt";
					else if (type == QUE_PLACE_QUEUE)
						fileName = statsDir + fileSep + "ReplicationStats-que_place_queue" + id + "-col" + c + "-ST.txt";
					else {
						Simulator.logln("Error: Internal error in AggregateStats of place " + name);
						throw new SimQPNException();
					}
					this.fileST[c] = new PrintStream(new FileOutputStream(fileName));
				}				
				catch (FileNotFoundException ex)  {
					Simulator.logln("Error - cannot open file: " + fileName);
					Simulator.logln();					
					Simulator.log(ex);					
					throw new SimQPNException();
				}
			}													
		}				
	}
	
	/**
	 * Method saveStats - saves the results of a run (replication).   
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void saveStats(PlaceStats stats) throws SimQPNException {		
		if (Simulator.analMethod == Simulator.WELCH)  {			
			if (statsLevel < 3) return;			
			for (int c = 0; c < numColors; c++)  {				
				if (stats.maxObsrvST[c] <= 0) return;								
				int numObsrv = stats.obsrvST[c].size();				
				if (numObsrv < stats.minObsrvST[c])  {					
					if (type == ORD_PLACE) 
						Simulator.log("Error: In place " + name);
					else if (type == QUE_PLACE_QUEUE)
						Simulator.log("Error: In queue of place " + name);
					else if (type == QUE_PLACE_DEP)
						Simulator.log("Error: In depository of place " + name);
					else {
						Simulator.logln("Error: Internal error in AggregateStats of place " + name);
						throw new SimQPNException();
					}					
					Simulator.logln(" only " + numObsrv + " observations collected for color " + c + "!");
					Simulator.logln("       Need at least " + stats.minObsrvST[c] + ". Please increase Simulator.rampUpLen or lower minObsrvST[c].");
					throw new SimQPNException();					
				}								
				if (numRepls == 0) sumKthObsrvST[c] = stats.obsrvST[c];
				else  {
					if (numObsrv < sumKthObsrvST[c].size())  
						sumKthObsrvST[c].removeFromTo(numObsrv, sumKthObsrvST[c].size()-1);					
					numObsrv = sumKthObsrvST[c].size();
					double[] obs = stats.obsrvST[c].elements();
					double[] sumKthObs = sumKthObsrvST[c].elements();
					for (int i=0; i < numObsrv; i++)  						
						sumKthObsrvST[c].set(i, sumKthObs[i] + obs[i]);					
				}
			}
			numRepls++;			
			return;	
		}

		// Make sure the run was long enough
		if (Simulator.useStdStateStats && (!stats.stdStateStatsAv))  {
			numTooShortRepls++;
			Simulator.logln("Replication skipped, since it was too short and no steady state data was available.");
			return;
		} 								
		if (Simulator.runMode == Simulator.CVRG_EST && statsLevel >= 3 && (!enghBadCIs))  {
			replStats.add(stats);
			numSavedRepls++;			
			double runLen = stats.endRunClock;			
			sumRunLen += runLen;
			sumSqRunLen += runLen * runLen;							 						
			for (int c = 0; c < numColors; c++)  {	
				// Colors with negative trueAvgST[c] are not considered!
				if (trueAvgST[c] < 0) continue;											
				double avgST =  stats.stdStateMeanST[c];									
				double leftEnd = avgST - stats.ciHalfLenST[c];
				double rightEnd = avgST + stats.ciHalfLenST[c];						 					
				if ((trueAvgST[c] >= leftEnd) && (trueAvgST[c] <= rightEnd)) numCvrgs[c]++;				
			}
		}
		else addStats(stats);		
	}

	/**
	 * Method enoughCvrgStats - returns true if enough data is available to provide estimates 
	 *                          for the coverage with the required precision.
	 * 
	 * Used only in mode CVRG_EST. 
	 */
	public boolean enoughCvrgStats()  {
		if (statsLevel < 3) return true;		
		
		boolean passed = enoughBadCIs();
		if (passed)  {			
			for (int c = 0; c < numColors; c++)  {				
				// Colors with negative trueAvgST[c] are not considered!
				if (trueAvgST[c] < 0) continue;											
				estCvrg[c]  = ((double) numCvrgs[c]) / numRepls;
				// Check if required precision for the c.i. coverage has been reached				
				if (useFdistr)  {	// Use the F distribution to construct c.i. for the true coverage 
					if (Math.abs(estCvrg[c] - getTrCvrgLowerLimit(c)) > reqCvrgAbsPrc || 
					    Math.abs(getTrCvrgUpperLimit(c) - estCvrg[c]) > reqCvrgAbsPrc)  {
							passed = false; break;
					    }
				}
				else  { 	// Use the Normal distribution to construct c.i. for the true coverage
					ciHalfLenTrCvrg[c] = Math.abs(Probability.normalInverse(signLevCvrg / 2)) * Math.sqrt((estCvrg[c] * (1 - estCvrg[c])) / numRepls);				
					if (ciHalfLenTrCvrg[c] > reqCvrgAbsPrc)  {					 										
/* DEBUG:
						Simulator.logln("Not enough coverage stats for " + name + " of type " + type + "***********************************");
						Simulator.logln("Color=" + c + ":estCvrg[c]=" + estCvrg[c] + ":ciHalfLenTrCvrg[c]=" + ciHalfLenTrCvrg[c]);
*/
						passed = false; break;
					}													 
				}
			}
		}																																
		return passed;	
	}

	/**
	 * Method getTrCvrgLowerLimit 
	 *   
	 * Used for constructing c.i. for the true coverage based on the F-distribution.
	 * See paper of Lee, Pawlikowski, McNickle [1999].             
	 *  
	 * Note: Assumes that numCvrgs[color] and numRepls are up-to-date!
	 * 
	 * Used only in mode CVRG_EST. 
	 */
	public double getTrCvrgLowerLimit(int color)  {
		double n	= numRepls;
		double np	= numCvrgs[color]; // == (n * estCvrg[color]);
		double r1	= 2 * (n - np + 1);  
		double r2	= 2 * np;	
		if (r1 < 1.0 || r2 < 1.0) 		
			Simulator.logln("place=" + name + "; type=" + type + "; np=" + np + "; n=" + n + "; r1=" + r1 + "; r2=" + r2);
		double Fq	= (new FDistribution(r1, r2)).inverseCdf(1 - (signLevCvrg / 2));	
		return np / (np + (n - np + 1) * Fq);
	}

	/**
	 * Method getTrCvrgUpperLimit 
	 *               
	 * Used for computing c.i. for the true coverage based on the F-distribution.
	 * See paper of Lee, Pawlikowski, McNickle [1999].             
	 *  
	 * Note: Assumes that numCvrgs[color] and numRepls are up-to-date!
	 * 
	 * Used only in mode CVRG_EST. 
	 */
	public double getTrCvrgUpperLimit(int color)  {
		double n	= numRepls;
		double np	= numCvrgs[color]; // == (n * estCvrg[color]);
		double r3	= 2 * (np + 1);  
		double r4	= 2 * (n - np);						
		if (r3 < 1.0 || r4 < 1.0) 		
			Simulator.logln("place=" + name + "; type=" + type + "; np=" + np + "; n=" + n + "; r3=" + r3 + "; r4=" + r4);		
		double Fq	= (new FDistribution(r3, r4)).inverseCdf(1 - (signLevCvrg / 2));		
		return ((np + 1) * Fq) / ((n - np) + (np + 1) * Fq);		
	}
	
	/**
	 * Method enoughBadCIs - returns true if at least reqMinBadCIs 'bad' confidence intervals have been recorded 
	 * 
	 * Used only in mode CVRG_EST.
	 */
	public boolean enoughBadCIs() {				
		if (enghBadCIs) return true;
		
		enghBadCIs = true;		
		for (int c = 0; c < numColors; c++)  {
			// Colors with negative trueAvgST[c] are not considered!
			if (trueAvgST[c] < 0) continue;			
			// Make sure a minimum number of 'bad' conf. intervals have been recorded
			if ((numSavedRepls - numCvrgs[c]) < reqMinBadCIs) { enghBadCIs = false; break; }																		 
		}		
		if (enghBadCIs)  { // threshold reached
			// DEBUG:			
			Simulator.logln("minBadCIs reached for " + name + " of type " + type + "***********************************");
			// Remove runs that were too short			
			avgRunLen	= sumRunLen / numSavedRepls;
			stDevRunLen	= Math.sqrt(Descriptive.sampleVariance(numSavedRepls, sumRunLen, sumSqRunLen));					
			reqMinRunLen = avgRunLen - stDevRunLen;
			sumRunLen = 0; sumSqRunLen = 0;	
			for (int c = 0; c < numColors; c++) numCvrgs[c] = 0;									 									
			for (int i=0; i < replStats.size(); i++)  {
				addStats((PlaceStats) replStats.get(i));
			}
			// Discard replStats to free memory
			replStats.clear();
			replStats = null;			
		}																																		
		return enghBadCIs;	
	}

	/**
	 * Method addStats - adds the results of a run (replication) to the overall statistics
	 * 
	 * Should be called only for successful runs to be considered in the analysis
	 * 
	 */
	public void addStats(PlaceStats stats)  {
		/* redundant since this is checked in saveStats
		// Make sure the run was long enough
		if (Simulator.useStdStateStats && (!stats.stdStateStatsAv)) {
			Simulator.logln("Replication skipped, since it was too short and no steady state data was available.");
			return;
		} 		
		*/
		
		double runLen = stats.endRunClock;		
		// Skip short runs in CVRG_EST mode
		if (Simulator.runMode == Simulator.CVRG_EST && statsLevel >= 3 && runLen < reqMinRunLen)  {
			numTooShortRepls++;
			return;
		} 
					
		numRepls++;							
		if (numRepls == 1 || runLen < minRunLen) minRunLen = runLen;			
		if (numRepls == 1 || runLen > maxRunLen) maxRunLen = runLen;		
		sumRunLen 			+= runLen;
		sumSqRunLen 		+= runLen * runLen;
		sumWallClockTime 	+= stats.runWallClockTime;
		sumSqWallClockTime	+= stats.runWallClockTime * stats.runWallClockTime;									 						
		// StatsLevel 1:
		for (int c = 0; c < numColors; c++)  {
			sumArrivThrPut[c]	+= stats.arrivThrPut[c];	   
			sumDeptThrPut[c]	+= stats.deptThrPut[c];
			sumSqArrivThrPut[c]	+= stats.arrivThrPut[c] * stats.arrivThrPut[c];			
			sumSqDeptThrPut[c]	+= stats.deptThrPut[c] * stats.deptThrPut[c];
		}		
		// StatsLevel 2:
		if (statsLevel >= 2)  { 							
			for (int c = 0; c < numColors; c++)  {
				double avgTkPop = stats.meanTkPop[c];					
				if (numRepls == 1 || avgTkPop < minAvgTkPop[c]) minAvgTkPop[c] = avgTkPop;					
				if (numRepls == 1 || avgTkPop > maxAvgTkPop[c]) maxAvgTkPop[c] = avgTkPop;					
				sumAvgTkPop[c]		+= avgTkPop;			
				sumColUtil[c]		+= stats.colUtil[c];						
				sumSqAvgTkPop[c]	+= avgTkPop * avgTkPop;			
				sumSqColUtil[c]		+= stats.colUtil[c] * stats.colUtil[c];
			}
			if (type == QUE_PLACE_QUEUE) {	
				double queueUtilQPl = ((QPlaceQueueStats) stats).queueUtilQPl;
				sumQueueUtilQPl		+= queueUtilQPl;
				sumSqQueueUtilQPl	+= queueUtilQPl * queueUtilQPl;										
			}											
		}		
		// StatsLevel 3:
		if (statsLevel >= 3)
			for (int c = 0; c < numColors; c++)  {
				double avgST =  Simulator.useStdStateStats ? stats.stdStateMeanST[c] : stats.meanST[c];					
				if (numRepls == 1 || avgST < minAvgST[c]) minAvgST[c] = avgST;
				if (numRepls == 1 || avgST > maxAvgST[c]) maxAvgST[c] = avgST;				
				sumAvgST[c]	  += avgST;								
				sumSqAvgST[c] += avgST * avgST; 
				numAvgST[c]++;														
				if (Simulator.analMethod == Simulator.BATCH_MEANS && Simulator.useStdStateStats)  {										
					sumBatchSizesST[c]	+= stats.batchSizeST[c];
					sumNumBatchesST[c]	+= stats.numBatchesST[c];
				}								
				if (Simulator.runMode == Simulator.CVRG_EST && trueAvgST[c] >= 0) {
					double leftEnd = avgST - stats.ciHalfLenST[c];
					double rightEnd = avgST + stats.ciHalfLenST[c];						 					
					if ((trueAvgST[c] >= leftEnd) && (trueAvgST[c] <= rightEnd)) numCvrgs[c]++;
				}
			}	
		// StatsLevel 4:
		if (statsLevel >= 4) 
			for (int c = 0; c < numColors; c++)  {
				double avgST =  Simulator.useStdStateStats ? stats.stdStateMeanST[c] : stats.meanST[c];		
				fileST[c].println(avgST); 
			}
	}

	/**
	 * Method finish - completes the data collection process
	 *                    	 
	 */
	public void finish() throws SimQPNException {
		if (statsLevel >= 4) 		
			for (int c = 0; c < numColors; c++)
				fileST[c].close();				
		processStats();
	}

	/**
	 * Method dumpMovAvgs() 
	 *
	 * INIT_TRANS:WELCH - dumps moving averages for a given window to a file
	 *                        	 
	 */	
	public void dumpMovAvgs(int color, int win) throws SimQPNException {		
		int numObsrv = avgKthObsrvST[color].size();
		double[] avgKthObs = avgKthObsrvST[color].elements();			

		String fileName = "";		
		try  {					
			if (type == ORD_PLACE)
				fileName = statsDir + fileSep + "WelchMovAvgST-ord_place" + name + "-col" + color + "-win" + win + ".txt"; 
			else if (type == QUE_PLACE_QUEUE)
				fileName = statsDir + fileSep + "WelchMovAvgST-que_place_queue" + name + "-col" + color + "-win" + win + ".txt";
			else if (type == QUE_PLACE_DEP)
				fileName = statsDir + fileSep + "WelchMovAvgST-que_place_dep" + name + "-col" + color + "-win" + win + ".txt";
			else {
				Simulator.logln("Error: Internal error in AggregateStats of place " + name);
				throw new SimQPNException();
			}
			PrintStream fileST = new PrintStream(new FileOutputStream(fileName));
							
			double movAvg;										
			for (int i=1; i <= numObsrv-win; i++)  {
				movAvg = 0;
				if (i <= win)  {				
					for (int s = (-1)*(i-1); s <= (i-1); s++)  
						movAvg += avgKthObs[i+s-1];																
					movAvg /= (2*i - 1);
				}									
				else  {				
					for (int s = (-1)*win; s <= win; s++)  
						movAvg += avgKthObs[i+s-1];																
					movAvg /= (2*win + 1);
				}					
				fileST.println(movAvg);	
			}
			
			fileST.close();
		}				
		catch (FileNotFoundException ex)  {											
			Simulator.logln("Error: Cannot open file: " + fileName);
			Simulator.logln();
			Simulator.log(ex);
			throw new SimQPNException();	
		}
	}
	
	
	/**
	 * Method processStats - processes gathered statistics (summarizes data)
	 *                        	 
	 */	
	public void processStats() throws SimQPNException {
		
		if (numRepls < 1) return;
		
		if (Simulator.analMethod == Simulator.WELCH)  {			
			if (statsLevel < 3) return;			
			for (int c = 0; c < numColors; c++)  {
				if (sumKthObsrvST[c] == null) return; // color with maxObsrvST[c] <= 0 				
				int numObsrv = sumKthObsrvST[c].size();
				double[] sumKthObs = sumKthObsrvST[c].elements();
				avgKthObsrvST[c] = new DoubleArrayList(numObsrv);
				for (int i=0; i < numObsrv; i++)  						
					avgKthObsrvST[c].add(sumKthObs[i] / numRepls);					
				int win = numObsrv / 4; 	// window for moving averages
				for (int i = 0; i < 4; i++)  {
					dumpMovAvgs(c, win);
					win /= 2;			
				}						
			}
			return;														
		}				
									
		avgRunLen			= sumRunLen / numRepls;
		stDevRunLen			= Math.sqrt(Descriptive.sampleVariance(numRepls, sumRunLen, sumSqRunLen));		
		avgWallClockTime 	= sumWallClockTime / numRepls;
		stDevWallClockTime	= Math.sqrt(Descriptive.sampleVariance(numRepls, sumWallClockTime, sumSqWallClockTime));

		// StatsLevel 1:
		for (int c = 0; c < numColors; c++)  {
			meanArrivThrPut[c]		= sumArrivThrPut[c] / numRepls;
			meanDeptThrPut[c]		= sumDeptThrPut[c] / numRepls;	
			stDevArrivThrPut[c]		= Math.sqrt(Descriptive.sampleVariance(numRepls, sumArrivThrPut[c], sumSqArrivThrPut[c]));
			stDevDeptThrPut[c]		= Math.sqrt(Descriptive.sampleVariance(numRepls, sumDeptThrPut[c], sumSqDeptThrPut[c]));			
		}
		// StatsLevel 2:
		if (statsLevel >= 2)  {		
			for (int c = 0; c < numColors; c++)  {
				meanAvgTkPop[c]		= sumAvgTkPop[c] / numRepls;
				meanColUtil[c]		= sumColUtil[c] / numRepls; 			
				stDevAvgTkPop[c]	= Math.sqrt(Descriptive.sampleVariance(numRepls, sumAvgTkPop[c], sumSqAvgTkPop[c]));
				stDevColUtil[c]		= Math.sqrt(Descriptive.sampleVariance(numRepls, sumColUtil[c], sumSqColUtil[c]));						
			}
			if (type == QUE_PLACE_QUEUE) {	
				meanQueueUtilQPl	= sumQueueUtilQPl / numRepls;  								
				stDevQueueUtilQPl	= Math.sqrt(Descriptive.sampleVariance(numRepls, sumQueueUtilQPl, sumSqQueueUtilQPl));
			}							
		}	
		// StatsLevel 3:
		if (statsLevel >= 3)
			for (int c = 0; c < numColors; c++)  {
				meanAvgST[c]		= sumAvgST[c] / numAvgST[c];
				varAvgST[c]			= Descriptive.sampleVariance(numAvgST[c], sumAvgST[c], sumSqAvgST[c]);
				stDevAvgST[c]		= Math.sqrt(varAvgST[c]);
				ciHalfLenAvgST[c]	= Probability.studentTInverse(signLevAvgST[c], numAvgST[c] - 1) * Math.sqrt(varAvgST[c] / numAvgST[c]); 
				confLevelAvgST[c]	= (int) (100 * (1 - signLevAvgST[c]));				
				if (Simulator.analMethod == Simulator.BATCH_MEANS && Simulator.useStdStateStats)  {  								
					avgBatchSizeST[c]	= sumBatchSizesST[c] / numRepls;					
					avgNumBatchesST[c]	= sumNumBatchesST[c] / numRepls;					 
				}																					
				if (Simulator.runMode == Simulator.CVRG_EST && trueAvgST[c] >= 0)  {
					estCvrg[c]  = ((double) numCvrgs[c]) / numRepls;															
					trCvrgLowerLimit[c] = getTrCvrgLowerLimit(c);
					trCvrgUpperLimit[c] = getTrCvrgUpperLimit(c);
					ciHalfLenTrCvrg[c] = Math.abs(Probability.normalInverse(signLevCvrg / 2)) * Math.sqrt((estCvrg[c] * (1 - estCvrg[c])) / numRepls);					
				}											
			}																														
		completed = true;		
	}
	
	/**
	 * Method printReport - prints a summary of the computed statistics
	 *                    	 
	 */
	public void printReport() throws SimQPNException {
		//...
		if (!completed) {
			Simulator.logln("AggregateStats " + name + " Error: Attempting to access statistics before data collection has finished!");
			throw new SimQPNException();
		}
		
		Simulator.logln();
		Simulator.logln();
		if (type == ORD_PLACE)						
			Simulator.logln("REPORT for Ordinary Place: " + name + "----------------------------------------");			
		else if (type == QUE_PLACE_DEP)
			Simulator.logln("REPORT for Depository of Queueing Place: " + name + "----------------------------------------");
		else if (type == QUE_PLACE_QUEUE)
			Simulator.logln("REPORT for Queue of Queueing Place: " + name + "----------------------------------------");
		else {
			Simulator.logln("Error: Internal error in AggregateStats of place " + name);
			throw new SimQPNException();
		}
		Simulator.logln("numReplicationsUsed = " + numRepls + " numTooShortRepls = " + numTooShortRepls); 				
		Simulator.logln("minRunLen=" + minRunLen + " maxRunLen=" + maxRunLen + " avgRunLen=" + avgRunLen + " stDevRunLen=" + stDevRunLen);
		Simulator.logln("avgWallClockTime=" + avgWallClockTime + " stDevWallClockTime=" + stDevWallClockTime);
						
		if (statsLevel >= 2 && type == QUE_PLACE_QUEUE) 
			Simulator.logln("meanQueueUtilQPl=" + meanQueueUtilQPl + " stDevQueueUtilQPl=" + stDevQueueUtilQPl);					
			
		for (int c = 0; c < numColors; c++) {
			Simulator.logln();				
			Simulator.logln("------------------ Color=" + c + " --------------------");				
			Simulator.logln("meanArrivThrPut[c]=" + meanArrivThrPut[c] + " meanDeptThrPut[c]=" + meanDeptThrPut[c]);
			Simulator.logln("stDevArrivThrPut[c]=" + stDevArrivThrPut[c] + " stDevDeptThrPut[c]=" + stDevDeptThrPut[c]);				
											
			if (statsLevel >= 2) {				
				Simulator.logln("minAvgTkPop[c]=" + minAvgTkPop[c] + " maxAvgTkPop[c]=" + maxAvgTkPop[c]);
				Simulator.logln("meanAvgTkPop[c]=" + meanAvgTkPop[c] + " meanColUtil[c]=" + meanColUtil[c]);
				Simulator.logln("stDevAvgTkPop[c]=" + stDevAvgTkPop[c] + " stDevColUtil[c]=" + stDevColUtil[c]);
			}
			if (statsLevel >= 3) {												
				Simulator.logln("-----");
				if (Simulator.analMethod == Simulator.BATCH_MEANS && Simulator.useStdStateStats)					
					Simulator.logln("avgBatchSizeST[c]=" + avgBatchSizeST[c] + " avgNumBatchesST[c]=" + avgNumBatchesST[c]);				
				Simulator.logln("meanAvgST[c]=" + meanAvgST[c] + " stDevAvgST[c]=" + stDevAvgST[c]);					
				Simulator.logln();																																							
				Simulator.logln(confLevelAvgST[c] + "% c.i. = " + meanAvgST[c] + " +/- " + ciHalfLenAvgST[c]);			
				if (Simulator.runMode == Simulator.CVRG_EST && trueAvgST[c] >= 0) {					
					Simulator.logln("trueAvgST[c]=" + trueAvgST[c] + " estCvrg[c]=" + estCvrg[c]);					
					int confLevelCvrg	= (int) (100 * (1 - signLevCvrg));					
					Simulator.logln(confLevelCvrg + "% c.i. (from F-distr.) = [" + trCvrgLowerLimit[c] + ", " + trCvrgUpperLimit[c] + "]");
					Simulator.logln(confLevelCvrg + "% c.i. (from N-distr.) = " + estCvrg[c] + " +/- " + ciHalfLenTrCvrg[c]);
					Simulator.logln("n * p = " + numRepls * estCvrg[c] + "; This should be >= 10 for the above c.i. to be valid!"); // see p.215 in Jain
					Simulator.logln("n * p * (1 - p) = " + numRepls * estCvrg[c] * (1 - estCvrg[c]) + "; For a 95% c.i. this should be >= 10; For 99% c.i. it should be >= 35."); // See p.385 in "Applied Statistics" from Revine, Ramsey, Smidt.										 					
					Simulator.logln();					 																																							
				}														
			}
			if (statsLevel >= 4) {
				Simulator.logln("Token sojourn times dumped in " + statsDir);
			}															
		}					
	}
				
}
