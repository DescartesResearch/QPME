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
 * http://www.eclipse.org/legal/epl-v10.html
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
	public enum AnalysisMethod{
		/** Method of Welch for determining the length of the initial transient.*/WELCH, 
		/** Replication/Deletion Approach (Method of Independent Replications) */ REPL_DEL ,
		/** Method of non-overlapping batch means.*/BATCH_MEANS};
		 
	private AnalysisMethod analMethod;				// Output data analysis method.

	//Parallel Simulation Parameters
	private boolean isParallel;
	private String decompositionApproach;
	private int numberOfLPs;


	// Supported Stopping Rules (Note: don't change these, values are used)
	public static final int FIXEDLEN = 0;		// Fixed run length.
	public static final int ABSPRC = 1;			// Run until enough data to provide absolute precision for  
												//   sojourn times (STs) in terms of confidence interval (c.i.) half lengths.
	public static final int RELPRC = 2;			// Run until enough data to provide relative precision for STs in terms
												//   of c.i. half lengths / means.
	
	public static final long MAX_INITIAL_HEARTBEAT = 2 * 60 * 1000;
												// Maximum time until initial heartbeat. Important for long running
												//   simulations with slow simulation clock progress.
	
	private int numRuns;					// Maximum number of runs.
	private boolean useStdStateStats; 	// For (MULT_REPL, statsLevel >= 3): Specifies whether to use ordinary
												//   or steady state sojourn times when estimating averages and c.i.
	private String statsDir;

	public int runMode;
	public int stoppingRule;				// Simulation stopping criterion.
	private static int debugLevel;				// Debug level - TODO: currently not used consistently!
	public double rampUpLen;				// Duration of the ramp up period.
	public double totRunLength;				// Maximum total duration of the simulation run (incl. rampUpLen).
	public double timeBtwChkStops;		// Time between checks if stopping criterion is fulfilled. 
												//   If set to 0, it is automatically adjusted to result in roughtly 
												//   secondsBtwChkStops sec between checks.
	public double secondsBtwChkStops;	// Seconds between checks if stopping criterion is fulfilled. TODO: Add to User's Guide. 
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
	
//	public boolean inRampUp;				// True if still in RampUp period (no measurements taken).
//	//public static boolean simRunning;			// True if simulation is currently running.
//	public double endRampUpClock;		// Clock at the end of RampUp, i.e. beginning of the measurement period.
//	public double endRunClock;			// Clock at the end of the run.
//	public double msrmPrdLen;			// Duration of the measurement period (endRunClock - endRampUpClock).
//	public double beginRunWallClock;		// currentTimeMillis at the begin of the run (wall clock time).
//	public double endRunWallClock;		// currentTimeMillis at the end of the run (wall clock time).
//	public double runWallClockTime;		// Total duration of the run in seconds.
	
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

	/**
	 * 
	 */
	public SimQPNConfiguration() {
	}
	
	public AnalysisMethod getAnalMethod() {
		return analMethod;
	}
	public void setAnalMethod(AnalysisMethod analMethod) {
		this.analMethod = analMethod;
	}
	public static int getDebugLevel() {
		return debugLevel;
	}
	public static void setDebugLevel(int debugLevel) {
		SimQPNConfiguration.debugLevel = debugLevel;
	}
	public String getStatsDir() {
		return statsDir;
	}
	public void setStatsDir(String statsDir) {
		this.statsDir = statsDir;
	}
	public int getNumRuns() {
		return numRuns;
	}
	public void setNumRuns(int numRuns) {
		this.numRuns = numRuns;
	}

	public boolean isUseStdStateStats() {
		return useStdStateStats;
	}

	/**
	 * Sets the use of standard state stats
	 * useStdStateStats configurable only in MULT_REPL mode
	 * 
	 * - automatically set to true in CVRG_EST mode.
	 * - automatically set to false in NORMAL:REPL_DEL mode. 		
	 * @param useStdStateStats
	 */
	public void setUseStdStateStats(boolean useStdStateStats) {
		this.useStdStateStats = useStdStateStats;
	}

	public boolean isParallel() {
		return isParallel;
	}

	public void setParallel(boolean isParallel) {
		this.isParallel = isParallel;
	}
	
	public void setDecompositionApproach(String decompositionApproach) {
		this.decompositionApproach = decompositionApproach;
	}
	
	public String getDecompositionApproach(){
		return decompositionApproach;
	}

	public int getNumerOfLPs() {
		return numberOfLPs;
	}
	
	public void setNumerOfLPs(int numberOfLPs) {
		this.numberOfLPs = numberOfLPs;
	}
	

}
