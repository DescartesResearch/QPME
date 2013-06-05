package de.tud.cs.simqpn.kernel.executor.parallel;

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
		while (cnt<1000000000){
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
		ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		for(LP lp: lps){
			threadPoolExecutor.execute(lp);
		}
		threadPoolExecutor.shutdown();
		return net;
	}
	
	LP[] createLPs(){
		int numPlaces = net.getNumPlaces();
		int numTrans  = net.getNumTrans();
		int numLPs = numPlaces + numTrans;
		LP[] lpArray = new LP[numLPs];
		for(int i=0; i<numPlaces; i++){
			Place[] places = new Place[1];
			places[0] = net.getPlace(i);
			lpArray[i] = new LP(places, new Transition[0], configuration, progressMonitor, i);
		}
		for(int i=0; i<numTrans; i++){
			int id = i + numPlaces;
			Transition[] transitions = new Transition[1];
			transitions[0] = net.getTrans(i);
			lpArray[id] = new LP(new Place[0], transitions, configuration, progressMonitor, id);
		}
		return lpArray;
	}


}
