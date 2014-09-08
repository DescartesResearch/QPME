package de.tud.cs.simqpn.kernel.executor.parallel.decomposition;

import java.util.List;
import org.apache.log4j.Logger;
import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.executor.parallel.LP;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;

public class NetDecomposer {

	private static Logger log = Logger.getLogger(NetDecomposer.class);

	public static LP[] decomposeNetIntoLPs(Net net,
			SimQPNConfiguration configuration,
			SimulatorProgress progressMonitor, int verbosityLevel) {
		List<LP> minimumRegions = MinimumRegionDecomposer
				.decomposeNetIntoMinimumRegions(net, configuration,
						progressMonitor, verbosityLevel);
		LPMerger merger = new LPMerger(net, minimumRegions, verbosityLevel);

		if (configuration.getDecompositionApproach().equals("specjenterprise")) {
			log.info("Merging optimized for SPECj Enterprise model");
			merger.mergeSPECjEnterpriseSpecific();
		} else {
			merger.mergeNoQueueLPs();
			merger.mergeLanes();
			merger.mergeIntoWorkloadGenerators();
			merger.mergeNonWorkloadGenerators();		
		}
		merger.setNewLPidentifiers();
		merger.setMetaInformation();
		return merger.getLPsAsArray();
	}

	
	public static boolean hasDecompositionSucceded(LP[] lps, int verbosityLevel) {
		if (lps == null) {
			log.info("Error during decomposition for parallel simulation.");
			return false;
		} else if (lps.length == 0) {
			log.info("Number of LPs is 0. Number of LPs has to be larger than one.");
			return false;
		} else if (lps.length == 1) {
			log.info("Number of LPs is 1. Number of LPs has to be larger than one.");
			return false;
		} else if (lps.length > Runtime.getRuntime().availableProcessors()) {
			log.info("Number of LPs is higher than the number of available cores.");
			log.info(NetDecomposer.lpDecompositionToString(lps));
			return false;
		} else {
			boolean hasOpenWorkload = false;
			boolean hasOpenWorkloadWithQueue = false;
			for (LP lp : lps) {
				if (lp.isWorkloadGenerator()) {
					hasOpenWorkload = true;
					if (lp.getQueues().length > 0) {
						hasOpenWorkloadWithQueue = true;
					}
				}
			}
			if (!hasOpenWorkload) {
				log.info("Could not identify open workloads.");
				return false;
			} else {
				if (hasOpenWorkloadWithQueue) {
					if (verbosityLevel > 0) {
						log.info(NetDecomposer.lpDecompositionToString(lps));
					}
					return true;
				} else {
					log.info("Identified open workload, but no queue in it.");
					return false;
				}
			}
		}
	}

	/**
	 * Returns a {@link String} representation of an {@link LP} array
	 * 
	 * @param lps
	 *            Array of LPs
	 */
	private static String lpDecompositionToString(LP[] lps) {
		StringBuffer sb = new StringBuffer();
		for (LP lp : lps) {
			sb.append(lp.toString());
		}
		return sb.toString();
	}
}
