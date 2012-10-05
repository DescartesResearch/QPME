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
�* All rights reserved. This program and the accompanying materials
�* are made available under the terms of the Eclipse Public License v1.0
�* which accompanies this distribution, and is available at
�* http://www.eclipse.org/legal/epl-v10.html
�*
�* Contributors:
�*����Elias Volanakis - initial API and implementation
 *    IBM Corporation
�*******************************************************************************/
package de.tud.cs.qpe.editors.net.controller.command;

import java.util.List;

import org.dom4j.Element;
import org.eclipse.gef.commands.Command;

import de.tud.cs.qpe.editors.net.gef.palette.templates.PlaceTransition;
import de.tud.cs.qpe.model.ConnectionHelper;
import de.tud.cs.qpe.model.IncidenceFunctionHelper;
import de.tud.cs.qpe.model.PlaceHelper;
import de.tud.cs.qpe.model.SubnetHelper;
import de.tud.cs.qpe.model.TransitionHelper;

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
	private Element connection;

	/** The new source endpoint. */
	private Element newSource;

	/** The new target endpoint. */
	private Element newTarget;

	/** The original source endpoint. */
	private final Element oldSource;

	/** The original target endpoint. */
	private final Element oldTarget;
	
	private List<Element> oldIncidenceFunctionConnections;

	/**
	 * Instantiate a command that can reconnect a Connection instance to a
	 * different source or target endpoint.
	 * 
	 * @param conn
	 *            the connection instance to reconnect (non-null)
	 * @throws IllegalArgumentException
	 *             if conn is null
	 */
	public ConnectionReconnectCommand(Element connection) {
		if (connection == null) {
			throw new IllegalArgumentException();
		}
		// Save the connection element.
		this.connection = connection;

		// Get the old source and target elements.
		oldSource = ConnectionHelper.getSource(connection);
		oldTarget = ConnectionHelper.getTarget(connection);

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
			// If a connection allready exists, prevent a new one.
			if (!ConnectionHelper.existsConnection(newSource, newTarget)) {
				// Allow connections from places to transitions.
				if (PlaceHelper.isPlace(newSource) && TransitionHelper.isTransition(newTarget)) {
					// In subnets connections from the output place are not allowed
					if (!SubnetHelper.isOutputPlace(newSource)) {
						return true;
					}
				}
				// Allow connections from transitions to places.
				if (TransitionHelper.isTransition(newSource) && PlaceHelper.isPlace(newTarget)) {
					// In subnets connections to the input place are not allowed
					if (!SubnetHelper.isInputPlace(newTarget)) {
						return true;
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
		Element transition;
		Element place;
		if(PlaceHelper.isPlace(oldSource)) {
			transition = oldTarget;
			place = oldSource;
		} else {
			transition = oldSource;
			place = oldTarget;
		}
		ConnectionHelper.setSource(connection, newSource);
		ConnectionHelper.setTarget(connection, newTarget);
		oldIncidenceFunctionConnections = IncidenceFunctionHelper.listAllConnectionsFromOrToPlace(transition, place);
		for(Element con : oldIncidenceFunctionConnections) {
			IncidenceFunctionHelper.removeConnection(transition, con);
		}
	}

	/**
	 * Reconnect the connection to its original source and target endpoints.
	 */
	public void undo() {
		Element transition;
		if(TransitionHelper.isTransition(oldSource)) {
			transition = oldSource;
		} else {
			transition = oldTarget;
		}
		ConnectionHelper.setSource(connection, oldSource);
		ConnectionHelper.setTarget(connection, oldTarget);
		for(Element con : oldIncidenceFunctionConnections) {
			IncidenceFunctionHelper.addConnection(transition, con);
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
	public void setNewSource(Element connectionSource) {
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
	public void setNewTarget(Element connectionTarget) {
		if (connectionTarget == null) {
			throw new IllegalArgumentException();
		}
		setLabel("move connection endpoint");
		newTarget = connectionTarget;
	}
}
