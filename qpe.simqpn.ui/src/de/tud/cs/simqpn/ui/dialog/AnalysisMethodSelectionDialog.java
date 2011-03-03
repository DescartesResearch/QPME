/* ==============================================
 * QPME : Queueing Petri net Modeling Environment
 * ==============================================
 *
 * (c) Copyright 2003-2010, by Samuel Kounev and Contributors.
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
package de.tud.cs.simqpn.ui.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class AnalysisMethodSelectionDialog extends Dialog {
	private Button scenario1Button;

	private Button scenario2Button;

	private Button scenario3Button;

	int scenario = 1;

	public AnalysisMethodSelectionDialog(Shell parentShell) {
		super(parentShell);
	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Select Analysis Method");
	}

	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		layout.verticalSpacing = 30;
		layout.horizontalSpacing = 30;

		// Stuff for scenario 1 mode.
		scenario1Button = new Button(container, SWT.RADIO);
		GridData gd = new GridData();
		gd.widthHint = 140;
		scenario1Button.setLayoutData(gd);
		scenario1Button.setText("Batch Means");
		scenario1Button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateDialog();
			}
		});
		scenario1Button.setSelection(true);
		
		Label scenario1Label = new Label(container, SWT.NULL);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		scenario1Label.setLayoutData(gd);
		scenario1Label
				.setText("Steady state analysis using the method of non-overlapping batch means.");

		// Stuff for scenario 2 mode.
		scenario2Button = new Button(container, SWT.RADIO);
		gd = new GridData();
		gd.widthHint = 145;
		scenario2Button.setLayoutData(gd);
		scenario2Button.setText("Replication/Deletion");
		scenario2Button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateDialog();
			}
		});

		Label scenario2Label = new Label(container, SWT.NULL);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		scenario2Label.setLayoutData(gd);
		scenario2Label
				.setText("Steady state analysis using the replication/deletion approach.");

		// Stuff for scenario 3 mode.
		scenario3Button = new Button(container, SWT.RADIO);
		gd = new GridData();
		gd.widthHint = 140;
		scenario3Button.setLayoutData(gd);
		scenario3Button.setText("Method of Welch");
		scenario3Button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateDialog();
			}
		});

		Label scenario3Label = new Label(container, SWT.NULL);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		scenario3Label.setLayoutData(gd);
		scenario3Label
				.setText("Analysis of the length of the initial transient (warm-up period) using the method of Welch.");

		return container;
	}

	public void updateDialog() {
		if (scenario1Button.getSelection()) {
			scenario = 1;
		} else if (scenario2Button.getSelection()) {
			scenario = 2;
		} else if (scenario3Button.getSelection()) {
			scenario = 3;
		}
	}

	public int getScenario() {
		return scenario;
	}
}
