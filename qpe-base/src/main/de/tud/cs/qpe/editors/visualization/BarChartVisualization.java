package de.tud.cs.qpe.editors.visualization;

import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import de.tud.cs.qpe.editors.query.model.MetricValue;

public class BarChartVisualization extends ChartVisualization {
	
	private static CategoryDataset createDataset(Map<? extends Object, MetricValue> aggregatedValues) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (Object key : aggregatedValues.keySet()) {
			dataset.setValue(new Double(aggregatedValues.get(key).getValue()), key.toString(), "");
		}
		return dataset;
	}
	
	private static JFreeChart createChart(String title, CategoryDataset dataset) {

		JFreeChart chart = ChartFactory.createBarChart(title, "", "", dataset, PlotOrientation.HORIZONTAL, true, true, false);

		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setNoDataMessage("No data available");
		return chart;
	}

	@Override
	public VisualizationComponent createChart(String title, Map<? extends Object, MetricValue> aggregatedValues) {
		return new ChartVisualizationComponent(createChart(title, createDataset(aggregatedValues)));
	}

	@Override
	public String getId() {
		return "bar-chart";
	}

	@Override
	public String getName() {
		return "Bar Chart";
	}
}
