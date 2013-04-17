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
 * 
 */
package de.tud.cs.qpe.editors.net;

import java.util.EventObject;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;
import org.dom4j.io.DOMWriter;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.AbstractRouter;
import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.SelectAllAction;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.properties.IPropertySheetPage;

import de.tud.cs.qpe.editors.net.controller.factory.NetEditPartFactory;
import de.tud.cs.qpe.editors.net.controller.factory.NetTreeEditPartFactory;
import de.tud.cs.qpe.editors.net.gef.NetEditorContextMenuProvider;
import de.tud.cs.qpe.editors.net.gef.action.CopyAction;
import de.tud.cs.qpe.editors.net.gef.action.CutAction;
import de.tud.cs.qpe.editors.net.gef.action.PasteAction;
import de.tud.cs.qpe.editors.net.gef.palette.NetEditorPaletteFactory;
import de.tud.cs.qpe.editors.net.gef.property.PlaceTransitionPropertyPage;
import edu.kit.ipd.descartes.qpme.model.migration.DocumentMigrationHandler;

/**
 * A graphical editor with flyout palette that can edit .qpn files.
 * 
 * @author Christofer Dutz
 */
public class NetEditorPage extends GraphicalEditorWithFlyoutPalette {
	/** Editor ID */
	public static final String ID = "de.tud.cs.qpe.editor.net";

	/** This is the root of the editor's model. */
	private Element diagram;

	/** Palette component, holding the tools and shapes. */
	private static PaletteRoot PALETTE_MODEL;

	protected GraphicalViewerKeyHandler keyHandler;

	/** Create a new ShapesEditor instance. This is called by the Workspace. */
	public NetEditorPage() {
		super();
		setEditDomain(new DefaultEditDomain(this));
	}

	/**
	 * Configure the graphical viewer before it receives contents.
	 * <p>
	 * This is the place to choose an appropriate RootEditPart and
	 * EditPartFactory for your editor. The RootEditPart determines the behavior
	 * of the editor's "work-area". For example, GEF includes zoomable and
	 * scrollable root edit parts. The EditPartFactory maps model elements to
	 * edit parts (controllers).
	 * </p>
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#configureGraphicalViewer()
	 */
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();

		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setEditPartFactory(new NetEditPartFactory());
		ScalableFreeformRootEditPart rootEditPart = new ScalableFreeformRootEditPart();
		viewer.setRootEditPart(rootEditPart);
		keyHandler = new GraphicalViewerKeyHandler(viewer);
		viewer.setKeyHandler(keyHandler);

		// TIP: Do everything to enable the zooming.
		ZoomManager manager = rootEditPart.getZoomManager();
		IAction action = new ZoomInAction(manager);
		getActionRegistry().registerAction(action);
		action = new ZoomOutAction(manager);
		getActionRegistry().registerAction(action);
		viewer.setProperty(MouseWheelHandler.KeyGenerator.getKey(SWT.CTRL), MouseWheelZoomHandler.SINGLETON);

		// configure the context menu provider
		ContextMenuProvider cmProvider = new NetEditorContextMenuProvider(viewer, getActionRegistry());
		viewer.setContextMenu(cmProvider);
		getSite().registerContextMenu(cmProvider, viewer);

		keyHandler.put(KeyStroke.getPressed('\177', 127, 0), getActionRegistry().getAction(ActionFactory.DELETE.getId()));
		keyHandler.put(KeyStroke.getPressed('a', 0x1, SWT.CTRL), getActionRegistry().getAction(ActionFactory.SELECT_ALL.getId()));
		keyHandler.put(KeyStroke.getPressed('A', 0x1, SWT.CTRL), getActionRegistry().getAction(ActionFactory.SELECT_ALL.getId()));
		keyHandler.put(KeyStroke.getPressed('c', 0x1, SWT.CTRL), getActionRegistry().getAction(ActionFactory.COPY.getId()));
		keyHandler.put(KeyStroke.getPressed('C', 0x1, SWT.CTRL), getActionRegistry().getAction(ActionFactory.COPY.getId()));
		keyHandler.put(KeyStroke.getPressed('v', 0x1, SWT.CTRL), getActionRegistry().getAction(ActionFactory.PASTE.getId()));
		keyHandler.put(KeyStroke.getPressed('V', 0x1, SWT.CTRL), getActionRegistry().getAction(ActionFactory.PASTE.getId()));

	}

	/**
	 * Set up the editor's inital content (after creation).
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette#initializeGraphicalViewer()
	 */
	protected void initializeGraphicalViewer() {
		super.initializeGraphicalViewer();
		GraphicalViewer viewer = getGraphicalViewer();

		// set the contents of this editor
		viewer.setContents(getModel());

		// add the ShortestPathConnectionRouter
		ScalableFreeformRootEditPart root = (ScalableFreeformRootEditPart) viewer.getRootEditPart();
		ConnectionLayer connLayer = (ConnectionLayer) root.getLayer(LayerConstants.CONNECTION_LAYER);

		AbstractRouter router;
		// Simple direct Line from one object to the other.
		router = new BendpointConnectionRouter();

		// Connection containing only horizontal and vertical lines.
		// router = new ManhattanConnectionRouter();

		// Rather complex path router.
		// router = new ShortestPathRouter();
		// GraphicalEditPart contentEditPart =
		// (GraphicalEditPart)root.getContents();
		// contentEditPart.getContentPane().addLayoutListener(router.getLayoutListener());

		connLayer.setConnectionRouter(router);
	}

	@SuppressWarnings("unchecked")
	protected void createActions() {
		super.createActions();

		ActionRegistry registry = getActionRegistry();
		IAction cutAction = new CutAction((IWorkbenchPart) this);
		registry.registerAction(cutAction);
		getSelectionActions().add(cutAction.getId());

		IAction copyAction = new CopyAction((IWorkbenchPart) this);
		registry.registerAction(copyAction);
		getSelectionActions().add(copyAction.getId());

		IAction pasteAction = new PasteAction((IWorkbenchPart) this);
		registry.registerAction(pasteAction);
		getSelectionActions().add(pasteAction.getId());

		IAction selectAllAction = new SelectAllAction(this);
		registry.registerAction(selectAllAction);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#commandStackChanged(java.util.EventObject)
	 */
	public void commandStackChanged(EventObject event) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
		super.commandStackChanged(event);
	}

	// TIP: The original selectionChanged will ignore selection
	// changes, that are propagated from the multipage editor
	// since it thinks it is not responsible ... we simply do
	// what the original would have done anyway :)
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		super.selectionChanged(part, selection);
		if (this.equals(part)) { // Propagated from MyMultiPageEditor.
			updateActions(getSelectionActions());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette#createPaletteViewerProvider()
	 */
	protected PaletteViewerProvider createPaletteViewerProvider() {
		return new PaletteViewerProvider(getEditDomain()) {
			protected void configurePaletteViewer(PaletteViewer viewer) {
				super.configurePaletteViewer(viewer);
				// create a drag source listener for this palette viewer
				// together with an appropriate transfer drop target listener,
				// this will enable
				// model element creation by dragging a
				// CombinatedTemplateCreationEntries
				// from the palette into the editor
				// @see ShapesEditor#createTransferDropTargetListener()
				viewer.addDragSourceListener(new TemplateTransferDragSourceListener(viewer));
			}
		};
	}

	/**
	 * @see org.eclipse.ui.ISaveablePart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void doSave(IProgressMonitor monitor) {
		getCommandStack().markSaveLocation();
	}

	/**
	 * @see org.eclipse.ui.ISaveablePart#doSaveAs()
	 */
	public void doSaveAs() {
	}

	public Element getModel() {
		return diagram;
	}

	public boolean isDirty() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette#getPalettePreferences()
	 */
	protected FlyoutPreferences getPalettePreferences() {
		return NetEditorPaletteFactory.createPalettePreferences();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette#getPaletteRoot()
	 */
	protected PaletteRoot getPaletteRoot() {
		if (PALETTE_MODEL == null)
			PALETTE_MODEL = NetEditorPaletteFactory.createPalette();
		return PALETTE_MODEL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.ISaveablePart#isSaveAsAllowed()
	 */
	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	 * Uses a ShapesEditorInput to serve as a dummy editor input It is up to the
	 * editor input to supply the initial shapes diagram
	 * 
	 * @see org.eclipse.ui.part.EditorPart#setInput(org.eclipse.ui.IEditorInput)
	 */
	protected void setInput(IEditorInput input) {
		super.setInput(input);
		NetEditorInput netInput = (NetEditorInput) input;
		diagram = netInput.getNetDiagram();
		setPartName(netInput.getName());
	}

	public Object getAdapter(Class type) {
		// Return a custom PropertySheetPage for
		// displaying the property sheets of the
		// net elements.
		if (type == IPropertySheetPage.class) {
			PlaceTransitionPropertyPage p = new PlaceTransitionPropertyPage(diagram);
//			getSite().getPage().addSelectionListener(p);
			return p;
		}
		if (type == IContentOutlinePage.class) {
			return new NetOutlinePage(new TreeViewer());
		}
		if (type == ZoomManager.class) {
			return ((ScalableRootEditPart) getGraphicalViewer().getRootEditPart()).getZoomManager();
		}
		return super.getAdapter(type);

	}

	/**
	 * Creates an outline pagebook for this editor.
	 */
	public class NetOutlinePage extends ContentOutlinePage {
		/**
		 * Create a new outline page for the shapes editor.
		 * 
		 * @param viewer
		 *            a viewer (TreeViewer instance) used for this outline page
		 * @throws IllegalArgumentException
		 *             if editor is null
		 */

		public NetOutlinePage(EditPartViewer viewer) {
			super(viewer);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.ui.part.IPage#createControl(org.eclipse.swt.widgets.Composite)
		 */
		public void createControl(Composite parent) {
			// create outline viewer page
			getViewer().createControl(parent);
			// configure outline viewer
			getViewer().setEditDomain(getEditDomain());
			getViewer().setEditPartFactory(new NetTreeEditPartFactory());
			// configure & add context menu to viewer
			ContextMenuProvider cmProvider = new NetEditorContextMenuProvider(getViewer(), getActionRegistry());
			getViewer().setContextMenu(cmProvider);
			// hook outline viewer
			getSelectionSynchronizer().addViewer(getViewer());
			// initialize outline viewer with model
			getViewer().setContents(getModel());
			// show outline viewer
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.ui.part.IPage#dispose()
		 */
		public void dispose() {
			// unhook outline viewer
			getSelectionSynchronizer().removeViewer(getViewer());
			// dispose
			super.dispose();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.ui.part.IPage#getControl()
		 */
		public Control getControl() {
			return getViewer().getControl();
		}

		/**
		 * @see org.eclipse.ui.part.IPageBookViewPage#init(org.eclipse.ui.part.IPageSite)
		 */
		public void init(IPageSite pageSite) {
			super.init(pageSite);
			ActionRegistry registry = getActionRegistry();
			IActionBars bars = pageSite.getActionBars();
			String id = ActionFactory.UNDO.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));
			id = ActionFactory.REDO.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));
			id = ActionFactory.COPY.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));
			id = ActionFactory.PASTE.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));
			id = ActionFactory.DELETE.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));
			id = ActionFactory.SELECT_ALL.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));
		}
	}

}