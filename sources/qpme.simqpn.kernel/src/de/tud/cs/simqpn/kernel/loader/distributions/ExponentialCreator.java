package de.tud.cs.simqpn.kernel.loader.distributions;

import cern.jet.random.AbstractContinousDistribution;
import cern.jet.random.Exponential;
import de.tud.cs.simqpn.kernel.RandomNumberGenerator;
import de.tud.cs.simqpn.kernel.SimQPNException;

public class ExponentialCreator extends DistributionCreator {

	double lambda = -1;
	
	@Override
	protected void loadParams() throws SimQPNException {
		lambda = this.loadPositiveDoubleParam("lambda");
	}

	@Override
	public AbstractContinousDistribution getDistribution()
			throws SimQPNException {
		return new Exponential(lambda, RandomNumberGenerator.nextRandNumGen());
	}

	@Override
	public double getMean() {
		return (double) 1 / lambda;
	}

	@Override
	public String getConstructionText() {
		return "(" + lambda + ", Simulator.nextRandNumGen())";
	}

	@Override
	public String getMeanComputationText() {
		return "1 / lambda";
	}

}
