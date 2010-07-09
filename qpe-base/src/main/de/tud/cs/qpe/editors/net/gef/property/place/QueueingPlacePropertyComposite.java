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
 *  2008/02/05  Frederik Zipp     Added queue reference selection combobox.    
 * 
 */
package de.tud.cs.qpe.editors.net.gef.property.place;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
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
import org.eclipse.swt.widgets.TableColumn;

import de.tud.cs.qpe.editors.net.QueueEditorPage;
import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.model.NetHelper;
import de.tud.cs.qpe.utils.DoubleCellEditor;
import de.tud.cs.qpe.utils.FileCellEditor;
import de.tud.cs.qpe.utils.IntegerCellEditor;

public class QueueingPlacePropertyComposite extends PlacePropertyComposite {
	protected Combo queue;

	protected Combo strategy;

	protected Spinner numberOfServers;

	protected final DistributionFunctionHelper distHelper = new DistributionFunctionHelper();

	public QueueingPlacePropertyComposite(Element net, Composite parent) {
		super(net, parent);
		columnNames = new String[] { "Color", "Initial", "Max", "Ranking",
				"Priority", "Distribution", "p1", "p2", "p3", "Input File" };
		initProperties();
		initColorTable();
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
	 * @see de.tud.cs.qpe.parts.property.place.PlacePropertyComposite#initCellEditors()
	 */
	protected ArrayList<CellEditor> initCellEditors() {
		ArrayList<CellEditor> cellEditors = super.initCellEditors();
		cellEditors.add(new IntegerCellEditor(colorTableViewer.getTable()));
		cellEditors.add(new IntegerCellEditor(colorTableViewer.getTable()));
		cellEditors.add(new ComboBoxCellEditor(colorTableViewer.getTable(),
				distHelper.getItems()));
		cellEditors.add(new DoubleCellEditor(colorTableViewer.getTable()));
		cellEditors.add(new DoubleCellEditor(colorTableViewer.getTable()));
		cellEditors.add(new DoubleCellEditor(colorTableViewer.getTable()));
		cellEditors.add(new FileCellEditor(colorTableViewer.getTable()));

		return cellEditors;
	}

	
	@Override
	protected Element createColorReference() {
		Element colorRef = super.createColorReference();
		colorRef.addAttribute("ranking", "0");
		colorRef.addAttribute("priority", "0");
		colorRef.addAttribute("distribution-function", "Exponential");
		
		// Initialize the default parameters of the distribution function.
		distHelper.initializeAttributes(colorRef);
		
		return colorRef;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tud.cs.qpe.parts.property.place.PlacePropertyComposite#initCellModifier()
	 */
	protected void initCellModifier() {
		colorTableViewer.setCellModifier(new ColorTableCellModifier() {
			@Override
			public boolean canModify(Object element, String property) {
				Element colorRef = (Element) element;
				String distrFkt = colorRef.attributeValue(
						"distribution-function", "Exponential");

				if (!super.canModify(element, property)) {
					return false;
				} else if ("p1".equals(property)) {
					if ((distrFkt == null)
							|| (distHelper.getNumberOfParameters(distrFkt) == 0)) {
						return false;
					}
				} else if ("p2".equals(property)) {
					if ((distrFkt == null)
							|| (distHelper.getNumberOfParameters(distrFkt) <= 1)) {
						return false;
					}
				} else if ("p3".equals(property)) {
					if ((distrFkt == null)
							|| (distHelper.getNumberOfParameters(distrFkt) <= 2)) {
						return false;
					}
				}
				if(!"Empirical".equals(distrFkt) && "Input File".equals(property)) {
					return false;
				}
				return true;
			}
			
			@Override
			protected Object getValue(int columnIndex, Element colorRef,
					Element color) {
				String distrFkt = colorRef.attributeValue(
						"distribution-function", "Exponential");

				switch (columnIndex) {
					case 0:
					case 1:
					case 2:
						return super.getValue(columnIndex, colorRef, color);
					case 3:
						return new Integer(colorRef.attributeValue("ranking", "0"));
					case 4:
						return new Integer(colorRef.attributeValue("priority", "0"));
					case 5:
						return distHelper.getValue(colorRef.attributeValue(
								"distribution-function", "Exponential"));
					case 6:
						if ((distrFkt != null)
								&& (distHelper.getNumberOfParameters(distrFkt) > 0)) {
							return new Double(colorRef.attributeValue(distHelper
									.getP1Name(distrFkt), "1.0"));
						}
					case 7:
						if ((distrFkt != null)
								&& (distHelper.getNumberOfParameters(distrFkt) > 1)) {
							return new Double(colorRef.attributeValue(distHelper
									.getP2Name(distrFkt), "1.0"));
						}
					case 8:
						if ((distrFkt != null)
								&& (distHelper.getNumberOfParameters(distrFkt) > 2)) {
							return new Double(colorRef.attributeValue(distHelper
									.getP3Name(distrFkt), "1.0"));
						}
					case 9:
						if("Empirical".equals(distrFkt)) {
							return colorRef.attributeValue("pdf_filename", "");
						}
				}

				return null;
			}
			
			@Override
			protected void modify(int columnIndex, Element colorRef, Object value) {
				int iValue;

				String distrFkt = colorRef.attributeValue(
						"distribution-function", "Exponential");
				switch (columnIndex) {
				case 0:
				case 1:
				case 2:
					super.modify(columnIndex, colorRef, value);
					break;
				case 3:
					iValue = ((Integer) value).intValue();
					if (iValue >= 0) {
						DocumentManager.setAttribute(colorRef, "ranking",
								((Integer) value).toString());
					}
					break;
				case 4:
					iValue = ((Integer) value).intValue();
					if (iValue >= 0) {
						DocumentManager.setAttribute(colorRef, "priority",
								((Integer) value).toString());
					}
					break;
				case 5:
					DocumentManager.setAttribute(colorRef,
							"distribution-function", distHelper
									.getValue((Integer) value));
					distHelper.initializeAttributes(colorRef);
					break;
				case 6:
					if ((distrFkt != null)
							&& (distHelper.getNumberOfParameters(distrFkt) > 0)) {
						DocumentManager.setAttribute(colorRef, distHelper
								.getP1Name(distrFkt), ((Double) value)
								.toString());
					}
					break;
				case 7:
					if ((distrFkt != null)
							&& (distHelper.getNumberOfParameters(distrFkt) > 1)) {
						DocumentManager.setAttribute(colorRef, distHelper
								.getP2Name(distrFkt), ((Double) value)
								.toString());
					}
					break;
				case 8:
					if ((distrFkt != null)
							&& (distHelper.getNumberOfParameters(distrFkt) > 2)) {
						DocumentManager.setAttribute(colorRef, distHelper
								.getP3Name(distrFkt), ((Double) value)
								.toString());
					}
					break;
				case 9:
					if("Empirical".equals(distrFkt)) {
						DocumentManager.setAttribute(colorRef, "pdf_filename",
							(String) value);
					}
					break;
				}

				distHelper.cleanupAttributes(colorRef);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tud.cs.qpe.parts.property.place.PlacePropertyComposite#initLabelProvider()
	 */
	protected void initLabelProvider() {
		colorTableViewer.setLabelProvider(new ColorTableLabelProvider() {
			@Override
			protected String getColumnText(Element colorRef, Element color,
					int columnIndex) {
				String curDistFkt = colorRef.attributeValue(
						"distribution-function", "Exponential");
				switch (columnIndex) {
					case 0:
					case 1:
					case 2:
						return super.getColumnText(colorRef, color, columnIndex);
					case 3:
						return colorRef.attributeValue("ranking", "0");
					case 4:
						return colorRef.attributeValue("priority", "0");
					case 5:
						return colorRef.attributeValue("distribution-function",
								"Exponential");
					case 6:
						if (distHelper.getNumberOfParameters(curDistFkt) >= 1) {
							return colorRef.attributeValue(distHelper
									.getP1Name(curDistFkt), "1.0");
						}
					case 7:
						if (distHelper.getNumberOfParameters(curDistFkt) >= 2) {
							return colorRef.attributeValue(distHelper
									.getP2Name(curDistFkt), "1.0");
						}
						break;
					case 8:
						if (distHelper.getNumberOfParameters(curDistFkt) == 3) {
							return colorRef.attributeValue(distHelper
									.getP3Name(curDistFkt), "1.0");
						}
						break;
					case 9:
						if("Empirical".equals(curDistFkt)) {
							return colorRef.attributeValue("pdf_filename", "");
						}
				}
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tud.cs.qpe.parts.property.place.PlacePropertyComposite#initTableColumns()
	 */
	protected void initTableColumns() {
		super.initTableColumns();
		TableColumn col = new TableColumn(colorTable, SWT.LEFT);
		col.setText("Ranking");
		col.setWidth(70);
		col = new TableColumn(colorTable, SWT.LEFT);
		col.setText("Priority");
		col.setWidth(70);
		col = new TableColumn(colorTable, SWT.LEFT);
		col.setText("Distribution");
		col.setWidth(100);
		col = new TableColumn(colorTable, SWT.LEFT);
		col.setText("p1");
		col.setWidth(40);
		col = new TableColumn(colorTable, SWT.LEFT);
		col.setText("p2");
		col.setWidth(40);
		col = new TableColumn(colorTable, SWT.LEFT);
		col.setText("p3");
		col.setWidth(40);
		col = new TableColumn(colorTable, SWT.LEFT);
		col.setText("Input File");
		col.setWidth(80);
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

	protected class DistributionFunctionHelper {
		protected final String[] items = { "Beta", "BreitWigner",
				"BreitWignerMeanSquare", "ChiSquare", "Gamma", "Hyperbolic",
				"Exponential", "ExponentialPower", "Logarithmic", "Normal",
				"StudentT", "Uniform", "VonMises", "Empirical", "Deterministic" };

		protected final int numParams[] = { 2, 3, 3, 1, 2, 2, 1, 1, 1, 2, 1, 2,
				1, 0, 1 };

		protected final String paramNames[][] = { { "alpha", "beta", null },
				{ "mean", "gamma", "cut" }, { "mean", "gamma", "cut" },
				{ "freedom", null, null }, { "alpha", "lambda", null },
				{ "alpha", "beta", null }, { "lambda", null, null },
				{ "tau", null, null }, { "p", null, null },
				{ "mean", "stdDev", null }, { "freedom", null, null },
				{ "min", "max", null }, { "freedom", null, null },
				{ null, null, null }, { "p1", null, null } };

		protected final String defaultValues[][] = { { "1", "1", null },
				{ "1", "1", "1" }, { "1", "1", "1" }, { "1", null, null },
				{ "1", "1", null }, { "1", "1", null }, { "1", null, null },
				{ "1", null, null }, { "1", null, null }, { "1", "1", null },
				{ "1", null, null }, { "1", "1", null }, { "1", null, null },
				{ null, null, null }, { "1", null, null } };

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

		/**
		 * Returns the name of the distriburion function identified by the given
		 * index.
		 * 
		 * @param intValue
		 * @return
		 */
		public String getValue(Integer intValue) {
			return items[intValue.intValue()];
		}

		public int getNumberOfParameters(String distFkt) {
			int i = getValue(distFkt);
			return numParams[i];
		}

		public String getP1Name(String distFkt) {
			int i = getValue(distFkt);
			return paramNames[i][0];
		}

		public String getP2Name(String distFkt) {
			int i = getValue(distFkt);
			return paramNames[i][1];
		}

		public String getP3Name(String distFkt) {
			int i = getValue(distFkt);
			return paramNames[i][2];
		}

		public void initializeAttributes(Element colorRef) {
			String distrFkt = colorRef.attributeValue("distribution-function",
					"Exponential");
			int i = getValue(distrFkt);
			switch (getNumberOfParameters(distrFkt)) {
			case 3:
				colorRef.addAttribute(getP3Name(distrFkt),
						defaultValues[i][2]);
			case 2:
				colorRef.addAttribute(getP2Name(distrFkt),
						defaultValues[i][1]);
			case 1:
				colorRef.addAttribute(getP1Name(distrFkt),
						defaultValues[i][0]);
			}
		}

		public void cleanupAttributes(Element colorRef) {
			int distFktIndex = getValue(colorRef.attributeValue(
					"distribution-function", "Exponential"));
			switch (distFktIndex) {
			case 0:
				removeAttributes(colorRef, new String[] { "cut", "freedom",
						"gamma", "lambda", "max", "mean", "min", "p", "p1",
						"p2", "p3", "pdf_filename", "stdDev", "tau" });
				break;
			case 1:
				removeAttributes(colorRef, new String[] { "alpha", "beta",
						"freedom", "lambda", "max", "min", "p", "p1", "p2",
						"p3", "pdf_filename", "stdDev", "tau" });
				break;
			case 2:
				removeAttributes(colorRef, new String[] { "alpha", "beta",
						"freedom", "lambda", "max", "min", "p", "p1", "p2",
						"p3", "pdf_filename", "stdDev", "tau" });
				break;
			case 3:
				removeAttributes(colorRef, new String[] { "alpha", "beta",
						"cut", "gamma", "lambda", "max", "mean", "min", "p",
						"p1", "p2", "p3", "pdf_filename", "stdDev", "tau" });
				break;
			case 4:
				removeAttributes(colorRef, new String[] { "beta", "cut",
						"freedom", "gamma", "max", "mean", "min", "p", "p1",
						"p2", "p3", "pdf_filename", "stdDev", "tau" });
				break;
			case 5:
				removeAttributes(colorRef, new String[] { "cut", "freedom",
						"gamma", "lambda", "max", "mean", "min", "p", "p1",
						"p2", "p3", "pdf_filename", "stdDev", "tau" });
				break;
			case 6:
				removeAttributes(colorRef, new String[] { "alpha", "beta",
						"cut", "freedom", "gamma", "max", "mean", "min", "p",
						"p1", "p2", "p3", "pdf_filename", "stdDev", "tau" });
				break;
			case 7:
				removeAttributes(colorRef,
						new String[] { "alpha", "beta", "cut", "freedom",
								"gamma", "lambda", "max", "mean", "min", "p",
								"p1", "p2", "p3", "pdf_filename", "stdDev" });
				break;
			case 8:
				removeAttributes(colorRef, new String[] { "alpha", "beta",
						"cut", "freedom", "gamma", "lambda", "max", "mean",
						"min", "p1", "p2", "p3", "pdf_filename", "stdDev",
						"tau" });
				break;
			case 9:
				removeAttributes(colorRef, new String[] { "alpha", "beta",
						"cut", "freedom", "gamma", "lambda", "max", "min", "p",
						"p1", "p2", "p3", "pdf_filename", "tau" });
				break;
			case 10:
				removeAttributes(colorRef, new String[] { "alpha", "beta",
						"cut", "gamma", "lambda", "max", "mean", "min", "p",
						"p1", "p2", "p3", "pdf_filename", "stdDev", "tau" });
				break;
			case 11:
				removeAttributes(colorRef, new String[] { "alpha", "beta",
						"cut", "freedom", "gamma", "lambda", "mean", "p", "p1",
						"p2", "p3", "pdf_filename", "stdDev", "tau" });
				break;
			case 12:
				removeAttributes(colorRef, new String[] { "alpha", "beta",
						"cut", "gamma", "lambda", "max", "mean", "min", "p",
						"p1", "p2", "p3", "pdf_filename", "stdDev", "tau" });
				break;
			case 13:
				removeAttributes(colorRef, new String[] { "alpha", "beta",
						"cut", "freedom", "gamma", "lambda", "max", "mean",
						"min", "p", "p1", "p2", "p3", "stdDev", "tau" });
				break;
			case 14:
				removeAttributes(colorRef, new String[] { "alpha", "beta",
						"cut", "freedom", "gamma", "lambda", "max", "mean",
						"min", "p", "pdf_filename", "stdDev", "tau" });
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
