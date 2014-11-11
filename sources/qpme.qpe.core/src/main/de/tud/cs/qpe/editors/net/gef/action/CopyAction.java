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
