package de.tud.cs.qpe.rcp.actions.window;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionDelegate;

public abstract class AbstractBaseViewDelegate extends ActionDelegate implements IWorkbenchWindowActionDelegate, IPartListener2 {
	protected IAction action;

	protected abstract String getViewId();

	protected abstract String getPartName();

	public void init(IWorkbenchWindow window) {
		window.getActivePage().addPartListener(this);
	}

	public void init(IAction action) {
		this.action = action;
		IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IViewReference visibleViews[] = activePage.getViewReferences();
		for (int i = 0; i < visibleViews.length; i++) {
			if (getViewId().equals(visibleViews[i].getId())) {
				action.setChecked(true);
				break;
			}
		}
	}

	public void run(IAction action) {
		IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IViewReference visibleViews[] = activePage.getViewReferences();
		for (int i = 0; i < visibleViews.length; i++) {
			if (getViewId().equals(visibleViews[i].getId())) {
				activePage.hideView(visibleViews[i]);
				action.setChecked(false);
				return;
			}
		}
		try {
			activePage.showView(getViewId());
			action.setChecked(true);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	public void partActivated(IWorkbenchPartReference partRef) {
	}

	public void partBroughtToTop(IWorkbenchPartReference partRef) {
	}

	public void partClosed(IWorkbenchPartReference partRef) {
		if (getPartName().equals(partRef.getPartName())) {
			action.setChecked(false);
		}
	}

	public void partDeactivated(IWorkbenchPartReference partRef) {
	}

	public void partHidden(IWorkbenchPartReference partRef) {
	}

	public void partInputChanged(IWorkbenchPartReference partRef) {
	}

	public void partOpened(IWorkbenchPartReference partRef) {
		if (getPartName().equals(partRef.getPartName())) {
			action.setChecked(true);
		}
	}

	public void partVisible(IWorkbenchPartReference partRef) {
	}
}
