/* ==============================================
 * QPME : Queueing Petri net Modeling Environment
 * ==============================================
 *
 * (c) Copyright 2003-2011, by Samuel Kounev and Contributors.
 * 
 * Project Info:   http://descartes.ipd.kit.edu/projects/qpme/
 *                 http://www.descartes-research.net/
 *    
 * All rights reserved. This software is made available under the terms of the 
 * Eclipse Public License (EPL) v1.0 as published by the Eclipse Foundation
�* http://www.eclipse.org/legal/epl-v10.html
 *
 * This software is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the Eclipse Public License (EPL)
 * for more details.
 *
 * You should have received a copy of the Eclipse Public License (EPL)
 * along with this software; if not visit http://www.eclipse.org or write to
 * Eclipse Foundation, Inc., 308 SW First Avenue, Suite 110, Portland, 97204 USA
 * Email: license (at) eclipse.org 
 *  
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *                                
 * =============================================
 *
 * Original Author(s):  Samuel Kounev
 * Contributor(s):      Frederik Zipp, Simon Spinner
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2003/08/??  Samuel Kounev     Created.
 *  ...
 *  2004/08/25  Samuel Kounev     Fixed a small bug - value of clock casted to (int) instead of (long)  
 *                                when doing heartbeats. Changed timeInitHeartBeat to 100000.
 *  2005/06/22  Samuel Kounev     Moved initialization of service time distributions from  
 *                                QPlace.init() to Simulator.getReady() as part of the model 
 *                                definition.        
 *  2006/10/14  Christofer Dutz   Added @SuppressWarnings("unchecked") and cleaned up imports to avoid warnings!
 *  2006/10/16  Samuel Kounev     Changed method execute to set Simulator.simRunning to true at the 
 *                                beginning of the simulaton and reset it at the end. The SimQPN 
 *                                plugin uses this variable to prevent starting the SimQPN net.getConfiguration()
 *                                wizard while a simulation run is in progress.
 *  2006/10/21  Samuel Kounev     Added different variants of the log(msg) method: log(msg); log(debugLev, msg);
 *                                logln(); logln(msg); logln(debugLev, msg); log(exception). 
 *                                Changed to use log methods for output.
 *  2006/10/21  Samuel Kounev     Changed to throw SimQPNException if output log file cannot be created.
 *
 *  2007/07/24  Samuel Kounev     Added support for setting timeBtwChkStops to 0 in which case it is
 *                                automatically adjusted to result in roughly 60 sec between checks.
 *  2007/08/22  Samuel Kounev     Added secondsBtwChkStops parameter which is set to 60 by default.
 *                                secondsBtwChkStops is only used if timeBtwChkStops is set to 0.
 *  2008/11/25  Samuel Kounev     Added Queues as first class objects decoupled from QueueingPlaces.
 *  2008/11/25  Samuel Kounev     Added support for multiple QPlaces sharing the same queue.
 *  2008/12/13  Samuel Kounev     Changed to extract information about the token colors and store it in 
 *                                the created Places and QPlaces.      
 *  2009/02/13  Samuel Kounev     Changed eventList to use PriorityQueue instead of LinkedList to speed up 
 *                                searches in the event list.   
 *  2009/04/08  Samuel Kounev     Changed the definition of eventList.Comparator to treat equality explicitly.
 *                                Potential problems when using eventList.remove() because of the following bug
 *                                http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6207984
 *                                On old JVMs that do not have the above bug fixed, if two events have the 
 *                                exact same time, the wrong one might be removed!
 *  2009/02/27  Frederik Zipp     Changed to return Stats as array.
 *  2009/03/08  Frederik Zipp     Added central queue management (queue editor).
 *  2009/03/08  Frederik Zipp     Made a change to support storing simulation results in an XML file.
 *  2009/16/12  Simon Spinner     Optimized run() avoiding usage of IntArrayList for managing enabled transitions. 
 *  2010/04/17	Simon Spinner     Add support for monitoring simulation progress and for cancelling.
 *  2010/04/17	Simon Spinner     Add warning for incidence functions with no input tokens.
 *  2010/07/24	Simon Spinner	  Add probe configuration support.
 * 
 */
package de.tud.cs.simqpn.kernel;

// Latest comments are labeled with "SDK-DEBUG2".
// Previous comments are labeled with "SDK-DEBUG".
// The original comments are labeled with "SK-CD".
// Please do not remove any comments! 
// You can add your comments/answers with a "CHRIS" label.

import static de.tud.cs.simqpn.kernel.util.LogUtil.formatDetailMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import de.tud.cs.simqpn.kernel.analyzer.*;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.Probe;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.loader.ConfigurationLoader;
import de.tud.cs.simqpn.kernel.loader.NetFlattener;
import de.tud.cs.simqpn.kernel.loader.NetLoader;
import de.tud.cs.simqpn.kernel.loader.XMLHelper;
import de.tud.cs.simqpn.kernel.loader.XMLValidator;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;
import de.tud.cs.simqpn.kernel.persistency.StatsDocumentBuilder;
import de.tud.cs.simqpn.kernel.random.RandomNumberGenerator;
import de.tud.cs.simqpn.kernel.stats.Stats;

/**
 * Class Simulator
 * 
 * @version %I%, %G%
 */

public class SimQPNController {

	//
	// ATTENTION: Update this constant every time a new
	// release of qpme is delivered so that
	// the version attribute in .simqpn files
	// is correct
	//
	public static final String QPME_VERSION = "2.1.0";

	private static Logger log = Logger.getLogger(SimQPNController.class);

	private SimQPNConfiguration configuration;
	
	private Net net;
	/** True if simulation is currently running. */
	private static boolean simRunning; 

	/**
	 * Constructor
	 * 
	 * TODO Keine Lese-Zugriffe auf Objekt bevor initialisiert: besser in
	 * init()-Methode auslagern: Ansonsten -->Illegal State Exception
	 * 
	 * @param netXML
	 * @param configurationName
	 * @throws SimQPNException
	 */
	public SimQPNController(Element netXML, String configurationName,
			String logConfigFilename) throws SimQPNException {
		// Random Number Generation (Note: needs to be initialized before
		// starting the model definition)
		RandomNumberGenerator.initialize();
		setConfiguration(ConfigurationLoader.configure(netXML,
				configurationName, logConfigFilename));
		netXML = NetFlattener.prepareNet(netXML, configurationName,
				getConfiguration().getStatsDir()); // flattens HQPNS
		net = new NetLoader().load(netXML, configurationName, configuration);
		getReady(netXML, configurationName);
	}

	/**
	 * Method getReady - prepares for the simulation run
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	private void getReady(Element netXML, String configurationName)
			throws SimQPNException {
		ConfigurationLoader.configureSimulatorSettings(netXML,
				configurationName, getConfiguration());

		// CONFIG: Whether to use indirect estimators for FCFS queues
		for (int p = 0; p < getNet().getNumPlaces(); p++) {
			Place pl = getNet().getPlace(p);
			if (pl.statsLevel >= 3 && pl instanceof QPlace) {
				((QPlace) pl).qPlaceQueueStats.indrStats = false;
				log.debug("places[" + p
						+ "].qPlaceQueueStats.indrStats = false;");
			}
		}

		configureBatchMeansMethod(netXML);

		XMLValidator.validateInputNet(netXML); // TODO Think about moving this
												// into Constructor

	}


	/**
	 * Method execute - executes the simulation run
	 * 
	 * TODO do not pass Element, and configurationString TODO delet Monitor
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public File execute(Element XMLNet, String configurationString, String outputFilename,
			SimulatorProgress monitor) throws SimQPNException {

		// TODO: Make the Stdout output print to $statsDir/Output.txt
		// CHRIS: Not done yet

		Stats[] result = null;

		setSimRunning(true); // True if simulation is currently running.

		// NOTE: In the following, if the simulation is interrupted, simRunning
		// should be reset.

		try {
			// TODO Factory Methode / Factory Klasse
			if (getConfiguration().runMode == SimQPNConfiguration.NORMAL) {
				if (getConfiguration().getAnalMethod() == SimQPNConfiguration.AnalysisMethod.BATCH_MEANS) {
					/** Method of non-overlapping batch means */
					result = new BatchMeans().analyze(net, configuration,
							monitor);
				} else if (getConfiguration().getAnalMethod() == SimQPNConfiguration.AnalysisMethod.REPL_DEL) {
					/**
					 * Replication/Deletion Approach (Method of Independent
					 * Replications)
					 */
					result = new ReplicationDeletionParallel()
							.analyze2(net, configuration, monitor, XMLNet,
									configurationString);
				} else {
					log.error("Illegal analysis method specified!");
					throw new SimQPNException();
				}
			} else if (getConfiguration().runMode == SimQPNConfiguration.INIT_TRANS) {
				if (getConfiguration().getAnalMethod() == SimQPNConfiguration.AnalysisMethod.WELCH) {
					result = new Welch().analyze2(net, configuration, monitor,
							XMLNet, configurationString);
				} else {
					log.error("Analysis method "
							+ getConfiguration().getAnalMethod()
							+ " not supported in INIT_TRANS mode!");
					throw new SimQPNException();
				}
			} else {
				log.error("Invalid run mode specified!");
				throw new SimQPNException();
			}
		} finally {
			setSimRunning(false);
			LogManager.shutdown();
		}

		File resultFile = null;
		// Skip stats document generation for WELCH and REPL_DEL since the 
		// document builder does not support these methods yet.
		
		if ((result != null)
				&& (configuration.getAnalMethod() == SimQPNConfiguration.AnalysisMethod.BATCH_MEANS)) {
			StatsDocumentBuilder builder = new StatsDocumentBuilder(result,
					XMLNet, configurationString);
			Document statsDocument = builder.buildDocument(configuration);
			if (outputFilename != null) {
				resultFile = new File(outputFilename);
			} else {
				resultFile = new File(configuration.getStatsDir(), builder.getResultFileBaseName() + ".simqpn");
			}
			saveXmlToFile(statsDocument, resultFile);
		}
		return resultFile;
	}

	private void saveXmlToFile(Document doc, File file) {
		XMLWriter writer = null;
		try {
			writer = new XMLWriter(new FileOutputStream(file),
					OutputFormat.createPrettyPrint());
			writer.write(doc);
		} catch (IOException e) {
			log.error("",e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public Net getNet() {
		return net;
	}

	public void setNet(Net net) {
		this.net = net;
	}

	public SimQPNConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(SimQPNConfiguration configuration) {
		this.configuration = configuration;
	}

	public static boolean isSimRunning() {
		return simRunning;
	}

	public static void setSimRunning(boolean simRunning) {
		SimQPNController.simRunning = simRunning;
	}
	
	/**
	 * Configures PlaceStats
	 * 
	 * @throws SimQPNException
	 */
	public void configureBatchMeansMethod(Element netXML) throws SimQPNException {
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
		if (getConfiguration().getAnalMethod() == SimQPNConfiguration.AnalysisMethod.BATCH_MEANS) {
			XPath xpathSelector = XMLHelper.createXPath("//place");
			List<Element> placeList = xpathSelector.selectNodes(netXML);
			xpathSelector = XMLHelper.createXPath("//probe");
			List<Element> probeList = xpathSelector.selectNodes(netXML);
			Iterator<Element> placeIterator;
			placeIterator = placeList.iterator();
			for (int p = 0; placeIterator.hasNext(); p++) {
				Element place = placeIterator.next();
				Place pl = getNet().getPlace(p);
				/**
				 * for (int p = 0; p<getNet().getNumPlaces(); p++) { Place
				 * pl = getNet().getPlace(p);
				 */
				if (pl.statsLevel >= 3) {
					log.debug("places[" + p + "]");
					xpathSelector = XMLHelper
							.createXPath("color-refs/color-ref");
					List<Element> colorList = xpathSelector.selectNodes(place);
					Iterator<Element> colorRefIterator = colorList.iterator();
					for (int cr = 0; colorRefIterator.hasNext(); cr++) {
						Element colorRef = colorRefIterator.next();
						Element colorRefSettings = ConfigurationLoader.getSettings(colorRef, net.getConfiguration());
						// Initialize Place (or Depository if pl is QPlace)
						if (colorRefSettings != null) {
							if (colorRefSettings.attributeValue("signLev") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"signLev\" for Batch Means Method is missing!",
										"configuration", getNet()
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
										"configuration", getNet()
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
										"configuration", getNet()
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
										"configuration", getNet()
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
										"configuration", getNet()
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
										"configuration", getNet()
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
										"configuration", getNet()
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
										"configuration", getNet()
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
											"configuration", getNet()
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
											"configuration", getNet()
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
									"configuration", getNet()
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
											"configuration", getNet()
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
											"configuration", getNet()
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
											"configuration", getNet()
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
											"configuration", getNet()
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
											"configuration", getNet()
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
											"configuration", getNet()
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
											"configuration", getNet()
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
											"configuration", getNet()
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
												getNet().getConfiguration(),
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
												getNet().getConfiguration(),
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
										"configuration", getNet()
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
				Probe pr = getNet().getProbe(p);
				if (pr.statsLevel >= 3) {
					log.debug("probes[" + p + "]");
					xpathSelector = XMLHelper
							.createXPath("color-refs/color-ref");
					Iterator<Element> colorRefIterator = xpathSelector
							.selectNodes(probe).iterator();
					for (int cr = 0; colorRefIterator.hasNext(); cr++) {
						Element colorRef = colorRefIterator.next();
						Element colorRefSettings = ConfigurationLoader.getSettings(colorRef, net.getConfiguration());
						// Initialize Probe
						if (colorRefSettings != null) {
							if (colorRefSettings.attributeValue("signLev") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"signLev\" for Batch Means Method is missing!",
										"configuration"
												+ getNet()
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
												+ getNet()
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
												+ getNet()
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
												+ getNet()
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
												+ getNet()
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
												+ getNet()
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
												+ getNet()
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
												+ getNet()
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
													+ getNet()
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
													+ getNet()
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
											+ net.getConfiguration(),
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
	}

} // end of Class Simulator
