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

package de.tud.cs.simqpn.rt.framework;

import static org.junit.Assert.fail;

import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.log4j.LogMF;
import org.apache.log4j.Logger;

import de.tud.cs.simqpn.rt.framework.results.Metric;
import de.tud.cs.simqpn.rt.framework.results.RunInfo;
import de.tud.cs.simqpn.rt.framework.results.SimulationResults;
import de.tud.cs.simqpn.rt.framework.results.Statistics;
import de.tud.cs.simqpn.rt.framework.results.Statistics.ElementType;

/**
 * Parses the console output of a simulation run and collects the simulation
 * results. Console outputs of Batch Means and Replication/Deletion runs are
 * supported. In cases a simqpn result file is available, should use that one
 * instead.
 * 
 * @author Simon Spinner
 * 
 */
public class ConsoleLogImport {

	private static Logger log = Logger.getLogger(ConsoleLogImport.class);

	/**
	 * Start the import process.
	 * @param info - Information of the current simulation run.
	 * @param reference - Container for the parsed simulation results
	 */
	public void execute(RunInfo info, SimulationResults reference) {
		String currentLine;
		
		Pattern placeLine = Pattern
				.compile("REPORT for (.*): (.*)----------------------------------------");
		Pattern colorLine = Pattern
				.compile("------------------ Color=(.*) --------------------");
		Pattern confidenceInterval = Pattern
				.compile("([0-9Ee\\-\\.]*)% c\\.i\\. = ([0-9Ee\\-\\.]*) \\+/- ([0-9Ee\\-\\.]*)"); 
				// such as: 95% c.i. = 3.4 +/- 0.5
		Pattern parameter = Pattern
				.compile("([\\p{Alpha}\\p{Space}\\[\\]]*)=[\\p{Space}]?([0-9Ee\\-\\.]*)");
				// such as: arrivThrPut = 0.943985
		
		Statistics currentElement = null;
		Statistics currentColor = null;

		try {
			LineIterator lines = IOUtils.lineIterator(new FileReader(info
					.getConsoleLogFile()));
			try {
				while (lines.hasNext()) {
					currentLine = lines.nextLine();

					Matcher m = placeLine.matcher(currentLine);
					if (m.matches()) {
						// Place results start						
						String typeStr = m.group(1).trim().toLowerCase();
						ElementType type;
						if (typeStr.equals("place")) {
							type = ElementType.ORDINARY_PLACE;
						} else if (typeStr.startsWith("queue")) {
							type = ElementType.QPLACE_QUEUE;
						} else if (typeStr.startsWith("depository")) {
							type = ElementType.QPLACE_DEPOSITORY;
						} else {
							throw new Exception("Unexpected element type: "
									+ typeStr);
						}
						
						currentElement = reference.getStatistics(m.group(2),
								type); // results compartement of a place starts
						currentColor = null; // reset color variable
						if (currentElement == null) {
							LogMF.error(log,
									"Unexpected reference place: {0}({1})",
									new Object[] { m.group(2), type });
							throw new RuntimeException();
						}
					} else {
						m = colorLine.matcher(currentLine);
						if (m.matches()) {
							// Color results start
							int colIdx = Integer.parseInt(m.group(1));
							if ((currentElement == null)
									|| (colIdx >= currentElement
											.getChildStats().size())) {
								LogMF.error(log, "Unexpected color: {0}",
										new Object[] { m.group(1) });
								throw new RuntimeException();
							}
							currentColor = currentElement.getChildStats().get(
									colIdx);
						} else {
							m = confidenceInterval.matcher(currentLine);
							if (m.matches()) {
								// Confidence interval
								currentColor.getOrCreateMetric(
										Metric.CONF_LEVEL_ST).addSample(
										Double.parseDouble(m.group(1)));
								currentColor.getOrCreateMetric(
										Metric.STD_STATE_MEAN_ST).addSample(
										Double.parseDouble(m.group(2)));
								currentColor.getOrCreateMetric(
										Metric.CI_HALF_LEN_ST).addSample(
										Double.parseDouble(m.group(3)));
							} else {
								m = parameter.matcher(currentLine);
								while (m.find()) {
									// Other metrics
									String name = m.group(1).trim();

									// Apply substitutions: In older versions of simqpn some metrics
									// had another name -> replace old metric names with the new ones.
									name = TestConfig.getInstance()
											.applySubstitutions(name);

									if (currentColor != null) {
										currentColor.getOrCreateMetric(name)
												.addSample(
														Double.parseDouble(m
																.group(2)));
									} else if (currentElement != null) {
										// No color currently selected -> metric of place
										currentElement.getOrCreateMetric(name)
												.addSample(
														Double.parseDouble(m
																.group(2)));
									}
								}
							}
						}
					}
				}
			} finally {
				LineIterator.closeQuietly(lines);
			}
		} catch (Exception ex) {
			log.error("Error parsing console import.", ex);
			fail();
		}
	}
}
