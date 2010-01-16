/* ==============================================
 * QPME : Queueing Petri net Modeling Environment
 * ==============================================
 *
 * (c) Copyright 2003-2010, by Samuel Kounev and Contributors.
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
package de.tud.cs.simqpn.plugin.wiizard;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import de.tud.cs.qpe.editors.net.NetEditorInput;
import de.tud.cs.qpe.editors.query.QueryEditorInput;
import de.tud.cs.qpe.editors.query.SimpleQueryEditor;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.Simulator;
import de.tud.cs.simqpn.kernel.Stats;
import de.tud.cs.simqpn.kernel.StatsDocumentBuilder;
import de.tud.cs.simqpn.plugin.wiizard.page.BaseWizardPage;
import de.tud.cs.simqpn.plugin.wiizard.page.Page1ConfigurationSelectionWizardPage;
import de.tud.cs.simqpn.plugin.wiizard.page.Page2SimulationRunSettingsWizardPage;
import de.tud.cs.simqpn.plugin.wiizard.page.Page3PlaceConfigurationParametersWizardPage;

public class RunSimulationWizard extends Wizard {
	private BaseWizardPage configurationSelectionPage;

	private BaseWizardPage simulationRunSettingsPage;

	private BaseWizardPage placesConfigurationSettingsPage;

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
		configurationSelectionPage = new Page1ConfigurationSelectionWizardPage(
				selection, net);
		addPage(configurationSelectionPage);

		simulationRunSettingsPage = new Page2SimulationRunSettingsWizardPage(
				selection, net);
		addPage(simulationRunSettingsPage);

		placesConfigurationSettingsPage = new Page3PlaceConfigurationParametersWizardPage(
				selection, net);
		addPage(placesConfigurationSettingsPage);

		configurationSelectionPage
				.addPropertyChangeListener(simulationRunSettingsPage);
		configurationSelectionPage
				.addPropertyChangeListener(placesConfigurationSettingsPage);

	}

	/**
	 * This method is called when 'Finish' button is pressed in the wizard. We
	 * will create an operation and run it using wizard as execution context.
	 */
	public boolean performFinish() {
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				try {
					doFinish(monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException
					.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * The worker method. It will find the container, create the file if missing
	 * or just replace its contents, and open the editor on the newly created
	 * file.
	 */
	private void doFinish(IProgressMonitor monitor) throws CoreException {
		monitor.setTaskName("Simulating...");
		getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();

				IEditorInput input = page.getActiveEditor().getEditorInput();
				if (input instanceof NetEditorInput) {
					NetEditorInput qpeInput = (NetEditorInput) input;
					try {
						Simulation simulation = new Simulation(qpeInput
								.getDocument().getRootElement(), getShell()
								.getDisplay());
						simulation.configure();
						simulation.start();
					} catch (SimQPNException e) {
						e.printStackTrace();
					}
				}
			}
		});
		monitor.worked(1);
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

	class Simulation extends Thread {
		protected Element net;
		protected Display display;

		public Simulation(Element net, Display display) {
			this.net = net;
			this.display = display;
		}

		class EditorOpener implements Runnable {

			private IEditorInput input;
			private String editorId;
			private Boolean activate;

			public EditorOpener(IEditorInput input, String editorId,
					Boolean activate) {
				this.input = input;
				this.editorId = editorId;
				this.activate = activate;
			}

			@Override
			public void run() {
				try {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getActivePage().openEditor(input, editorId,
									activate);
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}

		}

		public void configure() throws SimQPNException {
			Simulator.configure(net,
					Page1ConfigurationSelectionWizardPage.activeConfiguration);
		}

		public void run() {
			try {
				String configuration = Page1ConfigurationSelectionWizardPage.activeConfiguration;
				Stats[] result = Simulator.execute(net, configuration);
				StatsDocumentBuilder builder = new StatsDocumentBuilder(result,
						net, configuration);
				Document statsDocument = builder.buildDocument();
				File resultsFile = new File(Simulator.statsDir, builder
						.getResultFileBaseName()
						+ ".simqpn");
				saveXmlToFile(statsDocument, resultsFile);

				IEditorInput simulationOutput = new QueryEditorInput(new Path(
						resultsFile.getAbsolutePath()));

				display.asyncExec(new EditorOpener(simulationOutput,
						SimpleQueryEditor.ID, true));

			} catch (SimQPNException e) {
				e.printStackTrace();
			}
		}

		private void saveXmlToFile(Document doc, File file) {
			XMLWriter writer = null;
			try {
				writer = new XMLWriter(new FileWriter(file), OutputFormat
						.createPrettyPrint());
				writer.write(doc);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}
}
