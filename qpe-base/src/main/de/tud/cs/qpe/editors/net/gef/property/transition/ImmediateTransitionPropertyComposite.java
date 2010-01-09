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
package de.tud.cs.qpe.editors.net.gef.property.transition;

import java.util.Iterator;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.tree.DefaultElement;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColorCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.utils.ColorHelper;
import de.tud.cs.qpe.utils.DoubleCellEditor;
import de.tud.cs.qpe.utils.ITableLabelColorProvider;

public class ImmediateTransitionPropertyComposite extends TransitionPropertyComposite {

	protected Text firingWeight;

	public ImmediateTransitionPropertyComposite(Element net, Composite parent) {
		super(net, parent);
		columnNames = new String[] { "Name", "Real Color", "Firing Weight" };
		initProperties();
		initColorTable();
	}

	public void updatePropertyFields() {
		super.updatePropertyFields();

		if (getModel() != null) {
			firingWeight.setText(getModel().attributeValue("weight", "1.0"));
		}
	}

	protected void initProperties() {
		super.initProperties();

		new Label(this, SWT.NULL).setText("Firing Weight");
		firingWeight = new Text(this, SWT.BORDER);
		firingWeight.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		firingWeight.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent event) {
				String oldValue = getModel().attributeValue("weight", "1.0");
				String newValue = ImmediateTransitionPropertyComposite.this.firingWeight.getText();
				try {
					double dValue = Double.parseDouble(newValue);
					if (dValue >= 0) {
						DocumentManager.setAttribute(getModel(), "weight", newValue);
					} else {
						firingWeight.setText(oldValue);
					}
				} catch (Exception e) {
					ImmediateTransitionPropertyComposite.this.firingWeight.setText(oldValue);
				}
			}
		});
		firingWeight.setText(getModel().attributeValue("weight", "1.0"));
	}

	protected void initColorTable() {
		Label modeName = new Label(this, SWT.NULL);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		modeName.setLayoutData(gd);
		modeName.setText("Modes");
		initTable();
		// Add buttons for ading and deleting modes
		Composite modeButtonComposite = new Composite(this, SWT.NULL);
		modeButtonComposite.setLayout(new GridLayout(2, false));
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		modeButtonComposite.setLayoutData(gd);
		addModeButton = new Button(modeButtonComposite, SWT.PUSH);
		addModeButton.setText("Add mode");
		addModeButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		addModeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Element modeContainer = getModel().element("modes");
				if (modeContainer == null) {
					modeContainer = getModel().addElement("modes");
				}
				// Create a new mode.
				Element mode = new DefaultElement("mode");

				// Generate a new Name and a new Color.
				mode.addAttribute("name", generateName());
				mode.addAttribute("real-color", ColorHelper.generateRandomColor());
				mode.addAttribute("firing-weight", "1.0");

				// Add the mode to the current transition.
				DocumentManager.addChild(modeContainer, mode);

				// Update the visuals.
				updatePropertyFields();
			}
		});

		delModeButton = new Button(modeButtonComposite, SWT.PUSH);
		delModeButton.setText("Del mode");
		delModeButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		delModeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Get the index of the selected color-ref.
				int selectionIndex = modeTable.getSelectionIndex();

				// Get the color-ref with the selected index.
				XPath xpathSelector = DocumentHelper.createXPath("modes/mode[" + Integer.toString(selectionIndex + 1) + "]");
				Element mode = (Element) xpathSelector.selectSingleNode(getModel());

				// Check if this mode is used and eventually let the user
				// confirm cascading delete.
				xpathSelector = DocumentHelper.createXPath("//transition/connections/connection[@source-id = '" + mode.attributeValue("id", "")
						+ "'] | //transition/connections/connection[@source-id = '" + mode.attributeValue("id", "") + "']");
				List connectionsUsingCurrentMode = xpathSelector.selectNodes(mode);
				if (connectionsUsingCurrentMode.size() > 0) {
					MessageBox messageBox = new MessageBox(ImmediateTransitionPropertyComposite.this.getShell(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
					messageBox
							.setMessage("The Mode you are trying to remove is beeing used in this transitions incidence function. Are you sure you want it to be removed? Removing this color will couse the removal of all associated connections. This process is irreversable.");
					int buttonId = messageBox.open();
					if (buttonId == SWT.YES) {
						// Remove the connections using this mode.
						Iterator usingConnectionIterator = connectionsUsingCurrentMode.iterator();
						while (usingConnectionIterator.hasNext()) {
							Element connection = (Element) usingConnectionIterator.next();
							DocumentManager.removeElement(connection);
						}

						// Remove the selected mode from the current transition.
						DocumentManager.removeElement(mode);
					}
				} else {
					// Remove the selected colorRef from the current node.
					DocumentManager.removeElement(mode);
				}

				// Update the visuals.
				updatePropertyFields();
			}
		});
		modeButtonComposite.layout();
	}

	protected void initTableColumns() {
		super.initTableColumns();
		TableColumn col = new TableColumn(modeTable, SWT.LEFT);
		col.setText("Firing Weight");
		col.setWidth(100);
	}

	protected void initLabelProvider() {
		modeTableViewer.setLabelProvider(new ITableLabelColorProvider() {
			public void dispose() {
			}

			public Image getColumnImage(Object element, int columnIndex) {
				return null;
			}

			public String getColumnText(Object element, int columnIndex) {
				Element mode = (Element) element;
				switch (columnIndex) {
				case 0:
					return mode.attributeValue("name", "new mode");
				case 1:
					return "";
				case 2:
					return mode.attributeValue("firing-weight", "1.0");
				}
				return null;
			}

			public void addListener(ILabelProviderListener listener) {
			}

			public void removeListener(ILabelProviderListener listener) {
			}

			public boolean isLabelProperty(Object element, String property) {
				return false;
			}

			public org.eclipse.swt.graphics.Color getForeground(Object element, int columnIndex) {
				return null;
			}

			public org.eclipse.swt.graphics.Color getBackground(Object element, int columnIndex) {
				if (columnIndex == 1) {
					Element mode = (Element) element;
					return new Color(null, ColorHelper.getRGBFromString(mode.attributeValue("real-color", "#ffffff")));
				}
				return null;
			}
		});
	}

	protected void initCellModifier() {
		modeTableViewer.setCellModifier(new ICellModifier() {
			public boolean canModify(Object element, String property) {
				return true;
			}

			public Object getValue(Object element, String property) {
				// Get the index first.
				int index = -1;
				for (int i = 0; i < columnNames.length; i++) {
					if (columnNames[i].equals(property)) {
						index = i;
						break;
					}
				}
				Element mode = (Element) element;

				switch (index) {
				case 0:
					return mode.attributeValue("name", "new mode");
				case 1:
					return null;
				case 2:
					return (Double) Double.parseDouble(mode.attributeValue("firing-weight", "1"));
				}

				return null;
			}

			public void modify(Object element, String property, Object value) {
				// Get the index first.
				int index = -1;
				for (int i = 0; i < columnNames.length; i++) {
					if (columnNames[i].equals(property)) {
						index = i;
						break;
					}
				}

				Element mode = null;
				if (element instanceof Item) {
					TableItem item = (TableItem) element;
					mode = (Element) item.getData();
				} else {
					mode = (Element) element;
				}

				switch (index) {
				case 0:
					DocumentManager.setAttribute(mode, "name", (String) value);
					break;
				case 1:
					if (value != null) {
						DocumentManager.setAttribute(mode, "real-color", (String) ColorHelper.getStringfromRGB((RGB) value));
					}
					break;
				case 2:
					double dValue = ((Double) value).doubleValue();
					if (dValue >= 0) {
						DocumentManager.setAttribute(mode, "firing-weight", ((Double) value).toString());
					}
					break;
				}

				modeTableViewer.update(mode, null);
			}
		});
	}

	protected void initCellEditors() {
		CellEditor[] cellEditors = new CellEditor[4];
		cellEditors[0] = new TextCellEditor(modeTableViewer.getTable());
		cellEditors[1] = new ColorCellEditor(modeTableViewer.getTable());
		cellEditors[2] = new DoubleCellEditor(modeTableViewer.getTable());

		modeTableViewer.setCellEditors(cellEditors);
	}

}
