package de.tud.cs.qpe.editors.query.model;

import java.util.List;

/**
 * Represents an aggregation method for metric values.
 * 
 * @author Frederik Zipp
 *
 */
public enum Aggregation {
	FOR_EACH("For each") {
		@Override
		public MetricValue aggregate(List<MetricValue> values) {
			throw new UnsupportedOperationException();
		}
	},
	AVERAGE("Average") {
		@Override
		public MetricValue aggregate(List<MetricValue> values) {
			if (values.isEmpty()) {
				return null;
			}
			return SUM.aggregate(values).divide(values.size());
		}
	},
	SUM("Sum") {
		@Override
		public MetricValue aggregate(List<MetricValue> values) {
			if (values.isEmpty() || values == null) {
				return null;
			}
			MetricValue sum = new MetricValue(values.get(0).getMetric(), 0);
			for (MetricValue value : values) {
				sum = sum.plus(value);
			}
			return sum;
		}
	};
	
	private final String name;

	/**
	 * Creates a new aggregation.
	 * 
	 * @param name	a human readable name of the aggregation
	 */
	private Aggregation(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the name of the aggregation.
	 * 
	 * @return	The name of the aggregation
	 */
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return getName();
	}

	/**
	 * Aggregates multiple metric values to a single metric value. 
	 * 
	 * @param values	the metric values to be aggregated
	 * @return			the aggregated metric value,
	 * 					null if 'values' is empty or null
	 */
	public abstract MetricValue aggregate(List<MetricValue> values);
}
