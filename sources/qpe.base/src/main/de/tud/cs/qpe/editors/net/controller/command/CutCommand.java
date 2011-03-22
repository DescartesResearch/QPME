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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.eclipse.jface.viewers.StructuredSelection;

import de.tud.cs.qpe.model.DocumentManager;

public class CutCommand extends CopyCommand {
	protected Document sourceDocument;
	
	Map<Element, Element> removedElements = new HashMap<Element, Element>();
	Map<Element, Element> removedConnections;
	
	public CutCommand(StructuredSelection selection) {
		super(selection);
	}
	
	public void execute() {
		// Copy the selection.
		super.execute();
		
		// Remove the elements (but only if they are not locked. If locked just copy them).
		Iterator placeIterator = places.iterator();
		while (placeIterator.hasNext()) {
			Element place = (Element) placeIterator.next();
			if (!Boolean.valueOf(place.attributeValue("locked", "false"))) {
				removedElements.put(place, place.getParent());
			}
		}
		Iterator transitionIterator = transitions.iterator();
		while (transitionIterator.hasNext()) {
			Element transition = (Element) transitionIterator.next();
			if (!Boolean.valueOf(transition.attributeValue("locked", "false"))) {
				removedElements.put(transition, transition.getParent());
			}
		}
		
		sourceDocument = removedElements.keySet().iterator().next().getDocument();
		
		redo();
	}

	public void redo() {
		// Remove all selected places and transitions.
		Iterator<Element> elementIterator = removedElements.keySet().iterator();
		while(elementIterator.hasNext()) {
			Element curElement = elementIterator.next();
			DocumentManager.removeElement(curElement);
		}

		// Find out which connections will have to be removed.
		findInvalidConnections();
		
		// Remove all now invalid connections.
		Iterator<Element> connectionIterator = removedConnections.keySet().iterator();
		while(connectionIterator.hasNext()) {
			Element curConnection = connectionIterator.next();
			DocumentManager.removeElement(curConnection);
		}
	}

	public void undo() {
		// Restore all places and transitions.
		Iterator<Element> elementIterator = removedElements.keySet().iterator();
		while(elementIterator.hasNext()) {
			Element child = elementIterator.next();
			Element parent = removedElements.get(child);
			DocumentManager.addChild(parent, child);
		}
		
		// Restore all connections.
		Iterator<Element> connectionIterator = removedConnections.keySet().iterator();
		while(connectionIterator.hasNext()) {
			Element child = connectionIterator.next();
			Element parent = removedConnections.get(child);
			DocumentManager.addChild(parent, child);
		}
	}
	
	private void findInvalidConnections() {
		removedConnections = new HashMap<Element, Element>();
		
		// After adding all places, transitions and connections
		// remove those whose sources or targets don't exist.
		XPath xpathSelector = DocumentHelper.createXPath("//connection");
		Iterator connectionIterator = xpathSelector.selectNodes(sourceDocument).iterator();
		while (connectionIterator.hasNext()) {
			Element connection = (Element) connectionIterator.next();
			xpathSelector = DocumentHelper.createXPath("//*[@id = '" + connection.attributeValue("source-id", "") + "']");
			Element el = (Element) xpathSelector.selectSingleNode(connection);
			if (el == null) {
				removedConnections.put(connection, connection.getParent());
			} else {
				xpathSelector = DocumentHelper.createXPath("//*[@id = '" + connection.attributeValue("target-id", "") + "']");
				el = (Element) xpathSelector.selectSingleNode(connection);
				if (el == null) {
					removedConnections.put(connection, connection.getParent());
				}
			}
		}
	}

	public boolean canUndo() {
		return true;
	}
}
