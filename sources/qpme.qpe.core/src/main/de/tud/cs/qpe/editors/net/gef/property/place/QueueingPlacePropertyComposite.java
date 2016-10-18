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
 *  2006        Christofer Dutz   Created.
 *  2008/02/05  Frederik Zipp     Added queue reference selection combobox.    
 * 
 */
package de.tud.cs.qpe.editors.net.gef.property.place;

import java.util.List;

import javax.xml.XMLConstants;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;

import de.tud.cs.qpe.editors.net.QueueEditorPage;
import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.model.NetHelper;
import de.tud.cs.qpe.utils.CellValidators;
import de.tud.cs.qpe.utils.FileCellEditor;
import de.tud.cs.qpe.utils.XmlAttributeEditingSupport;
import de.tud.cs.qpe.utils.XmlAttributeLabelProvider;
import de.tud.cs.qpe.utils.XmlEnumerationAttributeEditingSupport;

public class QueueingPlacePropertyComposite extends PlacePropertyComposite {
	protected Combo queue;

	protected Combo strategy;

	protected Spinner numberOfServers;

	protected final DistributionFunctionHelper distHelper = new DistributionFunctionHelper();

	public QueueingPlacePropertyComposite(Element net, Composite parent) {
		super(net, parent);
		initProperties();
		initColorTable();
	}
	
	@Override
	public void colorRefAdded(Element colorRef) {
		super.colorRefAdded(colorRef);
		
		colorRef.addAttribute(new QName("type", new Namespace("xsi", XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI)), "queueing-color-reference");
		
		colorRef.addAttribute("ranking", "0");
		colorRef.addAttribute("priority", "0");
		colorRef.addAttribute("distribution-function", "Exponential");
		
		// Initialize the default parameters of the distribution function.
		distHelper.initializeAttributes(colorRef);		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tud.cs.qpe.parts.property.place.PlacePropertyComposite#initProperties()
	 */
	protected void initProperties() {
		super.initProperties();

		new Label(this, SWT.NULL).setText("Queue");
		queue = new Combo(this, SWT.BORDER);
		queue.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		queue.addModifyListener(new ModifyListener () {
			public void modifyText(ModifyEvent evnt) {
				if ("place".equals(getModel().getName())) {
					String oldValue = getModel().attributeValue("queue-ref", "");
					Element q = NetHelper.getQueueByName(getModel(), 
							QueueingPlacePropertyComposite.this.queue.getText());
					if (q != null) {
						String newValue = q.attributeValue("id", "");
						if (!newValue.equals(oldValue)) {
							DocumentManager.setAttribute(getModel(), "queue-ref", newValue);
							strategy.setText(q.attributeValue("strategy", "FCFS"));
							numberOfServers.setSelection(Integer.parseInt(q.attributeValue("number-of-servers", "1")));
						}
					}
				}
			}
		});
		
		new Label(this, SWT.NULL).setText("Scheduling Strategy");
		strategy = new Combo(this, SWT.BORDER);
		strategy.add("PRIO");
		strategy.add("PS");
		strategy.add("FCFS");
		strategy.add("IS");
		strategy.add("RANDOM");
		strategy.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		strategy.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent evnt) {
				if ("place".equals(getModel().getName())) {
					Element q = getQueueElement();
					if (q != null) {
						String oldValue = q.attributeValue("strategy");
						String newValue = QueueingPlacePropertyComposite.this.strategy.getText();
						if (!oldValue.equals(newValue)) {
							DocumentManager.setAttribute(q, "strategy",	newValue);
						}
					}
				}
			}
		});

		new Label(this, SWT.NULL).setText("Number Of \nServers");
		numberOfServers = new Spinner(this, SWT.BORDER);
		numberOfServers.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		numberOfServers.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent evnt) {
				if (getModel() != null) {
					if ("place".equals(getModel().getName())) {
						Element q = getQueueElement();
						if (q != null) {
							String oldValue = q.attributeValue("number-of-servers");
							String newValue = Integer.toString(QueueingPlacePropertyComposite.this.numberOfServers.getSelection());
							if (!oldValue.equals(newValue)) {
								DocumentManager.setAttribute(q, "number-of-servers", newValue);
							}
						}
					}
				}
			}
		});
		numberOfServers.setMinimum(1);
		numberOfServers.setMaximum(1000000);
		
		Button newQueueButton = new Button(this, SWT.NULL);
		newQueueButton.setText("New Queue");
		newQueueButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		newQueueButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				Element newQueue = QueueEditorPage.createQueue(getModel());
				NetHelper.addQueue(getModel(), newQueue);
				updateQueueList();
				selectQueue(newQueue.attributeValue("name", ""));
				updatePropertyFields();
			}
		});
	}

	private Element getQueueElement() {
		String id = getModel().attributeValue("queue-ref", "");
		return NetHelper.getQueueById(getModel(), id);
	}
	
	private void selectQueue(String name) {
		queue.select(queue.indexOf(name));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tud.cs.qpe.parts.property.place.PlacePropertyComposite#initTableColumns()
	 */
	protected void initTableColumns() {
		super.initTableColumns();
		
		
		TableViewerColumn col = colorTable.createColumn("Ranking", 70);
		colorTable.setColumnLabelProvider(col, new XmlAttributeLabelProvider("ranking", "0"));
		XmlAttributeEditingSupport editor = new XmlAttributeEditingSupport(col.getViewer(), "ranking");
		editor.setValidator(CellValidators.newNonNegativeIntegerValidator());
		colorTable.setColumnEditingSupport(col, editor);
		
		col = colorTable.createColumn("Priority", 70);
		colorTable.setColumnLabelProvider(col, new XmlAttributeLabelProvider("priority", "0"));
		editor = new XmlAttributeEditingSupport(col.getViewer(), "priority");
		editor.setValidator(CellValidators.newNonNegativeIntegerValidator());
		colorTable.setColumnEditingSupport(col, editor);
		
		col = colorTable.createColumn("Distribution", 100);
		colorTable.setColumnLabelProvider(col, new XmlAttributeLabelProvider("distribution-function", "Exponential"));
		colorTable.setColumnEditingSupport(col, new XmlEnumerationAttributeEditingSupport(col.getViewer(), "distribution-function") {	
			@Override
			protected Object[] getItems() {
				return distHelper.getItems();
			}
			
			@Override
			protected void setValue(Object element, Object value) {
				super.setValue(element, value);
				distHelper.initializeAttributes((Element)element);
				distHelper.cleanupAttributes((Element)element);
			}
		});
		
		col = colorTable.createColumn("p1", 40);
		colorTable.setColumnLabelProvider(col, new DistributionFunctionParameterLabelProvider(0));
		colorTable.setColumnEditingSupport(col, new DistributionFunctionParameterEditingSupport(col.getViewer(), 0));
		
		col = colorTable.createColumn("p2", 40);
		colorTable.setColumnLabelProvider(col, new DistributionFunctionParameterLabelProvider(1));
		colorTable.setColumnEditingSupport(col, new DistributionFunctionParameterEditingSupport(col.getViewer(), 1));
		
		col = colorTable.createColumn("p3", 40);
		colorTable.setColumnLabelProvider(col, new DistributionFunctionParameterLabelProvider(2));
		colorTable.setColumnEditingSupport(col, new DistributionFunctionParameterEditingSupport(col.getViewer(), 2));
		
		col = colorTable.createColumn("Input File", 80);
		colorTable.setColumnLabelProvider(col, new CellLabelProvider() {
			
			@Override
			public void update(ViewerCell cell) {
				Element colorRef = (Element)cell.getElement();
				String curDistFkt = colorRef.attributeValue(
						"distribution-function", "Exponential");
				if("Empirical".equals(curDistFkt)) {
					cell.setText(colorRef.attributeValue("pdf_filename", ""));
				} else if ("Replay".equals(curDistFkt)) {
					cell.setText(colorRef.attributeValue("replay_filename", ""));
				} else {
					cell.setText("");
				}
			}
		});
		colorTable.setColumnEditingSupport(col, new XmlAttributeEditingSupport(col.getViewer(), "pdf_filename") {
			
			@Override
			protected CellEditor createCellEditor(Composite parent) {
				return new FileCellEditor(parent);
			}
			
			@Override
			protected boolean canEdit(Object element) {
				Element colorRef = (Element)element;
				String curDistFkt = colorRef.attributeValue(
						"distribution-function", "Exponential");
				return ("Empirical".equals(curDistFkt) || "Replay".equals(curDistFkt));
			}

			@Override
			protected void setValue(Object element, Object value) {
				updateInternalAttribute(element);
				super.setValue(element, value);
			}

			@Override
			protected Object getValue(Object element) {
				updateInternalAttribute(element);
				return ((Element) element).attributeValue(attribute, "");
			}

			private void updateInternalAttribute(Object element) {
				Element colorRef = (Element) element;
				String curDistFkt = colorRef.attributeValue("distribution-function", "Exponential");
				if ("Empirical".equals(curDistFkt))
					this.attribute = "pdf_filename";
				else if ("Replay".equals(curDistFkt))
					this.attribute = "replay_filename";
			}
			
		});
	}

	protected void updatePropertyFields() {
		super.updatePropertyFields();
		if (getModel() != null) {
			updateQueueList();
			String id = getModel().attributeValue("queue-ref", "");
			Element q = NetHelper.getQueueById(getModel(), id);
			if (q != null) {
				queue.select(queue.indexOf(q.attributeValue("name", "")));
				strategy.select(strategy.indexOf(q.attributeValue("strategy", "")));
				numberOfServers.setSelection(Integer.parseInt(q.attributeValue("number-of-servers", "1")));
			}
		}
	}

	private void updateQueueList() {
		queue.removeAll();
		List<Element> queues = NetHelper.listQueues(getModel());
		for (Element queueElement : queues) {
			queue.add(queueElement.attributeValue("name"));
		}
	}
	
	private class DistributionFunctionParameterLabelProvider extends CellLabelProvider {
		
		private int parameterIndex;
		
		public DistributionFunctionParameterLabelProvider(int parameterIndex) {
			this.parameterIndex = parameterIndex;
		}

		@Override
		public void update(ViewerCell cell) {
			Element colorRef = (Element)cell.getElement();
			String curDistFkt = colorRef.attributeValue(
					"distribution-function", "Exponential");
			if (parameterIndex < distHelper.getNumberOfParameters(curDistFkt)) {
				cell.setText(colorRef.attributeValue(distHelper
						.getParamterName(curDistFkt, parameterIndex), "1.0"));
			} else {
				cell.setText("");
			}			
		}
	}
	
	private class DistributionFunctionParameterEditingSupport extends EditingSupport {
		
		private CellEditor cellEditor;
		private int parameterIndex;

		public DistributionFunctionParameterEditingSupport(ColumnViewer viewer, int parameterIndex) {
			super(viewer);
			this.parameterIndex = parameterIndex;
			this.cellEditor = new TextCellEditor((Composite)viewer.getControl());
			this.cellEditor.setValidator(CellValidators.newDoubleValidator());
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			return cellEditor;
		}

		@Override
		protected boolean canEdit(Object element) {
			String distrFkt = getDistributionFunction(element);
			return (distHelper.getNumberOfParameters(distrFkt) > parameterIndex);
		}
		
		@Override
		protected Object getValue(Object element) {
			Element colorRef = (Element)element;
			String distrFkt = getDistributionFunction(element);
			if (parameterIndex < distHelper.getNumberOfParameters(distrFkt)) {
				return colorRef.attributeValue(
						distHelper.getParamterName(distrFkt, parameterIndex), "1.0");
			}
			return null;
		}

		@Override
		protected void setValue(Object element, Object value) {
			if (value != null) {
				Element colorRef = (Element)element;
				String distrFkt = getDistributionFunction(element);
				if (parameterIndex < distHelper.getNumberOfParameters(distrFkt)) {
					DocumentManager.setAttribute(colorRef, 
							distHelper.getParamterName(distrFkt, parameterIndex), value.toString());
					getViewer().refresh();
				}
			}
		}
		
		private String getDistributionFunction(Object element) {
			Element colorRef = (Element) element;
			return colorRef.attributeValue(
					"distribution-function", "Exponential");
		}
		
	}

	protected class DistributionFunctionHelper {
		protected final String[] items = { "Beta", "BreitWigner",
				"BreitWignerMeanSquare", "ChiSquare", "Gamma", "Hyperbolic",
				"Exponential", "ExponentialPower", "Logarithmic", "Normal",
				"StudentT", "Uniform", "VonMises", "Empirical", "Deterministic", "Replay" };

		protected final int numParams[] = { 2, 3, 3, 1, 2, 2, 1, 1, 1, 2, 1, 2,
				1, 2, 1, 0 };

		protected final String paramNames[][] = { { "alpha", "beta", null },
				{ "mean", "gamma", "cut" }, { "mean", "gamma", "cut" },
				{ "freedom", null, null }, { "alpha", "lambda", null },
				{ "alpha", "beta", null }, { "lambda", null, null },
				{ "tau", null, null }, { "p", null, null },
				{ "mean", "stdDev", null }, { "freedom", null, null },
				{ "min", "max", null }, { "freedom", null, null },
				{ "scale", "offset", null }, { "c", null, null }, {} };

		protected final String defaultValues[][] = { { "1", "1", null },
				{ "1", "1", "1" }, { "1", "1", "1" }, { "1", null, null },
				{ "1", "1", null }, { "1", "1", null }, { "1", null, null },
				{ "1", null, null }, { "1", null, null }, { "1", "1", null },
				{ "1", null, null }, { "1", "1", null }, { "1", null, null },
				{ "1", "0", null }, { "1", null, null }, {} };

		public String[] getItems() {
			return items;
		}

		/**
		 * Returns the index of the string in the combobox.
		 * 
		 * @param stringValue
		 * @return
		 */
		public Integer getValue(String stringValue) {
			for (int i = 0; i < items.length; i++) {
				if (items[i].equals(stringValue)) {
					return new Integer(i);
				}
			}
			return new Integer(0);
		}

		public int getNumberOfParameters(String distFkt) {
			int i = getValue(distFkt);
			return numParams[i];
		}
		
		public String getParamterName(String distFkt, int index) {
			int i = getValue(distFkt);
			return paramNames[i][index];
		}

		public void initializeAttributes(Element colorRef) {
			String distrFkt = colorRef.attributeValue("distribution-function",
					"Exponential");
			int i = getValue(distrFkt);
			for (int index = 0; index < getNumberOfParameters(distrFkt); index++) {
				colorRef.addAttribute(getParamterName(distrFkt, index), defaultValues[i][index]);
			}
		}

		public void cleanupAttributes(Element colorRef) {
			int distFktIndex = getValue(colorRef.attributeValue(
					"distribution-function", "Exponential"));
			switch (distFktIndex) {
			case 0:
				removeAttributes(colorRef, new String[] { "cut", "freedom",
						"gamma", "lambda", "max", "mean", "min", "p", "p1",
						"p2", "p3", "pdf_filename", "stdDev", "tau", "scale", "offset" });
				break;
			case 1:
				removeAttributes(colorRef, new String[] { "alpha", "beta",
						"freedom", "lambda", "max", "min", "p", "p1", "p2",
						"p3", "pdf_filename", "stdDev", "tau", "scale", "offset" });
				break;
			case 2:
				removeAttributes(colorRef, new String[] { "alpha", "beta",
						"freedom", "lambda", "max", "min", "p", "p1", "p2",
						"p3", "pdf_filename", "stdDev", "tau", "scale", "offset" });
				break;
			case 3:
				removeAttributes(colorRef, new String[] { "alpha", "beta",
						"cut", "gamma", "lambda", "max", "mean", "min", "p",
						"p1", "p2", "p3", "pdf_filename", "stdDev", "tau", "scale", "offset" });
				break;
			case 4:
				removeAttributes(colorRef, new String[] { "beta", "cut",
						"freedom", "gamma", "max", "mean", "min", "p", "p1",
						"p2", "p3", "pdf_filename", "stdDev", "tau" });
				break;
			case 5:
				removeAttributes(colorRef, new String[] { "cut", "freedom",
						"gamma", "lambda", "max", "mean", "min", "p", "p1",
						"p2", "p3", "pdf_filename", "stdDev", "tau", "scale", "offset" });
				break;
			case 6:
				removeAttributes(colorRef, new String[] { "alpha", "beta",
						"cut", "freedom", "gamma", "max", "mean", "min", "p",
						"p1", "p2", "p3", "pdf_filename", "stdDev", "tau", "scale", "offset" });
				break;
			case 7:
				removeAttributes(colorRef,
						new String[] { "alpha", "beta", "cut", "freedom",
								"gamma", "lambda", "max", "mean", "min", "p",
								"p1", "p2", "p3", "pdf_filename", "stdDev", "scale", "offset" });
				break;
			case 8:
				removeAttributes(colorRef, new String[] { "alpha", "beta",
						"cut", "freedom", "gamma", "lambda", "max", "mean",
						"min", "p1", "p2", "p3", "pdf_filename", "stdDev",
						"tau", "scale", "offset" });
				break;
			case 9:
				removeAttributes(colorRef, new String[] { "alpha", "beta",
						"cut", "freedom", "gamma", "lambda", "max", "min", "p",
						"p1", "p2", "p3", "pdf_filename", "tau", "scale", "offset" });
				break;
			case 10:
				removeAttributes(colorRef, new String[] { "alpha", "beta",
						"cut", "gamma", "lambda", "max", "mean", "min", "p",
						"p1", "p2", "p3", "pdf_filename", "stdDev", "tau", "scale", "offset" });
				break;
			case 11:
				removeAttributes(colorRef, new String[] { "alpha", "beta",
						"cut", "freedom", "gamma", "lambda", "mean", "p", "p1",
						"p2", "p3", "pdf_filename", "stdDev", "tau", "scale", "offset" });
				break;
			case 12:
				removeAttributes(colorRef, new String[] { "alpha", "beta",
						"cut", "gamma", "lambda", "max", "mean", "min", "p",
						"p1", "p2", "p3", "pdf_filename", "stdDev", "tau", "scale", "offset" });
				break;
			case 13:
				removeAttributes(colorRef, new String[] { "alpha", "beta",
						"cut", "freedom", "gamma", "lambda", "max", "mean",
						"min", "p", "p1", "p2", "p3", "stdDev", "tau", "scale", "offset" });
				break;
			case 14:
				removeAttributes(colorRef, new String[] { "alpha", "beta",
						"cut", "freedom", "gamma", "lambda", "max", "mean",
						"min", "p", "pdf_filename", "stdDev", "tau", "scale", "offset" });
				break;
			}
		}

		protected void removeAttributes(Element colorRef,
				String[] removableAttributeNames) {
			for (int i = 0; i < removableAttributeNames.length; i++) {
				Attribute attr = colorRef.attribute(removableAttributeNames[i]);
				if (attr != null) {
					DocumentManager.removeAttribute(colorRef,
							removableAttributeNames[i]);
				}
			}
		}
	}
}
