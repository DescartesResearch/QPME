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
 *  04/03/2011  Simon Spinner     Created.
 * 
 */
package de.tud.cs.qpe.editors.net;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.model.NetHelper;
import de.tud.cs.qpe.utils.XmlAttributeEditingSupport;
import de.tud.cs.qpe.utils.XmlAttributeLabelProvider;

public class ProbeEditorPage extends EditorPart implements PropertyChangeListener {
	
	private final class PlaceReferenceColumnEditingSupport extends XmlAttributeEditingSupport {
		
		public PlaceReferenceColumnEditingSupport(ColumnViewer column, String refAttribute) {
			super(column, refAttribute); 
		}
		
		@Override
		protected CellEditor createCellEditor(Composite parent) {
			ComboBoxViewerCellEditor editor = new ComboBoxViewerCellEditor((Composite) getViewer().getControl(), SWT.READ_ONLY);
			editor.setLabelProvider(new LabelProvider() {
				@Override
				public String getText(Object element) {
					Element place = ((Element)element);
					return NetHelper.getFullyQualifiedName(place);
				}
			});
			editor.setContenProvider(new ArrayContentProvider());
			return editor;
		}
		
		public void updateInput() {
			((ComboBoxViewerCellEditor)cellEditor).setInput(NetHelper.listAllPlaces(model));
		}

		@Override
		protected Object getValue(Object element) {
			Element probe = (Element)element;
			String placeId = probe.attributeValue(attribute);
			if (placeId != null) {
				return NetHelper.getPlaceById(probe, placeId);
			}
			return null;
		}

		@Override
		protected void setValue(Object element, Object value) {
			if (value != null) {
				Element probe = (Element)element;
				Element place = (Element)value;
				
				if (!probe.attributeValue(attribute).equals(place.attributeValue("id"))) {
					DocumentManager.setAttribute(probe, attribute,  place.attributeValue("id"));
					getViewer().refresh();
				}
			}
		}
		
	}
	
	private class TriggerAttributeEditingSupport extends XmlAttributeEditingSupport {

		public TriggerAttributeEditingSupport(ColumnViewer column, String attribute) {
			super(column, attribute);
		}
		
		@Override
		protected CellEditor createCellEditor(Composite parent) {
			ComboBoxViewerCellEditor cellEditor = new ComboBoxViewerCellEditor((Composite) getViewer().getControl(), SWT.READ_ONLY);
			cellEditor.setLabelProvider(new LabelProvider());
			cellEditor.setContenProvider(new ArrayContentProvider());
			cellEditor.setInput(new String[] {"On Entry", "On Exit"});
			return cellEditor;
		}
		
		@Override
		protected Object getValue(Object element) {
			if (((Element)element).attributeValue(attribute).equals("entry")) {
				return "On Entry";
			} else {
				return "On Exit";
			}
		}
		
		@Override
		protected void setValue(Object element, Object value) {
			if (value != null) {
				Element e = (Element)element;
				if (!e.attributeValue(attribute).equals(value)) {
					if (value.equals("On Entry")) {
						DocumentManager.setAttribute(e, attribute, "entry");
					} else {
						DocumentManager.setAttribute(e, attribute, "exit");
					}
					getViewer().refresh();
				}
			}
		}
		
	}
	
	private class TriggerAttributeLabelProvider extends XmlAttributeLabelProvider {

		public TriggerAttributeLabelProvider(String attribute) {
			super(attribute, "");
		}
		
		@Override
		public void update(ViewerCell cell) {
			Element element = (Element)cell.getElement();
			if (element.attributeValue(getAttribute()).equals("entry")) {
				cell.setText("On Entry");
			} else {
				cell.setText("On Exit");
			}
		}		
	}
	
	private final class PlaceReferenceLabelProvider extends CellLabelProvider {
		
		private String refAttribute;
		
		public PlaceReferenceLabelProvider(String refAttribute) {
			this.refAttribute = refAttribute;
		}

		@Override
		public void update(ViewerCell cell) {
			Element element = (Element)cell.getElement();
			String placeId = element.attributeValue(refAttribute);
			if (placeId != null) {
				Element place = NetHelper.getPlaceById(element, placeId);
				if (place != null) {
					cell.setText(NetHelper.getFullyQualifiedName(place));
				}
			}
		}
		
	}
	
	protected TableViewer probeTableViewer;
	
	protected PlaceReferenceColumnEditingSupport startPlaceColumnEditing;
	
	protected PlaceReferenceColumnEditingSupport endPlaceColumnEditing;

	protected Button addProbeButton;

	protected Button delProbeButton;

	protected Element model;

	@Override
	public void doSave(IProgressMonitor monitor) {	}

	@Override
	public void doSaveAs() { }

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
		
		NetEditorInput netInput = (NetEditorInput) input;
		model = netInput.getNetDiagram();

		// Add the probe editor as listener to modifications of the
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
		
		Label probeName = new Label(parent, SWT.NULL);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		probeName.setLayoutData(gd);
		probeName.setText("Probes");
		initTable(parent);
		// Add buttons for ading and deleting colors
		Composite probeButtonComposite = new Composite(parent, SWT.NULL);
		probeButtonComposite.setLayout(new GridLayout(2, false));
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		probeButtonComposite.setLayoutData(gd);
		addProbeButton = new Button(probeButtonComposite, SWT.PUSH);
		addProbeButton.setText("Add Probe");
		addProbeButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		addProbeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Element newProbe = createProbe();
				NetHelper.addProbe(model, newProbe);
				updatePropertyFields();
			}
		});

		delProbeButton = new Button(probeButtonComposite, SWT.PUSH);
		delProbeButton.setText("Delete Probe");
		delProbeButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		delProbeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int selection = probeTableViewer.getTable().getSelectionIndex();
				Element probe = (Element) probeTableViewer.getTable().getItem(selection).getData();
				NetHelper.removeProbe(model, probe);
				updatePropertyFields();
			}
		});
		probeButtonComposite.layout();
		
		updatePropertyFields();		
	}

	@Override
	public void setFocus() {		
	}
	
	public void updatePropertyFields() {
		List<Element> probes = NetHelper.listProbes(model);
		probeTableViewer.setInput(probes);
		startPlaceColumnEditing.updateInput();
		endPlaceColumnEditing.updateInput();
		delProbeButton.setEnabled(probeTableViewer.getTable().getSelectionIndex() != -1);
	}

	private Element createProbe() {
		// Add the probe names to a hashset.
		// This way fast checks for name duplicates can be performed.
		HashSet<String> nameIndex = new HashSet<String>();

		List<Element> probes = NetHelper.listProbes(model);
		for (Element probe : probes) {
			nameIndex.add(probe.attributeValue("name"));
		}

		// Find a new name.
		Element newProbe = new DefaultElement("probe");
		for (int x = 0;; x++) {
			if ((x == 0) && (!nameIndex.contains("new probe"))) {
				newProbe.addAttribute("name", "new probe");
				break;
			} else if ((x > 0) && !nameIndex.contains("new probe " + Integer.toString(x))) {
				newProbe.addAttribute("name", "new probe " + Integer.toString(x));
				break;
			}
		}
		newProbe.addAttribute("start-place-id", "");
		newProbe.addAttribute("start-trigger", "entry");
		newProbe.addAttribute("end-place-id", "");
		newProbe.addAttribute("end-trigger", "exit");

		return newProbe;
	}

	protected void initTable(Composite parent) {
		TableLayout tableLayout = new TableLayout();
		for (int i = 0; i < 5; i++)
			tableLayout.addColumnData(new ColumnWeightData(1));		
		
		Table probeTable = new Table(parent, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		probeTable.setLayoutData(gd);
		probeTable.setLinesVisible(true);
		probeTable.setHeaderVisible(true);
		probeTable.setLayout(tableLayout);
		
		probeTableViewer = new TableViewer(probeTable);
		probeTableViewer.setContentProvider(new ArrayContentProvider());
		
		getSite().setSelectionProvider(probeTableViewer);
		
		initTableColumns();

		// Add a listener updating the delete-buttons
		// enabled state when another item is selected.
		probeTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent e) {
				delProbeButton.setEnabled(probeTableViewer.getTable().getSelectionIndex() != -1);
			}
		});

		// Add a mouse listener for deselecting all selected items
		// when the empty table-area without entries is clicked
		probeTable.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				Point mouseClickPoint = new Point(e.x, e.y);
				if (probeTableViewer.getTable().getItem(mouseClickPoint) == null) {
					probeTableViewer.getTable().deselectAll();
					updatePropertyFields();
				}
			}
		});
	}

	protected void initTableColumns() {
		TableViewerColumn col = new TableViewerColumn(probeTableViewer, SWT.LEFT);
		col.setLabelProvider(new XmlAttributeLabelProvider("name", ""));
		col.getColumn().setText("Name");
		XmlAttributeEditingSupport editor = new XmlAttributeEditingSupport(col.getViewer(), "name");
		editor.setValidator(new ICellEditorValidator() {			
			@Override
			public String isValid(Object value) {
				if(NetHelper.existsProbeWithName(model, value.toString())) {
					return "Another probe with this name already exists.";
				}
				return null;
			}
		});
		col.setEditingSupport(editor);
		
		col = new TableViewerColumn(probeTableViewer, SWT.LEFT);
		col.setLabelProvider(new PlaceReferenceLabelProvider("start-place-id"));
		col.getColumn().setText("Start Place");
		startPlaceColumnEditing = new PlaceReferenceColumnEditingSupport(col.getViewer(), "start-place-id");
		col.setEditingSupport(startPlaceColumnEditing);
		
		col = new TableViewerColumn(probeTableViewer, SWT.LEFT);
		col.setLabelProvider(new TriggerAttributeLabelProvider("start-trigger"));
		col.getColumn().setText("Start Trigger");
		col.setEditingSupport(new TriggerAttributeEditingSupport(col.getViewer(), "start-trigger"));
		
		col = new TableViewerColumn(probeTableViewer, SWT.LEFT);
		col.setLabelProvider(new PlaceReferenceLabelProvider("end-place-id"));
		col.getColumn().setText("End Place");
		endPlaceColumnEditing = new PlaceReferenceColumnEditingSupport(col.getViewer(), "end-place-id");
		col.setEditingSupport(endPlaceColumnEditing);
		
		col = new TableViewerColumn(probeTableViewer, SWT.LEFT);
		col.setLabelProvider(new TriggerAttributeLabelProvider("end-trigger"));
		col.getColumn().setText("End Trigger");
		col.setEditingSupport(new TriggerAttributeEditingSupport(col.getViewer(), "end-trigger"));
	}
	
	public void propertyChange(PropertyChangeEvent arg0) {
		updatePropertyFields();
	}
}
