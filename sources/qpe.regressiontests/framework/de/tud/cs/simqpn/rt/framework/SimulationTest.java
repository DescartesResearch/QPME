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

package de.tud.cs.simqpn.rt.framework;

import static org.junit.Assert.fail;

import java.io.File;

import org.apache.log4j.Logger;
import org.junit.AfterClass;

import de.tud.cs.simqpn.rt.framework.results.SimulationResults;
import de.tud.cs.simqpn.rt.framework.run.RunConfig;
import de.tud.cs.simqpn.rt.framework.run.RunConfig.AnalysisMode;
import de.tud.cs.simqpn.rt.framework.run.RunConfig.Revision;
import de.tud.cs.simqpn.rt.framework.run.RunConfig.StoppingRule;
import de.tud.cs.simqpn.rt.framework.run.SimulationRunner;

public class SimulationTest {
	
	private static final Logger log = Logger.getLogger(SimulationTest.class);
	
	private static String testName;
	protected static SimulationResults results;
	protected static TestReport report;
	
	public static void initTest(String name, String modelFile, String configuration, AnalysisMode analysisMode, StoppingRule stoppingRule, String comments) throws Exception {
		initTest(name, modelFile, configuration, analysisMode, stoppingRule, false, comments);
	}

	public static void initTest(String name, String model, String configuration, AnalysisMode analysisMode, StoppingRule stoppingRule, boolean expectError, String comments) throws Exception {		
		log.info("Start test " + name);
		
		testName = name;
		
		File modelFile = new File("testfiles/" + testName + "/" + model);
		if (!modelFile.exists()) {
			fail("Could not find model file: " + model);
		}
		
		SimulationRunner runner = new SimulationRunner(Revision.TRUNK, testName);
		int repeats = TestConfig.getInstance().getTestRuns(testName);
		RunConfig config = new RunConfig();
		config.setModel(modelFile);
		config.setConfigurationName(configuration);
		config.setAnalysisMode(analysisMode);
		config.setStoppingRule(stoppingRule);
		config.setRepeats(repeats);
		config.setExpectError(expectError);
		results = runner.run(config);
		
		
		String description = "<b>Model: </b> " + model + "<br />"
								+ "<b>Configuration: </b> " + configuration + "<br />"
								+ "<b>Analysis Mode: </b> " + analysisMode + "<br />"
								+ "<b>Stopping Rule: </b> " + stoppingRule + "<br />"
								+ "<b>Repeats: </b>" + repeats + "<br />"
								+ "<b>T-test Significance Level: </b>" + TestConfig.getInstance().getTestSignificaneLevel(name) + "<br />"
								+ "<b>Comments: </b>" + comments + "<br />";
		report = new TestReport(testName, description);
		File reportDir = new File("reports/" + testName);
		reportDir.mkdirs();
		report.startReport(new File(reportDir, "latest.html"));
	}
	
	@AfterClass
	public static void finishTest() throws Exception {
		if (report != null) {
			report.endReport();
		}
	}
}
