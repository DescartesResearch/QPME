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

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.Transition;
import de.tud.cs.simqpn.kernel.executor.parallel.LP;

public class LPMerger {

	private static Logger log = Logger.getLogger(LPMerger.class);
	private final Net net;
	private List<LP> lps;
	private final int verbosityLevel;
	private final int cores;

	LPMerger(Net net, List<LP> minimumRegions, int verbosityLevel) {
		this(net, minimumRegions, verbosityLevel, Runtime.getRuntime()
				.availableProcessors());
	}

	LPMerger(Net net, List<LP> minimumRegions, int verbosityLevel, int cores) {
		this.net = net;
		this.lps = minimumRegions;
		this.verbosityLevel = 5;// verbosityLevel;
		this.cores = cores;
		LPSetModifier.setPredecessorsAndSuccessors(lps);
	}

	public void mergeWorkloadGenerators() {
		log.info("Merge workload generators");
		List<LP> generators = getWorkloadGenerators();
		LP merged = null;
		for (LP generator : generators) {
			if (merged == null) {
				merged = generator;
			} else {
				merged = LPSetModifier.merge(lps, merged, generator,
						verbosityLevel);
			}
		}
	}

	/**
	 * Returns a decomposition of the (internal) {@link Net} into a logical
	 * process ({@link LP}) array
	 * 
	 * @return Array of logical processes
	 */
	public LP[] getLPs() {
		LPSetModifier.setPredecessorsAndSuccessors(lps);
		LPSetModifier.setNewLPidentifiers(lps);
		LPSetModifier.setMetaInformation(lps);
		return lps.toArray(new LP[lps.size()]);
	}

	public void mergeCyclicConnected() {
		CycleMerger merger = new CycleMerger();
		lps = merger.mergeCyclicConnected(lps,verbosityLevel);
	}

	/**
	 * Merges LPs that include no queueing places into a predecessor LP.
	 */
	protected void mergeNoQueueLPsIntoPredecessor() {
		log.info("Merging no queue LPs into predecessors");
		LPSetModifier.setPredecessorsAndSuccessors(lps);
		for (int i = 0; i < lps.size(); i++) {
			LP lp = lps.get(i);
			if (lp.getQueues().length == 0) {
				// LPSetModifier.setPredecessors(lps, lp);
				// LPSetModifier.setInPlaces(lp);
				// if(!lp.isWorkloadGenerator()){
				LP pred = lp.getPredecessors().get(0);
				LPSetModifier.merge(lps, pred, lp, verbosityLevel);
				i = 0;
			}
			// }
		}
	}

	protected void mergeNonWorkloadGenerators() {
		log.info("Merging non-workload-generators");

		for (int i = 0; i < lps.size(); i++) {
			LP lp = lps.get(i);
			if (lp.getPlaces().length < net.getNumPlaces() / cores) {
				for (int j = 0; j < lps.size(); j++) {
					LP lpToMerge = lps.get(j);
					if (lp != lpToMerge) {
						if (lpToMerge.getPlaces().length < net.getNumPlaces()
								/ cores) {
							lp = LPSetModifier.merge(lps, lp, lpToMerge,
									verbosityLevel);
						}
					}
				}
			}
		}
	}

	/**
	 * @deprecated
	 */
	protected void mergeIntoWorkloadGenerators() {
		log.info("Merging into workload-generators");

		for (int cnt = 0; lps.size() > cores && cnt < 10; cnt++) {
			List<LP> openWorkloads = getWorkloadGenerators();
			for (LP openWorkload : openWorkloads) {
				List<LP> successors = openWorkload.getSuccessors();
				for (LP suc : successors) {
					if (openWorkload.getPlaces().length < net.getNumPlaces()
							/ cores) {
						if (!suc.isWorkloadGenerator()) {
							openWorkload = LPSetModifier.merge(lps,
									openWorkload, suc, verbosityLevel);
						}
					}
				}
			}

		}
	}

	public List<LP> getWorkloadGenerators() {
		List<LP> openWorkloads = new ArrayList<LP>();
		for (LP lp : lps) {
			if (lp.isWorkloadGenerator()) {
				openWorkloads.add(lp);
			}
		}
		return openWorkloads;
	}

	void mergeLanes() {
		int counterD = lps.size();
		for (int i = 0; i < counterD; i++) {
			LP lp1 = lps.get(i);
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
						if (lps.size() <= cores) {
							return;
						}
						LPSetModifier.merge(lps, lp1, lp2, verbosityLevel);
						i = i - 1;
						counterD = lps.size();
						break;
					}
				}
			}
		}

		// for generated models
		if (lps.get(1).getPlaces()[0].name.equals("token generator")) {
			LP lp1 = lps.remove(0);// (lp1);
			LP lp2 = lps.remove(0);// (lp1);
			LP newLP = LP.merge(lp1, lp2);
			lps.add(0, newLP);
		}
	}

	protected void mergeSPECjEnterpriseSpecific() {
		// SPEC Model
		LPSetModifier.merge(lps, lps.get(15), lps.get(3), verbosityLevel);
		LPSetModifier.merge(lps, lps.get(lps.size() - 1), lps.get(13),
				verbosityLevel);
		LPSetModifier.merge(lps, lps.get(lps.size() - 1), lps.get(12),
				verbosityLevel);

		LPSetModifier.merge(lps, lps.get(11), lps.get(10), verbosityLevel);
		LPSetModifier.merge(lps, lps.get(lps.size() - 1), lps.get(9),
				verbosityLevel);

		LPSetModifier.merge(lps, lps.get(8), lps.get(7), verbosityLevel);
		LPSetModifier.merge(lps, lps.get(lps.size() - 1), lps.get(6),
				verbosityLevel);

		LPSetModifier.merge(lps, lps.get(lps.size() - 1),
				lps.get(lps.size() - 2), verbosityLevel); // merge CPUs

		LPSetModifier.merge(lps, lps.get(5), lps.get(3), verbosityLevel);
		LPSetModifier.merge(lps, lps.get(lps.size() - 1), lps.get(0),
				verbosityLevel);
		// merge(lps, lps.get(lps.size()-1), lps.get(2), verbosityLevel);
		LPSetModifier.merge(lps, lps.get(0), lps.get(2), verbosityLevel);
		LPSetModifier.merge(lps, lps.get(0), lps.get(1), verbosityLevel);

		for (int i = 0; i < lps.size(); i++) {
			lps.get(i).resetPredecessors();
			lps.get(i).resetInPlaces();
		}
	}

}
