package de.tud.cs.qpe.editors.incidence.view.figure;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import de.tud.cs.qpe.editors.incidence.view.anchor.ModeAnchor;
import de.tud.cs.qpe.editors.net.view.figure.BaseFigure;

public class ColorRefFigure extends BaseFigure {

	public static final int componentHeight = 40;

	public static final int componentWidth = 40;

	protected ConnectionAnchor anchor;
	
	public ColorRefFigure() {
		super();
		ToolbarLayout layout = new ToolbarLayout(true);
		layout.setStretchMinorAxis(false);
		layout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);
		layout.setSpacing(2);
		setLayoutManager(layout);
	}
	
	protected Figure createFigure() {
		Figure figure = new Ellipse();
		Rectangle bounds = figure.getBounds();
		bounds.setSize(componentWidth, componentHeight);
		figure.setBounds(bounds);
		return figure;
	}

	public ConnectionAnchor getConnectionAnchor() {
		if (anchor == null) {
			anchor = new ModeAnchor(figure);
		}
		return anchor;
	}

	public void setRealColor(RGB realColor) {
		((Ellipse)figure).setBackgroundColor(new Color(null, realColor));
	}
	
	public RGB getRealColor() {
		return ((Ellipse)figure).getBackgroundColor().getRGB();
	}
}
