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
package de.tud.cs.simqpn.ui.wizard.page;

import java.io.File;

import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import de.tud.cs.simqpn.ui.model.Configuration;

public class Page2SimulationRunSettingsWizardPage extends WizardPage {
	
	private Configuration configuration;
	
	protected Label rampUpLenLabel;
	
	protected Text rampUpLenText;

	protected Text totRunLenText;

	protected Combo stoppingRuleCombobox;

	protected Label timeBtwChkStopsLabel;

	protected Text timeBtwChkStopsText;
	
	protected Label secondsBtwChkStopsLabel;

	protected Text secondsBtwChkStopsText;
	
//	protected Text timeInitHeartBeatText;
//
//	protected Text secondsBtwHeartBeatsText;

	protected Spinner verbosityLevelSpinner;

	protected Text statsDirText;

	protected boolean modifyListenersActive = true;
	
	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public Page2SimulationRunSettingsWizardPage() {
		super("placeSettingsWizardPage");
		setTitle("Simulation Run Configuration");
		// setDescription("Configure simulator-specific place parameters.");
	}
	
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
		updateDialog();
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 20;

		rampUpLenLabel = new Label(container, SWT.NULL);
		rampUpLenLabel.setText("Warm up period");
		rampUpLenText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		rampUpLenText.setLayoutData(gd);
		rampUpLenText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateModel();
			}
		});

		Label totRunLenLabel = new Label(container, SWT.NULL);
		totRunLenLabel.setText("Max total run length");
		totRunLenText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		totRunLenText.setLayoutData(gd);
		totRunLenText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateModel();
			}
		});

		Label stoppingRuleLabel = new Label(container, SWT.NULL);
		stoppingRuleLabel.setText("Simulation stopping criterion");
		stoppingRuleCombobox = new Combo(container, SWT.BORDER | SWT.READ_ONLY);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		stoppingRuleCombobox.setLayoutData(gd);
		stoppingRuleCombobox.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if(stoppingRuleCombobox.getSelectionIndex() == 1) {
					timeBtwChkStopsText.setText(Double.toString(configuration.getTimeBetweenStopChecks()));
					secondsBtwChkStopsText.setText(Double.toString(configuration.getSecondsBetweenStopChecks()));
				}
				updateModel();
			}
		});

		// Advanced Settings
		timeBtwChkStopsLabel = new Label(container, SWT.NULL);
		timeBtwChkStopsLabel.setText("Time between stop checks");
		timeBtwChkStopsText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		timeBtwChkStopsText.setLayoutData(gd);
		timeBtwChkStopsText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateModel();
			}
		});	

		secondsBtwChkStopsLabel = new Label(container, SWT.NULL);
		secondsBtwChkStopsLabel.setText("Seconds between stop checks");
		secondsBtwChkStopsText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		secondsBtwChkStopsText.setLayoutData(gd);
		secondsBtwChkStopsText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateModel();
			}
		});		
		
		// Verbosity level.
		Label verbosityLevelLabel = new Label(container, SWT.NULL);
		verbosityLevelLabel.setText("Verbosity level");
		verbosityLevelSpinner = new Spinner(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		verbosityLevelSpinner.setLayoutData(gd);
		verbosityLevelSpinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateModel();
			}
		});

		// Stats Dir.
		Label statsDirLabel = new Label(container, SWT.NULL);
		statsDirLabel.setText("Output directory");
		statsDirText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		statsDirText.setLayoutData(gd);
		statsDirText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateModel();
			}
		});
		// When pressing the button a choose-directory dialog is presented.
		Button directorySelectionDialogButton = new Button(container, SWT.NULL);
		directorySelectionDialogButton.setText("...");
		directorySelectionDialogButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.OPEN);
				String path = dialog.open();
				if (path != null) {
					statsDirText.setText(path);
				}
			}
		});
		setControl(container);
	}

	private boolean validate() {
		setPageComplete(false);

		double dValue;
		if(rampUpLenText.isVisible()) {
			try {
				dValue = Double.parseDouble(rampUpLenText.getText());
			} catch (NumberFormatException nfe) {
				setErrorMessage("Invalid number format. Warm up period must be a non negative number.");
				return false;
			}
			if (dValue < 0) {
				setErrorMessage("Warm up period must be a non negative number.");
				return false;
			}
		}

		try {
			dValue = Double.parseDouble(totRunLenText.getText());
		} catch (NumberFormatException nfe) {
			setErrorMessage("Invalid number format. Max total run length must be a positive number.");
			return false;
		}
		if (dValue <= 0) {
			setErrorMessage("Max total run length must be a positive number.");
			return false;
		}

		if ((stoppingRuleCombobox.getSelectionIndex() == 1) || (stoppingRuleCombobox.getSelectionIndex() == 2)) {
			try {
				dValue = Double.parseDouble(timeBtwChkStopsText.getText());
			} catch (NumberFormatException nfe) {
				setErrorMessage("Invalid number format. Time between stop checks must be a positive number.");
				return false;
			}
			if (dValue < 0) {
				setErrorMessage("Time between stop checks must be a non-negative number.");
				return false;
			}
			try {
				dValue = Double.parseDouble(secondsBtwChkStopsText.getText());
			} catch (NumberFormatException nfe) {
				setErrorMessage("Invalid number format. Seconds between stop checks must be a non-negative number.");
				return false;
			}
			if (dValue < 0) {
				setErrorMessage("Seconds between stop checks must be a non-negative number.");
				return false;
			}			
		}
		
		int iValue;
		try {
			iValue = verbosityLevelSpinner.getSelection();
		} catch (NumberFormatException nfe) {
			setErrorMessage("Invalid number format. Verbosity level must be a non negative number.");
			return false;
		}
		if (iValue < 0) {
			setErrorMessage("Verbosity level must be a non negative number.");
			return false;
		}

		File file = new File(statsDirText.getText());
		if (!file.exists() || !file.isDirectory()) {
			setErrorMessage("The output directory must exist.");
			return false;
		}

		// If runing in Replication/Deletion Mode, there are no place-settings.
		setPageComplete(true);

		setErrorMessage(null);
		return true;
	}

	public void updateModel() {
		if (modifyListenersActive == true) {
			// We have to initialize update the visibility first or
			// the fields will never be shown because of failled
			// field validation.
			if (stoppingRuleCombobox.getSelectionIndex() == 1) {
				timeBtwChkStopsLabel.setVisible(true);
				timeBtwChkStopsText.setVisible(true);
				secondsBtwChkStopsLabel.setVisible(true);
				secondsBtwChkStopsText.setVisible(true);				
			} else if (stoppingRuleCombobox.getSelectionIndex() == 2) {
				timeBtwChkStopsLabel.setVisible(true);
				timeBtwChkStopsText.setVisible(true);
				secondsBtwChkStopsLabel.setVisible(true);
				secondsBtwChkStopsText.setVisible(true);								
			} else {
				timeBtwChkStopsLabel.setVisible(false);
				timeBtwChkStopsText.setVisible(false);
				secondsBtwChkStopsLabel.setVisible(false);
				secondsBtwChkStopsText.setVisible(false);												
			}

			if (validate()) {
				configuration.setRampUpLength(Double.parseDouble(rampUpLenText.getText()));

				configuration.setTotalRunLength(Double.parseDouble(totRunLenText.getText()));
				String sValue;
				if (stoppingRuleCombobox.getSelectionIndex() == 1) {
					sValue = Configuration.ABSOLUTE_PRECISION_STOPPING_RULE;
				} else if (stoppingRuleCombobox.getSelectionIndex() == 2) {
					sValue = Configuration.RELATIVE_PRECISION_STOPPING_RULE;
				} else {
					sValue = Configuration.FIXED_LENGTH_STOPPING_RULE;
				}
				
				configuration.setStoppingRule(sValue);
				String text = timeBtwChkStopsText.getText();
				if (text != null && text.length() > 0) {
					configuration.setTimeBetweenStopChecks(Double.parseDouble(text));
				}
				text = secondsBtwChkStopsText.getText();
				if (text != null && text.length() > 0) {
					configuration.setSecondsBetweenStopChecks(Double.parseDouble(text));
				}
				configuration.setVerbosityLevel(verbosityLevelSpinner.getSelection());
				configuration.setOutputDirectory(statsDirText.getText());
			}
		}
	}

	protected void updateDialog() {
		modifyListenersActive = false;
		
		if (configuration.getScenario() != Configuration.BATCH_MEANS_SCENARIO) {
			stoppingRuleCombobox.setItems(new String[] { "Fixed sample size" });
			stoppingRuleCombobox.setEnabled(false);
		} else {
			stoppingRuleCombobox.setItems(new String[] { "Fixed sample size", "Sequential / Absolute precision", "Sequential / Relative precision" });
			stoppingRuleCombobox.setEnabled(true);
		}
		
		if (configuration.getScenario() == Configuration.WELCH_SCENARIO) {
			rampUpLenLabel.setVisible(false);
			rampUpLenText.setVisible(false);
		} else {
			rampUpLenLabel.setVisible(true);
			rampUpLenText.setVisible(true);
			rampUpLenText.setText(Double.toString(configuration.getRampUpLength()));
		}
		
		totRunLenText.setText(Double.toString(configuration.getTotalRunLength()));
		String stoppingRule = configuration.getStoppingRule();
		if (Configuration.ABSOLUTE_PRECISION_STOPPING_RULE.equals(stoppingRule)) {
			stoppingRuleCombobox.select(1);
			timeBtwChkStopsLabel.setVisible(true);
			timeBtwChkStopsText.setVisible(true);
			timeBtwChkStopsText.setText(Double.toString(configuration.getTimeBetweenStopChecks()));
			secondsBtwChkStopsLabel.setVisible(true);
			secondsBtwChkStopsText.setVisible(true);
			secondsBtwChkStopsText.setText(Double.toString(configuration.getSecondsBetweenStopChecks()));				
		} else if (Configuration.RELATIVE_PRECISION_STOPPING_RULE.equals(stoppingRule)) {
			stoppingRuleCombobox.select(2);
			timeBtwChkStopsLabel.setVisible(true);
			timeBtwChkStopsText.setVisible(true);
			timeBtwChkStopsText.setText(Double.toString(configuration.getTimeBetweenStopChecks()));
			secondsBtwChkStopsLabel.setVisible(true);
			secondsBtwChkStopsText.setVisible(true);
			secondsBtwChkStopsText.setText(Double.toString(configuration.getSecondsBetweenStopChecks()));								
		} else {
			stoppingRuleCombobox.select(0);
			timeBtwChkStopsLabel.setVisible(false);
			timeBtwChkStopsText.setVisible(false);				
			secondsBtwChkStopsLabel.setVisible(false);
			secondsBtwChkStopsText.setVisible(false);				
		}
		verbosityLevelSpinner.setSelection(configuration.getVerbosityLevel());
		statsDirText.setText(configuration.getOutputDirectory());

		modifyListenersActive = true;

		validate();
	}
}
