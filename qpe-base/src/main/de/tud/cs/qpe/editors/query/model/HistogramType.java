package de.tud.cs.qpe.editors.query.model;

public enum HistogramType {
	CONFIDENCE_INTERVAL("Residence Time", "histST");
	
	private final String name;
	private final String type;

	private HistogramType(String name, String type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getType() {
		return this.type;
	}
	
	public static HistogramType fromType(String type) {
		for (HistogramType histogramType : values()) {
			if (histogramType.type.equals(type)) {
				return histogramType;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
