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
 * Original Author(s):  J?rgen Walter
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2013/03/20  J?rgen Walter     Created.
 * 
 */
package de.tud.cs.simqpn.kernel.entities.queue;

import java.util.LinkedList;
import java.util.Random;

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.Token;
import de.tud.cs.simqpn.kernel.executor.Executor;

/**
 * This class implements RANDOM scheduling.This queuing strategy processes
 * incoming tokens in random order.
 * 
 * @author J?rgen Walter
 */
public class RANDOMQueue extends Queue {

	private static Logger log = Logger.getLogger(Queue.class);
	private int numBusyServers;
	private LinkedList<Token> waitingLine;

	public RANDOMQueue(int id, String xmlID, String name,
			QueueingDiscipline queueDiscipline, int numServers) {
		super(id, xmlID, name, queueDiscipline, numServers);
		this.numBusyServers = 0;
		this.waitingLine = new LinkedList<Token>();
	}

	@Override
	public Queue clone(SimQPNConfiguration configuration, Place[] places) {
		RANDOMQueue clone = new RANDOMQueue(id, xmlId, name, queueDiscip,
				numServers);
		clone.setParameters(this, configuration, places);
		return clone;
	}

	@Override
	protected void setParameters(Queue queue,
			SimQPNConfiguration configuration, Place[] placesOfNewNet) {
		super.setParameters(queue, configuration, placesOfNewNet);
		numBusyServers = ((RANDOMQueue) queue).numBusyServers;
		waitingLine = new LinkedList<Token>();
		if (((RANDOMQueue) queue).waitingLine.size() > 0) {
			log.warn("Cloning of non empty RANDOM queue is currently not supportet");
			// TODO soloution should look like
			// for(Token token:((RANDOMQueue)queue).waitingLine){
			// waitingLine.add(token.clone(placesOfNewNet));
			// }
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * No actions necessary for this queue. Empty method body.
	 */
	@Override
	public double getLookahead(QPlace qpl, int color) {
		return 0;
	}

	@Override
	public void addTokens(QPlace qPl, int color, int count,
			Token[] tokensToBeAdded, Executor executor) throws SimQPNException {
		super.addTokens(qPl, color, count, tokensToBeAdded, executor);

		int n = 0;
		while (n < count && numBusyServers < numServers) {
			// Schedule service completion event
			double servTime = qPl.randServTimeGen[color].nextDouble(null, -1);
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
	}

	@Override
	public void completeService(Token token, Executor executor)
			throws SimQPNException {
		super.completeService(token, executor);
		if (waitingLine.size() > 0) {
			Random randomGenerator = new Random();
			int index = randomGenerator.nextInt(waitingLine.size());
			Token tk = (Token) waitingLine.remove(index);
			QPlace qPl = (QPlace) tk.place;
			double servTime = qPl.randServTimeGen[tk.color].nextDouble(null, -1);
			if (servTime < 0)
				servTime = 0;
			executor.scheduleEvent(servTime, this, tk);
			// Update stats
			if (qPl.statsLevel >= 3) {
				qPl.qPlaceQueueStats.updateDelayTimeStats(tk.color,
						executor.getClock() - tk.arrivTS,
						executor.getConfiguration());
			}
		} else {
			numBusyServers--;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * No actions necessary for this queue type. Empty method body.
	 */
	@Override
	public void updateEvents(Executor executor) {
	}

}
