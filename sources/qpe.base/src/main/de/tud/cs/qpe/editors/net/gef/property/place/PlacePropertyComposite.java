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

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
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
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import de.tud.cs.qpe.editors.net.gef.property.PlaceTransitionPropertyComposite;
import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.model.NetHelper;
import de.tud.cs.qpe.model.PlaceHelper;
import de.tud.cs.qpe.model.SubnetHelper;
import de.tud.cs.qpe.utils.ColorCellEditor;
import de.tud.cs.qpe.utils.ColorHelper;
import de.tud.cs.qpe.utils.ITableLabelColorProvider;
import de.tud.cs.qpe.utils.IntegerCellEditor;

public abstract class PlacePropertyComposite extends PlaceTransitionPropertyComposite {
	
	protected class ColorTableCellModifier implements ICellModifier {
		public boolean canModify(Object element, String property) {
			// If this is an input/actualpopulation/output place of a subnet, colors
			// cannot be modified.
			if (SubnetHelper.isRestrictedPlace(getModel())) {
				return false;
			}			
			
			if ("Color".equals(property)) {
				return !unusedColors.isEmpty();
			}
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

			// Get the color-ref element representing
			// the color-ref beeing edited.
			Element colorRef = getColorReference(element);
			Element color = NetHelper.getColorByReference(getModel(), colorRef);
			
			return getValue(index, colorRef, color);
		}
		
		protected Object getValue(int colIndex, Element colorRef, Element color) {
			switch (colIndex) {
			case 0:
				// Update the color-cell-editor.
				List<Element> items = new ArrayList<Element>();
				items.add(color);
				items.addAll(unusedColors);
				if (colorCellEditor != null) {
					colorCellEditor.setItems(items);
					colorCellEditor.setValue(color);
				}
				return color;
			case 1:
				return (Integer) Integer.parseInt(colorRef.attributeValue("initial-population", "0"));
			case 2:
				return (Integer) Integer.parseInt(colorRef.attributeValue("maximum-capacity", "0"));
			}

			return null;
		}

		public void modify(Object element, String property, Object value) {
			// Get the index first. The index specifies
			// which attribute is edited.
			int index = -1;
			for (int i = 0; i < columnNames.length; i++) {
				if (columnNames[i].equals(property)) {
					index = i;
					break;
				}
			}

			// Get the color-ref element representing
			// the color-ref beeing edited.
			Element colorRef = getColorReference(element);

			// We have to save this, in case the color-id has changed, so 
			// the update-methods know what was changed.
			String oldColorId = colorRef.attributeValue("color-id");
			
			modify(index, colorRef, value);

			colorTableViewer.update(colorRef, null);
			
			colorRefModified(oldColorId, colorRef);
		}
		
		protected void modify(int colIndex, Element colorRef, Object value) {
			int iValue;
			switch (colIndex) {
			case 0:
				Element color = (Element) value;
				DocumentManager.setAttribute(colorRef, "color-id", color.attributeValue("id"));

				// If a color-setting inside a color-ref has
				// changed, then the colorCellEditor has to
				// be updated.
				updateUsedColors();

				// Since Eclipe does lasy instantiation we can't be sure
				// that the cellEditor allready exists.
				updateUnusedColors();

				if (colorCellEditor != null) {
					colorCellEditor.setItems(unusedColors);
				}

				break;
			case 1:
				iValue = ((Integer) value).intValue();
				if(iValue >= 0) {
					DocumentManager.setAttribute(colorRef, "initial-population", ((Integer) value).toString());
				}
				break;
			case 2:
				iValue = ((Integer) value).intValue();
				if(iValue >= 0) {
					DocumentManager.setAttribute(colorRef, "maximum-capacity", ((Integer) value).toString());
				}
				break;
			}
		}
	}
	
	protected class ColorTableLabelProvider implements ITableLabelColorProvider {
		public void dispose() {
		}

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			// Get the color-ref element representing
			// the color-ref beeing edited.
			Element colorRef = getColorReference(element);
			Element color = NetHelper.getColorByReference(getModel(), colorRef);

			return getColumnText(colorRef, color, columnIndex);
		}
		
		protected String getColumnText(Element colorRef, Element color, int columnIndex) {
			switch (columnIndex) {
			case 0:
				if (color != null) {
					return color.attributeValue("name", "new color");
				}
			case 1:
				return colorRef.attributeValue("initial-population", "0");
			case 2:
				return colorRef.attributeValue("maximum-capacity", "0");
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
			if (SubnetHelper.isRestrictedPlace(getModel())) {
				return new Color(null, 180, 180, 180);
			}
			return null;
		}

		public org.eclipse.swt.graphics.Color getBackground(Object element, int columnIndex) {
			if (columnIndex == 0) {
				Element colorRef = getColorReference(element);
				Element color = NetHelper.getColorByReference(getModel(), colorRef);
				
				if (color != null) {
					return new Color(null, ColorHelper.getRGBFromString(color.attributeValue("real-color", "#ffffff")));
				}
			}
			return null;
		}
	}
	
	protected Combo departureDiscipline;

	protected Table colorTable;

	protected TableViewer colorTableViewer;

	protected String[] columnNames;

	protected Button addColorButton;

	protected Button delColorButton;

	// List of all colors used in the current place.
	protected List<Element> usedColors = new ArrayList<Element>();

	protected List<Element> unusedColors = new ArrayList<Element>();

	protected ColorCellEditor colorCellEditor;

	public PlacePropertyComposite(Element model, Composite parent) {
		super(model, parent);
	}

	public void setModel(Element model) {
		super.setModel(model);

		if (getModel() != null) {
			// Iterate through all colors of this
			// net and all it's parent nets.
			List<Element> visibleColors = PlaceHelper.listVisibleColors(getModel());
			for (Element color : visibleColors) {
				// Check if the current color is referenced
				// by the current element. If it is, put it
				// into the used-list, otherwise into the
				// unused-list, which is used for populating
				// the drop-down-lists.
				String colorId = color.attributeValue("id");
				if (!PlaceHelper.existsColorReferenceForColorId(getModel(), colorId)) {
					unusedColors.add(color);
				} else {
					usedColors.add(color);
				}
			}

			updateUnusedColors();
		}
	}

	protected void updatePropertyFields() {
		super.updatePropertyFields();

		if (getModel() != null) {
			departureDiscipline.setText((String) getModel().attributeValue("departure-discipline", "NORMAL"));
			departureDiscipline.setEnabled(!PlaceHelper.isLocked(getModel()));
			// Set the input of the color table.
			colorTableViewer.setInput(PlaceHelper.listColorReferences(getModel()));

			if (SubnetHelper.isRestrictedPlace(getModel())) {
				delColorButton.setEnabled(false);
				addColorButton.setEnabled(false);
			} else {
				// Only if an entry is selected, the
				// delete button will be enabled.
				delColorButton.setEnabled(colorTable.getSelectionIndex() != -1);
	
				// Only if the color-list is not empty, we are
				// allowerd to add a color.
				addColorButton.setEnabled((unusedColors != null) && !unusedColors.isEmpty());
			}
		}
	}

	public void activate() {
		super.activate();
		if (colorTableViewer.getInput() != null) {
			// Add the listeners to the colors referneced by the new color-refs.
			Iterator inputIterator = ((List) colorTableViewer.getInput()).iterator();
			while (inputIterator.hasNext()) {
				Element colorRef = (Element) inputIterator.next();
				Element color = NetHelper.getColorByReference(getModel(), colorRef);
				DocumentManager.addPropertyChangeListener(color, this);
			}
		}
	}

	public void deactivate() {
		super.deactivate();

		if (colorTableViewer.getInput() != null) {
			// Remove the listeners from the old elements.
			Iterator inputIterator = ((List) colorTableViewer.getInput()).iterator();
			while (inputIterator.hasNext()) {
				Element colorRef = (Element) inputIterator.next();
				// TIP: If the current element is deactivated because it is
				// deleted,
				// then we have to get the original document it one beolnged
				// to or the XPath will return no result.
				Element color = NetHelper.getColorByReference(DocumentManager.getDocumentRoot(colorRef), colorRef);
				DocumentManager.removePropertyChangeListener(color, this);
			}
		}
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
		Label colorLabel = new Label(this, SWT.NULL);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		colorLabel.setLayoutData(gd);
		colorLabel.setText("Colors");

		// Initialize the table itself.
		initTable();

		// Add buttons for ading and deleting colors
		Composite colorButtonComposite = new Composite(this, SWT.NULL);
		colorButtonComposite.setLayout(new GridLayout(2, false));
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		colorButtonComposite.setLayoutData(gd);
		addColorButton = new Button(colorButtonComposite, SWT.PUSH);
		addColorButton.setText("Add Color-Ref");
		addColorButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		addColorButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (!unusedColors.isEmpty()) {
					Element colorRef = createColorReference();
				
					PlaceHelper.addColorReference(getModel(), colorRef);

					// Register as listener for chages to the referenced color.
					// TODO: Something is going wrong here when adding colors to a place.
					Element color = NetHelper.getColorByReference(getModel(), colorRef);
					DocumentManager.addPropertyChangeListener(color, PlacePropertyComposite.this);

					// Update the visuals.
					updatePropertyFields();
					
					colorRefAdded(colorRef);
				}
			}
		});

		delColorButton = new Button(colorButtonComposite, SWT.PUSH);
		delColorButton.setText("Delete Color-Ref");
		delColorButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		delColorButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Get the index of the selected color-ref.
				int selectionIndex = colorTable.getSelectionIndex();

				// Get the color-ref with the selected index.
				Element colorRef = PlaceHelper.getColorReferenceByIndex(getModel(), selectionIndex);

				// Unregister as listener for chages to the referenced color.
				Element color = NetHelper.getColorByReference(getModel(), colorRef);
				DocumentManager.removePropertyChangeListener(color, PlacePropertyComposite.this);

				colorRefRemoved(colorRef);
				
				PlaceHelper.removeColorReference(getModel(), colorRef);

				// Update the visuals.
				updatePropertyFields();
			}
		});
		colorButtonComposite.layout();
	}
	
	protected Element createColorReference() {
		// Create a new color-ref.
		Element colorRef = new DefaultElement("color-ref");

		// Set the new Color-Ref to the first color in the list.
		colorRef.addAttribute("color-id", (String) unusedColors.get(0).attributeValue("id"));

		// Set the default attributes.
		colorRef.addAttribute("initial-population", "0");
		colorRef.addAttribute("maximum-capacity", "0");
		
		return colorRef;		
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
		ArrayList<CellEditor> editors = initCellEditors();
		colorTableViewer.setCellEditors(editors.toArray(new CellEditor[editors.size()]));

		// Add a listener updating the delete-buttons
		// enabled state when another item is selected.
		colorTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent e) {
				if (SubnetHelper.isRestrictedPlace(getModel())) {
					// If this is a restricted place in a subnet, only color references to locally defined
					// colors can be deleted.
					TableItem[] selection = colorTable.getSelection();
					if (selection.length == 1) {
						Element colorRef = (Element)selection[0].getData();
						Element subnet = SubnetHelper.getSubnetOfPlace(getModel());
						delColorButton.setEnabled(SubnetHelper.isColorLocallyDefined(subnet, colorRef.attributeValue("color-id")));
					} else {
						delColorButton.setEnabled(false);
					}
				} else {
					delColorButton.setEnabled(colorTable.getSelectionIndex() != -1);
				}
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
		col.setWidth(145);
		col = new TableColumn(colorTable, SWT.LEFT);
		col.setText("Initial");
		col.setWidth(50);
		col = new TableColumn(colorTable, SWT.LEFT);
		col.setText("Max");
		col.setWidth(50);
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
		colorTableViewer.setLabelProvider(new ColorTableLabelProvider());
	}

	protected void initCellModifier() {
		colorTableViewer.setCellModifier(new ColorTableCellModifier());
	}

	protected ArrayList<CellEditor> initCellEditors() {
		ArrayList<CellEditor> cellEditors = new ArrayList<CellEditor>();
		colorCellEditor = new ColorCellEditor(colorTableViewer.getTable());
		cellEditors.add(colorCellEditor);
		cellEditors.add(new IntegerCellEditor(colorTableViewer.getTable()));
		cellEditors.add(new IntegerCellEditor(colorTableViewer.getTable()));
		
		return cellEditors;
	}

	protected void updateUsedColors() {
		// When using Lists the event oldValue contains
		// the element actually being added, removed or
		// changed and the newValue contains the entire
		// list after the operation was performed.
		if (usedColors != null) {
			usedColors.clear();
			List<Element> colorRefs = PlaceHelper.listColorReferences(getModel());
			for (Element colorRef : colorRefs) {
				usedColors.add(NetHelper.getColorByReference(getModel(), colorRef));
			}
		}
	}

	protected void updateUnusedColors() {
		unusedColors.clear();

		// Select all color-nodes that are defined in this and
		// higher-level nets.
		List<Element> colors = PlaceHelper.listVisibleColors(getModel());

		// For each of these colors, check if they are
		// allready referenced by this net-element. If
		// not, then add it to the unused color list.
		for (Element color : colors) {
			if (!PlaceHelper.existsColorReferenceForColorId(getModel(), color.attributeValue("id"))) {
				unusedColors.add(color);
			}
		}

		// Enable/disable the "Add color-ref"-Button
		updatePropertyFields();
	}

	public void propertyChange(PropertyChangeEvent event) {
		String propertyName = event.getPropertyName();

		// If something happened to any child element, then update the used
		// and unused colors ... just to be on the safe side.
		if (DocumentManager.PROP_CHILD_MODIFIED.equals(propertyName) || DocumentManager.PROP_CHILD_ADDED.equals(propertyName)
				|| DocumentManager.PROP_CHILD_REMOVED.equals(propertyName)) {
			updateUsedColors();
			updateUnusedColors();
		}
		if (DocumentManager.PROP_ATTRIBUTE_MODIFIED.equals(propertyName)) {
			updatePropertyFields();
		}
	}
	
	protected void colorRefAdded(Element colorRef) {	
	}
	
	protected void colorRefRemoved(Element colorRef) {
	}
	
	protected void colorRefModified(String oldColorId, Element colorRef) {
	}
	
	protected void departureDisciplineModified(String oldDiscipline, String newDiscipline) {
		
	}
	
	protected Element getColorReference(Object element) {
		Element colorRef = null;
		// If a selection is made from the drop-down
		// list, then this is of type Item ... otherwise
		// the direct value will be used.
		if (element instanceof Item) {
			TableItem item = (TableItem) element;
			colorRef = (Element) item.getData();
		} else {
			colorRef = (Element) element;
		}
		return colorRef;
	}
}
