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
package de.tud.cs.qpe.editors.net.controller.command;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import de.tud.cs.qpe.editors.net.NetEditorInput;
import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.model.NetHelper;
import de.tud.cs.qpe.model.XPathHelper;
import de.tud.cs.qpe.utils.IdGenerator;

public class PasteCommand extends Command {
	protected Document targetDocument;

	List<Element> places = new ArrayList<Element>();

	List<Element> transitions = new ArrayList<Element>();

	List<Element> connections = new ArrayList<Element>();

	List<Element> colors = new ArrayList<Element>();

	List<Element> addedElements = null;

	public boolean canExecute() {
		if (Clipboard.getDefault().getContents() instanceof Document) {
			Document doc = (Document) Clipboard.getDefault().getContents();
			if ("qpe-clipboard-document".equals(doc.getRootElement().getName())) {
				return true;
			}
		}
		return false;
	}

	public void execute() {
		Document clipBoardContent = (Document) Clipboard.getDefault()
				.getContents();

		IEditorPart targetEditor = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (targetEditor != null) {
			IEditorInput input = targetEditor.getEditorInput();
			if (input instanceof NetEditorInput) {
				NetEditorInput qpeInput = (NetEditorInput) input;
				targetDocument = qpeInput.getNetDiagram().getDocument();
			}
		}

		if ((clipBoardContent != null) && (targetDocument != null)) {
			String sourceId = clipBoardContent.getRootElement().attributeValue(
					"event-manager-id");
			String targetId = targetDocument.getRootElement().attributeValue(
					"event-manager-id");

			// Make a copy of the clipboard-document and operate on this from
			// now on.
			Document clipBoardCopy = (Document) clipBoardContent.clone();

			// Remove all meta-attributes from the copied elements.
			for (Element metaAttribute : NetHelper.listAllMetadataExceptLocation(clipBoardCopy.getRootElement())) {
				metaAttribute.detach();
			}

			// Replace the places color-ref ids and update all
			// incidence-functions.
			for (Element colorRef : NetHelper.listAllColorReferences(clipBoardCopy.getRootElement())) {
				replaceId(colorRef);
			}

			// Replace the places ids and update the connections id-refs
			for (Element place : NetHelper.listAllPlaces(clipBoardCopy.getRootElement())) {
				replaceId(place);
				places.add((Element) place.clone());
			}

			// Replace the transitions modes ids and update the current
			// transitions incidence-function.
			for (Element mode :  NetHelper.listAllModes(clipBoardCopy.getRootElement())) {
				replaceId(mode);
			}

			// Replace the transitions ids and update the connections id-refs
			for (Element transition : NetHelper.listAllTransitions(clipBoardCopy.getRootElement())) {
				replaceId(transition);
				transitions.add((Element) transition.clone());
			}

			// Replace the connections ids.
			for (Element connection : NetHelper.listAllConnections(clipBoardCopy.getRootElement())) {
				replaceId(connection);
				connections.add((Element) connection.clone());
			}

			// Only if copying from one document to a second.
			if ((sourceId == null) || !sourceId.equals(targetId)) {
				// Add colors used by prefixing their name with the source
				// documents name. Check, if a color with equal attributes
				// allready exist ... if the name is allready used ... add
				// a number (colors were copied ... something was changed
				// then some thing was copied again).

				String newColorPrefix = null;
				if (clipBoardCopy.getRootElement().attributeValue("path") != null) {
					newColorPrefix = new File(clipBoardCopy.getRootElement()
							.attributeValue("path")).getName();
					newColorPrefix = newColorPrefix.substring(0, newColorPrefix
							.length() - 4);
				} else {
					newColorPrefix = "new document";
				}
				newColorPrefix += ".";
				
				for (Element color : NetHelper.listAllColors(clipBoardCopy.getRootElement())) {
					String curName = color.attributeValue("name", "");
					// Since the Ids are generated new every time colors are
					// copied, we have to check for equality by comparing every
					// attribute of a color. If the names are equal, they are
					// treated as equal.

					// Add the color, if it doesen't yet exist.
					if (!NetHelper.existsColorWithName(targetDocument.getRootElement(), newColorPrefix + curName)) {
						color.addAttribute("name", newColorPrefix + curName);
						colors.add((Element) color.clone());
					}
				}
			}
		}

		// Actually to the paste.
		redo();
	}

	public void redo() {
		addedElements = new ArrayList<Element>();

		Element placesRoot = targetDocument.getRootElement().element("places");
		Iterator<Element> placeIterator = places.iterator();
		while (placeIterator.hasNext()) {
			Element place = placeIterator.next();
			Point location = DocumentManager.getLocation(place);
			if (location == null) {
				location = new Point(0, 0);
			}

			do {
				location.x += 10;
				location.y += 10;
			} while (NetHelper.existsElementAtLocation(targetDocument.getRootElement(), location.x, location.y));
			DocumentManager.setLocation(place, location);
			DocumentManager.addChild(placesRoot, place);
			addedElements.add(place);
		}

		Element transitionsRoot = targetDocument.getRootElement().element(
				"transitions");
		Iterator<Element> transitionIterator = transitions.iterator();
		while (transitionIterator.hasNext()) {
			Element transition = transitionIterator.next();
			Point location = DocumentManager.getLocation(transition);

			do {
				location.x += 10;
				location.y += 10;
			} while (NetHelper.existsElementAtLocation(targetDocument.getRootElement(), location.x, location.y));
			DocumentManager.setLocation(transition, location);
			DocumentManager.addChild(transitionsRoot, transition);
			addedElements.add(transition);
		}

		Element connectionsRoot = targetDocument.getRootElement().element(
				"connections");
		Iterator<Element> connectionIterator = connections.iterator();
		while (connectionIterator.hasNext()) {
			Element connection = connectionIterator.next();
			DocumentManager.addChild(connectionsRoot, connection);
			addedElements.add(connection);
		}

		Element colorsRoot = targetDocument.getRootElement().element("colors");
		Iterator<Element> colorIterator = colors.iterator();
		while (colorIterator.hasNext()) {
			Element color = colorIterator.next();
			DocumentManager.addChild(colorsRoot, color);
			addedElements.add(color);
		}

		removeInvalidConnections();
	}

	public void undo() {
		Iterator<Element> addedElementIterator = addedElements.iterator();
		while (addedElementIterator.hasNext()) {
			Element element = addedElementIterator.next();
			DocumentManager.removeElement(element);
		}
	}

	private void replaceId(Element element) {
		String oldId = element.attributeValue("id");
		String newId = IdGenerator.get();

		for (Attribute usage : NetHelper.listAllUsages(element, oldId)) {
			usage.setValue(newId);
		}
	}

	private void removeInvalidConnections() {
		// After adding all places, transitions and connections
		// remove those whose sources or targets don't exist.
		for (Element connection : NetHelper.listAllConnections(targetDocument.getRootElement())) {
			Element el = NetHelper.getElement(connection, connection.attributeValue("source-id", ""));
			if (el == null) {
				DocumentManager.removeElement(connection);
				addedElements.remove(connection);
			} else {
				el = NetHelper.getElement(connection, connection.attributeValue("target-id", ""));
				if (el == null) {
					DocumentManager.removeElement(connection);
					addedElements.remove(connection);
				}
			}
		}
	}
}
