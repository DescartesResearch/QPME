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
 *  ----------  ----------------  ----------------------------------------------------------------------------------
 *  2011/01/21  Simon Spinner     Created.         
 * 
 */

package de.tud.cs.simqpn.rt.framework.run;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.ProcessDestroyer;
import org.apache.log4j.LogMF;
import org.apache.log4j.Logger;

public class HistoricRun extends Run {
	
	private static final Logger log = Logger.getLogger(HistoricRun.class);
	
	private File binaryFile;

	public HistoricRun(int index, File binaryFile, RunConfig config, File tmpDir, ProcessDestroyer destroyer) {
		super(index, config, tmpDir, destroyer);
		this.binaryFile = binaryFile;
	}
	
	@Override
	protected void prepareEnvironment(Map<String, Object> substitutions)
			throws IOException {
		super.prepareEnvironment(substitutions);
		
		if(!binaryFile.exists()) {
			LogMF.error(log, "Could not find the simqpn binary: {0}", new Object[] { binaryFile.getAbsolutePath() });
		}
		substitutions.put("binaryFile", binaryFile);
	}
	
	@Override
	protected CommandLine createCommandLine() {
		CommandLine cmd = super.createCommandLine();
		cmd.addArgument("-jar");
		cmd.addArgument("${binaryFile}");
		cmd.addArgument("-r");
		cmd.addArgument(config.getConfigurationName());
		cmd.addArgument("${modelFile}");
		return cmd;
	}
}