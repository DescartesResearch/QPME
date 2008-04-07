package de.tud.cs.qpe.editors.net.gef.property;

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

import de.tud.cs.qpe.editors.net.controller.editpart.editor.PlaceTransitionEditPart;
import de.tud.cs.qpe.editors.net.gef.property.place.OrdinaryPlacePropertyComposite;
import de.tud.cs.qpe.editors.net.gef.property.place.QueueingPlacePropertyComposite;
import de.tud.cs.qpe.editors.net.gef.property.place.SubnetPlacePropertyComposite;
import de.tud.cs.qpe.editors.net.gef.property.transition.ImmediateTransitionPropertyComposite;
import de.tud.cs.qpe.editors.net.gef.property.transition.TimedTransitionPropertyComposite;

public class PlaceTransitionPropertyPage implements IPropertySheetPage {
	protected Composite content;

	protected StackLayout stackLayout;

	protected Composite emptyProperties;

	protected OrdinaryPlacePropertyComposite ordinaryPlaceProperties;

	protected QueueingPlacePropertyComposite queueingPlaceProperties;

	protected SubnetPlacePropertyComposite subnetPlaceProperties;

	protected ImmediateTransitionPropertyComposite immediateTransitionProperties;

	protected TimedTransitionPropertyComposite timedTransitionProperties;

	protected Element net;

	protected PropertyChangeListener oldSelectedPropertyComposite;

	public PlaceTransitionPropertyPage(Element net) {
		super();
		this.net = net;
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

		ordinaryPlaceProperties = new OrdinaryPlacePropertyComposite(net,
				content);
		queueingPlaceProperties = new QueueingPlacePropertyComposite(net,
				content);
		subnetPlaceProperties = new SubnetPlacePropertyComposite(net, content);

		immediateTransitionProperties = new ImmediateTransitionPropertyComposite(
				net, content);
		timedTransitionProperties = new TimedTransitionPropertyComposite(net,
				content);
		// Initialy set the emptyProperties to be the active
		// propertysheet.
		stackLayout.topControl = emptyProperties;
		content.layout();
	}

	public void dispose() {
		Composite oldPropetyComposite = (Composite) stackLayout.topControl;
		if(oldPropetyComposite instanceof PlaceTransitionPropertyComposite) {
			if((oldPropetyComposite != null) && (stackLayout.topControl != emptyProperties)) {
				((PlaceTransitionPropertyComposite) oldPropetyComposite).deactivate();
			}
		}
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
				// Remove the last property change listener.
				if(stackLayout.topControl instanceof PlaceTransitionPropertyComposite) {
					PlaceTransitionPropertyComposite oldPropetyComposite = (PlaceTransitionPropertyComposite) stackLayout.topControl;
					oldPropetyComposite.deactivate();
				}
				
				if (structuredSelection.getFirstElement() instanceof PlaceTransitionEditPart) {
					PlaceTransitionEditPart editPart = (PlaceTransitionEditPart) structuredSelection
							.getFirstElement();
					Element newModel = (Element) editPart.getModel();
					String type = newModel.attributeValue("type");
					
					if ("ordinary-place".equals(type)) {
						stackLayout.topControl = ordinaryPlaceProperties;
					} else if ("queueing-place".equals(type)) {
						stackLayout.topControl = queueingPlaceProperties;
					} else if ("subnet-place".equals(type)) {
						stackLayout.topControl = subnetPlaceProperties;
					} else if ("immediate-transition".equals(type)) {
						stackLayout.topControl = immediateTransitionProperties;
					} else if ("timed-transition".equals(type)) {
						stackLayout.topControl = timedTransitionProperties;
					} else {
						stackLayout.topControl = emptyProperties;
					}

					// Set the new property change listener.
					PlaceTransitionPropertyComposite newPropetyComposite = (PlaceTransitionPropertyComposite) stackLayout.topControl;
					newPropetyComposite.setModel(newModel);
					newPropetyComposite.activate();
				} else {
					stackLayout.topControl = emptyProperties;
				}
				content.layout();
			}
			// If more than one Element is selected, then ony a few
			// Options make sense or they have to work differently.
			else {

			}
		}
	}
}
