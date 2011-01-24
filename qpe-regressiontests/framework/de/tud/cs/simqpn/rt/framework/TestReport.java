package de.tud.cs.simqpn.rt.framework;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import de.tud.cs.simqpn.rt.framework.stats.Metric;


public class TestReport {
	
	
	private String queue = null;
	private String place = null;
	private String placeType = null;
	private String color = null;
	
	private XMLStreamWriter writer;
	
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
	
	public void startPlaceAssert(String place, String placeType) {
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
