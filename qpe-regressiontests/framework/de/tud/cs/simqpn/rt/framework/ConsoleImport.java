package de.tud.cs.simqpn.rt.framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.tud.cs.simqpn.rt.framework.stats.PlaceStatistics;
import de.tud.cs.simqpn.rt.framework.stats.Statistics;

public class ConsoleImport {
	
	private static Logger log = Logger.getLogger(ConsoleImport.class.getName());
	
	public boolean parse(Reader consoleOutput, SimulationResults reference) {
		BufferedReader in = new BufferedReader(consoleOutput);
		String line;
		Pattern placeLine = Pattern.compile("REPORT for (.*) : (.*)----------------------------------------");
		Pattern colorLine = Pattern.compile("------------------ Color=(.*) --------------------");
		Pattern confidenceInterval = Pattern.compile("([0-9Ee\\-\\.]*)% c\\.i\\. = ([0-9Ee\\-\\.]*) \\+/- ([0-9Ee\\-\\.]*)");
		Pattern parameter = Pattern.compile("([\\p{Alpha}\\p{Space}]*)=([0-9Ee\\-\\.]*)");
		PlaceStatistics currentElement = null;
		Statistics currentColor = null;
		
		try {
			try {
				 line = in.readLine();
				 while(line != null) {
					if(line.startsWith("Info: STOPPING because max totalRunLen is reached!")) {
						log.warning("Necessary precision might not be reached.");
						return false;
					}
					
					Matcher m = placeLine.matcher(line);
					if(m.matches()) {
						String type = m.group(1).toLowerCase();
						if(type.equals("queue")) {
							type = "qplace:queue";
						} else if(type.equals("depository")) {
							type = "qplace:depository";
						}

						currentElement = reference.getPlaceStatistics(type, m.group(2));
						if (currentElement == null) {
							log.severe("Unexpected reference place: " + m.group(2) + "(" + type + ")");
							throw new RuntimeException();
						}
					} else {
						m = colorLine.matcher(line);
						if(m.matches()) {
							int colIdx = Integer.parseInt(m.group(1));
							if ((currentElement == null) || (colIdx >= currentElement.getColorCount())) {
								log.severe("Unexpected color: " + m.group(1) + " in " + currentElement.type + " " + currentElement.getName());
								throw new RuntimeException();
							}
							currentColor = currentElement.getColorStats(colIdx);
						}
						else
						{
							m = confidenceInterval.matcher(line);
							if(m.matches()) {
								
								currentColor.getOrCreateMetric("confLevelST").addSample(Double.parseDouble(m.group(1)));
								currentColor.getOrCreateMetric("stdStateMeanST").addSample(Double.parseDouble(m.group(2)));
								currentColor.getOrCreateMetric("ciHalfLenST").addSample(Double.parseDouble(m.group(3)));
							} else {
								m = parameter.matcher(line);
								while(m.find()) {
									String name = m.group(1).trim();
									if (name.equals("arrivThrPut")) {
										currentColor.getOrCreateMetric("arrivThrPut").addSample(Double.parseDouble(m.group(2)));
									} else if (name.equals("deptThrPut")) {
										currentColor.getOrCreateMetric("deptThrPut").addSample(Double.parseDouble(m.group(2)));
									} else if (name.equals("meanTkPop")) {
										currentColor.getOrCreateMetric("meanTkPop").addSample(Double.parseDouble(m.group(2)));
									} else if (name.equals("colUtil")) {
										currentColor.getOrCreateMetric("tkColOcp").addSample(Double.parseDouble(m.group(2)));
									} else if (name.equals("meanST")) {
										currentColor.getOrCreateMetric("meanST").addSample(Double.parseDouble(m.group(2))); 
									} else if (name.equals("stDevST")) {
										currentColor.getOrCreateMetric("stDevST").addSample(Double.parseDouble(m.group(2)));
									} else if (name.equals("Overall Queue Util")) {
										currentElement.getOrCreateMetric("tkOcp").addSample(Double.parseDouble(m.group(2)));
									}
								}
							}
						}
					}
					line = in.readLine();
				}
			} finally {
				in.close();
			}
		} catch(IOException ex) {
			log.log(Level.SEVERE, "Error parsing console import.", ex);
		}
		return true;
	}
}
