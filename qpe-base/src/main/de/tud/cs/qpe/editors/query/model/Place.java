package de.tud.cs.qpe.editors.query.model;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Place implements Comparable<Place> {
	
	private final String name;
	private final String id;
	private final String type;
	private final int statsLevel;
	private final Map<Metric, MetricValue> metricValues;
	private final Map<Color, Map<Metric, MetricValue>> colorMetricValues;
	private final Map<Color, Map<HistogramType, Histogram>> colorHistograms;

	public Place(String name, String id, String type, int statsLevel,
					Map<Metric, MetricValue> metricValues,
					Map<Color, Map<Metric, MetricValue>> colorMetricValues,
					Map<Color, Map<HistogramType, Histogram>> colorHistograms) {
		this.name = name;
		this.id = id;
		this.type = type;
		this.statsLevel = statsLevel;
		this.metricValues = metricValues;
		this.colorMetricValues = colorMetricValues;
		this.colorHistograms = colorHistograms;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getId() {
		return this.id;
	}

	public String getType() {
		return this.type;
	}
	
	public int getStatsLevel() {
		return this.statsLevel;
	}
	
	public Map<HistogramType, Histogram> getHistograms(Color color) {
		return this.colorHistograms.get(color);
	}

	public Histogram getHistogram(Color color, HistogramType type) {
		Map<HistogramType, Histogram> histograms = getHistograms(color);
		if (histograms != null) {
			return histograms.get(type);
		}
		return null;
	}

	public Map<Metric, MetricValue> getMetricValues() {
		return this.metricValues;
	}

	public Map<Metric, MetricValue> getMetricValues(Color color) {
		return this.colorMetricValues.get(color);
	}

	public MetricValue getMetricValue(Color color, Metric metric) {
		Map<Metric, MetricValue> mvalues = getMetricValues(color);
		if (mvalues != null) {
			return mvalues.get(metric);
		}
		return null;
	}

	public MetricValue getMetricValue(Metric metric) {
		if (this.metricValues != null) {
			return this.metricValues.get(metric);
		}
		return null;
	}

	public Set<Color> getColors() {
		if (this.colorMetricValues.keySet() != null) {
			return this.colorMetricValues.keySet();
		}
		return new HashSet<Color>();
	}

	public boolean hasMetric(Metric metric) {
		return metricValues.keySet().contains(metric) || colorMetricValues.keySet().contains(metric);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Place other = (Place) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getName() + " (" + getType() + ")";
	}
	
	@Override
	public int compareTo(Place o) {
		return this.name.compareTo(o.getName());
	}

	public boolean hasColors() {
		return !this.colorMetricValues.isEmpty();
	}
}
