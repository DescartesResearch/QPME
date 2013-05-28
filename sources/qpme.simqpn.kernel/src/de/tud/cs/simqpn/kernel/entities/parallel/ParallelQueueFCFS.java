package de.tud.cs.simqpn.kernel.entities.parallel;

import java.util.List;

import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.Queue;
import de.tud.cs.simqpn.kernel.entities.Token;
import de.tud.cs.simqpn.kernel.executor.Executor;

/**
 * Queue with lookahead via FutureList
 * 
 * @author D
 * 
 */
public class ParallelQueueFCFS extends Queue {

	/**
	 * Saves the previously calculated service times for incoming events
	 */
	List<Double>[] arrayOfFutureLists;

	public ParallelQueueFCFS(int id, String xmlId, String name, int queueDiscip,
			int numServers) throws SimQPNException {
		super(id, xmlId, name, queueDiscip, numServers);
	}
	
	
	double getLookahead(double color){
		//arrayOfFutureLists[color].get(0);
		return 0;
	}

	double getLookahead(int color){
		return arrayOfFutureLists[color].get(0);
	}

	/**
	 * Method addTokens - deposits N tokens of particular color
	 * 
	 * JUERGEN: calls scheduleEvent()
	 * 
	 * @param color
	 *            - color of tokens
	 * @param count
	 *            - number of tokens to deposit
	 * @param tokensToBeAdded
	 *            - individual tokens (if tracking = true)
	 * @return
	 * @exception
	 */
	public void addTokens(QPlace qPl, int color, int count,
			Token[] tokensToBeAdded, Executor executor) throws SimQPNException {

		if (queueDiscip == FCFS) {
			int n = 0;
			while (n < count && numBusyServers < numServers) {
				// Schedule service completion event

				// calculate the service time for the next event
				arrayOfFutureLists[color].add(qPl.randServTimeGen[color]
						.nextDouble());
				double servTime = arrayOfFutureLists[color].remove(0);
				if (servTime < 0)
					servTime = 0;
				Token tk = (tokensToBeAdded != null) ? tokensToBeAdded[n]
						: new Token(qPl, color);
				tk.arrivTS = executor.getClock();
				executor.scheduleEvent(executor.getClock() + servTime, this, tk);
				numBusyServers++;
				n++;
				// Update Stats
				if (qPl.statsLevel >= 3)
					qPl.qPlaceQueueStats.updateDelayTimeStats(color, 0,
							executor);
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

	}

}
