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
 *  2008/02/03  Frederik Zipp     Register QueueEditorPage.
 * 
 */
package de.tud.cs.qpe.editors.net;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;
import org.dom4j.io.DOMWriter;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultAttribute;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.MultiPageEditorPart;

import de.tud.cs.qpe.QPEBasePlugin;
import de.tud.cs.qpe.editors.incidence.IncidenceFunctionEditorInput;
import de.tud.cs.qpe.editors.subnet.SubnetEditorInput;
import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.utils.CloseableEditor;
import edu.kit.ipd.descartes.qpme.model.migration.DocumentMigrationHandler;

public class MultipageNetEditor extends MultiPageEditorPart implements PropertyChangeListener, ISelectionListener, CloseableEditor {
	/** Editor ID */
	public static final String ID = "de.tud.cs.qpe.editor.net";

	protected NetEditorPage netEditor;

	protected ColorEditorPage colorEditor;
	protected ProbeEditorPage probeEditor;
	protected QueueEditorPage queueEditor;

	public MultipageNetEditor() {
		super();
	}

	/**
	 * The <code>MultiPageEditorExample</code> implementation of this method
	 * checks that the input is an instance of <code>IFileEditorInput</code>.
	 */
	public void init(IEditorSite site, IEditorInput editorInput) throws PartInitException {
		if (!(editorInput instanceof NetEditorInput))
			throw new PartInitException("Invalid Input: Must be NetEditorInput");

		super.init(site, editorInput);
		
		migrateDocument(site.getShell(), editorInput);
		
		Element diagramRoot = ((NetEditorInput) editorInput).getDocument().getRootElement();				
		
		String title = null;
		if (diagramRoot.attributeValue("path") != null) {
			title = new File(diagramRoot.attributeValue("path")).getName();
		} else {
			title = "new document";
		}
		this.setPartName(title);
		

		// Add editor as listener to modifications of the
		// current document.
		DocumentManager.addPropertyChangeListener(((NetEditorInput) editorInput).getDocument().getRootElement(), this);

		// TIP: Since MultipageEditors don't automatically listen
		// for selection events, we have to register manually.
		// This is needed in order to be able to propagate the
		// selection-events to the child editor.
		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(this);
	}
	
	private void migrateDocument(Shell currentShell, IEditorInput input) throws PartInitException {
		DocumentMigrationHandler migration = new DocumentMigrationHandler();
		Document doc = ((NetEditorInput)input).getDocument();
		try {
			org.w3c.dom.Document w3cDoc = (new DOMWriter()).write(doc);
			if (migration.isMigrationRequired(w3cDoc)) {				
				if (MessageDialog.openConfirm(currentShell, "Document Migration", "You try to open a .qpe file from a previous version of QPME. Do you want to migrate it to the current version?")) {
					if (migration.canMigrate(w3cDoc)) {
						w3cDoc = migration.migrate(w3cDoc);
						doc = (new DOMReader()).read(w3cDoc);
						((NetEditorInput)input).replaceWith(doc);
						return;
					} else {
						throw new PartInitException("Unsupported document version.");
					}
				} else {
					throw new PartInitException("Document migration cancelled by user.");
				}
			}
		} catch (TransformerException e) {
			throw new PartInitException("Error during document migration.", e);
		} catch (DocumentException e) {
			throw new PartInitException("Error during document migration.", e);
		}
	}

	protected void createGraphicalEditorPage() {
		// The first Page is where the GEF-Net-Editor is
		// located.
		try {
			// Create instance of the new editor page.
			netEditor = new NetEditorPage();

			// Initialize the editor page and set the input.
			int index = addPage(netEditor, getEditorInput());

			// Set the page title
			setPageText(index, "Net Editor");
		} catch (PartInitException e) {
			ErrorDialog.openError(getSite().getShell(), "Error creating net editor", null, e.getStatus());
		}
	}

	protected void createColorEditorPage() {
		try {
			colorEditor = new ColorEditorPage();
			int index = addPage(colorEditor, getEditorInput());
			setPageText(index, "Colors");
		} catch (PartInitException e) {
			ErrorDialog.openError(getSite().getShell(), "Error creating color editor", null, e.getStatus());
		}
	}
	
	protected void createProbeEditorPage() {
		try {
			probeEditor = new ProbeEditorPage();
			int index = addPage(probeEditor, getEditorInput());
			setPageText(index, "Probes");
		} catch (PartInitException e) {
			ErrorDialog.openError(getSite().getShell(), "Error creating probe editor", null, e.getStatus());
		}
	}

	protected void createQueueEditorPage() {
		try {
			queueEditor = new QueueEditorPage();
			int index = addPage(queueEditor, getEditorInput());
			setPageText(index, "Queues");
		} catch (PartInitException e) {
			ErrorDialog.openError(getSite().getShell(), "Error creating queue editor", null, e.getStatus());
		}
	}

	protected void createPages() {
		createGraphicalEditorPage();
		createColorEditorPage();
		createQueueEditorPage();
		createProbeEditorPage();
	}

	public void close(final boolean save) {
		Display display = getSite().getShell().getDisplay();

//		String eventManagerId = ((NetEditorInput) this.getEditorInput()).getDocument().getRootElement().attributeValue("event-manager-id");
//		// Close all child-editors.
//		IEditorReference[] editorReferences = getSite().getWorkbenchWindow().getActivePage().getEditorReferences();
//		for(int i = 0; i < editorReferences.length; i++) {
//			try {
//				if(editorReferences[i].getEditorInput() instanceof NetEditorInput) {
//					NetEditorInput input = (NetEditorInput) editorReferences[i].getEditorInput();
//					String currentEditorEventManagerId = input.getDocument().getRootElement().attributeValue("event-manager-id"); 
//					if(eventManagerId.equals(currentEditorEventManagerId)) {
//						getSite().getPage().closeEditor(editorReferences[i].getEditor(false), false);
//					}
//				}
//			} catch(PartInitException e) {
//			}
//		}
		display.asyncExec(new Runnable() {
			public void run() {
				getSite().getPage().closeEditor(MultipageNetEditor.this, save);
			}
		});
	}

	public void dispose() {
		super.dispose();
		// Close all incidence function for this net when closing
		// the nets main editor.
		NetEditorInput curNetEditorInput = (NetEditorInput) this.getEditorInput();
		String curEditorNetId = curNetEditorInput.getDocument().getRootElement().attributeValue("event-manager-id");

		if(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() != null) {
			IEditorReference[] editors = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences();
			for (int i = 0; i < editors.length; i++) {
				try {
					IEditorInput editorInput = editors[i].getEditorInput();
					if (editors[i].getEditor(false) instanceof CloseableEditor) {
						if (editorInput instanceof SubnetEditorInput) {
							SubnetEditorInput input = (SubnetEditorInput) editorInput;
							String editorInputId = input.getDocument().getRootElement().attributeValue("event-manager-id");
							if (curEditorNetId.equals(editorInputId) && (curNetEditorInput != editorInput)) {
								CloseableEditor closeableEditor = (CloseableEditor) editors[i].getEditor(false);
								closeableEditor.close(false);
							}
						}
						if (editorInput instanceof IncidenceFunctionEditorInput) {
							IncidenceFunctionEditorInput input = (IncidenceFunctionEditorInput) editorInput;
							String editorInputId = input.getDocument().getRootElement().attributeValue("event-manager-id");
							if (curEditorNetId.equals(editorInputId) && (curNetEditorInput != editorInput)) {
								CloseableEditor closeableEditor = (CloseableEditor) editors[i].getEditor(false);
								closeableEditor.close(false);
							}
						}
					}
				} catch (PartInitException e) {
				}
			}
		}
	}

	public boolean isDirty() {
		NetEditorInput input = null;
		if (this.getEditorInput() instanceof NetEditorInput) {
			input = (NetEditorInput) this.getEditorInput();
			Element rootElement = input.getNetDiagram().getDocument().getRootElement();
			return "true".equals(rootElement.attributeValue("dirty", "false"));
		}
		return false;
	}

	public void doSave(IProgressMonitor monitor) {
		NetEditorInput input = (NetEditorInput) this.getEditorInput();
		save(input.getNetDiagram().getDocument().getRootElement().attributeValue("path"));
	}
	
	public void doSaveAs() {
		save(null);
	}
	
	private void save(String path) {
		NetEditorInput input = (NetEditorInput) this.getEditorInput();

		// Create a copy of the current input, because we are going to
		// remove some attributes from the documents root node, which
		// are neded for further operations.
		Document doc = (Document) input.getNetDiagram().getDocument().clone();
		Element root = doc.getRootElement();

		// Add version number of qpme
		String ver = QPEBasePlugin.getDefault().getBundle().getVersion().toString();
		root.addAttribute("qpme-version", ver);

		// If this document is new, then ask for a destination file first.
		if (path == null) {
			path = openFileDialog();
			DocumentManager.setAttribute(input.getNetDiagram().getDocument().getRootElement(), "path", path);
		}

		if (path != null) {
			// Remove the "transient" attributes which are not intended to
			// be saved.
			root.remove(root.attribute("event-manager-id"));
			root.remove(root.attribute("dirty"));
			if (root.attribute("path") != null) {
				root.remove(root.attribute("path"));
			}

			// Write the document to the desired destination.
			XMLWriter writer = null;
			try {
				OutputFormat format = OutputFormat.createPrettyPrint();
				writer = new XMLWriter(new FileOutputStream(path), format);
				writer.write(doc);

				// Reset the dirty-property.
				input.getNetDiagram().getDocument().getRootElement().addAttribute("dirty", "false");
				firePropertyChange(PROP_DIRTY);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}

	public boolean isSaveAsAllowed() {
		return false;
	}

	private String openFileDialog() {
		FileDialog dialog = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.SAVE);
		dialog.setText("Save");
		dialog.setFilterExtensions(new String[] { "*.qpe" });
		return dialog.open();
	}

	public Object getAdapter(Class type) {
		// Simply act as a proxy, since the Multipage
		// eidor is linked in between the net editpart
		// and the workbench and a MultipageEditor
		// is incompatible with a GefEditor.
		return netEditor.getAdapter(type);
	}

	// TIP: This method simply propagates the selection
	// events to the gef editor. Remeber to register as
	// selection listener in the init-method and to
	// override the selectionChanged method in the editor,
	// or the editor will simply ignore the selection
	// changes.
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (this.equals(getSite().getPage().getActiveEditor())) {
			if (netEditor.equals(getActiveEditor()))
				netEditor.selectionChanged(getActiveEditor(), selection);
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
				String title = new File(((DefaultAttribute) evt.getNewValue()).getStringValue()).getName();
				this.setPartName(title);
			}
		} else if (DocumentManager.PROP_DOCUMENT_MODIFIED.equals(prop)) {
			((NetEditorInput) this.getEditorInput()).getNetDiagram().getDocument().getRootElement().addAttribute("dirty", "true");
			firePropertyChange(PROP_DIRTY);
		}

	}
}
