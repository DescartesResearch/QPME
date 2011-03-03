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
 *  2010/06/26  Simon Spinner     Created.
 * 
 */
package de.tud.cs.qpe.model;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

import de.tud.cs.qpe.utils.IdGenerator;

public class IncidenceFuntionHelper extends XPathHelper {
	public static List<Element> listAllConnectionsFromOrToPlace(Element transition, Element place) {
		List<Element> colorRefs = PlaceHelper.listColorReferences(place);
		List<Element> connections = new ArrayList<Element>();
		for(Element cRef : colorRefs) {
			connections.addAll(listAllConnectionsFromOrToColorRef(transition, cRef));
		}
		return connections;
	}
	
	public static List<Element> listAllConnectionsFromOrToColorRef(Element transition, Element colorRef) {
		return query(transition, "connections/connection[@source-id = '"
							+ colorRef.attributeValue("id", "")
							+ "' or @target-id = '"
							+ colorRef.attributeValue("id", "") + "']");
	}
		
	public static void addConnection(Element transition, Element connection) {
		if(transition.element("connections") == null) {
			transition.addElement("connections");
		}
		DocumentManager.addChild(transition.element("connections"), connection);
	}
	
	public static void removeConnection(Element transition, Element connection) {
		DocumentManager.removeElement(connection);
	}
	
	public static void addConnectionFromColorRefToMode(Element transition, Element mode, Element colorRef, int count) {
		if(transition.element("connections") == null) {
			transition.addElement("connections");
		}
		Element newConnection = new DefaultElement("connection");
		DocumentManager.addChild(transition.element("connections"), newConnection);
		DocumentManager.setAttribute(newConnection, "id", IdGenerator.get());
		DocumentManager.setAttribute(newConnection, "count", Integer.toString(count));
		DocumentManager.setAttribute(newConnection, "source-id", colorRef.attributeValue("id"));
		DocumentManager.setAttribute(newConnection, "target-id", mode.attributeValue("id"));
	}
	
	public static boolean existsConnectionFromColorRefToMode(Element transition, Element mode, Element colorRef) {
		return (element(transition, "connections/connection[@source-id='" + colorRef.attributeValue("id") + "' and @target-id='" 
				+ mode.attributeValue("id") + "']") != null); 
	}
	
	public static void removeAllConnectionsToMode(Element transition, Element mode) {
		List<Element> incidenceFunctionConnections = query(transition, "connections/connection[@target-id = " + mode.attributeValue("id") + "]");
		for(Element connection : incidenceFunctionConnections) {
			DocumentManager.removeElement(connection);
		}
	}
	
	public static void addConnectionFromModeToColorRef(Element transition, Element mode, Element colorRef, int count) {
		if(transition.element("connections") == null) {
			transition.addElement("connections");
		}
		Element newConnection = new DefaultElement("connection");
		DocumentManager.addChild(transition.element("connections"), newConnection);
		DocumentManager.setAttribute(newConnection, "id", IdGenerator.get());
		DocumentManager.setAttribute(newConnection, "count", Integer.toString(count));
		DocumentManager.setAttribute(newConnection, "source-id", mode.attributeValue("id"));
		DocumentManager.setAttribute(newConnection, "target-id", colorRef.attributeValue("id"));
	}
	
	public static boolean existsConnectionFromModeToColorRef(Element transition, Element mode, Element colorRef) {
		return (element(transition, "connections/connection[@source-id='" + mode.attributeValue("id") + "' and @target-id='" 
				+ colorRef.attributeValue("id") + "']") != null); 
	}
	
	public static void removeAllConnectionsFromMode(Element transition, Element mode) {
		List<Element> incidenceFunctionConnections = query(transition, "connections/connection[@source-id = " + mode.attributeValue("id") + "]");
		for(Element connection : incidenceFunctionConnections) {
			DocumentManager.removeElement(connection);
		}
	}
}
