/*
 * Copyright (c) 2009 Samuel Kounev. All rights reserved.
 *    
 * The use, copying, modification or distribution of this software and its documentation for 
 * any purpose is NOT allowed without the written permission of the author.
 *  
 * This source code is provided as is, without any express or implied warranty.
 *
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------
 *  2008/12/14  Samuel Kounev     Created.
 *  
 */

/**
 * Class TimeHistogram
 * 
 * @author Samuel Kounev
 * @version %I%, %G%
 */

package de.tud.cs.simqpn.kernel;

import java.io.Serializable;

public class TimeHistogram implements Serializable  {
	private static final long serialVersionUID = 689255573778061L;

	private double			bucketSize;					// Size of buckets (granularity) 
	private int				numBuckets;					// Total number of buckets
	private int[]			data;						// Histogram data
	
	private final int       MIN_NUM_BUCKETS = 10;
	private final int       MAX_NUM_BUCKETS = 1000;
	private final double    DEFAULT_BUCKET_SIZE = 100;
	
	int count = 0;
	
	/**
	 * Constructor 
	 *
	 * @param bSize      - bucketSize in simulation time units. 
	 * @param upperBound - upper bound for time values in simulation time units. If set to 0, infinite upper bound 
	 *                     is assumed, i.e., the number of buckets automatically expands to accomodate observed values.  
	 * 
	 */	
	public TimeHistogram(double bSize, double upperBound)  {
		if (bSize > 0)
			this.bucketSize = bSize;
		else 
			this.bucketSize = DEFAULT_BUCKET_SIZE;
		
		if (upperBound > 0)  {
			if (upperBound < this.bucketSize) 
				Simulator.logln("WARNING: upperBound < bucketSize for a histogram! The number of buckets will be zero!");
			this.numBuckets = (int) Math.ceil(upperBound / this.bucketSize);			
		}
		else this.numBuckets = MIN_NUM_BUCKETS;
		
		this.data = new int[numBuckets];
		for (int i = 0; i < numBuckets; i++) this.data[i] = 0;
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
			bucket = (bucket < MAX_NUM_BUCKETS ? bucket : MAX_NUM_BUCKETS - 1);
			if (numBuckets < MAX_NUM_BUCKETS) 
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
