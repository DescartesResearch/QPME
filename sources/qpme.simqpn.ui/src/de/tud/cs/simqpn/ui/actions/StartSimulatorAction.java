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
package de.tud.cs.simqpn.ui.actions;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IProgressMonitorWithBlocking;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import de.tud.cs.qpe.editors.net.NetEditorInput;
import de.tud.cs.qpe.editors.query.QueryEditorInput;
import de.tud.cs.qpe.editors.query.SimpleQueryEditor;
import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNController;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;
import de.tud.cs.simqpn.ui.wizard.RunSimulationWizard;

public class StartSimulatorAction extends AbstractHandler {

	private static Logger log = Logger.getLogger(StartSimulatorAction.class);

	/**
	 * The constructor.
	 */
	public StartSimulatorAction() {
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell shell = HandlerUtil.getActiveShell(event);
		if (SimQPNController.isSimRunning()) {
			MessageDialog
					.openWarning(
							shell,
							"Simulation warning",
							"SimQPN is currently active! Please wait until the current simulation run is finished.");
		} else {
			IEditorInput input = HandlerUtil.getActiveEditorInput(event);
			if (input instanceof NetEditorInput) {
				NetEditorInput qpeInput = (NetEditorInput) input;
				Element net = qpeInput.getNetDiagram();

				RunSimulationWizard wizard = new RunSimulationWizard(net);
				WizardDialog dialog = new WizardDialog(shell, wizard);
				dialog.create();

				int result = dialog.open();

				if (result == WizardDialog.OK) {
					String configuration = wizard.getActiveConfiguration();
					try {
						Simulation simulation = new Simulation(net, shell,
								configuration);

						ProgressMonitorDialog progress = new ProgressMonitorDialog(
								shell) {

							@Override
							protected Control createDialogArea(Composite parent) {
								Control c = super.createDialogArea(parent);
								GridData gd = (GridData) subTaskLabel
										.getLayoutData();
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

				// TODO: Maximize the Console view
			} else {
				MessageDialog
						.openError(shell, "Missing model error",
								"No queueing petri net model found.\nPlease choose one in the editor area.");
			}
		}
		return null;
	}

	/**
	 * Selection in the workbench has been changed. We can change the state of
	 * the 'real' action here if we want, but this can only happen after the
	 * delegate has been created.
	 * 
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
//		IWorkbenchPage page = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage();
//
//		IEditorPart activeEditor = page.getActiveEditor();
//		if (activeEditor != null) {
//			IEditorInput input = activeEditor.getEditorInput();
//			if (input instanceof NetEditorInput) {
//				NetEditorInput qpeInput = (NetEditorInput) input;
//				net = qpeInput.getNetDiagram();
//				return;
//			}
//		}
//		net = null;
	}

	/**
	 * We can use this method to dispose of any system resources we previously
	 * allocated.
	 * 
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	class Simulation implements IRunnableWithProgress, SimulatorProgress {

		protected Element net;
		protected Shell shell;
		private String configuration;
		private IProgressMonitor monitor;
		private int totalWork;
		private int worked;
		private int lastProgress;
		private int numRuns; // total number of simulation runs
		private int currentRun; // number of current simulation run
		private long totalTime; // total run time of simulation (in
								// milliseconds)
		private long remainingTime; // (in seconds)
		private String failedPlace = null;

		public Simulation(Element net, Shell shell, String configuration) {
			this.net = net;
			this.shell = shell;
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
							.getActivePage()
							.openEditor(input, editorId, activate);
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}

		}

		@Override
		public void run(IProgressMonitor monitor)
				throws InvocationTargetException, InterruptedException {
			this.monitor = monitor;

			monitor.setTaskName("Simulation");
			try {
				monitor.subTask("Configure Simulator");

				SimQPNController sim = new SimQPNController(net, configuration, null);
				File resultFile = sim.execute(net, configuration, null, this);

				monitor.subTask("Collect Results");
				if (resultFile != null){
					IEditorInput simulationOutput = new QueryEditorInput(
							new Path(resultFile.getAbsolutePath()));
					shell.getDisplay().asyncExec(
							new EditorOpener(simulationOutput,
									SimpleQueryEditor.ID, true));
				}
			} catch (SimQPNException e) {
				shell.getDisplay().syncExec(new Runnable() {
					@Override
					public void run() {
						MessageDialog
								.openError(shell, "Simulation Error",
										"An error occurred during the simulation. For details see the console output.");
					}
				});
				throw new InvocationTargetException(e);
			} catch (final Exception e) {
				shell.getDisplay().syncExec(new Runnable() {
					@Override
					public void run() {
						MessageDialog.openError(shell, "Simulation Error",
								"An internal error occurred during the simulation: "
										+ e.getClass().getName());
					}
				});
				throw new InvocationTargetException(e);
			} finally {
				monitor.done();
			}

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#finishSimulation()
		 */
		@Override
		public void finishSimulation() {
			log.info("Simulation finished.");
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#finishSimulationRun()
		 */
		@Override
		public void finishSimulationRun() {
			log.info("Simulation run finished.");
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#finishWarmUp()
		 */
		@Override
		public void finishWarmUp(SimQPNConfiguration configuration) {
			boolean inRampUp = false; // Proven to be false
			updateStatusString(configuration, inRampUp);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#startSimulation()
		 */
		@Override
		public void startSimulation(SimQPNConfiguration configuration) {
			this.numRuns = (configuration.getAnalMethod() == SimQPNConfiguration.AnalysisMethod.BATCH_MEANS) ? 1
					: configuration.getNumRuns();
			this.totalWork = numRuns * 100;
			this.worked = 0;
			this.totalTime = 0;
			monitor.beginTask("Simulation", totalWork);

			switch (configuration.getAnalMethod()) {
			case BATCH_MEANS:
				monitor.setTaskName("Simulation (Batch Means)");
				break;
			case REPL_DEL:
				monitor.setTaskName("Simulation (Replication/Deletion)");
				break;
			case WELCH:
				monitor.setTaskName("Simulation (Method of Welch)");
				break;
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * de.tud.cs.simqpn.kernel.SimulatorProgress#startSimulationRun(int)
		 */
		@Override
		public void startSimulationRun(int number, SimQPNConfiguration configuration) {
			log.info("Simulation run " + number + "/" + numRuns + " started.");
			this.currentRun = number;
			this.lastProgress = 0;

			updateStatusString(configuration, true);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * de.tud.cs.simqpn.kernel.SimulatorProgress#updateSimulationProgress
		 * (double, long)
		 */
		@Override
		public void updateSimulationProgress(double progress, long elapsedTime, SimQPNConfiguration configuration, boolean inRampUp) {
			totalTime += elapsedTime;
			double totalProgress = (currentRun - 1) * 100 + progress;
			remainingTime = ((long) ((totalTime / totalProgress) * (totalWork - totalProgress))) / 1000;

			int progressDiff = ((int) progress) - lastProgress;
			if (progressDiff > 0) {
				worked += progressDiff;
				monitor.worked(progressDiff);
				lastProgress = (int) progress;
			}
			updateStatusString(configuration, inRampUp);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.tud.cs.simqpn.kernel.SimulatorProgress#isCanceled()
		 */
		@Override
		public boolean isCanceled() {
			return monitor.isCanceled();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * de.tud.cs.simqpn.kernel.SimulatorProgress#getMaxUpdateLogicalTimeInterval
		 * ()
		 */
		@Override
		public double getMaxUpdateLogicalTimeInterval(SimQPNConfiguration configuration) {
			// if numRuns > 1 distribute the heartbeats approximately evenly
			// over the simulation runs
			int numberHeartbeats = (100 / numRuns) + 1;
			return configuration.totRunLen / numberHeartbeats;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * de.tud.cs.simqpn.kernel.SimulatorProgress#precisionCheck(boolean,
		 * java.lang.String)
		 */
		@Override
		public void precisionCheck(boolean done, String failedPlaceName) {
			if (!done) {
				this.failedPlace = failedPlaceName;
			}
		}

		@Override
		public void warning(final String message) {
			log.warn(message);
			shell.getDisplay().syncExec(new Runnable() {
				@Override
				public void run() {
					if (lastProgress != 100) {
						// Simulation still running
						MessageDialog dialog = new MessageDialog(shell,
								"Simulation Warning", null, message,
								MessageDialog.WARNING, new String[] {
										"Cancel Simulation", "Ignore" }, 0);
						if (dialog.open() == 0) {
							monitor.setCanceled(true);
						}
					} else {
						MessageDialog.openWarning(shell, "Simulation Warning",
								message);
					}
				}
			});
		}

		private void updateStatusString(SimQPNConfiguration configuration, boolean inRampUp) {
			StringBuilder status = new StringBuilder();

			// Remaining time display
			if (totalTime > 0) {
				if (configuration.stoppingRule == SimQPNConfiguration.FIXEDLEN) {
					status.append("Remaining Time: ");
				} else {
					status.append("Maximum Remaining Time: ");
				}
				if (remainingTime >= 3600) {
					long hours = remainingTime / 3600;
					status.append(hours).append(" h ");
					if (hours < 10) {
						status.append((remainingTime % 3600) / 60).append(
								" min");
					}
					status.append("\n");
				} else if (remainingTime >= 60)
					status.append(remainingTime / 60).append(" min\n");
				else
					status.append(remainingTime % 60).append(" sec\n");
			} else {
				status.append("Calculate remaining time (This can take a while).\n");
			}

			// Number of run
			if (configuration.getAnalMethod() != SimQPNConfiguration.AnalysisMethod.BATCH_MEANS) {
				status.append("Run: ").append(currentRun).append("/")
						.append(numRuns).append("\n");
			}

			// Phase
			status.append("Phase: ");
			if (configuration.getAnalMethod() == SimQPNConfiguration.AnalysisMethod.WELCH) {
				status.append("Determine Warm-up Period Length");
			} else {
				if (inRampUp) {
					status.append("Warm-Up Period");
				} else {
					status.append("Steady State Period");
				}
			}
			status.append("\n");

			// Failed place name
			if (configuration.stoppingRule != SimQPNConfiguration.FIXEDLEN
					&& (failedPlace != null)) {
				status.append("There are not enough statistics for place ")
						.append(failedPlace)
						.append(". Precision check failed.");
			}

			monitor.subTask(status.toString());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * de.tud.cs.simqpn.kernel.SimulatorProgress#getMaxUpdateRealTimeInterval
		 * ()
		 */
		@Override
		public long getMaxUpdateRealTimeInterval() {
			return 30000; // 30 sec
		}

	}
}
