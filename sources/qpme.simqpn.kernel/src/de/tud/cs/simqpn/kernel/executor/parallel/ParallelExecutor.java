package de.tud.cs.simqpn.kernel.executor.parallel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.analyzer.ReplicationDeletionParallel;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.Transition;
import de.tud.cs.simqpn.kernel.entities.queue.Queue;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;

/**
 * This class decomposes a {@link Net} and runs its parts parallel by the use of
 * {@link LP}s
 * 
 * @author D
 * 
 */
public class ParallelExecutor implements Callable<Net> {
	private static Logger log = Logger
			.getLogger(ReplicationDeletionParallel.class);

	private Net net;
	private SimQPNConfiguration configuration;
	private SimulatorProgress progressMonitor;
	private int runID;

	/**
	 * Constructor
	 * 
	 * @param net
	 * @param configuration
	 * @param progressMonitor
	 * @param runID
	 */
	public ParallelExecutor(Net net, SimQPNConfiguration configuration,
			SimulatorProgress progressMonitor, int runID) {
		this.net = net;
		this.configuration = configuration;
		this.progressMonitor = progressMonitor;
		this.runID = runID;
	}

	/**
	 * Simulates the passed net.
	 * 
	 * @throws SimQPNException
	 */
	// Modifies net
	public Net call() throws SimQPNException {
		LP[] lps = decomposeNetIntoLPs();
		System.out.println(lpDecompositionToString(lps));

		CyclicBarrier barrier = new CyclicBarrier(lps.length);
		StopCriterionController stopCriterion = new StopCriterionController(
				lps.length, barrier);
		for (LP lp : lps) {
			lp.setBarrier(barrier);
			lp.setStopCriterion(stopCriterion);
		}

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
				log.error("", e);
			}
		}
		
		
		
		return this.net;
	}

	/**
	 * Returns a {@link String} representation of an {@link LP} array
	 * 
	 * @param lps
	 *            Array of LPs
	 */
	private String lpDecompositionToString(LP[] lps) {
		StringBuffer sb = new StringBuffer();
		for (LP lp : lps) {
			sb.append("LP" + lp.getId() + "\n");
			for (Place p : lp.getPlaces()) {
				if (p instanceof QPlace) {
					sb.append("\t"
							+ p.name
							+ "(QPplace), queue "
							+ ((QPlace) p).queue.name + "  "
							+ ((QPlace) p).queue.getClass().toString()
									.split("queue.")[1] + "\n");

				} else {
					sb.append("\t" + p.name + "(Place)" + "\n");
				}
			}
			for (Transition t : lp.getTransitions()) {
				sb.append("\t" + t.name + "(transition)" + " ID " + t.id + "\n");
			}

			sb.append("\tsuccessors: ");
			for (LP suc : lp.getSuccessors()) {
				sb.append("LP" + suc.getId() + " ");
			}
			sb.append("\n");
			sb.append("\tpredecessors: ");
			for (LP pred : lp.getPredecessors()) {
				sb.append("LP" + pred.getId() + " ");
			}
			sb.append("\n");

		}
		return sb.toString();
	}

	/**
	 * Returns a decomposition of the (internal) {@link Net} into a logical
	 * process ({@link LP}) array
	 * 
	 * @return Array of logical processes
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
						progressMonitor, idLP));
				idLP++;
			} else {
				// Place is already in another LP
			}
		}

		// mergePlaceLPsIntoPredecessors(listLPs);

		// Modify transition ids
		int transCnt = 0;
		for (LP lp : listLPs) {
			for (Transition trans : lp.getTransitions()) {
				trans.id = transCnt++;
			}
		}


		// Set LP id to places and transitions
		for (LP lp : listLPs) {
			for (Place place : lp.getPlaces()) {
				place.setExecutor(lp);
			}
			for (Transition transition : lp.getTransitions()) {
				transition.setExecutor(lp);
			}
		}

		setPredAndSuccessors(listLPs);

		return listLPs.toArray(new LP[listLPs.size()]);
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
						lp.addPredecessor(predLP);
						predLP.addSuccessor(lp);
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
