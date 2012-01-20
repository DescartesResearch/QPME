/* ==============================================
 * QPME : Queueing Petri net Modeling Environment
 * ==============================================
 *
 * (c) Copyright 2003-2011, by Samuel Kounev and Contributors.
 * 
 * Project Info:   http://descartes.ipd.kit.edu/projects/qpme/
 *                 http://www.descartes-research.net/
 *    
 * All rights reserved. This software is made available under the terms of the 
 * Eclipse Public License (EPL) v1.0 as published by the Eclipse Foundation
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * This software is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the Eclipse Public License (EPL)
 * for more details.
 *
 * You should have received a copy of the Eclipse Public License (EPL)
 * along with this software; if not visit http://www.eclipse.org or write to
 * Eclipse Foundation, Inc., 308 SW First Avenue, Suite 110, Portland, 97204 USA
 * Email: license (at) eclipse.org 
 *  
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *                                
 * =============================================
 *
 * Original Author(s):  Samuel Kounev and Christofer Dutz
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2006        Christofer Dutz   Created.
 * 
 */
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
