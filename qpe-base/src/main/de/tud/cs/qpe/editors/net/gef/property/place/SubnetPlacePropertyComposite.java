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
package de.tud.cs.qpe.editors.net.gef.property.place;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.tree.DefaultElement;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import de.tud.cs.qpe.editors.subnet.SubnetEditor;
import de.tud.cs.qpe.editors.subnet.SubnetEditorInput;
import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.utils.ColorHelper;
import de.tud.cs.qpe.utils.IdGenerator;

public class SubnetPlacePropertyComposite extends PlacePropertyComposite {

	public SubnetPlacePropertyComposite(Element net, Composite parent) {
		super(net, parent);
		
		Button editSubnetButton = new Button(this, SWT.NULL);
		editSubnetButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));
		editSubnetButton.setText("Edit Subnet");
		editSubnetButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				// Create a new input for the subnet editor.
				IEditorInput input = new SubnetEditorInput(((Element) getModel()).element("subnet"));

				// Open the incidence-function editor.
				if (input.exists()) {
					try {
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(input, SubnetEditor.ID, true);
					} catch (PartInitException e) {
						e.printStackTrace();
					}
				}
			}
		});

		columnNames = new String[] { "Color", "Initial", "Max" };
		initProperties();
		initColorTable();
	}

	// If a color-ref is added to the subnet-place,
	// it has to be added to the fixed portion of the
	// subnet.
	@Override
	protected void colorRefAdded(Element colorRef) {
		//DocumentManager.addPropertyCangeListener(colorRef, this);
		
		XPath xpathSelector = DocumentHelper.createXPath("../../subnet");
		Element subnet = (Element) xpathSelector.selectSingleNode(colorRef);
		
		xpathSelector = DocumentHelper.createXPath("//color[@id = '" + colorRef.attributeValue("color-id") + "']");
		Element color = (Element) xpathSelector.selectSingleNode(colorRef);
		String colorName = color.attributeValue("name");
		
		// Whenever a new colorRef is added, then: 
		// 1. it has to be added to input-place, actual-population and output-place 
		xpathSelector = DocumentHelper.createXPath("places/place[@name = 'input-place' and @locked = 'true']");
		Element element = (Element) xpathSelector.selectSingleNode(subnet);
		if(element.element("color-refs") == null) {
			element.addElement("color-refs");
		}
		Element inputColorRef = new DefaultElement("color-ref");
		DocumentManager.addChild(element.element("color-refs"), inputColorRef);
		
		xpathSelector = DocumentHelper.createXPath("places/place[@name = 'actual population' and @locked = 'true']");
		element = (Element) xpathSelector.selectSingleNode(subnet);
		if(element.element("color-refs") == null) {
			element.addElement("color-refs");
		}
		Element actualPopulationColorRef = new DefaultElement("color-ref");
		DocumentManager.addChild(element.element("color-refs"), actualPopulationColorRef);

		xpathSelector = DocumentHelper.createXPath("places/place[@name = 'output-place' and @locked = 'true']");
		element = (Element) xpathSelector.selectSingleNode(subnet);
		if(element.element("color-refs") == null) {
			element.addElement("color-refs");
		}
		Element outputColorRef = new DefaultElement("color-ref");
		DocumentManager.addChild(element.element("color-refs"), outputColorRef);
		
		// Copy the attributes to the new color-refs
		Iterator attributeIterator = colorRef.attributeIterator();
		while(attributeIterator.hasNext()) {
			Attribute attribute = (Attribute) attributeIterator.next();
			if("id".equals(attribute.getName())) {
				DocumentManager.setAttribute(inputColorRef, "id", IdGenerator.get());
				DocumentManager.setAttribute(actualPopulationColorRef, "id", IdGenerator.get());
				DocumentManager.setAttribute(outputColorRef, "id", IdGenerator.get());
			} else {
				DocumentManager.setAttribute(inputColorRef, attribute.getName(), attribute.getValue());
				DocumentManager.setAttribute(actualPopulationColorRef, attribute.getName(), attribute.getValue());
				DocumentManager.setAttribute(outputColorRef, attribute.getName(), attribute.getValue());
			}
		}
		
		// 2. one mode has to be added to input and output-transition
		xpathSelector = DocumentHelper.createXPath("transitions/transition[@name = 'input-transition' and @locked = 'true']");
		Element inputTransition = (Element) xpathSelector.selectSingleNode(subnet);
		if(inputTransition.element("modes") == null) {
			inputTransition.addElement("modes");
		}
		Element inputTransitionMode = new DefaultElement("mode");
		DocumentManager.addChild(inputTransition.element("modes"), inputTransitionMode);
		DocumentManager.setAttribute(inputTransitionMode, "name", "Mode:" + colorName);
		DocumentManager.setAttribute(inputTransitionMode, "real-color", ColorHelper.generateRandomColor());
		DocumentManager.setAttribute(inputTransitionMode, "firing-weight", "1.0");
		DocumentManager.setAttribute(inputTransitionMode, "id", IdGenerator.get());
		
		xpathSelector = DocumentHelper.createXPath("transitions/transition[@name = 'output-transition' and @locked = 'true']");
		Element outputTransition = (Element) xpathSelector.selectSingleNode(subnet);
		if(outputTransition.element("modes") == null) {
			outputTransition.addElement("modes");
		}
		Element outputTransitionMode = new DefaultElement("mode");
		DocumentManager.addChild(outputTransition.element("modes"), outputTransitionMode);
		DocumentManager.setAttribute(outputTransitionMode, "name", "Mode:" + colorName);
		DocumentManager.setAttribute(outputTransitionMode, "real-color", ColorHelper.generateRandomColor());
		DocumentManager.setAttribute(outputTransitionMode, "firing-weight", "1.0");
		DocumentManager.setAttribute(outputTransitionMode, "id", IdGenerator.get());
		
		// 3. one connection has to be added to the incidence-function
		//    connecting one color-ref in the input to the same color-ref
		//    in the output-place.
		if(inputTransition.element("connections") == null) {
			inputTransition.addElement("connections");
		}
		Element newConnection = new DefaultElement("connection");
		DocumentManager.addChild(inputTransition.element("connections"), newConnection);
		DocumentManager.setAttribute(newConnection, "id", IdGenerator.get());
		DocumentManager.setAttribute(newConnection, "count", "1");
		DocumentManager.setAttribute(newConnection, "source-id", inputColorRef.attributeValue("id"));
		DocumentManager.setAttribute(newConnection, "target-id", inputTransitionMode.attributeValue("id"));
		newConnection = new DefaultElement("connection");
		DocumentManager.addChild(inputTransition.element("connections"), newConnection);
		DocumentManager.setAttribute(newConnection, "id", IdGenerator.get());
		DocumentManager.setAttribute(newConnection, "count", "1");
		DocumentManager.setAttribute(newConnection, "source-id", inputTransitionMode.attributeValue("id"));
		DocumentManager.setAttribute(newConnection, "target-id", actualPopulationColorRef.attributeValue("id"));

		if(outputTransition.element("connections") == null) {
			outputTransition.addElement("connections");
		}
		newConnection = new DefaultElement("connection");
		DocumentManager.addChild(outputTransition.element("connections"), newConnection);
		DocumentManager.setAttribute(newConnection, "id", IdGenerator.get());
		DocumentManager.setAttribute(newConnection, "count", "1");
		DocumentManager.setAttribute(newConnection, "source-id", actualPopulationColorRef.attributeValue("id"));
		DocumentManager.setAttribute(newConnection, "target-id", outputTransitionMode.attributeValue("id"));
		newConnection = new DefaultElement("connection");
		DocumentManager.addChild(outputTransition.element("connections"), newConnection);
		DocumentManager.setAttribute(newConnection, "id", IdGenerator.get());
		DocumentManager.setAttribute(newConnection, "count", "1");
		DocumentManager.setAttribute(newConnection, "source-id", outputTransitionMode.attributeValue("id"));
		DocumentManager.setAttribute(newConnection, "target-id", outputColorRef.attributeValue("id"));
		
		super.colorRefAdded(colorRef);
	}

	// If a color-ref is removed from the subnet-place,
	// it has to be removed from the fixed portion of the
	// subnet.
	@SuppressWarnings("unchecked")
	@Override
	protected void colorRefModified(String oldColorId, Element colorRef) {
		XPath xpathSelector = DocumentHelper.createXPath("../../subnet");
		Element subnet = (Element) xpathSelector.selectSingleNode(colorRef);
		
		List colorRefs = new ArrayList();
		xpathSelector = DocumentHelper.createXPath("places/place[@name = 'input-place' and @locked = 'true']/color-refs/color-ref[@color-id='" + oldColorId + "']");
		colorRefs.add(xpathSelector.selectSingleNode(subnet));
		xpathSelector = DocumentHelper.createXPath("places/place[@name = 'actual population' and @locked = 'true']/color-refs/color-ref[@color-id='" + oldColorId + "']");
		colorRefs.add(xpathSelector.selectSingleNode(subnet));
		xpathSelector = DocumentHelper.createXPath("places/place[@name = 'output-place' and @locked = 'true']/color-refs/color-ref[@color-id='" + oldColorId + "']");
		colorRefs.add(xpathSelector.selectSingleNode(subnet));

		/*Iterator colorRefIterator = colorRefs.iterator();
		while(colorRefIterator.hasNext()) {
			Element curColorRef = (Element) colorRefIterator.next();
			Iterator attributeIterator = colorRef.attributeIterator();
			while(attributeIterator.hasNext()) {
				Attribute attribute = (Attribute) attributeIterator.next();
				if(!"id".equals(attribute.getName())) {
					//curColorRef.addAttribute(attribute.getName(), attribute.getValue());
				}
			}
		}*/

		super.colorRefModified(oldColorId, colorRef);
	}

	// If a color-ref is modified, the modification has
	// to be transfered to fixed part of the subnet.
	@SuppressWarnings("unchecked")
	@Override
	protected void colorRefRemoved(Element colorRef) {
		super.colorRefRemoved(colorRef);
//		DocumentManager.removePropertyChangeListener(colorRef, this);
		
		XPath xpathSelector = DocumentHelper.createXPath("../../subnet");
		Element subnet = (Element) xpathSelector.selectSingleNode(colorRef);
			
		// Select the color-ref elements in input-, outputplace and actual population.
		List removableElements = new ArrayList();
		xpathSelector = DocumentHelper.createXPath("places/place[@name = 'input-place' and @locked = 'true']/color-refs/color-ref[@color-id = '" + colorRef.attributeValue("color-id") + "']");
		Element inputPlaceColorRef = (Element) xpathSelector.selectSingleNode(subnet);
		removableElements.add(inputPlaceColorRef);
		xpathSelector = DocumentHelper.createXPath("places/place[@name = 'actual population' and @locked = 'true']/color-refs/color-ref[@color-id = '" + colorRef.attributeValue("color-id") + "']");
		Element actualPopulationColorRef = (Element) xpathSelector.selectSingleNode(subnet);
		removableElements.add(actualPopulationColorRef);
		xpathSelector = DocumentHelper.createXPath("places/place[@name = 'output-place' and @locked = 'true']/color-refs/color-ref[@color-id = '" + colorRef.attributeValue("color-id") + "']");
		Element outputPlaceColorRef = (Element) xpathSelector.selectSingleNode(subnet);
		removableElements.add(outputPlaceColorRef);
		
		// Select all the connections going in and out to the color refs beeing removed.
		xpathSelector = DocumentHelper.createXPath("//connection[@source-id = '" + inputPlaceColorRef.attributeValue("id") + "' or @target-id = '" + inputPlaceColorRef.attributeValue("id") + "']");
		List inputPlaceColorRefConnections = xpathSelector.selectNodes(subnet);
		removableElements.addAll(inputPlaceColorRefConnections);
		xpathSelector = DocumentHelper.createXPath("//connection[@source-id = '" + actualPopulationColorRef.attributeValue("id") + "' or @target-id = '" + actualPopulationColorRef.attributeValue("id") + "']");
		List actualPopulationColorRefConnections = xpathSelector.selectNodes(subnet);
		removableElements.addAll(actualPopulationColorRefConnections);
		xpathSelector = DocumentHelper.createXPath("//connection[@source-id = '" + outputPlaceColorRef.attributeValue("id") + "' or @target-id = '" + outputPlaceColorRef.attributeValue("id") + "']");
		List outputPlaceColorRefConnection = xpathSelector.selectNodes(subnet);
		removableElements.addAll(outputPlaceColorRefConnection);
		
		Iterator removableElementIterator = removableElements.iterator();
		while(removableElementIterator.hasNext()) {
			Element removableElement = (Element) removableElementIterator.next();
			DocumentManager.removeElement(removableElement);
		}
		
		super.colorRefRemoved(colorRef);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
	}
	

}
