package de.tud.cs.qpe.editors.net.gef.action;

import java.util.Iterator;

import org.dom4j.Element;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionDelegate;

import de.tud.cs.qpe.editors.incidence.IncidenceFunctionEditor;
import de.tud.cs.qpe.editors.incidence.IncidenceFunctionEditorInput;
import de.tud.cs.qpe.editors.net.controller.editpart.editor.TransitionEditPart;

public class OpenIncidenceFunctionDelegate extends ActionDelegate implements
		IObjectActionDelegate {
//	private IWorkbenchPart targetPart;

	private TransitionEditPart selected;

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
//		this.targetPart = targetPart;
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			Iterator iter = ((IStructuredSelection) selection).iterator();
			while (iter.hasNext()) {
				Object obj = iter.next();
				if (obj instanceof TransitionEditPart) {
					selected = (TransitionEditPart) obj;
				} else {
					selected = null;
				}
			}
			action.setEnabled(selected != null);
		}
	}

	public void run(IAction action) {
		if (selected != null) {
			if(!IncidenceFunctionEditorInput.isValid((Element) selected.getModel())) {
				MessageBox messageBox = new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.ICON_WARNING | SWT.OK);
				messageBox.setMessage("Before you can open the incidence function editor you must define at least one transition mode and at least one token color in each input and output place of the transition!");
				messageBox.open();
			} else {
				// Create a new input for the incidence-function editor.
				IEditorInput input = new IncidenceFunctionEditorInput(
						(Element) selected.getModel());
	
				// Open the incidence-function editor.
				if (input.exists()) {
					try {
						PlatformUI.getWorkbench().getActiveWorkbenchWindow()
								.getActivePage().openEditor(input,
										IncidenceFunctionEditor.ID, true);
					} catch (PartInitException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
