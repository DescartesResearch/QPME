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
 * Contributor(s):  Simon Spinner 
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
 *  2010/04/17	Simon Spinner     Make numBuckets and bucketSize configurable. 
 */
package de.tud.cs.simqpn.ui.wizard.page;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;

import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.model.NetHelper;
import de.tud.cs.qpe.model.PlaceHelper;
import de.tud.cs.qpe.model.ProbeHelper;
import de.tud.cs.qpe.model.SubnetHelper;
import de.tud.cs.qpe.utils.CellValidators;
import de.tud.cs.qpe.utils.ValidatingEditingSupport;
import de.tud.cs.simqpn.ui.model.Configuration;

public class Page3PlaceConfigurationParametersWizardPage extends WizardPage {
	protected Tree placeTree;

	protected TreeViewer placeTreeViewer;

	protected boolean modifyListenersActive = true;
	
	private Configuration configuration;
	
	private Element net;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public Page3PlaceConfigurationParametersWizardPage(Element net) {
		super("placeSettingsWizardPage");
		setTitle("Configure simulator-specific place parameters.");
		this.net = net;
	}
	
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
		TreeNode[] nodes = initSettingsTree(net);
		placeTreeViewer.setColumnProperties(configuration.getColumnTitles());
		String[] titles = configuration.getColumnTitles();
		for (int i = 0; i < titles.length; i++) {
			placeTree.getColumn(i).setText(titles[i]);
		}
		placeTreeViewer.setInput(nodes);
		placeTreeViewer.refresh();
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
		
		TableLayout tableLayout = new TableLayout();
		for (int i = 0; i < Configuration.MAX_NUM_ATTRIBUTES; i++) {
			if (i == 0) {
				tableLayout.addColumnData(new ColumnWeightData(1, 130));
			} else if (i == 7) {
				tableLayout.addColumnData(new ColumnWeightData(1, 150));
			} else {
				tableLayout.addColumnData(new ColumnWeightData(1, 85));
			}			
		}
		

		placeTree = new Tree(container, SWT.FULL_SELECTION | SWT.BORDER | SWT.H_SCROLL
				| SWT.V_SCROLL);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 3;
		placeTree.setLayoutData(gd);
		placeTree.setLinesVisible(true);
		placeTree.setHeaderVisible(true);
		placeTree.setLayout(tableLayout);

		// TIP: The Tree ist just a plain old treeview, if we want to
		// don anything special with it, it has to be done with a
		// treeviewer.
		placeTreeViewer = new TreeViewer(placeTree);
		placeTreeViewer.setContentProvider(new TreeNodeContentProvider());
		
		for (int i = 0; i < Configuration.MAX_NUM_ATTRIBUTES; i++) {
			TreeViewerColumn col = new TreeViewerColumn(placeTreeViewer, SWT.LEFT);
			col.setLabelProvider(new SettingsLabelProvider(i));
			col.setEditingSupport(new SettingsEditingSupport(col.getViewer(), i));
		}
		
		setControl(container);
	}
	
	private TreeNode[] initSettingsTree(Element net) {
		ArrayList<TreeNode> nodes = new ArrayList<TreeNode>();
		List<Element>  places = NetHelper.listPlaces(net);
		for (Element place : places) {
			initPlaceSettings(nodes, place);
		}		
		
		List<Element> probes = NetHelper.listProbes(net);
		for (Element probe : probes) {
			initProbeSettings(nodes, probe);
		}
		
		return nodes.toArray(new TreeNode[nodes.size()]);
	}

	private void initProbeSettings(ArrayList<TreeNode> nodes, Element probe) {
		Element probeMetaAttribute = NetHelper.getMetadata(probe, configuration.getName());
		if (probeMetaAttribute != null) {
			Settings probeSettings = new Settings(probe.attributeValue("name"), SettingsType.PROBE, probeMetaAttribute);
			
			TreeNode probeNode = new TreeNode(probeSettings);				
			int statsLevel = Integer.parseInt(probeMetaAttribute
					.attributeValue("statsLevel"));
			
			if (statsLevel >= 3) {
				List<Element> colorRefs = ProbeHelper.listColorReferences(probe);
				ArrayList<TreeNode> colorRefNodes = new ArrayList<TreeNode>();
				
				for (Element colorRef : colorRefs) {
					initColorRef(colorRefNodes, colorRef);
				}					
				probeNode.setChildren(colorRefNodes.toArray(new TreeNode[colorRefNodes.size()]));
			} else {
				// Sometimes there might be some orphaned meta attribute elements, 
				// which should be removed here to avoid inconsistencies.
				removeMetaAttributeElements(probeSettings);
			}
			nodes.add(probeNode);
		}
	}

	private void initColorRef(ArrayList<TreeNode> colorRefNodes, Element colorRef) {
		Element colorRefMetaAttribute = NetHelper.getMetadata(colorRef, configuration.getName());
		if (colorRefMetaAttribute != null) {
			Element color = NetHelper.getColorByReference(colorRef);
			String name = NetHelper.getFullyQualifiedName(color);
			Settings colorRefSettings = new Settings(name, SettingsType.COLOR_REF, colorRefMetaAttribute);
			TreeNode colorRefNode = new TreeNode(colorRefSettings);
			colorRefNodes.add(colorRefNode);
		}
	}
	
	private void initCompoundColorRef(ArrayList<TreeNode> colorRefNodes, Element colorRef) {
		Element colorRefMetaAttribute = NetHelper.getMetadata(colorRef, configuration.getName());
		if (colorRefMetaAttribute != null) {
			Element color = NetHelper.getColorByReference(colorRef);
			String name = NetHelper.getFullyQualifiedName(color);
			Settings colorRefSettings = new Settings(name, SettingsType.COMPOSITE_COLOR_REF, colorRefMetaAttribute);
			TreeNode colorRefNode = new TreeNode(colorRefSettings);
			
			Settings depositorySettings = new Settings("depository", SettingsType.DEPOSITORY, colorRefMetaAttribute);
			Settings queueSettings = new Settings("queue", SettingsType.QUEUE, colorRefMetaAttribute);
			
			colorRefNode.setChildren(new TreeNode[] { new TreeNode(queueSettings), new TreeNode(depositorySettings) });
			
			colorRefNodes.add(colorRefNode);
		}
	}

	private void initPlaceSettings(ArrayList<TreeNode> nodes, Element place) {
		Element placeMetaAttribute = NetHelper.getMetadata(place, configuration.getName());
		if (placeMetaAttribute != null) {
			SettingsType type = SettingsType.PLACE;
			if ("subnet-place".equals(place.attributeValue("type"))) {
				type = SettingsType.SUBNET_PLACE;
			} else if ("queueing-place".equals(place.attributeValue("type"))) {
				type = SettingsType.QUEUEING_PLACE;
			}
			Settings placeSettings = new Settings(place.attributeValue("name"), type, placeMetaAttribute);			
			TreeNode placeNode = new TreeNode(placeSettings);				
	
			if(type == SettingsType.SUBNET_PLACE) {				
				List<Element> subnetPlaces = SubnetHelper.listSubnetPlaces(place);
				ArrayList<TreeNode> subnetPlaceNodes = new ArrayList<TreeNode>();
				for (Element subnetPlace : subnetPlaces) {
					initPlaceSettings(subnetPlaceNodes, subnetPlace);
				}
				placeNode.setChildren(subnetPlaceNodes.toArray(new TreeNode[subnetPlaces.size()]));				
			} else {
				int statsLevel = Integer.parseInt(placeMetaAttribute
						.attributeValue("statsLevel"));
				if (statsLevel >= 3) {
					List<Element> colorRefs = PlaceHelper.listColorReferences(place);
					ArrayList<TreeNode> colorRefNodes = new ArrayList<TreeNode>();
					
					for (Element colorRef : colorRefs) {
						if (type != SettingsType.QUEUEING_PLACE) {
							initColorRef(colorRefNodes, colorRef);
						} else {
							initCompoundColorRef(colorRefNodes, colorRef);
						}
					}					
					placeNode.setChildren(colorRefNodes.toArray(new TreeNode[colorRefNodes.size()]));
				} else {
					// Sometimes there might be some orphaned meta attribute elements, 
					// which should be removed here to avoid inconsistencies.
					removeMetaAttributeElements(placeSettings);
				}
			}				
			nodes.add(placeNode);
		}
	}
	
	private void removeMetaAttributeElements(Settings settings) {
		List<Element> colorRefs = PlaceHelper.listColorReferences(settings.metadata.getParent().getParent());
		for (Element ref : colorRefs) {
			Element m = NetHelper.getMetadata(ref, configuration.getName());
			if (m != null) {
				DocumentManager.removeElement(m);
			}
		}
	}

	// ///////////////////////////////////////////////////
	// Utility Classes
	
	private enum SettingsType {
		PLACE, SUBNET_PLACE, QUEUEING_PLACE, PROBE, COLOR_REF, QUEUE, DEPOSITORY, COMPOSITE_COLOR_REF
	}
	
	private class Settings {
		public String name;
		public SettingsType type;
		public Element metadata;
		
		public Settings(String name, SettingsType type, Element metadata) {
			this.name = name;
			this.type = type;
			this.metadata = metadata;
		}
	}
	
	private class SettingsLabelProvider extends CellLabelProvider {
		
		private int colIdx;
		
		public SettingsLabelProvider(int colIdx) {
			this.colIdx = colIdx;
		}
		
		@Override
		public void update(ViewerCell cell) {
			TreeNode node = (TreeNode)cell.getElement();
			Settings settings = (Settings)node.getValue();
			
			if (colIdx == 0) {
				cell.setText(settings.name);
				return;
			}
			
			switch(settings.type) {
			case PLACE:
			case QUEUEING_PLACE:
			case PROBE:
				if (colIdx == 1) {
					cell.setText(settings.metadata.attributeValue("statsLevel"));
				} else {
					cell.setText("");
				}
				break;
			case SUBNET_PLACE:			
			case COMPOSITE_COLOR_REF:
				cell.setText("");
				break;
			case COLOR_REF:
			case DEPOSITORY:
			{
				String attrName = configuration.getAttributeName(colIdx);
				if (attrName != null) {
					cell.setText(settings.metadata.attributeValue(attrName, ""));
				} else {
					cell.setText("");
				}
				break;
			}
			case QUEUE:
			{
				String attrName = configuration.getQueueAttributeName(colIdx);
				if (attrName != null) {
					cell.setText(settings.metadata.attributeValue(attrName, ""));
				} else {
					cell.setText("");
				}
				break;
			}
			}
		}
		
	}
	
	private class SettingsEditingSupport extends ValidatingEditingSupport {
		
		private int colIdx;
		
		public SettingsEditingSupport(ColumnViewer viewer, int colIdx) {
			super(viewer);
			this.colIdx = colIdx;
		}
		
		@Override
		protected CellEditor createCellEditor(Composite parent) {
			return new TextCellEditor(parent);
		}

		@Override
		protected Object getValue(Object element) {
			TreeNode node = (TreeNode)element;
			Settings settings = (Settings)node.getValue();
			
			switch(settings.type) {
			case PROBE:
			case PLACE:
			case QUEUEING_PLACE:
				if (colIdx == 1) {
					return settings.metadata.attributeValue("statsLevel");
				}
			case COLOR_REF:
			case DEPOSITORY:
				if (colIdx > 1) {
					String name = configuration.getAttributeName(colIdx);
					if (name != null) {
						return settings.metadata.attributeValue(name);
					}
				}
				return null;
			case QUEUE:
				if (colIdx > 1) {
					String name = configuration.getQueueAttributeName(colIdx);
					if (name != null) {
						return settings.metadata.attributeValue(name);
					}
				}
				return null;
			}
			return null;
		}

		@Override
		protected void setValue(Object element, Object value) {
			if (value == null)
				return;
			
			TreeNode node = (TreeNode)element;
			Settings settings = (Settings)node.getValue();

			switch(settings.type) {
			case PROBE:
				if (colIdx == 1) {
					int statsLevel = Integer.parseInt((String)value);
					DocumentManager.setAttribute(settings.metadata, "statsLevel", (String)value);
					if (statsLevel < 3) {
						node.setChildren(null);
						removeMetaAttributeElements(settings);
					} else {
						List<Element> colorRefs = ProbeHelper.listColorReferences(settings.metadata.getParent().getParent());
						ArrayList<TreeNode> colorRefNodes = new ArrayList<TreeNode>();
						for (Element ref : colorRefs) {
							Element m = NetHelper.getMetadata(ref, configuration.getName());
							if (m == null) {
								m = NetHelper.createMetadata(ref, configuration.getName());
							}
							configuration.initColorRefDepositoryMetadata(m);
							initColorRef(colorRefNodes, ref);
						}
						node.setChildren(colorRefNodes.toArray(new TreeNode[colorRefNodes.size()]));
					}
				}
				break;
			case PLACE:
			case QUEUEING_PLACE:
				if (colIdx == 1) {
					int statsLevel = Integer.parseInt((String)value);
					DocumentManager.setAttribute(settings.metadata, "statsLevel", (String)value);
					if (statsLevel < 3) {
						node.setChildren(null);
						removeMetaAttributeElements(settings);
					} else {
						List<Element> colorRefs = PlaceHelper.listColorReferences(settings.metadata.getParent().getParent());
						ArrayList<TreeNode> colorRefNodes = new ArrayList<TreeNode>();
						for (Element ref : colorRefs) {
							Element m = NetHelper.getMetadata(ref, configuration.getName());
							if (m == null) {
								m = NetHelper.createMetadata(ref, configuration.getName());
							}
							configuration.initColorRefDepositoryMetadata(m);
							if (settings.type == SettingsType.QUEUEING_PLACE) {
								configuration.initColorRefQueueMetadata(m);
								initCompoundColorRef(colorRefNodes, ref);
							} else {
								initColorRef(colorRefNodes, ref);
								configuration.initColorRefQueueMetadata(m);
							}
						}
						node.setChildren(colorRefNodes.toArray(new TreeNode[colorRefNodes.size()]));
					}
				}
				break;
			case SUBNET_PLACE:
			case COMPOSITE_COLOR_REF:
				break;
			case COLOR_REF:
			case DEPOSITORY:
				if (colIdx > 1) {
					String attrName = configuration.getAttributeName(colIdx);
					if (attrName != null) {
						DocumentManager.setAttribute(settings.metadata, attrName, (String)value);
					}
				}
				break;
			case QUEUE:
				if (colIdx > 1) {
					String attrName = configuration.getQueueAttributeName(colIdx);
					if (attrName != null) {
						DocumentManager.setAttribute(settings.metadata, attrName, (String)value);
					}
				}
			}
			
			placeTreeViewer.refresh();			
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			CellEditor editor = super.getCellEditor(element);
			TreeNode node = (TreeNode)element;
			Settings settings = (Settings)node.getValue();
			cellEditor.setValidator(null);
			switch(settings.type) {
			case PLACE:
			case QUEUEING_PLACE:
				if (colIdx == 1) {
					editor.setValidator(CellValidators.newIntegerRangeValidator(0, 5));
				}
				break;
			case COLOR_REF:
			case QUEUE:
			case DEPOSITORY:
				if (colIdx > 1) {
					editor.setValidator(configuration.getValidator(colIdx));
				}
				break;
			default:
				break;
			}
			return editor;
		}

		@Override
		protected boolean canEdit(Object element) {
			TreeNode node = (TreeNode)element;
			Settings settings = (Settings)node.getValue();
			
			if (colIdx == 0) {
				return false;
			} else {
				switch(settings.type) {
				case PROBE:
				case PLACE:
				case QUEUEING_PLACE:
					return (colIdx == 1);
				case SUBNET_PLACE:
				case COMPOSITE_COLOR_REF:
					return false;
				case COLOR_REF:
				case DEPOSITORY:
					return ((colIdx > 1) && (configuration.getAttributeName(colIdx) != null));
				case QUEUE:
					return ((colIdx > 1) && (configuration.getQueueAttributeName(colIdx) != null));
				}
			}
			return false;
		}
		
	}	
}
