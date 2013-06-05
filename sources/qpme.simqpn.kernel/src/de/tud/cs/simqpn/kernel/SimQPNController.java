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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import de.tud.cs.simqpn.kernel.analyzer.BatchMeansParallel;
import de.tud.cs.simqpn.kernel.analyzer.ReplicationDeletion;
import de.tud.cs.simqpn.kernel.analyzer.Welch;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.executor.QueueEvent;
import de.tud.cs.simqpn.kernel.loader.ConfigurationLoader;
import de.tud.cs.simqpn.kernel.loader.NetFlattener;
import de.tud.cs.simqpn.kernel.loader.NetLoader;
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
	/** Progress monitoring */
	private static SimulatorProgress progressMonitor;
	
	private Net net;
	/** True if simulation is currently running. */
	private static boolean simRunning; 

	/**
	 * Global simulation clock. Time is usually measured in milliseconds.
	 * 
	 * TODO Check if using double for time is really needed and if overhead is
	 * tolerable. Consider switching to float.
	 */
	private double clock;
	/**
	 * Global simulation event list. Contains events scheduled for processing at specified points in time.
	 */
	public static PriorityQueue<QueueEvent> eventList = 
	new PriorityQueue<QueueEvent>(10, new Comparator<QueueEvent>() {
		public int compare(QueueEvent a, QueueEvent b) {
			return (a.time < b.time ? -1 : (a.time == b.time ? 0 : 1));
		}
	});

	// public static LinkedList eventList; // Old LinkedList implementation of
	// the event list.

	/*
	 * WARNING: Watch out when defining the Comparator above: Equality should be
	 * treated explicitly!
	 * 
	 * Potential problems when using eventList.remove() because of the following
	 * bug http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6207984
	 * http://bugs.sun.com/bugdatabase/view_bug.do;jsessionid=859
	 * a6e381a7abfffffffff7e644d05a59d93?bug_id=6268068
	 * 
	 * On old JVMs that do not have the above bug fixed, if two events have the
	 * exact same time, the wrong one might be removed!
	 */

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
		setConfiguration(ConfigurationLoader.configureSimulatorSettings(netXML,
				configurationName, getConfiguration()));

		// CONFIG: Whether to use indirect estimators for FCFS queues
		for (int p = 0; p < getNet().getNumPlaces(); p++) {
			Place pl = getNet().getPlace(p);
			if (pl.statsLevel >= 3 && pl instanceof QPlace) {
				((QPlace) pl).qPlaceQueueStats.indrStats = false;
				log.debug("places[" + p
						+ "].qPlaceQueueStats.indrStats = false;");
			}
		}

		setConfiguration(ConfigurationLoader.configureBatchMeansMethod(netXML,
				this));

		initializeWorkingVariables();
		XMLValidator.validateInputNet(netXML); // TODO Think about moving this
												// into Constructor

	}

	private void initializeWorkingVariables() throws SimQPNException {
		getConfiguration().inRampUp = true;
		getConfiguration().endRampUpClock = 0;
		getConfiguration().endRunClock = 0;
		getConfiguration().msrmPrdLen = 0; // Set at the end of the run when the
											// actual length is known.
		getConfiguration().beginRunWallClock = 0;
		getConfiguration().endRunWallClock = 0;
		getConfiguration().runWallClockTime = 0;

		setClock(0); // Note that it has been assumed throughout the code that
		// the simulation starts at virtual time 0.

		eventList.clear();
		// eventList = new LinkedList(); // Old LinkedList implementation of the
		// event list.

		// Make sure clock has been initialized before calling init below
		// Call places[i].init() first and then thans[i].init() and
		// queues[i].init()
		for (int i = 0; i < getNet().getNumProbes(); i++)
			getNet().getProbe(i).init();
		for (int i = 0; i < getNet().getNumPlaces(); i++)
			getNet().getPlace(i).init(getClock());
		for (int i = 0; i < getNet().getNumTrans(); i++)
			getNet().getTrans(i).init();
		for (int i = 0; i < getNet().getNumQueues(); i++)
			getNet().getQueue(i).init(configuration);
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

		setProgressMonitor(monitor);

		Stats[] result = null;

		setSimRunning(true); // True if simulation is currently running.

		// NOTE: In the following, if the simulation is interrupted, simRunning
		// should be reset.

		try {
			// TODO Factory Methode / Factory Klasse
			if (getConfiguration().runMode == SimQPNConfiguration.NORMAL) {
				if (getConfiguration().getAnalMethod() == SimQPNConfiguration.AnalysisMethod.BATCH_MEANS) {
					/** Method of non-overlapping batch means */
					result = new BatchMeansParallel().analyze(net, configuration,
							monitor);
				} else if (getConfiguration().getAnalMethod() == SimQPNConfiguration.AnalysisMethod.REPL_DEL) {
					/**
					 * Replication/Deletion Approach (Method of Independent
					 * Replications)
					 */
					result = new ReplicationDeletion()
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
			e.printStackTrace();
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

	public static SimulatorProgress getProgressMonitor() {
		return progressMonitor;
	}

	public static void setProgressMonitor(SimulatorProgress progressMonitor) {
		SimQPNController.progressMonitor = progressMonitor;
	}

	public double getClock() {
		return clock;
	}

	public void setClock(double clock) {
		this.clock = clock;
	}

	public static boolean isSimRunning() {
		return simRunning;
	}

	public static void setSimRunning(boolean simRunning) {
		SimQPNController.simRunning = simRunning;
	}

} // end of Class Simulator
