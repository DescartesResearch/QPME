package de.tud.cs.simqpn.kernel.loading.distributions;

import cern.jet.random.AbstractContinousDistribution;

public class Periodical extends AbstractContinousDistribution {

	private static final long serialVersionUID = -6171122408442436886L;

	private final double[] data;
	private int currentPos = 0;
	
	double pdf[] = null;
	String pdfFilename = null;
	
	public Periodical(double[] data) {
		this.data = data;
		
		if (data.length == 0) {
			throw new IllegalArgumentException("Provided empty data");
		}
	}
	
	@Override
	public double nextDouble() {
		double result = data[currentPos];
		currentPos = (currentPos + 1) % data.length;
		return result;
	}

}
