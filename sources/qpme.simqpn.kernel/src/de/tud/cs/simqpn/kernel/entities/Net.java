package de.tud.cs.simqpn.kernel.entities;

import de.tud.cs.simqpn.kernel.SimQPNException;

public class Net {
	// XML Configuration
	private String configuration;

	private Place[] places;
	private Transition[] transitions;
	private Queue[] queues;
	private Probe[] probes;

	private int numPlaces;
	private int numTransitions;
	private int numQueues;
	private int numProbes;

	/**
	 * Constructor
	 */
	public Net() {
	}

	public Queue getQueue(int id) {
		return queues[id];
	}

	public Place getPlace(int id) {
		return places[id];
	}

	public Transition getTrans(int id) {
		return transitions[id];
	}

	public Probe getProbe(int id) {
		return probes[id];
	}

	public void configureProbes() throws SimQPNException {
		for (int pr = 0; pr < getNumProbes(); pr++) {
			probes[pr].instrument();
		}
	}

	public int getNumPlaces() {
		return numPlaces;
	}

	public int getNumTrans() {
		return numTransitions;
	}

	public int getNumQueues() {
		return numQueues;
	}

	public int getNumProbes() {
		return numProbes;
	}

	public void setTransitions(Transition[] transitions) {
		this.transitions = transitions;
		this.numTransitions = transitions.length;
	}

	public Transition[] getTransitions() {
		return transitions;
	}

	public void setPlaces(Place[] places) {
		this.places = places;
		this.numPlaces = places.length;
	}

	public Place[] getPlaces() {
		return places;
	}

	public void setQueues(Queue[] queues) {
		this.queues = queues;
		this.numQueues = queues.length;
	}

	public Queue[] getQueues() {
		return queues;
	}

	public void setProbes(Probe[] probes) {
		this.probes = probes;
		this.numProbes = probes.length;
	}

	public Probe[] getProbes() {
		return probes;
	}

	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

}
