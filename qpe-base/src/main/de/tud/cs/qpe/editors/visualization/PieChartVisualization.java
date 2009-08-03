package de.tud.cs.qpe.editors.visualization;

import java.text.AttributedString;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import de.tud.cs.qpe.editors.query.model.MetricValue;

public class PieChartVisualization extends ChartVisualization {
	
	private static PieDataset createDataset(Map<? extends Object, MetricValue> aggregatedValues) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (Object key : aggregatedValues.keySet()) {
			dataset.setValue(key.toString(), new Double(aggregatedValues.get(key).getValue()));
		}
		return dataset;
	}
	
	private static JFreeChart createChart(String title, PieDataset dataset) {

		JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, false);

		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setSectionOutlinesVisible(false);
		plot.setNoDataMessage("No data available");
		plot.setCircular(false);
//		plot.setLabelGenerator(new NoLabelGenerator());
		return chart;
	}


	@Override
	public VisualizationComponent createChart(String title, Map<? extends Object, MetricValue> aggregatedValues) {
		return new ChartVisualizationComponent(createChart(title, createDataset(aggregatedValues)));
	}

	@Override
	public String getId() {
		return "pie-chart";
	}

	@Override
	public String getName() {
		return "Pie Chart";
	}
	
	@SuppressWarnings("unused")
	private static class NoLabelGenerator implements PieSectionLabelGenerator {

		@Override
		@SuppressWarnings("unchecked")
		public AttributedString generateAttributedSectionLabel(PieDataset arg0, Comparable arg1) {
			return null;
		}

		@Override
		@SuppressWarnings("unchecked")
		public String generateSectionLabel(PieDataset arg0, Comparable arg1) {
			return null;
		}
	}
}
