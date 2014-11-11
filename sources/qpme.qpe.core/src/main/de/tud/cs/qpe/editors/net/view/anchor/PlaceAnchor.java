/* ==============================================
 * QPME : Queueing Petri net Modeling Environment
 * ==============================================
 *
 * (c) Copyright 2003-2011, by Samuel Kounev and Contributors.
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
