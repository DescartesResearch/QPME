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

public class FCFSQueue extends Queue {

	private static Logger log = Logger.getLogger(FCFSQueue.class);
	/** Number of currently busy servers. */
	public int numBusyServers;
	/** List of tokens waiting for service (waiting area of the queue). */
	public LinkedList<Token> waitingLine;


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
	}

	@Override
	public Queue clone(SimQPNConfiguration configuration, Place[] places) {
		FCFSQueue clone = null;
		try {
			clone = new FCFSQueue(id, xmlId, name, queueDiscip, numServers);
			clone.setParameters(this, configuration, places);
			
			clone.numBusyServers = this.numBusyServers;
			clone.waitingLine = new LinkedList<Token>();
			if (this.waitingLine != null) {
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

			//FCFS specific settings
		} catch (SimQPNException e) {
			log.error("", e);
		}
		return clone;
	}

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
			if (qPl.statsLevel >= 3)
				qPl.qPlaceQueueStats.updateDelayTimeStats(color, 0,
						executor.getConfiguration());
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
		if (waitingLine.size() > 0) {
			Token tk = waitingLine.removeFirst();
			QPlace qPl = (QPlace) tk.place;
			double servTime = qPl.randServTimeGen[tk.color].nextDouble();
			if (servTime < 0)
				servTime = 0;
			executor.scheduleEvent(servTime, this, tk);
			// Update stats
			if (qPl.statsLevel >= 3)
				qPl.qPlaceQueueStats.updateDelayTimeStats(tk.color,
						executor.getClock() - tk.arrivTS,
						executor.getConfiguration());
		} else
			numBusyServers--;
	}

}
