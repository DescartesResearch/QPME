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

public class SubnetHelper extends XPathHelper {
	
	public static boolean isColorLocallyDefined(Element subnet, String colorId) {
		return (element(subnet, "colors/color[@id ='" + colorId + "']") != null);
	}
	
	public static List<Element> listLocallyDefinedColors(Element subnet) {
		return query(subnet, "colors/color");
	}
	
	public static List<Element> listVisibleColors(Element subnet) {
		return query(subnet, "ancestor-or-self::*/colors/color");
	}
	
	public static Element getSubnetOfPlace(Element place) {
		return element(place, "../parent::subnet");
	}
	
	public static Element getSubnet(Element subnetPlace) {
		return element(subnetPlace, "subnet");
	}
	
	public static Element getSubnetOfColorReference(Element colorRef) {
		return element(colorRef, "../../subnet");
	}
	
	public static Element getInputPlaceOfSubnet(Element subnet) {
		return element(subnet, "places/place[@name = 'input-place' and @locked = 'true']");
	}
	
	public static Element getActualPopulationPlaceOfSubnet(Element subnet) {
		return element(subnet, "places/place[@name = 'actual population']");
	}
	
	public static Element getOutputPlaceOfSubnet(Element subnet) {
		return element(subnet, "places/place[@name = 'output-place' and @locked = 'true']");
	}
	
	public static Element getInputTransitionOfSubnet(Element subnet) {
		return element(subnet, "transitions/transition[@name = 'input-transition']");
	}
	
	public static Element getOutputTransitionOfSubnet(Element subnet) {
		return element(subnet, "transitions/transition[@name = 'output-transition']");
	}
	
	public static boolean isRestrictedPlace(Element place) {
		return isInputPlace(place) || isOutputPlace(place) || isActualPopulationPlace(place);
	}
	
	public static boolean isInputPlace(Element place) {
		return (place.attributeValue("name").equals("input-place") && Boolean.valueOf(place.attributeValue("locked", "false")));
	}
	
	public static boolean isActualPopulationPlace(Element place) {
		return (place.attributeValue("name").equals("actual population") && place.getParent().getParent().getName().equals("subnet"));
	}
	
	public static boolean isOutputPlace(Element place) {
		return (place.attributeValue("name").equals("output-place") && Boolean.valueOf(place.attributeValue("locked", "false")));
	}

	public static List<Element> listSubnetPlaces(Element place) {
		return query(place, "subnet/places/place");
	}
}
