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
 * Original Author(s):  Samuel Kounev
 * Contributor(s): Simon Spinner  
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2003/08/??  Samuel Kounev     Created.
 *  2008/11/29  Samuel Kounev     Added run related parameters.   
 *  2008/12/13  Samuel Kounev     Added a field to store token colors that can reside in the respective location.                                
 *  2010/08/02  Simon Spinner     Added constant for PROBE stats.
 * 
 */
package de.tud.cs.simqpn.kernel;

/**
 * Class Stats
 *
 * 
 * @author Samuel Kounev
 * @version %I%, %G%
 */

public class Stats {
	// Types of statistics
	public static final int ORD_PLACE		= 0;
	public static final int QUE_PLACE_QUEUE	= 1;	 
	public static final int QUE_PLACE_DEP	= 2;
	public static final int QUEUE			= 3;
	public static final int PROBE           = 4;

	// NOTE: The following data is replicated from the respective data in the target Place or Queue to make Stats objects self-contained!
	public int 			id;						// ID of target place or queue			 		
	public String		name;					// Name of target place or queue					
	public int 			type;					// Type of statistics (ORD_PLACE, QUE_PLACE_QUEUE, QUE_PLACE_DEP, QUEUE) 
	public int			numColors;				// Number of colors
	public int			statsLevel;				// Determines the amount of statistics to be gathered during the run (see below).
	
	public String[]		colors;					// type!=QUEUE: Names of the colors that can reside in the respective location (currently not used for type==QUEUE).
	public boolean		completed;				// Indicates whether statistics collection has been completed and gathered statistics have been processed.	
	
	//NOTE: The five variables below are replicated from the respective data in Simulator in order to make the Stats objects self-contained! This data is then used in AggregateStats for example.
	public boolean		inRampUp;				// True if still in RampUp period (no measurements taken).
	public double		endRampUpClock;			// Clock at the end of RampUp, i.e. beginning of the measurement period.
	public double		endRunClock;			// Clock at the end of the run.
	public double		msrmPrdLen;				// Duration of the measurement period in simulation time.
	public double		runWallClockTime;		// Total duration of the run in seconds. 
		
	public String 		fileSep 	= System.getProperty("file.separator");
	public String 		statsDir 	= Simulator.statsDir;
	
	/* NOTE:
	 * statsLevel determines the amount of statistics to be gathered during the run:
	 *   Level 1: Token Throughput (Arrival/Departure Rates)
	 *   Level 2: + Token Population, Token Occupancy and Queue Utilization
	 *   Level 3: + Token Sojourn Times (sample mean and variance + steady state point estimates and confidence intervals)
	 *   Level 4: + Token Sojourn Time Histograms
	 *   Level 5: + Record Sojourn Times in a file 
	 */	 	 	 
	 	 
	/**
	 * Constructor
	 * 
	 * @param id          - global id of the place
	 * @param name        - name of the place
	 * @param type		  - type of statistics (ORD_PLACE, QUE_PLACE_QUEUE, QUE_PLACE_DEP or QUEUE)
	 * @param numColors   - number of colors
	 * @param statsLevel  - determines the amount of statistics to be gathered during the run
	 */
	public Stats(int id, String name, int type, int numColors, int statsLevel) {		
		this.id 				= id;
		this.name 				= name;		
		this.type				= type;
		this.numColors			= numColors;
		this.statsLevel			= statsLevel;
		this.completed			= false;		
		this.inRampUp 			= true;
		this.endRampUpClock 	= 0;
		this.endRunClock 		= 0;
		this.msrmPrdLen 		= 0;
		this.runWallClockTime 	= 0;
	}	
}
