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
package de.tud.cs.qpe.editors.net;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColorCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IEditorInput;

import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.model.NetHelper;
import de.tud.cs.qpe.utils.ColorHelper;
import de.tud.cs.qpe.utils.ITableLabelColorProvider;

public class ColorEditorPage extends Composite implements PropertyChangeListener {
	protected Table colorTable;

	protected TableViewer colorTableViewer;

	protected String[] columnNames;

	protected Button addColorButton;

	protected Button delColorButton;

	protected Element model;

	public ColorEditorPage(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout());
		columnNames = new String[] { "Name", "Real Color", "Description" };
		initColorTable();
	}

	/**
	 * Uses a ShapesEditorInput to serve as a dummy editor input It is up to the
	 * editor input to supply the initial shapes diagram
	 * 
	 * @see org.eclipse.ui.part.EditorPart#setInput(org.eclipse.ui.IEditorInput)
	 */
	protected void setInput(IEditorInput input) {
		if (model != null) {
			DocumentManager.removePropertyChangeListener(model, this);
		}
		
		NetEditorInput netInput = (NetEditorInput) input;
		model = netInput.getNetDiagram();

		// Add the color editor as listener to modifications of the
		// current document.
		DocumentManager.addPropertyChangeListener(model, this);

		updatePropertyFields();
	}

	public void updatePropertyFields() {
		List<Element> colors = NetHelper.listColors(model);
		colorTableViewer.setInput(colors);
		delColorButton.setEnabled(colorTable.getSelectionIndex() != -1);
	}

	private Element createColor() {
		// Add the color names to a hashset.
		// This way fast checks for name duplicates can be performed.
		HashSet<String> nameIndex = new HashSet<String>();

		List<Element> colors = NetHelper.listColors(model);
		for (Element color : colors) {
			nameIndex.add(color.attributeValue("name"));
		}

		// Find a new name.
		Element newColor = new DefaultElement("color");
		for (int x = 0;; x++) {
			if ((x == 0) && (!nameIndex.contains("new color"))) {
				newColor.addAttribute("name", "new color");
				break;
			} else if ((x > 0) && !nameIndex.contains("new color " + Integer.toString(x))) {
				newColor.addAttribute("name", "new color " + Integer.toString(x));
				break;
			}
		}

		// Find a new color.
		String newRgbCode = ColorHelper.generateRandomColor();
		newColor.addAttribute("real-color", newRgbCode);

		return newColor;
	}

	protected void initColorTable() {
		Label colorName = new Label(this, SWT.NULL);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		colorName.setLayoutData(gd);
		colorName.setText("Colors");
		initTable();
		// Add buttons for ading and deleting colors
		Composite colorButtonComposite = new Composite(this, SWT.NULL);
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
				NetHelper.addColor(model, newColor);
				updatePropertyFields();
			}
		});

		delColorButton = new Button(colorButtonComposite, SWT.PUSH);
		delColorButton.setText("Delete Color");
		delColorButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		delColorButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int selection = colorTable.getSelectionIndex();
				Element color = (Element) colorTable.getItem(selection).getData();
				// Check if the color is used. If yes, show a dialog asking the
				// user if he is sure. If yes then all usages of this collor
				// have to be removed.
				if (NetHelper.isColorReferencedInNet(model, color)) {
					boolean result = MessageDialog.openConfirm(getShell(), "Color References", "The Color you are trying to remove is referenced in the current net. Are you sure you want it to be removed? Removing this color will cause the removal of all its references. This process is irreversible.");
					if (result) {
						// Remove the color and all of its references.
						NetHelper.removeColor(model, color);
					}
				} else {
					NetHelper.removeColor(model, color);
				}

				updatePropertyFields();
			}
		});
		colorButtonComposite.layout();
	}

	protected void initTable() {
		colorTable = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		colorTable.setLayoutData(gd);
		colorTable.setLinesVisible(true);
		colorTable.setHeaderVisible(true);

		initTableColumns();
		initTableViewer();
		initCellEditors();

		// Add a listener updating the delete-buttons
		// enabled state when another item is selected.
		colorTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent e) {
				delColorButton.setEnabled(colorTable.getSelectionIndex() != -1);
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
		TableColumn col = new TableColumn(colorTable, SWT.LEFT);
		col.setText("Name");
		col.setWidth(150);
		col = new TableColumn(colorTable, SWT.LEFT);
		col.setText("Real Color");
		col.setWidth(100);
		col = new TableColumn(colorTable, SWT.LEFT);
		col.setText("Description");
		col.setWidth(500);
	}

	protected void initTableViewer() {
		colorTableViewer = new TableViewer(colorTable);
		colorTableViewer.setColumnProperties(columnNames);

		initContentProvider();
		initLabelProvider();
		initCellModifier();
	}

	protected void initContentProvider() {
		colorTableViewer.setContentProvider(new IStructuredContentProvider() {
			public Object[] getElements(Object inputElements) {
				List l = (List) inputElements;
				return l.toArray();
			}

			public void dispose() {
			}

			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}
		});
	}

	protected void initLabelProvider() {
		colorTableViewer.setLabelProvider(new ITableLabelColorProvider() {
			public void dispose() {
			}

			public Image getColumnImage(Object element, int columnIndex) {
				return null;
			}

			public String getColumnText(Object element, int columnIndex) {
				Element color = (Element) element;
				switch (columnIndex) {
				case 0:
					return color.attributeValue("name", "new color");
				case 1:
					return "";
				case 2:
					return color.attributeValue("description");
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
					String realColorString = ((Element) element).attributeValue("real-color");
					RGB realColor = ColorHelper.getRGBFromString(realColorString);
					return new Color(Display.getDefault(), realColor);
				}
				return null;
			}
		});
	}

	protected void initCellModifier() {
		colorTableViewer.setCellModifier(new ICellModifier() {
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
				Element color = (Element) element;

				switch (index) {
				case 0:
					return color.attributeValue("name", "unnnamed color");
				case 1:
					String realColor = color.attributeValue("real-color");
					return ColorHelper.getRGBFromString(realColor);
				case 2:
					return color.attributeValue("description", "");
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

				Element color = null;
				if (element instanceof Item) {
					TableItem item = (TableItem) element;
					color = (Element) item.getData();
				} else {
					color = (Element) element;
				}

				switch (index) {
				case 0:
					if (!color.attributeValue("name").equals(value)) {
						if(!NetHelper.existsColorWithName(model, value.toString())) {
							DocumentManager.setAttribute(color, "name", (String) value);
						} else {
							MessageDialog.openError(getShell(), "Duplicate color names", "Another color with this name already exists.");
						}
					}
					break;
				case 1:
					DocumentManager.setAttribute(color, "real-color", ColorHelper.getStringfromRGB((RGB) value));
					break;
				case 2:
					DocumentManager.setAttribute(color, "description", (String) value);
					break;
				}

				colorTableViewer.update(color, null);
			}
		});
	}

	protected void initCellEditors() {
		CellEditor[] cellEditors = new CellEditor[3];
		cellEditors[0] = new TextCellEditor(colorTableViewer.getTable());
		cellEditors[1] = new ColorCellEditor(colorTableViewer.getTable());
		cellEditors[2] = cellEditors[0];

		colorTableViewer.setCellEditors(cellEditors);
	}

	public void propertyChange(PropertyChangeEvent arg0) {
		updatePropertyFields();
	}
}
