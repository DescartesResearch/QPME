/*
 * Copyright (c) 2006 Samuel Kounev. All rights reserved.
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
	
	public int 			id;				 		
	public String		name;			
	public int 			type;			
	public int			numColors;		
	public int			statsLevel;		
	public boolean		completed;		
										
	
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
