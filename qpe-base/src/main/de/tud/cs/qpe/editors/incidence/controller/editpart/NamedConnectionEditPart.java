package de.tud.cs.qpe.editors.incidence.controller.editpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tud.cs.qpe.editors.incidence.controller.command.ConnectionDeleteCommand;
import de.tud.cs.qpe.editors.incidence.view.figure.NamedConnectionFigure;
import de.tud.cs.qpe.model.DocumentManager;

public class NamedConnectionEditPart extends AbstractConnectionEditPart
		implements PropertyChangeListener {

	/**
	 * Upon activation, attach to the model element as a property change
	 * listener.
	 */
	public void activate() {
		if (!isActive()) {
			super.activate();
			DocumentManager.addPropertyChangeListener(getCastedModel(), this);
		}
	}

	/**
	 * Upon deactivation, detach from the model element as a property change
	 * listener.
	 */
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			DocumentManager
					.removePropertyChangeListener(getCastedModel(), this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	protected void createEditPolicies() {
		// Selection handle edit policy.
		// Makes the connection show a feedback, when selected by the user.
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE,
				new ConnectionEndpointEditPolicy());
		// Allows the removal of the connection model element
		installEditPolicy(EditPolicy.CONNECTION_ROLE,
				new ConnectionEditPolicy() {
					protected Command getDeleteCommand(GroupRequest request) {
						return new ConnectionDeleteCommand(getCastedModel());
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	protected IFigure createFigure() {
		if (figure == null) {
			figure = new NamedConnectionFigure();
		}
		return figure;
	}

	public Element getCastedModel() {
		return (Element) getModel();
	}

	public void refreshVisuals() {
		super.refreshVisuals();
		((NamedConnectionFigure) figure).setName(getCastedModel()
				.attributeValue("count", "1"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		String key = event.getPropertyName();
		if(DocumentManager.PROP_ATTRIBUTE_MODIFIED.equals(key)) {
			Attribute attribute = (Attribute) event.getNewValue();
			if("count".equals(attribute.getName())) {
				refreshVisuals();
			} else {
				figure.revalidate();
			}
		}
	}
}
