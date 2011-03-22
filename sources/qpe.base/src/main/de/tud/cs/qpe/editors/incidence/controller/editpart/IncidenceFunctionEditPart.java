/* =============================================
 * QPME : Queueing Petri net Modeling Environment
 * =============================================
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
 *  2006        Christofer Dutz   Created.
 * 
 */

package de.tud.cs.qpe.editors.incidence.controller.editpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.tree.DefaultElement;
import org.eclipse.draw2d.AbstractLayout;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

import de.tud.cs.qpe.editors.incidence.model.InputPlaceWrapper;
import de.tud.cs.qpe.editors.incidence.model.OutputPlaceWrapper;
import de.tud.cs.qpe.editors.incidence.view.layout.IncidenceFunctionLayout;
import de.tud.cs.qpe.model.DocumentManager;

public class IncidenceFunctionEditPart extends AbstractGraphicalEditPart
		implements PropertyChangeListener {

	protected AbstractLayout layoutManager;

	protected List modelChildren;

	public IncidenceFunctionEditPart() {
		super();
	}
	
	/**
	 * Upon activation, attach to the model element as a property change
	 * listener.
	 */
	public void activate() {
		if (!isActive()) {
			super.activate();
			DocumentManager.addPropertyChangeListener(getCastedModel(), this);
			Element modes = getCastedModel().element("modes");
			if(modes == null) {
				modes = new DefaultElement("modes");
				DocumentManager.addChild(getCastedModel(), modes, false);
			} 
			DocumentManager.addPropertyChangeListener(modes, this);
		}
	}

	/**
	 * Upon deactivation, detach from the model element as a property change
	 * listener.
	 */
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			DocumentManager
					.removePropertyChangeListener(getCastedModel(), this);
			DocumentManager.removePropertyChangeListener(getCastedModel().element("modes"), this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	protected void createEditPolicies() {
		// disallows the removal of this edit part from its parent
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new RootComponentEditPolicy());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	protected IFigure createFigure() {
		Figure f = new Figure();
		f.setBorder(new MarginBorder(3));
		// Setup the LayoutManager. Here the ColorLayout
		// alligns the child elements (Color figures) in a
		// row underneath each other.
		layoutManager = new IncidenceFunctionLayout();
		f.setLayoutManager(layoutManager);
		return f;
	}

	private Element getCastedModel() {
		return (Element) getModel();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@SuppressWarnings("unchecked")
	protected List getModelChildren() {
		// This little if statement solved a problem that
		// held me up for about 9 Hours. If the list is 
		// recreated everyt time the method is called, then 
		// the wrapper objects are also recreated. Since these
		// are diveren object instances, eclipse always recreated
		// the editparts and figurs, resulting in realy strange 
		// problems.
		if(modelChildren != null) {
			return modelChildren;
		}
		
		Element model = getCastedModel();

		// Add all modes of the current transisiton.
		XPath xpathSelector = DocumentHelper.createXPath("modes/mode");
		List modes = xpathSelector.selectNodes(model);

		// Select all connections to the current
		// transition from the other places.
		List inputPlaces = new ArrayList<Element>();
		xpathSelector = DocumentHelper
				.createXPath("../../connections/connection[@target-id='"
						+ model.attributeValue("id", "") + "']");
		Iterator incommingConnectionsIterator = xpathSelector
				.selectNodes(model).iterator();
		while (incommingConnectionsIterator.hasNext()) {
			Element connection = (Element) incommingConnectionsIterator.next();

			xpathSelector = DocumentHelper
					.createXPath("../../places/place[@id = '"
							+ connection.attributeValue("source-id", "") + "']");
			Element targetElement = (Element) xpathSelector
					.selectSingleNode(model);
			inputPlaces.add(targetElement);
		}

		// Select all connections from the current
		// transition to places.
		List outputPlaces = new ArrayList<Element>();
		xpathSelector = DocumentHelper
				.createXPath("../../connections/connection[@source-id='"
						+ model.attributeValue("id", "") + "']");
		Iterator outgoingConnectionsIterator = xpathSelector.selectNodes(model)
				.iterator();
		while (outgoingConnectionsIterator.hasNext()) {
			Element connection = (Element) outgoingConnectionsIterator.next();

			xpathSelector = DocumentHelper
					.createXPath("../../places/place[@id = '"
							+ connection.attributeValue("target-id", "") + "']");
			Element targetElement = (Element) xpathSelector
					.selectSingleNode(model);
			outputPlaces.add(targetElement);
		}

		modelChildren = new ArrayList<Object>();
		modelChildren.addAll(modes);
		Iterator placeIterator = inputPlaces.iterator();
		while (placeIterator.hasNext()) {
			Element place = (Element) placeIterator.next();
			modelChildren.add(new InputPlaceWrapper(place));
		}
		placeIterator = outputPlaces.iterator();
		while (placeIterator.hasNext()) {
			Element place = (Element) placeIterator.next();
			modelChildren.add(new OutputPlaceWrapper(place));
		}
		
		return modelChildren;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
		if (DocumentManager.PROP_CHILD_ADDED.equals(prop)) {
			Element modeToAdd = (Element)evt.getNewValue();
			modelChildren.add(modeToAdd);
			refresh();
		}
		if (DocumentManager.PROP_CHILD_REMOVED.equals(prop)) {
			Element modeToRemove = (Element)evt.getNewValue();
			modelChildren.remove(modeToRemove);
			refresh();
		}
		// Since this edit part is registered to be notified
		// for modifications of its children. We simply need
		// to check for child modification events.
		if (DocumentManager.PROP_CHILD_MODIFIED.equals(prop)) {
			refreshVisuals();
			refreshChildren();
		}
	}
}
