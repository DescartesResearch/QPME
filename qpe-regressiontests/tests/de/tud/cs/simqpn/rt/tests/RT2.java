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
