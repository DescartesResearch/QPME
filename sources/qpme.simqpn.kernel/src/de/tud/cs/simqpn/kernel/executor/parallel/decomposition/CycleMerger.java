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
 *  2015/02/05  Jürgen Walter     Created
 * 
 */package de.tud.cs.simqpn.kernel.executor.parallel.decomposition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.executor.parallel.LP;

/**
 * This class merges LPs that are cyclic connected and thereby forms an acyclic
 * graph which is returned. The algorithm is based on Tarjans algorithm for the
 * detection of strongly connected components in graphs.
 * 
 * @author Jürgen Walter
 */
public class CycleMerger {
	private static Logger log = Logger.getLogger(CycleMerger.class);

	private Map<LP, Integer> lowLinks;
	private Map<LP, Integer> depthFirstSearchIndexes;
	private int depthFirstSearchIndex;
	private List<LP> unvisitedLPs;
	private List<LP> visitedLPs;
	private Stack<LP> stack;

	private CycleMerger(){
	};
	
	/**
	 * Merges LPs that are cyclic connected and thereby forms an acyclic graph.
	 * 
	 * @param lps
	 *            List of LPs
	 * @return List of LPs that form an acyclic graph
	 */
	public static void mergeCycles(List<LP> lps, int verbosityLevel) {
		CycleMerger merger = new CycleMerger();
		merger.findCycles(lps);
		merger.mergeCyclesInternal(lps, verbosityLevel);
	}

	private void findCycles(List<LP> lps) {
		depthFirstSearchIndex = 0;
		unvisitedLPs = new ArrayList<LP>(lps);
		//Collections.copy(lps, unvisitedLPs);
		visitedLPs = new ArrayList<LP>();
		stack = new Stack<LP>();
		lowLinks = new HashMap<LP, Integer>();
		depthFirstSearchIndexes = new HashMap<LP, Integer>();
		for (int i = 0; i < lps.size(); i++) {
			LP lp = lps.get(i);
			if (!lowLinks.containsKey(lp)) {
				doTarjanProcedure(lp);
			}
		}
	}

	private void doTarjanProcedure(LP lp) {
		// Set the depth index for LP to the smallest unused index
		depthFirstSearchIndexes.put(lp, depthFirstSearchIndex);
		lowLinks.put(lp, depthFirstSearchIndex);
		depthFirstSearchIndex++;
		stack.push(lp);
		unvisitedLPs.remove(lp);
		visitedLPs.add(lp);
		for (LP successor : lp.getSuccessors()) {
			// Successor has not yet been visited; recurse on it
			if (unvisitedLPs.contains(successor)) {
				doTarjanProcedure(successor);
				lowLinks.put(lp,
						Math.min(lowLinks.get(lp), lowLinks.get(successor)));
			} else if (stack.contains(successor)) { // could be implemented in
													// O(1).// (e.g. by setting
													// a Bits at the LP for
													// "push" and "pop")
				lowLinks.put(
						lp,
						Math.min(lowLinks.get(lp),
								depthFirstSearchIndexes.get(successor)));
			}
		}
		if (lowLinks.get(lp).equals(depthFirstSearchIndexes.get(lp))) {
			LP lp2;
			do {
				lp2 = stack.pop();
				// merge lp2 and lp2'
			} while (!lp.equals(lp2));
		}
	}

	private void mergeCyclesInternal(List<LP> lps, int verbosityLevel) {
		Collection<Integer> lowLinkSet = lowLinks.values();
		Collection<Integer> cycleIdentifiers = findDuplicates(lowLinkSet);
		if (cycleIdentifiers.size() > 0) {
			log.info("Merging Cycles ...");
			for (Integer cycleIdentifier : cycleIdentifiers) {
				StringBuffer sb = new StringBuffer();
				LP merge = null;
				for(int i=0; i<lps.size(); i++){
//				for (int i = 0; i < visitedLPs.size(); i++) {
					LP lp = lps.get(i);				//visitedLPs.get(i);
					if (lowLinks.containsKey(lp)) { // merged LPs do not have
													// keys
						if (lowLinks.get(lp).equals(cycleIdentifier)) {
							if (merge == null) {
								merge = lp;
							} else {
								merge = LPSetModifier.merge(lps, merge,lp,
										verbosityLevel);
							}
							sb.append(" " + lp.getPlacesString());
						}
					}
				}
				log.info("Cycle " + sb.toString() + "merged!");
			}
		}
	}

	private Collection<Integer> findDuplicates(
			Collection<Integer> listContainingDuplicates) {
		final Collection<Integer> setToReturn = new HashSet<Integer>();
		final Collection<Integer> set1 = new HashSet<Integer>();

		for (Integer yourInt : listContainingDuplicates) {
			if (!set1.add(yourInt)) {
				setToReturn.add(yourInt);
			}
		}
		return setToReturn;
	}

}
