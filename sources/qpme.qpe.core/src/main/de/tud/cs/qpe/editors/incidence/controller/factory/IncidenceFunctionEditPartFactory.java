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
 *  2006        Christofer Dutz   Created.
 * 
 */
package de.tud.cs.qpe.editors.incidence.controller.factory;

import org.dom4j.Element;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import de.tud.cs.qpe.editors.incidence.controller.editpart.ColorRefEditPart;
import de.tud.cs.qpe.editors.incidence.controller.editpart.IncidenceFunctionEditPart;
import de.tud.cs.qpe.editors.incidence.controller.editpart.ModeEditPart;
import de.tud.cs.qpe.editors.incidence.controller.editpart.NamedConnectionEditPart;
import de.tud.cs.qpe.editors.incidence.controller.editpart.PlaceEditPart;
import de.tud.cs.qpe.editors.incidence.model.PlaceWrapper;

public class IncidenceFunctionEditPartFactory implements EditPartFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart,
	 *      java.lang.Object)
	 */
	public EditPart createEditPart(EditPart context, Object modelElement) {
		// get EditPart for model element
		EditPart part = getPartForElement(modelElement);
		// store model element in EditPart
		part.setModel(modelElement);
		return part;
	}

	/**
	 * Maps an object to an EditPart.
	 * 
	 * @throws RuntimeException
	 *             if no match was found (programming error)
	 */
	private EditPart getPartForElement(Object modelElement) {
		if (modelElement instanceof Element) {
			Element element = (Element) modelElement;
			if ("transition".equals(element.getName())) {
				return new IncidenceFunctionEditPart();
			}
			if ("mode".equals(element.getName())) {
				return new ModeEditPart();
			}
			if ("color-ref".equals(element.getName())) {
				return new ColorRefEditPart();
			}
			if ("connection".equals(element.getName())) {
				return new NamedConnectionEditPart();
			}
		} else if(modelElement instanceof PlaceWrapper) {
			return new PlaceEditPart();
		}
		throw new RuntimeException("Can't create part for model element: "
				+ ((modelElement != null) ? modelElement.getClass().getName()
						: "null"));
	}
}