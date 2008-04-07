package de.tud.cs.qpe.editors.net.view.figure.place;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

public class SubnetPlaceFigure extends QueueingPlaceFigure {
	protected Figure createFigure() {
		Figure figure = new SubnetPlace();
		Rectangle bounds = figure.getBounds();
		bounds.setSize(componentWidth, componentHeight);
		figure.setBounds(bounds);
		return figure;
	}
	
	protected class SubnetPlace extends QueueingPlace {
		protected SubnetPlace() {
			super();
			setLineWidth(3);
		}
		
		protected void paintClientArea(Graphics graphics) {
			graphics.setLineWidth(3);
			super.paintClientArea(graphics);
		}
	}
}
