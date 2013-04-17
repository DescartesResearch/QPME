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
package de.tud.cs.qpe.editors.net.gef;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.DeleteRetargetAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.gef.ui.actions.ZoomInRetargetAction;
import org.eclipse.gef.ui.actions.ZoomOutRetargetAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.RetargetAction;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;

/**
 * Contributes actions to a toolbar. This class is tied to the editor in the
 * definition of editor-extension (see plugin.xml).
 * 
 * @author Christofer Dutz
 */
public class NetEditorActionBarContributor extends MultiPageEditorActionBarContributor implements ISelectionListener {

	private ActionRegistry registry = new ActionRegistry();

	/**
	 * Contains the {@link RetargetAction}s that are registered as global
	 * action handlers. We need to hold on to these so that we can remove them
	 * as PartListeners in dispose().
	 */
	private List<RetargetAction> retargetActions = new ArrayList<RetargetAction>();

	private List<String> globalActionKeys = new ArrayList<String>();

	/**
	 * Adds the given action to the action registry.
	 * 
	 * @param action
	 *            the action to add
	 */
	protected void addAction(IAction action) {
		getActionRegistry().registerAction(action);
	}

	/**
	 * Indicates the existence of a global action identified by the specified
	 * key. This global action is defined outside the scope of this contributor,
	 * such as the Workbench's undo action, or an action provided by a workbench
	 * ActionSet. The list of global action keys is used whenever the active
	 * editor is changed ({@link #setActiveEditor(IEditorPart)}). Keys
	 * provided here will result in corresponding actions being obtained from
	 * the active editor's <code>ActionRegistry</code>, and those actions
	 * will be registered with the ActionBars for this contributor. The editor's
	 * action handler and the global action must have the same key.
	 * 
	 * @param key
	 *            the key identifying the global action
	 */
	protected void addGlobalActionKey(String key) {
		globalActionKeys.add(key);
	}

	/**
	 * Adds the specified RetargetAction to this contributors
	 * <code>ActionRegistry</code>. The RetargetAction is also added as a
	 * <code>IPartListener</code> of the contributor's page. Also, the
	 * retarget action's ID is flagged as a global action key, by calling {@link
	 * #addGlobalActionKey(String)}.
	 * 
	 * @param action
	 *            the retarget action being added
	 */
	protected void addRetargetAction(RetargetAction action) {
		addAction(action);
		retargetActions.add(action);
		getPage().addPartListener(action);
		addGlobalActionKey(action.getId());
	}

	/**
	 * Disposes the contributor. Removes all {@link RetargetAction}s that were
	 * {@link org.eclipse.ui.IPartListener}s on the
	 * {@link org.eclipse.ui.IWorkbenchPage} and disposes them. Also disposes
	 * the action registry.
	 * <P>
	 * Subclasses may extend this method to perform additional cleanup.
	 * 
	 * @see org.eclipse.ui.part.EditorActionBarContributor#dispose()
	 */
	public void dispose() {
		for (int i = 0; i < retargetActions.size(); i++) {
			RetargetAction action = (RetargetAction) retargetActions.get(i);
			getPage().removePartListener(action);
			action.dispose();
		}
		registry.dispose();
		retargetActions = null;
		registry = null;
	}

	/**
	 * Retrieves an action from the action registry using the given ID.
	 * 
	 * @param id
	 *            the ID of the sought action
	 * @return <code>null</code> or the action if found
	 */
	protected IAction getAction(String id) {
		return getActionRegistry().getAction(id);
	}

	/**
	 * returns this contributor's ActionRegsitry.
	 * 
	 * @return the ActionRegistry
	 */
	protected ActionRegistry getActionRegistry() {
		return registry;
	}

	/**
	 * @see org.eclipse.ui.part.EditorActionBarContributor#init(IActionBars)
	 */
	public void init(IActionBars bars) {
		buildActions();
		declareGlobalActionKeys();
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().addSelectionListener(this);
		super.init(bars);
	}

	/**
	 * @see org.eclipse.ui.IEditorActionBarContributor#setActiveEditor(IEditorPart)
	 */
	public void setActiveEditor(IEditorPart editor) {
		ActionRegistry registry = (ActionRegistry) editor.getAdapter(ActionRegistry.class);
		if(registry != null) {
			IActionBars bars = getActionBars();
			for (int i = 0; i < globalActionKeys.size(); i++) {
				String id = (String) globalActionKeys.get(i);
				bars.setGlobalActionHandler(id, registry.getAction(id));
			}
		}
		getActionBars().updateActionBars();
	}

	protected void buildActions() {
		IWorkbenchWindow workbenchWindow = getPage().getWorkbenchWindow();
		addRetargetAction(new UndoRetargetAction());
		addRetargetAction(new RedoRetargetAction());
		addRetargetAction(new ZoomInRetargetAction());
		addRetargetAction(new ZoomOutRetargetAction());
		addRetargetAction((RetargetAction) ActionFactory.CUT.create(workbenchWindow));
		addRetargetAction((RetargetAction) ActionFactory.COPY.create(workbenchWindow));
		addRetargetAction((RetargetAction) ActionFactory.PASTE.create(workbenchWindow));
		addRetargetAction((RetargetAction) ActionFactory.SELECT_ALL.create(workbenchWindow));
		addRetargetAction(new DeleteRetargetAction());
	}

	public void contributeToToolBar(IToolBarManager toolBar) {
		IToolBarManager netEditBar = new ToolBarManager();
		toolBar.add(new ToolBarContributionItem(netEditBar, "file")); //$NON-NLS-1$

		toolBar.add(getAction(ActionFactory.UNDO.getId()));
		toolBar.add(getAction(ActionFactory.REDO.getId()));
		toolBar.add(new Separator());
		toolBar.add(getAction(GEFActionConstants.ZOOM_IN));
		toolBar.add(getAction(GEFActionConstants.ZOOM_OUT));
		toolBar.add(new Separator());
		toolBar.add(getAction(ActionFactory.CUT.getId()));
		toolBar.add(getAction(ActionFactory.COPY.getId()));
		toolBar.add(getAction(ActionFactory.PASTE.getId()));
		toolBar.add(new Separator());
		//toolBar.add(getAction(ActionFactory.SELECT_ALL.getId()));
		toolBar.add(getAction(ActionFactory.DELETE.getId()));
	}

	public void contributeToMenu(IMenuManager menuManager) {
		// TIP: Add the copy/paste actions previously added edit-menu.
		IMenuManager editMenu = menuManager.findMenuUsingPath(IWorkbenchActionConstants.M_EDIT);

		GEFActionConstants.addStandardActionGroups(editMenu);

		editMenu.appendToGroup(GEFActionConstants.GROUP_UNDO, getAction(ActionFactory.UNDO.getId()));
		editMenu.appendToGroup(GEFActionConstants.GROUP_UNDO, getAction(ActionFactory.REDO.getId()));

		editMenu.appendToGroup(GEFActionConstants.GROUP_VIEW, getAction(GEFActionConstants.ZOOM_IN));
		editMenu.appendToGroup(GEFActionConstants.GROUP_VIEW, getAction(GEFActionConstants.ZOOM_OUT));

		editMenu.appendToGroup(GEFActionConstants.GROUP_EDIT, getAction(ActionFactory.CUT.getId()));
		editMenu.appendToGroup(GEFActionConstants.GROUP_EDIT, getAction(ActionFactory.COPY.getId()));
		editMenu.appendToGroup(GEFActionConstants.GROUP_EDIT, getAction(ActionFactory.PASTE.getId()));

		editMenu.add(new Separator("de.tud.cs.qpe.editors.net.group.select"));
		editMenu.appendToGroup("de.tud.cs.qpe.editors.net.group.select", getAction(ActionFactory.DELETE.getId()));
		editMenu.appendToGroup("de.tud.cs.qpe.editors.net.group.select", getAction(ActionFactory.SELECT_ALL.getId()));
	}

	protected void declareGlobalActionKeys() {
/*		addGlobalActionKey(ActionFactory.UNDO.getId());
		addGlobalActionKey(ActionFactory.REDO.getId());
		addGlobalActionKey(GEFActionConstants.ZOOM_IN);
		addGlobalActionKey(GEFActionConstants.ZOOM_OUT);
		addGlobalActionKey(ActionFactory.CUT.getId());
		addGlobalActionKey(ActionFactory.COPY.getId());
		addGlobalActionKey(ActionFactory.PASTE.getId());*/
		addGlobalActionKey(ActionFactory.DELETE.getId());
/*		addGlobalActionKey(ActionFactory.SELECT_ALL.getId());*/
	}

	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		getActionBars().updateActionBars();
	}

	public void setActivePage(IEditorPart activeEditor) {
		if (activeEditor != null) {
			ActionRegistry registry = (ActionRegistry) activeEditor.getAdapter(ActionRegistry.class);
			if (registry != null) {
				IActionBars bars = getActionBars();
				for (int i = 0; i < globalActionKeys.size(); i++) {
					String id = (String) globalActionKeys.get(i);
					bars.setGlobalActionHandler(id, registry.getAction(id));
				}
			}
			getActionBars().updateActionBars();
		}
	}
}