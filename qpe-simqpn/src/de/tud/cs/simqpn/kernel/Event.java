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
 * Class Event
 *
 * @author Samuel Kounev
 * @version
 */
public class Event {
		
	public double 			time;
	public QueueingPlace	qPlace;
	public Token			token;

	/**
	 * Constructor
	 *
	 * @param time       - time of occurance
	 * @param name       - QueueingPlace involved
	 * @param name       - Token served  	  
	 */
	public Event(double time, QueueingPlace qPlace, Token token) {
		this.time	= time;
		this.qPlace	= qPlace;
		this.token	= token;		
	}
	
}
