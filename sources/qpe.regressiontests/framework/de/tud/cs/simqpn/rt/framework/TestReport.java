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
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2011/01/21  Simon Spinner     Created.         
 * 
 */

package de.tud.cs.simqpn.rt.framework;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.math.MathException;
import org.apache.commons.math.stat.inference.TestUtils;
import org.w3c.dom.Document;

import de.tud.cs.simqpn.rt.framework.results.Metric;
import de.tud.cs.simqpn.rt.framework.results.Statistics.ElementType;


public class TestReport {
	
	
	private enum CheckStatus {
		SUCCESS, HIGHLIGHT, FAILURE, ERROR
	}
	
	private class CheckReport {
		public Metric expectedMean;
		public Metric actualMean;
		public double relativeError;
		public boolean tTest;
		public CheckStatus status;
		public String errorMessage;
	}
	
	private class ReportContainer {
		public String name;
		public String type;
		public String errorMessage;
		public List<CheckReport> checkResults = new ArrayList<CheckReport>();
		public List<ReportContainer> childContainers = new ArrayList<ReportContainer>();
	}
	
	private String testName;
	private String description;

	private Stack<ReportContainer> containerTree = new Stack<ReportContainer>();
	
	private XMLStreamWriter writer;
	
	public TestReport(String testName, String description) {
		this.testName = testName;
		this.description = description;
	}
	
	public String getTestName() {
		return testName;
	}
	
	public void startReport(File reportFile) {
		if (!containerTree.isEmpty()) {
			throw new IllegalStateException("Another report is currently being created.");
		}
		
		ReportContainer report = new ReportContainer();
		report.name = "";
		report.type = "report";
		containerTree.add(report);
	}
	
	public void endReport() {
		try {
			Document doc = writeReport();
			
			InputStream xslt = this.getClass().getResourceAsStream("/de/tud/cs/simqpn/rt/resources/report_to_html.xsl");
			TransformerFactory transformFactory = TransformerFactory.newInstance();
			Transformer htmlTransform = transformFactory.newTransformer(new StreamSource(xslt));
			DOMSource source = new DOMSource(doc);
			DOMResult html = new DOMResult();
			htmlTransform.transform(source, new StreamResult(new File("reports/" + testName + "/latest.html")));
			
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}		
	}
	
	private Document writeReport() throws XMLStreamException, ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = docFactory.newDocumentBuilder();		
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
	
		Document doc = builder.newDocument();
		writer = factory.createXMLStreamWriter(new DOMResult(doc));
		
		writer.writeStartDocument();
		writer.writeStartElement("report");
		writer.writeAttribute("test", testName);
		
		writer.writeStartElement("description");
		writer.writeCharacters(description);
		writer.writeEndElement(); //description
		
		writeContainer(writer, containerTree.pop());
		
		writer.writeEndElement(); //test-report		
		writer.writeEndDocument();
		
		writer.close();
		
		return doc;
	}

	private void writeContainer(XMLStreamWriter writer, ReportContainer container) throws XMLStreamException {
		writer.writeStartElement("container");
		
		writer.writeAttribute("name", container.name);
		writer.writeAttribute("type", container.type.toString());
		
		if (container.errorMessage != null) {
			writer.writeStartElement("error");
			writer.writeComment(container.errorMessage);
			writer.writeEndElement(); //error
		}
		
		if (!container.checkResults.isEmpty()) {
			writer.writeStartElement("check-results");
			for (CheckReport check : container.checkResults) {
				writer.writeStartElement("check-result");
				writer.writeAttribute("name", check.expectedMean.getName());
				
				writer.writeAttribute("expected-mean", String.format("%.5g", check.expectedMean.getMean()));
				writer.writeAttribute("status", check.status.toString());
				if (check.errorMessage != null) {
					writer.writeStartElement("error");
					writer.writeCharacters(check.errorMessage);
					writer.writeEndElement(); //error
				} else {
					writer.writeAttribute("actual-mean", String.format("%.5g", check.actualMean.getMean()));
					writer.writeAttribute("relative-error", String.format("%.5g", check.relativeError));	
					try {
						writer.writeAttribute("t-test", ((check.tTest) ? "yes" : "no") + " (" + String.format("%.5g", TestUtils.tTest(check.expectedMean.getSamples(), check.actualMean.getSamples())) + ")");
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MathException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				writer.writeEndElement(); //check-result
			}
			writer.writeEndElement(); //check-results
		}
		
		if (!container.childContainers.isEmpty()) {
			writer.writeStartElement("child-containers");
			for (ReportContainer childContainer : container.childContainers) {
				writeContainer(writer, childContainer);
			}
			writer.writeEndElement(); //child-container
		}
		
		writer.writeEndElement(); //container		
	}

	public void startQueueAssert(String queueName) {
		
		ReportContainer queueContainer = new ReportContainer();
		queueContainer.name = queueName;
		queueContainer.type = ElementType.QUEUE.toString();
		
		containerTree.peek().childContainers.add(queueContainer);
		containerTree.push(queueContainer);
	}
	
	public void endQueueAssert() {
		if (!containerTree.peek().type.equals(ElementType.QUEUE.toString())) {
			throw new IllegalStateException("Unexpected endQueueAssert()");
		}
		containerTree.pop();
	}
	
	public void startPlaceAssert(String placeName, ElementType placeType) {
		ReportContainer placeContainer = new ReportContainer();
		placeContainer.name = placeName;
		placeContainer.type = placeType.toString();
		
		containerTree.peek().childContainers.add(placeContainer);
		containerTree.push(placeContainer);
	}
	
	public void endPlaceAssert() {
		String type = containerTree.peek().type;
		if ((!type.equals(ElementType.ORDINARY_PLACE.toString())) && 
				(!type.equals(ElementType.QPLACE_DEPOSITORY.toString())) && 
				(!type.equals(ElementType.QPLACE_QUEUE.toString()))) {
			throw new IllegalStateException("Unexpected endPlaceAssert()");
		}
		containerTree.pop();
	}
	
	public void startColorAssert(String colorName) {
		ReportContainer colorContainer = new ReportContainer();
		colorContainer.name = colorName;
		colorContainer.type = ElementType.COLOR.toString();
		
		containerTree.peek().childContainers.add(colorContainer);
		containerTree.push(colorContainer);
	}
	
	public void endColorAssert() {
		if (!containerTree.peek().type.equals(ElementType.COLOR.toString())) {
			throw new IllegalStateException("Unexpected endColorAssert()");
		}
		containerTree.pop();
	}
	
	public void success(Metric expected, Metric actual, boolean tTest) {
		CheckReport check = new CheckReport();
		check.expectedMean = expected;
		check.actualMean = actual;
		check.relativeError = calcuateError(actual, expected);
		check.tTest = tTest;
		check.status = CheckStatus.SUCCESS;
		
		containerTree.peek().checkResults.add(check);
	}
	
	public void highlight(Metric expected, Metric actual, boolean tTest) {
		CheckReport check = new CheckReport();
		check.expectedMean = expected;
		check.actualMean = actual;
		check.relativeError = calcuateError(actual, expected);
		check.tTest = tTest;
		check.status = CheckStatus.HIGHLIGHT;
		
		containerTree.peek().checkResults.add(check);
	}
	
	public void failure(Metric expected, Metric actual, boolean tTest) {
		CheckReport check = new CheckReport();
		check.expectedMean = expected;
		check.actualMean = actual;
		check.relativeError = calcuateError(actual, expected);
		check.tTest = tTest;
		check.status = CheckStatus.FAILURE;
		
		containerTree.peek().checkResults.add(check);
	}
	
	public void error(Metric expected, String errorMessage) {
		CheckReport check = new CheckReport();
		check.expectedMean = expected;
		check.errorMessage = errorMessage;
		check.status = CheckStatus.ERROR;
		
		containerTree.peek().checkResults.add(check);
	}
	
	public void error(String message) {
		containerTree.peek().errorMessage = message;
	}
	
	public void startReferenceSet(String name) {
		ReportContainer container = new ReportContainer();
		container.name = name;
		container.type = "reference-set";
		
		containerTree.peek().childContainers.add(container);
		containerTree.push(container);
	}
	
	public void endReferenceSet() {
		if (!containerTree.peek().type.equals("reference-set")) {
			throw new IllegalStateException("Unexpected endReferenceSet()");
		}
		containerTree.pop();
	}
	
	private double calcuateError(Metric actual, Metric expected) {
		if ((actual.getMean() == 0.0) && (expected.getMean() == 0.0)) {
			return 0.0;
		} else {
			return ((actual.getMean() - expected.getMean()) / expected.getMean() * 100);
		}
	}
}
