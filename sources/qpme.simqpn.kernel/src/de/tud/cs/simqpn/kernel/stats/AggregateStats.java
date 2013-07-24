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
 *  2006/10/14  Christofer Dutz   Added @SuppressWarnings("unchecked") and cleaned up 
 *                                imports to avoid warnings!
 *  2006/10/21  Samuel Kounev     Modified to use the Simulator.log() methods for output.           
 * 
 */
package de.tud.cs.simqpn.kernel.stats;

import static de.tud.cs.simqpn.kernel.util.LogUtil.formatMultilineMessage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import cern.colt.list.AbstractDoubleList;
import cern.colt.list.DoubleArrayList;
import cern.jet.stat.Descriptive;
import cern.jet.stat.Probability;
import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.util.LogUtil.ReportLevel;

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
	
	private static Logger log = Logger.getLogger(AggregateStats.class);

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
	public double[]		sumTkColOcp;			// Sum of average token color occupancies (for meanTkColOcp).
	public double[]		sumSqAvgTkPop;			// Sum of squares of average token populations (for stDevAvgTkPop).
	public double[]		sumSqTkColOcp;			// Sum of squares of average token color occupancies (for stDevTkColOcp).
	public double[]		minAvgTkPop;			// Minimum average token population (TkPop).
	public double[]		maxAvgTkPop;			// Maximum average token population.
	public double[]		meanAvgTkPop;			// Mean average token population.
	public double[]		meanTkColOcp;			// Mean average token color occupancy.
	public double[]		stDevAvgTkPop;			// Std. deviation of average token population.
	public double[]		stDevTkColOcp;			// Std. deviation of average token color occupancy.
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
	public LinkedList<PlaceStats>replStats;		// PlaceStats/QPlaceQueueStats objects collected from run replications.
	public int 			numSavedRepls;			// Number of saved replications for coverage analysis.
										            													
	// StatsLevel 5 ------------------------------------------------------------------------------------------------------
	public PrintStream[] fileST;				// Level 5: File output streams for dumping sojourn times.
				
	/**
	 * Constructor
	 *
	 * @param id          - global id of the place
	 * @param name        - name of the node
	 * @param type		  - type of the statistics (ORD_PLACE, QUE_PLACE_QUEUE or QUE_PLACE_DEP) 	 
	 * @param numColors   - number of colors
	 * @param statsLevel  - determines the amount of statistics to be gathered during the run
	 */
	public AggregateStats(int id, String name, int type, int numColors, int statsLevel, SimQPNConfiguration configuration) throws SimQPNException {
		super(id, name, type, numColors, statsLevel, configuration);		

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
			this.sumTkColOcp	= new double[numColors];
			this.sumSqAvgTkPop	= new double[numColors];
			this.sumSqTkColOcp	= new double[numColors];						
			this.minAvgTkPop	= new double[numColors];
			this.maxAvgTkPop	= new double[numColors];
			this.meanAvgTkPop	= new double[numColors];			
			this.meanTkColOcp	= new double[numColors];
			this.stDevAvgTkPop	= new double[numColors];			
			this.stDevTkColOcp	= new double[numColors];			
			for (int c = 0; c < numColors; c++) {						
				sumAvgTkPop[c]	 = 0; sumTkColOcp[c]	= 0;				
				sumSqAvgTkPop[c] = 0; sumSqTkColOcp[c]	= 0;
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
			
			if (configuration.getAnalMethod() == SimQPNConfiguration.AnalysisMethod.WELCH)  {											
				this.sumKthObsrvST = new AbstractDoubleList[numColors];				
				this.avgKthObsrvST = new AbstractDoubleList[numColors];
				for (int c = 0; c < numColors; c++) 
					this.sumKthObsrvST[c] = null;  // used to detect colors that shouldn't be considered in the analysis	
			}											
			if (configuration.getAnalMethod() == SimQPNConfiguration.AnalysisMethod.BATCH_MEANS && configuration.isUseStdStateStats())  {				
				this.sumBatchSizesST	= new int[numColors];
				this.avgBatchSizeST		= new int[numColors];
				this.sumNumBatchesST	= new int[numColors];
				this.avgNumBatchesST	= new int[numColors];
				for (int c = 0; c < numColors; c++)  {
					this.sumBatchSizesST[c] = 0;
					this.sumNumBatchesST[c] = 0;
				}					 				
			}					
			if (configuration.runMode == SimQPNConfiguration.CVRG_EST)  {				
				this.trueAvgST			= new double[numColors];				
				this.numCvrgs			= new int[numColors];
				this.estCvrg			= new double[numColors];
				this.ciHalfLenTrCvrg	= new double[numColors];
				this.trCvrgLowerLimit	= new double[numColors];
				this.trCvrgUpperLimit	= new double[numColors];				
				this.replStats			= new LinkedList<PlaceStats>();				
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

		if (statsLevel >= 5)  {	
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
						log.error("Internal error in AggregateStats of place " + name);
						throw new SimQPNException();
					}
					this.fileST[c] = new PrintStream(new FileOutputStream(fileName));
				}				
				catch (FileNotFoundException ex)  {
					log.error("Cannot open file: " + fileName, ex);										
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
	public void saveStats(PlaceStats stats, SimQPNConfiguration configuration) throws SimQPNException {		
		if (configuration.getAnalMethod() == SimQPNConfiguration.AnalysisMethod.WELCH)  {			
			if (statsLevel < 3) return;			
			for (int c = 0; c < numColors; c++)  {				
				if (stats.maxObsrvST[c] <= 0) return;								
				int numObsrv = stats.obsrvST[c].size();				
				if (numObsrv < stats.minObsrvST[c])  {					
					if (type == ORD_PLACE) 
						log.error(formatMultilineMessage(
								"In place " + name + " only " + numObsrv + " observations collected for color " + c + "!",
								"Need at least " + stats.minObsrvST[c] + ". Please increase Simulator.rampUpLen or lower minObsrvST[c]."
								));
					else if (type == QUE_PLACE_QUEUE)
						log.error(formatMultilineMessage(
								"In queue of place " + name + " only " + numObsrv + " observations collected for color " + c + "!",
								"Need at least " + stats.minObsrvST[c] + ". Please increase Simulator.rampUpLen or lower minObsrvST[c]."
								));
					else if (type == QUE_PLACE_DEP)
						log.error(formatMultilineMessage(
								"In depository of place " + name + " only " + numObsrv + " observations collected for color " + c + "!",
								"Need at least " + stats.minObsrvST[c] + ". Please increase Simulator.rampUpLen or lower minObsrvST[c]."
								));
					else {
						log.error("Internal error in AggregateStats of place " + name);
					}					
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
		if (configuration.isUseStdStateStats() && (!stats.stdStateStatsAv))  {
			numTooShortRepls++;
			log.info("Replication skipped, since it was too short and no steady state data was available.");
			return;
		} 								
		if (configuration.runMode == SimQPNConfiguration.CVRG_EST && statsLevel >= 3 && (!enghBadCIs))  {
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
		else addStats(stats, configuration);		
	}

	/**
	 * Method enoughCvrgStats - returns true if enough data is available to provide estimates 
	 *                          for the coverage with the required precision.
	 * 
	 * Used only in mode CVRG_EST. 
	 */
//	public boolean enoughCvrgStats()  {
//		if (statsLevel < 3) return true;		
//		
//		boolean passed = enoughBadCIs();
//		if (passed)  {			
//			for (int c = 0; c < numColors; c++)  {				
//				// Colors with negative trueAvgST[c] are not considered!
//				if (trueAvgST[c] < 0) continue;											
//				estCvrg[c]  = ((double) numCvrgs[c]) / numRepls;
//				// Check if required precision for the c.i. coverage has been reached				
//				if (useFdistr)  {	// Use the F distribution to construct c.i. for the true coverage 
//					if (Math.abs(estCvrg[c] - getTrCvrgLowerLimit(c)) > reqCvrgAbsPrc || 
//					    Math.abs(getTrCvrgUpperLimit(c) - estCvrg[c]) > reqCvrgAbsPrc)  {
//							passed = false; break;
//					    }
//				}
//				else  { 	// Use the Normal distribution to construct c.i. for the true coverage
//					ciHalfLenTrCvrg[c] = Math.abs(Probability.normalInverse(signLevCvrg / 2)) * Math.sqrt((estCvrg[c] * (1 - estCvrg[c])) / numRepls);				
//					if (ciHalfLenTrCvrg[c] > reqCvrgAbsPrc)  {					 										
///* DEBUG:
//						Simulator.logln("Not enough coverage stats for " + name + " of type " + type + "***********************************");
//						Simulator.logln("Color=" + c + ":estCvrg[c]=" + estCvrg[c] + ":ciHalfLenTrCvrg[c]=" + ciHalfLenTrCvrg[c]);
//*/
//						passed = false; break;
//					}													 
//				}
//			}
//		}																																
//		return passed;	
//	}

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
//	public double getTrCvrgLowerLimit(int color)  {
//		double n	= numRepls;
//		double np	= numCvrgs[color]; // == (n * estCvrg[color]);
//		double r1	= 2 * (n - np + 1);  
//		double r2	= 2 * np;	
//		if (r1 < 1.0 || r2 < 1.0) 		
//			log.debug("place=" + name + "; type=" + type + "; np=" + np + "; n=" + n + "; r1=" + r1 + "; r2=" + r2);
//		double Fq	= (new FDistribution(r1, r2)).inverseCdf(1 - (signLevCvrg / 2));	
//		return np / (np + (n - np + 1) * Fq);
//	}

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
//	public double getTrCvrgUpperLimit(int color)  {
//		double n	= numRepls;
//		double np	= numCvrgs[color]; // == (n * estCvrg[color]);
//		double r3	= 2 * (np + 1);  
//		double r4	= 2 * (n - np);						
//		if (r3 < 1.0 || r4 < 1.0) 		
//			log.debug("place=" + name + "; type=" + type + "; np=" + np + "; n=" + n + "; r3=" + r3 + "; r4=" + r4);		
//		double Fq	= (new FDistribution(r3, r4)).inverseCdf(1 - (signLevCvrg / 2));		
//		return ((np + 1) * Fq) / ((n - np) + (np + 1) * Fq);		
//	}
	
	/**
	 * Method enoughBadCIs - returns true if at least reqMinBadCIs 'bad' confidence intervals have been recorded 
	 * 
	 * Used only in mode CVRG_EST.
	 */
	public boolean enoughBadCIs(SimQPNConfiguration configuration) {				
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
			log.debug("minBadCIs reached for " + name + " of type " + type + "***********************************");
			// Remove runs that were too short			
			avgRunLen	= sumRunLen / numSavedRepls;
			stDevRunLen	= Math.sqrt(Descriptive.sampleVariance(numSavedRepls, sumRunLen, sumSqRunLen));					
			reqMinRunLen = avgRunLen - stDevRunLen;
			sumRunLen = 0; sumSqRunLen = 0;	
			for (int c = 0; c < numColors; c++) numCvrgs[c] = 0;									 									
			for (int i=0; i < replStats.size(); i++)  {
				addStats((PlaceStats) replStats.get(i), configuration);
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
	public void addStats(PlaceStats stats, SimQPNConfiguration configuration)  {
		/* redundant since this is checked in saveStats
		// Make sure the run was long enough
		if (Simulator.useStdStateStats && (!stats.stdStateStatsAv)) {
			Simulator.logln("Replication skipped, since it was too short and no steady state data was available.");
			return;
		} 		
		*/
		
		double runLen = stats.endRunClock;		
		// Skip short runs in CVRG_EST mode
		if (configuration.runMode == SimQPNConfiguration.CVRG_EST && statsLevel >= 3 && runLen < reqMinRunLen)  {
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
				sumTkColOcp[c]		+= stats.tkColOcp[c];						
				sumSqAvgTkPop[c]	+= avgTkPop * avgTkPop;			
				sumSqTkColOcp[c]	+= stats.tkColOcp[c] * stats.tkColOcp[c];
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
				double avgST =  configuration.isUseStdStateStats() ? stats.stdStateMeanST[c] : stats.meanST[c];					
				if (numRepls == 1 || avgST < minAvgST[c]) minAvgST[c] = avgST;
				if (numRepls == 1 || avgST > maxAvgST[c]) maxAvgST[c] = avgST;				
				sumAvgST[c]	  += avgST;								
				sumSqAvgST[c] += avgST * avgST; 
				numAvgST[c]++;														
				if (configuration.getAnalMethod() == SimQPNConfiguration.AnalysisMethod.BATCH_MEANS && configuration.isUseStdStateStats())  {										
					sumBatchSizesST[c]	+= stats.batchSizeST[c];
					sumNumBatchesST[c]	+= stats.numBatchesST[c];
				}								
				if (configuration.runMode == SimQPNConfiguration.CVRG_EST && trueAvgST[c] >= 0) {
					double leftEnd = avgST - stats.ciHalfLenST[c];
					double rightEnd = avgST + stats.ciHalfLenST[c];						 					
					if ((trueAvgST[c] >= leftEnd) && (trueAvgST[c] <= rightEnd)) numCvrgs[c]++;
				}
			}	
		// StatsLevel 5:
		if (statsLevel >= 5) 
			for (int c = 0; c < numColors; c++)  {
				double avgST =  configuration.isUseStdStateStats() ? stats.stdStateMeanST[c] : stats.meanST[c];		
				fileST[c].println(avgST); 
			}
	}

	/**
	 * Method finish - completes the data collection process
	 *                    	 
	 */
	public void finish(SimQPNConfiguration configuration) throws SimQPNException {
		if (statsLevel >= 5) 		
			for (int c = 0; c < numColors; c++)
				fileST[c].close();				
		processStats(configuration);
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
				log.error("Internal error in AggregateStats of place " + name);
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
			log.error("Cannot open file: " + fileName, ex);
			throw new SimQPNException();	
		}
	}
	
	
	/**
	 * Method processStats - processes gathered statistics (summarizes data)
	 *                        	 
	 */	
	public void processStats(SimQPNConfiguration configuration) throws SimQPNException {
		
		if (numRepls < 1) return;
		
		if (configuration.getAnalMethod() == SimQPNConfiguration.AnalysisMethod.WELCH)  {			
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
				meanTkColOcp[c]		= sumTkColOcp[c] / numRepls; 			
				stDevAvgTkPop[c]	= Math.sqrt(Descriptive.sampleVariance(numRepls, sumAvgTkPop[c], sumSqAvgTkPop[c]));
				stDevTkColOcp[c]	= Math.sqrt(Descriptive.sampleVariance(numRepls, sumTkColOcp[c], sumSqTkColOcp[c]));						
			}
			if (type == QUE_PLACE_QUEUE) {	
				meanQueueUtilQPl	= sumQueueUtilQPl / numRepls;  								
				stDevQueueUtilQPl	= Math.sqrt(Descriptive.sampleVariance(numRepls, sumQueueUtilQPl, sumSqQueueUtilQPl));
			}							
		}	

		// StatsLevel 3:
		if (statsLevel >= 3){
			for (int c = 0; c < numColors; c++)  {
				meanAvgST[c]		= sumAvgST[c] / numAvgST[c];
				varAvgST[c]			= Descriptive.sampleVariance(numAvgST[c], sumAvgST[c], sumSqAvgST[c]);
				stDevAvgST[c]		= Math.sqrt(varAvgST[c]);
				ciHalfLenAvgST[c]	= Probability.studentTInverse(signLevAvgST[c], numAvgST[c] - 1) * Math.sqrt(varAvgST[c] / numAvgST[c]); 
				confLevelAvgST[c]	= (int) (100 * (1 - signLevAvgST[c]));				
				if (configuration.getAnalMethod() == SimQPNConfiguration.AnalysisMethod.BATCH_MEANS && configuration.isUseStdStateStats())  {  								
					avgBatchSizeST[c]	= sumBatchSizesST[c] / numRepls;					
					avgNumBatchesST[c]	= sumNumBatchesST[c] / numRepls;					 
				}																					
//				if (Simulator.runMode == Simulator.CVRG_EST && trueAvgST[c] >= 0)  {
//					estCvrg[c]  = ((double) numCvrgs[c]) / numRepls;															
//					trCvrgLowerLimit[c] = getTrCvrgLowerLimit(c);
//					trCvrgUpperLimit[c] = getTrCvrgUpperLimit(c);
//					ciHalfLenTrCvrg[c] = Math.abs(Probability.normalInverse(signLevCvrg / 2)) * Math.sqrt((estCvrg[c] * (1 - estCvrg[c])) / numRepls);					
//				}											
			}
		}
		completed = true;		
	}
	
	/**
	 * Method printReport - prints a summary of the computed statistics
	 *                    	 
	 */
	public void printReport(SimQPNConfiguration configuration) throws SimQPNException {
		//...
		if (!completed) {
			log.error("AggregateStats " + name + ": Attempting to access statistics before data collection has finished!");
			throw new SimQPNException();
		}
		
		StringBuilder report = new StringBuilder();
		
		if (type == ORD_PLACE)						
			report.append("for Ordinary Place: ").append(name).append("----------------------------------------\n");			
		else if (type == QUE_PLACE_DEP)
			report.append("for Depository of Queueing Place: ").append(name).append("----------------------------------------\n");
		else if (type == QUE_PLACE_QUEUE)
			report.append("for Queue of Queueing Place: ").append(name).append("----------------------------------------\n");
		else {
			log.error("Internal error in AggregateStats of place " + name);
			throw new SimQPNException();
		}
		report.append("numReplicationsUsed = ").append(numRepls);
		report.append(" numTooShortRepls = ").append(numTooShortRepls).append("\n"); 				
		report.append("minRunLen=").append(minRunLen);
		report.append(" maxRunLen=").append(maxRunLen);
		report.append(" avgRunLen=").append(avgRunLen);
		report.append(" stDevRunLen=").append(stDevRunLen).append("\n");
		report.append("avgWallClockTime=").append(avgWallClockTime);
		report.append(" stDevWallClockTime=").append(stDevWallClockTime).append("\n");
						
		if (statsLevel >= 2 && type == QUE_PLACE_QUEUE) 
			report.append("meanQueueUtilQPl=").append(meanQueueUtilQPl).append(" stDevQueueUtilQPl=").append(stDevQueueUtilQPl).append("\n");					
			
		for (int c = 0; c < numColors; c++) {
			report.append("\n");				
			report.append("------------------ Color=" + c + " --------------------\n"); // +\n				
			report.append("meanArrivThrPut[c]=").append(meanArrivThrPut[c]).append("\n"); //+\n
			report.append(" meanDeptThrPut[c]=").append(meanDeptThrPut[c]).append("\n");
			report.append("stDevArrivThrPut[c]=").append(stDevArrivThrPut[c]).append("\n"); //+\n
			report.append(" stDevDeptThrPut[c]=").append(stDevDeptThrPut[c]).append("\n");				
											
			if (statsLevel >= 2) {				
				report.append("minAvgTkPop[c]=").append(minAvgTkPop[c]).append(" maxAvgTkPop[c]=").append(maxAvgTkPop[c]).append("\n");
				report.append("meanAvgTkPop[c]=").append(meanAvgTkPop[c]).append(" meanTkColOcp[c]=").append(meanTkColOcp[c]).append("\n");
				report.append("stDevAvgTkPop[c]=").append(stDevAvgTkPop[c]).append(" stDevTkColOcp[c]=").append(stDevTkColOcp[c]).append("\n");
			}
			if (statsLevel >= 3) {												
				report.append("-----\n");
				if (configuration.getAnalMethod() == SimQPNConfiguration.AnalysisMethod.BATCH_MEANS && configuration.isUseStdStateStats())					
					report.append("avgBatchSizeST[c]=").append(avgBatchSizeST[c]).append(" avgNumBatchesST[c]=").append(avgNumBatchesST[c]).append("\n");				
				report.append("meanAvgST[c]=").append(meanAvgST[c]).append(" stDevAvgST[c]=").append(stDevAvgST[c]).append("\n");					
				report.append("\n");																																							
				report.append(confLevelAvgST[c]).append("% c.i. = ").append(meanAvgST[c]).append(" +/- ").append(ciHalfLenAvgST[c]).append("\n");			
				if (configuration.runMode == SimQPNConfiguration.CVRG_EST && trueAvgST[c] >= 0) {					
					report.append("trueAvgST[c]=").append(trueAvgST[c]).append(" estCvrg[c]=").append(estCvrg[c]).append("\n");					
					int confLevelCvrg	= (int) (100 * (1 - signLevCvrg));					
					report.append(confLevelCvrg).append("% c.i. (from F-distr.) = [").append(trCvrgLowerLimit[c]).append(", ").append(trCvrgUpperLimit[c]).append("]\n");
					report.append(confLevelCvrg).append("% c.i. (from N-distr.) = ").append(estCvrg[c]).append(" +/- ").append(ciHalfLenTrCvrg[c]).append("\n");
					report.append("n * p = ").append(numRepls * estCvrg[c]).append("; This should be >= 10 for the above c.i. to be valid!\n"); // see p.215 in Jain
					report.append("n * p * (1 - p) = ").append(numRepls * estCvrg[c] * (1 - estCvrg[c])).append("; For a 95% c.i. this should be >= 10; For 99% c.i. it should be >= 35.\n"); // See p.385 in "Applied Statistics" from Revine, Ramsey, Smidt.										 					
					report.append("\n");					 																																							
				}														
			}
			if (statsLevel >= 5) {
				report.append("Token sojourn times dumped in ").append(statsDir).append("\n");
			}															
		}
		
		log.log(ReportLevel.REPORT, report);
	}
				
}
