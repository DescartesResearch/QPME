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
 * Original Author(s):  Samuel Kounev and Christofer Dutz
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2006/??/??  Christofer Dutz   Created.
 *  2009/12/23  Samuel Kounev     Added code to save simulation results to XML (.simqpn) file.
 * 
 */

package de.tud.cs.simqpn.console;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.Simulator;
import de.tud.cs.simqpn.kernel.SimulatorProgress;
import de.tud.cs.simqpn.kernel.Stats;
import de.tud.cs.simqpn.kernel.StatsDocumentBuilder;

public class SimQPN implements IApplication {

	private static void runSimulatorOnDocument(Document netDocument,
			String configuration, String outputFilename, String logConfigFilename, SimulatorProgress progress) throws SimQPNException {
		Element net = netDocument.getRootElement();
		Simulator.configure(net, configuration, logConfigFilename);
		net = Simulator.prepareNet(net, configuration);
		Stats[] result = Simulator.execute(net, configuration, progress);
		// Skip stats document generation for WELCH and REPL_DEL since the 
		// document builder does not support these methods yet.
		if ((result != null) && (Simulator.analMethod == Simulator.BATCH_MEANS)) {
			StatsDocumentBuilder builder = new StatsDocumentBuilder(result, net, configuration);
			Document statsDocument = builder.buildDocument();

			File resultsFile = null;
			if (outputFilename != null) {
				resultsFile = new File(outputFilename);
			} else {
				resultsFile = new File(Simulator.statsDir, builder.getResultFileBaseName() + ".simqpn");
			}

			System.out.println("Saving simulation result to "
				+ resultsFile.getAbsolutePath());

			saveXmlToFile(statsDocument, resultsFile);
		}
	}
		
	private static void saveXmlToFile(Document doc, File file) {
		XMLWriter writer = null;
		try {
			writer = new XMLWriter(new FileOutputStream(file), OutputFormat.createPrettyPrint());
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

	@Override
	public Object start(IApplicationContext context) throws Exception {
		
		Document netDocument = null;
		String configuration = null;
		String outputFilename = null;
		String logConfigFilename = null;
		boolean listConfigurations = false;
		boolean runSimulation = false;
		boolean validConfig = false;

		try {
			String[] args = Platform.getApplicationArgs();
			for (int x = 0; x < args.length; x++) {
				if ("-l".equals(args[x])) {
					listConfigurations = true;
				} else if ("-r".equals(args[x])) {
					runSimulation = true;
					configuration = args[++x];
				} else if ("-o".equals(args[x])) {
					outputFilename = args[++x];
				} else if ("-v".equals(args[x])) { 
					logConfigFilename = args[++x];
				} else if (x == (args.length - 1)) {
					SAXReader xmlReader = new SAXReader();
					netDocument = xmlReader.read(args[x]);
				}
			}

			if ((listConfigurations || runSimulation) == false) {
				System.out.println();
				System.out.println("Syntax: Commandline [-l] [-r \"configuration name\"] [-o \"output-filename\"] [-v \"log-configuration\"] qpn-file");
				System.out.println("Error: At least one of the switches '-l' or '-r' must be specified!");
			} else if (netDocument == null) {
				System.out.println();
				System.out.println("Syntax: Commandline [-l] [-r \"configuration name\"] [-o \"output-filename\"] [-v \"log-configuration\"] qpn-file");
			} else {
				System.out.println();
				if (listConfigurations == true) {
					System.out.println("Available configurations");
					System.out.println("---------------------------------------");
				}
				XPath xpathSelector = DocumentHelper
						.createXPath("/net/meta-attributes/meta-attribute[@name = 'sim-qpn']/@configuration-name");
				Iterator configurationNameIterator = xpathSelector
						.selectNodes(netDocument).iterator();
				while (configurationNameIterator.hasNext()) {
					Attribute nameAttribute = (Attribute) configurationNameIterator
							.next();
					if (nameAttribute.getStringValue().equals(configuration)) 
						validConfig = true;
					if (listConfigurations == true) 
						System.out.println(" - " + nameAttribute.getStringValue());
				}
				
				if (listConfigurations == true) 
					System.out.println("---------------------------------------");
				
				if (runSimulation == true) {
					if (!validConfig) 
						System.out.println("Error: Configuration \"" + configuration + "\" does not exist!");
					else {
						System.out.println();
						System.out.println("Running configuration \"" + configuration + "\"");
						System.out.println();
						try {
							runSimulatorOnDocument(netDocument, configuration,
									outputFilename, logConfigFilename, new ConsoleSimulatorProgress());
						} catch (SimQPNException e) {
							e.printStackTrace();
						}
					}					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		// empty		
	}

}
