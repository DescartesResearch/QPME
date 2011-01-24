package de.tud.cs.simqpn.rt.framework;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.tud.cs.simqpn.rt.framework.SimulationRunner.Revision;
import de.tud.cs.simqpn.rt.framework.stats.Metric;
import de.tud.cs.simqpn.rt.framework.stats.PlaceStatistics;
import de.tud.cs.simqpn.rt.framework.stats.Statistics;

public class DataCollector {

	public static Logger log = Logger.getLogger(DataCollector.class.getName());

	public static void main(String[] args) {
//		collectReferenceData("RT1", Revision.R100, "reference/r100/ispass03.qpe", "example_config", 30);
//		collectReferenceData("RT1", Revision.R162, "reference/r162/ispass03.qpe", "example_config", 30);
//		collectReferenceData("RT2", Revision.R100, "reference/r100/pepsy-bcmp2.qpe", "example_config", 30);
//		collectReferenceData("RT2", Revision.R162, "reference/r162/pepsy-bcmp2.qpe", "example_config", 30);
		collectReferenceData("RT3", Revision.R100, "reference/r100/SjAS04Model_6AS-L5.qpe", "example_config", 30);
		collectReferenceData("RT3", Revision.R162, "reference/r162/SjAS04Model_6AS-L5.qpe", "example_config", 30);
//		collectReferenceData("RT4", Revision.R162, "reference/r162/SPECjms2007Model.qpe", "new configuration", 30);
//		collectReferenceData("RT5", Revision.R100, "reference/r100/ispass03.qpe", "example_config", 30);
//		collectReferenceData("RT5", Revision.R162, "reference/r162/ispass03.qpe", "example_config", 30);
	}

	public static void collectReferenceData(String test, Revision revision,
			String model, String configuration, int repeats) {
		log.info("Start reference collection data for test " + test + "(" + revision.toString() + ")");
		
		try {
			SimulationRunner runner = new SimulationRunner(revision, test);
			SimulationResults reference = runner.run(model, configuration, repeats);
		
		
			for(PlaceStatistics pl : reference.getPlaces()) {
				
				for(Metric m : pl.getMetrics()) {
					checkCV(pl.getName(), "all colors", m);
				}
				
			
				for(Statistics col : pl.getColorStats()) {
					
					for(Metric m : col.getMetrics()) {
						checkCV(pl.getName(), col.getName(), m);
					}
				}
			}
			
			reference.save(new File("./testfiles/" + test + "/reference/" + revision.toString() + "/reference-testdata.xml"));
		} catch(Exception ex) {
			log.log(Level.SEVERE, "Error saving reference data.", ex);
		}
		
		log.info("Finished reference data collection for test " + test + "(" + revision.toString() + ")");
		
	}
	
	private static void checkCV(String place, String col, Metric value) {
		double cv = (value.getStandardDeviation() / value.getMean());
		if (cv > 0.05) {
			log.warning("CV=" + cv + " of metric " + value.getName() + " at " + place + " and color " + col);
		}
	}

}
