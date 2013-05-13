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
import de.tud.cs.simqpn.kernel.analyzer.BatchMeans;
import de.tud.cs.simqpn.kernel.analyzer.ReplicationDeletion;
import de.tud.cs.simqpn.kernel.analyzer.SimulatorResults;
import de.tud.cs.simqpn.kernel.analyzer.Welch;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.Probe;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.Queue;
import de.tud.cs.simqpn.kernel.entities.Token;
import de.tud.cs.simqpn.kernel.entities.Transition;
import de.tud.cs.simqpn.kernel.executor.QueueEvent;
import de.tud.cs.simqpn.kernel.loader.ConfigurationLoader;
import de.tud.cs.simqpn.kernel.loader.NetLoader;
import de.tud.cs.simqpn.kernel.loader.XMLHelper;
import de.tud.cs.simqpn.kernel.loader.XMLValidator;
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

public class SimQPNConfiguration {
		
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
	
	private static int numRuns;					// Maximum number of runs.
	public static boolean useStdStateStats; 	// For (MULT_REPL, statsLevel >= 3): Specifies whether to use ordinary
												//   or steady state sojourn times when estimating averages and c.i.
	private static String statsDir;

	public static int runMode;
	private static int analMethod;				// Output data analysis method.
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
	//public static boolean simRunning;			// True if simulation is currently running.
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

	public static int getAnalMethod() {
		return analMethod;
	}
	public void setAnalMethod(int analMethod) {
		SimQPNConfiguration.analMethod = analMethod;
	}
	public static int getDebugLevel() {
		return debugLevel;
	}
	public static void setDebugLevel(int debugLevel) {
		SimQPNConfiguration.debugLevel = debugLevel;
	}
	public static String getStatsDir() {
		return statsDir;
	}
	public static void setStatsDir(String statsDir) {
		SimQPNConfiguration.statsDir = statsDir;
	}
	public static int getNumRuns() {
		return numRuns;
	}
	public static void setNumRuns(int numRuns) {
		SimQPNConfiguration.numRuns = numRuns;
	}

}
