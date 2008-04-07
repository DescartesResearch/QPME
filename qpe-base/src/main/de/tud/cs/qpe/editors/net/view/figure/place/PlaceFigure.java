package de.tud.cs.qpe.editors.net.view.figure.place;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.geometry.Rectangle;

import de.tud.cs.qpe.editors.net.view.anchor.PlaceAnchor;
import de.tud.cs.qpe.editors.net.view.figure.BaseFigure;

public abstract class PlaceFigure extends BaseFigure {
	public static final int componentHeight = 40;

	public static final int componentWidth = 40;

	protected ConnectionAnchor anchor;

	protected Figure createFigure() {
		Figure figure = new Place();
		Rectangle bounds = figure.getBounds();
		bounds.setSize(componentWidth, componentHeight);
		figure.setBounds(bounds);
		return figure;
	}

	public ConnectionAnchor getConnectionAnchor() {
		if (anchor == null) {
			anchor = new PlaceAnchor(this.figure);
		}
		return anchor;
	}

	protected class Place extends Ellipse {
		protected Place() {
			setLineWidth(1);
			setOpaque(true);
			setBackgroundColor(ColorConstants.green);
		}
	}
}
