package de.tud.cs.qpe.editors.incidence.view.figure;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.swt.graphics.Color;

import de.tud.cs.qpe.editors.net.view.figure.BaseFigure;

public class PlaceFigure extends BaseFigure {
	public static final int TYPE_INPUT_PLACE = 1;

	public static final int TYPE_OUTPUT_PLACE = 2;

	protected int type;

	protected Figure createFigure() {
		Figure figure = new RectangleFigure();

		ToolbarLayout layout = new ToolbarLayout();
		layout.setStretchMinorAxis(false);
		layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
		layout.setSpacing(6);
		figure.setLayoutManager(layout);

		figure.setBackgroundColor(new Color(null, 255, 255, 160));
		figure.setOpaque(true);

		return figure;
	}

	public void add(IFigure figure, Object constraint, int index) {
		this.figure.add(figure, constraint, index);
	}

	public ConnectionAnchor getConnectionAnchor() {
		return null;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
