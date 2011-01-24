package de.tud.cs.simqpn.rt.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import de.tud.cs.simqpn.rt.framework.stats.Metric;
import de.tud.cs.simqpn.rt.framework.stats.PlaceStatistics;
import de.tud.cs.simqpn.rt.framework.stats.Statistics;

public class SimulationResults {

	private Map<String, PlaceStatistics> places = new HashMap<String, PlaceStatistics>();
	private Map<String, Statistics> queues = new HashMap<String, Statistics>();
	
	
	public SimulationResults() {
	}
	
	public void addPlaceStatistics(PlaceStatistics stats) {
		places.put(PlaceStatistics.getId(stats.getType(), stats.getName()), stats);
	}
	
	public void addQueueStatistics(Statistics stats) {
		queues.put(stats.getName(), stats);
	}

	public PlaceStatistics getPlaceStatistics(String type, String name) {
		return places.get(PlaceStatistics.getId(type, name));
	}
	
	public PlaceStatistics getOrCreatePlaceStatistics(String type, String name) {
		PlaceStatistics stats = places.get(PlaceStatistics.getId(type, name));
		if (stats == null) {
			stats = new PlaceStatistics(name, type);
			places.put(PlaceStatistics.getId(type, name), stats);
		}
		return stats;
	}

	public Statistics getQueueStatistics(String name) {
		return queues.get(name);
	}
	
	public Statistics getOrCreateQueueStatistics(String name) {
		Statistics stats = queues.get(name);
		if (stats == null) {
			stats = new Statistics(name);
			queues.put(name, stats);
		}
		return stats;
	}

	public Collection<PlaceStatistics> getPlaces() {
		return Collections.unmodifiableCollection(places.values());
	}

	public Collection<Statistics> getQueues() {
		return Collections.unmodifiableCollection(queues.values());
	}

	public void save(File file) throws FileNotFoundException,
			XMLStreamException {
		XMLOutputFactory outFactory = XMLOutputFactory.newInstance();
		XMLStreamWriter writer = outFactory
				.createXMLStreamWriter(new FileOutputStream(file));
		try {
			writer.writeStartDocument();
			writer.writeStartElement("test-data");

			for (PlaceStatistics place : places.values()) {
				writer.writeStartElement("place");
				writer.writeAttribute("name", place.getName());
				writer.writeAttribute("type", place.type);
				
				for (Metric metric : place.getMetrics()) {
					writeMetricElement(metric, writer);
				}
				
				for (Statistics color : place.getColorStats()) {
					writer.writeStartElement("color");
					writer.writeAttribute("name", color.getName());

					for (Metric metric : color.getMetrics()) {
						writeMetricElement(metric, writer);
					}

					writer.writeEndElement();
				}

				writer.writeEndElement();
			}

			for (Statistics queue : queues.values()) {
				writer.writeStartElement("queue");
				writer.writeAttribute("name", queue.getName());

				for (Metric metric : queue.getMetrics()) {
					writeMetricElement(metric, writer);
				}

				writer.writeEndElement();
			}

			writer.writeEndElement();
			writer.writeEndDocument();
		} finally {
			writer.close();
		}
	}

	public void load(File file) throws FileNotFoundException,
			XMLStreamException {
		XMLInputFactory inFactory = XMLInputFactory.newInstance();
		XMLStreamReader reader = inFactory
				.createXMLStreamReader(new FileInputStream(file));

		while (reader.hasNext()) {
			reader.next();

			if (reader.isStartElement()) {
				if (reader.getName().equals(new QName("place"))) {
					PlaceStatistics place = readPlaceElement(reader);
					places.put(PlaceStatistics.getId(place.getType(), place.getName()), place);
				} else if (reader.getName().equals(new QName("queue"))) {
					Statistics queue = readQueueElement(reader);
					queues.put(queue.getName(), queue);
				}
			}
		}
	}

	private Statistics readQueueElement(XMLStreamReader reader)
			throws NumberFormatException, XMLStreamException {

		String name = reader.getAttributeValue(null, "name");
		Statistics queue = new Statistics(name);

		while (reader.hasNext()) {
			reader.next();

			if (reader.isEndElement()
					&& reader.getName().equals(new QName("queue")))
				break;

			if (reader.isStartElement()
					&& reader.getName().equals(new QName("metric"))) {
				Metric metric = readMetricElement(reader);
				queue.addMetric(metric);
			}
		}
		return queue;
	}

	private PlaceStatistics readPlaceElement(XMLStreamReader reader)
			throws XMLStreamException {
		String name = reader.getAttributeValue(null, "name");
		String type = reader.getAttributeValue(null, "type");
		PlaceStatistics place = new PlaceStatistics(name, type);

		while (reader.hasNext()) {
			reader.next();

			if (reader.isEndElement()
					&& reader.getName().equals(new QName("place")))
				break;

			if (reader.isStartElement()
					&& reader.getName().equals(new QName("color"))) {
				Statistics color = readColorElement(reader);
				place.addColorStats(color);
			}

			if (reader.isStartElement()
					&& reader.getName().equals(new QName("metric"))) {
				Metric metric = readMetricElement(reader);
				place.addMetric(metric);
			}
		}
		return place;
	}

	private Statistics readColorElement(XMLStreamReader reader)
			throws XMLStreamException {
		String name = reader.getAttributeValue(null, "name");
		Statistics color = new Statistics(name);
		
		while (reader.hasNext()) {
			reader.next();

			if (reader.isEndElement()
					&& reader.getName().equals(new QName("color")))
				break;

			if (reader.isStartElement()
					&& reader.getName().equals(new QName("metric"))) {
				Metric metric = readMetricElement(reader);
				color.addMetric(metric);				
			}
		}

		return color;
	}

	private Metric readMetricElement(XMLStreamReader reader) throws XMLStreamException {
		String name = reader.getAttributeValue(null, "name");
		Metric metric = new Metric(name);
		
		while (reader.hasNext()) {
			reader.next();

			if (reader.isEndElement()
					&& reader.getName().equals(new QName("metric")))
				break;
			
			if (reader.isStartElement()
					&& reader.getName().equals(new QName("sample"))) {
				metric.addSample(Double.parseDouble(reader.getAttributeValue(null, "value")));
			}
		}
		
		return metric;
	}

	private void writeMetricElement(Metric metric,
			XMLStreamWriter writer) throws XMLStreamException {
		writer.writeStartElement("metric");
		writer.writeAttribute("name", metric.getName());
		writer.writeStartElement("summary");
		double mean = metric.getMean();
		if(mean != Double.NaN) {
			writer.writeAttribute("mean", Double.toString(mean));
		}
		double stdDev = metric.getStandardDeviation();
		if(stdDev != Double.NaN) {
			writer.writeAttribute("stdDev", Double.toString(stdDev));
		}
		double max = metric.getMaximum();
		if(max != Double.NaN) {
			writer.writeAttribute("max", Double.toString(max));
		}
		double min = metric.getMinimum();
		if(min != Double.NaN) {
			writer.writeAttribute("min", Double.toString(min));
		}
		writer.writeEndElement();
		
		writer.writeStartElement("data");
		
		for(double value : metric.getSamples()) {
			writer.writeStartElement("sample");
			writer.writeAttribute("value", Double.toString(value));
			writer.writeEndElement();
		}		
		writer.writeEndElement();
		writer.writeEndElement();
	}

}
