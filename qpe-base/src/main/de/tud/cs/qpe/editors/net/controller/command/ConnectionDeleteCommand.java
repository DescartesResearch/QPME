/* ==============================================
 * QPME : Queueing Petri net Modeling Environment
 * ==============================================
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.eclipse.gef.commands.Command;

import de.tud.cs.qpe.model.DocumentManager;

/**
 * A command to disconnect (remove) a connection from its endpoints. The command
 * can be undone or redone.
 * 
 * @author Elias Volanakis
 */
public class ConnectionDeleteCommand extends Command {

	/** Connection instance to disconnect. */
	private final Element connection;

	/** Container the connections are stored in */
	private Element connectionContainer;

	private List incidenceFunctionConnections;

	private Element incidenceFunctionConnectionsContainer;

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
		if("true".equals(connection.attributeValue("locked"))) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */

	@SuppressWarnings("unchecked")
	public void execute() {
		connectionContainer = connection.getParent();

		// Get source and targets.
		String sourceId = connection.attributeValue("source-id", "");
		XPath xpathSelector = DocumentHelper.createXPath("//place[@id = '"
				+ sourceId + "'] | //transition[@id = '" + sourceId + "']");
		Element sourceElement = (Element) xpathSelector
				.selectSingleNode(connection);

		String targetId = connection.attributeValue("target-id", "");
		xpathSelector = DocumentHelper.createXPath("//place[@id = '" + targetId
				+ "'] | //transition[@id = '" + targetId + "']");
		Element targetElement = (Element) xpathSelector
				.selectSingleNode(connection);

		// Find out which one is the transition and which
		// one is the place.
		Element transition;
		Element place;
		if ("transition".equals(sourceElement.getName())) {
			transition = sourceElement;
			place = targetElement;
		} else {
			transition = targetElement;
			place = sourceElement;
		}

		// Save the container element.
		incidenceFunctionConnectionsContainer = transition
				.element("connections");

		incidenceFunctionConnections = new ArrayList();
		// Get all color-refs of the place and remove
		// all connections in the transition using it
		// as source or target.
		xpathSelector = DocumentHelper.createXPath("color-refs/color-ref");
		Iterator colorRefIterator = xpathSelector.selectNodes(place).iterator();
		while (colorRefIterator.hasNext()) {
			Element colorRef = (Element) colorRefIterator.next();
			xpathSelector = DocumentHelper
					.createXPath("connections/connection[@source-id = '"
							+ colorRef.attributeValue("id", "")
							+ "' or @target-id = '"
							+ colorRef.attributeValue("id", "") + "']");
			;
			List connections = xpathSelector.selectNodes(transition);
			incidenceFunctionConnections.addAll(connections);
		}

		redo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	public void redo() {
		// Remove the connections in the transition.
		Iterator connectionIterator = incidenceFunctionConnections.iterator();
		while (connectionIterator.hasNext()) {
			Element connection = (Element) connectionIterator.next();
			DocumentManager.removeElement(connection);
		}

		// Remove the connection itself.
		DocumentManager.removeElement(connection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		// Readd the connection.
		DocumentManager.addChild(connectionContainer, connection);

		// Readd the connections in the transition.
		Iterator connectionIterator = incidenceFunctionConnections.iterator();
		while (connectionIterator.hasNext()) {
			Element connection = (Element) connectionIterator.next();
			DocumentManager.addChild(incidenceFunctionConnectionsContainer,
					connection);
			;
		}
	}
}
