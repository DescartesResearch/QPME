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
 *  2008/12/14  Samuel Kounev     Created.
 *  2009/03/13  Frederik Zipp     Made data accessible from outside.
 *  2010/04/17	Simon Spinner	  Make bucketSize and numBuckets configurable.
 * 
 */

package de.tud.cs.simqpn.kernel.entities.stats;

import java.io.Serializable;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;

public class TimeHistogram implements Serializable  {
	private static final long serialVersionUID = 689255573778061L;

	private double			bucketSize;					// Size of buckets (granularity) 
	private int				numBuckets;					// Total number of buckets
	private int				maxNumBuckets;				// Maximum number of buckets
	private int[]			data;						// Histogram data
	
	/**
	 * Constructor 
	 *
	 */	
	public TimeHistogram()  {
		this.numBuckets = SimQPNConfiguration.TIME_HISTOGRAM_MIN_NUM_BUCKETS;
		this.maxNumBuckets = SimQPNConfiguration.TIME_HISTOGRAM_MAX_NUM_BUCKETS;
		
		this.data = new int[numBuckets];
		for (int i = 0; i < numBuckets; i++) this.data[i] = 0;
	}
	
	/**
	 * Sets the bucket size of the time histogram. This method should only be called before
	 * entries are added to the histogram.
	 *
	 * @param bucketSize - bucketSize in simulation time units. If set to 0, the default bucket
	 * 					   size is used.
	 */
	public void setBucketSize(double  bucketSize) {
		if (bucketSize > 0)
			this.bucketSize = bucketSize;
		else
			this.bucketSize = SimQPNConfiguration.TIME_HISTOGRAM_DEFAULT_BUCKET_SIZE;
	}

	/**
	 * Specifies the maximum number of buckets. This method should only be called before
	 * entries are added to the histogram.
	 *
	 * @param maxNumBuckets - number of buckets. If set to 0, a default value is used.
	 */
	public void setMaximumNumberOfBuckets(int maxNumBuckets) {
		if(maxNumBuckets > 0) {
			this.maxNumBuckets = maxNumBuckets;
		} else {
			this.maxNumBuckets = SimQPNConfiguration.TIME_HISTOGRAM_MAX_NUM_BUCKETS;
		}
	}

	//TODO: See how to deal with updateDelayTimeStats - print a WARNING
	
	/**
	 * addEntry - adds an entry to the histogram. 
	 * 
	 * @param time
	 */
	public void addEntry(double time)  {		
		int bucket = (int) Math.ceil(time / bucketSize) - 1;
		if (bucket < 0)
			bucket = 0; 
		else if (bucket >= numBuckets)  {			
			bucket = (bucket < maxNumBuckets ? bucket : maxNumBuckets - 1);
			if (numBuckets < maxNumBuckets)
				expandHistogram(bucket + 1);
		}			
		data[bucket]++;		
	}

	/**
	 * expandHistogram - expands the histogram to accomodate a specified number of buckets 
	 * 
	 * @param newNumBuckets
	 */
	public void expandHistogram(int newNumBuckets)  {		
		int[] dataTMP = data; 
		data = new int[newNumBuckets];
		numBuckets = newNumBuckets;
		System.arraycopy(dataTMP, 0, data, 0, dataTMP.length);				
	}

	/**
	 * add - combines another histogram into this one.
	 * 
	 * @param timeHistogram
	 */
	public void add(TimeHistogram timeHistogram)  {		
        if (timeHistogram != null) 
	        for (int i = 0; i < numBuckets; i++)
				data[i] += timeHistogram.data[i];
	}
	
	/**
	 * subtract - subtracts another histogram from this one. 
	 * Note that this can lead to negative counts if not use with care.
	 * 
	 * @param timeHistogram
	 */
	public void subtract(TimeHistogram timeHistogram)  {		
        if (timeHistogram != null)
	        for (int i = 0; i < numBuckets; i++) 
				data[i] -= timeHistogram.data[i];
	}

	/**
	 * reset - resets the histogram.
	 * 
	 */	
	public void reset()  {
		for (int i=0; i < data.length; i++)
			data[i] = 0;
	}

	/**
	 * toString - returns the histogram data as a string.
	 * 
	 */	
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		boolean first = true;
		for (int i = 0; i < data.length; i++) {
			if (first) first = false;
			else buffer.append(", ");
			buffer.append(data[i]);
		}
		return buffer.toString();
	}

	/**
	 * getNumBuckets - returns the number of buckets.
	 * 
	 */	
	public int getNumBuckets() {
		return numBuckets;
	}

	/**
	 * getBucketSize - returns the bucket size.
	 * 
	 */	
	public double getBucketSize() {
		return bucketSize;
	}

	/**
	 * getMaximumNumberOfBuckets - returns the maximum number of buckets.
	 *
	 */
	public int getMaximumNumberOfBuckets() {
		return maxNumBuckets;
	}

	/**
	 * getData - returns the histogram data.
	 * 
	 */	
	public int[] getData() {
		return data;
	}


	/**
	 * getPercentile - returns the approximate value of P(X) for the sample population. 
	 * The value returned is the upperbound of the bucket in which the X percentile lies.
	 * 
	 * @return value of X percentile
	 */
	public double getPercentile(double Xpercentile)  {
		int sum = 0;
		int i;
		for(i = 0; i < numBuckets; i++)
			sum += data[i];				
		double totalSumPercentile = (double) (Xpercentile * sum);		
		if (totalSumPercentile == 0)
			return 0;			
		// Find which bucket the totalPercentile value lies in
		sum = 0;
		for (i = 0; i < numBuckets && sum < totalSumPercentile; i++)
			sum += data[i];
		// Now return the bucket in terms of the original scale
		return (i * bucketSize);		
	}

	/**
	 * getMean - return the mean value of the histogram
	 * 
	 * @return 
	 */	
	public double getMean()  {
		int total = 0;
		double sum = 0; 
		for(int i = 0; i < numBuckets; i++)
			total += data[i]; 
		for(int i = 0; i < numBuckets; i++)			
			sum += ((bucketSize * i) + (bucketSize / 2)) * (((double) data[i]) / total);		
		return sum;
	}		
	
}
