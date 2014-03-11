package de.tud.cs.simqpn.kernel.analyzer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNController;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.stats.AggregateStats;
import de.tud.cs.simqpn.kernel.entities.stats.Stats;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;

public abstract class Analyzer {
	private static Logger log = Logger.getLogger(Analyzer.class);

	
	public abstract Stats[] analyze(Net net, SimQPNConfiguration configuration,
			SimulatorProgress monitor)
			throws SimQPNException;

	public abstract File writeToFile(Stats[] result, SimQPNConfiguration configuration,
			String outputFileName, Element XML, String configurationName)
			throws SimQPNException;
	
	/**
	 * Factory method
	 * @param configuration
	 * @param aggregateStats
	 * @param XMLNet
	 * @param configurationString
	 * @return
	 */
	public static Analyzer getAnalyzer(SimQPNConfiguration configuration,
			AggregateStats[] aggregateStats, Element XMLNet,
			String configurationString) {
		Analyzer analyzer = null;
		try {
			if (configuration.runMode == SimQPNConfiguration.NORMAL) {
				if (configuration.getAnalMethod() == SimQPNConfiguration.AnalysisMethod.BATCH_MEANS) {
					analyzer = new BatchMeans();
				} else if (configuration.getAnalMethod() == SimQPNConfiguration.AnalysisMethod.REPL_DEL) {
					configuration.setUseStdStateStats(false);
					analyzer = new ReplicationDeletion(aggregateStats);
				} else {
					log.error("Illegal analysis method specified!");
					throw new SimQPNException();
				}
			} else if (configuration.runMode == SimQPNConfiguration.INIT_TRANS) {
				if (configuration.getAnalMethod() == SimQPNConfiguration.AnalysisMethod.WELCH) {
					analyzer = new Welch(XMLNet, configurationString);
				} else {
					log.error("Analysis method "
							+ configuration.getAnalMethod()
							+ " not supported in INIT_TRANS mode!");
					throw new SimQPNException();
				}
			} else {
				log.error("Invalid run mode specified!");
				throw new SimQPNException();
			}
		} catch (SimQPNException ex) {
			log.error("Error during analyzer creation");
			SimQPNController.setSimRunning(false);
		}
		return analyzer;
	}


		
	protected void saveXmlToFile(Document doc, File file) {
		XMLWriter writer = null;
		try {
			writer = new XMLWriter(new FileOutputStream(file),
					OutputFormat.createPrettyPrint());
			writer.write(doc);
		} catch (IOException e) {
			log.error("", e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
				}
			}
		}
	}
		
}
