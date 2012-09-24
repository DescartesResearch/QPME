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
package de.tud.cs.qpe.editors.incidence.controller.command;

import java.beans.PropertyChangeListener;

import org.dom4j.Element;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;

import de.tud.cs.qpe.editors.incidence.controller.editpart.ColorRefEditPart;
import de.tud.cs.qpe.editors.incidence.controller.editpart.ModeEditPart;
import de.tud.cs.qpe.editors.incidence.controller.editpart.PlaceEditPart;
import de.tud.cs.qpe.editors.incidence.model.PlaceWrapper;
import de.tud.cs.qpe.editors.incidence.view.figure.PlaceFigure;
import de.tud.cs.qpe.model.ConnectionHelper;
import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.model.IncidenceFunctionHelper;

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
	private final EditPart source;

	/** Target endpoint for the connection. */
	private EditPart target;

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
	public ConnectionCreateCommand(EditPart source) {
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
	public void setTarget(EditPart target) {
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
			// Check if the connection starts at a color-ref
			if (source instanceof ColorRefEditPart) {
				// If a connection allready exists, prevent a new one.
				if (target instanceof ModeEditPart) {
					if (!IncidenceFunctionHelper.existsConnection((Element) target.getParent().getModel() , (Element) source.getModel(), (Element) target.getModel())) {
						if (((PlaceEditPart) source.getParent()).getType() == PlaceFigure.TYPE_INPUT_PLACE) {
							return true;
						}
					}
				}
			} else if (source instanceof ModeEditPart) {
				// If a connection allready exists, prevent a new one.
				if (target instanceof ColorRefEditPart) {
					if (!IncidenceFunctionHelper.existsConnection((Element) source.getParent().getModel(), (Element) source.getModel(), (Element) target.getModel())) {
						if (((PlaceEditPart) target.getParent()).getType() == PlaceFigure.TYPE_OUTPUT_PLACE) {
							return true;
						}
					}
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
		Element transition = null;
		
		if (source instanceof ColorRefEditPart) {
			transition = (Element)target.getParent().getModel();
		} else {
			transition = (Element)source.getParent().getModel();
		}
		
		if(transition != null) {
			// create a new connection element for this connection
			// and set it's attributes.
			connection = IncidenceFunctionHelper.createConnection((Element) source.getModel(), (Element) target.getModel(), 1);
			IncidenceFunctionHelper.addConnection(transition, connection);
		}
		
		// Activate the connection listaners.
		DocumentManager.addPropertyChangeListener(connection, (PropertyChangeListener) source);
		DocumentManager.addPropertyChangeListener(connection, (PropertyChangeListener) target);
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
		// Activate the connection listaners.
		DocumentManager.removePropertyChangeListener(connection, (PropertyChangeListener) source);
		DocumentManager.removePropertyChangeListener(connection, (PropertyChangeListener) target);

	}
}
