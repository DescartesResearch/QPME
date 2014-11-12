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
 * Original Author(s):  Jürgen Walter
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2013/??/??  Jürgen Walter     Created.
 * 
 */
package de.tud.cs.simqpn.kernel.analyzer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNController;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.stats.AggregateStats;
import de.tud.cs.simqpn.kernel.entities.stats.Stats;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;

public abstract class Analyzer {
	private static Logger log = Logger.getLogger(Analyzer.class);

	
	public abstract Stats[] analyze(Net net, SimQPNConfiguration configuration,
			SimulatorProgress monitor, int verbositiyLevel)
			throws SimQPNException;

	public abstract File writeToFile(Stats[] result, SimQPNConfiguration configuration,
			String outputFileName, Element XML, String configurationName)
			throws SimQPNException;
	
	/**
	 * Factory method
	 * @param configuration
	 * @param aggregateStats
	 * @param XMLNet
	 * @param configurationString
	 * @return
	 */
	public static Analyzer getAnalyzer(SimQPNConfiguration configuration,
			AggregateStats[] aggregateStats) {
		Analyzer analyzer = null;
		try {
			if (configuration.runMode == SimQPNConfiguration.NORMAL) {
				if (configuration.getAnalMethod() == SimQPNConfiguration.AnalysisMethod.BATCH_MEANS) {
					analyzer = new BatchMeans();
				} else if (configuration.getAnalMethod() == SimQPNConfiguration.AnalysisMethod.REPL_DEL) {
					configuration.setUseStdStateStats(false);
					analyzer = new ReplicationDeletion(aggregateStats);
				} else {
					log.error("Illegal analysis method specified!");
					throw new SimQPNException();
				}
			} else if (configuration.runMode == SimQPNConfiguration.INIT_TRANS) {
				if (configuration.getAnalMethod() == SimQPNConfiguration.AnalysisMethod.WELCH) {
					analyzer = new Welch();
				} else {
					log.error("Analysis method "
							+ configuration.getAnalMethod()
							+ " not supported in INIT_TRANS mode!");
					throw new SimQPNException();
				}
			} else {
				log.error("Invalid run mode specified!");
				throw new SimQPNException();
			}
		} catch (SimQPNException ex) {
			log.error("Error during analyzer creation");
			SimQPNController.setSimRunning(false);
		}
		return analyzer;
	}


		
	protected void saveXmlToFile(Document doc, File file) {
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
		
}
