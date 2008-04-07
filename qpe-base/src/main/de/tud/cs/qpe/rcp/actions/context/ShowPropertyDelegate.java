package de.tud.cs.qpe.rcp.actions.context;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionDelegate;

public class ShowPropertyDelegate extends ActionDelegate implements
		IObjectActionDelegate {
	public void run(IAction action) {
		IWorkbenchPage activePage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		try {
			activePage.showView("org.eclipse.ui.views.PropertySheet");
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}
}
