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
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;

import de.tud.cs.qpe.model.DocumentManager;

/**
 * A command to resize and/or move a shape. The command can be undone or redone.
 * 
 * @author Elias Volanakis
 */
public class PlaceTransitionSetConstraintCommand extends Command {
	/** Stores the new location. */
	private final Point newLocation;

	/** Stores the old location. */
	private Point oldLocation;

	/** A request to move/resize an edit part. */
	private final ChangeBoundsRequest request;

	/** Element to manipulate. */
	private final Element element;

	/**
	 * Create a command that can resize and/or move an Element.
	 * 
	 * @param shape
	 *            the Element to manipulate
	 * @param req
	 *            the move and resize request
	 * @param newBounds
	 *            the new size and location
	 * @throws IllegalArgumentException
	 *             if any of the parameters is null
	 */
	public PlaceTransitionSetConstraintCommand(Element element,
			ChangeBoundsRequest req, Rectangle newBounds) {
		if (element == null || req == null || newBounds == null) {
			throw new IllegalArgumentException();
		}
		this.element = element;
		this.request = req;
		this.newLocation = newBounds.getTopLeft();
		setLabel("move");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	public boolean canExecute() {
		Object type = request.getType();
		// make sure the Request is of a type we support:
		// since the net-elements have fixed sizes we only
		// respond to move-requests.
		return (RequestConstants.REQ_MOVE.equals(type) || RequestConstants.REQ_MOVE_CHILDREN
				.equals(type));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		XPath xpathSelector = DocumentHelper
				.createXPath("meta-attributes/meta-attribute[@name = 'location']");
		Element metaAttribute = (Element) xpathSelector
				.selectSingleNode(element);
		if (metaAttribute != null) {
			String xPosition = metaAttribute.attributeValue("location-x", "0");
			String yPosition = metaAttribute.attributeValue("location-y", "0");
			oldLocation = new Point(Integer.parseInt(xPosition), Integer
					.parseInt(yPosition));
		} else {
			oldLocation = null;
		}
		redo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	public void redo() {
		DocumentManager.setLocation(element, newLocation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		DocumentManager.setLocation(element, oldLocation);
	}
}
