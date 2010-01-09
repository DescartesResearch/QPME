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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.tree.DefaultElement;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IEditorInput;

import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.utils.ITableLabelColorProvider;
import de.tud.cs.qpe.utils.IntegerCellEditor;

public class QueueEditorPage extends Composite implements PropertyChangeListener {
	private static final String[] STRATEGIES = new String[] { "PRIO", "PS", "FCFS", "IS", "RANDOM" };

	protected Table queueTable;

	protected TableViewer queueTableViewer;

	protected String[] columnNames;

	protected Button addQueueButton;

	protected Button delQueueButton;

	protected Element model;

	public QueueEditorPage(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout());
		columnNames = new String[] { "Name", "Scheduling Strategy", "Number Of Servers", "Description" };
		initQueueTable();
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

		// Add the queue editor as listener to modifications of the
		// current document.
		DocumentManager.addPropertyChangeListener(model, this);

		updatePropertyFields();
	}

	public void updatePropertyFields() {
		XPath xpathSelector = DocumentHelper.createXPath("ancestor-or-self::*/queues/queue");
		List queues = xpathSelector.selectNodes(model);
		queueTableViewer.setInput(queues);
		delQueueButton.setEnabled(queueTable.getSelectionIndex() != -1);
	}

	public static Element createQueue(Element model) {
		// Add the queues to indexed hashmaps.
		// This way fast checks for name duplicates can be performed.
		HashMap<String, Element> nameIndex = new HashMap<String, Element>();

		XPath xpathSelector = DocumentHelper.createXPath("ancestor-or-self::*/queues/queue");
		Iterator queueIterator = xpathSelector.selectNodes(model).iterator();
		while (queueIterator.hasNext()) {
			Element queue = (Element) queueIterator.next();
			nameIndex.put(queue.attributeValue("name"), queue);
		}

		// Find a new name.
		Element newQueue = new DefaultElement("queue");
		for (int x = 0;; x++) {
			if ((x == 0) && (!nameIndex.containsKey("new queue"))) {
				newQueue.addAttribute("name", "new queue");
				break;
			} else if ((x > 0) && !nameIndex.containsKey("new queue " + Integer.toString(x))) {
				newQueue.addAttribute("name", "new queue " + Integer.toString(x));
				break;
			}
		}

		newQueue.addAttribute("strategy", "FCFS");
		newQueue.addAttribute("number-of-servers", "1");

		return newQueue;
	}

	protected void initQueueTable() {
		Label queueName = new Label(this, SWT.NULL);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		queueName.setLayoutData(gd);
		queueName.setText("Queues");
		initTable();
		// Add buttons for ading and deleting queues
		Composite queueButtonComposite = new Composite(this, SWT.NULL);
		queueButtonComposite.setLayout(new GridLayout(2, false));
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		queueButtonComposite.setLayoutData(gd);
		addQueueButton = new Button(queueButtonComposite, SWT.PUSH);
		addQueueButton.setText("Add queue");
		addQueueButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		addQueueButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Element newQueue = createQueue(model);
				DocumentManager.addChild(model.element("queues"), newQueue);
				updatePropertyFields();
			}
		});

		delQueueButton = new Button(queueButtonComposite, SWT.PUSH);
		delQueueButton.setText("Del queue");
		delQueueButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		delQueueButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int selection = queueTable.getSelectionIndex();
				Element queue = (Element) queueTable.getItem(selection).getData();
				// Check if the queue is used. If yes, show a dialog asking the
				// user if he is sure. If yes then all usages of this queue
				// have to be removed.
				XPath xpathSelector = DocumentHelper.createXPath("//queue-ref[@queue-id = '" + queue.attributeValue("id") + "']");
				if (xpathSelector.selectSingleNode(model.getDocument()) != null) {
					MessageBox messageBox = new MessageBox(queueTable.getShell(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
					messageBox
							.setMessage("The Queue you are trying to remove is beeing used in the current net. Are you sure you want it to be removed? Removing this queue will couse the removal of all element using it. This process is irreversable.");
					int buttonId = messageBox.open();
					if (buttonId == SWT.YES) {
						// Remove the queue.
						DocumentManager.removeElement(queue);

						// Remove all references to that queue.
						xpathSelector = DocumentHelper.createXPath("//queue-ref[@queue-id = '" + queue.attributeValue("id") + "']");
						Iterator queueRefItarator = xpathSelector.selectNodes(model).iterator();
						while (queueRefItarator.hasNext()) {
							Element queueRef = (Element) queueRefItarator.next();
							DocumentManager.removeElement(queueRef);
						}

					}
				} else {
					model.element("queues").remove(queue);
				}

				updatePropertyFields();
			}
		});
		queueButtonComposite.layout();
	}

	protected void initTable() {
		queueTable = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		queueTable.setLayoutData(gd);
		queueTable.setLinesVisible(true);
		queueTable.setHeaderVisible(true);

		initTableColumns();
		initTableViewer();
		initCellEditors();

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
		TableColumn col = new TableColumn(queueTable, SWT.LEFT);
		col.setText("Name");
		col.setWidth(150);
		col = new TableColumn(queueTable, SWT.LEFT);
		col.setText("Scheduling Strategy");
		col.setWidth(150);
		col = new TableColumn(queueTable, SWT.LEFT);
		col.setText("Number Of Servers");
		col.setWidth(150);
		col = new TableColumn(queueTable, SWT.LEFT);
		col.setText("Description");
		col.setWidth(500);
	}

	protected void initTableViewer() {
		queueTableViewer = new TableViewer(queueTable);
		queueTableViewer.setColumnProperties(columnNames);

		initContentProvider();
		initLabelProvider();
		initCellModifier();
	}

	protected void initContentProvider() {
		queueTableViewer.setContentProvider(new IStructuredContentProvider() {
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
		queueTableViewer.setLabelProvider(new ITableLabelColorProvider() {
			public void dispose() {
			}

			public Image getColumnImage(Object element, int columnIndex) {
				return null;
			}

			public String getColumnText(Object element, int columnIndex) {
				Element queue = (Element) element;
				switch (columnIndex) {
				case 0:
					return queue.attributeValue("name", "new queue");
				case 1:
					return queue.attributeValue("strategy");
				case 2:
					return queue.attributeValue("number-of-servers");
				case 3:
					return queue.attributeValue("description");
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
				return null;
			}
		});
	}

	protected void initCellModifier() {
		queueTableViewer.setCellModifier(new ICellModifier() {
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
				Element queue = (Element) element;

				switch (index) {
				case 0:
					return queue.attributeValue("name", "unnnamed queue");
				case 1:
					for (int i = 0; i < STRATEGIES.length; i++) {
						if (STRATEGIES[i].equals(queue.attributeValue("strategy", "FCFS"))) {
							return new Integer(i);
						}
					}
					return new Integer(0);
				case 2:
					return Integer.parseInt(queue.attributeValue("number-of-servers", "1"));
				case 3:
					return queue.attributeValue("description", "");
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

				Element queue = null;
				if (element instanceof Item) {
					TableItem item = (TableItem) element;
					queue = (Element) item.getData();
				} else {
					queue = (Element) element;
				}

				int iValue;
				switch (index) {
				case 0:
					// Add the queues to indexed hashmaps.
					// This way fast checks for name
					// duplicates can be performed.
					HashMap<String, Element> nameIndex = new HashMap<String, Element>();
					XPath xpathSelector = DocumentHelper.createXPath("ancestor-or-self::*/queues/queue");
					Iterator queueIterator = xpathSelector.selectNodes(model).iterator();
					while (queueIterator.hasNext()) {
						Element tmpQueue = (Element) queueIterator.next();
						nameIndex.put((String) tmpQueue.attributeValue("name"), tmpQueue);
					}

					if (!nameIndex.containsKey(value)) {
						DocumentManager.setAttribute(queue, "name", (String) value);
					}
					break;
				case 1:
					DocumentManager.setAttribute(queue, "strategy", STRATEGIES[(Integer) value]);
					break;
				case 2:
					iValue = ((Integer) value).intValue();
					if(iValue >= 0) {
						DocumentManager.setAttribute(queue, "number-of-servers", ((Integer) value).toString());
					}
					break;
				case 3:
					DocumentManager.setAttribute(queue, "description", (String) value);
					break;
				}

				queueTableViewer.update(queue, null);
			}
		});
	}

	protected void initCellEditors() {
		CellEditor[] cellEditors = new CellEditor[4];
		cellEditors[0] = new TextCellEditor(queueTableViewer.getTable());
		cellEditors[1] = new ComboBoxCellEditor(queueTableViewer.getTable(), STRATEGIES);
		cellEditors[2] = new IntegerCellEditor(queueTableViewer.getTable());
		cellEditors[3] = cellEditors[0];

		queueTableViewer.setCellEditors(cellEditors);
	}

	public void propertyChange(PropertyChangeEvent arg0) {
		updatePropertyFields();
	}
}
