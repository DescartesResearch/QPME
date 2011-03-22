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
package de.tud.cs.qpe.rcp.actions.file;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import de.tud.cs.qpe.QPEBasePlugin;
import de.tud.cs.qpe.editors.net.MultipageNetEditor;
import de.tud.cs.qpe.editors.net.NetEditorInput;
import de.tud.cs.qpe.model.DocumentManager;

public class SaveAction extends Action implements ActionFactory.IWorkbenchAction {
	public SaveAction() {
		setId(ActionFactory.SAVE.getId());
		setText("Save");
		setToolTipText("Save");
		setImageDescriptor(QPEBasePlugin.getImageDescriptor("images/save.png"));
		setDisabledImageDescriptor(QPEBasePlugin.getImageDescriptor("images/save_d.png"));
	}

	public void run() {
		IWorkbenchPage workbenchPage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart editorPart = workbenchPage.getActiveEditor();
		if (editorPart instanceof MultipageNetEditor) {
			MultipageNetEditor editor = (MultipageNetEditor) editorPart;
			NetEditorInput input = (NetEditorInput) editor.getEditorInput();

			// If a new file, was created, the path is not set.
			// so show the Save-Dialog.
			if (input.getNetDiagram().attributeValue("path") == null) {
				String path = openFileDialog();
				if (path != null) {
					DocumentManager.setAttribute(input.getNetDiagram(), "path", path);
				}
			}
			if (input.getNetDiagram().attributeValue("path") != null) {
				editor.doSave(null);
			}
		}
	}

	private String openFileDialog() {
		FileDialog dialog = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.SAVE);
		dialog.setText("Save");
		dialog.setFilterExtensions(new String[] { "*.qpe" });
		return dialog.open();
	}

	public void dispose() {
	}
}
