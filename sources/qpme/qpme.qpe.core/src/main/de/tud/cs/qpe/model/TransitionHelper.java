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
�* http://www.eclipse.org/legal/epl-v10.html
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
 * Original Author(s):  Samuel Kounev and Christofer Dutz
 * Contributor(s):  
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2010/06/16  Simon Spinner     Created.
 * 
 */

package de.tud.cs.qpe.model;

import java.util.List;

import org.dom4j.Element;

public class TransitionHelper extends XPathHelper {

	public static boolean isTransition(Element elem) {
		return "transitions".equals(elem.getParent().getName());
	}

	public static boolean isImmediateTranstion(Element elem) {
		return "immediate-transition".equals(elem.attributeValue("type"));
	}
	
	public static boolean isTimedTransition(Element elem) {
		return "timed-transition".equals(elem.attributeValue("type"));
	}

	public static List<Element> listModes(Element transition) {
		return query(transition, "modes/mode");
	}

	public static void addMode(Element transition, Element mode) {
		if (transition.element("modes") == null) {
			transition.addElement("modes");
		}
		DocumentManager.addChild(transition.element("modes"), mode);
	}

	public static Element getModeByName(Element transition, String name) {
		return element(transition, "modes/mode[@name='" + name + "']");
	}

	public static void removeMode(Element transition, Element mode) {
		IncidenceFuntionHelper.removeAllConnectionsToMode(transition, mode);
		IncidenceFuntionHelper.removeAllConnectionsFromMode(transition, mode);

		DocumentManager.removeElement(mode);
	}

	public static boolean isModeReferencedInIncidenceFunction(Element mode) {
		List<Element> connections = query(
				mode,
				"//transition/connections/connection[@source-id = '"
						+ mode.attributeValue("id", "")
						+ "'] | //transition/connections/connection[@target-id = '"
						+ mode.attributeValue("id", "") + "']");
		return connections.size() != 0;
	}
}
