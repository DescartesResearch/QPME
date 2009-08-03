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
import de.tud.cs.qpe.rcp.actions.file.PreferencesAction;
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

	private IWorkbenchAction preferencesAction;

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
		preferencesAction = new PreferencesAction();
		register(preferencesAction);
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
		fileMenu.add(new Separator("preferences"));
		fileMenu.add(preferencesAction);
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
