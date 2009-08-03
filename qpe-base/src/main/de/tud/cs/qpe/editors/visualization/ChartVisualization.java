package de.tud.cs.qpe.editors.visualization;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;

public abstract class ChartVisualization extends Visualization {
	public class ChartVisualizationComponent implements VisualizationComponent {

		private final JFreeChart chart;

		public ChartVisualizationComponent(JFreeChart chart) {
			this.chart = chart;
		}
		
		@Override
		public Composite realize(Composite parent) {
			return new ChartComposite(parent, SWT.NONE, this.chart, true);
		}
	}
}
