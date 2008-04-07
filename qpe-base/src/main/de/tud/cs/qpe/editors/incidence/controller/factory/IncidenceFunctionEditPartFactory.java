package de.tud.cs.qpe.editors.incidence.controller.factory;

import org.dom4j.Element;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import de.tud.cs.qpe.editors.incidence.controller.editpart.ColorRefEditPart;
import de.tud.cs.qpe.editors.incidence.controller.editpart.IncidenceFunctionEditPart;
import de.tud.cs.qpe.editors.incidence.controller.editpart.ModeEditPart;
import de.tud.cs.qpe.editors.incidence.controller.editpart.NamedConnectionEditPart;
import de.tud.cs.qpe.editors.incidence.controller.editpart.PlaceEditPart;
import de.tud.cs.qpe.editors.incidence.model.PlaceWrapper;

public class IncidenceFunctionEditPartFactory implements EditPartFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart,
	 *      java.lang.Object)
	 */
	public EditPart createEditPart(EditPart context, Object modelElement) {
		// get EditPart for model element
		EditPart part = getPartForElement(modelElement);
		// store model element in EditPart
		part.setModel(modelElement);
		return part;
	}

	/**
	 * Maps an object to an EditPart.
	 * 
	 * @throws RuntimeException
	 *             if no match was found (programming error)
	 */
	private EditPart getPartForElement(Object modelElement) {
		if (modelElement instanceof Element) {
			Element element = (Element) modelElement;
			if ("transition".equals(element.getName())) {
				return new IncidenceFunctionEditPart();
			}
			if ("mode".equals(element.getName())) {
				return new ModeEditPart();
			}
			if ("color-ref".equals(element.getName())) {
				return new ColorRefEditPart();
			}
			if ("connection".equals(element.getName())) {
				return new NamedConnectionEditPart();
			}
		} else if(modelElement instanceof PlaceWrapper) {
			return new PlaceEditPart();
		}
		throw new RuntimeException("Can't create part for model element: "
				+ ((modelElement != null) ? modelElement.getClass().getName()
						: "null"));
	}
}