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
 *  2011/01/21  Simon Spinner     Created.         
 * 
 */

package de.tud.cs.simqpn.rt.framework.results;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RunInfo {

	private File modelFile;
	private File consoleLogFile;
	private File runDirectory;
	private List<String> errors = new ArrayList<String>();
	private List<String> warnings = new ArrayList<String>();
	private double runLength;
	private double wallClockTime;
	private boolean overflowFlag;

	public void addErrorMessage(String message) {
		errors.add(message);		
	}
	
	public void addWarnMessage(String message) {
		warnings.add(message);
	}
	
	public int getErrorCount() {
		return errors.size();
	}
	
	public int getWarningCount() {
		return warnings.size();
	}
	
	public double getTotalRunLength() {
		return runLength;
	}
	
	public void setRunLength(double runLength) {
		this.runLength = runLength;
	}

	public void setModelFile(File modelFile) {
		this.modelFile = modelFile;
	}	

	public File getModelFile() {
		return modelFile;
	}

	public File getConsoleLogFile() {
		return consoleLogFile;
	}

	public void setConsoleLogFile(File consoleLogFile) {
		this.consoleLogFile = consoleLogFile;
	}
	
	public File getRunDirectory() {
		return runDirectory;
	}

	public void setRunDirectory(File runDirectory) {
		this.runDirectory = runDirectory;
	}

	public void setOverflowFlag(boolean overflowFlag) {
		this.overflowFlag = overflowFlag;		
	}
	
	public boolean getOverflowFlag() {
		return overflowFlag;
	}

	public void setWallClockTime(double wallClockTime) {
		this.wallClockTime = wallClockTime;		
	}
	
	public double getWallClockTime() {
		return this.wallClockTime;
	}
}
