package de.tud.cs.simqpn.console;

import java.util.Iterator;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.Simulator;

public class SimQPN {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Document netDocument = null;
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
							Simulator.configure(netDocument.getRootElement(),
									configuration);
							Simulator.execute(netDocument.getRootElement(),
									configuration);
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
}
