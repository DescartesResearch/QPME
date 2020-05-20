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

package de.tud.cs.simqpn.kernel.console;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.XMLConstants;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import de.tud.cs.simqpn.kernel.SimQPNController;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.monitor.ConsoleSimulatorProgress;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;

public class SimQPN implements IApplication {
	
	public static long runtime;

	public static void main(String[] args) {
		startSimQPNWithCommandLine(args);
	}

    /*
     * This method is a helper to run the simulation with a file on the hard disk.
     *
     * ATTENTION: This method is used by the DML solvers to trigger the simulation.
     * If the signature is changed, you will also need to adapt the DML tooling.
     */
	public static long runSimulator(String inputFilename, String configuration, String outputFilename,
			String logConfigFilename, SimulatorProgress progress) {
		try {
			BasicConfigurator.configure();
			LogManager.getRootLogger().setLevel((Level) Level.FATAL);
			Document inputDoc = loadXMLFile(inputFilename);
			runSimulatorOnDocument(inputFilename, inputDoc, configuration, outputFilename, logConfigFilename, progress,
					null);
			return runtime;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	private static void runSimulatorOnDocument(String qpeFilename, Document netDocument,
			String configurationName, String outputFilename, String logConfigFilename, SimulatorProgress progress, Date date) throws SimQPNException {
		Element net = netDocument.getRootElement();
		SimQPNController sim = SimQPNController.createSimQPNController(qpeFilename, net, configurationName,
				logConfigFilename, date);
		long tic = System.currentTimeMillis();
		sim.execute(configurationName, outputFilename, progress);
		long toc = System.currentTimeMillis();
		SimQPN.runtime = toc - tic;
		net = sim.getXMLDescription();
	}

	@Override
	public Object start(IApplicationContext context) throws Exception {
		String[] args = Platform.getApplicationArgs();
		startSimQPNWithCommandLine(args);
		return IApplication.EXIT_OK;
	}
	
	private static Document loadXMLFile(String path) throws DocumentException{
		// check if path is a URL
		File xmlFile = null;
		try {
			URL url = new URL(path);
			xmlFile = new File(url.toURI());
		} catch(MalformedURLException ex) {
			xmlFile = new File(path);
		} catch(URISyntaxException ex) {
			xmlFile = new File(path);
		}
		SAXReader xmlReader = new SAXReader();
		return xmlReader.read(xmlFile);
	}


	private static Integer startSimQPNWithCommandLine(String[] args) {
		//Standard config for logging until specialize logging is initilalized
		BasicConfigurator.configure(); 
		
		Document netDocument = null;
		String documentFilepath = null;
		String configuration = null;
		String outputFilename = null;
		String logConfigFilename = null;
		Date date = null;
		boolean listConfigurations = false;
		boolean runSimulation = false;
		boolean validConfig = false;

		try {
			for (int x = 0; x < args.length; x++) {
				if ("-l".equals(args[x])) {
					listConfigurations = true;
				} else if ("-r".equals(args[x])) {
					runSimulation = true;
					configuration = args[++x].replace("\"", "");
				} else if ("-o".equals(args[x])) {
					outputFilename = args[++x];
				} else if ("-d".equals(args[x])) {
					date = new Date (Long.parseLong(args[++x]));
				} else if ("-v".equals(args[x])) { 
					logConfigFilename = args[++x];
				} else {
					netDocument = loadXMLFile(args[x]);
					documentFilepath = args[x];
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
						.createXPath("/net/meta-attributes/meta-attribute[@xsi:type = 'simqpn-configuration']/@configuration-name");
				Map<String, String> namespaceUris = new HashMap<String, String>();
				namespaceUris.put("xsi", XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI);
				xpathSelector.setNamespaceURIs(namespaceUris);
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
				//System.out.println("waitkey");
				//System.in.read(); //FOR DEBUG PURPOSE				
				if (runSimulation == true) {
					if (!validConfig) 
						System.out.println("Error: Configuration \"" + configuration + "\" does not exist!");
					else {
						System.out.println();
						System.out.println("Running configuration \"" + configuration + "\"");
						System.out.println();
						try {
							runSimulatorOnDocument(documentFilepath, netDocument, configuration,
									outputFilename, logConfigFilename, new ConsoleSimulatorProgress(), date);
						} catch (SimQPNException e) {
							e.printStackTrace();
							return new Integer(1); // signal error
						}
					}					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Integer(1); // signal error
		}
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		// empty		
	}

}
