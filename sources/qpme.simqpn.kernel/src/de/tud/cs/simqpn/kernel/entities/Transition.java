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
 *  ...
 *  2004/08/25  Samuel Kounev     Implemented support for FIFO departure discipline.                                 
 *  2004/08/25  Samuel Kounev     Changed type of modeWeights from int[] to double[], so that 
 *                                arbitrary values for weights are supported.
 *  2009/16/12  Simon Spinner     Optimized avoiding usage of IntArrayList for managing enabled modes.                                
 *  2010/07/27  Simon Spinner     Add probe support.
 * 
 */
package de.tud.cs.simqpn.kernel.entities;

import static de.tud.cs.simqpn.kernel.util.LogUtil.formatMultilineMessage;

import java.util.Arrays;
import java.util.Random;

import org.apache.log4j.Logger;

import cern.jet.random.Empirical;
import cern.jet.random.EmpiricalWalker;
import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.executor.Executor;
import de.tud.cs.simqpn.kernel.random.RandomNumberGenerator;

/**
 * Class Transition
 * 
 * @author Samuel Kounev
 * @version
 */

public class Transition extends Node {

	private static Logger log = Logger.getLogger(Transition.class);

	public int numModes;
	public double transWeight;

	public Place[] inPlaces;
	public Place[] outPlaces;

	public int[][][] inFunc; // [mode, inPlace, color]
	public int[][][] outFunc; // [mode, outPlace, color]
	public double[] modeWeights; // [1..numModes]

	public boolean[] modeStatus; // [1..numModes] Specifying the current status
									// (enabled/disabled) of a mode
	public int enModesCnt; // Number of currently enabled modes

	private Token[] tkCopyBuffer; // INTERNAL: [1..maxNumTokens] with
									// maxNumTokens=max(outFunc[mode, outPlace,
									// color])
									// a buffer for copying tokens
	private int[] tkIndexBuffer; // INTERNAL: [1..maxNumTokens] with
									// maxNumTokens=max(outFunc[mode, outPlace,
									// color])
									// a buffer for temporarily storing tokens
									// indexes
	private ProbeTimestamp[] probeData; // INTERNAL: [1..numProbes] buffer to
										// copy probe timestamps between tokens
	private boolean conflictWarnings = true; // INTERNAL: flag controlling
												// whether warning for
												// conflicting
												// timestamps are generated for
												// this transition.

	public EmpiricalWalker randModeGen; // Random number generator for
										// generating modes to fire

	private Random randGen = new Random();

	public Transition(Transition transition, Place[] places,
			SimQPNConfiguration configuration) throws SimQPNException {
		super(transition.id, transition.name);
		this.numModes = transition.numModes;
		this.transWeight = transition.transWeight;
		this.modeWeights = new double[transition.numModes];
		this.inPlaces = new Place[transition.inPlaces.length];
		this.outPlaces = new Place[transition.outPlaces.length];

		for (int i = 0; i < transition.inPlaces.length; i++) {
			this.inPlaces[i] = places[transition.inPlaces[i].id];
		}
		for (int i = 0; i < transition.outPlaces.length; i++) {
			this.outPlaces[i] = places[transition.outPlaces[i].id];
		}

		this.inFunc = transition.inFunc.clone();// [mode, inPlace, color]
		this.outFunc = transition.outFunc.clone();

		this.modeStatus = transition.modeStatus.clone();

		this.enModesCnt = transition.enModesCnt;
		this.probeData = transition.probeData;// new ProbeTimestamp[numProbes];

		// Create randModeGen
		double[] pdf = new double[numModes];
		for (int m = 0; m < numModes; m++)
			pdf[m] = 1;
		this.randModeGen = new EmpiricalWalker(pdf, Empirical.NO_INTERPOLATION,
				RandomNumberGenerator.nextRandNumGen());
		// Note: Here we use a default distribution. The actual distribution is
		// set each time before using randModeGen.
		
		this.tkCopyBuffer = transition.tkCopyBuffer.clone(); //JUERGEN: seems sufficient in our context
		this.tkIndexBuffer = transition.tkIndexBuffer.clone(); //JUERGEN: OK

	}

	public void finishCloning(Transition transitionToClone, Place[] places) {
		this.inPlaces = new Place[transitionToClone.inPlaces.length];
		for (int i = 0; i < transitionToClone.inPlaces.length; i++) {
			this.inPlaces[i] = places[transitionToClone.inPlaces[i].id];
		}
		this.outPlaces = new Place[transitionToClone.outPlaces.length];
		for (int i = 0; i < transitionToClone.outPlaces.length; i++) {
			this.outPlaces[i] = places[transitionToClone.outPlaces[i].id];
		}

	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            - global id of the transition
	 * @param name
	 *            - name of the transition
	 * @param numModes
	 *            - number of modes
	 * @param numInPlaces
	 *            - number of input places
	 * @param numOutPlaces
	 *            - number of output places
	 * @param transWeight
	 *            - transition weight
	 */
	public Transition(int id, String name, int numModes, int numInPlaces,
			int numOutPlaces, int numProbes, double transWeight)
			throws SimQPNException {
		super(id, name);
		this.numModes = numModes;
		this.transWeight = transWeight;
		this.modeWeights = new double[numModes];
		this.inPlaces = new Place[numInPlaces];
		this.outPlaces = new Place[numOutPlaces];
		this.inFunc = new int[numModes][numInPlaces][];
		this.outFunc = new int[numModes][numOutPlaces][];
		this.modeStatus = new boolean[numModes];
		this.enModesCnt = 0;
		this.probeData = new ProbeTimestamp[numProbes];

		// Create randModeGen
		double[] pdf = new double[numModes];
		for (int m = 0; m < numModes; m++)
			pdf[m] = 1;
		this.randModeGen = new EmpiricalWalker(pdf, Empirical.NO_INTERPOLATION,
				RandomNumberGenerator.nextRandNumGen());
		// Note: Here we use a default distribution. The actual distribution is
		// set each time before using randModeGen.
	}

	/**
	 * Method init - checks for enabled modes and initializes modeStatus and
	 * enModesCnt
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public void init() {
		int m, p, c, nM, nP, nC;
		boolean enabled;
		Place pl;
		nM = numModes;
		nP = inPlaces.length;
		Arrays.fill(modeStatus, false);
		enModesCnt = 0;

		for (m = 0; m < nM; m++) {
			enabled = true;
			for (p = 0; p < nP; p++) {
				pl = inPlaces[p];
				nC = pl.numColors;
				for (c = 0; enabled && (c < nC); c++)
					if (pl.availTokens[c] < inFunc[m][p][c]) {
						enabled = false;
						break;
					}
				if (!enabled)
					break;
			}
			if (enabled) {
				modeStatus[m] = true;
				enModesCnt++;
			}
		}

		// Init token copy buffer
		int maxNumTokens = 0;
		for (m = 0; m < nM; m++) {
			for (p = 0; p < outPlaces.length; p++) {
				nC = outPlaces[p].numColors;
				for (c = 0; c < nC; c++) {
					if (outFunc[m][p][c] > maxNumTokens) {
						maxNumTokens = outFunc[m][p][c];
					}
				}
			}
			for (p = 0; p < inPlaces.length; p++) {
				nC = inPlaces[p].numColors;
				for (c = 0; c < nC; c++) {
					if (inFunc[m][p][c] > maxNumTokens) {
						maxNumTokens = inFunc[m][p][c];
					}
				}
			}
		}
		tkCopyBuffer = new Token[maxNumTokens];
		tkIndexBuffer = new int[maxNumTokens];
	}

	/**
	 * Method updateState - updates modeStatus and enModesCnt after a change in
	 * the token population of an input place. Note: Must be called whenever
	 * changing token population in any of the input transitions.
	 * 
	 * @param inPlaceId
	 *            - id of updated input place
	 * @param color
	 *            - color of updated token count
	 * @param newAvailTkCnt
	 *            - new available token count
	 * @param delta
	 *            - difference between new and old token count
	 * @return
	 * @exception
	 */
	public void updateState(int inPlaceId, int color, int newAvailTkCnt,
			int delta) {

		if (delta > 0) { // CASE A: TOKENS HAVE BEEN ADDED
			if (enModesCnt == numModes)
				return;
			// Find index of updated input place
			int uInP = 0;
			while (inPlaces[uInP].id != inPlaceId)
				uInP++;
			// Check for newly enabled modes
			int m, p, c, nC;
			Place pl;
			boolean enabled;
			int nM = numModes;
			int nP = inPlaces.length;
			for (m = 0; m < nM; m++) {
				// only consider disabled modes that require tokens of
				// the respective color
				if ((!modeStatus[m]) && inFunc[m][uInP][color] > 0) {
					enabled = true;
					for (p = 0; p < nP; p++) {
						pl = inPlaces[p];
						nC = pl.numColors;
						for (c = 0; enabled && (c < nC); c++)
							if (pl.availTokens[c] < inFunc[m][p][c]) {
								enabled = false;
								break;
							}
						if (!enabled)
							break;
					}
					if (enabled) {
						modeStatus[m] = true;
						enModesCnt++;
					}
				}
			}
		} else { // CASE B: TOKENS HAVE BEEN REMOVED
			// Find index of updated input place
			int uInP = 0;
			while (inPlaces[uInP].id != inPlaceId)
				uInP++;
			// Check for newly disabled modes
			int nM = numModes;
			for (int m = 0; m < nM; m++) {
				if ((modeStatus[m]) && (newAvailTkCnt < inFunc[m][uInP][color])) {
					modeStatus[m] = false;
					enModesCnt--;
				}
			}
		}
	}

	/**
	 * Method enabled
	 * 
	 * @param
	 * @return boolean - true if at least one mode enabled, false otherwise
	 * @exception
	 */
	public boolean enabled() {
		return (enModesCnt > 0);
	}

	/**
	 * Method checkIfEnabled - checks if transition is enabled Note: does not
	 * rely on modeStatus/enModesCnt
	 * 
	 * @param
	 * @return boolean - true if at least one mode enabled, false otherwise
	 * @exception
	 */
	public boolean checkIfEnabled() {
		int m, p, c, nM, nP, nC;
		boolean enabled = false;
		nM = numModes;
		nP = inPlaces.length;
		Place pl;
		for (m = 0; (!enabled) && (m < nM); m++) {
			enabled = true;
			for (p = 0; p < nP; p++) {
				pl = inPlaces[p];
				nC = pl.numColors;
				for (c = 0; enabled && (c < nC); c++)
					if (pl.availTokens[c] < inFunc[m][p][c]) {
						enabled = false;
						break;
					}
				if (!enabled)
					break;
			}
		}
		return enabled;
	}

	/**
	 * Method fire - fires in a mode chosen based on weights
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public void fire() throws SimQPNException {

		int nM = numModes;
		// Choose mode to fire based on weights
		int mode;
		if (enModesCnt == 1) {
			mode = -1;
			for (int m = 0; m < nM; m++) {
				if (modeStatus[m]) {
					mode = m;
					break;
				}
			}
		} else {
			double[] pdf = new double[enModesCnt];
			int[] enModesIDs = new int[enModesCnt];
			for (int m = 0, e = 0; m < nM; m++) {
				if (modeStatus[m]) {
					pdf[e] = modeWeights[m];
					enModesIDs[e] = m;
					e++;
				}
			}
			randModeGen.setState2(pdf);
			mode = enModesIDs[randModeGen.nextInt()];
		}
		int p, c, nP, nC, prC, n;
		int maxN = 0;
		Place pl;
		Probe probe;
		int probeIdx;
		
		Place.ProbeAction probeAction;
		nP = inPlaces.length;
		// Step 1: Remove input tokens
		for (p = 0; p < nP; p++) {
			pl = inPlaces[p];
			Executor executorIn = pl.getExecutor();
			nC = pl.numColors;
			for (c = 0; c < nC; c++) {
				n = inFunc[mode][p][c];
				if (n != 0) {
					if (n > maxN)
						maxN = n;

					Token[] tokens = pl.removeTokens(c, n, tkCopyBuffer,
							executorIn.getClock(), executorIn.getConfiguration());
					prC = pl.probeInstrumentations[c].length;

					if (prC > 0) {
						// The input place is instrumented with probes, so look
						// for timestamp data
						// Iterate over all probes relevant to the current input
						// place
						for (int pr = 0; pr < prC; pr++) {
							probe = pl.probeInstrumentations[c][pr];
							probeIdx = probe.id;
							probeAction = pl.probeActions[c][probeIdx];
							ProbeTimestamp data = probeData[probeIdx];

							switch (probeAction) {
							case PROBE_ACTION_END_ON_EXIT:
							case PROBE_ACTION_START_ON_ENTRY_AND_END_ON_EXIT:
								if (pl.individualTokens[c]) {
									for (int i = 0; i < n; i++) {
										ProbeTimestamp curStamp = tokens[i].probeData[pr];
										if (curStamp == null)
											continue;

										probe.probeStats.updateSojTimeStats(c,
												executorIn.getClock()
														- curStamp.timestamp,
												executorIn.getConfiguration());
									}
								}
								break;
							case PROBE_ACTION_START_ON_EXIT:
							case PROBE_ACTION_START_ON_EXIT_AND_END_ON_ENTRY:
								if (data == null) {
									// There is no timestamp so far for the
									// current probe, so create it.
									data = new ProbeTimestamp(probeIdx,
											executorIn.getClock());
								}
								break;
							default:
								if (pl.individualTokens[c]) {
									boolean conflict = false;
									int j = 0; // the actual number of tokens
												// with timestamps
									// Filter out null timestamps and check that
									// there are no conflicting timestamps
									for (int i = 0; i < n; i++) {
										ProbeTimestamp curStamp = tokens[i].probeData[pr];
										if (curStamp == null)
											continue;

										tkIndexBuffer[j] = i;
										j++;

										if (data == null) {
											data = curStamp;
										} else if (data.timestamp != curStamp.timestamp) {
											conflict = true;
											if (conflictWarnings) {
												log.warn(formatMultilineMessage(
														"Conflicting timestamps for probe "
																+ probe.name
																+ " at transition "
																+ name
																+ " and mode "
																+ mode + ".",
														"One randomly chosen timestamp will be used. Other timestamps are dumped.",
														"Further occurences of this warning are disabled for this transition."));
												conflictWarnings = false; // no
																			// further
																			// warnings
																			// for
																			// this
																			// mode
											}
										}
									}

									if (conflict) {
										// Choose a timestamp from one tokens in
										// the list randomly
										int randTokenIndex = tkIndexBuffer[randGen
												.nextInt(j)];
										data = tokens[randTokenIndex].probeData[pr];
									}
								}
								break;
							}
							probeData[probeIdx] = data;
						}
					}
				}
			}
		}
		for (int i = 0; i < maxN; i++)
			tkCopyBuffer[i] = null; // NOTE: set all references in the token
									// buffer to null,
									// so that they can be deleted by the GC.

		// Step 2: Deposit output tokens
		nP = outPlaces.length;
		for (p = 0; p < nP; p++) {
			pl = outPlaces[p];
			Executor executorOut = pl.getExecutor();
			nC = pl.numColors;
			for (c = 0; c < nC; c++) {
				n = outFunc[mode][p][c];
				if (n != 0) {
					prC = pl.probeInstrumentations[c].length;
					if (prC > 0) {
						// The output place is instrumented with probes
						// so produce tokens with timestamps
						ProbeTimestamp[] outData = new ProbeTimestamp[prC];
						// Iterate over all relevant probes of this place
						for (int pr = 0; pr < prC; pr++) {
							probe = pl.probeInstrumentations[c][pr];
							probeIdx = probe.id;
							probeAction = pl.probeActions[c][probeIdx];
							ProbeTimestamp timestamp = probeData[probeIdx];
							switch (probeAction) {
							case PROBE_ACTION_START_ON_ENTRY:
							case PROBE_ACTION_START_ON_ENTRY_AND_END_ON_EXIT:
								if (timestamp == null) {
									outData[pr] = new ProbeTimestamp(probeIdx,
											executorOut.getClock()); //TODO check which executor
								}
								break;
							case PROBE_ACTION_END_ON_ENTRY:
							case PROBE_ACTION_START_ON_EXIT_AND_END_ON_ENTRY:
								probe.probeStats
										.updateSojTimeStats(c,
												executorOut.getClock() //TODO check which executor
														- timestamp.timestamp, 
												executorOut.getConfiguration());
								break;
							default:
								outData[pr] = timestamp;
								break;
							}
						}
						// Create tokens and put them in the token buffer
						for (int i = 0; i < n; i++) {
							tkCopyBuffer[i] = new Token(pl, c, outData);
						}
						pl.addTokens(c, n, tkCopyBuffer, executorOut); // Note: the
																	// contents
																	// of
																	// tkCopyBuffer
																	// are all
																	// set to
																	// null.
					} else {
						pl.addTokens(c, n, null, executorOut);
					}
				}
			}
			synchronized (executorOut) {
				executorOut.notify(); //TODO CHECK				
			}
		}

		// ATTENTION: all elements of probeData must be reset to null, so that
		// the next fire works correctly.
		for (int i = 0; i < probeData.length; i++) {
			probeData[i] = null;
		}

	} // end fire()

	/**
	 * Get the index of an output place in this transition.
	 * 
	 * @param place
	 *            output place
	 * @return index of place (or -1 if place is not an output place)
	 */
	public int getIndexOfOutputPlace(Place place) {
		for (int p = 0; p < outPlaces.length; p++) {
			if (outPlaces[p].equals(place)) {
				return p;
			}
		}
		return -1;
	}

	/**
	 * Get the index of an input place in this transition.
	 * 
	 * @param place
	 *            input place
	 * @return index of place (or -1 if place is not an input place)
	 */
	public int getIndexOfInputPlace(Place place) {
		for (int p = 0; p < inPlaces.length; p++) {
			if (inPlaces[p].equals(place)) {
				return p;
			}
		}
		return -1;
	}

}
