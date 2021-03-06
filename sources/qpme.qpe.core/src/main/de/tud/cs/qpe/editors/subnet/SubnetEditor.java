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
package de.tud.cs.qpe.editors.subnet;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import org.dom4j.tree.DefaultAttribute;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.MultiPageEditorPart;

import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.utils.CloseableEditor;

public class SubnetEditor extends MultiPageEditorPart implements
		PropertyChangeListener, ISelectionListener, CloseableEditor {
	/** Editor ID */
	public static final String ID = "de.tud.cs.qpe.editor.subnet";

	protected SubnetEditorPage subnetEditor;

	protected SubnetColorEditorPage colorEditor;

	public SubnetEditor() {
		super();
	}

	/**
	 * The <code>MultiPageEditorExample</code> implementation of this method
	 * checks that the input is an instance of <code>IFileEditorInput</code>.
	 */
	public void init(IEditorSite site, IEditorInput editorInput)
			throws PartInitException {
		if (!(editorInput instanceof SubnetEditorInput))
			throw new PartInitException(
					"Invalid Input: Must be SubnetEditorInput");

		this.setPartName(((SubnetEditorInput) editorInput).getName());
		super.init(site, editorInput);

		// Add editor as listener to modifications of the
		// current document.
		DocumentManager.addPropertyChangeListener(((SubnetEditorInput) editorInput).getDocument().getRootElement(), this);

		// TIP: Since MultipageEditors don't automatically listen
		// for selection events, we have to register manually.
		// This is needed in order to be able to propagate the
		// selection-events to the child editor.
		getSite().getWorkbenchWindow().getSelectionService()
				.addSelectionListener(this);
	}
	
	protected void createGraphicalEditorPage() {
		// The first Page is where the GEF-Net-Editor is
		// located.
		try {
			// Create instance of the new editor page.
			subnetEditor = new SubnetEditorPage();

			// Initialize the editor page and set the input.
			int index = addPage(subnetEditor, getEditorInput());

			// Set the page title
			setPageText(index, "Subnet Editor");
		} catch (PartInitException e) {
			ErrorDialog.openError(getSite().getShell(),
					"Error creating subnet editor", null, e.getStatus());
		}
	}

	protected void createColorEditorPage() {
		try {
			colorEditor = new SubnetColorEditorPage();
			int index = addPage(colorEditor, getEditorInput());
			setPageText(index, "Colors");
		} catch(PartInitException e) {
			ErrorDialog.openError(getSite().getShell(), "Error creating color editor", null, e.getStatus());
		}
	}

	protected void createPages() {
		createGraphicalEditorPage();
		createColorEditorPage();
	}

	public void close(final boolean save) {
		Display display = getSite().getShell().getDisplay();
		display.asyncExec(new Runnable() {
			public void run() {
				getSite().getPage().closeEditor(SubnetEditor.this, save);
			}
		});
	}

	public void dispose() {
		super.dispose();
		DocumentManager.removePropertyChangeListener(((SubnetEditorInput) this.getEditorInput()).getDocument().getRootElement(), this);
		// Close all incidence function for this net when closing
		// the nets main editor.
		// TODO: Only close subnet and incidence function editors that are children of the current subnet editor.
//		SubnetEditorInput curNetEditorInput = (SubnetEditorInput) this
//				.getEditorInput();
//		String curEditorNetId = curNetEditorInput.getDocument()
//				.getRootElement().attributeValue("event-manager-id");
//
//		IWorkbenchWindow activeWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
//		if (activeWindow != null) {
//			IWorkbenchPage activePage = activeWindow.getActivePage();
//			if (activePage != null) {
//				IEditorReference[] editors = activePage.getEditorReferences();
//				for (int i = 0; i < editors.length; i++) {
//					try {
//						IEditorInput editorInput = editors[i].getEditorInput();
//						if (editors[i].getEditor(false) instanceof CloseableEditor) {
//							if (editorInput instanceof SubnetEditorInput) {
//								SubnetEditorInput input = (SubnetEditorInput) editorInput;
//								String editorInputId = input.getDocument()
//										.getRootElement().attributeValue(
//												"event-manager-id");
//								if (curEditorNetId.equals(editorInputId)
//										&& (curNetEditorInput != editorInput)) {
//									CloseableEditor closeableEditor = (CloseableEditor) editors[i]
//											.getEditor(false);
//									closeableEditor.close(false);
//								}
//							}
//						}
//					} catch (PartInitException e) {
//					}
//				}
//			}
//		}
	}

	public boolean isDirty() {
		// Since this is a child-page, it is never dirty.
		return false;
	}

	public void doSave(IProgressMonitor monitor) {
	}

	public boolean isSaveAsAllowed() {
		return false;
	}

	public void doSaveAs() {
	}

	public Object getAdapter(Class type) {
		// Simply act as a proxy, since the Multipage
		// eidor is linked in between the net editpart
		// and the workbench and a MultipageEditor
		// is incompatible with a GefEditor.
		return subnetEditor.getAdapter(type);
	}

	// TIP: This method simply propagates the selection
	// events to the gef editor. Remeber to register as
	// selection listener in the init-method and to
	// override the selectionChanged method in the editor,
	// or the editor will simply ignore the selection
	// changes.
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (this.equals(getSite().getPage().getActiveEditor())) {
			if (subnetEditor.equals(getActiveEditor()))
				subnetEditor.selectionChanged(getActiveEditor(), selection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
		// Since this edit part is registered to be notified
		// for modifications of its children. We simply need
		// to check for child modification events.
		if (DocumentManager.PROP_ATTRIBUTE_MODIFIED.equals(prop)) {
			if ("path".equals(((DefaultAttribute) evt.getNewValue()).getName())) {
				String title = new File(((DefaultAttribute) evt.getNewValue())
						.getStringValue()).getName();
				this.setPartName(title);
			}
		} else if (DocumentManager.PROP_DOCUMENT_MODIFIED.equals(prop)) {
			((SubnetEditorInput) this.getEditorInput()).getSubnet()
					.getDocument().getRootElement().addAttribute("dirty",
							"true");
			firePropertyChange(PROP_DIRTY);
		}

	}
}
