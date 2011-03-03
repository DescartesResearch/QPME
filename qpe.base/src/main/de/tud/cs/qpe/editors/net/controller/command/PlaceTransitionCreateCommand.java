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

import java.util.HashSet;
import java.util.Iterator;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
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
			HashSet<String> nameIndex = new HashSet<String>();

			XPath xpathSelector = DocumentHelper.createXPath("./places/place");
			Iterator queueIterator = xpathSelector.selectNodes(parent).iterator();
			while (queueIterator.hasNext()) {
				Element queue = (Element) queueIterator.next();
				nameIndex.add(queue.attributeValue("name"));
			}

			// Find a new name.
			String placeName = "new place";
			for (int x = 0;; x++) {
				if ((x == 0) && (!nameIndex.contains("new place"))) {
					break;
				} else if ((x > 0) && !nameIndex.contains("new place " + Integer.toString(x))) {
					placeName = "new place " + Integer.toString(x);
					break;
				}
			}
			newElement.addAttribute("name", placeName);
			DocumentManager.addChild(parent.element("places"), newElement);
		} else if ("transition".equals(newElement.getName())) {
			HashSet<String> nameIndex = new HashSet<String>();

			XPath xpathSelector = DocumentHelper.createXPath("./transitions/transition");
			Iterator queueIterator = xpathSelector.selectNodes(parent).iterator();
			while (queueIterator.hasNext()) {
				Element queue = (Element) queueIterator.next();
				nameIndex.add(queue.attributeValue("name"));
			}

			// Find a new name.
			String transitionName = "new transition";
			for (int x = 0;; x++) {
				if ((x == 0) && (!nameIndex.contains("new transition"))) {
					break;
				} else if ((x > 0) && !nameIndex.contains("new transition " + Integer.toString(x))) {
					transitionName = "new transition " + Integer.toString(x);
					break;
				}
			}
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
