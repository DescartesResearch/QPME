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
