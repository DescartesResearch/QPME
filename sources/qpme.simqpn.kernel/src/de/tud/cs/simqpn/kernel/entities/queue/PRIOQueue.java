package de.tud.cs.simqpn.kernel.entities.queue;

import java.util.Comparator;
import java.util.PriorityQueue;

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.Token;
import de.tud.cs.simqpn.kernel.executor.Executor;

public class PRIOQueue extends Queue {
	private static Logger log = Logger.getLogger(PRIOQueue.class);
	//TODO test if statistic collection works for PRIO

	/** List of tokens waiting for service (waiting area of the queue).*/
	private final PriorityQueue<Token> priorityQueue; 
	
	private int numBusyServers;

	public PRIOQueue(int id, String xmlID, String name,
			QueuingDiscipline queueDiscipline, int numServers) {
		super(id, xmlID, name, queueDiscipline, numServers);
		this.numBusyServers = 0;
		Comparator<Token> tokenComparator = new TokenPrioComparator();
		this.priorityQueue = new PriorityQueue<Token>(2, tokenComparator);
	}

	@Override
	public Queue clone(SimQPNConfiguration configuration, Place[] places) {
		log.warn("Clone for PRIO queueing strategy not fully not tested");
		PRIOQueue queue;
		try {
			queue = (PRIOQueue) super.clone();
			return queue;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void addTokens(QPlace queueingPlace, int color, int count,
			Token[] tokensToBeAdded, Executor executor) throws SimQPNException {
		super.addTokens(queueingPlace, color, count, tokensToBeAdded, executor);
		int n = 0;
		while (n < count && numBusyServers < numServers) {
			// Schedule service completion event
			double servTime = queueingPlace.randServTimeGen[color].nextDouble();
			if (servTime < 0)
				servTime = 0;
			Token tk = (tokensToBeAdded != null) ? tokensToBeAdded[n]
					: new Token(queueingPlace, color);
			tk.arrivTS = executor.getClock();
			executor.scheduleEvent(servTime, this, tk);
			numBusyServers++;
			n++;
			// Update Stats
			if (queueingPlace.statsLevel >= 3)
				queueingPlace.qPlaceQueueStats.updateDelayTimeStats(color, 0,
						executor.getConfiguration());
		}
		while (n < count) {
			// Place the rest of the tokens in the priorityQueue
			Token tk = (tokensToBeAdded != null) ? tokensToBeAdded[n]
					: new Token(queueingPlace, color);
			tk.arrivTS = executor.getClock();
			priorityQueue.add(tk);
			n++;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * No actions necessary for this queue type. Empty method body.
	 */
	@Override
	public void updateEvents(Executor executor) {
	};

	@Override
	public double getLookahead(QPlace qpl, int color) {
		return 0;
	}

	@Override
	public void completeService(Token token, Executor executor)
			throws SimQPNException {
		super.completeService(token, executor);
		if (!priorityQueue.isEmpty()) {
			Token tk = priorityQueue.remove();
			QPlace qPl = (QPlace) tk.place;
			double servTime = qPl.randServTimeGen[tk.color].nextDouble();
			if (servTime < 0) {
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
	}
	
	private class TokenPrioComparator implements Comparator<Token> {

		@Override
		public int compare(Token t1, Token t2) {
			if (((QPlace) t1.place).getPriorities()[t1.color] > ((QPlace) t2.place).getPriorities()[t2.color]){
				return -1;
			}else if (((QPlace) t1.place).getPriorities()[t1.color] < ((QPlace) t2.place).getPriorities()[t2.color]){
				return 1;
			}else{
				return 0;
			}
		}
	}

}
