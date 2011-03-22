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
 *  2009/08/03  Frederik Zipp     Preferences.
 *  2010/15/01  Philipp Meier     Removed unused PreferencesPage and Action.
 */
package de.tud.cs.qpe.rcp;

import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import de.tud.cs.qpe.rcp.actions.file.CloseAction;
import de.tud.cs.qpe.rcp.actions.file.CloseAllAction;
import de.tud.cs.qpe.rcp.actions.file.ExitAction;
import de.tud.cs.qpe.rcp.actions.file.NewAction;
import de.tud.cs.qpe.rcp.actions.file.OpenAction;
import de.tud.cs.qpe.rcp.actions.file.SaveAction;
import de.tud.cs.qpe.rcp.actions.file.SaveAllAction;
import de.tud.cs.qpe.rcp.actions.file.SaveAsAction;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	// private IWorkbenchAction exitAction;

	private IWorkbenchAction newAction;

	private IWorkbenchAction openAction;

	private IWorkbenchAction closeAction;

	private IWorkbenchAction closeAllAction;

	private IWorkbenchAction saveAction;

	private IWorkbenchAction saveAsAction;

	private IWorkbenchAction saveAllAction;

	private IWorkbenchAction exitAction;

	private IWorkbenchAction introAction;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	protected void makeActions(IWorkbenchWindow window) {
		newAction = new NewAction();
		register(newAction);
		openAction = new OpenAction();
		register(openAction);
		closeAction = new CloseAction();
		register(closeAction);
		closeAllAction = new CloseAllAction();
		register(closeAllAction);
		saveAction = new SaveAction();
		register(saveAction);
		saveAsAction = new SaveAsAction();
		register(saveAsAction);
		saveAllAction = new SaveAllAction();
		register(saveAllAction);
		exitAction = new ExitAction();
		register(exitAction);
		introAction = ActionFactory.INTRO.create(window);
		register(introAction);
	}

	protected void fillMenuBar(IMenuManager menuBar) {
		// TIP: Since some anoying plugins are contributing
		// their menu entries either some anoying errors will
		// appear in the console or we add the menus they
		// are looking for and simply hide them. I know this
		// is sort of ugly ... but it works and is certainly
		// prettier than errors in the logs or stupid menues 
		// in the menu-bar.
		MenuManager hiddenMenu = new MenuManager("&Help", IWorkbenchActionConstants.M_HELP);
		menuBar.add(hiddenMenu);
		hiddenMenu.setVisible(false);
		
		// TIP: If I don't setup the menus here,
		// they will appear in random order.
		MenuManager fileMenu = new MenuManager("&File", "qpeFile");
		menuBar.add(fileMenu);
		fileMenu.add(new Separator("new"));
		fileMenu.add(newAction);
		fileMenu.add(new Separator("open"));
		fileMenu.add(openAction);
		fileMenu.add(new Separator("close"));
		fileMenu.add(closeAction);
		fileMenu.add(closeAllAction);
		fileMenu.add(new Separator("save"));
		fileMenu.add(saveAction);
		fileMenu.add(saveAsAction);
		fileMenu.add(saveAllAction);
		fileMenu.add(new Separator("quit"));
		fileMenu.add(exitAction);

		MenuManager editMenu = new MenuManager("&Edit", IWorkbenchActionConstants.M_EDIT);
		menuBar.add(editMenu);

		MenuManager viewMenu = new MenuManager("&View", "qpeView");
		menuBar.add(viewMenu);

		MenuManager extrasMenu = new MenuManager("&Tools", "qpeTools");
		menuBar.add(extrasMenu);

		MenuManager helpMenu = new MenuManager("&Help", "qpeHelp");
		menuBar.add(helpMenu);
		helpMenu.add(introAction);
	}

	protected void fillCoolBar(ICoolBarManager coolBar) {
		coolBar.setLockLayout(true);
		IToolBarManager fileBar = new ToolBarManager();
		coolBar.add(new ToolBarContributionItem(fileBar, "file"));
		fileBar.add(newAction);
		fileBar.add(new Separator());
		fileBar.add(openAction);
		fileBar.add(new Separator());
		fileBar.add(saveAction);
		fileBar.add(saveAllAction);
	}
}
