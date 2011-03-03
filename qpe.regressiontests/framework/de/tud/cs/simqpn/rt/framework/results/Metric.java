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

package de.tud.cs.simqpn.rt.framework.results;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;


public class Metric {
	
	public static final String WALL_CLOCK_TIME = "wallClockTime";
	
	///////////////////////////////////////////////////////////
	// Batch Mean metrics
	public static final String TK_OCP = "tkOcp";
	public static final String ARRIV_CNT = "arrivCnt";
	public static final String DEPT_CNT = "deptCnt";
	public static final String ARRIV_THR_PUT = "arrivThrPut";
	public static final String DEPT_THR_PUT = "deptThrPut";
	public static final String MEAN_TK_POP = "meanTkPop";
	public static final String MIN_TK_POP = "minTkPop";
	public static final String MAX_TK_POP = "maxTkPop";
	public static final String TK_COL_OCP = "tkColOcp";
	public static final String MEAN_ST = "meanST";
	public static final String MIN_ST = "minST";
	public static final String MAX_ST = "maxST";
	public static final String ST_DEV_ST = "stDevST";
	public static final String NUM_BATCHES_ST = "numBatchesST";
	public static final String BATCH_SIZE_ST = "batchSizeST";
	public static final String STD_STATE_MEAN_ST = "stdStateMeanST";
	public static final String ST_DEV_STD_STATE_MEAN_ST = "stDevStdStateMeanST";
	public static final String CI_HALF_LEN_ST = "ciHalfLenST";
	public static final String CONF_LEVEL_ST = "confLevelST";
	public static final String TOT_ARRIV_THR_PUT = "totArrivThrPut";
	public static final String TOT_DEPT_THR_PUT = "totDeptThrPut";
	public static final String MEAN_TOT_TK_POP = "meanTotTkPop";
	public static final String QUEUE_UTIL = "queueUtil";
	public static final String HIST_ST = "histST";

	///////////////////////////////////////////////////////////
	// Replication/Deletion
	public static final String NUM_REPLICATIONS_USED = "numReplicationsUsed";
	public static final String NUM_TOO_SHORT_REPLS = "numTooShortRepls";
	public static final String MIN_RUN_LEN = "minRunLen";
	public static final String MAX_RUN_LEN = "maxRunLen";
	public static final String AVG_RUN_LEN = "avgRunLen";
	public static final String ST_DEV_RUN_LEN = "stDevRunLen";
	public static final String AVG_WALL_CLOCK_TIME = "avgWallClockTime";
	public static final String ST_DEV_WALL_CLOCK_TIME = "stDevWallClockTime";
	public static final String MEAN_QUEUE_UTIL_QPL = "meanQueueUtilQPl";
	public static final String ST_DEV_QUEUE_UTIL_QPL = "stDevQueueUtilQPl";
	public static final String MEAN_ARRIV_THR_PUT_C = "meanArrivThrPut[c]";
	public static final String MEAN_DEPT_THR_PUT_C = "meanDeptThrPut[c]";
	public static final String ST_DEV_ARRIV_THR_PUT_C = "stDevArrivThrPut[c]";
	public static final String ST_DEV_DEPT_THR_PUT_C = "stDevDeptThrPut[c]";
	public static final String MIN_AVG_TK_POP_C = "minAvgTkPop[c]";
	public static final String MAX_AVG_TK_POP_C = "maxAvgTkPop[c]";
	public static final String MEAN_AVG_TK_POP_C = "meanAvgTkPop[c]";
	public static final String MEAN_TK_COL_OCP_C = "meanTkColOcp[c]";
	public static final String ST_DEV_AVG_TK_POP_C = "stDevAvgTkPop[c]";
	public static final String ST_DEV_TK_COL_OCP_C = "stDevTkColOcp[c]";
	public static final String MEAN_AVG_ST_C = "meanAvgST[c]";
	public static final String ST_DEV_AVG_ST_C = "stDevAvgST[c]";
	
	///////////////////////////////////////////////////////////
	// Method of Welch
	public static final String MOV_AVG_ST = "movAvgST";
	
	private String name;
	private DescriptiveStatistics samples;
	
	public Metric(String name) {
		this.name = name;
		samples = new DescriptiveStatistics();		
	}
	
	public String getName() {
		return name;
	}
	
	public void addSample(double value) {
		samples.addValue(value);
	}
	
	public double getMean() {
		return samples.getMean();
	}
	
	public double getStandardDeviation() {
		return samples.getStandardDeviation();
	}
	
	public double getMinimum() {
		return samples.getMin();
	}
	
	public double getMaximum() {
		return samples.getMax();
	}
	
	public double[] getSamples() {
		return samples.getValues();
	}

	public long getSampleCount() {
		return samples.getN();
	}
}
