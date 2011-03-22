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
import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertQueueCount;
import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertRelativePrecision;
import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertResults;
import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertRunLengthLessOrEqual;

import org.junit.BeforeClass;
import org.junit.Test;

import de.tud.cs.simqpn.rt.framework.SimulationTest;
import de.tud.cs.simqpn.rt.framework.results.Statistics.ElementType;
import de.tud.cs.simqpn.rt.framework.run.RunConfig.AnalysisMode;
import de.tud.cs.simqpn.rt.framework.run.RunConfig.Revision;
import de.tud.cs.simqpn.rt.framework.run.RunConfig.StoppingRule;


public class RT2 extends SimulationTest {
	
	@BeforeClass
	public static void init() throws Exception {
		initTest("RT2", "pepsy-bcmp2.qpe", "example_config", AnalysisMode.BATCH_MEANS, StoppingRule.RELATIVE_PRECISION, "");
	}
	
	@Test
	public void checkIntegrity() {
		
		assertRunLengthLessOrEqual(32E8, results);
		
		assertNoErrors(results);
		assertNoWarnings(results);
		
		assertPlaceCount(10, results);
		assertQueueCount(5, results);
	}
	
	@Test
	public void checkStability() {
		assertFlowEquilibrium(results);
	}
	
	@Test
	public void checkPrecision() {
		assertRelativePrecision(0.05, results, ElementType.QPLACE_DEPOSITORY, "CPU");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_QUEUE, "CPU");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_DEPOSITORY, "Disk 1");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_QUEUE, "Disk 1");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_DEPOSITORY, "Disk 2");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_QUEUE, "Disk 2");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_DEPOSITORY, "Disk 3");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_QUEUE, "Disk 3");
	}
	
	@Test
	public void checkR100() throws Exception {		
		assertResults(report, Revision.R100, results);
	}
	
	@Test
	public void checkR162() throws Exception {		
		assertResults(report, Revision.R162, results);
	}
}
