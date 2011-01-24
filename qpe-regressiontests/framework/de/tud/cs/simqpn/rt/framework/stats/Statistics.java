package de.tud.cs.simqpn.rt.framework.stats;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class Statistics {
	
	private String name;
	private Map<String, Metric> metrics;
	
	public Statistics(String name) {
		this.name = name;
		metrics = new HashMap<String, Metric>();
	}
	
	public String getName() {
		return name;
	}
	
	public void addMetric(Metric metric) {
		metrics.put(metric.getName(), metric);
	}
	
	public Metric getMetric(String name) {
		return metrics.get(name);
	}
	
	public Metric getOrCreateMetric(String name) {
		Metric metric = metrics.get(name);
		if (metric == null) {
			metric = new Metric(name);
			metrics.put(name, metric);
		}
		return metric;
	}
	
	public Collection<Metric> getMetrics() {
		return metrics.values();
	}
	
}
