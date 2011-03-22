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

public class RT3 extends SimulationTest {
	@BeforeClass
	public static void init() throws Exception {
		initTest("RT3", "SjAS04Model_6AS-L5.qpe", "example_config", AnalysisMode.BATCH_MEANS, StoppingRule.RELATIVE_PRECISION, "FIFO Departure Discipline");
	}
	
	@Test
	public void checkIntegrity() {
		
		assertRunLengthLessOrEqual(100E7, results);
		
		assertNoErrors(results);
		assertNoWarnings(results);
		
		assertPlaceCount(30, results);
		assertQueueCount(13, results);
	}
	
	@Test
	public void checkStability() {
		assertFlowEquilibrium(results);
	}
	
	@Test
	public void checkPrecision() {
		assertRelativePrecision(0.01, results, ElementType.QPLACE_DEPOSITORY, "A1");
		assertRelativePrecision(0.01, results, ElementType.QPLACE_QUEUE, "A1");
		assertRelativePrecision(0.01, results, ElementType.QPLACE_DEPOSITORY, "A2");
		assertRelativePrecision(0.01, results, ElementType.QPLACE_QUEUE, "A2");
		assertRelativePrecision(0.01, results, ElementType.QPLACE_DEPOSITORY, "A3");
		assertRelativePrecision(0.01, results, ElementType.QPLACE_QUEUE, "A3");
		assertRelativePrecision(0.01, results, ElementType.QPLACE_DEPOSITORY, "A4");
		assertRelativePrecision(0.01, results, ElementType.QPLACE_QUEUE, "A4");
		assertRelativePrecision(0.01, results, ElementType.QPLACE_DEPOSITORY, "A5");
		assertRelativePrecision(0.01, results, ElementType.QPLACE_QUEUE, "A5");
		assertRelativePrecision(0.01, results, ElementType.QPLACE_DEPOSITORY, "A6");
		assertRelativePrecision(0.01, results, ElementType.QPLACE_QUEUE, "A6");
		assertRelativePrecision(0.01, results, ElementType.QPLACE_DEPOSITORY, "B1");
		assertRelativePrecision(0.01, results, ElementType.QPLACE_QUEUE, "B1");
		assertRelativePrecision(0.01, results, ElementType.QPLACE_DEPOSITORY, "B2");
		assertRelativePrecision(0.01, results, ElementType.QPLACE_QUEUE, "B2");
		assertRelativePrecision(0.01, results, ElementType.QPLACE_DEPOSITORY, "L");
		assertRelativePrecision(0.01, results, ElementType.QPLACE_QUEUE, "L");
		assertRelativePrecision(0.01, results, ElementType.QPLACE_DEPOSITORY, "H");
		assertRelativePrecision(0.01, results, ElementType.QPLACE_QUEUE, "H");
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
