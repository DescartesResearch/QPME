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

public class CopyCommand extends Command {
	private StructuredSelection selection;

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
					XPath xpathSelector = DocumentHelper.createXPath("color-refs/color-ref");
					Iterator colorRefIterator = xpathSelector.selectNodes(placeEditPart.getModel()).iterator();
					while (colorRefIterator.hasNext()) {
						Element colorRef = (Element) colorRefIterator.next();
						xpathSelector = DocumentHelper.createXPath("//color[@id = '" + colorRef.attributeValue("color-id") + "']");
						Element color = (Element) xpathSelector.selectSingleNode(colorRef);
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
			XPath xpathSelector = DocumentHelper.createXPath("//connection[@source-id = '" + connection.attributeValue("source-id", "") + "' and @target-id = '"
					+ connection.attributeValue("target-id", "") + "']");
			Element test = (Element) xpathSelector.selectSingleNode(curElement);
			if (test == null) {
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
