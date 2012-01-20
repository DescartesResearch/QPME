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
†* http://www.eclipse.org/legal/epl-v10.html
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
