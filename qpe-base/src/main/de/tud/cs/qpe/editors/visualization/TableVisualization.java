package de.tud.cs.qpe.editors.visualization;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;

import de.tud.cs.qpe.editors.query.model.MetricValue;

public class TableVisualization extends Visualization {

	private final NumberFormat FORMAT = new DecimalFormat();
	
	@Override
	public VisualizationComponent createChart(String title, Map<? extends Object, MetricValue> aggregatedValues) {
		System.out.println();
		System.out.println(title);
		System.out.println(repeat ("-", title.length()));
		for (Object key : aggregatedValues.keySet()) {
			System.out.println(key.toString() + ": " + FORMAT.format(aggregatedValues.get(key).getValue()));
		}
		return null;
	}

	private String repeat(String string, int times) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < times; i++) {
			builder.append(string);
		}
		return builder.toString();
	}

	@Override
	public String getId() {
		return "table";
	}

	@Override
	public String getName() {
		return "Console Output";
	}
}
