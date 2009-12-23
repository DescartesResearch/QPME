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
 *  ----------  ----------------  ------------------------------------------------------------------
 *  2006/??/??  Christofer Dutz   Created.
 *  2009/12/23  Samuel Kounev     Added code to save simulation results to XML (.simqpn) file.
 * 
 */

package de.tud.cs.simqpn.console;

import java.io.File;
import java.io.FileWriter;
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
 
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.Simulator;
import de.tud.cs.simqpn.kernel.Stats;
import de.tud.cs.simqpn.kernel.StatsDocumentBuilder;

public class SimQPN {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Document netDocument = null;
		Element net;
		String configuration = null;
		boolean listConfigurations = false;
		boolean runSimulation = false;
		boolean validConfig = false;

		try {
			for (int x = 0; x < args.length; x++) {
				if ("-l".equals(args[x])) {
					listConfigurations = true;
				} else if ("-r".equals(args[x])) {
					runSimulation = true;
					configuration = args[++x];
				} else if (x == (args.length - 1)) {
					SAXReader xmlReader = new SAXReader();
					netDocument = xmlReader.read(args[x]);
				}
			}

			if ((listConfigurations || runSimulation) == false) {
				System.out.println();
				System.out.println("Syntax: Commandline [-l] [-r \"configuration name\"] qpn-file");
				System.out.println("Error: At least one of the switches '-l' or '-r' must be specified!");
			} else if (netDocument == null) {
				System.out.println();
				System.out.println("Syntax: Commandline [-l] [-r \"configuration name\"] qpn-file");
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
							net = netDocument.getRootElement();
							Simulator.configure(net, configuration);							
							Stats[] result = Simulator.execute(net, configuration);
							StatsDocumentBuilder builder = new StatsDocumentBuilder(result, net, configuration);
							Document statsDocument = builder.buildDocument();
							File resultsFile = new File(Simulator.statsDir, builder.getResultFileBaseName() + ".simqpn");
							saveXmlToFile(statsDocument, resultsFile);
						} catch (SimQPNException e) {
							e.printStackTrace();
						}
					}					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	private static void saveXmlToFile(Document doc, File file) {
		XMLWriter writer = null;
		try {
			writer = new XMLWriter(new FileWriter(file), OutputFormat.createPrettyPrint());
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

}
