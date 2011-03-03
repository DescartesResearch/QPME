/* ==============================================
 * QPME : Queueing Petri net Modeling Environment
 * ==============================================
 *
 * (c) Copyright 2003-2010, by Samuel Kounev and Contributors.
 * 
 * Project Info:   http://descartes.ipd.kit.edu/projects/qpme/
 *                 http://www.descartes-research.net/
 *    
 * All rights reserved. This software is made available under the terms of the 
 * Eclipse Public License (EPL) v1.0 as published by the Eclipse Foundation
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * This software is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the Eclipse Public License (EPL)
 * for more details.
 *
 * You should have received a copy of the Eclipse Public License (EPL)
 * along with this software; if not visit http://www.eclipse.org or write to
 * Eclipse Foundation, Inc., 308 SW First Avenue, Suite 110, Portland, 97204 USA
 * Email: license (at) eclipse.org 
 *  
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *                                
 * =============================================
 *
 * Original Author(s):  Samuel Kounev and Christofer Dutz
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2006        Christofer Dutz   Created.
 * 
 */
package de.tud.cs.qpe.utils.view.anchor;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

public class ModeAnchor extends AbstractConnectionAnchor {

	/**
	 * Constructs a new ChopboxAnchor.
	 */
	protected ModeAnchor() {
	}

	/**
	 * Constructs a ChopboxAnchor with the given <i>owner</i> figure.
	 * 
	 * @param owner
	 *            The owner figure
	 * @since 2.0
	 */
	public ModeAnchor(IFigure owner) {
		super(owner);
	}

	/**
	 * Gets a Rectangle from {@link #getBox()} and returns the Point where a
	 * line from the center of the Rectangle to the Point <i>reference</i>
	 * intersects the Rectangle.
	 * 
	 * @param reference
	 *            The reference point
	 * @return The anchor location
	 */
	public Point getLocation(Point reference) {
		Rectangle r = Rectangle.SINGLETON;
		r.setBounds(getBox());
		r.resize(1, 1);

		// Get the center of the Figure
		getOwner().translateToAbsolute(r);
		Point center = new Point(r.x + 0.5f * r.width, r.y + 0.5f * r.height);

		// Calculate the relative position between center and mouse.
		float dx = reference.x - center.x;
		float dy = reference.y - center.y;

		// Since the pitch of a vertical line is equal to infinity,
		// we simply catch this case with a special case.
		// (Avoids division by 0)
		if (dy == 0) {
			if (dx > 0) {
				return new Point(Math.round(center.x + r.width / 2), Math.round(center.y));
			}	
			return new Point(Math.round(center.x - r.width / 2), Math.round(center.y));
		}

		// Calcuate pitch and offset of the line from the center to the
		// target.
		float fPitch = dy / dx;
		float fOffset = center.y - (center.x * fPitch);
		
		// Calcuate pitch and offset of the line representing the
		// figures border.
		float uPitch;
		float uOffset;
		
		if (dx > 0 && dy > 0) {
			uPitch = -1;
			uOffset = center.y - (uPitch * center.x) + (r.height / 2); 
		} else if (dx > 0 && dy < 0) {
			uPitch = 1;
			uOffset = center.y - (uPitch * center.x) - (r.height / 2);
		} else if (dx < 0 && dy < 0) {
			uPitch = -1;
			uOffset = center.y - (uPitch * center.x) - (r.height / 2);
		} else if (dx < 0 && dy > 0) {
			uPitch = 1;
			uOffset = center.y - (uPitch * center.x) + (r.height / 2); 
		} 

		// Since we allready cought the case dy = 0 dx = 0 is the
		// only possible remaining case.
		else {
			if (dy > 0) {
				return new Point(Math.round(center.x), Math.round(center.y + r.height / 2));
			}
			return new Point(Math.round(center.x), Math.round(center.y - r.height / 2));
		}

		float resultX = (fOffset - uOffset) / (uPitch - fPitch);
		float resultY = fPitch * resultX + fOffset;
		
		return new Point(Math.round(resultX), Math.round(resultY));
	}

	/**
	 * Returns the bounds of this ChopboxAnchor's owner. Subclasses can override
	 * this method to adjust the box the anchor can be placed on. For instance,
	 * the owner figure may have a drop shadow that should not be included in
	 * the box.
	 * 
	 * @return The bounds of this ChopboxAnchor's owner
	 * @since 2.0
	 */
	protected Rectangle getBox() {
		return getOwner().getBounds();
	}

	/**
	 * Returns the anchor's reference point. In the case of the ChopboxAnchor,
	 * this is the center of the anchor's owner.
	 * 
	 * @return The reference point
	 */
	public Point getReferencePoint() {
		Point ref = getBox().getCenter();
		getOwner().translateToAbsolute(ref);
		return ref;
	}

	/**
	 * Returns <code>true</code> if the other anchor has the same owner and
	 * box.
	 * 
	 * @param obj
	 *            the other anchor
	 * @return <code>true</code> if equal
	 */
	public boolean equals(Object obj) {
		if (obj instanceof ModeAnchor) {
			ModeAnchor other = (ModeAnchor) obj;
			return other.getOwner() == getOwner() && other.getBox().equals(getBox());
		}
		return false;
	}

	/**
	 * The owning figure's hashcode is used since equality is approximately
	 * based on the owner.
	 * 
	 * @return the hash code.
	 */
	public int hashCode() {
		if (getOwner() != null)
			return getOwner().hashCode();
		return super.hashCode();
	}
}
