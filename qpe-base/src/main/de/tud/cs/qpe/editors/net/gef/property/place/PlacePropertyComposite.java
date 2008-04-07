package de.tud.cs.qpe.editors.net.gef.property.place;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
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
import de.tud.cs.qpe.utils.ColorCellEditor;
import de.tud.cs.qpe.utils.ColorHelper;
import de.tud.cs.qpe.utils.ITableLabelColorProvider;
import de.tud.cs.qpe.utils.IntegerCellEditor;

public abstract class PlacePropertyComposite extends PlaceTransitionPropertyComposite {
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
			XPath xpathSelector = DocumentHelper.createXPath("ancestor::*/colors/color");
			Iterator colorIterator = xpathSelector.selectNodes(getModel()).iterator();
			while (colorIterator.hasNext()) {
				Element color = (Element) colorIterator.next();

				// Check if the current color is referenced
				// by the current element. If it is, put it
				// into the used-list, otherwise into the
				// unused-list, which is used for populating
				// the drop-down-lists.
				String colorId = color.attributeValue("id");
				xpathSelector = DocumentHelper.createXPath("color-ref[@color-id = '" + colorId + "']");
				if (xpathSelector.selectSingleNode(model) == null) {
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
			// Set the input of the color table.
			XPath xpathSelector = DocumentHelper.createXPath("color-refs/color-ref");
			colorTableViewer.setInput(xpathSelector.selectNodes(getModel()));

			// Only if an entry is selected, the
			// delete button will be enabled.
			delColorButton.setEnabled(colorTable.getSelectionIndex() != -1);

			// Only if the color-list is not empty, we are
			// allowerd to add a color.
			addColorButton.setEnabled((unusedColors != null) && !unusedColors.isEmpty());
		}
	}

	public void activate() {
		super.activate();
		if (colorTableViewer.getInput() != null) {
			// Add the listeners to the colors referneced by the new color-refs.
			Iterator inputIterator = ((List) colorTableViewer.getInput()).iterator();
			while (inputIterator.hasNext()) {
				Element entry = (Element) inputIterator.next();
				XPath xpathSelector = DocumentHelper.createXPath("//color[@id = '" + entry.attributeValue("color-id") + "']");
				Element color = (Element) xpathSelector.selectSingleNode(entry);
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
				Element entry = (Element) inputIterator.next();
				XPath xpathSelector = DocumentHelper.createXPath("//color[@id = '" + entry.attributeValue("color-id") + "']");
				// TIP: If the current element is deactivated because it is
				// deleted,
				// then we have to get the original document it one beolnged
				// to or the XPath will return no result.
				Element color = (Element) xpathSelector.selectSingleNode(DocumentManager.getDocumentRoot(entry));
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
		addColorButton.setText("Add color-ref");
		addColorButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		addColorButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (!unusedColors.isEmpty()) {
					Element colorRefContainer = getModel().element("color-refs");
					if (colorRefContainer == null) {
						colorRefContainer = getModel().addElement("color-refs");
					}
					// Create a new color-ref.
					Element colorRef = new DefaultElement("color-ref");

					// Set the new Color-Ref to the first color in the list.
					colorRef.addAttribute("color-id", (String) unusedColors.get(0).attributeValue("id"));

					// Set the default attributes.
					colorRef.addAttribute("initial-population", "0");
					colorRef.addAttribute("maximum-capacity", "0");

					// Add the color-ref to the current place.
					DocumentManager.addChild(colorRefContainer, colorRef);

					// Register as listener for chages to the referenced color.
					XPath xpathSelector = DocumentHelper.createXPath("//color[@id = '" + colorRef.attributeValue("color-id") + "']");

					// TODO: Something is going wrong here when adding colors to a place.
					Element color = (Element) xpathSelector.selectSingleNode(colorRef);
					DocumentManager.addPropertyChangeListener(color, PlacePropertyComposite.this);

					// Update the visuals.
					updatePropertyFields();
					
					colorRefAdded(colorRef);
				}
			}
		});

		delColorButton = new Button(colorButtonComposite, SWT.PUSH);
		delColorButton.setText("Del color-ref");
		delColorButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		delColorButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Get the index of the selected color-ref.
				int selectionIndex = colorTable.getSelectionIndex();

				// Get the color-ref with the selected index.
				XPath xpathSelector = DocumentHelper.createXPath("color-refs/color-ref[" + Integer.toString(selectionIndex + 1) + "]");
				Element colorRef = (Element) xpathSelector.selectSingleNode(getModel());

				// Unregister as listener for chages to the referenced color.
				xpathSelector = DocumentHelper.createXPath("//color[@id = '" + colorRef.attributeValue("color-id") + "']");
				Element color = (Element) xpathSelector.selectSingleNode(colorRef);
				DocumentManager.removePropertyChangeListener(color, PlacePropertyComposite.this);

				// TODO: remove all connections in the Incidence-Function using
				// the current color-ref.
				
				colorRefRemoved(colorRef);
				
				// Remove the selected colorRef from the current node.
				DocumentManager.removeElement(colorRef);

				// Update the visuals.
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
		colorTableViewer.setLabelProvider(new ITableLabelColorProvider() {
			public void dispose() {
			}

			public Image getColumnImage(Object element, int columnIndex) {
				return null;
			}

			public String getColumnText(Object element, int columnIndex) {
				Element colorRef = (Element) element;
				String colorId = colorRef.attributeValue("color-id");
				XPath xpathSelector = DocumentHelper.createXPath("//color[@id = '" + colorId + "']");
				Element color = (Element) xpathSelector.selectSingleNode(getModel());

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
				return null;
			}

			public org.eclipse.swt.graphics.Color getBackground(Object element, int columnIndex) {
				if (columnIndex == 0) {
					Element colorRef = (Element) element;
					XPath xpathSelector = DocumentHelper.createXPath("//color[@id = '" + colorRef.attributeValue("color-id") + "']");
					Element color = (Element) xpathSelector.selectSingleNode(colorRef);
					if (color != null) {
						return new Color(null, ColorHelper.getRGBFromString(color.attributeValue("real-color", "#ffffff")));
					}
				}
				return null;
			}
		});
	}

	protected void initCellModifier() {
		colorTableViewer.setCellModifier(new ICellModifier() {
			public boolean canModify(Object element, String property) {
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

				Element colorRef = (Element) element;
				String colorId = colorRef.attributeValue("color-id");
				XPath xpathSelector = DocumentHelper.createXPath("//color[@id = '" + colorId + "']");
				Element color = (Element) xpathSelector.selectSingleNode(getModel());

				switch (index) {
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

				// We have to save this, in case the color-id has changed, so 
				// the update-methods know what was changed.
				String oldColorId = colorRef.attributeValue("color-id");
				
				int iValue;
				switch (index) {
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

				colorTableViewer.update(colorRef, null);
				
				colorRefModified(oldColorId, colorRef);
			}
		});
	}

	protected void initCellEditors() {
		CellEditor[] cellEditors = new CellEditor[3];
		colorCellEditor = new ColorCellEditor(colorTableViewer.getTable());
		cellEditors[0] = colorCellEditor;
		cellEditors[1] = new IntegerCellEditor(colorTableViewer.getTable());
		cellEditors[2] = new IntegerCellEditor(colorTableViewer.getTable());

		colorTableViewer.setCellEditors(cellEditors);
	}

	protected void updateUsedColors() {
		// When using Lists the event oldValue contains
		// the element actually being added, removed or
		// changed and the newValue contains the entire
		// list after the operation was performed.
		if (usedColors != null) {
			usedColors.clear();
			XPath xpathSelector = DocumentHelper.createXPath("color-refs/color-ref");
			Iterator colorRefIterator = xpathSelector.selectNodes(getModel()).iterator();
			while (colorRefIterator.hasNext()) {
				Element colorRef = (Element) colorRefIterator.next();
				xpathSelector = DocumentHelper.createXPath("../../colors/color[@id = '" + colorRef.attributeValue("color-id") + "']");
				usedColors.add((Element) xpathSelector.selectSingleNode(getModel()));
			}
		}
	}

	protected void updateUnusedColors() {
		unusedColors.clear();

		// Select all color-nodes that are defined in this and
		// higher-level nets.
		XPath xpathSelector = DocumentHelper.createXPath("ancestor::*/colors/color");
		Iterator colorIterator = xpathSelector.selectNodes(getModel()).iterator();

		// For each of these colors, check if they are
		// allready referenced by this net-element. If
		// not, then add it to the unused color list.
		while (colorIterator.hasNext()) {
			Element color = (Element) colorIterator.next();
			xpathSelector = DocumentHelper.createXPath("color-refs/color-ref[@color-id = '" + color.attributeValue("id") + "']");
			if (xpathSelector.selectSingleNode(getModel()) == null) {
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
		// Get all the connections in any incidence function that use this color-ref and removde them.
		XPath xpathSelector = DocumentHelper.createXPath("//*[@source-id = " + colorRef.attributeValue("id") + " or @target-id = " + colorRef.attributeValue("id") + "]");
		Iterator incidenceFunctionConnectionIterator = xpathSelector.selectNodes(colorRef).iterator();
		while(incidenceFunctionConnectionIterator.hasNext()) {
			Element connection = (Element) incidenceFunctionConnectionIterator.next();
			DocumentManager.removeElement(connection);
		}
	}
	
	protected void colorRefModified(String oldColorId, Element colorRef) {		
	}
}
