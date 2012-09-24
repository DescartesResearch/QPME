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

/*******************************************************************************
 * Copyright (c) 2004 Elias Volanakis.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *����Elias Volanakis - initial API and implementation
 *    IBM Corporation
 *******************************************************************************/
package de.tud.cs.qpe.editors.net.controller.editpart.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import de.tud.cs.qpe.editors.net.controller.command.ConnectionCreateCommand;
import de.tud.cs.qpe.editors.net.controller.command.ConnectionReconnectCommand;
import de.tud.cs.qpe.editors.net.controller.command.PlaceTransitionDeleteCommand;
import de.tud.cs.qpe.editors.net.view.figure.BaseFigure;
import de.tud.cs.qpe.editors.net.view.figure.place.OrdinaryPlaceFigure;
import de.tud.cs.qpe.editors.net.view.figure.place.QueueingPlaceFigure;
import de.tud.cs.qpe.editors.net.view.figure.place.SubnetPlaceFigure;
import de.tud.cs.qpe.editors.net.view.figure.transition.ImmediateTransitionFigure;
import de.tud.cs.qpe.editors.net.view.figure.transition.TimedTransitionFigure;
import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.model.NetHelper;
import de.tud.cs.qpe.model.PlaceHelper;
import de.tud.cs.qpe.model.TransitionHelper;

/**
 * EditPart used for Shape instances (more specific for EllipticalShape and
 * RectangularShape instances).
 * <p>
 * This edit part must implement the PropertyChangeListener interface, so it can
 * be notified of property changes in the corresponding model element.
 * </p>
 * 
 * @author Elias Volanakis
 */
public class PlaceTransitionEditPart extends AbstractGraphicalEditPart
		implements PropertyChangeListener, NodeEditPart {

	protected ConnectionAnchor anchor;

	protected BaseFigure view;

	/**
	 * Upon activation, attach to the model element as a property change
	 * listener.
	 */
	public void activate() {
		if (!isActive()) {
			super.activate();
			DocumentManager.addPropertyChangeListener(getCastedModel(), this);
			// Add listener for connections.
			Element net = (Element) getParent().getModel();
			Element connections = net.element("connections");
			DocumentManager.addPropertyChangeListener(connections, this);
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
			// Remove listener for connections.
			// Here we have to go the way over the parent edit-parts model
			// since on element deletion the element is detatched and there
			// is no way to navigate back.
			Element net = (Element) getParent().getModel();
			Element connections = net.element("connections");
			DocumentManager.removePropertyChangeListener(connections, this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	protected void createEditPolicies() {
		// allow removal of the associated model element
		installEditPolicy(EditPolicy.CONNECTION_ROLE,
				new ConnectionEditPolicy() {
					protected Command getDeleteCommand(GroupRequest request) {
						return new PlaceTransitionDeleteCommand(
								getCastedModel());
					}
				});

		// allow the creation of connections and
		// and the reconnection of connections between Shape instances
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new GraphicalNodeEditPolicy() {
					/*
					 * (non-Javadoc)
					 * 
					 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getConnectionCompleteCommand(org.eclipse.gef.requests.CreateConnectionRequest)
					 */
					protected Command getConnectionCompleteCommand(
							CreateConnectionRequest request) {
						ConnectionCreateCommand cmd = (ConnectionCreateCommand) request
								.getStartCommand();
						cmd.setTarget((Element) getHost().getModel());
						return cmd;
					}

					/*
					 * (non-Javadoc)
					 * 
					 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getConnectionCreateCommand(org.eclipse.gef.requests.CreateConnectionRequest)
					 */
					protected Command getConnectionCreateCommand(
							CreateConnectionRequest request) {
						Element source = (Element) getHost().getModel();
						ConnectionCreateCommand cmd = new ConnectionCreateCommand(
								source);
						request.setStartCommand(cmd);
						return cmd;
					}

					/*
					 * (non-Javadoc)
					 * 
					 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getReconnectSourceCommand(org.eclipse.gef.requests.ReconnectRequest)
					 */
					protected Command getReconnectSourceCommand(
							ReconnectRequest request) {
						Element conn = (Element) request
								.getConnectionEditPart().getModel();
						Element newSource = (Element) getHost().getModel();
						ConnectionReconnectCommand cmd = new ConnectionReconnectCommand(
								conn);
						cmd.setNewSource(newSource);
						return cmd;
					}

					/*
					 * (non-Javadoc)
					 * 
					 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getReconnectTargetCommand(org.eclipse.gef.requests.ReconnectRequest)
					 */
					protected Command getReconnectTargetCommand(
							ReconnectRequest request) {
						Element conn = (Element) request
								.getConnectionEditPart().getModel();
						Element newTarget = (Element) getHost().getModel();
						ConnectionReconnectCommand cmd = new ConnectionReconnectCommand(
								conn);
						cmd.setNewTarget(newTarget);
						return cmd;
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	protected IFigure createFigure() {
		view = createFigureForModel();
		return view;
	}

	/**
	 * Return a IFigure depending on the instance of the current model element.
	 * This allows this EditPart to be used for both sublasses of Shape.
	 */
	protected BaseFigure createFigureForModel() {
		Element model = (Element) getModel();
		BaseFigure newFigure = null;
		if (PlaceHelper.isPlace(model)) {
			if (PlaceHelper.isOrdinaryPlace(model)) {
				newFigure = new OrdinaryPlaceFigure();
			} else if (PlaceHelper.isQueueingPlace(model)) {
				newFigure = new QueueingPlaceFigure();
			} else if (PlaceHelper.isSubnetPlace(model)) {
				newFigure = new SubnetPlaceFigure();
			}
		} else if (TransitionHelper.isTransition(model)) {
			if (TransitionHelper.isImmediateTranstion(model)) {
				newFigure = new ImmediateTransitionFigure();
			} else if (TransitionHelper.isTimedTransition(model)) {
				newFigure = new TimedTransitionFigure();
			}
		} else {
			// if Figures gets extended the conditions above must be updated
			throw new IllegalArgumentException();
		}

		if (newFigure != null) {
			((BaseFigure) newFigure).setName(model.attributeValue("name",
					"new element"));
		}

		return newFigure;
	}

	protected Element getCastedModel() {
		return (Element) getModel();
	}

	protected ConnectionAnchor getConnectionAnchor() {
		return ((BaseFigure) getFigure()).getConnectionAnchor();
	}

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
	 */
	public ConnectionAnchor getSourceConnectionAnchor(
			ConnectionEditPart connection) {
		return getConnectionAnchor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.Request)
	 */
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return getConnectionAnchor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
	 */
	public ConnectionAnchor getTargetConnectionAnchor(
			ConnectionEditPart connection) {
		return getConnectionAnchor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.Request)
	 */
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return getConnectionAnchor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
		if (DocumentManager.PROP_LOCATION_MODIFIED.equals(prop)) {
			refreshVisuals();
		} else if (DocumentManager.PROP_ATTRIBUTE_MODIFIED.equals(prop)) {
			view
					.setName(getCastedModel().attributeValue("name",
							"new element"));
		} else if ("connections"
				.equals(((Element) evt.getOldValue()).getName())
				&& (DocumentManager.PROP_CHILD_ADDED.equals(prop) || DocumentManager.PROP_CHILD_REMOVED
						.equals(prop))) {
			refreshSourceConnections();
			refreshTargetConnections();
		} else if ("connection".equals(((Element) evt.getOldValue()).getName())
				&& DocumentManager.PROP_CHILD_MODIFIED.equals(prop)) {
			refreshSourceConnections();
			refreshTargetConnections();
		}
	}

	protected void refreshVisuals() {
		// notify parent container of changed position & location
		// if this line is removed, the XYLayoutManager used by the parent
		// container
		// (the Figure of the ShapesDiagramEditPart), will not know the bounds
		// of this figure
		// and will not draw it correctly.
		Element location = NetHelper.getQpePositionMetaAttribute(getCastedModel());

		int xPos = 0;
		int yPos = 0;

		// When creating new figures, there is no
		// location meta-attribute.
		if (location != null) {
			xPos = Integer.parseInt(location.attributeValue("location-x", "0"));
			yPos = Integer.parseInt(location.attributeValue("location-y", "0"));
		}

		Rectangle bounds = view.getBounds();
		bounds.setSize(view.getPreferredSize());
		bounds.setLocation(xPos, yPos);
		((GraphicalEditPart) getParent()).setLayoutConstraint(this,
				getFigure(), bounds);
	}
}