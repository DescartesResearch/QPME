package de.tud.cs.qpe.rcp.actions.file;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import de.tud.cs.qpe.QPEBasePlugin;
import de.tud.cs.qpe.editors.net.NetEditorInput;
import de.tud.cs.qpe.editors.net.NetEditorPage;
import de.tud.cs.qpe.editors.query.QueryEditorInput;
import de.tud.cs.qpe.editors.query.SimpleQueryEditor;

public class OpenAction extends Action implements ActionFactory.IWorkbenchAction {
	public OpenAction() {
		setId("open");
		setText("Open File...");
		setToolTipText("Open File...");
		setImageDescriptor(QPEBasePlugin.getImageDescriptor("images/open.png"));
		setDisabledImageDescriptor(QPEBasePlugin.getImageDescriptor("images/open_d.png"));
	}

	public void run() {
		String path = openFileDialog();
		if (path != null) {
			IEditorInput input = null;
			String editor = null;
			if (path.endsWith(".qpe")) {
				input = new NetEditorInput(new Path(path));
				editor = NetEditorPage.ID;
			}
			if (path.endsWith(".simqpn")) {
				input = new QueryEditorInput(new Path(path));
				editor = SimpleQueryEditor.ID;
			}
			if (input != null && input.exists()) {
				try {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(input, editor, true);
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String openFileDialog() {
		FileDialog dialog = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.OPEN);
		dialog.setText("Open QPE document");
		dialog.setFilterExtensions(new String[] { "*.qpe", "*.simqpn" });
		return dialog.open();
	}

	public void dispose() {
	}
}
