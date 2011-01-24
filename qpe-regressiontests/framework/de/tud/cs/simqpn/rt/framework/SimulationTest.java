package de.tud.cs.simqpn.rt.framework;

import java.io.File;

import org.junit.AfterClass;

import de.tud.cs.simqpn.rt.framework.SimulationRunner.Revision;

public class SimulationTest {
	
	protected static SimulationResults results;
	protected static TestReport report;

	public static void initTest(String testName, String model, String configuration) {
		SimulationRunner runner = new SimulationRunner(Revision.TRUNK, testName);
		results = runner.run(model, configuration);
		
		report = new TestReport();
		File reportDir = new File("testfiles/" + testName + "/reports");
		reportDir.mkdirs();
		report.startReport(new File(reportDir, "latest.html"));
	}
	
	@AfterClass
	public static void finishTest() throws Exception {
		report.endReport();
	}
}
