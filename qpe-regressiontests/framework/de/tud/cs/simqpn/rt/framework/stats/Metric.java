package de.tud.cs.simqpn.rt.framework.stats;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;


public class Metric {
	
	public static final String CI_HALF_LEN_ST = "ciHalfLenST";
	public static final String STD_STATE_MEAN_ST = "stdStateMeanST";
	
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
