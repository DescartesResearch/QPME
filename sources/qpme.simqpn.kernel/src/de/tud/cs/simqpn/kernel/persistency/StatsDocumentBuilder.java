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
 * Original Author(s):  Frederik Zipp
 * Contributor(s): Simon Spinner  
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2009/02/27  Frederik Zipp     Build XML document for simulation results.
 *  2010/08/02  Simon Spinner	  Add probe support.
 * 
 */
package de.tud.cs.simqpn.kernel.persistency;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.SimQPNController;
import de.tud.cs.simqpn.kernel.stats.PlaceStats;
import de.tud.cs.simqpn.kernel.stats.ProbeStats;
import de.tud.cs.simqpn.kernel.stats.QPlaceQueueStats;
import de.tud.cs.simqpn.kernel.stats.QueueStats;
import de.tud.cs.simqpn.kernel.stats.Stats;
import de.tud.cs.simqpn.kernel.stats.TimeHistogram;



/**
* TODO Think about: Change Constructor parameters to Net and SIMQPNConfiguration. This has high impact on whole class
*/
public class StatsDocumentBuilder {

	private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HHmmssS");
	
	private static Logger log = Logger.getLogger(StatsDocumentBuilder.class);
	
	private final Stats[] data;
	private Document doc;
	private final Element net;
	private final String configurationName;


	public StatsDocumentBuilder(Stats[] data, Element net, String configurationName) {
		this.data = data;
		this.net = net;
		this.configurationName = configurationName;
	}

	public Document buildDocument(SimQPNConfiguration configuration) throws SimQPNException {
		this.doc = DocumentFactory.getInstance().createDocument();
		Element root = this.doc.addElement("simqpn-results");
		this.doc.setRootElement(root);
		root.addAttribute("qpme-version", SimQPNController.QPME_VERSION);
		root.addAttribute("model-file", net.attributeValue("path"));
		String timestamp = TIMESTAMP_FORMAT.format(new Date());
		root.addAttribute("date", timestamp);
		root.addAttribute("name", getResultFileBaseName());
		root.addAttribute("configuration-name", this.configurationName);
		for (Stats stats : this.data) {
			if (stats != null) {
				if (!stats.completed) {
					log.error("AggregateStats " + stats.name + " Error: Attempting to access statistics before data collection has finished!");
					throw new SimQPNException();
				}
				addStats(stats, this.doc.getRootElement(), configuration);
			}
		}
		return this.doc;
	}

	public String getResultFileBaseName() {
		return "SimQPN_Output_" + this.configurationName + "_" + TIMESTAMP_FORMAT.format(new Date());
	}
	
	private void addStats(Stats stats, Element parent, SimQPNConfiguration configuration) {
		Element place = parent.addElement("observed-element");
		switch (stats.type) {
		case Stats.ORD_PLACE:
			place.addAttribute("type", "place");
			addOrdinaryPlaceMetrics((PlaceStats) stats, place);
			break;
		case Stats.QUE_PLACE_QUEUE:
			place.addAttribute("type", "qplace:queue");
			addQPlaceQueueMetrics((QPlaceQueueStats) stats, place);
			break;
		case Stats.QUE_PLACE_DEP:
			place.addAttribute("type", "qplace:depository");
			addOrdinaryPlaceMetrics((PlaceStats) stats, place);
			break;
		case Stats.QUEUE:
			place.addAttribute("type", "queue");
			addQueueMetrics((QueueStats) stats, place);
			break;
		case Stats.PROBE:
			place.addAttribute("type", "probe");
			break;
		}
		place.addAttribute("name", stats.name);
		if (stats.type == Stats.QUEUE) {
			place.addAttribute("id", getQueueElement(stats.name).attributeValue("id"));
		} else if (stats.type == Stats.PROBE) {
			place.addAttribute("id", getProbeElement(stats.name).attributeValue("id"));
		} else {
			place.addAttribute("id", getPlaceElement(stats.name).attributeValue("id"));
		}
		place.addAttribute("stats-level", Integer.toString(stats.statsLevel));
		if (stats.colors != null) {
			addColors(stats, place, configuration);
		}
	}

	private void addColors(Stats stats, Element place, SimQPNConfiguration configuration) {
		for (int colorIndex = 0; colorIndex < stats.colors.length; colorIndex++) {
			String colorName = stats.colors[colorIndex];
			Element color = place.addElement("color");
			color.addAttribute("name", colorName);
			color.addAttribute("id", getColorElement(colorName).attributeValue("id"));
			color.addAttribute("real-color", getColorElement(colorName).attributeValue("real-color"));
			switch (stats.type) {
			case Stats.ORD_PLACE:
			case Stats.QUE_PLACE_DEP:
				addOrdinaryPlaceMetrics((PlaceStats) stats, color, colorIndex, configuration);
				break;
			case Stats.QUE_PLACE_QUEUE:
				addQPlaceQueueMetrics((QPlaceQueueStats) stats, color, colorIndex, configuration);
				break;
			case Stats.PROBE:
				addProbeMetrics((ProbeStats) stats, color, colorIndex, configuration);
				break;
			case Stats.QUEUE:
				// nothing
			}
		}
	}

	private void addOrdinaryPlaceMetrics(PlaceStats stats, Element place) {
		if (stats.statsLevel >= 2) {
			addMetric(place, "tkOcp", stats.tkOcp);
		}
	}
	
	private void addOrdinaryPlaceMetrics(PlaceStats stats, Element color, int colorIndex, SimQPNConfiguration configuration) {
		if (stats.statsLevel >= 1) {
			addMetric(color, "arrivThrPut", stats.arrivThrPut[colorIndex]);
			addMetric(color, "deptThrPut", stats.deptThrPut[colorIndex]);
		}
			
		if (stats.statsLevel >= 2) {
			addMetric(color, "minTkPop", stats.minTkPop[colorIndex]);
			addMetric(color, "maxTkPop", stats.maxTkPop[colorIndex]);
			addMetric(color, "meanTkPop", stats.meanTkPop[colorIndex]);
			addMetric(color, "tkColOcp", stats.tkColOcp[colorIndex]);
		}

		if (stats.statsLevel >= 3) {
			addMetric(color, "minST", stats.minST[colorIndex]);
			addMetric(color, "maxST", stats.maxST[colorIndex]);
			addMetric(color, "meanST", stats.meanST[colorIndex]);
			addMetric(color, "stDevST", stats.stDevST[colorIndex]);
			if (configuration.getAnalMethod() == SimQPNConfiguration.AnalysisMethod.BATCH_MEANS
					&& stats.minBatches[colorIndex] > 0
					&& stats.numBatchesST[colorIndex] >= stats.minBatches[colorIndex]) {
				addMetric(color, "stdStateMeanST", stats.stdStateMeanST[colorIndex]);
				addMetric(color, "ciHalfLenST", stats.ciHalfLenST[colorIndex]);
				addMetric(color, "confLevelST", stats.confLevelST[colorIndex]);
			}
		}
		if (stats.statsLevel >= 4) {
			addHistogram(color, "histST", stats.histST[colorIndex]);
		}
	}
	
	private void addProbeMetrics(ProbeStats stats, Element color, int colorIndex, SimQPNConfiguration configuration) {
		if (stats.statsLevel >= 1) {
			addMetric(color, "arrivThrPut", stats.arrivThrPut[colorIndex]);
			addMetric(color, "deptThrPut", stats.deptThrPut[colorIndex]);
		}
			
		if (stats.statsLevel >= 2) {
			addMetric(color, "minTkPop", stats.minTkPop[colorIndex]);
			addMetric(color, "maxTkPop", stats.maxTkPop[colorIndex]);
			addMetric(color, "meanTkPop", stats.meanTkPop[colorIndex]);
			addMetric(color, "tkColOcp", stats.tkColOcp[colorIndex]);
		}

		if (stats.statsLevel >= 3) {
			addMetric(color, "minST", stats.minST[colorIndex]);
			addMetric(color, "maxST", stats.maxST[colorIndex]);
			addMetric(color, "meanST", stats.meanST[colorIndex]);
			addMetric(color, "stDevST", stats.stDevST[colorIndex]);
			if (configuration.getAnalMethod() == SimQPNConfiguration.AnalysisMethod.BATCH_MEANS
					&& stats.minBatches[colorIndex] > 0
					&& stats.numBatchesST[colorIndex] >= stats.minBatches[colorIndex]) {
				addMetric(color, "stdStateMeanST", stats.stdStateMeanST[colorIndex]);
				addMetric(color, "ciHalfLenST", stats.ciHalfLenST[colorIndex]);
				addMetric(color, "confLevelST", stats.confLevelST[colorIndex]);
			}
		}
		if (stats.statsLevel >= 4) {
			addHistogram(color, "histST", stats.histST[colorIndex]);
		}
	}

	private void addQPlaceQueueMetrics(QPlaceQueueStats stats, Element place) {
		if (stats.statsLevel >= 2)
			addMetric(place, "queueUtilQPl", stats.queueUtilQPl);
	}
	
	private void addQPlaceQueueMetrics(QPlaceQueueStats stats, Element color, int colorIndex, SimQPNConfiguration configuration) {
		addOrdinaryPlaceMetrics(stats, color, colorIndex, configuration);
	}

	private void addQueueMetrics(QueueStats stats, Element place) {
		if (stats.statsLevel >= 1) {
			addMetric(place, "totArrivThrPut", stats.totArrivThrPut);
			addMetric(place, "totDeptThrPut", stats.totDeptThrPut);
		}
		if (stats.statsLevel >= 2) {
			addMetric(place, "meanTotTkPop", stats.meanTotTkPop);
			addMetric(place, "queueUtil", stats.queueUtil);
		}
		if (stats.statsLevel >= 3) {
			addMetric(place, "meanST", stats.meanST);
		}
	}

	private void addMetric(Element parent, String type, int value) {
		addMetric(parent, type, Integer.toString(value));
	}

	private void addMetric(Element parent, String type, double value) {
		addMetric(parent, type, Double.toString(value));
	}

	private void addMetric(Element parent, String type, String value) {
		Element metric = parent.addElement("metric");
		metric.addAttribute("type", type);
		metric.addAttribute("value", value);
	}

	private void addHistogram(Element parent, String type, TimeHistogram histogram) {
		Element histogramElement = parent.addElement("histogram");
		histogramElement.addAttribute("type", type);
		histogramElement.addAttribute("bucket-size", Double.toString(histogram.getBucketSize()));
		histogramElement.addAttribute("num-buckets", Integer.toString(histogram.getNumBuckets()));
		Element mean = histogramElement.addElement("mean");
		mean.setText(Double.toString(histogram.getMean()));
		Element percentiles = histogramElement.addElement("percentiles");
		addPercentile(percentiles, 0.25, histogram);
		addPercentile(percentiles, 0.5, histogram);
		addPercentile(percentiles, 0.75, histogram);
		addPercentile(percentiles, 1.0, histogram);
		Element buckets = histogramElement.addElement("buckets");
		for (int i = 0; i < histogram.getData().length; i++) {
			Element bucket = buckets.addElement("bucket");
			bucket.setText(Integer.toString(histogram.getData()[i]));
			bucket.addAttribute("index", Integer.toString(i));
		}
	}

	private void addPercentile(Element parent, double Xpercentile, TimeHistogram histogram) {
		Element percentile = parent.addElement("percentile");
		percentile.setText(Double.toString(histogram.getPercentile(Xpercentile)));
		percentile.addAttribute("for", Double.toString(Xpercentile));
	}

	private Element getColorElement(String colorName) {
		XPath xpathSelector = DocumentHelper.createXPath("/net/colors/color[@name = '" + colorName + "']");
		return (Element) xpathSelector.selectSingleNode(this.net);
	}

	private Element getPlaceElement(String placeName) {
		XPath xpathSelector = DocumentHelper.createXPath("/net/places/place[@name = '" + placeName + "']");
		return (Element) xpathSelector.selectSingleNode(this.net);
	}

	private Element getQueueElement(String queueName) {
		XPath xpathSelector = DocumentHelper.createXPath("/net/queues/queue[@name = '" + queueName + "']");
		return (Element) xpathSelector.selectSingleNode(this.net);
	}
	
	private Element getProbeElement(String probeName) {
		XPath xpathSelector = DocumentHelper.createXPath("/net/probes/probe[@name = '" + probeName + "']");
		return (Element) xpathSelector.selectSingleNode(this.net);
	}
}
