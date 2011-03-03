package de.tud.cs.simqpn.rt.tests;

import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertNoErrors;
import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertPlaceCount;
import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertQueueCount;
import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertResults;
import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertRunLength;

import org.junit.BeforeClass;
import org.junit.Test;

import de.tud.cs.simqpn.rt.framework.SimulationTest;
import de.tud.cs.simqpn.rt.framework.run.RunConfig.AnalysisMode;
import de.tud.cs.simqpn.rt.framework.run.RunConfig.Revision;
import de.tud.cs.simqpn.rt.framework.run.RunConfig.StoppingRule;

public class RT4 extends SimulationTest {
	
	@BeforeClass
	public static void init() throws Exception {
		initTest("RT4", "SPECjms2007Model.qpe", "new configuration", AnalysisMode.BATCH_MEANS, StoppingRule.FIXED_LENGTH, "Shared Queues");
	}
	
	@Test
	public void checkIntegrity() {
		
		assertRunLength(1320, results);
		
		assertNoErrors(results);
		
		assertPlaceCount(120, results);
		assertQueueCount(23, results);
	}
	
//	@Test
//	public void checkStability() {
//		assertFlowEquilibrium(results);
//	}
	
	@Test
	public void checkR162() throws Exception {		
		assertResults(report, Revision.R162, results);
	}

}
