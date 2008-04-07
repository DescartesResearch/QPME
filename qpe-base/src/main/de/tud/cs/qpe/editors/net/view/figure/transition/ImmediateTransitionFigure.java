package de.tud.cs.qpe.editors.net.view.figure.transition;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;

public class ImmediateTransitionFigure extends TransitionFigure {
	protected Figure createFigure() {
		Figure figure = super.createFigure();
		figure.setBackgroundColor(ColorConstants.black);
		return figure;
	}
}
