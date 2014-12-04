/* ==============================================
 * QPME : Queueing Petri net Modeling Environment
 * ==============================================
 *
 * (c) Copyright 2003-2011, by Samuel Kounev and Contributors.
 * 
 * Project Info:   http://descartes.ipd.kit.edu/projects/qpme/
 *                 http://www.descartes-research.net/
 *    
 * All rights reserved. This software is made available under the terms of the 
 * Eclipse Public License (EPL) v1.0 as published by the Eclipse Foundation
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * This software is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the Eclipse Public License (EPL)
 * for more details.
 *
 * You should have received a copy of the Eclipse Public License (EPL)
 * along with this software; if not visit http://www.eclipse.org or write to
 * Eclipse Foundation, Inc., 308 SW First Avenue, Suite 110, Portland, 97204 USA
 * Email: license (at) eclipse.org 
 *  
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *                                
 * =============================================
 *
 * Original Author(s):  Jürgen Walter
 * Contributor(s):
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2013/??/??  Jürgen Walter     Created.
 * 
 */
package de.tud.cs.simqpn.kernel.entities;

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.queue.Queue;
import de.tud.cs.simqpn.kernel.entities.stats.QueueStats;

/**
 * The Net class holds all entities of a Queuing Petri Net(QPN).
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
	 * Constructor.
	 */
	public Net() {
	};

	/**
	 * Extended constructor which copies some values from the parameters. This
	 * constructor is part of the clone function.
	 * 
	 * @param originalNet
	 *            the net from which we copy attributes
	 * @param configuration
	 *            the configuration for simulation
	 * @see #clone(SimQPNConfiguration)
	 */
	private Net(Net originalNet, SimQPNConfiguration configuration) {
		if (xmlConfigurationName != null) {
			this.xmlConfigurationName = originalNet.xmlConfigurationName;
		}
		this.numPlaces = originalNet.numPlaces;
		this.numTransitions = originalNet.numTransitions;
		this.numQueues = originalNet.numQueues;
		this.numProbes = originalNet.numProbes;
		this.places = new Place[originalNet.numPlaces];
		this.transitions = new Transition[originalNet.numTransitions];
		this.queues = new Queue[originalNet.numQueues];
		if (originalNet.numProbes > 0) {
			this.probes = new Probe[originalNet.numProbes];
		}
	}

	/**
	 * Clones this net.
	 * 
	 * @param configuration
	 *            the configuration for simulation
	 * @return a clone of the net
	 */
	public Net clone(SimQPNConfiguration configuration) {
		Net clone = null;
		try {
			clone = new Net(this, configuration);
			clone.finishCloning(this, configuration);
			//log.info(this.toString());
		} catch (SimQPNException e) {
			log.error("Error during net cloning", e);
		}
		return clone;
	}

	/**
	 * Finishes cloning of a net.
	 * 
	 * @param originalNet
	 *            the net from which we copy entities
	 * @param configuration
	 *            the configuration for simulation
	 * @see #clone(SimQPNConfiguration)
	 * @throws SimQPNException
	 *             if errors in clone functions of places or probes
	 */
	private void finishCloning(Net originalNet,
			SimQPNConfiguration configuration) throws SimQPNException {
		for (int i = 0; i < originalNet.numQueues; i++) {
			this.queues[i] = originalNet.queues[i].clone(configuration, places);
		}
		for (int i = 0; i < originalNet.numPlaces; i++) {
			this.places[i] = originalNet.places[i].clone(this.queues,
					this.transitions, configuration);
		}

		setQueueToPlacePointers(originalNet, configuration);

		for (int i = 0; i < originalNet.numTransitions; i++) {
			this.transitions[i] = originalNet.getTrans(i).clone(this.places);
		}

		setPlaceToTransitionPointers(originalNet);

		for (int i = 0; i < originalNet.numProbes; i++) {
			this.probes[i] = new Probe(originalNet.probes[i], configuration,
					this.places);
		}
		
		this.initializeProbes();
	}

	/**
	 * Sets pointers from places to transitions according an existing net.
	 * 
	 * @param originalNet
	 *            the net of which we copy links
	 */
	private void setPlaceToTransitionPointers(Net originalNet) {
		for (Place place : places) {
			for (int i = 0; i < originalNet.getPlace(place.id).inTrans.length; i++) {
				Transition originalInTransition = originalNet
						.getPlace(place.id).inTrans[i];
				place.inTrans[i] = transitions[originalInTransition.id];
			}
			for (int i = 0; i < originalNet.getPlace(place.id).outTrans.length; i++) {
				Transition originalOutTrans = originalNet.getPlace(place.id).outTrans[i];
				place.outTrans[i] = transitions[originalOutTrans.id];
			}
		}
	}

	/**
	 * Sets pointers from queue to places according an existing net.
	 * 
	 * @param originalNet
	 *            the net of which we copy links
	 * @param configuration
	 *            the configuration for simulation run
	 */
	private void setQueueToPlacePointers(Net originalNet,
			SimQPNConfiguration configuration) {
		for (Queue queue : this.queues) {
			Queue originalQueue = originalNet.getQueue(queue.id);
			if (originalQueue.qPlaces != null) {
				for (int i = 0; i < originalQueue.qPlaces.length; i++) {
					queue.addQPlace((QPlace) places[originalQueue.qPlaces[i].id]);
				}
			}
			if (originalQueue.getQueueStats() != null) {
				try {
					queue.setQueueStats(new QueueStats(originalQueue
							.getQueueStats().id,
							originalQueue.getQueueStats().name, originalQueue
									.getQueueStats().numColors, originalQueue
									.getQueueStats().statsLevel, originalQueue
									.getQueueStats().queueDiscip, originalQueue
									.getQueueStats().numServers, queue,
							configuration));
				} catch (SimQPNException e) {
					log.error("", e);
				}
			}

		}
	}

	/**
	 * Returns a string representation of this net.
	 * @return a string representation of this net
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		int verbosityLevel = 2;
		sb.append("\n");
		for (Place place : places) {
			sb.append(place.name + "\t");
			for (int i = 0; i < place.colors.length; i++) {
				sb.append("[" + place.colors[i] + "] " + place.tokenPop[i]);
			}
			if(verbosityLevel == 1){
				sb.append("\t\t"+place.getClass());					
			}
			if(verbosityLevel == 2){
				if((""+place.getClass()).endsWith("QPlace")){
					sb.append("\t"+((QPlace)place).queue.name);															
				}
			}

			sb.append("\n");
		}
		// sb.append("\n");
		for (Queue queue : queues) {
			sb.append(queue.name + " ");
			// sb.append(queue.getClass().getName().split(".")[queue.getClass().getName().split(".").length-1]+" ");
			sb.append("(" + queue.getClass().toString().split("queue.")[1]
					+ ")");
			// sb.append(queue.totalMaxPopulation+" "+queue.maxEpochPopulation+" | "+queue.maxPopulationAtRisingStart
			// +" < "+ queue.totalMaxPopulation+ " " +queue.cntConsRisingEpoch);
			if(verbosityLevel == 2){
				for(QPlace qPlace:queue.qPlaces){
					sb.append("\t"+qPlace.name);
				}
			}

			if (queue.getQueueStats() != null) {
				sb.append("(stats) ");
			}
			sb.append("\n");
		}
		sb.append("\n");
		for (Transition trans : transitions) {
			sb.append(trans.name);
			for (Place inPlace : trans.inPlaces) {
				sb.append("\t" + inPlace.name + " ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * Returns queue according to id.
	 * @param id	the internal id for the queue
	 * @return	queue according to id.
	 */
	public Queue getQueue(int id) {
		return queues[id];
	}

	/**
	 * Returns place according to id.
	 * @param id	the internal id for the place
	 * @return	place according to id.
	 */
	public Place getPlace(int id) {
		return places[id];
	}

	/**
	 * Returns transition according to id.
	 * @param id	the internal id for the transition
	 * @return	transition according to id.
	 */
	public Transition getTrans(int id) {
		return transitions[id];
	}

	/**
	 * Returns probe according to id.
	 * @param id	the internal id for the probe
	 * @return	probe according to id.
	 */
	public Probe getProbe(int id) {
		return probes[id];
	}

	/**
	 * Initialization of probes
	 * 
	 * @throws SimQPNException
	 *             if instrumentation of a probe fails
	 */
	public void initializeProbes() throws SimQPNException {
		for (int pr = 0; pr < getNumProbes(); pr++) {
			probes[pr].instrument();
		}
	}

	/**
	 * Returns the number of places of this net.
	 * @return the number of places of this net
	 */
	public int getNumPlaces() {
		return numPlaces;
	}

	/**
	 * Returns the number of transitions of this net.
	 * @return the number of transitions of this net
	 */
	public int getNumTrans() {
		return numTransitions;
	}

	/**
	 * Returns the number of queues of this net.
	 * @return the number of queues of this net
	 */
	public int getNumQueues() {
		return numQueues;
	}

	/**
	 * Returns the number of probes of this net.
	 * @return the number of probes of this net
	 */
	public int getNumProbes() {
		return numProbes;
	}

	/**
	 * Set transitions for this net.
	 * @param transitions	the transitions of this net
	 */
	public void setTransitions(Transition[] transitions) {
		this.transitions = transitions;
		this.numTransitions = transitions.length;
	}

	/**
	 * Returns the transitions of this net.
	 * @return the transitions of this net
	 */
	public Transition[] getTransitions() {
		return transitions;
	}

	/**
	 * Sets the places for net.
	 * @param places	the places of the net.
	 */
	public void setPlaces(Place[] places) {
		this.places = places;
		this.numPlaces = places.length;
	}

	/**
	 * Returns the places of this net.
	 * @return the places of this net
	 */
	public Place[] getPlaces() {
		return places;
	}
	
	/**
	 * Sets the queues for this net.
	 * @param queues	the new queues array
	 */
	public void setQueues(Queue[] queues) {
		this.queues = queues;
		this.numQueues = queues.length;
	}

	/**
	 * Returns the queues of this net.
	 * @return the queues of this net
	 */
	public Queue[] getQueues() {
		return queues;
	}

	/**
	 * Sets the probes for this net.
	 * @param queues	the new probes array
	 */
	public void setProbes(Probe[] probes) {
		this.probes = probes;
		this.numProbes = probes.length;
	}

	/**
	 * Returns the queues of this net.
	 * @return the queues of this net
	 */
	public Probe[] getProbes() {
		return probes;
	}

	/**
	 * Returns the name of the configuration in xml files.
	 * @return the name of the configuration in xml files
	 */
	public String getConfigurationName() {
		return xmlConfigurationName;
	}

	/**
	 * Sets the name of configuration in xml files.
	 * @param configurationName	name of configuration within xml files
	 */
	public void setConfigurationName(String configurationName) {
		this.xmlConfigurationName = configurationName;
	}

	public void setNumProbes(int numProbes) {
		this.numProbes = numProbes;
	}

}
