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
package de.tud.cs.qpe.rcp;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import de.tud.cs.qpe.editors.net.NetEditorInput;
import de.tud.cs.qpe.editors.net.NetEditorPage;

public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {
	private List<String> openFilesList;

	private static final String PERSPECTIVE_ID = "de.tud.cs.qpe.perspective";

	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		return new ApplicationWorkbenchWindowAdvisor(configurer);
	}

	public void initialize(IWorkbenchConfigurer configurer) {
		super.initialize(configurer);
		configurer.setSaveAndRestore(true);
	}

	public void postStartup() {
		if (openFilesList != null) {
			Iterator<String> openFileIterator = openFilesList.iterator();
			while (openFileIterator.hasNext()) {
				String path = openFileIterator.next();
				if (new File(path).exists()) {
					IEditorInput input = new NetEditorInput(new Path(
							path));
					if (input.exists()) {
						try {
							PlatformUI.getWorkbench()
									.getActiveWorkbenchWindow().getActivePage()
									.openEditor(input, NetEditorPage.ID, true);
						} catch (PartInitException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		// Initialize the Console and redirect System.out to it.
		MessageConsole toolConsole = new MessageConsole("Tool Console", null);
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(
				new IConsole[] { toolConsole });
		// ConsolePlugin.getDefault().getConsoleManager().showConsoleView(toolConsole);
		// ConsolePlugin.getDefault().getConsoleManager().showConsoleView(problemConsole);
		MessageConsoleStream stream = toolConsole.newMessageStream();
		stream.setActivateOnWrite(true);

		// TIP: After creating the console and a stream to write to,
		// redirect System.out to the console.
		System.setOut(new PrintStream(stream));

		super.postStartup();
	}

	public IStatus restoreState(IMemento memento) {
		openFilesList = new ArrayList<String>();
		IMemento openFiles = memento.getChild("open-files");
		if (openFiles != null) {
			IMemento[] files = openFiles.getChildren("file");
			for (int i = 0; i < files.length; i++) {
				String file = files[i].getTextData();
				if (file != null) {
					openFilesList.add(file);
				}
			}
		}
		return super.restoreState(memento);
	}

	public IStatus saveState(IMemento memento) {
		IMemento openFiles = memento.createChild("open-files");
		IEditorReference[] editors = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences();
		for (int i = 0; i < editors.length; i++) {
			try {
				IEditorInput editorInput = editors[i].getEditorInput();

				// When checking for NetEditorInput we only get net editor
				// pages.
				if (editorInput instanceof NetEditorInput) {
					NetEditorInput input = (NetEditorInput) editorInput;
					String path = input.getDocument().getRootElement()
							.attributeValue("path");
					if (path != null) {
						IMemento file = openFiles.createChild("file");
						file.putTextData(path);
					}
				}
			} catch (PartInitException e) {
			}
		}
		return super.saveState(memento);
	}

	public String getInitialWindowPerspectiveId() {
		return PERSPECTIVE_ID;
	}
}
