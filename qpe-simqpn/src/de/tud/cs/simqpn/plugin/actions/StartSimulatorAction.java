package de.tud.cs.simqpn.plugin.actions;

import org.dom4j.Element;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import de.tud.cs.qpe.editors.net.NetEditorInput;
import de.tud.cs.simqpn.kernel.Simulator;
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
			MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
			messageBox
					.setMessage("SimQPN is currently active! Please wait until the current simulation run is finished.");
			messageBox.open();
		} else {
			if (net != null) {
				Wizard wizard = new RunSimulationWizard(net);
				WizardDialog dialog = new WizardDialog(shell, wizard);
				dialog.create();

				dialog.open();

				// TODO: Maximize the Console view
			} else {
				// TODO: Do something to complain !!!
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
}
