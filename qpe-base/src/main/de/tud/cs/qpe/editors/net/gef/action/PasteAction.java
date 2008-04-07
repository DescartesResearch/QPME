package de.tud.cs.qpe.editors.net.gef.action;

import org.dom4j.Document;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import de.tud.cs.qpe.editors.net.controller.command.PasteCommand;

public class PasteAction extends SelectionAction {

	public PasteAction(IWorkbenchPart part, int style) {
		super(part, style);
	}

	public PasteAction(IWorkbenchPart part) {
		super(part);
	}

	public void init() {
		setId(ActionFactory.PASTE.getId());
		setText("Paste");
		setToolTipText("Paste");
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE_DISABLED));
		setEnabled(false);
	}

	protected boolean calculateEnabled() {
		if (Clipboard.getDefault().getContents() instanceof Document) {
			Document doc = (Document) Clipboard.getDefault().getContents();
			if ("qpe-clipboard-document".equals(doc.getRootElement().getName())) {
				return true;
			}
		}
		return false;
	}

	public void run() {
		PasteCommand pasteCommand = new PasteCommand();
		pasteCommand.setLabel("Paste");
		// TIP: If just calling "pasteCommand.execute()" the command is executed but is not undoable.
		getCommandStack().execute(pasteCommand);
	}
}
