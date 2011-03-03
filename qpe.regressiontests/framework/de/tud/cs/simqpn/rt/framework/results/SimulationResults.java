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

package de.tud.cs.simqpn.rt.framework.results;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import de.tud.cs.simqpn.rt.framework.results.Statistics.ElementType;

public class SimulationResults {

	private List<RunInfo> infos = new ArrayList<RunInfo>();
	private Metric wallClockTime = new Metric(Metric.WALL_CLOCK_TIME);
	private Map<String, Statistics> stats = new HashMap<String, Statistics>();

	public SimulationResults() {
	}

	public List<RunInfo> getRunInfos() {
		return infos;
	}
	
	public Metric getWallClockTime() {
		return wallClockTime;
	}

	public void addStatistics(Statistics s) {
		stats.put(Statistics.getId(s.getName(), s.getType()), s);
	}

	public Statistics getStatistics(String name, ElementType type) {
		return stats.get(Statistics.getId(name, type));
	}

	public Statistics getOrCreateStatistics(String name, ElementType type) {
		Statistics s = getStatistics(name, type);
		if (s == null) {
			s = new Statistics(name, type);
			stats.put(Statistics.getId(name, type), s);
		}
		return s;
	}

	public List<Statistics> getPlaceStats() {
		List<Statistics> ret = new ArrayList<Statistics>();
		for (Statistics s : stats.values()) {
			if ((s.getType() == ElementType.ORDINARY_PLACE)
					|| (s.getType() == ElementType.QPLACE_DEPOSITORY)
					|| (s.getType() == ElementType.QPLACE_QUEUE)) {
				ret.add(s);
			}
		}
		return ret;
	}

	public List<Statistics> getQueueStats() {
		List<Statistics> ret = new ArrayList<Statistics>();
		for (Statistics s : stats.values()) {
			if (s.getType() == ElementType.QUEUE) {
				ret.add(s);
			}
		}
		return ret;
	}

	public List<Statistics> getProbeStats() {
		List<Statistics> ret = new ArrayList<Statistics>();
		for (Statistics s : stats.values()) {
			if (s.getType() == ElementType.PROBE) {
				ret.add(s);
			}
		}
		return ret;
	}

	public void save(File file) throws Exception {
		XMLOutputFactory outFactory = XMLOutputFactory.newInstance();
		XMLStreamWriter writer = outFactory
				.createXMLStreamWriter(new FileOutputStream(file));
		try {
			writer.writeStartDocument();
			writer.writeStartElement("test-data");

			writeMetricElement(wallClockTime, writer);
			
			for (Statistics place : stats.values()) {
				writeDataSetElement(place, writer);
			}
			
			writer.writeEndElement();
			writer.writeEndDocument();
		} finally {
			writer.close();
		}
	}
	
	private void writeDataSetElement(Statistics stats, XMLStreamWriter writer) throws Exception {
		writer.writeStartElement("dataset");
		writer.writeAttribute("name", stats.getName());
		writer.writeAttribute("type", stats.getType().toString());	

		for (Metric metric : stats.getMetrics()) {
			writeMetricElement(metric, writer);
		}

		for (Statistics color : stats.getChildStats()) {
			writeDataSetElement(color, writer);
		}

		writer.writeEndElement();
	}

	private void writeMetricElement(Metric metric, XMLStreamWriter writer)
			throws XMLStreamException {
		writer.writeStartElement("metric");
		writer.writeAttribute("name", metric.getName());
		writer.writeStartElement("summary");
		double mean = metric.getMean();
		if (mean != Double.NaN) {
			writer.writeAttribute("mean", Double.toString(mean));
		}
		double stdDev = metric.getStandardDeviation();
		if (stdDev != Double.NaN) {
			writer.writeAttribute("stdDev", Double.toString(stdDev));
		}
		double max = metric.getMaximum();
		if (max != Double.NaN) {
			writer.writeAttribute("max", Double.toString(max));
		}
		double min = metric.getMinimum();
		if (min != Double.NaN) {
			writer.writeAttribute("min", Double.toString(min));
		}
		writer.writeEndElement();

		writer.writeStartElement("data");

		StringBuilder valueText = new StringBuilder();
		int i = 0;
		for (double value : metric.getSamples()) {
			if (i > 0) {
				valueText.append(", ");
			}
			valueText.append(Double.toString(value));
			i++;
		}
		writer.writeCharacters(valueText.toString());

		writer.writeEndElement();
		writer.writeEndElement();
	}

	public void load(File file) throws Exception {
		XMLInputFactory inFactory = XMLInputFactory.newInstance();
		XMLStreamReader reader = inFactory
				.createXMLStreamReader(new FileInputStream(file));

		while (reader.hasNext()) {
			reader.next();

			if (reader.isStartElement()
					&& reader.getName().equals(new QName("metric"))) {
				Metric metric = readMetricElement(reader);
				if (metric.getName().equals(Metric.WALL_CLOCK_TIME)) {
					this.wallClockTime = metric;
				}
			}
			
			if (reader.isStartElement() && reader.getName().equals(new QName("dataset"))) {
				addStatistics(readDataSetElement(reader));
			}
		}
	}

	private Statistics readDataSetElement(XMLStreamReader reader)
			throws Exception {

		String name = reader.getAttributeValue(null, "name");
		ElementType type = ElementType.parseType(reader.getAttributeValue(null, "type"));
		Statistics element = new Statistics(name, type);

		while (reader.hasNext()) {
			reader.next();

			if (reader.isEndElement()
					&& reader.getName().equals(new QName("dataset")))
				break;

			if (reader.isStartElement()
					&& reader.getName().equals(new QName("dataset"))) {
				Statistics color = readDataSetElement(reader);
				element.addChildStats(color);
			}

			if (reader.isStartElement()
					&& reader.getName().equals(new QName("metric"))) {
				Metric metric = readMetricElement(reader);
				element.addMetric(metric);
			}
		}
		return element;
	}

	private Metric readMetricElement(XMLStreamReader reader)
			throws XMLStreamException {
		String name = reader.getAttributeValue(null, "name");
		Metric metric = new Metric(name);

		while (reader.hasNext()) {
			reader.next();

			if (reader.isEndElement()
					&& reader.getName().equals(new QName("metric")))
				break;

			if (reader.isStartElement()
					&& reader.getName().equals(new QName("data"))) {
				String data = reader.getElementText();
				StringTokenizer tokenizer = new StringTokenizer(data, ",");
				while (tokenizer.hasMoreTokens()) {
					String sample = tokenizer.nextToken().trim();
					metric.addSample(Double.parseDouble(sample));
				}
			}
		}

		return metric;
	}
}
