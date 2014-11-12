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
 *  2004/08/25  Samuel Kounev     Implemented support for FIFO (First-In-First-Out) departure 
 *                                discipline, i.e. arriving tokens become available for output 
 *                                transitions in the order of their arrival. Default is NORMAL, i.e.  
 *                                tokens become available immediately upon arrival.
 *  2006/10/14  Christofer Dutz   Added @SuppressWarnings("unchecked") and cleaned up 
 *                                imports to avoid warnings!
 *  2006/10/21  Samuel Kounev     Modified to use the Simulator.log() methods for output.                                
 *  2008/11/29  Samuel Kounev     Changed LinkedList tokens[c] to store Double objects instead of 
 *                                Token objects since only the arrival timestamps are needed. 
 *                                Renamed tokens to tokArrivTS.                                
 *  2008/11/29  Samuel Kounev     Changed depQueue to store Integer objects containing the colors 
 *                                of tokens in the order of their arrival since this is the only
 *                                information actually used in the code.
 *  2008/12/13  Samuel Kounev     Changed to store names of token colors that can reside in this place.                               
 *  2010/07/24	Simon Spinner	  Add token tracking support.
 */
package de.tud.cs.simqpn.kernel.entities;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import cern.jet.random.engine.RandomEngine;
import de.tud.cs.simqpn.kernel.RandomNumberGenerator;
import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.SimQPNConfiguration.AnalysisMethod;
import de.tud.cs.simqpn.kernel.entities.queue.Queue;
import de.tud.cs.simqpn.kernel.entities.stats.PlaceStats;
import de.tud.cs.simqpn.kernel.entities.stats.Stats;
import de.tud.cs.simqpn.kernel.executor.Executor;
import de.tud.cs.simqpn.kernel.executor.parallel.LP;
import de.tud.cs.simqpn.kernel.loading.XMLWelch;

/**
 * Class Place
 * 
 * @author Samuel Kounev
 * @version
 */
public class Place extends Node {

	private Executor executor;

	/** Supported departure disciplines */
	public enum DepartureDiscipline {
		/**
		 * Departure as specified in the original QPN formalism. Arriving tokens
		 * become available for output transitions immediately upon arrival.
		 */
		NORMAL,
		/**
		 * Extension to the original QPN formalism. Arriving tokens become
		 * available for output transitions in the order of their arrival. That
		 * means a token can leave the place/depository only after all tokens
		 * that have arrived before it have left, hence the term
		 * First-In-First-Out (FIFO)
		 */
		FIFO,
		/**
		 * Extension to the original QPN formalism. Arriving tokens become
		 * available for output transitions randomly. That means a token has to
		 * wait until it is randomly chosen before it can leave the
		 * place/depository. The token has to wait on all previously chosen
		 * tokens.
		 */
		RANDOM;
	}

	/** Supported probe actions */
	public enum ProbeAction {
		PROBE_ACTION_NONE, PROBE_ACTION_START_ON_EXIT, PROBE_ACTION_START_ON_ENTRY, PROBE_ACTION_START_ON_ENTRY_AND_END_ON_EXIT, PROBE_ACTION_START_ON_EXIT_AND_END_ON_ENTRY, PROBE_ACTION_END_ON_EXIT, PROBE_ACTION_END_ON_ENTRY, PROBE_ACTION_TRANSFER;
	}

	private static Logger log = Logger.getLogger(Place.class);

	public int numColors;
	/** Names of the colors that can reside in this Place. **/
	public String[] colors;
	/** Determines the amount of statistics to be gathered during the run. */
	public int statsLevel;
	protected DepartureDiscipline departureDiscipline;
	/** stores the colors of tokens in the order of their arrival. **/
	private LinkedList<Integer> departureQueue; // FIFO + RANDOM
											
	public Transition[] inTrans;
	public Transition[] outTrans;
	public int[] tokenPop;
	/** The token population currently available for output transitions.*/
	public int[] availTokens;
	/** true if a token is currently available for output transitions of the place */
	public boolean departureReady; 

	@SuppressWarnings("rawtypes")
	public LinkedList[] tokArrivTS; // statsLevel >= 3: Arrival timestamps of
									// tokens in the place/depository.

	@SuppressWarnings("rawtypes")
	public LinkedList[] tokens; // individualTokens[color] = true: list of
								// individual tokens.

	/**
	 * individualTokens[color] specifies whether tokens of the specified color
	 * should be stored individually
	 */
	public boolean[] individualTokens; 
	
	// Configuration of probes
	public ProbeAction[][] probeActions;
	public Probe[][] probeInstrumentations; // probeInstrumentations[numColors]:
											// List of probes tracking tokens in
											// this place

	public PlaceStats placeStats;

	public Element element;

	/** RANDOM departure discipline */
	private RandomEngine randomElement;

	/**
	 * 
	 * Constructor
	 * 
	 * @param id
	 *            - global id of the place
	 * @param name
	 *            - name of the place
	 * @param colors
	 *            - names of the colors that can reside in this place
	 * @param numInTrans
	 *            - number of input transitions
	 * @param numOutTrans
	 *            - number of output transitions
	 * @param numProbes
	 *            - number of all probes in net
	 * @param statsLevel
	 *            - determines the amount of statistics to be gathered during
	 *            the run
	 * @param departureDiscipline
	 *            - determines the order of token departure
	 * @param element
	 *            - reference to the XML element representing the place
	 */
	@SuppressWarnings("rawtypes")
	public Place(int id, String name, String[] colors, int numInTrans,
			int numOutTrans, int numProbes, int statsLevel,
			DepartureDiscipline departureDiscipline, Element element,
			SimQPNConfiguration configuration) throws SimQPNException {
		super(id, name);
		this.colors = colors.clone();
		this.numColors = colors.length;
		this.inTrans = new Transition[numInTrans];
		this.outTrans = new Transition[numOutTrans];
		this.tokenPop = new int[numColors];
		this.availTokens = new int[numColors];
		this.statsLevel = statsLevel;
		this.departureDiscipline = departureDiscipline;
		this.tokens = new LinkedList[numColors];
		this.individualTokens = new boolean[numColors];
		this.probeActions = new ProbeAction[numColors][numProbes];
		this.probeInstrumentations = new Probe[numColors][];
		this.element = element;

		for (int c = 0; c < numColors; c++) {
			this.tokenPop[c] = 0;
			this.availTokens[c] = 0;
		}
		if (departureDiscipline == DepartureDiscipline.FIFO || departureDiscipline == DepartureDiscipline.RANDOM) {
			this.departureQueue = new LinkedList<Integer>();
			this.departureReady = false;
		}
		if(departureDiscipline == DepartureDiscipline.RANDOM){
			randomElement = RandomNumberGenerator.nextRandNumGen();
		}
		if (statsLevel > 0) {
			if (this instanceof QPlace)
				placeStats = new PlaceStats(id, name, Stats.QUE_PLACE_DEP,
						colors, statsLevel, configuration);
			else
				placeStats = new PlaceStats(id, name, Stats.ORD_PLACE, colors,
						statsLevel, configuration);
			if (statsLevel >= 3) {
				this.tokArrivTS = new LinkedList[numColors];
				for (int c = 0; c < numColors; c++)
					this.tokArrivTS[c] = new LinkedList();
			}
		}

		// By default tracking is disabled
		Arrays.fill(individualTokens, false);
		for (int c = 0; c < numColors; c++) {
			Arrays.fill(probeActions[c], ProbeAction.PROBE_ACTION_NONE);
			probeInstrumentations[c] = new Probe[0];
		}
	}

	public Place clone(Queue[] queues, Transition[] transitions,
			SimQPNConfiguration configuration) throws SimQPNException {
		Place clone = new Place(id, name, colors, this.inTrans.length,
				this.outTrans.length, probeActions[0].length, statsLevel,
				departureDiscipline, element, configuration);
		/*
		 * NOTE: Cloning assumes that placeStats have NOT been edited before
		 * cloning. Otherwise, the whole placeStats object would have to be
		 * cloned. Now we just modify the necessary elements.
		 */
		if(XMLWelch.isMeasuringSojurnTimes(this, configuration.getAnalMethod())){
			for(int c=0; c < this.numColors; c++){
				clone.placeStats.minObsrvST[c]	= this.placeStats.minObsrvST[c]; 
				clone.placeStats.maxObsrvST[c]	= this.placeStats.maxObsrvST[c]; 
			}
		}
		clone.finishCloning(this, queues, transitions, configuration);
		return clone;
	}

	protected void finishCloning(Place original, Queue[] queues,
			Transition[] transitions, SimQPNConfiguration configuration)
			throws SimQPNException {
		this.inTrans = new Transition[original.inTrans.length];

		this.tokenPop = original.tokenPop.clone();
		for (int i = 0; i < original.inTrans.length; i++) {
			this.inTrans[i] = transitions[original.inTrans[i].id];
		}
		this.outTrans = new Transition[original.outTrans.length];
		for (int i = 0; i < original.outTrans.length; i++) {
			this.outTrans[i] = transitions[original.outTrans[i].id];
		}
	}

	/**
	 * Initializes internal data structures for the place.
	 * 
	 * Note: make sure clock has been initialized before calling Place.init
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	public void init(double clock) throws SimQPNException {

		if (departureDiscipline == DepartureDiscipline.NORMAL) {
			availTokens = tokenPop; // Note: from here on, availTokens and
									// tokenPop point to the same array!
		} else if (departureDiscipline == DepartureDiscipline.FIFO || departureDiscipline == DepartureDiscipline.RANDOM) {
			int totTkPop = 0;
			int[] tkPop = new int[numColors];
			for (int c = 0; c < numColors; c++) {
				tkPop[c] = tokenPop[c];
				totTkPop += tokenPop[c];
			}
			if (totTkPop > 0) {
				while (totTkPop > 0)
					for (int c = 0; c < numColors; c++)
						if (tkPop[c] > 0) {
							departureQueue.addLast(new Integer(c));
							tkPop[c]--;
							totTkPop--;
						}
				int nextCol = ((Integer) departureQueue.removeFirst()).intValue();
				availTokens[nextCol]++;
				departureReady = true;
			}
		} else {
			log.error("Invalid depDiscip specified for place " + name);
			throw new SimQPNException();
		}

		if (statsLevel >= 3) {
			for (int c = 0; c < numColors; c++) {
				for (int i = 0; i < tokenPop[c]; i++) {
					tokArrivTS[c].addLast(new Double(clock));
				}
			}
		}

		// Initialize token list for the initial marking
		for (int c = 0; c < numColors; c++) {
			int prC = probeInstrumentations[c].length;

			// Create timestamps for all probes associated with this place
			ProbeTimestamp[] timestamps = new ProbeTimestamp[prC];
			for (int pr = 0; pr < prC; pr++) {
				timestamps[pr] = new ProbeTimestamp(
						probeInstrumentations[c][pr].id, clock);
			}

			// Create tokens
			for (int i = 0; i < tokenPop[c]; i++) {
				if (individualTokens[c]) {
					tokens[c].addLast(new Token(this, c, timestamps));
				}
			}
		}
	}

	/**
	 * Method start
	 * 
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public void start(SimQPNConfiguration configuration, double clock)
			throws SimQPNException {
		if (statsLevel > 0)
			placeStats.start(tokenPop, configuration, clock);
	}

	/**
	 * Method finish
	 * 
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public void finish(SimQPNConfiguration configuration,
			double runWallClockTime, double clock) throws SimQPNException {
		// Complete statistics collection
		if (statsLevel > 0)
			placeStats.finish(tokenPop, configuration, runWallClockTime, clock);
	}

	/**
	 * Method report
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public void report(SimQPNConfiguration configuration)
			throws SimQPNException {
		if (statsLevel > 0) {
			placeStats.printReport(configuration);
			/* cdutz */
			placeStats.addReportMetaData(element, configuration);
		}
	}

	/**
	 * Deposits N tokens of particular color
	 * 
	 * @param color
	 *            - color of tokens
	 * @param count
	 *            - number of tokens to deposit
	 * @param tokensToBeAdded
	 *            - Tokens to be added or null if tracking=false. After the call
	 *            all elements of this array are set to null.
	 * @return
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	public void addTokens(int color, int count, Token[] tokensToBeAdded,
			Executor executor) throws SimQPNException {
		if (count <= 0) { // DEBUG
			log.error("Attempted to add nonpositive number of tokens to place "
					+ name);
			throw new SimQPNException();
		}

		// Update Stats
		if (statsLevel > 0) {
			placeStats.updateTkPopStats(color, tokenPop[color], count,
					executor.getClock());
			if (statsLevel >= 3) {
				for (int i = 0; i < count; i++)
					tokArrivTS[color].addLast(new Double(executor.getClock()));
			}
		}
		// Now add tokens and update affected transitions
		tokenPop[color] += count;
		if (individualTokens[color]) {
			if (tokensToBeAdded == null) { // DEBUG
				log.error("Cannot add tokens to place " + name);
				throw new SimQPNException();
			}

			for (int i = 0; i < count; i++) {
				tokens[color].addLast(tokensToBeAdded[i]);
				tokensToBeAdded[i] = null; // Note: set null to avoid dangling
											// references.
			}
		}

		if (departureDiscipline == DepartureDiscipline.NORMAL) {
			for (int i = 0; i < outTrans.length; i++)
				outTrans[i].updateState(id, color, tokenPop[color], count);
		} else if (departureDiscipline == DepartureDiscipline.FIFO || departureDiscipline == DepartureDiscipline.RANDOM) {
			if (departureReady) {
				for (int i = 0; i < count; i++)
					departureQueue.addLast(new Integer(color));
			} else {
				for (int i = 0; i < (count - 1); i++)
					departureQueue.addLast(new Integer(color));
				availTokens[color]++;
				departureReady = true;
				for (int i = 0; i < outTrans.length; i++)
					outTrans[i].updateState(id, color, availTokens[color], 1);
			}
		} else {
			log.error("Invalid depDiscip specified for place " + name);
			throw new SimQPNException();
		}
	}

	/**
	 * Removes N tokens of particular color
	 * 
	 * @param color
	 *            - color of tokens
	 * @param count
	 *            - number of tokens to remove (must be = 1 if tracking enabled)
	 * @param returnBuffer
	 *            - an array used to store the removed tokens, if tracking
	 *            enabled. (Condition: returnBuffer.length >= count)
	 * @return removed tokens (if tracking is enabled)
	 * @exception
	 */
	public Token[] removeTokens(int color, int count, Token[] returnBuffer,
			double clock, SimQPNConfiguration configuration)
			throws SimQPNException {
		/*
		 * //DEBUG if (count <= 0) { Simulator.logln(
		 * "Error: Attempted to remove nonpositive number of tokens from place "
		 * + name); throw new SimQPNException(); } if (availTokens[color] <
		 * count || tokenPop[color] < count) {
		 * Simulator.logln("Error: Attempted to remove more tokens from place "
		 * + name + " than are available!"); throw new SimQPNException(); }
		 */
		// Update Stats
		if (statsLevel > 0) {
			placeStats.updateTkPopStats(color, tokenPop[color], (-1) * count,
					clock);
			if (statsLevel >= 3) {
				Double arrivTS;
				for (int i = 0; i < count; i++) {
					arrivTS = (Double) tokArrivTS[color].removeFirst();
					placeStats.updateSojTimeStats(color,
							clock - arrivTS.doubleValue(), configuration);
				}
			}
		}
		// Now remove tokens and update affected transitions
		tokenPop[color] -= count;
		if (individualTokens[color]) {
			if (returnBuffer.length < count) {
				log.error("Return buffer for removed tokens too small.");
				throw new SimQPNException();
			}
			for (int i = 0; i < count; i++) {
				returnBuffer[i] = (Token) tokens[color].removeFirst();
			}
		}

		if (departureDiscipline == DepartureDiscipline.NORMAL) {
			for (int i = 0; i < outTrans.length; i++)
				outTrans[i].updateState(id, color, tokenPop[color], (-1)
						* count);
		} else if (departureDiscipline == DepartureDiscipline.FIFO) {
			availTokens[color] -= count;
			for (int i = 0; i < outTrans.length; i++)
				outTrans[i].updateState(id, color, availTokens[color], (-1)
						* count);
			if (departureQueue.size() > 0) {
				int nextCol = ((Integer) departureQueue.removeFirst()).intValue();
				availTokens[nextCol]++;
				departureReady = true; // Left for clarity. Actually redundant since
									// depReady should already be true.
				for (int i = 0; i < outTrans.length; i++)
					outTrans[i].updateState(id, nextCol, availTokens[nextCol],
							1);
			} else{
				departureReady = false;
			}
		}else if (departureDiscipline == DepartureDiscipline.RANDOM)  {
			availTokens[color] -= count;
			for (int i = 0; i < outTrans.length; i++){
				outTrans[i].updateState(id, color, availTokens[color], (-1)*count);			
			}
			if (departureQueue.size() > 0) {
				int queueIdx = (int)(departureQueue.size() * randomElement.raw());
				int nextCol = (Integer) departureQueue.remove(queueIdx);
				availTokens[nextCol]++;
				departureReady = true; // Left for clarity. Actually redundant since depReady should already be true.
				for (int i = 0; i < outTrans.length; i++)
					outTrans[i].updateState(id, nextCol, availTokens[nextCol], 1);										
			}else{ 
				departureReady = false;
			}
		} else {
			log.error("Invalid depDiscip specified for place " + name);
			throw new SimQPNException();
		}

		return returnBuffer;
	}

	/**
	 * Maps the name of a color to the index of the color in this place.
	 * 
	 * @param color
	 *            - name of the color
	 * @return index of the color (or -1 if color not defined in this place)
	 */
	public int getColorIndex(String color) {
		for (int c = 0; c < colors.length; c++) {
			if (colors[c].equals(color)) {
				return c;
			}
		}
		return -1;
	}

	/**
	 * Adds a probe instrumentation to this place.
	 * 
	 * @param probe
	 *            - the probe
	 * @param action
	 *            - a PROBE_ACTION constant.
	 */
	@SuppressWarnings("rawtypes")
	public void addProbe(Probe probe, ProbeAction action) {
		int probeId = probe.id;
		for (String trackedColor : probe.colors) {
			int c = getColorIndex(trackedColor);
			if (c >= 0) {
				probeActions[c][probeId] = action;
				if ((action != ProbeAction.PROBE_ACTION_START_ON_EXIT)
						&& (action != ProbeAction.PROBE_ACTION_END_ON_ENTRY)
						&& (action != ProbeAction.PROBE_ACTION_START_ON_EXIT_AND_END_ON_ENTRY)) {
					individualTokens[c] = true;
					if (tokens[c] == null) {
						tokens[c] = new LinkedList();
					}
				}

				boolean found = false;
				for (int i = 0; i < probeInstrumentations[c].length; i++) {
					if (probeInstrumentations[c][i].id == probeId) {
						found = true;
						break;
					}
				}

				if (!found) {
					Probe[] p = new Probe[probeInstrumentations[c].length + 1];
					System.arraycopy(probeInstrumentations[c], 0, p, 0,
							probeInstrumentations[c].length);
					p[p.length - 1] = probe;
					probeInstrumentations[c] = p;
				}
			}
		}
	}

	public Executor getExecutor() {
		return executor;
	}

	public void setExecutor(Executor executor) {
		this.executor = executor;
	}

	public double getSumLookahead(int color, Place endPlace, double sumLookahead) {
		if (this.id == endPlace.id) {
			return sumLookahead;
		}
		// double look = 0;
		// for (Transition transition : inTrans) {
		// for (Place inPlace : transition.inPlaces) {
		// look =
		// }
		// }
		Place inPlace = this.inTrans[0].inPlaces[0];
		return inPlace.getSumLookahead(color, endPlace, sumLookahead
				+ getLookahead(color));
	}

	public double getTimeSaveToProcess(int color, List<Integer> visitedPlaces,
			String format, final boolean verbose) {
		Collection<Double> saveTimes = new LinkedList<Double>();
		if (!visitedPlaces.contains(this.id)) {
			visitedPlaces.add(this.id);
		}
		format += "\t";
		for (Transition transition : inTrans) {
			for (Place inPlace : transition.inPlaces) {
				if (!visitedPlaces.contains(inPlace.id)) {
					// if (inPlace.tokenPop[color] == 0) {
					if (((QPlace) inPlace).getQueueTokenPop()[color] == 0) {
						double save = inPlace.getTimeSaveToProcess(color,
								visitedPlaces, format, verbose);
						if (save != 0) {
							saveTimes.add(save + inPlace.getLookahead(color));
						}
					} else {
						Place nextEventPlace = ((LP) inPlace.getExecutor())
								.getNextEventPlace();
						if (nextEventPlace != null
								&& !(nextEventPlace.id == inPlace.id)) {
							saveTimes.add(inPlace.getExecutor().getClock()
									+ inPlace.getLookahead(color));
						} else {
							double save = inPlace.getTimeSaveToProcess(color,
									visitedPlaces, format, verbose);
							if (save != 0) {
								saveTimes.add(save
										+ inPlace.getLookahead(color));
							}
						}
					}
				}
			}
		}
		if (verbose) {
			System.out.println(format + name + " " + saveTimes);
		}

		if (saveTimes.isEmpty()) {
			// return 0;
			double max = Math.max(((LP) this.getExecutor()).getNextEventTime(),
					this.getExecutor().getClock());
			max = ((LP) this.getExecutor()).getNextEventTime();
			return max;
		} else {
			return Collections.min(saveTimes);
		}

	}

	/**
	 * Returns the lookahead for a specific color.
	 * @param color		the color for which lookahead is returned.
	 * @return the lookahead
	 */
	public double getLookahead(int color) {
		return 0;
	}

	public double getLookahead() {
		return 0;
	}

}
