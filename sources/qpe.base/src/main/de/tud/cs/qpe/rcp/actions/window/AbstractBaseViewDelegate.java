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
package de.tud.cs.qpe.rcp.actions.window;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionDelegate;

public abstract class AbstractBaseViewDelegate extends ActionDelegate implements IWorkbenchWindowActionDelegate, IPartListener2 {
	protected IAction action;

	protected abstract String getViewId();

	protected abstract String getPartName();

	public void init(IWorkbenchWindow window) {
		window.getActivePage().addPartListener(this);
	}

	public void init(IAction action) {
		this.action = action;
		IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IViewReference visibleViews[] = activePage.getViewReferences();
		for (int i = 0; i < visibleViews.length; i++) {
			if (getViewId().equals(visibleViews[i].getId())) {
				action.setChecked(true);
				break;
			}
		}
	}

	public void run(IAction action) {
		IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IViewReference visibleViews[] = activePage.getViewReferences();
		for (int i = 0; i < visibleViews.length; i++) {
			if (getViewId().equals(visibleViews[i].getId())) {
				activePage.hideView(visibleViews[i]);
				action.setChecked(false);
				return;
			}
		}
		try {
			activePage.showView(getViewId());
			action.setChecked(true);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	public void partActivated(IWorkbenchPartReference partRef) {
	}

	public void partBroughtToTop(IWorkbenchPartReference partRef) {
	}

	public void partClosed(IWorkbenchPartReference partRef) {
		if (getPartName().equals(partRef.getPartName())) {
			action.setChecked(false);
		}
	}

	public void partDeactivated(IWorkbenchPartReference partRef) {
	}

	public void partHidden(IWorkbenchPartReference partRef) {
	}

	public void partInputChanged(IWorkbenchPartReference partRef) {
	}

	public void partOpened(IWorkbenchPartReference partRef) {
		if (getPartName().equals(partRef.getPartName())) {
			action.setChecked(true);
		}
	}

	public void partVisible(IWorkbenchPartReference partRef) {
	}
}
