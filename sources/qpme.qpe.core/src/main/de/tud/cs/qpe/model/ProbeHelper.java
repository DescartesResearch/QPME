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
 *  2010/03/06  Simon Spinner     Created.
 * 
 */
package de.tud.cs.qpe.model;

import java.util.List;

import org.dom4j.Element;

public class ProbeHelper extends XPathHelper {	
	public static List<Element> listColorReferences(Element place) {
		return queryElements(place, "color-refs/color-ref");
	}

	public static void addColorReference(Element probe, Element colorRef) {
		Element colorRefContainer = probe.element("color-refs");
		if (colorRefContainer == null) {
			colorRefContainer = probe.addElement("color-refs");
		}
		
		// Add the color-ref to the current place.
		DocumentManager.addChild(colorRefContainer, colorRef);	
	}

	public static void removeColorReference(Element model, Element colorRef) {
		DocumentManager.removeElement(colorRef);		
	}

	public static boolean isProbe(Element newModel) {
		return "probe".equals(newModel.getName());
	}
	
	public static Element createSimqpnProbeConfigurationMetadata(Element place, String configName) {
		return PlaceHelper.createSimqpnPlaceConfigurationMetadata(place, configName);
	}
}
