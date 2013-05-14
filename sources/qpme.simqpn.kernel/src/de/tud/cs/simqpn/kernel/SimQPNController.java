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
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.PriorityQueue;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import cern.jet.random.Empirical;
import cern.jet.random.EmpiricalWalker;
import de.tud.cs.simqpn.kernel.analyzer.BatchMeans;
import de.tud.cs.simqpn.kernel.analyzer.ReplicationDeletion;
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
import de.tud.cs.simqpn.kernel.stats.Stats;
import de.tud.cs.simqpn.kernel.util.LogUtil;
import de.tud.cs.simqpn.kernel.util.LogUtil.ReportLevel;

/**
 * Class Simulator
 * 
 * @version %I%, %G%
 */

public class SimQPNController {
	
	//
	//ATTENTION: Update this constant every time a new
	//			 release of qpme is delivered so that
	//			 the version attribute in .simqpn files
	//			 is correct
	//
	public static final String QPME_VERSION = "2.1.0";	
	
	private static Logger log = Logger.getLogger(SimQPNController.class);
	public static boolean simRunning;			// True if simulation is currently running.

	
	public SimQPNConfiguration configuration;
	public static SimulatorProgress progressMonitor;	// Progress monitoring
	private Net net;

	// Check if using double for time is really needed and if overhead is tolerable. Consider switching to float.
	public static double clock;					// Global simulation clock. Time is usually measured in milliseconds.
	public static 
		PriorityQueue<QueueEvent> eventList =		// Global simulation event list. Contains events scheduled for processing at specified points in time.
	      new PriorityQueue<QueueEvent>(10, 
	        new Comparator<QueueEvent>() {
	          public int compare(QueueEvent a, QueueEvent b) {
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
	
	
	/**
	 * Constructor
	 * @param netXML
	 * @param configurationName
	 * @throws SimQPNException
	 */
	public SimQPNController(Element netXML, String configurationName, String logConfigFilename) throws SimQPNException {
		// Random Number Generation (Note: needs to be initialized before starting the model definition)
		RandomNumberGenerator.initialize();
		configuration = ConfigurationLoader.configure(netXML, configurationName, logConfigFilename);
		netXML = prepareNet(netXML, configurationName); //flattens HQPNS
		net = new NetLoader().load(netXML, configurationName, this);
	}

	/**
	 * Method getReady - prepares for the simulation run
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public void getReady(Element netXML, String configurationName) throws SimQPNException {
		configuration = ConfigurationLoader.configureSimulatorSettings(netXML, configurationName, configuration);

		// CONFIG: Whether to use indirect estimators for FCFS queues
		for (int p = 0; p < getNet().getNumPlaces(); p++) {
			Place pl = getNet().getPlace(p);
			if (pl.statsLevel >= 3 && pl instanceof QPlace) {
				((QPlace) pl).qPlaceQueueStats.indrStats = false;
				log.debug("places[" + p + "].qPlaceQueueStats.indrStats = false;");				
			}
		}

		configuration = ConfigurationLoader.configureBatchMeansMethod(netXML, this);

		initializeWorkingVariables();
	}
	


	
	/**
	 * Prepares the net for simulation. Should be called after Simulator.configure and
	 * before Simulator.execute
	 */
	public Element prepareNet(Element net, String configurationString) throws SimQPNException {
		Element result = net;
		XPath xpathSelector = XMLHelper.createXPath("//place[@xsi:type = 'subnet-place']");
		if(xpathSelector.selectSingleNode(net) != null) {
			try {
				// There are subnet-places in the net; replace the subnet place by their subnet
				InputStream xslt = SimQPNController.class.getResourceAsStream("/de/tud/cs/simqpn/transforms/hqpn_transform.xsl");
				TransformerFactory transformFactory = TransformerFactory.newInstance();
				Transformer hqpnTransform = transformFactory.newTransformer(new StreamSource(xslt));
				DocumentSource source = new DocumentSource(net.getDocument());
				DocumentResult flattenNet = new DocumentResult();
				hqpnTransform.transform(source, flattenNet);
				result = flattenNet.getDocument().getRootElement();
				// Save the transformed net to disk for debug purposes
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HHmmssS");
				File f = new File(configuration.getStatsDir(), "SimQPN_FlatHQPN_" + configurationString + "_" + dateFormat.format(new Date()) + ".qpe");
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
	public Stats[] execute(Element XMLNet, String configurationString, SimulatorProgress monitor) throws SimQPNException {
		
		// TODO: Make the Stdout output print to $statsDir/Output.txt
		// CHRIS: Not done yet

		progressMonitor = monitor;

		Stats[] result = null;
		
		simRunning = true;
		// NOTE: In the following, if the simulation is interrupted, simRunning should be reset. 		  
				
		try {
			XMLValidator.validateInputNet(XMLNet);
			if (configuration.runMode == SimQPNConfiguration.NORMAL) {
				if (configuration.getAnalMethod() == SimQPNConfiguration.BATCH_MEANS) { // Method of non-overlapping batch means
					result = new BatchMeans().analyze(XMLNet, configurationString, monitor, this);
				} else if (configuration.getAnalMethod() == SimQPNConfiguration.REPL_DEL) { // Replication/Deletion Approach (Method of Independent Replications) 				
					result = new ReplicationDeletion().analyze(XMLNet, configurationString, monitor, this);
				} else {
					log.error("Illegal analysis method specified!");
					throw new SimQPNException();				
				}
			} else if (configuration.runMode == SimQPNConfiguration.INIT_TRANS) {
				if (configuration.getAnalMethod() == SimQPNConfiguration.WELCH) {
					result = new Welch().analyze(XMLNet, configurationString, monitor, this);
				} else {
					log.error("Analysis method " + configuration.getAnalMethod() + " not supported in INIT_TRANS mode!");
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
	
	private void initializeWorkingVariables() throws SimQPNException {
		configuration.inRampUp = true;
		configuration.endRampUpClock = 0;
		configuration.endRunClock = 0;
		configuration.msrmPrdLen = 0; // Set at the end of the run when the actual length is known.
		configuration.beginRunWallClock = 0;
		configuration.endRunWallClock = 0;
		configuration.runWallClockTime = 0;

		clock = 0;	// Note that it has been assumed throughout the code that
		   			// the simulation starts at virtual time 0.

		eventList.clear();
		// eventList = new LinkedList();  // Old LinkedList implementation of the event list.
		
		// Make sure clock has been initialized before calling init below
		// Call places[i].init() first and then thans[i].init() and queues[i].init() 
		for (int i = 0; i < getNet().getNumProbes(); i++)
			getNet().getProbe(i).init();
		for (int i = 0; i < getNet().getNumPlaces(); i++)
			getNet().getPlace(i).init();
		for (int i = 0; i < getNet().getNumTrans(); i++)
			getNet().getTrans(i).init();
		for (int i = 0; i < getNet().getNumQueues(); i++)
			getNet().getQueue(i).init(this);
	}
	
	/**
	 * Method scheduleEvent - schedules an event 
	 * 
	 * @param time		- Time at which the event should be processed
	 * @param queue		- Queue involved
	 * @param token		- Token that is to completes service 	
	 * @return 
	 * @exception
	 */
	public static void scheduleEvent(double time, Queue queue, Token token) {
		QueueEvent ev = new QueueEvent(time, queue, token);
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
	public SimQPNController run() throws SimQPNException {
		// SimonSpinner: TEMP CHANGE
		//		try {
		//			System.in.read();
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		}
		// SimonSpinner: TEMP CHANGE
				
		boolean[] transStatus;					// Transition status: true = enabled, false = disabled
		int enTransCnt = 0;
		int[] enTransIDs = new int[getNet().getNumTrans()];
		
		EmpiricalWalker randTransGen;		// Random number generator for generating next transition to fire.

		// Initialize transStatus and enTransCnt 		
		transStatus = new boolean[getNet().getNumTrans()];
		for (int i = 0; i < getNet().getNumTrans(); i++) {
			if (getNet().getTrans(i).enabled()) {
				transStatus[i] = true;
				enTransCnt++;
			} else {
				transStatus[i] = false;
			}
		}
		
		// Create randTransGen
		double[] pdf = new double[getNet().getNumTrans()];
		for (int t = 0; t < getNet().getNumTrans(); t++)
			pdf[t] = 1;

		randTransGen = new EmpiricalWalker(pdf, Empirical.NO_INTERPOLATION, RandomNumberGenerator.nextRandNumGen());
		// Note: Here we use a default distribution. The actual distribution is set each time before using randTransGen. 		

		// Note: we store totRunLen and rampUpLen in local variables to improve performance of the while loop below.		
		double totRunL = configuration.totRunLen;
		double rampUpL = configuration.rampUpLen;
		double nextChkAfter = configuration.timeBtwChkStops > 0 ? configuration.timeBtwChkStops : totRunL; // If secondsBtwChkStops is used, disable checking of stopping criterion
																			   // until beforeInitHeartBeat == false. By setting nextChkAfter = totRunL
																			   // stopping criterion checking is disabled.
		/* ORIGINAL HEARTBEAT IMPLEMENTATION
		double nextChkAfter = timeBtwChkStops > 0 ? timeBtwChkStops : timeInitHeartBeat;
		*/

		configuration.beginRunWallClock = System.currentTimeMillis();


		boolean beforeInitHeartBeat = true;		// Flag indicating when we are still before the first heart beat (progress update).
												//   If true, the value of timeBtwHeartBeats is still measured, and set to 0.
		double nextHeartBeat = 0.0;				// Simulation run time of the next heart beat.
		double timeBtwHeartBeats = 0.0;			// How often progress updates are made (in logical simulation time units).
		long lastTimeMsrm = System.currentTimeMillis();		// The value of the last wall clock time measurement. Used for progress updates.
		double maxProgressInterval = progressMonitor.getMaxUpdateLogicalTimeInterval();
		long progressUpdateRate = progressMonitor.getMaxUpdateRealTimeInterval();
		
		// BEGIN MAIN SIMULATION LOOP ---------------------------------------------------------------------------------
		while (clock < totRunL) { 

			if (configuration.inRampUp && clock > rampUpL) {
				configuration.inRampUp = false;
				configuration.endRampUpClock = clock;
				if (configuration.getAnalMethod() == SimQPNConfiguration.WELCH)
					break;
				for (int p = 0; p < getNet().getNumPlaces(); p++)
					getNet().getPlace(p).start(this);
				for (int q = 0; q < getNet().getNumQueues(); q++)
					getNet().getQueue(q).start();
				for (int pr = 0; pr < getNet().getNumProbes(); pr++)
					getNet().getProbe(pr).start(this);

				progressMonitor.finishWarmUp();
			}

			// Step 1: Fire until no transitions are enabled.
			int fireCnt = 0;
			while (enTransCnt > 0) {				
				Transition nextTrans;		// transition to fire next

				if (enTransCnt == 1) {				
					nextTrans = null;
					for (int t = 0; t < getNet().getNumTrans(); t++) {
						if (transStatus[t]) {
							nextTrans = getNet().getTrans(t);
							break;
						}
					}
				} else {
					// Choose transition to fire based on weights
					pdf = new double[enTransCnt];					
					for (int t = 0, e = 0; t < getNet().getNumTrans(); t++) {
						if (transStatus[t]) {
							pdf[e] = getNet().getTrans(t).transWeight;
							enTransIDs[e] = t;
							e++;
						}
					}
					randTransGen.setState2(pdf);					
					nextTrans = getNet().getTrans(enTransIDs[randTransGen.nextInt()]);
				}

				nextTrans.fire(this);		// Fire transition

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
			for (int q = 0; q < getNet().getNumQueues(); q++)
				if (getNet().getQueue(q).queueDiscip == Queue.PS && (!getNet().getQueue(q).eventsUpToDate))
					getNet().getQueue(q).updateEvents();
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
				QueueEvent ev = eventList.poll();
				// Event ev = (Event) eventList.remove(0); // Old LinkedList implementation of the event list.
								
				// Advance simulation time
				clock = ev.time;

				QPlace qpl = (QPlace) ev.token.place;
				qpl.completeService(ev.token, this);

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
				if(((curTimeMsrm - lastTimeMsrm) >= SimQPNConfiguration.MAX_INITIAL_HEARTBEAT)
						|| (SimQPNController.clock >= maxProgressInterval)) {
					
					if(SimQPNController.clock >= maxProgressInterval) {
						timeBtwHeartBeats = maxProgressInterval;
					} else {
						timeBtwHeartBeats = (SimQPNController.clock / (curTimeMsrm - lastTimeMsrm)) * progressUpdateRate;
					}
					beforeInitHeartBeat = false;
					if (configuration.timeBtwChkStops == 0) {
						// enable checking of stopping criterion
						nextChkAfter = clock;
					}
				}
				
				if (progressMonitor.isCanceled()) {
					clock = totRunL;
				}
			} else {
				if(SimQPNController.clock >= nextHeartBeat) {
					long curTimeMsrm = System.currentTimeMillis();
					progressMonitor.updateSimulationProgress(clock / (totRunL - 1) * 100, (curTimeMsrm - lastTimeMsrm));					
					lastTimeMsrm = curTimeMsrm;
					nextHeartBeat = SimQPNController.clock + timeBtwHeartBeats;
					
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
			if (configuration.stoppingRule != SimQPNConfiguration.FIXEDLEN && (!configuration.inRampUp) && clock > nextChkAfter) {
				double elapsedSecs = (System.currentTimeMillis() - configuration.beginRunWallClock) / 1000;				
				double clockTimePerSec = clock / elapsedSecs;	
				boolean done = true;
				Place pl = null;

				for (int p = 0; p < getNet().getNumPlaces(); p++) {
					pl = getNet().getPlace(p);
					if (pl.statsLevel >= 3) {
						if (!pl.placeStats.enoughStats(this)) {
							done = false;
							break;
						}
						if ((pl instanceof QPlace) && !(((QPlace) pl).qPlaceQueueStats.enoughStats(this))) {
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
					for (int pr = 0; pr < getNet().getNumProbes(); pr++) {
						probe = getNet().getProbe(pr);
						if (probe.statsLevel >= 3) {
							if (!probe.probeStats.enoughStats(this)) {
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

				if (configuration.timeBtwChkStops > 0)
					nextChkAfter = SimQPNController.clock + configuration.timeBtwChkStops;
				else
					nextChkAfter = SimQPNController.clock + clockTimePerSec * configuration.secondsBtwChkStops;
			}

		}

		// END MAIN SIMULATION LOOP ---------------------------------------------------------------------------------
		progressMonitor.updateSimulationProgress(100, 0);
		
		if (progressMonitor.isCanceled()) {
			progressMonitor.warning("The simulation was canceled by the user.\n"
					+ "The required precision may not have been reached!");
		} else {
			if (clock >= configuration.totRunLen)  {
				if (configuration.stoppingRule != SimQPNConfiguration.FIXEDLEN)  {
					progressMonitor.warning("The simulation was stopped because of reaching max totalRunLen.\n"
							+ "The required precision may not have been reached!");
				}
				else
					log.info("STOPPING because max totalRunLen is reached!");
			}
		}
		
		configuration.endRunClock = clock;
		configuration.msrmPrdLen = configuration.endRunClock - configuration.endRampUpClock;
		configuration.endRunWallClock = System.currentTimeMillis();
		configuration.runWallClockTime = (configuration.endRunWallClock - configuration.beginRunWallClock) / 1000;	// total time elapsed in seconds 
		
		log.info("msrmPrdLen= " + configuration.msrmPrdLen + " totalRunLen= " + configuration.endRunClock + " runWallClockTime=" + (int) (configuration.runWallClockTime / 60) + " min (=" + configuration.runWallClockTime + " sec)");

		
		// Complete statistics collection (make sure this is done AFTER the above statements)
		if (configuration.getAnalMethod() != SimQPNConfiguration.WELCH) {		
			for (int p = 0; p < getNet().getNumPlaces(); p++)
				getNet().getPlace(p).finish(this);
			for (int q = 0; q < getNet().getNumQueues(); q++)  //NOTE: queues[*].finish() should be called after places[*].finish()! 
				getNet().getQueue(q).finish(this);
			for (int pr = 0; pr < getNet().getNumProbes(); pr++)
				getNet().getProbe(pr).finish(this);
		}
		return this;
	} // end of run() method


	public Net getNet() {
		return net;
	}

	public void setNet(Net net) {
		this.net = net;
	}

} // end of Class Simulator
