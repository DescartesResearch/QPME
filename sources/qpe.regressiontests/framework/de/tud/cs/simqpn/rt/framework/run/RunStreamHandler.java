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

package de.tud.cs.simqpn.rt.framework.run;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.exec.ExecuteStreamHandler;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.log4j.Logger;

/**
 * Processes the output and error stream of a forked simulation process.
 * 
 * @author Simon Spinner
 *
 */
public class RunStreamHandler implements ExecuteStreamHandler {

	/**
	 * Thread that reads process output from a stream and checks for specific
	 * messages. It also writes all the output to a file.
	 */
	private class OutputFilter extends Thread {

		public volatile boolean stop = false;
		public volatile boolean precessionReached = true;
		public volatile double runLength = 0.0;
		public volatile boolean overflow = false;

		public InputStream source;
		public PrintStream target;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			try {
				LineIterator lines = new LineIterator(new InputStreamReader(
						source));
				try {
					while (!stop && lines.hasNext()) {
						String currentLine = lines.nextLine();

						if (checkForOverflow(currentLine)) {
							overflow = true;
							processWatchdog.destroyProcess();
							return;
						}

						if (checkPrecision) {
							if (checkIfPrecisionReached(currentLine)) {
								precessionReached = false;
							}
						}

						if (parseRunLength) {
							if (checkForRunSummaryLine(currentLine)) {
								Pattern runLengthPattern = Pattern
										.compile(".*totalRunLen= ([0-9Ee\\-\\.]*).*");
								Matcher m = runLengthPattern
										.matcher(currentLine);
								if (m.matches()) {
									runLength = Double.parseDouble(m.group(1));
								}
							}
						}

						synchronized (target) {
							target.println(currentLine);
							target.flush();
						}
					}
				} finally {
					LineIterator.closeQuietly(lines);
				}
			} catch (IllegalStateException ex) {
				log.error("Error pumping output.", ex);
			}
		}

		private boolean checkForRunSummaryLine(String currentLine) {
			return currentLine.contains("totalRunLen= ");
		}

		private boolean checkIfPrecisionReached(String currentLine) {
			return currentLine
					.contains("The simulation was stopped because of reaching max totalRunLen")
					|| currentLine
							.contains("STOPPING because max totalRunLen is reached!");
		}

		private boolean checkForOverflow(String currentLine) {
			return currentLine
					.contains("is exceedingly growing. An overflow might occur.");
		}
	}

	public static final Logger log = Logger.getLogger(RunStreamHandler.class);

	private OutputFilter outFilter;
	private OutputFilter errFilter;

	private PrintStream file;
	private File logFile;

	private InputStream out;
	private InputStream err;

	private boolean checkPrecision;
	private boolean parseRunLength;

	private ExecuteWatchdog processWatchdog;

	public RunStreamHandler(File logFile, boolean checkPrecision,
			boolean parseRunLength, ExecuteWatchdog processWatchdog) {
		this.logFile = logFile;
		this.processWatchdog = processWatchdog;
		this.checkPrecision = checkPrecision;
		this.parseRunLength = parseRunLength;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.exec.ExecuteStreamHandler#setProcessErrorStream(java.io.InputStream)
	 */
	@Override
	public void setProcessErrorStream(InputStream err) throws IOException {
		this.err = err;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.exec.ExecuteStreamHandler#setProcessInputStream(java.io.OutputStream)
	 */
	@Override
	public void setProcessInputStream(OutputStream in) throws IOException {
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.exec.ExecuteStreamHandler#setProcessOutputStream(java.io.InputStream)
	 */
	@Override
	public void setProcessOutputStream(InputStream out) throws IOException {
		this.out = out;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.exec.ExecuteStreamHandler#start()
	 */
	@Override
	public void start() throws IOException {
		file = new PrintStream(logFile);

		outFilter = new OutputFilter();
		outFilter.source = out;
		outFilter.target = file;
		errFilter = new OutputFilter();
		errFilter.source = err;
		errFilter.target = file;

		outFilter.start();
		errFilter.start();
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.exec.ExecuteStreamHandler#stop()
	 */
	@Override
	public void stop() {
		if (outFilter != null) {
			outFilter.stop = true;
			try {
				outFilter.join();
			} catch (InterruptedException e) {
			}
		}

		if (errFilter != null) {
			errFilter.stop = true;
			try {
				errFilter.join();
			} catch (InterruptedException e) {
			}
		}

		if (file != null) {
			IOUtils.closeQuietly(file);
		}
	}

	/**
	 * @return flag indicating whether an overflow has occurred during the simulation run.
	 */
	public boolean hasOverflowOccurred() {
		return outFilter.overflow || errFilter.overflow;
	}

	/**
	 * @return flag indicating whether the precision was reached in this simulation run.
	 */
	public boolean isPrecisionReached() {
		return outFilter.precessionReached && errFilter.precessionReached;
	}

	/**
	 * @return total run length in simulation time of the simulation run.
	 */
	public double getTotalRunLength() {
		return outFilter.runLength;
	}
}