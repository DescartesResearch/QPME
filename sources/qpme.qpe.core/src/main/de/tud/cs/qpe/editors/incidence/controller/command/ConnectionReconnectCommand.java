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

/*******************************************************************************
 * Copyright (c) 2004 Elias Volanakis.
 �* All rights reserved. This program and the accompanying materials
 �* are made available under the terms of the Eclipse Public License v1.0
 �* which accompanies this distribution, and is available at
 �* http://www.eclipse.org/legal/epl-v10.html
 �*
 �* Contributors:
 �*����Elias Volanakis - initial API and implementation
 *    IBM Corporation
 �*******************************************************************************/
package de.tud.cs.qpe.editors.incidence.controller.command;

import java.beans.PropertyChangeListener;

import org.dom4j.Element;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;

import de.tud.cs.qpe.editors.incidence.controller.editpart.ColorRefEditPart;
import de.tud.cs.qpe.editors.incidence.controller.editpart.ModeEditPart;
import de.tud.cs.qpe.editors.incidence.controller.editpart.PlaceEditPart;
import de.tud.cs.qpe.editors.incidence.view.figure.PlaceFigure;
import de.tud.cs.qpe.editors.net.gef.palette.templates.PlaceTransition;
import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.model.IncidenceFunctionHelper;

/**
 * A command to reconnect a connection to a different start point or end point.
 * The command can be undone or redone.
 * <p>
 * This command is designed to be used together with a GraphicalNodeEditPolicy.
 * To use this command propertly, following steps are necessary:
 * </p>
 * <ol>
 * <li>Create a subclass of GraphicalNodeEditPolicy.</li>
 * <li>Override the <tt>getReconnectSourceCommand(...)</tt> method. Here you
 * need to obtain the Connection model element from the ReconnectRequest, create
 * a new ConnectionReconnectCommand, set the new connection <i>source</i> by
 * calling the <tt>setNewSource(Shape)</tt> method and return the command
 * instance.
 * <li>Override the <tt>getReconnectTargetCommand(...)</tt> method.</li>
 * Here again you need to obtain the Connection model element from the
 * ReconnectRequest, create a new ConnectionReconnectCommand, set the new
 * connection <i>target</i> by calling the <tt>setNewTarget(Shape)</tt>
 * method and return the command instance.</li>
 * </ol>
 * 
 * @see org.eclipse.gef.examples.shapes.parts.PlaceTransitionEditPart#createEditPolicies()
 *      for an example of the above procedure.
 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy
 * @see #setNewSource(PlaceTransition)
 * @see #setNewTarget(PlaceTransition)
 * @author Elias Volanakis
 */
public class ConnectionReconnectCommand extends Command {

	/** The connection instance to reconnect. */
	private ConnectionEditPart connection;

	/** The new source endpoint. */
	private EditPart newSource;

	/** The new target endpoint. */
	private EditPart newTarget;

	/** The original source endpoint. */
	private final EditPart oldSource;

	/** The original target endpoint. */
	private final EditPart oldTarget;

	/**
	 * Instantiate a command that can reconnect a Connection instance to a
	 * different source or target endpoint.
	 * 
	 * @param conn
	 *            the connection instance to reconnect (non-null)
	 * @throws IllegalArgumentException
	 *             if conn is null
	 */
	public ConnectionReconnectCommand(ConnectionEditPart connection) {
		if (connection == null) {
			throw new IllegalArgumentException();
		}
		// Save the connection element.
		this.connection = connection;

		// Get the old source and target elements.
		oldSource = connection.getSource();
		oldTarget = connection.getTarget();

		// Save them as new ones jut to make the query easier.
		newSource = oldSource;
		newTarget = oldTarget;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	public boolean canExecute() {
		if ((newSource != null) && (newTarget != null)) {
			// Check if the connection starts at a color-ref
			if (newSource instanceof ColorRefEditPart) {
				// If a connection allready exists, prevent a new one.
				if (!IncidenceFunctionHelper.existsConnection((Element) newTarget.getParent().getModel(), (Element) newSource.getModel(), (Element) newTarget.getModel())) {
					if (((PlaceEditPart) newSource.getParent()).getType() == PlaceFigure.TYPE_INPUT_PLACE) {
						if (newTarget instanceof ModeEditPart) {
							return true;
						}
					}
				}
			} else if (newSource instanceof ModeEditPart) {
				// If a connection allready exists, prevent a new one.
				if (!IncidenceFunctionHelper.existsConnection((Element) newSource.getParent().getModel(), (Element) newSource.getModel(), (Element) newTarget.getModel())) {
					if (newTarget instanceof ColorRefEditPart) {
						if (((PlaceEditPart) newTarget.getParent()).getType() == PlaceFigure.TYPE_OUTPUT_PLACE) {
							return true;
						}
					}
				}
			}
		}
		// Everything else is bad.
		return false;
	}

	/**
	 * Reconnect the connection to newSource (if setNewSource(...) was invoked
	 * before) or newTarget (if setNewTarget(...) was invoked before).
	 */
	public void execute() {
		redo();
	}

	public void redo() {
		// Theoretically this could result in update problems, but
		// since only one side can be changed at a time, only one
		// update event is fired.
		DocumentManager.setAttribute((Element) connection.getModel(), "source-id", ((Element) newSource.getModel()).attributeValue("id"));
		DocumentManager.setAttribute((Element) connection.getModel(), "target-id", ((Element) newTarget.getModel()).attributeValue("id"));

		// Update the connection listaners.
		if (oldSource == newSource) {
			DocumentManager.removePropertyChangeListener((Element) connection.getModel(), (PropertyChangeListener) oldTarget);
			DocumentManager.addPropertyChangeListener((Element) connection.getModel(), (PropertyChangeListener) newTarget);
		} else {
			DocumentManager.removePropertyChangeListener((Element) connection.getModel(), (PropertyChangeListener) oldSource);
			DocumentManager.addPropertyChangeListener((Element) connection.getModel(), (PropertyChangeListener) newSource);
		}
	}

	/**
	 * Reconnect the connection to its original source and target endpoints.
	 */
	public void undo() {
		DocumentManager.setAttribute((Element) connection.getModel(), "source-id", ((Element) oldSource.getModel()).attributeValue("id"));
		DocumentManager.setAttribute((Element) connection.getModel(), "target-id", ((Element) oldTarget.getModel()).attributeValue("id"));

		// Update the connection listaners.
		if (oldSource == newSource) {
			DocumentManager.removePropertyChangeListener((Element) connection.getModel(), (PropertyChangeListener) newTarget);
			DocumentManager.addPropertyChangeListener((Element) connection.getModel(), (PropertyChangeListener) oldTarget);
		} else {
			DocumentManager.removePropertyChangeListener((Element) connection.getModel(), (PropertyChangeListener) newSource);
			DocumentManager.addPropertyChangeListener((Element) connection.getModel(), (PropertyChangeListener) oldSource);
		}
	}

	/**
	 * Set a new source endpoint for this connection. When execute() is invoked,
	 * the source endpoint of the connection will be attached to the supplied
	 * Shape instance.
	 * <p>
	 * Note: Calling this method, deactivates reconnection of the <i>target</i>
	 * endpoint. A single instance of this command can only reconnect either the
	 * source or the target endpoint.
	 * </p>
	 * 
	 * @param connectionSource
	 *            a non-null Shape instance, to be used as a new source endpoint
	 * @throws IllegalArgumentException
	 *             if connectionSource is null
	 */
	public void setNewSource(EditPart connectionSource) {
		if (connectionSource == null) {
			throw new IllegalArgumentException();
		}
		setLabel("move connection startpoint");
		newSource = connectionSource;
	}

	/**
	 * Set a new target endpoint for this connection When execute() is invoked,
	 * the target endpoint of the connection will be attached to the supplied
	 * Shape instance.
	 * <p>
	 * Note: Calling this method, deactivates reconnection of the <i>source</i>
	 * endpoint. A single instance of this command can only reconnect either the
	 * source or the target endpoint.
	 * </p>
	 * 
	 * @param connectionTarget
	 *            a non-null Shape instance, to be used as a new target endpoint
	 * @throws IllegalArgumentException
	 *             if connectionTarget is null
	 */
	public void setNewTarget(EditPart connectionTarget) {
		if (connectionTarget == null) {
			throw new IllegalArgumentException();
		}
		setLabel("move connection endpoint");
		newTarget = connectionTarget;
	}
}
