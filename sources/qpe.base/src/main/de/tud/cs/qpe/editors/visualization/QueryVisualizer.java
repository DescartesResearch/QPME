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
 * Original Author(s):  Frederik Zipp and Samuel Kounev
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2009/03/27  Frederik Zipp     Created.
 * 
 */
package de.tud.cs.qpe.editors.visualization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.draw2d.geometry.Dimension;

import de.tud.cs.qpe.editors.query.model.Aggregation;
import de.tud.cs.qpe.editors.query.model.Color;
import de.tud.cs.qpe.editors.query.model.Histogram;
import de.tud.cs.qpe.editors.query.model.HistogramType;
import de.tud.cs.qpe.editors.query.model.MetricQuery;
import de.tud.cs.qpe.editors.query.model.MetricValue;
import de.tud.cs.qpe.editors.query.model.Place;
import de.tud.cs.qpe.editors.query.model.Query;
import de.tud.cs.qpe.editors.query.model.SimulationResults;

public class QueryVisualizer {
	
	private final SimulationResults simulationResults;

	public QueryVisualizer(SimulationResults simulationResults) {
		this.simulationResults = simulationResults;
	}
	
	public List<ChartGroup> visualize(Query<Place, Color> query) {
		List<ChartGroup> chartGroups = new ArrayList<ChartGroup>();
		for (MetricQuery metricQuery : query.getMetricQueries()) {
			if (metricQuery.getMetric().isDirect()) {
				chartGroups.add(visualizePlaceMetric(query, metricQuery));
			} else {
				if (metricQuery.getAggregationA() != Aggregation.FOR_EACH
						&& metricQuery.getAggregationB() == Aggregation.FOR_EACH) {
					chartGroups.add(visualizeAggregationForeach(query, metricQuery)); 
				}
				if (metricQuery.getAggregationA() == Aggregation.FOR_EACH
						&& metricQuery.getAggregationB() != Aggregation.FOR_EACH) {
					chartGroups.add(visualizeForeachAggregation(query, metricQuery)); 
				}
				if (metricQuery.getAggregationA() != Aggregation.FOR_EACH
						&& metricQuery.getAggregationB() != Aggregation.FOR_EACH) {
					chartGroups.add(visualizeAggregationAggregation(query, metricQuery)); 
				}
				if (metricQuery.getAggregationA() == Aggregation.FOR_EACH
						&& metricQuery.getAggregationB() == Aggregation.FOR_EACH) {
					chartGroups.add(visualizeForeachForeach(query, metricQuery)); 
				}
			}
		}
		return chartGroups;
	}
	
	private ChartGroup visualizePlaceMetric(Query<Place, Color> query,
			MetricQuery metricQuery) {
		ChartGroup charts = new ChartGroup(metricQuery.getTitle());
		Map<Place, MetricValue> values = new HashMap<Place, MetricValue>();
		for (Place place : query.getFilterSetA()) {
			MetricValue metricValue = simulationResults.getMetricValue(place, metricQuery.getMetric());
			if (metricValue != null) {
				values.put(place, metricValue);
			}
		}
		VisualizationComponent visu = metricQuery.getVisualizationB().createChart(metricQuery.getMetric().getName(), values);
		if (visu != null) {
			charts.add(new SizedVisualizationComponent(visu, metricQuery.getSize()));
		}
		return charts;
	}

	private ChartGroup visualizeAggregationForeach(Query<Place, Color> query, MetricQuery metricQuery) {
		ChartGroup charts = new ChartGroup(metricQuery.getTitle());
		Map<Color, MetricValue> aggregatedValues = new HashMap<Color, MetricValue>();
		for (Color color : query.getFilterSetB()) {
			List<MetricValue> values = new ArrayList<MetricValue>();
			for (Place place : query.getFilterSetA()) {
				MetricValue metricValue = simulationResults.getMetricValue(place, color, metricQuery.getMetric());
				if (metricValue != null) {
					values.add(metricValue);
				}
			}
			MetricValue aggregatedValue = metricQuery.getAggregationA().aggregate(values);
			if (aggregatedValue != null) {
				aggregatedValues.put(color, aggregatedValue);
			}
		}
		VisualizationComponent visu = metricQuery.getVisualizationB().createChart(metricQuery.getMetric().getName(), aggregatedValues);
		if (visu != null) {
			charts.add(new SizedVisualizationComponent(visu, metricQuery.getSize()));
		}
		return charts;
	}

	private ChartGroup visualizeForeachAggregation(Query<Place, Color> query, MetricQuery metricQuery) {
		ChartGroup charts = new ChartGroup(metricQuery.getTitle());
		Map<Place, MetricValue> aggregatedValues = new HashMap<Place, MetricValue>();
		for (Place place : query.getFilterSetA()) {
			List<MetricValue> values = new ArrayList<MetricValue>();
			for (Color color : query.getFilterSetB()) {
				MetricValue metricValue = simulationResults.getMetricValue(place, color, metricQuery.getMetric());
				if (metricValue != null) {
					values.add(metricValue);
				}
			}
			MetricValue aggregatedValue = metricQuery.getAggregationB().aggregate(values);
			if (aggregatedValue != null) {
				aggregatedValues.put(place, aggregatedValue);
			}
		}
		VisualizationComponent visu = metricQuery.getVisualizationA().createChart(metricQuery.getMetric().getName(), aggregatedValues);
		if (visu != null) {
			charts.add(new SizedVisualizationComponent(visu, metricQuery.getSize()));
		}
		return charts;
	}

	private ChartGroup visualizeAggregationAggregation(Query<Place, Color> query, MetricQuery metricQuery) {
		ChartGroup charts = new ChartGroup(metricQuery.getTitle());
		List<MetricValue> aggregatedValues = new ArrayList<MetricValue>();
		for (Color color : query.getFilterSetB()) {
			List<MetricValue> values = new ArrayList<MetricValue>();
			for (Place place : query.getFilterSetA()) {
				MetricValue metricValue = simulationResults.getMetricValue(place, color, metricQuery.getMetric());
				if (metricValue != null) {
					values.add(metricValue);
				}
			}
			MetricValue aggregatedValue = metricQuery.getAggregationA().aggregate(values);
			if (aggregatedValue != null) {
				aggregatedValues.add(aggregatedValue);
			}
		}
		MetricValue aggregatedValue = metricQuery.getAggregationB().aggregate(aggregatedValues);
		if (aggregatedValue != null) {
			Map<Object, MetricValue> data = new HashMap<Object, MetricValue>();
			data.put("Value", aggregatedValue);
			VisualizationComponent visu = new BarChartVisualization().createChart(metricQuery.getMetric().getName(), data);
			if (visu != null) {
				charts.add(new SizedVisualizationComponent(visu, metricQuery.getSize()));
			}
		}
		return charts;
	}

	private ChartGroup visualizeForeachForeach(Query<Place, Color> query, MetricQuery metricQuery) {
		ChartGroup charts = new ChartGroup(metricQuery.getTitle());
		for (Place place : query.getFilterSetA()) {
			Map<Color, MetricValue> values = new HashMap<Color, MetricValue>();
			for (Color color : query.getFilterSetB()) {
				MetricValue metricValue = simulationResults.getMetricValue(place, color, metricQuery.getMetric());
				if (metricValue != null) {
					values.put(color, metricValue);
				}
			}
			VisualizationComponent visu = metricQuery.getVisualizationA().createChart(metricQuery.getMetric().getName() + " for " + place, values);
			if (visu != null) {
				charts.add(new SizedVisualizationComponent(visu, metricQuery.getSize()));
			}
		}
		for (Color color : query.getFilterSetB()) {
			Map<Place, MetricValue> values = new HashMap<Place, MetricValue>();
			for (Place place : query.getFilterSetA()) {
				MetricValue metricValue = simulationResults.getMetricValue(place, color, metricQuery.getMetric());
				if (metricValue != null) {
					values.put(place, metricValue);
				}
			}
			VisualizationComponent visu = metricQuery.getVisualizationB().createChart(metricQuery.getMetric().getName() + " for Color " + color, values);
			if (visu != null) {
				charts.add(new SizedVisualizationComponent(visu, metricQuery.getSize()));
			}
		}
		return charts;
	}

	public List<ChartGroup> visualizeHistograms(HistogramType histogramType, Set<Place> places, Set<Color> colors, Dimension size) {
		ChartGroup charts = new ChartGroup("");
		for (Place place : places) {
			for (Color color : colors) {
				Histogram histogram = simulationResults.getHistogram(place, color, histogramType);
				String title = histogramType.getName() + " (" + place.getName() + ", " + color.getName() + ")";
				VisualizationComponent visu = new HistogramVisualization().createChart(title, histogram);
				if (visu != null) {
					charts.add(new SizedVisualizationComponent(visu, size));
				}
			}
		}
		
		List<ChartGroup> chartGroups = new ArrayList<ChartGroup>();
		chartGroups.add(charts);
		return chartGroups;
	}
}