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
 * Original Author(s):  Simon Spinner
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ----------------------------------------------------------------------------------
 *  2011/01/21  Simon Spinner     Created.         
 * 
 */

package de.tud.cs.simqpn.rt.framework.results;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Statistics {
	
	public enum ElementType {
		
		ORDINARY_PLACE("place"),                
		QPLACE_DEPOSITORY("qplace:depository"),
		QPLACE_QUEUE("qplace:queue"),
		QUEUE("queue"),
		PROBE("probe"),
		COLOR("color");                         
		
		private final String identifier;
		
		private ElementType(String identifier) {
			this.identifier = identifier;
		}
		
		@Override
		public String toString() {
			return identifier;
		}
		
		public static ElementType parseType(String s) {
			if (s.equals(ORDINARY_PLACE.toString())) {
				return ORDINARY_PLACE;
			} else if (s.equals(QPLACE_DEPOSITORY.toString())) {
				return QPLACE_DEPOSITORY;
			} else if (s.equals(QPLACE_QUEUE.toString())) {
				return QPLACE_QUEUE;
			} else if (s.equals(QUEUE.toString())) {
				return QUEUE;
			} else if (s.equals(PROBE.toString())) {
				return PROBE;
			} else if (s.equals(COLOR.toString())) {
				return COLOR;
			}
			return null;
		}
	}
	

	private String name;
	private ElementType type;
	private Map<String, Metric> metrics;
	private List<Statistics> children;
	
	public Statistics(String name, ElementType type) {
		this.name = name;
		this.type = type;
		metrics = new HashMap<String, Metric>();
		children = new ArrayList<Statistics>();
	}

	public String getName() {
		return name;
	}
	
	public ElementType getType() {
		return type;
	}
	
	public void addMetric(Metric metric) {
		metrics.put(metric.getName(), metric);
	}
	
	public Metric getMetric(String name) {
		return metrics.get(name);
	}
	
	public Metric getOrCreateMetric(String name) {
		Metric metric = metrics.get(name);
		if (metric == null) {
			metric = new Metric(name);
			metrics.put(name, metric);
		}
		return metric;
	}
	
	public Collection<Metric> getMetrics() {
		return metrics.values();
	}
	
	public void addChildStats(Statistics stats) {
		children.add(stats);
	}
	
	public List<Statistics> getChildStats() {
		return children;
	}
	
	public Statistics findChildStats(String name, ElementType type) {
		for (int i = 0; i < children.size(); i++) {
			Statistics cur = children.get(i);
			if (cur.getName().equals(name) && (cur.getType() == type)) {
				return cur;
			}
		}
		return null;
	}

	public static String getId(String name, ElementType type) {
		return type + ":" + name;
	}
	
}
