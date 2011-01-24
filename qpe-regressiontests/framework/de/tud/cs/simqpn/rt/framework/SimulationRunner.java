package de.tud.cs.simqpn.rt.framework;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.stream.XMLStreamException;

public class SimulationRunner {
	
	public enum Revision {
		TRUNK, R100, R162
	}
	
	private abstract class Run implements Callable<Void> {
		protected int index;
		protected String configuration;
		protected File modelFile;
		protected File tmpDir;
		
		public Run(int index, String configuration, File modelFile, File tmpDir) {
			super();
			this.index = index;
			this.configuration = configuration;
			this.modelFile = modelFile;
			this.tmpDir = tmpDir;
		}
		
		@Override
		public Void call() throws Exception {
			log.info("Start simulation run " + index);
			while(true) {
				try {
					ProcessBuilder p = setupProcessBuilder();

					Process process = p.start();
					BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
					
					if(!parseConsoleOutput(in)) {
						log.warning("Simulation run " + index + " skipped to due insufficient precision. Retry...");
						process.destroy();
					} else {
						assertEquals("Error return code.", 0, process.waitFor());
						break;
					}
				} catch(IOException ex) {
					log.log(Level.SEVERE, "Error running simulation.", ex);
					fail();
				} catch (InterruptedException ex) {
					log.log(Level.SEVERE, "Error running simulation.", ex);
					fail();
				}
			}
			log.info("Simulation run " + index + " finished");
			return null;
		}

		protected abstract ProcessBuilder setupProcessBuilder() throws IOException;
		
		protected abstract boolean parseConsoleOutput(BufferedReader in) throws IOException;
	}
	
	private class TrunkRun extends Run {

		public TrunkRun(int index, String configuration, File modelFile, File tmpDir) {
			super(index, configuration, modelFile, tmpDir);
		}

		protected ProcessBuilder setupProcessBuilder() throws IOException {
			StringBuilder classPath = new StringBuilder("\"");
			classPath.append(new File("../QPE-SimQPN/lib/colt.jar").getCanonicalPath()).append(File.pathSeparator);
			classPath.append(new File("../QPE-SimQPN/lib/dom4j-1.6.1.jar").getCanonicalPath()).append(File.pathSeparator);
			classPath.append(new File("../QPE-SimQPN/lib/jaxen-1.1-beta-6.jar").getCanonicalPath()).append(File.pathSeparator);
			classPath.append(new File("../QPE-SimQPN/bin/classes").getCanonicalPath()).append("\"");
			ProcessBuilder p = new ProcessBuilder("java.exe", "-cp",
					classPath.toString(), "de.tud.cs.simqpn.console.SimQPN",
					"-r", configuration, modelFile.getAbsolutePath());
			p.directory(tmpDir);
			p.redirectErrorStream(true);
			return p;
		}
		
		protected boolean parseConsoleOutput(BufferedReader in) throws IOException {
			String line = in.readLine();
			boolean precisionReached = true;
			while(line != null) {
				if(line.startsWith("WARNING: The simulation was stopped because of reaching max totalRunLen")) {
					precisionReached = false;
					break;
				}
				line = in.readLine();
			}
			return precisionReached;
		}
	}
	
	private class R100Run extends Run {
		
		private ConsoleImport consoleImport;
		private SimulationResults results;

		public R100Run(int index, String configuration, File modelFile, File tmpDir, ConsoleImport consoleImport, SimulationResults results) {
			super(index, configuration, modelFile, tmpDir);
			this.consoleImport = consoleImport;
			this.results = results;
		}

		protected ProcessBuilder setupProcessBuilder() throws IOException {
			File binary = new File("./historic-executables/simqpn-base-r100.jar");
			if(!binary.exists()) {
				log.severe("Could not find the simqpn binary: " + binary.getAbsolutePath());
			}
			ProcessBuilder p = new ProcessBuilder("java.exe", "-jar",
					binary.getAbsolutePath(), "-r",
					configuration, modelFile.getAbsolutePath());
			p.directory(tmpDir);
			p.redirectErrorStream(true);
			return p;
		}
		
		protected boolean parseConsoleOutput(BufferedReader in) throws IOException {
			return consoleImport.parse(in, results);
		}
	}
	
	private class R162Run extends Run {

		public R162Run(int index, String configuration, File modelFile, File tmpDir) {
			super(index, configuration, modelFile, tmpDir);
		}

		protected ProcessBuilder setupProcessBuilder() throws IOException {
			File binary = new File("./historic-executables/simqpn-base-r162.jar");
			if(!binary.exists()) {
				log.severe("Could not find the simqpn binary: " + binary.getAbsolutePath());
			}
			ProcessBuilder p = new ProcessBuilder("java.exe", "-jar",
					binary.getAbsolutePath(),
					"-r", configuration, modelFile.getAbsolutePath());
			p.directory(tmpDir);
			p.redirectErrorStream(true);
			return p;
		}
		
		protected boolean parseConsoleOutput(BufferedReader in) throws IOException {
			String line = in.readLine();
			boolean precisionReached = true;
			while(line != null) {
				if(line.startsWith("WARNING: The simulation was stopped because of reaching max totalRunLen")) {
					precisionReached = false;
					break;
				}
				line = in.readLine();
			}
			return precisionReached;
		}
	}
	
	public static Logger log = Logger.getLogger(SimulationRunner.class.getName());
	
	private Revision revisionToRun;
	private String testName;
	
	public SimulationRunner(Revision revisionToRun, String testName) {
		this.revisionToRun = revisionToRun;
		this.testName = testName;
	}
	
	public SimulationResults run(String model, String configuration) {
		int runs = Integer.parseInt(System.getProperty("rt.simulationruns", "1"));
		return this.run(model, configuration, runs);
	}
	
	public SimulationResults run(String model, String configuration, int repeats) {
		File modelFile = new File("testfiles/" + testName + "/" + model);
		if (!modelFile.exists()) {
			log.severe("Could not find model file: " + modelFile.getName());
			throw new RuntimeException();
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hhmmssSSS");
		File tmpDir = new File("./tmp/" + dateFormat.format(new Date()) + "/" + testName);
		if(!tmpDir.mkdirs()) {
			log.severe("Could not create temporary directory");
		}
		
		SimulationResults results = new SimulationResults();
		if (revisionToRun == Revision.R100) {
			ModelImport extractor = new ModelImport();
			try {
				extractor.initWithModelStructure(results, modelFile);
			} catch (Exception e) {
				log.log(Level.SEVERE, "Could not read model structure.", e);
				throw new RuntimeException();
			}
		}
		ConsoleImport outputParser = new ConsoleImport();
		
		ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		ArrayList<Run> runs = new ArrayList<Run>(repeats);		
		switch(revisionToRun) {
		case R100:
			for (int i = 0; i < repeats; i++) {
				runs.add(new R100Run(i, configuration, modelFile, tmpDir, outputParser, results));
			}
			break;
		case R162:
			for (int i = 0; i < repeats; i++) {
				runs.add(new R162Run(i, configuration, modelFile, tmpDir));
			}
			break;
		case TRUNK:
			for (int i = 0; i < repeats; i++) {
				runs.add(new TrunkRun(i, configuration, modelFile, tmpDir));
			}
			break;
		}
		
		try {
			List<Future<Void>> futures = service.invokeAll(runs);
			for(Future<Void> f : futures) {
				f.get();
			}
		} catch (InterruptedException e1) {
			log.log(Level.SEVERE, "Error running simulation.", e1);
		} catch (ExecutionException e) {
			log.log(Level.SEVERE, "Error running simulation.", e);
		}		
		service.shutdownNow();
		
		if ((revisionToRun == Revision.R162) || (revisionToRun == Revision.TRUNK)) {
			SimQPNResultFileImport resultImport = new SimQPNResultFileImport();
			File[] simqpnFiles =  tmpDir.listFiles(new FilenameFilter() {			
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".simqpn");
				}
			});
			for(File simqpnFile : simqpnFiles) {
				try {
					resultImport.execute(simqpnFile, results);
				} catch (FileNotFoundException e) {
					log.log(Level.SEVERE, "Error reading result file.", e);
					fail();
				} catch (XMLStreamException e) {
					log.log(Level.SEVERE, "Error reading result file.", e);
					fail();
				}
			}
		}
		
		return results;
	}
}
