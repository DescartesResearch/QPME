package de.tud.cs.simqpn.kernel.loader;

import static de.tud.cs.simqpn.kernel.util.LogUtil.formatDetailMessage;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.XPath;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNController;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.Probe;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.util.LogUtil;
import de.tud.cs.simqpn.kernel.util.LogUtil.ReportLevel;

public class ConfigurationLoader {
	private static Logger log = Logger.getLogger(ConfigurationLoader.class);

	public static SimQPNConfiguration configure(Element netElement,
			String configurationString, String logConfigFilename)
			throws SimQPNException {
		// BEGIN-CONFIG
		// ------------------------------------------------------------------------------------------------------

		/*
		 * Global run configuration parameters: 1. "Analysis Method"
		 * (analMethod) == BATCH_MEANS, REPL_DEL or WELCH 2.
		 * "Maximum Number of Runs" (numRuns) 3. "Output Directory" (statsDir)
		 * 
		 * numRuns should only be available if analMethod is REPL_DEL or WELCH.
		 * 
		 * IMPORTANT: runMode is implied from the chosen analysis method: - If
		 * the user chooses BATCH_MEANS or REPL_DEL, runMode is set to NORMAL. -
		 * If the user chooses WELCH, runMode is set to INIT_TRANS.
		 * 
		 * The QPN to be simulated is defined in the getReady() method.
		 * 
		 * Depending on the selected analysis method different parameters must
		 * be configured - see methods getReady and runWelchMtd for detailed
		 * information.
		 * 
		 * IMPORTANT: The order in which things are done must remain
		 * unchanged!!!
		 */
		SimQPNConfiguration configuration = new SimQPNConfiguration();

		// Initialize logging
		if (logConfigFilename != null) {
			LogUtil.configureCustomLogging(logConfigFilename);
		} else {
			XPath xpathSelector = XMLHelper
					.createXPath("/net/meta-attributes/meta-attribute[@xsi:type = 'simqpn-configuration' and @configuration-name = '"
							+ configurationString + "']/@output-directory");
			Attribute outputDirAttribute = (Attribute) xpathSelector
					.selectSingleNode(netElement);
			try {
				LogUtil.configureDefaultLogging(
						outputDirAttribute.getStringValue(), "SimQPN_Output_"
								+ configurationString);
			} catch (IOException e) {
				log.error(
						"Cannot create simulation output log file! Please check output directory path.",
						e);
				throw new SimQPNException();
			}
		}

		Element simulatorSettings = ConfigurationLoader.getSettings(netElement,
				configurationString);

		if (simulatorSettings.attributeValue("verbosity-level") == null) {
			log.error(formatDetailMessage(
					"Configuration parameter \"verbosity-level\" is not configured!",
					"configuration", configurationString));
			throw new SimQPNException();
		}
		SimQPNConfiguration.setDebugLevel(Integer.parseInt(simulatorSettings
				.attributeValue("verbosity-level")));
		switch (SimQPNConfiguration.getDebugLevel()) {
		case 0:
			Logger.getRootLogger().setLevel(ReportLevel.REPORT);
			break;
		case 1:
			Logger.getRootLogger().setLevel(Level.INFO);
			break;
		case 2:
			Logger.getRootLogger().setLevel(Level.DEBUG);
			break;
		default:
			Logger.getRootLogger().setLevel(Level.TRACE);
			break;
		}
		log.debug("debugLevel = " + SimQPNConfiguration.getDebugLevel() + ";");

		// There are 3 possible scenarios (combinations of runMode and
		// analMethod):
		log.debug("Scenario set to: "
				+ simulatorSettings.attributeValue("scenario"));
		switch (Integer.parseInt(simulatorSettings.attributeValue("scenario",
				"-1"))) {
		// Scenario 1:
		case 1: {
			configuration.runMode = SimQPNConfiguration.NORMAL;
			configuration.setAnalMethod(SimQPNConfiguration.AnalysisMethod.BATCH_MEANS);
			log.debug("-- runMode = NORMAL");
			log.debug("-- analMethod = BATCH_MEANS");
			break;
		}
		// Scenario 2:
		case 2: {
			configuration.runMode = SimQPNConfiguration.NORMAL;
			configuration.setAnalMethod(SimQPNConfiguration.AnalysisMethod.REPL_DEL);
			log.debug("-- runMode = NORMAL");
			log.debug("-- analMethod = REPL_DEL");
			if (simulatorSettings.attributeValue("number-of-runs") == null) {
				log.error("\"number-of-runs\" parameter for Replication/Deletion Method not specified!");
				throw new SimQPNException();
			}
			configuration.setNumRuns(Integer.parseInt(simulatorSettings
					.attributeValue("number-of-runs")));
			log.debug("-- numRuns = " + configuration.getNumRuns());
			break;
		}
		// Scenario 3:
		case 3: {
			configuration.runMode = SimQPNConfiguration.INIT_TRANS;
			configuration.setAnalMethod(SimQPNConfiguration.AnalysisMethod.WELCH);
			log.debug("-- runMode = INIT_TRANS");
			log.debug("-- analMethod = WELCH");
			if (simulatorSettings.attributeValue("number-of-runs") == null) {
				log.error("\"number-of-runs\" parameter for Method of Welch not specified!");
				throw new SimQPNException();
			}
			configuration.setNumRuns(Integer.parseInt(simulatorSettings
					.attributeValue("number-of-runs")));
			log.debug("-- numRuns = " + configuration.getNumRuns());
			break;
		}
		default: {
			log.error("Invalid analysis method (scenario) specified!");
			throw new SimQPNException();
		}
		}
		;

		configuration.setStatsDir(simulatorSettings
				.attributeValue("output-directory"));
		log.debug("statsDir = " + configuration.getStatsDir());

		// END-CONFIG
		// ------------------------------------------------------------------------------------------------------
		return configuration;
	}

	/**
	 * Modifies configuration
	 * @param netXML
	 * @param configurationString
	 * @param configuration
	 * @throws SimQPNException
	 */
	public static void configureSimulatorSettings(
			Element netXML, String configurationString,
			SimQPNConfiguration configuration) throws SimQPNException {
		log.debug("/////////////////////////////////////////////");
		log.debug("// Misc settings");

		Element simulatorSettings = getSettings(netXML, configurationString);

		if ("RELPRC".equals(simulatorSettings.attributeValue("stopping-rule"))) {
			configuration.stoppingRule = SimQPNConfiguration.RELPRC;
			log.debug("stoppingRule = RELPRC;");
		} else if ("ABSPRC".equals(simulatorSettings
				.attributeValue("stopping-rule"))) {
			configuration.stoppingRule = SimQPNConfiguration.ABSPRC;
			log.debug("stoppingRule = ABSPRC;");
		} else if ("FIXEDLEN".equals(simulatorSettings
				.attributeValue("stopping-rule"))) {
			configuration.stoppingRule = SimQPNConfiguration.FIXEDLEN;
			log.debug("stoppingRule = FIXEDLEN;");
		} else {
			log.error(formatDetailMessage(
					"Configuration parameter \"stopping-rule\" not configured correctly!",
					"configuration", configurationString));
			throw new SimQPNException();
		}

		// Only for scenario 1 stopping-rule is set. For others it is set to
		// FIXEDLEN.
		if (Integer.parseInt(simulatorSettings.attributeValue("scenario")) != 1
				&& configuration.stoppingRule != SimQPNConfiguration.FIXEDLEN) {
			log.error(formatDetailMessage("Stopping rule \""
					+ simulatorSettings.attributeValue("stopping-rule")
					+ "\" is not supported for the chosen analysis method!",
					"configuration", configurationString));
			throw new SimQPNException();
		}

		if (simulatorSettings.attributeValue("total-run-length") == null) {
			log.error(formatDetailMessage(
					"Configuration parameter \"total-run-length\" is not configured!",
					"configuration", configurationString));
			throw new SimQPNException();
		}
		configuration.totRunLen = Double.parseDouble(simulatorSettings
				.attributeValue("total-run-length"));
		log.debug("totRunLen = " + configuration.totRunLen + ";");

		if (Integer.parseInt(simulatorSettings.attributeValue("scenario")) != 3) {
			if (simulatorSettings.attributeValue("ramp-up-length") == null) {
				log.error(formatDetailMessage(
						"Configuration parameter \"ramp-up-length\" is not configured!",
						"configuration", configurationString));
				throw new SimQPNException();
			}
			configuration.rampUpLen = Double.parseDouble(simulatorSettings
					.attributeValue("ramp-up-length"));
			log.debug("rampUpLen = " + configuration.rampUpLen + ";");
		} else { // Method of Welch
			configuration.rampUpLen = configuration.totRunLen; // Note: The
																// method of
																// Welch is
																// currently run
																// until
																// rampUpLen is
																// reached.
		}

		if (configuration.stoppingRule != SimQPNConfiguration.FIXEDLEN) {
			if (simulatorSettings.attributeValue("time-between-stop-checks") == null) {
				log.error(formatDetailMessage(
						"Configuration parameter \"time-between-stop-checks\" is not configured!",
						"configuration", configurationString));
				throw new SimQPNException();
			}
			configuration.timeBtwChkStops = Double
					.parseDouble(simulatorSettings
							.attributeValue("time-between-stop-checks"));
			log.debug("timeBtwChkStops = " + configuration.timeBtwChkStops
					+ ";");
			if (simulatorSettings.attributeValue("seconds-between-stop-checks") == null) {
				log.error(formatDetailMessage(
						"Configuration parameter \"seconds-between-stop-checks\" is not configured!",
						"configuration", configurationString));
				throw new SimQPNException();
			}
			configuration.secondsBtwChkStops = Double
					.parseDouble(simulatorSettings
							.attributeValue("seconds-between-stop-checks"));
			log.debug("secondsBtwChkStops = "
					+ configuration.secondsBtwChkStops + ";");
		}

		/*
		 * ORIGINAL HEARTBEAT IMPLEMENTATION
		 * if(simulatorSettings.attributeValue("time-before-initial-heart-beat")
		 * == null) { logln(
		 * "Error: Configuration parameter \"time-before-initial-heart-beat\" is not configured!"
		 * ); logln("  configuration = " + configuration); throw new
		 * SimQPNException(); } timeInitHeartBeat =
		 * Double.parseDouble(simulatorSettings
		 * .attributeValue("time-before-initial-heart-beat")); logln(2,
		 * "timeInitHeartBeat = " + timeInitHeartBeat + ";");
		 * 
		 * if(simulatorSettings.attributeValue("seconds-between-heart-beats") ==
		 * null) { logln(
		 * "Error: Configuration parameter \"seconds-between-heart-beats\" is not configured!"
		 * ); logln("  configuration = " + configuration); throw new
		 * SimQPNException(); } secsBtwHeartBeats =
		 * Double.parseDouble(simulatorSettings
		 * .attributeValue("seconds-between-heart-beats")); logln(2,
		 * "secsBtwHeartBeats = " + secsBtwHeartBeats + ";");
		 */

		if (configuration.getAnalMethod() != SimQPNConfiguration.AnalysisMethod.BATCH_MEANS
				&& configuration.stoppingRule != SimQPNConfiguration.FIXEDLEN) {
			log.error(formatDetailMessage(
					"Stopping rule \""
							+ simulatorSettings.attributeValue("stopping-rule")
							+ "\" is not supported for the batch means analysis method!",
					"configuration", configurationString));
			throw new SimQPNException();
		}
	}

	public static Element getSettings(Element element, String configurationName) {
		XPath xpathSelector = XMLHelper
				.createXPath("meta-attributes/meta-attribute[starts-with(@xsi:type, 'simqpn') and @configuration-name='"
						+ configurationName + "']");
		Element elementSettings = (Element) xpathSelector
				.selectSingleNode(element);
		return elementSettings;
	}

}
