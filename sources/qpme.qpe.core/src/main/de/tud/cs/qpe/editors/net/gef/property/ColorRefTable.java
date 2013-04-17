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
 * Original Author(s):  Simon Spinner
 * Contributor(s):      
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2011/03/22  Simon Spinner     Created.
 */
package de.tud.cs.qpe.editors.net.gef.property;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
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

import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.model.NetHelper;
import de.tud.cs.qpe.utils.XmlColorAttributeLabelProvider;

public abstract class ColorRefTable implements PropertyChangeListener {

	private Table colorTable;

	private TableViewer colorTableViewer;

	private Button addColorButton;

	private Button delColorButton;
	
	private boolean enabled = true;
	
	private List<Element> unusedColors = new ArrayList<Element>();
	
	private Set<Element> observedElements = new HashSet<Element>();
	 
	private ListenerList listeners = new ListenerList();
	
	private void attachToPropertyChanges(Element target) {
		if (!observedElements.contains(target)) {
			DocumentManager.addPropertyChangeListener(target, this);
			observedElements.add(target);
		}
	}
	
	private void detachFromPropertyChanges(Element target) {
		if(observedElements.contains(target)) {
			observedElements.remove(target);
			DocumentManager.removePropertyChangeListener(target, this);
		}
	}
	
	private void detachFromAllPropertyChanges() {
		for (Element target : observedElements) {
			DocumentManager.removePropertyChangeListener(target, this);
		}
		observedElements.clear();
	}

	public ColorRefTable(Composite parent) {
		initColorTable(parent);
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		update();
	}

	public TableViewerColumn createColumn(String title, int width) {
		TableViewerColumn column = new TableViewerColumn(colorTableViewer, SWT.LEFT);
		column.getColumn().setText(title);
		column.getColumn().setWidth(width);
		return column;
	}
	
	public void setColumnLabelProvider(TableViewerColumn column, CellLabelProvider labelProvider) {
		column.setLabelProvider(new LockedCellLabelProviderDelegate(labelProvider));
	}
	
	public void setColumnEditingSupport(TableViewerColumn column, EditingSupport editingSupport) {
		column.setEditingSupport(editingSupport);
	}
	
	public void addColorRefTableListener(IColorRefTableListener listener) {
		listeners.add(listener);
	}
	
	public void removeColorRefTableListener(IColorRefTableListener listener) {
		listeners.remove(listener);
	}

	public void activate() {
		for (Element colorRef : getColorReferences()) {				
			Element color = NetHelper.getColorByReference(colorRef);
			attachToPropertyChanges(color);
		}
		updateUnusedColors();
	}

	public void deactivate() {
		detachFromAllPropertyChanges();
	}
	
	protected void fireColorRefAdded(final Element colorRef) {
        Object[] targets = listeners.getListeners();
        for (int i = 0; i < targets.length; ++i) {
            final IColorRefTableListener l = (IColorRefTableListener) targets[i];
            SafeRunnable.run(new SafeRunnable() {
                public void run() {
                    l.colorRefAdded(colorRef);
                }
            });
        }
	}
	
	protected void fireColorRefModified(final String oldColorId, final Element colorRef) {
        Object[] targets = listeners.getListeners();
        for (int i = 0; i < targets.length; ++i) {
            final IColorRefTableListener l = (IColorRefTableListener) targets[i];
            SafeRunnable.run(new SafeRunnable() {
                public void run() {
                    l.colorRefModified(oldColorId, colorRef);
                }
            });
        }
	}
	
	protected void fireColorRefRemoved(final Element colorRef) {
        Object[] targets = listeners.getListeners();
        for (int i = 0; i < targets.length; ++i) {
            final IColorRefTableListener l = (IColorRefTableListener) targets[i];
            SafeRunnable.run(new SafeRunnable() {
                public void run() {
                    l.colorRefRemoved(colorRef);
                }
            });
        }
	}
	
	protected Element createColorReference() {
		// Create a new color-ref.
		Element colorRef = new DefaultElement("color-ref");

		// Set the new Color-Ref to the first color in the list.
		colorRef.addAttribute("color-id", (String) unusedColors.get(0).attributeValue("id"));
		
		return colorRef;		
	}

	private void initColorTable(Composite parent) {
		Label colorLabel = new Label(parent, SWT.NULL);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		colorLabel.setLayoutData(gd);
		colorLabel.setText("Colors");

		// Initialize the table itself.
		initTable(parent);

		// Add buttons for ading and deleting colors
		Composite colorButtonComposite = new Composite(parent, SWT.NULL);
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
					
					fireColorRefAdded(colorRef);

					attachToPropertyChanges(colorRef);
					Element color = NetHelper.getColorByReference(colorRef);					
					attachToPropertyChanges(color);
					
					updateUnusedColors();
				}
			}
		});

		delColorButton = new Button(colorButtonComposite, SWT.PUSH);
		delColorButton.setText("Delete Color-Ref");
		delColorButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		delColorButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection selection = (IStructuredSelection)colorTableViewer.getSelection();
				if (selection.size() == 1) {

					Element colorRef = (Element)selection.getFirstElement();
					detachFromPropertyChanges(colorRef);
					
					// Unregister as listener for changes to the referenced color.
					Element color = NetHelper.getColorByReference(colorRef);
					detachFromPropertyChanges(color);
	
					fireColorRefRemoved(colorRef);
					
					updateUnusedColors();
				} else {
					update();
				}
			}
		});
		colorButtonComposite.layout();
	}

	private void initTable(Composite parent) {
		colorTable = new Table(parent, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		colorTable.setLayoutData(gd);
		colorTable.setLinesVisible(true);
		colorTable.setHeaderVisible(true);

		colorTableViewer = new TableViewer(colorTable) {
			protected void triggerEditorActivationEvent(ColumnViewerEditorActivationEvent event) {
				if (enabled) {
					super.triggerEditorActivationEvent(event);
				}
			}
		};
		colorTableViewer.setContentProvider(new ArrayContentProvider());

		initTableColumns();

		// Add a listener updating the delete-buttons
		// enabled state when another item is selected.
		colorTableViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent e) {
						if (enabled) {
							IStructuredSelection selection = (IStructuredSelection)e.getSelection();
							delColorButton.setEnabled(selection.size() == 1);
						} else {
							delColorButton.setEnabled(false);
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
					update();
				}
			}
		});
	}

	private void initTableColumns() {
		TableViewerColumn col = createColumn("Name", 145);
		setColumnLabelProvider(col, new XmlColorAttributeLabelProvider("real-color") {
			@Override
			protected Element getCastedElement(ViewerCell cell) {
				Element colorRef = super.getCastedElement(cell);
				return NetHelper.getColorByReference(colorRef);
			}

			@Override
			protected void update(ViewerCell cell, Element element) {
				super.update(cell, element);
				cell.setText(NetHelper.getFullyQualifiedName(element));
			}
		});
		setColumnEditingSupport(col, new ColorReferenceEditingSupport(col.getViewer()) {
			@Override
			protected void setValue(Object element, Object value) {
				Element colorRef = (Element) element;
				String oldColorId = colorRef.attributeValue("color-id");
				super.setValue(element, value);
				fireColorRefModified(oldColorId, colorRef);
			}
		});
	}
	
	private void update() {
		// Set the input of the color table.
		colorTableViewer.setInput(getColorReferences());
			
		if (enabled) {
			// Only if an entry is selected, the
			// delete button will be enabled.
			delColorButton.setEnabled(((IStructuredSelection)colorTableViewer.getSelection()).size() != 0);
	
			// Only if the color-list is not empty, we are
			// allowed to add a color.
			addColorButton.setEnabled((unusedColors != null) && !unusedColors.isEmpty());
		} else {
			delColorButton.setEnabled(false);
			addColorButton.setEnabled(false);
		}
	}

	private void updateUnusedColors() {
		unusedColors.clear();
		
		List<Element> availableColors = getAvailableColors();
		List<Element> colorRefs = getColorReferences();
		for (Element color : availableColors) {
			// Check if the current color is referenced
			// by the current element. If it is, put it
			// into the used-list, otherwise into the
			// unused-list, which is used for populating
			// the drop-down-lists.
			String colorId = color.attributeValue("id");
			Element match = null;
			for (int i = 0; i < colorRefs.size(); i++) {
				Element cur = colorRefs.get(i);
				if (cur.attributeValue("color-id").equals(colorId)) {
					match = cur;
				}
			}			
			
			if (match == null) {
				unusedColors.add(color);
			}
		}

		// Enable/disable the "Add color-ref"-Button
		update();
	}

	protected abstract List<Element> getColorReferences();
	protected abstract List<Element> getAvailableColors();

	public void propertyChange(PropertyChangeEvent event) {
		String propertyName = event.getPropertyName();
		if (DocumentManager.PROP_ATTRIBUTE_MODIFIED.equals(propertyName)) {
			update();
		}
	}
	
	private class LockedCellLabelProviderDelegate extends CellLabelProvider {
		
		private CellLabelProvider delegate;
		
		public LockedCellLabelProviderDelegate(CellLabelProvider delegate) {
			this.delegate = delegate;
		}

		@Override
		public void update(ViewerCell cell) {
			delegate.update(cell);
			if (enabled) {
				cell.setForeground(new Color(null, 0, 0, 0));
			} else {
				cell.setForeground(new Color(null, 180, 180, 180));
			}
		}		
	}
	
	private class ColorReferenceEditingSupport extends EditingSupport {

		private ComboBoxViewerCellEditor cellEditor;

		public ColorReferenceEditingSupport(ColumnViewer viewer) {
			super(viewer);
			cellEditor = new ComboBoxViewerCellEditor((Composite) viewer.getControl());
			cellEditor.setContentProvider(new ArrayContentProvider());
			cellEditor.setLabelProvider(new LabelProvider() {
				@Override
				public String getText(Object element) {
					Element color = (Element)element;
					return NetHelper.getFullyQualifiedName(color);
				}				
			});
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			return cellEditor;
		}

		@Override
		protected boolean canEdit(Object element) {
			return !unusedColors.isEmpty();
		}

		@Override
		protected Object getValue(Object element) {
			// Update the color-cell-editor.
			Element colorRef = (Element) element;
			Element color = NetHelper.getColorByReference(colorRef);
			List<Element> items = new ArrayList<Element>();
			items.add(color);
			items.addAll(unusedColors);
			if (cellEditor != null) {
				cellEditor.setInput(items);
				cellEditor.setValue(color);
			}
			return color;
		}

		@Override
		protected void setValue(Object element, Object value) {
			Element colorRef = (Element) element;
			Element color = (Element) value;
			String oldColorId = colorRef.attributeValue("color-id");
			String newColorId = color.attributeValue("id");

			if (!oldColorId.equals(newColorId)) {
				DocumentManager.setAttribute(colorRef, "color-id",
						color.attributeValue("id"));
				updateUnusedColors();
				if (cellEditor != null) {
					cellEditor.setInput(unusedColors);
				}
				fireColorRefModified(oldColorId, colorRef);
				getViewer().refresh();
			}
		}

	}
}