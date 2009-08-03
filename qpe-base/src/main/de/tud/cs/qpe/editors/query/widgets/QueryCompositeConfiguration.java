package de.tud.cs.qpe.editors.query.widgets;

import de.tud.cs.qpe.editors.query.model.FilterGroup;
import de.tud.cs.qpe.editors.query.model.Metric;
import de.tud.cs.qpe.editors.visualization.Visualization;

public class QueryCompositeConfiguration<A, B> {
	
	private final FilterGroup<A> groupA;
	private final FilterGroup<B> groupB;
	private final Metric[] metrics;
	private final Visualization[] visualizations;

	public QueryCompositeConfiguration(FilterGroup<A> groupA, FilterGroup<B> groupB, Metric[] metrics, Visualization[] visualizations) {
		this.groupA = groupA;
		this.groupB = groupB;
		this.metrics = metrics;
		this.visualizations = visualizations;
	}
	
	public Metric[] getMetrics() {
		return this.metrics;
	}
	
	public FilterGroup<A> getFilterGroupA() {
		return this.groupA;
	}

	public FilterGroup<B> getFilterGroupB() {
		return this.groupB;
	}
	
	public Visualization[] getVisualizations() {
		return this.visualizations;
	}
}
