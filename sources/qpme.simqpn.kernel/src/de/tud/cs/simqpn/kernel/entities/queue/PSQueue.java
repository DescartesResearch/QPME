package de.tud.cs.simqpn.kernel.entities.queue;

import org.apache.log4j.Logger;

import cern.jet.random.AbstractContinousDistribution;
import cern.jet.random.Empirical;
import cern.jet.random.EmpiricalWalker;
import cern.jet.random.Exponential;
import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.Token;
import de.tud.cs.simqpn.kernel.executor.Executor;
import de.tud.cs.simqpn.kernel.executor.QueueEvent;
import de.tud.cs.simqpn.kernel.random.RandomNumberGenerator;

public class PSQueue extends Queue {

	private static Logger log = Logger.getLogger(PSQueue.class);

	/**
	 * True if currently scheduled events for this queue (if any) reflect the
	 * latest token popolation of the queue.
	 */
	public boolean eventsUpToDate;

	/**
	 * PS queues: True if there is currently a service completion event
	 * scheduled for this queue.
	 */
	public boolean eventScheduled;

	/** Next service completion event scheduled for this queue. */
	public QueueEvent nextEvent;

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
	public int tkSchedPl;

	/**
	 * Requires expPS==false: Color of the next token scheduled to complete
	 * service.
	 */
	public int tkSchedCol;

	/**
	 * Requires expPS==false: Index in QPlace.queueTokArrivTS[tkSchedCol] and
	 * QPlace.queueTokResidServTimes[tkSchedCol] of the next token scheduled to
	 * complete service.
	 */
	public int tkSchedPos;

	/**
	 * Requires expPS==false: Time of the last event scheduling, i.e. time of
	 * the last event with effect on this queue.
	 */
	public double lastEventClock;

	/**
	 * Requires expPS==false: Token population at the time of the last event
	 * scheduling.
	 */
	public int lastEventTkCnt;

	/**
	 * Requires expPS==true: Random number generators for generating service
	 * times.
	 */
	public AbstractContinousDistribution[] expRandServTimeGen;

	/**
	 * Requires expPS==true: Random number generator for generating token
	 * colors.
	 */
	public EmpiricalWalker randColorGen;

	/**
	 * @param id
	 * @param xmlId
	 * @param name
	 * @param queueDiscip
	 * @param numServers
	 * @throws SimQPNException
	 * @Override
	 */
	public PSQueue(int id, String xmlId, String name,
			QueuingDiscipline queueDiscip, int numServers)
			throws SimQPNException {

		super(id, xmlId, name, queueDiscip, numServers);

		eventsUpToDate = false;
		eventScheduled = false;
		expPS = false; // By default non-exponential queue is assumed.
	}

	@Override
	public Queue clone(SimQPNConfiguration configuration, Place[] places) {
		PSQueue clone = null;
		try {
			clone = new PSQueue(id, xmlId, name, queueDiscip,
					numServers);
			super.finishCloning(clone, configuration, places);
			clone.eventsUpToDate = this.eventsUpToDate;
			clone.eventScheduled = this.eventScheduled;

			// TODO fix this and following line
			// TODO Copy event into LP
			clone.nextEvent = this.nextEvent;
			if (this.nextEvent != null) {
				log.error("copy mistake in queue");
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
				// TODO JUERGEN Check if random is right
				clone.expRandServTimeGen = this.expRandServTimeGen;
				// TODO JUERGEN Check if random is right
				clone.randColorGen = this.randColorGen;
			}
		} catch (SimQPNException e) {
			log.error("Error during PSQueue cloning",e);
		}
		return clone;
	};

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
	 * Method updateEvents (Used only for PS queues). Schedules next service
	 * completion event (if any) according to current token population.
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	@Override
	public void updateEvents(Executor executor) throws SimQPNException {
		if (eventsUpToDate) {
			return;
		}

		int totQueTokCnt = 0;
		for (int p = 0, nC = 0; p < qPlaces.length; p++) {
			nC = qPlaces[p].numColors;
			for (int c = 0; c < nC; c++)
				totQueTokCnt += qPlaces[p].getQueueTokenPop()[c];
		}

		if (totQueTokCnt > 0) {
			if (expPS) { // Exponential service times
				double[] meanServRates = new double[totNumColors];
				double conc = 1;
				if (numServers > 1 && totQueTokCnt > 1) // "-/M/n-PS" queues
					conc = (totQueTokCnt <= numServers) ? totQueTokCnt
							: numServers;
				for (int p = 0, nC = 0, i = 0; p < qPlaces.length; p++) {
					nC = qPlaces[p].numColors;
					for (int c = 0; c < nC; c++)
						meanServRates[i++] = (((double) qPlaces[p]
								.getQueueTokenPop()[c]) / totQueTokCnt)
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
					throw new SimQPNException();
				}
				double servTime = minRST * totQueTokCnt; // Default for
															// "-/G/1-PS" queue
				if (numServers > 1 && totQueTokCnt > 1) // "-/G/n-PS" queues
					servTime /= ((totQueTokCnt <= numServers) ? totQueTokCnt
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
				lastEventTkCnt = totQueTokCnt;
				eventScheduled = true;
			}
		}
		eventsUpToDate = true;
	}

	/**
	 * Method clearEvents (Used only for PS queues) - clears all scheduled
	 * service completion events for this queue.
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	@Override
	public void clearEvents(Executor executor) throws SimQPNException {
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
	 * Method updateResidServTimes - updates token residual times. Used only for
	 * PS queues with non-exp service times.
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	@Override
	public void updateResidServTimes(double clock) {
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

	// @Override
	// public void onQueueEventScheduled(QueueEvent queueEvent) {
	// this.nextEvent = queueEvent;
	// }

	@Override
	public void addTokens(QPlace qPl, int color, int count,
			Token[] tokensToBeAdded, Executor executor) throws SimQPNException {
		super.addTokens(qPl, color, count, tokensToBeAdded, executor);

		if (!expPS) {
			if (eventScheduled) {
				/*
				 * NOTE: WATCH OUT! Method should be called before the new
				 * tokens have been added to queueTokResidServTimes!
				 */
				updateResidServTimes(executor.getClock());
			}
			for (int i = 0; i < count; i++) {
				double servTime = qPl.randServTimeGen[color].nextDouble();
				if (servTime < 0)
					servTime = 0;
				qPl.getQueueTokResidServTimes()[color].add(servTime);
			}
		}
		if ((tokensToBeAdded != null) || (qPl.statsLevel >= 3)) {
			// if we get tokens from caller or we have to measure the sojourn
			// times, store the individual tokens.
			for (int i = 0; i < count; i++) {
				Token tk = (tokensToBeAdded != null) ? tokensToBeAdded[i]
						: new Token(qPl, color);
				tk.arrivTS = executor.getClock();
				qPl.queueTokens[color].add(tk);
			}
		}
		if (eventScheduled) {
			clearEvents(executor); // NOTE: clearEvents() resets eventScheduled
									// to false;
		}
		eventsUpToDate = false;
	}

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
			updateResidServTimes(executor.getClock()); // NOTE: WATCH OUT!
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

	@Override
	public boolean areEventsUpToDate() {
		return eventsUpToDate;
	}

	@Override
	public void onQueueEventScheduled(QueueEvent queueEvent) {
		nextEvent = queueEvent;
	}

}
