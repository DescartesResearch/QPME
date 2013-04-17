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
 * Original Author(s):  Frederik Zipp and Samuel Kounev
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2009/02/27  Frederik Zipp     Created.
 * 
 */
package de.tud.cs.qpe.editors.query.widgets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import de.tud.cs.qpe.editors.query.model.MetricQuery;
import de.tud.cs.qpe.editors.query.model.Query;

public class QueryComposite<A, B> extends Composite {

	private static final int COLUMNS = 8;
	private final QueryCompositeConfiguration<A, B> configuration;
	private FilterGroupComposite<A> filterCompositeA;
	private FilterGroupComposite<B> filterCompositeB;
	private PlusMinusManager<MetricRow<A, B>> metricRows;

	public QueryComposite(Composite parent, int style, QueryCompositeConfiguration<A, B> configuration) {
		super(parent, style);
		this.configuration = configuration;
		setLayout(new GridLayout(COLUMNS, false));
		addFilterLabels();
		addFilters();
		addAggregationLabels();
		addAggregations();
	}

	private void addFilters() {
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		filterCompositeA = new FilterGroupComposite<A>(this, SWT.NONE, this.configuration.getFilterGroupA());
		filterCompositeA.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true));
		filterCompositeB = new FilterGroupComposite<B>(this, SWT.NONE, this.configuration.getFilterGroupB());
		filterCompositeB.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
	}

	private void addAggregations() {
		this.metricRows = new PlusMinusManager<MetricRow<A, B>>(this, getParent().getParent(), new MetricRow.Provider<A, B>(this.configuration));
	}
	
	private void addFilterLabels() {
		addLabelRow(new String[] {
				"",
				"",
				this.configuration.getFilterGroupA().getName() + ":",
				this.configuration.getFilterGroupB().getName() + ":",
				"",
				"",
				"",
				""});
	}
	
	private void addAggregationLabels() {
		addLabelRow(new String[] {
				"Title:",
				"Metric:",
				"Aggregation:",
				"Aggregation:",
				"Visualization:",
				"Visualization:",
				"Size:",
				""});
	}
	
	private void addLabelRow(String[] labels) {
		if (labels == null || labels.length < COLUMNS) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < COLUMNS; i++) {
			Label label = new Label(this, SWT.NONE);
			label.setText(labels[i]);
		}
	}
	
	public Set<A> getFilterSetA() {
		return this.filterCompositeA.getFilterSet();
	}
	
	public Set<B> getFilterSetB() {
		return this.filterCompositeB.getFilterSet();
	}

	public List<MetricQuery> getMetricQueries() {
		List<MetricQuery> list = new ArrayList<MetricQuery>();
		for (MetricRow<A, B> metricRow : this.metricRows.getEntries()) {
			if (metricRow.isValid()) {
				list.add(metricRow.getMetricQuery());
			}
		}
		return list;
	}
	
	public Query<A, B> getQuery() {
		return new Query<A, B>(getFilterSetA(), getFilterSetB(), getMetricQueries());
	}
	
	public void setQuery(Query<A, B> query) {
		this.filterCompositeA.setFilterSet(query.getFilterSetA());
		this.filterCompositeB.setFilterSet(query.getFilterSetB());
		this.metricRows.clear();
		for (MetricQuery metricQuery : query.getMetricQueries()) {
			MetricRow<A, B> metricRow = this.metricRows.addEntry();
			metricRow.setMetricQuery(metricQuery);
		}
		if (query.getMetricQueries().isEmpty()) {
			this.metricRows.addEntry();
		}
	}
}
