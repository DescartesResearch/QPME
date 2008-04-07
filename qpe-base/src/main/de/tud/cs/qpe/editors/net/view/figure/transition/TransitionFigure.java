package de.tud.cs.qpe.editors.net.view.figure.transition;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Rectangle;

import de.tud.cs.qpe.editors.net.view.anchor.TransitionAnchor;
import de.tud.cs.qpe.editors.net.view.figure.BaseFigure;

public abstract class TransitionFigure extends BaseFigure {
	public static final int componentHeight = 40;

	public static final int componentWidth = 20;

	protected ConnectionAnchor anchor;

	protected Figure createFigure() {
		RectangleFigure figure = new RectangleFigure();
		Rectangle bounds = figure.getBounds();
		bounds.setSize(componentWidth, componentHeight);
		figure.setBounds(bounds);
		return figure;
	}

	public ConnectionAnchor getConnectionAnchor() {
		if (anchor == null) {
			anchor = new TransitionAnchor(this.figure);
		}
		return anchor;
	}
}
