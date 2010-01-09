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
