package de.tud.cs.qpe.rcp.actions.file;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import de.tud.cs.qpe.QPEBasePlugin;
import de.tud.cs.qpe.editors.net.NetEditorInput;
import de.tud.cs.qpe.editors.net.NetEditorPage;

public class NewAction extends Action implements ActionFactory.IWorkbenchAction {
	public NewAction() {
		setId(ActionFactory.NEW.getId());
		setText("New");
		setToolTipText("Create a new document");
		setImageDescriptor(QPEBasePlugin.getImageDescriptor("images/new.png"));
		setDisabledImageDescriptor(QPEBasePlugin.getImageDescriptor("images/new_d.png"));
	}

	public void run() {
		IEditorInput input = new NetEditorInput(null);
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(input, NetEditorPage.ID, true);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	public void dispose() {
	}
}
