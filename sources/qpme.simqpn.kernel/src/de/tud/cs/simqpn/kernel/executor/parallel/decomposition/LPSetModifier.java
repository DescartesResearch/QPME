package de.tud.cs.simqpn.kernel.executor.parallel.decomposition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.Transition;
import de.tud.cs.simqpn.kernel.executor.parallel.LP;

public class LPSetModifier {
	private static Logger log = Logger.getLogger(LPSetModifier.class);


	public static LP merge(List<LP> lps, LP lp1, LP lp2, int verbosityLevel) {
		lps.remove(lp1);
		lps.remove(lp2);
		LP merged = LP.merge(lp1, lp2);
		lps.add(merged);
		rewire(lp1,merged);
		rewire(lp2, merged);

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
	
	/**
	 * Sets predecessor and successor from old to merged LP
	 * @param old
	 * @param merged
	 */
	private static void rewire(LP old, LP merged){
		for(LP pre : old.getPredecessors()){
			pre.getSuccessors().remove(old);
			pre.addSuccessor(merged);
			merged.addPredecessor(pre);
		}
		for(LP suc : old.getSuccessors()){
			suc.getPredecessors().remove(old);
			suc.addPredecessor(merged);
			merged.addSuccessor(suc);
		}
		merged.getPredecessors().remove(old);
		merged.getSuccessors().remove(old);


	}
	
	public static void setNewLPidentifiers(List<LP> lps) {
		for (int i = 0; i < lps.size(); i++) {
			LP lp = lps.get(i);
			lp.setId(i);
			lp.setExecutorToEntities();
		}
	}

	public static void setMetaInformation(List<LP> lps) {
		LPSetModifier.setPredecessorsAndSuccessors(lps);
		// mergePlaceLPsIntoPredecessors(listLPs);

		// Modify transition ids
		int transCnt = 0;
		for (LP lp : lps) {
			for (Transition trans : lp.getTransitions()) {
				trans.id = transCnt++;
			}
		}
	}

//	public static void setInPlaces(LP lp) {
//		ArrayList<Place> inPlaces = new ArrayList<Place>();
//		for (Place place : lp.getPlaces()) {
//			for (Transition transition : place.inTrans) {
//				if (lp.getId() != transition.getExecutor().getId()) {
//					inPlaces.add(place);
//				}
//			}
//		}
//		if(inPlaces.size() == 0){
//			lp.setInPlaces(new Place[0]);
//		}else{
//			removeDuplicateWithOrder2(inPlaces);
//			lp.setInPlaces(inPlaces.toArray(new Place[inPlaces.size()]));			
//		}
//	}

	static void setPredecessors(List<LP> lps, LP lp) {
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
	public static void setPredecessorsAndSuccessors(List<LP> listLPs) {
		for (LP lp: listLPs) {
			setPredecessorAndSuccessor(lp);
		}
	}

	public static void setPredecessorAndSuccessor(LP lp) {
		//lp.resetInPlaces();
//		lp.resetPredecessors();
//		lp.resetSuccessorList();
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
//		removeDuplicateWithOrder((ArrayList<LP>) lp.getPredecessors());
//		removeDuplicateWithOrder((ArrayList<LP>) lp.getSuccessors());
//		removeDuplicateWithOrder((ArrayList<LP>) lp.getSuccessors());
//		removeDuplicateWithOrder((ArrayList<LP>) lp.getSuccessors());
	}

	/**
	 * Removes duplicates while preserving the order of an LP list
	 * 
	 * TODO HELPER FUNCTION, Move this in a helper class
	 * 
	 * @param arrayList
	 */
	private static void removeDuplicateWithOrder(List<LP> list) {
		Set<LP> set = new HashSet<LP>();
		List<LP> newList = new ArrayList<LP>();
		for (Iterator<LP> iter = list.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add((LP) element))
				newList.add((LP) element);
		}
		list.clear();
		list.addAll(newList);
	}

//	private static void removeDuplicateWithOrder2(ArrayList<Place> arrayList) {
//		Set<Place> set = new HashSet<Place>();
//		List<Place> newList = new ArrayList<Place>();
//		for (Iterator<Place> iter = arrayList.iterator(); iter.hasNext();) {
//			Object element = iter.next();
//			if (set.add((Place) element))
//				newList.add((Place) element);
//		}
//		arrayList.clear();
//		arrayList.addAll(newList);
//	}

}
