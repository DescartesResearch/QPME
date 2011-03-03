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

import java.util.List;
import java.util.Set;

public class Query<A, B> {

	private final Set<A> filterSetA;
	private final Set<B> filterSetB;
	private List<MetricQuery> metricQueries;
	
	public Query(Set<A> filterSetA, Set<B> filterSetB, List<MetricQuery> metricQueries) {
		this.filterSetA = filterSetA;
		this.filterSetB = filterSetB;
		this.metricQueries = metricQueries;
	}
	
	public Set<A> getFilterSetA() {
		return this.filterSetA;
	}
	
	public Set<B> getFilterSetB() {
		return this.filterSetB;
	}
	
	public List<MetricQuery> getMetricQueries() {
		return this.metricQueries;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		builder.append(getFilterSetA());
		builder.append(", ");
		builder.append(getFilterSetB());
		builder.append(", ");
		builder.append(getMetricQueries());
		builder.append(']');
		return builder.toString();
	}
}
