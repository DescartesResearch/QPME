/*******************************************************************************
 * Copyright (c) 2004 Elias Volanakis.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Elias Volanakis - initial API and implementation
 *    Chris Aniszczyk - updated for RCP usage
 *    IBM Corporation
 *******************************************************************************/
package de.tud.cs.qpe.editors.incidence;

import java.io.File;

import org.dom4j.Element;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.AbstractRouter;
import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.views.properties.IPropertySheetPage;

import de.tud.cs.qpe.editors.incidence.controller.factory.IncidenceFunctionEditPartFactory;
import de.tud.cs.qpe.editors.incidence.gef.IncidenceFunctionEditorContextMenuProvider;
import de.tud.cs.qpe.editors.incidence.gef.palette.IncidenceFunctionEditorPaletteFactory;
import de.tud.cs.qpe.editors.incidence.gef.property.IncidenceFunctionPropertyPage;
import de.tud.cs.qpe.editors.net.controller.factory.DomElementFactory;
import de.tud.cs.qpe.utils.CloseableEditor;

/**
 * A graphical editor with flyout palette that can edit .qpn files.
 * 
 * @author Christofer Dutz
 */
public class IncidenceFunctionEditor extends GraphicalEditorWithFlyoutPalette implements CloseableEditor {
	/** Editor ID */
	public static final String ID = "de.tud.cs.qpe.editor.incidence";

	/** Palette component, holding the tools and shapes. */
	private static PaletteRoot PALETTE_MODEL;

	protected Element incidenceFunction;

	public IncidenceFunctionEditor() {
		super();
		setEditDomain(new DefaultEditDomain(this));
	}

	/**
	 * The <code>MultiPageEditorExample</code> implementation of this method
	 * checks that the input is an instance of <code>IFileEditorInput</code>.
	 */
	public void init(IEditorSite site, IEditorInput editorInput) throws PartInitException {
		if (!(editorInput instanceof IncidenceFunctionEditorInput))
			throw new PartInitException("Invalid Input: Must be IncidenceFunctionEditorInput");

		IncidenceFunctionEditorInput input = (IncidenceFunctionEditorInput) editorInput;
		incidenceFunction = input.getIncidenceFunction();

		File docFile = new File(incidenceFunction.getDocument().getRootElement().attributeValue("path", "new document.qpe"));
		String name = docFile.getName().substring(0, docFile.getName().length() - 4) + ":" + incidenceFunction.attributeValue("name", "new transition");
		this.setPartName(name);
		
		super.init(site, editorInput);
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
		viewer.setEditPartFactory(new IncidenceFunctionEditPartFactory());
		ScalableFreeformRootEditPart rootEditPart = new ScalableFreeformRootEditPart();
		viewer.setRootEditPart(rootEditPart);
		viewer.setKeyHandler(new GraphicalViewerKeyHandler(viewer));

		// TIP: Do everything to enable the zooming.
		ZoomManager manager = rootEditPart.getZoomManager();
		IAction action = new ZoomInAction(manager);
		getActionRegistry().registerAction(action);
		action = new ZoomOutAction(manager);
		getActionRegistry().registerAction(action);
		viewer.setProperty(MouseWheelHandler.KeyGenerator.getKey(SWT.CTRL), MouseWheelZoomHandler.SINGLETON);

		// configure the context menu provider
		ContextMenuProvider cmProvider = new IncidenceFunctionEditorContextMenuProvider(viewer, getActionRegistry());
		viewer.setContextMenu(cmProvider);
		getSite().registerContextMenu(cmProvider, viewer);
	}

	/**
	 * Set up the editor's inital content (after creation).
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette#initializeGraphicalViewer()
	 */
	protected void initializeGraphicalViewer() {
		super.initializeGraphicalViewer();
		GraphicalViewer viewer = getGraphicalViewer();

		// set the contents of this editor.
		viewer.setContents(getModel());

		// add the ShortestPathConnectionRouter.
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

		// listen for dropped parts
		viewer.addDropTargetListener(createTransferDropTargetListener());
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
	 * Create a transfer drop target listener. When using a
	 * CombinedTemplateCreationEntry tool in the palette, this will enable model
	 * element creation by dragging from the palette.
	 * 
	 * @see #createPaletteViewerProvider()
	 */
	private TransferDropTargetListener createTransferDropTargetListener() {
		return new TemplateTransferDropTargetListener(getGraphicalViewer()) {
			protected CreationFactory getFactory(Object template) {
				return new DomElementFactory((Class) template);
			}
		};
	}

	public void close(final boolean save) {
		Display display = getSite().getShell().getDisplay();
		display.asyncExec(new Runnable() {
			public void run() {
				getSite().getPage().closeEditor(IncidenceFunctionEditor.this, save);
			}
		});
	}

	public Element getModel() {
		return incidenceFunction;
	}

	public boolean isDirty() {
		// Since this is a child-page, it is never dirty.
		return false;
	}

	public void doSave(IProgressMonitor monitor) {
	}

	public boolean isSaveAsAllowed() {
		return true;
	}

	public void doSaveAs() {
	}

	protected FlyoutPreferences getPalettePreferences() {
		return IncidenceFunctionEditorPaletteFactory.createPalettePreferences();
	}

	protected PaletteRoot getPaletteRoot() {
		if (PALETTE_MODEL == null)
			PALETTE_MODEL = IncidenceFunctionEditorPaletteFactory.createPalette();
		return PALETTE_MODEL;
	}

	public Object getAdapter(Class type) {
		// Return a custom PropertySheetPage for
		// displaying the property sheets of the
		// incidence-function.
		if (type == IPropertySheetPage.class) {
			IncidenceFunctionPropertyPage propertyPage = new IncidenceFunctionPropertyPage(incidenceFunction);
			return propertyPage;
		}
		return super.getAdapter(type);
	}
}
