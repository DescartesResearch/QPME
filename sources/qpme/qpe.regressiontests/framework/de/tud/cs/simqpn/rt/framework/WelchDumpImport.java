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
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2011/01/21  Simon Spinner     Created.
 */
package de.tud.cs.simqpn.rt.framework;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.LineIterator;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.log4j.Logger;

import de.tud.cs.simqpn.rt.framework.results.Metric;
import de.tud.cs.simqpn.rt.framework.results.RunInfo;
import de.tud.cs.simqpn.rt.framework.results.SimulationResults;
import de.tud.cs.simqpn.rt.framework.results.Statistics;
import de.tud.cs.simqpn.rt.framework.results.Statistics.ElementType;

public class WelchDumpImport {
	
	private static final Logger log = Logger.getLogger(WelchDumpImport.class);
	
	public void execute(RunInfo info, SimulationResults results) {
		Pattern fileNamePattern = Pattern.compile("WelchMovAvgST-(.*)-col([0-9]*)-win([0-9]*)\\.txt");
		FileFilter resultsFiles = new RegexFileFilter(fileNamePattern);
		for (File f : info.getRunDirectory().listFiles(resultsFiles)) {
			Matcher m = fileNamePattern.matcher(f.getName());
			if (m.matches()) {
				String typeAndName = m.group(1);
				int colIdx = Integer.parseInt(m.group(2));
				int windowEnd = Integer.parseInt(m.group(3));
				ElementType type;
				String name = "";
				
				if (typeAndName.startsWith("ord_place")) {
					type = ElementType.ORDINARY_PLACE;
					name = typeAndName.substring("ord_place".length());
				} else if (typeAndName.startsWith("place")) {
					type = ElementType.ORDINARY_PLACE;
					name = typeAndName.substring("place".length());
				} else if (typeAndName.startsWith("que_place_dep")) {
					type = ElementType.QPLACE_DEPOSITORY;
					name = typeAndName.substring("que_place_dep".length());
				} else if (typeAndName.startsWith("depository")) {
					type = ElementType.QPLACE_DEPOSITORY;
					name = typeAndName.substring("depository".length());
				} else if (typeAndName.startsWith("que_place_queue")) {
					type = ElementType.QPLACE_QUEUE;
					name = typeAndName.substring("que_place_queue".length());
				} else if (typeAndName.startsWith("queue")) {
					type = ElementType.QPLACE_QUEUE;
					name = typeAndName.substring("queue".length());
				} else {
					log.error("Cannot detect type of place for file: " + f.getName());
					throw new RuntimeException();
				}
				
				Statistics place = results.getStatistics(name, type);
				if (place == null) {
					log.error("Cannot find place " + name + " in results.");
					throw new RuntimeException();
				}
				
				if (colIdx >= place.getChildStats().size()) {
					log.error("Cannot find color with index: " + colIdx);
					throw new RuntimeException();
				}
				Statistics color = place.getChildStats().get(colIdx);
				Metric metric = color.getOrCreateMetric(Metric.MOV_AVG_ST + ":" + windowEnd);
				readWelchDumpFile(f, metric);
				
			}
		}
	}
	
	private void readWelchDumpFile(File dumpFile, Metric metric) {
		try {
			LineIterator lines = new LineIterator(new FileReader(dumpFile));
			while(lines.hasNext()) {
				String l = lines.nextLine();
				metric.addSample(Double.parseDouble(l));
			}
		} catch(FileNotFoundException ex) {
			log.error("Could not find file: " + dumpFile.getName());
		}			
	}

}
