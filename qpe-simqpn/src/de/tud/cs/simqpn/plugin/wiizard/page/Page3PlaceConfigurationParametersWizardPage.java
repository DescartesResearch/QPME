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
 *  2008/05/05  Frederik Zipp     Set max statsLevel to 5.
 * 
 */
package de.tud.cs.simqpn.plugin.wiizard.page;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.utils.DoubleCellEditor;
import de.tud.cs.qpe.utils.IntegerCellEditor;

public class Page3PlaceConfigurationParametersWizardPage extends BaseWizardPage
		implements PropertyChangeListener {
	protected Tree placeTree;

	protected TreeViewer placeTreeViewer;

	protected boolean modifyListenersActive = true;

	protected EditorConfiguration configs[];

	protected int activeConfig = 0;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public Page3PlaceConfigurationParametersWizardPage(ISelection selection,
			Element net) {
		super("placeSettingsWizardPage", selection, net);
		setDescription("Configure simulator-specific place parameters.");
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 20;

		Label placeTableLabel = new Label(container, SWT.NULL);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		placeTableLabel.setLayoutData(gd);
		placeTableLabel.setText("Place Settings");

		placeTree = new Tree(container, SWT.BORDER | SWT.H_SCROLL
				| SWT.V_SCROLL);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 3;
		placeTree.setLayoutData(gd);
		placeTree.setLinesVisible(true);
		placeTree.setHeaderVisible(true);

		// Define the tables clumns.
		for (int i = 0; i < 8; i++) {
			TreeColumn column = new TreeColumn(placeTree, SWT.LEFT, i);
			// TIP: Don't forget to set the width. If not set it is set to
			// 0 and it will look as if the column didn't exist.
			if (i == 0) {
				column.setWidth(130);
			} else if (i == 7) {
				column.setWidth(180);
			} 
			else {
				column.setWidth(115);
			}			
		}

		// TIP: The Tree ist just a plain old treeview, if we want to
		// don anything special with it, it has to be done with a
		// treeviewer.
		placeTreeViewer = new TreeViewer(placeTree);

		// The content provider provides the tree-viewer with
		// the information needed (parents, children, ...)
		// One thing I didn't quite understand, is the
		// getElements method which seems to be redirected to
		// getChildren manually ... don't know why this is done
		// but I've seen it done this way everywhere.
		placeTreeViewer.setContentProvider(new ITreeContentProvider() {
			public Object[] getChildren(Object parentElement) {
				Object[] children = null;

				Element element = (Element) parentElement;
				if ("net".equals(element.getName())) {
					XPath xpathSelector = DocumentHelper
							.createXPath("places/place");
					List placeList = xpathSelector.selectNodes(element);
					children = new Object[placeList.size()];
					for (int i = 0; i < children.length; i++) {
						children[i] = placeList.get(i);
					}
				} else if ("place".equals(element.getName())) {
					Element placeMetaAttribute = getMetaAttribute(element);
					if (placeMetaAttribute != null) {
						int statsLevel = Integer.parseInt(placeMetaAttribute
								.attributeValue("statsLevel"));
						if (statsLevel >= 3) {
							XPath xpathSelector = DocumentHelper
									.createXPath("color-refs/color-ref");
							List colorRefList = xpathSelector
									.selectNodes(element);
							children = new Object[colorRefList.size()];
							for (int i = 0; i < children.length; i++) {
								if ("queueing-place".equals(element
										.attributeValue("type"))) {
									children[i] = colorRefList.get(i);
								} else {
									children[i] = new DepositorySettings(
											getMetaAttribute((Element) colorRefList
													.get(i)));
								}
							}
						}
					}
				} else if ("color-ref".equals(element.getName())
						&& "queueing-place".equals(element.getParent()
								.getParent().attributeValue("type"))) {
					children = new Object[2];
					children[0] = new DepositorySettings(
							getMetaAttribute(element));
					children[1] = new QueueSettings(getMetaAttribute(element));
				}
				return children;
			}

			public Object getParent(Object element) {
				Object parent = null;

				if (element instanceof Element) {
					Element childEelement = (Element) element;
					if ("place".equals(childEelement.getName())) {
						parent = childEelement.getParent().getParent();
					} else if ("color-ref".equals(childEelement.getName())) {
						parent = childEelement.getParent().getParent();
					}
				} else if (element instanceof DepositorySettings) {
					DepositorySettings castedElement = (DepositorySettings) element;
					if ("queueing-place".equals(castedElement.getParent()
							.getParent().getParent().attributeValue("type"))) {
						return castedElement.getParent();
					} else {
						return castedElement.getParent().getParent()
								.getParent();
					}
				} else if (element instanceof QueueSettings) {
					QueueSettings castedElement = (QueueSettings) element;
					return castedElement.getParent();
				}
				return parent;
			}

			public boolean hasChildren(Object parentElement) {
				if (parentElement instanceof Element) {
					Element element = (Element) parentElement;
					if ("net".equals(element.getName())) {
						XPath xpathSelector = DocumentHelper
								.createXPath("places/place");
						return !xpathSelector.selectNodes(element).isEmpty();
					} else if ("place".equals(element.getName())) {
						Element placeMetaAttribute = getMetaAttribute(element);
						if (placeMetaAttribute != null) {
							int statsLevel = Integer
									.parseInt(placeMetaAttribute
											.attributeValue("statsLevel"));
							if (statsLevel >= 3) {
								XPath xpathSelector = DocumentHelper
										.createXPath("color-refs/color-ref");
								return !xpathSelector.selectNodes(element)
										.isEmpty();
							}
						}
					} else if ("color-ref".equals(element.getName())
							&& "queueing-place".equals(element.getParent()
									.getParent().attributeValue("type"))) {
						return true;
					}
				}
				return false;
			}

			public Object[] getElements(Object inputElement) {
				return getChildren(inputElement);
			}

			public void dispose() {
				// 
			}

			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
			}
		});

		// TIP: It seems, that if the table has not defined any TreeColumns then
		// a plain LabelProvider will be used. Since, we don't provide an
		// instance of LabelProvider, a default one will be used and
		// the TableLableProvider is ignored without notice. Took me quite
		// a while to find that one out.
		placeTreeViewer.setLabelProvider(new ITableLabelProvider() {
			public String getColumnText(Object element, int columnIndex) {
				if (element instanceof Element) {
					Element castedElement = (Element) element;
					if ("place".equals(castedElement.getName())) {
						Element metaAttribute = getMetaAttribute(castedElement);
						if (metaAttribute != null) {
							if (columnIndex == 0) {
								return castedElement.attributeValue("name",
										"new place");
							} else if (1 == columnIndex) {
								return metaAttribute
										.attributeValue("statsLevel");
							}
						}
						return null;
					} else if ("color-ref".equals(castedElement.getName())) {
						if (columnIndex == 0) {
							XPath xpathSelector = DocumentHelper
									.createXPath("//color[@id = '"
											+ castedElement.attributeValue(
													"color-id", "") + "']");
							Element color = (Element) xpathSelector
									.selectSingleNode(castedElement);
							return color
									.attributeValue("name", "new color");
						}
					}
				}

				else if (element instanceof DepositorySettings) {
					DepositorySettings depositorySettings = (DepositorySettings) element;
					if (columnIndex == 0) {
						if ("queueing-place"
								.equals(depositorySettings.getParent()
										.getParent().getParent().getParent()
										.getParent().attributeValue("type"))) {
							return "depository";
						} else {
							Element castedElement = depositorySettings
									.getParent().getParent().getParent();
							XPath xpathSelector = DocumentHelper
									.createXPath("//color[@id = '"
											+ castedElement.attributeValue(
													"color-id", "") + "']");
							Element color = (Element) xpathSelector
									.selectSingleNode(castedElement);
							return color
									.attributeValue("name", "new color");
						}
					}
					DepositorySettings settings = (DepositorySettings) element;
					return settings.getAttributeValues()[columnIndex];
				}

				else if (element instanceof QueueSettings) {
					if (columnIndex == 0) {
						return "queue";
					}
					QueueSettings settings = (QueueSettings) element;
					return settings.getAttributeValues()[columnIndex];
				}
				return null;
			}

			public Image getColumnImage(Object element, int columnIndex) {
				return null;
			}

			public void addListener(ILabelProviderListener listener) {
			}

			public void removeListener(ILabelProviderListener listener) {
			}

			public void dispose() {
			}

			public boolean isLabelProperty(Object element, String property) {
				return true;
			}
		});

		// A CellModifier is responsible for editing a cells
		// conent. At first canModify is used to check, if
		// a cell can be modified. Then getValue is used to
		// get the input-object for the CellEditor. Abter
		// eiting the modify method deals with updating
		// the tables model.
		placeTreeViewer.setCellModifier(new ICellModifier() {
			public boolean canModify(Object element, String property) {
				if (element instanceof Element) {
					Element castedElement = (Element) element;
					if ("place".equals(castedElement.getName())) {
						// The name property is the only one not editalbe.
						if ("statsLevel".equals(property)) {
							return true;
						}
					}
				} else if ((element instanceof DepositorySettings)
						|| (element instanceof QueueSettings)) {
					int propertyIndex = getPropertyIndex(property);
					if ((propertyIndex != -1)
							&& configs[activeConfig].cellEditors[propertyIndex] != null) {
						return true;
					}
				}
				return false;
			}

			public Object getValue(Object element, String property) {
				if (element instanceof Element) {
					Element castedElement = (Element) element;
					if ("place".equals(castedElement.getName())) {
						// The name property is the only one not editalbe.
						if ("statsLevel".equals(property)) {
							return new Integer(getMetaAttribute(castedElement)
									.attributeValue("statsLevel"));
						}
					}
				} else if (element instanceof DepositorySettings) {
					DepositorySettings settings = (DepositorySettings) element;
					int propertyIndex = getPropertyIndex(property);
					if ((propertyIndex != -1)
							&& (configs[activeConfig].cellEditors[propertyIndex] != null)
							&& (settings.getAttributeValues()[propertyIndex] != null)) {
						if (configs[activeConfig].cellEditors[propertyIndex] instanceof DoubleCellEditor) {
							return new Double(
									settings.getAttributeValues()[propertyIndex]);
						} else if (configs[activeConfig].cellEditors[propertyIndex] instanceof IntegerCellEditor) {
							return new Integer(
									settings.getAttributeValues()[propertyIndex]);
						}
					}
				} else if (element instanceof QueueSettings) {
					QueueSettings settings = (QueueSettings) element;
					int propertyIndex = getPropertyIndex(property);
					if ((propertyIndex != -1)
							&& (configs[activeConfig].cellEditors[propertyIndex] != null)
							&& (settings.getAttributeValues()[propertyIndex] != null)) {
						if (configs[activeConfig].cellEditors[propertyIndex] instanceof DoubleCellEditor) {
							return new Double(
									settings.getAttributeValues()[propertyIndex]);
						} else if (configs[activeConfig].cellEditors[propertyIndex] instanceof IntegerCellEditor) {
							return new Integer(
									settings.getAttributeValues()[propertyIndex]);
						}
					}
				}
				return null;
			}

			public void modify(Object element, String property, Object value) {
				if (element instanceof TreeItem) {
					TreeItem treeItem = (TreeItem) element;
					Object treeItemData = treeItem.getData();
					if (treeItemData instanceof Element) {
						Element place = (Element) treeItemData;
						Integer iVal = (Integer) value;
						if ((0 <= iVal) && (iVal <= 5)) {
							DocumentManager.setAttribute(
									getMetaAttribute(place), property,
									((Integer) value).toString());

							XPath xpathSelector = DocumentHelper
									.createXPath("color-refs/color-ref");
							Iterator colorRefIterator = xpathSelector
									.selectNodes(place).iterator();
							while (colorRefIterator.hasNext()) {
								Element colorRef = (Element) colorRefIterator
										.next();

								// Make sure all meta-attributes exist.
								if (iVal >= 3) {
									xpathSelector = DocumentHelper
											.createXPath("meta-attributes/meta-attribute[@name = 'sim-qpn' and @configuration-name='"
													+ Page1ConfigurationSelectionWizardPage.activeConfiguration
													+ "']");
									Element metaAttribute = (Element) xpathSelector
											.selectSingleNode(colorRef);
									if (metaAttribute == null) {
										// get or create a meta-attribute.
										metaAttribute = getMetaAttribute(colorRef);
										initializeMetaAttribute(metaAttribute, activeConfig + 1);
									}
								}
								// Remove eventually exisiting meta-attributes
								// for each color
								else {
									xpathSelector = DocumentHelper
											.createXPath("meta-attributes/meta-attribute[@name = 'sim-qpn' and @configuration-name='"
													+ Page1ConfigurationSelectionWizardPage.activeConfiguration
													+ "']");
									Element metaAttribute = (Element) xpathSelector
											.selectSingleNode(colorRef);
									if (metaAttribute != null) {
										colorRef.element("meta-attributes").remove(metaAttribute);
									}
								}
							}

							placeTreeViewer.setInput(net);
						}
					} else if (treeItemData instanceof DepositorySettings) {
						DepositorySettings settings = (DepositorySettings) treeItemData;
						Element castedElement = settings.getParent();
						int propertyIndex = getPropertyIndex(property);
						if (propertyIndex != -1) {
							String msg = settings.getMsg(propertyIndex, value);
							if (msg == null) {
								String attributeName = settings
										.getAttributeNames()[propertyIndex];
								if (value instanceof Double) {
									DocumentManager.setAttribute(castedElement,
											attributeName, ((Double) value)
													.toString());
									setErrorMessage(null);
								} else if (value instanceof Integer) {
									if (activeConfig == 2) {
										int minObsrv = 0;
										int maxObsrv = 0;
										if (propertyIndex == 2) {
											minObsrv = ((Integer) value)
													.intValue();
											maxObsrv = Integer
													.parseInt(castedElement
															.attributeValue(
																	settings
																			.getAttributeNames()[3]));
										} else {
											minObsrv = Integer
													.parseInt(castedElement
															.attributeValue(
																	settings
																			.getAttributeNames()[2]));
											maxObsrv = ((Integer) value)
													.intValue();
										}

										if (maxObsrv < minObsrv) {
											setErrorMessage("maxObsrv must be greater than or equal to minOsrv");
										} else {
											DocumentManager.setAttribute(
													castedElement,
													attributeName,
													((Integer) value)
															.toString());
											setErrorMessage(null);
										}
									} else {
										DocumentManager.setAttribute(
												castedElement, attributeName,
												((Integer) value).toString());
										setErrorMessage(null);
									}
								}
							} else {
								setErrorMessage(msg);
							}
						}
					} else if (treeItemData instanceof QueueSettings) {
						QueueSettings settings = (QueueSettings) treeItemData;
						Element castedElement = settings.getParent();
						int propertyIndex = getPropertyIndex(property);
						if (propertyIndex != -1) {
							String msg = settings.getMsg(propertyIndex, value);
							if (msg == null) {
								String attributeName = settings
										.getAttributeNames()[propertyIndex];
								if (value instanceof Double) {
									DocumentManager.setAttribute(castedElement,
											attributeName, ((Double) value)
													.toString());
									setErrorMessage(null);
								} else if (value instanceof Integer) {
									if (activeConfig == 2) {
										int minObsrv = 0;
										int maxObsrv = 0;
										if (propertyIndex == 2) {
											minObsrv = ((Integer) value)
													.intValue();
											maxObsrv = Integer
													.parseInt(castedElement
															.attributeValue(
																	settings
																			.getAttributeNames()[3]));
										} else {
											minObsrv = Integer
													.parseInt(castedElement
															.attributeValue(
																	settings
																			.getAttributeNames()[2]));
											maxObsrv = ((Integer) value)
													.intValue();
										}

										if (maxObsrv < minObsrv) {
											setErrorMessage("maxObsrv must be greater than or equal to minOsrv");
										} else {
											DocumentManager.setAttribute(
													castedElement,
													attributeName,
													((Integer) value)
															.toString());
											setErrorMessage(null);
										}
									} else {
										DocumentManager.setAttribute(
												castedElement, attributeName,
												((Integer) value).toString());
										setErrorMessage(null);
									}
								}
							} else {
								setErrorMessage(msg);
							}
						}
					}
					// After modifying the input, make the treeViewer
					// update its content.
					placeTreeViewer.refresh();
				}
			}
		});

		configs = new EditorConfiguration[3];
		configs[0] = new EditorConfiguration();
		configs[0].attributeNames = new String[] { "Name", "statsLevel",
				"signLev", "reqAbsPrc", "reqRelPrc", "batchSize", "minBatches",
				"numBMeansCorlTested" };
		configs[0].cellEditors = new CellEditor[] { null,
				new IntegerCellEditor(placeTreeViewer.getTree()),
				new DoubleCellEditor(placeTreeViewer.getTree()),
				new DoubleCellEditor(placeTreeViewer.getTree()),
				new DoubleCellEditor(placeTreeViewer.getTree()),
				new IntegerCellEditor(placeTreeViewer.getTree()),
				new IntegerCellEditor(placeTreeViewer.getTree()),
				new IntegerCellEditor(placeTreeViewer.getTree()) };

		configs[1] = new EditorConfiguration();
		configs[1].attributeNames = new String[] { "Name", "statsLevel",
				"signLevAvgST", "", "", "", "", "" };
		configs[1].cellEditors = new CellEditor[] { null,
				new IntegerCellEditor(placeTreeViewer.getTree()),
				new DoubleCellEditor(placeTreeViewer.getTree()), null, null,
				null, null, null };

		configs[2] = new EditorConfiguration();
		configs[2].attributeNames = new String[] { "Name", "statsLevel",
				"minObsrv", "maxObsrv", "", "", "", "" };
		configs[2].cellEditors = new CellEditor[] { null,
				new IntegerCellEditor(placeTreeViewer.getTree()),
				new IntegerCellEditor(placeTreeViewer.getTree()),
				new IntegerCellEditor(placeTreeViewer.getTree()), null, null,
				null, null };

		placeTreeViewer
				.setColumnProperties(configs[activeConfig].attributeNames);

		placeTreeViewer.setInput(net);

		updateDialog();
		setControl(container);
	}

	protected void updateDialog() {
		Element metaAttribute = getMetaAttribute();
		if (metaAttribute != null) {
			// Find out which conifg is the correct one.
			if ("1".equals(metaAttribute.attributeValue("scenario"))) {
				activeConfig = 0;
				setTitle("Configuration Parameters for Batch Means Method");
			} else if ("2".equals(metaAttribute.attributeValue("scenario"))) {
				activeConfig = 1;
				setTitle("Configuration Parameters for Replication/Deletion Method");
			} else {
				activeConfig = 2;
				setTitle("Configuration Parameters for Method of Welch");
			}

			// Update the treeViewer.
			placeTreeViewer.setCellEditors(configs[activeConfig].cellEditors);
			// Update the treeViewers column labels.
			TreeColumn[] columns = placeTree.getColumns();
			for (int i = 0; i < columns.length; i++) {
				columns[i].setText(configs[activeConfig].attributeNames[i]);
			}
			// Update the property names.
			placeTreeViewer
					.setColumnProperties(configs[activeConfig].attributeNames);

			// Redraw everything.
			placeTreeViewer.refresh();
		}
	}

	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
	}

	protected int getPropertyIndex(String property) {
		if (property != null) {
			for (int i = 0; i < configs[activeConfig].attributeNames.length; i++) {
				if (property.equals(configs[activeConfig].attributeNames[i])) {
					return i;
				}
			}
		}
		return -1;
	}

	// ///////////////////////////////////////////////////
	// Utility Classes

	class EditorConfiguration {
		String[] attributeNames;

		CellEditor[] cellEditors;
	}

	class DepositorySettings {
		protected Element parent;

		public DepositorySettings(Element parent) {
			this.parent = parent;
		}

		public Element getParent() {
			return parent;
		}

		public String[] getAttributeNames() {
			String[] attributeNames = null;
			if (Page3PlaceConfigurationParametersWizardPage.this.activeConfig == 0) {
				attributeNames = new String[8];
				attributeNames[0] = null;
				attributeNames[1] = null;
				attributeNames[2] = "signLev";
				attributeNames[3] = "reqAbsPrc";
				attributeNames[4] = "reqRelPrc";
				attributeNames[5] = "batchSize";
				attributeNames[6] = "minBatches";
				attributeNames[7] = "numBMeansCorlTested";
			} else if (Page3PlaceConfigurationParametersWizardPage.this.activeConfig == 1) {
				attributeNames = new String[8];
				attributeNames[0] = null;
				attributeNames[1] = null;
				attributeNames[2] = "signLevAvgST";
				attributeNames[3] = null;
				attributeNames[4] = null;
				attributeNames[5] = null;
				attributeNames[6] = null;
				attributeNames[7] = null;
			} else {
				attributeNames = new String[8];
				attributeNames[0] = null;
				attributeNames[1] = null;
				attributeNames[2] = "minObsrv";
				attributeNames[3] = "maxObsrv";
				attributeNames[4] = null;
				attributeNames[5] = null;
				attributeNames[6] = null;
				attributeNames[7] = null;
			}
			return attributeNames;
		}

		public String[] getAttributeValues() {
			String[] attributeValues = null;
			if (Page3PlaceConfigurationParametersWizardPage.this.activeConfig == 0) {
				attributeValues = new String[8];
				attributeValues[0] = null;
				attributeValues[1] = null;
				attributeValues[2] = parent.attributeValue("signLev");
				attributeValues[3] = parent.attributeValue("reqAbsPrc");
				attributeValues[4] = parent.attributeValue("reqRelPrc");
				attributeValues[5] = parent.attributeValue("batchSize");
				attributeValues[6] = parent.attributeValue("minBatches");
				attributeValues[7] = parent
						.attributeValue("numBMeansCorlTested");
			} else if (Page3PlaceConfigurationParametersWizardPage.this.activeConfig == 1) {
				attributeValues = new String[8];
				attributeValues[0] = null;
				attributeValues[1] = null;
				attributeValues[2] = parent.attributeValue("signLevAvgST");
				attributeValues[3] = null;
				attributeValues[4] = null;
				attributeValues[5] = null;
				attributeValues[6] = null;
				attributeValues[7] = null;
			} else {
				attributeValues = new String[8];
				attributeValues[0] = null;
				attributeValues[1] = null;
				attributeValues[2] = parent.attributeValue("minObsrv");
				attributeValues[3] = parent.attributeValue("maxObsrv");
				attributeValues[4] = null;
				attributeValues[5] = null;
				attributeValues[6] = null;
				attributeValues[7] = null;
			}
			return attributeValues;
		}

		public String getMsg(int index, Object obj) {
			Double dVal;
			Integer iVal;
			if (Page3PlaceConfigurationParametersWizardPage.this.activeConfig == 0) {
				switch (index) {
				case 2:
					dVal = (Double) obj;
					if (dVal.doubleValue() <= 0) {
						return "signLev must be a positive number";
					}
					break;
				case 3:
					dVal = (Double) obj;
					if (dVal.doubleValue() <= 0) {
						return "reqAbsPrc must be a positive number";
					}
					break;
				case 4:
					dVal = (Double) obj;
					if (dVal.doubleValue() <= 0) {
						return "reqRelPrc must be a positive number";
					}
					break;
				case 5:
					iVal = (Integer) obj;
					if (iVal.intValue() < 10) {
						return "batchSize must be a positive integer greater than or equal to 10";
					}
					break;
				case 6:
					iVal = (Integer) obj;
					if (iVal.intValue() < 0) {
						return "minBatches must be a non-negative integer";
					}
					break;
				case 7:
					iVal = (Integer) obj;
					if ((iVal.intValue() < 0) || (iVal.intValue() % 2 == 1)) {
						return "numBMeansCorlTested must be a non-negative even number";
					}
					break;
				}
			} else if (Page3PlaceConfigurationParametersWizardPage.this.activeConfig == 1) {
				switch (index) {
				case 2:
					dVal = (Double) obj;
					if (dVal.doubleValue() <= 0) {
						return "signLevAvgST must be a positive number";
					}
				}
			} else {
				switch (index) {
				case 2:
					iVal = (Integer) obj;
					if (iVal.intValue() < 0) {
						return "minObsrv must be a non-negative integer";
					}
				case 3:
					iVal = (Integer) obj;
					if (iVal.intValue() < 0) {
						return "maxObsrv must be a non-negative integer";
					}
					break;
				}
			}
			return null;
		}
	}

	class QueueSettings {
		protected Element parent;

		public QueueSettings(Element parent) {
			this.parent = parent;
		}

		public Element getParent() {
			return parent;
		}

		public String[] getAttributeNames() {
			String[] attributeNames = null;
			if (Page3PlaceConfigurationParametersWizardPage.this.activeConfig == 0) {
				attributeNames = new String[8];
				attributeNames[0] = null;
				attributeNames[1] = null;
				attributeNames[2] = "queueSignLev";
				attributeNames[3] = "queueReqAbsPrc";
				attributeNames[4] = "queueReqRelPrc";
				attributeNames[5] = "queueBatchSize";
				attributeNames[6] = "queueMinBatches";
				attributeNames[7] = "queueNumBMeansCorlTested";
			} else if (Page3PlaceConfigurationParametersWizardPage.this.activeConfig == 1) {
				attributeNames = new String[8];
				attributeNames[0] = null;
				attributeNames[1] = null;
				attributeNames[2] = "queueSignLevAvgST";
				attributeNames[3] = null;
				attributeNames[4] = null;
				attributeNames[5] = null;
				attributeNames[6] = null;
				attributeNames[7] = null;
			} else {
				attributeNames = new String[8];
				attributeNames[0] = null;
				attributeNames[1] = null;
				attributeNames[2] = "queueMinObsrv";
				attributeNames[3] = "queueMaxObsrv";
				attributeNames[4] = null;
				attributeNames[5] = null;
				attributeNames[6] = null;
				attributeNames[7] = null;
			}
			return attributeNames;
		}

		public String[] getAttributeValues() {
			String[] attributeValues = null;
			if (Page3PlaceConfigurationParametersWizardPage.this.activeConfig == 0) {
				attributeValues = new String[8];
				attributeValues[0] = null;
				attributeValues[1] = null;
				attributeValues[2] = parent.attributeValue("queueSignLev");
				attributeValues[3] = parent.attributeValue("queueReqAbsPrc");
				attributeValues[4] = parent.attributeValue("queueReqRelPrc");
				attributeValues[5] = parent.attributeValue("queueBatchSize");
				attributeValues[6] = parent.attributeValue("queueMinBatches");
				attributeValues[7] = parent
						.attributeValue("queueNumBMeansCorlTested");
			} else if (Page3PlaceConfigurationParametersWizardPage.this.activeConfig == 1) {
				attributeValues = new String[8];
				attributeValues[0] = null;
				attributeValues[1] = null;
				attributeValues[2] = parent.attributeValue("queueSignLevAvgST");
				attributeValues[3] = null;
				attributeValues[4] = null;
				attributeValues[5] = null;
				attributeValues[6] = null;
				attributeValues[7] = null;
			} else {
				attributeValues = new String[8];
				attributeValues[0] = null;
				attributeValues[1] = null;
				attributeValues[2] = parent.attributeValue("queueMinObsrv");
				attributeValues[3] = parent.attributeValue("queueMaxObsrv");
				attributeValues[4] = null;
				attributeValues[5] = null;
				attributeValues[6] = null;
				attributeValues[7] = null;
			}
			return attributeValues;
		}

		public String getMsg(int index, Object obj) {
			Double dVal;
			Integer iVal;
			if (Page3PlaceConfigurationParametersWizardPage.this.activeConfig == 0) {
				switch (index) {
				case 2:
					dVal = (Double) obj;
					if (dVal.doubleValue() <= 0) {
						return "signLev must be a positive number";
					}
					break;
				case 3:
					dVal = (Double) obj;
					if (dVal.doubleValue() <= 0) {
						return "reqAbsPrc must be a positive number";
					}
					break;
				case 4:
					dVal = (Double) obj;
					if (dVal.doubleValue() <= 0) {
						return "reqRelPrc must be a positive number";
					}
					break;
				case 5:
					iVal = (Integer) obj;
					if (iVal.intValue() < 10) {
						return "batchSize must be a positive integer greater than or equal to 10";
					}
					break;
				case 6:
					iVal = (Integer) obj;
					if (iVal.intValue() < 0) {
						return "minBatches must be a non-negative integer";
					}
					break;
				case 7:
					iVal = (Integer) obj;
					if ((iVal.intValue() < 0) || (iVal.intValue() % 2 == 1)) {
						return "numBMeansCorlTested must be a non-negative even number";
					}
					break;
				}
			} else if (Page3PlaceConfigurationParametersWizardPage.this.activeConfig == 1) {
				switch (index) {
				case 2:
					dVal = (Double) obj;
					if (dVal.doubleValue() <= 0) {
						return "queueSignLevAvgST must be a positive number";
					}
					break;
				}
			} else {
				switch (index) {
				case 2:
					iVal = (Integer) obj;
					if (iVal.intValue() < 0) {
						return "minObsrv must be a non-negative integer";
					}
					break;
				case 3:
					iVal = (Integer) obj;
					if (iVal.intValue() < 0) {
						return "maxObsrv must be a non-negative integer";
					}
					break;
				}
			}
			return null;
		}
	}
	
	public static void initializeMetaAttribute(Element metaAttribute, int scenaro) {
		switch (scenaro) {
		case 1:
			metaAttribute.addAttribute(
					"signLev", "0.05");
			metaAttribute.addAttribute(
					"reqAbsPrc", "50");
			metaAttribute.addAttribute(
					"reqRelPrc", "0.05");
			metaAttribute.addAttribute(
					"batchSize", "200");
			metaAttribute.addAttribute(
					"minBatches", "60");
			metaAttribute
					.addAttribute(
							"numBMeansCorlTested",
							"50");
			if ("queueing-place".equals(metaAttribute.getParent().getParent().getParent().getParent()
					.attributeValue("type"))) {
				metaAttribute.addAttribute(
						"queueSignLev", "0.05");
				metaAttribute.addAttribute(
						"queueReqAbsPrc", "50");
				metaAttribute.addAttribute(
						"queueReqRelPrc",
						"0.05");
				metaAttribute
						.addAttribute(
								"queueBatchSize",
								"200");
				metaAttribute
						.addAttribute(
								"queueMinBatches",
								"60");
				metaAttribute
						.addAttribute(
								"queueNumBMeansCorlTested",
								"50");
			}
			break;
		case 2:
			metaAttribute.addAttribute(
					"signLevAvgST", "0.05");
			if ("queueing-place".equals(metaAttribute.getParent().getParent().getParent().getParent()
					.attributeValue("type"))) {
				metaAttribute.addAttribute(
						"queueSignLevAvgST",
						"0.05");
			}
			break;
		case 3:
			metaAttribute.addAttribute(
					"minObsrv", "500");
			metaAttribute.addAttribute(
					"maxObsrv", "10000");
			if ("queueing-place".equals(metaAttribute.getParent().getParent().getParent().getParent()
					.attributeValue("type"))) {
				metaAttribute.addAttribute(
						"queueMinObsrv", "500");
				metaAttribute.addAttribute(
						"queueMaxObsrv",
						"10000");
			}
			break;
		}
	}
}
