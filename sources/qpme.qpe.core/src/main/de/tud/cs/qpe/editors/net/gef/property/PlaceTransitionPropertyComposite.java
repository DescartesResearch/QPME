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
package de.tud.cs.qpe.editors.net.gef.property;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.model.XPathHelper;

public abstract class PlaceTransitionPropertyComposite extends ElementPropertyComposite {

	private Text name;
	private ControlDecoration nameDecoration;

	public PlaceTransitionPropertyComposite(Element model, Composite parent) {
		super(parent);

		GridLayout layout = new GridLayout(1, false);
		layout.verticalSpacing = 8;
		layout.horizontalSpacing = 12;
		layout.marginLeft = FieldDecorationRegistry.getDefault().getMaximumDecorationWidth();
		setLayout(layout);

		// Output a label for the name.
		new Label(this, SWT.NULL).setText("Name");

		// Output the editable textfield for the name.
		name = new Text(this, SWT.BORDER);
		nameDecoration = new ControlDecoration(name, SWT.LEFT | SWT.TOP);
		FieldDecoration decoration = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);
		nameDecoration.setImage(decoration.getImage());
		nameDecoration.hide();
		name.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		// Update the Label in the editor if the value is changed.
		name.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String newValue = name.getText();
				if (newValue.isEmpty()) {
					name.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
					nameDecoration.setDescriptionText("A name is required.");
					nameDecoration.setShowHover(true);
					nameDecoration.show();
				} else {
					Element conflict = XPathHelper.element(getModel(), "../*[@name='" + newValue.toString() + "' and not(@id='" + getModel().attributeValue("id") + "')]");
					if(conflict == null) {
						name.setBackground(null);
						nameDecoration.setShowHover(false);
						nameDecoration.hide();
						DocumentManager.setAttribute(getModel(), "name", newValue);
					} else {
						name.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
						nameDecoration.setDescriptionText("The name is not unique.");
						nameDecoration.setShowHover(true);
						nameDecoration.show();
					}
				}
			}
		});
		this.layout();
	}

	protected void updatePropertyFields() {
		if (getModel() != null) {
			name.setEnabled(!Boolean.valueOf(getModel().attributeValue("locked", "false")));
			if(!name.getText().equals(getModel().attributeValue("name", "unamed-element"))) {
				name.setText(getModel().attributeValue("name", "unamed-element"));
			}
		}
	}
}
