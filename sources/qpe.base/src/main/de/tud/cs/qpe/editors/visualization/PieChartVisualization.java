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

import java.text.AttributedString;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import de.tud.cs.qpe.editors.query.model.MetricValue;

public class PieChartVisualization extends ChartVisualization {
	
	private static PieDataset createDataset(Map<? extends Object, MetricValue> aggregatedValues) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (Object key : aggregatedValues.keySet()) {
			dataset.setValue(key.toString(), new Double(aggregatedValues.get(key).getValue()));
		}
		return dataset;
	}
	
	private static JFreeChart createChart(String title, PieDataset dataset) {

		JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, false);

		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setSectionOutlinesVisible(false);
		plot.setNoDataMessage("No data available");
		plot.setCircular(false);
//		plot.setLabelGenerator(new NoLabelGenerator());
		return chart;
	}


	@Override
	public VisualizationComponent createChart(String title, Map<? extends Object, MetricValue> aggregatedValues) {
		return new ChartVisualizationComponent(createChart(title, createDataset(aggregatedValues)));
	}

	@Override
	public String getId() {
		return "pie-chart";
	}

	@Override
	public String getName() {
		return "Pie Chart";
	}
	
	@SuppressWarnings("unused")
	private static class NoLabelGenerator implements PieSectionLabelGenerator {

		@Override
		@SuppressWarnings("unchecked")
		public AttributedString generateAttributedSectionLabel(PieDataset arg0, Comparable arg1) {
			return null;
		}

		@Override
		@SuppressWarnings("unchecked")
		public String generateSectionLabel(PieDataset arg0, Comparable arg1) {
			return null;
		}
	}
}
