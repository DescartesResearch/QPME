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
import java.io.FileOutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import de.tud.cs.simqpn.rt.framework.results.Metric;
import de.tud.cs.simqpn.rt.framework.results.Statistics.ElementType;


public class TestReport {
	
	private String testName;
	
	private String queue = null;
	private String place = null;
	private ElementType placeType = null;
	private String color = null;
	
	private XMLStreamWriter writer;
	
	public TestReport(String testName) {
		this.testName = testName;
	}
	
	public String getTestName() {
		return testName;
	}
	
	public void startReport(File reportFile) {
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		try {
			writer = factory.createXMLStreamWriter(new FileOutputStream(reportFile));
			writer.writeStartDocument();
			writer.writeStartElement("html");
			writer.writeStartElement("head");
			writer.writeStartElement("title");
			writer.writeCharacters("SimQPN Regression Test Results");
			writer.writeEndElement();
			writer.writeStartElement("body");
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void endReport() {
		try {
			writer.writeEndElement();
			writer.writeEndDocument();
			writer.close();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}
	
	public void startQueueAssert(String queue) {
		this.queue = queue;
		
		try {
			writer.writeStartElement("tr");			
			writer.writeStartElement("td");
			writer.writeAttribute("colspan", "6");
			writer.writeAttribute("style", "background-color:lightgrey");
			writer.writeCharacters("Queue: " + queue);
			writer.writeEndElement();		
			writer.writeEndElement();
		} catch(XMLStreamException ex) {
			ex.printStackTrace();
		}
	}
	
	public void endQueueAssert() {
		this.queue = null;
	}
	
	public void startPlaceAssert(String place, ElementType placeType) {
		this.place = place;
		this.placeType = placeType;
		
		try {
			writer.writeStartElement("tr");			
			writer.writeStartElement("td");
			writer.writeAttribute("colspan", "6");
			writer.writeAttribute("style", "background-color:lightgrey");
			writer.writeCharacters("Place: " + place + " (" + placeType + ")");
			writer.writeEndElement();		
			writer.writeEndElement();
		} catch(XMLStreamException ex) {
			ex.printStackTrace();
		}
	}
	
	public void endPlaceAssert() {
		this.place = null;
		this.placeType = null;
	}
	
	public void startColorAssert(String color) {
		this.color = color;
		
		try {
			writer.writeStartElement("tr");
			writer.writeStartElement("td");
			writer.writeAttribute("width", "60");
			writer.writeCharacters(" ");
			writer.writeEndElement();
			writer.writeStartElement("td");
			writer.writeAttribute("style", "background-color:lightgrey");
			writer.writeAttribute("colspan", "5");
			writer.writeCharacters("Color: " + color);
			writer.writeEndElement();		
			writer.writeEndElement();
		} catch(XMLStreamException ex) {
			ex.printStackTrace();
		}
	}
	
	public void endColorAssert() {
		this.color = null;
	}
	
	public void success(String message) {
		try {
			writer.writeStartElement("tr");
			writer.writeStartElement("td");
			writer.writeCharacters(" ");
			writer.writeEndElement();
			writer.writeStartElement("td");
			writer.writeAttribute("width", "60");
			writer.writeCharacters(" ");
			writer.writeEndElement();
			writer.writeStartElement("td");
			writer.writeAttribute("colspan", "4");
			writer.writeAttribute("style", "color: green;");
			writer.writeCharacters(message);
			writer.writeEndElement();		
			writer.writeEndElement();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void success(Metric expected, Metric actual) {
		try {
			writer.writeStartElement("tr");
			writer.writeStartElement("td");
			writer.writeCharacters(" ");
			writer.writeEndElement();
			writer.writeStartElement("td");
			writer.writeAttribute("width", "60");
			writer.writeCharacters(" ");
			writer.writeEndElement();
			writer.writeStartElement("td");
			writer.writeCharacters(expected.getName());
			writer.writeEndElement();
			writer.writeStartElement("td");
			writer.writeCharacters(Double.toString(expected.getMean()));
			writer.writeEndElement();
			writer.writeStartElement("td");
			writer.writeCharacters(Double.toString(actual.getMean()));
			writer.writeEndElement();
			writer.writeStartElement("td");
			writer.writeAttribute("style", "color: green;");
			if ((actual.getMean() == 0.0) && (expected.getMean() == 0.0)) {
				writer.writeCharacters("---");
			} else {
				writer.writeCharacters(Double.toString((actual.getMean() - expected.getMean()) / expected.getMean() * 100) + "%");
			}
			writer.writeEndElement();
			writer.writeEndElement();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void ignored(Metric expected) {
		try {
			writer.writeStartElement("tr");	
			writer.writeStartElement("td");
			writer.writeCharacters(" ");
			writer.writeEndElement();
			writer.writeStartElement("td");
			writer.writeAttribute("width", "60");
			writer.writeCharacters(" ");
			writer.writeEndElement();
			writer.writeStartElement("td");
			writer.writeCharacters(expected.getName());
			writer.writeEndElement();
			writer.writeStartElement("td");
			writer.writeAttribute("colspan", "3");
			writer.writeAttribute("style", "color: grey;");
			writer.writeCharacters("ignored");
			writer.writeEndElement();		
			writer.writeEndElement();
		} catch(Exception ex) {
			ex.printStackTrace();
		}	
	}
	
	public void failure(String message) {
		try {
			writer.writeStartElement("tr");	
			writer.writeStartElement("td");
			writer.writeCharacters(" ");
			writer.writeEndElement();
			writer.writeStartElement("td");
			writer.writeAttribute("width", "60");
			writer.writeCharacters(" ");
			writer.writeEndElement();
			writer.writeStartElement("td");
			writer.writeAttribute("colspan", "4");
			writer.writeAttribute("style", "color: red;");
			writer.writeCharacters(message);
			writer.writeEndElement();		
			writer.writeEndElement();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void failure(Metric expected, Metric actual) {
		try {
			writer.writeStartElement("tr");	
			writer.writeStartElement("td");
			writer.writeCharacters(" ");
			writer.writeEndElement();
			writer.writeStartElement("td");
			writer.writeAttribute("width", "60");
			writer.writeCharacters(" ");
			writer.writeEndElement();
			writer.writeStartElement("td");
			writer.writeCharacters(expected.getName());
			writer.writeEndElement();
			writer.writeStartElement("td");
			writer.writeCharacters(Double.toString(expected.getMean()));
			writer.writeEndElement();
			writer.writeStartElement("td");
			writer.writeCharacters(Double.toString(actual.getMean()));
			writer.writeEndElement();
			writer.writeStartElement("td");
			writer.writeAttribute("style", "color: red;");
			if ((actual.getMean() == 0.0) && (expected.getMean() == 0.0)) {
				writer.writeCharacters("---");
			} else {
				writer.writeCharacters(Double.toString((actual.getMean() - expected.getMean()) / expected.getMean() * 100) + "%");
			}
			writer.writeEndElement();
			writer.writeEndElement();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void startAssertSet() {
		try {
			writer.writeStartElement("table");
			writer.writeAttribute("width", "100%");
			writer.writeStartElement("tr");	
			writer.writeAttribute("style", "font-weight:bold;");
			writer.writeStartElement("td");
			writer.writeAttribute("colspan", "3");
			writer.writeCharacters("Element");
			writer.writeEndElement();
			writer.writeStartElement("td");
			writer.writeCharacters("Expected Mean");
			writer.writeEndElement();
			writer.writeStartElement("td");
			writer.writeCharacters("Actual Mean");
			writer.writeEndElement();
			writer.writeStartElement("td");
			writer.writeCharacters("Relative Error");
			writer.writeEndElement();
			writer.writeEndElement();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}
	
	public void endAssertSet() {
		try {
			writer.writeEndElement();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}		
	}	
}
