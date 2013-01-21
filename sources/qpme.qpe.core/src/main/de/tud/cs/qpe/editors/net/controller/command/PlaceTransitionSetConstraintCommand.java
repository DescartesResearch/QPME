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

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;

import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.model.NetHelper;

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
		Element metaAttribute = NetHelper.getQpePositionMetaAttribute(element);
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
