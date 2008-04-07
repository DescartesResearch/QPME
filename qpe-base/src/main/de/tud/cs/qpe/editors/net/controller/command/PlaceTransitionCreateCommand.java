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

import org.dom4j.Element;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import de.tud.cs.qpe.model.DocumentManager;

/**
 * A command to add a Shape to a ShapeDiagram. The command can be undone or
 * redone.
 * 
 * @author Elias Volanakis
 */
public class PlaceTransitionCreateCommand extends Command {
	/** The new Element. */
	private Element newElement;

	/** Net to add to. */
	private final Element parent;

	/** The bounds of the new Shape. */
	private Rectangle bounds;

	/**
	 * Create a command that will add a new Element to a Net.
	 * 
	 * @param parent
	 *            the Net that will hold the new element
	 * @param req
	 *            a request to create a new Element
	 * @throws IllegalArgumentException
	 *             if any parameter is null, or the request does not provide a
	 *             new Element instance
	 */
	public PlaceTransitionCreateCommand(Element parent, Element newElement, Rectangle bounds) {
		this.newElement = newElement;
		this.parent = parent;
		this.bounds = bounds;
		setLabel("element creation");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	public boolean canExecute() {
		return ((newElement != null) && (parent != null) && (bounds != null));
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
		// Obtain the new Element instance from the request.
		// This causes the factory stored in the request to
		// create a new instance. The factory is supplied in
		// the palette-tool-entry,
		// see ShapesEditorPaletteFactory#createComponentsGroup()
		if ("place".equals(newElement.getName())) {
			String placeName = "new place";
			newElement.addAttribute("name", placeName);
			DocumentManager.addChild(parent.element("places"), newElement);
		} else if ("transition".equals(newElement.getName())) {
			String transitionName = "new transition";
			newElement.addAttribute("name", transitionName);
			DocumentManager.addChild(parent.element("transitions"), newElement);
		}
		DocumentManager.setLocation(newElement, bounds.getLocation());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		DocumentManager.removeElement(newElement);
	}
}
