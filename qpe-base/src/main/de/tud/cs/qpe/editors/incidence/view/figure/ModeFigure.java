package de.tud.cs.qpe.editors.incidence.view.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import de.tud.cs.qpe.editors.incidence.view.anchor.ModeAnchor;
import de.tud.cs.qpe.editors.net.view.figure.BaseFigure;

public class ModeFigure extends BaseFigure {

	public static final int componentHeight = 40;

	public static final int componentWidth = 40;

	protected ConnectionAnchor anchor;
	
	protected Figure createFigure() {
		Figure figure = new Mode();
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
		((Mode)figure).setRealColor(realColor);
	}
	
	public RGB getRealColor() {
		return ((Mode)figure).getRealColor();
	}
	
	protected class Mode extends RectangleFigure {
		private PointList corners;

		protected RGB realColor;

		protected Mode() {
			setLineWidth(1);
			setOpaque(true);
			setBackgroundColor(ColorConstants.green);
		}

		public void setRealColor(RGB realColor) {
			this.realColor = realColor;
		}
		
		public RGB getRealColor() {
			return realColor;
		}
		
		protected void fillShape(Graphics graphics) {
			graphics.setBackgroundColor(new Color(null, realColor));
			graphics.fillPolygon(corners);
		}

		protected void outlineShape(Graphics graphics) {
			graphics.drawPolygon(corners);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.draw2d.Figure#setBounds(org.eclipse.draw2d.geometry.Rectangle)
		 */
		@Override
		public void setBounds(Rectangle rect) {
			super.setBounds(rect);
			corners = new PointList();
			// Top.
			corners.addPoint(this.getLocation().x + (getBounds().width / 2) - 1, this.getLocation().y);
			// Left.
			corners.addPoint(this.getLocation().x, this.getLocation().y + (getBounds().height / 2) - 1);
			// Bottom.
			corners.addPoint(this.getLocation().x + (getBounds().width / 2) - 1, this.getLocation().y + getBounds().height - 2);
			// Right.
			corners.addPoint(this.getLocation().x + getBounds().width - 2, this.getLocation().y + (getBounds().height / 2) - 1);		
		}
	}
}
