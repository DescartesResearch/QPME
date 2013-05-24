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

	public static SimQPNConfiguration configureSimulatorSettings(
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
		return configuration;
	}

	/**
	 * Configures PlaceStats
	 * 
	 * @throws SimQPNException
	 */
	public static SimQPNConfiguration configureBatchMeansMethod(Element netXML,
			SimQPNController sim) throws SimQPNException {
		/*
		 * "Advanced Configuration Options" only applicable to the BATCH_MEANS
		 * method
		 * 
		 * These options are configurable for each place/queue/depository that
		 * has statsLevel >= 3. Options should be displayed only if BATCH_MEANS
		 * is chosen and in this case only for places that have statsLevel set
		 * to be >= 3.
		 * 
		 * double signLevST - Required significance level. double reqAbsPrc -
		 * Used if stoppingRule=ABSPRC: Required absolute precision (max c.i.
		 * half length). double reqRelPrc - Used if stoppingRule=RELPRC:
		 * Required relative precision (max ratio of c.i. half length to mean).
		 * int batchSizeST - Batch size for the batch means algorithm. int
		 * minBatches - Minimum number of batches required for steady state
		 * statistics. If minBatches[c] <= 0, no steady state statistics are
		 * collected for this color! int numBMeansCorlTested - If > 0 checks
		 * whether the batch size is sufficient - the first numBMeansCorlTested
		 * batch means from the beginning of steady state are tested for
		 * autocorrelation.
		 * 
		 * NOTE: reqAbsPrc should be available only if stoppingRule=ABSPRC;
		 * reqRelPrc should be available only if stoppingRule=RELPRC.
		 * 
		 * Check and make sure that numBMeansCorlTested is even!
		 */

		// CONFIG: BATCH_MEANS Method Initialization Parameters
		if (sim.getConfiguration().getAnalMethod() == SimQPNConfiguration.AnalysisMethod.BATCH_MEANS) {
			XPath xpathSelector = XMLHelper.createXPath("//place");
			List<Element> placeList = xpathSelector.selectNodes(netXML);
			xpathSelector = XMLHelper.createXPath("//probe");
			List<Element> probeList = xpathSelector.selectNodes(netXML);

			Iterator<Element> placeIterator;
			placeIterator = placeList.iterator();
			for (int p = 0; placeIterator.hasNext(); p++) {
				Element place = placeIterator.next();
				Place pl = sim.getNet().getPlace(p);
				/**
				 * for (int p = 0; p<sim.getNet().getNumPlaces(); p++) { Place
				 * pl = sim.getNet().getPlace(p);
				 */
				if (pl.statsLevel >= 3) {
					log.debug("places[" + p + "]");
					xpathSelector = XMLHelper
							.createXPath("color-refs/color-ref");
					List<Element> colorList = xpathSelector.selectNodes(place);
					Iterator<Element> colorRefIterator = colorList.iterator();
					for (int cr = 0; colorRefIterator.hasNext(); cr++) {
						Element colorRef = colorRefIterator.next();
						Element colorRefSettings = getSettings(colorRef, sim
								.getNet().getConfiguration());
						// Initialize Place (or Depository if pl is QPlace)
						if (colorRefSettings != null) {
							if (colorRefSettings.attributeValue("signLev") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"signLev\" for Batch Means Method is missing!",
										"configuration", sim.getNet()
												.getConfiguration(),
										"place-num", Integer.toString(p),
										"place.id", place.attributeValue("id"),
										"place.name", place
												.attributeValue("name"),
										"colorRef-num", Integer.toString(cr),
										"colorRef.id", colorRef
												.attributeValue("id"),
										"colorRef.color-id", colorRef
												.attributeValue("color-id")));
								throw new SimQPNException();
							}
							pl.placeStats.signLevST[cr] = Double
									.parseDouble(colorRefSettings
											.attributeValue("signLev"));
							log.debug("-- placeStats.signLevST[" + cr + "] = "
									+ pl.placeStats.signLevST[cr]);

							if (colorRefSettings.attributeValue("reqAbsPrc") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"reqAbsPrc\" for Batch Means Method is missing!",
										"configuration", sim.getNet()
												.getConfiguration(),
										"place-num", Integer.toString(p),
										"place.id", place.attributeValue("id"),
										"place.name", place
												.attributeValue("name"),
										"colorRef-num", Integer.toString(cr),
										"colorRef.id", colorRef
												.attributeValue("id"),
										"colorRef.color-id", colorRef
												.attributeValue("color-id")));
								throw new SimQPNException();
							}
							pl.placeStats.reqAbsPrc[cr] = Double
									.parseDouble(colorRefSettings
											.attributeValue("reqAbsPrc"));
							log.debug("-- placeStats.reqAbsPrc[" + cr + "] = "
									+ pl.placeStats.reqAbsPrc[cr]);

							if (colorRefSettings.attributeValue("reqRelPrc") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"reqRelPrc\" for Batch Means Method is missing!",
										"configuration", sim.getNet()
												.getConfiguration(),
										"place-num", Integer.toString(p),
										"place.id", place.attributeValue("id"),
										"place.name", place
												.attributeValue("name"),
										"colorRef-num", Integer.toString(cr),
										"colorRef.id", colorRef
												.attributeValue("id"),
										"colorRef.color-id", colorRef
												.attributeValue("color-id")));
								throw new SimQPNException();
							}
							pl.placeStats.reqRelPrc[cr] = Double
									.parseDouble(colorRefSettings
											.attributeValue("reqRelPrc"));
							log.debug("-- placeStats.reqRelPrc[" + cr + "] = "
									+ pl.placeStats.reqRelPrc[cr]);

							if (colorRefSettings.attributeValue("batchSize") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"batchSize\" for Batch Means Method is missing!",
										"configuration", sim.getNet()
												.getConfiguration(),
										"place-num", Integer.toString(p),
										"place.id", place.attributeValue("id"),
										"place.name", place
												.attributeValue("name"),
										"colorRef-num", Integer.toString(cr),
										"colorRef.id", colorRef
												.attributeValue("id"),
										"colorRef.color-id", colorRef
												.attributeValue("color-id")));
								throw new SimQPNException();
							}
							pl.placeStats.batchSizeST[cr] = Integer
									.parseInt(colorRefSettings
											.attributeValue("batchSize"));
							log.debug("-- placeStats.batchSizeST[" + cr
									+ "] = " + pl.placeStats.batchSizeST[cr]);

							if (colorRefSettings.attributeValue("minBatches") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"minBatches\" for Batch Means Method is missing!",
										"configuration", sim.getNet()
												.getConfiguration(),
										"place-num", Integer.toString(p),
										"place.id", place.attributeValue("id"),
										"place.name", place
												.attributeValue("name"),
										"colorRef-num", Integer.toString(cr),
										"colorRef.id", colorRef
												.attributeValue("id"),
										"colorRef.color-id", colorRef
												.attributeValue("color-id")));
								throw new SimQPNException();
							}
							pl.placeStats.minBatches[cr] = Integer
									.parseInt(colorRefSettings
											.attributeValue("minBatches"));
							log.debug("-- placeStats.minBatches[" + cr + "] = "
									+ pl.placeStats.minBatches[cr]);

							if (colorRefSettings
									.attributeValue("numBMeansCorlTested") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"numBMeansCorlTested\" for Batch Means Method is missing!",
										"configuration", sim.getNet()
												.getConfiguration(),
										"place-num", Integer.toString(p),
										"place.id", place.attributeValue("id"),
										"place.name", place
												.attributeValue("name"),
										"colorRef-num", Integer.toString(cr),
										"colorRef.id", colorRef
												.attributeValue("id"),
										"colorRef.color-id", colorRef
												.attributeValue("color-id")));
								throw new SimQPNException();
							}
							pl.placeStats.numBMeansCorlTested[cr] = Integer
									.parseInt(colorRefSettings
											.attributeValue("numBMeansCorlTested"));
							log.debug("-- placeStats.numBMeansCorlTested[" + cr
									+ "] = "
									+ pl.placeStats.numBMeansCorlTested[cr]);

							// Note that if (minBatches > 0 &&
							// numBMeansCorlTested[c] > 0),
							// minBatches[c] must be > numBMeansCorlTested[c]!
							if (pl.placeStats.minBatches[cr] > 0
									&& pl.placeStats.numBMeansCorlTested[cr] > 0
									&& !(pl.placeStats.minBatches[cr] > pl.placeStats.numBMeansCorlTested[cr])) {
								log.error(formatDetailMessage(
										"Place.placeStats.minBatches[c] must be greater than Place.placeStats.numBMeansCorlTested[c]!",
										"configuration", sim.getNet()
												.getConfiguration(),
										"place-num", Integer.toString(p),
										"place.id", place.attributeValue("id"),
										"place.name", place
												.attributeValue("name"),
										"colorRef-num", Integer.toString(cr),
										"colorRef.id", colorRef
												.attributeValue("id"),
										"colorRef.color-id", colorRef
												.attributeValue("color-id")));
								throw new SimQPNException();
							}
							// If (numBMeansCorlTested[c] <= 0) no correlation
							// test is done!
							// Note that numBMeansCorlTested must be even!
							if (pl.placeStats.numBMeansCorlTested[cr] % 2 != 0) {
								log.error(formatDetailMessage(
										"Place.placeStats.numBMeansCorlTested[c] must be even!",
										"configuration", sim.getNet()
												.getConfiguration(),
										"place-num", Integer.toString(p),
										"place.id", place.attributeValue("id"),
										"place.name", place
												.attributeValue("name"),
										"colorRef-num", Integer.toString(cr),
										"colorRef.id", colorRef
												.attributeValue("id"),
										"colorRef.color-id", colorRef
												.attributeValue("color-id")));
								throw new SimQPNException();
							}

							if (pl.statsLevel >= 4) {
								if (colorRefSettings
										.attributeValue("bucketSize") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"bucketSize\" for Batch Means Method is missing!",
											"configuration", sim.getNet()
													.getConfiguration(),
											"place-num", Integer.toString(p),
											"place.id", place
													.attributeValue("id"),
											"place.name", place
													.attributeValue("name"),
											"colorRef-num", Integer
													.toString(cr),
											"colorRef.id", colorRef
													.attributeValue("id"),
											"colorRef.color-id", colorRef
													.attributeValue("color-id")));
									throw new SimQPNException();
								}
								pl.placeStats.histST[cr].setBucketSize(Double
										.parseDouble(colorRefSettings
												.attributeValue("bucketSize")));
								log.debug("-- placeStats.histST["
										+ cr
										+ "].bucketSize = "
										+ pl.placeStats.histST[cr]
												.getBucketSize());

								if (colorRefSettings
										.attributeValue("maxBuckets") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"maxBuckets\" for Batch Means Method is missing!",
											"configuration", sim.getNet()
													.getConfiguration(),
											"place-num", Integer.toString(p),
											"place.id", place
													.attributeValue("id"),
											"place.name", place
													.attributeValue("name"),
											"colorRef-num", Integer
													.toString(cr),
											"colorRef.id", colorRef
													.attributeValue("id"),
											"colorRef.color-id", colorRef
													.attributeValue("color-id")));
									throw new SimQPNException();
								}
								pl.placeStats.histST[cr]
										.setMaximumNumberOfBuckets(Integer.parseInt(colorRefSettings
												.attributeValue("maxBuckets")));
								log.debug("-- placeStats.histST["
										+ cr
										+ "].maxBuckets = "
										+ pl.placeStats.histST[cr]
												.getMaximumNumberOfBuckets());
							}
						} else {
							log.error(formatDetailMessage(
									"SimQPN configuration parameters for Batch Means Method are missing!",
									"configuration", sim.getNet()
											.getConfiguration(), "place-num",
									Integer.toString(p), "place.id", place
											.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(cr),
									"colorRef.id", colorRef
											.attributeValue("id"),
									"colorRef.color-id", colorRef
											.attributeValue("color-id")));
							throw new SimQPNException();
							/*
							 * ORIGINAL MANUAL CONFIGURATION
							 * pl.placeStats.signLevST[cr] = 0.05; // e.g. 0.05
							 * ---> 95% c.i.; 0.1 ---> 90% logln(2,
							 * "-- placeStats.signLevST[" + cr + "] = " +
							 * pl.placeStats.signLevST[cr]);
							 * pl.placeStats.reqAbsPrc[cr] = 50; logln(2,
							 * "-- placeStats.reqAbsPrc[" + cr + "] = " +
							 * pl.placeStats.reqAbsPrc[cr]);
							 * pl.placeStats.reqRelPrc[cr] = 0.05; // e.g. 0.1
							 * ---> 10% relative precision logln(2,
							 * "-- placeStats.reqRelPrc[" + cr + "] = " +
							 * pl.placeStats.reqRelPrc[cr]);
							 * pl.placeStats.batchSizeST[cr] = 200; // Initial
							 * batch size logln(2, "-- placeStats.batchSizeST["
							 * + cr + "] = " + pl.placeStats.batchSizeST[cr]);
							 * pl.placeStats.minBatches[cr] = 60; // Note that
							 * if (minBatches > 0 && numBMeansCorlTested[c] >
							 * 0), // minBatches[c] must be >
							 * numBMeansCorlTested[c]! logln(2,
							 * "-- placeStats.minBatches[" + cr + "] = " +
							 * pl.placeStats.minBatches[cr]);
							 * pl.placeStats.numBMeansCorlTested[cr] = 50; //
							 * Note that numBMeansCorlTested must be even! // If
							 * (numBMeansCorlTested[c] <= 0) no correlation test
							 * is done! logln(2,
							 * "-- placeStats.numBMeansCorlTested[" + cr +
							 * "] = " + pl.placeStats.numBMeansCorlTested[cr]);
							 */
						}

						// Initialize Queue if pl is QPlace
						if (pl instanceof QPlace) {
							QPlace qpl = (QPlace) pl;
							if (colorRefSettings != null) {
								if (colorRefSettings
										.attributeValue("queueSignLev") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"queueSignLev\" for Batch Means Method is missing!",
											"configuration", sim.getNet()
													.getConfiguration(),
											"place-num", Integer.toString(p),
											"place.id", place
													.attributeValue("id"),
											"place.name", place
													.attributeValue("name"),
											"colorRef-num", Integer
													.toString(cr),
											"colorRef.id", colorRef
													.attributeValue("id"),
											"colorRef.color-id", colorRef
													.attributeValue("color-id")));
									throw new SimQPNException();
								}
								qpl.qPlaceQueueStats.signLevST[cr] = Double
										.parseDouble(colorRefSettings
												.attributeValue("queueSignLev"));
								log.debug("-- qPlaceQueueStats.signLevST[" + cr
										+ "] = "
										+ qpl.qPlaceQueueStats.signLevST[cr]);

								if (colorRefSettings
										.attributeValue("queueReqAbsPrc") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"queueReqAbsPrc\" for Batch Means Method is missing!",
											"configuration", sim.getNet()
													.getConfiguration(),
											"place-num", Integer.toString(p),
											"place.id", place
													.attributeValue("id"),
											"place.name", place
													.attributeValue("name"),
											"colorRef-num", Integer
													.toString(cr),
											"colorRef.id", colorRef
													.attributeValue("id"),
											"colorRef.color-id", colorRef
													.attributeValue("color-id")));
									throw new SimQPNException();
								}
								qpl.qPlaceQueueStats.reqAbsPrc[cr] = Double
										.parseDouble(colorRefSettings
												.attributeValue("queueReqAbsPrc"));
								log.debug("-- qPlaceQueueStats.reqAbsPrc[" + cr
										+ "] = "
										+ qpl.qPlaceQueueStats.reqAbsPrc[cr]);

								if (colorRefSettings
										.attributeValue("queueReqRelPrc") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"queueReqRelPrc\" for Batch Means Method is missing!",
											"configuration", sim.getNet()
													.getConfiguration(),
											"place-num", Integer.toString(p),
											"place.id", place
													.attributeValue("id"),
											"place.name", place
													.attributeValue("name"),
											"colorRef-num", Integer
													.toString(cr),
											"colorRef.id", colorRef
													.attributeValue("id"),
											"colorRef.color-id", colorRef
													.attributeValue("color-id")));
									throw new SimQPNException();
								}
								qpl.qPlaceQueueStats.reqRelPrc[cr] = Double
										.parseDouble(colorRefSettings
												.attributeValue("queueReqRelPrc"));
								log.debug("-- qPlaceQueueStats.reqRelPrc[" + cr
										+ "] = "
										+ qpl.qPlaceQueueStats.reqRelPrc[cr]);

								if (colorRefSettings
										.attributeValue("queueBatchSize") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"queueBatchSize\" for Batch Means Method is missing!",
											"configuration", sim.getNet()
													.getConfiguration(),
											"place-num", Integer.toString(p),
											"place.id", place
													.attributeValue("id"),
											"place.name", place
													.attributeValue("name"),
											"colorRef-num", Integer
													.toString(cr),
											"colorRef.id", colorRef
													.attributeValue("id"),
											"colorRef.color-id", colorRef
													.attributeValue("color-id")));
									throw new SimQPNException();
								}
								qpl.qPlaceQueueStats.batchSizeST[cr] = Integer
										.parseInt(colorRefSettings
												.attributeValue("queueBatchSize"));
								log.debug("-- qPlaceQueueStats.batchSizeST["
										+ cr + "] = "
										+ qpl.qPlaceQueueStats.batchSizeST[cr]);

								if (colorRefSettings
										.attributeValue("queueMinBatches") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"queueMinBatches\" for Batch Means Method is missing!",
											"configuration", sim.getNet()
													.getConfiguration(),
											"place-num", Integer.toString(p),
											"place.id", place
													.attributeValue("id"),
											"place.name", place
													.attributeValue("name"),
											"colorRef-num", Integer
													.toString(cr),
											"colorRef.id", colorRef
													.attributeValue("id"),
											"colorRef.color-id", colorRef
													.attributeValue("color-id")));
									throw new SimQPNException();
								}
								qpl.qPlaceQueueStats.minBatches[cr] = Integer
										.parseInt(colorRefSettings
												.attributeValue("queueMinBatches"));
								log.debug("-- qPlaceQueueStats.minBatches["
										+ cr + "] = "
										+ qpl.qPlaceQueueStats.minBatches[cr]);

								if (colorRefSettings
										.attributeValue("queueNumBMeansCorlTested") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"queueNumBMeansCorlTested\" for Batch Means Method is missing!",
											"configuration", sim.getNet()
													.getConfiguration(),
											"place-num", Integer.toString(p),
											"place.id", place
													.attributeValue("id"),
											"place.name", place
													.attributeValue("name"),
											"colorRef-num", Integer
													.toString(cr),
											"colorRef.id", colorRef
													.attributeValue("id"),
											"colorRef.color-id", colorRef
													.attributeValue("color-id")));
									throw new SimQPNException();
								}
								qpl.qPlaceQueueStats.numBMeansCorlTested[cr] = Integer
										.parseInt(colorRefSettings
												.attributeValue("queueNumBMeansCorlTested"));
								log.debug("-- qPlaceQueueStats.numBMeansCorlTested["
										+ cr
										+ "] = "
										+ qpl.qPlaceQueueStats.numBMeansCorlTested[cr]);

								if (qpl.qPlaceQueueStats.minBatches[cr] > 0
										&& qpl.qPlaceQueueStats.numBMeansCorlTested[cr] > 0
										&& !(qpl.qPlaceQueueStats.minBatches[cr] > qpl.qPlaceQueueStats.numBMeansCorlTested[cr])) {
									log.error(formatDetailMessage(
											"QPlace.qPlaceQueueStats.minBatches[c] must be greater than QPlace.qPlaceQueueStats.numBMeansCorlTested[c]!",
											"configuration", sim.getNet()
													.getConfiguration(),
											"place-num", Integer.toString(p),
											"place.id", place
													.attributeValue("id"),
											"place.name", place
													.attributeValue("name"),
											"colorRef-num", Integer
													.toString(cr),
											"colorRef.id", colorRef
													.attributeValue("id"),
											"colorRef.color-id", colorRef
													.attributeValue("color-id")));
									throw new SimQPNException();
								}
								// If (numBMeansCorlTested[c] <= 0) no
								// correlation test is done!
								// Note that numBMeansCorlTested must be even!
								if (qpl.qPlaceQueueStats.numBMeansCorlTested[cr] % 2 != 0) {
									log.error(formatDetailMessage(
											"QPlace.qPlaceQueueStats.numBMeansCorlTested[c] must be even!",
											"configuration", sim.getNet()
													.getConfiguration(),
											"place-num", Integer.toString(p),
											"place.id", place
													.attributeValue("id"),
											"place.name", place
													.attributeValue("name"),
											"colorRef-num", Integer
													.toString(cr),
											"colorRef.id", colorRef
													.attributeValue("id"),
											"colorRef.color-id", colorRef
													.attributeValue("color-id")));
									throw new SimQPNException();
								}

								if (pl.statsLevel >= 4) {
									if (colorRefSettings
											.attributeValue("queueBucketSize") == null) {
										log.error(formatDetailMessage(
												"Configuration parameter \"queueBucketSize\" for Batch Means Method is missing!",
												"configuration",
												sim.getNet().getConfiguration(),
												"place-num",
												Integer.toString(p),
												"place.id",
												place.attributeValue("id"),
												"place.name",
												place.attributeValue("name"),
												"colorRef-num",
												Integer.toString(cr),
												"colorRef.id",
												colorRef.attributeValue("id"),
												"colorRef.color-id",
												colorRef.attributeValue("color-id")));
										throw new SimQPNException();
									}
									qpl.qPlaceQueueStats.histST[cr]
											.setBucketSize(Double
													.parseDouble(colorRefSettings
															.attributeValue("queueBucketSize")));
									log.debug("-- qPlaceQueueStats.histST["
											+ cr
											+ "].bucketSize = "
											+ qpl.qPlaceQueueStats.histST[cr]
													.getBucketSize());

									if (colorRefSettings
											.attributeValue("queueMaxBuckets") == null) {
										log.error(formatDetailMessage(
												"Configuration parameter \"queueMaxBuckets\" for Batch Means Method is missing!",
												"configuration",
												sim.getNet().getConfiguration(),
												"place-num",
												Integer.toString(p),
												"place.id",
												place.attributeValue("id"),
												"place.name",
												place.attributeValue("name"),
												"colorRef-num",
												Integer.toString(cr),
												"colorRef.id",
												colorRef.attributeValue("id"),
												"colorRef.color-id",
												colorRef.attributeValue("color-id")));
										throw new SimQPNException();
									}
									qpl.qPlaceQueueStats.histST[cr]
											.setMaximumNumberOfBuckets(Integer.parseInt(colorRefSettings
													.attributeValue("queueMaxBuckets")));
									log.debug("-- qPlaceQueueStats.histST["
											+ cr
											+ "].maxBuckets = "
											+ qpl.qPlaceQueueStats.histST[cr]
													.getMaximumNumberOfBuckets());
								}
							} else {
								log.error(formatDetailMessage(
										"SimQPN configuration parameters for Batch Means Method are missing!",
										"configuration", sim.getNet()
												.getConfiguration(),
										"place-num", Integer.toString(p),
										"place.id", place.attributeValue("id"),
										"place.name", place
												.attributeValue("name"),
										"colorRef-num", Integer.toString(cr),
										"colorRef.id", colorRef
												.attributeValue("id"),
										"colorRef.color-id", colorRef
												.attributeValue("color-id")));
								throw new SimQPNException();
								/*
								 * ORIGINAL MANUAL CONFIGURATION
								 * qpl.qPlaceQueueStats.signLevST[cr] = 0.05; //
								 * e.g. 0.05 ---> 95% c.i.; 0.1 ---> 90%
								 * logln(2, "-- qPlaceQueueStats.signLevST[" +
								 * cr + "] = " +
								 * qpl.qPlaceQueueStats.signLevST[cr]);
								 * qpl.qPlaceQueueStats.reqAbsPrc[cr] = 50;
								 * logln(2, "-- qPlaceQueueStats.reqAbsPrc[" +
								 * cr + "] = " +
								 * qpl.qPlaceQueueStats.reqAbsPrc[cr]);
								 * qpl.qPlaceQueueStats.reqRelPrc[cr] = 0.05; //
								 * e.g. 0.1 ---> 10% relative precision logln(2,
								 * "-- qPlaceQueueStats.reqRelPrc[" + cr +
								 * "] = " + qpl.qPlaceQueueStats.reqRelPrc[cr]);
								 * qpl.qPlaceQueueStats.batchSizeST[cr] = 200;
								 * // Initial batch size logln(2,
								 * "-- qPlaceQueueStats.batchSizeST[" + cr +
								 * "] = " +
								 * qpl.qPlaceQueueStats.batchSizeST[cr]);
								 * qpl.qPlaceQueueStats.minBatches[cr] = 60; //
								 * Note that if (minBatches > 0 &&
								 * numBMeansCorlTested[c] > 0), // minBatches[c]
								 * must be > numBMeansCorlTested[c]! logln(2,
								 * "-- qPlaceQueueStats.minBatches[" + cr +
								 * "] = " +
								 * qpl.qPlaceQueueStats.minBatches[cr]);
								 * qpl.qPlaceQueueStats.numBMeansCorlTested[cr]
								 * = 50; // Note that numBMeansCorlTested must
								 * be even! // If (numBMeansCorlTested[c] <= 0)
								 * no correlation test is done! logln(2,
								 * "-- qPlaceQueueStats.numBMeansCorlTested[" +
								 * cr + "] = " +
								 * qpl.qPlaceQueueStats.numBMeansCorlTested
								 * [cr]);
								 */
							}
						}
					}
				}
			}

			// Configure the probes statistic collection
			Iterator<Element> probeIterator = probeList.iterator();
			for (int p = 0; probeIterator.hasNext(); p++) {
				Element probe = probeIterator.next();
				Probe pr = sim.getNet().getProbe(p);
				if (pr.statsLevel >= 3) {
					log.debug("probes[" + p + "]");
					xpathSelector = XMLHelper
							.createXPath("color-refs/color-ref");
					Iterator<Element> colorRefIterator = xpathSelector
							.selectNodes(probe).iterator();
					for (int cr = 0; colorRefIterator.hasNext(); cr++) {
						Element colorRef = colorRefIterator.next();
						Element colorRefSettings = getSettings(colorRef, sim
								.getNet().getConfiguration());
						// Initialize Probe
						if (colorRefSettings != null) {
							if (colorRefSettings.attributeValue("signLev") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"signLev\" for Batch Means Method is missing!",
										"configuration"
												+ sim.getNet()
														.getConfiguration(),
										"probe-num" + Integer.toString(p),
										"probe.id" + probe.attributeValue("id"),
										"probe.name"
												+ probe.attributeValue("name"),
										"colorRef-num" + Integer.toString(cr),
										"colorRef.id"
												+ colorRef.attributeValue("id"),
										"colorRef.color-id"
												+ colorRef
														.attributeValue("color-id")));
								throw new SimQPNException();
							}
							pr.probeStats.signLevST[cr] = Double
									.parseDouble(colorRefSettings
											.attributeValue("signLev"));
							log.debug("-- probeStats.signLevST[" + cr + "] = "
									+ pr.probeStats.signLevST[cr]);

							if (colorRefSettings.attributeValue("reqAbsPrc") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"reqAbsPrc\" for Batch Means Method is missing!",
										"configuration"
												+ sim.getNet()
														.getConfiguration(),
										"probe-num" + Integer.toString(p),
										"probe.id" + probe.attributeValue("id"),
										"probe.name"
												+ probe.attributeValue("name"),
										"colorRef-num" + Integer.toString(cr),
										"colorRef.id"
												+ colorRef.attributeValue("id"),
										"colorRef.color-id"
												+ colorRef
														.attributeValue("color-id")));
								throw new SimQPNException();
							}
							pr.probeStats.reqAbsPrc[cr] = Double
									.parseDouble(colorRefSettings
											.attributeValue("reqAbsPrc"));
							log.debug("-- probeStats.reqAbsPrc[" + cr + "] = "
									+ pr.probeStats.reqAbsPrc[cr]);

							if (colorRefSettings.attributeValue("reqRelPrc") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"reqRelPrc\" for Batch Means Method is missing!",
										"configuration"
												+ sim.getNet()
														.getConfiguration(),
										"probe-num" + Integer.toString(p),
										"probe.id" + probe.attributeValue("id"),
										"probe.name"
												+ probe.attributeValue("name"),
										"colorRef-num" + Integer.toString(cr),
										"colorRef.id"
												+ colorRef.attributeValue("id"),
										"colorRef.color-id"
												+ colorRef
														.attributeValue("color-id")));
								throw new SimQPNException();
							}
							pr.probeStats.reqRelPrc[cr] = Double
									.parseDouble(colorRefSettings
											.attributeValue("reqRelPrc"));
							log.debug("-- probeStats.reqRelPrc[" + cr + "] = "
									+ pr.probeStats.reqRelPrc[cr]);

							if (colorRefSettings.attributeValue("batchSize") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"batchSize\" for Batch Means Method is missing!",
										"configuration"
												+ sim.getNet()
														.getConfiguration(),
										"probe-num" + Integer.toString(p),
										"probe.id" + probe.attributeValue("id"),
										"probe.name"
												+ probe.attributeValue("name"),
										"colorRef-num" + Integer.toString(cr),
										"colorRef.id"
												+ colorRef.attributeValue("id"),
										"colorRef.color-id"
												+ colorRef
														.attributeValue("color-id")));
								throw new SimQPNException();
							}
							pr.probeStats.batchSizeST[cr] = Integer
									.parseInt(colorRefSettings
											.attributeValue("batchSize"));
							log.debug("-- probeStats.batchSizeST[" + cr
									+ "] = " + pr.probeStats.batchSizeST[cr]);

							if (colorRefSettings.attributeValue("minBatches") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"minBatches\" for Batch Means Method is missing!",
										"configuration"
												+ sim.getNet()
														.getConfiguration(),
										"probe-num" + Integer.toString(p),
										"probe.id" + probe.attributeValue("id"),
										"probe.name"
												+ probe.attributeValue("name"),
										"colorRef-num" + Integer.toString(cr),
										"colorRef.id"
												+ colorRef.attributeValue("id"),
										"colorRef.color-id"
												+ colorRef
														.attributeValue("color-id")));
								throw new SimQPNException();
							}
							pr.probeStats.minBatches[cr] = Integer
									.parseInt(colorRefSettings
											.attributeValue("minBatches"));
							log.debug("-- probeStats.minBatches[" + cr + "] = "
									+ pr.probeStats.minBatches[cr]);

							if (colorRefSettings
									.attributeValue("numBMeansCorlTested") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"numBMeansCorlTested\" for Batch Means Method is missing!",
										"configuration"
												+ sim.getNet()
														.getConfiguration(),
										"probe-num" + Integer.toString(p),
										"probe.id" + probe.attributeValue("id"),
										"probe.name"
												+ probe.attributeValue("name"),
										"colorRef-num" + Integer.toString(cr),
										"colorRef.id"
												+ colorRef.attributeValue("id"),
										"colorRef.color-id"
												+ colorRef
														.attributeValue("color-id")));
								throw new SimQPNException();
							}
							pr.probeStats.numBMeansCorlTested[cr] = Integer
									.parseInt(colorRefSettings
											.attributeValue("numBMeansCorlTested"));
							log.debug("-- probeStats.numBMeansCorlTested[" + cr
									+ "] = "
									+ pr.probeStats.numBMeansCorlTested[cr]);

							// Note that if (minBatches > 0 &&
							// numBMeansCorlTested[c] > 0),
							// minBatches[c] must be > numBMeansCorlTested[c]!
							if (pr.probeStats.minBatches[cr] > 0
									&& pr.probeStats.numBMeansCorlTested[cr] > 0
									&& !(pr.probeStats.minBatches[cr] > pr.probeStats.numBMeansCorlTested[cr])) {
								log.error(formatDetailMessage(
										"Probe.probeStats.minBatches[c] must be greater than Probe.probeStats.numBMeansCorlTested[c]!",
										"configuration"
												+ sim.getNet()
														.getConfiguration(),
										"probe-num" + Integer.toString(p),
										"probe.id" + probe.attributeValue("id"),
										"probe.name"
												+ probe.attributeValue("name"),
										"colorRef-num" + Integer.toString(cr),
										"colorRef.id"
												+ colorRef.attributeValue("id"),
										"colorRef.color-id"
												+ colorRef
														.attributeValue("color-id")));
								throw new SimQPNException();
							}
							// If (numBMeansCorlTested[c] <= 0) no correlation
							// test is done!
							// Note that numBMeansCorlTested must be even!
							if (pr.probeStats.numBMeansCorlTested[cr] % 2 != 0) {
								log.error(formatDetailMessage(
										"Probe.probeStats.numBMeansCorlTested[c] must be even!",
										"configuration"
												+ sim.getNet()
														.getConfiguration(),
										"probe-num" + Integer.toString(p),
										"probe.id" + probe.attributeValue("id"),
										"probe.name"
												+ probe.attributeValue("name"),
										"colorRef-num" + Integer.toString(cr),
										"colorRef.id"
												+ colorRef.attributeValue("id"),
										"colorRef.color-id"
												+ colorRef
														.attributeValue("color-id")));
								throw new SimQPNException();
							}

							if (pr.statsLevel >= 4) {
								if (colorRefSettings
										.attributeValue("bucketSize") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"bucketSize\" for Batch Means Method is missing!",
											"configuration"
													+ sim.getNet()
															.getConfiguration(),
											"probe-num" + Integer.toString(p),
											"probe.id"
													+ probe.attributeValue("id"),
											"probe.name"
													+ probe.attributeValue("name"),
											"colorRef-num"
													+ Integer.toString(cr),
											"colorRef.id"
													+ colorRef
															.attributeValue("id"),
											"colorRef.color-id"
													+ colorRef
															.attributeValue("color-id")));
									throw new SimQPNException();
								}
								pr.probeStats.histST[cr].setBucketSize(Double
										.parseDouble(colorRefSettings
												.attributeValue("bucketSize")));
								log.debug("-- probeStats.histST["
										+ cr
										+ "].bucketSize = "
										+ pr.probeStats.histST[cr]
												.getBucketSize());

								if (colorRefSettings
										.attributeValue("maxBuckets") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"maxBuckets\" for Batch Means Method is missing!",
											"configuration"
													+ sim.getNet()
															.getConfiguration(),
											"probe-num" + Integer.toString(p),
											"probe.id"
													+ probe.attributeValue("id"),
											"probe.name"
													+ probe.attributeValue("name"),
											"colorRef-num"
													+ Integer.toString(cr),
											"colorRef.id"
													+ colorRef
															.attributeValue("id"),
											"colorRef.color-id"
													+ colorRef
															.attributeValue("color-id")));
									throw new SimQPNException();
								}
								pr.probeStats.histST[cr]
										.setMaximumNumberOfBuckets(Integer.parseInt(colorRefSettings
												.attributeValue("maxBuckets")));
								log.debug("-- probeStats.histST["
										+ cr
										+ "].maxBuckets = "
										+ pr.probeStats.histST[cr]
												.getMaximumNumberOfBuckets());
							}
						} else {
							log.error(formatDetailMessage(
									"SimQPN configuration parameters for Batch Means Method are missing!",
									"configuration"
											+ sim.getNet().getConfiguration(),
									"probe-num" + Integer.toString(p),
									"probe.id" + probe.attributeValue("id"),
									"probe.name" + probe.attributeValue("name"),
									"colorRef-num" + Integer.toString(cr),
									"colorRef.id"
											+ colorRef.attributeValue("id"),
									"colorRef.color-id"
											+ colorRef
													.attributeValue("color-id")));
							throw new SimQPNException();
						}
					}
				}
			}
		}
		return sim.getConfiguration();
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
