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
package de.tud.cs.qpe.rcp.actions.context;

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
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionDelegate;

import de.tud.cs.qpe.editors.net.NetEditorInput;
import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.utils.IdGenerator;

public class PasteDelegate extends ActionDelegate implements IEditorActionDelegate, IWorkbenchWindowActionDelegate {
	protected static List<Action> actionList = new ArrayList<Action>();

	protected Document targetDocument;

	public static void setEnabled(boolean enabled) {
		Iterator<Action> actionIterator = actionList.iterator();
		while(actionIterator.hasNext()) {
			Action action = actionIterator.next();
			action.setEnabled(enabled);
		}
	}

	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
	}

	public void init(IWorkbenchWindow window) {
	}

	public void init(IAction act) {
		if(!actionList.contains(act)) {
			actionList.add((Action) act);
		}
		setEnabled(false);
	}

	public void run(IAction action) {
		Document clipBoardContent = CopySelectionDelegate.getClipboardContent();

		IEditorPart targetEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (targetEditor != null) {
			IEditorInput input = targetEditor.getEditorInput();
			if (input instanceof NetEditorInput) {
				NetEditorInput qpeInput = (NetEditorInput) input;
				targetDocument = qpeInput.getNetDiagram().getDocument();
			}
		}
		
		if ((clipBoardContent != null) && (targetDocument != null)) {
			String sourceId = clipBoardContent.getRootElement().attributeValue("event-manager-id");
			String targetId = targetDocument.getRootElement().attributeValue("event-manager-id");

			// Make a copy of the clipboard-document and operate on this from
			// now on.
			Document clipBoardCopy = (Document) clipBoardContent.clone();

			// Replace the places color-ref ids and update all
			// incidence-functions.
			XPath xpathSelector = DocumentHelper.createXPath("//color-ref");
			Iterator colorRefIterator = xpathSelector.selectNodes(clipBoardCopy).iterator();
			while (colorRefIterator.hasNext()) {
				Element colorRef = (Element) colorRefIterator.next();
				replaceId(colorRef);
			}

			// Replace the places ids and update the connections id-refs
			xpathSelector = DocumentHelper.createXPath("//place");
			Iterator placeIterator = xpathSelector.selectNodes(clipBoardCopy).iterator();
			while (placeIterator.hasNext()) {
				Element place = (Element) placeIterator.next();
				Point location = DocumentManager.getLocation(place);
				location.x += 10;
				location.y += 10;
				DocumentManager.setLocation(place, location);
				replaceId(place);
				DocumentManager.addChild(targetDocument.getRootElement().element("places"), (Element) place.clone());
			}

			// Replace the transitions modes ids and update the current
			// transitions incidence-function.
			xpathSelector = DocumentHelper.createXPath("//mode");
			Iterator modeIterator = xpathSelector.selectNodes(clipBoardCopy).iterator();
			while (modeIterator.hasNext()) {
				Element mode = (Element) modeIterator.next();
				replaceId(mode);
			}

			// Replace the transitions ids and update the connections id-refs
			xpathSelector = DocumentHelper.createXPath("//transition");
			Iterator tansitionIterator = xpathSelector.selectNodes(clipBoardCopy).iterator();
			while (tansitionIterator.hasNext()) {
				Element transition = (Element) tansitionIterator.next();
				Point location = DocumentManager.getLocation(transition);
				location.x += 10;
				location.y += 10;
				DocumentManager.setLocation(transition, location);
				replaceId(transition);
				DocumentManager.addChild(targetDocument.getRootElement().element("transitions"), (Element) transition.clone());
			}

			// Replace the connections ids.
			xpathSelector = DocumentHelper.createXPath("/net/connections/connection");
			Iterator connectionIterator = xpathSelector.selectNodes(clipBoardCopy).iterator();
			while (connectionIterator.hasNext()) {
				Element connection = (Element) connectionIterator.next();
				replaceId(connection);
				DocumentManager.addChild(targetDocument.getRootElement().element("connections"), (Element) connection.clone());
			}

			// After adding all places, transitions and connections
			// remove those whose sources or targets don't exist.
			xpathSelector = DocumentHelper.createXPath("//connection");
			connectionIterator = xpathSelector.selectNodes(targetDocument).iterator();
			while (connectionIterator.hasNext()) {
				Element connection = (Element) connectionIterator.next();
				xpathSelector = DocumentHelper.createXPath("//*[@id = '" + connection.attributeValue("source-id", "") + "']");
				Element el = (Element) xpathSelector.selectSingleNode(connection);
				if (el == null) {
					DocumentManager.removeElement(connection);
				} else {
					xpathSelector = DocumentHelper.createXPath("//*[@id = '" + connection.attributeValue("target-id", "") + "']");
					el = (Element) xpathSelector.selectSingleNode(connection);
					if (el == null) {
						DocumentManager.removeElement(connection);
					}
				}
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
					newColorPrefix = new File(clipBoardCopy.getRootElement().attributeValue("path")).getName();
					newColorPrefix = newColorPrefix.substring(0, newColorPrefix.length() - 4);
				} else {
					newColorPrefix = "new document";
				}
				newColorPrefix += ".";

				xpathSelector = DocumentHelper.createXPath("//color");
				Iterator colorIterator = xpathSelector.selectNodes(clipBoardCopy).iterator();
				while (colorIterator.hasNext()) {
					Element color = (Element) colorIterator.next();
					String curName = color.attributeValue("name", "");
					// Since the Ids are generated new every time colors are
					// copied, we have to check for equality by comparing every
					// attribute of a color. If the names are equal, they are
					// treated as equal.

					// Add the color, if it doesen't yet exist.
					xpathSelector = DocumentHelper.createXPath("//color[@name = '" + newColorPrefix + curName + "']");
					Element testColor = (Element) xpathSelector.selectSingleNode(targetDocument);
					if (testColor == null) {
						color.addAttribute("name", newColorPrefix + curName);
						DocumentManager.addChild(targetDocument.getRootElement().element("colors"), (Element) color.clone());
					}
				}
			}
		}
	}

	private void replaceId(Element element) {
		String oldId = element.attributeValue("id");
		String newId = IdGenerator.get();

		XPath xpathSelector = DocumentHelper.createXPath("//@*[. = '" + oldId + "']");
		Iterator occurences = xpathSelector.selectNodes(element).iterator();
		while (occurences.hasNext()) {
			Attribute usage = (Attribute) occurences.next();
			usage.setValue(newId);
		}
	}
}
