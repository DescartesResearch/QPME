package de.tud.cs.qpe.editors.net.view.figure.place;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

public class QueueingPlaceFigure extends PlaceFigure {
	protected Figure createFigure() {
		Figure figure = new QueueingPlace();
		Rectangle bounds = figure.getBounds();
		bounds.setSize(componentWidth, componentHeight);
		figure.setBounds(bounds);
		return figure;
	}
	
	protected class QueueingPlace extends Place {
		protected QueueingPlace() {
			super();
		}

		protected void paintClientArea(Graphics graphics) {
			graphics.drawLine(bounds.x + (componentWidth / 2), bounds.y, bounds.x + (componentWidth / 2), bounds.y + componentHeight);
		}
	}
}
