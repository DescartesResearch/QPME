/* ==============================================
 * QPME : Queueing Petri net Modeling Environment
 * ==============================================
 *
 * (c) Copyright 2003-2010, by Samuel Kounev and Contributors.
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

public class PlaceHelper extends XPathHelper {
	
	public static boolean isPlace(Element elem) {
		return "places".equals(elem.getParent().getName());
	}
	
	public static List<Element> listVisibleColors(Element place) {
		return query(place, "ancestor::*/colors/color");
	}
	
	public static boolean existsColorReferenceForColorId(Element place, String colorId) {
		return (getColorReferenceByColorId(place, colorId) != null);		
	}
	
	public static List<Element> listColorReferences(Element place) {
		return query(place, "color-refs/color-ref");
	}
	
	public static Element getColorReferenceByColorId(Element place, String colorId) {
		return element(place, "color-refs/color-ref[@color-id = '" + colorId + "']");
	}
	
	public static void addColorReference(Element place, Element colorRef) {
		Element colorRefContainer = place.element("color-refs");
		if (colorRefContainer == null) {
			colorRefContainer = place.addElement("color-refs");
		}
		
		// Add the color-ref to the current place.
		DocumentManager.addChild(colorRefContainer, colorRef);
	}
	
	public static void removeColorReference(Element place, Element colorRef) {
		// Get all the connections in any incidence function that use this color-ref and remove them.
		removeIncomingConnections(place, colorRef);
		removeOutgoingConnections(place, colorRef);
		
		// Remove the selected colorRef from the current node.
		DocumentManager.removeElement(colorRef);
	}
	
	public static void removeOutgoingConnections(Element place, Element colorRef) {
		List<Element> incidenceFunctionConnections = query(colorRef, "//*[@source-id = " + colorRef.attributeValue("id") + "]");
		for(Element connection : incidenceFunctionConnections) {
			DocumentManager.removeElement(connection);
		}
	}
	
	public static void removeIncomingConnections(Element place, Element colorRef) {
		List<Element> incidenceFunctionConnections = query(colorRef, "//*[@target-id = " + colorRef.attributeValue("id") + "]");
		for(Element connection : incidenceFunctionConnections) {
			DocumentManager.removeElement(connection);
		}
	}
	
	public static boolean isLocked(Element place) {
		return "true".equals(place.attributeValue("locked"));
	}
}
