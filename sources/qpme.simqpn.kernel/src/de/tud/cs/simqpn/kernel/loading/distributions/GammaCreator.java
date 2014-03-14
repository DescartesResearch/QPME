package de.tud.cs.simqpn.kernel.loading.distributions;

import cern.jet.random.AbstractContinousDistribution;
import cern.jet.random.Gamma;
import de.tud.cs.simqpn.kernel.RandomNumberGenerator;
import de.tud.cs.simqpn.kernel.SimQPNException;

public class GammaCreator extends DistributionCreator {

	double alpha = -1;
	double lambda = -1;
	
	@Override
	protected void loadParams() throws SimQPNException {
		alpha = this.loadPositiveDoubleParam("alpha");
		lambda = this.loadPositiveDoubleParam("lambda");
	}

	@Override
	public AbstractContinousDistribution getDistribution()
			throws SimQPNException {
		return new Gamma(alpha, lambda, RandomNumberGenerator.nextRandNumGen());
	}

	@Override
	public double getMean() {
		return alpha * lambda;
	}

	@Override
	public String getConstructionText() {
		return "(" + alpha + ", " + lambda + ", Simulator.nextRandNumGen())";
	}

	@Override
	public String getMeanComputationText() {
		return "alpha * lambda";
	}

}
