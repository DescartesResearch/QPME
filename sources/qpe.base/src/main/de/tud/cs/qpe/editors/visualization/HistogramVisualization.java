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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.SimpleHistogramBin;
import org.jfree.data.statistics.SimpleHistogramDataset;
import org.jfree.experimental.chart.swt.ChartComposite;

import de.tud.cs.qpe.editors.query.model.Histogram;

public class HistogramVisualization {

	private static SimpleHistogramDataset createDataset(Histogram histogram, String title) {
		SimpleHistogramDataset dataset = new SimpleHistogramDataset(title);
		double bucketSize = histogram.getBucketSize();
		for (int i = 0; i < histogram.getNumBuckets(); i++) {
			double lowerBound = i * bucketSize;
			double upperBound = lowerBound + bucketSize;
			SimpleHistogramBin bin = new SimpleHistogramBin(lowerBound, upperBound, true, false);
			bin.setItemCount(histogram.getBucketCount(i));
			dataset.addBin(bin);
		}
		return dataset;
	}

	public VisualizationComponent createChart(String title, Histogram histogram) {
        JFreeChart chart = ChartFactory.createHistogram(
                title, 
                null, 
                null, 
                createDataset(histogram, title), 
                PlotOrientation.VERTICAL, 
                true, 
                false, 
                false
            );
            chart.getXYPlot().setForegroundAlpha(0.75f);
		return new HistogramVisualizationComponent(chart);
	}
	
	private class HistogramVisualizationComponent implements VisualizationComponent {

		private final JFreeChart chart;

		public HistogramVisualizationComponent(JFreeChart chart) {
			this.chart = chart;
		}
		
		@Override
		public Composite realize(Composite parent) {
			return new ChartComposite(parent, SWT.NONE, this.chart, true);
		}
	}
}
