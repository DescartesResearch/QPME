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

public class RT5 extends SimulationTest {
	
	@BeforeClass
	public static void init() throws Exception {
		initTest("RT5", "ispass03_hierarchical.qpe", "example_config");
	}
	
	@Test
	public void checkCount() {
		assertPlaceCount(12, results);
		assertQueueCount(4, results);
	}
	
	@Test
	public void checkPrecision() {
		assertRelativePrecision(0.05, results, "qplace:depository", "Client");
		assertRelativePrecision(0.05, results, "qplace:queue", "Client");
		assertRelativePrecision(0.05, results, "qplace:depository", "WLS-CPU");
		assertRelativePrecision(0.05, results, "qplace:queue", "WLS-CPU");
		assertRelativePrecision(0.05, results, "qplace:depository", "DBS-CPU");
		assertRelativePrecision(0.05, results, "qplace:queue", "DBS-CPU");
		assertRelativePrecision(0.05, results, "qplace:depository", "DBS-I/O");
		assertRelativePrecision(0.05, results, "qplace:queue", "DBS-I/O");
	}
	
	@Test
	public void checkR100() throws Exception {		
		SimulationResults r100ref = new SimulationResults();
		r100ref.load(new File("testfiles/RT5/reference/r100/reference-testdata.xml"));
		
		assertResults(report, r100ref, results);
	}
	
	@Test
	public void checkR162() throws Exception {		
		SimulationResults r162ref = new SimulationResults();
		r162ref.load(new File("testfiles/RT5/reference/r162/reference-testdata.xml"));
		
		assertResults(report, r162ref, results);
	}
}
