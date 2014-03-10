package de.tud.cs.simqpn.kernel.loader.distributions;

import cern.jet.random.AbstractContinousDistribution;
import cern.jet.random.Beta;
import de.tud.cs.simqpn.kernel.RandomNumberGenerator;
import de.tud.cs.simqpn.kernel.SimQPNException;

public class BetaCreator extends DistributionCreator {

	double alpha = -1;
	double beta = -1;
	
	@Override
	public AbstractContinousDistribution getDistribution() throws SimQPNException {
		return new Beta(alpha, beta, RandomNumberGenerator.nextRandNumGen());
	}

	@Override
	public String getConstructionText() {
		return "(" + alpha + ", " + beta + ", Simulator.nextRandNumGen())";
	}

	@Override
	public String getMeanComputationText() {
		return "alpha / (alpha + beta)";
	}
	
	@Override
	public double getMean() {
		return (double) alpha / (alpha + beta);
	}

	@Override
	protected void loadParams() throws SimQPNException {
		alpha = this.loadPositiveDoubleParam("alpha");
		beta = this.loadPositiveDoubleParam("beta");						
	}
}
