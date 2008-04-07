package de.tud.cs.qpe.editors.incidence.controller.editpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.XPath;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import de.tud.cs.qpe.editors.incidence.model.InputPlaceWrapper;
import de.tud.cs.qpe.editors.incidence.model.PlaceWrapper;
import de.tud.cs.qpe.editors.incidence.view.figure.PlaceFigure;
import de.tud.cs.qpe.model.DocumentManager;

public class PlaceEditPart extends AbstractGraphicalEditPart implements
		PropertyChangeListener {
	protected PlaceFigure view;

	public PlaceEditPart() {
		super();
	}

	/**
	 * Upon activation, attach to the model element as a property change
	 * listener.
	 */
	public void activate() {
		if (!isActive()) {
			super.activate();
			DocumentManager.addPropertyChangeListener(getCastedModel()
					.getElement(), this);
		}
	}

	public void addChild(EditPart part, int index) {
		super.addChild(part, index);
	}
	
	/**
	 * Upon deactivation, detach from the model element as a property change
	 * listener.
	 */
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			DocumentManager.removePropertyChangeListener(getCastedModel()
					.getElement(), this);
		}
	}

	/**
	 * Places are just simple containers for color refs so they are not edited
	 * at all.
	 */
	protected void createEditPolicies() {
	}

	public int getType() {
		if (getModel() instanceof InputPlaceWrapper) {
			return PlaceFigure.TYPE_INPUT_PLACE;
		}
		return PlaceFigure.TYPE_OUTPUT_PLACE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	protected IFigure createFigure() {
		view = new PlaceFigure();
		view.setType(getType());
		view.setName(((PlaceWrapper) getModel()).getElement().attributeValue(
				"name", "new place"));
		return view;
	}

	protected PlaceWrapper getCastedModel() {
		return (PlaceWrapper) getModel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	protected List getModelChildren() {
		XPath xpathSelector = DocumentHelper
				.createXPath("color-refs/color-ref");
		return xpathSelector.selectNodes(getCastedModel().getElement());
	}

	public void propertyChange(PropertyChangeEvent evt) {
		refreshChildren();
		refreshVisuals();
	}
}
