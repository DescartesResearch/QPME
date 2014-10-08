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
package de.tud.cs.qpe.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.XPath;
import org.eclipse.draw2d.geometry.Point;

import de.tud.cs.qpe.utils.IdGenerator;

/**
 * Since there is no way to attach a pojo to a dom object, this manager object
 * deals with this task. For every registered document it stores exactly one
 * event wrapper.
 * 
 * @author cdutz
 */

public class DocumentManager {
	private static Logger log = Logger.getLogger(DocumentManager.class);
	
	public static final String PROP_DOCUMENT_REGISTERD = "document.registered";

	public static final String PROP_DOCUMENT_UNREGISTERD = "document.unregistered";

	public static final String PROP_DOCUMENT_MODIFIED = "document.modified";

	public static final String PROP_CHILD_ADDED = "child.added";

	public static final String PROP_CHILD_REMOVED = "child.removed";

	public static final String PROP_CHILD_MODIFIED = "child.modified";

	public static final String PROP_ATTRIBUTE_MODIFIED = "attribute.modified";

	public static final String PROP_ATTRIBUTE_REMOVED = "attribute.removed";

	public static final String PROP_LOCATION_MODIFIED = "attribute.location";

	protected static DocumentManager instance;

	protected Map<String, DocumentEventWrapper> documentMap;

	protected PropertyChangeSupport documentRegistrationListeners;

	protected DocumentManager() {
		documentMap = new HashMap<String, DocumentEventWrapper>();
		documentRegistrationListeners = new PropertyChangeSupport(this);
	}

	protected static DocumentManager getInstance() {
		if (instance == null) {
			instance = new DocumentManager();
		}
		return instance;
	}

	protected DocumentEventWrapper getDocumentEventWrapper(Element element) {
		String eventManagerId = getElementId(element);
		return documentMap.get(eventManagerId);
	}

	public static String getElementId(Element element) {
		Element curElement = element;
		while (curElement.getParent() != null) {
			curElement = curElement.getParent();
		}
		return curElement.attributeValue("event-manager-id", "");
	}

	public static void registerInput(Document doc) {
		String eventManagerId = IdGenerator.get();

		// Set the event manager id in the root element
		// of the current document. This will then be used
		// to identify the event wrapper object.
		doc.getRootElement().addAttribute("event-manager-id", eventManagerId);
		doc.getRootElement().addAttribute("dirty", "false");
		DocumentEventWrapper wrapper = getInstance().new DocumentEventWrapper(doc);
		getInstance().documentMap.put(eventManagerId, wrapper);
		getInstance().documentRegistrationListeners.firePropertyChange(PROP_DOCUMENT_REGISTERD, null, doc);
	}

	public static void unregisterInput(Document doc) {
		String documentId = doc.getRootElement().attributeValue("event-manager-id");
		getInstance().documentRegistrationListeners.firePropertyChange(PROP_DOCUMENT_UNREGISTERD, null, doc);
		getInstance().documentMap.remove(documentId);
	}

	public static void addDocumentRegistrationListener(PropertyChangeListener l) {
		getInstance().documentRegistrationListeners.addPropertyChangeListener(l);
	}

	public static void removeDocumentRegistrationListener(PropertyChangeListener l) {
		getInstance().documentRegistrationListeners.removePropertyChangeListener(l);
	}

	public static void addPropertyChangeListener(Element element, PropertyChangeListener l) {
		getInstance().getDocumentEventWrapper(element).addPropertyChangeListener(element, l);
	}

	public static void removePropertyChangeListener(Element element, PropertyChangeListener l) {
		DocumentEventWrapper wrapper = getInstance().getDocumentEventWrapper(element);
		if (wrapper != null) {
			wrapper.removePropertyChangeListener(element, l);
		}
	}

	public static void setAttribute(Element element, String name, String value) {
		if((name != null) && (value != null)) {
			DocumentEventWrapper wrapper = getInstance().getDocumentEventWrapper(element);
			if (wrapper != null) {
				wrapper.setAttribute(element, name, value);
			}
		}
	}

	public static void setLocation(Element element, Point location) {
		// Save the old value.
		Point oldValue = null;
		XPath xpathSelector = DocumentHelper.createXPath("meta-attributes/meta-attribute[@xsi:type = 'location-attribute']");
		Element locationMetaAttribute = (Element) xpathSelector.selectSingleNode(element);
		if (locationMetaAttribute != null) {
			int xPos = Integer.parseInt(locationMetaAttribute.attributeValue("location-x", "0"));
			int yPos = Integer.parseInt(locationMetaAttribute.attributeValue("location-y", "0"));
			oldValue = new Point(xPos, yPos);
		} else {
			// Eventually add the meta-attribute container element.
			Element container = element.element("meta-attribute");
			if (container == null) {
				container = element.addElement("meta-attributes");
			}

			locationMetaAttribute = container.addElement("meta-attribute");
			locationMetaAttribute.addAttribute(new QName("type", new Namespace("xsi", XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI)), "location-attribute");
		}

		// Add the new value.
		locationMetaAttribute.addAttribute("location-x", Integer.toString(location.x));
		locationMetaAttribute.addAttribute("location-y", Integer.toString(location.y));

		DocumentEventWrapper wrapper = getInstance().getDocumentEventWrapper(element);
		if (wrapper != null) {
			wrapper.fireLocationChanged(element, oldValue, location);
		}
	}

	public static Point getLocation(Element element) {
		XPath xpathSelector = DocumentHelper.createXPath("meta-attributes/meta-attribute[@xsi:type = 'location-attribute']");
		Element locationMetaAttribute = (Element) xpathSelector.selectSingleNode(element);
		if (locationMetaAttribute != null) {
			int xPos = Integer.parseInt(locationMetaAttribute.attributeValue("location-x", "0"));
			int yPos = Integer.parseInt(locationMetaAttribute.attributeValue("location-y", "0"));
			return new Point(xPos, yPos);
		}
		return null;
	}

	public static void removeAttribute(Element element, String name) {
		DocumentEventWrapper wrapper = getInstance().getDocumentEventWrapper(element);
		if (wrapper != null) {
			wrapper.removeAttribute(element, name);
		}
	}

	public static void addChild(Element element, Element child) {
		addChild(element, child, true); 
	}

	public static void addChild(Element element, Element child, boolean createId) {
		DocumentEventWrapper wrapper = getInstance().getDocumentEventWrapper(element);
		if (wrapper != null) {
			wrapper.addElement(element, child, createId);
		}else{
			throw new RuntimeException("No net specified. Call DocumentManager.registerInput(net.getDocument());");
		}
	}

	public static void removeElement(Element element) {
		DocumentEventWrapper wrapper = getInstance().getDocumentEventWrapper(element);
		if (wrapper != null) {
			wrapper.removeElement(element);
		}
	}

	// TIP: Since after detaching an element xpath evaluations
	// can only be performed, this method returns the document
	// root of the document the element belongs to od had
	// belonged to if it is detached.
	public static Element getDocumentRoot(Element element) {
		DocumentEventWrapper wrapper = getInstance().getDocumentEventWrapper(element);
		if (wrapper != null) {
			return wrapper.getDocumentRoot();
		}
		return null;
	}

	class DocumentEventWrapper {
		/** Input object for which to handle the evens */
		protected Document doc;

		protected Map<Element, PropertyChangeSupport> pcsElementMap;

		public DocumentEventWrapper(Document doc) {
			this.doc = doc;
			this.pcsElementMap = new HashMap<Element, PropertyChangeSupport>();
		}

		/**
		 * Attach a non-null PropertyChangeListener to this object.
		 * 
		 * @param l
		 *            a non-null PropertyChangeListener instance
		 * @throws IllegalArgumentException
		 *             if the parameter is null
		 */
		public synchronized void addPropertyChangeListener(Element element, PropertyChangeListener l) {
			if (l == null) {
				throw new IllegalArgumentException();
			}

			PropertyChangeSupport pcsDelegate = pcsElementMap.get(element);
			if (pcsDelegate == null) {
				pcsDelegate = new PropertyChangeSupport(this);
				pcsElementMap.put(element, pcsDelegate);
			}

			pcsDelegate.addPropertyChangeListener(l);
			// pcsDelegate.addPropertyChangeListener(this);
		}

		/**
		 * Remove a PropertyChangeListener from this component.
		 * 
		 * @param l
		 *            a PropertyChangeListener instance
		 */
		public synchronized void removePropertyChangeListener(Element element, PropertyChangeListener l) {
			PropertyChangeSupport pcsDelegate = pcsElementMap.get(element);
			if ((l != null) && (pcsDelegate != null)) {
				pcsDelegate.removePropertyChangeListener(l);
				// pcsDelegate.removePropertyChangeListener(this);
				if (pcsDelegate.getPropertyChangeListeners().length == 0) {
					pcsElementMap.remove(element);
				}
			}
		}

		/**
		 * Remove all PropertyChangeListeners for a given element. Used when
		 * deleteing elements.
		 * 
		 * @param l
		 *            a PropertyChangeListener instance
		 */
		public synchronized void removeAllPropertyChangeListeners(Element element) {
			pcsElementMap.remove(element);
		}

		/**
		 * Report a property change to registered listeners (for example edit
		 * parts).
		 * 
		 * @param property
		 *            the programmatic name of the property that changed
		 * @param oldValue
		 *            the old value of this property
		 * @param newValue
		 *            the new value of this property
		 */
		protected void firePropertyChange(Element element, String property, Object baseElement, Object changedNode) {
			PropertyChangeSupport pcsDelegate = pcsElementMap.get(element);
			if ((pcsDelegate != null) && (pcsDelegate.hasListeners(property))) {
				pcsDelegate.firePropertyChange(property, baseElement, changedNode);
			}
		}

		protected void fireChildModified(Element element) {
			Element parent = element;
			// Fire child-modified event for all of this nodes parents.
			// TIP: Since the ancestor-axis is a reverse axis the net
			// would have been updated first. This way we ensure that
			// updates are performed from the place where they occured
			// up back to the net-node.
			while (parent.getParent() != null) {
				parent = parent.getParent();
				firePropertyChange(parent, PROP_CHILD_MODIFIED, element, null);
			}
		}

		protected void fireDocumentModified(Element element) {
			// Get a reference to the document.
			Document doc = element.getDocument();
			// Fire document-modified event.
			firePropertyChange(doc.getRootElement(), PROP_DOCUMENT_MODIFIED, doc, element);
		}

		public void setAttribute(Element baseElement, String name, String value) {
			// Save the old value.
			Attribute oldValue = null;
			if (baseElement.attribute(name) != null) {
				oldValue = (Attribute) baseElement.attribute(name);
			}

			// Only if the value is realy changed an update is fired.
			if ((oldValue == null) || (!oldValue.getValue().equals(value))) {
				if (oldValue != null) {
					oldValue.detach();
				}

				// Add the new value.
				baseElement.addAttribute(name, value);

				// Fire an attribute modified event for the current element.
				firePropertyChange(baseElement, PROP_ATTRIBUTE_MODIFIED, baseElement, baseElement.attribute(name));

				// Fire child-modified events for the parent nodes.
				fireChildModified(baseElement);

				// Fire a document-modified for the entire document.
				fireDocumentModified(baseElement);
			}
		}

		public void fireLocationChanged(Element baseElement, Point oldValue, Point newValue) {
			// Fire an attribute modified event for the current element.
			firePropertyChange(baseElement, PROP_LOCATION_MODIFIED, oldValue, newValue);

			// Fire child-modified events for the parent nodes.
			fireChildModified(baseElement);

			// Fire a document-modified for the entire document.
			fireDocumentModified(baseElement);
		}

		public void removeAttribute(Element baseElement, String name) {
			Attribute attribute = baseElement.attribute(name);
			if (attribute != null) {
				baseElement.remove(attribute);
			}

			firePropertyChange(baseElement, PROP_ATTRIBUTE_REMOVED, baseElement, name);
		}

		public void addElement(Element baseElement, Element child, boolean createId) {
			// If the element was removed and is readded
			// by a undo-action, then we have to remove the
			// event-manager-id.
			if (child.attribute("event-manager-id") != null) {
				child.remove(child.attribute("event-manager-id"));
			}

			// If the element has an id attribute then it
			// this is propably an undo of a delete-operation
			// then the id is left unchanged. If no such attribute
			// exists, then create a new unique id for the element,
			// if this is desired (container-elements sometimes
			// have no ids <connections>.
			if ((child.attributeValue("id") == null) && createId) {
				child.addAttribute("id", IdGenerator.get());
			}

			// Add the element.
			baseElement.add(child);

			// Fire an attribute modified event for the current element.
			firePropertyChange(baseElement, PROP_CHILD_ADDED, baseElement, child);

			// Fire child-modified events for the parent nodes.
			fireChildModified(baseElement);

			// Fire a document-modified for the entire document.
			fireDocumentModified(baseElement);
		}

		public void removeElement(Element element) {
			Element baseElement = element.getParent();

			// Save the document-id just in case
			// some event-listeners have to unregister.
			String eventManagerId = DocumentManager.getElementId(element);
			element.addAttribute("event-manager-id", eventManagerId);

			// Remove the element.
			element.detach();

			// Fire an attribute modified event for the current element.
			firePropertyChange(baseElement, PROP_CHILD_REMOVED, baseElement, element);

			// Fire child-modified events for the parent nodes.
			fireChildModified(baseElement);

			// Fire a document-modified for the entire document.
			fireDocumentModified(baseElement);

			// TIP: Remove all property-change listeners for this
			// element, since they won't be able to unregister after
			// the elemnt has bee detached.
			pcsElementMap.remove(element);

			doc.getRootElement().addAttribute("dirty", "true");
		}

		public Element getDocumentRoot() {
			return doc.getRootElement();
		}
	}
}
