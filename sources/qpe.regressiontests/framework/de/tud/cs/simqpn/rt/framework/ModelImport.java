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
 * Original Author(s):  Simon Spinner
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2011/01/21  Simon Spinner     Created.         
 * 
 */

package de.tud.cs.simqpn.rt.framework;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import de.tud.cs.simqpn.rt.framework.results.SimulationResults;
import de.tud.cs.simqpn.rt.framework.results.Statistics;
import de.tud.cs.simqpn.rt.framework.results.Statistics.ElementType;

/**
 * Imports the model structure consisting of places and contained color
 * references, and initializes a results container with this information.
 * 
 * This is required because in console outputs of earlier versions only the
 * color index is included not the color name. In order to be able to determine
 * the color name corresponding to an index, this class populates the results
 * container with places, queues and their color references.
 * 
 * @author Simon Spinner
 * 
 */
public class ModelImport {

	/**
	 * Start import.
	 * 
	 * @param results
	 *            - Container for the results.
	 * @param modelFile
	 *            - Model to be parsed.
	 * @throws Exception
	 */
	public void initWithModelStructure(SimulationResults results, File modelFile)
			throws Exception {
		// Init DOM with XPath capabilities
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = factory.newDocumentBuilder();
		Document doc = documentBuilder.parse(modelFile);

		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();

		// Read all places
		NodeList places = (NodeList) xpath.evaluate("//place", doc,
				XPathConstants.NODESET);
		for (int i = 0; i < places.getLength(); i++) {
			Element xmlElem = (Element) places.item(i);
			NodeList colorRefs = (NodeList) xpath.evaluate(
					"./color-refs/color-ref", xmlElem, XPathConstants.NODESET);

			String type = xmlElem.getAttribute("type");
			if (type.equals("ordinary-place")) {
				Statistics elem = new Statistics(xmlElem.getAttribute("name"),
						ElementType.ORDINARY_PLACE);

				for (int j = 0; j < colorRefs.getLength(); j++) {
					Element xmlColorRef = (Element) colorRefs.item(j);
					String colorName = (String) xpath.evaluate(
							"//color[@id='"
									+ xmlColorRef.getAttribute("color-id")
									+ "']/@name", doc, XPathConstants.STRING);
					Statistics colorRef = new Statistics(colorName,
							ElementType.COLOR);

					elem.addChildStats(colorRef);
				}
				results.addStatistics(elem);
			} else if (type.equals("queueing-place")) {
				// For queueing places we need to create two stats objects, one
				// for the queue and one for the depository
				Statistics queueElem = new Statistics(
						xmlElem.getAttribute("name"), ElementType.QPLACE_QUEUE);
				Statistics depositoryElem = new Statistics(
						xmlElem.getAttribute("name"),
						ElementType.QPLACE_DEPOSITORY);
				for (int j = 0; j < colorRefs.getLength(); j++) {
					Element xmlColorRef = (Element) colorRefs.item(j);
					String colorName = (String) xpath.evaluate(
							"//color[@id='"
									+ xmlColorRef.getAttribute("color-id")
									+ "']/@name", doc, XPathConstants.STRING);
					Statistics queueColorRef = new Statistics(colorName,
							ElementType.COLOR);
					Statistics depositoryColorRef = new Statistics(colorName,
							ElementType.COLOR);

					queueElem.addChildStats(queueColorRef);
					depositoryElem.addChildStats(depositoryColorRef);
				}

				results.addStatistics(queueElem);
				results.addStatistics(depositoryElem);
			}
		}
	}
}
