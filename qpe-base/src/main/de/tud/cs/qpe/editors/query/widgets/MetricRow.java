package de.tud.cs.qpe.editors.query.widgets;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import de.tud.cs.qpe.editors.query.model.Aggregation;
import de.tud.cs.qpe.editors.query.model.Metric;
import de.tud.cs.qpe.editors.query.model.MetricQuery;
import de.tud.cs.qpe.editors.visualization.Visualization;

public class MetricRow<A, B> implements IPlusMinusEntry {
	
	private static final String DEFAULT_SIZE = "640x480";
	
	private Text title;
	private SelectorComposite<Metric> metric;
	private SelectorComposite<Aggregation> aggregationA;
	private SelectorComposite<Aggregation> aggregationB;
	private SelectorComposite<Visualization> visualizationA;
	private SelectorComposite<Visualization> visualizationB;
	private Text size;
	private PlusMinusComposite plusMinus;
	private final QueryCompositeConfiguration<A, B> configuration;

	public MetricRow(QueryCompositeConfiguration<A, B> configuration) {
		this.configuration = configuration;
	}
	
	public void addWidgets(Composite parent) {
		this.title = new Text(parent, SWT.BORDER);
		this.metric = new SelectorComposite<Metric>(parent, SWT.NONE, this.configuration.getMetrics()); 
		this.aggregationA = new SelectorComposite<Aggregation>(parent, SWT.NONE, this.configuration.getFilterGroupA().getAggregations());
		this.aggregationA.addListener(new AggregationChangeListener());
		this.aggregationB = new SelectorComposite<Aggregation>(parent, SWT.NONE, this.configuration.getFilterGroupB().getAggregations());
		this.aggregationB.addListener(new AggregationChangeListener());
		this.visualizationA = new SelectorComposite<Visualization>(parent, SWT.NONE, this.configuration.getVisualizations());
		this.visualizationB = new SelectorComposite<Visualization>(parent, SWT.NONE, this.configuration.getVisualizations());
		updateVisualizationStates();
		this.size = new Text(parent, SWT.BORDER);
		this.size.setText(DEFAULT_SIZE);
		this.plusMinus = new PlusMinusComposite(parent, SWT.NONE);
	}

	public void removeWidgets() {
		this.title.dispose();
		this.metric.dispose();
		this.aggregationA.dispose();
		this.aggregationB.dispose();
		this.visualizationA.dispose();
		this.visualizationB.dispose();
		this.size.dispose();
		this.plusMinus.dispose();
	}

	public PlusMinusComposite getPlusMinus() {
		return this.plusMinus;
	}

	public String getTitle() {
		return this.title.getText();
	}
	
	public Metric getMetric() {
		return this.metric.getSelection();
	}
	
	public Aggregation getAggregationA() {
		return this.aggregationA.getSelection();
	}
	
	public Aggregation getAggregationB() {
		return this.aggregationB.getSelection();
	}

	public Visualization getVisualizationA() {
		return this.visualizationA.getSelection();
	}

	public Visualization getVisualizationB() {
		return this.visualizationB.getSelection();
	}

	public Dimension getSize() {
		return parseSize(this.size.getText());
	}

	private Dimension parseSize(String string) {
		int i = string.indexOf('x');
		int x = Integer.parseInt(string.substring(0, i));
		int y = Integer.parseInt(string.substring(i + 1));
		return new Dimension(x, y);
	}

	public MetricQuery getMetricQuery() {
		if (isValid()) {
			return new MetricQuery(getTitle(), getMetric(), getAggregationA(), getAggregationB(), getVisualizationA(), getVisualizationB(), getSize());
		}
		return null;
	}

	public void setMetricQuery(MetricQuery metricQuery) {
		this.title.setText(metricQuery.getTitle());
		this.metric.setSelection(metricQuery.getMetric());
		this.aggregationA.setSelection(metricQuery.getAggregationA());
		this.aggregationB.setSelection(metricQuery.getAggregationB());
		this.visualizationA.setSelection(metricQuery.getVisualizationA());
		this.visualizationB.setSelection(metricQuery.getVisualizationB());
		this.size.setText(metricQuery.getSize().width + "x" + metricQuery.getSize().height);
	}
	
	public boolean isValid() {
		return this.metric.hasSelection()
			&& this.aggregationA.hasSelection()
			&& this.aggregationB.hasSelection()
			&& (this.visualizationA.isEnabled() ? this.visualizationA.hasSelection() : true)
			&& (this.visualizationB.isEnabled() ? this.visualizationB.hasSelection() : true);
	}
	
	public static class Provider<A, B> implements IPlusMinusEntryProvider<MetricRow<A, B>> {
		
		private final QueryCompositeConfiguration<A, B> configuration;

		public Provider(QueryCompositeConfiguration<A, B> configuration) {
			this.configuration = configuration;
		}
		
		@Override
		public MetricRow<A, B> createEntry() {
			return new MetricRow<A, B>(this.configuration);
		}
	}
	
	private class AggregationChangeListener implements ISelectionListener {

		@Override
		public void onSelectionEvent() {
			updateVisualizationStates();
		}
	}

	private void updateVisualizationStates() {
		visualizationA.setEnabled(aggregationA.getSelection() == Aggregation.FOR_EACH);
		visualizationB.setEnabled(aggregationB.getSelection() == Aggregation.FOR_EACH);
	}		
}
