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
		return getMinimumSize(container, wHint, hHint);
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
		Dimension modePartSize = getInputPartSize(container);
		Dimension outputPlacePartSize = getInputPartSize(container);

		minimumSize.width = margin + inputPlacePartSize.width + 2 * margin + modePartSize.width + 2 * margin + outputPlacePartSize.width;
		minimumSize.height = margin + Math.max(Math.max(inputPlacePartSize.height, modePartSize.height), outputPlacePartSize.height) + margin;

		return minimumSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.LayoutManager#layout(org.eclipse.draw2d.IFigure)
	 */
	public void layout(IFigure container) {
		Dimension totalSize = new Dimension();
		totalSize.width = container.getBounds().width;
		totalSize.height = container.getBounds().height;
		if ((totalSize.width != 0) && (totalSize.height != 0)) {
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
			inputMargin = Math.max(margin, (totalSize.height - inputHeight) / (inputElementCounter + 1));
			modeMargin = Math.max(margin, (totalSize.height - modeHeight) / (modeElementCounter + 1));
			outputMargin = Math.max(margin, (totalSize.height - outputHeight) / (outputElementCounter + 1));

			int maxHeight = Math.max(Math.max(inputHeight, modeHeight), outputMargin);
			if (maxHeight == inputHeight) {
				maxHeight = inputMargin + maxHeight + inputMargin;
			} else if (maxHeight == modeHeight) {
				maxHeight = modeMargin + maxHeight + modeMargin;
			} else {
				maxHeight = outputMargin + maxHeight + outputMargin;
			}
			// Eventually update the IncidenceFunctionLayers size.
			if (maxHeight > totalSize.height) {
				container.setBounds(new Rectangle(0, 0, totalSize.width, totalSize.height));
			}

			// Do the actual layouting.

			Dimension inputPlacePartSize = getInputPartSize(container);
			Dimension outputPlacePartSize = getOutputPartSize(container);

			Point inputPlacePoint = new Point();
			inputPlacePoint.x = margin + (inputPlacePartSize.width / 2);
			inputPlacePoint.y = inputMargin;
			Point modePoint = new Point();
			modePoint.x = totalSize.width / 2;
			modePoint.y = modeMargin;
			Point outputPlacePoint = new Point();
			outputPlacePoint.x = totalSize.width - (margin + (outputPlacePartSize.width / 2));
			outputPlacePoint.y = outputMargin;

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
					if (figure.getBounds().width > size.width) {
						size.width = figure.getBounds().width;
					}
					size.height += margin + figure.getBounds().height;
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
				if (figure.getBounds().width > size.width) {
					size.width = figure.getBounds().width;
				}
				size.height += margin + figure.getBounds().height;
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
					if (figure.getBounds().width > size.width) {
						size.width = figure.getBounds().width;
					}
					size.height += margin + figure.getBounds().height;
				}
			}
		}
		return size;
	}
}
