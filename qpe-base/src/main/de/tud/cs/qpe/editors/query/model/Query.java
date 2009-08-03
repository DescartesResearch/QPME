package de.tud.cs.qpe.editors.query.model;

import java.util.List;
import java.util.Set;

public class Query<A, B> {

	private final Set<A> filterSetA;
	private final Set<B> filterSetB;
	private List<MetricQuery> metricQueries;
	
	public Query(Set<A> filterSetA, Set<B> filterSetB, List<MetricQuery> metricQueries) {
		this.filterSetA = filterSetA;
		this.filterSetB = filterSetB;
		this.metricQueries = metricQueries;
	}
	
	public Set<A> getFilterSetA() {
		return this.filterSetA;
	}
	
	public Set<B> getFilterSetB() {
		return this.filterSetB;
	}
	
	public List<MetricQuery> getMetricQueries() {
		return this.metricQueries;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		builder.append(getFilterSetA());
		builder.append(", ");
		builder.append(getFilterSetB());
		builder.append(", ");
		builder.append(getMetricQueries());
		builder.append(']');
		return builder.toString();
	}
}
