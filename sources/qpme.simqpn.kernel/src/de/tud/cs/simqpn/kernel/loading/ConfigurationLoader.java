package de.tud.cs.simqpn.kernel.loading;

import static de.tud.cs.simqpn.kernel.util.LogUtil.formatDetailMessage;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.dom4j.Element;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNConfiguration.AnalysisMethod;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.util.LogUtil.ReportLevel;

public class ConfigurationLoader {
	private static Logger log = Logger.getLogger(ConfigurationLoader.class);

	public static SimQPNConfiguration loadConfiguration(Element XMLDescription,
			String configurationName)
			throws SimQPNException {
		/*
		 * Global run configuration parameters:
		 * 
		 * 1. "Analysis Method" (analMethod) == BATCH_MEANS, REPL_DEL or WELCH
		 * 
		 * 2."Maximum Number of Runs" (numRuns)
		 *  numRuns should only be available if analMethod is REPL_DEL or WELCH.
		 * 
		 * 3. "Output Directory" (statsDir) 
		 * 
		 * IMPORTANT: runMode is implied from the chosen analysis method: - If
		 * the user chooses BATCH_MEANS or REPL_DEL, runMode is set to NORMAL. -
		 * If the user chooses WELCH, runMode is set to INIT_TRANS.
		 * 
		 * Depending on the selected analysis method different parameters must
		 * be configured - see methods getReady and runWelchMtd for detailed
		 * information.
		 */
		SimQPNConfiguration configuration = new SimQPNConfiguration();

		Element simulatorSettings = XMLHelper.getSettings(
				XMLDescription, configurationName);

		loadDebugLevel(configurationName, simulatorSettings);
		loadAnalysisMethod(configuration, simulatorSettings);

		configuration.setStatsDir(simulatorSettings
				.attributeValue("output-directory"));
		log.debug("statsDir = " + configuration.getStatsDir());

		log.debug("/////////////////////////////////////////////");
		log.debug("// Misc settings");

		loadStoppingRule(configurationName, configuration, simulatorSettings);
		loadRunLength(configurationName, configuration, simulatorSettings);
		loadRampUpLength(configurationName, configuration, simulatorSettings);
		loadTimeBtwStopChecks(configurationName, configuration,
				simulatorSettings);
		loadIsParallel(configurationName, configuration,
				simulatorSettings);

		if (configuration.getAnalMethod() != SimQPNConfiguration.AnalysisMethod.BATCH_MEANS
				&& configuration.stoppingRule != SimQPNConfiguration.FIXEDLEN) {
			log.error(formatDetailMessage(
					"Stopping rule \""
							+ simulatorSettings.attributeValue("stopping-rule")
							+ "\" is not supported for the batch means analysis method!",
					"configuration", configurationName));
			throw new SimQPNException();
		}

		// END-CONFIG
		// ------------------------------------------------------------------------------------------------------
		return configuration;
	}

	private static void loadIsParallel(String configurationName,
			SimQPNConfiguration configuration, Element simulatorSettings) {
		if (simulatorSettings.attributeValue("is-parallel") == null) {
			// take default values
			if (configuration.getAnalMethod() == AnalysisMethod.REPL_DEL) {
				configuration.setParallel(true);
			} else {
				configuration.setParallel(false);
			}
		} else {
			configuration.setParallel(Boolean.parseBoolean(simulatorSettings
					.attributeValue("is-parallel")));
		}
	}

	private static void loadDebugLevel(String configurationName,
			Element simulatorSettings) throws SimQPNException {
		if (simulatorSettings.attributeValue("verbosity-level") == null) {
			log.error(formatDetailMessage(
					"Configuration parameter \"verbosity-level\" is not configured!",
					"configuration", configurationName));
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
	}

	private static void loadAnalysisMethod(SimQPNConfiguration configuration,
			Element simulatorSettings) throws SimQPNException {
		// There are 3 possible scenarios (combinations of runMode and
		// analMethod):
		log.debug("Scenario set to: "
				+ simulatorSettings.attributeValue("scenario"));
		switch (Integer.parseInt(simulatorSettings.attributeValue("scenario",
				"-1"))) {
		// Scenario 1:
		case 1: {
			configuration.runMode = SimQPNConfiguration.NORMAL;
			configuration
					.setAnalMethod(SimQPNConfiguration.AnalysisMethod.BATCH_MEANS);
			log.debug("-- runMode = NORMAL");
			log.debug("-- analMethod = BATCH_MEANS");
			break;
		}
		// Scenario 2:
		case 2: {
			configuration.runMode = SimQPNConfiguration.NORMAL;
			configuration
					.setAnalMethod(SimQPNConfiguration.AnalysisMethod.REPL_DEL);
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
			configuration
					.setAnalMethod(SimQPNConfiguration.AnalysisMethod.WELCH);
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
	}



	private static void loadTimeBtwStopChecks(String configurationName,
			SimQPNConfiguration configuration, Element simulatorSettings)
			throws SimQPNException {
		if (configuration.stoppingRule != SimQPNConfiguration.FIXEDLEN) {
			if (simulatorSettings.attributeValue("time-between-stop-checks") == null) {
				log.error(formatDetailMessage(
						"Configuration parameter \"time-between-stop-checks\" is not configured!",
						"configuration", configurationName));
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
						"configuration", configurationName));
				throw new SimQPNException();
			}
			configuration.secondsBtwChkStops = Double
					.parseDouble(simulatorSettings
							.attributeValue("seconds-between-stop-checks"));
			log.debug("secondsBtwChkStops = "
					+ configuration.secondsBtwChkStops + ";");
		}
	}

	private static void loadRampUpLength(String configurationName,
			SimQPNConfiguration configuration, Element simulatorSettings)
			throws SimQPNException {
		if (Integer.parseInt(simulatorSettings.attributeValue("scenario")) != 3) {
			if (simulatorSettings.attributeValue("ramp-up-length") == null) {
				log.error(formatDetailMessage(
						"Configuration parameter \"ramp-up-length\" is not configured!",
						"configuration", configurationName));
				throw new SimQPNException();
			}
			configuration.rampUpLen = Double.parseDouble(simulatorSettings
					.attributeValue("ramp-up-length"));
			log.debug("rampUpLen = " + configuration.rampUpLen + ";");
		} else { // Method of Welch
			/*
			 * Note: The method of Welch is currently run until rampUpLen is
			 * reached.
			 */
			configuration.rampUpLen = configuration.totRunLength;
		}
	}

	private static void loadRunLength(String configurationName,
			SimQPNConfiguration configuration, Element simulatorSettings)
			throws SimQPNException {
		if (simulatorSettings.attributeValue("total-run-length") == null) {
			log.error(formatDetailMessage(
					"Configuration parameter \"total-run-length\" is not configured!",
					"configuration", configurationName));
			throw new SimQPNException();
		}
		configuration.totRunLength = Double.parseDouble(simulatorSettings
				.attributeValue("total-run-length"));
		log.debug("totRunLen = " + configuration.totRunLength + ";");
	}

	private static void loadStoppingRule(String configurationName,
			SimQPNConfiguration configuration, Element simulatorSettings)
			throws SimQPNException {
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
					"configuration", configurationName));
			throw new SimQPNException();
		}

		// Only for scenario 1 stopping-rule is set. For others it is set to
		// FIXEDLEN.
		if (Integer.parseInt(simulatorSettings.attributeValue("scenario")) != 1
				&& configuration.stoppingRule != SimQPNConfiguration.FIXEDLEN) {
			log.error(formatDetailMessage("Stopping rule \""
					+ simulatorSettings.attributeValue("stopping-rule")
					+ "\" is not supported for the chosen analysis method!",
					"configuration", configurationName));
			throw new SimQPNException();
		}
	}

}
