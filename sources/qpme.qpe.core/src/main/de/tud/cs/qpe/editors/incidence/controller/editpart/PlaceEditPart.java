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
�* http://www.eclipse.org/legal/epl-v10.html
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
package de.tud.cs.qpe.editors.incidence.controller.editpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.XPath;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import de.tud.cs.qpe.editors.incidence.model.InputPlaceWrapper;
import de.tud.cs.qpe.editors.incidence.model.PlaceWrapper;
import de.tud.cs.qpe.editors.incidence.view.figure.PlaceFigure;
import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.model.SubnetHelper;

public class PlaceEditPart extends AbstractGraphicalEditPart implements
		PropertyChangeListener {
	protected PlaceFigure view;

	public PlaceEditPart() {
		super();
	}

	/**
	 * Upon activation, attach to the model element as a property change
	 * listener.
	 */
	public void activate() {
		if (!isActive()) {
			super.activate();
			DocumentManager.addPropertyChangeListener(getCastedModel()
					.getElement(), this);
		}
	}

	public void addChild(EditPart part, int index) {
		super.addChild(part, index);
	}
	
	/**
	 * Upon deactivation, detach from the model element as a property change
	 * listener.
	 */
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			DocumentManager.removePropertyChangeListener(getCastedModel()
					.getElement(), this);
		}
	}

	/**
	 * Places are just simple containers for color refs so they are not edited
	 * at all.
	 */
	protected void createEditPolicies() {
	}

	public int getType() {
		if (getModel() instanceof InputPlaceWrapper) {
			return PlaceFigure.TYPE_INPUT_PLACE;
		}
		return PlaceFigure.TYPE_OUTPUT_PLACE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	protected IFigure createFigure() {
		view = new PlaceFigure();
		view.setType(getType());
		view.setName(((PlaceWrapper) getModel()).getElement().attributeValue(
				"name", "new place"));
		return view;
	}

	protected PlaceWrapper getCastedModel() {
		return (PlaceWrapper) getModel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	protected List getModelChildren() {
		if (getType() == PlaceFigure.TYPE_INPUT_PLACE) {
			return SubnetHelper.listOutColorReferences(getCastedModel().getElement());
		} else {
			return SubnetHelper.listInColorReferences(getCastedModel().getElement());
		}
	}

	public void propertyChange(PropertyChangeEvent evt) {
		refreshChildren();
		refreshVisuals();
	}
}
