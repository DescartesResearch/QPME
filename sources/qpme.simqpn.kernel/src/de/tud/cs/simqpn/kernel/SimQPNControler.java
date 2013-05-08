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
import static de.tud.cs.simqpn.kernel.util.LogUtil.formatMultilineMessage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

import javax.xml.XMLConstants;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.XPath;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import cern.jet.random.AbstractContinousDistribution;
import cern.jet.random.Beta;
import cern.jet.random.BreitWigner;
import cern.jet.random.BreitWignerMeanSquare;
import cern.jet.random.ChiSquare;
import cern.jet.random.Empirical;
import cern.jet.random.EmpiricalWalker;
import cern.jet.random.Exponential;
import cern.jet.random.ExponentialPower;
import cern.jet.random.Gamma;
import cern.jet.random.Hyperbolic;
import cern.jet.random.Logarithmic;
import cern.jet.random.Normal;
import cern.jet.random.StudentT;
import cern.jet.random.Uniform;
import cern.jet.random.VonMises;
import de.tud.cs.simqpn.kernel.analyzer.SimulatorResults;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.Probe;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.Queue;
import de.tud.cs.simqpn.kernel.entities.Token;
import de.tud.cs.simqpn.kernel.entities.Transition;
import de.tud.cs.simqpn.kernel.executor.Event;
import de.tud.cs.simqpn.kernel.loader.NetLoader;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;
import de.tud.cs.simqpn.kernel.random.RandomNumberGenerator;
import de.tud.cs.simqpn.kernel.stats.AggregateStats;
import de.tud.cs.simqpn.kernel.stats.Stats;
import de.tud.cs.simqpn.kernel.util.LogUtil;
import de.tud.cs.simqpn.kernel.util.LogUtil.ReportLevel;

/**
 * Class Simulator
 * 
 * @version %I%, %G%
 */

public class SimQPNControler {
	
	//
	//ATTENTION: Update this constant every time a new
	//			 release of qpme is delivered so that
	//			 the version attribute in .simqpn files
	//			 is correct
	//
	public static final String QPME_VERSION = "2.1.0";	
	
	private static Logger log = Logger.getLogger(SimQPNControler.class);
	
	// Supported Run Modes
	// SK-CD: Only consider run modes NORMAL and INIT_TRANS, ignore the rest!
	public static final int NORMAL = 0; 		// Normal run mode.
	public static final int INIT_TRANS = 1; 	// Used for determining the length of the initial transient
												//   (currently only the method Welch is supported).
	public static final int MULT_REPL = 2;  	// Runs multiple replications, used to study the behavior of analysis
												//   methods when run multiple times (currently only supported for the
												//   BATCH_MEANS method).
	public static final int CVRG_EST = 3;		// Coverage estimation (currently only supported for BATCH_MEANS with 
												//   RELPRC stopping rule).

	// Supported Analysis Methods
	public static final int WELCH = 0;			// Method of Welch for determining the length of the initial transient.
	public static final int REPL_DEL = 1;		// Replication/Deletion Approach (Method of Independent Replications).
	public static final int BATCH_MEANS = 2;	// Method of non-overlapping batch means.

	// Supported Stopping Rules (Note: don't change these, values are used)
	public static final int FIXEDLEN = 0;		// Fixed run length.
	public static final int ABSPRC = 1;			// Run until enough data to provide absolute precision for  
												//   sojourn times (STs) in terms of confidence interval (c.i.) half lengths.
	public static final int RELPRC = 2;			// Run until enough data to provide relative precision for STs in terms
												//   of c.i. half lengths / means.
	
	public static final long MAX_INITIAL_HEARTBEAT = 2 * 60 * 1000;
												// Maximum time until initial heartbeat. Important for long running
												//   simulations with slow simulation clock progress.
	
	public static int numRuns;					// Maximum number of runs.
	public static boolean useStdStateStats; 	// For (MULT_REPL, statsLevel >= 3): Specifies whether to use ordinary
												//   or steady state sojourn times when estimating averages and c.i.
	public static String statsDir;

	public static int runMode;
	public static int analMethod;				// Output data analysis method.
	public static int stoppingRule;				// Simulation stopping criterion.
	private static int debugLevel;				// Debug level - TODO: currently not used consistently!
	public static double rampUpLen;				// Duration of the ramp up period.
	public static double totRunLen;				// Maximum total duration of the simulation run (incl. rampUpLen).
	public static double timeBtwChkStops;		// Time between checks if stopping criterion is fulfilled. 
												//   If set to 0, it is automatically adjusted to result in roughtly 
												//   secondsBtwChkStops sec between checks.
	public static double secondsBtwChkStops;	// Seconds between checks if stopping criterion is fulfilled. TODO: Add to User's Guide. 
												//   Used only when timeBtwChkStops == 0.
	/* ORIGINAL HEARTBEAT IMPLEMENTATION
	public static double timeInitHeartBeat;		// Time when the first progress update is made. After this
												//   progress updates are made once every secsBtwHeartBeats seconds.
	public static double secsBtwHeartBeats;		// How often progress updates are made (heart beats).
	*/
	
	public static double runsBtwCvrgChks;		// CVRG_EST: Frequency of checking if enough data has been gathered
												//   to provide conf. interval for true coverage with required relative precision.

	/* NOTE: Stopping Rules
	 * Total run duration is always limited to totRunLen. 
	 *   1. If stoppingRule is FIXEDLEN duration is always fixed to totRunLen.
	 *   2. If stoppingRule is ABSPRC or RELPRC the run ends as soon as enough data is 
	 *      available to provide required precision for STs or totRunLen is reached.
	 *      chkStopFreq specifies how often it is checked (time between checks) 
	 *      if enough data is available to provide required precision.
	 */
	
	public static boolean inRampUp;				// True if still in RampUp period (no measurements taken).
	public static boolean simRunning;			// True if simulation is currently running.
	public static double endRampUpClock;		// Clock at the end of RampUp, i.e. beginning of the measurement period.
	public static double endRunClock;			// Clock at the end of the run.
	public static double msrmPrdLen;			// Duration of the measurement period (endRunClock - endRampUpClock).
	public static double beginRunWallClock;		// currentTimeMillis at the begin of the run (wall clock time).
	public static double endRunWallClock;		// currentTimeMillis at the end of the run (wall clock time).
	public static double runWallClockTime;		// Total duration of the run in seconds.
	
	// Time histogram configuration parameters.
	public static final int       TIME_HISTOGRAM_MIN_NUM_BUCKETS = 2;
	public static final int       TIME_HISTOGRAM_MAX_NUM_BUCKETS = 1000;
	public static final double    TIME_HISTOGRAM_DEFAULT_BUCKET_SIZE = 100;
	
	// Queue overflow detection sensitivity configuration parameters
	public static final long OVERFLOW_DET_START_EPOCH_LENGTH = 100;
	public static final long OVERFLOW_DET_MAX_EPOCH_LENGTH = 10000 * OVERFLOW_DET_START_EPOCH_LENGTH;
	public static final long OVERFLOW_DET_DETECTION_THRESHOLD = 1000;
	public static final int  OVERFLOW_DET_MIN_CONS_RISING_EPOCHS = 30;
	public static final int  OVERFLOW_DET_MAX_CONS_RISING_EPOCHS = 100;
	
	// Progress monitoring
	public static SimulatorProgress progressMonitor;

	// Check if using double for time is really needed and if overhead is tolerable. Consider switching to float.
	public static double clock;					// Global simulation clock. Time is usually measured in milliseconds.
	public static 
		PriorityQueue<Event> eventList =		// Global simulation event list. Contains events scheduled for processing at specified points in time.
	      new PriorityQueue<Event>(10, 
	        new Comparator<Event>() {
	          public int compare(Event a, Event b) {
	        	 return (a.time < b.time ? -1 : (a.time == b.time ? 0 : 1));	        	 
	          }
	        }
	      );
	// public static LinkedList eventList;		// Old LinkedList implementation of the event list.	

	/* WARNING: Watch out when defining the Comparator above: Equality should be treated explicitly!
	 * 
	 * Potential problems when using eventList.remove() because of the following bug
	 * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6207984
	 * http://bugs.sun.com/bugdatabase/view_bug.do;jsessionid=859a6e381a7abfffffffff7e644d05a59d93?bug_id=6268068
	 * 
	 * On old JVMs that do not have the above bug fixed, if two events have the exact same time, the wrong one might be removed! 
	 *   
	 */
	
	// XML Configuration.
//	protected String configuration;
	//private Element netXML;
	private Net net;
		
	private static Map<String, String> namespaceUris = new HashMap<String, String>();	
	static {
		namespaceUris.put("xsi", XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI);
	}
	private static final QName XSI_TYPE_ATTRIBUTE = new QName("type", new Namespace("xsi", XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI));

	public SimQPNControler(Element netXML, String configuration) throws SimQPNException {

		// Random Number Generation (Note: needs to be initialized before starting the model definition)
		RandomNumberGenerator.initialize();

//		this.configuration = configuration;
//		System.out.println(configuration);
		this.net = NetLoader.load(netXML, configuration);
		System.out.println("loading of the net finished");

		this.getReady(netXML);
	}

	/**
	 * Method getReady - prepares for the simulation run
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	private void getReady(Element netXML) throws SimQPNException {
		// BEGIN-CONFIG
		// ------------------------------------------------------------------------------------------------------

		configureSimulatorSettings(netXML);

		configureBatchMeansMethod();

		// END-CONFIG
		// ---------------------------------------------------------------------------------------------------------

		// Working variables
		initializeWorkingVariables();
	}
	
	private void configureSimulatorSettings(Element netXML) throws SimQPNException {
		log.debug("/////////////////////////////////////////////");
		log.debug("// Misc settings");
		
		Element simulatorSettings = getSettings(netXML, net.getConfiguration());
		System.out.println("simulatorSettings" + simulatorSettings);
		
		if ("RELPRC".equals(simulatorSettings.attributeValue("stopping-rule"))) {
			stoppingRule = RELPRC;
			log.debug("stoppingRule = RELPRC;");			
		} else if ("ABSPRC".equals(simulatorSettings.attributeValue("stopping-rule"))) {
			stoppingRule = ABSPRC;
			log.debug("stoppingRule = ABSPRC;");			
		} else if ("FIXEDLEN".equals(simulatorSettings.attributeValue("stopping-rule"))) {
			stoppingRule = FIXEDLEN;
			log.debug("stoppingRule = FIXEDLEN;");			
		} else {
			log.error(formatDetailMessage(
					"Configuration parameter \"stopping-rule\" not configured correctly!",					
					"configuration", net.getConfiguration()
					));
			throw new SimQPNException();
		}
		// Only for scenario 1 stopping-rule is set. For others it is set to FIXEDLEN.		
		if (Integer.parseInt(simulatorSettings.attributeValue("scenario")) != 1 && stoppingRule != FIXEDLEN)  {
			log.error(formatDetailMessage(
					"Stopping rule \"" + simulatorSettings.attributeValue("stopping-rule") + "\" is not supported for the chosen analysis method!",					
					"configuration", net.getConfiguration()
					));
			throw new SimQPNException();
		}

		if (simulatorSettings.attributeValue("total-run-length") == null) {
			log.error(formatDetailMessage(
					"Configuration parameter \"total-run-length\" is not configured!",					
					"configuration", net.getConfiguration()
					));
			throw new SimQPNException();
		}		
		totRunLen = Double.parseDouble(simulatorSettings.attributeValue("total-run-length"));
		log.debug("totRunLen = " + totRunLen + ";");
		
		if (Integer.parseInt(simulatorSettings.attributeValue("scenario")) != 3) {		
			if (simulatorSettings.attributeValue("ramp-up-length") == null) {
				log.error(formatDetailMessage(
						"Configuration parameter \"ramp-up-length\" is not configured!",					
						"configuration", net.getConfiguration()
						));
				throw new SimQPNException();
			}
			rampUpLen = Double.parseDouble(simulatorSettings.attributeValue("ramp-up-length"));
			log.debug("rampUpLen = " + rampUpLen + ";");			
		} else { // Method of Welch
			rampUpLen = totRunLen; // Note: The method of Welch is currently run until rampUpLen is reached.
		}
						
		if(stoppingRule != FIXEDLEN) {
			if(simulatorSettings.attributeValue("time-between-stop-checks") == null) {						
				log.error(formatDetailMessage(
						"Configuration parameter \"time-between-stop-checks\" is not configured!",					
						"configuration", net.getConfiguration()
						));
				throw new SimQPNException();
			}		
			timeBtwChkStops = Double.parseDouble(simulatorSettings.attributeValue("time-between-stop-checks"));
			log.debug("timeBtwChkStops = " + timeBtwChkStops + ";");			
			if(simulatorSettings.attributeValue("seconds-between-stop-checks") == null) {						
				log.error(formatDetailMessage(
						"Configuration parameter \"seconds-between-stop-checks\" is not configured!",					
						"configuration", net.getConfiguration()
						));
				throw new SimQPNException();
			}		
			secondsBtwChkStops = Double.parseDouble(simulatorSettings.attributeValue("seconds-between-stop-checks"));
			log.debug("secondsBtwChkStops = " + secondsBtwChkStops + ";");
		}
		
		/* ORIGINAL HEARTBEAT IMPLEMENTATION
		if(simulatorSettings.attributeValue("time-before-initial-heart-beat") == null) {		
			logln("Error: Configuration parameter \"time-before-initial-heart-beat\" is not configured!");					
			logln("  configuration = " + configuration);						
			throw new SimQPNException();
		}		
		timeInitHeartBeat = Double.parseDouble(simulatorSettings.attributeValue("time-before-initial-heart-beat"));
		logln(2, "timeInitHeartBeat = " + timeInitHeartBeat + ";");
		
		if(simulatorSettings.attributeValue("seconds-between-heart-beats") == null) {		
			logln("Error: Configuration parameter \"seconds-between-heart-beats\" is not configured!");					
			logln("  configuration = " + configuration);						
			throw new SimQPNException();
		}		
		secsBtwHeartBeats = Double.parseDouble(simulatorSettings.attributeValue("seconds-between-heart-beats"));
		logln(2, "secsBtwHeartBeats = " + secsBtwHeartBeats + ";");
		*/
		
		if (analMethod != BATCH_MEANS && stoppingRule != FIXEDLEN) {
			log.error(formatDetailMessage(
					"Stopping rule \"" + simulatorSettings.attributeValue("stopping-rule") + "\" is not supported for the batch means analysis method!",					
					"configuration", net.getConfiguration()
					));
			throw new SimQPNException();
		}
		// CONFIG: Whether to use indirect estimators for FCFS queues
		for (int p = 0; p < net.getNumPlaces(); p++) {
			Place pl = net.getPlace(p);
			if (pl.statsLevel >= 3 && pl instanceof QPlace) {
				((QPlace) pl).qPlaceQueueStats.indrStats = false;
				log.debug("places[" + p + "].qPlaceQueueStats.indrStats = false;");				
			}
		}
	}



	public static void configure(Element net, String configuration, String logConfigFilename) throws SimQPNException {
		// BEGIN-CONFIG
		// ------------------------------------------------------------------------------------------------------

		/* Global run configuration parameters:  
		 *   1. "Analysis Method" (analMethod) == BATCH_MEANS, REPL_DEL or WELCH
		 *   2. "Maximum Number of Runs" (numRuns) 
		 *   3. "Output Directory" (statsDir)
		 * 
		 * numRuns should only be available if analMethod is REPL_DEL or WELCH.
		 * 
		 * IMPORTANT: runMode is implied from the chosen analysis method: 
		 *   - If the user chooses BATCH_MEANS or REPL_DEL, runMode is set to NORMAL. 
		 *   - If the user chooses WELCH, runMode is set to INIT_TRANS.
		 * 
		 * The QPN to be simulated is defined in the getReady() method.
		 * 
		 * Depending on the selected analysis method different parameters must be 
		 * configured - see methods getReady and runWelchMtd for detailed
		 * information.
		 * 
		 * IMPORTANT: The order in which things are done must remain unchanged!!!
		 * 
		 */

		// Initialize logging
		if (logConfigFilename != null) {
			LogUtil.configureCustomLogging(logConfigFilename);
		} else {
			XPath xpathSelector = createXPath("/net/meta-attributes/meta-attribute[@xsi:type = 'simqpn-configuration' and @configuration-name = '" + configuration + "']/@output-directory");
			Attribute outputDirAttribute = (Attribute) xpathSelector.selectSingleNode(net);
			try {
				LogUtil.configureDefaultLogging(outputDirAttribute.getStringValue(), "SimQPN_Output_" + configuration);
			} catch (IOException e) {
				log.error("Cannot create simulation output log file! Please check output directory path.", e);
				throw new SimQPNException();
			}
		}
		
		Element simulatorSettings = getSettings(net, configuration);

		if (simulatorSettings.attributeValue("verbosity-level") == null) {
			log.error(formatDetailMessage(
					"Configuration parameter \"verbosity-level\" is not configured!",
					"configuration", configuration
					));
			throw new SimQPNException();
		}
		debugLevel = Integer.parseInt(simulatorSettings.attributeValue("verbosity-level"));
		switch(debugLevel) {
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
		log.debug("debugLevel = " + debugLevel + ";");
		
		// There are 3 possible scenarios (combinations of runMode and analMethod):
		log.debug("Scenario set to: " + simulatorSettings.attributeValue("scenario"));
		switch (Integer.parseInt(simulatorSettings.attributeValue("scenario", "-1"))) {
			// Scenario 1:
			case 1: {
				runMode = NORMAL;
				analMethod = BATCH_MEANS;
				log.debug("-- runMode = NORMAL");
				log.debug("-- analMethod = BATCH_MEANS");				
				break;
			}
			// Scenario 2:
			case 2: {
				runMode = NORMAL;
				analMethod = REPL_DEL;
				log.debug("-- runMode = NORMAL");
				log.debug("-- analMethod = REPL_DEL");				
				if(simulatorSettings.attributeValue("number-of-runs") == null) {
					log.error("\"number-of-runs\" parameter for Replication/Deletion Method not specified!");					
					throw new SimQPNException();
				}				
				numRuns = Integer.parseInt(simulatorSettings.attributeValue("number-of-runs"));
				log.debug("-- numRuns = " + numRuns);
				break;
			}
			// Scenario 3:
			case 3: {
				runMode = INIT_TRANS;
				analMethod = WELCH;
				log.debug("-- runMode = INIT_TRANS");
				log.debug("-- analMethod = WELCH");				
				if(simulatorSettings.attributeValue("number-of-runs") == null) {
					log.error("\"number-of-runs\" parameter for Method of Welch not specified!");
					throw new SimQPNException();
				}				
				numRuns = Integer.parseInt(simulatorSettings.attributeValue("number-of-runs"));
				log.debug("-- numRuns = " + numRuns);
				break;
			}
			default: {	
				log.error("Invalid analysis method (scenario) specified!");
				throw new SimQPNException();
			}
		};
		
		statsDir = simulatorSettings.attributeValue("output-directory");
		log.debug("statsDir = " + statsDir);
		
		// END-CONFIG
		// ------------------------------------------------------------------------------------------------------
	}
	
	/**
	 * Prepares the net for simulation. Should be called after Simulator.configure and
	 * before Simulator.execute
	 */
	public static Element prepareNet(Element net, String configuration) throws SimQPNException {
		Element result = net;
		XPath xpathSelector = createXPath("//place[@xsi:type = 'subnet-place']");
		if(xpathSelector.selectSingleNode(net) != null) {
			try {
				// There are subnet-places in the net; replace the subnet place by their subnet
				InputStream xslt = SimQPNControler.class.getResourceAsStream("/de/tud/cs/simqpn/transforms/hqpn_transform.xsl");
				TransformerFactory transformFactory = TransformerFactory.newInstance();
				Transformer hqpnTransform = transformFactory.newTransformer(new StreamSource(xslt));
				DocumentSource source = new DocumentSource(net.getDocument());
				DocumentResult flattenNet = new DocumentResult();
				hqpnTransform.transform(source, flattenNet);
				result = flattenNet.getDocument().getRootElement();
				
				// Save the transformed net to disk for debug purposes
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HHmmssS");
				File f = new File(SimQPNControler.statsDir, "SimQPN_FlatHQPN_" + configuration + "_" + dateFormat.format(new Date()) + ".qpe");
				XMLWriter writer = null;
				try {
					writer = new XMLWriter(new FileOutputStream(f), OutputFormat.createPrettyPrint());
					writer.write(result.getDocument());
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (writer != null) {
						try {
							writer.close();
						} catch (IOException e) {
						}
					}
				}
			} catch (Exception e) {
				log.error("Could not merge subnets into a flattened net.", e);
				throw new SimQPNException();
			}
		}			
		return result;
	}

	/**
	 * Method execute - executes the simulation run
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public static Stats[] execute(Element net, String configuration, SimulatorProgress monitor) throws SimQPNException {
		
		// TODO: Make the Stdout output print to $statsDir/Output.txt
		// CHRIS: Not done yet

		Stats[] result = null;
		
		simRunning = true;
		// NOTE: In the following, if the simulation is interrupted, simRunning should be reset. 		  
				
		try {
			validateInputNet(net);
			
			if (runMode == NORMAL) {
				if (analMethod == BATCH_MEANS) { // Method of non-overlapping batch means
					SimulatorResults results = runBatchMeans(net, configuration, monitor);
					List<Stats> stats = new ArrayList<Stats>();
					for (int p = 0; p < results.getPlaces().length; p++) {
						stats.add(results.getPlaces()[p].placeStats);
						if (results.getPlaces()[p] instanceof QPlace) {
							stats.add(((QPlace) results.getPlaces()[p]).qPlaceQueueStats);
						}
						results.getPlaces()[p].report();
					}
					if (results.getQueues() != null) {
						for (Queue queue : results.getQueues()) {
							if (queue != null && queue.queueStats != null) {
								stats.add(queue.queueStats);
							}
						}
					}
					for (int pr = 0; pr < results.getProbes().length; pr++) {
						stats.add(results.getProbes()[pr].probeStats);
						results.getProbes()[pr].report();
					}
					result = (Stats[]) stats.toArray(new Stats[stats.size()]);
				} else if (analMethod == REPL_DEL) { // Replication/Deletion Approach (Method of Independent Replications) 				
					useStdStateStats = false;
					// useStdStateStats configurable only in MULT_REPL mode
					//   - automatically set to true in CVRG_EST mode.
					//   - automatically set to false in NORMAL:REPL_DEL mode. 					
					AggregateStats[] aggrStats = runMultRepl(net, configuration, monitor);
					for (int i = 0; i < aggrStats.length; i++)
						if (aggrStats[i] != null)
							aggrStats[i].printReport();
					result = aggrStats;
				} else {
					log.error("Illegal analysis method specified!");
					throw new SimQPNException();				
				}
			} else if (runMode == INIT_TRANS) {
				if (analMethod == WELCH) {
					runWelchMtd(net, configuration, monitor);
				} else {
					log.error("Analysis method " + analMethod + " not supported in INIT_TRANS mode!");
					throw new SimQPNException();
				}
			} else {
				log.error("Invalid run mode specified!");				
				throw new SimQPNException();
			}				
		} finally {		
			simRunning = false;	
			LogManager.shutdown();
		}
		return result;
	}

	private static void validateInputNet(Element net) throws SimQPNException {
		XPath xpathSelector = createXPath("//color-ref[@maximum-capacity > 0]");
		if(xpathSelector.selectSingleNode(net) != null) {
			log.error("Max population attribute currently not supported (Set to 0 for all places)");
			throw new SimQPNException();
		}		
		xpathSelector = createXPath("//color-ref[@ranking > 0]");
		if(xpathSelector.selectSingleNode(net) != null) {
			log.error("Ranking attribute currently not supported (Set to 0 for all places)");
			throw new SimQPNException();
		}
		xpathSelector = createXPath("//color-ref[@priority > 0]");
		if(xpathSelector.selectSingleNode(net) != null) {
			log.error("Priority attribute currently not supported (Set to 0 for all places)");
			throw new SimQPNException();
		}
		xpathSelector = createXPath("//transition[@priority > 0]");
		if(xpathSelector.selectSingleNode(net) != null) {
			log.error("Transition priorities currently not supported (Set to 0 for all places)");
			throw new SimQPNException();
		}
	}

	/* 
	 * Former main method main 
	 *
	public static void main(String[] args) {

		// Run configuration parameters
			
		runMode				= NORMAL;			
		analMethod			= BATCH_MEANS;

//		runMode				= NORMAL;			
//		analMethod			= REPL_DEL;

//		runMode				= MULT_REPL;			
//		analMethod			= BATCH_MEANS;
				 				 		 				
//		runMode				= CVRG_EST;			
//		analMethod			= BATCH_MEANS;
		
//		runMode				= INIT_TRANS;			
//		analMethod			= WELCH;    

		debugLevel			= 1;		// Should normally be 1 
		numRuns				= 10000;	// Maximum number of runs		
		useStdStateStats	= true;		// Configurable only in MULT_REPL mode 
										//  - automatically set to true in CVRG_EST mode
										//  - automatically set to false in NORMAL:REPL_DEL mode. 
		
		runsBtwCvrgChks		= 10;		// CVRG_EST: Frequency of checking if enough data has been gathered to provide
										// conf. interval for true coverage with required relative precision												 
		
		if (runMode == NORMAL)  {
			if (analMethod == BATCH_MEANS)  {    // Method of non-overlapping batch means				
				Place[] places = runBatchMeans(); 					
				for (int p=0; p < places.length; p++) 
					places[p].report();		
			}
			else if (analMethod == REPL_DEL)  {  // Replication/Deletion Approach (Method of Independent Replications)
				useStdStateStats = false;
				AggregateStats[] aggrStats = runMultRepl();									
				for (int i=0; i < aggrStats.length; i++) 
					if (aggrStats[i] != null) aggrStats[i].printReport(); 						
			}			
			else  {
				System.out.println("Error: Illegal analysis method specified!");
				System.exit(-1);
			}						 
		}
		else if (runMode == INIT_TRANS)  {
			if (analMethod == WELCH)  { 				
				AggregateStats[] aggrStats = runWelchMtd();			
			}
			else  {
				System.out.println("Error: Analysis method " + analMethod + " not supported in INIT_TRANS mode!");
				System.exit(-1);
			}
		}													
		else if (runMode == MULT_REPL)  { // Currently only BATCH_MEANS supported in MULT_REPL mode!		
			if (analMethod == BATCH_MEANS)  { 
				AggregateStats[] aggrStats = runMultRepl();			
				for (int i=0; i < aggrStats.length; i++) 
					if (aggrStats[i] != null) aggrStats[i].printReport(); 						
			}
			else  {
				System.out.println("Error: Analysis method " + analMethod + " not supported in MULT_REPL mode!");
				System.exit(-1);
			}						 								
		}								
		else if (runMode == CVRG_EST)  {  // Currently only BATCH_MEANS supported for coverage estimation!
			if (analMethod == BATCH_MEANS)  {  							
				runCvrgEst();			
			}
			else  {
				System.out.println("Error: Analysis method " + analMethod + " not supported in CVRG_EST mode!");
				System.exit(-1);
			}						 								
		}
		else  {
			System.out.println("Error: Invalid run mode specified!");
			System.exit(-1);
		}
		
	}
*/
	
	/**
	 * Method runBatchMeans - implements the method of non-overlapping batch means
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public static SimulatorResults runBatchMeans(Element netXML, String configuration, SimulatorProgress monitor) throws SimQPNException {
		progressMonitor =  monitor;
		progressMonitor.startSimulation();
		SimQPNControler sim = new SimQPNControler(netXML, configuration);
		progressMonitor.startSimulationRun(1);
		sim.run();
		progressMonitor.finishSimulationRun();
		progressMonitor.finishSimulation();
		progressMonitor = null;
		return new SimulatorResults(sim.net.getPlaceArray(), sim.net.getQueueArray(), sim.net.getProbeArray());
	}

	/**
	 * Method runMultRepl - runs multiple replications 
	 * Used in MULT_REPL mode as well as for the REPL/DEL method in NORMAL mode
	 *  
	 * MULT_REPL mode is used to study the behavior of analysis methods when run multiple times 
	 * 
	 * @param  	
	 * @return 
	 * @exception
	 */
	public static AggregateStats[] runMultRepl(Element netXML, String configuration, SimulatorProgress monitor) throws SimQPNException {

		if (numRuns <= 1) {
			log.error("numRuns should be > 1!");
			throw new SimQPNException();
		}	

		progressMonitor = monitor;

		SimQPNControler sim = new SimQPNControler(netXML, configuration);

		int numPlaces = sim.net.getNumPlaces();
		Place[] places = sim.net.getPlaceArray();
		List placeList = sim.net.getPlaceList();
		
		AggregateStats[] aggrStats = new AggregateStats[numPlaces * 2]; 
		// Note: aggrStats should be large enough to accomodate 2 stats per place in case all places are queueing places.
		
		Place pl;

		for (int p = 0; p < numPlaces; p++) {
			pl = places[p];
			if (pl.statsLevel > 0) {
				if (pl instanceof QPlace) {
					aggrStats[p * 2] = new AggregateStats(pl.id, pl.name, Stats.QUE_PLACE_QUEUE, pl.numColors, pl.statsLevel);
					aggrStats[(p * 2) + 1] = new AggregateStats(pl.id, pl.name, Stats.QUE_PLACE_DEP, pl.numColors, pl.statsLevel);
				} else {
					aggrStats[p * 2] = new AggregateStats(pl.id, pl.name, Stats.ORD_PLACE, pl.numColors, pl.statsLevel);
					aggrStats[(p * 2) + 1] = null;
				}
			} else {
				aggrStats[p * 2] = null;
				aggrStats[(p * 2) + 1] = null;
			}
		}
		
		/*  CONFIG: 
		 *  Set signLevAvgST for AggregateStats here:
		 *  Should be configurable only for places with statsLevel >= 3 !
		 *
		 *  if (!(places[p] instanceof QPlace)) {								// ORDINARY PLACE
		 *     For every color c, set aggrStats[p * 2].signLevAvgST[c]                   
		 *  }
		 *  else {																		// QUEUEING PLACE
		 *     For every color c, set aggrStats[p * 2].signLevAvgST[c]                  //   - QUEUE
		 *     For every color c, set aggrStats[(p * 2) + 1].signLevAvgST[c]            //   - DEPOSITORY
		 *  }
		 *  
		 */		
		// Iterate through all places.
		Iterator placeIterator = placeList.iterator();		
		for (int p = 0; placeIterator.hasNext(); p++) {
			Element place = (Element) placeIterator.next();
			pl = places[p];
			if (pl.statsLevel >= 3) {
				XPath xpathSelector = createXPath("color-refs/color-ref");
				Iterator colorRefIterator = xpathSelector.selectNodes(place).iterator();
				for (int c = 0; colorRefIterator.hasNext(); c++) {
					Element colorRef = (Element) colorRefIterator.next();
					Element element = getSettings(colorRef, configuration);						
					if (pl instanceof QPlace) {													
						if(element == null || element.attributeValue("queueSignLevAvgST") == null) {								
							log.error(formatDetailMessage(
									"queueSignLevAvgST settings for the Replication/Deletion Method not found!",
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"), 
									"color-num", Integer.toString(c),
									"colorRef.id", colorRef.attributeValue("id"),
									"colorRef.color-id", colorRef.attributeValue("color-id")
									));
							throw new SimQPNException();							
						}								
						aggrStats[p * 2].signLevAvgST[c] = Double.parseDouble(element.attributeValue("queueSignLevAvgST"));
						log.debug("aggrStats[" + p * 2 + "].signLevAvgST[" + c + "] = " + aggrStats[p * 2].signLevAvgST[c] + " (queue)");
					}
					if (element == null || element.attributeValue("signLevAvgST") == null) {
						log.error(formatDetailMessage(
								"signLevAvgST settings for the Replication/Deletion Method not found!",
								"place.id", place.attributeValue("id"),
								"place.name", place.attributeValue("name"), 
								"color-num", Integer.toString(c),
								"colorRef.id", colorRef.attributeValue("id"),
								"colorRef.color-id", colorRef.attributeValue("color-id")
								));
						throw new SimQPNException();
					}						
					if (pl instanceof QPlace) {
						aggrStats[(p * 2) + 1].signLevAvgST[c] = Double.parseDouble(element.attributeValue("signLevAvgST"));
						log.debug("aggrStats[" + (p * 2) + 1 + "].signLevAvgST[" + c + "] = " + aggrStats[(p * 2) + 1].signLevAvgST[c] + " (depository)");						
					}
					else {
						aggrStats[p * 2].signLevAvgST[c] = Double.parseDouble(element.attributeValue("signLevAvgST"));
						log.debug("aggrStats[" + p * 2 + "].signLevAvgST[" + c + "] = " + aggrStats[p * 2].signLevAvgST[c] + " (ordinary place)");						
					}
				}
			}
		}

		progressMonitor.startSimulation();

		// Run replication loop
		for (int i = 0; i < numRuns; i++) {
			progressMonitor.startSimulationRun(i + 1);
			sim.run();
			progressMonitor.finishSimulationRun();

			for (int p = 0; p < numPlaces; p++) {
				pl = places[p];
				if (pl.statsLevel > 0) {
					if (pl instanceof QPlace) {
						QPlace qPl = (QPlace) pl;
						aggrStats[p * 2].saveStats(qPl.qPlaceQueueStats);
						aggrStats[(p * 2) + 1].saveStats(qPl.placeStats);
					} else {
						aggrStats[p * 2].saveStats(pl.placeStats);
					}
				}
			}

			if (progressMonitor.isCanceled())
				break;

			sim = new SimQPNControler(netXML, configuration);
			places = sim.net.getPlaceArray();
		}

		progressMonitor.finishSimulation();

		for (int i = 0; i < 2 * numPlaces; i++)
			if (aggrStats[i] != null)
				aggrStats[i].finish();
		
		progressMonitor = null;

		return aggrStats;
	}

	/**
	 * Method runWelchMtd - runs the method of Welch
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public static AggregateStats[] runWelchMtd(Element net, String configuration, SimulatorProgress monitor) throws SimQPNException {

		if (numRuns < 5) {
			log.warn(formatMultilineMessage(
					"Number of runs for the method of Welch should be at least 5!",
					"Setting numRuns to 5."
					));
			numRuns = 5;
		}
		
		progressMonitor = monitor;

		SimQPNControler sim = new SimQPNControler(net, configuration);

		int numPlaces = sim.net.getNumPlaces();
		Place[] places = sim.net.getPlaceArray();
		List placeList = sim.net.getPlaceList();

		AggregateStats[] aggrStats = new AggregateStats[numPlaces * 2]; 
		// Note: aggrStats should be large enough to accomodate 2 stats per place in case all places are queueing places.

		Place pl;

		for (int p = 0; p < numPlaces; p++) {
			pl = places[p];
			if (pl.statsLevel > 0) {
				if (pl instanceof QPlace) {
					aggrStats[p * 2] = new AggregateStats(pl.id, pl.name, Stats.QUE_PLACE_QUEUE, pl.numColors, pl.statsLevel);
					aggrStats[(p * 2) + 1] = new AggregateStats(pl.id, pl.name, Stats.QUE_PLACE_DEP, pl.numColors, pl.statsLevel);
				} else {
					aggrStats[p * 2] = new AggregateStats(pl.id, pl.name, Stats.ORD_PLACE, pl.numColors, pl.statsLevel);
					aggrStats[(p * 2) + 1] = null;
				}
			} else {
				aggrStats[p * 2] = null;
				aggrStats[(p * 2) + 1] = null;
			}
		}

		progressMonitor.startSimulation();

		// Run replication loop
		for (int i = 0; i < numRuns; i++) {
			/* BEGIN-CONFIG
			 * ----------------------------------------------------------------------------------------------------------------
			 * 
			 * minObsrvST - Minumum number of observations required 
			 * maxObsrvST - Maximum number of observations considered (if <= 0 token color is not considered in the analysis).
			 * 
			 * ... places[p].placeStats.minObsrvST/maxObsrvST
			 * ... ((QPlace) places[p]).qPlaceQueueStats[c]/maxObsrvST[c]
			 * 
			 * Note: Places/QPlaces with (StatsLevel < 3) are not considered in the analysis!
			 * Note: If (maxObsrvST[c] <= 0) the respective token color is not considered in the analysis!
			 * 
			 */

			// Iterate through all places.
			Iterator placeIterator = placeList.iterator();
			
			/* SDK-DEBUG: 
			 *   - Does placeList.iterator() always return places in the same order (matching the order in the places array)?
			 * CHRIS: It returns the Elements in the document order. If this is not changed, then it always returns them in the same order.
			 *   - Note that XML in general does not guarantee any ordering of elements.
			 * CHRIS: In general there is no guarantee, but the dom implementation used does respect the order of elements. The only problems I encountered
			 * was when merging sets of nodes, but since I don't do that, there should be no problems. 
			 *    
			 */
			for (int p = 0; placeIterator.hasNext(); p++) {
				Element place = (Element) placeIterator.next();
				pl = places[p];
				// If the stats-level is 3 or higher set minObsrvST and maxObsrvST for each 
				// color-ref of the current place (depository and queue). 
				// These values are used in WELCH method.
				if (pl.statsLevel >= 3) {
					XPath xpathSelector = createXPath("color-refs/color-ref");
					Iterator colorRefIterator = xpathSelector.selectNodes(place).iterator();
					for (int c = 0; colorRefIterator.hasNext(); c++) {
						Element colorRef = (Element) colorRefIterator.next();
						Element element = getSettings(colorRef, configuration);						
						if (element == null || element.attributeValue("minObsrv") == null || element.attributeValue("maxObsrv") == null) {
							log.error(formatDetailMessage(
									"minObsrv/maxObsrv settings for the Method of Welch not found!",
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"), 
									"color-num", Integer.toString(c),
									"colorRef.id", colorRef.attributeValue("id"),
									"colorRef.color-id", colorRef.attributeValue("color-id")
									));
							throw new SimQPNException();
						}						
						pl.placeStats.minObsrvST[c] = Integer.parseInt(element.attributeValue("minObsrv")); 
						pl.placeStats.maxObsrvST[c] = Integer.parseInt(element.attributeValue("maxObsrv")); 
						log.debug("pl.placeStats.minObsrvST[" + c + "] = " + pl.placeStats.minObsrvST[c]);
						log.debug("pl.placeStats.maxObsrvST[" + c + "] = " + pl.placeStats.maxObsrvST[c]);
						if (pl instanceof QPlace) {
							QPlace qPl = (QPlace) pl;							
							if(element.attributeValue("queueMinObsrv") == null || element.attributeValue("queueMaxObsrv") == null) {								
								log.error(formatDetailMessage(
										"queueMinObsrv/queueMaxObsrv settings for the Method of Welch not found!",
										"place.id", place.attributeValue("id"),
										"place.name", place.attributeValue("name"), 
										"color-num", Integer.toString(c),
										"colorRef.id", colorRef.attributeValue("id"),
										"colorRef.color-id", colorRef.attributeValue("color-id")
										));
								throw new SimQPNException();							
							}							
							qPl.qPlaceQueueStats.minObsrvST[c] = Integer.parseInt(element.attributeValue("queueMinObsrv"));							
							qPl.qPlaceQueueStats.maxObsrvST[c] = Integer.parseInt(element.attributeValue("queueMaxObsrv"));  
							log.debug("qPl.qPlaceQueueStats.minObsrvST[" + c + "] = " + qPl.qPlaceQueueStats.minObsrvST[c]);
							log.debug("qPl.qPlaceQueueStats.maxObsrvST[" + c + "] = " + qPl.qPlaceQueueStats.maxObsrvST[c]);
						}
					}
				}
			}			
			// END-CONFIG
			// -----------------------------------------------------------------------------------------


			progressMonitor.startSimulationRun(i + 1);
			sim.run();
			progressMonitor.finishSimulationRun();

			for (int p = 0; p < numPlaces; p++) {
				pl = places[p];
				if (pl.statsLevel > 0) {
					if (pl instanceof QPlace) {
						QPlace qPl = (QPlace) pl;
						aggrStats[p * 2].saveStats(qPl.qPlaceQueueStats);
						aggrStats[(p * 2) + 1].saveStats(qPl.placeStats);
					} else {
						aggrStats[p * 2].saveStats(pl.placeStats);
					}
				}
			}

			if (progressMonitor.isCanceled())
				break;

			sim = new SimQPNControler(net, configuration);
			places = sim.net.getPlaceArray();
		}

		progressMonitor.finishSimulation();

		for (int i = 0; i < 2 * numPlaces; i++)
			if (aggrStats[i] != null)
				aggrStats[i].finish();

		progressMonitor = null;
		
		return aggrStats;
	}

	
	/**
	/*
	 * Method runCvrgEst - implements coverage estimation for the batch means method
	 * 
	 *
	public static void runCvrgEst() {

		System.out.println("---------------------------------------------");						
		System.out.println(" Starting CVRG_EST Method                    ");		
		System.out.println("---------------------------------------------");

		if (!useStdStateStats)  {
			System.out.println("Setting useStdStateStats to true");				
			useStdStateStats = true;
		}			
			
		if (analMethod != BATCH_MEANS)  { 
			System.out.println("Error: Coverage estimation only supported for the method of batch means!");
			System.exit(-1);
		}				
		
		if (stoppingRule != FIXEDLEN)  { 
			System.out.println("Warning: Coverage estimation should be used with RELPRC stopping rule!");			
		}
				
		Simulator sim = new Simulator();		
		sim.getReady();
						
		int numPlaces = sim.numPlaces;
		Place[]	places = sim.places;
		AggregateStats[] aggrStats = new AggregateStats[numPlaces*2];  // Should be large enough to accomodate 2 stats per place in case all places are queueing places
		Place pl;			
														
		for (int p=0; p < numPlaces; p++)  {
			pl = places[p];
			if (pl.statsLevel > 0)  {					 
				if (pl instanceof QPlace) {
					aggrStats[p*2] 		= new AggregateStats(pl.id, pl.name, Stats.QUE_PLACE_QUEUE, pl.numColors, pl.statsLevel);
					aggrStats[(p*2)+1]	= new AggregateStats(pl.id, pl.name, Stats.QUE_PLACE_DEP, pl.numColors, pl.statsLevel);																	
				}
				else {
					aggrStats[p*2] 		= new AggregateStats(pl.id, pl.name, Stats.ORD_PLACE, pl.numColors, pl.statsLevel);
					aggrStats[(p*2)+1]	= null;
				}					 
			}
			else {
				aggrStats[p*2] 		= null;
				aggrStats[(p*2)+1] 	= null;
			}
		}
		 
		// BEGIN-CONFIG
		
		// NOTE: Tokens whose trueAvgST is negative are not considered in the coverage analysis!
		// NOTE: If no steady state statistics are collected for a token color (i.e. minBatches[c] <= 0),
		//       trueAvgST should be set to -1 (or other negative number)!
		// NOTE: If the soj. time of a token is deterministic you must set its trueAvgST[0] to -1 (or other negative number)! 
		//       Otherwise the coverage estimation process would loop forever waiting for reqMinBadCIs to be reached!
		   
		// Init trueAvgST for AggregateStats:

		  // Client
		  aggrStats[0].trueAvgST[0] = 200;
		  aggrStats[1].trueAvgST[0] = 1199.9709;

		  // WLS-CPU
		  aggrStats[2].trueAvgST[0] = 3967.2524;
		  aggrStats[3].trueAvgST[0] = -1;
		  // DBS-CPU
		  aggrStats[6].trueAvgST[0] = 218.1469;
		  aggrStats[7].trueAvgST[0] = -1;

		  // DBS-I/O
		  aggrStats[8].trueAvgST[0] = 14.4828;
		  aggrStats[9].trueAvgST[0] = -1;

		
// PEPSY-EXAMPLE1 
//		// Terminals
//		aggrStats[0].trueAvgST[0] = 10000;
//		aggrStats[0].trueAvgST[1] = 10000;
//		aggrStats[1].trueAvgST[0] = -1;
//		aggrStats[1].trueAvgST[1] = -1;
//		// CPU
//		aggrStats[2].trueAvgST[0] = 476.73673;
//		aggrStats[2].trueAvgST[1] = 588.69769;
//		aggrStats[3].trueAvgST[0] = -1;
//		aggrStats[3].trueAvgST[1] = -1;
//		//Disk1
//		aggrStats[4].trueAvgST[0] = 2055;
//		aggrStats[4].trueAvgST[1] = 2056;
//		aggrStats[5].trueAvgST[0] = -1;
//		aggrStats[5].trueAvgST[1] = -1;
//		//Disk2
//		aggrStats[6].trueAvgST[0] = 513.6108;
//		aggrStats[6].trueAvgST[1] = 513.6098;
//		aggrStats[7].trueAvgST[0] = -1;
//		aggrStats[7].trueAvgST[1] = -1;
//		//Disk3
//		aggrStats[8].trueAvgST[0] = 40857;
//		aggrStats[8].trueAvgST[1] = 40941;
//		aggrStats[9].trueAvgST[0] = -1;
//		aggrStats[9].trueAvgST[1] = -1; 

// PEPSY-EXAMPLE2-heavy load
//		// Terminals
//		aggrStats[0].trueAvgST[0] = 5000;
//		aggrStats[0].trueAvgST[1] = 5000;
//		aggrStats[1].trueAvgST[0] = -1;
//		aggrStats[1].trueAvgST[1] = -1;
//		// CPU
//		aggrStats[2].trueAvgST[0] = 590.91;
//		aggrStats[2].trueAvgST[1] = 725.72;
//		aggrStats[3].trueAvgST[0] = -1;
//		aggrStats[3].trueAvgST[1] = -1;
//		//Disk1
//		aggrStats[4].trueAvgST[0] = 2403;
//		aggrStats[4].trueAvgST[1] = 2405;
//		aggrStats[5].trueAvgST[0] = -1;
//		aggrStats[5].trueAvgST[1] = -1;
//		//Disk2
//		aggrStats[6].trueAvgST[0] = 517.537;
//		aggrStats[6].trueAvgST[1] = 517.554;
//		aggrStats[7].trueAvgST[0] = -1;
//		aggrStats[7].trueAvgST[1] = -1;
//		//Disk3
//		aggrStats[8].trueAvgST[0] = 72563;
//		aggrStats[8].trueAvgST[1] = 72859;
//		aggrStats[9].trueAvgST[0] = -1;
//		aggrStats[9].trueAvgST[1] = -1; 


		// If needed set signLevAvgST[c] for AggregateStats here (otherwise default values used)

		// If needed set useFdistr, reqMinBadCIs, signLevCvrg and reqCvrgAbsPrc for AggregateStats here (otherwise default values used)				
		
		for (int i=0; i < numPlaces*2; i++)  
			if (aggrStats[i] != null)  { 			
				aggrStats[i].useFdistr		= true; 
				aggrStats[i].reqCvrgAbsPrc	= 0.05;
				aggrStats[i].reqMinBadCIs	= 200; 
				aggrStats[i].signLevCvrg	= 0.05;			
			}								

		// END-CONFIG
		
		boolean done = false;
								
		// Run replication loop			
		for (int i=1; i <= numRuns && !done; i++) {
			System.out.println("Starting Run " + i);				
			sim.run();
			
			// Check if enough data to provide c.i. for coverage with required precision				
			if (i % runsBtwCvrgChks == 0) done = true;
										
//			System.out.println("Checking if done");
			for (int p=0; p < numPlaces; p++) {
				pl = places[p];
				if (pl.statsLevel > 0) {	
					if (pl instanceof QPlace) {
						QPlace qPl = (QPlace) pl; 
						aggrStats[p*2].saveStats(qPl.qPlaceQueueStats);
						aggrStats[(p*2)+1].saveStats(qPl.placeStats);						
						if (i % runsBtwCvrgChks == 0) {																				
							if (!aggrStats[p*2].enoughCvrgStats()) done = false; 
							if (!aggrStats[(p*2)+1].enoughCvrgStats()) done = false;																
						}											
					}
					else {						
						aggrStats[p*2].saveStats(pl.placeStats);						
						if (i % runsBtwCvrgChks == 0) {							 
							if (!aggrStats[p*2].enoughCvrgStats()) done = false;
						}
					}									
				}
			}								
			sim = new Simulator();		
			sim.getReady();
			places = sim.places;
		}
					
		for (int i=0; i < 2 * numPlaces; i++) 
			if (aggrStats[i] != null) aggrStats[i].finish();
			
		for (int i=0; i < 2 * numPlaces; i++) 
			if (aggrStats[i] != null) aggrStats[i].printReport(); 					
		
	}
*/	
		
	private void initializeWorkingVariables() throws SimQPNException {
		inRampUp = true;
		endRampUpClock = 0;
		endRunClock = 0;
		msrmPrdLen = 0; // Set at the end of the run when the actual length is known.
		beginRunWallClock = 0;
		endRunWallClock = 0;
		runWallClockTime = 0;

		clock = 0;	// Note that it has been assumed throughout the code that
		   			// the simulation starts at virtual time 0.

		eventList.clear();
		// eventList = new LinkedList();  // Old LinkedList implementation of the event list.
		
		// Make sure clock has been initialized before calling init below
		// Call places[i].init() first and then thans[i].init() and queues[i].init() 
		for (int i = 0; i < net.getNumProbes(); i++)
			net.getProbe(i).init();
		for (int i = 0; i < net.getNumPlaces(); i++)
			net.getPlace(i).init();
		for (int i = 0; i < net.getNumTrans(); i++)
			net.getTrans(i).init();
		for (int i = 0; i < net.getNumQueues(); i++)
			net.getQueue(i).init();
	}

	private void configureBatchMeansMethod() throws SimQPNException {
		/*
		 * "Advanced Configuration Options" only applicable to the BATCH_MEANS method
		 * 
		 * These options are configurable for each place/queue/depository that
		 * has statsLevel >= 3. Options should be displayed only if BATCH_MEANS
		 * is chosen and in this case only for places that have statsLevel set
		 * to be >= 3.
		 * 
		 * double signLevST - Required significance level. 
		 * double reqAbsPrc - Used if stoppingRule=ABSPRC: Required absolute precision (max c.i. half length). 
		 * double reqRelPrc - Used if stoppingRule=RELPRC: Required relative precision (max ratio of c.i. half length to mean).
		 * int batchSizeST - Batch size for the batch means algorithm.
		 * int minBatches - Minimum number of batches required for steady state statistics. If minBatches[c] <= 0, no steady state statistics are collected for this color!
		 * int numBMeansCorlTested - If > 0 checks whether the batch size is sufficient - the first numBMeansCorlTested batch means from the beginning of steady state are tested for autocorrelation.
		 * 
		 * NOTE: reqAbsPrc should be available only if stoppingRule=ABSPRC; reqRelPrc should be available only if stoppingRule=RELPRC.
		 * 
		 * Check and make sure that numBMeansCorlTested is even!
		 */

		// CONFIG: BATCH_MEANS Method Initialization Parameters
		if (analMethod == BATCH_MEANS)  {
			Iterator placeIterator; placeIterator = net.getPlaceList().iterator();
			for (int p = 0; placeIterator.hasNext(); p++) {
				Element place = (Element) placeIterator.next();
				Place pl = net.getPlace(p);
				if (pl.statsLevel >= 3) {
					log.debug("places[" + p + "]");
					XPath xpathSelector = createXPath("color-refs/color-ref");
					Iterator colorRefIterator = xpathSelector.selectNodes(place).iterator();
					for (int cr = 0; colorRefIterator.hasNext(); cr++) {
						Element colorRef = (Element) colorRefIterator.next();
						Element colorRefSettings = getSettings(colorRef, net.getConfiguration());
						// Initialize Place (or Depository if pl is QPlace)
						if (colorRefSettings != null) {
							if (colorRefSettings.attributeValue("signLev") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"signLev\" for Batch Means Method is missing!",
										"configuration", net.getConfiguration(),						
										"place-num", Integer.toString(p),
										"place.id", place.attributeValue("id"),
										"place.name", place.attributeValue("name"),						
										"colorRef-num", Integer.toString(cr),
										"colorRef.id", colorRef.attributeValue("id"),
										"colorRef.color-id", colorRef.attributeValue("color-id")
										));
								throw new SimQPNException();
							}
							pl.placeStats.signLevST[cr] = Double.parseDouble(colorRefSettings.attributeValue("signLev"));
							log.debug("-- placeStats.signLevST[" + cr + "] = " + pl.placeStats.signLevST[cr]);							

							if (colorRefSettings.attributeValue("reqAbsPrc") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"reqAbsPrc\" for Batch Means Method is missing!",
										"configuration", net.getConfiguration(),
										"place-num", Integer.toString(p),
										"place.id", place.attributeValue("id"),
										"place.name", place.attributeValue("name"),						
										"colorRef-num", Integer.toString(cr),
										"colorRef.id", colorRef.attributeValue("id"),
										"colorRef.color-id", colorRef.attributeValue("color-id")
										));
								throw new SimQPNException();
							}
							pl.placeStats.reqAbsPrc[cr] = Double.parseDouble(colorRefSettings.attributeValue("reqAbsPrc"));
							log.debug("-- placeStats.reqAbsPrc[" + cr + "] = " + pl.placeStats.reqAbsPrc[cr]);
							
							if (colorRefSettings.attributeValue("reqRelPrc") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"reqRelPrc\" for Batch Means Method is missing!",
										"configuration", net.getConfiguration(),
										"place-num", Integer.toString(p),
										"place.id", place.attributeValue("id"),
										"place.name", place.attributeValue("name"),
										"colorRef-num", Integer.toString(cr),
										"colorRef.id", colorRef.attributeValue("id"),
										"colorRef.color-id", colorRef.attributeValue("color-id")
										));
								throw new SimQPNException();
							}
							pl.placeStats.reqRelPrc[cr] = Double.parseDouble(colorRefSettings.attributeValue("reqRelPrc"));
							log.debug("-- placeStats.reqRelPrc[" + cr + "] = " + pl.placeStats.reqRelPrc[cr]);

							if (colorRefSettings.attributeValue("batchSize") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"batchSize\" for Batch Means Method is missing!",
										"configuration", net.getConfiguration(),
										"place-num", Integer.toString(p),
										"place.id", place.attributeValue("id"),
										"place.name", place.attributeValue("name"),						
										"colorRef-num", Integer.toString(cr),
										"colorRef.id", colorRef.attributeValue("id"),
										"colorRef.color-id", colorRef.attributeValue("color-id")
										));								
								throw new SimQPNException();
							}
							pl.placeStats.batchSizeST[cr] = Integer.parseInt(colorRefSettings.attributeValue("batchSize"));
							log.debug("-- placeStats.batchSizeST[" + cr + "] = " + pl.placeStats.batchSizeST[cr]);							

							if (colorRefSettings.attributeValue("minBatches") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"minBatches\" for Batch Means Method is missing!",
										"configuration", net.getConfiguration(),
										"place-num", Integer.toString(p),
										"place.id", place.attributeValue("id"),
										"place.name", place.attributeValue("name"),						
										"colorRef-num", Integer.toString(cr),
										"colorRef.id", colorRef.attributeValue("id"),
										"colorRef.color-id", colorRef.attributeValue("color-id")
										));
								throw new SimQPNException();
							}
							pl.placeStats.minBatches[cr] = Integer.parseInt(colorRefSettings.attributeValue("minBatches"));
							log.debug("-- placeStats.minBatches[" + cr + "] = " + pl.placeStats.minBatches[cr]);							

							if (colorRefSettings.attributeValue("numBMeansCorlTested") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"numBMeansCorlTested\" for Batch Means Method is missing!",
										"configuration", net.getConfiguration(),
										"place-num", Integer.toString(p),
										"place.id", place.attributeValue("id"),
										"place.name", place.attributeValue("name"),						
										"colorRef-num", Integer.toString(cr),
										"colorRef.id", colorRef.attributeValue("id"),
										"colorRef.color-id", colorRef.attributeValue("color-id")
										));																								
								throw new SimQPNException();
							}
							pl.placeStats.numBMeansCorlTested[cr] = Integer.parseInt(colorRefSettings.attributeValue("numBMeansCorlTested"));
							log.debug("-- placeStats.numBMeansCorlTested[" + cr + "] = " + pl.placeStats.numBMeansCorlTested[cr]);
							
							// Note that if (minBatches > 0 && numBMeansCorlTested[c] > 0),
							// minBatches[c] must be > numBMeansCorlTested[c]!
							if (pl.placeStats.minBatches[cr] > 0 && 
								pl.placeStats.numBMeansCorlTested[cr] > 0 && 
								!(pl.placeStats.minBatches[cr] > pl.placeStats.numBMeansCorlTested[cr])) {
								log.error(formatDetailMessage(
										"Place.placeStats.minBatches[c] must be greater than Place.placeStats.numBMeansCorlTested[c]!",
										"configuration", net.getConfiguration(),
										"place-num", Integer.toString(p),
										"place.id", place.attributeValue("id"),
										"place.name", place.attributeValue("name"),						
										"colorRef-num", Integer.toString(cr),
										"colorRef.id", colorRef.attributeValue("id"),
										"colorRef.color-id", colorRef.attributeValue("color-id")
										));																								
								throw new SimQPNException();
							}
							// If (numBMeansCorlTested[c] <= 0) no correlation test is done!
							// Note that numBMeansCorlTested must be even!
							if (pl.placeStats.numBMeansCorlTested[cr] % 2 != 0) {
								log.error(formatDetailMessage(
										"Place.placeStats.numBMeansCorlTested[c] must be even!",
										"configuration", net.getConfiguration(),
										"place-num", Integer.toString(p),
										"place.id", place.attributeValue("id"),
										"place.name", place.attributeValue("name"),						
										"colorRef-num", Integer.toString(cr),
										"colorRef.id", colorRef.attributeValue("id"),
										"colorRef.color-id", colorRef.attributeValue("color-id")
										));																								
								throw new SimQPNException();
							}

							if (pl.statsLevel >= 4) {
								if (colorRefSettings.attributeValue("bucketSize") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"bucketSize\" for Batch Means Method is missing!",
											"configuration", net.getConfiguration(),
											"place-num", Integer.toString(p),
											"place.id", place.attributeValue("id"),
											"place.name", place.attributeValue("name"),
											"colorRef-num", Integer.toString(cr),
											"colorRef.id", colorRef.attributeValue("id"),
											"colorRef.color-id", colorRef.attributeValue("color-id")
											));
									throw new SimQPNException();
								}
								pl.placeStats.histST[cr].setBucketSize(Double.parseDouble(colorRefSettings.attributeValue("bucketSize")));
								log.debug("-- placeStats.histST[" + cr + "].bucketSize = " + pl.placeStats.histST[cr].getBucketSize());

								if(colorRefSettings.attributeValue("maxBuckets") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"maxBuckets\" for Batch Means Method is missing!",
											"configuration", net.getConfiguration(),
											"place-num", Integer.toString(p),
											"place.id", place.attributeValue("id"),
											"place.name", place.attributeValue("name"),
											"colorRef-num", Integer.toString(cr),
											"colorRef.id", colorRef.attributeValue("id"),
											"colorRef.color-id", colorRef.attributeValue("color-id")
											));
									throw new SimQPNException();
								}
								pl.placeStats.histST[cr].setMaximumNumberOfBuckets(Integer.parseInt(colorRefSettings.attributeValue("maxBuckets")));
								log.debug("-- placeStats.histST[" + cr + "].maxBuckets = " + pl.placeStats.histST[cr].getMaximumNumberOfBuckets());
							}
						} else {
							log.error(formatDetailMessage(
									"SimQPN configuration parameters for Batch Means Method are missing!",
									"configuration", net.getConfiguration(),						
									"place-num", Integer.toString(p),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),						
									"colorRef-num", Integer.toString(cr),
									"colorRef.id", colorRef.attributeValue("id"),
									"colorRef.color-id", colorRef.attributeValue("color-id")
									));
							throw new SimQPNException();
							/* ORIGINAL MANUAL CONFIGURATION
							pl.placeStats.signLevST[cr] = 0.05;     // e.g. 0.05 ---> 95% c.i.; 0.1 ---> 90%
							logln(2, "-- placeStats.signLevST[" + cr + "] = " + pl.placeStats.signLevST[cr]);							
							pl.placeStats.reqAbsPrc[cr] = 50;
							logln(2, "-- placeStats.reqAbsPrc[" + cr + "] = " + pl.placeStats.reqAbsPrc[cr]);							
							pl.placeStats.reqRelPrc[cr] = 0.05;		// e.g. 0.1 ---> 10% relative precision
							logln(2, "-- placeStats.reqRelPrc[" + cr + "] = " + pl.placeStats.reqRelPrc[cr]);								 
							pl.placeStats.batchSizeST[cr] = 200;    // Initial batch size
							logln(2, "-- placeStats.batchSizeST[" + cr + "] = " + pl.placeStats.batchSizeST[cr]);							
							pl.placeStats.minBatches[cr] = 60; 
								// Note that if (minBatches > 0 && numBMeansCorlTested[c] > 0),
								// minBatches[c] must be > numBMeansCorlTested[c]!
							logln(2, "-- placeStats.minBatches[" + cr + "] = " + pl.placeStats.minBatches[cr]);	 
							pl.placeStats.numBMeansCorlTested[cr] = 50; 
								// Note that numBMeansCorlTested must be even!
								// If (numBMeansCorlTested[c] <= 0) no correlation test is done!
							logln(2, "-- placeStats.numBMeansCorlTested[" + cr + "] = " + pl.placeStats.numBMeansCorlTested[cr]);
							
						    */
						}

						// Initialize Queue if pl is QPlace
						if (pl instanceof QPlace) {
							QPlace qpl = (QPlace) pl;
							if (colorRefSettings != null) {
								if (colorRefSettings.attributeValue("queueSignLev") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"queueSignLev\" for Batch Means Method is missing!",
											"configuration", net.getConfiguration(),						
											"place-num", Integer.toString(p),
											"place.id", place.attributeValue("id"),
											"place.name", place.attributeValue("name"),						
											"colorRef-num", Integer.toString(cr),
											"colorRef.id", colorRef.attributeValue("id"),
											"colorRef.color-id", colorRef.attributeValue("color-id")
											));
									throw new SimQPNException();
								}								
								qpl.qPlaceQueueStats.signLevST[cr] = Double.parseDouble(colorRefSettings.attributeValue("queueSignLev"));
								log.debug("-- qPlaceQueueStats.signLevST[" + cr + "] = " + qpl.qPlaceQueueStats.signLevST[cr]);

								if (colorRefSettings.attributeValue("queueReqAbsPrc") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"queueReqAbsPrc\" for Batch Means Method is missing!",
											"configuration", net.getConfiguration(),						
											"place-num", Integer.toString(p),
											"place.id", place.attributeValue("id"),
											"place.name", place.attributeValue("name"),						
											"colorRef-num", Integer.toString(cr),
											"colorRef.id", colorRef.attributeValue("id"),
											"colorRef.color-id", colorRef.attributeValue("color-id")
											));
									throw new SimQPNException();
								}
								qpl.qPlaceQueueStats.reqAbsPrc[cr] = Double.parseDouble(colorRefSettings.attributeValue("queueReqAbsPrc"));
								log.debug("-- qPlaceQueueStats.reqAbsPrc[" + cr + "] = " + qpl.qPlaceQueueStats.reqAbsPrc[cr]);

								if (colorRefSettings.attributeValue("queueReqRelPrc") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"queueReqRelPrc\" for Batch Means Method is missing!",
											"configuration", net.getConfiguration(),						
											"place-num", Integer.toString(p),
											"place.id", place.attributeValue("id"),
											"place.name", place.attributeValue("name"),						
											"colorRef-num", Integer.toString(cr),
											"colorRef.id", colorRef.attributeValue("id"),
											"colorRef.color-id", colorRef.attributeValue("color-id")
											));
									throw new SimQPNException();
								}
								qpl.qPlaceQueueStats.reqRelPrc[cr] = Double.parseDouble(colorRefSettings.attributeValue("queueReqRelPrc"));
								log.debug("-- qPlaceQueueStats.reqRelPrc[" + cr + "] = " + qpl.qPlaceQueueStats.reqRelPrc[cr]);
								
								if (colorRefSettings.attributeValue("queueBatchSize") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"queueBatchSize\" for Batch Means Method is missing!",
											"configuration", net.getConfiguration(),						
											"place-num", Integer.toString(p),
											"place.id", place.attributeValue("id"),
											"place.name", place.attributeValue("name"),						
											"colorRef-num", Integer.toString(cr),
											"colorRef.id", colorRef.attributeValue("id"),
											"colorRef.color-id", colorRef.attributeValue("color-id")
											));
									throw new SimQPNException();
								}								
								qpl.qPlaceQueueStats.batchSizeST[cr] = Integer.parseInt(colorRefSettings.attributeValue("queueBatchSize"));
								log.debug("-- qPlaceQueueStats.batchSizeST[" + cr + "] = " + qpl.qPlaceQueueStats.batchSizeST[cr]);

								if(colorRefSettings.attributeValue("queueMinBatches") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"queueMinBatches\" for Batch Means Method is missing!",
											"configuration", net.getConfiguration(),						
											"place-num", Integer.toString(p),
											"place.id", place.attributeValue("id"),
											"place.name", place.attributeValue("name"),						
											"colorRef-num", Integer.toString(cr),
											"colorRef.id", colorRef.attributeValue("id"),
											"colorRef.color-id", colorRef.attributeValue("color-id")
											));																																
									throw new SimQPNException();
								}
								qpl.qPlaceQueueStats.minBatches[cr] = Integer.parseInt(colorRefSettings.attributeValue("queueMinBatches"));
								log.debug("-- qPlaceQueueStats.minBatches[" + cr + "] = " + qpl.qPlaceQueueStats.minBatches[cr]);								

								if(colorRefSettings.attributeValue("queueNumBMeansCorlTested") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"queueNumBMeansCorlTested\" for Batch Means Method is missing!",
											"configuration", net.getConfiguration(),						
											"place-num", Integer.toString(p),
											"place.id", place.attributeValue("id"),
											"place.name", place.attributeValue("name"),						
											"colorRef-num", Integer.toString(cr),
											"colorRef.id", colorRef.attributeValue("id"),
											"colorRef.color-id", colorRef.attributeValue("color-id")
											));	
									throw new SimQPNException();
								}								
								qpl.qPlaceQueueStats.numBMeansCorlTested[cr] = Integer.parseInt(colorRefSettings.attributeValue("queueNumBMeansCorlTested"));
								log.debug("-- qPlaceQueueStats.numBMeansCorlTested[" + cr + "] = " + qpl.qPlaceQueueStats.numBMeansCorlTested[cr]);								
								
								if (qpl.qPlaceQueueStats.minBatches[cr] > 0 && 
									qpl.qPlaceQueueStats.numBMeansCorlTested[cr] > 0 && 
									!(qpl.qPlaceQueueStats.minBatches[cr] > qpl.qPlaceQueueStats.numBMeansCorlTested[cr])) {
									log.error(formatDetailMessage(
											"QPlace.qPlaceQueueStats.minBatches[c] must be greater than QPlace.qPlaceQueueStats.numBMeansCorlTested[c]!",
											"configuration", net.getConfiguration(),						
											"place-num", Integer.toString(p),
											"place.id", place.attributeValue("id"),
											"place.name", place.attributeValue("name"),						
											"colorRef-num", Integer.toString(cr),
											"colorRef.id", colorRef.attributeValue("id"),
											"colorRef.color-id", colorRef.attributeValue("color-id")
											));																								
									throw new SimQPNException();
								}
								// If (numBMeansCorlTested[c] <= 0) no correlation test is done!								
								// Note that numBMeansCorlTested must be even!
								if (qpl.qPlaceQueueStats.numBMeansCorlTested[cr] % 2 != 0) {
									log.error(formatDetailMessage(
											"QPlace.qPlaceQueueStats.numBMeansCorlTested[c] must be even!",
											"configuration", net.getConfiguration(),						
											"place-num", Integer.toString(p),
											"place.id", place.attributeValue("id"),
											"place.name", place.attributeValue("name"),						
											"colorRef-num", Integer.toString(cr),
											"colorRef.id", colorRef.attributeValue("id"),
											"colorRef.color-id", colorRef.attributeValue("color-id")
											));																								
									throw new SimQPNException();
								}

								if (pl.statsLevel >= 4) {
									if (colorRefSettings.attributeValue("queueBucketSize") == null) {
										log.error(formatDetailMessage(
												"Configuration parameter \"queueBucketSize\" for Batch Means Method is missing!",
												"configuration", net.getConfiguration(),						
												"place-num", Integer.toString(p),
												"place.id", place.attributeValue("id"),
												"place.name", place.attributeValue("name"),						
												"colorRef-num", Integer.toString(cr),
												"colorRef.id", colorRef.attributeValue("id"),
												"colorRef.color-id", colorRef.attributeValue("color-id")
												));	
										throw new SimQPNException();
									}
									qpl.qPlaceQueueStats.histST[cr].setBucketSize(Double.parseDouble(colorRefSettings.attributeValue("queueBucketSize")));
									log.debug("-- qPlaceQueueStats.histST[" + cr + "].bucketSize = " + qpl.qPlaceQueueStats.histST[cr].getBucketSize());

									if(colorRefSettings.attributeValue("queueMaxBuckets") == null) {
										log.error(formatDetailMessage(
												"Configuration parameter \"queueMaxBuckets\" for Batch Means Method is missing!",
												"configuration", net.getConfiguration(),						
												"place-num", Integer.toString(p),
												"place.id", place.attributeValue("id"),
												"place.name", place.attributeValue("name"),						
												"colorRef-num", Integer.toString(cr),
												"colorRef.id", colorRef.attributeValue("id"),
												"colorRef.color-id", colorRef.attributeValue("color-id")
												));
										throw new SimQPNException();
									}
									qpl.qPlaceQueueStats.histST[cr].setMaximumNumberOfBuckets(Integer.parseInt(colorRefSettings.attributeValue("queueMaxBuckets")));
									log.debug("-- qPlaceQueueStats.histST[" + cr + "].maxBuckets = " + qpl.qPlaceQueueStats.histST[cr].getMaximumNumberOfBuckets());
								}
							} else {
								log.error(formatDetailMessage(
										"SimQPN configuration parameters for Batch Means Method are missing!",
										"configuration", net.getConfiguration(),						
										"place-num", Integer.toString(p),
										"place.id", place.attributeValue("id"),
										"place.name", place.attributeValue("name"),						
										"colorRef-num", Integer.toString(cr),
										"colorRef.id", colorRef.attributeValue("id"),
										"colorRef.color-id", colorRef.attributeValue("color-id")
										));
								throw new SimQPNException();
								/* ORIGINAL MANUAL CONFIGURATION
								qpl.qPlaceQueueStats.signLevST[cr] = 0.05;		// e.g. 0.05 ---> 95% c.i.; 0.1 ---> 90%
								logln(2, "-- qPlaceQueueStats.signLevST[" + cr + "] = " + qpl.qPlaceQueueStats.signLevST[cr]);								
								qpl.qPlaceQueueStats.reqAbsPrc[cr] = 50;
								logln(2, "-- qPlaceQueueStats.reqAbsPrc[" + cr + "] = " + qpl.qPlaceQueueStats.reqAbsPrc[cr]);								
								qpl.qPlaceQueueStats.reqRelPrc[cr] = 0.05;		// e.g. 0.1 ---> 10% relative precision
								logln(2, "-- qPlaceQueueStats.reqRelPrc[" + cr + "] = " + qpl.qPlaceQueueStats.reqRelPrc[cr]);								
								qpl.qPlaceQueueStats.batchSizeST[cr] = 200;		// Initial batch size
								logln(2, "-- qPlaceQueueStats.batchSizeST[" + cr + "] = " + qpl.qPlaceQueueStats.batchSizeST[cr]);								
								qpl.qPlaceQueueStats.minBatches[cr] = 60; 
									// Note that if (minBatches > 0 && numBMeansCorlTested[c] > 0),
									// minBatches[c] must be > numBMeansCorlTested[c]!
								logln(2, "-- qPlaceQueueStats.minBatches[" + cr + "] = " + qpl.qPlaceQueueStats.minBatches[cr]);								
								qpl.qPlaceQueueStats.numBMeansCorlTested[cr] = 50; 
									// Note that numBMeansCorlTested must be even!
									// If (numBMeansCorlTested[c] <= 0) no correlation test is done!
								logln(2, "-- qPlaceQueueStats.numBMeansCorlTested[" + cr + "] = " + qpl.qPlaceQueueStats.numBMeansCorlTested[cr]);
							    */
							}
						}
					}
				}
			}
			
			// Configure the probes statistic collection
			Iterator probeIterator = net.getProbeList().iterator();
			for (int p = 0; probeIterator.hasNext(); p++) {
				Element probe = (Element) probeIterator.next();
				Probe pr = net.getProbe(p);
				if (pr.statsLevel >= 3) {
					log.debug("probes[" + p + "]");
					XPath xpathSelector = createXPath("color-refs/color-ref");
					Iterator colorRefIterator = xpathSelector.selectNodes(probe).iterator();
					for (int cr = 0; colorRefIterator.hasNext(); cr++) {
						Element colorRef = (Element) colorRefIterator.next();
						Element colorRefSettings = getSettings(colorRef, net.getConfiguration());
						// Initialize Probe
						if (colorRefSettings != null) {
							if (colorRefSettings.attributeValue("signLev") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"signLev\" for Batch Means Method is missing!",
										"configuration" + net.getConfiguration(),						
										"probe-num" + Integer.toString(p),
										"probe.id" + probe.attributeValue("id"),
										"probe.name" + probe.attributeValue("name"),						
										"colorRef-num" + Integer.toString(cr),
										"colorRef.id" + colorRef.attributeValue("id"),
										"colorRef.color-id" + colorRef.attributeValue("color-id")
										));
								throw new SimQPNException();
							}
							pr.probeStats.signLevST[cr] = Double.parseDouble(colorRefSettings.attributeValue("signLev"));
							log.debug("-- probeStats.signLevST[" + cr + "] = " + pr.probeStats.signLevST[cr]);							

							if (colorRefSettings.attributeValue("reqAbsPrc") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"reqAbsPrc\" for Batch Means Method is missing!",
										"configuration" + net.getConfiguration(),						
										"probe-num" + Integer.toString(p),
										"probe.id" + probe.attributeValue("id"),
										"probe.name" + probe.attributeValue("name"),						
										"colorRef-num" + Integer.toString(cr),
										"colorRef.id" + colorRef.attributeValue("id"),
										"colorRef.color-id" + colorRef.attributeValue("color-id")
										));
								throw new SimQPNException();
							}
							pr.probeStats.reqAbsPrc[cr] = Double.parseDouble(colorRefSettings.attributeValue("reqAbsPrc"));
							log.debug("-- probeStats.reqAbsPrc[" + cr + "] = " + pr.probeStats.reqAbsPrc[cr]);
							
							if (colorRefSettings.attributeValue("reqRelPrc") == null) {								
								log.error(formatDetailMessage(
										"Configuration parameter \"reqRelPrc\" for Batch Means Method is missing!",
										"configuration" + net.getConfiguration(),						
										"probe-num" + Integer.toString(p),
										"probe.id" + probe.attributeValue("id"),
										"probe.name" + probe.attributeValue("name"),						
										"colorRef-num" + Integer.toString(cr),
										"colorRef.id" + colorRef.attributeValue("id"),
										"colorRef.color-id" + colorRef.attributeValue("color-id")
										));
								throw new SimQPNException();
							}
							pr.probeStats.reqRelPrc[cr] = Double.parseDouble(colorRefSettings.attributeValue("reqRelPrc"));
							log.debug("-- probeStats.reqRelPrc[" + cr + "] = " + pr.probeStats.reqRelPrc[cr]);

							if (colorRefSettings.attributeValue("batchSize") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"batchSize\" for Batch Means Method is missing!",
										"configuration" + net.getConfiguration(),						
										"probe-num" + Integer.toString(p),
										"probe.id" + probe.attributeValue("id"),
										"probe.name" + probe.attributeValue("name"),						
										"colorRef-num" + Integer.toString(cr),
										"colorRef.id" + colorRef.attributeValue("id"),
										"colorRef.color-id" + colorRef.attributeValue("color-id")
										));						
								throw new SimQPNException();
							}
							pr.probeStats.batchSizeST[cr] = Integer.parseInt(colorRefSettings.attributeValue("batchSize"));
							log.debug("-- probeStats.batchSizeST[" + cr + "] = " + pr.probeStats.batchSizeST[cr]);							

							if (colorRefSettings.attributeValue("minBatches") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"minBatches\" for Batch Means Method is missing!",
										"configuration" + net.getConfiguration(),						
										"probe-num" + Integer.toString(p),
										"probe.id" + probe.attributeValue("id"),
										"probe.name" + probe.attributeValue("name"),						
										"colorRef-num" + Integer.toString(cr),
										"colorRef.id" + colorRef.attributeValue("id"),
										"colorRef.color-id" + colorRef.attributeValue("color-id")
										));																
								throw new SimQPNException();
							}
							pr.probeStats.minBatches[cr] = Integer.parseInt(colorRefSettings.attributeValue("minBatches"));
							log.debug("-- probeStats.minBatches[" + cr + "] = " + pr.probeStats.minBatches[cr]);							

							if (colorRefSettings.attributeValue("numBMeansCorlTested") == null) {
								log.error(formatDetailMessage(
										"Configuration parameter \"numBMeansCorlTested\" for Batch Means Method is missing!",
										"configuration" + net.getConfiguration(),						
										"probe-num" + Integer.toString(p),
										"probe.id" + probe.attributeValue("id"),
										"probe.name" + probe.attributeValue("name"),						
										"colorRef-num" + Integer.toString(cr),
										"colorRef.id" + colorRef.attributeValue("id"),
										"colorRef.color-id" + colorRef.attributeValue("color-id")
										));																								
								throw new SimQPNException();
							}
							pr.probeStats.numBMeansCorlTested[cr] = Integer.parseInt(colorRefSettings.attributeValue("numBMeansCorlTested"));
							log.debug("-- probeStats.numBMeansCorlTested[" + cr + "] = " + pr.probeStats.numBMeansCorlTested[cr]);
							
							// Note that if (minBatches > 0 && numBMeansCorlTested[c] > 0),
							// minBatches[c] must be > numBMeansCorlTested[c]!
							if (pr.probeStats.minBatches[cr] > 0 && 
								pr.probeStats.numBMeansCorlTested[cr] > 0 && 
								!(pr.probeStats.minBatches[cr] > pr.probeStats.numBMeansCorlTested[cr])) {
								log.error(formatDetailMessage(
										"Probe.probeStats.minBatches[c] must be greater than Probe.probeStats.numBMeansCorlTested[c]!",
										"configuration" + net.getConfiguration(),						
										"probe-num" + Integer.toString(p),
										"probe.id" + probe.attributeValue("id"),
										"probe.name" + probe.attributeValue("name"),						
										"colorRef-num" + Integer.toString(cr),
										"colorRef.id" + colorRef.attributeValue("id"),
										"colorRef.color-id" + colorRef.attributeValue("color-id")
										));																							
								throw new SimQPNException();
							}
							// If (numBMeansCorlTested[c] <= 0) no correlation test is done!
							// Note that numBMeansCorlTested must be even!
							if (pr.probeStats.numBMeansCorlTested[cr] % 2 != 0) {
								log.error(formatDetailMessage(
										"Probe.probeStats.numBMeansCorlTested[c] must be even!",
										"configuration" + net.getConfiguration(),						
										"probe-num" + Integer.toString(p),
										"probe.id" + probe.attributeValue("id"),
										"probe.name" + probe.attributeValue("name"),						
										"colorRef-num" + Integer.toString(cr),
										"colorRef.id" + colorRef.attributeValue("id"),
										"colorRef.color-id" + colorRef.attributeValue("color-id")
										));																									
								throw new SimQPNException();
							}

							if (pr.statsLevel >= 4) {
								if (colorRefSettings.attributeValue("bucketSize") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"bucketSize\" for Batch Means Method is missing!",
											"configuration" + net.getConfiguration(),						
											"probe-num" + Integer.toString(p),
											"probe.id" + probe.attributeValue("id"),
											"probe.name" + probe.attributeValue("name"),						
											"colorRef-num" + Integer.toString(cr),
											"colorRef.id" + colorRef.attributeValue("id"),
											"colorRef.color-id" + colorRef.attributeValue("color-id")
											));	
									throw new SimQPNException();
								}
								pr.probeStats.histST[cr].setBucketSize(Double.parseDouble(colorRefSettings.attributeValue("bucketSize")));
								log.debug("-- probeStats.histST[" + cr + "].bucketSize = " + pr.probeStats.histST[cr].getBucketSize());

								if(colorRefSettings.attributeValue("maxBuckets") == null) {
									log.error(formatDetailMessage(
											"Configuration parameter \"maxBuckets\" for Batch Means Method is missing!",
											"configuration" + net.getConfiguration(),						
											"probe-num" + Integer.toString(p),
											"probe.id" + probe.attributeValue("id"),
											"probe.name" + probe.attributeValue("name"),						
											"colorRef-num" + Integer.toString(cr),
											"colorRef.id" + colorRef.attributeValue("id"),
											"colorRef.color-id" + colorRef.attributeValue("color-id")
											));
									throw new SimQPNException();
								}
								pr.probeStats.histST[cr].setMaximumNumberOfBuckets(Integer.parseInt(colorRefSettings.attributeValue("maxBuckets")));
								log.debug("-- probeStats.histST[" + cr + "].maxBuckets = " + pr.probeStats.histST[cr].getMaximumNumberOfBuckets());
							}
						} else {
							log.error(formatDetailMessage(
									"SimQPN configuration parameters for Batch Means Method are missing!",
									"configuration" + net.getConfiguration(),						
									"probe-num" + Integer.toString(p),
									"probe.id" + probe.attributeValue("id"),
									"probe.name" + probe.attributeValue("name"),						
									"colorRef-num" + Integer.toString(cr),
									"colorRef.id" + colorRef.attributeValue("id"),
									"colorRef.color-id" + colorRef.attributeValue("color-id")
									));
							throw new SimQPNException();
						}
					}
				}
			}
		}
	}

	protected int getNumConnectionsWithTargetId(String id) {
		Integer num = net.getTargetIdToNumConnectionsMap().get(id);
		if (num != null) {
			return num.intValue();
		}

		return 0;
	}

	protected int getNumConnectionsWithSourceId(String id) {
		Integer num = net.getSourceIdToNumConnectionsMap().get(id);
		if (num != null) {
			return num.intValue();
		}

		return 0;
	}

//	private Queue findQueueByXmlId(String xmlId) {
//		for (Queue queue : net.getQueueList()) {
//			if (xmlId.equals(queue.xmlId)) {
//				return queue;
//			}
//		}
//		throw new NoSuchElementException();
//	}
	
	/**
	 * Method scheduleEvent - schedules an event 
	 * 
	 * @param time		- Time at which the event should be processed
	 * @param queue		- Queue involved
	 * @param token		- Token that is to completes service 	
	 * @return 
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	public static void scheduleEvent(double time, Queue queue, Token token) {
		Event ev = new Event(time, queue, token);
		eventList.add(ev);
		queue.nextEvent = ev;
		
		/* Old LinkedList implementation of the event list
		int i = eventList.size() - 1;
		while (i >= 0) {
			Event ev = (Event) eventList.get(i);
			if (ev.time <= time)
				break;
			else
				i--;
		}
		eventList.add(i + 1, new Event(time, queue, token));
		*/
	}

	/**
	 * Method run - starts the simulation run.
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public void run() throws SimQPNException {
			
		// SimonSpinner: TEMP CHANGE
		//		try {
		//			System.in.read();
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		}
		// SimonSpinner: TEMP CHANGE
				
		boolean[] transStatus;					// Transition status: true = enabled, false = disabled
		int enTransCnt = 0;
		int[] enTransIDs = new int[net.getNumTrans()];
		
		EmpiricalWalker randTransGen;		// Random number generator for generating next transition to fire.

		// Initialize transStatus and enTransCnt 		
		transStatus = new boolean[net.getNumTrans()];
		for (int i = 0; i < net.getNumTrans(); i++) {
			if (net.getTrans(i).enabled()) {
				transStatus[i] = true;
				enTransCnt++;
			} else {
				transStatus[i] = false;
			}
		}
		
		// Create randTransGen
		double[] pdf = new double[net.getNumTrans()];
		for (int t = 0; t < net.getNumTrans(); t++)
			pdf[t] = 1;

		randTransGen = new EmpiricalWalker(pdf, Empirical.NO_INTERPOLATION, RandomNumberGenerator.nextRandNumGen());
		// Note: Here we use a default distribution. The actual distribution is set each time before using randTransGen. 		

		// Note: we store totRunLen and rampUpLen in local variables to improve performance of the while loop below.		
		double totRunL = totRunLen;
		double rampUpL = rampUpLen;
		double nextChkAfter = timeBtwChkStops > 0 ? timeBtwChkStops : totRunL; // If secondsBtwChkStops is used, disable checking of stopping criterion
																			   // until beforeInitHeartBeat == false. By setting nextChkAfter = totRunL
																			   // stopping criterion checking is disabled.
		/* ORIGINAL HEARTBEAT IMPLEMENTATION
		double nextChkAfter = timeBtwChkStops > 0 ? timeBtwChkStops : timeInitHeartBeat;
		*/

		beginRunWallClock = System.currentTimeMillis();


		boolean beforeInitHeartBeat = true;		// Flag indicating when we are still before the first heart beat (progress update).
												//   If true, the value of timeBtwHeartBeats is still measured, and set to 0.
		double nextHeartBeat = 0.0;				// Simulation run time of the next heart beat.
		double timeBtwHeartBeats = 0.0;			// How often progress updates are made (in logical simulation time units).
		long lastTimeMsrm = System.currentTimeMillis();		// The value of the last wall clock time measurement. Used for progress updates.
		double maxProgressInterval = progressMonitor.getMaxUpdateLogicalTimeInterval();
		long progressUpdateRate = progressMonitor.getMaxUpdateRealTimeInterval();
		
		// BEGIN MAIN SIMULATION LOOP ---------------------------------------------------------------------------------
		while (clock < totRunL) { 

			if (inRampUp && clock > rampUpL) {
				inRampUp = false;
				endRampUpClock = clock;
				if (analMethod == WELCH)
					break;
				for (int p = 0; p < net.getNumPlaces(); p++)
					net.getPlace(p).start();
				for (int q = 0; q < net.getNumQueues(); q++)
					net.getQueue(q).start();
				for (int pr = 0; pr < net.getNumProbes(); pr++)
					net.getProbe(pr).start();

				progressMonitor.finishWarmUp();
			}

			// Step 1: Fire until no transitions are enabled.
			int fireCnt = 0;
			while (enTransCnt > 0) {				
				Transition nextTrans;		// transition to fire next

				if (enTransCnt == 1) {				
					nextTrans = null;
					for (int t = 0; t < net.getNumTrans(); t++) {
						if (transStatus[t]) {
							nextTrans = net.getTrans(t);
							break;
						}
					}
				} else {
					// Choose transition to fire based on weights
					pdf = new double[enTransCnt];					
					for (int t = 0, e = 0; t < net.getNumTrans(); t++) {
						if (transStatus[t]) {
							pdf[e] = net.getTrans(t).transWeight;
							enTransIDs[e] = t;
							e++;
						}
					}
					randTransGen.setState2(pdf);					
					nextTrans = net.getTrans(enTransIDs[randTransGen.nextInt()]);
				}

				nextTrans.fire();		// Fire transition

				// Update transStatus
				int p, t, nP, nT;
				Place pl;
				Transition tr;
				// Check if some transitions were disabled (newly-disabled transitions)
				nP = nextTrans.inPlaces.length;
				for (p = 0; p < nP; p++) {
					pl = nextTrans.inPlaces[p];
					nT = pl.outTrans.length;
					for (t = 0; t < nT; t++) {
						tr = pl.outTrans[t];						
						if ((!tr.enabled()) && transStatus[tr.id])	{
							transStatus[tr.id] = false;
							enTransCnt--;
						}
					}
				}
				// Check if some transitions were enabled (newly-enabled transitions)				
				nP = nextTrans.outPlaces.length;
				for (p = 0; p < nP; p++) {
					pl = nextTrans.outPlaces[p];
					nT = pl.outTrans.length;
					for (t = 0; t < nT; t++) {
						tr = pl.outTrans[t];						
						if (tr.enabled() && (!transStatus[tr.id])) {
							transStatus[tr.id] = true;
							enTransCnt++;
						}						
					}
				}
				
				// If there are always transitions enabled,
				// this results in an infinite loop. Make it
				// possible for the user to cancel the simulation
				// anyway.
				if (fireCnt > 10000000) {
					if (progressMonitor.isCanceled()) {
						clock = totRunL;
						break;
					}
					fireCnt = 0;
				} else {
					fireCnt++;
				}
			} // end firing enabled transitions

			// Step 2: Make sure all service completion events in PS QPlaces have been scheduled
			for (int q = 0; q < net.getNumQueues(); q++)
				if (net.getQueue(q).queueDiscip == Queue.PS && (!net.getQueue(q).eventsUpToDate))
					net.getQueue(q).updateEvents();
			/* Alternative Code
			for (int p = 0; p < numPlaces; p++)
				if (places[p] instanceof QPlace) {
					QPlace qpl = (QPlace) places[p];
					if ((qpl.queue.queueDiscip == Queue.PS) && (!qpl.queue.eventsUpToDate))
						qpl.queue.updateEvents();
				} 
			*/			

			// Step 3: Process next event in event list
			if (eventList.size() > 0) {
				Event ev = eventList.poll();
				// Event ev = (Event) eventList.remove(0); // Old LinkedList implementation of the event list.
								
				// Advance simulation time
				clock = ev.time;

				QPlace qpl = (QPlace) ev.token.place;
				qpl.completeService(ev.token);

				// Check if some transitions were enabled and update transStatus				
				int t, nT;
				Transition tr;
				nT = qpl.outTrans.length;
				for (t = 0; t < nT; t++) {
					tr = qpl.outTrans[t];					
					if (tr.enabled() && (!transStatus[tr.id])) {
						transStatus[tr.id] = true;
						enTransCnt++;
					}
				}
			} else {
				log.error("QPN is not live.");
				throw new SimQPNException();
			}

			// Step 4: Heart Beat
			if(beforeInitHeartBeat) {
				long curTimeMsrm = System.currentTimeMillis();
				if(((curTimeMsrm - lastTimeMsrm) >= MAX_INITIAL_HEARTBEAT)
						|| (SimQPNControler.clock >= maxProgressInterval)) {
					
					if(SimQPNControler.clock >= maxProgressInterval) {
						timeBtwHeartBeats = maxProgressInterval;
					} else {
						timeBtwHeartBeats = (SimQPNControler.clock / (curTimeMsrm - lastTimeMsrm)) * progressUpdateRate;
					}
					beforeInitHeartBeat = false;
					if (timeBtwChkStops == 0) {
						// enable checking of stopping criterion
						nextChkAfter = clock;
					}
				}
				
				if (progressMonitor.isCanceled()) {
					clock = totRunL;
				}
			} else {
				if(SimQPNControler.clock >= nextHeartBeat) {
					long curTimeMsrm = System.currentTimeMillis();
					progressMonitor.updateSimulationProgress(clock / (totRunL - 1) * 100, (curTimeMsrm - lastTimeMsrm));					
					lastTimeMsrm = curTimeMsrm;
					nextHeartBeat = SimQPNControler.clock + timeBtwHeartBeats;
					
					if (progressMonitor.isCanceled()) {
						clock = totRunL;
					}
				}
			}

			/* ORIGINAL HEARTBEAT IMPLEMENTATION
			if (runMode == NORMAL && analMethod != REPL_DEL && clock > nextHeartBeat) {
				double elapsedSecs = (System.currentTimeMillis() - beginRunWallClock) / 1000;
				double clockTimePerSec = clock / elapsedSecs;
				log("Info: Simulation time = " + (long) clock + "  Elapsed wall clock time = ");
				if (nextHeartBeat == timeInitHeartBeat)		// check if this is the initial heart beat
					logln((int) elapsedSecs + " sec");
				else
					logln((int) (elapsedSecs / 60) + " min");
				nextHeartBeat = Simulator.clock + clockTimePerSec * (secsBtwHeartBeats + 10);
				// Make sure at least secsBtwHeartBeats seconds have elapsed at next heart beat
			}
			*/

			// Step 5: Check Stopping Criterion
			if (stoppingRule != FIXEDLEN && (!inRampUp) && clock > nextChkAfter) {
				double elapsedSecs = (System.currentTimeMillis() - beginRunWallClock) / 1000;				
				double clockTimePerSec = clock / elapsedSecs;	
				boolean done = true;
				Place pl = null;

				for (int p = 0; p < net.getNumPlaces(); p++) {
					pl = net.getPlace(p);
					if (pl.statsLevel >= 3) {
						if (!pl.placeStats.enoughStats()) {
							done = false;
							break;
						}
						if ((pl instanceof QPlace) && !(((QPlace) pl).qPlaceQueueStats.enoughStats())) {
							done = false;
							break;
						}
					}
				}

				if (!done) {
					// The test already failed for a place.
					progressMonitor.precisionCheck(done, pl.name);
				} else {
					// Check also the probes, whether they have enough samples
					Probe probe = null;
					for (int pr = 0; pr < net.getNumProbes(); pr++) {
						probe = net.getProbe(pr);
						if (probe.statsLevel >= 3) {
							if (!probe.probeStats.enoughStats()) {
								done = false;
								break;
							}
						}
					}
					if (done) {
						progressMonitor.precisionCheck(done, null);
						break; // exit while loop
					} else {
						progressMonitor.precisionCheck(done, probe.name); //TODO: distinguish between places and probes.
					}
				}

				if (timeBtwChkStops > 0)
					nextChkAfter = SimQPNControler.clock + timeBtwChkStops;
				else
					nextChkAfter = SimQPNControler.clock + clockTimePerSec * secondsBtwChkStops;
			}

		}

		// END MAIN SIMULATION LOOP ---------------------------------------------------------------------------------
		progressMonitor.updateSimulationProgress(100, 0);
		
		if (progressMonitor.isCanceled()) {
			progressMonitor.warning("The simulation was canceled by the user.\n"
					+ "The required precision may not have been reached!");
		} else {
			if (SimQPNControler.clock >= SimQPNControler.totRunLen)  {
				if (SimQPNControler.stoppingRule != SimQPNControler.FIXEDLEN)  {
					progressMonitor.warning("The simulation was stopped because of reaching max totalRunLen.\n"
							+ "The required precision may not have been reached!");
				}
				else
					log.info("STOPPING because max totalRunLen is reached!");
			}
		}
		
		endRunClock = clock;
		msrmPrdLen = endRunClock - endRampUpClock;
		endRunWallClock = System.currentTimeMillis();
		runWallClockTime = (endRunWallClock - beginRunWallClock) / 1000;	// total time elapsed in seconds 
		
		log.info("msrmPrdLen= " + msrmPrdLen + " totalRunLen= " + endRunClock + " runWallClockTime=" + (int) (runWallClockTime / 60) + " min (=" + runWallClockTime + " sec)");

		
		// Complete statistics collection (make sure this is done AFTER the above statements)
		if (analMethod != WELCH) {		
			for (int p = 0; p < net.getNumPlaces(); p++)
				net.getPlace(p).finish();
			for (int q = 0; q < net.getNumQueues(); q++)  //NOTE: queues[*].finish() should be called after places[*].finish()! 
				net.getQueue(q).finish();
			for (int pr = 0; pr < net.getNumProbes(); pr++)
				net.getProbe(pr).finish();
		}	
	} // end of run() method

	private static Element getSettings(Element element, String configuration) {
		XPath xpathSelector = createXPath("meta-attributes/meta-attribute[starts-with(@xsi:type, 'simqpn') and @configuration-name='" + configuration + "']");
		Element elementSettings = (Element) xpathSelector.selectSingleNode(element);
		return elementSettings;
	}
	
	private static XPath createXPath(String xpath) {
		XPath selector = DocumentHelper.createXPath(xpath);
		selector.setNamespaceURIs(namespaceUris);
		return selector;
	}

} // end of Class Simulator
