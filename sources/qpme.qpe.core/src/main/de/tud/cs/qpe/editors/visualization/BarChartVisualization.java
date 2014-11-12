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

import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import de.tud.cs.qpe.editors.query.model.MetricValue;

public class BarChartVisualization extends ChartVisualization {
	
	private static CategoryDataset createDataset(Map<? extends Object, MetricValue> aggregatedValues) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (Object key : aggregatedValues.keySet()) {
			dataset.setValue(new Double(aggregatedValues.get(key).getValue()), key.toString(), "");
		}
		return dataset;
	}
	
	private static JFreeChart createChart(String title, CategoryDataset dataset) {

		JFreeChart chart = ChartFactory.createBarChart(title, "", "", dataset, PlotOrientation.HORIZONTAL, true, true, false);

		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setNoDataMessage("No data available");
		return chart;
	}

	@Override
	public VisualizationComponent createChart(String title, Map<? extends Object, MetricValue> aggregatedValues) {
		return new ChartVisualizationComponent(createChart(title, createDataset(aggregatedValues)));
	}

	@Override
	public String getId() {
		return "bar-chart";
	}

	@Override
	public String getName() {
		return "Bar Chart";
	}
}
