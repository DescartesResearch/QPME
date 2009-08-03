package de.tud.cs.qpe.editors.query.model;

public final class MetricValue {

	private final Metric metric;
	private final double value;

	public MetricValue(Metric metric, double value) {
		this.metric = metric;
		this.value = value;
	}

	public Metric getMetric() {
		return this.metric;
	}

	public double getValue() {
		return this.value;
	}
	
	public MetricValue plus(MetricValue that) {
		if (!this.metric.equals(that.metric)) {
			throw new IllegalArgumentException("Metric values must be of same metric");
		}
		return new MetricValue(this.metric, this.value + that.getValue());
	}

	public MetricValue divide(double x) {
		return new MetricValue(this.metric, this.value / x);
	}
}
