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

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.Token;
import de.tud.cs.simqpn.kernel.executor.Executor;
import de.tud.cs.simqpn.kernel.executor.QueueEvent;

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
	private static Logger log = Logger.getLogger(ISQueue.class);

	public ISQueue(int id, String xmlId, String name,
			QueuingDiscipline queueDiscip, int numServers)
			throws SimQPNException {
		super(id, xmlId, name, queueDiscip, numServers);
	}

	@Override
	public Queue clone(SimQPNConfiguration configuration, Place[] places) {
		ISQueue clone = null;
		try {
			clone = new ISQueue(id, xmlId, name, queueDiscip, numServers);
			clone.setParameters(this, configuration, places);
			// IS specific settings
		} catch (SimQPNException e) {
			log.error("", e);
		}
		return clone;
	}

	@Override
	public void addTokens(QPlace qPl, int color, int count,
			Token[] tokensToBeAdded, Executor executor) throws SimQPNException {
		super.addTokens(qPl, color, count, tokensToBeAdded, executor);

		// Schedule service completion events
		for (int i = 0; i < count; i++) {
			double servTime = qPl.randServTimeGen[color].nextDouble();
			if (servTime < 0)
				servTime = 0;
			Token tk = (tokensToBeAdded != null) ? tokensToBeAdded[i]
					: new Token(qPl, color);
			tk.arrivTS = executor.getClock();
			executor.scheduleEvent(servTime, this, tk);
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
	 * Always true
	 */
	@Override
	public boolean areEventsUpToDate() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getLookahead(QPlace qpl, int color_id) {
		return 0;
	}

	/**
	 * Nothing to do
	 */
	@Override
	public void onQueueEventScheduled(QueueEvent queueEvent) {
	};
	
	/**
	 * Empty method body.
	 */
	@Override
	public void updateEvents(Executor executor) throws SimQPNException {};

}
