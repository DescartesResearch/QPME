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
 *  2008/02/27  Frederik Zipp     Use StatsDocumentBuilder. Write XML document to file.  
 *  2010/15/01  Philipp Meier     Automatically open .simqpn file in query edtior after completion.
 * 
 */
package de.tud.cs.simqpn.ui.wizard;

import org.dom4j.Element;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

import de.tud.cs.simqpn.ui.wizard.page.BaseWizardPage;
import de.tud.cs.simqpn.ui.wizard.page.Page1ConfigurationSelectionWizardPage;
import de.tud.cs.simqpn.ui.wizard.page.Page2SimulationRunSettingsWizardPage;
import de.tud.cs.simqpn.ui.wizard.page.Page3PlaceConfigurationParametersWizardPage;

public class RunSimulationWizard extends Wizard {
	private Page1ConfigurationSelectionWizardPage configurationSelectionPage;

	private Page2SimulationRunSettingsWizardPage simulationRunSettingsPage;

	private Page3PlaceConfigurationParametersWizardPage placesConfigurationSettingsPage;

	private ISelection selection;

	private Element net;

	/**
	 * Constructor for SampleNewWizard.
	 */
	public RunSimulationWizard(Element net) {
		super();
		setNeedsProgressMonitor(true);
		this.net = net;
	}

	/**
	 * Adding the page to the wizard.
	 */
	public void addPages() {
		configurationSelectionPage = new Page1ConfigurationSelectionWizardPage(net);
		addPage(configurationSelectionPage);

		simulationRunSettingsPage = new Page2SimulationRunSettingsWizardPage();
		addPage(simulationRunSettingsPage);

		placesConfigurationSettingsPage = new Page3PlaceConfigurationParametersWizardPage(net);
		addPage(placesConfigurationSettingsPage);
	}

	/**
	 * This method is called when 'Finish' button is pressed in the wizard. We
	 * will create an operation and run it using wizard as execution context.
	 */
	public boolean performFinish() {
		return true;
	}

	/**
	 * We will accept the selection in the workbench to see if we can initialize
	 * from it.
	 * 
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}
	
	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		if (page == configurationSelectionPage) {
			simulationRunSettingsPage.setConfiguration(configurationSelectionPage.getSelectedConfiguration());
		} else if (page == simulationRunSettingsPage) {
			placesConfigurationSettingsPage.setConfiguration(configurationSelectionPage.getSelectedConfiguration());
		}
		return super.getNextPage(page);
	}

	public String getActiveConfiguration() {
		return configurationSelectionPage.getSelectedConfiguration().getName();
	}
}
