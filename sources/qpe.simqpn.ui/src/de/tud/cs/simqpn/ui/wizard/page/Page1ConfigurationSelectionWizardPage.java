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
package de.tud.cs.simqpn.ui.wizard.page;

import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.tree.DefaultElement;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
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
import org.eclipse.ui.PlatformUI;

import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.utils.IntegerCellEditor;
import de.tud.cs.simqpn.ui.dialog.AnalysisMethodSelectionDialog;

public class Page1ConfigurationSelectionWizardPage extends BaseWizardPage {
	public static final String PROP_CONFIGURATION_CHANGED = "wizard.configuration";

	protected Table configurationTable;

	protected TableViewer configurationTableViewer;

	protected Button addConfigurationButton;

	protected Button delConfigurationButton;

	protected String[] columnNames;

	public static String activeConfiguration;

	protected PropertyChangeSupport pcs;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public Page1ConfigurationSelectionWizardPage(ISelection selection,
			Element net) {
		super("configurationSelectionWizardPage", selection, net);
		setTitle("Select Run Configuration");
		addPropertyChangeListener(this);
		columnNames = new String[] { "Name", "Analysis Method", "Number of Runs",
				"Description" };
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
					Element newConfguration = createConfiguration();
					DocumentManager.addChild(net.element("meta-attributes"),
							newConfguration);

					initializeMetaAttribute(newConfguration, dialog.getScenario());
					
					// Set the active configuration to the new one, or the
					// getMetaAttibutes method cannot return it properly.
					activeConfiguration = newConfguration.attributeValue("configuration-name");
					
					// Add a meta-Attribute to all places containing the statsLevel attribute
					XPath xpathSelector = DocumentHelper.createXPath("//place");
					Iterator placeIterator = xpathSelector.selectNodes(net).iterator();
					while(placeIterator.hasNext()) {
						Element place = (Element) placeIterator.next();
						Element placeMetaAttribute = getMetaAttribute(place);
						placeMetaAttribute.addAttribute("statsLevel", "1");
					}

					// Update the tables contents.
					updateTableContents();
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
				int selection = configurationTable.getSelectionIndex();
				Element configuration = (Element) configurationTable.getItem(
						selection).getData();

				MessageBox messageBox = new MessageBox(configurationTable
						.getShell(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
				messageBox
						.setMessage("Are you sure you want to remove configuration \""
								+ configuration
										.attributeValue("configuration-name")
								+ "\"? This action is not undoable!");
				int buttonId = messageBox.open();
				if (buttonId == SWT.YES) {
					// Remove all meta-attributes for this configuration.
					XPath xpathSelector = DocumentHelper
							.createXPath("//meta-attribute[@name='sim-qpn' and @configuration-name='"
									+ configuration
											.attributeValue("configuration-name")
									+ "']");
					Iterator configurationElementIterator = xpathSelector
							.selectNodes(net).iterator();
					while (configurationElementIterator.hasNext()) {
						Element curElement = (Element) configurationElementIterator
								.next();
						DocumentManager.removeElement(curElement);
					}

					// Update the tables contents.
					updateTableContents();
					updateDialog();

					// Update error-messages.
					updateMessages();
				}
			}
		});
		delConfigurationButton.setEnabled(false);
		
		// Initialize eventually missing meta-attributes.
		setupMetaAttributes();
	}

	protected void initTable(Composite parent) {
		configurationTable = new Table(parent, SWT.BORDER | SWT.FULL_SELECTION);

		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		configurationTable.setLayoutData(gd);
		configurationTable.setLinesVisible(true);
		configurationTable.setHeaderVisible(true);

		initTableColumns();
		initTableViewer();
		initCellEditors();

		// Add a listener updating the delete-buttons
		// enabled state when another item is selected.
		configurationTableViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent e) {
						Object obj = ((StructuredSelection) e.getSelection())
								.getFirstElement();
						if (obj instanceof Element) {
							Element selectedConfiguration = (Element) obj;
							activeConfiguration = selectedConfiguration
									.attributeValue("configuration-name");
							// Tell the others about the change of
							// configuration.
							firePropertyChanged(PROP_CONFIGURATION_CHANGED);
						}
						delConfigurationButton.setEnabled(configurationTable
								.getSelectionIndex() != -1);
						setPageComplete(configurationTable.getSelectionIndex() != -1);
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
		TableColumn col = new TableColumn(configurationTable, SWT.LEFT);
		col.setText(columnNames[0]);
		col.setWidth(150);
		col = new TableColumn(configurationTable, SWT.LEFT);
		col.setText(columnNames[1]);
		col.setWidth(150);
		col = new TableColumn(configurationTable, SWT.LEFT);
		col.setText(columnNames[2]);
		col.setWidth(125);
		col = new TableColumn(configurationTable, SWT.LEFT);
		col.setText(columnNames[3]);
		col.setWidth(570);
	}

	protected void initTableViewer() {
		configurationTableViewer = new TableViewer(configurationTable);
		configurationTableViewer.setColumnProperties(columnNames);

		initContentProvider();
		initLabelProvider();
		initCellModifier();
	}

	protected void initContentProvider() {
		configurationTableViewer
				.setContentProvider(new IStructuredContentProvider() {
					public Object[] getElements(Object inputElements) {
						List l = (List) inputElements;
						return l.toArray();
					}

					public void dispose() {
					}

					public void inputChanged(Viewer viewer, Object oldInput,
							Object newInput) {
					}
				});
	}

	protected void initLabelProvider() {
		configurationTableViewer.setLabelProvider(new ITableLabelProvider() {
			public void dispose() {
			}

			public Image getColumnImage(Object element, int columnIndex) {
				return null;
			}

			public String getColumnText(Object element, int columnIndex) {
				Element configuration = (Element) element;
				switch (columnIndex) {
				case 0:
					return configuration.attributeValue("configuration-name",
							"new configuration");
				case 1:
					int value = Integer.parseInt(configuration.attributeValue("scenario", "0"));
					switch(value) {
					case 1: return "Batch Means";
					case 2: return "Replication/Deletion";
					case 3: return "Method of Welch";
					}
					return null;
				case 2:
					return configuration.attributeValue("number-of-runs",
							"n.a.");
				case 3:
					return configuration.attributeValue(
							"configuration-description", "");
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
		});
	}

	protected void initCellModifier() {
		configurationTableViewer.setCellModifier(new ICellModifier() {
			public boolean canModify(Object object, String property) {
				Element element = (Element) object;
				// The scenario is not cahngeable
				if ("Analysis Method".equals(property)) {
					return false;
				}
				// If the scenario is set to bach-means, then the
				// number-of-runs is not used
				if ("Number of runs".equals(property) && "1".equals(element.attributeValue("scenario"))) {
					return false;
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
				Element color = (Element) element;

				switch (index) {
				case 0:
					return color.attributeValue("configuration-name");
				case 1:
					return null;
				case 2:
					return new Integer(color.attributeValue("number-of-runs",
							"1"));
				case 3:
					return color
							.attributeValue("configuration-description", "");
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

				Element configuration = null;
				if (element instanceof Item) {
					TableItem item = (TableItem) element;
					configuration = (Element) item.getData();
				} else {
					configuration = (Element) element;
				}

				switch (index) {
				case 0:
					// Check if the name is used yet. Only update if it is not
					if(!configuration.attributeValue("configuration-name", "").equals(value)) {
						XPath xpathSelector  = DocumentHelper.createXPath("/net/meta-attributes/meta-attribute[@name='sim-qpn' and @configuration-name='" + (String) value + "']");
						if(xpathSelector.selectSingleNode(net) == null) {
							// Get all meta-attributes of the current configuration.
							xpathSelector = DocumentHelper
									.createXPath("//meta-attribute[@name='sim-qpn' and @configuration-name='"
											+ configuration.attributeValue(
													"configuration-name", "") + "']");
							// Iterate through them and change their names.
							Iterator configurationElementIterator = xpathSelector
									.selectNodes(net).iterator();
							while (configurationElementIterator.hasNext()) {
								Element configurationElement = (Element) configurationElementIterator
										.next();
								DocumentManager.setAttribute(configurationElement,
										"configuration-name", (String) value);
							}
						}
					}
					break;
				case 1:
					DocumentManager.setAttribute(configuration, "scenario",
							(String) value);
					break;
				case 2:
					DocumentManager.setAttribute(configuration,
							"number-of-runs", ((Integer) value).toString());
					break;
				case 3:
					DocumentManager.setAttribute(configuration,
							"configuration-description", (String) value);
					break;
				}

				configurationTableViewer.update(configuration, null);
			}
		});
	}

	protected void initCellEditors() {
		CellEditor[] cellEditors = new CellEditor[4];
		cellEditors[0] = new TextCellEditor(configurationTableViewer.getTable());
		cellEditors[1] = null;
		cellEditors[2] = new IntegerCellEditor(configurationTableViewer
				.getTable());
		cellEditors[3] = cellEditors[0];

		configurationTableViewer.setCellEditors(cellEditors);
	}

	private Element createConfiguration() {
		// Add the configurations to an indexed hashmap.
		// This way fast checks for name duplicates can be performed.
		HashMap<String, Element> nameIndex = new HashMap<String, Element>();

		XPath xpathSelector = DocumentHelper
				.createXPath("/net/meta-attributes/meta-attribute[@name='sim-qpn']");
		Iterator configurationIterator = xpathSelector.selectNodes(net)
				.iterator();
		while (configurationIterator.hasNext()) {
			Element configuration = (Element) configurationIterator.next();
			nameIndex.put(configuration.attributeValue("configuration-name"),
					configuration);
		}

		// Find a new name.
		Element newConfiguration = new DefaultElement("meta-attribute");
		newConfiguration.addAttribute("name", "sim-qpn");
		for (int x = 0;; x++) {
			if ((x == 0) && (!nameIndex.containsKey("new configuration"))) {
				newConfiguration.addAttribute("configuration-name",
						"new configuration");
				break;
			} else if ((x > 0)
					&& !nameIndex.containsKey("new configuration "
							+ Integer.toString(x))) {
				newConfiguration.addAttribute("configuration-name",
						"new configuration " + Integer.toString(x));
				break;
			}
		}

		// Set the analysis mathod to BATCH_MEANS as default.
		// Gets rid of nasty initialisation problems.
		newConfiguration.addAttribute("scenario", "1");
		return newConfiguration;
	}
	
	private void updateTableContents() {
		XPath xpathSelector = DocumentHelper.createXPath("/net/meta-attributes/meta-attribute[@name='sim-qpn']");
		List configurations = xpathSelector.selectNodes(net);
		configurationTableViewer.setInput(configurations);

		// pre-select first entry
		if (configurationTable.getItemCount() > 0) {
			configurationTable.setSelection(0);
			Element simQpnAttrib = (Element) configurations.get(0);
			activeConfiguration = simQpnAttrib
				.attributeValue("configuration-name");
		}
	}

	private void updateMessages() {
		setPageComplete(configurationTable.getSelectionIndex() != -1);
	}

	public void updateDialog() {
		delConfigurationButton.setEnabled(configurationTable
				.getSelectionIndex() != -1);
	}
	
	public void setupMetaAttributes() {
		XPath xpathSelector = DocumentHelper.createXPath("/net/meta-attributes/meta-attribute[@name='sim-qpn']");
		Iterator configurations = xpathSelector.selectNodes(net).iterator();
		while(configurations.hasNext()) {
			Element configuration = (Element) configurations.next();
			String name = configuration.attributeValue("configuration-name");
			xpathSelector = DocumentHelper.createXPath("//place[not(meta-attributes/meta-attribute[@name='sim-qpn' and @configuration-name='" + name + "'])]");
			Iterator placesWithoutMetaAttributes = xpathSelector.selectNodes(net).iterator();
			while(placesWithoutMetaAttributes.hasNext()) {
				Element placeWithoutMetaAttribute = (Element) placesWithoutMetaAttributes.next();
				
				Element metaAttributeContainer = placeWithoutMetaAttribute.element("meta-attributes"); 
				if(metaAttributeContainer == null) {
					metaAttributeContainer = placeWithoutMetaAttribute.addElement("meta-attributes");
				}

				Element metaAttribute = new DefaultElement("meta-attribute");
				metaAttribute.addAttribute("name", "sim-qpn");
				metaAttribute.addAttribute("configuration-name", name);
				metaAttribute.addAttribute("statsLevel", "1");
				
				DocumentManager.addChild(metaAttributeContainer, metaAttribute);
			}
			
			// Get all elements whose parent places have a stats level of 3 r higher
			// and who don't posses a meta-attribute for that configuration.
			xpathSelector = DocumentHelper.createXPath("//color-ref[../../meta-attributes/meta-attribute[@name='sim-qpn' and @configuration-name='" + name + "' and @statsLevel >= 3] and not(meta-attributes/meta-attribute[@name='sim-qpn' and @configuration-name='" + name + "'])]");
			Iterator colorRefsWithoutMetaAttributes = xpathSelector.selectNodes(net).iterator();
			while(colorRefsWithoutMetaAttributes.hasNext()) {
				Element colorRefWithoutMetaAttributes = (Element) colorRefsWithoutMetaAttributes.next();

				Element metaAttributeContainer = colorRefWithoutMetaAttributes.element("meta-attributes"); 
				if(metaAttributeContainer == null) {
					metaAttributeContainer = colorRefWithoutMetaAttributes.addElement("meta-attributes");
				}
				
				Element metaAttribute = new DefaultElement("meta-attribute");
				DocumentManager.addChild(metaAttributeContainer, metaAttribute);
				metaAttribute.addAttribute("name", "sim-qpn");
				metaAttribute.addAttribute("configuration-name", name);
				Page3PlaceConfigurationParametersWizardPage.initializeMetaAttribute(metaAttribute, Integer.parseInt(configuration.attributeValue("scenario")));
			}
		}
	}
	
	public static void initializeMetaAttribute(Element metaAttribute, int scenaro) {
		switch (scenaro) {
		case 1: {
			metaAttribute.addAttribute("scenario", "1");
			metaAttribute.addAttribute("ramp-up-length", "1000000");
			metaAttribute.addAttribute("total-run-length", "10000000");
			metaAttribute.addAttribute("stopping-rule", "FIXEDLEN");
			metaAttribute.addAttribute("time-before-initial-heart-beat", "100000");
			metaAttribute.addAttribute("time-between-stop-checks", "100000");
			metaAttribute.addAttribute("seconds-between-stop-checks", "60");
			metaAttribute.addAttribute("seconds-between-heart-beats", "60");
			metaAttribute.addAttribute("verbosity-level", "0");
			metaAttribute.addAttribute("output-directory", "/");
			break;
		}
		case 2: {
			metaAttribute.addAttribute("scenario", "2");
			metaAttribute.addAttribute("number-of-runs", "100");
			metaAttribute.addAttribute("ramp-up-length", "1000000");
			metaAttribute.addAttribute("total-run-length", "10000000");
			metaAttribute.addAttribute("stopping-rule", "FIXEDLEN");
			metaAttribute.addAttribute("time-before-initial-heart-beat", "100000");
			metaAttribute.addAttribute("seconds-between-heart-beats", "60");
			metaAttribute.addAttribute("verbosity-level", "0");
			metaAttribute.addAttribute("output-directory", "/");
			break;
		}
		case 3: {
			metaAttribute.addAttribute("scenario", "3");
			metaAttribute.addAttribute("number-of-runs", "100");
			metaAttribute.addAttribute("total-run-length", "10000000");
			metaAttribute.addAttribute("stopping-rule", "FIXEDLEN");
			metaAttribute.addAttribute("time-before-initial-heart-beat", "100000");
			metaAttribute.addAttribute("seconds-between-heart-beats", "60");
			metaAttribute.addAttribute("verbosity-level", "0");
			metaAttribute.addAttribute("output-directory", "/");
			break;
		}
		}		
	}
}
