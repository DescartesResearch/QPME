package de.tud.cs.qpe.editors.net.controller.editpart.editor;

import org.dom4j.Element;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import de.tud.cs.qpe.editors.incidence.IncidenceFunctionEditor;
import de.tud.cs.qpe.editors.incidence.IncidenceFunctionEditorInput;

public class TransitionEditPart extends PlaceTransitionEditPart {

	public TransitionEditPart() {
		super();
	}

	// TIP: To be able to listen for double-clicks on transitions, we have to override the performRequest method.
	public void performRequest(Request req) {
		if(req instanceof SelectionRequest) {
			SelectionRequest selectionReq = (SelectionRequest) req;
			if("open".equals(selectionReq.getType())) {
				if(!IncidenceFunctionEditorInput.isValid((Element) getModel())) {
					MessageBox messageBox = new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.ICON_WARNING | SWT.OK);
					messageBox.setMessage("Before you can open the incidence function editor you must define at least one transition mode and at least one token color in each input and output place of the transition!");
					messageBox.open();
				} else {
					// Create a new input for the incidence-function editor.
					IEditorInput input = new IncidenceFunctionEditorInput(
							(Element) getModel());
	
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
		super.performRequest(req);
	}
}
