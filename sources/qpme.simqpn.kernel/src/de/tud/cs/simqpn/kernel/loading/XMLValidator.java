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
 *  2013/05/13  Jürgen Walter     Created. Code has been taken from other classes during refactoring
 * 
 */
package de.tud.cs.simqpn.kernel.loading;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.XPath;

import de.tud.cs.simqpn.kernel.SimQPNException;

public class XMLValidator {
	
	private static Logger log = Logger.getLogger(XMLValidator.class);

	public static void validateInputNet(Element net) throws SimQPNException {
		XPath xpathSelector = XMLHelper.createXPath("//color-ref[@maximum-capacity > 0]");
		if(xpathSelector.selectSingleNode(net) != null) {
			log.error("Max population attribute currently not supported (Set to 0 for all places)");
			throw new SimQPNException();
		}		
		xpathSelector = XMLHelper.createXPath("//color-ref[@ranking > 0]");
		if(xpathSelector.selectSingleNode(net) != null) {
			log.error("Ranking attribute currently not supported (Set to 0 for all places)");
			throw new SimQPNException();
		}
		xpathSelector = XMLHelper.createXPath("//color-ref[@priority > 0]");
		if(xpathSelector.selectSingleNode(net) != null) {
			log.error("Priority attribute currently not supported (Set to 0 for all places)");
			throw new SimQPNException();
		}
		xpathSelector = XMLHelper.createXPath("//transition[@priority > 0]");
		if(xpathSelector.selectSingleNode(net) != null) {
			log.error("Transition priorities currently not supported (Set to 0 for all places)");
			throw new SimQPNException();
		}
	}


}
