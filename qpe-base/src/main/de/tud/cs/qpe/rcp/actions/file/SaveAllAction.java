package de.tud.cs.qpe.rcp.actions.file;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import de.tud.cs.qpe.QPEBasePlugin;

public class SaveAllAction extends Action implements ActionFactory.IWorkbenchAction {
	public SaveAllAction() {
		setId(ActionFactory.SAVE.getId());
		setText("Save All");
		setToolTipText("Save All");
		setImageDescriptor(QPEBasePlugin.getImageDescriptor("images/save_all.png"));
		setDisabledImageDescriptor(QPEBasePlugin.getImageDescriptor("images/save_all_d.png"));
	}

	public void run() {
		IWorkbenchPage pages[] = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPages();
		for (int i = 0; i < pages.length; i++) {
			IEditorReference editorReferences[] = pages[i].getEditorReferences();
			for (int j = 0; j < editorReferences.length; j++) {
				// TODO: Use a real progress-monitor.
				editorReferences[j].getEditor(false).doSave(null);
			}
		}
	}

	public void dispose() {
	}
}
