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
 *  2010        Simon Spinner     Created.
 */
package de.tud.cs.simqpn.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;

public class LogUtil {
	
	/**
	 * Custom log level for printing results of the simulation to the console.
	 */
	public static class ReportLevel extends Level {
		
		private static final long serialVersionUID = -4323839422756035057L;

		public static final int REPORT_INT = INFO_INT + 10;
		
		public static final ReportLevel REPORT = new ReportLevel(REPORT_INT, "REPORT", 0);

		protected ReportLevel(int level, String levelStr,
				int syslogEquivalent) {
			super(level, levelStr, syslogEquivalent);
		}
		
		 public static Level toLevel(int val) {
	         if (val == REPORT_INT) {
	             return REPORT;
	         }
	         return (Level) toLevel(val, Level.DEBUG);
	     }
		 
		 public static Level toLevel(int val, Level defaultLevel) {
	         if (val == REPORT_INT) {
	             return REPORT;
	         }
	         return Level.toLevel(val,defaultLevel);
	     }
		 
		 public static Level toLevel(String sArg, Level defaultLevel) {     
	        if(sArg != null && sArg.toUpperCase().equals("REPORT")) {
	            return REPORT;
	        }
	        return Level.toLevel(sArg,defaultLevel);
		 }
		
	}
	
	private static final Logger log = Logger.getLogger(LogUtil.class);
	
	public static void configureCustomLogging(String configFilename) {
		PropertyConfigurator.configure(configFilename);
		log.info("Custom logging configuration");
	}
	
	public static void configureDefaultLogging(String outputDirectory, String filePrefix) throws IOException {
		//Configure console output
		BasicConfigurator.resetConfiguration();
		PatternLayout layout = new PatternLayout("%p %m%n");
		ConsoleAppender appender = new ConsoleAppender(layout);
		BasicConfigurator.configure(appender);
		
		//Configure file output		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HHmmssS");
		
		File outputDirectoryFile = new File(outputDirectory);
		File logFileName = new File(outputDirectoryFile, filePrefix + "_" + dateFormat.format(new Date()) + ".log");

		BasicConfigurator.configure(new FileAppender(layout, logFileName.getAbsolutePath()));
		log.info("Logging to " + logFileName.getAbsolutePath());
		
	}
	
	public static String formatDetailMessage(String message, String...details) {
		StringBuilder result = new StringBuilder(message);
		result.append("\nDetails:");
		for (int i = 0; i < details.length; i += 2) {
			result.append("\n  ");
			if ((i + 1) < details.length) {
				result.append(details[i]);
				result.append(" = ");
				result.append(details[i + 1]);
			} else {
				result.append(details[i]);
			}
		}
		return result.toString();
	}
	
	public static String formatMultilineMessage(String...lines) {
		StringBuilder result = new StringBuilder();
		for(int i = 0; i < lines.length; i++) {
			if (i > 0) {
				result.append("    ");
			}
			result.append(lines[i]);
			if (i < (lines.length - 1)) {
				result.append("\n");
			}
		}
		return result.toString();
	}

}
