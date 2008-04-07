package de.tud.cs.qpe.rcp.actions.file;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

public class ExitAction extends Action implements ActionFactory.IWorkbenchAction {
	public ExitAction() {
		setId(ActionFactory.QUIT.getId());
		setText("Exit");
		setToolTipText("Exit");
	}

	public void run() {
		PlatformUI.getWorkbench().close();
	}

	public void dispose() {
	}
}
