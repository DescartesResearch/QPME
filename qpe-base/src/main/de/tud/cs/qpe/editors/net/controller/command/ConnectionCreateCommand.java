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

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.tree.DefaultElement;
import org.eclipse.gef.commands.Command;

import de.tud.cs.qpe.model.DocumentManager;

/**
 * A command to create a connection between two shapes. The command can be
 * undone or redone.
 * <p>
 * This command is designed to be used together with a GraphicalNodeEditPolicy.
 * To use this command properly, following steps are necessary:
 * </p>
 * <ol>
 * <li>Create a subclass of GraphicalNodeEditPolicy.</li>
 * <li>Override the <tt>getConnectionCreateCommand(...)</tt> method, to
 * create a new instance of this class and put it into the
 * CreateConnectionRequest.</li>
 * <li>Override the <tt>getConnectionCompleteCommand(...)</tt> method, to
 * obtain the Command from the ConnectionRequest, call setTarget(...) to set the
 * target endpoint of the connection and return this command instance.</li>
 * </ol>
 * 
 * @see org.eclipse.gef.examples.shapes.parts.PlaceTransitionEditPart#createEditPolicies()
 *      for an example of the above procedure.
 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy
 * @author Elias Volanakis
 */
public class ConnectionCreateCommand extends Command {
	/** The connection instance. */
	private Element connection;

	/** Start endpoint for the connection. */
	private final Element source;

	/** Target endpoint for the connection. */
	private Element target;

	/**
	 * Instantiate a command that can create a connection between two Elements.
	 * 
	 * @param source
	 *            the source endpoint (a non-null Shape instance)
	 * @param lineStyle
	 *            the desired line style. See Connection#setLineStyle(int) for
	 *            details
	 * @throws IllegalArgumentException
	 *             if source is null
	 */
	public ConnectionCreateCommand(Element source) {
		if (source == null) {
			throw new IllegalArgumentException();
		}
		this.source = source;
		setLabel("connection creation");
	}

	/**
	 * Set the target endpoint for the connection.
	 * 
	 * @param target
	 *            that target endpoint (a non-null Shape instance)
	 * @throws IllegalArgumentException
	 *             if target is null
	 */
	public void setTarget(Element target) {
		if (target == null) {
			throw new IllegalArgumentException();
		}
		this.target = target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	public boolean canExecute() {
		if ((source != null) && (target != null)) {
			// If a connection allready exists, prevent a new one.
			XPath xpathSelector = DocumentHelper
					.createXPath("../../connections/connection[@source-id = '"
							+ source.attributeValue("id")
							+ "' and @target-id = '"
							+ target.attributeValue("id") + "']");
			if (xpathSelector.selectNodes(source).size() == 0) {
				// Allow connections from places to transitions.
				if ("places".equals(source.getParent().getName())
						&& "transitions".equals(target.getParent().getName())) {
					return true;
				}
				// Allow connections from transitions to places.
				if ("transitions".equals(source.getParent().getName())
						&& "places".equals(target.getParent().getName())) {
					return true;
				}
			}
		}
		// Everything else is bad.
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		redo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	public void redo() {
		XPath xpathSelector = DocumentHelper.createXPath("../../connections");
		Element container = (Element) xpathSelector.selectSingleNode(source);
		// create a new connection element for this connection
		// and set it's attributes.
		connection = new DefaultElement("connection");
		connection.addAttribute("source-id", source.attributeValue("id"));
		connection.addAttribute("target-id", target.attributeValue("id"));
		DocumentManager.addChild(container, connection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */

	public void undo() {
		// Remove the connection element from its
		// container.
		DocumentManager.removeElement(connection);
	}
}
