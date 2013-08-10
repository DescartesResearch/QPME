package de.tud.cs.simqpn.kernel.executor.parallel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.Transition;
import de.tud.cs.simqpn.kernel.entities.queue.Queue;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;

public class NetDecomposer {
	
	private Net net;
	private SimQPNConfiguration configuration;
	private SimulatorProgress progressMonitor;
	
	public NetDecomposer(Net net, SimQPNConfiguration configuration, SimulatorProgress progressMonitor) {
		this.net = net;
		this.configuration = configuration;
		this.progressMonitor = progressMonitor;
	}
	
	/**
	 * Returns a decomposition of the (internal) {@link Net} into a logical
	 * process ({@link LP}) array
	 * 
	 * @return Array of logical processes
	 */
	public LP[] decomposeNetIntoLPs() {
		List<LP> listLPs = decomposeNetIntoMinimumRegionLPs();

		mergeLPsInLane(listLPs);

		setInPlaces(listLPs);

		setPredAndSuccessors(listLPs);

		// mergePlaceLPsIntoPredecessors(listLPs);

		// Modify transition ids
		int transCnt = 0;
		for (LP lp : listLPs) {
			for (Transition trans : lp.getTransitions()) {
				trans.id = transCnt++;
			}
		}

		return listLPs.toArray(new LP[listLPs.size()]);
	}

	private void setInPlaces(List<LP> listLPs) {
		for (LP lp : listLPs) {
			ArrayList<Place> inPlaces = new ArrayList<Place>();
			for (Place place : lp.getPlaces()) {
				for (Transition transition : place.inTrans) {
					if (lp.getId() != transition.getExecutor().getId()) {
						inPlaces.add(place);
					}
				}
			}
			lp.setInPlaces(inPlaces.toArray(new Place[inPlaces.size()]));
		}
	}

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
	private List<LP> decomposeNetIntoMinimumRegionLPs() {
		int numPlaces = net.getNumPlaces();
		int numTrans = net.getNumTrans();
		Place[] places = net.getPlaces();
		// TODO check if no probes in newly created nets

		/* already assigned to LPs */
		boolean[] usedTransitions = new boolean[numTrans];
		boolean[] usedPlace = new boolean[numPlaces];
		List<LP> listLPs = new ArrayList<LP>();
		List<Integer> idTransitionsLP = null;
		List<Integer> idPlacesLP = null;

		int idLP = 0;
		for (int i = 0; i < numPlaces; i++) {
			if (usedPlace[i] == false) {
				usedPlace[i] = true;
				idTransitionsLP = new ArrayList<Integer>();
				idPlacesLP = new ArrayList<Integer>();
				idPlacesLP.add(i);
				for (Transition t : places[i].outTrans) {
					if (usedTransitions[t.id] == false) {
						// TODO make recursive
						idTransitionsLP.add(t.id);
						usedTransitions[t.id] = true;
						if (t.inPlaces.length > 1) {
							for (Place p : t.inPlaces) {
								int placeId = p.id;
								if (!usedPlace[placeId]) {
									idPlacesLP.add(placeId);
									usedPlace[placeId] = true;
								}
							}
						} else {
							continue;
						}
					}
				}
				// from list to net elements
				Transition[] transitionsLP = new Transition[idTransitionsLP
						.size()];
				for (int j = 0; j < idTransitionsLP.size(); j++) {
					transitionsLP[j] = net.getTrans(idTransitionsLP.get(j));
				}
				Place[] placesLP = new Place[idPlacesLP.size()];
				for (int j = 0; j < idPlacesLP.size(); j++) {
					placesLP[j] = net.getPlace(idPlacesLP.get(j));
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

				listLPs.add(new LP(placesLP, transitionsLP, queueList
						.toArray(new Queue[queueList.size()]), configuration,
						progressMonitor, idLP));
				idLP++;
			} else {
				// Place is already in another LP
			}
		}

		// Set LP id to places and transitions
		for (LP lp : listLPs) {
			lp.setExecutorToEntities();
		}
		return listLPs;
	}

	private void mergeLPsInLane(List<LP> listLPs) {
		int counterD = listLPs.size();
		for (int i = 0; i < counterD; i++) {
			LP lp1 = listLPs.get(i);
			for (Transition transition : lp1.getTransitions()) {
				if (transition.outPlaces.length == 1) {
					LP lp2 = (LP) transition.outPlaces[0].getExecutor();
					if (lp1.getId() != lp2.getId()) {
						boolean shouldMerge = true;
						for (Transition transition2 : lp2.getTransitions()) {
							if (transition2.inPlaces.length > 1) {
								shouldMerge = false;
								break;
							}
						}
						if (!shouldMerge) {
							break;
						}
						// merge
						listLPs.remove(lp1);
						listLPs.remove(lp2);
						LP newLP = LP.merge(lp1, lp2);
						listLPs.add(newLP);
						i = -1;
						counterD = listLPs.size();
						break;
					}
				}
			}
		}

		/* Set new LP ids */
		for (int i = 0; i < listLPs.size(); i++) {
			LP lp = listLPs.get(i);
			lp.setId(i);
			lp.setExecutorToEntities();
		}
	}

	/**
	 * Sets predecessor and successor for the LPs
	 * 
	 * @param listLPs
	 */
	private void setPredAndSuccessors(List<LP> listLPs) {
		for (LP lp : listLPs) {
			for (Place place : lp.getPlaces()) {
				for (Transition inTrans : place.inTrans) {
					for (Place prePlace : inTrans.inPlaces) {
						LP predLP = (LP) prePlace.getExecutor();
						if (!predLP.equals(lp)) {
							lp.addPredecessor(predLP);
							predLP.addSuccessor(lp);
						}
					}
				}
			}
		}

		for (LP lp : listLPs) {
			removeDuplicateWithOrder((ArrayList<LP>) lp.getPredecessors());
			removeDuplicateWithOrder((ArrayList<LP>) lp.getSuccessors());
		}
	}

	/**
	 * Removes duplicates while preserving the order of an LP list
	 * 
	 * TODO HELPER FUNCTION, Move this in a helper class
	 * 
	 * @param arrayList
	 */
	private static void removeDuplicateWithOrder(ArrayList<LP> arrayList) {
		Set<LP> set = new HashSet<LP>();
		List<LP> newList = new ArrayList<LP>();
		for (Iterator<LP> iter = arrayList.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add((LP) element))
				newList.add((LP) element);
		}
		arrayList.clear();
		arrayList.addAll(newList);
	}

}
