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
 * Original Author(s):  Jürgen Walter
 * Contributor(s):
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2013/07/16  Jürgen Walter     Extracted from Queue.
 * 
 */
package de.tud.cs.simqpn.kernel.entities.queue;

import org.apache.log4j.Logger;

import cern.jet.random.AbstractContinousDistribution;
import cern.jet.random.Empirical;
import cern.jet.random.EmpiricalWalker;
import cern.jet.random.Exponential;
import de.tud.cs.simqpn.kernel.RandomNumberGenerator;
import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.Token;
import de.tud.cs.simqpn.kernel.executor.Executor;
import de.tud.cs.simqpn.kernel.executor.QueueEvent;

/**
 * This class implements the Processor Sharing(PS) scheduling strategy.
 * 
 * Processor sharing is a version of Round Robin scheduling with infinitesimal
 * small time slices. All requests are served simultaneously. PS is used to
 * model CPUs.
 */
public class PSQueue extends Queue {

	private static Logger log = Logger.getLogger(PSQueue.class);

	/**
	 * True if currently scheduled events for this queue (if any) reflect the
	 * latest token population of the queue.
	 */
	private boolean eventsUpToDate;

	/**
	 * PS queues: True if there is currently a service completion event
	 * scheduled for this queue.
	 */
	private boolean eventScheduled;

	/** Next service completion event scheduled for this queue. */
	private QueueEvent nextEvent;

	/**
	 * true = Processor-Sharing with exponential service times.
	 * 
	 * false = Processor-Sharing with non-exponential service times.
	 */
	public boolean expPS;

	/**
	 * Requires expPS==false: Queueing place containing the next token scheduled
	 * to complete service.
	 */
	private int tkSchedPl;

	/**
	 * Requires expPS==false: Color of the next token scheduled to complete
	 * service.
	 */
	private int tkSchedCol;

	/**
	 * Requires expPS==false: Index in QPlace.queueTokArrivTS[tkSchedCol] and
	 * QPlace.queueTokResidServTimes[tkSchedCol] of the next token scheduled to
	 * complete service.
	 */
	private int tkSchedPos;

	/**
	 * Requires expPS==false: Time of the last event scheduling, i.e. time of
	 * the last event with effect on this queue.
	 */
	private double lastEventClock;

	/**
	 * Requires expPS==false: Token population at the time of the last event
	 * scheduling.
	 */
	private int lastEventTkCnt;

	/**
	 * Requires expPS==true: Random number generators for generating service
	 * times.
	 */
	private AbstractContinousDistribution[] expRandServTimeGen;

	/**
	 * Requires expPS==true: Random number generator for generating token
	 * colors.
	 */
	private EmpiricalWalker randColorGen;

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
	public PSQueue(int id, String xmlId, String name,
			QueueingDiscipline queueDiscip, int numServers) {

		super(id, xmlId, name, queueDiscip, numServers);

		eventsUpToDate = false;
		eventScheduled = false;
		expPS = false; // By default non-exponential queue is assumed.
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Queue clone(SimQPNConfiguration configuration, Place[] places) {
		PSQueue clone = null;
		try {
			clone = new PSQueue(id, xmlId, name, queueDiscip, numServers);
			clone.setParameters(this, configuration, places);
			clone.eventsUpToDate = this.eventsUpToDate;
			clone.eventScheduled = this.eventScheduled;

			clone.nextEvent = this.nextEvent;
			if (this.nextEvent != null) {
				log.error("Copy error for PS queue: Queue to clone has already scheduled next event");
				// Note: This appears only trying to clone queue that has been
				// simulated. If this error is thrown we could add event cloning
				// in the future.
				throw new SimQPNException();
			}
			clone.expPS = this.expPS;
			if (!this.expPS) {
				clone.tkSchedPl = this.tkSchedPl;
				clone.tkSchedCol = this.tkSchedCol;
				clone.tkSchedPos = this.tkSchedPos;
				clone.lastEventClock = this.lastEventClock;
				clone.lastEventTkCnt = this.lastEventTkCnt;
			} else {
				clone.expRandServTimeGen = this.expRandServTimeGen.clone();
				clone.randColorGen = (EmpiricalWalker) this.randColorGen
						.clone();
			}
		} catch (SimQPNException e) {
			log.error("Error during PSQueue cloning", e);
		}
		return clone;
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(SimQPNConfiguration configuration) throws SimQPNException {
		super.init(configuration);

		// PS Queues
		if (expPS) {
			expRandServTimeGen = new Exponential[1];
			expRandServTimeGen[0] = new Exponential(0,
					RandomNumberGenerator.nextRandNumGen());
			double[] pdf = new double[totNumColors];
			for (int c = 0; c < totNumColors; c++)
				pdf[c] = 1;
			randColorGen = new EmpiricalWalker(pdf, Empirical.NO_INTERPOLATION,
					RandomNumberGenerator.nextRandNumGen());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateEvents(Executor executor) {
		if (eventsUpToDate) {
			return;
		}

		int totalQueueTokenCnt = 0;
		for (int placeID = 0, numColors = 0; placeID < qPlaces.length; placeID++) {
			numColors = qPlaces[placeID].numColors;
			for (int c = 0; c < numColors; c++) {
				totalQueueTokenCnt += qPlaces[placeID].getQueueTokenPop()[c];
			}
		}

		if (totalQueueTokenCnt > 0) {
			if (expPS) { // Exponential service times
				double[] meanServRates = new double[totNumColors];
				double conc = 1;
				if (numServers > 1 && totalQueueTokenCnt > 1) // "-/M/n-PS"
																// queues
					conc = (totalQueueTokenCnt <= numServers) ? totalQueueTokenCnt
							: numServers;
				for (int p = 0, nC = 0, i = 0; p < qPlaces.length; p++) {
					nC = qPlaces[p].numColors;
					for (int c = 0; c < nC; c++)
						meanServRates[i++] = (((double) qPlaces[p]
								.getQueueTokenPop()[c]) / totalQueueTokenCnt)
								* ((double) 1 / qPlaces[p].meanServTimes[c])
								* conc;
				}
				double totServRate = 0;
				for (int i = 0; i < totNumColors; i++)
					totServRate += meanServRates[i];
				double[] pdf = new double[totNumColors];
				for (int i = 0; i < totNumColors; i++)
					pdf[i] = meanServRates[i] / totServRate;

				((Exponential) expRandServTimeGen[0]).setState(totServRate);
				randColorGen.setState2(pdf);
				double servTime = expRandServTimeGen[0].nextDouble();
				if (servTime < 0)
					servTime = 0;
				int color = randColorGen.nextInt();

				boolean done = false;
				for (int p = 0, nC = 0, i = 0; p < qPlaces.length; p++) {
					nC = qPlaces[p].numColors;
					for (int c = 0; c < nC; c++, i++) {
						if (i == color) {
							if (qPlaces[p].queueTokens[c] != null) {
								executor.scheduleEvent(servTime, this,
										(Token) qPlaces[p].queueTokens[c]
												.get(0));
							} else {
								executor.scheduleEvent(servTime, this,
										new Token(qPlaces[p], c));
							}
							done = true;
							break;
						}
					}
					if (done)
						break;
				}
				eventScheduled = true;
			} else { // Non-exponential service times
				// Find token with minimal residual service time (RST)
				double curRST, minRST = -1;
				int numTk;
				for (int p = 0, nC = 0; p < qPlaces.length; p++) {
					nC = qPlaces[p].numColors;
					for (int c = 0; c < nC; c++) {
						numTk = qPlaces[p].getQueueTokenPop()[c]; // =
																	// queueTokResidServTimes[c].size();
						if (numTk > 0)
							for (int i = 0; i < numTk; i++) {
								curRST = qPlaces[p].getQueueTokResidServTimes()[c]
										.get(i);
								if (minRST == -1 || curRST < minRST) {
									minRST = curRST;
									tkSchedPl = p;
									tkSchedCol = c;
									tkSchedPos = i;
								}
							}
					}
				}
				if (minRST == -1) { // DEBUG
					log.error("Illegal state in queue " + name);
				}
				double servTime = minRST * totalQueueTokenCnt; // Default for
																// "-/G/1-PS"
																// queue
				if (numServers > 1 && totalQueueTokenCnt > 1) // "-/G/n-PS"
																// queues
					servTime /= ((totalQueueTokenCnt <= numServers) ? totalQueueTokenCnt
							: numServers);
				if (qPlaces[tkSchedPl].queueTokens[tkSchedCol] != null) {
					executor.scheduleEvent(servTime, this,
							(Token) qPlaces[tkSchedPl].queueTokens[tkSchedCol]
									.get(tkSchedPos));
				} else {
					executor.scheduleEvent(servTime, this, new Token(
							qPlaces[tkSchedPl], tkSchedCol));
				}
				lastEventClock = executor.getClock();
				lastEventTkCnt = totalQueueTokenCnt;
				eventScheduled = true;
			}
		}
		eventsUpToDate = true;
	}

	/**
	 * Deposits N tokens of particular color.
	 * 
	 * @param queuingPlace
	 *            the QPlace
	 * @param color
	 *            color of tokens
	 * @param count
	 *            number of tokens to deposit
	 * @param tokensToBeAdded
	 *            individual tokens (if tracking = true)
	 * @param executor
	 *            the executor
	 * @throws SimQPNException
	 *             inherited from queue, not relevant for PSQueue
	 */
	@Override
	public void addTokens(QPlace queuingPlace, int color, int count,
			Token[] tokensToBeAdded, Executor executor) throws SimQPNException {
		super.addTokens(queuingPlace, color, count, tokensToBeAdded, executor);

		if (!expPS) {
			if (eventScheduled) {
				/*
				 * NOTE: WATCH OUT! Method should be called before the new
				 * tokens have been added to queueTokResidServTimes!
				 */
				updateResidualServiceTimes(executor.getClock());
			}
			for (int i = 0; i < count; i++) {
				double serviceTime = queuingPlace.removeNextServiceTime(color);
				queuingPlace.getQueueTokResidServTimes()[color]
						.add(serviceTime);
			}
		}
		if ((tokensToBeAdded != null) || (queuingPlace.statsLevel >= 3)) {
			// if we get tokens from caller or we have to measure the sojourn
			// times, store the individual tokens.
			for (int i = 0; i < count; i++) {
				Token token = (tokensToBeAdded != null) ? tokensToBeAdded[i]
						: new Token(queuingPlace, color);
				token.arrivTS = executor.getClock();
				queuingPlace.queueTokens[color].add(token);
			}
		}
		if (eventScheduled) {
			removeScheduledEvent(executor); // NOTE: clearEvents() resets
											// eventScheduled
			// to false;
		}
		eventsUpToDate = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void completeService(Token token, Executor executor)
			throws SimQPNException {
		super.completeService(token, executor);

		QPlace qPl = ((QPlace) token.place);
		if (!expPS) {
			if (qPlaces[tkSchedPl].queueTokens[tkSchedCol] != null)
				qPlaces[tkSchedPl].queueTokens[tkSchedCol].remove(tkSchedPos);
			qPlaces[tkSchedPl].getQueueTokResidServTimes()[tkSchedCol]
					.remove(tkSchedPos);
			updateResidualServiceTimes(executor.getClock()); // NOTE: WATCH OUT!
			// Method should be
			// called after served
			// token has been
			// removed from
			// queueTokResidServTimes!
		} else if (qPl.queueTokens[token.color] != null)
			qPl.queueTokens[token.color].remove(0);
		eventScheduled = false;
		eventsUpToDate = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onQueueEventScheduled(QueueEvent queueEvent) {
		nextEvent = queueEvent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getLookahead(QPlace queuingPlace, int color) {
		return queuingPlace.getNextServiceTime(color);
	}

	/**
	 * Clears scheduled service completion event.
	 * 
	 * @param executor
	 */
	private void removeScheduledEvent(Executor executor) {
		// Remove scheduled event from the event list.
		// Note that a maximum of one event can be scheduled per PS QPlace at a
		// time.

		/*
		 * WARNING: Potential problems with the use of eventList.remove() below
		 * because of the following bug:
		 * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6207984
		 * http://bugs.sun.com/bugdatabase/view_bug.do;jsessionid=859
		 * a6e381a7abfffffffff7e644d05a59d93?bug_id=6268068
		 * 
		 * On old JVMs that do not have the above bug fixed, if two events have
		 * the exact same time, the wrong one might be removed!
		 */
		executor.removeEvent(nextEvent);

		eventScheduled = false;
	}

	/**
	 * Updates the token residual times of tokens actually being in process.
	 * 
	 * Service times change if the number of parallel processed tokens either
	 * increase or decrease.
	 * 
	 * An increase of tokens slows down the processing by the queue.
	 * 
	 * Used only with non-exponential service times.
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	private void updateResidualServiceTimes(double clock) {
		int numTk;
		double curRST;
		double timeServed = (clock - lastEventClock) / lastEventTkCnt; // Default
																		// for
																		// "-/G/1-PS"
		if (numServers > 1 && lastEventTkCnt > 1)
			timeServed *= ((lastEventTkCnt <= numServers) ? lastEventTkCnt
					: numServers); // "-/G/n-PS" queues

		for (int p = 0, nC = 0; p < qPlaces.length; p++) {
			nC = qPlaces[p].numColors;
			for (int c = 0; c < nC; c++) {
				/*
				 * NOTE: don't use queueTokenPop[c] here! If tokens have been
				 * added, this would mess things up
				 */
				numTk = qPlaces[p].getQueueTokResidServTimes()[c].size();
				if (numTk > 0)
					for (int i = 0; i < numTk; i++) {
						curRST = qPlaces[p].getQueueTokResidServTimes()[c]
								.get(i) - timeServed;
						qPlaces[p].getQueueTokResidServTimes()[c]
								.set(i, curRST);
					}
			}
		}
	}

}
