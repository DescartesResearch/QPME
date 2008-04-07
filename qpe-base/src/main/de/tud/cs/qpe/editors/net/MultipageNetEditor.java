package de.tud.cs.qpe.editors.net;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultAttribute;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.MultiPageEditorPart;

import de.tud.cs.qpe.editors.incidence.IncidenceFunctionEditorInput;
import de.tud.cs.qpe.editors.subnet.SubnetEditorInput;
import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.utils.CloseableEditor;

public class MultipageNetEditor extends MultiPageEditorPart implements PropertyChangeListener, ISelectionListener, CloseableEditor {
	/** Editor ID */
	public static final String ID = "de.tud.cs.qpe.editor.net";

	protected NetEditorPage netEditor;

	protected ColorEditorPage colorEditor;

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

		String title = null;
		if (((NetEditorInput) editorInput).getDocument().getRootElement().attributeValue("path") != null) {
			title = new File(((NetEditorInput) editorInput).getDocument().getRootElement().attributeValue("path")).getName();
		} else {
			title = "new document";
		}

		this.setPartName(title);
		super.init(site, editorInput);

		// Add editor as listener to modifications of the
		// current document.
		DocumentManager.addPropertyChangeListener(((NetEditorInput) editorInput).getDocument().getRootElement(), this);

		// TIP: Since MultipageEditors don't automatically listen
		// for selection events, we have to register manually.
		// This is needed in order to be able to propagate the
		// selection-events to the child editor.
		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(this);
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
		// Create the Page for editing a nets colors.
		colorEditor = new ColorEditorPage(getContainer(), SWT.NONE);
		colorEditor.setInput(getEditorInput());
		int index = addPage(colorEditor);
		setPageText(index, "Colors");
	}

	protected void createPages() {
		createGraphicalEditorPage();
		createColorEditorPage();
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

		// Create a copy of the current input, because we are going to
		// remove some attributes from the documents root node, which
		// are neded for further operations.
		Document doc = (Document) input.getNetDiagram().getDocument().clone();
		Element root = doc.getRootElement();

		// If this document is new, then ask for a destination file first.
		String path = root.attributeValue("path");
		if (path == null) {
			path = openFileDialog();
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
				writer = new XMLWriter(new FileWriter(path), format);
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

	public void doSaveAs() {
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
