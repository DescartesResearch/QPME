package de.tud.cs.qpe.editors.incidence.gef.property;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.dom4j.Element;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;

import de.tud.cs.qpe.editors.incidence.controller.editpart.NamedConnectionEditPart;
import de.tud.cs.qpe.model.DocumentManager;

public class IncidenceFunctionPropertyPage implements IPropertySheetPage,
		PropertyChangeListener {
	protected Element model;

	protected Composite content;

	protected StackLayout stackLayout;

	protected Composite emptyProperties;

	protected ConnectionPropertyComposite connectionProperties;

	protected Element connection;

	public IncidenceFunctionPropertyPage(Element connection) {
		super();
		this.connection = connection;
	}

	public void createControl(Composite parent) {
		content = new Composite(parent, SWT.NULL);
		content.setLayoutData(new GridData(GridData.FILL_BOTH));

		// Initialize the stack-layout.
		stackLayout = new StackLayout();
		content.setLayout(stackLayout);

		// Initialize the individual property pages.
		emptyProperties = new Composite(content, SWT.NULL);
		emptyProperties.setLayout(new GridLayout());

		// Initialize the property-sheet for connections.
		connectionProperties = new ConnectionPropertyComposite(connection,
				content);

		// Initialy set the emptyProperties to be the active
		// propertysheet.
		stackLayout.topControl = emptyProperties;

		content.layout();
	}

	public void dispose() {
	}

	public Control getControl() {
		return content;
	}

	public void setActionBars(IActionBars actionBars) {
	}

	public void setFocus() {
	}

	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (selection instanceof StructuredSelection) {
			StructuredSelection structuredSelection = (StructuredSelection) selection;
			// If only one element is selected then an ordinary
			// PropertyPage for this element is returned.
			if (structuredSelection.size() == 1) {
				// Remove the property change listener if the new selection
				// does not show the Connection composite.
				if (stackLayout.topControl instanceof ConnectionPropertyComposite) {
					ConnectionPropertyComposite oldComposite = (ConnectionPropertyComposite) stackLayout.topControl;
					oldComposite.deactivate();
				}

				if (structuredSelection.getFirstElement() instanceof NamedConnectionEditPart) {
					NamedConnectionEditPart editPart = (NamedConnectionEditPart) structuredSelection
							.getFirstElement();
					Element newModel = (Element) editPart.getModel();
					if ("connection".equals(newModel.getName())) {
						stackLayout.topControl = connectionProperties;
					} else {
						stackLayout.topControl = emptyProperties;
					}

					// Set the new property change listener.
					ConnectionPropertyComposite newPropetyComposite = (ConnectionPropertyComposite) stackLayout.topControl;
					newPropetyComposite.setModel(newModel);
					newPropetyComposite.activate();
				} else {
					stackLayout.topControl = emptyProperties;
				}
				content.layout();
			}
		}
	}

	public void setModel(Element model) {
		this.model = model;
	}

	public Element getModel() {
		return model;
	}

	public void activate() {
		DocumentManager.addPropertyChangeListener(getModel(), this);
	}

	public void deactivate() {
		DocumentManager.removePropertyChangeListener(getModel(), this);
	}

	public void propertyChange(PropertyChangeEvent event) {
	}
}
