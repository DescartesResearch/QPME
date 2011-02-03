package de.tud.cs.simqpn.rt.tests;

import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertNoErrors;
import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertNoWarnings;
import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertPlaceCount;
import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertResults;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import de.tud.cs.simqpn.rt.framework.SimulationTest;
import de.tud.cs.simqpn.rt.framework.run.SimulationRunner.AnalysisMode;
import de.tud.cs.simqpn.rt.framework.run.SimulationRunner.Revision;
import de.tud.cs.simqpn.rt.framework.run.SimulationRunner.StoppingRule;

public class RT7 extends SimulationTest {
	@BeforeClass
	public static void init() throws Exception {
		initTest("RT7", "pepsy-bcmp2.qpe", "example_config", AnalysisMode.REPLICATION_DELETION, StoppingRule.FIXED_LENGTH, "");
	}
	
	@Test
	public void checkIntegrity() {
		
		assertNoErrors(results);
		assertNoWarnings(results);
		
		assertPlaceCount(10, results);
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
