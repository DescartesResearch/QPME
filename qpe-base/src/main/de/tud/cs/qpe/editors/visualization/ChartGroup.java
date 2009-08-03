package de.tud.cs.qpe.editors.visualization;

import java.util.ArrayList;

public class ChartGroup extends ArrayList<SizedVisualizationComponent> {
	
	private static final long serialVersionUID = -433536224085589036L;

	private final String title;

	public ChartGroup(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}
}
