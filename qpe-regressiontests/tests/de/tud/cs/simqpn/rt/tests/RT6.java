package de.tud.cs.simqpn.rt.tests;

import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertAbsolutePrecision;
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
import de.tud.cs.simqpn.rt.framework.results.Statistics.ElementType;
import de.tud.cs.simqpn.rt.framework.run.RunConfig.AnalysisMode;
import de.tud.cs.simqpn.rt.framework.run.RunConfig.Revision;
import de.tud.cs.simqpn.rt.framework.run.RunConfig.StoppingRule;

public class RT6 extends SimulationTest {

	@BeforeClass
	public static void init() throws Exception {
		initTest("RT6", "ispass03.qpe", "example_config", AnalysisMode.BATCH_MEANS, StoppingRule.ABSOLUTE_PRECISION, "");
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
	public void checkPrecision() {
		assertAbsolutePrecision(10.0, results, ElementType.QPLACE_DEPOSITORY, "Client");
		assertAbsolutePrecision(1.5, results, ElementType.QPLACE_QUEUE, "Client");
		assertAbsolutePrecision(10, results, ElementType.QPLACE_DEPOSITORY, "WLS-CPU");
		assertAbsolutePrecision(40, results, ElementType.QPLACE_QUEUE, "WLS-CPU");
		assertAbsolutePrecision(10, results, ElementType.QPLACE_DEPOSITORY, "DBS-CPU");
		assertAbsolutePrecision(10, results, ElementType.QPLACE_QUEUE, "DBS-CPU");
		assertAbsolutePrecision(10, results, ElementType.QPLACE_DEPOSITORY, "DBS-I/O");
		assertAbsolutePrecision(1, results, ElementType.QPLACE_QUEUE, "DBS-I/O");
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
