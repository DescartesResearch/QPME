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
�* http://www.eclipse.org/legal/epl-v10.html
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
package de.tud.cs.qpe.editors.net.gef.property;

import java.beans.PropertyChangeListener;

import org.dom4j.Element;
import org.eclipse.gef.editparts.AbstractEditPart;
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

import de.tud.cs.qpe.editors.net.gef.property.place.OrdinaryPlacePropertyComposite;
import de.tud.cs.qpe.editors.net.gef.property.place.QueueingPlacePropertyComposite;
import de.tud.cs.qpe.editors.net.gef.property.place.SubnetPlacePropertyComposite;
import de.tud.cs.qpe.editors.net.gef.property.transition.ImmediateTransitionPropertyComposite;
import de.tud.cs.qpe.editors.net.gef.property.transition.TimedTransitionPropertyComposite;
import de.tud.cs.qpe.model.PlaceHelper;
import de.tud.cs.qpe.model.ProbeHelper;
import de.tud.cs.qpe.model.TransitionHelper;

public class PlaceTransitionPropertyPage implements IPropertySheetPage {
	protected Composite content;

	protected StackLayout stackLayout;

	protected Composite emptyProperties;

	protected OrdinaryPlacePropertyComposite ordinaryPlaceProperties;

	protected QueueingPlacePropertyComposite queueingPlaceProperties;

	protected SubnetPlacePropertyComposite subnetPlaceProperties;

	protected ImmediateTransitionPropertyComposite immediateTransitionProperties;

	protected TimedTransitionPropertyComposite timedTransitionProperties;
	
	protected ProbeProperyComposite probeProperties;

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
		probeProperties = new ProbeProperyComposite(content);
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
			if (structuredSelection.size() <= 1) {
				// Remove the last property change listener.
				if(stackLayout.topControl instanceof ElementPropertyComposite) {
					ElementPropertyComposite oldPropetyComposite = (ElementPropertyComposite) stackLayout.topControl;
					oldPropetyComposite.deactivate();
				}
				
				Element newModel = null;				
				if (structuredSelection.size() == 1) {
					if (structuredSelection.getFirstElement() instanceof AbstractEditPart) {
						AbstractEditPart editPart = (AbstractEditPart) structuredSelection
								.getFirstElement();
						if (editPart.getModel() instanceof Element) {
							newModel = (Element) editPart.getModel();					
						} 
					} else if (structuredSelection.getFirstElement() instanceof Element) {
						newModel = (Element)structuredSelection.getFirstElement();										
					} 
				}
				
				if (newModel != null) {					
					stackLayout.topControl = emptyProperties;
					if (ProbeHelper.isProbe(newModel)) {
						stackLayout.topControl = probeProperties;
					} else if (PlaceHelper.isPlace(newModel)) {
						if (PlaceHelper.isOrdinaryPlace(newModel)) {
							stackLayout.topControl = ordinaryPlaceProperties;
						} else if (PlaceHelper.isQueueingPlace(newModel)) {
							stackLayout.topControl = queueingPlaceProperties;
						} else if (PlaceHelper.isSubnetPlace(newModel)) {
							stackLayout.topControl = subnetPlaceProperties;
						}
					} else if (TransitionHelper.isTransition(newModel)) {
						if (TransitionHelper.isImmediateTranstion(newModel)) {
							stackLayout.topControl = immediateTransitionProperties;
						} else if (TransitionHelper.isTimedTransition(newModel)) {
							stackLayout.topControl = timedTransitionProperties;
						}
					}
					
					if (stackLayout.topControl != emptyProperties) {
						// Set the new property change listener.
						ElementPropertyComposite newPropetyComposite = (ElementPropertyComposite) stackLayout.topControl;
						newPropetyComposite.setModel(newModel);
						newPropetyComposite.activate();
					}
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
