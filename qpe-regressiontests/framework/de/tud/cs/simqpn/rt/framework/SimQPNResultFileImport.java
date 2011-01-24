package de.tud.cs.simqpn.rt.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import de.tud.cs.simqpn.rt.framework.stats.PlaceStatistics;
import de.tud.cs.simqpn.rt.framework.stats.Statistics;

public class SimQPNResultFileImport {
	public void execute(File simqpnFile, SimulationResults results) throws FileNotFoundException, XMLStreamException {
		
		XMLInputFactory inFactory = XMLInputFactory.newInstance();
		XMLStreamReader reader = inFactory
				.createXMLStreamReader(new FileInputStream(simqpnFile));

		while (reader.hasNext()) {
			reader.next();

			if (reader.isStartElement()) {
				if (reader.getName().equals(new QName("observed-element"))) {
					if("queue".equals(reader.getAttributeValue(null, "type"))) {
						readQueueElement(reader, results);
					} else {
						readPlaceElement(reader, results);
					}
				}
			}
		}
	}
	
	private Statistics readQueueElement(XMLStreamReader reader, SimulationResults results) throws XMLStreamException {
		String name = reader.getAttributeValue(null, "name");
		Statistics queue = results.getOrCreateQueueStatistics(name);
		
		while (reader.hasNext()) {
			reader.next();
			
			if (reader.isEndElement() && reader.getName().equals(new QName("observed-element")))
				break;

			if (reader.isStartElement() && reader.getName().equals(new QName("metric"))) {
				String metric = reader.getAttributeValue(null, "type");
				queue.getOrCreateMetric(metric).addSample(Double.parseDouble(reader.getAttributeValue(null, "value")));
			}
		}		
		return queue;
	}

	private PlaceStatistics readPlaceElement(XMLStreamReader reader, SimulationResults results) throws XMLStreamException {
		String name = reader.getAttributeValue(null, "name");
		String type = reader.getAttributeValue(null, "type");
		PlaceStatistics place = results.getOrCreatePlaceStatistics(type, name);
		int colIndex = 0;

		while (reader.hasNext()) {
			reader.next();

			if (reader.isEndElement() && reader.getName().equals(new QName("observed-element")))
				break;

			if (reader.isStartElement() && reader.getName().equals(new QName("color"))) {
				readColorElement(reader, place, colIndex);
				colIndex++;
			}
			
			if (reader.isStartElement() && reader.getName().equals(new QName("metric"))) {
				String metric = reader.getAttributeValue(null, "type");
				if("tkOcp".equals(metric) || "queueUtilQPl".equals(metric)) {
					place.getOrCreateMetric("tkOcp").addSample(Double.parseDouble(reader.getAttributeValue(null, "value")));
				}
			}
		}
		return place;
	}

	private void readColorElement(XMLStreamReader reader, PlaceStatistics placeStats, int colIndex) throws XMLStreamException {
		String name = reader.getAttributeValue(null, "name");
		if (colIndex == placeStats.getColorCount()) {
			placeStats.addColorStats(new Statistics(name));
		}
		Statistics color = placeStats.getColorStats(colIndex);

		while (reader.hasNext()) {
			reader.next();
			
			if (reader.isEndElement() && reader.getName().equals(new QName("color")))
				break;

			if (reader.isStartElement() && reader.getName().equals(new QName("metric"))) {
				String metric = reader.getAttributeValue(null, "type");
				color.getOrCreateMetric(metric).addSample(Double.parseDouble(reader.getAttributeValue(null, "value")));
			}
		}
	}
}
