package de.tud.cs.qpe.editors.query.model;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Element;


public class SimulationResults {

	private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HHmmssS");

	private File modelFile;
	private Date date;
	private String name;
	private String configurationName;
	private List<Place> places;
	
	public SimulationResults(Element data) {
		this.modelFile = new File(data.attributeValue("model-file"));
		try {
			this.date = TIMESTAMP_FORMAT.parse(data.attributeValue("date"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.name = data.attributeValue("name");
		this.configurationName = data.attributeValue("configuration-name");
		parsePlaces(data);
	}
	
	@SuppressWarnings("unchecked")
	private void parsePlaces(Element data) {
		this.places = new ArrayList<Place>();
		List<Element> placeNodes = data.elements("observed-element");
		for (Element placeNode : placeNodes) {
			this.places.add(parsePlace(placeNode));
		}
	}

	private Place parsePlace(Element placeNode) {
		String name = placeNode.attributeValue("name");
		String id = placeNode.attributeValue("id");
		String type = placeNode.attributeValue("type");
		int statsLevel = Integer.parseInt(placeNode.attributeValue("stats-level"));
		Map<Metric, MetricValue> metricValues = parseMetricValues(placeNode);
		Map<Color, Map<Metric, MetricValue>> colorMetricValues = parseColorMetricValues(placeNode);
		Map<Color, Map<HistogramType, Histogram>> colorHistograms = parseColorHistograms(placeNode);
		return new Place(name, id, type, statsLevel, metricValues, colorMetricValues, colorHistograms);
	}

	@SuppressWarnings("unchecked")
	private Map<Color, Map<HistogramType, Histogram>> parseColorHistograms(Element placeNode) {
		Map<Color, Map<HistogramType, Histogram>> colorHistograms = new HashMap<Color, Map<HistogramType,Histogram>>();
		List<Element> colorNodes = placeNode.elements("color");
		for (Element colorNode : colorNodes) {
			colorHistograms.put(parseColor(colorNode), parseHistograms(colorNode));
		}
		return colorHistograms;
	}

	@SuppressWarnings("unchecked")
	private Map<HistogramType, Histogram> parseHistograms(Element node) {
		Map<HistogramType, Histogram> histograms = new HashMap<HistogramType, Histogram>();
		List<Element> histogramNodes = node.elements("histogram");
		for (Element histogramNode : histogramNodes) {
			Histogram histogram = new Histogram(histogramNode);
			if (histogram.getType() != null) {
				histograms.put(histogram.getType(), histogram);
			}
		}
		return histograms;
	}

	@SuppressWarnings("unchecked")
	private Map<Color, Map<Metric, MetricValue>> parseColorMetricValues(Element placeNode) {
		Map<Color, Map<Metric, MetricValue>> colorMetricValues = new HashMap<Color, Map<Metric, MetricValue>>();
		List<Element> colorNodes = placeNode.elements("color");
		for (Element colorNode : colorNodes) {
			colorMetricValues.put(parseColor(colorNode), parseMetricValues(colorNode));
		}
		return colorMetricValues;
	}

	private Color parseColor(Element colorNode) {
		return new Color(colorNode.attributeValue("name"), colorNode.attributeValue("id"), colorNode.attributeValue("real-color"));
	}

	@SuppressWarnings("unchecked")
	private Map<Metric, MetricValue> parseMetricValues(Element node) {
		Map<Metric, MetricValue> metricValues = new HashMap<Metric, MetricValue>();
		List<Element> metricValueNodes = node.elements("metric");
		for (Element metricValueNode : metricValueNodes) {
			MetricValue metricValue = parseMetricValue(metricValueNode);
			if (metricValue.getMetric() != null) {
				metricValues.put(metricValue.getMetric(), metricValue);
			}
		}
		return metricValues;
	}

	private MetricValue parseMetricValue(Element metricValueNode) {
		Metric metric = Metric.fromType(metricValueNode.attributeValue("type"));
		double value = 0.0;
		try {
			value = Double.parseDouble(metricValueNode.attributeValue("value"));
		} catch (NumberFormatException e) {
//			e.printStackTrace();
		}
		return new MetricValue(metric, value);
	}

	public File getModelFile() {
		return this.modelFile;
	}

	public Date getDate() {
		return this.date;
	}

	public String getName() {
		return this.name;
	}

	public String getConfigurationName() {
		return this.configurationName;
	}

	public Place[] getPlaces() {
		Set<Place> placeSet = new HashSet<Place>(this.places);
		Place[] result = placeSet.toArray(new Place[placeSet.size()]);
		Arrays.sort(result);
		return result;
	}

	public Place[] getPlacesFiltered(String[] types) {
		Set<Place> placeSet = new HashSet<Place>();
		for (Place place : this.places) {
			if (inArray(types, place.getType())) {
				placeSet.add(place);
			}
		}
		Place[] result = placeSet.toArray(new Place[placeSet.size()]);
		Arrays.sort(result);
		return result;
	}
	
	private boolean inArray(String[] types, String type) {
		for (String item : types) {
			if (type.equals(item)) {
				return true;
			}
		}
		return false;
	}

	public Color[] getColors() {
		Set<Color> colorSet = new HashSet<Color>();
		for (Place place : this.places) {
			for (Color color : place.getColors()) {
				colorSet.add(color);
			}
		}
		Color[] result = colorSet.toArray(new Color[colorSet.size()]);
		Arrays.sort(result);
		return result;
	}
	
	public MetricValue getMetricValue(Place place, Color color, Metric metric) {
		return place.getMetricValue(color, metric);
	}

	public MetricValue getMetricValue(Place place, Metric metric) {
		return place.getMetricValue(metric);
	}

	public Histogram getHistogram(Place place, Color color, HistogramType type) {
		return place.getHistogram(color, type);
	}

	public Metric[] getAvailableColorMetrics() {
		return getAvailableColorMetrics(this.places);
	}
	
	public Metric[] getAvailableColorMetrics(Collection<Place> places) {
		Set<Metric> metrics = new HashSet<Metric>();
		for (Place place : places) {
			for (Color color : place.getColors()) {
				Map<Metric, MetricValue> metricValues = place.getMetricValues(color);
				if (metricValues != null && metricValues.keySet() != null) {
					metrics.addAll(metricValues.keySet());
				}
			}
		}
		return metrics.toArray(new Metric[metrics.size()]);
	}


	public Metric[] getAvailablePlaceMetrics() {
		return getAvailablePlaceMetrics(this.places);
	}
	
	public Metric[] getAvailablePlaceMetrics(Collection<Place> places) {
		Set<Metric> metrics = new HashSet<Metric>();
		for (Place place : places) {
			Map<Metric, MetricValue> metricValues = place.getMetricValues();
			if (metricValues != null && metricValues.keySet() != null) {
				metrics.addAll(metricValues.keySet());
			}
		}
		return metrics.toArray(new Metric[metrics.size()]);
	}
	
	public Metric[] getAllAvailableMetrics() {
		Set<Metric> metrics = new HashSet<Metric>();
		for (Place place : this.places) {
			Map<Metric, MetricValue> metricValues = place.getMetricValues();
			if (metricValues != null && metricValues.keySet() != null) {
				metrics.addAll(metricValues.keySet());
			}
			for (Color color : place.getColors()) {
				metricValues = place.getMetricValues(color);
				if (metricValues != null && metricValues.keySet() != null) {
					metrics.addAll(metricValues.keySet());
				}
			}
		}
		return metrics.toArray(new Metric[metrics.size()]);
	}

	public HistogramType[] getAvailableHistogramTypes() {
		Set<HistogramType> types = new HashSet<HistogramType>();
		for (Place place : this.places) {
			for (Color color : place.getColors()) {
				Map<HistogramType, Histogram> histograms = place.getHistograms(color);
				if (histograms != null && histograms.keySet() != null) {
					types.addAll(histograms.keySet());
				}
			}
		}
		return types.toArray(new HistogramType[types.size()]);
	}
}
