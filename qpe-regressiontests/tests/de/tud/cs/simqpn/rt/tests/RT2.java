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


public class RT2 extends SimulationTest {
	
	@BeforeClass
	public static void init() throws Exception {
		initTest("RT2", "pepsy-bcmp2.qpe", "example_config");
	}
	
	@Test
	public void checkCount() {
		assertPlaceCount(10, results);
		assertQueueCount(5, results);
	}
	
	@Test
	public void checkPrecision() {
		assertRelativePrecision(0.05, results, "qplace:depository", "CPU");
		assertRelativePrecision(0.05, results, "qplace:queue", "CPU");
		assertRelativePrecision(0.05, results, "qplace:depository", "Disk 1");
		assertRelativePrecision(0.05, results, "qplace:queue", "Disk 1");
		assertRelativePrecision(0.05, results, "qplace:depository", "Disk 2");
		assertRelativePrecision(0.05, results, "qplace:queue", "Disk 2");
		assertRelativePrecision(0.05, results, "qplace:depository", "Disk 3");
		assertRelativePrecision(0.05, results, "qplace:queue", "Disk 3");
	}
	
	@Test
	public void checkR100() throws Exception {		
		SimulationResults r100ref = new SimulationResults();
		r100ref.load(new File("testfiles/RT2/reference/r100/reference-testdata.xml"));
		
		assertResults(report, r100ref, results);
	}
	
	@Test
	public void checkR162() throws Exception {		
		SimulationResults r162ref = new SimulationResults();
		r162ref.load(new File("testfiles/RT2/reference/r162/reference-testdata.xml"));
		
		assertResults(report, r162ref, results);
	}
}
