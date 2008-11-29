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
 * Class Token
 *
 * @author Samuel Kounev
 * @version
 */
public class Token {
	public Place   place;
	public double  arrivTS; //NOTE: arrivTS is used only in statLevel >= 3, otherwise it is set to -1 
	public int     color;
	
	public Token(Place place, double arrivTS, int color) {
		this.place		= place;
		this.arrivTS	= arrivTS;  
		this.color		= color;			 
	}
}