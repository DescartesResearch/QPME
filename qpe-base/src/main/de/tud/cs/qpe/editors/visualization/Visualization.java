package de.tud.cs.qpe.editors.visualization;

import java.util.Map;

import de.tud.cs.qpe.editors.query.model.Metric;
import de.tud.cs.qpe.editors.query.model.MetricValue;

public abstract class Visualization {

	public static final Visualization[] VISUALIZATIONS = {
			new PieChartVisualization(),
			new BarChartVisualization(),
			new TableVisualization(),
		};
	
	public abstract String getName();

	public abstract String getId();
	
	public abstract VisualizationComponent createChart(String title, Map<? extends Object, MetricValue> aggregatedValues);
	
	public boolean isAvailableForMetric(Metric metric) {
		return true;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
