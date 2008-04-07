package de.tud.cs.qpe.editors.net.gef.action;

import java.util.Iterator;

import org.dom4j.Element;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionDelegate;

import de.tud.cs.qpe.editors.net.controller.editpart.editor.SubnetPlaceEditPart;
import de.tud.cs.qpe.editors.subnet.SubnetEditor;
import de.tud.cs.qpe.editors.subnet.SubnetEditorInput;

public class OpenSubnetDelegate extends ActionDelegate implements
		IObjectActionDelegate {

	private SubnetPlaceEditPart selected;

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			Iterator iter = ((IStructuredSelection) selection).iterator();
			while (iter.hasNext()) {
				Object obj = iter.next();
				if (obj instanceof SubnetPlaceEditPart) {
					selected = (SubnetPlaceEditPart) obj;
				} else {
					selected = null;
				}
			}
			action.setEnabled(selected != null);
		}
	}

	public void run(IAction action) {
		if (selected != null) {
			// Create a new input for the subnet editor.
			IEditorInput input = new SubnetEditorInput(((Element) selected.getModel()).element("subnet"));

			// Open the subnet editor.
			if (input.exists()) {
				try {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getActivePage().openEditor(input, SubnetEditor.ID,
									true);
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
