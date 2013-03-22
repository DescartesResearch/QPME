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
 *  2009/02/27  Frederik Zipp     Created.
 * 
 */
package de.tud.cs.qpe.editors.query.model;

import java.util.List;

/**
 * Represents an aggregation method for metric values.
 * 
 * @author Frederik Zipp
 *
 */
public enum Aggregation {
	FOR_EACH("For each") {
		@Override
		public MetricValue aggregate(List<MetricValue> values) {
			throw new UnsupportedOperationException();
		}
	},
	AVERAGE("Average") {
		@Override
		public MetricValue aggregate(List<MetricValue> values) {
			if (values.isEmpty()) {
				return null;
			}
			return SUM.aggregate(values).divide(values.size());
		}
	},
	SUM("Sum") {
		@Override
		public MetricValue aggregate(List<MetricValue> values) {
			if (values.isEmpty() || values == null) {
				return null;
			}
			MetricValue sum = new MetricValue(values.get(0).getMetric(), 0);
			for (MetricValue value : values) {
				sum = sum.plus(value);
			}
			return sum;
		}
	};
	
	private final String name;

	/**
	 * Creates a new aggregation.
	 * 
	 * @param name	a human readable name of the aggregation
	 */
	private Aggregation(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the name of the aggregation.
	 * 
	 * @return	The name of the aggregation
	 */
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return getName();
	}

	/**
	 * Aggregates multiple metric values to a single metric value. 
	 * 
	 * @param values	the metric values to be aggregated
	 * @return			the aggregated metric value,
	 * 					null if 'values' is empty or null
	 */
	public abstract MetricValue aggregate(List<MetricValue> values);
}
