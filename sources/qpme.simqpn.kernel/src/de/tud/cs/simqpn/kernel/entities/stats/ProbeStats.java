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
 * Original Author(s):  Samuel Kounev
 * Contributor(s): Simon Spinner  
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2010/08/02	Simon Spinner     Created.
 *  
 */
package de.tud.cs.simqpn.kernel.entities.stats;

import java.util.Arrays;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;

/**
 * Calculates the stats for a probe. This class reuses the algorithms for
 * calculating the sojourn times of tokens from PlaceStats. It only customizes
 * the report output for probes.
 * 
 * @author Simon Spinner
 *
 */
public class ProbeStats extends PlaceStats {
	
	private static final long serialVersionUID = -1382416739631120671L;

	/**
	 * Constructor
	 * 
	 * @param id 			- global id of the probe
	 * @param name 			- name of the probe
	 * @param colors 	    - names of the colors that are inspected by the probe
	 * @param statsLevel	- determines the amount of statistics to be gathered during the run
	 *            
	 */
	public ProbeStats(int id, String name, String[] colors, int statsLevel, SimQPNConfiguration configuration) throws SimQPNException {
		super(id, name, PROBE, colors, statsLevel, configuration);
	}

	/* (non-Javadoc)
	 * @see de.tud.cs.simqpn.kernel.PlaceStats#printReportHeader()
	 */
	@Override
	protected void printReportHeader(StringBuilder report) throws SimQPNException {
		report.append("REPORT for Probe : ").append(name).append("----------------------------------------\n");
	}	

	/* (non-Javadoc)
	 * @see de.tud.cs.simqpn.kernel.PlaceStats#printColorPopulationStats(int)
	 */
	@Override
	protected void printColorPopulationStats(StringBuilder report, int c) {
		// Not stats collected
	}

	/* (non-Javadoc)
	 * @see de.tud.cs.simqpn.kernel.PlaceStats#printPlaceStats()
	 */
	@Override
	protected void printPlaceStats(StringBuilder report) {
		// Not stats collected
	}

	/* (non-Javadoc)
	 * @see de.tud.cs.simqpn.kernel.PlaceStats#getTypeDescription()
	 */
	@Override
	protected String getTypeDescription() throws SimQPNException {
		return "probe";
	}
	
	/**
	 * Method start - should be called at the end of RampUp to start taking measurements.	    	 
	 * 
	 * @exception SimQPNException
	 */
	public void start(SimQPNConfiguration configuration, double clock) throws SimQPNException {
		int[] tokenPop = new int[colors.length];
		Arrays.fill(tokenPop, 0);
		this.start(tokenPop, configuration, clock);
	}
	
	/**
	 * Method finish - should be called at the end of the measurement period to
	 *                 complete statistics collection.
	 *  
	 * Note: Completes accumulated areas under the curve.   
	 * @exception SimQPNException
	 */
	public void finish(SimQPNConfiguration configuration, double runWallClockTime, double clock) throws SimQPNException {
		int[] tokenPop = new int[colors.length];
		Arrays.fill(tokenPop, 0);
		this.finish(tokenPop, configuration, runWallClockTime, clock);
	}
	
	
}
