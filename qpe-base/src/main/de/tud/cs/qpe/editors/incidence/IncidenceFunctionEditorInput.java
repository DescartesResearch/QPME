/*******************************************************************************
 * Copyright (c) 2005 Chris Aniszczyk
  * All rights reserved. This program and the accompanying materials
  * are made available under the terms of the Eclipse Public License v1.0
  * which accompanies this distribution, and is available at
  * http://www.eclipse.org/legal/epl-v10.html
  *
  * Contributors:
  *    Chris Aniszczyk - initial API and implementation
 *    IBM Corporation
  *******************************************************************************/

package de.tud.cs.qpe.editors.incidence;

import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import de.tud.cs.qpe.QPEBasePlugin;

public class IncidenceFunctionEditorInput implements IEditorInput {
	protected Element content;

	public IncidenceFunctionEditorInput(Element incidenceFunction) {
		content = incidenceFunction;
	}

	public Element getModel() {
		return content;
	}

	public boolean exists() {
		return content != null;
	}

	public ImageDescriptor getImageDescriptor() {
		return QPEBasePlugin.imageDescriptorFromPlugin("org.eclipse.gef.examples.shapes.rcp", "shapes.gif");
	}

	public String getName() {
		String name = content.getDocument().getRootElement().attributeValue("path", "new document") + ":" + content.getParent().attributeValue("name", "new transition");
		return name;
	}

	public IPersistableElement getPersistable() {
		return null;
	}

	public String getToolTipText() {
		String name = content.getDocument().getRootElement().attributeValue("path", "new document") + ":" + content.getParent().attributeValue("name", "new transition");
		return name;
	}

	public Object getAdapter(Class adapter) {
		return null;
	}

	public int hashCode() {
		return content.hashCode();
	}

	public Element getIncidenceFunction() {
		return content;
	}

	public Document getDocument() {
		return content.getDocument();
	}

	/*
	 * Implementation of the euqals method to prevent multiple editors for the
	 * same input to be opened.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof IncidenceFunctionEditorInput) {
			IncidenceFunctionEditorInput editorInput = (IncidenceFunctionEditorInput) arg0;
			return editorInput.getModel().attributeValue("id", "").equals(getModel().attributeValue("id"));
		}
		return false;
	}
	
	public static boolean isValid(Element transitionModel) {
		// Check, if at least one mode is configured 
		boolean valid = true;
		XPath xpathSelector = DocumentHelper.createXPath("modes/mode");
		if(xpathSelector.selectNodes(transitionModel).size() < 1) {
			valid = false;
		}
		 
		xpathSelector = DocumentHelper.createXPath("//connection[@target-id = '" + transitionModel.attributeValue("id") + "']");
		Iterator inputPlaceIterator = xpathSelector.selectNodes(transitionModel).iterator();
		if(!inputPlaceIterator.hasNext()) {
			valid = false;
		}
		while(inputPlaceIterator.hasNext()) {
			Element incommingConnection = (Element) inputPlaceIterator.next();
			xpathSelector = DocumentHelper.createXPath("//place[@id = '" + incommingConnection.attributeValue("source-id") + "']");
			Element inputPlace = (Element) xpathSelector.selectSingleNode(transitionModel);
			xpathSelector = DocumentHelper.createXPath("color-refs/color-ref");
			if(xpathSelector.selectNodes(inputPlace).size() < 1) {
				valid = false;
			}
		}
		
		xpathSelector = DocumentHelper.createXPath("//connection[@source-id = '" + transitionModel.attributeValue("id") + "']");
		Iterator outputPlaceIterator = xpathSelector.selectNodes(transitionModel).iterator();
		if(!outputPlaceIterator.hasNext()) {
			valid = false;
		}
		while(outputPlaceIterator.hasNext()) {
			Element outgoingConnection = (Element) outputPlaceIterator.next();
			xpathSelector = DocumentHelper.createXPath("//place[@id = '" + outgoingConnection.attributeValue("target-id") + "']");
			Element outputPlace = (Element) xpathSelector.selectSingleNode(transitionModel);
			xpathSelector = DocumentHelper.createXPath("color-refs/color-ref");
			if(xpathSelector.selectNodes(outputPlace).size() < 1) {
				valid = false;
			}
		}
		
		return valid;
	}
}
