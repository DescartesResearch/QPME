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

public class RT5 extends SimulationTest {
	
	@BeforeClass
	public static void init() throws Exception {
		initTest("RT5", "ispass03_hierarchical.qpe", "example_config", AnalysisMode.BATCH_MEANS, StoppingRule.RELATIVE_PRECISION, "HQPN");
	}
	
	@Test
	public void checkIntegrity() {
		
		assertRunLengthLessOrEqual(50E6, results);
		
		assertNoErrors(results);
		assertNoWarnings(results);
		
		assertPlaceCount(15, results);
		assertQueueCount(4, results);
	}
	
	@Test
	public void checkStability() {
		assertFlowEquilibrium(results);
	}
	
	@Test
	public void checkPrecision() {
		assertRelativePrecision(0.05, results, ElementType.QPLACE_DEPOSITORY, "Client");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_QUEUE, "Client");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_DEPOSITORY, "WLS-CPU");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_QUEUE, "WLS-CPU");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_DEPOSITORY, "DBS.DBS-CPU");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_QUEUE, "DBS.DBS-CPU");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_DEPOSITORY, "DBS.DBS-I/O");
		assertRelativePrecision(0.05, results, ElementType.QPLACE_QUEUE, "DBS.DBS-I/O");
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