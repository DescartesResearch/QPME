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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;

import org.dom4j.Attribute;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;

public class XPathHelper {
	
	private static Map<String, String> namespaceUris = new HashMap<String, String>();
	
	static {
		namespaceUris.put("xsi", XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI);
	}
	
	@SuppressWarnings("unchecked")
	public static List<Element> queryElements(Element elem, String xpath) {
		XPath xpathSelector = DocumentHelper.createXPath(xpath);
		xpathSelector.setNamespaceURIs(namespaceUris);
		return (List<Element>)xpathSelector.selectNodes(elem);
	}
	
	@SuppressWarnings("unchecked")
	public static List<Attribute> queryAttributes(Element elem, String xpath) {
		XPath xpathSelector = DocumentHelper.createXPath(xpath);
		xpathSelector.setNamespaceURIs(namespaceUris);
		return (List<Attribute>)xpathSelector.selectNodes(elem);
	}
	
	public static Element element(Element elem, String xpath) {
		XPath xpathSelector = DocumentHelper.createXPath(xpath);
		xpathSelector.setNamespaceURIs(namespaceUris);
		return (Element)xpathSelector.selectSingleNode(elem);
	}
}
