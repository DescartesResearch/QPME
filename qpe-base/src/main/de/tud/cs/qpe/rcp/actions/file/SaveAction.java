package de.tud.cs.qpe.rcp.actions.file;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import de.tud.cs.qpe.QPEBasePlugin;
import de.tud.cs.qpe.editors.net.MultipageNetEditor;
import de.tud.cs.qpe.editors.net.NetEditorInput;
import de.tud.cs.qpe.model.DocumentManager;

public class SaveAction extends Action implements ActionFactory.IWorkbenchAction {
	public SaveAction() {
		setId(ActionFactory.SAVE.getId());
		setText("Save");
		setToolTipText("Save");
		setImageDescriptor(QPEBasePlugin.getImageDescriptor("images/save.png"));
		setDisabledImageDescriptor(QPEBasePlugin.getImageDescriptor("images/save_d.png"));
	}

	public void run() {
		IWorkbenchPage workbenchPage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart editorPart = workbenchPage.getActiveEditor();
		if (editorPart instanceof MultipageNetEditor) {
			MultipageNetEditor editor = (MultipageNetEditor) editorPart;
			NetEditorInput input = (NetEditorInput) editor.getEditorInput();

			// If a new file, was created, the path is not set.
			// so show the Save-Dialog.
			if (input.getNetDiagram().attributeValue("path") == null) {
				String path = openFileDialog();
				if (path != null) {
					DocumentManager.setAttribute(input.getNetDiagram(), "path", path);
				}
			}
			if (input.getNetDiagram().attributeValue("path") != null) {
				editor.doSave(null);
			}
		}
	}

	private String openFileDialog() {
		FileDialog dialog = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.SAVE);
		dialog.setText("Save");
		dialog.setFilterExtensions(new String[] { "*.qpe" });
		return dialog.open();
	}

	public void dispose() {
	}
}
