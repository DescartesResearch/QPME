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
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2008/11/25  Samuel Kounev     Created.
 * 
 */
package de.tud.cs.simqpn.kernel.entities.stats;

import org.apache.log4j.Logger;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.queue.Queue;
import de.tud.cs.simqpn.kernel.entities.queue.QueueingDiscipline;
import de.tud.cs.simqpn.kernel.util.LogUtil.ReportLevel;

/**
 * Class QueueStats
 *
 * 
 * @author Samuel Kounev
 * @version %I%, %G%
 */

public class QueueStats extends Stats implements java.io.Serializable {
	private static final long serialVersionUID = 154545454L;
	
	private static Logger log = Logger.getLogger(QueueStats.class);

	public QueueingDiscipline queueDiscip;	// Queueing discipline.
	public int		numServers;		// FCFS queues: Number of servers in queueing station.			
	public Queue	queue;			// Associated Queue object.
	
	// ----------------------------------------------------------------------------------------------------
	//  STATISTICS
	// ----------------------------------------------------------------------------------------------------

	// StatsLevel 1 ---------------------------------------------------------------------------------------
	public double	totArrivThrPut;				// Total arrival throughput over all queueing places this queue is part of.
	public double	totDeptThrPut;				// Total departure throughput over all queueing places this queue is part of.
		
	// StatsLevel 2 ---------------------------------------------------------------------------------------
	public double	meanTotTkPop;				// Mean queue total token population.	
	public double	areaQueUtil;				// Accumulated area under the curve for computing the expected queue utilization. 
	public double	lastTkPopClock;				// Time of last token population change (over all colors).		
	public int		lastTotTkPop;				// Last queue total token population (over all colors).		
	public double	queueUtil;					// Utilization of the queue = (areaQueUtil / msrmPrdLen) - fraction of the available server resources that are used on average.

	// StatsLevel 3 ---------------------------------------------------------------------------------------	
	public double	meanST;						// Mean Sojourn Time over all tokens visiting this queue.
	
	// StatsLevel 5 ---------------------------------------------------------------------------------------
	
	// ----------------------------------------------------------------------------------------------------

	/**
	 * Constructor
	 *
	 * @param id			- global id of the queue
	 * @param name			- name of the queue
	 * @param numColors		- total number of token colors over all queueing places the queue is part of
	 * @param statsLevel	- determines the amount of statistics to be gathered during the run
	 * @param queueDiscip	- queueing discipline
	 * @param numServers	- FCFS queues: number of servers in queueing station
	 * @param MyQueue			- reference to respective Queue object
	 * 
	 */	
	public QueueStats(int id, String name, int numColors, int statsLevel, QueueingDiscipline queueDiscip, int numServers, Queue queue, SimQPNConfiguration configuration) throws SimQPNException  {
		super(id, name, QUEUE, numColors, statsLevel, configuration);
		this.queueDiscip	= queueDiscip;
		this.numServers		= numServers;	
		this.queue			= queue;
		this.colors			= null; //NOTE: Currently colors is not used for QueueStats
	}

	/**
	 * Method init - should be called at the beginning of the measurement period (ideally the beginning of steady state)	    	 
	 * 
	 * @param 
	 * @return
	 * @exception
	 */
	public void init(double clock)  {		
		// statsLevel >= 1		
		totArrivThrPut				= 0;
		totDeptThrPut				= 0;
		if (statsLevel >= 2)  {			
			meanTotTkPop			= 0;
			areaQueUtil				= 0;
			lastTotTkPop 			= 0;		
			for (int p=0; p < queue.qPlaces.length; p++)
				for (int c=0; c < queue.qPlaces[p].numColors; c++)
					lastTotTkPop += queue.qPlaces[p].getQueueTokenPop()[c];				
			lastTkPopClock = clock;
		}
	}


	/**
	 * Method start - should be called at the end of RampUp to start taking measurements.	    	 
	 * 
	 * @param 
	 * @return
	 * @exception
	 */
	public void start(double clock) throws SimQPNException  {	
		init(clock);
		inRampUp = false;
		endRampUpClock = clock;	
	}

	/**
	 * Method finish - should be called at the end of the measurement period to
	 *                 complete statistics collection.
	 *  
	 * Note: Completes accumulated areas under the curve.   
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public void finish(SimQPNConfiguration configuration, double runWallClockTime, double clock) throws SimQPNException  {		
		if (statsLevel >= 2) //NOTE: This makes sure areaQueUtil is complete!
			updateTotTkPopStats(0, clock);
		endRunClock = clock;
		msrmPrdLen = endRunClock - endRampUpClock;		
		this.runWallClockTime = runWallClockTime;
		processStats(); 
	}
		
	/**
	 * Method updateTotTkPopStats
	 * 
	 * NOTE: Currently called only when statsLevel >= 2
	 * 
	 * @param delta     - difference between new and old token population
	 */
	public void updateTotTkPopStats(int delta, double clock) {
		if (inRampUp) return;		
		double elapsedTime = clock - lastTkPopClock;
		if (elapsedTime > 0) {
			if (numServers > 1)	//NOTE: WATCH OUT with IS queues here! Below we assume that for such queues numServers == 0.				
				areaQueUtil += elapsedTime * (lastTotTkPop > numServers ? 1 : (((double) lastTotTkPop) / numServers));																						
			else areaQueUtil += elapsedTime * (lastTotTkPop > 0 ? 1 : 0);  
			lastTkPopClock = clock;				
		}			
		lastTotTkPop += delta;									
	}

	/**
	 * Method processStats - processes gathered statistics (summarizes data)
	 *                        
	 * NOTE: Here we assume that (statsLevel <= queue.qPlaces[*].startsLevel)
	 * 
	 */	
	public void processStats()  {
		int totNumST = 0;
		for (int p = 0; p < queue.qPlaces.length; p++)
			for (int c = 0; c < queue.qPlaces[p].numColors; c++)  {				
				totArrivThrPut	+= queue.qPlaces[p].qPlaceQueueStats.arrivThrPut[c];				
				totDeptThrPut	+= queue.qPlaces[p].qPlaceQueueStats.deptThrPut[c];
				if (statsLevel >= 3)
					totNumST	+= queue.qPlaces[p].qPlaceQueueStats.numST[c];
			}		
		if (statsLevel >= 2)
			queueUtil = areaQueUtil / msrmPrdLen;			
		for (int p = 0; p < queue.qPlaces.length; p++)
			for (int c = 0; c < queue.qPlaces[p].numColors; c++)  {				
				if (statsLevel >= 2)  
					meanTotTkPop += queue.qPlaces[p].qPlaceQueueStats.meanTkPop[c];
				if (statsLevel >= 3)  
					meanST += ((double) queue.qPlaces[p].qPlaceQueueStats.numST[c] / totNumST) * queue.qPlaces[p].qPlaceQueueStats.meanST[c];  					            	                                             				
			}	
		completed = true;					
	}
	
	/**
	 * Method printReport - prints a summary of the collected statistics 
	 * 
	 */	
	public void printReport() throws SimQPNException {		
		if (!completed) {
			log.error("QueueStats " + name + ": Attempting to access statistics before data collection has finished!");
			throw new SimQPNException();
		}		

		StringBuilder report = new StringBuilder();
			
		report.append("for Queue : ").append(name).append("----------------------------------------\n");
		report.append("\n");		
		report.append("totArrivThrPut=").append(totArrivThrPut);
		report.append(" totDeptThrPut=").append(totDeptThrPut).append("\n");					
		if (statsLevel >= 2) 
			report.append("meanTotTkPop=").append(meanTotTkPop).append(" queueUtil=").append(queueUtil).append("\n");			
		if (statsLevel >= 3)  
			report.append("meanST=").append(meanST).append("\n");
		
		report.append("\n\n");
		
		//Output report
		log.log(ReportLevel.REPORT, report);
	}
	
}
