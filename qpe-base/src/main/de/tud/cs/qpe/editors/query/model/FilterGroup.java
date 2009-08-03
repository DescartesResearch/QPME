package de.tud.cs.qpe.editors.query.model;

public class FilterGroup<T> {
	
	private final Aggregation[] aggregations;
	private final String name;
	private final T[] items;

	public FilterGroup(String name, T[] items, Aggregation[] aggregations) {
		this.name = name;
		this.items = items;
		this.aggregations = aggregations;
	}
	
	public Aggregation[] getAggregations() {
		return this.aggregations; 
	}
	
	public String getName() {
		return this.name;
	}
	
	public T[] getItems() {
		return this.items;
	}
}
