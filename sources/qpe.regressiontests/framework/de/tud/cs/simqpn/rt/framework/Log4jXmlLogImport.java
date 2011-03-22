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
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Level;
import org.apache.log4j.xml.Log4jEntityResolver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import de.tud.cs.simqpn.rt.framework.results.RunInfo;

/**
 * Parses the log4j xml output of a run. It is useful to detect whether errors
 * or warnings were issued.
 * 
 * @author Simon Spinner
 * 
 */
public class Log4jXmlLogImport {

	// The log4j xml output is not a complete xml file. It needs to be embedded
	// as an entity into the following template.
	private static final String xmlLogTemplate = "<?xml version=\"1.0\" ?>"
			+ "<!DOCTYPE log4j:eventSet PUBLIC \"-//APACHE//DTD LOG4J 1.2//EN\" \"log4j.dtd\" [<!ENTITY data SYSTEM \"%s\">]>"
			+ "<log4j:eventSet version=\"1.2\" xmlns:log4j=\"http://jakarta.apache.org/log4j/\">"
			+ "&data;" + "</log4j:eventSet>";

	/**
	 * Start the import.
	 * 
	 * @param logFile - File containing the xml output of the simulation run.
	 * @param info - Information of the current simulation run.
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void execute(File logFile, RunInfo info)
			throws ParserConfigurationException, IOException, SAXException {
		
		// Init xml parrser
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = factory.newDocumentBuilder();
		StringReader xmlContent = new StringReader(String.format(
				xmlLogTemplate, logFile));
		documentBuilder.setEntityResolver(new Log4jEntityResolver());

		Document doc = documentBuilder.parse(new InputSource(xmlContent));
		xmlContent.close();

		// Parse DOM
		Element root = doc.getDocumentElement();
		NodeList list = root.getElementsByTagName("log4j:event");

		for (int i = 0; i < list.getLength(); i++) {
			Element eventElem = (Element) list.item(i);
			String level = eventElem.getAttribute("level");
			String message = eventElem.getElementsByTagName("log4j:message")
					.item(0).getTextContent();
			
			// Check for errors and warnings.
			if (Level.ERROR.toString().equals(level)) {
				info.addErrorMessage(message);
			} else if (Level.WARN.toString().equals(level)) {
				info.addWarnMessage(message);
			}
		}
	}
}
