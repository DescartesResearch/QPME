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
 * Original Author(s):  Simon Spinner
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2011/02/03  Simon Spinner     Created.         
 * 
 */

package de.tud.cs.simqpn.rt.framework.run;

import java.io.File;

/**
 * Contains the information required to do a simulation run.
 * 
 * @author Simon Spinner
 *
 */
public class RunConfig {
	
	public enum Revision {
		TRUNK, R100, R162
	}
	
	public enum AnalysisMode {
		BATCH_MEANS, REPLICATION_DELETION, WELCH
	}
	
	public enum StoppingRule {
		FIXED_LENGTH, ABSOLUTE_PRECISION, RELATIVE_PRECISION
	}

	private File model;
	private String configurationName;
	private AnalysisMode analysisMode;
	private StoppingRule stoppingRule;
	private boolean expectError;
	private int repeats;

	public File getModel() {
		return model;
	}

	public void setModel(File model) {
		this.model = model;
	}

	public String getConfigurationName() {
		return configurationName;
	}

	public void setConfigurationName(String name) {
		this.configurationName = name;
	}

	public AnalysisMode getAnalysisMode() {
		return analysisMode;
	}

	public void setAnalysisMode(AnalysisMode mode) {
		this.analysisMode = mode;
	}

	public StoppingRule getStoppingRule() {
		return stoppingRule;
	}

	public void setStoppingRule(StoppingRule rule) {
		this.stoppingRule = rule;
	}

	public boolean isExpectError() {
		return expectError;
	}

	public void setExpectError(boolean expectError) {
		this.expectError = expectError;
	}

	public int getRepeats() {
		return repeats;
	}

	public void setRepeats(int repeats) {
		this.repeats = repeats;
	}

}
