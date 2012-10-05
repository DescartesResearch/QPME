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
 * Original Author(s):  Frederik Zipp
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2009/02/03  Frederik Zipp     Created.
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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
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
import de.tud.cs.qpe.utils.CellValidators;
import de.tud.cs.qpe.utils.XmlAttributeEditingSupport;
import de.tud.cs.qpe.utils.XmlAttributeLabelProvider;
import de.tud.cs.qpe.utils.XmlEnumerationAttributeEditingSupport;

public class QueueEditorPage extends EditorPart implements PropertyChangeListener {
	private static final String[] STRATEGIES = new String[] { "PRIO", "PS", "FCFS", "IS", "RANDOM" };

	protected Table queueTable;

	protected TableViewer queueTableViewer;

	protected Button addQueueButton;

	protected Button delQueueButton;

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
		
		NetEditorInput netInput = (NetEditorInput) input;
		model = netInput.getNetDiagram();

		// Add the queue editor as listener to modifications of the
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
		
		Label queueName = new Label(parent, SWT.NULL);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		queueName.setLayoutData(gd);
		queueName.setText("Queues");
		initTable(parent);
		// Add buttons for ading and deleting queues
		Composite queueButtonComposite = new Composite(parent, SWT.NULL);
		queueButtonComposite.setLayout(new GridLayout(2, false));
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		queueButtonComposite.setLayoutData(gd);
		addQueueButton = new Button(queueButtonComposite, SWT.PUSH);
		addQueueButton.setText("Add Queue");
		addQueueButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		addQueueButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Element newQueue = createQueue(model);
				NetHelper.addQueue(model, newQueue);
				updatePropertyFields();
			}
		});

		delQueueButton = new Button(queueButtonComposite, SWT.PUSH);
		delQueueButton.setText("Delete Queue");
		delQueueButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		delQueueButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int selection = queueTable.getSelectionIndex();
				Element queue = (Element) queueTable.getItem(selection).getData();
				// Check if the queue is used. If yes, show a dialog asking the
				// user if he is sure. If yes then all usages of this queue
				// have to be removed.
				if (NetHelper.isQueueReferencedInNet(model, queue)) {
					boolean result = MessageDialog.openConfirm(getSite().getShell(), "", "The Queue you are trying to remove is is referenced in the current net. Are you sure you want it to be removed? Removing this queue will cause the removal of all its references. This process is irreversible.");
					if (result) {
						// Remove the queue and its references.
						NetHelper.removeQueue(model, queue);
					}
				} else {
					NetHelper.removeQueue(model, queue);
				}

				updatePropertyFields();
			}
		});
		queueButtonComposite.layout();
		
		updatePropertyFields();
	}

	@Override
	public void setFocus() {}

	public void updatePropertyFields() {
		List<Element> queues = NetHelper.listQueues(model);
		queueTableViewer.setInput(queues);
		delQueueButton.setEnabled(queueTable.getSelectionIndex() != -1);
	}

	public static Element createQueue(Element model) {
		// Add the queue names to a hashset.
		// This way fast checks for name duplicates can be performed.
		HashSet<String> nameIndex = new HashSet<String>();

		List<Element> queues = NetHelper.listQueues(model);
		for (Element queue : queues) {
			nameIndex.add(queue.attributeValue("name"));
		}

		// Find a new name.
		Element newQueue = new DefaultElement("queue");
		for (int x = 0;; x++) {
			if ((x == 0) && (!nameIndex.contains("new queue"))) {
				newQueue.addAttribute("name", "new queue");
				break;
			} else if ((x > 0) && !nameIndex.contains("new queue " + Integer.toString(x))) {
				newQueue.addAttribute("name", "new queue " + Integer.toString(x));
				break;
			}
		}

		newQueue.addAttribute("strategy", "FCFS");
		newQueue.addAttribute("number-of-servers", "1");

		return newQueue;
	}

	protected void initTable(Composite parent) {
		TableLayout tableLayout = new TableLayout();
		for (int i = 0; i < 4; i++)
			tableLayout.addColumnData(new ColumnWeightData(1));
		
		queueTable = new Table(parent, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		queueTable.setLayoutData(gd);
		queueTable.setLinesVisible(true);
		queueTable.setHeaderVisible(true);
		queueTable.setLayout(tableLayout);
		
		queueTableViewer = new TableViewer(queueTable);
		queueTableViewer.setContentProvider(new ArrayContentProvider());
		
		getSite().setSelectionProvider(queueTableViewer);

		initTableColumns();

		// Add a listener updating the delete-buttons
		// enabled state when another item is selected.
		queueTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent e) {
				delQueueButton.setEnabled(queueTable.getSelectionIndex() != -1);
			}
		});

		// Add a mouse listener for deselecting all selected items
		// when the empty table-area without entries is clicked
		queueTable.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				Point mouseClickPoint = new Point(e.x, e.y);
				if (queueTable.getItem(mouseClickPoint) == null) {
					queueTable.deselectAll();
					updatePropertyFields();
				}
			}
		});
	}

	protected void initTableColumns() {
		TableViewerColumn col = new TableViewerColumn(queueTableViewer, SWT.LEFT);
		col.getColumn().setText("Name");
		col.setLabelProvider(new XmlAttributeLabelProvider("name", "new queue"));
		XmlAttributeEditingSupport editor = new XmlAttributeEditingSupport(col.getViewer(), "name");
		editor.setValidator(new ICellEditorValidator() {			
			@Override
			public String isValid(Object value) {
				if(NetHelper.existsQueueWithName(model, value.toString())) {
					return "Another queue with this name already exists.";
				}
				return null;
			}
		});
		col.setEditingSupport(editor);
		
		col = new TableViewerColumn(queueTableViewer, SWT.LEFT);
		col.getColumn().setText("Scheduling Strategy");
		col.setLabelProvider(new XmlAttributeLabelProvider("strategy", ""));
		col.setEditingSupport(new XmlEnumerationAttributeEditingSupport(col.getViewer(), "strategy") {
			
			@Override
			protected Object[] getItems() {
				return STRATEGIES;
			}
		});
		
		col = new TableViewerColumn(queueTableViewer, SWT.LEFT);
		col.getColumn().setText("Number Of Servers");
		col.setLabelProvider(new XmlAttributeLabelProvider("number-of-servers", ""));
		editor = new XmlAttributeEditingSupport(col.getViewer(), "number-of-servers");
		editor.setValidator(CellValidators.newNonNegativeIntegerValidator());
		col.setEditingSupport(editor);
		
		col = new TableViewerColumn(queueTableViewer, SWT.LEFT);
		col.getColumn().setText("Description");
		col.setLabelProvider(new XmlAttributeLabelProvider("description", ""));
		col.setEditingSupport(new XmlAttributeEditingSupport(col.getViewer(), "description"));
	}

	public void propertyChange(PropertyChangeEvent arg0) {
		updatePropertyFields();
	}
}
