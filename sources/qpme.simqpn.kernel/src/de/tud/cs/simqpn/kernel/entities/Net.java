package de.tud.cs.simqpn.kernel.entities;

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.queue.Queue;
import de.tud.cs.simqpn.kernel.stats.QueueStats;

/**
 * The Net class holds all entities of a QPN
 * 
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
	public Net() {
	};

	/**
	 * Constructor which copies some values from the parameters. This
	 * constructor is part of the clone function
	 * 
	 * @param net
	 * @param configuration
	 * @see #clone()
	 * @throws SimQPNException
	 */
	private Net(Net net, SimQPNConfiguration configuration) {
		if (xmlConfigurationName != null) {
			this.xmlConfigurationName = net.xmlConfigurationName;
		}
		this.numPlaces = net.numPlaces;
		this.numTransitions = net.numTransitions;
		this.numQueues = net.numQueues;
		this.numProbes = net.numProbes;
		this.places = new Place[net.numPlaces];
		this.transitions = new Transition[net.numTransitions];
		this.queues = new Queue[net.numQueues];
		if (net.numProbes > 0) {
			this.probes = new Probe[net.numProbes];
		}
	}

	/**
	 * Clones the Net instance
	 * 
	 * @param configuration
	 * @return
	 */
	public Net clone(SimQPNConfiguration configuration) {
		//DEBUG	
		//System.out.println("original " + this +"\n");
		Net clone = null;
		try {
			clone = new Net(this, configuration);
			clone.finishCloning(this, configuration);
		} catch (SimQPNException e) {
			log.error("Error during net cloning", e);
		}
		//DEBUG 
		//System.out.println("clone " + clone);
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
		for (int i = 0; i < net.numQueues; i++) {
			this.queues[i] = net.queues[i].clone(configuration, places);
		}		
		for (int i = 0; i < net.numPlaces; i++) {
			this.places[i] = net.places[i].clone(this.queues, this.transitions,
					configuration);
		}
		
		setQueueToPlacePointers(net, configuration);

		for (int i = 0; i < net.numTransitions; i++) {
			this.transitions[i] = net.getTrans(i).clone(this.places);
		}
		
		setPlaceToTransitionPointers(net);

		for (int i = 0; i < net.numProbes; i++) {
			this.probes[i] = new Probe(net.probes[i], configuration,
					this.places);
		}
	}
	
	private void setPlaceToTransitionPointers(Net net){
		for(Place place: places){
			for(int i=0; i<net.getPlace(place.id).inTrans.length; i++){
				Transition original = net.getPlace(place.id).inTrans[i];
				place.inTrans[i] = transitions[original.id];
			}
			for(int i=0; i<net.getPlace(place.id).outTrans.length; i++){
				Transition original = net.getPlace(place.id).outTrans[i];
				place.outTrans[i] = transitions[original.id];
			}
		}
	}

	private void setQueueToPlacePointers(Net net,
			SimQPNConfiguration configuration) {
		for(Queue queue: this.queues){
			Queue original = net.getQueue(queue.id);	
			if (original.qPlaces != null) {
				for (int i = 0; i < original.qPlaces.length; i++) {
					try {
						queue.addQPlace((QPlace) places[original.qPlaces[i].id]);
					} catch (SimQPNException e) {
						log.error("", e);
					}
				}
			}
			if (original.queueStats != null) {
				try {
					queue.queueStats = new QueueStats(original.queueStats.id,
							original.queueStats.name, original.queueStats.numColors,
							original.queueStats.statsLevel,
							original.queueStats.queueDiscip,
							original.queueStats.numServers, queue, configuration);
				} catch (SimQPNException e) {
					log.error("", e);
				}
			}
			
		}
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		for (Place place : places) {
			sb.append(place.name + "\t");
			for(int i=0; i<place.colors.length; i++){
				sb.append("["+place.colors[i]+"] "+ place.tokenPop[i]);
			}
			sb.append("\n");
		}
		//sb.append("\n");
		for (Queue queue : queues) {
			sb.append(queue.name+" ");
			//sb.append(queue.getClass().getName().split(".")[queue.getClass().getName().split(".").length-1]+" ");
			sb.append("("+queue.getClass().toString().split("queue.")[1]+")");
			//sb.append(queue.totalMaxPopulation+" "+queue.maxEpochPopulation+" | "+queue.maxPopulationAtRisingStart +" < "+ queue.totalMaxPopulation+ " " +queue.cntConsRisingEpoch);				

			if(queue.queueStats != null){
				sb.append("(stats) ");
			}
			sb.append("\n");
		}
		sb.append("\n");
		for (Transition trans : transitions) {
			sb.append(trans.name);
			for(Place inPlace: trans.inPlaces){
				sb.append("\t"+inPlace.name + " ");
			}
			sb.append("\n");
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
