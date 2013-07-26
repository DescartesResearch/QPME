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
import java.util.List;

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.ProbeTimestamp;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.Token;
import de.tud.cs.simqpn.kernel.executor.Executor;
import de.tud.cs.simqpn.kernel.executor.QueueEvent;

/**
 * This class implements the First-Come-First-Serve scheduling strategy.
 */
public class FCFSQueue extends Queue {

	private static Logger log = Logger.getLogger(FCFSQueue.class);
	/** Number of currently busy servers. */
	private int numBusyServers;
	/** List of tokens waiting for service (waiting area of the queue). */
	private LinkedList<Token> waitingLine;
	
	private List<Double> futureList;


	/**
	 * Constructor
	 * 
	 * @param id
	 * @param xmlId
	 * @param name
	 * @param queueDiscip
	 * @param numServers
	 * @throws SimQPNException
	 */
	public FCFSQueue(int id, String xmlId, String name,
			QueuingDiscipline queueDiscip, int numServers)
			throws SimQPNException {

		super(id, xmlId, name, queueDiscip, numServers);

		this.numBusyServers = 0;
		this.waitingLine = new LinkedList<Token>();
		this.futureList = new LinkedList<Double>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Queue clone(SimQPNConfiguration configuration, Place[] places) {
		FCFSQueue clone = null;
		try {
			clone = new FCFSQueue(id, xmlId, name, queueDiscip, numServers);
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

			// FCFS specific settings
		} catch (SimQPNException e) {
			log.error("", e);
		}
		return clone;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addTokens(QPlace qPl, int color, int count,
			Token[] tokensToBeAdded, Executor executor) throws SimQPNException {
		super.addTokens(qPl, color, count, tokensToBeAdded, executor);
		int n = 0;
		while (n < count && numBusyServers < numServers) {
			// Schedule service completion event
			double servTime = qPl.randServTimeGen[color].nextDouble();
			if (servTime < 0)
				servTime = 0;
			Token tk = (tokensToBeAdded != null) ? tokensToBeAdded[n]
					: new Token(qPl, color);
			tk.arrivTS = executor.getClock();
			executor.scheduleEvent(servTime, this, tk);
			numBusyServers++;
			n++;
			// Update Stats
			if (qPl.statsLevel >= 3) {
				qPl.qPlaceQueueStats.updateDelayTimeStats(color, 0,
						executor.getConfiguration());
			}
		}
		while (n < count) {
			// Place the rest of the tokens in the waitingLine
			Token tk = (tokensToBeAdded != null) ? tokensToBeAdded[n]
					: new Token(qPl, color);
			tk.arrivTS = executor.getClock();
			waitingLine.addLast(tk);
			n++;
		}

		// ORIGINAL
		// int n = 0;
		// while (n < count && numBusyServers < numServers) {
		// // Schedule service completion event
		// double servTime = qPl.randServTimeGen[color].nextDouble();
		// if (servTime < 0) servTime = 0;
		// Token tk = (tokensToBeAdded != null) ? tokensToBeAdded[n] : new
		// Token(qPl, color);
		// tk.arrivTS = Simulator.clock;
		// Simulator.scheduleEvent(Simulator.clock + servTime, this, tk);
		// numBusyServers++; n++;
		// // Update Stats
		// if (qPl.statsLevel >= 3)
		// qPl.qPlaceQueueStats.updateDelayTimeStats(color, 0);
		// }
		// while (n < count) {
		// // Place the rest of the tokens in the waitingLine
		// Token tk = (tokensToBeAdded != null) ? tokensToBeAdded[n] : new
		// Token(qPl, color);
		// tk.arrivTS = Simulator.clock;
		// waitingLine.addLast(tk);
		// n++;
		// }
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void completeService(Token token, Executor executor)
			throws SimQPNException {
		super.completeService(token, executor);
		if (waitingLine.size() > 0) {
			Token tk = waitingLine.removeFirst();
			QPlace qPl = (QPlace) tk.place;
			double servTime;
			if(qPl.futurList.isEmpty()){
				servTime = qPl.randServTimeGen[tk.color].nextDouble();
				if (servTime < 0)
					servTime = 0;
			}else{
				servTime = qPl.futurList.get(0);
			}
			executor.scheduleEvent(servTime, this, tk);
			// Update stats
			if (qPl.statsLevel >= 3)
				qPl.qPlaceQueueStats.updateDelayTimeStats(tk.color,
						executor.getClock() - tk.arrivTS,
						executor.getConfiguration());
		} else {
			numBusyServers--;
		}
	}

	/**
	 * Always true for FCFS
	 */
	@Override
	public boolean areEventsUpToDate() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getLookahead(QPlace qPl, int color_id) {
		if(qPl.futurList.isEmpty()){
			double servTime = qPl.randServTimeGen[color_id].nextDouble();
			if (servTime < 0)
				servTime = 0;
			qPl.futurList.add(servTime);
		}
		return qPl.futurList.get(0);
	}

	/**
	 * Nothing to do for FCFS
	 */
	@Override
	public void onQueueEventScheduled(QueueEvent queueEvent) {
	}

	/**
	 * Empty method body.
	 */
	@Override
	public void updateEvents(Executor executor) throws SimQPNException {}

}
