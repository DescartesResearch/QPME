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
 *  2014/09/08  Jürgen Walter     Created
 * 
 */
package de.tud.cs.simqpn.kernel.executor.parallel.decomposition;

import java.util.ArrayList;
import java.util.List;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.Transition;
import de.tud.cs.simqpn.kernel.entities.queue.Queue;
import de.tud.cs.simqpn.kernel.executor.parallel.LP;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;

public class MinimumRegionDecomposer {

	/**
	 * Decomposes the net into minimum regions as described by Chiola and
	 * Ferscha [1].
	 * 
	 * The basic idea is to merge places into an LP if they share an incoming
	 * transition. Note: This function does not check recursively whether the
	 * "second" place (which is merged whith the "first" place) shares one
	 * incoming transition with another place.
	 * 
	 * [1] Chiola, G. & Ferscha, A. Ajmone Marsan, M. (Ed.) Distributed
	 * simulation of timed Petri nets: Exploiting the net structure to obtain
	 * efficiency Application and Theory of Petri Nets 1993, Springer Berlin
	 * Heidelberg, 1993, 691, 146-165
	 * 
	 * @return
	 */
	protected static List<LP> decomposeNetIntoMinimumRegions(Net net,
			SimQPNConfiguration configuration,
			SimulatorProgress progressMonitor, int verbosityLevel) {
		int numPlaces = net.getNumPlaces();
		int numTrans = net.getNumTrans();
		Place[] places = net.getPlaces();
		// TODO check if no probes in newly created nets

		/* already assigned to LPs */
		boolean[] alreadyAssignedTransitions = new boolean[numTrans];
		boolean[] alreadyAssignedPlaces = new boolean[numPlaces];
		List<LP> minimumRegions = new ArrayList<LP>();
		List<Integer> transitionIDs = null;
		List<Integer> placeIDs = null;

		for (int placeID = 0, lpID = 0; placeID < numPlaces; placeID++) {
			if (alreadyAssignedPlaces[placeID] == true) {
				continue;
			}
			placeIDs = new ArrayList<Integer>();
			placeIDs.add(placeID);
			alreadyAssignedPlaces[placeID] = true;

			transitionIDs = new ArrayList<Integer>();

			for (Transition transition : places[placeID].outTrans) {
				if (alreadyAssignedTransitions[transition.id] == false) {
					// TODO make recursive
					transitionIDs.add(transition.id);
					alreadyAssignedTransitions[transition.id] = true;
					if (transition.inPlaces.length > 1) {
						for (Place p : transition.inPlaces) {
							int placeId = p.id;
							if (!alreadyAssignedPlaces[placeId]) {
								placeIDs.add(placeId);
								alreadyAssignedPlaces[placeId] = true;
								for (Transition t2 : places[placeId].outTrans) {
									if (alreadyAssignedTransitions[t2.id] == false) {
										transitionIDs.add(t2.id);
										alreadyAssignedTransitions[t2.id] = true;
									}
								}
							}
						}
					} else {
						continue;
					}
				}
			}
			
			minimumRegions.add(createMinimumRegion(net, configuration,
					progressMonitor, verbosityLevel, transitionIDs, placeIDs,
					lpID));
			lpID++;
		}

		// Set LP id to places and transitions
		for (LP lp : minimumRegions) {
			lp.setExecutorToEntities();
		}
		return minimumRegions;
	}

	private static LP createMinimumRegion(Net net,
			SimQPNConfiguration configuration,
			SimulatorProgress progressMonitor, int verbosityLevel,
			List<Integer> transitionIDs, List<Integer> placeIDs, int lpID) {
		// from list to net elements
		Transition[] transitionsLP = new Transition[transitionIDs.size()];
		for (int j = 0; j < transitionIDs.size(); j++) {
			transitionsLP[j] = net.getTrans(transitionIDs.get(j));
		}
		Place[] placesLP = new Place[placeIDs.size()];
		for (int j = 0; j < placeIDs.size(); j++) {
			placesLP[j] = net.getPlace(placeIDs.get(j));
			// Probe[][] probeInstrumentations =
			// placesLP[j].probeInstrumentations;
			// System.out.println("PROBE PROBE PROBE "+placesLP[j].name+
			// " has "+probeInstrumentations.length);
			// System.out.println("\tPROBE PROBE PROBE "+probeInstrumentations.length);
			// for(int k=0; k<probeInstrumentations.length; k++){
			// System.out.println("\t\tPROBE PROBE PROBE "+probeInstrumentations[k].length);
			// for(int l=0; l<probeInstrumentations[k].length; l++){
			// Probe probe = probeInstrumentations[k][l];
			// System.out.println("\t\t\tPROBE PROBE PROBE "+probe.name+
			// " + id "+probe.id);
			// }
			// }
		}

		/** get relevant queues */
		List<Queue> queueList = new ArrayList<Queue>();
		for (Place p : placesLP) {
			if (p instanceof QPlace) {
				Queue queue = ((QPlace) p).queue;
				queueList.add(queue);
			}
		}

		return new LP(placesLP, transitionsLP,
				queueList.toArray(new Queue[queueList.size()]), configuration,
				progressMonitor, lpID, verbosityLevel);
	}

}
