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

import java.io.File;

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
		File docFile = new File(content.getDocument().getRootElement().attributeValue("path", "new document.qpe"));
		return docFile.getName().substring(0, docFile.getName().length() - 4) + ":" + content.getParent().attributeValue("name", "new place");
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
			return editorInput.getModel().getParent().attributeValue("id", "").equals(getModel().getParent().attributeValue("id", ""));
		}
		return false;
	}
}
