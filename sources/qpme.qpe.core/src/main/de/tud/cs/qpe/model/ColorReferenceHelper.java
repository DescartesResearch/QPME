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
 * Original Author(s):  Samuel Kounev and Simon Spinner
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2013        Simon Spinner     Created.
 * 
 */
package de.tud.cs.qpe.model;

import javax.xml.XMLConstants;

import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.tree.DefaultElement;

public class ColorReferenceHelper extends XPathHelper {
	
	public static Element getPlace(Element colorRef) {
		return element(colorRef, "../..");
	}
	
	public static Element createSimqpnBatchMeansColoRefConfigurationMetadata(Element colorRef, String configName) {
		if (PlaceHelper.isQueueingPlace(getPlace(colorRef))) {
			return createSimqpnColoRefConfigurationMetadata(colorRef, configName, "simqpn-batch-means-queueing-color-configuration");
		} else {
			return createSimqpnColoRefConfigurationMetadata(colorRef, configName, "simqpn-batch-means-color-configuration");
		}
	}
	
	public static Element createSimqpnReplDelColoRefConfigurationMetadata(Element colorRef, String configName) {
		if (PlaceHelper.isQueueingPlace(getPlace(colorRef))) {
			return createSimqpnColoRefConfigurationMetadata(colorRef, configName, "simqpn-replication-deletion-queueing-color-configuration");
		} else {
			return createSimqpnColoRefConfigurationMetadata(colorRef, configName, "simqpn-replication-deletion-color-configuration");
		}
	}
	
	public static Element createSimqpnWelchColoRefConfigurationMetadata(Element colorRef, String configName) {
		if (PlaceHelper.isQueueingPlace(getPlace(colorRef))) {
			return createSimqpnColoRefConfigurationMetadata(colorRef, configName, "simqpn-welch-queueing-color-configuration");
		} else {
			return createSimqpnColoRefConfigurationMetadata(colorRef, configName, "simqpn-welch-color-configuration");
		}
	}
	
	private static Element createSimqpnColoRefConfigurationMetadata(Element colorRef, String configName, String type) {
		Element metaAttributeContainer = colorRef.element("meta-attributes"); 
		if(metaAttributeContainer == null) {
			metaAttributeContainer = colorRef.addElement("meta-attributes");
		}

		Element metaAttribute = new DefaultElement("meta-attribute");
		metaAttribute.addAttribute(new QName("type", new Namespace("xsi", XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI)), type);
		metaAttribute.addAttribute("configuration-name", configName);
		
		DocumentManager.addChild(metaAttributeContainer, metaAttribute);
		return metaAttribute;
	}

}
