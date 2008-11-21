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
 * Class Node
 *
 * @author Samuel Kounev
 * @version
 */

public class Node {

	public int 		id;		// Note that ids are unique only within a node class (e.g. Transition or Place)
	public String	name;
	
	/**
	 * Constructor
	 *
	 * @param id         - id of the node
	 * @param name       - name of the node		  
	 */
	public Node(int id, String name) {
		this.id = id;
		this.name = name; 
	}

}
