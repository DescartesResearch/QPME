package de.tud.cs.qpe.rcp.actions.file;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

public class CloseAllAction extends Action implements ActionFactory.IWorkbenchAction {
	public CloseAllAction() {
		setId(ActionFactory.CLOSE_ALL.getId());
		setText("Close All");
		setToolTipText("Close All");
		setEnabled(true);
	}

	public void run() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		window.getActivePage().closeAllEditors(true);
	}

	public void dispose() {
	}
}
