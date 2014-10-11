package de.tud.cs.simqpn.kernel.loading.distributions;

import cern.jet.random.AbstractContinousDistribution;
import cern.jet.random.Uniform;
import de.tud.cs.simqpn.kernel.RandomNumberGenerator;
import de.tud.cs.simqpn.kernel.SimQPNException;

public class UniformCreator extends DistributionCreator {

	double min = -1;
	double max = -1;
	
	@Override
	protected void loadParams() throws SimQPNException {
		min = this.loadDoubleParam("min");
		max = this.loadDoubleParam("max");
		
		if (max <= min) {
			throw new SimQPNException("Max parameter(" + max + ") must be more than min parameter (" + min +
					") for " + this.getClass().getName() + "!");
		}
	}

	@Override
	public AbstractContinousDistribution getDistribution()
			throws SimQPNException {
		return new Uniform(min, max, RandomNumberGenerator.nextRandNumGen());
	}

	@Override
	public double getMean() {
		return (double) (min + max) / 2;
	}

	@Override
	public String getConstructionText() {
		return "(" + min + ", " + max + ", Simulator.nextRandNumGen())";
	}

	@Override
	public String getMeanComputationText() {
		return "(double) (min + max) / 2";
	}

}
