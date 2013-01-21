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
 * 
 */
package de.tud.cs.qpe.editors.net.gef.property.transition;

import java.beans.PropertyChangeEvent;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.tree.DefaultElement;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import de.tud.cs.qpe.editors.incidence.IncidenceFunctionEditor;
import de.tud.cs.qpe.editors.incidence.IncidenceFunctionEditorInput;
import de.tud.cs.qpe.editors.net.gef.property.PlaceTransitionPropertyComposite;
import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.model.TransitionHelper;
import de.tud.cs.qpe.utils.ColorHelper;
import de.tud.cs.qpe.utils.XmlAttributeEditingSupport;
import de.tud.cs.qpe.utils.XmlAttributeLabelProvider;
import de.tud.cs.qpe.utils.XmlColorAttributeEditingSupport;
import de.tud.cs.qpe.utils.XmlColorAttributeLabelProvider;

public abstract class TransitionPropertyComposite extends
		PlaceTransitionPropertyComposite {
	protected Table modeTable;

	protected TableViewer modeTableViewer;

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
							MessageDialog.openWarning(getShell(), "Missing colors and/or modes", "Before you can open the incidence function editor you must define at least one transition mode and at least one token color in each input and output place of the transition!");
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

			modeTableViewer.setInput(TransitionHelper.listModes(getModel()));
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
		addModeButton.setText("Add Mode");
		addModeButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		addModeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Create a new mode.
				Element mode = createMode();

				// Add the mode to the current transition.
				TransitionHelper.addMode(getModel(), mode);

				updatePropertyFields();
			}
		});

		delModeButton = new Button(modeButtonComposite, SWT.PUSH);
		delModeButton.setText("Delete Mode");
		delModeButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		delModeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection selection = (IStructuredSelection)modeTableViewer.getSelection();
				if (selection.size() == 1) {
					Element mode = (Element)selection.getFirstElement();
					
					if (TransitionHelper.isModeReferencedInIncidenceFunction(mode)) {
						boolean result = MessageDialog.openConfirm(getShell(), "Mode References", "The Mode you are trying to remove is beeing used in this transitions incidence function. Are you sure you want it to be removed? Removing this color will couse the removal of all associated connections. This process is irreversable.");
						if (result) {
							TransitionHelper.removeMode(getModel(), mode);
						}
					} else {
						TransitionHelper.removeMode(getModel(), mode);
					}
					updatePropertyFields();
				}
			}
		});
		modeButtonComposite.layout();
	}
	
	protected Element createMode() {
		// Create a new mode.
		Element mode = new DefaultElement("mode");

		// Generate a new Name and a new Color.
		mode.addAttribute("name", generateName());
		mode.addAttribute("real-color", ColorHelper.generateRandomColor());
		
		return mode;
	}

	protected void initTable() {
		modeTable = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		modeTable.setLayoutData(gd);
		modeTable.setLinesVisible(true);
		modeTable.setHeaderVisible(true);
		
		modeTableViewer = new TableViewer(modeTable);
		modeTableViewer.setContentProvider(new ArrayContentProvider());

		initTableColumns();

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
		TableViewerColumn col = new TableViewerColumn(modeTableViewer, SWT.LEFT);
		col.getColumn().setText("Name");
		col.getColumn().setWidth(120);
		col.setLabelProvider(new XmlAttributeLabelProvider("name", "new mode"));
		col.setEditingSupport(new XmlAttributeEditingSupport(col.getViewer(), "name"));
		
		col = new TableViewerColumn(modeTableViewer, SWT.LEFT);
		col.getColumn().setText("Real Color");
		col.getColumn().setWidth(80);
		col.setLabelProvider(new XmlColorAttributeLabelProvider("real-color"));
		col.setEditingSupport(new XmlColorAttributeEditingSupport(col.getViewer(), "real-color"));
	}

	protected String generateName() {
		String name = "new mode";
		Element mode = TransitionHelper.getModeByName(getModel(), name);
		for (int i = 1; mode != null; i++) {
			name = "new mode " + Integer.toString(i);
			mode = TransitionHelper.getModeByName(getModel(), name);			
		}
		return name;
	}

	public void propertyChange(PropertyChangeEvent event) {
		updatePropertyFields();
	}
}
