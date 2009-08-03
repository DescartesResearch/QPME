package de.tud.cs.qpe.editors.visualization;

import org.eclipse.draw2d.geometry.Dimension;

public class SizedVisualizationComponent {
	private VisualizationComponent visualizationComponent;
	private Dimension size;

	public SizedVisualizationComponent(VisualizationComponent visualizationComponent, Dimension size) {
		this.visualizationComponent = visualizationComponent;
		this.size = size;
	}

	public VisualizationComponent getVisualizationComponent() {
		return this.visualizationComponent;
	}

	public Dimension getSize() {
		return this.size;
	}
}
