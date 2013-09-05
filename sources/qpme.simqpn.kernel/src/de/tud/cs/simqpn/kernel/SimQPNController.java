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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration.AnalysisMethod;
import de.tud.cs.simqpn.kernel.analyzer.Analyzer;
import de.tud.cs.simqpn.kernel.analyzer.BatchMeans;
import de.tud.cs.simqpn.kernel.analyzer.ReplicationDeletion;
import de.tud.cs.simqpn.kernel.analyzer.ReplicationDeletionParallel;
import de.tud.cs.simqpn.kernel.analyzer.Welch;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.loader.ConfigurationLoader;
import de.tud.cs.simqpn.kernel.loader.NetLoader;
import de.tud.cs.simqpn.kernel.loader.XMLAggregateStats;
import de.tud.cs.simqpn.kernel.loader.XMLBatchMeans;
import de.tud.cs.simqpn.kernel.loader.XMLNetFlattener;
import de.tud.cs.simqpn.kernel.loader.XMLValidator;
import de.tud.cs.simqpn.kernel.loader.XMLWelch;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;
import de.tud.cs.simqpn.kernel.persistency.StatsDocumentBuilder;
import de.tud.cs.simqpn.kernel.random.RandomNumberGenerator;
import de.tud.cs.simqpn.kernel.stats.AggregateStats;
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
	private AggregateStats[] aggregateStats;

	/**
	 * Used to
	 * 
	 * The get Ready Method
	 * 
	 * TODO Keine Lese-Zugriffe auf Objekt bevor initialisiert: besser in
	 * init()-Methode auslagern: Ansonsten -->Illegal State Exception
	 * 
	 * @param netXML
	 * @param configurationName
	 * @param logConfigFilename
	 * @return
	 * @throws SimQPNException
	 */
	public static SimQPNController getSimQPNController(Element netXML,
			String configurationName, String logConfigFilename)
			throws SimQPNException {
		XMLValidator.validateInputNet(netXML);

		SimQPNController sim = new SimQPNController(netXML, configurationName,
				logConfigFilename);
		sim.getReady(netXML, configurationName);
		return sim;
	}

	/**
	 * Constructor
	 * 
	 * @param netXML
	 * @param configurationName
	 * @throws SimQPNException
	 */
	private SimQPNController(Element netXML, String configurationName,
			String logConfigFilename) throws SimQPNException {
		// Random Number Generation (Note: needs to be initialized before
		// starting the model definition)
		RandomNumberGenerator.initialize();
		this.configuration = ConfigurationLoader.loadConfiguration(netXML,
				configurationName, logConfigFilename);
		this.net = new NetLoader().load(netXML, configurationName,
				configuration);
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
				configurationName, configuration);
		
		// CONFIG: Whether to use indirect estimators for FCFS queues
		for (int p = 0; p < getNet().getNumPlaces(); p++) {
			Place pl = getNet().getPlace(p);
			if (pl.statsLevel >= 3 && pl instanceof QPlace) {
				((QPlace) pl).qPlaceQueueStats.indrStats = false;
				log.debug("places[" + p
						+ "].qPlaceQueueStats.indrStats = false;");
			}
		}

		XMLBatchMeans.configureBatchMeansMethod(netXML, configuration, net);
		netXML = XMLNetFlattener.flattenHierarchicalNetParts(netXML,
				configurationName, configuration.getStatsDir());
		
		aggregateStats = XMLAggregateStats.initStatsArray(net, configuration, netXML);
		if(configuration.getAnalMethod() == AnalysisMethod.WELCH){
			XMLWelch.configurePlaceStats(net.getPlaces(), netXML, configurationName);			
		}
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
	public File execute(Element XMLNet, String configurationString,
			String outputFilename, SimulatorProgress monitor)
			throws SimQPNException {

		// TODO: Make the Stdout output print to $statsDir/Output.txt
		// CHRIS: Not done yet

		Stats[] result = null;

		setSimRunning(true); // True if simulation is currently running.

		// NOTE: In the following, if the simulation is interrupted, simRunning
		// should be reset.

		
		Analyzer analyzer = null;
		try {
			// TODO Factory Methode / Factory Klasse
			if (getConfiguration().runMode == SimQPNConfiguration.NORMAL) {
				if (getConfiguration().getAnalMethod() == SimQPNConfiguration.AnalysisMethod.BATCH_MEANS) {
					analyzer = new BatchMeans();
				} else if (getConfiguration().getAnalMethod() == SimQPNConfiguration.AnalysisMethod.REPL_DEL) {
					configuration.setUseStdStateStats(false);
					//analyzer = new ReplicationDeletion(aggregateStats);
					analyzer = new ReplicationDeletionParallel(aggregateStats);
					
				} else {
					log.error("Illegal analysis method specified!");
					throw new SimQPNException();
				}
			} else if (getConfiguration().runMode == SimQPNConfiguration.INIT_TRANS) {
				if (getConfiguration().getAnalMethod() == SimQPNConfiguration.AnalysisMethod.WELCH) {
					analyzer = new Welch(XMLNet, configurationString);
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
			result = analyzer.analyze(net, configuration, monitor);
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
				resultFile = new File(configuration.getStatsDir(),
						builder.getResultFileBaseName() + ".simqpn");
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
			log.error("", e);
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

} // end of Class Simulator
