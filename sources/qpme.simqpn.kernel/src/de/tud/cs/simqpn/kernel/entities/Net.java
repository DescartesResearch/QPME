package de.tud.cs.simqpn.kernel.entities;

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.queue.Queue;

/**
 * The Net class holds all entities of a QPN 
 * @author Jürgen Walter
 *
 */
public class Net {

	private static Logger log = Logger.getLogger(Net.class);

	private String xmlConfigurationName;

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
	public Net() {};
	
	/**
	 * Constructor which copies some values from the parameters. This constructor is part of
	 * the clone function
	 * 
	 * @param net
	 * @param configuration
	 * @see #clone()
	 * @throws SimQPNException
	 */
	private Net(Net net, SimQPNConfiguration configuration)
			throws SimQPNException {
		this.setConfigurationName(net.xmlConfigurationName);
		this.numPlaces = net.numPlaces;
		this.numTransitions = net.numTransitions;
		this.numQueues = net.numQueues;
		this.numProbes = net.numProbes;
		this.places = new Place[net.numPlaces];
		this.transitions = new Transition[net.numTransitions];
		this.queues = new Queue[net.numQueues];
		this.probes = new Probe[net.numProbes];
	}

	/**
	 * Clones the Net instance
	 * 
	 * @param configuration
	 * @return
	 */
	public Net clone(SimQPNConfiguration configuration) {
		Net clone = null;
		try {
			clone = new Net(this, configuration);
			clone.finishCloning(this, configuration);
		} catch (SimQPNException e) {
			log.error("Error during net cloning", e);
		}
		return clone;
	}

	/**
	 * Finishes cloning of a net
	 * 
	 * @param net
	 * @param configuration
	 * @see #clone()
	 * @throws SimQPNException
	 */
	private void finishCloning(Net net, SimQPNConfiguration configuration)
			throws SimQPNException {
		double clock = 0;
		for (int i = 0; i < net.numPlaces; i++) {
			if (net.places[i].getClass().toString().endsWith("QPlace")) {
				this.places[i] = new QPlace((QPlace) net.places[i], queues,
						configuration);
			} else {
				this.places[i] = new Place(net.places[i], configuration);
			}
		}
		for (int i = 0; i < net.numTransitions; i++) {
			this.transitions[i] = new Transition(net.transitions[i], places,
					configuration);
		}
		for (int i = 0; i < net.numQueues; i++) {
			this.queues[i] = net.queues[i].clone(configuration, places);
			// = new Queue(net.queues[i], configuration, places);
		}

		for (int i = 0; i < net.numPlaces; i++) {
			if (net.places[i].getClass().toString().endsWith("QPlace")) {
				((QPlace) this.places[i]).finishCloning((QPlace) net.places[i],
						this.queues, configuration);
			}
		}

		for (int i = 0; i < net.numProbes; i++) {
			this.probes[i] = new Probe(net.probes[i], configuration,
					this.places);
		}

		/** finish cloning */
		for (int i = 0; i < net.numPlaces; i++) {
			this.places[i].finishCloning(net.places[i], this.transitions);
		}
		for (int i = 0; i < net.numTransitions; i++) {
			this.transitions[i].finishCloning(net.transitions[i], this.places);
		}

		for (int i = 0; i < numProbes; i++)
			probes[i].init();
		for (int i = 0; i < numPlaces; i++)
			places[i].init(clock);
		for (int i = 0; i < numTransitions; i++)
			transitions[i].init();
		for (int i = 0; i < numQueues; i++)
			queues[i].init(configuration);

	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		for (Place place : places) {
			sb.append(place.name + " ");
		}
		sb.append("\n");
		for (Queue queue : queues) {
			sb.append(queue.name + "("+queue.queueStats+") ");
		}
		sb.append("\n");
		for (Transition trans : transitions) {
			sb.append(trans.name + " ");
		}
		return sb.toString();
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

	public void setConfiguration(String configuration) {
		this.setConfigurationName(configuration);
	}

	public String getConfigurationName() {
		return xmlConfigurationName;
	}

	public void setConfigurationName(String configurationName) {
		this.xmlConfigurationName = configurationName;
	}

}
