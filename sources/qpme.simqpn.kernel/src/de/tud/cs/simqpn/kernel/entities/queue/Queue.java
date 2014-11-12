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
import de.tud.cs.simqpn.kernel.entities.stats.QueueStats;
import de.tud.cs.simqpn.kernel.executor.Executor;
import de.tud.cs.simqpn.kernel.executor.QueueEvent;

/**
 * This class is the abstract base class for all queuing strategies.
 * 
 * Note: We use the term queue to refer to a queuing station including both the
 * waiting area and the servers.
 * 
 * Note: We assume that in the beginning of the run, the queue is empty!
 * 
 * @author Samuel Kounev
 * @version %I%, %G%
 */
public abstract class Queue {

	/** logger for progress and error logging. */
	private static Logger log = Logger.getLogger(Queue.class);
	/**
	 * Global id of the queue.
	 */
	/* Note: This class does not ensure this ID to be unique */
	public final int id;
	/** XML ID. */
	public final String xmlId;
	/** Name of the queue. */
	public final String name;
	/** Queueing discipline of the queue. */
	public final QueueingDiscipline queueDiscip;
	/** Number of servers in queuing station. */
	public final int numServers;
	/** Queueing places this queue is part of. */
	public QPlace[] qPlaces;
	/**
	 * Total number of token colors over all queuing places the queue is part
	 * of.
	 */
	public int totNumColors;
	/**
	 * The minimum statsLevel of all queuing places the queue is part of.
	 */
	private int statsLevel;
	/*
	 * NOTE: we set statsLevel to the minimum here because currently some of
	 * statistics we compute are based on corresponding statistics from the
	 * QPlaces the queue is part of.
	 */
	/** Object containing statistics for this queue. */
	private QueueStats queueStats;
	/** The current number of tokens residing in the queue. */
	private long tokenPopulation;
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
	 * The number of measurements that were taken in the current epoch. Used for
	 * overflow detection.
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
	private int cntConsRisingEpoch;
	/**
	 * The setting for overflow warning.
	 */
	private boolean deactivateWarning = false;

	/**
	 * Constructor.
	 * 
	 * @param id
	 *            global id of the queue
	 * @param xmlID
	 *            identification within XML File
	 * @param name
	 *            name of the queue
	 * @param queueDiscipline
	 *            queuing discipline
	 * @param numServers
	 *            number of servers in queue
	 */
	public Queue(int id, String xmlID, String name,
			QueueingDiscipline queueDiscipline, int numServers) {
		this.id = id;
		this.xmlId = xmlID;
		this.name = name;
		this.queueDiscip = queueDiscipline;
		this.numServers = numServers;
		this.qPlaces = null;
		this.totNumColors = 0;
		this.statsLevel = 0;
	}

	/**
	 * Returns a clone of this queue.
	 * 
	 * @param configuration
	 *            the configuration for the simulation run
	 * @param places
	 *            the places of the cloned net
	 * @return a clone of this queue
	 */
	public abstract Queue clone(SimQPNConfiguration configuration,
			Place[] places);

	/**
	 * Sets instance parameters to the ones of the passed queue.
	 * 
	 * @param queue
	 *            the queue we take the settings from
	 * @param configuration
	 *            the configuration used
	 * @param places
	 *            the places the queue should refer to
	 */
	protected void setParameters(Queue queue,
			SimQPNConfiguration configuration, Place[] places) {
		this.totNumColors = queue.totNumColors;
		this.statsLevel = queue.statsLevel;
		this.tokenPopulation = queue.tokenPopulation;
		this.maxEpochPopulation = queue.maxEpochPopulation;
		this.totalMaxPopulation = queue.totalMaxPopulation; //
		this.epochMsrmCnt = queue.epochMsrmCnt;
		this.epochLength = queue.epochLength; //
		this.maxEpochPopulation = queue.maxEpochPopulation;
		this.cntConsRisingEpoch = queue.cntConsRisingEpoch;
		this.deactivateWarning = queue.deactivateWarning; //

	}

	/**
	 * Adds a queuing place (this queue is part of) to the list of queuing
	 * places.
	 * 
	 * @param qPl
	 *            The queuing place to bee added
	 */
	public void addQPlace(QPlace qPl) {
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
	 * Initializes object QueueStats for statistic collection.
	 * 
	 * NOTE: Should be called after the qPlaces array has been initialized
	 * before the run has started.
	 * 
	 * @param configuration
	 *            the simulation configuration
	 * @throws SimQPNException
	 *             if no qPlaces are associated with this queue or if error
	 *             during QueueStats object generation
	 */
	public void init(SimQPNConfiguration configuration) throws SimQPNException {
		statsLevel = 10;

		if (qPlaces == null) {
			log.error("No qplaces associated with queue " + name);
			throw new SimQPNException();
		}

		for (int p = 0; p < qPlaces.length; p++) {
			/*
			 * NOTE: The two variables below are intentionally set here and not
			 * in addQPlace(), since the user might choose (although that's not
			 * recommended) to initialize qPlaces externally bypassing
			 * addQPlace().
			 */
			totNumColors += qPlaces[p].numColors;
			if (qPlaces[p].statsLevel < statsLevel) {
				statsLevel = qPlaces[p].statsLevel;
			}
		}

		if (statsLevel > 0) {
			// NOTE: This is intentionally done here after
			// qPlaces has been initialized!
			queueStats = new QueueStats(id, name, totNumColors, statsLevel,
					queueDiscip, numServers, this, configuration);
		}
	}

	/**
	 * Starts the collection of statistics for this queue.
	 * 
	 * @param clock
	 *            The initial clock when to start statistic collection
	 * @throws SimQPNException
	 *             if error in QueueStats
	 */
	public void start(double clock) throws SimQPNException {
		if (statsLevel > 0) {
			queueStats.start(clock);
		}
	}

	/**
	 * Complete statistics collection.
	 * 
	 * @param configuration
	 *            the simulation configuration
	 * @param runWallClockTime
	 *            the time when finished data collection
	 * @param clock
	 *            simulation clock
	 * @throws SimQPNException
	 *             if error in queue stats
	 */
	public void finish(SimQPNConfiguration configuration,
			double runWallClockTime, double clock) throws SimQPNException {
		if (statsLevel > 0) {
			queueStats.finish(configuration, runWallClockTime, clock);
		}
	}

	/**
	 * Logs a report for this queue.
	 * 
	 * @throws SimQPNException
	 *             if error in queueStats
	 */
	public void report() throws SimQPNException {
		if (statsLevel > 0) {
			queueStats.printReport();
		}
	}

	/**
	 * Updates residual times of current token population.
	 * 
	 * @param executor
	 *            the executor to schedule event
	 *            
	 */
	public abstract void updateEvents(Executor executor);

	// TODO: Consider Simulator.scheduleEvent() below for the case statsLevel <
	// 3

	/**
	 * Deposits N tokens of particular color.
	 * 
	 * @param qPl
	 *            the QPlace
	 * @param color
	 *            color of tokens
	 * @param count
	 *            number of tokens to deposit
	 * @param tokensToBeAdded
	 *            individual tokens (if tracking = true)
	 * @param executor
	 *            the executor
	 * @throws SimQPNException	if an overwriting method throws an exception
	 */
	public void addTokens(QPlace qPl, int color, int count,
			Token[] tokensToBeAdded, Executor executor) throws SimQPNException{

		tokenPopulation += count;

		/*
		 * Overflow detection mechanism: The following algorithm tries to
		 * determine an upper bound for the token population in the queue. A
		 * number of measurements is grouped to epochs with dynamic length. The
		 * length of an epoch is adjusted to the growth rate of the population.
		 * If the maximum total population increases in several consecutive
		 * epochs a warning is printed out.
		 */
		if (tokenPopulation > maxEpochPopulation) {
			maxEpochPopulation = tokenPopulation;
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

		if (statsLevel >= 2) {
			/*
			 * NOTE: For statsLevel=1, we don't need to do anything since
			 * throughput data is calculated as sum of the throughput of all
			 * QPlaces the Queue is part of.
			 */
			queueStats.updateTotTkPopStats(count, executor.getClock());
		}
	}

	/**
	 * Completes service of a token and schedules next waiting token for
	 * service.
	 * 
	 * @param token
	 *            token completing service
	 * @param executor
	 *            the executor to complete service
	 * @throws SimQPNException
	 *             if overwriting methods throw an exception
	 */
	public void completeService(Token token, Executor executor)
			throws SimQPNException {
		tokenPopulation--;
		if (statsLevel >= 2) {
			/*
			 * NOTE: For statsLevel=1, we don't need to do anything since
			 * throughput data is calculated as sum of the throughput of all
			 * QPlaces the Queue is part of.
			 */
			queueStats.updateTotTkPopStats(-1, executor.getClock());
		}
	}

	/**
	 * Returns the time span from current clock the queue can ensure not to
	 * finish a token.
	 * 
	 * @param qpl
	 *            the QPlace to getLookahead for.
	 * @param color
	 *            the color of the token
	 * @return the time span from current clock the queue can ensure not to
	 *         finish a token
	 */
	public abstract double getLookahead(QPlace qpl, int color);

	/**
	 * Actualize queue if new event was scheduled. This methods only performs
	 * actions if overwritten.
	 * 
	 * @param queueEvent
	 *            the event that was scheduled
	 */
	public void onQueueEventScheduled(QueueEvent queueEvent) {
	}

	/**
	 * Returns queue statistic object.
	 * 
	 * @return queue statistic object
	 */
	public QueueStats getQueueStats() {
		return queueStats;
	}

	/**
	 * Set statistic collection object.
	 * 
	 * @param queueStats
	 *            statistic collection object
	 */
	public void setQueueStats(QueueStats queueStats) {
		this.queueStats = queueStats;
	}

	/**
	 * @return the tkPopulation
	 */
	public long getTokenPopulation() {
		return tokenPopulation;
	}

}
