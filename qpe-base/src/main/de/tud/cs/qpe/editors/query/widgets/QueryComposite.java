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
