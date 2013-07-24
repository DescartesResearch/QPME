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
 * Contributor(s):      Frederik Zipp, Simon Spinner  
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2008/11/22  Samuel Kounev     Created.
 *  2008/12/13  Samuel Kounev     Fixed a bug in updateEvents() for expPS==true.
 *  2009/02/13  Samuel Kounev     Changed eventList to use PriorityQueue instead of LinkedList
 *                                to speed up searches in the event list.
 *  2009/04/08  Samuel Kounev     Added a check in clearEvents() to make sure that events are removed from the event lists.
 *  2009/08/03  Frederik Zipp     Added xmlId property. 
 *  2009/05/05  Frederik Zipp     Support for central queues.
 *  2010/07/24  Simon Spinner	  Support for tracking tokens.
 *  2013/07/16  Jürgen Walter	  Extracted queuing strategies (FCFS, PS, IS)
 *  2013/07/22	Jürgen Walter	  Made queue abstract
 * 
 */

package de.tud.cs.simqpn.kernel.entities.queue;

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.Token;
import de.tud.cs.simqpn.kernel.executor.Executor;
import de.tud.cs.simqpn.kernel.executor.QueueEvent;
import de.tud.cs.simqpn.kernel.stats.QueueStats;

/**
 * Queue is the abstract base class for all queuing strategies.
 * 
 * Note: We use the term queue to refer to a queueing station including both the
 * waiting area and the servers.
 * 
 * Note: We assume that in the beginning of the run, the queue is empty!
 * 
 * @author Samuel Kounev
 * @version %I%, %G%
 */
public abstract class Queue {

	private static Logger log = Logger.getLogger(Queue.class);

	public int id; // Global id of the queue.
	public String xmlId; // XML ID
	public String name; // Name of the queue.
	public QueuingDiscipline queueDiscip; // Queueing discipline.

	public QPlace[] qPlaces; // Queueing places this queue is part of.
	public int totNumColors; // Total number of token colors over all queueing
								// places the queue is part of.
	private int statsLevel; // The minimum statsLevel of all queueing places the
							// queue
	// is part of.
	// NOTE: we set statsLevel to the minimum here because
	// currently some of statistics we compute are based on
	// corresponding statistics from the QPlaces the queue is
	// part of.

	/** Number of servers in queueing station. */
	public int numServers;
	/** Object containing statistics for this queue. */
	public QueueStats queueStats;
	/** The current number of tokens residing in the queue. */
	private long tkPopulation;
	/**
	 * The maximum token population in the current epoch. Used for Overflow
	 * Detection
	 */
	private long maxEpochPopulation;
	/**
	 * The total maximum token population in the queue. Used for Overflow
	 * Detection
	 */
	private long totalMaxPopulation;
	/**
	 * Overflow Detection: the number of measurements that were taken in the
	 * current epoch
	 */
	private int epochMsrmCnt;
	/**
	 * The length (number of measurements) of one epoch. Used for
	 * OverflowDetection
	 */
	private long epochLength = SimQPNConfiguration.OVERFLOW_DET_START_EPOCH_LENGTH;

	private long maxPopulationAtRisingStart;
	/**
	 * The number of consecutive epochs in which the maximum population has
	 * grown. Used for Overflow Detection.
	 */
	public int cntConsRisingEpoch;
	private boolean deactivateWarning = false;

	/**
	 * Returns a clone of this queue
	 * 
	 * @param configuration
	 * @param places
	 * @return
	 */
	public abstract Queue clone(SimQPNConfiguration configuration,
			Place[] places);

	/**
	 * Sets instance parameters to the ones of the passed queue
	 * 
	 * @param queue
	 *            - The queue we take the settings from
	 * @param configuration
	 *            - The configuration used
	 * @param places
	 *            - The places the queue should refer to
	 */
	protected void setParameters(Queue queue,
			SimQPNConfiguration configuration, Place[] places) {
		this.totNumColors = queue.totNumColors;
		this.statsLevel = queue.statsLevel;
		this.numServers = queue.numServers;
		this.tkPopulation = queue.tkPopulation;
		this.maxEpochPopulation = queue.maxEpochPopulation;
		this.totalMaxPopulation = queue.totalMaxPopulation; //
		this.epochMsrmCnt = queue.epochMsrmCnt;
		this.epochLength = queue.epochLength; //
		this.maxEpochPopulation = queue.maxEpochPopulation;
		this.cntConsRisingEpoch = queue.cntConsRisingEpoch;
		this.deactivateWarning = queue.deactivateWarning; //

	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            - global id of the queue
	 * @param name
	 *            - name of the queue
	 * @param queueDiscipline
	 *            - queueing discipline
	 * @param numServers
	 *            - number of servers in queue
	 */
	public Queue(int id, String xmlId, String name,
			QueuingDiscipline queueDiscipline, int numServers)
			throws SimQPNException {

		this.id = id;
		this.xmlId = xmlId;
		this.name = name;
		this.queueDiscip = queueDiscipline;
		this.numServers = numServers;
		this.qPlaces = null;
		this.totNumColors = 0;
		this.statsLevel = 0;

	}

	/**
	 * Adds a queuing place (this queue is part of) to the list of queuing
	 * places
	 * 
	 * @param qPl
	 *            - The queuing place to bee added
	 * @throws SimQPNException
	 */
	public void addQPlace(QPlace qPl) throws SimQPNException {
		if (qPlaces == null) {
			qPlaces = new QPlace[1];
			qPlaces[0] = qPl;
		} else {
			QPlace[] qPlacesTMP = qPlaces;
			qPlaces = new QPlace[qPlacesTMP.length + 1];
			System.arraycopy(qPlacesTMP, 0, qPlaces, 0, qPlacesTMP.length);
			qPlaces[qPlaces.length - 1] = qPl;
		}
	}

	/**
	 * Initializes object for statistic collection
	 * 
	 * NOTE: Should be called after the qPlaces array has been initialized
	 * before the run has started.
	 * 
	 * @param configuration
	 * @throws SimQPNException
	 */
	public void init(SimQPNConfiguration configuration) throws SimQPNException {
		statsLevel = 10;

		if (qPlaces == null) {
			log.error("No qplaces associated with queue " + name);
			throw new SimQPNException();
		}

		for (int p = 0; p < qPlaces.length; p++) { // NOTE: The two variables
													// below are intentionally
													// set here and not in
													// addQPlace(), since the
													// user might choose
													// (although that's not
													// recommended) to
													// initialize qPlaces
													// externally bypassing
													// addQPlace().
			totNumColors += qPlaces[p].numColors;
			if (qPlaces[p].statsLevel < statsLevel)
				statsLevel = qPlaces[p].statsLevel;
		}

		if (statsLevel > 0) // NOTE: This is intentionally done here after
							// qPlaces has been initialized!
			queueStats = new QueueStats(id, name, totNumColors, statsLevel,
					queueDiscip, numServers, this, configuration);

	}

	/**
	 * Starts the collection of statistics for this queue.
	 * 
	 * @param clock
	 *            - The initial clock when to start statistic collection
	 * @throws SimQPNException
	 */
	public void start(double clock) throws SimQPNException {
		if (statsLevel > 0)
			queueStats.start(clock);
	}

	/**
	 * Complete statistics collection.
	 * 
	 * @param configuration
	 * @param runWallClockTime
	 * @param clock
	 *            -
	 * @throws SimQPNException
	 */
	public void finish(SimQPNConfiguration configuration,
			double runWallClockTime, double clock) throws SimQPNException {
		if (statsLevel > 0)
			queueStats.finish(configuration, runWallClockTime, clock);
	}

	/**
	 * Method report
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public void report() throws SimQPNException {
		if (statsLevel > 0)
			queueStats.printReport();
	}

	/**
	 * Method updateEvents (Used only for PS queues). Schedules next service
	 * completion event (if any) according to current token population.
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public void updateEvents(Executor executor) throws SimQPNException {
	}

	/**
	 * Method clearEvents (Used only for PS queues) - clears all scheduled
	 * service completion events for this queue.
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public void clearEvents(Executor executor) throws SimQPNException {
	}

	/**
	 * Method updateResidServTimes - updates token residual times. Used only for
	 * PS queues with non-exp service times.
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public void updateResidServTimes(double clock) {
	}

	// TODO: Consider Simulator.scheduleEvent() below for the case statsLevel <
	// 3

	/**
	 * Method addTokens - deposits N tokens of particular color
	 * 
	 * @param color
	 *            - color of tokens
	 * @param count
	 *            - number of tokens to deposit
	 * @param tokensToBeAdded
	 *            - individual tokens (if tracking = true)
	 * @return
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	public void addTokens(QPlace qPl, int color, int count,
			Token[] tokensToBeAdded, Executor executor) throws SimQPNException {
		System.out.println(count);

		tkPopulation += count;

		/*
		 * Overflow detection mechanism: The following algorithm tries to
		 * determine an upper bound for the token population in the queue. A
		 * number of measurements is grouped to epochs with dynamic length. The
		 * length of an epoch is adjusted to the growth rate of the population.
		 * If the maximum total population increases in several consecutive
		 * epochs a warning is printed out.
		 */
		if (tkPopulation > maxEpochPopulation) {
			maxEpochPopulation = tkPopulation;
		}
		epochMsrmCnt++;

		if (executor.getClock() <= 1.0) {
			// Skip overflow detection at the beginning of the simulation.
			// No representative results can be determined during startup.
			cntConsRisingEpoch = 0;
			maxPopulationAtRisingStart = maxEpochPopulation;
		} else if (epochMsrmCnt >= epochLength) {
			// New maximum population?
			if (maxEpochPopulation > totalMaxPopulation) {
				totalMaxPopulation = maxEpochPopulation;
				cntConsRisingEpoch++;
			} else {
				// Maximum population of queue in current epoch is lower
				// than the total maximum. Reset consecutive epoch counter
				// and increase epoch length. If the population is growing
				// indefinitely
				// (maybe with valleys) then there must be a value for epoch
				// length, so that
				// in each epoch a new maximum is reached. Otherwise an upper
				// bound is finally reached.
				cntConsRisingEpoch = 0;
				maxPopulationAtRisingStart = totalMaxPopulation;
				if ((epochLength * 2) <= SimQPNConfiguration.OVERFLOW_DET_MAX_EPOCH_LENGTH) {
					epochLength *= 2;
				}
			}

			maxEpochPopulation = 0;
			epochMsrmCnt = 0;

			if (totalMaxPopulation < SimQPNConfiguration.OVERFLOW_DET_DETECTION_THRESHOLD) {
				// If total population is below the detection threshold, do not
				// count consecutive rising epochs. Thus the overflow detection
				// is
				// disabled if the queue population is low.
				maxPopulationAtRisingStart = totalMaxPopulation;
			} else {

				// Check whether an overflow warning is issued. The first
				// part of the condition is for fast growing queues, the second
				// part for slowly growing ones.
				if (((totalMaxPopulation > 10 * maxPopulationAtRisingStart) && (cntConsRisingEpoch > SimQPNConfiguration.OVERFLOW_DET_MIN_CONS_RISING_EPOCHS))
						|| ((cntConsRisingEpoch > SimQPNConfiguration.OVERFLOW_DET_MAX_CONS_RISING_EPOCHS) && (totalMaxPopulation > 2 * maxPopulationAtRisingStart))) {
					if (!deactivateWarning) {
						executor.getProgressMonitor()
								.warning(
										executor.getId(),
										"Queue \""
												+ name
												+ "\" is exceedingly growing. An overflow might occur.");
						deactivateWarning = true;
					}
					cntConsRisingEpoch = 0;
					maxPopulationAtRisingStart = totalMaxPopulation;
				}
			}
		}

		if (statsLevel >= 2) { // NOTE: For statsLevel=1, we don't need to do
								// anything since throughput data is calculated
								// as sum of the throughputs of all QPlaces the
								// Queue is part of.
			queueStats.updateTotTkPopStats(count, executor.getClock());
		}
		// if (queueDiscip == unknown) {
		// log.error("Invalid queueing discipline for QPlace " + name);
		// throw new SimQPNException();
		// }
	}

	/**
	 * Method completeService - completes service of a token and schedules next
	 * waiting token for service.
	 * 
	 * @param token
	 *            - token completing service
	 * @return
	 * @exception
	 */
	public void completeService(Token token, Executor executor)
			throws SimQPNException {

		tkPopulation--;

		if (statsLevel >= 2) // NOTE: For statsLevel=1, we don't need to do
								// anything since throughput data is calculated
								// as sum of the throughputs of all QPlaces the
								// Queue is part of.
			queueStats.updateTotTkPopStats(-1, executor.getClock());

		// if (queueDiscip unknown) {
		// log.error("Invalid queueing discipline for QPlace " + name);
		// throw new SimQPNException();
		// }
	}

	public boolean areEventsUpToDate() {
		return true;
	}

	/**
	 * Should be overwritten to improve lookahead
	 * 
	 * @return
	 */
	public double getLookahead() {
		return 0;
	}

	public void onQueueEventScheduled(QueueEvent queueEvent) {

	}

}
