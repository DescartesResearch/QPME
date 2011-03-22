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
 * Contributor(s): Simon Spinner  
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2003/08/??  Samuel Kounev     Created.
 *  2008/11/29  Samuel Kounev     Added reference to the place where the token is located.
 *  2010/07/24	Simon Spinner	  Added timestamps map for probes.                                 
 * 
 */
package de.tud.cs.simqpn.kernel;


/**
 * Class Token
 *
 * @author Samuel Kounev
 * @version
 */
public final class Token {
	public final Place   place;
	public double        arrivTS; //NOTE: arrivTS is used only in statLevel >= 3, otherwise it is set to -1 
	public final int     color;
	public final ProbeTimestamp[] 
				         probeData; //NOTE: probeData is only used if tracking is enabled for place and color of this token.
	
	public Token(Place place, int color) {
		this.place		= place;
		this.arrivTS	= -1;  
		this.color		= color;
		this.probeData = null;
	}
	
	public Token(Place place, int color, ProbeTimestamp[] probeData) {
		this.place		= place;
		this.arrivTS	= -1;  
		this.color		= color;
		this.probeData = probeData;
	}
}