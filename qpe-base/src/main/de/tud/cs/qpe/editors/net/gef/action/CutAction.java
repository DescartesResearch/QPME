package de.tud.cs.qpe.editors.net.gef.action;

import java.util.Iterator;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import de.tud.cs.qpe.editors.net.controller.command.CutCommand;
import de.tud.cs.qpe.editors.net.controller.editpart.editor.PlaceTransitionEditPart;

public class CutAction extends SelectionAction {

	public CutAction(IWorkbenchPart part, int style) {
		super(part, style);
	}

	public CutAction(IWorkbenchPart part) {
		super(part);
	}

	public void init() {
		setId(ActionFactory.CUT.getId());
		setText("Cut");
		setToolTipText("Cut");
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_CUT));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_CUT_DISABLED));
		setEnabled(false);
	}

	protected boolean calculateEnabled() {
		Iterator selectionIterator = this.getSelectedObjects().iterator();
		while (selectionIterator.hasNext()) {
			Object obj = selectionIterator.next();
			if (obj instanceof PlaceTransitionEditPart) {
				return true;
			}
		}
		return false;
	}

	public void run() {
		CutCommand cutCommand = new CutCommand((StructuredSelection) this.getSelection());
		cutCommand.setLabel("Cut");
		getCommandStack().execute(cutCommand);
	}
}
