/* ==============================================
 * QPME : Queueing Petri net Modeling Environment
 * ==============================================
 *
 * (c) Copyright 2003-2010, by Samuel Kounev and Contributors.
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
 *  2010/15/01  Philipp Meier     Show both *.qpn and *.simqpn files in dialog for File->Open File.
 * 
 */
package de.tud.cs.qpe.rcp.actions.file;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import de.tud.cs.qpe.QPEBasePlugin;
import de.tud.cs.qpe.editors.net.NetEditorInput;
import de.tud.cs.qpe.editors.net.NetEditorPage;
import de.tud.cs.qpe.editors.query.QueryEditorInput;
import de.tud.cs.qpe.editors.query.SimpleQueryEditor;

public class OpenAction extends Action implements ActionFactory.IWorkbenchAction {
	public OpenAction() {
		setId("open");
		setText("Open File...");
		setToolTipText("Open File...");
		setImageDescriptor(QPEBasePlugin.getImageDescriptor("images/open.png"));
		setDisabledImageDescriptor(QPEBasePlugin.getImageDescriptor("images/open_d.png"));
	}

	public void run() {
		String path = openFileDialog();
		if (path != null) {
			IEditorInput input = null;
			String editor = null;
			if (path.endsWith(".qpe")) {
				input = new NetEditorInput(new Path(path));
				editor = NetEditorPage.ID;
			}
			if (path.endsWith(".simqpn")) {
				input = new QueryEditorInput(new Path(path));
				editor = SimpleQueryEditor.ID;
			}
			if (input != null && input.exists()) {
				try {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(input, editor, true);
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String openFileDialog() {
		FileDialog dialog = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.OPEN);
		dialog.setText("Open QPE document");		
		dialog.setFilterExtensions(new String[] { "*.qpe;*.simqpn", "*.qpe", "*.simqpn"});
		return dialog.open();
	}

	public void dispose() {
	}
}
