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
 *  2006        Christofer Dutz   Created.
 * 
 */
package de.tud.cs.qpe.editors.net.gef.palette.templates;

import javax.xml.XMLConstants;

import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.eclipse.draw2d.geometry.Point;

import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.utils.IdGenerator;

public class SubnetPlace extends Place {
	private static final long serialVersionUID = 8679419958508543570L;
	
	public SubnetPlace() {
		super();
		addAttribute(new QName("type", new Namespace("xsi", XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI)), "subnet-place");
//		Element fixed = addElement("fixed");
//		fixed.addElement("input-place");
//		fixed.addElement("input-transition");
//		fixed.addElement("actual-population");
//		fixed.addElement("output-place");
		
		Element subnet = addElement("subnet");
		subnet.addElement("colors");
		Element places = subnet.addElement("places");
		// Add the input-place.
		Element inputPlace = places.addElement("place");
		inputPlace.addAttribute(new QName("type", new Namespace("xsi", XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI)), "ordinary-place");
		inputPlace.addAttribute("name", "input-place");
		inputPlace.addAttribute("departure-discipline", "NORMAL");
		inputPlace.addAttribute("id", IdGenerator.get());
		inputPlace.addAttribute("locked", "true");
		DocumentManager.setLocation(inputPlace, new Point(20,120));

		// Add the actual-population-place.
		Element actualPopulation = places.addElement("place");
		actualPopulation.addAttribute(new QName("type", new Namespace("xsi", XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI)), "ordinary-place");
		actualPopulation.addAttribute("name", "actual population");
		actualPopulation.addAttribute("departure-discipline", "NORMAL");
		actualPopulation.addAttribute("id", IdGenerator.get());
//		actualPopulation.addAttribute("locked", "true");
		DocumentManager.setLocation(actualPopulation, new Point(220,20));

		// Add the output-place.
		Element outputPlace = places.addElement("place");
		outputPlace.addAttribute(new QName("type", new Namespace("xsi", XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI)), "ordinary-place");
		outputPlace.addAttribute("name", "output-place");
		outputPlace.addAttribute("departure-discipline", "NORMAL");
		outputPlace.addAttribute("id", IdGenerator.get());
		outputPlace.addAttribute("locked", "true");
		DocumentManager.setLocation(outputPlace, new Point(420,120));

		Element transitions = subnet.addElement("transitions");
		// Add the input-transition.
		Element inputTransition = transitions.addElement("transition");
		inputTransition.addAttribute(new QName("type", new Namespace("xsi", XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI)), "immediate-transition");
		inputTransition.addAttribute("name", "input-transition");
		inputTransition.addAttribute("weight", "1.0");
		inputTransition.addAttribute("priority", "0");
		inputTransition.addAttribute("id", IdGenerator.get());
//		inputTransition.addAttribute("locked", "true");
		DocumentManager.setLocation(inputTransition, new Point(120,120));

		// Add the output-transition.
		Element outputTransition = transitions.addElement("transition");
		outputTransition.addAttribute(new QName("type", new Namespace("xsi", XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI)), "immediate-transition");
		outputTransition.addAttribute("name", "output-transition");
		outputTransition.addAttribute("weight", "1.0");
		outputTransition.addAttribute("priority", "0");
		outputTransition.addAttribute("id", IdGenerator.get());
//		outputTransition.addAttribute("locked", "true");
		DocumentManager.setLocation(outputTransition, new Point(320,120));

		Element connections = subnet.addElement("connections");
		Element connection = connections.addElement("connection");
		connection.addAttribute("source-id", inputPlace.attributeValue("id"));
		connection.addAttribute("target-id", inputTransition.attributeValue("id"));
		connection.addAttribute("id", IdGenerator.get());
//		connection.addAttribute("locked", "true");
		connection = connections.addElement("connection");
		connection.addAttribute("source-id", inputTransition.attributeValue("id"));
		connection.addAttribute("target-id", actualPopulation.attributeValue("id"));
		connection.addAttribute("id", IdGenerator.get());
//		connection.addAttribute("locked", "true");
		connection = connections.addElement("connection");
		connection.addAttribute("source-id", actualPopulation.attributeValue("id"));
		connection.addAttribute("target-id", outputTransition.attributeValue("id"));
		connection.addAttribute("id", IdGenerator.get());
//		connection.addAttribute("locked", "true");
		connection = connections.addElement("connection");
		connection.addAttribute("source-id", outputTransition.attributeValue("id"));
		connection.addAttribute("target-id", outputPlace.attributeValue("id"));
		connection.addAttribute("id", IdGenerator.get());
//		connection.addAttribute("locked", "true");
	}
}
