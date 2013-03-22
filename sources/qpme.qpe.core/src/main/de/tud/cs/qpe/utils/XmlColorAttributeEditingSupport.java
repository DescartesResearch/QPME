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
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColorCellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;

import de.tud.cs.qpe.model.DocumentManager;

public class XmlColorAttributeEditingSupport extends XmlAttributeEditingSupport {

	public XmlColorAttributeEditingSupport(ColumnViewer column, String attribute) {
		super(column, attribute);
	}
	
	@Override
	protected CellEditor createCellEditor(Composite parent) {
		return new ColorCellEditor(parent);
	}
	
	@Override
	protected Object getValue(Object element) {
		String realColor = ((Element)element).attributeValue("real-color");
		return ColorHelper.getRGBFromString(realColor);
	}
	
	@Override
	protected void setValue(Object element, Object value) {
		if (value != null) {
			String realColor = ColorHelper.getStringfromRGB((RGB) value);
			Element color = (Element)element;
			if (!realColor.equals(color.attributeValue("real-color"))) {
				DocumentManager.setAttribute(color, "real-color", realColor);
				getViewer().refresh();
			}
		}
	}
	
}