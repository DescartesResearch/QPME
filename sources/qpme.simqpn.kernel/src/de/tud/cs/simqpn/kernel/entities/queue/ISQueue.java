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

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.Token;
import de.tud.cs.simqpn.kernel.executor.Executor;

/**
 * This class implements the Infinite Server (IS) scheduling strategy.
 * 
 * Queues with this scheduling strategy are often called delay resources or
 * delay servers.
 * 
 * This queuing strategy delays any number of entities for a period of time.
 * 
 */
public class ISQueue extends Queue {
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
	public ISQueue(int id, String xmlId, String name,
			QueueingDiscipline queueDiscip, int numServers){
		super(id, xmlId, name, queueDiscip, numServers);
	}

	@Override
	public Queue clone(SimQPNConfiguration configuration, Place[] places) {
		ISQueue clone = new ISQueue(id, xmlId, name, queueDiscip, numServers);
		clone.setParameters(this, configuration, places);
		// IS specific settings...
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
	 * @throws SimQPNException	inherited from queue, not relevant for ISQueue
	 */
	@Override
	public void addTokens(QPlace queuingPlace, int color, int count,
			Token[] tokensToBeAdded, Executor executor) throws SimQPNException{
		super.addTokens(queuingPlace, color, count, tokensToBeAdded, executor);

		// Schedule service completion events
		for (int i = 0; i < count; i++) {
			double servTime = queuingPlace.removeNextServiceTime(color);
			Token token = (tokensToBeAdded != null) ? tokensToBeAdded[i]
					: new Token(queuingPlace, color);
			token.arrivTS = executor.getClock();
			executor.scheduleEvent(servTime, this, token);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void completeService(Token token, Executor executor)
			throws SimQPNException {
		super.completeService(token, executor);
		// Nothing to do
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getLookahead(QPlace queuingPlace, int color) {
		return queuingPlace.getNextServiceTime(color);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * For infinite server scheduling strategy leaving or entering of new services has no effect on the queuing time of the queued events.
	 */
	@Override
	public void updateEvents(Executor executor) {};

}
