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
�* http://www.eclipse.org/legal/epl-v10.html
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
 *  2009/08/03  Frederik Zipp     Preferences.
 *  2010/15/01  Philipp Meier     Removed unused PreferencesPage and Action.
 */
package de.tud.cs.qpe.rcp;

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.contexts.IContextService;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private IWorkbenchWindow window;
	
	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	protected void makeActions(IWorkbenchWindow window) {
		this.window = window;
	}

	protected void fillMenuBar(IMenuManager menuBar) {
		menuBar.add(new MenuManager("&File", IWorkbenchActionConstants.M_FILE));
		menuBar.add(new MenuManager("&Edit", IWorkbenchActionConstants.M_EDIT));
		menuBar.add(new MenuManager("&Navigate",
				IWorkbenchActionConstants.M_NAVIGATE));
		menuBar.add(new MenuManager("&Project",
				IWorkbenchActionConstants.M_PROJECT));
		menuBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
		menuBar.add(new MenuManager("&Run", "qpme.qpe.run"));
		MenuManager windowMenu = new MenuManager("&Window",
				IWorkbenchActionConstants.M_WINDOW);
		{
			MenuManager showViewMenuMgr = new MenuManager(
					"Show &View", "showView");
			IContributionItem showViewMenu = ContributionItemFactory.VIEWS_SHORTLIST
					.create(window);
			showViewMenuMgr.add(showViewMenu);
			windowMenu.add(showViewMenuMgr);
		}
		menuBar.add(windowMenu);

		menuBar.add(new MenuManager("&Help", IWorkbenchActionConstants.M_HELP));

		// Activate qpme context so that certain standard commands are overriden
		IContextService contextService = (IContextService) PlatformUI
				.getWorkbench().getService(IContextService.class);
		contextService.activateContext("qpme.qpe.core.context");
	}

	protected void fillCoolBar(ICoolBarManager coolBar) {
		// coolBar.setLockLayout(true);
		// IToolBarManager fileBar = new ToolBarManager();
		// coolBar.add(new ToolBarContributionItem(fileBar, "file"));
		// fileBar.add(newAction);
		// fileBar.add(new Separator());
		// fileBar.add(openAction);
		// fileBar.add(new Separator());
		// fileBar.add(saveAction);
		// fileBar.add(saveAllAction);
	}
}
