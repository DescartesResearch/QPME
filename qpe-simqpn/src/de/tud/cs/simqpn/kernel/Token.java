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
 * Class Token
 *
 * @author Samuel Kounev
 * @version
 */
public class Token {
	public double arrivalTS;
	public int color;
	
	public Token(double arrivalTS, int color) {
		this.arrivalTS = arrivalTS;
		this.color = color;			 
	}
}