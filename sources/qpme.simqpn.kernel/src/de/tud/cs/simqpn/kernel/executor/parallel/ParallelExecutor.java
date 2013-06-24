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

	/**
	 * Modifies net / simulates net
	 * 
	 * @throws SimQPNException
	 */
	public Net call() throws SimQPNException {

		LP[] lps = createLPs();
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

		return net;
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
				sb.append("\t" + t.name + "(transition)" + "\n");
			}
		}
		return sb.toString();
	}

	LP[] createLPs() {

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

		LP[] lpArray = listLPs.toArray(new LP[listLPs.size()]);

		lpArray[lpArray.length - 1].setSuccessor(lpArray[0]);
		for (int i = 0; i < lpArray.length - 1; i++) {
			lpArray[i].setSuccessor(lpArray[i + 1]);
		}

		lpArray[0].setPredecessor(lpArray[lpArray.length - 1]);
		for (int i = 1; i < lpArray.length; i++) {
			lpArray[i].setPredecessor(lpArray[i - 1]);
		}
		return lpArray;
	}

}
