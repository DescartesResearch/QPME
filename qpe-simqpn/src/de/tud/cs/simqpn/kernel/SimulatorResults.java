package de.tud.cs.simqpn.kernel;

public class SimulatorResults {

	private final Place[] places;
	private final Queue[] queues;

	public SimulatorResults(Place[] places, Queue[] queues) {
		this.places = places;
		this.queues = queues;
	}

	public Place[] getPlaces() {
		return this.places;
	}

	public Queue[] getQueues() {
		return this.queues;
	}
}
