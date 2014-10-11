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
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2003/08/??  Samuel Kounev     Created.
 *  2008/11/29  Samuel Kounev     Added a reference to the Queue where the respective token is served. 
 * 
 */
 
package de.tud.cs.simqpn.kernel.executor;

import de.tud.cs.simqpn.kernel.entities.Token;
import de.tud.cs.simqpn.kernel.entities.queue.Queue;

/**
 * Class Event
 *
 * @author Samuel Kounev
 * @version
 */
public class QueueEvent implements Comparable{
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
	public QueueEvent(double time, Queue queue, Token token) {
		this.time	= time;
		this.queue	= queue;
		this.token	= token;		
	}

	@Override
	public int compareTo(Object a) {
		return (this.time < ((QueueEvent) a).time ? -1 : (this.time == ((QueueEvent) a).time ? 0 : 1));
	}
}
