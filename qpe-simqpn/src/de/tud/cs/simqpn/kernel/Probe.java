/* ==============================================
 * QPME : Queueing Petri net Modeling Environment
 * ==============================================
 *
 * (c) Copyright 2003-2010, by Samuel Kounev and Contributors.
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
 *  ----------  ----------------  -----------------------------------------------------------------------------
 *  2010/07/31	Simon Spinner	  Created.
 *  
 */
package de.tud.cs.simqpn.kernel;

import java.util.HashSet;
import java.util.Set;

import org.dom4j.Element;

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
	
	public Probe(int id, String xmlId, String name, String[] colors, Place startPlace, int startTrigger, Place endPlace, int endTrigger, int statsLevel, Element element) throws SimQPNException {
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
			probeStats = new ProbeStats(id, name, colors, statsLevel);
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
			Set<Place> markedPlaces = new HashSet<Place>();
			markedPlaces.add(startPlace);

			int ret;
			if ((startPlace == endPlace) && (startTrigger == ON_ENTRY) && (endTrigger == ON_EXIT)) {
				// no search needed only one place needs to be instrumented
				ret = MARK;
			} else {
				ret = markRoutesToEndPlace(endPlace, markedPlaces, new HashSet<Place>(), new HashSet<Place>());
			}			
			if (ret == MARK) {
				Simulator.log(2, "probes[" + id + "].instrumentations = { ");
				
				// Instrument start place
				if (startPlace == endPlace) {
					if ((startTrigger == ON_ENTRY) && (endTrigger == ON_EXIT)) {
						startPlace.addProbe(this, Place.PROBE_ACTION_START_ON_ENTRY_AND_END_ON_EXIT); 
						Simulator.log(2, startPlace.name + "(start_on_entry), ");
					} else if ((startTrigger == ON_EXIT) && (endTrigger == ON_ENTRY)) {
						startPlace.addProbe(this, Place.PROBE_ACTION_START_ON_EXIT_AND_END_ON_ENTRY);
						Simulator.log(2, startPlace.name + "(start_on_exit), ");
					} else {
						Simulator.logln("Error: illegal combination of start and end trigger.");
						Simulator.logln("Details: ");
						Simulator.logln("  probe-num           = " + id);
						Simulator.logln("  probe.id            = " + xmlId);
						Simulator.logln("  probe.name          = " + name);
						Simulator.logln("  probe.start-trigger = " + startPlace.name);
						Simulator.logln("  probe.end-trigger   = " + endPlace.name);
						throw new SimQPNException();
					}
				} else {
					if (startTrigger == ON_ENTRY) {
						startPlace.addProbe(this, Place.PROBE_ACTION_START_ON_ENTRY);
						Simulator.log(2, startPlace.name + "(start_on_entry), ");
					} else {
						startPlace.addProbe(this, Place.PROBE_ACTION_START_ON_EXIT);
						Simulator.log(2, startPlace.name + "(start_on_exit), ");
					}
				}
				
				// Instrument the places in between
				for (Place pl : markedPlaces) {
					if ((pl != startPlace) && (pl != endPlace)) {
						pl.addProbe(this, Place.PROBE_ACTION_TRANSFER);
						Simulator.log(2, pl.name + ", ");
					}
				}
				
				// Instrument the end place
				if (endTrigger == ON_EXIT) {
					if (startPlace != endPlace) endPlace.addProbe(this, Place.PROBE_ACTION_END_ON_EXIT);
					Simulator.log(2, endPlace.name + "(end_on_exit)");
				} else {
					if (startPlace != endPlace) endPlace.addProbe(this, Place.PROBE_ACTION_END_ON_ENTRY);
					Simulator.log(2, endPlace.name + "(end_on_entry)");
				}
				Simulator.logln(2, " }");
				
			} else {
				Simulator.logln("Error: start and end place of probe not connected.");
				Simulator.logln("Details: ");
				Simulator.logln("  probe-num         = " + id);
				Simulator.logln("  probe.id          = " + xmlId);
				Simulator.logln("  probe.name        = " + name);
				Simulator.logln("  probe.start-place = " + startPlace.name);
				Simulator.logln("  probe.end-place   = " + endPlace.name);
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
	public void start() throws SimQPNException {	
		if (statsLevel > 0)	
			probeStats.start();					
	}
	
	/**
	 * Method finish
	 *  	    
	 * 	 
	 * @param 
	 * @return
	 * @exception
	 */
	public void finish() throws SimQPNException {
		// Complete statistics collection
		if (statsLevel > 0)	
			probeStats.finish();					
	}

	/**
	 * Method report
	 *  	    
	 * @param 
	 * @return
	 * @exception
	 */
	public void report() throws SimQPNException {
		if (statsLevel > 0) {
			probeStats.printReport();					
			probeStats.addReportMetaData(element);
		}
	}
	
	/**
	 * Finds all possible routes between to places in the net.
	 * 
	 * @param end - end place
	 * @param markedPlaces - all places that lie on a possible route (the start place must already be in this set).
	 * @param notMarkedPlaces - all places that definitely not lie on a possible route
	 * @param currentRoute - all places that lie on the currently followed route
	 * @return MARK, if a route exists, NOT_MARK, if no route exists, INDETERMINATE, if no route can be found because of cycle.
	 * @throws SimQPNException
	 */
	private int markRoutesToEndPlace(Place end, Set<Place> markedPlaces, Set<Place> notMarkedPlaces, Set<Place> currentRoute) throws SimQPNException {
		// Note: The search starts at the end place and traverses the net in direction of the start place.
		
		if (markedPlaces.contains(end) && (!currentRoute.isEmpty())) {
			// we reached a place that is either the start place or another one for which
			// we already found a way to the start.
			markedPlaces.addAll(currentRoute);
			return MARK;
		}
		if (notMarkedPlaces.contains(end)) {
			// we reached a place for which we already determined that there is no way to
			// the start
			return NOT_MARK;
		}		
		if (currentRoute.contains(end)) {
			// we reached a place we have already visited on the current route -> there is a cycle
			return INDETERMINATE;
		}
		
		currentRoute.add(end);
		int ret = INDETERMINATE;
		Place[] possibleInPlaces = calculatePossibleInputPlaces(end);
		int placesLeft = possibleInPlaces.length; // Number of input places that are still INDETERMINATE
		while(placesLeft > 0) { // Iterate until all places are either MARK or NOT_MARK
			int done = 0;
			for (int i = 0; i < possibleInPlaces.length; i++) {
				if (possibleInPlaces[i] != null) {
					// Recursion
					switch (markRoutesToEndPlace(possibleInPlaces[i], markedPlaces, notMarkedPlaces, currentRoute)) {
					case MARK:
						ret = MARK;
						possibleInPlaces[i] = null;
						placesLeft--;
						done++;
						break;
					case NOT_MARK:
						if (ret != MARK) ret = NOT_MARK;
						possibleInPlaces[i] = null;
						placesLeft--;
						done++;
						break;
					case INDETERMINATE:
						break;
					}
				}
			}
			// Check that in the last iteration some routes were set to MARK or NOT_MARK. 
			if (done == 0) {
				markedPlaces.remove(end); // Maybe there was found a route to the start place. However we need to
										  // repeat the search from this place again in order to solve the indeterminate
										  // routes.
				ret = INDETERMINATE; // We could not determine for all routes leading to this place whether they
									 // come from the start place or not. So this place needs to be revisited later
									 // when more parts of the net are finished.
				break; // All input places are indeterminate
			}
		}
		
		currentRoute.remove(end);
		
		if (ret == NOT_MARK) {
			notMarkedPlaces.add(end);
		}
		
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
