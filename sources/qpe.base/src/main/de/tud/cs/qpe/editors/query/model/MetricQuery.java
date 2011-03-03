/* ==============================================
 * QPME : Queueing Petri net Modeling Environment
 * ==============================================
 *
 * (c) Copyright 2003-2010, by Samuel Kounev and Contributors.
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
 *  2009/02/27  Frederik Zipp     Created.
 * 
 */
package de.tud.cs.qpe.editors.query.model;

import org.eclipse.draw2d.geometry.Dimension;

import de.tud.cs.qpe.editors.visualization.Visualization;

public class MetricQuery {

	private final Metric metric;
	private final Aggregation aggregationA;
	private final Aggregation aggregationB;
	private final Visualization visualizationA;
	private final Visualization visualizationB;
	private final String title;
	private final Dimension size;

	public MetricQuery(String title, Metric metric, Aggregation aggregationA, Aggregation aggregationB, Visualization visualizationA, Visualization visualizationB, Dimension size) {
		this.title = title;
		this.metric = metric;
		this.aggregationA = aggregationA;
		this.aggregationB = aggregationB;
		this.visualizationA = visualizationA;
		this.visualizationB = visualizationB;
		this.size = size;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public Metric getMetric() {
		return this.metric;
	}
	
	public Aggregation getAggregationA() {
		return this.aggregationA;
	}
	
	public Aggregation getAggregationB() {
		return this.aggregationB;
	}

	public Visualization getVisualizationA() {
		return this.visualizationA;
	}
	
	public Visualization getVisualizationB() {
		return this.visualizationB;
	}

	public Dimension getSize() {
		return this.size;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append('(');
		builder.append(this.title);
		builder.append(", ");
		builder.append(this.metric);
		builder.append(", ");
		builder.append(this.aggregationA);
		builder.append(", ");
		builder.append(this.aggregationB);
		builder.append(", ");
		builder.append(this.visualizationA);
		builder.append(", ");
		builder.append(this.visualizationB);
		builder.append(", ");
		builder.append(this.size);
		builder.append(')');
		return builder.toString();
	}
}
