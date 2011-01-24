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
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import de.tud.cs.simqpn.rt.framework.results.Metric;
import de.tud.cs.simqpn.rt.framework.results.SimulationResults;
import de.tud.cs.simqpn.rt.framework.results.Statistics;
import de.tud.cs.simqpn.rt.framework.results.Statistics.ElementType;

/**
 * Reads the simulation results from a *.simqpn file and stores them in a results
 * container.
 * 
 * @author Simon Spinner
 * 
 */
public class SimQPNResultFileImport {
	
	// Percentiles of a histogram that are used for comparison
	private static final double[] histogrammPercentiles = new double[] { 0.1,
			0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9 };

	/**
	 * Start import of *.simqpn file.
	 * @param resultFile - The *.simpqn file to import.
	 * @param results - The result container where to store the read values.
	 * @throws FileNotFoundException
	 * @throws XMLStreamException
	 */
	public void execute(File resultFile, SimulationResults results)
			throws FileNotFoundException, XMLStreamException {

		// Init StAX reader
		XMLInputFactory inFactory = XMLInputFactory.newInstance();
		XMLStreamReader reader = inFactory
				.createXMLStreamReader(new FileInputStream(resultFile));

		while (reader.hasNext()) {
			reader.next();

			if (reader.isStartElement()) {
				//<observed-element>
				if (reader.getName().equals(new QName("observed-element"))) {
					if ("queue".equals(reader.getAttributeValue(null, "type"))) {
						readQueueElement(reader, results);
					} else {
						readPlaceElement(reader, results);
					}
				}
			}
		}
	}

	private void readQueueElement(XMLStreamReader reader,
			SimulationResults results) throws XMLStreamException {
		String name = reader.getAttributeValue(null, "name");
		Statistics queue = results.getOrCreateStatistics(name,
				ElementType.QUEUE);

		while (reader.hasNext()) {
			reader.next();

			//</observed-element>
			if (reader.isEndElement()
					&& reader.getName().equals(new QName("observed-element")))
				break;

			if (reader.isStartElement()) {
				//<metric>
				if (reader.getName().equals(new QName("metric"))) {
					String metric = reader.getAttributeValue(null, "type");
					queue.getOrCreateMetric(metric).addSample(
							Double.parseDouble(reader.getAttributeValue(null,
									"value")));
				}
			}
		}
	}

	private void readHistogramElement(XMLStreamReader reader, Statistics stats)
			throws XMLStreamException {
		int bucketCount = Integer.parseInt(reader.getAttributeValue(null,
				"num-buckets"));
		double bucketSize = Double.parseDouble(reader.getAttributeValue(null,
				"bucket-size"));
		int[] data = new int[bucketCount]; // Array for all buckets of histogram

		while (reader.hasNext()) {
			reader.next();

			//</histogram>
			if (reader.isEndElement()
					&& reader.getName().equals(new QName("histogram")))
				break;

			//<bucket>
			if (reader.isStartElement()
					&& reader.getName().equals(new QName("bucket"))) {
				int index = Integer.parseInt(reader.getAttributeValue(null,
						"index"));
				data[index] = Integer.parseInt(reader.getElementText());
			}
		}

		// Derive the percentiles of the bucket data
		for (double Xpercentile : histogrammPercentiles) {
			int sum = 0;
			int i;
			for (i = 0; i < bucketCount; i++)
				sum += data[i];
			double totalSumPercentile = (double) (Xpercentile * sum);
			if (totalSumPercentile == 0) {
				stats.getOrCreateMetric(
						Metric.HIST_ST + ":" + Xpercentile).addSample(0);
				continue;
			}
			sum = 0;
			for (i = 0; i < bucketCount && sum < totalSumPercentile; i++)
				sum += data[i];
			
			// Create a separate metric for each percentile
			stats.getOrCreateMetric(Metric.HIST_ST + ":" + Xpercentile)
					.addSample(i * bucketSize);
		}
	}

	private void readPlaceElement(XMLStreamReader reader,
			SimulationResults results) throws XMLStreamException {
		String name = reader.getAttributeValue(null, "name");
		String type = reader.getAttributeValue(null, "type");
		Statistics place = results.getOrCreateStatistics(name,
				ElementType.parseType(type));
		int colIndex = 0;

		while (reader.hasNext()) {
			reader.next();

			//</observed-element>
			if (reader.isEndElement()
					&& reader.getName().equals(new QName("observed-element")))
				break;

			//<color>
			if (reader.isStartElement()
					&& reader.getName().equals(new QName("color"))) {
				readColorElement(reader, place, colIndex);
				colIndex++;
			}

			//<metric>
			if (reader.isStartElement()) {
				if (reader.getName().equals(new QName("metric"))) {
					String metric = reader.getAttributeValue(null, "type");
					metric = TestConfig.getInstance()
							.applySubstitutions(metric);
					place.getOrCreateMetric(metric).addSample(
							Double.parseDouble(reader.getAttributeValue(null,
									"value")));
				}
			}
		}
	}

	private void readColorElement(XMLStreamReader reader,
			Statistics placeStats, int colIndex) throws XMLStreamException {
		String name = reader.getAttributeValue(null, "name");
		if (colIndex == placeStats.getChildStats().size()) {
			placeStats.addChildStats(new Statistics(name, ElementType.COLOR));
		}
		Statistics color = placeStats.getChildStats().get(colIndex);

		while (reader.hasNext()) {
			reader.next();

			//</color>
			if (reader.isEndElement()
					&& reader.getName().equals(new QName("color")))
				break;

			if (reader.isStartElement()) {
				if (reader.getName().equals(new QName("metric"))) {
					//<metric>
					String metric = reader.getAttributeValue(null, "type");
					metric = TestConfig.getInstance()
							.applySubstitutions(metric);
					color.getOrCreateMetric(metric).addSample(
							Double.parseDouble(reader.getAttributeValue(null,
									"value")));
				} else if (reader.getName().equals(new QName("histogram"))) {
					//<histogram>
					readHistogramElement(reader, color);
				}
			}
		}
	}
}
