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
package de.tud.cs.qpe.editors.net.gef.property.place;

import java.util.List;

import org.dom4j.Element;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import de.tud.cs.qpe.editors.net.gef.property.ColorRefTable;
import de.tud.cs.qpe.editors.net.gef.property.IColorRefTableListener;
import de.tud.cs.qpe.editors.net.gef.property.PlaceTransitionPropertyComposite;
import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.model.PlaceHelper;
import de.tud.cs.qpe.model.SubnetHelper;
import de.tud.cs.qpe.utils.CellValidators;
import de.tud.cs.qpe.utils.XmlAttributeEditingSupport;
import de.tud.cs.qpe.utils.XmlAttributeLabelProvider;

public abstract class PlacePropertyComposite extends PlaceTransitionPropertyComposite implements IColorRefTableListener {
	
	protected Combo departureDiscipline;
	
	protected ColorRefTable colorTable;

	public PlacePropertyComposite(Element model, Composite parent) {
		super(model, parent);
	}

	protected void updatePropertyFields() {
		super.updatePropertyFields();

		if (getModel() != null) {
			departureDiscipline.setText((String) getModel().attributeValue("departure-discipline", "NORMAL"));
			departureDiscipline.setEnabled(!PlaceHelper.isLocked(getModel()));	
			
			colorTable.setEnabled(!SubnetHelper.isRestrictedPlace(getModel()));
		}
	}

	public void activate() {
		super.activate();
		colorTable.activate();
		updatePropertyFields();
	}

	public void deactivate() {
		colorTable.deactivate();
		super.deactivate();
	}

	protected void initProperties() {
		new Label(this, SWT.NULL).setText("Departure Discipline");
		departureDiscipline = new Combo(this, SWT.BORDER);
		departureDiscipline.add("NORMAL");
		departureDiscipline.add("FIFO");
		departureDiscipline.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		departureDiscipline.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent evnt) {
				String oldValue = getModel().attributeValue("departure-discipline", "NORMAL");
				String newValue = PlacePropertyComposite.this.departureDiscipline.getText();
				if (!oldValue.equals(newValue)) {
					DocumentManager.setAttribute(getModel(), "departure-discipline", newValue);
					departureDisciplineModified(oldValue, newValue);
				}
			}
		});		
	}
	
	protected void initColorTable() {
		colorTable = new ColorRefTable(this) {
			@Override
			protected List<Element> getAvailableColors() {
				return PlaceHelper.listVisibleColors(getModel()) ;
			}
			
			@Override
			protected List<Element> getColorReferences() {
				return PlaceHelper.listColorReferences(getModel());
			}			
		};
		colorTable.addColorRefTableListener(this);
		
		initTableColumns();
	}

	protected void initTableColumns() {		
		TableViewerColumn col = colorTable.createColumn("Initial", 50);
		colorTable.setColumnLabelProvider(col, new XmlAttributeLabelProvider("initial-population", "0"));
		XmlAttributeEditingSupport editor = new XmlAttributeEditingSupport(col.getViewer(), "initial-population");
		editor.setValidator(CellValidators.newNonNegativeIntegerValidator());
		colorTable.setColumnEditingSupport(col, editor);
		
		col = colorTable.createColumn("Max", 50);
		colorTable.setColumnLabelProvider(col, new XmlAttributeLabelProvider("maximum-capacity", "0"));
		editor = new XmlAttributeEditingSupport(col.getViewer(), "maximum-capacity");
		editor.setValidator(CellValidators.newNonNegativeIntegerValidator());
		colorTable.setColumnEditingSupport(col, editor);
	}
	
	public void colorRefAdded(Element colorRef) {		
		// Set the default attributes.
		colorRef.addAttribute("initial-population", "0");
		colorRef.addAttribute("maximum-capacity", "0");
		
		PlaceHelper.addColorReference(getModel(), colorRef);
	}
	
	public void colorRefRemoved(Element colorRef) {
		PlaceHelper.removeColorReference(getModel(), colorRef);
	}
	
	public void colorRefModified(String oldColorId, Element colorRef) {
	}
	
	protected void departureDisciplineModified(String oldDiscipline, String newDiscipline) {
		
	}
}
