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
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Elias Volanakis - initial API and implementation
 *    IBM Corporation
 *******************************************************************************/
package de.tud.cs.qpe.editors.net.controller.command;

import java.util.List;

import org.dom4j.Element;
import org.eclipse.gef.commands.Command;

import de.tud.cs.qpe.model.ConnectionHelper;
import de.tud.cs.qpe.model.IncidenceFuntionHelper;
import de.tud.cs.qpe.model.NetHelper;
import de.tud.cs.qpe.model.TransitionHelper;

/**
 * A command to disconnect (remove) a connection from its endpoints. The command
 * can be undone or redone.
 * 
 * @author Elias Volanakis
 */
public class ConnectionDeleteCommand extends Command {

	/** Connection instance to disconnect. */
	private final Element connection;
	
	private Element transition;

	private List<Element> incidenceFunctionConnections;

	/**
	 * Create a command that will disconnect a connection from its endpoints.
	 * 
	 * @param conn
	 *            the connection instance to disconnect (non-null)
	 * @throws IllegalArgumentException
	 *             if conn is null
	 */
	public ConnectionDeleteCommand(Element connection) {
		if (connection == null) {
			throw new IllegalArgumentException();
		}
		setLabel("connection deletion");
		this.connection = connection;
	}

	public boolean canExecute() {
		return !ConnectionHelper.isLocked(connection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		// Get source and targets.
		Element sourceElement = ConnectionHelper.getSource(connection);
		Element targetElement = ConnectionHelper.getTarget(connection);

		// Find out which one is the transition and which
		// one is the place.
		Element place;
		if (TransitionHelper.isTransition(sourceElement)) {
			transition = sourceElement;
			place = targetElement;
		} else {
			transition = targetElement;
			place = sourceElement;
		}
		incidenceFunctionConnections = IncidenceFuntionHelper.listAllConnectionsFromOrToPlace(transition, place);

		redo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	public void redo() {
		// Remove the connections in the transition.
		for(Element con : incidenceFunctionConnections) {
			IncidenceFuntionHelper.removeConnection(transition, con);
		}

		// Remove the connection itself.
		ConnectionHelper.removeConnection(connection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		// Read the connection.
		ConnectionHelper.addConnection(NetHelper.getNetOfTransition(transition), connection);

		// Read the connections in the transition.
		for(Element con : incidenceFunctionConnections) {
			IncidenceFuntionHelper.addConnection(transition, con);
		}
	}
}
