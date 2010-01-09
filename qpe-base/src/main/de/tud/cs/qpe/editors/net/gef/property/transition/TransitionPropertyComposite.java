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

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import de.tud.cs.qpe.editors.incidence.IncidenceFunctionEditor;
import de.tud.cs.qpe.editors.incidence.IncidenceFunctionEditorInput;
import de.tud.cs.qpe.editors.net.gef.property.PlaceTransitionPropertyComposite;
import de.tud.cs.qpe.model.DocumentManager;

public abstract class TransitionPropertyComposite extends
		PlaceTransitionPropertyComposite {
	protected Table modeTable;

	protected TableViewer modeTableViewer;

	protected String[] columnNames;

	protected Button addModeButton;

	protected Button delModeButton;

	protected Spinner priority;

	public TransitionPropertyComposite(Element net, Composite parent) {
		super(net, parent);

		Button editIncidenceFunctionButton = new Button(this, SWT.NULL);
		editIncidenceFunctionButton.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));
		editIncidenceFunctionButton.setText("Edit Incidence Function");
		editIncidenceFunctionButton
				.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent event) {
						if (!IncidenceFunctionEditorInput.isValid(getModel())) {
							MessageBox messageBox = new MessageBox(PlatformUI
									.getWorkbench().getActiveWorkbenchWindow()
									.getShell(), SWT.ICON_WARNING | SWT.OK);
							messageBox
									.setMessage("Before you can open the incidence function editor you must define at least one transition mode and at least one token color in each input and output place of the transition!");
							messageBox.open();
						} else {
							// Create a new input for the incidence-function
							// editor.
							IEditorInput input = new IncidenceFunctionEditorInput(
									getModel());

							// Open the incidence-function editor.
							if (input.exists()) {
								try {
									PlatformUI.getWorkbench()
											.getActiveWorkbenchWindow()
											.getActivePage().openEditor(input,
													IncidenceFunctionEditor.ID,
													true);
								} catch (PartInitException e) {
									e.printStackTrace();
								}
							}
						}
					}
				});

		this.layout();
	}

	public void setModel(Element model) {
		super.setModel(model);
		updatePropertyFields();
	}

	public void updatePropertyFields() {
		super.updatePropertyFields();
		if (getModel() != null) {
			priority.setSelection(Integer.parseInt(getModel().attributeValue(
					"priority", "0")));

			XPath xpathSelector = DocumentHelper.createXPath("modes/mode");
			List modes = xpathSelector.selectNodes(getModel());
			modeTableViewer.setInput(modes);
			delModeButton.setEnabled(modeTable.getSelectionIndex() != -1);
		}
	}

	public void activate() {
		super.activate();
	}

	public void deactivate() {
		super.deactivate();
	}

	protected void initProperties() {
		new Label(this, SWT.NULL).setText("Priority");
		priority = new Spinner(this, SWT.BORDER);
		priority.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		priority.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent evnt) {
				String newValue = Integer
						.toString(TransitionPropertyComposite.this.priority
								.getSelection());
				DocumentManager.setAttribute(getModel(), "priority", newValue);
			}
		});
		priority.setMinimum(0);
		priority.setMaximum(1000000);
	}

	protected abstract void initColorTable();

	protected void initTable() {
		modeTable = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		modeTable.setLayoutData(gd);
		modeTable.setLinesVisible(true);
		modeTable.setHeaderVisible(true);

		initTableColumns();
		initTableViewer();
		initCellEditors();

		// Add a listener updating the delete-buttons
		// enabled state when another item is selected.
		modeTableViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent e) {
						delModeButton
								.setEnabled(modeTable.getSelectionIndex() != -1);
					}
				});

		// Add a mouse listener for deselecting all selected items
		// when the empty table-area without entries is clicked
		modeTable.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				Point mouseClickPoint = new Point(e.x, e.y);
				if (modeTable.getItem(mouseClickPoint) == null) {
					modeTable.deselectAll();
					updatePropertyFields();
				}
			}
		});
	}

	protected void initTableColumns() {
		TableColumn col = new TableColumn(modeTable, SWT.LEFT);
		col.setText("Name");
		col.setWidth(120);
		col = new TableColumn(modeTable, SWT.LEFT);
		col.setText("Real Color");
		col.setWidth(80);
	}

	protected void initTableViewer() {
		modeTableViewer = new TableViewer(modeTable);
		modeTableViewer.setColumnProperties(columnNames);

		initContentProvider();
		initLabelProvider();
		initCellModifier();
	}

	protected void initContentProvider() {
		modeTableViewer.setContentProvider(new IStructuredContentProvider() {
			public Object[] getElements(Object inputElements) {
				List l = (List) inputElements;
				return l.toArray();
			}

			public void dispose() {
			}

			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
			}
		});
	}

	protected abstract void initLabelProvider();

	protected abstract void initCellModifier();

	protected abstract void initCellEditors();

	protected String generateName() {
		Element modeContainer = getModel().element("modes");
		if (modeContainer == null) {
			return "new mode";
		}

		String name = "mode 1";
		XPath xpathSelector = DocumentHelper.createXPath("mode[@name = '"
				+ name + "']");
		Element mode = (Element) xpathSelector.selectSingleNode(modeContainer);
		for (int i = 1; mode != null; i++) {
			name = "mode " + Integer.toString(i);
			xpathSelector = DocumentHelper.createXPath("mode[@name = '" + name
					+ "']");
			mode = (Element) xpathSelector.selectSingleNode(modeContainer);
		}
		return name;
	}

	public void propertyChange(PropertyChangeEvent event) {
	}
}
