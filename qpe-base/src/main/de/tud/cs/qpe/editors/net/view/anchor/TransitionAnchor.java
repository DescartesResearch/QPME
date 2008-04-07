package de.tud.cs.qpe.editors.net.view.anchor;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.handles.HandleBounds;

public class TransitionAnchor extends ChopboxAnchor {
	
	public TransitionAnchor() {
		super();
	}
	
	public TransitionAnchor(IFigure owner) {
		super(owner);
	}
	
	/**
	 * Returns the bounds of this ChopboxAnchor's owner.  Subclasses can override this method
	 * to adjust the box the anchor can be placed on.  For instance, the owner figure may have
	 * a drop shadow that should not be included in the box. 
	 *  
	 * @return The bounds of this ChopboxAnchor's owner
	 * @since 2.0
	 */
	protected Rectangle getBox() {
		if( getOwner() instanceof HandleBounds) {
			return ((HandleBounds) getOwner()).getHandleBounds();
		}
		return getOwner().getBounds();
	}
}
