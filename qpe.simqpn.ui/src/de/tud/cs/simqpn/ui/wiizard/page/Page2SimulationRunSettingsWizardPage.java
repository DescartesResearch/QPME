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
package de.tud.cs.simqpn.plugin.wiizard.page;

import java.beans.PropertyChangeEvent;
import java.io.File;

import org.dom4j.Element;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
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

import de.tud.cs.qpe.model.DocumentManager;

public class Page2SimulationRunSettingsWizardPage extends BaseWizardPage {
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
	public Page2SimulationRunSettingsWizardPage(ISelection selection, Element net) {
		super("placeSettingsWizardPage", selection, net);
		setTitle("Simulation Run Configuration");
		// setDescription("Configure simulator-specific place parameters.");
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
				if(!"Fixed sample size".equals(stoppingRuleCombobox.getText())) {
					Element metaAttribute = getMetaAttribute();
					timeBtwChkStopsText.setText(metaAttribute.attributeValue("time-between-stop-checks", "100000"));
					secondsBtwChkStopsText.setText(metaAttribute.attributeValue("seconds-between-stop-checks", "60"));
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
		
		//
		// Since progress monitor implementation these parameters are not used anymore
		//		
//		Label timeInitHeartBeatLabel = new Label(container, SWT.NULL);
//		timeInitHeartBeatLabel.setText("Time before initial heart beat");
//		timeInitHeartBeatText = new Text(container, SWT.BORDER | SWT.SINGLE);
//		gd = new GridData(GridData.FILL_HORIZONTAL);
//		gd.horizontalSpan = 2;
//		timeInitHeartBeatText.setLayoutData(gd);
//		timeInitHeartBeatText.addModifyListener(new ModifyListener() {
//			public void modifyText(ModifyEvent e) {
//				updateModel();
//			}
//		});
//
//		Label timeBtwHeartBeatsLabel = new Label(container, SWT.NULL);
//		timeBtwHeartBeatsLabel.setText("Seconds between heart beats");
//		secondsBtwHeartBeatsText = new Text(container, SWT.BORDER | SWT.SINGLE);
//		gd = new GridData(GridData.FILL_HORIZONTAL);
//		gd.horizontalSpan = 2;
//		secondsBtwHeartBeatsText.setLayoutData(gd);
//		secondsBtwHeartBeatsText.addModifyListener(new ModifyListener() {
//			public void modifyText(ModifyEvent e) {
//				updateModel();
//			}
//		});
		

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

		updateDialog();

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

//		try {
//			dValue = Double.parseDouble(timeInitHeartBeatText.getText());
//		} catch (NumberFormatException nfe) {
//			setErrorMessage("Invalid number format. Time before initial heart beat must be a positive number.");
//			return false;
//		}
//		if (dValue <= 0) {
//			setErrorMessage("Time before initial heart beat must be a positive number.");
//			return false;
//		}
//
//		try {
//			dValue = Double.parseDouble(secondsBtwHeartBeatsText.getText());
//		} catch (NumberFormatException nfe) {
//			setErrorMessage("Invalid number format. Seconds between heart beats must be a positive number.");
//			return false;
//		}
//		if (dValue <= 0) {
//			setErrorMessage("Seconds between heart beats must be a positive number.");
//			return false;
//		}

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
			Element metaAttribute = getMetaAttribute();

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
				DocumentManager.removeAttribute(metaAttribute, "time-between-stop-checks");
				DocumentManager.removeAttribute(metaAttribute, "seconds-between-stop-checks");
				timeBtwChkStopsLabel.setVisible(false);
				timeBtwChkStopsText.setVisible(false);
				secondsBtwChkStopsLabel.setVisible(false);
				secondsBtwChkStopsText.setVisible(false);												
			}

			if (validate()) {
				if(rampUpLenText.isVisible()) {
					DocumentManager.setAttribute(metaAttribute, "ramp-up-length", rampUpLenText.getText());
				} else {
					DocumentManager.removeAttribute(metaAttribute, "ramp-up-length");
				}
				DocumentManager.setAttribute(metaAttribute, "total-run-length", totRunLenText.getText());
				String sValue;
				if (stoppingRuleCombobox.getSelectionIndex() == 1) {
					sValue = "ABSPRC";
				} else if (stoppingRuleCombobox.getSelectionIndex() == 2) {
					sValue = "RELPRC";
				} else {
					sValue = "FIXEDLEN";
				}
				DocumentManager.setAttribute(metaAttribute, "stopping-rule", sValue);
				if(timeBtwChkStopsText.isVisible()) {
					DocumentManager.setAttribute(metaAttribute, "time-between-stop-checks", timeBtwChkStopsText.getText());
				} else {
					DocumentManager.removeAttribute(metaAttribute, "time-between-stop-checks");
				}				
				if(secondsBtwChkStopsText.isVisible()) {
					DocumentManager.setAttribute(metaAttribute, "seconds-between-stop-checks", secondsBtwChkStopsText.getText());
				} else {
					DocumentManager.removeAttribute(metaAttribute, "seconds-between-stop-checks");
				}
//				DocumentManager.setAttribute(metaAttribute, "time-before-initial-heart-beat", timeInitHeartBeatText.getText());
//				DocumentManager.setAttribute(metaAttribute, "seconds-between-heart-beats", secondsBtwHeartBeatsText.getText());
				DocumentManager.setAttribute(metaAttribute, "verbosity-level", Integer.toString(verbosityLevelSpinner.getSelection()));
				DocumentManager.setAttribute(metaAttribute, "output-directory", statsDirText.getText());
			}
		}
	}

	protected void updateDialog() {
		Element metaAttribute = getMetaAttribute();
		if (metaAttribute != null) {
			modifyListenersActive = false;
			if (!"1".equals(metaAttribute.attributeValue("scenario"))) {
				stoppingRuleCombobox.setItems(new String[] { "Fixed sample size" });
				stoppingRuleCombobox.setEnabled(false);
			} else {
				stoppingRuleCombobox.setItems(new String[] { "Fixed sample size", "Sequential / Absolute precision", "Sequential / Relative precision" });
				stoppingRuleCombobox.setEnabled(true);
			}
			
			if("3".equals(metaAttribute.attributeValue("scenario"))) {
				rampUpLenLabel.setVisible(false);
				rampUpLenText.setVisible(false);
			} else {
				rampUpLenLabel.setVisible(true);
				rampUpLenText.setVisible(true);
				rampUpLenText.setText(metaAttribute.attributeValue("ramp-up-length", ""));
			}

			totRunLenText.setText(metaAttribute.attributeValue("total-run-length", ""));
			String stoppingRule = metaAttribute.attributeValue("stopping-rule");
			if ("ABSPRC".equals(stoppingRule)) {
				stoppingRuleCombobox.select(1);
				timeBtwChkStopsLabel.setVisible(true);
				timeBtwChkStopsText.setVisible(true);
				timeBtwChkStopsText.setText(metaAttribute.attributeValue("time-between-stop-checks", ""));
				secondsBtwChkStopsLabel.setVisible(true);
				secondsBtwChkStopsText.setVisible(true);
				secondsBtwChkStopsText.setText(metaAttribute.attributeValue("seconds-between-stop-checks", ""));				
			} else if ("RELPRC".equals(stoppingRule)) {
				stoppingRuleCombobox.select(2);
				timeBtwChkStopsLabel.setVisible(true);
				timeBtwChkStopsText.setVisible(true);
				timeBtwChkStopsText.setText(metaAttribute.attributeValue("time-between-stop-checks", ""));
				secondsBtwChkStopsLabel.setVisible(true);
				secondsBtwChkStopsText.setVisible(true);
				secondsBtwChkStopsText.setText(metaAttribute.attributeValue("seconds-between-stop-checks", ""));								
			} else {
				stoppingRuleCombobox.select(0);
				timeBtwChkStopsLabel.setVisible(false);
				timeBtwChkStopsText.setVisible(false);				
				secondsBtwChkStopsLabel.setVisible(false);
				secondsBtwChkStopsText.setVisible(false);				
			}
//			timeInitHeartBeatText.setText(metaAttribute.attributeValue("time-before-initial-heart-beat", ""));
//			secondsBtwHeartBeatsText.setText(metaAttribute.attributeValue("seconds-between-heart-beats", ""));
			verbosityLevelSpinner.setSelection(Integer.parseInt(metaAttribute.attributeValue("verbosity-level", "")));
			statsDirText.setText(metaAttribute.attributeValue("output-directory", ""));

			modifyListenersActive = true;
		}
		validate();
	}

	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
	}
}
