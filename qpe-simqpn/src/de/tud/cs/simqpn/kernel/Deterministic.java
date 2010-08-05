package de.tud.cs.simqpn.kernel;

import cern.jet.random.AbstractContinousDistribution;

/**
 * A deterministic distribution that always returns the double passed to the
 * constructor.
 * 
 * @author Philipp Meier
 * 
 */
public class Deterministic extends AbstractContinousDistribution {

	private double value;

	public Deterministic(double value) {
		super();
		this.value = value;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public double nextDouble() {
		return value;
	}

}
