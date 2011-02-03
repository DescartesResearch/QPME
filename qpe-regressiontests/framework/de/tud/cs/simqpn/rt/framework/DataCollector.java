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

package de.tud.cs.simqpn.rt.framework;

import java.io.File;

import org.apache.log4j.LogMF;
import org.apache.log4j.Logger;

import de.tud.cs.simqpn.rt.framework.results.Metric;
import de.tud.cs.simqpn.rt.framework.results.SimulationResults;
import de.tud.cs.simqpn.rt.framework.results.Statistics;
import de.tud.cs.simqpn.rt.framework.run.RunConfig;
import de.tud.cs.simqpn.rt.framework.run.SimulationRunner;
import de.tud.cs.simqpn.rt.framework.run.SimulationRunner.AnalysisMode;
import de.tud.cs.simqpn.rt.framework.run.SimulationRunner.Revision;
import de.tud.cs.simqpn.rt.framework.run.SimulationRunner.StoppingRule;

public class DataCollector {

	public static Logger log = Logger.getLogger(DataCollector.class);

	public static void main(String[] args) {
//		collectReferenceData("RT1", Revision.R100, "ispass03.qpe", "example_config", AnalysisMode.BATCH_MEANS, StoppingRule.RELATIVE_PRECISION, 30);
//		collectReferenceData("RT1", Revision.R162, "ispass03.qpe", "example_config", AnalysisMode.BATCH_MEANS, StoppingRule.RELATIVE_PRECISION, 30);
//		collectReferenceData("RT2", Revision.R100, "pepsy-bcmp2.qpe", "example_config", AnalysisMode.BATCH_MEANS, StoppingRule.RELATIVE_PRECISION, 100);
//		collectReferenceData("RT2", Revision.R162, "pepsy-bcmp2.qpe", "example_config", AnalysisMode.BATCH_MEANS, StoppingRule.RELATIVE_PRECISION, 100);
//		collectReferenceData("RT3", Revision.R100, "SjAS04Model_6AS-L5.qpe", "example_config", AnalysisMode.BATCH_MEANS, StoppingRule.RELATIVE_PRECISION, 30);
//		collectReferenceData("RT3", Revision.R162, "SjAS04Model_6AS-L5.qpe", "example_config", AnalysisMode.BATCH_MEANS, StoppingRule.RELATIVE_PRECISION, 30);
//		collectReferenceData("RT4", Revision.R162, "SPECjms2007Model.qpe", "new configuration", AnalysisMode.BATCH_MEANS, StoppingRule.FIXED_LENGTH, 15);
//		collectReferenceData("RT5", Revision.R100, "ispass03.qpe", "example_config", AnalysisMode.BATCH_MEANS, StoppingRule.RELATIVE_PRECISION, 100);
//		collectReferenceData("RT5", Revision.R162, "ispass03.qpe", "example_config", AnalysisMode.BATCH_MEANS, StoppingRule.RELATIVE_PRECISION, 100);
//		collectReferenceData("RT6", Revision.R100, "ispass03.qpe", "example_config", AnalysisMode.BATCH_MEANS, StoppingRule.ABSOLUTE_PRECISION, 100);
//		collectReferenceData("RT6", Revision.R162, "ispass03.qpe", "example_config", AnalysisMode.BATCH_MEANS, StoppingRule.ABSOLUTE_PRECISION, 100);
//		collectReferenceData("RT7", Revision.R100, "pepsy-bcmp2.qpe", "example_config", AnalysisMode.REPLICATION_DELETION, StoppingRule.FIXED_LENGTH, 4);
//		collectReferenceData("RT7", Revision.R162, "pepsy-bcmp2.qpe", "example_config", AnalysisMode.REPLICATION_DELETION, StoppingRule.FIXED_LENGTH, 4);
//		collectReferenceData("RT8", Revision.R100, "pepsy-bcmp2.qpe", "example_config", AnalysisMode.WELCH, StoppingRule.FIXED_LENGTH, 4);
//		collectReferenceData("RT8", Revision.R162, "pepsy-bcmp2.qpe", "example_config", AnalysisMode.WELCH, StoppingRule.FIXED_LENGTH, 4);
//		collectReferenceData("RT12", Revision.R162, "ispass03.qpe", "example_config", AnalysisMode.BATCH_MEANS, StoppingRule.RELATIVE_PRECISION, 100);
	}

	public static void collectReferenceData(String test, Revision revision,
			String model, String configuration, AnalysisMode mode, StoppingRule stoppingRule, int repeats) {
		log.info("Start reference collection data for test " + test + "(" + revision.toString() + ")");
		
		File modelFile = new File("testfiles/" + test + "/reference/" + revision.toString().toLowerCase() + "/" + model);
		if (!modelFile.exists()) {
			log.error("Could not find model file: " + model);
			return;
		}
		
		try {
			SimulationRunner runner = new SimulationRunner(revision, test);
			RunConfig config = new RunConfig();
			config.setModel(modelFile);
			config.setConfigurationName(configuration);
			config.setAnalysisMode(mode);
			config.setStoppingRule(stoppingRule);
			config.setRepeats(repeats);
			config.setExpectError(false);
			SimulationResults reference = runner.run(config);
		
		
			for(Statistics pl : reference.getPlaceStats()) {
				
				for(Metric m : pl.getMetrics()) {
					checkCV(pl.getName(), "all colors", m);
				}
				
			
				for(Statistics col : pl.getChildStats()) {
					
					for(Metric m : col.getMetrics()) {
						checkCV(pl.getName(), col.getName(), m);
					}
				}
			}
			
			reference.save(new File("./testfiles/" + test + "/reference/" + revision.toString() + "/reference-testdata.xml"));
		} catch(Exception ex) {
			log.error("Error saving reference data.", ex);
		}
		
		log.info("Finished reference data collection for test " + test + "(" + revision.toString() + ")");
		
	}
	
	private static void checkCV(String place, String col, Metric value) {
		double cv = (value.getStandardDeviation() / value.getMean());
		if (cv > 0.05) {
			LogMF.warn(log, "CV={0} of metric {1} at {2} and color {3}", cv, value.getName(), place, col);
		}
	}

}
