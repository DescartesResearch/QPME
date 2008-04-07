package de.tud.cs.qpe.rcp.actions.file;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

public class CloseAction extends Action implements ActionFactory.IWorkbenchAction {
	public CloseAction() {
		setId(ActionFactory.CLOSE.getId());
		setText("Close");
		setToolTipText("Close");
		setEnabled(true);
	}

	public void run() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		window.getActivePage().closeEditor(window.getActivePage().getActiveEditor(), true);
	}

	public void dispose() {
	}
}
