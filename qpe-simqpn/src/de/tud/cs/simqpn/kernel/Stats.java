/*
 * Copyright (c) 2009 Samuel Kounev. All rights reserved.
 *    
 * The use, copying, modification or distribution of this software and its documentation for 
 * any purpose is NOT allowed without the written permission of the author.
 *  
 * This source code is provided as is, without any express or implied warranty.
 *
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------
 *  2003/08/??  Samuel Kounev     Created.
 *  ...                                 
 * 
 */

package de.tud.cs.simqpn.kernel;

/**
 * Class QueueStats
 *
 * 
 * @author Samuel Kounev
 * @version %I%, %G%
 */

public class Stats {
	// Types of statistics
	public static final int PLACE		= 0;
	public static final int QUEUE		= 1;	 
	public static final int DEPOSITORY	= 2;
	
	public int 			id;						// ID of target node			 		
	public String		name;					// Name of target node			
	public int 			type;					// Type of statistics (QUEUE, PLACE or DEPOSITORY)		
	public int			numColors;				// Number of colors
	public int			statsLevel;				// Determines the amount of statistics to be gathered during the run (see below).
	public boolean		completed;				// Indicates whether statistics collection has been completed and gathered statistics have been processed.
										
	
	public String 		fileSep 	= System.getProperty("file.separator");
	public String 		statsDir 	= Simulator.statsDir;
	
	/* NOTE:
	 * statsLevel determines the amount of statistics to be gathered during the run:
	 *   Level 1: Token Throughput (Arrival/Departure Rates)
	 *   Level 2: + Token Population, Utilization
	 *   Level 3: + Token Sojourn Times (sample mean and variance + steady state point estimates and confidence intervals)
	 *   Level 4: + Record Sojourn Times in a file 
	 */	 	 	 
	 	 
	/**
	 * Constructor
	 * 
	 * @param id          - global id of the place
	 * @param name        - name of the place
	 * @param type		  - type of statistics (PLACE, QUEUE or DEPOSITORY)
	 * @param numColors   - number of colors
	 * @param statsLevel  - determines the amount of statistics to be gathered during the run
	 */
	public Stats(int id, String name, int type, int numColors, int statsLevel) {		
		this.id 			= id;
		this.name 			= name;		
		this.type			= type;
		this.numColors		= numColors;
		this.statsLevel		= statsLevel;
		this.completed		= false;
	}
	
}
