package de.tud.cs.simqpn.rt.tests;

import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertPlaceCount;
import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertQueueCount;
import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertRelativePrecision;
import static de.tud.cs.simqpn.rt.framework.SimulationAssert.assertResults;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import de.tud.cs.simqpn.rt.framework.SimulationResults;
import de.tud.cs.simqpn.rt.framework.SimulationTest;

public class RT3 extends SimulationTest {
	@BeforeClass
	public static void init() throws Exception {
		initTest("RT3", "SjAS04Model_6AS-L5.qpe", "example_config");
	}
	
	@Test
	public void checkCount() {
		assertPlaceCount(30, results);
		assertQueueCount(13, results);
	}
	
	@Test
	public void checkPrecision() {
		assertRelativePrecision(0.05, results, "qplace:depository", "A1");
		assertRelativePrecision(0.05, results, "qplace:queue", "A1");
		assertRelativePrecision(0.05, results, "qplace:depository", "A2");
		assertRelativePrecision(0.05, results, "qplace:queue", "A2");
		assertRelativePrecision(0.05, results, "qplace:depository", "A3");
		assertRelativePrecision(0.05, results, "qplace:queue", "A3");
		assertRelativePrecision(0.05, results, "qplace:depository", "A4");
		assertRelativePrecision(0.05, results, "qplace:queue", "A4");
		assertRelativePrecision(0.05, results, "qplace:depository", "A5");
		assertRelativePrecision(0.05, results, "qplace:queue", "A5");
		assertRelativePrecision(0.05, results, "qplace:depository", "A6");
		assertRelativePrecision(0.05, results, "qplace:queue", "A6");
		assertRelativePrecision(0.05, results, "qplace:depository", "B1");
		assertRelativePrecision(0.05, results, "qplace:queue", "B1");
		assertRelativePrecision(0.05, results, "qplace:depository", "B2");
		assertRelativePrecision(0.05, results, "qplace:queue", "B2");
		assertRelativePrecision(0.05, results, "qplace:depository", "L");
		assertRelativePrecision(0.05, results, "qplace:queue", "L");
		assertRelativePrecision(0.05, results, "qplace:depository", "H");
		assertRelativePrecision(0.05, results, "qplace:queue", "H");
	}
	
	@Test
	public void checkR100() throws Exception {		
		SimulationResults r100ref = new SimulationResults();
		r100ref.load(new File("testfiles/RT3/reference/r100/reference-testdata.xml"));
		
		assertResults(report, r100ref, results);
	}
	
	@Test
	public void checkR162() throws Exception {		
		SimulationResults r162ref = new SimulationResults();
		r162ref.load(new File("testfiles/RT3/reference/r162/reference-testdata.xml"));
		
		assertResults(report, r162ref, results);
	}
}
