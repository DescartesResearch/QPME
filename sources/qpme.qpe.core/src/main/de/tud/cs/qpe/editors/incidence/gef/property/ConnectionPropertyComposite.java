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
package de.tud.cs.qpe.editors.incidence.gef.property;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.dom4j.Element;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.tud.cs.qpe.model.DocumentManager;

public class ConnectionPropertyComposite extends Composite implements PropertyChangeListener {
	protected Element _model;

	protected Text count;

	public ConnectionPropertyComposite(Element model, Composite parent) {
		super(parent, SWT.BORDER);

		this._model = model;

		GridLayout layout = new GridLayout(1, false);
		layout.verticalSpacing = 8;
		layout.horizontalSpacing = 12;
		setLayout(layout);

		// Output a label for the number of tokens.
		new Label(this, SWT.NULL).setText("Number of tokens");

		// Output the editable textfield for the number of tokens.
		count = new Text(this, SWT.BORDER);
		count.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		// Update the Label in the editor if the value is changed.
		count.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String newValue = ConnectionPropertyComposite.this.count.getText();
				try {
					int intValue = Integer.parseInt(newValue);
					if (intValue >= 0) {
						DocumentManager.setAttribute(getModel(), "count", newValue);
					}
				} catch (NumberFormatException nfe) {
				}
			}
		});
		count.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent event) {
				String oldValue = getModel().attributeValue("count", "1");
				String newValue = count.getText();
				try {
					double dValue = Double.parseDouble(newValue);
					if (dValue >= 0) {
						DocumentManager.setAttribute(getModel(), "count", newValue);
					} else {
						count.setText(oldValue);
					}
				} catch (Exception e) {
					count.setText(oldValue);
				}
			}
		});

		// Switched from ModifyListeners to FocusListeners because modifications
		// resulted in realy strange input bebaviour.
		count.addFocusListener(new FocusListener() {
			String oldValue;

			public void focusGained(FocusEvent evnt) {
				oldValue = ConnectionPropertyComposite.this.count.getText();
			}

			public void focusLost(FocusEvent evnt) {
				String newValue = ConnectionPropertyComposite.this.count.getText();
				if ((oldValue != null) && (!oldValue.equals(newValue))) {
					try {
						int intValue = Integer.parseInt(newValue);
						if (intValue >= 0) {
							DocumentManager.setAttribute(getModel(), "count", newValue);
						} else {
							ConnectionPropertyComposite.this.count.setText(oldValue);
						}
					} catch (NumberFormatException nfe) {
						ConnectionPropertyComposite.this.count.setText(oldValue);
					}
				}
			}
		});

		this.layout();
	}

	public void setModel(Element model) {
		this._model = model;
		updatePropertyFields();
	}

	public Element getModel() {
		return _model;
	}

	public void activate() {
		DocumentManager.addPropertyChangeListener(getModel(), this);
	}

	public void deactivate() {
		DocumentManager.removePropertyChangeListener(getModel(), this);
	}

	protected void updatePropertyFields() {
		if (getModel() != null) {
			count.setText(getModel().attributeValue("count", "1"));
		}
	}

	public void propertyChange(PropertyChangeEvent event) {
	}
}
