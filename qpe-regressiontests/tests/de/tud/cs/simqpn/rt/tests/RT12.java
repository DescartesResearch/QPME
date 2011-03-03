package de.tud.cs.simqpn.rt.tests;

import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertFlowEquilibrium;
import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertNoErrors;
import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertNoWarnings;
import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertPlaceCount;
import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertQueueCount;
import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertResults;
import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertRunLengthLessOrEqual;

import org.junit.BeforeClass;
import org.junit.Test;

import de.tud.cs.simqpn.rt.framework.SimulationTest;
import de.tud.cs.simqpn.rt.framework.run.RunConfig.AnalysisMode;
import de.tud.cs.simqpn.rt.framework.run.RunConfig.Revision;
import de.tud.cs.simqpn.rt.framework.run.RunConfig.StoppingRule;


public class RT12 extends SimulationTest {
	
	@BeforeClass
	public static void init() throws Exception {
		initTest("RT12", "ispass03.qpe", "example_config", AnalysisMode.BATCH_MEANS, StoppingRule.RELATIVE_PRECISION, "statsLevel=4");
	}
	
	@Test
	public void checkIntegrity() {
		
		assertRunLengthLessOrEqual(50E6, results);
		
		assertNoErrors(results);
		assertNoWarnings(results);
		
		assertPlaceCount(12, results);
		assertQueueCount(4, results);
	}
	
	@Test
	public void checkStability() {
		assertFlowEquilibrium(results);
	}
	
	@Test
	public void checkR162() throws Exception {		
		assertResults(report, Revision.R162, results);
	}
}
