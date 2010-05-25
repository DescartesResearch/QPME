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
 * Contributor(s):  Simon Spinner 
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2006        Christofer Dutz   Created.
 *  2010/04/17	Simon Spinner	  Progress dialog added.
 */
package de.tud.cs.simqpn.plugin.actions;

import static de.tud.cs.simqpn.kernel.Simulator.logln;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IProgressMonitorWithBlocking;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import de.tud.cs.qpe.editors.net.NetEditorInput;
import de.tud.cs.qpe.editors.query.QueryEditorInput;
import de.tud.cs.qpe.editors.query.SimpleQueryEditor;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.Simulator;
import de.tud.cs.simqpn.kernel.SimulatorProgress;
import de.tud.cs.simqpn.kernel.Stats;
import de.tud.cs.simqpn.kernel.StatsDocumentBuilder;
import de.tud.cs.simqpn.plugin.QPESimQPNPlugin;
import de.tud.cs.simqpn.plugin.wiizard.RunSimulationWizard;

public class StartSimulatorAction extends Action implements
		IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;

	private Element net;

	/**
	 * The constructor.
	 */
	public StartSimulatorAction() {
	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		Shell shell = window.getShell();
		if(Simulator.simRunning) {
			MessageDialog.openWarning(shell, "Simulation warning", "SimQPN is currently active! Please wait until the current simulation run is finished.");
		} else {
			if (net != null) {
				RunSimulationWizard wizard = new RunSimulationWizard(net);
				WizardDialog dialog = new WizardDialog(shell, wizard);
				dialog.create();

				int result = dialog.open();

				if(result == WizardDialog.OK) {
					String configuration = wizard.getActiveConfiguration();
					IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					IEditorInput input = page.getActiveEditor().getEditorInput();
					if (input instanceof NetEditorInput) {
						NetEditorInput qpeInput = (NetEditorInput) input;
						try {
							Simulation simulation = new Simulation(qpeInput
									.getDocument().getRootElement(), PlatformUI.getWorkbench()
									.getDisplay(), configuration);

							ProgressMonitorDialog progress = new ProgressMonitorDialog(shell) {								

								@Override
								protected Control createDialogArea(
										Composite parent) {
									Control c = super.createDialogArea(parent);
									GridData gd = (GridData) subTaskLabel.getLayoutData();
									gd.heightHint = convertVerticalDLUsToPixels(30);
									return c;
								}

								@Override
								protected void cancelPressed() {
									super.cancelPressed();
									IProgressMonitorWithBlocking p = (IProgressMonitorWithBlocking) getProgressMonitor();
									p.setBlocked(Status.CANCEL_STATUS);
									p.setTaskName("Cancelling simulation...");
									p.subTask("");
								}
								
							};
							progress.run(true, true, simulation);
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

				// TODO: Maximize the Console view
			} else {
				MessageDialog.openError(shell, "Missing model error", "No queueing petri net model found.\nPlease choose one in the editor area."); 
			}
		}
	}

	/**
	 * Selection in the workbench has been changed. We can change the state of
	 * the 'real' action here if we want, but this can only happen after the
	 * delegate has been created.
	 * 
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();

		IEditorPart activeEditor = page.getActiveEditor();
		if (activeEditor != null) {
			IEditorInput input = activeEditor.getEditorInput();
			if (input instanceof NetEditorInput) {
				NetEditorInput qpeInput = (NetEditorInput) input;
				net = qpeInput.getNetDiagram();
				return;
			}
		}
		net = null;
	}

	/**
	 * We can use this method to dispose of any system resources we previously
	 * allocated.
	 * 
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to be able to provide parent shell
	 * for the message dialog.
	 * 
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	class Simulation implements IRunnableWithProgress, SimulatorProgress {
		protected Element net;
		protected Display display;
		private String configuration;
		private IProgressMonitor monitor;
		private int totalWork;
		private int worked;
		private int lastProgress;
		private int numRuns;			// total number of simulation runs
		private int currentRun;			// number of current simulation run
		private long totalTime;			// total run time of simulation (in milliseconds)
		private long remainingTime;		// (in seconds)
		private String failedPlace = null;

		public Simulation(Element net, Display display, String configuration) {
			this.net = net;
			this.display = display;
			this.configuration = configuration;
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

		@Override
		public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			this.monitor = monitor;

			monitor.setTaskName("Simulation");
			try {
				monitor.subTask("Configure Simulator");
				Simulator.configure(net, configuration);

				Stats[] result = Simulator.execute(net, configuration, this);

				monitor.subTask("Collect Results");
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
				display.syncExec(new Runnable() {
					@Override
					public void run() {
						MessageDialog.openError(window.getShell(), "Simulation Error", 
							"An error occurred during the simulation. For details see the console output.");
					}
				});
				throw new InvocationTargetException(e);
			} catch (final Exception e) {
				display.syncExec(new Runnable() {
					@Override
					public void run() {
						MessageDialog.openError(window.getShell(), "Simulation Error", 
								"An internal error occurred during the simulation: " + e.getClass().getName());
					}
				});
				throw new InvocationTargetException(e);
			} finally {
				monitor.done();
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

		/* (non-Javadoc)
		 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#finishSimulation()
		 */
		@Override
		public void finishSimulation() {
			logln();
			logln();
			logln("Simulation finished.");
		}

		/* (non-Javadoc)
		 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#finishSimulationRun()
		 */
		@Override
		public void finishSimulationRun() {
			logln("Simulation run finished.");
			logln();
		}

		/* (non-Javadoc)
		 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#finishWarmUp()
		 */
		@Override
		public void finishWarmUp() {
			updateStatusString();
		}

		/* (non-Javadoc)
		 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#startSimulation()
		 */
		@Override
		public void startSimulation() {
			this.numRuns = (Simulator.analMethod == Simulator.BATCH_MEANS) ? 1 : Simulator.numRuns;
			this.totalWork = numRuns * 100;
			this.worked = 0;
			this.totalTime = 0;
			monitor.beginTask("Simulation", totalWork);

			switch(Simulator.analMethod) {
			case Simulator.BATCH_MEANS:
				monitor.setTaskName("Simulation (Batch Means)");
				break;
			case Simulator.REPL_DEL:
				monitor.setTaskName("Simulation (Replication/Deletion)");
				break;
			case Simulator.WELCH:
				monitor.setTaskName("Simulation (Method of Welch)");
				break;
			}
		}

		/* (non-Javadoc)
		 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#startSimulationRun(int)
		 */
		@Override
		public void startSimulationRun(int number) {
			logln("Simulation run " + number + "/" + numRuns + " started.");
			this.currentRun = number;
			this.lastProgress = 0;

			updateStatusString();
		}

		/* (non-Javadoc)
		 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#updateSimulationProgress(double, long)
		 */
		@Override
		public void updateSimulationProgress(double progress, long elapsedTime) {
			totalTime += elapsedTime;
			double totalProgress = (currentRun - 1) * 100 + progress;
			remainingTime = ((long) ((totalTime / totalProgress) * (totalWork - totalProgress))) / 1000;

			int progressDiff = ((int) progress) - lastProgress;
			if(progressDiff > 0) {
				worked += progressDiff;
				monitor.worked(progressDiff);
				lastProgress = (int) progress;
			}
			updateStatusString();
		}

		/* (non-Javadoc)
		 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#isCanceled()
		 */
		@Override
		public boolean isCanceled() {
			return monitor.isCanceled();
		}

		/* (non-Javadoc)
		 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#getMaxUpdateLogicalTimeInterval()
		 */
		@Override
		public double getMaxUpdateLogicalTimeInterval() {
			// if numRuns > 1 distribute the heartbeats approximately evenly over the simulation runs
			int numberHeartbeats = (100 / numRuns) + 1;
			return Simulator.totRunLen / numberHeartbeats;
		}

		/* (non-Javadoc)
		 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#precisionCheck(boolean, java.lang.String)
		 */
		@Override
		public void precisionCheck(boolean done, String failedPlaceName) {
			if(!done) {
				this.failedPlace = failedPlaceName;
			}
		}

		@Override
		public void warning(final String message) {
			Simulator.logln("WARNING: " + message);
			display.syncExec(new Runnable() {
				@Override
				public void run() {
					if(lastProgress != 100) {
						//Simulation still running
						MessageDialog dialog = new MessageDialog(window.getShell(), "Simulation Warning", 
								null, message, MessageDialog.WARNING, new String[] {"Cancel Simulation", "Ignore"}, 0);
						if(dialog.open() == 0) {
							monitor.setCanceled(true);
						}
					} else {
						MessageDialog.openWarning(window.getShell(), "Simulation Warning", message);
					}
				}				
			});
		}

		private void updateStatusString() {
			StringBuilder status = new StringBuilder();
			
			// Remaining time display
			if (totalTime > 0) {
				if (Simulator.stoppingRule == Simulator.FIXEDLEN) {
					status.append("Remaining Time: ");
				} else {
					status.append("Maximum Remaining Time: ");
				}
				if(remainingTime >= 3600) {
					long hours = remainingTime / 3600;
					status.append(hours).append(" h ");
					if(hours < 10) {					
						status.append((remainingTime % 3600) / 60).append(" min");
					}
					status.append("\n");
				} else if(remainingTime >= 60)
					status.append(remainingTime / 60).append(" min\n");
				else
					status.append(remainingTime % 60).append(" sec\n");
			} else {
				status.append("Calculate remaining time (This can take a while).\n");
			}

			// Number of run
			if (Simulator.analMethod != Simulator.BATCH_MEANS) {
				status.append("Run: ").append(currentRun).append("/").append(numRuns).append("\n");
			}
			
			// Phase
			status.append("Phase: ");
			if (Simulator.analMethod == Simulator.WELCH) {
				status.append("Determine Warm-up Period Length");
			} else {
				if (Simulator.inRampUp) {
					status.append("Warm-Up Period");
				} else {
					status.append("Steady State Period");
				}
			}
			status.append("\n");
			
			// Failed place name
			if (Simulator.stoppingRule != Simulator.FIXEDLEN && (failedPlace != null)) {
				status.append("There are not enough statistics for place ").append(failedPlace).append(". Precision check failed.");
			}

			monitor.subTask(status.toString());
		}

		/* (non-Javadoc)
		 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#getMaxUpdateRealTimeInterval()
		 */
		@Override
		public long getMaxUpdateRealTimeInterval() {
			return 30000; // 30 sec
		}
	}
}
