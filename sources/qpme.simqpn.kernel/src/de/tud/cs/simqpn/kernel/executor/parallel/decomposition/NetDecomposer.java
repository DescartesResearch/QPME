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
 *  2013/08/10  Jürgen Walter     Created
 * 
 */
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
		
		int cores = configuration.getNumerOfLPs();
		if(cores <= 1){
		 cores = Runtime.getRuntime().availableProcessors();
		}
		LPMerger merger = new LPMerger(net, minimumRegions, verbosityLevel, cores);
		
		if (configuration.getDecompositionApproach() != null){
			if(configuration.getDecompositionApproach().equals("specjenterprise")) {
				log.info("Merging optimized for SPECj Enterprise model");
				merger.mergeSPECjEnterpriseSpecific();
			} else if (configuration.getDecompositionApproach().equals("generated")){
				merger.mergeNoQueueLPsIntoPredecessor();
				merger.mergeLanes();
				merger.mergeIntoWorkloadGenerators();
				//merger.mergeNonWorkloadGenerators();		
			}else{
				log.info("Could not find specialized decomposition approach");
				merger.mergeNoQueueLPsIntoPredecessor();
				merger.mergeLanes();
				merger.mergeIntoWorkloadGenerators();
				merger.mergeNonWorkloadGenerators();		
			}
		} else {
			//merger.mergeWorkloadGenerators();
			//merger.mergeIntoWorkloadGenerators();
			merger.mergeCyclicConnected();
			//merger.mergeNoQueueLPsIntoPredecessor();
			//merger.mergeNonWorkloadGenerators();
		}
		return merger.getLPs();
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
