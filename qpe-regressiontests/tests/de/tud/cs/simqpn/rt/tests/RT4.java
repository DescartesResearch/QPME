package de.tud.cs.simqpn.rt.tests;

import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertPlaceCount;
import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertQueueCount;
import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertResults;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import de.tud.cs.simqpn.rt.framework.SimulationResults;
import de.tud.cs.simqpn.rt.framework.SimulationTest;

public class RT4 extends SimulationTest {
	
	@BeforeClass
	public static void init() throws Exception {
		initTest("RT4", "SPECjms2007Model.qpe", "example_config");
	}
	
	@Test
	public void checkCount() {
		assertPlaceCount(120, results);
		assertQueueCount(23, results);
	}
	
	@Test
	public void checkR162() throws Exception {		
		SimulationResults r162ref = new SimulationResults();
		r162ref.load(new File("testfiles/RT4/reference/r162/reference-testdata.xml"));
		
		assertResults(report, r162ref, results);
	}

}
