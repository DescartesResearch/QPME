/*******************************************************************************
 * Copyright (c) 2004 Elias Volanakis.
 †* All rights reserved. This program and the accompanying materials
 †* are made available under the terms of the Eclipse Public License v1.0
 †* which accompanies this distribution, and is available at
 †* http://www.eclipse.org/legal/epl-v10.html
 †*
 †* Contributors:
 †*††††Elias Volanakis - initial API and implementation
 *    IBM Corporation
 †*******************************************************************************/
package de.tud.cs.qpe.editors.net.controller.factory;

import org.dom4j.Element;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import de.tud.cs.qpe.editors.net.controller.editpart.outline.DiagramTreeEditPart;
import de.tud.cs.qpe.editors.net.controller.editpart.outline.NetTreeEditPart;

/**
 * Factory that maps model elements to TreeEditParts. TreeEditParts are used in
 * the outline view of the ShapesEditor.
 * 
 * @author Elias Volanakis
 */
public class NetTreeEditPartFactory implements EditPartFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart,
	 *      java.lang.Object)
	 */
	public EditPart createEditPart(EditPart context, Object model) {
		Element element = (Element) model;
		Element parent = element.getParent();
		if ("net".equals(element.getName())) {
			// TODO: H‰‰‰‰???
			return new DiagramTreeEditPart((Element) model);
		}
		if(parent != null) {
			if ("places".equals(element.getParent().getName())) {
				// TODO: H‰‰‰‰???
				return new NetTreeEditPart((Element) model);
			}
			if ("transitions".equals(element.getParent().getName())) {
				// TODO: H‰‰‰‰???
				return new NetTreeEditPart((Element) model);
			}
		}
		return null; // will not show an entry for the corresponding model
		// instance
	}

}
