package de.tud.cs.simqpn.kernel.entities.queue;

/**
 *  Supported Queueing Disciplines:
 *
 */
public enum QueuingDiscipline {
	IS(false),
	FCFS(true),
	PS(true),
	THREAD_SCHEDULER(true);

	private boolean hasServers = false;
	
	private QueuingDiscipline(boolean hasServers) {
		this.hasServers = hasServers;
	}
	
	public boolean hasServers() {
		return hasServers;
	}
}
