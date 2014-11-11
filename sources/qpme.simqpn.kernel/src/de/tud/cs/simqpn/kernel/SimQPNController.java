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

import java.io.File;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Element;

import de.tud.cs.simqpn.kernel.analyzer.Analyzer;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.stats.AggregateStats;
import de.tud.cs.simqpn.kernel.entities.stats.Stats;
import de.tud.cs.simqpn.kernel.loading.ConfigurationLoader;
import de.tud.cs.simqpn.kernel.loading.NetLoader;
import de.tud.cs.simqpn.kernel.loading.XMLAggregateStats;
import de.tud.cs.simqpn.kernel.loading.XMLBatchMeans;
import de.tud.cs.simqpn.kernel.loading.XMLValidator;
import de.tud.cs.simqpn.kernel.loading.flatten.XMLNetFlattener;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;
import de.tud.cs.simqpn.kernel.util.LogUtil;

/**
 * The SimQPNController class manages the simulation from loading to result processing and saving.
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
	private Element XMLDescription;

	private static boolean simulationCurrentlyRunning;
	private AggregateStats[] aggregateStats;

	/**
	 * Should be called to create an instance of SimQPNController. Encapsulates
	 * instantiation and initialization.
	 * 
	 * Note: We did separate the creation and modification to ensure no read
	 * access before initialization/the constructor has been left. Otherwise an
	 * IllegalStateException is likely.
	 * 
	 * @param XMLDescription
	 * @param configurationName
	 * @param logConfigFilename
	 * @return
	 * @throws SimQPNException
	 */
	public static SimQPNController createSimQPNController(Element XMLDescription,
			String configurationName, String logConfigFilename, Date randomSeed)
			throws SimQPNException {
		XMLValidator.validateInputNet(XMLDescription);

		SimQPNController sim = new SimQPNController();
		if(randomSeed == null){
			Date currentTime = new Date(); // User current time as random seed
			sim.initialize(XMLDescription, configurationName, logConfigFilename, currentTime);			
		}else{
			sim.initialize(XMLDescription, configurationName, logConfigFilename, randomSeed);						
		}
		return sim;
	}
	
	public static SimQPNController createSimQPNController(Element XMLDescription,
			String configurationName, String logConfigFilename)
			throws SimQPNException {
		return createSimQPNController(XMLDescription, configurationName, logConfigFilename, null);
	}

	/**
	 * Instead of this constructor use
	 * {@link #createSimQPNController(Element, String, String)} for object creation.
	 * 
	 * @param XMLDescription
	 * @param configurationName
	 * @throws SimQPNException
	 */
	private SimQPNController() {};

	/**
	 * Loads net and configuration from XML description.
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	private void initialize(Element XMLDescription, String configurationName, String logConfigFilename, Date date)
			throws SimQPNException {
		LogUtil.initializeLogging(XMLDescription, configurationName, logConfigFilename);

		// NOTE: Random needs to be initialized before starting the model definition
		RandomNumberGenerator.initialize(date);
		
		this.configuration = ConfigurationLoader.loadConfiguration(
				XMLDescription, configurationName);
		
		this.XMLDescription = XMLNetFlattener.flattenHierarchicalNetParts(XMLDescription,
				configurationName, configuration.getStatsDir());

		this.net = NetLoader.load(this.XMLDescription, configurationName,
				configuration);

		// CONFIG: Whether to use indirect estimators for FCFS queues
		for (int p = 0; p < getNet().getNumPlaces(); p++) {
			Place pl = getNet().getPlace(p);
			if (pl.statsLevel >= 3 && pl instanceof QPlace) {
				((QPlace) pl).qPlaceQueueStats.indrStats = false;
				log.debug("places[" + p
						+ "].qPlaceQueueStats.indrStats = false;");
			}
		}

		XMLBatchMeans.modificateNetForBatchMeans(this.XMLDescription, configuration, net);

		aggregateStats = XMLAggregateStats.initStatsArray(this.XMLDescription, net, configuration);
	}

	/**
	 * Executes the simulation run. 
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public File execute(String configurationName,
			String outputFilename, SimulatorProgress monitor)
			throws SimQPNException {
		// TODO: Make the Stdout output print to $statsDir/Output.txt
		// CHRIS: Not done yet

		setSimRunning(true);
		Analyzer analyzer = null;
		Stats[] result = null;
		analyzer = Analyzer.getAnalyzer(configuration, aggregateStats);
		int verbosityLevel = 1;
		result = analyzer.analyze(net, configuration, monitor, verbosityLevel);
		setSimRunning(false);
		LogManager.shutdown();
		File resultFile = analyzer.writeToFile(result, configuration,
				outputFilename, XMLDescription, configurationName);
		return resultFile;
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
		// True if simulation is currently running.
		return simulationCurrentlyRunning;
	}

	/** Should be reset if the simulation is interrupted */
	public static void setSimRunning(boolean simRunning) {
		SimQPNController.simulationCurrentlyRunning = simRunning;
	}

	public Element getXMLDescription() {
		return XMLDescription;
	}

}