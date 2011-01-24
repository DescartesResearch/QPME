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
 * Original Author(s):  Simon Spinner
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ----------------------------------------------------------------------------------
 *  2011/01/21  Simon Spinner     Created.         
 * 
 */

package de.tud.cs.simqpn.rt.framework;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import de.tud.cs.simqpn.rt.framework.results.Metric;

public class TestConfig {
	
	public static enum CheckConfig {
		MIN_CHECK, MAX_CHECK, MEAN_CHECK;
	}
	
	
	private static class TestDefinition {
		public String name;
		public int runs;
		public double accuracy;
		public Set<String> skippedElements = new HashSet<String>();
	}
	
	private static final Logger log = Logger.getLogger(TestConfig.class);

	private static TestConfig instance = null;

	private Map<String, String> substitutions = new HashMap<String, String>();
	private Set<String> ignores = new HashSet<String>();
	private Map<String, CheckConfig> checks = new HashMap<String, CheckConfig>();
	private Map<String, TestDefinition> tests = new HashMap<String, TestDefinition>();

	private TestConfig() {
	}

	public static TestConfig getInstance() {
		if (instance == null) {
			instance = new TestConfig();
			instance.load();
		}
		return instance;
	}

	private void load() {		
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		URL schemaURL = this.getClass().getResource("/de/tud/cs/simqpn/rt/resources/testconfig.xsd");
		if (schemaURL == null) {
			log.error("Could not find schema file testconfig.xsd");
			throw new RuntimeException();
		}
		
		try {
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File("testfiles/default.testconfig.xml"));
			
			Schema schema = schemaFactory.newSchema(schemaURL);
			Validator validator = schema.newValidator();
			validator.setErrorHandler(new ErrorHandler() {
				
				@Override
				public void warning(SAXParseException exception) throws SAXException {
					log.warn(exception.getMessage());				
				}
				
				@Override
				public void fatalError(SAXParseException exception) throws SAXException {
					log.error(exception.getMessage());
					throw exception;
				}
				
				@Override
				public void error(SAXParseException exception) throws SAXException {
					log.error(exception.getMessage());
					throw exception;
				}
			});
			validator.validate(new DOMSource(doc));
			
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			
			NodeList list = (NodeList)xpath.evaluate("//substitute", doc, XPathConstants.NODESET);
			for (int i = 0; i < list.getLength(); i++) {
				Element elem = (Element)list.item(i);
				substitutions.put(elem.getAttribute("name"), elem.getAttribute("with"));
			}
						
			list = (NodeList)xpath.evaluate("//ignore", doc, XPathConstants.NODESET);
			for (int i = 0; i < list.getLength(); i++) {
				Element elem = (Element)list.item(i);
				ignores.add(elem.getAttribute("metric"));
			}
			
			list = (NodeList)xpath.evaluate("//check", doc, XPathConstants.NODESET);
			for (int i = 0; i < list.getLength(); i++) {
				Element elem = (Element)list.item(i);
				String type = elem.getAttribute("type").toLowerCase();
				CheckConfig check;
				if (type.equals("mean")) {
					check = CheckConfig.MEAN_CHECK;
				} else if (type.equals("max")) {
					check = CheckConfig.MAX_CHECK;
				} else if (type.equals("min")) {
					check = CheckConfig.MIN_CHECK;
				} else {
					log.error("Unkown check type: " + type);
					throw new RuntimeException();
				}				
				checks.put(elem.getAttribute("metric"), check);
			}
			
			list = (NodeList)xpath.evaluate("//test", doc, XPathConstants.NODESET);
			for (int i = 0; i < list.getLength(); i++) {
				Element elem = (Element)list.item(i);
				TestDefinition def = new TestDefinition();
				def.name = elem.getAttribute("name");
				def.runs = Integer.parseInt(elem.getAttribute("test-runs"));
				def.accuracy = Double.parseDouble(elem.getAttribute("accuracy"));
				
				NodeList skips = elem.getElementsByTagName("skip");
				for (int j = 0; j < skips.getLength(); j++) {
					Element skipElem = (Element)skips.item(j);
					String key = skipElem.getAttribute("place");
					if (skipElem.hasAttribute("color")) {
						key = key + ":" + skipElem.getAttribute("color");
					}
					def.skippedElements.add(key);
				}
			
				tests.put(def.name, def);
			}
			
		} catch(SAXParseException ex) {
			log.error("Invalid test config file. See previous error messages for details.");
			throw new RuntimeException(ex);
		} catch(Exception ex) {
			log.error("Could not load test config", ex);
			throw new RuntimeException(ex);
		}
	}

	public String applySubstitutions(String name) {
		if (substitutions.containsKey(name)) {
			return substitutions.get(name);
		} else {
			return name;
		}
	}
	
	public boolean isIgnored(Metric metric) {
		return ignores.contains(metric.getName());
	}

	public CheckConfig getCheckConfig(Metric expected) {
		String name = expected.getName();
		CheckConfig c = checks.get(name.split(":")[0]);
		if (c == null) {
			throw new RuntimeException("Missing check configuration for metric " + expected.getName() + ".");
		}
		return c;
	}
	
	public int getTestRuns(String testName) {
		return getDefinition(testName).runs;
	}
	
	public double getTestAccuracy(String testName) {
		return getDefinition(testName).accuracy;
	}
	
	public boolean isSkipped(String testName, String place, String color) {
		if (color != null) {
			return getDefinition(testName).skippedElements.contains(place + ":" + color);
		} else {
			return getDefinition(testName).skippedElements.contains(place);
		}
	}
	
	private TestDefinition getDefinition(String testName) {
		TestDefinition def = tests.get(testName);
		if (def == null) {
			throw new RuntimeException("Missing test definition for test " + testName + ".");
		}
		return def;
	}

}
