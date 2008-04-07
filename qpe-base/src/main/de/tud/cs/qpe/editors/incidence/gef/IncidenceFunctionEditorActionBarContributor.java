package de.tud.cs.qpe.editors.incidence.gef;

import org.eclipse.gef.ui.actions.ActionBarContributor;
import org.eclipse.gef.ui.actions.DeleteRetargetAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.gef.ui.actions.ZoomInRetargetAction;
import org.eclipse.gef.ui.actions.ZoomOutRetargetAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.actions.ActionFactory;

/**
 * Contributes actions to a toolbar. This class is tied to the editor in the
 * definition of editor-extension (see plugin.xml).
 * 
 * @author Christofer Dutz
 */
public class IncidenceFunctionEditorActionBarContributor extends ActionBarContributor {

	/**
	 * Create actions managed by this contributor.
	 * 
	 * @see org.eclipse.gef.ui.actions.ActionBarContributor#buildActions()
	 */
	protected void buildActions() {
		addRetargetAction(new ZoomInRetargetAction());
		addRetargetAction(new ZoomOutRetargetAction());
		addRetargetAction(new UndoRetargetAction());
		addRetargetAction(new RedoRetargetAction());
		addRetargetAction(new DeleteRetargetAction());
	}

	/**
	 * Add actions to the given toolbar.
	 * 
	 * @see org.eclipse.ui.part.EditorActionBarContributor#contributeToToolBar(org.eclipse.jface.action.IToolBarManager)
	 */
	public void contributeToToolBar(IToolBarManager toolBarManager) {
		toolBarManager.add(getAction(ActionFactory.UNDO.getId()));
		toolBarManager.add(getAction(ActionFactory.REDO.getId()));
		toolBarManager.add(new Separator());
		toolBarManager.add(getAction(GEFActionConstants.ZOOM_IN));
		toolBarManager.add(getAction(GEFActionConstants.ZOOM_OUT));
		toolBarManager.add(new Separator());
		toolBarManager.add(getAction(ActionFactory.DELETE.getId()));
	}

	public void contributeToMenu(IMenuManager menuManager) {
		// TIP: Add the copy/paste actions previously added edit-menu.
		IMenuManager editMenu = menuManager.findMenuUsingPath(IWorkbenchActionConstants.M_EDIT);

		GEFActionConstants.addStandardActionGroups(editMenu);

		editMenu.appendToGroup(GEFActionConstants.GROUP_UNDO, getAction(ActionFactory.UNDO.getId()));
		editMenu.appendToGroup(GEFActionConstants.GROUP_UNDO, getAction(ActionFactory.REDO.getId()));
		
		editMenu.appendToGroup(GEFActionConstants.GROUP_VIEW, getAction(GEFActionConstants.ZOOM_IN));
		editMenu.appendToGroup(GEFActionConstants.GROUP_VIEW, getAction(GEFActionConstants.ZOOM_OUT));

		editMenu.add(new Separator("de.tud.cs.qpe.editors.net.group.select"));
		editMenu.appendToGroup("de.tud.cs.qpe.editors.net.group.select", getAction(ActionFactory.DELETE.getId()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.ActionBarContributor#declareGlobalActionKeys()
	 */
	protected void declareGlobalActionKeys() {
/*		addGlobalActionKey(ActionFactory.UNDO.getId());
		addGlobalActionKey(ActionFactory.REDO.getId());
		addGlobalActionKey(GEFActionConstants.ZOOM_IN);
		addGlobalActionKey(GEFActionConstants.ZOOM_OUT);*/
		addGlobalActionKey(ActionFactory.DELETE.getId());
	}

}