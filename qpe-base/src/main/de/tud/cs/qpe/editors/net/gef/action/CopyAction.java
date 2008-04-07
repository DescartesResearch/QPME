package de.tud.cs.qpe.editors.net.gef.action;

import java.util.Iterator;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import de.tud.cs.qpe.editors.net.controller.command.CopyCommand;
import de.tud.cs.qpe.editors.net.controller.editpart.editor.PlaceTransitionEditPart;

public class CopyAction extends SelectionAction {

	public CopyAction(IWorkbenchPart part, int style) {
		super(part, style);
	}

	public CopyAction(IWorkbenchPart part) {
		super(part);
	}
	
	public void init() {
		setId(ActionFactory.COPY.getId());
		setText("Copy");
		setToolTipText("Copy");
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
		setEnabled(false);
	}

	protected boolean calculateEnabled() {
		Iterator selectionIterator = this.getSelectedObjects().iterator();
		while(selectionIterator.hasNext()) {
			Object obj = selectionIterator.next();
			if(obj instanceof PlaceTransitionEditPart) {
				return true;
			}
		}
		return false;
	}
	
	public void run() {
		CopyCommand copyCommand = new CopyCommand((StructuredSelection) this.getSelection());
		// TIP: Since there is no sense in undoing a copy, we simply execute the command. No need to use the command-stack.
		copyCommand.execute();
	}
}
