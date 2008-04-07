package de.tud.cs.qpe.editors.net.controller.editpart.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import de.tud.cs.qpe.editors.net.controller.command.PlaceTransitionCreateCommand;
import de.tud.cs.qpe.editors.net.controller.command.PlaceTransitionSetConstraintCommand;
import de.tud.cs.qpe.model.DocumentManager;

public class NetEditPart extends AbstractGraphicalEditPart implements PropertyChangeListener {

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
			DocumentManager.removePropertyChangeListener(getCastedModel(), this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	protected void createEditPolicies() {
		// disallows the removal of this edit part from its parent
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		// handles constraint changes (e.g. moving and/or resizing) of model
		// elements and creation of new model elements
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new NetLayoutEditPolicy());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	protected IFigure createFigure() {
		Figure f = new FreeformLayer();
		f.setBorder(new MarginBorder(3));
		f.setLayoutManager(new FreeformLayout());
		return f;
	}

	private Element getCastedModel() {
		return (Element) getModel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	protected List getModelChildren() {
		XPath xpathSelector = DocumentHelper.createXPath("places/place | transitions/transition");
		return xpathSelector.selectNodes(getCastedModel());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
		// Since this edit part is registered to be notified
		// for modifications of its children. We simply need
		// to check for child modification events.
		if (DocumentManager.PROP_CHILD_MODIFIED.equals(prop)) {
			refreshChildren();
		}
	}

	/**
	 * EditPolicy for the Figure used by this edit part. Children of
	 * XYLayoutEditPolicy can be used in Figures with XYLayout.
	 * 
	 * @author Elias Volanakis
	 */
	private class NetLayoutEditPolicy extends XYLayoutEditPolicy {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createAddCommand(org.eclipse.gef.EditPart,
		 *      java.lang.Object)
		 */
		protected Command createAddCommand(EditPart child, Object constraint) {
			// not used in this example
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createChangeConstraintCommand(org.eclipse.gef.requests.ChangeBoundsRequest,
		 *      org.eclipse.gef.EditPart, java.lang.Object)
		 */
		protected Command createChangeConstraintCommand(ChangeBoundsRequest request, EditPart child, Object constraint) {
			if (child instanceof PlaceTransitionEditPart && constraint instanceof Rectangle) {
				// return a command that can move and/or resize a Shape
				return new PlaceTransitionSetConstraintCommand((Element) child.getModel(), request, (Rectangle) constraint);
			}
			return super.createChangeConstraintCommand(request, child, constraint);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createChangeConstraintCommand(org.eclipse.gef.EditPart,
		 *      java.lang.Object)
		 */
		protected Command createChangeConstraintCommand(EditPart child, Object constraint) {
			// not used in this example
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getCreateCommand(org.eclipse.gef.requests.CreateRequest)
		 */
		protected Command getCreateCommand(CreateRequest request) {
			Class childType = (Class) request.getNewObjectType();
			if (childType != null) {
				// return a command that can add a Shape to a ShapesDiagram
				return new PlaceTransitionCreateCommand((Element) getHost().getModel(), (Element) request.getNewObject(), (Rectangle) getConstraintFor(request));
			}
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getDeleteDependantCommand(org.eclipse.gef.Request)
		 */
		protected Command getDeleteDependantCommand(Request request) {
			// TODO: Mabe have to implement for connection deletion.
			// not used in this example
			return null;
		}
	}
}