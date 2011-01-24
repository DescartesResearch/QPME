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
import de.tud.cs.simqpn.rt.framework.run.SimulationRunner.AnalysisMode;
import de.tud.cs.simqpn.rt.framework.run.SimulationRunner.Revision;
import de.tud.cs.simqpn.rt.framework.run.SimulationRunner.StoppingRule;

public class RT3 extends SimulationTest {
	@BeforeClass
	public static void init() throws Exception {
		initTest("RT3", "SjAS04Model_6AS-L5.qpe", "example_config", AnalysisMode.BATCH_MEANS, StoppingRule.RELATIVE_PRECISION);
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
		assertRelativePrecision(0.05, results, ElementType.QPLACE_DEPOSITORY, "A1");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_QUEUE, "A1");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_DEPOSITORY, "A2");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_QUEUE, "A2");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_DEPOSITORY, "A3");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_QUEUE, "A3");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_DEPOSITORY, "A4");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_QUEUE, "A4");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_DEPOSITORY, "A5");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_QUEUE, "A5");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_DEPOSITORY, "A6");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_QUEUE, "A6");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_DEPOSITORY, "B1");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_QUEUE, "B1");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_DEPOSITORY, "B2");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_QUEUE, "B2");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_DEPOSITORY, "L");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_QUEUE, "L");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_DEPOSITORY, "H");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_QUEUE, "H");
	}
	
	@Test
	public void checkR100() throws Exception {		
		assertResults(report, loadReferenceData(Revision.R100), results);
	}
	
	@Test
	public void checkR162() throws Exception {		
		assertResults(report, loadReferenceData(Revision.R162), results);
	}
}
