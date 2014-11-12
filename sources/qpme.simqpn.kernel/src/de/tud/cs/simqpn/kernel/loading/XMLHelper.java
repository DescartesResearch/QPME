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
 * Original Author(s):  Jürgen Walter
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2013/05/13  Jürgen Walter     Created. Code has been taken from other classes during refactoring
 * 
 */
package de.tud.cs.simqpn.kernel.loading;

import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;

public class XMLHelper {
	private static Map<String, String> namespaceUris = new HashMap<String, String>();
	static {
		namespaceUris.put("xsi", XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI);
	}

	public static XPath createXPath(String xpath) {
		XPath xPath = DocumentHelper.createXPath(xpath);
		xPath.setNamespaceURIs(namespaceUris);
		return xPath;
	}

	public static Element getSettings(Element element, String configuration) {
		XPath xpathSelector = XMLHelper
				.createXPath("meta-attributes/meta-attribute[starts-with(@xsi:type, 'simqpn') and @configuration-name='"
						+ configuration + "']");
		Element elementSettings = (Element) xpathSelector
				.selectSingleNode(element);
		return elementSettings;
	}

}
