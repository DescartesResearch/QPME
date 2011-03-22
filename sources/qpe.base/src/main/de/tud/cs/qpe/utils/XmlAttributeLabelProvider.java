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
 *  06/03/2011  Simon Spinner     Created.
 * 
 */
package de.tud.cs.qpe.utils;

import org.dom4j.Element;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Color;

public class XmlAttributeLabelProvider extends CellLabelProvider {
	
	private String attribute;
	private String defaultValue;
	
	public XmlAttributeLabelProvider(String attribute, String defaultValue) {
		this.attribute = attribute;
		this.defaultValue = defaultValue;
	}

	@Override
	public void update(ViewerCell cell) {
		Element element = getCastedElement(cell);
		if (element != null) {
			update(cell, element);
		}
	}
	
	protected Element getCastedElement(ViewerCell cell) {
		return (Element)cell.getElement();
	}
	
	protected void update(ViewerCell cell, Element element) {
		cell.setText(element.attributeValue(attribute, defaultValue));
	}	

	protected String getAttribute() {
		return attribute;
	}
	
	protected String getDefaultValue() {
		return defaultValue;
	}
}