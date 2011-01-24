package de.tud.cs.simqpn.rt.tests;

import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertOverflow;

import org.junit.BeforeClass;
import org.junit.Test;

import de.tud.cs.simqpn.rt.framework.SimulationTest;
import de.tud.cs.simqpn.rt.framework.run.SimulationRunner.AnalysisMode;
import de.tud.cs.simqpn.rt.framework.run.SimulationRunner.StoppingRule;

public class RT10 extends SimulationTest {
	
	@BeforeClass
	public static void init() throws Exception {
		initTest("RT10", "overflow.qpe", "test_config", AnalysisMode.BATCH_MEANS, StoppingRule.FIXED_LENGTH, true);
	}
	
	@Test
	public void checkOverflow() {
		
		assertOverflow(results);
		
	}

}
