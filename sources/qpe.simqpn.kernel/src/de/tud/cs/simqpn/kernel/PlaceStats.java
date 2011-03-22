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
 * Contributor(s): Simon Spinner  
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2003/08/??  Samuel Kounev     Created.
 *  2005/12/13  Samuel Kounev     Modified to make it possible to switch off steady state statistics collection 
 *                                on a per color basis. Steady state statistics are collected for color c only 
 *                                when minBatches[c] > 0.     
 *  2006/08/23  Samuel Kounev     Fixed use of debugLevel.                                                            
 *  2006/10/14  Christofer Dutz   Added @SuppressWarnings("unchecked") and cleaned up 
 *                                imports to avoid warnings!
 *  2006/10/21  Samuel Kounev     Modified to use the Simulator.log() methods for output.                                
 *  2007/06/11  Samuel Kounev     Fixed a bug in enoughStats related to the use of ABSPRC.
 *  2008/12/13  Samuel Kounev     Added code to ignore the result of the batch means correlation test and print  
 *                                a warning in cases where the standard deviation of the token residence time is 0.
 *  2008/12/13  Samuel Kounev     Changed to store names of token colors that can reside in this place.
 *  2008/12/15  Samuel Kounev     Added new statLevel 4 storing sojourn time histogram data.
 *  2008/12/16  Samuel Kounev     Extended to estimate tkOcp - fraction of time that there is a token in the place.
 *  2010/08/02  Simon Spinner     Changed output code to make it more customizable in subclasses.
 *  
 */
package de.tud.cs.simqpn.kernel;

import static de.tud.cs.simqpn.util.LogUtil.formatMultilineMessage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.tree.DefaultElement;

import cern.colt.list.AbstractDoubleList;
import cern.colt.list.DoubleArrayList;
import cern.jet.stat.Descriptive;
import cern.jet.stat.Probability;
import de.tud.cs.simqpn.util.LogUtil.ReportLevel;

/**
 * Class PlaceStats
 * 
 * 
 * @author Samuel Kounev
 * @version %I%, %G%
 */

public class PlaceStats extends Stats implements java.io.Serializable {
	private static final long serialVersionUID = 2L;
	
	private static final Logger log = Logger.getLogger(PlaceStats.class);
 	
	// ----------------------------------------------------------------------------------------------------
	// STATISTICS
	// ----------------------------------------------------------------------------------------------------

	// StatsLevel 1 ---------------------------------------------------------------------------------------
	public int[] arrivCnt;					// Total number of tokens arrived to this place during the run.
	public int[] deptCnt;					// Total number of tokens departed from this place during the run.
	public double[] arrivThrPut;			// Arrival rate = (arrivCnt[c] / msrmPrdLen).
	public double[] deptThrPut;				// Departure rate = (deptCnt[c] / msrmPrdLen).
	
	// StatsLevel 2 ---------------------------------------------------------------------------------------
	public int[] minTkPop;					// Minimum observed token population (TkPop).				
	public int[] maxTkPop;					// Maximum observed token population.
	public double[] areaTkPop;				// Accumulated area under the curve for computing the expected average token population.
	public double[] areaTkColOcp;			// Accumulated area under the curve for computing the expected token color occupancy - for each color the fraction of time that there are tokens of this color in the place.
	public double[] lastColTkPopClock;		// Time of last color token population change.
	public double[] meanTkPop;				// Mean Token Population = (areaTkPop[c] / msrmPrdLen).
	public double[] tkColOcp;				// Token color occupancy = (areaTkColOcp[c] / msrmPrdLen) - fraction of time that there is a token of a given color in the place.
	
	public double	areaTkOcp;				// Accumulated area under the curve for computing the expected token occupancy - fraction of time that there is a token in the place.
	public double	lastTkPopClock;			// Time of last token population change (over all colors of this place).
	public int		lastTotTkPop;			// Last queue total token population (over all colors of this place).		
	public double	tkOcp;					// Token occupancy of the place = (areaTkOcp / msrmPrdLen).
	
	// StatsLevel 3 ---------------------------------------------------------------------------------------
	public double[] minST;					// Minimum observed token sojourn time.
	public double[] maxST;					// Maximum observed token sojourn time.
	public double[] sumST;					// Sum of token sojourn times.
	public double[] sumSqST;				// Sum of token sojourn time squares.
	public int[] numST;						// Number of sojourn times observed.
	public double[] meanST;					// Mean Sojourn Time = (sumST[c] / numST[c]).
	public double[] stDevST;				// Sojourn Time Standard Deviation = Math.sqrt(Descriptive.sampleVariance(numST[c], sumST[c], sumSqST[c])).

	// BATCH_MEANS: Steady state statistics //TODO: Make the stuff below to be used only when method is BATCH_MEANS
	public double[] signLevST;				// Required significance level, (1 - signLevST) = the confidence level.				
	public double[] reqAbsPrc;				// Mode ABSPRC: Required absolute precision (max c.i. half length).
	public double[] reqRelPrc;				// Mode RELPRC: Required relative precision (max ratio of c.i. half length to mean).
	public int[] batchSizeST;				// Batch size for the batch means algorithm.
	public int[] minBatches;				// Minimum number of batches required for steady state statistics.
											// NOTE: If minBatches[c] <= 0, no steady state statistics are collected for this color! 

	// For ((NORMAL/MULT_REPL):BATCH_MEANS, statsLevel >= 3): BATCH MEANS AUTOCORRELATION TEST
	public int[] numBMeansCorlTested;		// If > 0 checks whether the batch size is sufficient - tests for autocorrelation between successive batch means (See paper of Pawlikowski in ACM Computing Surveys, Vol.22, No.2, June 1990).
											// the first numBMeansCorlTested batch means from the beginning of steady state are tested for autocorrelation.
	public boolean[] corlTestPassedOnce;	// Indicates that the first batch size which passes the autocorrelation test has been reached.
	 										// Note that the augorithm uses the second batch size which passes the test (see Pawlikowski).
	public AbstractDoubleList[]
	                          batchMeansST;	// Stores sojourn time batch means; used if numBMeansCorlTested > 0.

	// Assumptions: 
	//    1. Steady state statistics are collected for color c only when minBatches[c] > 0 !    
	//    2. The batch means correlation test is performed for color c only if numBMeansCorlTested[c] > 0.
	//       If (minBatches[c] <= 0), numBMeansCorlTested is ignored!
	//    3. numBMeansCorlTested must be even!
	//    4. We should always have (minBatches[c] > numBMeansCorlTested[c]) to ensure that the 
	//       batch means correlation test has been passed before starting steady state analysis!
	// These assumptions are enforced in the init() method. 
	
	public double[] sumBatchST;				// Sum of means in current batch.
	public double[] sumBMeansST;			// Sum of sojourn time batch means.
	public double[] sumBMeansSqST;			// Sum of sojourn time batch mean squares.
	public int[] numBatchesST;				// Number of batches.
	public boolean stdStateStatsAv;			// true if there were enough batches to compute all requested steady state statistics.
	public double[] stdStateMeanST;			// Steady State Mean Sojourn Time = (sumBMeansST[c] / numBatchesST[c]).
	public double[] varStdStateMeanST;		// Steady State Sojourn Time Variation = Descriptive.sampleVariance(numBatchesST[c], sumBMeansST[c], sumBMeansSqST[c]).
	public double[] stDevStdStateMeanST;	// Steady State Sojourn Time Standard Deviation = Math.sqrt(varStdStateMeanST[c]).
	public double[] ciHalfLenST;			// Confidence Interval Half Length = Probability.studentTInverse(signLevST[c], numBatchesST[c] - 1) * Math.sqrt(varStdStateMeanST[c] / numBatchesST[c]).
	public int[] confLevelST;				// Confidence Level = (int) (100 * (1 - signLevST[c])).

	// INIT_TRANS:WELCH (Method of Welch)	
	public int[] minObsrvST;				// Minumum number of observations required.	
	public int[] maxObsrvST;				// Maximum number of observations considered (if <= 0 token color is not considered in the analysis).
	public AbstractDoubleList[] obsrvST;	// Stores sojourn time observations.

	// StatsLevel 4 ---------------------------------------------------------------------------------------
	public TimeHistogram[] histST;			// Token sojourn time histograms.
	
	// StatsLevel 5 ---------------------------------------------------------------------------------------
	public PrintStream[] fileST;			// Level 5: File output streams for dumping sojourn times.
	
	/**
	 * Constructor
	 * 
	 * @param id 			- global id of the place
	 * @param name 			- name of the place
	 * @param type 			- type of statistics (ORD_PLACE, QUE_PLACE_QUEUE or QUE_PLACE_DEP)
	 * @param colors 	    - names of the colors that can reside in the respective location
	 * @param statsLevel	- determines the amount of statistics to be gathered during the run
	 *            
	 */
	public PlaceStats(int id, String name, int type, String[] colors, int statsLevel) throws SimQPNException {
		super(id, name, type, colors.length, statsLevel);
		this.colors = colors;
		
		// statsLevel >= 1
		this.arrivCnt 								= new int[numColors];
		this.deptCnt 								= new int[numColors];
		this.arrivThrPut 							= new double[numColors];
		this.deptThrPut 							= new double[numColors];
		if (statsLevel >= 2) {
			this.minTkPop 							= new int[numColors];
			this.maxTkPop 							= new int[numColors];
			this.areaTkPop 							= new double[numColors];
			this.areaTkColOcp 						= new double[numColors];
			this.lastColTkPopClock 					= new double[numColors];
			this.meanTkPop 							= new double[numColors];
			this.tkColOcp 							= new double[numColors];
		}
		if (statsLevel >= 3) {
			this.minST 								= new double[numColors];
			this.maxST 								= new double[numColors];
			this.sumST 								= new double[numColors];
			this.sumSqST 							= new double[numColors];
			this.numST 								= new int[numColors];
			this.meanST 							= new double[numColors];
			this.stDevST 							= new double[numColors];
			if (Simulator.analMethod == Simulator.WELCH) {
				this.minObsrvST 					= new int[numColors];
				this.maxObsrvST 					= new int[numColors];
				this.obsrvST 						= new AbstractDoubleList[numColors];
				for (int c = 0; c < numColors; c++) {
					// DEFAULT-CONFIG
					this.minObsrvST[c] 				= 500;
					this.maxObsrvST[c] 				= 100000;
					this.obsrvST[c] 				= new DoubleArrayList();  // Note: Max capacity will be set later so that the user has a chance to modify maxObsrvST.
				}
			}
			if (Simulator.analMethod == Simulator.BATCH_MEANS) {
				this.signLevST 						= new double[numColors];
				this.reqAbsPrc 						= new double[numColors];
				this.reqRelPrc 						= new double[numColors];
				this.batchSizeST					= new int[numColors];
				this.minBatches 					= new int[numColors];
				this.numBMeansCorlTested 			= new int[numColors];
				this.corlTestPassedOnce 			= new boolean[numColors];
				this.batchMeansST 					= new AbstractDoubleList[numColors];
				// DEFAULT-CONFIG: Default parameters for batch means algorithm
				for (int c = 0; c < numColors; c++) {
					this.signLevST[c] 				= 0.1;		// e.g. 0.05 ---> 95% c.i.; 0.1 ---> 90%
					this.reqAbsPrc[c] 				= 20;
					this.reqRelPrc[c] 				= 0.1;		// e.g. 0.1 ---> 10% relative precision
					this.batchSizeST[c] 			= 500;
					this.minBatches[c] 				= 30;		// As per Schmeiser [1982]; see Pawlikowski [1990]
					this.numBMeansCorlTested[c] 	= 28;		// numBMeansCorlTested should be < minBatches and should be even!
					this.corlTestPassedOnce[c] 		= false;	// Note: this is not a parameter and mustn't be modified! 
				}
				this.sumBatchST 					= new double[numColors];
				this.sumBMeansST 					= new double[numColors];
				this.sumBMeansSqST 					= new double[numColors];
				this.numBatchesST 					= new int[numColors];
				this.stdStateMeanST 				= new double[numColors];
				this.varStdStateMeanST 				= new double[numColors];
				this.stDevStdStateMeanST 			= new double[numColors];
				this.ciHalfLenST 					= new double[numColors];
				this.confLevelST 					= new int[numColors];
			}
		}

		if (statsLevel >= 4) {
			this.histST = new TimeHistogram[numColors];
			for (int c = 0; c < numColors; c++) 
				histST[c] = new TimeHistogram();
		}
		
		if ((statsLevel >= 5) && !(this instanceof QPlaceQueueStats)) {
			String fileName = "";
			this.fileST = new PrintStream[numColors];
			for (int c = 0; c < numColors; c++) {
				try {
					if (type == ORD_PLACE)
						fileName = statsDir + fileSep + "RunStats-ord_place" + id + "-col" + c + "-ST.txt";
					else if (type == QUE_PLACE_DEP)
						fileName = statsDir + fileSep + "RunStats-que_place_dep" + id + "-col" + c + "-ST.txt";
					else {
						log.error("Internal error in PlaceStats of place " + name);						
						throw new SimQPNException();						
					}
					this.fileST[c] = new PrintStream(new FileOutputStream(
							fileName));
				} catch (FileNotFoundException ex) {
					log.error("Cannot open file: " + fileName, ex);
					throw new SimQPNException();
				}
			}
		}
	}

	/**
	 * Method init - should be called at the beginning of the measurement period (ideally the beginning of steady state)	    	 
	 * 
	 * @param tokenPop - current token population 
	 * @return
	 * @exception
	 */
	public void init(int[] tokenPop) throws SimQPNException {
		// statsLevel >= 1
		for (int c = 0; c < numColors; c++)  {
			arrivCnt[c]					= 0; //TODO: Should we instead set arrivCnt to tokenPop[c] here? Currently, we could have deptCnt > arrivCnt if there are tokens in the place in the initial marking.
			deptCnt[c]					= 0;
		}
		if (statsLevel >= 2)  {
			areaTkOcp					= 0;
			lastTotTkPop				= 0;
			for (int c = 0; c < numColors; c++) {
				areaTkPop[c] 			= 0;
				areaTkColOcp[c] 		= 0;
				lastTotTkPop			+= tokenPop[c];
				lastColTkPopClock[c]	= Simulator.clock;
				minTkPop[c] 			= tokenPop[c];
				maxTkPop[c] 			= tokenPop[c];
			}
			lastTkPopClock				= Simulator.clock;			
		}
		if (statsLevel >= 3)
			for (int c = 0; c < numColors; c++) {
				minST[c] 				= 0;
				maxST[c] 				= 0;
				sumST[c] 				= 0;
				sumSqST[c] 				= 0;
				numST[c] 				= 0;
				if (Simulator.analMethod == Simulator.BATCH_MEANS
						&& minBatches[c] > 0) {
					sumBatchST[c] 		= 0;
					sumBMeansST[c] 		= 0;
					sumBMeansSqST[c] 	= 0;
					numBatchesST[c] 	= 0;
					// NOTE: Steady state analysis is performed for color c only if minBatches[c] > 0. 
					//       The following important assumptions must be enforced here:     
					//       1. The batch means correlation test is performed for color c only if numBMeansCorlTested[c] > 0.
					//          If (minBatches[c] <= 0), numBMeansCorlTested is ignored!
					//       2. numBMeansCorlTested must be even!
					//       3. We should always have (minBatches[c] > numBMeansCorlTested[c]) to ensure that the 
					//          batch means correlation test has been passed before starting steady state analysis!
					
					if (numBMeansCorlTested[c] > 0) {
						// Ensure that numBMeansCorlTested is even
						if (numBMeansCorlTested[c] % 2 != 0) {
							log.warn(formatMultilineMessage(
									"In " + getTypeDescription() + " " + name + " numBMeansCorlTested for color " + c + " is not even!?",
									"Setting numBMeansCorlTested["	+ c + "] to (numBMeansCorlTested[" + c + "] + 1) = " + (numBMeansCorlTested[c] + 1)
									));
							numBMeansCorlTested[c]++;
						}
						// Ensure that minBatches[c] > numBMeansCorlTested[c]
						if (!(minBatches[c] > numBMeansCorlTested[c])) {
							log.warn(formatMultilineMessage(
									"In " + getTypeDescription() + " " + name + " minBatches <= numBMeansCorlTested for color "	+ c + "!?",
									"Setting minBatches[" + c + "] to numBMeansCorlTested[" + c + "] + 1 = " + (numBMeansCorlTested[c] + 1)
									));
							minBatches[c] = numBMeansCorlTested[c] + 1;
						}
						batchMeansST[c] = new DoubleArrayList(numBMeansCorlTested[c]);
					}
				}
			}
	}

	/**
	 * Method start - should be called at the end of RampUp to start taking measurements.	    	 
	 * 
	 * @param tokenPop - current token population
	 * @return
	 * @exception
	 */
	public void start(int[] tokenPop) throws SimQPNException {		
		init(tokenPop);
		inRampUp = false;
		endRampUpClock = Simulator.clock;
	}

	/**
	 * Method finish - should be called at the end of the measurement period to
	 *                 complete statistics collection.
	 *  
	 * Note: Completes accumulated areas under the curve.   
	 * 
	 * @param tokenPop - current token population
	 * @return
	 * @exception
	 */
	public void finish(int[] tokenPop) throws SimQPNException  {
		if (statsLevel >= 2)  //NOTE: This makes sure areaTkPop, areaTkColOcp and areaTkOcp (and areaQueUtilQPl for QPlaceQueueStats) are complete!
			for (int c = 0; c < numColors; c++)
				updateTkPopStats(c, tokenPop[c], 0);
		endRunClock = Simulator.clock;
		msrmPrdLen = endRunClock - endRampUpClock;		
		runWallClockTime = Simulator.runWallClockTime;

		if (statsLevel >= 5)
			for (int c = 0; c < numColors; c++)
				fileST[c].close();

		processStats();
	}

	
	/**
	 * Method updateTkPopStats
	 * 
	 * @param color     - token color
	 * @param oldTkPop  - old token population
	 * @param delta     - difference between new and old token population
	 */
	public void updateTkPopStats(int color, int oldTkPop, int delta) {
		if (inRampUp) return;
		if (delta > 0)
			arrivCnt[color] += delta;
		else
			deptCnt[color] += (-1) * delta;
		if (statsLevel >= 2) {
			int newTkPop = oldTkPop + delta;
			if (newTkPop < minTkPop[color])
				minTkPop[color] = newTkPop;
			if (newTkPop > maxTkPop[color])
				maxTkPop[color] = newTkPop;
			double elapsedTime = Simulator.clock - lastColTkPopClock[color];
			if (elapsedTime > 0) {
				areaTkPop[color] += elapsedTime * oldTkPop;
				areaTkColOcp[color] += elapsedTime * (oldTkPop > 0 ? 1 : 0);
				lastColTkPopClock[color] = Simulator.clock;
			}														
			elapsedTime = Simulator.clock - lastTkPopClock;			
			if (elapsedTime > 0) {																																			
				areaTkOcp += elapsedTime * (lastTotTkPop > 0 ? 1 : 0);  
				lastTkPopClock = Simulator.clock;			
			}			
			lastTotTkPop += delta;			
		}
	}

	/**
	 * Method updateSojTimeStats
	 * 
	 * @param color   -  token color
	 * @param sojTime -  sojourn time of token in place
	 */
	public void updateSojTimeStats(int color, double sojTime) throws SimQPNException {
		if (Simulator.analMethod == Simulator.WELCH) {
			if (maxObsrvST[color] <= 0) return;		// Do not consider colors with nonpositive maxObsrvST
			int numObsrv = obsrvST[color].size();
			if (numObsrv == maxObsrvST[color]) return;
			if (numObsrv == 0)
				obsrvST[color].ensureCapacity(maxObsrvST[color]);
			obsrvST[color].add(sojTime);
			if ((numObsrv + 1) == maxObsrvST[color] && log.isTraceEnabled()) {
				log.warn(formatMultilineMessage(
						"Maximum number of observations allowed for " + getTypeDescription() + " " + name + ", color " + color + " reached!",
						"Further observations will be ignored. Consider increasing maxObsrvST[c]."
						));
			}
			return;
		}

		if (inRampUp) return;

		if (numST[color] == 0) {
			minST[color] = sojTime;
			maxST[color] = sojTime;
		} else {
			if (sojTime < minST[color])	minST[color] = sojTime;
			if (sojTime > maxST[color])	maxST[color] = sojTime;
		}
		sumST[color]	+= sojTime;
		sumSqST[color]	+= sojTime * sojTime;
		numST[color]++;

		if (Simulator.analMethod == Simulator.BATCH_MEANS
				&& minBatches[color] > 0) {
			sumBatchST[color] += sojTime;
			if (numST[color] % batchSizeST[color] == 0) {
				double bMeanST = sumBatchST[color] / batchSizeST[color];
				sumBMeansST[color] += bMeanST;
				sumBMeansSqST[color] += bMeanST * bMeanST;
				numBatchesST[color]++;
				sumBatchST[color] = 0;
				if (numBMeansCorlTested[color] > 0
						&& numBatchesST[color] <= numBMeansCorlTested[color]) {
					batchMeansST[color].add(bMeanST);
					if (numBatchesST[color] == numBMeansCorlTested[color]) {
						if (testBMeansForCorlST(color)) { // Test PASSED
							// Clear BMeans list to free memory
							batchMeansST[color].clear();
							batchMeansST[color] = null;
						} else { // Test FAILED
							log.trace("Increasing batch size to " + batchSizeST[color] * 2);
							doubleBatchSizeST(color);
						}
					}
				}
			}
		}
		
		if (statsLevel >= 4) 
			histST[color].addEntry(sojTime); 
		
		if (statsLevel >= 5)
			fileST[color].println(sojTime);
	}

	/**
	 * Method doubleBatchSizeST
	 * 
	 * @param col - token color
	 */
	public void doubleBatchSizeST(int color) {
		AbstractDoubleList 
			newBatchMeansST = new DoubleArrayList(numBMeansCorlTested[color]);

		double[] oldBMs = batchMeansST[color].elements();
		batchSizeST[color] *= 2;
		numBatchesST[color] /= 2; // Note: since (numBatchesST == numBMeansCorlTested) we know that numBatchesST is even
		sumBMeansST[color] = 0;
		sumBMeansSqST[color] = 0;

		double newBM;
		for (int i = 0; i < numBatchesST[color]; i++) {
			newBM = (oldBMs[2 * i] + oldBMs[2 * i + 1]) / 2;
			sumBMeansST[color] += newBM;
			sumBMeansSqST[color] += newBM * newBM;
			newBatchMeansST.add(newBM);
		}
		batchMeansST[color].clear();
		batchMeansST[color] = newBatchMeansST;
		sumBatchST[color] = 0;
		// DEBUG:
		if (numST[color] % batchSizeST[color] != 0)
			log.error("In doubleBatchSizeST: numST[color] % batchSizeST[color] != 0 ");
	}

	/**
	 * Method testBMeansForCorlST
	 * 
	 * @param color -
	 *            token color
	 */
	public boolean testBMeansForCorlST(int color) throws SimQPNException {
		int L = (int) (0.1 * numBatchesST[color]);
		double beta = signLevST[color] / L;
		// DEBUG:		
		// beta = ((double) 0.01) / L;
		
		/*DEBUG			
		System.out.println("  Considering lags 1 to " + L + " of " + numBatchesST[color] + " batch means :");
		
		for (int k = 1; k <= L; k++) {
			System.out.println("   Ordinary est. of autocorl. coef. of lag " + k + ": " + 
								estAutoCorlCoef(batchMeansST[color], 0, numBatchesST[color] - 1, k));
		}
		for (int k = 1; k <= L; k++) {
			System.out.println("   Jackknife est. of autocorl. coef. of lag " + k + ": " +
								jkEstAutoCorlCoef(batchMeansST[color], 0, numBatchesST[color] - 1, k));			
		}
		System.out.println();
		*/
		double corl = 0;
		for (int k = 1; k <= L; k++) {
			double jkEstAbs = Math.abs(jkEstAutoCorlCoef(batchMeansST[color], 0, numBatchesST[color] - 1, k));
			double upperBound = Math.abs(Probability.normalInverse(beta / 2)) * Math.sqrt(varAutoCorlCoef(batchMeansST[color], 0, numBatchesST[color] - 1, k));			
			// DEBUG: System.out.println("   Jackknife est. of autocorl. coef. of lag " + k + ": " + jkEst + " UPPERBOUND = " + upperBound);			
			if (!(jkEstAbs < upperBound)) {
				corl += jkEstAbs;
				//DEBUG: System.out.println("  Lag " + k + " autocorr. is NOT statistically negligible at sign. level " + beta);				
			}
		}
		boolean passed = false;
		if (corl == 0) {
			if (corlTestPassedOnce[color])
				passed = true;
			else
				corlTestPassedOnce[color] = true;
		}
		if (!passed) {
			double stDevST = Math.sqrt(Descriptive.sampleVariance(numST[color], sumST[color], sumSqST[color]));
			if (stDevST == 0)  {
				log.warn(formatMultilineMessage(
						"Batch means correlation test was run for " + getTypeDescription() + " " + name + ", color " + color,
						"However, the standard deviation of the token residence time is 0 and therefore the result of the test is ignored!",
						"In future runs, you should set numBMeansCorlTested to 0 to switch off the correlation test!"
						));
				passed = true;
			}
		}		
		if (log.isTraceEnabled()) {
			if (passed)
				log.info("PASSED Batch means correlation test for " + getTypeDescription() + " " + name + ", color " + color);
			else
				log.info("FAILED Batch means correlation test for " + getTypeDescription() + " " + name + ", color " + color);
		}
		return passed;
	}

	/**
	 * Method estAutoCorlCoef
	 *  	 
	 * @param bMeans		
	 * @param from				 
	 * @param to			
	 * @param k	
	 * 
	 * Computes an estimator of the autocorrelation coefficients of lag 'k' for 
	 * the batch means bMeans[from]..bMeans[to].
	 * 
	 * Uses formulas in Pawlikowski [1990]: p.132 
	 * Note: the sequence of batch means can be regarded as nonautocorrelated when all 
	 * autocorrelation coefficents assume small magnitudes, say, if they are less than 0.05
	 * Usually it is suggested to consider autocovariances of the lag not greater than 25% 
	 * of the sample size or even 8-10%.
	 * 	 
	 */
	public double estAutoCorlCoef(AbstractDoubleList bMeans, int from, int to, int k) {
		double[] bMs = bMeans.elements();
		int numBMs = to - from + 1;
		double sumBMs = 0;
		double avgBM = 0;
		double Rk = 0; // estimator of the autocovariance of lag k
		double R0 = 0; // estimator of the autocovatiance of lag 0

		// compute avg batch mean
		for (int i = from; i <= to; i++) sumBMs += bMs[i];
		avgBM = sumBMs / numBMs;

		// compute estimator Rk of the autocovariance of lag k 		
		for (int i = k + 1; i <= numBMs; i++) {
			// Note that the i-th BM is at position (from-1)+i in our array 			
			Rk += ((bMs[from - 1 + i] - avgBM) * (bMs[from - 1 + i - k] - avgBM));
		}
		Rk *= (((double) 1) / (numBMs - k));

		// compute estimator R0 of the autocovatiance of lag 0
		for (int i = 1; i <= numBMs; i++) {
			// Note that the ith BM is at position (from-1)+i in our array 			
			R0 += ((bMs[from - 1 + i] - avgBM) * (bMs[from - 1 + i] - avgBM));
		}
		R0 *= (((double) 1) / numBMs);

		return Rk / R0;
	}

	/**
	 * Method jkEstAutoCorlCoef
	 *  	 
	 * @param bMeans		
	 * @param from				 
	 * @param to			
	 * @param k	
	 * 
	 * Computes a jackknife estimator of the autocorrelation coefficients of lag 'k' for 
	 * the batch means bMeans[from]..bMeans[to].
	 * 
	 * Uses formulas in Pawlikowski [1990]: p.154
	 *  
	 */
	public double jkEstAutoCorlCoef(AbstractDoubleList bMeans, int from,
			int to, int k) {
		int numBMs = to - from + 1;
		return (2 * estAutoCorlCoef(bMeans, from, to, k))
				- ((estAutoCorlCoef(bMeans, from, from + (numBMs / 2) - 1, k) + estAutoCorlCoef(
						bMeans, from + (numBMs / 2), to, k)) / 2);
	}

	/**
	 * Method varAutoCorlCoef
	 * 
	 * @param bMeans		
	 * @param from				 
	 * @param to			
	 * @param k	
  	 * 
	 * Computes the variance of the autocorrelation coefficient of lag 'k' [Bartlett 1946] 
	 * 
	 * Uses formulas in Pawlikowski [1990]: p.157
	 *  
	 */
	public double varAutoCorlCoef(AbstractDoubleList bMeans, int from, int to,
			int k) {
		int numBMs = to - from + 1;
		double var = 0;
		double jkEst;
		if (k == 1)
			var = ((double) 1) / numBMs;
		else {
			for (int u = 1; u <= k - 1; u++) {
				jkEst = jkEstAutoCorlCoef(bMeans, from, to, u);
				var += (jkEst * jkEst);
			}
			var = (var * 2 + 1) / numBMs;
		}
		return var;
	}

	/**
	 * Method enoughStats - returns true if enough data is available to provide estimates 
	 *                      with the required precision. Applicable only for statLevel >= 3 in 
	 *                      modes ABSPRC and RELPRC.  
	 */
	public boolean enoughStats() throws SimQPNException {
		if (statsLevel < 3) return true;

		if (Simulator.analMethod != Simulator.BATCH_MEANS) {
			log.error(formatMultilineMessage(
					"PlaceStats.enoughStats should only be called when BATCH_MEANS method is used!",
					"Please check your configuration parameters"
					));
			throw new SimQPNException();
		}

		boolean passed = true;
		double varStdStateMeanST = -1, ciHalfLenST = -1, stdStateMeanST = -1;
		int c;

		if (log.isTraceEnabled()) {
			log.trace("Checking for enough stats in " + getTypeDescription() + " " + name);	
		}

		int passedColors = 0;
		for (c = 0; c < numColors; c++) {
			if (minBatches[c] <= 0) continue;
			if (numBatchesST[c] < minBatches[c]) {
				if (log.isTraceEnabled()) {
					log.trace(formatMultilineMessage(
							"Checking for enough stats in " + getTypeDescription() + " " + name,
							"FAILED because (numBatchesST=" + numBatchesST[c] + ") < (minBatches=" + minBatches[c] + ") for color " + c));
				}
				passed = false;
				break;
			}
			varStdStateMeanST = Descriptive.sampleVariance(numBatchesST[c], sumBMeansST[c], sumBMeansSqST[c]);
			ciHalfLenST = Probability.studentTInverse(signLevST[c], numBatchesST[c] - 1) * Math.sqrt(varStdStateMeanST / numBatchesST[c]);
			if ((Simulator.stoppingRule == Simulator.ABSPRC) && (ciHalfLenST > reqAbsPrc[c])) {
				if (log.isTraceEnabled()) {
					log.trace(formatMultilineMessage(
							"Checking for enough stats in " + getTypeDescription() + " " + name,
							"FAILED because (ciHalfLenST=" + ciHalfLenST + ") > (reqAbsPrc=" + reqAbsPrc[c] + ") for color " + c
							));
				}
				passed = false;
				break;
			} else if (Simulator.stoppingRule == Simulator.RELPRC) {
				stdStateMeanST = sumBMeansST[c] / numBatchesST[c];
				if (this instanceof QPlaceQueueStats) {
					QPlaceQueueStats qSt = (QPlaceQueueStats) this;
					if (qSt.indrStats)
						stdStateMeanST += qSt.meanServTimes[c];
				}
				if ((ciHalfLenST / stdStateMeanST) > reqRelPrc[c]) {
					if (log.isTraceEnabled()) {
						log.trace(formatMultilineMessage(
								"Checking for enough stats in " + getTypeDescription() + " " + name,
								"FAILED because {(ciHalfLenST="
								+ ciHalfLenST + ") / (stdStateMeanST="
								+ stdStateMeanST + ") == "
								+ (ciHalfLenST / stdStateMeanST)
								+ "} > (reqRelPrc=" + reqRelPrc[c]
								+ ") for color " + c
								));
					}
					passed = false;
					break;
				}
			}
			passedColors++;
		}

		if (passed && log.isTraceEnabled()) {
			log.trace(formatMultilineMessage(
					"Checking for enough stats in " + getTypeDescription() + " " + name,
					"PASSED: Enough stats gathered for " + getTypeDescription() + " " + name
					));			
		}
		return passed;
	}

	/**
	 * Method processStats - processes gathered statistics (summarizes data)
	 * 
	 */
	public void processStats() throws SimQPNException {
		stdStateStatsAv = true;
		
		if (statsLevel >= 2)
			tkOcp = areaTkOcp / msrmPrdLen;
		
		for (int c = 0; c < numColors; c++) {
			arrivThrPut[c] = arrivCnt[c] / msrmPrdLen;
			deptThrPut[c] = deptCnt[c] / msrmPrdLen;
			if (statsLevel >= 2) {
				meanTkPop[c] = areaTkPop[c] / msrmPrdLen;
				tkColOcp[c] = areaTkColOcp[c] / msrmPrdLen;
			}
			if (statsLevel >= 3) {
				meanST[c] = sumST[c] / numST[c];
				stDevST[c] = Math.sqrt(Descriptive.sampleVariance(numST[c],
						sumST[c], sumSqST[c]));
				if (Simulator.analMethod == Simulator.BATCH_MEANS && minBatches[c] > 0) {
					// Steady State Statistics
					if (numBatchesST[c] >= minBatches[c]) {
						stdStateMeanST[c] = sumBMeansST[c] / numBatchesST[c];
						varStdStateMeanST[c] = Descriptive.sampleVariance(
								numBatchesST[c], sumBMeansST[c],
								sumBMeansSqST[c]);
						stDevStdStateMeanST[c] = Math
								.sqrt(varStdStateMeanST[c]);
						ciHalfLenST[c] = Probability.studentTInverse(
								signLevST[c], numBatchesST[c] - 1)
								* Math.sqrt(varStdStateMeanST[c]
										/ numBatchesST[c]);
						confLevelST[c] = (int) (100 * (1 - signLevST[c]));
					} else
						stdStateStatsAv = false;
				}
			}
		}
		completed = true;
	}

	/**
	 * Method printReport - prints a summary of the computed statistics
	 * 
	 */
	public void printReport() throws SimQPNException {

		if (!completed) {
			log.error("PlaceStats " + name + ": Attempting to access statistics before data collection has finished!");
			throw new SimQPNException();
		}
		
		StringBuilder report = new StringBuilder();
	
		printReportHeader(report);

		report.append("\n");

		printPlaceStats(report); 
				
		for (int c = 0; c < numColors; c++) {
			report.append("\n");
			report.append("------------------ Color = " + colors[c] + " --------------------\n");
			
			printColorPopulationStats(report, c);
			
			printColorSojournTimeStats(report, c);
		}
		
		report.append("\n\n");
		
		//Output report
		log.log(ReportLevel.REPORT, report);
	}

	/**
	 * Prints the sojourn times for the specified color.
	 * @param report 
	 * @param c color index
	 */
	protected void printColorSojournTimeStats(StringBuilder report, int c) {
		if (statsLevel >= 3) {
			report.append("-----\n");
			// Simulator.logln("numST=" + numST[c] + " minST=" + minST[c] + " maxST=" + maxST[c]);
			report.append("meanST=").append(meanST[c]);
			report.append(" stDevST=").append(stDevST[c]).append("\n");				
			if (Simulator.analMethod == Simulator.BATCH_MEANS && minBatches[c] > 0) {
				report.append("\n");
				report.append("Steady State Statistics: ");
				if (numBatchesST[c] >= minBatches[c]) {
					report.append("numBatchesST=").append(numBatchesST[c]);
					report.append(" batchSizeST=").append(batchSizeST[c]);
					report.append(" stDevStdStateMeanST=").append(stDevStdStateMeanST[c]).append("\n");
					report.append(confLevelST[c]).append("% c.i. = ").append(stdStateMeanST[c])
							.append(" +/- ").append(ciHalfLenST[c]).append("\n");
				} else {
					report.append("Only " + numBatchesST[c] + " batches collected!\n");
					report.append("Need at least " + minBatches[c] + " for steady state statistics.\n");
				}
			}
		}
		if (statsLevel >= 4) {
			report.append("-----\n");
			report.append("Histogram Data\n");
			for (int i = 1; i < 10; i++) 
				report.append("   ").append(i*10).append("% percentile=")
						.append(histST[c].getPercentile((double) i / 10)).append("\n");
			report.append("   histogramMean=").append(histST[c].getMean()).append("\n");				
		}						
		if (statsLevel >= 5) {
			report.append("Token sojourn times dumped in ").append(statsDir).append("\n");
		}
	}

	/**
	 * Prints the population stats for the specified color
	 * @param report 
	 * @param c color index
	 */
	protected void printColorPopulationStats(StringBuilder report, int c) {
		report.append("arrivCnt=").append(arrivCnt[c]);
		report.append(" deptCnt=").append(deptCnt[c]).append("\n");
		
		
		report.append("arrivThrPut=").append(arrivThrPut[c]);
		report.append(" deptThrPut=").append(deptThrPut[c]).append("\n");
		if (statsLevel >= 2) {
			// Simulator.logln("minTkPop=" + minTkPop[c] + " maxTkPop=" +
			// maxTkPop[c]);
			report.append("meanTkPop=").append(meanTkPop[c]);
			report.append(" tkColOcp=").append(tkColOcp[c]).append("\n");
		}
	}

	/**
	 * Outputs the stats that belong to the complete place.
	 * @param report 
	 */
	protected void printPlaceStats(StringBuilder report) {
		if (statsLevel >= 2) 
			report.append("tkOcp=").append(tkOcp).append("\n");
	}

	/**
	 * Prints a human-readable header for the report of this Stats object.
	 * Can be overriden to customize the report output in subclasses
	 * @param report 
	 * @throws SimQPNException
	 */
	protected void printReportHeader(StringBuilder report) throws SimQPNException {
		if (type == ORD_PLACE)
			report.append("for Ordinary Place : ").append(name).append("----------------------------------------\n");
		else if (type == QUE_PLACE_DEP)
			report.append("for Depository of Queueing Place : ").append(name).append("----------------------------------------\n");
		else {
			log.error("Internal error in PlaceStats of place " + name);
			throw new SimQPNException();			
		}
	}

	/* ADDED BY CDUTZ */
	public void addReportMetaData(Element element) {
		// Iterate through all color-refs and set the result-data for each one.
		Iterator colorRefIterator = element
				.elementIterator("color-refs/color-ref");
		for (int c = 0; colorRefIterator.hasNext(); c++) {
			Element colorRef = (Element) colorRefIterator.next();

			// Create a new meta-attribute tag and set the attributes.
			Element metaAttribute = new DefaultElement("meta-attribute");
			metaAttribute.addAttribute("name", "simulator-results");

			metaAttribute.addAttribute("arrivCnt", Integer
					.toString(arrivCnt[c]));
			metaAttribute.addAttribute("deptCnt", Double.toString(minST[c]));
			metaAttribute.addAttribute("arrivThrPut", Double
					.toString(arrivThrPut[c]));
			metaAttribute.addAttribute("deptThrPut", Double
					.toString(deptThrPut[c]));
			if (statsLevel >= 2) {
				metaAttribute.addAttribute("minTkPop", Integer
						.toString(minTkPop[c]));
				metaAttribute.addAttribute("maxTkPop", Integer
						.toString(maxTkPop[c]));
				metaAttribute.addAttribute("meanTkPop", Double
						.toString(meanTkPop[c]));
				metaAttribute.addAttribute("tkColOcp", Double
						.toString(tkColOcp[c]));
				if (statsLevel >= 3) {
					metaAttribute.addAttribute("numST", Integer
							.toString(numST[c]));
					metaAttribute.addAttribute("minST", Double
							.toString(minST[c]));
					metaAttribute.addAttribute("maxST", Double
							.toString(maxST[c]));
					metaAttribute.addAttribute("meanST", Double
							.toString(meanST[c]));
					metaAttribute.addAttribute("stDevST", Double
							.toString(stDevST[c]));
					if (Simulator.analMethod == Simulator.BATCH_MEANS
							&& minBatches[c] > 0) {
						if (numBatchesST[c] >= minBatches[c]) {
							metaAttribute.addAttribute("numBatchesST", Double
									.toString(numBatchesST[c]));
							metaAttribute.addAttribute("batchSizeST", Double
									.toString(batchSizeST[c]));
							metaAttribute.addAttribute("stDevStdStateMeanST",
									Double.toString(stDevStdStateMeanST[c]));
							// TODO: have a look at how this should be added.
							// Simulator.logln(confLevelST[c] + "% c.i. = " +
							// stdStateMeanST[c] + " +/- " + ciHalfLenST[c]);
						} else {
							// TODO: have a look at how this should be added.
							// Simulator.logln("Only " + numBatchesST[c] + "
							// batches collected!");
							// Simulator.logln("Need at least " +
							// minBatches[c] + " for steady state statistics.");
						}
					}
					if (statsLevel >= 5) {
						metaAttribute
								.addAttribute("sojournTimesFile", statsDir);
					}
				}
			}

			Element metaAttributeContainer = colorRef
					.element("meta-attributes");
			if (metaAttributeContainer == null) {
				metaAttributeContainer = colorRef.addElement("meta-attributes");
			}

			// Eventually remove an old meta-attribute.
			XPath xpathSelector = DocumentHelper
					.createXPath("meta-attribute[@name = 'simulator-results'");
			Element oldMetaAttribute = (Element) xpathSelector
					.selectSingleNode(metaAttributeContainer);
			if (oldMetaAttribute != null) {
				metaAttributeContainer.remove(oldMetaAttribute);
			}
			
			// Add the new meta-attribute.
			metaAttributeContainer.add(metaAttribute);
		}
	}
	
	/**
	 * Used when printing warning and messages to the output. Can be customized in subclasses.
	 * @return a human readable description of the object assiociated with this Stats object.
	 * @throws SimQPNException
	 */
	protected String getTypeDescription() throws SimQPNException {
		if (type == ORD_PLACE)
			return "place";
		else if (type == QUE_PLACE_QUEUE)
			return "queue of place";
		else if (type == QUE_PLACE_DEP)
			return "depository of place";
		else {
			log.error("Internal error in PlaceStats of place " + name);
			throw new SimQPNException();
		}
	}
}
