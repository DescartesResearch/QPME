/**
 * 
 */
package de.tud.cs.qpe.editors.net.view.anchor;

import org.eclipse.draw2d.EllipseAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import de.tud.cs.qpe.editors.net.view.figure.place.PlaceFigure;

/**
 * @author cdutz
 *
 */
public class PlaceAnchor extends EllipseAnchor {
	
	public PlaceAnchor() {
		super();
	}
	
	public PlaceAnchor(IFigure owner) {
		super(owner);
	}
	
	/**
	 * Returns a point on the ellipse (defined by the owner's bounding box) where the
	 * connection should be anchored.
	 * @see org.eclipse.draw2d.ConnectionAnchor#getLocation(Point)
	 */
	public Point getLocation(Point reference) {
		Rectangle r = Rectangle.SINGLETON;
		r.setBounds(getOwner().getBounds());
		r.width = PlaceFigure.componentWidth;
		r.height = PlaceFigure.componentHeight;
		r.translate(-1, -1);
		r.resize(1, 1);
		getOwner().translateToAbsolute(r);
		
		Point ref = r.getCenter().negate().translate(reference);
		
		if (ref.x == 0)
			return new Point(reference.x, (ref.y > 0) ? r.bottom() : r.y);
		if (ref.y == 0)
			return new Point((ref.x > 0) ? r.right() : r.x, reference.y);
		
		float dx = (ref.x > 0) ? 0.5f : -0.5f;
		float dy = (ref.y > 0) ? 0.5f : -0.5f;
		  
		// ref.x, ref.y, r.width, r.height != 0 => safe to proceed
		  
		float k = (float)(ref.y * r.width) / (ref.x * r.height);
		k = k * k;
		  
		return r.getCenter().translate(1,1).translate((int)(r.width * dx / Math.sqrt(1 + k)), (int)(r.height * dy / Math.sqrt(1 + 1 / k)));
	}
}
