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

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

public class ConnectionHelper extends XPathHelper {
	public static Element createConnection(Element source, Element target) {
		Element container = element(source, "../../connections");
		Element connection = new DefaultElement("connection");
		connection.addAttribute("source-id", source.attributeValue("id"));
		connection.addAttribute("target-id", target.attributeValue("id"));
		DocumentManager.addChild(container, connection);
		return connection;
	}
	
	public static void addConnection(Element net, Element connection) {
		if(net.element("connections") == null) {
			net.addElement("connections");
		}
		DocumentManager.addChild(net.element("connections"), connection);
	}
	
	public static boolean existsConnection(Element source, Element target) {
		return (element(source, "../../connections/connection[@source-id = '"
							+ source.attributeValue("id")
							+ "' and @target-id = '"
							+ target.attributeValue("id") + "']") != null);
	}
	
	public static void removeConnection(Element connection) {
		DocumentManager.removeElement(connection);
	}
	
	public static boolean isLocked(Element connection) {
		return "true".equals(connection.attributeValue("locked"));
	}
	
	public static Element getSource(Element connection) {
		String sourceId = connection.attributeValue("source-id", "");
		return element(connection, "//place[@id = '"
				+ sourceId + "'] | //transition[@id = '" + sourceId + "']");
	}
	
	public static void setSource(Element connection, Element newSource) {
		DocumentManager.setAttribute(connection, "source-id", newSource
				.attributeValue("id"));
	}
	
	public static Element getTarget(Element connection) {
		String targetId = connection.attributeValue("target-id", "");
		return element(connection, "//place[@id = '" + targetId
				+ "'] | //transition[@id = '" + targetId + "']");
	}
	
	public static void setTarget(Element connection, Element newTarget) {
		DocumentManager.setAttribute(connection, "target-id", newTarget
				.attributeValue("id"));
	}
}
