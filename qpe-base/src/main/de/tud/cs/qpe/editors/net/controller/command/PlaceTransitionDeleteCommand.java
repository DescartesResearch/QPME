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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.eclipse.gef.commands.Command;

import de.tud.cs.qpe.model.DocumentManager;

/**
 * A command to remove a shape from its parent. The command can be undone or
 * redone.
 * 
 * @author Elias Volanakis
 */
public class PlaceTransitionDeleteCommand extends Command {
	/** Element to remove. */
	private final Element child;

	/** Container to remove from. */
	private Element childContainer;

	/** Holds a copy of the connections of child. */
	private List connections;

	/** Container containing the connections. */
	private Element connectionContainer;

	/** Holds a copy of the connections of related incidenceFunctions. */
	private Map<Element, List> incidenceFunctionConnections;

	/**
	 * Create a command that will remove the Element from its parent.
	 * 
	 * @param parent
	 *            the Net containing the child
	 * @param child
	 *            the Element to remove
	 * @throws IllegalArgumentException
	 *             if any parameter is null
	 */
	public PlaceTransitionDeleteCommand(Element child) {
		if (child == null) {
			throw new IllegalArgumentException();
		}
		setLabel("element deletion");
		this.child = child;
	}

	public boolean canExecute() {
		if("true".equals(child.attributeValue("locked"))) {
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
		// TODO: Get a list of all connections to and from the current element.

		// TODO: if this element is a place, get a list of all connections in
		// transitions incidence functions.

		// Save the container for this element.
		childContainer = (Element) child.getParent();

		// save the container for connections.
		XPath xpathSelector = DocumentHelper.createXPath("../connections");
		connectionContainer = (Element) xpathSelector
				.selectSingleNode(childContainer);

		// store a copy of incoming & outgoing connections before proceeding.
		xpathSelector = DocumentHelper.createXPath("connection[@source-id = '"
				+ child.attributeValue("id") + "' or @target-id = '"
				+ child.attributeValue("id") + "']");
		connections = xpathSelector.selectNodes(connectionContainer);

		// if this element is a place, then all elements
		// linked to this one with connections have to be
		// transitions. Here the connections in the incidence
		// function have to also be removed.
		if ("place".equals(child.getName())) {
			// Get all color-refs of the place and remove
			// all connections in the transition using it
			// as source or target.

			// Build a map mapping the color-refs ids
			// to the element instances for fast access
			// in folowin matching process.
			Map colorRefMap = new HashMap<String, Element>();
			xpathSelector = DocumentHelper.createXPath("color-refs/color-ref");
			Iterator colorRefIterator = xpathSelector.selectNodes(child)
					.iterator();
			while (colorRefIterator.hasNext()) {
				Element colorRef = (Element) colorRefIterator.next();
				colorRefMap.put(colorRef.attributeValue("id", ""), colorRef);
			}

			// The key of this map is the transitions connection
			// container the connections stored in the list belong
			// to. The value is the connection list for the current
			// incidence-function.
			incidenceFunctionConnections = new HashMap<Element, List>();
			Iterator connectionIterator = connections.iterator();
			while (connectionIterator.hasNext()) {
				// Current connection.
				Element connection = (Element) connectionIterator.next();

				// List containing all incidence-function connections
				// using the current connection.
				List connectionsList = new ArrayList();

				// Get the transition which this connection connects to.
				xpathSelector = DocumentHelper
						.createXPath("//transition[@id = '"
								+ connection.attributeValue("source-id", "")
								+ "' or @id = '"
								+ connection.attributeValue("target-id", "")
								+ "']");
				Element transition = (Element) xpathSelector
						.selectSingleNode(connection);

				// For each connection to a transition, iterate
				// through the incidence-functions connections and
				// check if it uses a color-ref of the current place.
				// If yes, then add it to the list of connections
				// that will be removed.
				xpathSelector = DocumentHelper
						.createXPath("connections/connection");
				Iterator incidenceFunctionConnectionsIterator = xpathSelector
						.selectNodes(transition).iterator();
				while (incidenceFunctionConnectionsIterator.hasNext()) {
					Element incidenceFunctionConnection = (Element) incidenceFunctionConnectionsIterator
							.next();

					// If this incidence-function connection uses
					// a color-ref from the place we are removing,
					// add it to the list of connections to be removed.
					String sourceId = incidenceFunctionConnection
							.attributeValue("source-id", "");
					String targetId = incidenceFunctionConnection
							.attributeValue("target-id", "");
					if ((colorRefMap.get(sourceId) != null)
							|| (colorRefMap.get(targetId) != null)) {
						connectionsList.add(incidenceFunctionConnection);
					}
				}

				// If incidence-function connections use the
				// current connection, then add it to the
				// connection-map.
				if (connectionsList.size() > 0) {
					incidenceFunctionConnections.put(transition
							.element("connections"), connectionsList);
				}
			}
		}

		redo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	public void redo() {
		// Remove the child and disconnect its connections
		DocumentManager.removeElement(child);

		// Remove the connection to all other
		// elements.
		Iterator connectionIterator = connections.iterator();
		while (connectionIterator.hasNext()) {
			Element connection = (Element) connectionIterator.next();
			DocumentManager.removeElement(connection);
		}

		if(incidenceFunctionConnections != null) {
			// Eventually remove all connections inside
			// incidence-functions.
			Iterator incidencFunctionIterator = incidenceFunctionConnections
					.keySet().iterator();
			while (incidencFunctionIterator.hasNext()) {
				Element incidencFunctionConnectionContainer = (Element) incidencFunctionIterator
						.next();
				Iterator incidenceFunctionConnectionIterator = incidenceFunctionConnections
						.get(incidencFunctionConnectionContainer).iterator();
				while (incidenceFunctionConnectionIterator.hasNext()) {
					Element incidenceFunctionConnection = (Element) incidenceFunctionConnectionIterator
							.next();
					DocumentManager.removeElement(incidenceFunctionConnection);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		// Readd the place or transition.
		DocumentManager.addChild(childContainer, child);

		// Readd the connection to all other
		// elements.
		Iterator connectionIterator = connections.iterator();
		while (connectionIterator.hasNext()) {
			Element connection = (Element) connectionIterator.next();
			DocumentManager.addChild(connectionContainer, connection);
		}

		if(incidenceFunctionConnections != null) {
			// Eventually read all connections inside
			// incidence-functions.
			Iterator incidencFunctionIterator = incidenceFunctionConnections
					.keySet().iterator();
			while (incidencFunctionIterator.hasNext()) {
				Element incidencFunctionConnectionContainer = (Element) incidencFunctionIterator
						.next();
				Iterator incidenceFunctionConnectionIterator = incidenceFunctionConnections
						.get(incidencFunctionConnectionContainer).iterator();
				while (incidenceFunctionConnectionIterator.hasNext()) {
					Element incidenceFunctionConnection = (Element) incidenceFunctionConnectionIterator
							.next();
					DocumentManager.addChild(incidencFunctionConnectionContainer,
							incidenceFunctionConnection);
				}
			}
		}
	}
}