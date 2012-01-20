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
 */
package de.tud.cs.simqpn.rt.tests;

import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertFlowEquilibrium;
import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertNoErrors;
import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertNoWarnings;
import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertPlaceCount;
import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertProbeCount;
import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertProbeResults;
import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertQueueCount;
import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertRunLengthLessOrEqual;

import org.junit.BeforeClass;
import org.junit.Test;

import de.tud.cs.simqpn.rt.framework.SimulationTest;
import de.tud.cs.simqpn.rt.framework.results.Statistics;
import de.tud.cs.simqpn.rt.framework.results.Statistics.ElementType;
import de.tud.cs.simqpn.rt.framework.run.RunConfig.AnalysisMode;
import de.tud.cs.simqpn.rt.framework.run.RunConfig.StoppingRule;

public class RT9 extends SimulationTest {
	
	@BeforeClass
	public static void init() throws Exception {
		initTest("RT9", "ispass03_probes.qpe", "example_config", AnalysisMode.BATCH_MEANS, StoppingRule.RELATIVE_PRECISION, "Probes");
	}
	
	@Test
	public void checkIntegrity() {
		
		assertRunLengthLessOrEqual(50E6, results);
		
		assertNoErrors(results);
		assertNoWarnings(results);
		
		assertPlaceCount(12, results);
		assertQueueCount(4, results);
		assertProbeCount(4, results);
	}
	
	@Test
	public void checkStability() {
		assertFlowEquilibrium(results);
	}
	
	@Test
	public void checkProbes() {
		
		//entry/exit
		Statistics probeStats = results.getStatistics("Probe1", ElementType.PROBE);
		Statistics[] places = new Statistics[4];
		places[0] = results.getStatistics("DBS-CPU", ElementType.QPLACE_QUEUE);
		places[1] = results.getStatistics("DBS-CPU", ElementType.QPLACE_DEPOSITORY);
		places[2] = results.getStatistics("DBS-I/O", ElementType.QPLACE_QUEUE);
		places[3] = results.getStatistics("DBS-I/O", ElementType.QPLACE_DEPOSITORY);
		
		assertProbeResults(probeStats, places);
		
		//entry/entry
		probeStats = results.getStatistics("Probe2", ElementType.PROBE);
		places = new Statistics[2];
		places[0] = results.getStatistics("DBS-CPU", ElementType.QPLACE_QUEUE);
		places[1] = results.getStatistics("DBS-CPU", ElementType.QPLACE_DEPOSITORY);
		
		assertProbeResults(probeStats, places);
		
		//exit/exit
		probeStats = results.getStatistics("Probe3", ElementType.PROBE);
		places = new Statistics[2];
		places[0] = results.getStatistics("DBS-I/O", ElementType.QPLACE_QUEUE);
		places[1] = results.getStatistics("DBS-I/O", ElementType.QPLACE_DEPOSITORY);
		
		assertProbeResults(probeStats, places);
		
		//exit/exit
		probeStats = results.getStatistics("Probe4", ElementType.PROBE);
		places = new Statistics[0];
	
		assertProbeResults(probeStats, places);
	}
}
