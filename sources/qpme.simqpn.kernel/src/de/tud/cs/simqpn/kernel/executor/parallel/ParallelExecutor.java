package de.tud.cs.simqpn.kernel.executor.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.Queue;
import de.tud.cs.simqpn.kernel.entities.Transition;
import de.tud.cs.simqpn.kernel.executor.Executor;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;

public class ParallelExecutor implements Callable<Net> {

	private Net net;
	private SimQPNConfiguration configuration;
	private SimulatorProgress progressMonitor;
	private int runID;

	public ParallelExecutor(Net net, SimQPNConfiguration configuration,
			SimulatorProgress progressMonitor, int runID) {
		this.net = net;
		this.configuration = configuration;
		this.progressMonitor = progressMonitor;
		this.runID = runID;
	}

	boolean[] transStatus; // Transition status: true = enabled, false =
	// disabled
	
//	public void setTransStatus(int transId, boolean value){
//		transStatus[transId] = value;
//	}

	/**
	 * Modifies net / simulates net
	 * 
	 * @throws SimQPNException
	 */
	public Net call() throws SimQPNException {
		// Initialize transStatus and enTransCnt		
		Net newNet = this.net;
		System.out.println();
		LP[] lps = decomposeNetIntoLPs();
		System.out.println(lpDecompositionToString(lps));

		Thread[] threads = new Thread[lps.length];
		for (int i = 0; i < lps.length; i++) {
			threads[i] = new Thread(lps[i]);
		}
		for (int i = 0; i < lps.length; i++) {
			threads[i].start();
		}

		for (int i = 0; i < lps.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
//
		//TODO merge all net elements into one net object
		
		return newNet;
	}

	/**
	 * 
	 * @param lps
	 */
	private String lpDecompositionToString(LP[] lps) {
		StringBuffer sb = new StringBuffer();
		for (LP lp : lps) {
			sb.append("LP" + lp.getId() + "\n");
			for (Place p : lp.getPlaces()) {
				if (p instanceof QPlace) {
					sb.append("\t" + p.name + "(QPplace), queue "
							+ ((QPlace) p).queue.name + "\n");
				} else {
					sb.append("\t" + p.name + "(Place)" + "\n");
				}
			}
			for (Transition t : lp.getTransitions()) {
				sb.append("\t" + t.name + "(transition)"+" ID "+t.id + "\n");
			}
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	private LP[] decomposeNetIntoLPs() {
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
						progressMonitor, idLP, net));
				idLP++;
			} else {
				// Place is already in another LP
			}				
		}
		// Give LP id to places
		for(LP lp:listLPs){
			for(Place place: lp.getPlaces()){
				place.setExecutor(lp);
			}
		}
		// 
		for(LP lp:listLPs){
			for(Place place: lp.getPlaces()){
				for(Transition inTrans: place.inTrans){
					for(Place prePlace: inTrans.inPlaces){
						LP predLP = (LP) prePlace.getExecutor();
						lp.addPredecessor(predLP);
						predLP.addSuccessor(lp);							
					}
				}
			}
		}
		//Modify transition ids
		int transCnt = 0;
		for(LP lp: listLPs){
			for(Transition trans : lp.getTransitions()){
				trans.id = transCnt++;
			}
		}
		
		//mergePlaceLPsIntoPredecessors(listLPs);
		return listLPs.toArray(new LP[listLPs.size()]);
	}

	private void mergePlaceLPsIntoPredecessors(List<LP> listLPs) {
		// MERGING LPs
		int length = listLPs.size()-1;
		for(int i=0; i< length; i++){
			LP lp = listLPs.get(i);
			boolean mergeFlag = true;
			for(Place place: lp.getPlaces()){
				if(place instanceof QPlace){
					mergeFlag = false;
				}
			}
			if(mergeFlag){
				(lp.getSuccessors().get(0)).merge(lp);
				listLPs.remove(lp);
				--length;
				System.out.println("LP "+lp.getId() + " was merged into "+ lp.getSuccessors().get(0));
			}
		}
	}

}
