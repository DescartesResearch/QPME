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
 *  ----------  ----------------  -----------------------------------------------------------------------------
 *  2003/08/??  Samuel Kounev     Created.
 *  2005/12/13  Samuel Kounev     Modified to make it possible to switch off steady state statistics collection 
 *                                on a per color basis. Steady state statistics are collected for color c only 
 *                                when minBatches[c] > 0.     
 *  2006/08/23  Samuel Kounev     Fixed use of debugLevel.                                                            
 *  2006/10/14  Christofer Dutz   Added @SuppressWarnings("unchecked") and cleaned up 
 *                                imports to avoid warnings!
 *  2006/10/21  Samuel Kounev     Modified to use the Simulator.log() methods for output.                                
 *  2007/06/11  Samuel Kounev     Fixed a bug in enoughStats related to the use of ABSPRC.
 * 
 */

package de.tud.cs.simqpn.kernel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Iterator;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.tree.DefaultElement;

import cern.colt.list.AbstractDoubleList;
import cern.colt.list.DoubleArrayList;
import cern.jet.stat.Descriptive;
import cern.jet.stat.Probability;
import de.tud.cs.qpe.model.DocumentManager;

/**
 * Class PlaceStats
 * 
 * 
 * @author Samuel Kounev
 * @version %I%, %G%
 */

public class PlaceStats extends Stats implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	public boolean inRampUp;

	public double endRampUpClock;

	public double endRunClock;

	public double msrmPrdLen;

	public double runWallClockTime;

	// ----------------------------------------------------------------------------------------------------
	// STATISTICS
	// ----------------------------------------------------------------------------------------------------

	public int[] arrivCnt;

	public int[] deptCnt;

	public double[] arrivThrPut;

	public double[] deptThrPut;

	public int[] minTkPop;

	public int[] maxTkPop;

	public double[] areaTkPop;

	public double[] areaColUtil;

	public double[] lastColTkPopClock;

	public double[] meanTkPop;

	public double[] colUtil;

	public double[] minST;

	public double[] maxST;

	public double[] sumST;

	public double[] sumSqST;

	public int[] numST;

	public double[] meanST;

	public double[] stDevST;

	public double[] signLevST;

	public double[] reqAbsPrc;

	public double[] reqRelPrc;

	public int[] batchSizeST;

	public int[] minBatches;

	public int[] numBMeansCorlTested;

	public boolean[] corlTestPassedOnce;

	public AbstractDoubleList[] batchMeansST;

	public double[] sumBatchST;

	public double[] sumBMeansST;

	public double[] sumBMeansSqST;

	public int[] numBatchesST;

	public boolean stdStateStatsAv;

	public double[] stdStateMeanST;

	public double[] varStdStateMeanST;

	public double[] stDevStdStateMeanST;

	public double[] ciHalfLenST;

	public int[] confLevelST;

	public int[] minObsrvST;

	public int[] maxObsrvST;

	public AbstractDoubleList[] obsrvST;

	public PrintStream[] fileST;

	/**
	 * Constructor
	 * 
	 * @param id -
	 *            global id of the place
	 * @param name -
	 *            name of the place
	 * @param type -
	 *            type of statistics (PLACE, QUEUE or DEPOSITORY)
	 * @param numColors -
	 *            number of colors
	 * @param statsLevel -
	 *            determines the amount of statistics to be gathered during the
	 *            run
	 */
	public PlaceStats(int id, String name, int type, int numColors,
			int statsLevel) throws SimQPNException {
		super(id, name, type, numColors, statsLevel);

		this.inRampUp = true;
		this.endRampUpClock = 0;
		this.endRunClock = 0;
		this.msrmPrdLen = 0;
		this.runWallClockTime = 0;

		// statsLevel >= 1
		this.arrivCnt = new int[numColors];
		this.deptCnt = new int[numColors];
		this.arrivThrPut = new double[numColors];
		this.deptThrPut = new double[numColors];
		if (statsLevel >= 2) {
			this.minTkPop = new int[numColors];
			this.maxTkPop = new int[numColors];
			this.areaTkPop = new double[numColors];
			this.areaColUtil = new double[numColors];
			this.lastColTkPopClock = new double[numColors];
			this.meanTkPop = new double[numColors];
			this.colUtil = new double[numColors];
		}
		if (statsLevel >= 3) {
			this.minST = new double[numColors];
			this.maxST = new double[numColors];
			this.sumST = new double[numColors];
			this.sumSqST = new double[numColors];
			this.numST = new int[numColors];
			this.meanST = new double[numColors];
			this.stDevST = new double[numColors];
			if (Simulator.analMethod == Simulator.WELCH) {
				this.minObsrvST = new int[numColors];
				this.maxObsrvST = new int[numColors];
				this.obsrvST = new AbstractDoubleList[numColors];
				for (int c = 0; c < numColors; c++) {
					// DEFAULT-CONFIG
					this.minObsrvST[c] = 500;
					this.maxObsrvST[c] = 100000;
					this.obsrvST[c] = new DoubleArrayList();
				}
			}
			if (Simulator.analMethod == Simulator.BATCH_MEANS) {
				this.signLevST = new double[numColors];
				this.reqAbsPrc = new double[numColors];
				this.reqRelPrc = new double[numColors];
				this.batchSizeST = new int[numColors];
				this.minBatches = new int[numColors];
				this.numBMeansCorlTested = new int[numColors];
				this.corlTestPassedOnce = new boolean[numColors];
				this.batchMeansST = new AbstractDoubleList[numColors];
				// DEFAULT-CONFIG: Default parameters for batch means algorithm
				for (int c = 0; c < numColors; c++) {
					this.signLevST[c] = 0.1;
					this.reqAbsPrc[c] = 20;
					this.reqRelPrc[c] = 0.1;
					this.batchSizeST[c] = 500;
					this.minBatches[c] = 30;
					this.numBMeansCorlTested[c] = 28;
					this.corlTestPassedOnce[c] = false;
				}
				this.sumBatchST = new double[numColors];
				this.sumBMeansST = new double[numColors];
				this.sumBMeansSqST = new double[numColors];
				this.numBatchesST = new int[numColors];
				this.stdStateMeanST = new double[numColors];
				this.varStdStateMeanST = new double[numColors];
				this.stDevStdStateMeanST = new double[numColors];
				this.ciHalfLenST = new double[numColors];
				this.confLevelST = new int[numColors];
			}
		}

		if ((statsLevel >= 4) && !(this instanceof QueueStats)) {
			String fileName = "";
			this.fileST = new PrintStream[numColors];
			for (int c = 0; c < numColors; c++) {
				try {
					if (type == PLACE)
						fileName = statsDir + fileSep + "RunStats-place" + id
								+ "-col" + c + "-ST.txt";
					else
						fileName = statsDir + fileSep + "RunStats-depository"
								+ id + "-col" + c + "-ST.txt";
					this.fileST[c] = new PrintStream(new FileOutputStream(
							fileName));
				} catch (FileNotFoundException ex) {
					Simulator.logln("Error: Cannot open file: " + fileName);
					Simulator.logln();
					Simulator.log(ex);
					throw new SimQPNException();
				}
			}
		}
	}

	/**
	 * Method init
	 * 
	 * @param tokenPop -
	 *            current token population
	 * @return
	 * @exception
	 */
	public void init(int[] tokenPop) {
		// statsLevel >= 1
		for (int c = 0; c < numColors; c++) {
			arrivCnt[c] = 0;
			deptCnt[c] = 0;
		}
		if (statsLevel >= 2)
			for (int c = 0; c < numColors; c++) {
				minTkPop[c] = tokenPop[c];
				maxTkPop[c] = tokenPop[c];
				areaTkPop[c] = 0;
				areaColUtil[c] = 0;
				lastColTkPopClock[c] = Simulator.clock;
			}
		if (statsLevel >= 3)
			for (int c = 0; c < numColors; c++) {
				minST[c] = 0;
				maxST[c] = 0;
				sumST[c] = 0;
				sumSqST[c] = 0;
				numST[c] = 0;
				if (Simulator.analMethod == Simulator.BATCH_MEANS
						&& minBatches[c] > 0) {
					sumBatchST[c] = 0;
					sumBMeansST[c] = 0;
					sumBMeansSqST[c] = 0;
					numBatchesST[c] = 0;
					if (numBMeansCorlTested[c] > 0) {
						if (numBMeansCorlTested[c] % 2 != 0) {
							if (type == PLACE)
								Simulator.log("Warning: In place " + name);
							else if (type == QUEUE)
								Simulator.log("Warning: In queue " + name);
							else if (type == DEPOSITORY)
								Simulator.log("Warning: In depository "	+ name);
								Simulator.logln(" numBMeansCorlTested for color " + c + " is not even!?");
								Simulator.logln(" Setting numBMeansCorlTested["	+ c + "] to (numBMeansCorlTested[" 
										+ c + "] + 1) = " + (numBMeansCorlTested[c] + 1));
							numBMeansCorlTested[c]++;
						}
						if (!(minBatches[c] > numBMeansCorlTested[c])) {
							if (type == PLACE)
								Simulator.log("Warning: In place " + name);
							else if (type == QUEUE)
								Simulator.log("Warning: In queue " + name);
							else if (type == DEPOSITORY)
								Simulator.log("Warning: In depository "
										+ name);
								Simulator.logln(" minBatches <= numBMeansCorlTested for color "	+ c + "!?");
								Simulator.logln("         Setting minBatches[" + c + "] to numBMeansCorlTested[" + c + "] + 1 = "
											+ (numBMeansCorlTested[c] + 1));
							minBatches[c] = numBMeansCorlTested[c] + 1;
						}
						batchMeansST[c] = new DoubleArrayList(
								numBMeansCorlTested[c]);
					}
				}
			}
	}

	/**
	 * Method start
	 * 
	 * @param tokenPop -
	 *            current token population
	 * @return
	 * @exception
	 */
	public void start(int[] tokenPop) {
		init(tokenPop);
		inRampUp = false;
		endRampUpClock = Simulator.clock;
	}

	/**
	 * Method finish
	 * 
	 * @param tokenPop -
	 *            current token population
	 * @return
	 * @exception
	 */
	public void finish(int[] tokenPop) {
		if (statsLevel >= 2)
			for (int c = 0; c < numColors; c++)
				updateTkPopStats(c, tokenPop[c], 0);
		endRunClock = Simulator.clock;
		msrmPrdLen = endRunClock - endRampUpClock;
		runWallClockTime = Simulator.runWallClockTime;

		if (statsLevel >= 4)
			for (int c = 0; c < numColors; c++)
				fileST[c].close();

		processStats();
	}

	/**
	 * Method updateTkPopStats
	 * 
	 * @param color -
	 *            token color
	 * @param oldTkPop -
	 *            old token population
	 * @param delta -
	 *            difference between new and old token population
	 */
	public void updateTkPopStats(int color, int oldTkPop, int delta) {
		if (inRampUp)
			return;
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
				areaColUtil[color] += elapsedTime * (oldTkPop > 0 ? 1 : 0);
				lastColTkPopClock[color] = Simulator.clock;
			}
		}
	}

	/**
	 * Method updateSojTimeStats
	 * 
	 * @param color -
	 *            token color
	 * @param sojTime -
	 *            sojourn time of token in place
	 */
	public void updateSojTimeStats(int color, double sojTime) {
		if (Simulator.analMethod == Simulator.WELCH) {
			if (maxObsrvST[color] <= 0)
				return; // Do not consider colors with nonpositive maxObsrvST
			int numObsrv = obsrvST[color].size();
			if (numObsrv == maxObsrvST[color])
				return;
			if (numObsrv == 0)
				obsrvST[color].ensureCapacity(maxObsrvST[color]);
			obsrvST[color].add(sojTime);
			if ((numObsrv + 1) == maxObsrvST[color] && Simulator.debugLevel > 0) {
				Simulator.log("Warning: Maximum number of observations allowed for ");
				if (type == PLACE)
					Simulator.log("place " + name + ", color " + color);
				else if (type == QUEUE)
					Simulator.log("queue " + name + ", color " + color);
				else if (type == DEPOSITORY)
					Simulator.log("depository " + name + ", color " + color);
				Simulator.logln(" reached!");
				Simulator.logln("         Further observations will be ignored. Consider increasing maxObsrvST[c].");
			}
			return;
		}

		if (inRampUp)
			return;

		if (numST[color] == 0) {
			minST[color] = sojTime;
			maxST[color] = sojTime;
		} else {
			if (sojTime < minST[color])
				minST[color] = sojTime;
			if (sojTime > maxST[color])
				maxST[color] = sojTime;
		}
		sumST[color] += sojTime;
		sumSqST[color] += sojTime * sojTime;
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
							Simulator.logln(3, "      Increasing batch size to " + batchSizeST[color] * 2);
							doubleBatchSizeST(color);
						}
					}
				}
			}
		}
		if (statsLevel >= 4)
			fileST[color].println(sojTime);
	}

	/**
	 * Method doubleBatchSizeST
	 * 
	 * @param col -
	 *            token color
	 */
	public void doubleBatchSizeST(int color) {
		AbstractDoubleList newBatchMeansST = new DoubleArrayList(
				numBMeansCorlTested[color]);

		double[] oldBMs = batchMeansST[color].elements();
		batchSizeST[color] *= 2;
		numBatchesST[color] /= 2; // Note: since (numBatchesST ==
		// numBMeansCorlTested) we know that
		// numBatchesST is even
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
			Simulator.logln("PROBLEM !!!!");
	}

	/**
	 * Method testBMeansForCorlST
	 * 
	 * @param color -
	 *            token color
	 */
	public boolean testBMeansForCorlST(int color) {
		int L = (int) (0.1 * numBatchesST[color]);
		double beta = signLevST[color] / L;
		double corl = 0;
		for (int k = 1; k <= L; k++) {
			double jkEstAbs = Math.abs(jkEstAutoCorlCoef(batchMeansST[color],
					0, numBatchesST[color] - 1, k));
			double upperBound = Math.abs(Probability.normalInverse(beta / 2))
					* Math.sqrt(varAutoCorlCoef(batchMeansST[color], 0,
							numBatchesST[color] - 1, k));
			if (!(jkEstAbs < upperBound)) {
				corl += jkEstAbs;
			}
		}
		boolean passed = false;
		if (corl == 0) {
			if (corlTestPassedOnce[color])
				passed = true;
			else
				corlTestPassedOnce[color] = true;
		}
		if (Simulator.debugLevel >= 3) {
			if (passed)
				Simulator.log("Info: PASSED ");
			else
				Simulator.log("Info: FAILED ");
			Simulator.log("Batch means correlation test for ");
			if (type == PLACE)
				Simulator.log("place " + name + ", color " + color);
			else if (type == QUEUE)
				Simulator.log("queue " + name + ", color " + color);
			else if (type == DEPOSITORY)
				Simulator.log("depository " + name + ", color " + color);
			Simulator.logln();
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
	 */
	public double estAutoCorlCoef(AbstractDoubleList bMeans, int from, int to,
			int k) {
		double[] bMs = bMeans.elements();
		int numBMs = to - from + 1;
		double sumBMs = 0;
		double avgBM = 0;
		double Rk = 0;
		double R0 = 0;

		for (int i = from; i <= to; i++)
			sumBMs += bMs[i];
		avgBM = sumBMs / numBMs;

		for (int i = k + 1; i <= numBMs; i++) {
			Rk += ((bMs[from - 1 + i] - avgBM) * (bMs[from - 1 + i - k] - avgBM));
		}
		Rk *= (((double) 1) / (numBMs - k));

		for (int i = 1; i <= numBMs; i++) {
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
	 * Method enoughStats
	 * 
	 * 
	 */
	public boolean enoughStats() throws SimQPNException {
		if (statsLevel < 3)
			return true;

		if (Simulator.analMethod != Simulator.BATCH_MEANS) {
			Simulator.logln("Error: PlaceStats.enoughStats should only be called when BATCH_MEANS method is used!");
			Simulator.logln("       Please check your configuration parameters");
			throw new SimQPNException();
		}

		boolean passed = true;
		double varStdStateMeanST = -1, ciHalfLenST = -1, stdStateMeanST = -1;
		int c;

		if (Simulator.debugLevel >= 3) {
			if (type == PLACE)
				Simulator.logln("Checking for enough stats in place " + name);
			else if (type == QUEUE)
				Simulator.logln("Checking for enough stats in queue " + name);
			else if (type == DEPOSITORY)
				Simulator.logln("Checking for enough stats in depository " + name);
		}

		for (c = 0; c < numColors; c++) {
			if (minBatches[c] <= 0)
				continue;
			if (numBatchesST[c] < minBatches[c]) {
				if (Simulator.debugLevel >= 3) {
					Simulator.logln("  FAILED because (numBatchesST="
							+ numBatchesST[c] + ") < (minBatches="
							+ minBatches[c] + ") for color " + c);
					Simulator.logln();
				}
				passed = false;
				break;
			}
			varStdStateMeanST = Descriptive.sampleVariance(numBatchesST[c],
					sumBMeansST[c], sumBMeansSqST[c]);
			ciHalfLenST = Probability.studentTInverse(signLevST[c],
					numBatchesST[c] - 1)
					* Math.sqrt(varStdStateMeanST / numBatchesST[c]);
			if ((Simulator.stoppingRule == Simulator.ABSPRC)
					&& (ciHalfLenST > reqAbsPrc[c])) {
				if (Simulator.debugLevel >= 3) {
					Simulator.logln("  FAILED because (ciHalfLenST="
							+ ciHalfLenST + ") > (reqAbsPrc=" + reqAbsPrc[c]
							+ ") for color " + c);
					Simulator.logln();
				}
				passed = false;
				break;
			} else if (Simulator.stoppingRule == Simulator.RELPRC) {
				stdStateMeanST = sumBMeansST[c] / numBatchesST[c];
				if (this instanceof QueueStats) {
					QueueStats qSt = (QueueStats) this;
					if (qSt.indrStats)
						stdStateMeanST += qSt.meanServTimes[c];
				}
				if ((ciHalfLenST / stdStateMeanST) > reqRelPrc[c]) {
					if (Simulator.debugLevel >= 3) {
						Simulator.logln("  FAILED because {(ciHalfLenST="
								+ ciHalfLenST + ") / (stdStateMeanST="
								+ stdStateMeanST + ") == "
								+ (ciHalfLenST / stdStateMeanST)
								+ "} > (reqRelPrc=" + reqRelPrc[c]
								+ ") for color " + c);
						Simulator.logln();
					}
					passed = false;
					break;
				}
			}
		}
		if (passed && Simulator.debugLevel >= 3) {
			if (type == PLACE)
				Simulator.logln("PASSED: Enough stats gathered for place "
						+ name);
			else if (type == QUEUE)
				Simulator.logln("PASSED: Enough stats gathered for queue "
						+ name);
			else if (type == DEPOSITORY)
				Simulator.logln("PASSED: Enough stats gathered for depository "	+ name);
			Simulator.logln();
		}
		return passed;
	}

	/**
	 * Method processStats
	 * 
	 */
	public void processStats() {
		stdStateStatsAv = true;
		for (int c = 0; c < numColors; c++) {
			arrivThrPut[c] = arrivCnt[c] / msrmPrdLen;
			deptThrPut[c] = deptCnt[c] / msrmPrdLen;
			if (statsLevel >= 2) {
				meanTkPop[c] = areaTkPop[c] / msrmPrdLen;
				colUtil[c] = areaColUtil[c] / msrmPrdLen;
			}
			if (statsLevel >= 3) {
				meanST[c] = sumST[c] / numST[c];
				stDevST[c] = Math.sqrt(Descriptive.sampleVariance(numST[c],
						sumST[c], sumSqST[c]));
				if (Simulator.analMethod == Simulator.BATCH_MEANS
						&& minBatches[c] > 0) {
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
			Simulator.logln("PlaceStats "
							+ name
							+ " Error: Attempting to access statistics before data collection has finished!");
			throw new SimQPNException();
		}

		Simulator.logln();
		Simulator.logln();
		if (type == PLACE)
			Simulator.logln("REPORT for Place : " + name
					+ "----------------------------------------");
		else
			Simulator.logln("REPORT for Depository : " + name
					+ "----------------------------------------");

		Simulator.logln();

		for (int c = 0; c < numColors; c++) {
			Simulator.logln();
			Simulator.logln("------------------ Color=" + c
					+ " --------------------");
			Simulator.logln("arrivCnt=" + arrivCnt[c] + " deptCnt="
					+ deptCnt[c]);
			Simulator.logln("arrivThrPut=" + arrivThrPut[c] + " deptThrPut="
					+ deptThrPut[c]);
			if (statsLevel >= 2) {
				// Simulator.logln("minTkPop=" + minTkPop[c] + " maxTkPop=" +
				// maxTkPop[c]);
				Simulator.logln("meanTkPop=" + meanTkPop[c] + " colUtil="
						+ colUtil[c]);
			}
			if (statsLevel >= 3) {
				Simulator.logln("-----");
				// Simulator.logln("numST=" + numST[c] + " minST=" + minST[c]
				// + " maxST=" + maxST[c]);
				Simulator.logln("meanST=" + meanST[c] + " stDevST="
						+ stDevST[c]);
				if (Simulator.analMethod == Simulator.BATCH_MEANS
						&& minBatches[c] > 0) {
					Simulator.logln();
					Simulator.logln("Steady State Statistics: ");
					if (numBatchesST[c] >= minBatches[c]) {
						Simulator.logln("numBatchesST=" + numBatchesST[c]
								+ " batchSizeST=" + batchSizeST[c]
								+ " stDevStdStateMeanST="
								+ stDevStdStateMeanST[c]);
						Simulator.logln(confLevelST[c] + "% c.i. = "
								+ stdStateMeanST[c] + " +/- " + ciHalfLenST[c]);
					} else {
						Simulator.logln("Only " + numBatchesST[c]
								+ " batches collected!");
						Simulator.logln("Need at least " + minBatches[c]
								+ " for steady state statistics.");
					}
				}
			}
			if (statsLevel >= 4) {
				Simulator.logln("Token sojourn times dumped in " + statsDir);
			}
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
				metaAttribute.addAttribute("colUtil", Double
						.toString(colUtil[c]));
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
					if (statsLevel >= 4) {
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
				DocumentManager.removeElement(oldMetaAttribute);
			}
			
			// Add the new meta-attribute.
			DocumentManager.addChild(metaAttributeContainer, metaAttribute);
		}
	}
}
