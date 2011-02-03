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
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.io.filefilter.SuffixFileFilter;

/**
 * Starts a simulation run with the most current version of SimQPN. The binaries
 * in the current Eclipse workspace are used for this run.
 * 
 * @author Simon Spinner
 * 
 */
public class TrunkRun extends Run {

	/**
	 * @param index - The position in a sequence of repeated runs.
	 * @param config - The run configuration.
	 * @param tmpDir - Directory where temporary files are stored in.
	 */
	public TrunkRun(int index, RunConfig config, File tmpDir) {
		super(index, config, tmpDir);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tud.cs.simqpn.rt.framework.run.Run#prepareEnvironment(java.util.Map)
	 */
	@Override
	protected void prepareEnvironment(Map<String, Object> substitutions)
			throws IOException {
		super.prepareEnvironment(substitutions);
		substitutions.put("classPath", createClassPath());
		substitutions.put("logConfigFile", createLogConfiguration());
	}

	/**
	 * Creates a log configuration file in the tmp directory. The template in
	 * de/tud/cs/simqpn/rt/resources/log4jTemplate.properties is used for this.
	 * 
	 * @return path to the created log configuration file.
	 * @throws IOException
	 */
	private File createLogConfiguration() throws IOException {
		Properties logConfig = new Properties();
		File runLogConfig = new File(tmpDir, "log4j.properties");
		logConfig.load(this.getClass().getResourceAsStream(
				"/de/tud/cs/simqpn/rt/resources/log4jTemplate.properties"));
		logConfig.store(new FileOutputStream(runLogConfig), null);
		return runLogConfig;
	}

	/**
	 * Creates a classpath consisting of all *.jar-files in the directory "lib"
	 * in the QPE-SimQPN project and the compiled classes in the directory
	 * "bin/classes" of the same project.
	 * 
	 * @return list of paths separated by the platform-specific path separator.
	 * @throws IOException
	 */
	private String createClassPath() throws IOException {
		StringBuilder classPath = new StringBuilder("");

		File libDir = new File("../QPE-SimQPN/lib");
		FileFilter filter = new SuffixFileFilter(".jar");
		for (File lib : libDir.listFiles(filter)) {
			classPath.append(lib.getCanonicalPath()).append(File.pathSeparator);
		}

		classPath.append(new File("../QPE-SimQPN/bin/classes")
				.getCanonicalPath());

		return classPath.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tud.cs.simqpn.rt.framework.run.Run#createCommandLine()
	 */
	@Override
	protected CommandLine createCommandLine() {
		CommandLine cmd = super.createCommandLine();
		cmd.addArgument("-cp");
		cmd.addArgument("${classPath}");
		cmd.addArgument("de.tud.cs.simqpn.console.SimQPN");
		cmd.addArgument("-r");
		cmd.addArgument(config.getConfigurationName());
		cmd.addArgument("-v");
		cmd.addArgument("${logConfigFile}");
		cmd.addArgument("${modelFile}");
		return cmd;
	}
}