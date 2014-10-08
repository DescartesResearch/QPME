package de.tud.cs.simqpn.kernel.loading.distributions;

import cern.jet.random.AbstractContinousDistribution;
import cern.jet.random.Normal;
import de.tud.cs.simqpn.kernel.RandomNumberGenerator;
import de.tud.cs.simqpn.kernel.SimQPNException;

public class NormalCreator extends DistributionCreator {

	double mean = -1;
	double stdDev = -1;
	
	@Override
	protected void loadParams() throws SimQPNException {
		mean = this.loadDoubleParam("mean");
		stdDev = this.loadPositiveDoubleParam("stdDev");
	}

	@Override
	public AbstractContinousDistribution getDistribution()
			throws SimQPNException {
		return new Normal(mean, stdDev, RandomNumberGenerator.nextRandNumGen());
	}

	@Override
	public double getMean() {
		return mean;
	}

	@Override
	public String getConstructionText() {
		return "(" + mean + ", " + stdDev + ", Simulator.nextRandNumGen())";
	}

	@Override
	public String getMeanComputationText() {
		return "mean";
	}

}
