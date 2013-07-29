package de.tud.cs.simqpn.kernel.entities.queue;

/**
 *  Supported queuing disciplines for queues.
 */
public enum QueuingDiscipline {
	IS(false),
	FCFS(true),
	PS(true);

	private boolean hasServers;
	
	private QueuingDiscipline(boolean hasServers) {
		this.hasServers = hasServers;
	}
	
	public boolean hasServers() {
		return hasServers;
	}
}
