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
package de.tud.cs.qpe.editors.net.view.figure;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.handles.HandleBounds;

public abstract class BaseFigure extends Figure implements
		HandleBounds {
	protected Figure figure;

	private Label name;
	
	public BaseFigure() {
		ToolbarLayout layout = new ToolbarLayout();
		layout.setStretchMinorAxis(false);
		layout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);
		layout.setSpacing(2);
		setLayoutManager(layout);

		figure = createFigure();
		name = new Label();

		baseAdd(figure);
		baseAdd(name);
	}
	
	protected abstract Figure createFigure();

	public abstract ConnectionAnchor getConnectionAnchor();

	public void setName(String name) {
		this.name.setText(name);
	}

	public String getName() {
		return name.getText();
	}

	public void setBounds(Rectangle newBounds) {
		super.setBounds(newBounds);
		
		// Make Eclipse repaint the current figure
		invalidate();
		// If this is missing the relics of the figure
		// remain at the old figures position.
		if(getParent() != null) {
			getParent().repaint();
		}
		// TODO: Something is seting the figures bounds without the setBounds methods. This is why I have to manually fire a fígureMoved.
		fireFigureMoved();
	}
	
	public void baseAdd(IFigure figure) {
		super.add(figure, null, -1);
	}

	public Rectangle getHandleBounds() {
		return figure.getBounds();
	}
}
