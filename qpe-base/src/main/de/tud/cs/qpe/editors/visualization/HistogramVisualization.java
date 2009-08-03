package de.tud.cs.qpe.editors.visualization;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.SimpleHistogramBin;
import org.jfree.data.statistics.SimpleHistogramDataset;
import org.jfree.experimental.chart.swt.ChartComposite;

import de.tud.cs.qpe.editors.query.model.Histogram;

public class HistogramVisualization {

	private static SimpleHistogramDataset createDataset(Histogram histogram, String title) {
		SimpleHistogramDataset dataset = new SimpleHistogramDataset(title);
		double bucketSize = histogram.getBucketSize();
		for (int i = 0; i < histogram.getNumBuckets(); i++) {
			double lowerBound = i * bucketSize;
			double upperBound = lowerBound + bucketSize;
			SimpleHistogramBin bin = new SimpleHistogramBin(lowerBound, upperBound, true, false);
			bin.setItemCount(histogram.getBucketCount(i));
			dataset.addBin(bin);
		}
		return dataset;
	}

	public VisualizationComponent createChart(String title, Histogram histogram) {
        JFreeChart chart = ChartFactory.createHistogram(
                title, 
                null, 
                null, 
                createDataset(histogram, title), 
                PlotOrientation.VERTICAL, 
                true, 
                false, 
                false
            );
            chart.getXYPlot().setForegroundAlpha(0.75f);
		return new HistogramVisualizationComponent(chart);
	}
	
	private class HistogramVisualizationComponent implements VisualizationComponent {

		private final JFreeChart chart;

		public HistogramVisualizationComponent(JFreeChart chart) {
			this.chart = chart;
		}
		
		@Override
		public Composite realize(Composite parent) {
			return new ChartComposite(parent, SWT.NONE, this.chart, true);
		}
	}
}
