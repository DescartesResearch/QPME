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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.Transition;
import de.tud.cs.simqpn.kernel.executor.parallel.LP;

public class LPMerger {

	private static Logger log = Logger.getLogger(LPMerger.class);
	private final Net net;
	private List<LP> lps;
	private final int verbosityLevel;
	private final int cores;
	
	LPMerger(Net net, List<LP> minimumRegions, int verbosityLevel) {
		this.net = net;
		this.lps = minimumRegions;
		this.verbosityLevel = 5;//verbosityLevel;
		this.cores = Runtime.getRuntime().availableProcessors();
	}

	public LP[] getLPsAsArray(){
		return lps.toArray(new LP[lps.size()]);
	}
	
	void mergeSPECjEnterpriseSpecific() {
		//SPEC Model
		
		merge(lps, lps.get(15), lps.get(3), verbosityLevel);
		merge(lps, lps.get(lps.size()-1), lps.get(13), verbosityLevel);
		merge(lps, lps.get(lps.size()-1), lps.get(12), verbosityLevel);

		merge(lps, lps.get(11), lps.get(10), verbosityLevel);		
		merge(lps, lps.get(lps.size()-1), lps.get(9), verbosityLevel);

		merge(lps, lps.get(8), lps.get(7), verbosityLevel);
		merge(lps, lps.get(lps.size()-1), lps.get(6), verbosityLevel);
		
		merge(lps, lps.get(lps.size()-1), lps.get(lps.size()-2), verbosityLevel);		// merge CPUs 

	
		merge(lps, lps.get(5), lps.get(3), verbosityLevel);
		merge(lps, lps.get(lps.size()-1), lps.get(0), verbosityLevel);
		//merge(lps, lps.get(lps.size()-1), lps.get(2), verbosityLevel);
		merge(lps, lps.get(0), lps.get(2), verbosityLevel);
		merge(lps, lps.get(0), lps.get(1), verbosityLevel);

		for(int i=0; i<lps.size(); i++){
			lps.get(i).resetPredecessors();
			lps.get(i).resetInPlaces();
		}
	}

	/**
	 * Merges LPs that include no queueing places into a predecessor LP
	 * @param lps
	 */
	void mergeNoQueueLPs() {
		for(int i=0; i<lps.size(); i++){
			LP lp = lps.get(i);
			if(lp.getQueues().length == 0){
				setPredecessors(lps, lp);
				setInPlaces(lp);
				if(!lp.isWorkloadGenerator()){
					LP pred = lp.getPredecessors().get(0);
					merge(lps, pred, lp, verbosityLevel);
					i = 0;
				}
			}
		}
		for(int i=0; i<lps.size(); i++){
			lps.get(i).resetPredecessors();
			lps.get(i).resetInPlaces();
		}
	}

	void mergeNonWorkloadGenerators() {
		setInPlaces(lps);
		setPredAndSuccessors(lps);

		for(int i=0; i<lps.size(); i++){
			LP lp = lps.get(i);
			if (lp.getPlaces().length < net.getNumPlaces()
					/ cores) {
				for(LP lpToMerge:lps){
					if(lp != lpToMerge){
						if (lpToMerge.getPlaces().length < net.getNumPlaces()
								/ cores) {
							lp = merge(lps, lp, lpToMerge,
									verbosityLevel);
						}
					}
				}
			}
		}
	}

	void mergeIntoWorkloadGenerators() {
		setInPlaces(lps);
		setPredAndSuccessors(lps);

		for (int cnt = 0; lps.size() > cores && cnt < 10; cnt++) {
			List<LP> openWorkloads = new ArrayList<LP>();
			for (LP lp : lps) {
				if (lp != null) {
					if (lp.isWorkloadGenerator()) {
						openWorkloads.add(lp);
					}
				}
			}
			for (LP openWorkload : openWorkloads) {
				List<LP> successors = openWorkload.getSuccessors();
				for (LP suc : successors) {
					if (openWorkload.getPlaces().length < net.getNumPlaces()
							/ cores) {
						if (!suc.isWorkloadGenerator()) {
							openWorkload = merge(lps, openWorkload, suc,
									verbosityLevel);
						}
					}
				}
			}

		}
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
						
						if(lps.size() <= cores){
							return;
						}

						merge(lps, lp1, lp2, verbosityLevel);
						
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
	
	void setNewLPidentifiers(){
		for (int i = 0; i < lps.size(); i++) {
			LP lp = lps.get(i);
			lp.setId(i);
			lp.setExecutorToEntities();
		}
	}

	
	void setMetaInformation() {
		setInPlaces(lps);
		setPredAndSuccessors(lps);
		// mergePlaceLPsIntoPredecessors(listLPs);

		// Modify transition ids
		int transCnt = 0;
		for (LP lp : lps) {
			for (Transition trans : lp.getTransitions()) {
				trans.id = transCnt++;
			}
		}
	}

	private static void setModelSpecificMetaInformations(List<LP> lps) {
		//lps.get(0).addPredecessor(lps.get(3));
	}
	
	/**
	 * Returns a decomposition of the (internal) {@link Net} into a logical
	 * process ({@link LP}) array
	 * 
	 * @return Array of logical processes
	 */

	private static void setInPlaces(List<LP> listLPs) {
		for (LP lp : listLPs) {
			setInPlaces(lp);
		}
	}

	private static void setInPlaces(LP lp) {
		ArrayList<Place> inPlaces = new ArrayList<Place>();
		for (Place place : lp.getPlaces()) {
			for (Transition transition : place.inTrans) {
				if (lp.getId() != transition.getExecutor().getId()) {
					inPlaces.add(place);
				}
			}
		}
		removeDuplicateWithOrder2(inPlaces);
		lp.setInPlaces(inPlaces.toArray(new Place[inPlaces.size()]));
	}


	private static LP merge(List<LP> lps, LP lp1, LP lp2, int verbosityLevel) {
		lps.remove(lp1);
		lps.remove(lp2);
		LP merged = LP.merge(lp1, lp2);
		setInPlaces(merged);
		lps.add(merged);
		lps.remove(null);
		if (verbosityLevel > 1) {
			String txt = "";		
			for(Place place: lp1.getPlaces()){
				txt+="["+place.name +"] ";
			}
			txt+= " and ";
			for(Place place: lp2.getPlaces()){
				txt+="["+place.name +"] ";
			}
			log.info("merged logical partitions "+txt);
		}
		return merged;
	}

	private static void setPredecessors(List<LP> lps, LP lp){
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
	
	/**
	 * Sets predecessor and successor for the LPs
	 * 
	 * @param listLPs
	 */
	private static void setPredAndSuccessors(List<LP> listLPs) {
		List<List<LP>> megaList = new ArrayList<List<LP>>();

		for (int i = 0; i < listLPs.size(); i++) {
			LP lp = listLPs.get(i);
			megaList.add(new ArrayList<LP>());
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
	
	private static void removeDuplicateWithOrder2(ArrayList<Place> arrayList) {
		Set<Place> set = new HashSet<Place>();
		List<Place> newList = new ArrayList<Place>();
		for (Iterator<Place> iter = arrayList.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add((Place) element))
				newList.add((Place) element);
		}
		arrayList.clear();
		arrayList.addAll(newList);
	}

}
