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
package de.tud.cs.qpe.editors.subnet;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.model.NetHelper;
import de.tud.cs.qpe.model.SubnetHelper;
import de.tud.cs.qpe.utils.ColorHelper;
import de.tud.cs.qpe.utils.XmlAttributeEditingSupport;
import de.tud.cs.qpe.utils.XmlAttributeLabelProvider;
import de.tud.cs.qpe.utils.XmlColorAttributeEditingSupport;
import de.tud.cs.qpe.utils.XmlColorAttributeLabelProvider;

public class SubnetColorEditorPage extends EditorPart implements
		PropertyChangeListener {
	protected Table colorTable;

	protected TableViewer colorTableViewer;

	protected Button addColorButton;

	protected Button delColorButton;

	protected Element model;
	
	@Override
	public void doSave(IProgressMonitor monitor) {}

	@Override
	public void doSaveAs() {}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);

		SubnetEditorInput netInput = (SubnetEditorInput) input;
		model = netInput.getSubnet();

		// Add the color editor as listener to modifications of the
		// current document.
		DocumentManager.addPropertyChangeListener(model, this);
	}
	
	@Override
	public void dispose() {
		if (model != null) {
			DocumentManager.removePropertyChangeListener(model, this);
		}
		super.dispose();
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout());
		
		Label colorName = new Label(parent, SWT.NULL);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		colorName.setLayoutData(gd);
		colorName.setText("Colors");
		initTable(parent);
		// Add buttons for ading and deleting colors
		Composite colorButtonComposite = new Composite(parent, SWT.NULL);
		colorButtonComposite.setLayout(new GridLayout(2, false));
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		colorButtonComposite.setLayoutData(gd);
		addColorButton = new Button(colorButtonComposite, SWT.PUSH);
		addColorButton.setText("Add Color");
		addColorButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		addColorButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Element newColor = createColor();
				DocumentManager.addChild(model.element("colors"), newColor);
				updatePropertyFields();
			}
		});

		delColorButton = new Button(colorButtonComposite, SWT.PUSH);
		delColorButton.setText("Delete Color");
		delColorButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		delColorButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection selection = (IStructuredSelection)colorTableViewer.getSelection();
				if (selection.size() == 1) {
					Element color = (Element)selection.getFirstElement();
					// Check if the color is used. If yes, show a dialog asking the
					// user if he is sure. If yes then all usages of this collor
					// have to be removed.
					if (NetHelper.isColorReferencedInNet(model, color)) {
						boolean result = MessageDialog.openConfirm(getSite().getShell(), "Color References", "The Color you are trying to remove is referenced in the current net. Are you sure you want it to be removed? Removing this color will cause the removal of all its references. This process is irreversible.");
						if (result) {
							// Remove the color and all of its references.
							NetHelper.removeColor(model, color);
						}
					} else {
						NetHelper.removeColor(model, color);
					}
				}
				updatePropertyFields();
			}
		});
		colorButtonComposite.layout();
		
		updatePropertyFields();
	}

	@Override
	public void setFocus() {}

	public void updatePropertyFields() {
		List<Element> colors = SubnetHelper.listVisibleColors(model);
		colorTableViewer.setInput(colors);
		delColorButton.setEnabled(isOwnColorSelected());
	}

	private Element createColor() {
		// Add the colors to indexed hashmaps.
		// This way fast checks for name and color
		// duplicates can be performed.
		HashMap<String, Element> nameIndex = new HashMap<String, Element>();
		HashMap<String, Element> colorIndex = new HashMap<String, Element>();

		List<Element> colors = SubnetHelper.listVisibleColors(model);
		for(Element color : colors) {
			nameIndex.put(color.attributeValue("name"), color);
			String rgb = color.attributeValue("real-color");
			colorIndex.put(rgb, color);
		}

		// Find a new name.
		Element newColor = new DefaultElement("color");
		for (int x = 0;; x++) {
			if ((x == 0) && (!nameIndex.containsKey("new color"))) {
				newColor.addAttribute("name", "new color");
				break;
			} else if ((x > 0)
					&& !nameIndex.containsKey("new color "
							+ Integer.toString(x))) {
				newColor.addAttribute("name", "new color "
						+ Integer.toString(x));
				break;
			}
		}

		// Find a new color.
		String newRgbCode = ColorHelper.generateRandomColor();
		newColor.addAttribute("real-color", newRgbCode);

		return newColor;
	}

	protected void initTable(Composite parent) {
		colorTable = new Table(parent, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		colorTable.setLayoutData(gd);
		colorTable.setLinesVisible(true);
		colorTable.setHeaderVisible(true);
		
		colorTableViewer = new TableViewer(colorTable);
		colorTableViewer.setContentProvider(new ArrayContentProvider());

		initTableColumns();

		// Add a listener updating the delete-buttons
		// enabled state when another item is selected.
		colorTableViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent e) {
						delColorButton.setEnabled(isOwnColorSelected());
					}
				});

		// Add a mouse listener for deselecting all selected items
		// when the empty table-area without entries is clicked
		colorTable.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				Point mouseClickPoint = new Point(e.x, e.y);
				if (colorTable.getItem(mouseClickPoint) == null) {
					colorTable.deselectAll();
					updatePropertyFields();
				}
			}
		});
	}

	protected void initTableColumns() {
		TableViewerColumn col = new TableViewerColumn(colorTableViewer, SWT.LEFT);
		col.getColumn().setText("Name");
		col.getColumn().setWidth(250);
		col.setLabelProvider(new XmlAttributeLabelProvider("name", "new color") {
			@Override
			public void update(ViewerCell cell) {
				super.update(cell);
				Element curColorElement = (Element) cell.getElement();
				if (curColorElement.getParent().getParent() != SubnetColorEditorPage.this.model) {
					cell.setForeground(new Color(null, 180, 180, 180));
				} else {
					cell.setForeground(new Color(null, 0, 0, 0));
				}
			}
		});
		XmlAttributeEditingSupport editor = new XmlAttributeEditingSupport(col.getViewer(), "name") {
			@Override
			protected boolean canEdit(Object element) {
				return isOwnColorSelected();
			}
		};
		editor.setValidator(new ICellEditorValidator() {			
			@Override
			public String isValid(Object value) {
				if(NetHelper.existsColorWithName(model, value.toString())) {
					return "Another color with this name already exists.";
				}
				return null;
			}
		});
		col.setEditingSupport(editor);
		
		col = new TableViewerColumn(colorTableViewer, SWT.LEFT);
		col.getColumn().setText("Real Color");
		col.getColumn().setWidth(250);
		col.setLabelProvider(new XmlColorAttributeLabelProvider("real-color"));
		col.setEditingSupport(new XmlColorAttributeEditingSupport(col.getViewer(), "real-color") {
			@Override
			protected boolean canEdit(Object element) {
				return isOwnColorSelected();
			}
		});
		
		col = new TableViewerColumn(colorTableViewer, SWT.LEFT);
		col.getColumn().setText("Description");
		col.getColumn().setWidth(250);
		col.setLabelProvider(new XmlAttributeLabelProvider("description", "") {
			@Override
			public void update(ViewerCell cell) {
				super.update(cell);
				Element curColorElement = (Element) cell.getElement();
				if (curColorElement.getParent().getParent() != SubnetColorEditorPage.this.model) {
					cell.setForeground(new Color(null, 180, 180, 180));
				} else {
					cell.setForeground(new Color(null, 0, 0, 0));
				}
			}
		});
		col.setEditingSupport(new XmlAttributeEditingSupport(col.getViewer(), "description") {
			@Override
			protected boolean canEdit(Object element) {
				return isOwnColorSelected();
			}
		});
	}

	public void propertyChange(PropertyChangeEvent arg0) {
		updatePropertyFields();
	}

	protected boolean isOwnColorSelected() {
		if (colorTable.getSelectionIndex() != -1) {
			Element curColorElement = (Element) colorTable.getItem(
					colorTable.getSelectionIndex()).getData();
			IEditorInput input = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.getActiveEditor().getEditorInput();
			if (input instanceof SubnetEditorInput) {
				SubnetEditorInput editorInput = (SubnetEditorInput) input;
				if (curColorElement.getParent().getParent() == editorInput
						.getSubnet()) {
					return true;
				}
			}
		}
		return false;
	}
}
