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
 * Original Author(s):  Simon Spinner
 * Contributor(s):  
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2010/07/31	Simon Spinner	  Created.
 *  
 */
package de.tud.cs.simqpn.kernel.entities;

import static de.tud.cs.simqpn.kernel.util.LogUtil.formatDetailMessage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import de.tud.cs.simqpn.kernel.SimQPNController;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.stats.ProbeStats;

/**
 * Class probe.
 * 
 * @author Simon Spinner
 *
 */
public class Probe {
	
	public static final int ON_ENTRY = 0;
	public static final int ON_EXIT = 1;
	
	private static final int NOT_MARK = 0;
	private static final int MARK = 1;
	private static final int INDETERMINATE = 2;
	
	private static Logger log = Logger.getLogger(Probe.class);
	
	public final int      id;
	public final String   xmlId;
	public final String   name;
	public final String[] colors;
	public final Place    startPlace;
	public final int      startTrigger;
	public final Place    endPlace;
	public final int      endTrigger;
	
	public int            statsLevel;
	public ProbeStats	  probeStats;
	
	public Element		  element;
	
	public Probe(int id, String xmlId, String name, String[] colors, Place startPlace, int startTrigger, Place endPlace, int endTrigger, int statsLevel, Element element, SimQPNController sim) throws SimQPNException {
		this.id = id;
		this.xmlId = xmlId;
		this.name = name;
		this.colors = colors;
		this.startPlace = startPlace;
		this.startTrigger = startTrigger;
		this.endPlace = endPlace;
		this.endTrigger = endTrigger;
		this.statsLevel = statsLevel;
		this.element = element;
		
		if (statsLevel > 0) {
			probeStats = new ProbeStats(id, name, colors, statsLevel, sim);
		}
	}
	
	public void init() {
		
	}
	
	/**
	 * Instruments all the places in the region of this probe.
	 * 
	 * ATTENTION: Needs to be called before {@link Place#init()}.
	 * 
	 * @throws SimQPNException
	 */
	public void instrument() throws SimQPNException {
		if (statsLevel >= 3) {
			Set<Place> visitedPlaces = new HashSet<Place>();
			Map<Place, Integer> markings = new HashMap<Place, Integer>();
			// The search for routes starts at the end place and goes back
			// to the start place.
			markings.put(startPlace, MARK);
			visitedPlaces.add(startPlace);

			int result;
			if ((startPlace == endPlace) && (startTrigger == ON_ENTRY) && (endTrigger == ON_EXIT)) {
				// no search needed only one place needs to be instrumented
				result = MARK;
			} else {
				result = INDETERMINATE;				
				
				// List of all indeterminate places. Indeterminate places are places
				// for which it has not been determined yet, whether it is possible
				// to reach the target place (-> start place). At the beginning only
				// the end place is in this list.
				List<Place> indeterminatePlaces = new LinkedList<Place>();
				indeterminatePlaces.add(endPlace);
				
				// Assumption: For all places it can be determined, whether the target
				//             place is reachable. However, it might require several attempts,
				//             if cycles need to be resolved.
				while (indeterminatePlaces.size() != 0) {
					int done = 0;
					// Step 1: For each indeterminate place try to find a route to the target place.
					for (Place curPlace : indeterminatePlaces) {					
						int r = markRoutesToEndPlace(curPlace, markings, visitedPlaces, 0);
						if (r != INDETERMINATE)
							done++;

						if ((r == NOT_MARK && result == INDETERMINATE) || (r == MARK)) {
							result = r;
						}
						visitedPlaces.clear();
						visitedPlaces.add(startPlace);
					}
					if (done == 0) 
						break;
					
					// Step 2: Collect all places that are still indeterminate
					indeterminatePlaces.clear();
					for (Entry<Place, Integer> mark : markings.entrySet()) {
						if (mark.getValue() == INDETERMINATE) {
							indeterminatePlaces.add(mark.getKey());
						}
					}
				}				

			}			
			if (result == MARK) {
				log.debug("probes[" + id + "].instrumentations = { ");
				
				// Instrument start place
				if (startPlace == endPlace) {
					if ((startTrigger == ON_ENTRY) && (endTrigger == ON_EXIT)) {
						startPlace.addProbe(this, Place.PROBE_ACTION_START_ON_ENTRY_AND_END_ON_EXIT); 
						log.debug(startPlace.name + "(start_on_entry), ");
					} else if ((startTrigger == ON_EXIT) && (endTrigger == ON_ENTRY)) {
						startPlace.addProbe(this, Place.PROBE_ACTION_START_ON_EXIT_AND_END_ON_ENTRY);
						log.debug(startPlace.name + "(start_on_exit), ");
					} else {
						log.error(formatDetailMessage(
								"Illegal combination of start and end trigger.",
								"probe-num", Integer.toString(id),
								"probe.id", xmlId,
								"probe.name", name,
								"probe.start-place", startPlace.name,
								"probe.end-place", endPlace.name
								));
						throw new SimQPNException();
					}
				} else {
					if (startTrigger == ON_ENTRY) {
						startPlace.addProbe(this, Place.PROBE_ACTION_START_ON_ENTRY);
						log.debug(startPlace.name + "(start_on_entry), ");
					} else {
						startPlace.addProbe(this, Place.PROBE_ACTION_START_ON_EXIT);
						log.debug(startPlace.name + "(start_on_exit), ");
					}
				}
				
				int markingCount = 2;
				// Instrument the places in between
				for (Entry<Place, Integer> marking : markings.entrySet()) {
					Place pl = marking.getKey();
					if ((pl != startPlace) && (pl != endPlace)) {
						if (marking.getValue() == MARK) {
							pl.addProbe(this, Place.PROBE_ACTION_TRANSFER);
							log.debug(pl.name + ", ");
							markingCount++;
						}
					}
				}
				
				// Instrument the end place
				if (endTrigger == ON_EXIT) {
					if (startPlace != endPlace) endPlace.addProbe(this, Place.PROBE_ACTION_END_ON_EXIT);
					log.debug(endPlace.name + "(end_on_exit)");
				} else {
					if (startPlace != endPlace) endPlace.addProbe(this, Place.PROBE_ACTION_END_ON_ENTRY);
					log.debug(endPlace.name + "(end_on_entry)");
				}
				log.debug(" }");
				log.debug("probes[" + id + "].instrumentations.count = " + markingCount);
				
			} else {
				log.error(formatDetailMessage(
						"Start and end place of probe not connected.",
						"probe-num", Integer.toString(id),
						"probe.id", xmlId,
						"probe.name", name,
						"probe.start-place", startPlace.name,
						"probe.end-place", endPlace.name
						));
				throw new SimQPNException();
			}
		}
	}
	
	/**
	 * Method start
	 *  	    
	 * 
	 * @param 
	 * @return
	 * @exception
	 */
	public void start(SimQPNController sim) throws SimQPNException {	
		if (statsLevel > 0)	
			probeStats.start(sim);					
	}
	
	/**
	 * Method finish
	 *  	    
	 * 	 
	 * @param 
	 * @return
	 * @exception
	 */
	public void finish(SimQPNController sim) throws SimQPNException {
		// Complete statistics collection
		if (statsLevel > 0)	
			probeStats.finish(sim);					
	}

	/**
	 * Method report
	 *  	    
	 * @param 
	 * @return
	 * @exception
	 */
	public void report(SimQPNController sim) throws SimQPNException {
		if (statsLevel > 0) {
			probeStats.printReport(sim);					
			probeStats.addReportMetaData(element, sim);
		}
	}
	
	/**
	 * Finds all possible routes between to places in the net.
	 * 
	 * @param end - end place
	 * @param markings - contains the result for all places. At the beginning at least the start place should be contained in the list.
	 * @param visitedPlaces - all places that have already been visited.
	 * @param flowLength - the current flow length (= 0 at start)
	 * @return MARK, if a route exists, NOT_MARK, if no route exists, INDETERMINATE, if no route can be found because of cycle.
	 * @throws SimQPNException
	 */
	private int markRoutesToEndPlace(Place end, Map<Place, Integer> markings, Set<Place> visitedPlaces, int flowLength) throws SimQPNException {
		// Note: The deep search starts at the end place and traverses the net in direction of the start place.
		
		// Note: If startPlace == endPlace, the search should not return immediately. It should also
		//		 look for a way to get from start to end in cycle including several intermediary places.
		if (flowLength > 0) {
			if (markings.containsKey(end)) {
				int mark = markings.get(end);
				if ((mark == MARK) || (mark == NOT_MARK)) {
					return mark;
				}
			}
			
			if (visitedPlaces.contains(end)) {
				return INDETERMINATE;
			}
		}
		
		visitedPlaces.add(end);
		int ret = INDETERMINATE;
		
		Place[] possibleInPlaces = calculatePossibleInputPlaces(end);
		if (possibleInPlaces.length == 0) {
			markings.put(end, NOT_MARK);
			return NOT_MARK;
		}

		for (int i = 0; i < possibleInPlaces.length; i++) {
			// Recursion
			switch (markRoutesToEndPlace(possibleInPlaces[i], markings, visitedPlaces, flowLength+1)) {
			case MARK:
				ret = MARK;
				break;
			case NOT_MARK:
				if (ret != MARK) ret = NOT_MARK;
				break;
			case INDETERMINATE:
				break;
			}
		}
		markings.put(end, ret);
		
		return ret;
	}
	
	/**
	 * This method backtracks all places from where tokens can be deposited in the specified
	 * place. The backtrack is only done for colors that are defined in this probe.
	 * 
	 * @param outPlace - the place for which the possible input places should be calculated
	 * @return array of places that are possible input places of the specified place
	 * @throws SimQPNException
	 */
	private Place[] calculatePossibleInputPlaces(Place outPlace) throws SimQPNException {
		Set<Place> possiblePlaces = new HashSet<Place>();
		Set<Integer> possibleModes = new HashSet<Integer>();

		// Iterate over all incoming transitions of the place
		for (Transition transition : outPlace.inTrans) {
			
			// Step 1: Determine all modes that can produce tokens of the
			//         in the place of a color defined in this probe.
			possibleModes.clear();
			int placeIdx = transition.getIndexOfOutputPlace(outPlace);
			if (placeIdx >= 0) {
				for (String color : colors) {
					int c = outPlace.getColorIndex(color);
					if (c >= 0) {
						for (int m = 0; m < transition.numModes; m++) {
							if (transition.outFunc[m][placeIdx][c] > 0) {
								possibleModes.add(m);
							}
						}
					}
				}
			}
	
			// Step 2: For all modes found in Step 1 determine the input places
			//         from which tokens of a color defined in this probe are consumed.
			placeLoop:
			for (Place inPlace : transition.inPlaces) {
				placeIdx = transition.getIndexOfInputPlace(inPlace);
				
				if ((placeIdx >= 0) && !possiblePlaces.contains(inPlace)) {
					for (String color : colors) {
						int c = inPlace.getColorIndex(color);
						if (c >= 0) {
							for (int m : possibleModes) {
								int tkCnt = transition.inFunc[m][placeIdx][c];
								if (tkCnt > 0) {
									possiblePlaces.add(inPlace);
									continue placeLoop; // The place is possible so continue directly with the next place
														// skipping the remaining colors
								}
							}
						}
					}
				}
			}
		}
		return possiblePlaces.toArray(new Place[possiblePlaces.size()]);
	}
}
