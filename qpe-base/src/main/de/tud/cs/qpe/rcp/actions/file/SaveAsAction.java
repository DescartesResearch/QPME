package de.tud.cs.qpe.rcp.actions.file;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import de.tud.cs.qpe.editors.net.MultipageNetEditor;
import de.tud.cs.qpe.editors.net.NetEditorInput;
import de.tud.cs.qpe.model.DocumentManager;

public class SaveAsAction extends Action implements ActionFactory.IWorkbenchAction {
	public SaveAsAction() {
		setId(ActionFactory.SAVE.getId());
		setText("Save As");
		setToolTipText("Save As");
		// setImageDescriptor(WorkbenchImages.getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_SAVEAS_EDIT));
		// setDisabledImageDescriptor(WorkbenchImages.getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_SAVEAS_EDIT_DISABLED));
	}

	public void run() {
		String path = openFileDialog();
		if (path != null) {
			IWorkbenchPage workbenchPage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			IEditorPart editorPart = workbenchPage.getActiveEditor();
			if (editorPart instanceof MultipageNetEditor) {
				MultipageNetEditor editor = (MultipageNetEditor) editorPart;
				NetEditorInput input = (NetEditorInput) editor.getEditorInput();
				DocumentManager.setAttribute(input.getNetDiagram(), "path", path);
				editor.doSave(null);
			}
		}
	}

	private String openFileDialog() {
		FileDialog dialog = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.SAVE);
		dialog.setText("Save As");
		dialog.setFilterExtensions(new String[] { "*.qpe" });
		return dialog.open();
	}

	public void dispose() {
	}
}
