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
 * Original Author(s):  Frederik Zipp and Samuel Kounev
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2009/05/05  Frederik Zipp     Created.
 * 
 */
package de.tud.cs.qpe.editors.r;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;
import org.rosuda.JRI.RMainLoopCallbacks;
import org.rosuda.JRI.Rengine;


public class REditor extends EditorPart {

	public static final String ID = "de.tud.cs.qpe.editors.r";
	
	private Text fileText;
	private Text commandText;
	private Rengine rEngine;

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		if (!(input instanceof REditorInput)) {
			throw new PartInitException("Invalid Input: Must be REditorInput");
		}

		setSite(site);
		setInput(input);
		
		initializeR();
	}

	private void initializeR() {
		// just making sure we have the right version of everything
		if (!Rengine.versionCheck()) {
			System.err.println("** Version mismatch - Java files don't match library version.");
			return;
		}

		System.out.println("Creating R engine");
		rEngine = new Rengine(null, false, new TextConsole());
		System.out.println("R engine created, waiting for R");
		// the engine creates R is a new thread, so we should wait until it's
		// ready
		if (!rEngine.waitForR()) {
			System.out.println("Cannot load R");
			return;
		}
		System.out.println("Ready");
	}

	public void createPartControl(Composite parent) {
		RowLayout layout = new RowLayout();
		layout.marginTop = 20;
		layout.marginLeft = 20;
		layout.spacing = 20;
		parent.setLayout(layout);
		Label label = new Label(parent, SWT.NONE);
		label.setText("File:");
		this.fileText = new Text(parent, SWT.BORDER);
		this.fileText.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				loadFile();
			}
		});
		Button chooseDirectoryButton = new Button(parent, SWT.NONE);
		chooseDirectoryButton.setText("Choose");
		chooseDirectoryButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				FileDialog fileDialog = new FileDialog(getShell());
				String fileName = fileDialog.open();
				fileText.setText(fileName);
				loadFile();
			}
		});
		label = new Label(parent, SWT.NONE);
		label.setText("Enter R command:");
		this.commandText = new Text(parent, SWT.BORDER);
		Button evalButton = new Button(parent, SWT.NONE);
		evalButton.setText("Evaluate");
		evalButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
//				rEngine.eval(getCommand());
			}
		});
	}

	public void setFocus() {
		// do nothing
	}


	@Override
	public void doSave(IProgressMonitor monitor) {
		// do nothing
	}

	@Override
	public void doSaveAs() {
		// do nothing
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}
	
	private String getFileName() {
		return this.fileText.getText();
	}
	
	private void loadFile() {
		String escapedFileName = getFileName().replaceAll("\\", "\\\\"); 
//		this.rEngine.eval("x <- read.delim('" + escapedFileName + "', header = FALSE, sep='\\n', quote='', dec='.', fill=FALSE, comment.char='')", false);
	}
	
	private String getCommand() {
		return this.commandText.getText();
	}

	private Shell getShell() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	}

	private class TextConsole implements RMainLoopCallbacks {
		public void rWriteConsole(Rengine re, String text, int oType) {
			System.out.print(text);
		}

		public void rBusy(Rengine re, int which) {
//			System.out.println("rBusy(" + which + ")");
		}

		public String rReadConsole(Rengine re, String prompt, int addToHistory) {
			System.out.print(prompt);
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				String s = br.readLine();
				return (s == null || s.length() == 0) ? s : s + "\n";
			} catch (Exception e) {
				System.out.println("jriReadConsole exception: " + e.getMessage());
			}
			return null;
		}

		public void rShowMessage(Rengine re, String message) {
//			System.out.println("rShowMessage \"" + message + "\"");
		}

		public String rChooseFile(Rengine re, int newFile) {
			FileDialog fd = new FileDialog(getShell(), newFile == 0 ? SWT.OPEN : SWT.SAVE);
			fd.setText(newFile == 0 ? "Open file" : "Save file");
			return fd.open();
		}

		public void rFlushConsole(Rengine re) {
		}

		public void rLoadHistory(Rengine re, String filename) {
		}

		public void rSaveHistory(Rengine re, String filename) {
		}			
	}
}
