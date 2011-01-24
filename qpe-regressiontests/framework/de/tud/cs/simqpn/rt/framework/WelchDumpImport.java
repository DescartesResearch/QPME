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
