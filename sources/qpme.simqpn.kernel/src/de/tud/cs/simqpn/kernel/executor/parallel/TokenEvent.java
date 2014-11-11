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
 * Original Author(s):  Jürgen Walter
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2014/??/??  Jürgen Walter     Created.
 * 
 */
package de.tud.cs.simqpn.kernel.executor.parallel;

import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.Token;
import de.tud.cs.simqpn.kernel.entities.Transition;

/**
 * Container to pass a token from one {@link parallel.LP Logical Process} to another
 * 
 * @author Jürgen Walter
 */
public class TokenEvent {
	/** time the token arrives in a LP */
	private double time;
	private Place place;
	private int color;
	private int number;
	private Token[] tkCopyBuffer;
	private Transition firingTrans;

	/**
	 * Constructor
	 * @param time the timestamp at which this event can be processed
	 * @param firingTrans the transition which fired
	 * @param place the recieving place
	 * @param color the color of the token(s)
	 * @param number the number of tokens
	 * @param tkCopyBuffer
	 */
	public TokenEvent(double time, Transition firingTrans, Place place, int color, int number, Token[] tkCopyBuffer) {
		this.time = time;
		this.firingTrans = firingTrans;
		this.place = place;
		this.color = color;
		this.number = number;
		this.tkCopyBuffer = tkCopyBuffer;
	}


	/**
	 * @return the incomingTime
	 */
	public double getTime() {
		return time;
	}

	/**
	 * @return the color
	 */
	public int getColor() {
		return color;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @return the place
	 */
	public Place getPlace() {
		return place;
	}

	/**
	 * @return the tkCopyBuffer
	 */
	public Token[] getTkCopyBuffer() {
		return tkCopyBuffer;
	}


	/**
	 * @return the firingTrans
	 */
	public Transition getFiringTrans() {
		return firingTrans;
	}

}
