package de.tud.cs.simqpn.kernel.entities.queue;

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.Token;
import de.tud.cs.simqpn.kernel.executor.Executor;

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
			super.finishCloning(clone, configuration, places);
			//IS specific settings
		} catch (SimQPNException e) {
			log.error("", e);
		}
		return clone;
	}

	@Override
	public void addTokens(QPlace qPl, int color, int count, Token[] tokensToBeAdded, Executor executor) throws SimQPNException {	
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
	
	@Override
	public void completeService(Token token, Executor executor)
			throws SimQPNException {
		super.completeService(token, executor);
		// Nothing to do
	}
}
