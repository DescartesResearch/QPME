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
 *  2013/03/14  Jürgen Walter     Created.
 * 
 */
package de.tud.cs.simqpn.kernel.entities.queue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.Token;
import de.tud.cs.simqpn.kernel.executor.Executor;

public class ThreadSchedulingQueue extends Queue {

	private static Logger log = Logger.getLogger(ThreadSchedulingQueue.class);

	private static class TokenData {
		public double alreadyProcessedTime = 0;
		public double requiredProcessingTime = 0;
		public double serviceProcessingStart = 0;
		public boolean alreadyScheduled = false;
		public boolean nextServiceCompletionIsFinal = false;

		public double computeRequiredProcessingTime(double contextSwitchCost) {
			return (requiredProcessingTime - alreadyProcessedTime)
					+ contextSwitchCost;
		}
	}

	private Map<Token, TokenData> tokensData = null;

	private Map<QPlace, Integer> busyServersMapping = null;
	private boolean[] serversAvailability = null; // true ~ the server is
													// available

	private LinkedList<Token> waitingTokens = new LinkedList<Token>();
	private double[] threadExecutionTimes = null;

	double threadQuantum = 0;
	double contextSwitchCost = 0;

	public ThreadSchedulingQueue(int id, String xmlId, String name,
			QueueingDiscipline queueDiscip, int numServers,
			double threadQuantum, double contextSwitchCost)
			throws SimQPNException {
		super(id, xmlId, name, queueDiscip, numServers);
		this.threadQuantum = threadQuantum;
		this.contextSwitchCost = contextSwitchCost;

		tokensData = new HashMap<Token, TokenData>(numServers * 20);
		busyServersMapping = new HashMap<QPlace, Integer>(numServers * 20);

		serversAvailability = new boolean[numServers];
		threadExecutionTimes = new double[numServers];
		for (int i = 0; i < threadExecutionTimes.length; i++) {
			serversAvailability[i] = true;
			threadExecutionTimes[i] = 0;
		}
	}

	public void setProperties(List<Element> properties) {
		for (Element property : properties) {
			String name = property.attribute("name").getValue();
			String value = property.attribute("value").getValue();

			if (name.equals("QUANTUM")) {
				threadQuantum = Double.valueOf(value);
				continue;
			}

			if (name.equals("CONTEXT_SWITCH_COST")) {
				contextSwitchCost = Double.valueOf(value);
				continue;
			}
		}

		// TODO: warnings and errors
	}

	public void addTokens(QPlace qPl, int color, int count,
			Token[] tokensToBeAdded, Executor executor) throws SimQPNException {

		super.addTokens(qPl, color, count, tokensToBeAdded, executor);

		assert (count > 0);

		if (!placeIsBeingProcessed(qPl)) {
			Token addedToken = (tokensToBeAdded != null) ? tokensToBeAdded[0]
					: new Token(qPl, color);

			waitingTokens.addLast(addedToken);

			tokensData.put(addedToken, new TokenData());

			if (busyServersMapping.size() < numServers) {
				scheduleNextToken(qPl, color, addedToken, contextSwitchCost);
			}
		}
	}

	private int findFirstAvailableServer() {
		for (int i = 0; i < serversAvailability.length; i++) {
			if (serversAvailability[i]) {
				return i;
			}
		}

		throw new InternalError(
				"All the servers are busy; this code should never be executed.");
	}

	private boolean placeIsBeingProcessed(QPlace place) {

		for (Token token : waitingTokens) {
			if (token.place == place) {
				return true;
			}
		}

		return false;
	}

	private boolean willPreemptionOccur(double threadRunningTime,
			double desiredTokenProcessingTime) {
		double remainingQuantum = threadQuantum - threadRunningTime;

		// we prefer the preemption to occur in the case of exact match
		return (remainingQuantum <= desiredTokenProcessingTime);
	}

	private void scheduleNextToken(QPlace queingPlace, int color,
			Token scheduledToken, double contextSwitchCost)
			throws SimQPNException {

		int serverIndex = findFirstAvailableServer();
		scheduleNextToken(queingPlace, color, scheduledToken,
				contextSwitchCost, serverIndex);
	}

	private void scheduleNextToken(QPlace queingPlace, int color,
			Token scheduledToken, double contextSwitchCost, int serverIndex)
			throws SimQPNException {

		TokenData tokenData = tokensData.get(scheduledToken);
		if (!tokenData.alreadyScheduled) {
			tokenData.alreadyScheduled = true;

			tokenData.requiredProcessingTime = queingPlace.randServTimeGen[color]
					.nextDouble();
			if (tokenData.requiredProcessingTime < 0) {
				tokenData.requiredProcessingTime = 0;
			}
		}

		double serviceTimeWithContextSwitch = tokenData
				.computeRequiredProcessingTime(contextSwitchCost);

		double preemptedServiceTime = serviceTimeWithContextSwitch;
		if (willPreemptionOccur(threadExecutionTimes[serverIndex],
				serviceTimeWithContextSwitch)) {
			preemptedServiceTime = threadQuantum
					- threadExecutionTimes[serverIndex];
			assert (preemptedServiceTime > 0);
		} else {
			tokenData.nextServiceCompletionIsFinal = true;
		}

		double tokenProcessingTime = preemptedServiceTime - contextSwitchCost;
		tokenData.alreadyProcessedTime += tokenProcessingTime;

		busyServersMapping.put(queingPlace, serverIndex);
		serversAvailability[serverIndex] = false;

		Executor executor = queingPlace.getExecutor();
		tokenData.serviceProcessingStart = executor.getClock();
		executor.scheduleEvent(executor.getClock() + preemptedServiceTime,
				this, scheduledToken);

		// Update Stats
		if (queingPlace.statsLevel >= 3) {
			// wrong
			// queingPlace.qPlaceQueueStats.updateDelayTimeStats(color,
			// Simulator.clock - scheduledToken.arrivTS);
		}
	}

	public void completeService(Token token, Executor executor)
			throws SimQPNException {
		completeServiceVerbose(token, executor);
	}

	// Add Queue
	private boolean completeServiceVerbose(Token token, Executor executor)
			throws SimQPNException {

		QPlace completedServicePlace = (QPlace) token.place;

		TokenData tokenData = tokensData.get(token);

		int serverIndex = busyServersMapping.remove(completedServicePlace);
		threadExecutionTimes[serverIndex] += executor.getClock()
				- tokenData.serviceProcessingStart;

		// figure out how the things are
		boolean tokenIsProcessed = tokenData.nextServiceCompletionIsFinal;
		boolean forcedPreemption = !tokenIsProcessed;
		boolean hasRemainingTokens = completedServicePlace
				.hasQueuedTokens(token.color);
		boolean threadIsStillRunning = (!forcedPreemption)
				&& hasRemainingTokens;

		// prepare the next token in the completed service place
		Token nextTokenInPlace = null;
		if (tokenIsProcessed) {
			waitingTokens.remove(token);
			tokensData.remove(tokenData);

			if (hasRemainingTokens) {
				nextTokenInPlace = new Token(completedServicePlace, token.color);

				waitingTokens.addFirst(nextTokenInPlace);
			}
		} else {
			// remove the token to the end
			waitingTokens.remove(token);
			waitingTokens.addLast(token);

			nextTokenInPlace = token;
		}

		// handle any remaining tokens if we have some quantum still left
		// before forced context switching
		if (threadIsStillRunning) {
			tokensData.put(nextTokenInPlace, new TokenData());

			scheduleNextToken(completedServicePlace, token.color,
					nextTokenInPlace, 0, serverIndex);
			return true;
		}

		threadExecutionTimes[serverIndex] = 0;

		if (waitingTokens.size() == 0) {
			// no place has any work; job done for now
			serversAvailability[serverIndex] = true;
			return true;
		}

		// now we have done a context switch; we will add the cost of the
		// context
		// switch to the next token

		// handle the waiting queuing places
		Token nextToken = waitingTokens.getFirst();
		scheduleNextToken((QPlace) nextToken.place, nextToken.color, nextToken,
				contextSwitchCost, serverIndex);

		return tokenIsProcessed;
	}

	@Override
	public Queue clone(SimQPNConfiguration configuration, Place[] places) {
		ThreadSchedulingQueue clone = null;
		try {
			clone = new ThreadSchedulingQueue(id, xmlId, name, queueDiscip,
					numServers, threadQuantum, contextSwitchCost);
			clone.setParameters(this, configuration, places);
		} catch (SimQPNException e) {
			log.info(e);
		}
		return clone;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * No actions necessary for IS queue. Empty method body.
	 */
	@Override
	public void updateEvents(Executor executor) {
	}

	@Override
	public double getLookahead(QPlace qpl, int color) {
		// TODO implement
		return 0;
	}
}
