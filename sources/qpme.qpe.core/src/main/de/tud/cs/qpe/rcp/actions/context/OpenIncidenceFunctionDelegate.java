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
package de.tud.cs.qpe.rcp.actions.context;

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
