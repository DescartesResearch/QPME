package de.tud.cs.simqpn.kernel.executor.parallel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.Transition;
import de.tud.cs.simqpn.kernel.executor.Executor;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;

public class ParallelExecutor {
	private static Logger log = Logger.getLogger(ParallelExecutor.class);

	public static void main(String[] args) throws InterruptedException {
		int cnt = 0;
		double time = System.nanoTime();
		while (cnt < 1000000000) {
			cnt++;
		}
		time = System.nanoTime() - time;
		System.out.println(time);
	}

	private Net net;
	private SimQPNConfiguration configuration;
	private SimulatorProgress progressMonitor;

	public ParallelExecutor(Net net, SimQPNConfiguration configuration,
			SimulatorProgress progressMonitor) {
		this.net = net;
		this.configuration = configuration;
		this.progressMonitor = progressMonitor;
	}

	public Net run() throws SimQPNException {
		LP[] lps = createLPs();
		ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors
				.newCachedThreadPool();
		for (LP lp : lps) {
			System.out.println("LP"+lp.getId());;
			for(Place p : lp.getPlaces()){
				System.out.println("\t"+p.name+"(place)");
			}
			for(Transition t : lp.getTransitions()){
				System.out.println("\t"+t.name +"(transition)");
			}
		}
		
		for (LP lp : lps) {
			threadPoolExecutor.execute(lp);
		}
		threadPoolExecutor.shutdown();
		return net;
	}

	
	
	LP[] createLPs() {
		int numPlaces = net.getNumPlaces();
		int numTrans = net.getNumTrans();
		Place[] places = net.getPlaces();

		/*already assigned to LPs*/ 
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
								if(!usedPlace[placeId]){
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
				Transition[] transitionsLP = new Transition[idTransitionsLP.size()];
				for (int j = 0; j < idTransitionsLP.size(); j++) {
					transitionsLP[j] = net.getTrans(idTransitionsLP.get(j));
				}
				Place[] placesLP = new Place[idPlacesLP.size()];
				for (int j = 0; j < idPlacesLP.size(); j++) {
					placesLP[j] = net.getPlace(idPlacesLP.get(j));
				}
				listLPs.add(new LP(placesLP, transitionsLP, configuration,
						progressMonitor, idLP));
				idLP++;
			} else {
				// Place is already in another LP
			}

		}

		LP[] lpArray = listLPs.toArray(new LP[listLPs.size()]);
		return lpArray;
	}

}
