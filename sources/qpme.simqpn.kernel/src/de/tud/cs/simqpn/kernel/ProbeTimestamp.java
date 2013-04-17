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
 * Original Author(s):  Simon Spinner
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2010/07/25  Simon Spinner     Created.
 * 
 */
package de.tud.cs.simqpn.kernel;

/**
 * This class represents a timestamp that is created by a
 * probe when a token enters the probe's region. Instances
 * of this class can be associated with tokens. This class
 * is immutable, so that it can be replicated without cloning.
 * 
 * @author Simon Spinner
 *
 */
public final class ProbeTimestamp {
	
	public final int probeId;
	public final double timestamp;
	
	/**
	 * Constructor
	 * 
	 * @param probeId - id of the probe this timestamps belongs to
	 * @param timestamp - time in simulation time units
	 */
	public ProbeTimestamp(int probeId, double timestamp) {
		this.probeId = probeId;
		this.timestamp = timestamp;
	}

}
