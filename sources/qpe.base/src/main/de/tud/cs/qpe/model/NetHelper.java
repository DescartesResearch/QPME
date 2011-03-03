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

public class NetHelper extends XPathHelper {
	public static List<Element> listColors(Element net) {
		return query(net, "./colors/color");
	}
	
	public static Element getColorByReference(Element model, Element colorRef) {
		return getColorById(model, colorRef.attributeValue("color-id"));
	}
	
	public static Element getColorById(Element model, String id) {
		return element(model, "//color[@id = '" + id + "']");
	}
	
	public static boolean existsColorWithName(Element model, String name) {
		return (element(model, "//color[@name='" + name + "']") != null);
	}
	
	public static void addColor(Element net, Element color) {
		DocumentManager.addChild(net.element("colors"), color);
	}
	
	public static void removeColor(Element net, Element color) {
		DocumentManager.removeElement(color);
		
		// Remove all references to that color.
		List<Element> references = query(net, "//color-ref[@color-id = '" + color.attributeValue("id") + "']");
		for(Element ref : references) {			
			DocumentManager.removeElement(ref);
			
			// Remove all usages of the deleted color refs in
			// any incidence-function.
			List<Element> incidenceFunctionConnections = query(net, "//connection[@source-id = '" + ref.attributeValue("id") + "' or @target-id = '"
					+ ref.attributeValue("id") + "']");
			for(Element con : incidenceFunctionConnections) {
				DocumentManager.removeElement(con);
			}
		}
	}

	public static boolean isColorReferencedInNet(Element net, Element color) {
		return (element(net, "//color-ref[@color-id = '" + color.attributeValue("id") + "']") != null);
	}
	
	public static Element getQueueByName(Element model, String name) {
		return element(model, "//queue[@name = '"+ name + "']");
	}
	
	public static Element getQueueById(Element model, String id) {
		return element(model, "//queue[@id = '" + id + "']");
	}
	
	public static List<Element> listQueues(Element model) {
		return query(model, "//queues/queue");
	}
	
	public static boolean isQueueReferencedInNet(Element net, Element queue) {
		return (element(net, "//queue-ref[@queue-id = '" + queue.attributeValue("id") + "']") != null);
	}
	
	public static void addQueue(Element model, Element newQueue) {
		Element queues = element(model, "//queues");		
		DocumentManager.addChild(queues, newQueue);
	}
	
	public static boolean existsQueueWithName(Element model, String name) {
		return (getQueueByName(model, name) != null);
	}
	
	public static void removeQueue(Element model, Element queue) {
		DocumentManager.removeElement(queue);

		// Remove all references to that queue.
		List<Element> references = query(model, "//queue-ref[@queue-id = '" + queue.attributeValue("id") + "']");
		for (Element ref : references) {
			DocumentManager.removeElement(ref);
		}
	}
	
	public static Element getNetOfPlace(Element place) {
		return place.getParent().getParent();
	}
	
	public static Element getNetOfTransition(Element transition) {
		return transition.getParent().getParent();
	}

}
