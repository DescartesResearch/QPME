package de.tud.cs.qpe.editors.net.controller.editpart.editor;

import org.dom4j.Element;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import de.tud.cs.qpe.editors.subnet.SubnetEditor;
import de.tud.cs.qpe.editors.subnet.SubnetEditorInput;

public class SubnetPlaceEditPart extends PlaceEditPart {
	// TIP: To be able to listen for double-clicks on transitions, we have to override the performRequest method.
	public void performRequest(Request req) {
		if(req instanceof SelectionRequest) {
			SelectionRequest selectionReq = (SelectionRequest) req;
			if("open".equals(selectionReq.getType())) {
				// Create a new input for the subnet editor.
				IEditorInput input = new SubnetEditorInput(
						((Element) getModel()).element("subnet"));

				// Open the subnet editor.
				if (input.exists()) {
					try {
						PlatformUI.getWorkbench().getActiveWorkbenchWindow()
								.getActivePage().openEditor(input,
										SubnetEditor.ID, true);
					} catch (PartInitException e) {
						e.printStackTrace();
					}
				}
			}
		}
		super.performRequest(req);
	}

}
