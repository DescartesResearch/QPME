package de.tud.cs.qpe.editors.query.model;

import org.eclipse.draw2d.geometry.Dimension;

import de.tud.cs.qpe.editors.visualization.Visualization;

public class MetricQuery {

	private final Metric metric;
	private final Aggregation aggregationA;
	private final Aggregation aggregationB;
	private final Visualization visualizationA;
	private final Visualization visualizationB;
	private final String title;
	private final Dimension size;

	public MetricQuery(String title, Metric metric, Aggregation aggregationA, Aggregation aggregationB, Visualization visualizationA, Visualization visualizationB, Dimension size) {
		this.title = title;
		this.metric = metric;
		this.aggregationA = aggregationA;
		this.aggregationB = aggregationB;
		this.visualizationA = visualizationA;
		this.visualizationB = visualizationB;
		this.size = size;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public Metric getMetric() {
		return this.metric;
	}
	
	public Aggregation getAggregationA() {
		return this.aggregationA;
	}
	
	public Aggregation getAggregationB() {
		return this.aggregationB;
	}

	public Visualization getVisualizationA() {
		return this.visualizationA;
	}
	
	public Visualization getVisualizationB() {
		return this.visualizationB;
	}

	public Dimension getSize() {
		return this.size;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append('(');
		builder.append(this.title);
		builder.append(", ");
		builder.append(this.metric);
		builder.append(", ");
		builder.append(this.aggregationA);
		builder.append(", ");
		builder.append(this.aggregationB);
		builder.append(", ");
		builder.append(this.visualizationA);
		builder.append(", ");
		builder.append(this.visualizationB);
		builder.append(", ");
		builder.append(this.size);
		builder.append(')');
		return builder.toString();
	}
}
