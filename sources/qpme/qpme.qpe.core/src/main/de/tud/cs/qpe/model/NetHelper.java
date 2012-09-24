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

import javax.xml.XMLConstants;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.tree.DefaultElement;

public class NetHelper extends XPathHelper {
	
	public static Element getQpePositionMetaAttribute(Element element) {
		return element(element, "meta-attributes/meta-attribute[@xsi:type = 'location-attribute']");
	}
	
	public static String getFullyQualifiedName(Element element) {		
		List<Element> ancestorPlaces = queryElements(element, "parent::node()/ancestor::place");
		StringBuilder qualifiedName = new StringBuilder();
		for (Element place : ancestorPlaces) {
			qualifiedName.append(place.attributeValue("name")).append(".");
		}
		qualifiedName.append(element.attributeValue("name"));
		return qualifiedName.toString();
	}
	
	public static List<Element> listProbes(Element net) {
		return queryElements(net, "./probes/probe");
	}
	
	public static boolean existsProbeWithName(Element model, String name) {
		return (element(model, "//probe[@name='" + name + "']") != null);
	}
	
	public static void addProbe(Element net, Element probe) {
		Element probes = net.element("probes");
		if (probes == null) {
			probes = new DefaultElement("probes");
			DocumentManager.addChild(net, probes, false);
		}
		DocumentManager.addChild(probes, probe);
	}
	
	public static void removeProbe(Element net, Element probe) {
		DocumentManager.removeElement(probe);
	}
	
	public static List<Element> listColors(Element net) {
		return queryElements(net, "./colors/color");
	}
	
	public static List<Element> listAllColors(Element net) {
		return queryElements(net, "//color");
	}
	
	public static Element getColorByReference(Element colorRef) {
		return getColorById(colorRef, colorRef.attributeValue("color-id"));
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
		List<Element> references = queryElements(net, "//color-ref[@color-id = '" + color.attributeValue("id") + "']");
		for(Element ref : references) {			
			DocumentManager.removeElement(ref);
			
			// Remove all usages of the deleted color refs in
			// any incidence-function.
			List<Element> incidenceFunctionConnections = queryElements(net, "//connection[@source-id = '" + ref.attributeValue("id") + "' or @target-id = '"
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
		return queryElements(model, "//queues/queue");
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
		List<Element> references = queryElements(model, "//queue-ref[@queue-id = '" + queue.attributeValue("id") + "']");
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

	public static List<Element> listPlaces(Element net) {
		return queryElements(net, "places/place");
	}
	
	public static List<Element> listTransitions(Element net) {
		return queryElements(net, "transitions/transition");
	}
	
	public static List<Element> listAllPlaces(Element model) {
		return queryElements(model, "//place");
	}

	public static Element getPlaceById(Element model, String id) {
		return element(model, "//place[@id = '" + id + "']");
	}
	
	public static Element getMetadata(Element element, String configName) {
		return element(element, "./meta-attributes/meta-attribute[starts-with(@xsi:type, 'simqpn') and @configuration-name='" + configName + "']");
	}
	
	public static Element createSimqpnConfigurationMetadata(Element element, String configName) {		
		Element metaAttributeContainer = element.element("meta-attributes"); 
		if(metaAttributeContainer == null) {
			metaAttributeContainer = element.addElement("meta-attributes");
		}

		Element metaAttribute = new DefaultElement("meta-attribute");
		metaAttribute.addAttribute(new QName("type", new Namespace("xsi", XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI)), "simqpn-configuration");
		metaAttribute.addAttribute("configuration-name", configName);
		
		DocumentManager.addChild(metaAttributeContainer, metaAttribute);
		return metaAttribute;
	}
	
	public static List<Element> listConfigurations(Element net) {
		return queryElements(net, "/net/meta-attributes/meta-attribute[@xsi:type='simqpn-configuration']");
	}
	
	public static List<Element> listAllMetadata(Element net, String configName) {
		return queryElements(net, "//meta-attribute[starts-with(@xsi:type, 'simqpn') and @configuration-name='" + configName + "']");
	}
	
	public static List<Element> listAllMetadataExceptLocation(Element net) {
		return queryElements(net, "//meta-attribute[@xsi:type != 'location-attribute']");
	}
	
	public static Element getElement(Element elem, String id) {
		return element(elem, "//*[@id = '" + id + "']");
	}

	public static List<Element> listAllConnections(Element net) {
		return queryElements(net, "//connection");
		
	}

	public static List<Element> listAllTransitions(Element net) {
		return queryElements(net, "//transition");
	}
	
	public static List<Element> listAllModes(Element net) {
		return queryElements(net, "//mode");
	}
	
	public static List<Element> listAllColorReferences(Element net) {
		return queryElements(net, "//color-ref");
	}
	
	public static List<Attribute> listAllUsages(Element net, String id) {
		return queryAttributes(net, "//@*[. = '" + id + "']");
	}
	
	public static boolean existsElementAtLocation(Element net, int x, int y) {
		return element(net, "//meta-attribute[@xsi:type = 'location-attribute' and @location-x='"
				+ x + "' and @location-y='" + y + "']") != null;
	}
	
}
