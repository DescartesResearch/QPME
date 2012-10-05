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
package de.tud.cs.qpe.editors.net.controller.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.jface.viewers.StructuredSelection;

import de.tud.cs.qpe.editors.net.controller.editpart.editor.ConnectionEditPart;
import de.tud.cs.qpe.editors.net.controller.editpart.editor.PlaceEditPart;
import de.tud.cs.qpe.editors.net.controller.editpart.editor.PlaceTransitionEditPart;
import de.tud.cs.qpe.editors.net.controller.editpart.editor.TransitionEditPart;
import de.tud.cs.qpe.model.ConnectionHelper;
import de.tud.cs.qpe.model.NetHelper;
import de.tud.cs.qpe.model.PlaceHelper;

public class CopyCommand extends Command {
	protected StructuredSelection selection;

	protected List<Element> places = new ArrayList<Element>();
	protected List<Element> transitions = new ArrayList<Element>();
	protected List<Element> connections = new ArrayList<Element>();
	protected List<Element> colors = new ArrayList<Element>();

	public CopyCommand(StructuredSelection selection) {
		this.selection = selection;
	}

	public boolean canExecute() {
		Iterator selectionIterator = this.selection.toList().iterator();
		while (selectionIterator.hasNext()) {
			Object curElement = selectionIterator.next();
			if (curElement instanceof PlaceTransitionEditPart) {
				return true;
			}
		}
		return false;
	}

	public boolean canUndo() {
		return false;
	}

	@SuppressWarnings("unchecked")
	public void execute() {
		Document sourceDocument = null;

		Iterator selectionIterator = selection.iterator();
		while (selectionIterator.hasNext()) {
			Object obj = selectionIterator.next();
			if (obj instanceof EditPart) {
				EditPart editPart = (EditPart) obj;
				List curConnections = new ArrayList();

				// If this is a place ... add its model to the place list.
				if (editPart instanceof PlaceEditPart) {
					PlaceEditPart placeEditPart = (PlaceEditPart) obj;
					places.add((Element) placeEditPart.getModel());
					sourceDocument = ((Element) placeEditPart.getModel()).getDocument();

					// remember all incomming and outgoing
					// connections to the current place.
					curConnections.addAll(placeEditPart.getSourceConnections());
					curConnections.addAll(placeEditPart.getTargetConnections());

					// Add the colors, which are referenced
					// by the current place.
					List<Element> colorReferences = PlaceHelper.listColorReferences((Element)placeEditPart.getModel());
					for (Element colorRef : colorReferences) {
						Element color = NetHelper.getColorById(colorRef, colorRef.attributeValue("color-id"));
						if (color != null) {
							colors.add(color);
						}
					}
				}
				// If this is a trnasition ... add its model to the transition
				// list.
				else if (editPart instanceof TransitionEditPart) {
					TransitionEditPart transitionEditPart = (TransitionEditPart) obj;
					transitions.add((Element) transitionEditPart.getModel());
					sourceDocument = ((Element) transitionEditPart.getModel()).getDocument();

					// remember all incomming and outgoing
					// connections to the current transition.
					curConnections.addAll(transitionEditPart.getSourceConnections());
					curConnections.addAll(transitionEditPart.getTargetConnections());
				}

				// Add the connections models to the connection list.
				Iterator connectionEditPartIterator = curConnections.iterator();
				while (connectionEditPartIterator.hasNext()) {
					ConnectionEditPart connectionEditPart = (ConnectionEditPart) connectionEditPartIterator.next();
					connections.add((Element) connectionEditPart.getModel());
				}
			}
		}

		// Finally save the elements in the clipBoard document.
		Document clipBoardDocument = DocumentFactory.getInstance().createDocument();
		Element root = clipBoardDocument.addElement("qpe-clipboard-document");
		// Save the document id, so we can find out if we
		// are copying from within the same document or
		// from one to another.
		root.addAttribute("event-manager-id", sourceDocument.getRootElement().attributeValue("event-manager-id", ""));
		if (sourceDocument.getRootElement().attributeValue("path") != null) {
			root.addAttribute("path", sourceDocument.getRootElement().attributeValue("path"));
		}

		// Add the places.
		Element curElement = root.addElement("places");
		Iterator placeIterator = places.iterator();
		while (placeIterator.hasNext()) {
			Element place = (Element) placeIterator.next();
			curElement.add((Element) place.clone());
		}

		// Add the transitions.
		curElement = root.addElement("transitons");
		Iterator transitionIterator = transitions.iterator();
		while (transitionIterator.hasNext()) {
			Element transition = (Element) transitionIterator.next();
			curElement.add((Element) transition.clone());
		}

		// Add the connections.
		curElement = root.addElement("connections");
		Iterator connectionIterator = connections.iterator();
		while (connectionIterator.hasNext()) {
			Element connection = (Element) connectionIterator.next();
			if (!ConnectionHelper.existsConnection(curElement, connection.attributeValue("source-id", ""), connection.attributeValue("target-id", ""))) {
				curElement.add((Element) connection.clone());
			}
		}

		// Add the colors.
		curElement = root.addElement("colors");
		Iterator colorIterator = colors.iterator();
		while (colorIterator.hasNext()) {
			Element color = (Element) colorIterator.next();
			curElement.add((Element) color.clone());
		}

		Clipboard.getDefault().setContents(clipBoardDocument);
	}
}
