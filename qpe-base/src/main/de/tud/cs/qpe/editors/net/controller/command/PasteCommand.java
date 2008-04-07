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
			XPath xpathSelector = DocumentHelper
					.createXPath("//meta-attribute[@name != 'location']");
			Iterator metaAttributeIterator = xpathSelector.selectNodes(
					clipBoardCopy).iterator();
			while (metaAttributeIterator.hasNext()) {
				Element metaAttribute = (Element) metaAttributeIterator.next();
				metaAttribute.detach();
			}

			// Replace the places color-ref ids and update all
			// incidence-functions.
			xpathSelector = DocumentHelper.createXPath("//color-ref");
			Iterator colorRefIterator = xpathSelector
					.selectNodes(clipBoardCopy).iterator();
			while (colorRefIterator.hasNext()) {
				Element colorRef = (Element) colorRefIterator.next();
				replaceId(colorRef);
			}

			// Replace the places ids and update the connections id-refs
			xpathSelector = DocumentHelper.createXPath("//place");
			Iterator placeIterator = xpathSelector.selectNodes(clipBoardCopy)
					.iterator();
			while (placeIterator.hasNext()) {
				Element place = (Element) placeIterator.next();
				replaceId(place);
				places.add((Element) place.clone());
			}

			// Replace the transitions modes ids and update the current
			// transitions incidence-function.
			xpathSelector = DocumentHelper.createXPath("//mode");
			Iterator modeIterator = xpathSelector.selectNodes(clipBoardCopy)
					.iterator();
			while (modeIterator.hasNext()) {
				Element mode = (Element) modeIterator.next();
				replaceId(mode);
			}

			// Replace the transitions ids and update the connections id-refs
			xpathSelector = DocumentHelper.createXPath("//transition");
			Iterator tansitionIterator = xpathSelector.selectNodes(
					clipBoardCopy).iterator();
			while (tansitionIterator.hasNext()) {
				Element transition = (Element) tansitionIterator.next();
				replaceId(transition);
				transitions.add((Element) transition.clone());
			}

			// Replace the connections ids.
			xpathSelector = DocumentHelper
					.createXPath("/qpe-clipboard-document/connections/connection");
			Iterator connectionIterator = xpathSelector.selectNodes(
					clipBoardCopy).iterator();
			while (connectionIterator.hasNext()) {
				Element connection = (Element) connectionIterator.next();
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

				xpathSelector = DocumentHelper.createXPath("//color");
				Iterator colorIterator = xpathSelector.selectNodes(
						clipBoardCopy).iterator();
				while (colorIterator.hasNext()) {
					Element color = (Element) colorIterator.next();
					String curName = color.attributeValue("name", "");
					// Since the Ids are generated new every time colors are
					// copied, we have to check for equality by comparing every
					// attribute of a color. If the names are equal, they are
					// treated as equal.

					// Add the color, if it doesen't yet exist.
					xpathSelector = DocumentHelper
							.createXPath("//color[@name = '" + newColorPrefix
									+ curName + "']");
					Element testColor = (Element) xpathSelector
							.selectSingleNode(targetDocument);
					if (testColor == null) {
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
			Element metaAttribute = null;
			do {
				location.x += 10;
				location.y += 10;
				XPath xpathSelector = DocumentHelper
						.createXPath("//meta-attribute[@name = 'location' and @location-x='"
								+ Integer.toString(location.x)
								+ "' and @location-y='"
								+ Integer.toString(location.y) + "']");
				metaAttribute = (Element) xpathSelector
						.selectSingleNode(targetDocument);
			} while (metaAttribute != null);
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
			Element metaAttribute = null;
			do {
				location.x += 10;
				location.y += 10;
				XPath xpathSelector = DocumentHelper
						.createXPath("//meta-attribute[@name = 'location' and @location-x='"
								+ Integer.toString(location.x)
								+ "' and @location-y='"
								+ Integer.toString(location.y) + "']");
				metaAttribute = (Element) xpathSelector
						.selectSingleNode(targetDocument);
			} while (metaAttribute != null);
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

		XPath xpathSelector = DocumentHelper.createXPath("//@*[. = '" + oldId
				+ "']");
		Iterator occurences = xpathSelector.selectNodes(element).iterator();
		while (occurences.hasNext()) {
			Attribute usage = (Attribute) occurences.next();
			usage.setValue(newId);
		}
	}

	private void removeInvalidConnections() {
		// After adding all places, transitions and connections
		// remove those whose sources or targets don't exist.
		XPath xpathSelector = DocumentHelper.createXPath("//connection");
		Iterator connectionIterator = xpathSelector.selectNodes(targetDocument)
				.iterator();
		while (connectionIterator.hasNext()) {
			Element connection = (Element) connectionIterator.next();
			xpathSelector = DocumentHelper.createXPath("//*[@id = '"
					+ connection.attributeValue("source-id", "") + "']");
			Element el = (Element) xpathSelector.selectSingleNode(connection);
			if (el == null) {
				DocumentManager.removeElement(connection);
				addedElements.remove(connection);
			} else {
				xpathSelector = DocumentHelper.createXPath("//*[@id = '"
						+ connection.attributeValue("target-id", "") + "']");
				el = (Element) xpathSelector.selectSingleNode(connection);
				if (el == null) {
					DocumentManager.removeElement(connection);
					addedElements.remove(connection);
				}
			}
		}
	}
}
