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
 *  2010/06/16  Simon Spinner     Updated synchronization of color references
 * 
 */
package de.tud.cs.qpe.editors.net.gef.property.place;

import java.util.Iterator;

import javax.xml.XMLConstants;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.tree.DefaultElement;
import org.eclipse.jface.viewers.TableViewerColumn;
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
import de.tud.cs.qpe.model.IncidenceFunctionHelper;
import de.tud.cs.qpe.model.NetHelper;
import de.tud.cs.qpe.model.PlaceHelper;
import de.tud.cs.qpe.model.SubnetHelper;
import de.tud.cs.qpe.model.TransitionHelper;
import de.tud.cs.qpe.utils.ColorHelper;
import de.tud.cs.qpe.utils.IdGenerator;
import de.tud.cs.qpe.utils.XmlAttributeLabelProvider;
import de.tud.cs.qpe.utils.XmlEnumerationAttributeEditingSupport;

public class SubnetPlacePropertyComposite extends PlacePropertyComposite {
	
	private String[] directionItems = new String[] {"in", "out", "both"};

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
		initProperties();
		initColorTable();
	}
	
	@Override
	protected void initTableColumns() {
		super.initTableColumns();
		TableViewerColumn col = colorTable.createColumn("Direction", 70);
		colorTable.setColumnLabelProvider(col, new XmlAttributeLabelProvider("direction", "both"));
		colorTable.setColumnEditingSupport(col, new XmlEnumerationAttributeEditingSupport(col.getViewer(), "direction") {	
			@Override
			protected Object[] getItems() {
				return directionItems;
			}
			
			@Override
			protected void setValue(Object element, Object value) {
				super.setValue(element, value);
				Element colorRef = (Element)element;
				colorRefModified(colorRef.attributeValue("color-id"), colorRef);
			}
		});
	}
	
	// If a color-ref is added to the subnet-place,
	// it has to be added to the fixed portion of the
	// subnet.
	@Override
	public void colorRefAdded(Element colorRef) {
		super.colorRefAdded(colorRef);
		
		colorRef.addAttribute(new QName("type", new Namespace("xsi", XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI)), "subnet-color-reference");
		
		// initialize attributes
		colorRef.addAttribute("direction", "both");
		
		Element subnet = SubnetHelper.getSubnetOfColorReference(colorRef);
		
		// Whenever a new colorRef is added, then: 
		// 1. it has to be added to input-place, actual-population and output-place
		Element inputPlace = SubnetHelper.getInputPlaceOfSubnet(subnet);
		propagateColorReferenceDown(inputPlace, colorRef.attributeValue("color-id"), colorRef);
		
		Element actualPopulationPlace = SubnetHelper.getActualPopulationPlaceOfSubnet(subnet);
		if (actualPopulationPlace != null) {
			propagateColorReferenceDown(actualPopulationPlace, colorRef.attributeValue("color-id"), colorRef);
		}
		
		Element outputPlace = SubnetHelper.getOutputPlaceOfSubnet(subnet);
		propagateColorReferenceDown(outputPlace, colorRef.attributeValue("color-id"), colorRef);
	}

	// If a color-ref is modified, the modification has
	// to be transfered to fixed part of the subnet.
	@Override
	public void colorRefModified(String oldColorId, Element colorRef) {
		Element subnet = SubnetHelper.getSubnetOfColorReference(colorRef);

		Element inputPlace = SubnetHelper.getInputPlaceOfSubnet(subnet);
		propagateColorReferenceDown(inputPlace, oldColorId, colorRef);
		
		Element actualPopulationPlace = SubnetHelper.getActualPopulationPlaceOfSubnet(subnet);
		if (actualPopulationPlace != null) {
			propagateColorReferenceDown(actualPopulationPlace, oldColorId, colorRef);
		}
		
		Element outputPlace = SubnetHelper.getOutputPlaceOfSubnet(subnet);
		propagateColorReferenceDown(outputPlace, oldColorId, colorRef);

		super.colorRefModified(oldColorId, colorRef);
	}

	// If a color-ref is removed from the subnet-place,
	// it has to be removed from the fixed portion of the
	// subnet.
	@Override
	public void colorRefRemoved(Element colorRef) {
		super.colorRefRemoved(colorRef);
		
		Element subnet = SubnetHelper.getSubnetOfColorReference(colorRef);
		Element color = NetHelper.getColorByReference(colorRef);
		String colorName = color.attributeValue("name");
			
		// Remove color reference in input-place.
		Element inputPlace = SubnetHelper.getInputPlaceOfSubnet(subnet);
		Element inputPlaceColorRef = PlaceHelper.getColorReferenceByColorId(inputPlace, colorRef.attributeValue("color-id"));
		if (inputPlaceColorRef != null) PlaceHelper.removeColorReference(inputPlace, inputPlaceColorRef);

		// Remove color reference in the actual population place (if existing).
		Element actualPopulationPlace = SubnetHelper.getActualPopulationPlaceOfSubnet(subnet);
		if (actualPopulationPlace != null) {
			Element actualPopulationColorRef = PlaceHelper.getColorReferenceByColorId(actualPopulationPlace, colorRef.attributeValue("color-id"));
			if (actualPopulationColorRef != null) PlaceHelper.removeColorReference(actualPopulationPlace, actualPopulationColorRef);
		}
		
		// Remove color reference in the output-place.
		Element outputPlace = SubnetHelper.getOutputPlaceOfSubnet(subnet);
		Element outputPlaceColorRef = PlaceHelper.getColorReferenceByColorId(outputPlace, colorRef.attributeValue("color-id"));
		if (outputPlaceColorRef != null) PlaceHelper.removeColorReference(outputPlace, outputPlaceColorRef);
		
		// Remove orphaned modes in the input-transition/output-transition
		Element inputTransition = SubnetHelper.getInputTransitionOfSubnet(subnet);
		if (inputTransition != null) {
			Element mode = TransitionHelper.getModeByName(inputTransition, "Mode:" + colorName);
			if(mode != null) {
				TransitionHelper.removeMode(inputTransition, mode);
			}
		}		
		Element outputTransition = SubnetHelper.getOutputTransitionOfSubnet(subnet);
		if (outputTransition != null) {
			Element mode = TransitionHelper.getModeByName(outputTransition, "Mode:" + colorName);
			if(mode != null) {
				TransitionHelper.removeMode(outputTransition, mode);
			}
		}
		
		super.colorRefRemoved(colorRef);
	}
	
	@Override
	protected void departureDisciplineModified(String oldDiscipline,
			String newDiscipline) {
		super.departureDisciplineModified(oldDiscipline, newDiscipline);
		
		Element subnet = SubnetHelper.getSubnet(getModel());
		Element outputPlace = SubnetHelper.getOutputPlaceOfSubnet(subnet);
		DocumentManager.setAttribute(outputPlace, "departure-discipline", newDiscipline);
	}
	
	private void propagateColorReferenceDown(Element place, String oldColorId, Element colorRef) {
		/*
		 * If a color reference is added or modified in the subnet place this method creates
		 * or updates the corresponding color references in the input-place, actual population (if existing)
		 * and output-place in the subnet.
		 * 
		 * If input-transition and/or output-transition also exist then corresponding modes
		 * for the current color are added/updated including necessary connections.
		 */
		
		Element placeColorRef = PlaceHelper.getColorReferenceByColorId(place, oldColorId);
		Element subnet = SubnetHelper.getSubnetOfPlace(place);
		Element color = NetHelper.getColorByReference(colorRef);
		String colorName = color.attributeValue("name");
		boolean inputPlace = SubnetHelper.isInputPlace(place);
		boolean outputPlace = SubnetHelper.isOutputPlace(place);
		boolean actualPopulationPlace = SubnetHelper.isActualPopulationPlace(place);
		
		String direction = colorRef.attributeValue("direction", "both");
		// Only add/update color reference in this place if this place is affected 
		if((inputPlace && direction.equals("in")) || (outputPlace && direction.equals("out")) || direction.equals("both")) {
			if(placeColorRef == null) {
				// This place does not have a color reference for the current color.
				placeColorRef = new DefaultElement("color-ref");
				PlaceHelper.addColorReference(place, placeColorRef);
				DocumentManager.setAttribute(placeColorRef, "id", IdGenerator.get());
			}
			
			//Copy all attributes of color reference, except the direction attribute
			Iterator attributeIterator = colorRef.attributeIterator();
			while(attributeIterator.hasNext()) {
				Attribute attribute = (Attribute) attributeIterator.next();
				// Do not copy direction and id attributes
				if("id".equals(attribute.getName()) || "direction".equals(attribute.getName())) {
					continue;
				}
				
				// If direction = "in" or "both" the initial population is placed in the input place
				// otherwise it is place in the output place
				if("initial-population".equals(attribute.getName())) {
					if(direction.equals("both") && (outputPlace || actualPopulationPlace)) {
						DocumentManager.setAttribute(placeColorRef, attribute.getName(), "0");
						continue;
					}
				}
				
				DocumentManager.setAttribute(placeColorRef, attribute.getName(), attribute.getValue());
			}
			
			
			if (!outputPlace) {
				// Either input or actual population place
				Element inputTransition = SubnetHelper.getInputTransitionOfSubnet(subnet);
				Element inputTransitionMode = null;
				if (inputTransition != null) {
					// An input transition exisits, then ensure that a mode for the current color exists.
					// Otherwise create it.
					inputTransitionMode = TransitionHelper.getModeByName(inputTransition, "Mode:" + colorName);
					if (inputTransitionMode == null) {
						inputTransitionMode = new DefaultElement("mode");
						TransitionHelper.addMode(inputTransition, inputTransitionMode);
						DocumentManager.setAttribute(inputTransitionMode, "name", "Mode:" + colorName);
						DocumentManager.setAttribute(inputTransitionMode, "real-color", ColorHelper.generateRandomColor());
						DocumentManager.setAttribute(inputTransitionMode, "firing-weight", "1.0");
						DocumentManager.setAttribute(inputTransitionMode, "id", IdGenerator.get());
					}
				
					// Create connections between place and mode, if not present
					if (actualPopulationPlace) {
						Element outColorRef = PlaceHelper.getColorReferenceByColorId(place, colorRef.attributeValue("color-id"));
						if (!IncidenceFunctionHelper.existsConnection(inputTransition, inputTransitionMode, outColorRef)) {
							Element connection = IncidenceFunctionHelper.createConnection(inputTransitionMode, outColorRef, 1);
							IncidenceFunctionHelper.addConnection(inputTransition, connection);
						}
					} else {
						Element inColorRef = PlaceHelper.getColorReferenceByColorId(place, colorRef.attributeValue("color-id"));
						if (!IncidenceFunctionHelper.existsConnection(inputTransition, inColorRef, inputTransitionMode)) {
							Element connection = IncidenceFunctionHelper.createConnection(inColorRef, inputTransitionMode, 1);
							IncidenceFunctionHelper.addConnection(inputTransition, connection);
						}
					}
					
					// if color changed delete the mode of the old color because that is orphaned now.
					if (!oldColorId.equals(colorRef.attributeValue("color-id"))) {
						String oldColorName = NetHelper.getColorById(place, oldColorId).attributeValue("name");
						Element oldMode = TransitionHelper.getModeByName(inputTransition, "Mode:" + oldColorName);
						if(oldMode != null) {
							TransitionHelper.removeMode(inputTransitionMode, oldMode);
						}
					}
				}
			}
			
			if (!inputPlace) {
				// Either output or actual population place
				Element outputTransition = SubnetHelper.getOutputTransitionOfSubnet(subnet);
				Element outputTransitionMode = null;
				if (outputTransition != null) {
					outputTransitionMode = TransitionHelper.getModeByName(outputTransition, "Mode:" + colorName);
					if (outputTransitionMode == null) {
						outputTransitionMode = new DefaultElement("mode");
						TransitionHelper.addMode(outputTransition, outputTransitionMode);
						DocumentManager.setAttribute(outputTransitionMode, "name", "Mode:" + colorName);
						DocumentManager.setAttribute(outputTransitionMode, "real-color", ColorHelper.generateRandomColor());
						DocumentManager.setAttribute(outputTransitionMode, "firing-weight", "1.0");
						DocumentManager.setAttribute(outputTransitionMode, "id", IdGenerator.get());
					}

					// An output transition exisits, then ensure that a mode for the current color exists.
					// Otherwise create it.
					if (actualPopulationPlace) {
						Element inColorRef = PlaceHelper.getColorReferenceByColorId(place, colorRef.attributeValue("color-id"));
						if (!IncidenceFunctionHelper.existsConnection(outputTransition, inColorRef, outputTransitionMode)) {
							Element connection = IncidenceFunctionHelper.createConnection(inColorRef, outputTransitionMode, 1);
							IncidenceFunctionHelper.addConnection(outputTransition, connection);
						}
					} else {
						Element outColorRef = PlaceHelper.getColorReferenceByColorId(place, colorRef.attributeValue("color-id"));
						if (!IncidenceFunctionHelper.existsConnection(outputTransition, outputTransitionMode, outColorRef)) {
							Element connection = IncidenceFunctionHelper.createConnection(outputTransitionMode, outColorRef, 1);
							IncidenceFunctionHelper.addConnection(outputTransition, connection);
						}
					}
					
					// if color changed delete the mode of the old color because that is orphaned now.
					if (!oldColorId.equals(colorRef.attributeValue("color-id"))) {
						String oldColorName = NetHelper.getColorById(place, oldColorId).attributeValue("name");
						Element oldMode = TransitionHelper.getModeByName(outputTransition, "Mode:" + oldColorName);
						if(oldMode != null) {
							TransitionHelper.removeMode(outputTransitionMode, oldMode);
						}
					}
				}
			}
		} else {
			if(placeColorRef != null) {
				// There should not be a color reference for the current defined in this place.
				// Remove the orphaned color reference from the place.
				if (direction.equals("out")) {
					PlaceHelper.removeIncomingConnections(place, colorRef);

					Element inputTransition = SubnetHelper.getInputTransitionOfSubnet(subnet);
					Element inputTransitionMode = TransitionHelper.getModeByName(inputTransition, "Mode:" + colorName);
					if(inputTransitionMode != null) {
						TransitionHelper.removeMode(inputTransition, inputTransitionMode);
					}
				} else if (direction.equals("in")) {
					PlaceHelper.removeOutgoingConnections(place, colorRef);

					Element outputTransition = SubnetHelper.getOutputTransitionOfSubnet(subnet);
					Element outputTransitionMode = TransitionHelper.getModeByName(outputTransition, "Mode:" + colorName);
					if(outputTransitionMode != null) {
						TransitionHelper.removeMode(outputTransition, outputTransitionMode);
					}
				}
				
				//Remove orphaned color references
				PlaceHelper.removeColorReference(place, placeColorRef);
			}
		}
	}
}
