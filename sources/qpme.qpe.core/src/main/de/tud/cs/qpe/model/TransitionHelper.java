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
 * Original Author(s):  Samuel Kounev and Simon Spinner
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

import javax.xml.XMLConstants;

import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;

public class TransitionHelper extends XPathHelper {

	public static boolean isTransition(Element elem) {
		return "transition".equals(elem.getName());
	}

	public static boolean isImmediateTranstion(Element elem) {
		return "immediate-transition".equals(elem.attributeValue(new QName("type", new Namespace("xsi", XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI))));
	}
	
	public static boolean isTimedTransition(Element elem) {
		return "timed-transition".equals(elem.attributeValue(new QName("type", new Namespace("xsi", XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI))));
	}

	public static List<Element> listModes(Element transition) {
		return queryElements(transition, "modes/mode");
	}

	public static void addMode(Element transition, Element mode) {
		if (transition.element("modes") == null) {
			transition.addElement("modes");
		}
		DocumentManager.addChild(transition.element("modes"), mode);
	}
	
	public static List<Element> listConnections(Element transition) {
		return queryElements(transition, "connections/connection");
	}
	
	public static Element getMode(Element transition, String id) {
		return element(transition, "modes/mode[@id='" + id + "']");
	}

	public static Element getModeByName(Element transition, String name) {
		return element(transition, "modes/mode[@name='" + name + "']");
	}

	public static void removeMode(Element transition, Element mode) {
		IncidenceFunctionHelper.removeAllConnectionsToMode(transition, mode);
		IncidenceFunctionHelper.removeAllConnectionsFromMode(transition, mode);

		DocumentManager.removeElement(mode);
	}

	public static boolean isModeReferencedInIncidenceFunction(Element mode) {
		List<Element> connections = queryElements(
				mode,
				"//transition/connections/connection[@source-id = '"
						+ mode.attributeValue("id", "")
						+ "'] | //transition/connections/connection[@target-id = '"
						+ mode.attributeValue("id", "") + "']");
		return connections.size() != 0;
	}
	
	public static List<Element> listIncomingConnections(Element transition) {
		return queryElements(transition, "//connection[@target-id = '" + transition.attributeValue("id") + "']");
	}
	
	public static List<Element> listOutgoingConnections(Element transition) {
		return queryElements(transition, "//connection[@source-id = '" + transition.attributeValue("id") + "']");
	}
}
