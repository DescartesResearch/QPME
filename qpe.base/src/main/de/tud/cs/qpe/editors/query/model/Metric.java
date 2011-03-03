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

/**
 * These are the available metrics for visualization.
 * 
 * Each metric has a human readable name and and an abbreviated type name
 * by which it is identified for example inside an XML document.
 * 
 *  @author Frederik Zipp
 */
public enum Metric {
	QUEUE_UTIL_QP1("Queue utilization due to this place", "queueUtilQPl", true),
	TK_OCP("Token Occupancy", "tkOcp", true),
	ARRIV_THR_PUT("Arrival Throughput", "arrivThrPut"),
	DEPT_THR_PUT("Departure Throughput", "deptThrPut"),
	MIN_TK_POP("Minimum Token Population", "minTkPop"),
	MAX_TK_POP("Maximum Token Population", "maxTkPop"),
	MEAN_TK_POP("Mean Token Population", "meanTkPop"),
	TK_COL_OCP("Token Color Occupancy", "tkColOcp"),
	MEAN_ST("Mean Token Residence Time", "meanST"),
	ST_DEV_ST("Standard Deviation Token Residence Time", "stDevST"),
	MIN_ST("Minimum Token Residence Time", "minST"),
	MAX_ST("Maximum Token Residence Time", "maxST"),
	CONFIDENCE_INTERVAL_MEAN("Confidence Interval Mean", "stdStateMeanST"),
	CONFIDENCE_INTERVAL_LENGTH("Confidence Interval Half Length", "ciHalfLenST"),
	TOT_ARRIV_THR_PUT("Total Arrival Throughput", "totArrivThrPut", true),
	TOT_DEPT_THR_PUT("Total Departure Throughput", "totDeptThrPut", true),
	MEAN_TOT_TK_POP("Mean Total Token Population", "meanTotTkPop", true),
	QUEUE_UTIL("Queue Utilization", "queueUtil", true);
	
	private final String name;
	private final String type;
	private final boolean direct;

	private Metric(String name, String id) {
		this(name, id, false);
	}

	private Metric(String name, String id, boolean direct) {
		this.name = name;
		this.type = id;
		this.direct = direct;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getType() {
		return this.type;
	}
	
	public boolean isDirect() {
		return this.direct;
	}
	
	public static Metric fromType(String type) {
		for (Metric metric : values()) {
			if (metric.type.equals(type)) {
				return metric;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
