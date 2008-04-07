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

package de.tud.cs.qpe.editors.subnet;

import org.dom4j.Document;
import org.dom4j.Element;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import de.tud.cs.qpe.QPEBasePlugin;

public class SubnetEditorInput implements IEditorInput {
	protected Element content;

	public SubnetEditorInput(Element subnet) {
		content = subnet;
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

	public Element getSubnet() {
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
		if (arg0 instanceof SubnetEditorInput) {
			SubnetEditorInput editorInput = (SubnetEditorInput) arg0;
			return editorInput.getModel().attributeValue("id", "").equals(getModel().attributeValue("id"));
		}
		return false;
	}
}
