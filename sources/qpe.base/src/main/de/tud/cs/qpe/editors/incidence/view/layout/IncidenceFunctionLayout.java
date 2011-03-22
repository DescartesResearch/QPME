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
package de.tud.cs.qpe.editors.incidence.view.layout;

import java.util.Iterator;

import org.eclipse.draw2d.AbstractLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import de.tud.cs.qpe.editors.incidence.view.figure.ModeFigure;
import de.tud.cs.qpe.editors.incidence.view.figure.PlaceFigure;

public class IncidenceFunctionLayout extends AbstractLayout {

	protected int margin = 20;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.AbstractLayout#calculatePreferredSize(org.eclipse.draw2d.IFigure,
	 *      int, int)
	 */
	@Override
	protected Dimension calculatePreferredSize(IFigure container, int wHint, int hHint) {
		Dimension minSize = getMinimumSize(container, wHint, hHint);
		return new Dimension(Math.max(minSize.width, wHint), Math.max(minSize.height, hHint));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.AbstractLayout#getMinimumSize(org.eclipse.draw2d.IFigure,
	 *      int, int)
	 */
	@Override
	public Dimension getMinimumSize(IFigure container, int wHint, int hHint) {
		Dimension minimumSize = new Dimension();

		Dimension inputPlacePartSize = getInputPartSize(container);
		Dimension modePartSize = getModePartSize(container);
		Dimension outputPlacePartSize = getOutputPartSize(container);

		minimumSize.width = margin + inputPlacePartSize.width + 5 * margin + modePartSize.width + 5 * margin + outputPlacePartSize.width;
		minimumSize.height = margin + Math.max(Math.max(inputPlacePartSize.height, modePartSize.height), outputPlacePartSize.height) + margin;

		return minimumSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.LayoutManager#layout(org.eclipse.draw2d.IFigure)
	 */
	public void layout(IFigure container) {
		Rectangle clientArea = container.getClientArea();
		if ((clientArea.width != 0) && (clientArea.height != 0)) {
			// Calculate sizes.
			int inputMargin = 0;
			int inputHeight = 0;
			int inputElementCounter = 0;
			int modeMargin = 0;
			int modeHeight = 0;
			int modeElementCounter = 0;
			int outputMargin = 0;
			int outputHeight = 0;
			int outputElementCounter = 0;

			Iterator figureIterator = container.getChildren().iterator();
			while (figureIterator.hasNext()) {
				IFigure figure = (IFigure) figureIterator.next();
				figure.setSize(figure.getPreferredSize());
				if (figure instanceof PlaceFigure) {
					PlaceFigure castedFigure = (PlaceFigure) figure;
					if(castedFigure.getType() == PlaceFigure.TYPE_INPUT_PLACE) {
						inputHeight += figure.getPreferredSize().height;
						inputElementCounter++;
					} else {
						outputHeight += figure.getPreferredSize().height;
						outputElementCounter++;
					}
				} else if (figure instanceof ModeFigure) {
					modeHeight += figure.getSize().height;
					modeElementCounter++;
				}
			}
			inputMargin = Math.max(margin, (clientArea.height - inputHeight) / (inputElementCounter + 1));
			modeMargin = Math.max(margin, (clientArea.height - modeHeight) / (modeElementCounter + 1));
			outputMargin = Math.max(margin, (clientArea.height - outputHeight) / (outputElementCounter + 1));

			int maxHeight = Math.max(Math.max(inputHeight, modeHeight), outputMargin);
			if (maxHeight == inputHeight) {
				maxHeight = inputMargin + maxHeight + inputMargin;
			} else if (maxHeight == modeHeight) {
				maxHeight = modeMargin + maxHeight + modeMargin;
			} else {
				maxHeight = outputMargin + maxHeight + outputMargin;
			}

			// Do the actual layouting.

			Dimension inputPlacePartSize = getInputPartSize(container);
			Dimension outputPlacePartSize = getOutputPartSize(container);

			Point inputPlacePoint = new Point();
			inputPlacePoint.x = clientArea.x + margin + (inputPlacePartSize.width / 2);
			inputPlacePoint.y = clientArea.y + inputMargin;
			Point modePoint = new Point();
			modePoint.x = clientArea.x + clientArea.width / 2;
			modePoint.y = clientArea.y + modeMargin;
			Point outputPlacePoint = new Point();
			outputPlacePoint.x = clientArea.x + clientArea.width - (margin + (outputPlacePartSize.width / 2));
			outputPlacePoint.y = clientArea.y + outputMargin;

			// Do the actual layouting.
			figureIterator = container.getChildren().iterator();
			while (figureIterator.hasNext()) {
				IFigure figure = (IFigure) figureIterator.next();
				Rectangle newBounds = figure.getBounds();

				if (figure instanceof PlaceFigure) {
					PlaceFigure castedFigure = (PlaceFigure) figure;
					if(castedFigure.getType() == PlaceFigure.TYPE_INPUT_PLACE) {
						newBounds.x = inputPlacePoint.x - (figure.getPreferredSize().width / 2);
						newBounds.y = inputPlacePoint.y;
						inputPlacePoint.y += newBounds.height + inputMargin;
					} else {
						newBounds.x = outputPlacePoint.x - (figure.getPreferredSize().width / 2);
						newBounds.y = outputPlacePoint.y;
						outputPlacePoint.y += newBounds.height + outputMargin;
					}
				} else if (figure instanceof ModeFigure) {
					newBounds.x = modePoint.x - (figure.getSize().width / 2);
					newBounds.y = modePoint.y;
					modePoint.y += newBounds.height + modeMargin;
				}

				figure.setBounds(newBounds);
				// Since the position might have changed. Relayout
				// the colors of the current place.
				if (figure.getLayoutManager() != null) {
					figure.getLayoutManager().layout(figure);
				}
			}
		}
	}

	protected Dimension getInputPartSize(IFigure container) {
		Dimension size = new Dimension();
		Iterator figureIterator = container.getChildren().iterator();
		while (figureIterator.hasNext()) {
			IFigure figure = (IFigure) figureIterator.next();
			if (figure instanceof PlaceFigure) {
				PlaceFigure castedFigure = (PlaceFigure) figure;
				if(castedFigure.getType() == PlaceFigure.TYPE_INPUT_PLACE) {
					if (figure.getPreferredSize().width > size.width) {
						size.width = figure.getPreferredSize().width;
					}
					size.height += margin + figure.getPreferredSize().height;
				}
			}
		}
		return size;
	}

	protected Dimension getModePartSize(IFigure container) {
		Dimension size = new Dimension();
		Iterator figureIterator = container.getChildren().iterator();
		while (figureIterator.hasNext()) {
			IFigure figure = (IFigure) figureIterator.next();
			if (figure instanceof ModeFigure) {
				if (figure.getPreferredSize().width > size.width) {
					size.width = figure.getPreferredSize().width;
				}
				size.height += margin + figure.getPreferredSize().height;
			}
		}
		return size;
	}

	protected Dimension getOutputPartSize(IFigure container) {
		Dimension size = new Dimension();
		Iterator figureIterator = container.getChildren().iterator();
		while (figureIterator.hasNext()) {
			IFigure figure = (IFigure) figureIterator.next();
			if (figure instanceof PlaceFigure) {
				PlaceFigure castedFigure = (PlaceFigure) figure;
				if(castedFigure.getType() == PlaceFigure.TYPE_OUTPUT_PLACE) {
					if (figure.getPreferredSize().width > size.width) {
						size.width = figure.getPreferredSize().width;
					}
					size.height += margin + figure.getPreferredSize().height;
				}
			}
		}
		return size;
	}
}
