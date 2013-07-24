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
 *  2004/08/25  Samuel Kounev     Implemented support for FIFO departure discipline.
 *  2004/08/25  Samuel Kounev     Implemented support for multi-server PS queues (-/M/n-PS).
 *  2005/06/21  Samuel Kounev     Enhanced to support PS queues with non-exponential service times.
 *                                Currently configured by setting the expPS property in init(). 
 *                                Tested successfully on ispass-sc1 with exp service times.
 *  2005/06/22  Samuel Kounev     Moved initialization of service time distributions from init() 
 *                                to Simulator.getReady() as part of the model definition.
 *  2006/07/28  Samuel Kounev     Changed to truncate service time distributions to avoid negative
 *                                values for service times, i.e. "if (servTime < 0) servTime = 0;"
 *  2006/10/14  Christofer Dutz   Added @SuppressWarnings("unchecked") and cleaned up 
 *                                imports to avoid warnings!
 *  2006/10/21  Samuel Kounev     Modified to use the Simulator.log() methods for output.
 *  2008/11/25  Samuel Kounev     Renamed from QueueingPlace to QPlace.                                  
 *  2008/11/29  Samuel Kounev     Replaced queueTokens LinkedList<Token> array with a DoubleArrayList[] 
 *                                containing the arrival timestamps since that is the only information 
 *                                that is actually used. Renamed queueTokens to queueTokArrivTS.
 *  2008/12/13  Samuel Kounev     Changed to store names of token colors that can reside in this place.                                 
 *  2010/07/24	Simon Spinner	  Add token tracking support.
 *  2010/07/24  Simon Spinner	  Reintroduce queueTokens structure in order to track the individual tokens.
 */
package de.tud.cs.simqpn.kernel.entities;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import cern.colt.list.AbstractDoubleList;
import cern.colt.list.DoubleArrayList;
import cern.jet.random.AbstractContinousDistribution;
import cern.jet.random.Exponential;
import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.queue.PSQueue;
import de.tud.cs.simqpn.kernel.entities.queue.Queue;
import de.tud.cs.simqpn.kernel.entities.queue.QueuingDiscipline;
import de.tud.cs.simqpn.kernel.executor.Executor;
import de.tud.cs.simqpn.kernel.random.RandomNumberGenerator;
import de.tud.cs.simqpn.kernel.stats.QPlaceQueueStats;

/**
 * Class QPlace
 * 
 * Note: We use the term queue to refer to a queueing station including both the
 * waiting area and the servers. Note: We assume that in the beginning of the
 * run, the queue is empty!
 * 
 * @author Samuel Kounev
 * @version %I%, %G%
 */

public class QPlace extends Place {

	private Logger log = Logger.getLogger(QPlace.class);

	public Queue queue; // Queue of the queueing place.

	public double[] meanServTimes; // Mean token service times at the queueing
									// station (all times usually in
									// milliseconds)
	private int[] queueTokenPop; // Number of tokens in the queueing station
									// (queue), i.e. token population.
									// Note that for queueing places
									// Place.tokenPop
									// contains tokens in the depository.

	public int[] getQueueTokenPop() {
		return queueTokenPop;
	}

	public void setQueueTokenPop(int[] queueTokenPop) {
		this.queueTokenPop = queueTokenPop;
	}

	private AbstractDoubleList[] queueTokResidServTimes; // PS queues:
															// expPS==false:
															// Residual service
															// times of the
															// tokens
															// in the queueing
															// station (queue).

	public AbstractDoubleList[] getQueueTokResidServTimes() {
		return queueTokResidServTimes;
	}

	public AbstractDoubleList getQueueTokResidServTimesForColor(int c) {
		return queueTokResidServTimes[c];
	}

	@SuppressWarnings("rawtypes")
	public ArrayList[] queueTokens; // PS queues: tokens in queueing station.

	public AbstractContinousDistribution[] randServTimeGen; // PS queues: Random
															// number generators
															// for generating
															// service times.

	public QPlaceQueueStats qPlaceQueueStats;

	public Element element;

	private Token[] tkCopyBuffer; // INTERNAL: Used to transfer tokens.

	/**
	 * Clones
	 */
	public Place clone(Queue[] queues, Transition[] transitions,
			SimQPNConfiguration configuration) throws SimQPNException {
		QPlace clone = new QPlace(id, name, colors, this.inTrans.length,
				this.outTrans.length, probeActions[0].length, statsLevel,
				depDiscip, queues[this.id], element, configuration);
		clone.finishCloning(this, queues, transitions, configuration);
		return clone;
	}

	/**
	 * Cloning constructor
	 * 
	 * @param qPlace
	 * @param configuration
	 * @throws SimQPNException
	 */
	@Override
	protected void finishCloning(Place original, Queue[] queues,
			Transition[] transitions, SimQPNConfiguration configuration)
			throws SimQPNException {
		super.finishCloning(original, queues, transitions, configuration);
		// this.queue = queues[((QPlace)original).queue.id];

		this.meanServTimes = ((QPlace) original).meanServTimes.clone();
		this.tkCopyBuffer = new Token[((QPlace) original).tkCopyBuffer.length];// new
		// Token[1];
		// // TODO
		for (int c = 0; c < numColors; c++)
			this.meanServTimes[c] = ((QPlace) original).meanServTimes[c];// -1;
																			// //
																			// -1
																			// means
		// 'uninitialized'
		this.queueTokenPop = ((QPlace) original).queueTokenPop.clone();
		// this.queueTokenPop = new int[numColors];
		// for (int c = 0; c < numColors; c++)
		// this.queueTokenPop[c] = qPlace.queueTokenPop[c];

		if (!(queues[((QPlace) original).queue.id].queueDiscip == QueuingDiscipline.PS && ((PSQueue) queues[((QPlace) original).queue.id]).expPS)) {
			this.randServTimeGen = new AbstractContinousDistribution[this.numColors];
			for (int c = 0; c < numColors; c++) {
				// qPlace.randServTimeGen[c].getClass()
				AbstractContinousDistribution distribution = ((QPlace) original).randServTimeGen[c];
				if (((QPlace) original).randServTimeGen[c].getClass().equals(
						Exponential.class)) {
					String lambdaString = distribution.toString().split("\\(")[1]
							.split("\\)")[0];
					double lambda = Double.parseDouble(lambdaString);
					this.randServTimeGen[c] = new Exponential(lambda,
							RandomNumberGenerator.nextRandNumGen());
				} else {
					// TODO implement copy of other distributions
					log.error("Copy of distribution" + distribution.getClass()
							+ " not implemented");
				}
			}

		}
		if (((QPlace) original).queueTokResidServTimes != null) {
			this.queueTokResidServTimes = new AbstractDoubleList[original.numColors];
			for (int c = 0; c < numColors; c++) {
				queueTokResidServTimes[c] = new DoubleArrayList(100);
			}
		}

		if (statsLevel > 0)
			qPlaceQueueStats = new QPlaceQueueStats(id, name, colors,
					statsLevel, queue.queueDiscip, queue.numServers,
					meanServTimes, configuration);

	}

	/**
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
	 * @param depDiscip
	 *            - determines the depository's departure discipline (order):
	 *            NORMAL or FIFO
	 * @param queue
	 *            - reference to the integrated Queue
	 * @param element
	 *            - reference to the XML element representing the place
	 * 
	 */
	public QPlace(int id, String name, String[] colors, int numInTrans,
			int numOutTrans, int numProbes, int statsLevel,
			DepartureDiscipline depDiscip, Queue queue, Element element,
			SimQPNConfiguration configuration) throws SimQPNException {
		super(id, name, colors, numInTrans, numOutTrans, numProbes, statsLevel,
				depDiscip, element, configuration);
		this.queue = queue;
		this.meanServTimes = new double[numColors];
		this.tkCopyBuffer = new Token[1];
		for (int c = 0; c < numColors; c++)
			this.meanServTimes[c] = -1; // -1 means 'uninitialized'

		this.queueTokenPop = new int[numColors];
		for (int c = 0; c < numColors; c++)
			this.queueTokenPop[c] = 0;

		if (queue != null) {
			if (statsLevel > 0)
				qPlaceQueueStats = new QPlaceQueueStats(id, name, colors,
						statsLevel, queue.queueDiscip, queue.numServers,
						meanServTimes, configuration);
		}
	}

	/**
	 * Method init
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void init(double clock) throws SimQPNException {
		super.init(clock);

		// SDK-TODO: This check might cause problems for some distributions
		// where meanServTimes is not initialized!

		// TODO do this with an overwritten method of QUEUE
		boolean isExponentialPSQueue = false;
		if (queue instanceof PSQueue) {
			isExponentialPSQueue = ((PSQueue) queue).expPS;
		}

		if ((statsLevel > 0)
				&& (isExponentialPSQueue || qPlaceQueueStats.indrStats)) {
			for (int c = 0; c < numColors; c++)
				// Make sure that all meanServTimes have been initialized
				if (meanServTimes[c] < 0) {
					log.error("meanServTimes[" + c
							+ "] has not been initialized for QPlace " + name);
					throw new SimQPNException();
				}
		}

		// PS Queues
		this.queueTokens = new ArrayList[numColors]; // TODO: replace with more
														// efficient data
														// structures.
		for (int c = 0; c < numColors; c++) {
			if (individualTokens[c]
					|| (queue.queueDiscip == QueuingDiscipline.PS && statsLevel >= 3))
				this.queueTokens[c] = new ArrayList(100); // SDK-TODO: See if
															// 100 is optimal
															// initial capacity.
															// Note: The list is
															// auto-expanding.
		}

		// PS Queues
		if (queue.queueDiscip == QueuingDiscipline.PS
				&& (!isExponentialPSQueue)) {
			queueTokResidServTimes = new DoubleArrayList[numColors]; // NOTE:
																		// Note
																		// that
																		// given
																		// that
																		// queueTokResidServTimes
																		// is
																		// updated
																		// frequently,
																		// it is
																		// more
																		// efficient
																		// to
																		// use
																		// an
																		// array
																		// here
																		// than
																		// a
																		// LinkedList!
			for (int c = 0; c < numColors; c++)
				queueTokResidServTimes[c] = new DoubleArrayList(100); // SDK-TODO:
																		// See
																		// if
																		// 100
																		// is
																		// optimal
																		// initial
																		// capacity.
																		// Note:
																		// The
																		// list
																		// is
																		// auto-expanding.
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
	@Override
	public void start(SimQPNConfiguration configuration, double clock)
			throws SimQPNException {
		if (statsLevel > 0) {
			// Start statistics collection
			qPlaceQueueStats.start(queueTokenPop, configuration, clock);
			super.start(configuration, clock);
		}
	}

	/**
	 * Method finish
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	@Override
	public void finish(SimQPNConfiguration configuration,
			double runWallClockTime, double clock) throws SimQPNException {
		if (statsLevel > 0) {
			// Complete statistics collection
			qPlaceQueueStats.finish(queueTokenPop, configuration,
					runWallClockTime, clock);
			super.finish(configuration, runWallClockTime, clock);
		}
	}

	/**
	 * Method addTokens - deposits N tokens of particular color
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
	@Override
	public void addTokens(int color, int count, Token[] tokensToBeAdded,
			Executor executor) throws SimQPNException {
		if (count <= 0) { // DEBUG
			log.error("Attempted to add nonpositive number of tokens to queue "
					+ name);
			throw new SimQPNException();
		}

		// Update Stats (below more...) (Note: watch out the order of this
		// and
		// next statement)
		if (statsLevel > 0) {
			qPlaceQueueStats.updateTkPopStats(color, queueTokenPop[color],
					count, executor.getClock());
		}
		queueTokenPop[color] += count;
		if (individualTokens[color]) {
			queue.addTokens(this, color, count, tokensToBeAdded, executor);
		} else {
			queue.addTokens(this, color, count, null, executor);
		}
	}

	/**
	 * Method completeService - completes service of a token, moves it to the
	 * depository and schedules next waiting token for service.
	 * 
	 * @param token
	 *            - token completing service
	 * @return
	 * @exception
	 */
	public void completeService(Token token, Executor executor)
			throws SimQPNException {
		if (queueTokenPop[token.color] < 1) {
			log.error("Attempted to remove a token from queue " + name
					+ " which is empty!");
			throw new SimQPNException();
		}

		// Update stats (below more...) (Note: watch out the order of this and
		// next statement)
		if (statsLevel > 0) {
			qPlaceQueueStats.updateTkPopStats(token.color,
					queueTokenPop[token.color], -1, executor.getClock());
			if (statsLevel >= 3)
				qPlaceQueueStats.updateSojTimeStats(token.color,
						executor.getClock() - token.arrivTS,
						executor.getConfiguration());
		}

		// Now remove token from queue and update queue state
		queueTokenPop[token.color]--;

		queue.completeService(token, executor);

		// Finally move token to depository
		if (individualTokens[token.color]) {
			tkCopyBuffer[0] = token;
			super.addTokens(token.color, 1, tkCopyBuffer, executor);
		} else {
			super.addTokens(token.color, 1, null, executor);
		}
	}

	public void report(SimQPNConfiguration configuration)
			throws SimQPNException {
		if (statsLevel > 0) {
			qPlaceQueueStats.printReport(configuration);
			super.report(configuration);
			queue.report();
		}
	}

}
