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
	public Queue			queue;
	public Token			token;

	/**
	 * Constructor
	 *
	 * @param time       - Time of occurance
	 * @param queue      - Queue involved
	 * @param token      - Token to be served  	  
	 */
	public Event(double time, Queue queue, Token token) {
		this.time	= time;
		this.queue	= queue;
		this.token	= token;		
	}
}
