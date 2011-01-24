package de.tud.cs.simqpn.rt.tests;

import static de.tud.cs.simqpn.rt.framework.SimulationAssert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import de.tud.cs.simqpn.rt.framework.SimulationTest;
import de.tud.cs.simqpn.rt.framework.results.Statistics;
import de.tud.cs.simqpn.rt.framework.results.Statistics.ElementType;
import de.tud.cs.simqpn.rt.framework.run.SimulationRunner.AnalysisMode;
import de.tud.cs.simqpn.rt.framework.run.SimulationRunner.StoppingRule;

public class RT9 extends SimulationTest {
	
	@BeforeClass
	public static void init() throws Exception {
		initTest("RT9", "ispass03_probes.qpe", "example_config", AnalysisMode.BATCH_MEANS, StoppingRule.RELATIVE_PRECISION);
	}
	
	@Test
	public void checkIntegrity() {
		
		assertRunLengthLessOrEqual(50E6, results);
		
		assertNoErrors(results);
		assertNoWarnings(results);
		
		assertPlaceCount(12, results);
		assertQueueCount(4, results);
		assertProbeCount(4, results);
	}
	
	@Test
	public void checkStability() {
		assertFlowEquilibrium(results);
	}
	
	@Test
	public void checkProbes() {
		
		//entry/exit
		Statistics probeStats = results.getStatistics("Probe1", ElementType.PROBE);
		Statistics[] places = new Statistics[4];
		places[0] = results.getStatistics("DBS-CPU", ElementType.QPLACE_QUEUE);
		places[1] = results.getStatistics("DBS-CPU", ElementType.QPLACE_DEPOSITORY);
		places[2] = results.getStatistics("DBS-I/O", ElementType.QPLACE_QUEUE);
		places[3] = results.getStatistics("DBS-I/O", ElementType.QPLACE_DEPOSITORY);
		
		assertProbeResults(probeStats, places);
		
		//entry/entry
		probeStats = results.getStatistics("Probe2", ElementType.PROBE);
		places = new Statistics[2];
		places[0] = results.getStatistics("DBS-CPU", ElementType.QPLACE_QUEUE);
		places[1] = results.getStatistics("DBS-CPU", ElementType.QPLACE_DEPOSITORY);
		
		assertProbeResults(probeStats, places);
		
		//exit/exit
		probeStats = results.getStatistics("Probe3", ElementType.PROBE);
		places = new Statistics[2];
		places[0] = results.getStatistics("DBS-I/O", ElementType.QPLACE_QUEUE);
		places[1] = results.getStatistics("DBS-I/O", ElementType.QPLACE_DEPOSITORY);
		
		assertProbeResults(probeStats, places);
		
		//exit/exit
		probeStats = results.getStatistics("Probe4", ElementType.PROBE);
		places = new Statistics[0];
	
		assertProbeResults(probeStats, places);
	}
}
