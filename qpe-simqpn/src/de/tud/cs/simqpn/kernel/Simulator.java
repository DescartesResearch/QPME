/*
 * Copyright (c) 2009 Samuel Kounev. All rights reserved.
 *    
 * The use, copying, modification or distribution of this software and its documentation for 
 * any purpose is NOT allowed without the written permission of the author.
 *  
 * This source code is provided as is, without any express or implied warranty.
 *
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  --------------------------------------------------------------------------------
 *  2003/08/??  Samuel Kounev     Created.
 *  ...
 *  2004/08/25  Samuel Kounev     Fixed a small bug - value of clock casted to (int) instead of (long)  
 *                                when doing heartbeats. Changed timeInitHeartBeat to 100000.
 *  2005/06/22  Samuel Kounev     Moved initialization of service time distributions from  
 *                                QueueingPlace.init() to Simulator.getReady() as part of the model 
 *                                definition.        
 *  2006/10/14  Christofer Dutz   Added @SuppressWarnings("unchecked") and cleaned up imports to avoid warnings!
 *  2006/10/16  Samuel Kounev     Changed method execute to set Simulator.simRunning to true at the 
 *                                beginning of the simulaton and reset it at the end. The SimQPN 
 *                                plugin uses this variable to prevent starting the SimQPN configuration
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
 *                                
 */

package de.tud.cs.simqpn.kernel;

// Latest comments are labeled with "SDK-DEBUG2".
// Previous comments are labeled with "SDK-DEBUG".
// The original comments are labeled with "SK-CD".
// Please do not remove any comments! 
// You can add your comments/answers with a "CHRIS" label.
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;

import cern.colt.list.AbstractIntList;
import cern.colt.list.IntArrayList;
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
import cern.jet.random.engine.DRand;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomSeedGenerator;
import cern.jet.random.engine.RandomSeedTable;
import edu.cornell.lassp.houle.RngPack.RandomElement;

/**
 * Class Simulator
 * 
 * @version %I%, %G%
 */

public class Simulator {
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

	public static int numRuns;					// Maximum number of runs.
	public static boolean useStdStateStats; 	// For (MULT_REPL, statsLevel >= 3): Specifies whether to use ordinary
												//   or steady state sojourn times when estimating averages and c.i.
	public static String statsDir;

	public static int runMode;
	public static int analMethod;				// Output data analysis method.
	public static int stoppingRule;				// Simulation stopping criterion.
	public static int debugLevel;				// Debug level - TODO: currently not used consistently!
	public static double rampUpLen;				// Duration of the ramp up period.
	public static double totRunLen;				// Maximum total duration of the simulation run (incl. rampUpLen).
	public static double timeBtwChkStops;		// Time between checks if stopping criterion is fulfilled. 
												//   If set to 0, it is automatically adjusted to result in roughtly 
												//   secondsBtwChkStops sec between checks.
	public static double secondsBtwChkStops;	// Seconds between checks if stopping criterion is fulfilled. TODO: Add to User's Guide. 
												//   Used only when timeBtwChkStops == 0.
	public static double timeInitHeartBeat;		// Time when the first progress update is made. After this
												//   progress updates are made once every secsBtwHeartBeats seconds.
	public static double secsBtwHeartBeats;		// How often progress updates are made (heart beats).
	
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

	// Check if using double for time is really needed and if overhead is tolerable. Consider switching to float.
	public static double clock;					// Global simulation clock. Time is usually measured in milliseconds.
	public static LinkedList eventList;			// Global simulation event list. Contains events scheduled for processing at specified points in time.

	// Supported Random Number Generators
	public static final int DRand = 0;				// cern.jet.random.engine.DRand
	public static final int MersenneTwister = 1;	// cern.jet.random.engine.MersenneTwister
	public static int randGenClass;					// Defines the type of uniform random number generators used during the simulation.
	public static Uniform randNumGen;				// Random number generator used for seed generation.
	public static boolean useRandSeedTable;			// Specifies whether RandomSeedGenerator (and thus RandomSeedTable) is used.
	public static RandomSeedGenerator randSeedGen;	// Used if useRandSeedTable == true.

	public int numPlaces;
	public int numTrans;
	public Place[] places;
	public Transition[] trans;

	// XML Configuration.
	protected String configuration;
	protected Element net;
	protected List placeList;
	protected List transitionList;
	protected static PrintStream logPrintStream;

	public Simulator(Element net, String configuration) {
		this.configuration = configuration;
		this.net = net;
		XPath xpathSelector = DocumentHelper.createXPath("//place");
		placeList = xpathSelector.selectNodes(net);
		xpathSelector = DocumentHelper.createXPath("//transition");
		transitionList = xpathSelector.selectNodes(net);
	}

	public static void configure(Element net, String configuration) throws SimQPNException {
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
		XPath xpathSelector = DocumentHelper.createXPath("/net/meta-attributes/meta-attribute[@name = 'sim-qpn' and @configuration-name = '" + configuration + "']/@output-directory");
		Attribute outputDirAttribute = (Attribute) xpathSelector.selectSingleNode(net);
		String outputDirectory = outputDirAttribute.getStringValue();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HHmmssS");
		if(!outputDirectory.endsWith("\\"))	outputDirectory += "\\";
		
		String logFileName = outputDirectory + "SimQPN_Output_" + configuration + "_" + dateFormat.format(new Date()) + ".log";
		try {
			File logFile = new File(logFileName);
			logln("Logging to " + logFile.getAbsolutePath());
			logPrintStream = new PrintStream(logFile);
		} catch (FileNotFoundException e) {
			logln("Error: Cannot create simulation output log file! Please check output directory path.");
			log(e);
			throw new SimQPNException();
		}
		
		Element simulatorSettings = getSettings(net, configuration);

		if (simulatorSettings.attributeValue("verbosity-level") == null) {		
			logln("Error: Configuration parameter \"verbosity-level\" is not configured!");					
			logln("  configuration = " + configuration);
			throw new SimQPNException();
		}
		debugLevel = Integer.parseInt(simulatorSettings.attributeValue("verbosity-level"));
		logln(2, "debugLevel = " + debugLevel + ";");		
		
		// There are 3 possible scenarios (combinations of runMode and analMethod):
		logln(2, "Scenario set to: " + simulatorSettings.attributeValue("scenario"));
		switch (Integer.parseInt(simulatorSettings.attributeValue("scenario", "-1"))) {
			// Scenario 1:
			case 1: {
				runMode = NORMAL;
				analMethod = BATCH_MEANS;
				logln(2, "-- runMode = NORMAL");
				logln(2, "-- analMethod = BATCH_MEANS");				
				break;
			}
			// Scenario 2:
			case 2: {
				runMode = NORMAL;
				analMethod = REPL_DEL;
				logln(2, "-- runMode = NORMAL");
				logln(2, "-- analMethod = REPL_DEL");				
				if(simulatorSettings.attributeValue("number-of-runs") == null) {
					logln("ERROR: \"number-of-runs\" parameter for Replication/Deletion Method not specified!");					
					throw new SimQPNException();
				}				
				numRuns = Integer.parseInt(simulatorSettings.attributeValue("number-of-runs"));
				logln(2, "-- numRuns = " + numRuns);
				break;
			}
			// Scenario 3:
			case 3: {
				runMode = INIT_TRANS;
				analMethod = WELCH;
				logln(2, "-- runMode = INIT_TRANS");
				logln(2, "-- analMethod = WELCH");				
				if(simulatorSettings.attributeValue("number-of-runs") == null) {
					logln("ERROR: \"number-of-runs\" parameter for Method of Welch not specified!");
					throw new SimQPNException();
				}				
				numRuns = Integer.parseInt(simulatorSettings.attributeValue("number-of-runs"));
				logln(2, "-- numRuns = " + numRuns);
				break;
			}
			default: {	
				logln("ERROR: Invalid analysis method (scenario) specified!");
				throw new SimQPNException();
			}
		};
		
		statsDir = simulatorSettings.attributeValue("output-directory");
		logln(2, "statsDir = " + statsDir);
		
		// END-CONFIG
		// ------------------------------------------------------------------------------------------------------
	}

	/**
	 * Method execute - executes the simulation run
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public static void execute(Element net, String configuration) throws SimQPNException {
		
		// TODO: Make the Stdout output print to $statsDir/Output.txt
		// CHRIS: Not done yet

		simRunning = true;
		// NOTE: In the following, if the simulation is interrupted, simRunning should be reset. 		  
				
		try {
			XPath xpathSelector = DocumentHelper.createXPath("//color-ref[@maximum-capacity > 0]");
			if(xpathSelector.selectSingleNode(net) != null) {
				logln("Error: Max population attribute currently not supported (Set to 0 for all places)");
				throw new SimQPNException();
			}		
			xpathSelector = DocumentHelper.createXPath("//color-ref[@ranking > 0]");
			if(xpathSelector.selectSingleNode(net) != null) {
				logln("Error: Ranking attribute currently not supported (Set to 0 for all places)");
				throw new SimQPNException();
			}
			xpathSelector = DocumentHelper.createXPath("//color-ref[@priority > 0]");
			if(xpathSelector.selectSingleNode(net) != null) {
				logln("Error: Priority attribute currently not supported (Set to 0 for all places)");
				throw new SimQPNException();
			}
			xpathSelector = DocumentHelper.createXPath("//transition[@priority > 0]");
			if(xpathSelector.selectSingleNode(net) != null) {
				logln("Error: Transition priorities currently not supported (Set to 0 for all places)");
				throw new SimQPNException();
			}
		
			if (runMode == NORMAL) {
				if (analMethod == BATCH_MEANS) { // Method of non-overlapping batch means
					Place[] places = runBatchMeans(net, configuration);
					for (int p = 0; p < places.length; p++)
						places[p].report();
				} else if (analMethod == REPL_DEL) { // Replication/Deletion Approach (Method of Independent Replications) 				
					useStdStateStats = false;
					// useStdStateStats configurable only in MULT_REPL mode
					//   - automatically set to true in CVRG_EST mode.
					//   - automatically set to false in NORMAL:REPL_DEL mode. 					
					AggregateStats[] aggrStats = runMultRepl(net, configuration);
					for (int i = 0; i < aggrStats.length; i++)
						if (aggrStats[i] != null)
							aggrStats[i].printReport();
				} else {
					logln("Error: Illegal analysis method specified!");
					throw new SimQPNException();				
				}
			} else if (runMode == INIT_TRANS) {
				if (analMethod == WELCH) {
					runWelchMtd(net, configuration);
				} else {
					logln("Error: Analysis method " + analMethod + " not supported in INIT_TRANS mode!");
					throw new SimQPNException();
				}
			} else {
				logln("Error: Invalid run mode specified!");				
				throw new SimQPNException();
			}		
		}
		catch(SimQPNException e) {
			simRunning = false;
			throw e;			
		}		
		simRunning = false;		
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
	public static Place[] runBatchMeans(Element net, String configuration) throws SimQPNException {
		Simulator sim = new Simulator(net, configuration);
		sim.getReady();
		sim.run();
		return sim.places;
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
	public static AggregateStats[] runMultRepl(Element net, String configuration) throws SimQPNException {

		if (numRuns <= 1) {
			logln("Error: numRuns should be > 1!");
			throw new SimQPNException();
		}

		Simulator sim = new Simulator(net, configuration);
		sim.getReady();

		int numPlaces = sim.numPlaces;
		Place[] places = sim.places;
		List placeList = sim.placeList;
		
		AggregateStats[] aggrStats = new AggregateStats[numPlaces * 2]; 
		// Note: aggrStats should be large enough to accomodate 2 stats per place in case all places are queueing places.
		
		Place pl;

		for (int p = 0; p < numPlaces; p++) {
			pl = places[p];
			if (pl.statsLevel > 0) {
				if (pl instanceof QueueingPlace) {
					aggrStats[p * 2] = new AggregateStats(pl.id, pl.name, Stats.QUEUE, pl.numColors, pl.statsLevel);
					aggrStats[(p * 2) + 1] = new AggregateStats(pl.id, pl.name, Stats.DEPOSITORY, pl.numColors, pl.statsLevel);
				} else {
					aggrStats[p * 2] = new AggregateStats(pl.id, pl.name, Stats.PLACE, pl.numColors, pl.statsLevel);
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
		 *  if (!(places[p] instanceof QueueingPlace)) {								// ORDINARY PLACE
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
				XPath xpathSelector = DocumentHelper.createXPath("color-refs/color-ref");
				Iterator colorRefIterator = xpathSelector.selectNodes(place).iterator();
				for (int c = 0; colorRefIterator.hasNext(); c++) {
					Element colorRef = (Element) colorRefIterator.next();
					Element element = getSettings(colorRef, configuration);						
					if (pl instanceof QueueingPlace) {													
						if(element == null || element.attributeValue("queueSignLevAvgST") == null) {								
							logln("Error: queueSignLevAvgST settings for the Replication/Deletion Method not found!");
							logln("Details: ");
							logln("   place.id           = " + place.attributeValue("id"));
							logln("   place.name         = " + place.attributeValue("name")); 
							logln("   color-num          = " + c);
							logln("   colorRef.id        = " + colorRef.attributeValue("id"));
							logln("   colorRef.color-id  = " + colorRef.attributeValue("color-id"));
							throw new SimQPNException();							
						}								
						aggrStats[p * 2].signLevAvgST[c] = Double.parseDouble(element.attributeValue("queueSignLevAvgST"));
						logln(2, "aggrStats[" + p * 2 + "].signLevAvgST[" + c + "] = " + aggrStats[p * 2].signLevAvgST[c] + " (queue)");
					}
					if (element == null || element.attributeValue("signLevAvgST") == null) {
						logln("Error: signLevAvgST settings for the Replication/Deletion Method not found!");
						logln("Details: ");
						logln("   place.id           = " + place.attributeValue("id"));
						logln("   place.name         = " + place.attributeValue("name")); 
						logln("   color-num          = " + c);
						logln("   colorRef.id        = " + colorRef.attributeValue("id"));
						logln("   colorRef.color-id  = " + colorRef.attributeValue("color-id"));
						throw new SimQPNException();
					}						
					if (pl instanceof QueueingPlace) {
						aggrStats[(p * 2) + 1].signLevAvgST[c] = Double.parseDouble(element.attributeValue("signLevAvgST"));
						logln(2, "aggrStats[" + (p * 2) + 1 + "].signLevAvgST[" + c + "] = " + aggrStats[(p * 2) + 1].signLevAvgST[c] + " (depository)");						
					}
					else {
						aggrStats[p * 2].signLevAvgST[c] = Double.parseDouble(element.attributeValue("signLevAvgST"));
						logln(2, "aggrStats[" + p * 2 + "].signLevAvgST[" + c + "] = " + aggrStats[p * 2].signLevAvgST[c] + " (ordinary place)");						
					}
				}
			}
		}
		
		logln("---------------------------------------------");
		logln(" Starting MULT_REPL (numRuns = " + numRuns + ")");
		logln("---------------------------------------------");

		// Run replication loop
		for (int i = 0; i < numRuns; i++) {
			logln("Starting Run " + (i + 1));
			sim.run();
			for (int p = 0; p < numPlaces; p++) {
				pl = places[p];
				if (pl.statsLevel > 0) {
					if (pl instanceof QueueingPlace) {
						QueueingPlace qPl = (QueueingPlace) pl;
						aggrStats[p * 2].saveStats(qPl.queueStats);
						aggrStats[(p * 2) + 1].saveStats(qPl.placeStats);
					} else {
						aggrStats[p * 2].saveStats(pl.placeStats);
					}
				}
			}
			sim = new Simulator(net, configuration);
			sim.getReady();
			places = sim.places;
		}

		for (int i = 0; i < 2 * numPlaces; i++)
			if (aggrStats[i] != null)
				aggrStats[i].finish();

		return aggrStats;
	}

	/**
	 * Method runWelchMtd - runs the method of Welch
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public static AggregateStats[] runWelchMtd(Element net, String configuration) throws SimQPNException {

		if (numRuns < 5) {
			logln("Warning: Number of runs for the method of Welch should be at least 5!");
			logln("         Setting numRuns to 5.");
			numRuns = 5;
		}

		Simulator sim = new Simulator(net, configuration);
		sim.getReady();

		int numPlaces = sim.numPlaces;
		Place[] places = sim.places;
		List placeList = sim.placeList;

		AggregateStats[] aggrStats = new AggregateStats[numPlaces * 2]; 
		// Note: aggrStats should be large enough to accomodate 2 stats per place in case all places are queueing places.

		Place pl;

		for (int p = 0; p < numPlaces; p++) {
			pl = places[p];
			if (pl.statsLevel > 0) {
				if (pl instanceof QueueingPlace) {
					aggrStats[p * 2] = new AggregateStats(pl.id, pl.name, Stats.QUEUE, pl.numColors, pl.statsLevel);
					aggrStats[(p * 2) + 1] = new AggregateStats(pl.id, pl.name, Stats.DEPOSITORY, pl.numColors, pl.statsLevel);
				} else {
					aggrStats[p * 2] = new AggregateStats(pl.id, pl.name, Stats.PLACE, pl.numColors, pl.statsLevel);
					aggrStats[(p * 2) + 1] = null;
				}
			} else {
				aggrStats[p * 2] = null;
				aggrStats[(p * 2) + 1] = null;
			}
		}

		logln("---------------------------------------------");
		logln(" Starting Method of Welch (numRuns = " + numRuns + ")");
		logln("---------------------------------------------");

		// Run replication loop
		for (int i = 0; i < numRuns; i++) {
			/* BEGIN-CONFIG
			 * ----------------------------------------------------------------------------------------------------------------
			 * 
			 * minObsrvST - Minumum number of observations required 
			 * maxObsrvST - Maximum number of observations considered (if <= 0 token color is not considered in the analysis).
			 * 
			 * ... places[p].placeStats.minObsrvST/maxObsrvST
			 * ... ((QueueingPlace) places[p]).queueStats.minObsrvST[c]/maxObsrvST[c]
			 * 
			 * Note: Places/QueueingPlaces with (StatsLevel < 3) are not considered in the analysis!
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
					XPath xpathSelector = DocumentHelper.createXPath("color-refs/color-ref");
					Iterator colorRefIterator = xpathSelector.selectNodes(place).iterator();
					for (int c = 0; colorRefIterator.hasNext(); c++) {
						Element colorRef = (Element) colorRefIterator.next();
						Element element = getSettings(colorRef, configuration);						
						if (element == null || element.attributeValue("minObsrv") == null || element.attributeValue("maxObsrv") == null) {
							logln("Error: minObsrv/maxObsrv settings for the Method of Welch not found!");
							logln("Details: ");
							logln("   place.id           = " + place.attributeValue("id"));
							logln("   place.name         = " + place.attributeValue("name")); 
							logln("   color-num          = " + c);
							logln("   colorRef.id        = " + colorRef.attributeValue("id"));
							logln("   colorRef.color-id  = " + colorRef.attributeValue("color-id"));
							throw new SimQPNException();
						}						
						pl.placeStats.minObsrvST[c] = Integer.parseInt(element.attributeValue("minObsrv")); 
						pl.placeStats.maxObsrvST[c] = Integer.parseInt(element.attributeValue("maxObsrv")); 
						logln(2, "pl.placeStats.minObsrvST[" + c + "] = " + pl.placeStats.minObsrvST[c]);
						logln(2, "pl.placeStats.maxObsrvST[" + c + "] = " + pl.placeStats.maxObsrvST[c]);
						if (pl instanceof QueueingPlace) {
							QueueingPlace qPl = (QueueingPlace) pl;							
							if(element.attributeValue("queueMinObsrv") == null || element.attributeValue("queueMaxObsrv") == null) {								
								logln("Error: queueMinObsrv/queueMaxObsrv settings for the Method of Welch not found!");
								logln("Details: ");
								logln("   place.id           = " + place.attributeValue("id"));
								logln("   place.name         = " + place.attributeValue("name")); 
								logln("   color-num          = " + c);
								logln("   colorRef.id        = " + colorRef.attributeValue("id"));
								logln("   colorRef.color-id  = " + colorRef.attributeValue("color-id"));
								throw new SimQPNException();							
							}							
							qPl.queueStats.minObsrvST[c] = Integer.parseInt(element.attributeValue("queueMinObsrv"));							
							qPl.queueStats.maxObsrvST[c] = Integer.parseInt(element.attributeValue("queueMaxObsrv"));  
							logln(2, "qPl.queueStats.minObsrvST[" + c + "] = " + qPl.queueStats.minObsrvST[c]);
							logln(2, "qPl.queueStats.maxObsrvST[" + c + "] = " + qPl.queueStats.maxObsrvST[c]);
						}
					}
				}
			}			
			// END-CONFIG
			// -----------------------------------------------------------------------------------------

			logln("Starting Run " + (i + 1));
			sim.run();
			for (int p = 0; p < numPlaces; p++) {
				pl = places[p];
				if (pl.statsLevel > 0) {
					if (pl instanceof QueueingPlace) {
						QueueingPlace qPl = (QueueingPlace) pl;
						aggrStats[p * 2].saveStats(qPl.queueStats);
						aggrStats[(p * 2) + 1].saveStats(qPl.placeStats);
					} else {
						aggrStats[p * 2].saveStats(pl.placeStats);
					}
				}
			}
			sim = new Simulator(net, configuration);
			sim.getReady();
			places = sim.places;
		}

		for (int i = 0; i < 2 * numPlaces; i++)
			if (aggrStats[i] != null)
				aggrStats[i].finish();

		return aggrStats;
	}

	
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
				if (pl instanceof QueueingPlace) {
					aggrStats[p*2] 		= new AggregateStats(pl.id, pl.name, Stats.QUEUE, pl.numColors, pl.statsLevel);
					aggrStats[(p*2)+1]	= new AggregateStats(pl.id, pl.name, Stats.DEPOSITORY, pl.numColors, pl.statsLevel);																	
				}
				else {
					aggrStats[p*2] 		= new AggregateStats(pl.id, pl.name, Stats.PLACE, pl.numColors, pl.statsLevel);
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
					if (pl instanceof QueueingPlace) {
						QueueingPlace qPl = (QueueingPlace) pl; 
						aggrStats[p*2].saveStats(qPl.queueStats);
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
		
	
	/**
	 * Method getReady - prepares for the simulation run
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public void getReady() throws SimQPNException {

		/*
		 * This is the method where the QPN to be simulated is defined.
		 * The QPN specification is currently hard-coded. 
		 * 
		 * IMPORTANT: Remember to keep the order of statements unchanged!!!
		 * 
		 */

		// Random Number Generation (Note: needs to be initialized before starting the model definition)
		randGenClass = MersenneTwister;
		useRandSeedTable = true;

		randNumGen = new Uniform(new DRand(new java.util.Date()));
		if (useRandSeedTable)
			randSeedGen = new RandomSeedGenerator(randNumGen.nextIntFromTo(0, Integer.MAX_VALUE), randNumGen.nextIntFromTo(0, RandomSeedTable.COLUMNS - 1));

		// *****************************************************************************************************************
		// BEGIN-MODEL-DEFINITION
		// *****************************************************************************************************************

		// Initialize the place and transition sizes.
		numPlaces = placeList.size();
		numTrans = transitionList.size();

		// -----------------------------------------------------------------------------------------------------------
		// CREATE PLACES
		// -----------------------------------------------------------------------------------------------------------
		/*
		 * @param int id          - global id of the place - IMPORTANT: must be equal to the index in the array!!!!!!! 
		 * @param String name     - name of the place
		 * @param int numColors   - number of colors 
		 * @param int numInTrans  - number of input transitions 
		 * @param int numOutTrans - number of output transitions 
		 * @param int[1..4] statsLevel - determines the amount of statistics to be gathered during the run 
		 *    Level 1: Token Throughput (Arrival/Departure Rates) 
		 *    Level 2: + Token Population, Utilization
		 *    Level 3: + Token Sojourn Times (sample mean and variance + steady state point estimates and confidence intervals) 
		 *    Level 4: + Record Sojourn Times in a file 
		 * @param int depDiscip/dDis - determines the departure discipline: Place.NORMAL or Place.FIFO
		 * 
		 * For QueueingPlace two extra parameters:
		 * 
		 * @param int queueDiscip/qDis - queueing discipline is QueueingPlace.IS, QueueingPlace.FCFS or QueueingPlace.PS. 
		 *    NOTE: if a different queueing discipline is specified (e.g. RANDOM) print WARNING and use FCFS instead. 
		 * @param int numServers - number of servers in queueing station - NOTE: for IS set to 0
		 * 
		 * IMPORTANT: Pay attention to variable types!!! Validate input parameters provided by the user to make sure 
		 *   that they make sense! For e.g. Number of servers should be an integer >= 0.
		 * 
		 * IMPORTANT: In general, you should provide a way to add "help information" associated with different items 
		 *   in the tool. For example, if one wants to know what a configuration option means, one should be able to 
		 *   somehow click on it and ask for help. Ballons that appear when moving the cursor over the option is one 
		 *   possibility. A more general solution would be to have a help page associated with each input screen in 
		 *   the QPE. The page could be displayed by pressing F1 for example.
		 * 
		 */

		/* 
		 * SDK-DEBUG: A general question: Does your code guarantee that all id's assigned to elements (places, 
		 *   transitions, colors, connections, modes) are globally unique across all element types?
		 * CHRIS: Yes it does. The id generator is initialized with the current system time from there on is 
		 *   allways increased by one for every id generated. Id generation is synchronized, so there should be 
		 *   no way of creating double ids (ok ... except if the user messes with the system time or works during 
		 *   daylightsaving time changes and starts the program the same second twice, but I thought the chance for 
		 *   that would be quite slim) ;)  
		 */
		
		logln(2, "\n/////////////////////////////////////////////");
		logln(2, "// Create places");
		// Allocate an array able to contain the places.
		logln(2, "places = new Place[" + numPlaces + "];");
		places = new Place[numPlaces];

		// Create the place-objects of every-place. Depending on its type-attribute create Place or QueueingPlace objects.
		Iterator placeIterator = placeList.iterator();
		
		for (int i = 0; placeIterator.hasNext(); i++) {
			Element place = (Element) placeIterator.next();
			// SDK-DEBUG: Are you sure the XPath expression below selects the right connections? 
			//   <connection> is a child of the <connections> child element of <net> <connections> inside transitions 
			//   should not be selected here!
			// CHRIS: Since I specify the source-id attribute and specify the id of a place, only inter place/tansition 
			//   connections can be selected, the incidence function connections are between color-refs and modes and since 
			//   the ids are concidered unique, the correct connection element will be selected.
			XPath xpathSelector = DocumentHelper.createXPath("//connection[@source-id = '" + place.attributeValue("id") + "']");
			int numOutgoingConnections = xpathSelector.selectNodes(net).size();
			xpathSelector = DocumentHelper.createXPath("//connection[@target-id = '" + place.attributeValue("id") + "']");
			int numIncomingConnections = xpathSelector.selectNodes(net).size();

			Element metaAttribute = getSettings(place, configuration);
			// Stats-level should be configurable as above.
			if (metaAttribute.attributeValue("statsLevel") == null) {
				logln("Error: statsLevel parameter not set!");
				logln("Details: ");
				logln("  place-num   = " + i);
				logln("  place.id    = " + place.attributeValue("id"));
				logln("  place.name  = " + place.attributeValue("name"));								
				throw new SimQPNException();
			}
			int statsLevel = Integer.parseInt(metaAttribute.attributeValue("statsLevel"));
			
			int dDis;
			
			// This is a user-defined config-parameter for both ordinary- and queueing-place.			
			if (place.attributeValue("departure-discipline") == null) {
				logln("ERROR: departure-discipline parameter not set!");
				logln("Details: ");
				logln("  place-num   = " + i);
				logln("  place.id    = " + place.attributeValue("id"));
				logln("  place.name  = " + place.attributeValue("name"));								
				throw new SimQPNException();
			}			
			if ("NORMAL".equals(place.attributeValue("departure-discipline"))) {
				dDis = Place.NORMAL;
			} else if ("FIFO".equals(place.attributeValue("departure-discipline"))) {
				dDis = Place.FIFO;
			} else {
				logln("ERROR: Invalid departure-discipline setting!");
				logln("Details: ");				
				logln("  place-num                  = " + i);
				logln("  place.id                   = " + place.attributeValue("id"));
				logln("  place.name                 = " + place.attributeValue("name"));
				logln("  place.departure-discipline = " + place.attributeValue("departure-discipline"));
				throw new SimQPNException();				
			}
			/* 
			 * IMPORTANT:			
			 * My understanding was that all attributes should be included in the XML document even if they are set to 
			 * their default values. Under this assumption, default values are dealt with in QPE and here when mapping 
			 * the model to SimQPN it is assumed that all attributes are set to valid values. So if that's not the case 
			 * we should print an error message and stop. This would make it easier to shake out potential bugs in the 
			 * mapping.
			 * 
			 * CHRIS: I had changed that in the editor. Unfortunately this only applys to newly created nodes. 
			 *
			 * SDK-DEBUG2: 
			 * I have noticed that still some parameters (e.g. minObsrv, maxObsrv, queueMinObsrv, queueMaxObsrv, distribution-function) 
			 * are not included in the XML file when the default value is chosen.  
			 * All attributes must be included in the XML even if they were not changed from their default values, i.e., 
			 * the XML file should be complete! 
			 * CHRIS: Fixed that now too.
			 */
			
			if ("ordinary-place".equals(place.attributeValue("type"))) {
				places[i] = new Place(
						i,																	// id 
						place.attributeValue("name"), 										// name
						place.element("color-refs").elements("color-ref").size(), 			// # colors
						numIncomingConnections,												// # incoming connections 
						numOutgoingConnections, 											// # outgoing connections
						statsLevel,															// stats level
						dDis, 																// departure discipline
						place);
				logln(2, "places[" + i + "] = new Place(" 
						+ i + ", '" 
						+ place.attributeValue("name") + "', " 
						+ place.element("color-refs").elements("color-ref").size() + ", " 
						+ numIncomingConnections + ", " 
						+ numOutgoingConnections + ", " 
						+ statsLevel + ", " 
						+ dDis + ", " 
						+ place + ")");				
			} else if ("queueing-place".equals(place.attributeValue("type"))) {
				int qDis = QueueingPlace.FCFS;
				int numServers;
				
				if ("IS".equals(place.attributeValue("strategy"))) {
					qDis = QueueingPlace.IS; 
					numServers = 0;
				} else {
					if ("FCFS".equals(place.attributeValue("strategy"))) {
						qDis = QueueingPlace.FCFS; 
					} else if ("PS".equals(place.attributeValue("strategy"))) {
						qDis = QueueingPlace.PS; 
					} else {						
						logln("ERROR: Invalid or missing \"strategy\" (queueing discipline) setting!");
						logln("Details: ");
						logln("  place-num      = " + i);
						logln("  place.id       = " + place.attributeValue("id"));
						logln("  place.name     = " + place.attributeValue("name"));
						logln("  place.strategy = " + place.attributeValue("strategy"));
						throw new SimQPNException();
					}
					if(place.attributeValue("number-of-servers") == null) {
						logln("ERROR: \"number-of-servers\" parameter not set!");
						logln("Details: ");
						logln("  place-num   = " + i);
						logln("  place.id    = " + place.attributeValue("id"));
						logln("  place.name  = " + place.attributeValue("name"));												
						throw new SimQPNException();
					}
					numServers = Integer.parseInt(place.attributeValue("number-of-servers"));
				}				
				places[i] = new QueueingPlace(
						i, 																	// id
						place.attributeValue("name"), 										// name
						place.element("color-refs").elements("color-ref").size(), 			// # colors
						numIncomingConnections,												// # incoming connections
						numOutgoingConnections, 											// # outgoing connections
						statsLevel, 														// stats level
						dDis, 																// departure discipline
						qDis, 																// queueing discipline
						numServers, 														// # servers
						place);
				logln(2, "places[" + i + "] = new QueueingPlace(" 
						+ i + ", '" 
						+ place.attributeValue("name") + "', " 
						+ place.element("color-refs").elements("color-ref").size() + ", " 
						+ numIncomingConnections + ", " 
						+ numOutgoingConnections + ", " 
						+ statsLevel + ", " 
						+ dDis + ", " 
						+ qDis + ", " 
						+ numServers + ", " 
						+ place + ")");								
			} else {
				logln("ERROR: Invalid or missing place type setting!");
				logln("       Currently only 'ordinary-place' and 'queueing-place' are supported.");
				logln("Details: ");
				logln("  place-num   = " + i);
				logln("  place.id    = " + place.attributeValue("id"));
				logln("  place.name  = " + place.attributeValue("name"));
				logln("  place.type  = " + place.attributeValue("type"));
				throw new SimQPNException();
			}
		}

		// -----------------------------------------------------------------------------------------------------------
		// CREATE TRANSITIONS
		// -----------------------------------------------------------------------------------------------------------
		/*
		 * public Transition(int id, String name, int numModes, int numInPlaces, int numOutPlaces, double transWeight) 
		 * 
		 * @param int id                  - global id of the transition 
		 * @param String name             - name of the transition 
		 * @param int numModes            - number of modes 
		 * @param int numInPlaces         - number of input places 
		 * @param int numOutPlaces        - number of output places 
		 * @param double transWeight      - transition weight
		 * 
		 * Validate input parameters provided by the user to make sure that they make sense!!! For e.g. transWeight >= 0
		 * 
		 * IMPORTANT: Timed transitions and Subnet places are currently not supported!!! If the net uses them, just 
		 * print an error message and exit.
		 * 
		 * IMPORTANT: trans id must be equal to its index in the trans array!!!
		 * 
		 */

		logln(2, "\n/////////////////////////////////////////////");
		logln(2, "// Create transitions");
		// Allocate an array able to contain the transitions.
		logln(2, "trans = new Transition[" + numTrans + "];");
		trans = new Transition[numTrans];

		// Create the transition-objects of every transition.
		Iterator transitionIterator = transitionList.iterator();
		for (int i = 0; transitionIterator.hasNext(); i++) {
			Element transition = (Element) transitionIterator.next();
			if(!"immediate-transition".equals(transition.attributeValue("type"))) {
				logln("ERROR: Invalid or missing transition type setting!");
				logln("       Only \"immediate-transition\" is currently supported.");				
				logln("Details: ");
				logln("  transition-num   = " + i);
				logln("  transition.id    = " + transition.attributeValue("id"));
				logln("  transition.name  = " + transition.attributeValue("name"));
				logln("  transition.type  = " + transition.attributeValue("type"));
				throw new SimQPNException();
			}
			XPath xpathSelector = DocumentHelper.createXPath("modes/mode");
			int numModes = xpathSelector.selectNodes(transition).size();
			xpathSelector = DocumentHelper.createXPath("//connection[@source-id = '" + transition.attributeValue("id") + "']");
			int numOutgoingConnections = xpathSelector.selectNodes(net).size();
			xpathSelector = DocumentHelper.createXPath("//connection[@target-id = '" + transition.attributeValue("id") + "']");
			int numIncomingConnections = xpathSelector.selectNodes(net).size();			
			if(transition.attributeValue("weight") == null) {
				logln("Error: transition weight not set!");
				logln("Details: ");
				logln("  transition-num   = " + i);
				logln("  transition.id    = " + transition.attributeValue("id"));
				logln("  transition.name  = " + transition.attributeValue("name"));
				logln("  transition.type  = " + transition.attributeValue("type"));				
				throw new SimQPNException();
			}
			double transitionWeight = Double.parseDouble(transition.attributeValue("weight"));
			
			trans[i] = new Transition(
					i,																		// id
					transition.attributeValue("name"), 										// name
					numModes, 																// # modes
					numIncomingConnections, 												// # incoming connections
					numOutgoingConnections, 												// # outgoing connections
					transitionWeight);														// transition weight
			logln(2, "trans[" + i + "] = new Transition(" 
					+ i + ", '" 
					+ transition.attributeValue("name") + "', " 
					+ numModes + ", " 
					+ numIncomingConnections + ", " 
					+ numOutgoingConnections + ", " 
					+ transitionWeight + ");       transition-element = " + transition);									
		}		

		// -----------------------------------------------------------------------------------------------------------
		// CONFIGURE INPUT/OUTPUT RELATIONSHIPS
		// -----------------------------------------------------------------------------------------------------------

		logln(2, "\n/////////////////////////////////////////////");
		logln(2, "// Configure input/output relationships");
		// Initialize the place-transition and transition-place connections.
		XPath xpathSelector = DocumentHelper.createXPath("/net/connections/connection");
		Iterator connectionIterator = xpathSelector.selectNodes(net).iterator();
		
		while (connectionIterator.hasNext()) {
			// Get the next connection
			Element connection = (Element) connectionIterator.next();

			// Select the source and target of this connection
			xpathSelector = DocumentHelper.createXPath("//*[@id = '" + connection.attributeValue("source-id") + "']");
			Element sourceElement = (Element) xpathSelector.selectSingleNode(net);
			xpathSelector = DocumentHelper.createXPath("//*[@id = '" + connection.attributeValue("target-id") + "']");
			Element targetElement = (Element) xpathSelector.selectSingleNode(net);

			// if the source is a place, then select the Place object which it is assigned to.
			if ("place".equals(sourceElement.getName())) {
				// Select the source place.
				for (int p = 0; p < places.length; p++) {
					if (placeList.get(p).equals(sourceElement)) {
						// SDK-DEBUG: Does "equals" work correctly here? Or should you rather compare the id's?
						// CHRIS: Since references to the DOM elements instances are returned the check works and is
						// a lot faster.

						// Select the target transition
						for (int t = 0; t < trans.length; t++) {
							if (transitionList.get(t).equals(targetElement)) {
								// Connect place and transition
								for (int ot = 0; ot < places[p].outTrans.length; ot++) {
									if (places[p].outTrans[ot] == null) {
										places[p].outTrans[ot] = trans[t];
										logln(2, "places[" + p + "].outTrans[" + ot + "] = trans[" + t + "];");										
										break;
									}
								}
								for (int ip = 0; ip < trans[t].inPlaces.length; ip++) {
									if (trans[t].inPlaces[ip] == null) {
										trans[t].inPlaces[ip] = places[p];
										logln(2, "trans[" + t + "].inPlaces[" + ip + "] = places[" + p + "];");										
										break;
									}
								}
								break;
							}
						}
						break;
					}
				}
			}
			// if the source is a transition, then select the Transition object which it is assigned to.
			else if ("transition".equals(sourceElement.getName())) {
				// Select the source transition.
				for (int t = 0; t < trans.length; t++) {
					if (transitionList.get(t).equals(sourceElement)) {
						// Select the target place.
						for (int p = 0; p < places.length; p++) {
							if (placeList.get(p).equals(targetElement)) {
								// Connect transition and place.
								for (int op = 0; op < trans[t].outPlaces.length; op++) {
									if (trans[t].outPlaces[op] == null) {
										trans[t].outPlaces[op] = places[p];
										logln(2, "trans[" + t + "].outPlaces[" + op + "] = places[" + p + "];");										
										break;
									}
								}
								for (int it = 0; it < places[p].inTrans.length; it++) {
									if (places[p].inTrans[it] == null) {
										places[p].inTrans[it] = trans[t];
										logln(2, "places[" + p + "].inTrans[" + it + "] = trans[" + t +"];");										
										break;
									}
								}
								break;
							}
						}
						break;
					}
				}
			}
		}

		// -----------------------------------------------------------------------------------------------------------
		// CONFIGURE TRANSITION MODE WEIGHTS
		// -----------------------------------------------------------------------------------------------------------

		logln(2, "\n/////////////////////////////////////////////");
		logln(2, "// Configure transition mode weights");
		transitionIterator = transitionList.iterator();
		for (int i = 0; transitionIterator.hasNext(); i++) {
			Element transition = (Element) transitionIterator.next();
			xpathSelector = DocumentHelper.createXPath("modes/mode");
			Iterator modeIterator = xpathSelector.selectNodes(transition).iterator();
			for (int j = 0; modeIterator.hasNext(); j++) {
				Element mode = (Element) modeIterator.next();
				if(mode.attributeValue("firing-weight") == null) {
					logln("Error: transition mode' \"firing-weight\" not set");
					logln("Details: ");
					logln("  transition-num   = " + i);
					logln("  transition.id    = " + transition.attributeValue("id"));
					logln("  transition.name  = " + transition.attributeValue("name"));
					logln("  mode-num         = " + j);
					logln("  mode.id          = " + mode.attributeValue("id"));
					logln("  mode.name        = " + mode.attributeValue("name"));					
					throw new SimQPNException();										
				}				
				trans[i].modeWeights[j] = Double.parseDouble(mode.attributeValue("firing-weight"));
				logln(2, "trans[" + i + "].modeWeights[" + j + "] = " + trans[i].modeWeights[j]);				
			}
		}

		// -----------------------------------------------------------------------------------------------------------
		// CONFIGURE TRANSITION INPUT/OUTPUT FUNCTIONS [mode, inPlace, color]
		// -----------------------------------------------------------------------------------------------------------

		/*
		 * For each transition for every input/output place must specify how many tokens of  each color of the 
		 * respective place are destroyed/created.
		 */

		logln(2, "\n/////////////////////////////////////////////");
		logln(2, "// Configure transition input/output functions [mode, inPlace, color]");
		// trans[{transition-number}].inFunc[{place-number}][{color-ref-number}][{mode-number}]
		// = {number-of-tokens};
		
		for (int t = 0; t < trans.length; t++) {
			// Select the element for the current transition.
			Element transition = (Element) transitionList.get(t);

			xpathSelector = DocumentHelper.createXPath("modes/mode");
			List modes = xpathSelector.selectNodes(transition);

			// Iterate through all modes
			for (int m = 0; m < trans[t].numModes; m++) {
				// Iterate through all input-places.
				for (int p = 0; p < trans[t].inPlaces.length; p++) {
					Element inputPlace = trans[t].inPlaces[p].element;
					// Go through all color-refs for the current place.
					xpathSelector = DocumentHelper.createXPath("color-refs/color-ref");
					List colorRefs = xpathSelector.selectNodes(inputPlace);

					// Allocate an array saving the number of tokens for each color-ref to the current mode for the
					// current input place. If there is no connection, then this value is 0.
					trans[t].inFunc[m][p] = new int[colorRefs.size()];
					logln(2, "trans[" + t + "].inFunc[" + m + "][" + p + "] = new int[" + colorRefs.size() + "];");					

					Iterator colorRefIterator = colorRefs.iterator();
					for (int con = 0; colorRefIterator.hasNext(); con++) {
						Element colorRef = (Element) colorRefIterator.next();

						String colorRefId = colorRef.attributeValue("id");
						String modeId = ((Element) modes.get(m)).attributeValue("id");

						xpathSelector = DocumentHelper.createXPath("connections/connection[(@source-id='" + colorRefId + "') and (@target-id='" + modeId + "')]");
						Element connection = (Element) xpathSelector.selectSingleNode(transition);
						if (connection != null) {
							if(connection.attributeValue("count") == null) {
								logln("ERROR: incidence function connection' \"count\" (arc weight) attribute not set!");
								logln("Details: ");
								logln("  transition-num    = " + t);
								logln("  transition.id     = " + transition.attributeValue("id"));
								logln("  transition.name   = " + transition.attributeValue("name"));
								logln("  mode-num          = " + m);
								logln("  mode.id           = " + ((Element) modes.get(m)).attributeValue("id"));
								logln("  mode.name         = " + ((Element) modes.get(m)).attributeValue("name"));																
								logln("  inPlace.id        = " + inputPlace.attributeValue("id"));
								logln("  inPlace.name      = " + inputPlace.attributeValue("name"));								
								logln("  colorRef.id       = " + colorRef.attributeValue("id"));
								logln("  colorRef.color-id = " + colorRef.attributeValue("color-id"));
								throw new SimQPNException();
							}
							int numTokens = Integer.parseInt(connection.attributeValue("count"));
							//SDK-DEBUG: Attribute "count" is usually missing in the XML file.
							//CHRIS: fixed that.
							trans[t].inFunc[m][p][con] = numTokens;							
							logln(2, "trans[" + t + "].inFunc[" + m + "][" + p + "][" + con +"] = " + numTokens);
						} else {							
							trans[t].inFunc[m][p][con] = 0;
							logln(2, "trans[" + t + "].inFunc[" + m + "][" + p + "][" + con +"] = 0");
						}
					}
				}

				// Iterate through all output-places.
				for (int p = 0; p < trans[t].outPlaces.length; p++) {
					Element outputPlace = trans[t].outPlaces[p].element;
					// Go through all color-refs for the current place.
					xpathSelector = DocumentHelper.createXPath("color-refs/color-ref");
					List colorRefs = xpathSelector.selectNodes(outputPlace);
					
					// Allocate an array saving the number of tokens for each color-ref to mode connection. 
					// If there is no connection, then this value is 0.
					trans[t].outFunc[m][p] = new int[colorRefs.size()];					
					logln(2, "trans[" + t + "].outFunc[" + m + "][" + p +"] = new int[" + colorRefs.size() + "];");
						
					Iterator colorRefIterator = colorRefs.iterator();
					for (int con = 0; colorRefIterator.hasNext(); con++) {
						Element colorRef = (Element) colorRefIterator.next();

						String colorRefId = colorRef.attributeValue("id");
						String modeId = ((Element) modes.get(m)).attributeValue("id");

						xpathSelector = DocumentHelper.createXPath("connections/connection[(@source-id='" + modeId + "') and (@target-id='" + colorRefId + "')]");
						Element connection = (Element) xpathSelector.selectSingleNode(transition);
						if (connection != null) {
							if(connection.attributeValue("count") == null) {
								logln("ERROR: incidence function connection' \"count\" (arc weight) attribute not set!");
								logln("Details: ");
								logln("  transition-num    = " + t);
								logln("  transition.id     = " + transition.attributeValue("id"));
								logln("  transition.name   = " + transition.attributeValue("name"));
								logln("  mode-num          = " + m);
								logln("  mode.id           = " + ((Element) modes.get(m)).attributeValue("id"));
								logln("  mode.name         = " + ((Element) modes.get(m)).attributeValue("name"));																
								logln("  outPlace.id       = " + outputPlace.attributeValue("id"));
								logln("  outPlace.name     = " + outputPlace.attributeValue("name"));								
								logln("  colorRef.id       = " + colorRef.attributeValue("id"));
								logln("  colorRef.color-id = " + colorRef.attributeValue("color-id"));
								throw new SimQPNException();								
							}
							int numTokens = Integer.parseInt(connection.attributeValue("count"));
							trans[t].outFunc[m][p][con] = numTokens;
							logln(2, "trans[" + t + "].outFunc[" + m + "][" + p + "][" + con + "] = " + numTokens + ";");							
						} else {
							trans[t].outFunc[m][p][con] = 0;
							logln(2, "trans[" + t + "].outFunc[" + m + "][" + p + "][" + con + "] = 0;");							
						}
					}
				}
			}
		}

		// -----------------------------------------------------------------------------------------------------------
		// CONFIGURE QUEUE SERVICE TIME DISTRIBUTIONS (times normally in milliseconds)
		// -----------------------------------------------------------------------------------------------------------

		/* 
		 * Distribution Name (Initialization Parameters)
		 * --------------------------------------------------------------------------------------------- 
		 * - Beta (double alpha, double beta) 
		 * - BreitWigner (double mean, double gamma, double cut) 
		 * - BreitWignerMeanSquare (double mean, double gamma, double cut) 
		 * - ChiSquare (double freedom) 
		 * - Gamma (double alpha, double lambda) 
		 * - Hyperbolic (double alpha, double beta) 
		 * - Exponential (double lambda) 
		 * - ExponentialPower (double tau) 
		 * - Logarithmic (double p) 
		 * - Normal (double mean, double stdDev) 
		 * - StudentT (double freedom) 
		 * - Uniform (double min, double max) 
		 * - VonMises (double freedom) 
		 * - Empirical (String pdf_filename)
		 * 
		 * For each distribution, additional initialization parameters needed are shown in the brackets. 
		 * The default distribution should be Exponential.
		 * Note that the last distribution has a String parameter containing a file name.
		 * 
		 * Three parameters p1, p2 and p3 of type double in the data model are used here to initialize
		 * the distribution function.
		 *  
		 * TODO: make the editor display the real names of the expected parameters after the user has chosen 
		 * the distribution, e.g. for Exponential a single field labeled "lambda" should be displayed.
		 * 
		 */
				
		logln(2, "\n/////////////////////////////////////////////");
		logln(2, "// Configure Queue Service Time Distributions (times in milliseconds)");
		placeIterator = placeList.iterator();
		for (int i = 0; placeIterator.hasNext(); i++) {
			Element place = (Element) placeIterator.next();
			if ("queueing-place".equals(place.attributeValue("type"))) {
				QueueingPlace qPl = (QueueingPlace) places[i];				
				
				// BEGIN-CONFIG
				if (qPl.queueDiscip == QueueingPlace.PS)  
					qPl.expPS = false; 				
				// END-CONFIG				
				
				if (!(qPl.queueDiscip == QueueingPlace.PS && qPl.expPS)) 
					qPl.randServTimeGen = new AbstractContinousDistribution[qPl.numColors];
				
				xpathSelector = DocumentHelper.createXPath("color-refs/color-ref");
				Iterator colorRefIterator = xpathSelector.selectNodes(place).iterator();
				for (int j = 0; colorRefIterator.hasNext(); j++) {
					Element colorRef = (Element) colorRefIterator.next();					
					
					if(colorRef.attributeValue("distribution-function") == null) {
						logln("Error: Queueing place' \"distribution-function\" parameter not set!");
						logln("Details: ");						
						logln("  place-num          = " + i);
						logln("  place.id           = " + place.attributeValue("id"));
						logln("  place.name         = " + place.attributeValue("name"));						
						logln("  colorRef-num       = " + j);
						logln("  colorRef.id        = " + colorRef.attributeValue("id"));
						logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 
						throw new SimQPNException();
					}
					String distributionFunction = colorRef.attributeValue("distribution-function");
										
					if (qPl.queueDiscip == QueueingPlace.PS && qPl.expPS) {
						logln("Info: expPS parameter of a queueing place with PS scheduling strategy set to true!");
						if (!"Exponential".equals(distributionFunction)) {
							logln("Error: Distribution function is configured as \"" + distributionFunction + "\"");							
							logln("       Distribution function must be set to \"Exponential\" since (expPS == true) !");
							logln("Details");
							logln("  place-num          = " + i);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + j);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 
							throw new SimQPNException();
						}						
					}				
									
					/*
					 * The code below does the following:
					 *   - checks the chosen distribution function
					 *   - checks that all required distribution input parameters have been set
					 *   - validates the values of the distribution input parameters
					 *   - initializes the random number generators for service times 
					 *   - initializes the meanServTimes array based on the chosen distribution   
					 * 
					 * The actual values in the meanServTimes array are currently only used in three cases 
					 *    1. QueueingPlace.expPS == true (Exponential distribution)     
					 *    2. QueueingPlace.queueStats.indrStats == true
					 *    3. distribution-function == Deterministic (not implemented yet)
					 * 
					 * Note: Service time distributions are truncated at 0 to avoid negative
					 *       values for service times, i.e. "if (servTime < 0) servTime = 0;"
					 *       
					 */
										
					if("Beta".equals(distributionFunction)) {
						if(colorRef.attributeValue("alpha") == null || colorRef.attributeValue("beta") == null) {
							logln("ERROR: Parameter \"alpha\" or \"beta\" of Beta distribution function not set!");
							logln("Details: ");							
							logln("  place-num          = " + i);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + j);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 
							throw new SimQPNException();																					
						}
						double alpha = Double.parseDouble(colorRef.attributeValue("alpha"));
						double beta = Double.parseDouble(colorRef.attributeValue("beta"));						
						// Validate input parameters
						if (!(alpha > 0 && beta > 0))  {
							logln("ERROR: Invalid \"alpha\" or \"beta\" parameter of Beta distribution!");
							logln("Details: ");							
							logln("  alpha, beta        = " + alpha + ", " + beta);
							logln("  place-num          = " + i);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + j);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 
							throw new SimQPNException();																												
						}						
						// Initialize random number generator and meanServTimes
						qPl.randServTimeGen[j] = new Beta(alpha, beta, Simulator.nextRandNumGen());
						logln(2, "((QueueingPlace) places[" + i + "]).randServTimeGen[" + j + "] = new Beta(" + alpha + ", " + beta + ", Simulator.nextRandNumGen())");						
						qPl.meanServTimes[j] = (double) alpha / (alpha + beta);
						logln(2, "((QueueingPlace) places[" + i + "]).meanServTimes[" + j + "] = alpha / (alpha + beta) = " + qPl.meanServTimes[j]);													
					} else if("BreitWigner".equals(distributionFunction)) {						
						if(colorRef.attributeValue("mean") == null || colorRef.attributeValue("gamma") == null || colorRef.attributeValue("cut") == null) {						
							logln("ERROR: Parameter \"mean\", \"gamma\" or \"cut\" of BreitWigner distribution function not set!");
							logln("Details:");
							logln("  place-num          = " + i);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + j);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 
							throw new SimQPNException();
						}
						double mean = Double.parseDouble(colorRef.attributeValue("mean"));
						double gamma = Double.parseDouble(colorRef.attributeValue("gamma"));
						double cut = Double.parseDouble(colorRef.attributeValue("cut"));						
						// Validate input parameters
						if (gamma <= 0)  {
							logln("ERROR: Invalid \"gamma\" parameter of BreitWigner distribution!");
							logln("Details: ");							
							logln("  gamma              = " + gamma);
							logln("  place-num          = " + i);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + j);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 
							throw new SimQPNException();																												
						}												
						// Initialize random number generator and meanServTimes
						qPl.randServTimeGen[j] = new BreitWigner(mean, gamma, cut, Simulator.nextRandNumGen());
						logln(2, "((QueueingPlace) places[" + i + "]).randServTimeGen[" + j + "] = new BreitWigner(" + mean + ", " + gamma + ", " + cut + ", Simulator.nextRandNumGen())");
						// NOTE: BreitWigner does not have a mean value! It is undefined. 
					} else if("BreitWignerMeanSquare".equals(distributionFunction)) {
						if(colorRef.attributeValue("mean") == null || colorRef.attributeValue("gamma") == null || colorRef.attributeValue("cut") == null) {
							logln("ERROR: Parameter \"mean\", \"gamma\" or \"cut\" of BreitWignerMeanSquare distribution function not set!");
							logln("Details:");
							logln("  place-num          = " + i);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + j);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 
							throw new SimQPNException();
						}
						double mean = Double.parseDouble(colorRef.attributeValue("mean"));
						double gamma = Double.parseDouble(colorRef.attributeValue("gamma"));
						double cut = Double.parseDouble(colorRef.attributeValue("cut"));						
						// Validate input parameters
						if (gamma <= 0)  {
							logln("ERROR: Invalid \"gamma\" parameter of BreitWignerMeanSquare distribution!");
							logln("Details: ");							
							logln("  gamma              = " + gamma);
							logln("  place-num          = " + i);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + j);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 
							throw new SimQPNException();																												
						}																		
						// Initialize random number generator and meanServTimes
						qPl.randServTimeGen[j] = new BreitWignerMeanSquare(mean, gamma, cut, Simulator.nextRandNumGen());
						logln(2, "((QueueingPlace) places[" + i + "]).randServTimeGen[" + j + "] = new BreitWignerMeanSquare(" + mean + ", " + gamma + ", " + cut + ", Simulator.nextRandNumGen())");
						// NOTE: BreitWigner does not have a mean value! It is undefined.
					} else if("ChiSquare".equals(distributionFunction)) {
						if(colorRef.attributeValue("freedom") == null) {
							logln("ERROR: Parameter \"freedom\" of ChiSquare distribution function not set!");
							logln("Details:");
							logln("  place-num          = " + i);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + j);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 							
							throw new SimQPNException();
						}
						double freedom = Double.parseDouble(colorRef.attributeValue("freedom"));
						// Validate input parameters
						if (!(freedom > 0))  {
							logln("ERROR: Invalid \"freedom\" parameter of ChiSquare distribution!");
							logln("Details: ");							
							logln("  freedom            = " + freedom);
							logln("  place-num          = " + i);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + j);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id"));
							throw new SimQPNException();																												
						}						
						// Initialize random number generator and meanServTimes
						qPl.randServTimeGen[j] = new ChiSquare(freedom, Simulator.nextRandNumGen());
						logln(2, "((QueueingPlace) places[" + i + "]).randServTimeGen[" + j + "] = new ChiSquare(" + freedom + ", Simulator.nextRandNumGen())");
						qPl.meanServTimes[j] = freedom;
						logln(2, "((QueueingPlace) places[" + i + "]).meanServTimes[" + j + "] = freedom = " + qPl.meanServTimes[j]);													
					} else if("Gamma".equals(distributionFunction)) {
						if(colorRef.attributeValue("alpha") == null || colorRef.attributeValue("lambda") == null) {
							logln("ERROR: Parameter \"alpha\" or \"lambda\" of Gamma distribution function not set!");
							logln("Details:");
							logln("  place-num          = " + i);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + j);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 														
							throw new SimQPNException();
						}
						double alpha = Double.parseDouble(colorRef.attributeValue("alpha"));
						double lambda = Double.parseDouble(colorRef.attributeValue("lambda"));
						// Validate input parameters
						if (!(alpha > 0 && lambda > 0))  {
							logln("ERROR: Invalid \"alpha\" or \"lambda\" parameter of Gamma distribution!");
							logln("Details: ");							
							logln("  alpha, lambda      = " + alpha + ", " + lambda);
							logln("  place-num          = " + i);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + j);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 
							throw new SimQPNException();																												
						}						
						// Initialize random number generator and meanServTimes
						qPl.randServTimeGen[j] = new Gamma(alpha, lambda, Simulator.nextRandNumGen());
						logln(2, "((QueueingPlace) places[" + i + "]).randServTimeGen[" + j + "] = new Gamma(" + alpha + ", " + lambda + ", Simulator.nextRandNumGen())");
						qPl.meanServTimes[j] = alpha * lambda;
						logln(2, "((QueueingPlace) places[" + i + "]).meanServTimes[" + j + "] = alpha * lambda = " + qPl.meanServTimes[j]);													
					} else if("Hyperbolic".equals(distributionFunction)) {
						if(colorRef.attributeValue("alpha") == null || colorRef.attributeValue("beta") == null) {
							logln("ERROR: Parameter \"alpha\" or \"beta\" of Hyperbolic distribution function not set!");
							logln("Details:");
							logln("  place-num          = " + i);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + j);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 																					
							throw new SimQPNException(); 
						}
						double alpha = Double.parseDouble(colorRef.attributeValue("alpha"));
						double beta = Double.parseDouble(colorRef.attributeValue("beta"));
						// Validate input parameters
						if (!(alpha > 0 && beta > 0))  {
							logln("ERROR: Invalid \"alpha\" or \"beta\" parameter of Hyperbolic distribution!");
							logln("Details:");
							logln("  alpha, beta        = " + alpha + ", " + beta);
							logln("  place-num          = " + i);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + j);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 
							throw new SimQPNException();																												
						}						
						// Initialize random number generator and meanServTimes
						qPl.randServTimeGen[j] = new Hyperbolic(alpha, beta, Simulator.nextRandNumGen());
						logln(2, "((QueueingPlace) places[" + i + "]).randServTimeGen[" + j + "] = new Hyperbolic(" + alpha + ", " + beta + ", Simulator.nextRandNumGen())");																		
						//SDK-TODO: find out how meanServTimes is computed?						
						//qPl.meanServTimes[j] = (double) ???;
						//logln(2, "((QueueingPlace) places[" + i + "]).meanServTimes[" + j + "] = ??? = " + qPl.meanServTimes[j]);
						logln("WARNING: meanServTimes for Hyperbolic distribution not initialized!");
						logln("         Might experience problems if indrStats is set to true!");						
						logln("Details:");
						logln("  place-num          = " + i);
						logln("  place.id           = " + place.attributeValue("id"));
						logln("  place.name         = " + place.attributeValue("name"));						
						logln("  colorRef-num       = " + j);
						logln("  colorRef.id        = " + colorRef.attributeValue("id"));
						logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 
					} else if("Exponential".equals(distributionFunction)) {
						if(colorRef.attributeValue("lambda") == null) {						
							logln("ERROR: Parameter \"lambda\" of Exponential distribution function not set!");
							logln("Details:");
							logln("  place-num          = " + i);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + j);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 
							throw new SimQPNException();																					
						}
						double lambda = Double.parseDouble(colorRef.attributeValue("lambda"));
						// Validate input parameters
						if (!(lambda > 0))  {
							logln("ERROR: Invalid \"lambda\" parameter of Exponential distribution!");
							logln("Details:");
							logln("  lambda             = " + lambda);
							logln("  place-num          = " + i);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + j);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 
							throw new SimQPNException();																												
						}						
						// Initialize random number generator and meanServTimes
						qPl.randServTimeGen[j] = new Exponential(lambda, Simulator.nextRandNumGen());
						logln(2, "((QueueingPlace) places[" + i + "]).randServTimeGen[" + j + "] = new Exponential(" + lambda + ", Simulator.nextRandNumGen())");
						qPl.meanServTimes[j] = (double) 1 / lambda;
						logln(2, "((QueueingPlace) places[" + i + "]).meanServTimes[" + j + "] = 1 / lambda = " + qPl.meanServTimes[j]);													
					} else if("ExponentialPower".equals(distributionFunction)) {
						if(colorRef.attributeValue("tau") == null) {
							logln("ERROR: Parameter \"tau\" of ExponentialPower distribution function not set!");
							logln("Details:");
							logln("  place-num          = " + i);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + j);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 
							throw new SimQPNException();																					
						}
						double tau = Double.parseDouble(colorRef.attributeValue("tau"));
						// Validate input parameters
						if (!(tau >= 1))  {
							logln("ERROR: Invalid \"tau\" parameter of ExponentialPower distribution!");
							logln("Details:");
							logln("  tau                = " + tau);
							logln("  place-num          = " + i);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + j);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 
							throw new SimQPNException();																												
						}						
						// Initialize random number generator and meanServTimes
						qPl.randServTimeGen[j] = new ExponentialPower(tau, Simulator.nextRandNumGen());
						logln(2, "((QueueingPlace) places[" + i + "]).randServTimeGen[" + j + "] = new ExponentialPower(" + tau + ", Simulator.nextRandNumGen())");
						//SDK-TODO: find out how meanServTimes is computed?						
						//qPl.meanServTimes[j] = (double) ???;
						//logln(2, "((QueueingPlace) places[" + i + "]).meanServTimes[" + j + "] = ??? = " + qPl.meanServTimes[j]);													
						logln("WARNING: meanServTimes for ExponentialPower distribution not initialized!");
						logln("         Might experience problems if indrStats is set to true!");						
						logln("Details:");
						logln("  place-num          = " + i);
						logln("  place.id           = " + place.attributeValue("id"));
						logln("  place.name         = " + place.attributeValue("name"));						
						logln("  colorRef-num       = " + j);
						logln("  colorRef.id        = " + colorRef.attributeValue("id"));
						logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 
					} else if("Logarithmic".equals(distributionFunction)) {
						if(colorRef.attributeValue("p") == null) {
							logln("ERROR: Parameter \"p\" of Logarithmic distribution function not set!");
							logln("Details:");
							logln("  place-num          = " + i);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + j);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 
							throw new SimQPNException();
						}						
						double p = Double.parseDouble(colorRef.attributeValue("p"));
						// Validate input parameters
						if (!(0 < p && p < 1))  {
							logln("ERROR: Invalid \"p\" parameter of Logarithmic distribution!");
							logln("Details:");
							logln("  p                  = " + p);
							logln("  place-num          = " + i);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + j);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 
							throw new SimQPNException();																												
						}						
						// Initialize random number generator and meanServTimes
						qPl.randServTimeGen[j] = new Logarithmic(p, Simulator.nextRandNumGen());
						logln(2, "((QueueingPlace) places[" + i + "]).randServTimeGen[" + j + "] = new Logarithmic(" + p + ", Simulator.nextRandNumGen())");						 						
						qPl.meanServTimes[j] = (double) ((-1) * p) / (Math.log(1-p) * (1-p));
						logln(2, "((QueueingPlace) places[" + i + "]).meanServTimes[" + j + "] = ((-1) * p) / (Math.log(1-p) * (1-p)) = " + qPl.meanServTimes[j]);													
					} else if("Normal".equals(distributionFunction)) {
						if(colorRef.attributeValue("mean") == null || colorRef.attributeValue("stdDev") == null) {
							logln("ERROR: Parameter \"mean\" or \"stdDev\" of Normal distribution function not set!");
							logln("Details:");
							logln("  place-num          = " + i);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + j);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 																					
							throw new SimQPNException();
						}
						double mean = Double.parseDouble(colorRef.attributeValue("mean"));
						double stdDev = Double.parseDouble(colorRef.attributeValue("stdDev"));
						// Validate input parameters
						if (!(stdDev > 0))  {
							logln("ERROR: Invalid \"stdDev\" parameter of Normal distribution!");
							logln("Details:");
							logln("  stdDev             = " + stdDev);
							logln("  place-num          = " + i);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + j);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 
							throw new SimQPNException();																												
						}						
						// Initialize random number generator and meanServTimes
						qPl.randServTimeGen[j] = new Normal(mean, stdDev, Simulator.nextRandNumGen());																									
						logln(2, "((QueueingPlace) places[" + i + "]).randServTimeGen[" + j + "] = new Normal(" + mean + ", " + stdDev + ", Simulator.nextRandNumGen())");
						qPl.meanServTimes[j] = mean;
						logln(2, "((QueueingPlace) places[" + i + "]).meanServTimes[" + j + "] = mean = " + qPl.meanServTimes[j]);													
					} else if("StudentT".equals(distributionFunction)) {
						if(colorRef.attributeValue("freedom") == null) {
							logln("ERROR: Parameter \"freedom\" of StudentT distribution function not set!");
							logln("Details:");
							logln("  place-num          = " + i);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + j);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 														
							throw new SimQPNException();
						}
						double freedom = Double.parseDouble(colorRef.attributeValue("freedom"));
						// Validate input parameters
						if (!(freedom > 0))  {
							logln("ERROR: Invalid \"freedom\" parameter of StudentT distribution!");
							logln("Details:");
							logln("  freedom            = " + freedom);
							logln("  place-num          = " + i);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + j);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id"));
							throw new SimQPNException();																												
						}						
						// Initialize random number generator and meanServTimes
						qPl.randServTimeGen[j] = new StudentT(freedom, Simulator.nextRandNumGen());																									
						logln(2, "((QueueingPlace) places[" + i + "]).randServTimeGen[" + j + "] = new StudentT(" + freedom + ", Simulator.nextRandNumGen())");
						//NOTE: The mean of the StudentT distribution is 0 for freedom > 1, otherwise it is undefined.												
						if (freedom > 1) {
							qPl.meanServTimes[j] = 0;
							logln(2, "((QueueingPlace) places[" + i + "]).meanServTimes[" + j + "] = " + qPl.meanServTimes[j]);													
						}
						else {
							logln("WARNING: meanServTimes for StudentT distribution not initialized!");
							logln("         Might experience problems if indrStats is set to true!");						
							logln("Details:");
							logln("  place-num          = " + i);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + j);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 
						}							
					} else if("Uniform".equals(distributionFunction)) {
						if(colorRef.attributeValue("min") == null || colorRef.attributeValue("max") == null) {
							logln("ERROR: Parameter \"min\" or \"max\" of Uniform distribution function not set!");
							logln("Details:");
							logln("  place-num          = " + i);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + j);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 																					
							throw new SimQPNException();
						}
						double min = Double.parseDouble(colorRef.attributeValue("min"));
						double max = Double.parseDouble(colorRef.attributeValue("max"));		
						if (!(min < max))  {
							logln("ERROR: Invalid \"min\" or \"max\" parameter of Uniform distribution!");
							logln("Details:");
							logln("  min,max            = " + min + "," + max);
							logln("  place-num          = " + i);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + j);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id"));
							throw new SimQPNException();																												
						}						
						// Initialize random number generator and meanServTimes
						qPl.randServTimeGen[j] = new Uniform(min, max, Simulator.nextRandNumGen());																									
						logln(2, "((QueueingPlace) places[" + i + "]).randServTimeGen[" + j + "] = new Uniform(" + min + ", " + max + ", Simulator.nextRandNumGen())");						
						qPl.meanServTimes[j] = (double) (min + max) / 2;
						logln(2, "((QueueingPlace) places[" + i + "]).meanServTimes[" + j + "] = (min + max) / 2 = " + qPl.meanServTimes[j]);
					} else if("VonMises".equals(distributionFunction)) {
						if(colorRef.attributeValue("freedom") == null) {
							logln("ERROR: Parameter \"freedom\" of VonMises distribution function not set!");
							logln("Details:");
							logln("  place-num          = " + i);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + j);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 														
							throw new SimQPNException();
						}
						double freedom = Double.parseDouble(colorRef.attributeValue("freedom"));
						if (!(freedom > 0))  {
							logln("ERROR: Invalid \"k\" parameter of VonMises distribution!");
							logln("Details:");
							logln("  k                  = " + freedom);
							logln("  place-num          = " + i);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + j);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id"));
							throw new SimQPNException();																												
						}
						//TODO: Check parameters. Rename "freedom" to "k" to avoid confusion.						
						// Initialize random number generator and meanServTimes
						qPl.randServTimeGen[j] = new VonMises(freedom, Simulator.nextRandNumGen());																									
						logln(2, "((QueueingPlace) places[" + i + "]).randServTimeGen[" + j + "] = new VonMises(" + freedom + ", Simulator.nextRandNumGen())");
						//SDK-TODO: find out how meanServTimes is computed?						
						//qPl.meanServTimes[j] = (double) ???;
						//logln(2, "((QueueingPlace) places[" + i + "]).meanServTimes[" + j + "] = ??? = " + qPl.meanServTimes[j]);
						logln("WARNING: meanServTimes for VonMises distribution not initialized!");
						logln("         Might experience problems if indrStats is set to true!");						
						logln("Details:");
						logln("  place-num          = " + i);
						logln("  place.id           = " + place.attributeValue("id"));
						logln("  place.name         = " + place.attributeValue("name"));						
						logln("  colorRef-num       = " + j);
						logln("  colorRef.id        = " + colorRef.attributeValue("id"));
						logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 
					} else if("Empirical".equals(distributionFunction)) {
						if (colorRef.attributeValue("pdf_filename") == null) {
							logln("ERROR: Parameter \"pdf_filename\" of Empirical distribution function not set!");
							logln("Details:");
							logln("  place-num          = " + i);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + j);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 																					
							throw new SimQPNException();
						}
						String pdf_filename = colorRef.attributeValue("pdf_filename");
						double[] pdf;						
						File pdfFile = new File(pdf_filename);
						if (!pdfFile.exists()) {							
							logln("ERROR: PDF file of Empirical distribution does not exist!");
							logln("Details:");
							logln("  place-num              = " + i);
							logln("  place.id               = " + place.attributeValue("id"));
							logln("  place.name             = " + place.attributeValue("name"));						
							logln("  colorRef-num           = " + j);
							logln("  colorRef.id            = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id      = " + colorRef.attributeValue("color-id"));
							logln("  colorRef.pdf_filename  = " + pdf_filename);							
							throw new SimQPNException();													
						}						
						BufferedReader input = null;
						try {
							input = new BufferedReader(new FileReader(pdfFile));
							String line = null;
							if ((line = input.readLine()) == null) {
								logln("ERROR: Invalid PDF file of Empirical distribution!");
								logln("Details:");
								logln("  place-num              = " + i);
								logln("  place.id               = " + place.attributeValue("id"));
								logln("  place.name             = " + place.attributeValue("name"));						
								logln("  colorRef-num           = " + j);
								logln("  colorRef.id            = " + colorRef.attributeValue("id"));
								logln("  colorRef.color-id      = " + colorRef.attributeValue("color-id"));
								logln("  colorRef.pdf_filename  = " + pdf_filename);							
								throw new SimQPNException();													
							}					      
							//SDK-TODO: See if it would be better to have values on separate lines?
							String[] params = line.split(";");
							pdf = new double[params.length];					      
							for (int x = 0; x < params.length; x++) 
								pdf[x] = Double.parseDouble(params[x]);							
						}
						catch (IOException ex) {
							logln("ERROR: Invalid PDF file of Empirical distribution!");
							logln("Details:");
							logln("  place-num              = " + i);
							logln("  place.id               = " + place.attributeValue("id"));
							logln("  place.name             = " + place.attributeValue("name"));						
							logln("  colorRef-num           = " + j);
							logln("  colorRef.id            = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id      = " + colorRef.attributeValue("color-id"));
							logln("  colorRef.pdf_filename  = " + pdf_filename);
							log(ex);
							throw new SimQPNException();													
						}
						finally {
							try {
								if (input != null) 
									input.close();								
							}
							catch (IOException ex) {
								logln("ERROR: Cannot close PDF file " + pdf_filename);
								log(ex);
								throw new SimQPNException();
							}
						}
						// Initialize random number generator and meanServTimes
						qPl.randServTimeGen[j] = new Empirical(pdf, cern.jet.random.Empirical.LINEAR_INTERPOLATION, Simulator.nextRandNumGen());
						logln(2, "((QueueingPlace) places[" + i + "]).randServTimeGen[" + j + "] = new Empirical(" + pdf_filename + ", LINEAR_INTERPOLATION, Simulator.nextRandNumGen())");
						//SDK-TODO: find out how meanServTimes is computed?						
						//qPl.meanServTimes[j] = (double) ???;
						//logln(2, "((QueueingPlace) places[" + i + "]).meanServTimes[" + j + "] = ??? = " + qPl.meanServTimes[j]);
						logln("WARNING: meanServTimes for Empirical distribution not initialized!");
						logln("         Might experience problems if indrStats is set to true!");						
						logln("Details:");
						logln("  place-num          = " + i);
						logln("  place.id           = " + place.attributeValue("id"));
						logln("  place.name         = " + place.attributeValue("name"));						
						logln("  colorRef-num       = " + j);
						logln("  colorRef.id        = " + colorRef.attributeValue("id"));
						logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 
					} else if("Deterministic".equals(distributionFunction)) {
						// Parameters p1, p2 and p3 passed from QPE.						
						//SDK-TODO: implement support for this, use p1 to set service time
						//TEMP
						logln("ERROR: Deterministic distribution function currently not supported!");
						logln("Details:");
						logln("  place-num          = " + i);
						logln("  place.id           = " + place.attributeValue("id"));
						logln("  place.name         = " + place.attributeValue("name"));						
						logln("  colorRef-num       = " + j);
						logln("  colorRef.id        = " + colorRef.attributeValue("id"));
						logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 																											
						throw new SimQPNException();						
					}
				}
			}
		}
		
		// --------------------------------------------------------------------------------------------------------
		// CONFIGURE INITIAL MARKING
		// --------------------------------------------------------------------------------------------------------
		// Note: All initial tokens should be in ordianary places or depositories.
		// No initial tokens are allowed in the queues.

		logln(2, "\n/////////////////////////////////////////////");
		logln(2, "// Configure Initial Marking");
		placeIterator = placeList.iterator();
		for (int i = 0; placeIterator.hasNext(); i++) {
			Element place = (Element) placeIterator.next();
			xpathSelector = DocumentHelper.createXPath("color-refs/color-ref");
			Iterator colorRefIterator = xpathSelector.selectNodes(place).iterator();
			for (int j = 0; colorRefIterator.hasNext(); j++) {
				Element colorRef = (Element) colorRefIterator.next();
				if(colorRef.attributeValue("initial-population") == null) {
					logln("Error: Queueing place' \"initial-population\" parameter not set!");					
					logln("  place-num          = " + i);
					logln("  place.id           = " + place.attributeValue("id"));
					logln("  place.name         = " + place.attributeValue("name"));						
					logln("  colorRef-num       = " + j);
					logln("  colorRef.id        = " + colorRef.attributeValue("id"));
					logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id")); 																											
					throw new SimQPNException();
				}
				places[i].tokenPop[j] = Integer.parseInt(colorRef.attributeValue("initial-population"));
				logln(2, "places[" + i + "].tokenPop[" + j + "] = " + places[i].tokenPop[j] + ";");				
			}
		}

		// *****************************************************************************************************************
		// END-MODEL-DEFINITION
		// *****************************************************************************************************************

		// BEGIN-CONFIG
		// ------------------------------------------------------------------------------------------------------

		logln(2, "\n/////////////////////////////////////////////");
		logln(2, "// Misc settings");
		Element simulatorSettings = getSettings(net, configuration);
						
		if ("RELPRC".equals(simulatorSettings.attributeValue("stopping-rule"))) {
			stoppingRule = RELPRC;
			logln(2, "stoppingRule = RELPRC;");			
		} else if ("ABSPRC".equals(simulatorSettings.attributeValue("stopping-rule"))) {
			stoppingRule = ABSPRC;
			logln(2, "stoppingRule = ABSPRC;");			
		} else if ("FIXEDLEN".equals(simulatorSettings.attributeValue("stopping-rule"))) {
			stoppingRule = FIXEDLEN;
			logln(2, "stoppingRule = FIXEDLEN;");			
		} else {
			logln("Error: Configuration parameter \"stopping-rule\" not configured correctly!");					
			logln("  configuration = " + configuration);
			throw new SimQPNException();
		}
		// Only for scenario 1 stopping-rule is set. For others it is set to FIXEDLEN.		
		if (Integer.parseInt(simulatorSettings.attributeValue("scenario")) != 1 && stoppingRule != FIXEDLEN)  {
			logln("Error: Stopping rule \"" + simulatorSettings.attributeValue("stopping-rule") + "\" is not supported for the chosen analysis method!");					
			logln("  configuration = " + configuration);
			throw new SimQPNException();
		}

		if (simulatorSettings.attributeValue("total-run-length") == null) {
			logln("Error: Configuration parameter \"total-run-length\" is not configured!");					
			logln("  configuration = " + configuration);
			throw new SimQPNException();
		}		
		totRunLen = Double.parseDouble(simulatorSettings.attributeValue("total-run-length"));
		logln(2, "totRunLen = " + totRunLen + ";");
		
		if (Integer.parseInt(simulatorSettings.attributeValue("scenario")) != 3) {		
			if (simulatorSettings.attributeValue("ramp-up-length") == null) {
				logln("Error: Configuration parameter \"ramp-up-length\" is not configured!");					
				logln("  configuration = " + configuration);
				throw new SimQPNException();
			}
			rampUpLen = Double.parseDouble(simulatorSettings.attributeValue("ramp-up-length"));
			logln(2, "rampUpLen = " + rampUpLen + ";");			
		} else { // Method of Welch
			rampUpLen = totRunLen; // Note: The method of Welch is currently run until rampUpLen is reached.
		}
						
		if(stoppingRule != FIXEDLEN) {
			if(simulatorSettings.attributeValue("time-between-stop-checks") == null) {						
				logln("Error: Configuration parameter \"time-between-stop-checks\" is not configured!");					
				logln("  configuration = " + configuration);			
				throw new SimQPNException();
			}		
			timeBtwChkStops = Double.parseDouble(simulatorSettings.attributeValue("time-between-stop-checks"));
			logln(2, "timeBtwChkStops = " + timeBtwChkStops + ";");			
			if(simulatorSettings.attributeValue("seconds-between-stop-checks") == null) {						
				logln("Error: Configuration parameter \"seconds-between-stop-checks\" is not configured!");					
				logln("  configuration = " + configuration);			
				throw new SimQPNException();
			}		
			secondsBtwChkStops = Double.parseDouble(simulatorSettings.attributeValue("seconds-between-stop-checks"));
			logln(2, "secondsBtwChkStops = " + secondsBtwChkStops + ";");
		}
		
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
		
		if (analMethod != BATCH_MEANS && stoppingRule != FIXEDLEN) {
			logln("Error: Stopping rule \"" + simulatorSettings.attributeValue("stopping-rule") + "\" is not supported for the batch means analysis method!");					
			logln("  configuration = " + configuration);
			throw new SimQPNException();
		}
		// CONFIG: Whether to use indirect estimators for FCFS queues
		for (int p = 0; p < numPlaces; p++) {
			Place pl = places[p];
			if (pl.statsLevel >= 3 && pl instanceof QueueingPlace) {
				((QueueingPlace) pl).queueStats.indrStats = false;
				logln(2, "places[" + p + "].queueStats.indrStats = false;");				
			}
		}

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
			placeIterator = placeList.iterator();
			for (int p = 0; placeIterator.hasNext(); p++) {
				Element place = (Element) placeIterator.next();
				Place pl = places[p];
				if (pl.statsLevel >= 3) {
					logln(2, "places[" + p + "]");
					xpathSelector = DocumentHelper.createXPath("color-refs/color-ref");
					Iterator colorRefIterator = xpathSelector.selectNodes(place).iterator();
					for (int cr = 0; colorRefIterator.hasNext(); cr++) {
						Element colorRef = (Element) colorRefIterator.next();
						Element colorRefSettings = getSettings(colorRef, configuration);
						// Initialize Place (or Depository if pl is QueueingPlace)
						if (colorRefSettings != null) {
							if (colorRefSettings.attributeValue("signLev") == null) {
								logln("Error: Configuration parameter \"signLev\" for Batch Means Method is missing!");
								logln("Details:");
								logln("  configuration = " + configuration);						
								logln("  place-num          = " + p);
								logln("  place.id           = " + place.attributeValue("id"));
								logln("  place.name         = " + place.attributeValue("name"));						
								logln("  colorRef-num       = " + cr);
								logln("  colorRef.id        = " + colorRef.attributeValue("id"));
								logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id"));
								throw new SimQPNException();
							}
							pl.placeStats.signLevST[cr] = Double.parseDouble(colorRefSettings.attributeValue("signLev"));
							logln(2, "-- placeStats.signLevST[" + cr + "] = " + pl.placeStats.signLevST[cr]);							

							if (colorRefSettings.attributeValue("reqAbsPrc") == null) {
								logln("Error: Configuration parameter \"reqAbsPrc\" for Batch Means Method is missing!");
								logln("Details:");
								logln("  configuration = " + configuration);						
								logln("  place-num          = " + p);
								logln("  place.id           = " + place.attributeValue("id"));
								logln("  place.name         = " + place.attributeValue("name"));						
								logln("  colorRef-num       = " + cr);
								logln("  colorRef.id        = " + colorRef.attributeValue("id"));
								logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id"));
								throw new SimQPNException();
							}
							pl.placeStats.reqAbsPrc[cr] = Double.parseDouble(colorRefSettings.attributeValue("reqAbsPrc"));
							logln(2, "-- placeStats.reqAbsPrc[" + cr + "] = " + pl.placeStats.reqAbsPrc[cr]);
							
							if (colorRefSettings.attributeValue("reqRelPrc") == null) {								
								logln("Error: Configuration parameter \"reqRelPrc\" for Batch Means Method is missing!");
								logln("Details:");
								logln("  configuration = " + configuration);						
								logln("  place-num          = " + p);
								logln("  place.id           = " + place.attributeValue("id"));
								logln("  place.name         = " + place.attributeValue("name"));						
								logln("  colorRef-num       = " + cr);
								logln("  colorRef.id        = " + colorRef.attributeValue("id"));
								logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id"));
								throw new SimQPNException();
							}
							pl.placeStats.reqRelPrc[cr] = Double.parseDouble(colorRefSettings.attributeValue("reqRelPrc"));
							logln(2, "-- placeStats.reqRelPrc[" + cr + "] = " + pl.placeStats.reqRelPrc[cr]);

							if (colorRefSettings.attributeValue("batchSize") == null) {
								logln("Error: Configuration parameter \"batchSize\" for Batch Means Method is missing!");
								logln("Details:");
								logln("  configuration = " + configuration);						
								logln("  place-num          = " + p);
								logln("  place.id           = " + place.attributeValue("id"));
								logln("  place.name         = " + place.attributeValue("name"));						
								logln("  colorRef-num       = " + cr);
								logln("  colorRef.id        = " + colorRef.attributeValue("id"));
								logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id"));								
								throw new SimQPNException();
							}
							pl.placeStats.batchSizeST[cr] = Integer.parseInt(colorRefSettings.attributeValue("batchSize"));
							logln(2, "-- placeStats.batchSizeST[" + cr + "] = " + pl.placeStats.batchSizeST[cr]);							

							if (colorRefSettings.attributeValue("minBatches") == null) {
								logln("Error: Configuration parameter \"minBatches\" for Batch Means Method is missing!");
								logln("Details:");
								logln("  configuration = " + configuration);						
								logln("  place-num          = " + p);
								logln("  place.id           = " + place.attributeValue("id"));
								logln("  place.name         = " + place.attributeValue("name"));						
								logln("  colorRef-num       = " + cr);
								logln("  colorRef.id        = " + colorRef.attributeValue("id"));
								logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id"));																
								throw new SimQPNException();
							}
							pl.placeStats.minBatches[cr] = Integer.parseInt(colorRefSettings.attributeValue("minBatches"));
							logln(2, "-- placeStats.minBatches[" + cr + "] = " + pl.placeStats.minBatches[cr]);							

							if (colorRefSettings.attributeValue("numBMeansCorlTested") == null) {
								logln("Error: Configuration parameter \"numBMeansCorlTested\" for Batch Means Method is missing!");
								logln("Details:");
								logln("  configuration = " + configuration);						
								logln("  place-num          = " + p);
								logln("  place.id           = " + place.attributeValue("id"));
								logln("  place.name         = " + place.attributeValue("name"));						
								logln("  colorRef-num       = " + cr);
								logln("  colorRef.id        = " + colorRef.attributeValue("id"));
								logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id"));																								
								throw new SimQPNException();
							}
							pl.placeStats.numBMeansCorlTested[cr] = Integer.parseInt(colorRefSettings.attributeValue("numBMeansCorlTested"));
							logln(2, "-- placeStats.numBMeansCorlTested[" + cr + "] = " + pl.placeStats.numBMeansCorlTested[cr]);
							
							// Note that if (minBatches > 0 && numBMeansCorlTested[c] > 0),
							// minBatches[c] must be > numBMeansCorlTested[c]!
							if (pl.placeStats.minBatches[cr] > 0 && 
								pl.placeStats.numBMeansCorlTested[cr] > 0 && 
								!(pl.placeStats.minBatches[cr] > pl.placeStats.numBMeansCorlTested[cr])) {
								logln("Error: Place.placeStats.minBatches[c] must be greater than Place.placeStats.numBMeansCorlTested[c]!");
								logln("Details:");
								logln("  configuration = " + configuration);						
								logln("  place-num          = " + p);
								logln("  place.id           = " + place.attributeValue("id"));
								logln("  place.name         = " + place.attributeValue("name"));						
								logln("  colorRef-num       = " + cr);
								logln("  colorRef.id        = " + colorRef.attributeValue("id"));
								logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id"));																								
								throw new SimQPNException();
							}
							// If (numBMeansCorlTested[c] <= 0) no correlation test is done!
							// Note that numBMeansCorlTested must be even!
							if (pl.placeStats.numBMeansCorlTested[cr] % 2 != 0) {
								logln("Error: Place.placeStats.numBMeansCorlTested[c] must be even!");
								logln("Details:");
								logln("  configuration = " + configuration);						
								logln("  place-num          = " + p);
								logln("  place.id           = " + place.attributeValue("id"));
								logln("  place.name         = " + place.attributeValue("name"));						
								logln("  colorRef-num       = " + cr);
								logln("  colorRef.id        = " + colorRef.attributeValue("id"));
								logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id"));																								
								throw new SimQPNException();
							}
						} else {							
							logln("Error: SimQPN configuration parameters for Batch Means Method are missing!");
							logln("Details:");
							logln("  configuration = " + configuration);						
							logln("  place-num          = " + p);
							logln("  place.id           = " + place.attributeValue("id"));
							logln("  place.name         = " + place.attributeValue("name"));						
							logln("  colorRef-num       = " + cr);
							logln("  colorRef.id        = " + colorRef.attributeValue("id"));
							logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id"));
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

						// Initialize Queue if pl is QueueingPlace
						if (pl instanceof QueueingPlace) {
							QueueingPlace qpl = (QueueingPlace) pl;
							if (colorRefSettings != null) {
								if (colorRefSettings.attributeValue("queueSignLev") == null) {
									logln("Error: Configuration parameter \"queueSignLev\" for Batch Means Method is missing!");
									logln("Details:");
									logln("  configuration = " + configuration);						
									logln("  place-num          = " + p);
									logln("  place.id           = " + place.attributeValue("id"));
									logln("  place.name         = " + place.attributeValue("name"));						
									logln("  colorRef-num       = " + cr);
									logln("  colorRef.id        = " + colorRef.attributeValue("id"));
									logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id"));																																	
									throw new SimQPNException();
								}								
								qpl.queueStats.signLevST[cr] = Double.parseDouble(colorRefSettings.attributeValue("queueSignLev"));
								logln(2, "-- queueStats.signLevST[" + cr + "] = " + qpl.queueStats.signLevST[cr]);

								if (colorRefSettings.attributeValue("queueReqAbsPrc") == null) {
									logln("Error: Configuration parameter \"queueReqAbsPrc\" for Batch Means Method is missing!");
									logln("Details:");
									logln("  configuration = " + configuration);						
									logln("  place-num          = " + p);
									logln("  place.id           = " + place.attributeValue("id"));
									logln("  place.name         = " + place.attributeValue("name"));						
									logln("  colorRef-num       = " + cr);
									logln("  colorRef.id        = " + colorRef.attributeValue("id"));
									logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id"));																																	
									throw new SimQPNException();
								}
								qpl.queueStats.reqAbsPrc[cr] = Double.parseDouble(colorRefSettings.attributeValue("queueReqAbsPrc"));
								logln(2, "-- queueStats.reqAbsPrc[" + cr + "] = " + qpl.queueStats.reqAbsPrc[cr]);

								if (colorRefSettings.attributeValue("queueReqRelPrc") == null) {
									logln("Error: Configuration parameter \"queueReqRelPrc\" for Batch Means Method is missing!");
									logln("Details:");
									logln("  configuration = " + configuration);						
									logln("  place-num          = " + p);
									logln("  place.id           = " + place.attributeValue("id"));
									logln("  place.name         = " + place.attributeValue("name"));						
									logln("  colorRef-num       = " + cr);
									logln("  colorRef.id        = " + colorRef.attributeValue("id"));
									logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id"));																																	
									throw new SimQPNException();
								}
								qpl.queueStats.reqRelPrc[cr] = Double.parseDouble(colorRefSettings.attributeValue("queueReqRelPrc"));
								logln(2, "-- queueStats.reqRelPrc[" + cr + "] = " + qpl.queueStats.reqRelPrc[cr]);
								
								if (colorRefSettings.attributeValue("queueBatchSize") == null) {
									logln("Error: Configuration parameter \"queueBatchSize\" for Batch Means Method is missing!");
									logln("Details:");
									logln("  configuration = " + configuration);						
									logln("  place-num          = " + p);
									logln("  place.id           = " + place.attributeValue("id"));
									logln("  place.name         = " + place.attributeValue("name"));						
									logln("  colorRef-num       = " + cr);
									logln("  colorRef.id        = " + colorRef.attributeValue("id"));
									logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id"));																																	
									throw new SimQPNException();
								}								
								qpl.queueStats.batchSizeST[cr] = Integer.parseInt(colorRefSettings.attributeValue("queueBatchSize"));
								logln(2, "-- queueStats.batchSizeST[" + cr + "] = " + qpl.queueStats.batchSizeST[cr]);

								if(colorRefSettings.attributeValue("queueMinBatches") == null) {
									logln("Error: Configuration parameter \"queueMinBatches\" for Batch Means Method is missing!");
									logln("Details:");
									logln("  configuration = " + configuration);						
									logln("  place-num          = " + p);
									logln("  place.id           = " + place.attributeValue("id"));
									logln("  place.name         = " + place.attributeValue("name"));						
									logln("  colorRef-num       = " + cr);
									logln("  colorRef.id        = " + colorRef.attributeValue("id"));
									logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id"));																																	
									throw new SimQPNException();
								}
								qpl.queueStats.minBatches[cr] = Integer.parseInt(colorRefSettings.attributeValue("queueMinBatches"));
								logln(2, "-- queueStats.minBatches[" + cr + "] = " + qpl.queueStats.minBatches[cr]);								

								if(colorRefSettings.attributeValue("queueNumBMeansCorlTested") == null) {
									logln("Error: Configuration parameter \"queueNumBMeansCorlTested\" for Batch Means Method is missing!");
									logln("Details:");
									logln("  configuration = " + configuration);						
									logln("  place-num          = " + p);
									logln("  place.id           = " + place.attributeValue("id"));
									logln("  place.name         = " + place.attributeValue("name"));						
									logln("  colorRef-num       = " + cr);
									logln("  colorRef.id        = " + colorRef.attributeValue("id"));
									logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id"));																																	
									throw new SimQPNException();
								}								
								qpl.queueStats.numBMeansCorlTested[cr] = Integer.parseInt(colorRefSettings.attributeValue("queueNumBMeansCorlTested"));
								logln(2, "-- queueStats.numBMeansCorlTested[" + cr + "] = " + qpl.queueStats.numBMeansCorlTested[cr]);								
								
								if (qpl.queueStats.minBatches[cr] > 0 && 
									qpl.queueStats.numBMeansCorlTested[cr] > 0 && 
									!(qpl.queueStats.minBatches[cr] > qpl.queueStats.numBMeansCorlTested[cr])) {
									logln("Error: QueueingPlace.queueStats.minBatches[c] must be greater than QueueingPlace.queueStats.numBMeansCorlTested[c]!");
									logln("Details:");
									logln("  configuration = " + configuration);						
									logln("  place-num          = " + p);
									logln("  place.id           = " + place.attributeValue("id"));
									logln("  place.name         = " + place.attributeValue("name"));						
									logln("  colorRef-num       = " + cr);
									logln("  colorRef.id        = " + colorRef.attributeValue("id"));
									logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id"));																								
									throw new SimQPNException();
								}
								// If (numBMeansCorlTested[c] <= 0) no correlation test is done!								
								// Note that numBMeansCorlTested must be even!
								if (qpl.queueStats.numBMeansCorlTested[cr] % 2 != 0) {
									logln("Error: QueueingPlace.queueStats.numBMeansCorlTested[c] must be even!");
									logln("Details:");
									logln("  configuration = " + configuration);						
									logln("  place-num          = " + p);
									logln("  place.id           = " + place.attributeValue("id"));
									logln("  place.name         = " + place.attributeValue("name"));						
									logln("  colorRef-num       = " + cr);
									logln("  colorRef.id        = " + colorRef.attributeValue("id"));
									logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id"));																								
									throw new SimQPNException();
								}
							} else {
								logln("Error: SimQPN configuration parameters for Batch Means Method are missing!");
								logln("Details:");
								logln("  configuration = " + configuration);						
								logln("  place-num          = " + p);
								logln("  place.id           = " + place.attributeValue("id"));
								logln("  place.name         = " + place.attributeValue("name"));						
								logln("  colorRef-num       = " + cr);
								logln("  colorRef.id        = " + colorRef.attributeValue("id"));
								logln("  colorRef.color-id  = " + colorRef.attributeValue("color-id"));
								throw new SimQPNException();
								/* ORIGINAL MANUAL CONFIGURATION
								qpl.queueStats.signLevST[cr] = 0.05;		// e.g. 0.05 ---> 95% c.i.; 0.1 ---> 90%
								logln(2, "-- queueStats.signLevST[" + cr + "] = " + qpl.queueStats.signLevST[cr]);								
								qpl.queueStats.reqAbsPrc[cr] = 50;
								logln(2, "-- queueStats.reqAbsPrc[" + cr + "] = " + qpl.queueStats.reqAbsPrc[cr]);								
								qpl.queueStats.reqRelPrc[cr] = 0.05;		// e.g. 0.1 ---> 10% relative precision
								logln(2, "-- queueStats.reqRelPrc[" + cr + "] = " + qpl.queueStats.reqRelPrc[cr]);								
								qpl.queueStats.batchSizeST[cr] = 200;		// Initial batch size
								logln(2, "-- queueStats.batchSizeST[" + cr + "] = " + qpl.queueStats.batchSizeST[cr]);								
								qpl.queueStats.minBatches[cr] = 60; 
									// Note that if (minBatches > 0 && numBMeansCorlTested[c] > 0),
									// minBatches[c] must be > numBMeansCorlTested[c]!
								logln(2, "-- queueStats.minBatches[" + cr + "] = " + qpl.queueStats.minBatches[cr]);								
								qpl.queueStats.numBMeansCorlTested[cr] = 50; 
									// Note that numBMeansCorlTested must be even!
									// If (numBMeansCorlTested[c] <= 0) no correlation test is done!
								logln(2, "-- queueStats.numBMeansCorlTested[" + cr + "] = " + qpl.queueStats.numBMeansCorlTested[cr]);
							    */
							}
						}
					}
				}
			}
		}

		// END-CONFIG
		// ---------------------------------------------------------------------------------------------------------

		// Working variables
		inRampUp = true;
		endRampUpClock = 0;
		endRunClock = 0;
		msrmPrdLen = 0; // Set at the end of the run when the actual length is known.
		beginRunWallClock = 0;
		endRunWallClock = 0;
		runWallClockTime = 0;

		clock = 0;	// Note that it has been assumed throughout the code that
		   			// the simulation starts at virtual time 0.

		eventList = new LinkedList();

		// Make sure clock has been initialized before calling init below
		// Call places[i].init() first and then thans[i].init()
		for (int i = 0; i < numPlaces; i++)
			places[i].init();
		for (int i = 0; i < numTrans; i++)
			trans[i].init();
	}

	/**
	 * Method nextRandNumGen - returns a uniform random number generator
	 * 
	 * @param
	 * @return int
	 * @exception
	 */
	public static RandomElement nextRandNumGen() throws SimQPNException {
		RandomElement randomElement = null;
		int nextSeed = 0;

		if (randGenClass == DRand) {
			if (useRandSeedTable) {
				nextSeed = randSeedGen.nextSeed();
				while (!(nextSeed >= 0 && nextSeed < 1073741823))
					nextSeed = randSeedGen.nextSeed();
				randomElement = new DRand(nextSeed);
			} else {
				nextSeed = randNumGen.nextIntFromTo(0, 1073741823 - 1);
				randomElement = new DRand(nextSeed);
			}
		} else if (randGenClass == MersenneTwister) {
			if (useRandSeedTable) {
				nextSeed = randSeedGen.nextSeed();
				// The seed can be any 32-bit integer except 0. 
				// Shawn J. Cokus commented that perhaps the seed should preferably be odd.
				// while (nextSeed % 2 == 0) nextSeed = randSeedGen.nextSeed();
				while (nextSeed == 0)
					nextSeed = randSeedGen.nextSeed();
				randomElement = new MersenneTwister(nextSeed);
			} else {
				nextSeed = randNumGen.nextIntFromTo(Integer.MIN_VALUE, Integer.MAX_VALUE);
				// The seed can be any 32-bit integer except 0. 
				// Shawn J. Cokus commented that perhaps the seed should preferably be odd.
				// while (nextSeed % 2 == 0) nextSeed = randNumGen.nextIntFromTo(Integer.MIN_VALUE, Integer.MAX_VALUE);				
				while (nextSeed == 0)
					nextSeed = randNumGen.nextIntFromTo(Integer.MIN_VALUE, Integer.MAX_VALUE);
				randomElement = new MersenneTwister(nextSeed);
			}
		} else {
			logln("Error: Invalid randGenClass setting!");
			throw new SimQPNException();
		}
		return randomElement;
	}

	/**
	 * Method scheduleEvent - schedules an event 
	 * 
	 * @param time		- time at which the event should be processed
	 * @param qPlace	- QueueingPlace involved 
	 * @param token		- token that completes service 	
	 * @return 
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	public static void scheduleEvent(double time, QueueingPlace qPlace, Token token) {
		int i = eventList.size() - 1;
		while (i >= 0) {
			Event ev = (Event) eventList.get(i);
			if (ev.time <= time)
				break;
			else
				i--;
		}
		eventList.add(i + 1, new Event(time, qPlace, token));
	}

	/**
	 * Method run - starts the simulation run.
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public void run() throws SimQPNException {

		AbstractIntList enTrans;			// List of currently enabled transitions.
		EmpiricalWalker randTransGen;		// Random number generator for generating next transition to fire.

		// Initialize enTrans
		enTrans = new IntArrayList(numTrans);
		for (int i = 0; i < numTrans; i++)
			if (trans[i].enabled())
				enTrans.add(i);

		// Create randTransGen
		double[] pdf = new double[numTrans];
		for (int t = 0; t < numTrans; t++)
			pdf[t] = 1;

		randTransGen = new EmpiricalWalker(pdf, Empirical.NO_INTERPOLATION, Simulator.nextRandNumGen());
		// Note: Here we use a default distribution. The actual distribution is set each time before using randTransGen. 		

		// Note: we store totRunLen and rampUpLen in local variables to improve performance of the while loop below.		
		double totRunL = totRunLen;
		double rampUpL = rampUpLen;		
		double nextChkAfter = timeBtwChkStops > 0 ? timeBtwChkStops : timeInitHeartBeat;

		beginRunWallClock = System.currentTimeMillis();
		double nextHeartBeat = timeInitHeartBeat;

		logln("Starting Simulator");	
		
		// BEGIN MAIN SIMULATION LOOP ---------------------------------------------------------------------------------
		while (clock < totRunL) { 

			if (inRampUp && clock > rampUpL) {
				inRampUp = false;
				endRampUpClock = clock;
				if (analMethod == WELCH)
					break;
				for (int p = 0; p < numPlaces; p++)
					places[p].start();
			}

			// Step 1: Fire until no transitions are enabled.			
			while (enTrans.size() > 0) {
				Transition nextTrans;		// transition to fire next

				int enTransCnt = enTrans.size();

				if (enTransCnt == 1)
					nextTrans = trans[enTrans.get(0)];
				else {
					// Choose transition to fire based on weights
					pdf = new double[enTransCnt];
					for (int t = 0; t < enTransCnt; t++)
						pdf[t] = trans[enTrans.get(t)].transWeight;
					randTransGen.setState2(pdf);
					nextTrans = trans[enTrans.get(randTransGen.nextInt())];
				}

				nextTrans.fire();		// Fire transition

				// Update enTrans
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
						if ((!tr.enabled()) && enTrans.contains(tr.id))
							enTrans.delete(tr.id);
					}
				}
				// Check if some transitions were enabled (newly-enabled transitions)				
				nP = nextTrans.outPlaces.length;
				for (p = 0; p < nP; p++) {
					pl = nextTrans.outPlaces[p];
					nT = pl.outTrans.length;
					for (t = 0; t < nT; t++) {
						tr = pl.outTrans[t];
						if ((tr.enabled()) && (!enTrans.contains(tr.id)))
							enTrans.add(tr.id);
					}
				}
			} // end firing enabled transitions

			// Step 2: Make sure all service completion events in PS QueueingPlaces have been scheduled
			for (int p = 0; p < numPlaces; p++)
				if (places[p] instanceof QueueingPlace) {
					QueueingPlace qpl = (QueueingPlace) places[p];
					if ((qpl.queueDiscip == QueueingPlace.PS) && (!qpl.eventsUpToDate))
						qpl.updateEvents();
				}

			// Step 3: Process next event in event list
			if (eventList.size() > 0) {
				Event ev = (Event) eventList.remove(0);

				// Advance simulation time
				clock = ev.time;

				QueueingPlace qpl = ev.qPlace;
				qpl.completeService(ev.token);

				// Check if some transitions were enabled and update enTrans				
				int t, nT;
				Transition tr;
				nT = qpl.outTrans.length;
				for (t = 0; t < nT; t++) {
					tr = qpl.outTrans[t];
					if ((tr.enabled()) && (!enTrans.contains(tr.id)))
						enTrans.add(tr.id);
				}
			} else {
				logln("Error: QPN is not live.");
				throw new SimQPNException();
			}

			// Step 4: Heart Beat
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

			// Step 5: Check Stopping Criterion
			if (stoppingRule != FIXEDLEN && (!inRampUp) && clock > nextChkAfter) {
				double elapsedSecs = (System.currentTimeMillis() - beginRunWallClock) / 1000;				
				double clockTimePerSec = clock / elapsedSecs;	
				boolean done = true;
				Place pl;
				for (int p = 0; p < numPlaces; p++) {
					pl = places[p];
					if (pl.statsLevel >= 3) {
						if (!pl.placeStats.enoughStats()) {
							done = false;
							break;
						}
						if ((pl instanceof QueueingPlace) && !(((QueueingPlace) pl).queueStats.enoughStats())) {
							done = false;
							break;
						}
					}
				}
				if (done)
					break; // exit while loop
				if (timeBtwChkStops > 0)
					nextChkAfter = Simulator.clock + timeBtwChkStops;
				else
					nextChkAfter = Simulator.clock + clockTimePerSec * secondsBtwChkStops;
			}

		}
		// END MAIN SIMULATION LOOP ---------------------------------------------------------------------------------
		logln("Simulator finished");

		if (clock >= totRunL)  {
			if (stoppingRule != FIXEDLEN)  {
				logln("WARNING: The simulation was stopped because of reaching max totalRunLen!");
				logln("         The required precision may not have been reached!");
			}
			else
				logln("Info: STOPPING because max totalRunLen is reached!");
		}
		
		endRunClock = clock;
		msrmPrdLen = endRunClock - endRampUpClock;
		endRunWallClock = System.currentTimeMillis();
		runWallClockTime = (endRunWallClock - beginRunWallClock) / 1000;	// total time elapsed in seconds 

		logln("  msrmPrdLen= " + msrmPrdLen + " totalRunLen= " + endRunClock + " runWallClockTime=" + (int) (runWallClockTime / 60) + " min");

		// Complete statistics collection (make sure this is done AFTER the above statements)
		if (analMethod != WELCH) {		
			for (int p = 0; p < numPlaces; p++)
				places[p].finish();
		}
		
		logln();
		logln();
		logln("Simulation finished.");
		
	} // end of run() method

	protected static Element getSettings(Element element, String configuration) {
		XPath xpathSelector = DocumentHelper.createXPath("meta-attributes/meta-attribute[@name='sim-qpn' and @configuration-name='" + configuration + "']");
		Element elementSettings = (Element) xpathSelector.selectSingleNode(element);
		return elementSettings;
	}

	protected static void logln() {
		if(logPrintStream != null)
			logPrintStream.println();
		System.out.println();
	}

	protected static void logln(String msg) {
		if(logPrintStream != null)
			logPrintStream.println(msg);
		System.out.println(msg);
	}
	
	protected static void logln(int debugLev, String msg) {
		if(debugLevel >= debugLev) {
			if(logPrintStream != null) 
				logPrintStream.println(msg);
			System.out.println(msg);
		}
	}
	
	protected static void log(String msg) {
		if(logPrintStream != null)
			logPrintStream.print(msg);
		System.out.print(msg);
	}
		
	protected static void log(int debugLev, String msg) {
		if(debugLevel >= debugLev) {
			if(logPrintStream != null)
				logPrintStream.print(msg);
			System.out.print(msg);
		}
	}

	protected static void log(Throwable exception) {
		if(logPrintStream != null)
			exception.printStackTrace(logPrintStream);
		System.out.print(exception.getMessage());
		exception.printStackTrace();
	}


} // end of Class Simulator
