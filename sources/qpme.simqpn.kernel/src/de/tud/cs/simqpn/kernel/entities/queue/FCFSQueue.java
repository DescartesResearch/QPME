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

import java.util.LinkedList;

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.ProbeTimestamp;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.Token;
import de.tud.cs.simqpn.kernel.executor.Executor;

/**
 * This class implements the First-Come-First-Serve scheduling strategy.
 */
public class FCFSQueue extends Queue {

	private static Logger log = Logger.getLogger(FCFSQueue.class);
	/** Number of currently busy servers. */
	private int numBusyServers;
	/** List of tokens waiting for service (waiting area of the queue). */
	private LinkedList<Token> waitingLine;

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
	public FCFSQueue(int id, String xmlId, String name,
			QueuingDiscipline queueDiscip, int numServers) {

		super(id, xmlId, name, queueDiscip, numServers);

		this.numBusyServers = 0;
		this.waitingLine = new LinkedList<Token>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Queue clone(SimQPNConfiguration configuration, Place[] places) {
		FCFSQueue clone = new FCFSQueue(id, xmlId, name, queueDiscip,
				numServers);
		clone.setParameters(this, configuration, places);

		clone.numBusyServers = this.numBusyServers;
		if (!this.waitingLine.isEmpty()) {
			for (int i = 0; i < this.waitingLine.size(); i++) {
				Token token = this.waitingLine.get(i);
				ProbeTimestamp[] probeTimestamps = new ProbeTimestamp[token.probeData.length];
				for (int j = 0; j < this.waitingLine.get(i).probeData.length; j++) {
					probeTimestamps[j] = new ProbeTimestamp(
							token.probeData[j].probeId,
							token.probeData[j].timestamp);
				}
				Token tokenCopy = new Token(places[token.place.id],
						token.color, probeTimestamps);
				clone.waitingLine.add(tokenCopy);
			}
		}

		return clone;
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
	 * @throws SimQPNException	if error during place stats update
	 */
	@Override
	public void addTokens(QPlace queuingPlace, int color, int count,
			Token[] tokensToBeAdded, Executor executor) throws SimQPNException {
		super.addTokens(queuingPlace, color, count, tokensToBeAdded, executor);
		int n = 0;
		while (n < count && numBusyServers < numServers) {
			// Schedule service completion event
			double servTime = queuingPlace.removeNextServiceTime(color);
			Token tk = (tokensToBeAdded != null) ? tokensToBeAdded[n]
					: new Token(queuingPlace, color);
			tk.arrivTS = executor.getClock();
			executor.scheduleEvent(servTime, this, tk);
			numBusyServers++;
			n++;
			// Update Stats
			if (queuingPlace.statsLevel >= 3) {
				queuingPlace.qPlaceQueueStats.updateDelayTimeStats(color, 0,
						executor.getConfiguration());
			}
		}
		while (n < count) {
			// Place the rest of the tokens in the waitingLine
			Token tk = (tokensToBeAdded != null) ? tokensToBeAdded[n]
					: new Token(queuingPlace, color);
			tk.arrivTS = executor.getClock();
			waitingLine.addLast(tk);
			n++;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void completeService(Token token, Executor executor)
			throws SimQPNException {
		super.completeService(token, executor);
		if (waitingLine.size() > 0) {
			Token nextToken = waitingLine.removeFirst();
			QPlace queuingPlace = (QPlace) nextToken.place;
			double serviceTime = queuingPlace.removeNextServiceTime(nextToken.color);
			executor.scheduleEvent(serviceTime, this, nextToken);
			// Update stats
			if (queuingPlace.statsLevel >= 3)
				queuingPlace.qPlaceQueueStats.updateDelayTimeStats(nextToken.color,
						executor.getClock() - nextToken.arrivTS,
						executor.getConfiguration());
		} else {
			numBusyServers--;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getLookahead(QPlace qPl, int color) {
		return qPl.getNextServiceTime(color);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * No actions necessary for FCFS queue. Empty method body.
	 */
	@Override
	public void updateEvents(Executor executor) {
	}

}
