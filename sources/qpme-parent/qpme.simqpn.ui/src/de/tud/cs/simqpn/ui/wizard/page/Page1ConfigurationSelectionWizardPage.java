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
package de.tud.cs.simqpn.ui.wizard.page;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.dom4j.Element;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.wizard.WizardPage;
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
import org.eclipse.ui.PlatformUI;

import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.model.NetHelper;
import de.tud.cs.qpe.utils.CellValidators;
import de.tud.cs.qpe.utils.ValidatingEditingSupport;
import de.tud.cs.simqpn.ui.dialog.AnalysisMethodSelectionDialog;
import de.tud.cs.simqpn.ui.model.BatchMeansConfiguration;
import de.tud.cs.simqpn.ui.model.Configuration;
import de.tud.cs.simqpn.ui.model.ReplicationDeletionConfiguration;
import de.tud.cs.simqpn.ui.model.WelchConfiguration;

public class Page1ConfigurationSelectionWizardPage extends WizardPage {

	protected Table configurationTable;

	protected TableViewer configurationTableViewer;

	protected Button addConfigurationButton;

	protected Button delConfigurationButton;
	
	private List<Configuration> configurations = new ArrayList<Configuration>();

	private Configuration selectedConfiguration;
	
	private Element net;
	
	private HashSet<String> names = new HashSet<String>();

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public Page1ConfigurationSelectionWizardPage(Element net) {
		super("configurationSelectionWizardPage");
		this.net = net;
		setTitle("Select Run Configuration");
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		layout.verticalSpacing = 30;
		layout.horizontalSpacing = 30;

		Label configurationName = new Label(container, SWT.NULL);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		configurationName.setLayoutData(gd);
		configurationName.setText("Configurations:");

		initConfigurationTable(container);

		updateTableContents();
		updateDialog();
		updateMessages();
		setControl(container);

		setPageComplete(configurationTable.getSelectionIndex() != -1);
	}

	protected void initConfigurationTable(Composite parent) {
		initTable(parent);

		addConfigurationButton = new Button(parent, SWT.PUSH);
		addConfigurationButton.setText("Add Configuration");
		addConfigurationButton.setLayoutData(new GridData(
				GridData.FILL_HORIZONTAL));
		addConfigurationButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				AnalysisMethodSelectionDialog dialog = new AnalysisMethodSelectionDialog(
						PlatformUI.getWorkbench().getActiveWorkbenchWindow()
								.getShell());
				int result = dialog.open();
				// 0 is returned, when the ok-button is pressed
				if(result == 0) {
					Configuration newConfiguration = createConfiguration(dialog.getScenario());
					
					configurations.add(newConfiguration);
					names.add(newConfiguration.getName());
					
					configurationTableViewer.setInput(configurations);

					updateDialog();

					// Update error-messages.
					updateMessages();
				}				
			}
		});

		delConfigurationButton = new Button(parent, SWT.PUSH);
		delConfigurationButton.setText("Delete Configuration");
		delConfigurationButton.setLayoutData(new GridData(
				GridData.FILL_HORIZONTAL));
		delConfigurationButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection selection = (IStructuredSelection)configurationTableViewer.getSelection();
				if (selection.size() == 1) {
					Configuration conf = (Configuration)selection.getFirstElement();
					boolean ok = MessageDialog.openConfirm(getShell(), "Remove Confirmation", "Are you sure you want to remove configuration \""
							+ conf.getName() + "\"? This action is not undoable!");
					if (ok) {
						names.remove(conf.getName());
						configurations.remove(conf);					
						
						List<Element> metadata = NetHelper.listAllMetadata(net, conf.getName());
						for (Element m : metadata) {
							DocumentManager.removeElement(m);
						}
						
						configurationTableViewer.setInput(configurations);						
					}
					
					updateDialog();

					// Update error-messages.
					updateMessages();
				}
			}
		});
		delConfigurationButton.setEnabled(false);
	}

	protected void initTable(Composite parent) {
		configurationTable = new Table(parent, SWT.BORDER | SWT.FULL_SELECTION);

		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		configurationTable.setLayoutData(gd);
		configurationTable.setLinesVisible(true);
		configurationTable.setHeaderVisible(true);
		
		configurationTableViewer = new TableViewer(configurationTable);
		configurationTableViewer.setContentProvider(new ArrayContentProvider());

		initTableColumns();

		// Add a listener updating the delete-buttons
		// enabled state when another item is selected.
		configurationTableViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent e) {
						IStructuredSelection selection = (StructuredSelection)e.getSelection();
						if (selection.size() == 1) {
							Configuration conf = (Configuration)selection.getFirstElement();
							selectedConfiguration = conf;
							delConfigurationButton.setEnabled(true);
							setPageComplete(true);
						} else {
							delConfigurationButton.setEnabled(false);
							setPageComplete(false);
						}
					}
				});

		// Add a mouse listener for deselecting all selected items
		// when the empty table-area without entries is clicked
		configurationTable.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				Point mouseClickPoint = new Point(e.x, e.y);
				if (configurationTable.getItem(mouseClickPoint) == null) {
					configurationTable.deselectAll();
					delConfigurationButton.setEnabled(configurationTable
							.getSelectionIndex() != -1);
					setPageComplete(configurationTable.getSelectionIndex() != -1);
				}
			}
		});
	}

	protected void initTableColumns() {
		TableViewerColumn col = new TableViewerColumn(configurationTableViewer, SWT.LEFT);
		col.getColumn().setText("Name");
		col.getColumn().setWidth(150);
		col.setLabelProvider(new CellLabelProvider() {			
			@Override
			public void update(ViewerCell cell) {
				Configuration c = (Configuration)cell.getElement();
				cell.setText(c.getName());
			}
		});
		col.setEditingSupport(new ValidatingEditingSupport(col.getViewer()) {
			@Override
			protected void setValue(Object element, Object value) {			
				Configuration configuration = (Configuration)element;
				names.remove(configuration.getName());
				configuration.setName((String)value);
				names.add(configuration.getName());
				getViewer().refresh();
			}
			
			@Override
			protected CellEditor createCellEditor(Composite parent) {
				TextCellEditor editor = new TextCellEditor((Composite)getViewer().getControl());
				editor.setValidator(new ICellEditorValidator() {
					@Override
					public String isValid(Object value) {
						if (names.contains((String)value)) {
							return "Another configuration with this name already exists.";
						} 
						return null;
					}					
				});
				return editor;
			}

			@Override
			protected Object getValue(Object element) {
				return ((Configuration)element).getName();
			}
		});
		
		col = new TableViewerColumn(configurationTableViewer, SWT.LEFT);
		col.getColumn().setText("Analysis Method");
		col.getColumn().setWidth(150);
		col.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				Configuration configuration = (Configuration)cell.getElement();
				switch(configuration.getScenario()) {
				case 1: 
					cell.setText("Batch Means");
					break;
				case 2: 
					cell.setText("Replication/Deletion");
					break;
				case 3:
					cell.setText("Method of Welch");
					break;
				}
			}
		});
		
		col = new TableViewerColumn(configurationTableViewer, SWT.LEFT);
		col.getColumn().setText("Number of Runs");
		col.getColumn().setWidth(125);
		col.setLabelProvider(new CellLabelProvider() {			
			@Override
			public void update(ViewerCell cell) {
				Configuration c = (Configuration)cell.getElement();
				cell.setText(Integer.toString(c.getNumberOfRuns()));								
			}
		});
		col.setEditingSupport(new ValidatingEditingSupport(col.getViewer()) {
			@Override
			protected CellEditor createCellEditor(Composite parent) {
				TextCellEditor editor = new TextCellEditor((Composite)getViewer().getControl());
				editor.setValidator(CellValidators.newPositiveIntegerValidator());
				return editor;
			}

			@Override
			protected boolean canEdit(Object element) {
				Configuration c = (Configuration)element;
				return c.getScenario() != 1;
			}

			@Override
			protected Object getValue(Object element) {
				Configuration c = (Configuration)element;
				return Integer.toString(c.getNumberOfRuns());
			}

			@Override
			protected void setValue(Object element, Object value) {
				Configuration c = (Configuration)element;
				c.setNumberOfRuns(Integer.parseInt((String) value));
				getViewer().refresh();
			}
			
		});
	
		col = new TableViewerColumn(configurationTableViewer, SWT.LEFT);
		col.getColumn().setText("Description");
		col.getColumn().setWidth(570);
		col.setLabelProvider(new CellLabelProvider() {			
			@Override
			public void update(ViewerCell cell) {
				Configuration c = (Configuration)cell.getElement();
				cell.setText(c.getDescription());
			}
		});
		col.setEditingSupport(new ValidatingEditingSupport(col.getViewer()) {			
			@Override
			protected void setValue(Object element, Object value) {
				Configuration c = (Configuration)element;
				c.setDescription((String) value);
				getViewer().refresh();
			}
			
			@Override
			protected Object getValue(Object element) {
				Configuration c = (Configuration)element;
				return c.getDescription();
			}
			
			@Override
			protected CellEditor createCellEditor(Composite parent) {
				return new TextCellEditor((Composite)getViewer().getControl());
			}
		});
	}

	private Configuration createConfiguration(int scenario) {
		// Find a new name.
		String name = "new configuration";
		if (names.contains(name)) {
			for (int x = 1; x < Integer.MAX_VALUE; x++) {
				name = "new configuration " + x;
				if (!names.contains(name)) {
					break;
				}
			}
		}

		Configuration conf = null;
		switch(scenario) {
		case 1:
			conf = new BatchMeansConfiguration();
			break;
		case 2:
			conf = new ReplicationDeletionConfiguration();
			break;
		case 3:
			conf = new WelchConfiguration();
			break;
		}
		
		conf.createSimulationConfiguration(net, name);
		conf.init(net);
		
		return conf;
	}
	
	private void updateTableContents() {
		names.clear();
		configurations.clear();
		
		List<Element> configurationElements = NetHelper.listConfigurations(net);
		
		for (Element c : configurationElements) {
			int scenario = Integer.parseInt(c.attributeValue("scenario"));
			Configuration conf = null;
			switch(scenario) {
			case 1:
				conf = new BatchMeansConfiguration();
				break;
			case 2:
				conf = new ReplicationDeletionConfiguration();
				break;
			case 3:
				conf = new WelchConfiguration();
				break;
			}
			conf.setMetadata(c);
			conf.init(net);
			names.add(conf.getName());
			
			configurations.add(conf);
		}
		
		
		configurationTableViewer.setInput(configurations);

		// pre-select first entry
		if (configurations.size() > 0) {
			selectedConfiguration = configurations.get(0);
			configurationTableViewer.setSelection(new StructuredSelection(selectedConfiguration));
		}
	}

	private void updateMessages() {
		setPageComplete(configurationTable.getSelectionIndex() != -1);
	}

	public void updateDialog() {
		delConfigurationButton.setEnabled(configurationTable
				.getSelectionIndex() != -1);
	}

	public Configuration getSelectedConfiguration() {
		return selectedConfiguration;
	}

}
