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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;

import de.tud.cs.qpe.editors.query.model.MetricValue;

public class TableVisualization extends Visualization {

	private final NumberFormat FORMAT = new DecimalFormat();
	
	@Override
	public VisualizationComponent createChart(String title, Map<? extends Object, MetricValue> aggregatedValues) {
		System.out.println();
		System.out.println(title);
		System.out.println(repeat ("-", title.length()));
		for (Object key : aggregatedValues.keySet()) {
			System.out.println(key.toString() + ": " + FORMAT.format(aggregatedValues.get(key).getValue()));
		}
		return null;
	}

	private String repeat(String string, int times) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < times; i++) {
			builder.append(string);
		}
		return builder.toString();
	}

	@Override
	public String getId() {
		return "table";
	}

	@Override
	public String getName() {
		return "Console Output";
	}
}
