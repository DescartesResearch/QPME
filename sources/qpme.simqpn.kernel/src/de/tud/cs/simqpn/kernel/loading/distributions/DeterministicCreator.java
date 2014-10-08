package de.tud.cs.simqpn.kernel.loading.distributions;

import cern.jet.random.AbstractContinousDistribution;
import de.tud.cs.simqpn.kernel.SimQPNException;

public class DeterministicCreator extends DistributionCreator {

	double p1 = -1;
	
	@Override
	protected void loadParams() throws SimQPNException {
		p1 = this.loadDoubleParam("p1");

		if (p1 < 0) {
			throw new SimQPNException("Invalid \"" + "p1" + "\" parameter (" + p1 + ") for " + this.getClass().getName() + "!");
		}
	}

	@Override
	public AbstractContinousDistribution getDistribution()
			throws SimQPNException {
		return new Deterministic(p1);
	}

	@Override
	public double getMean() {
		return p1;
	}

	@Override
	public String getConstructionText() {
		return "(" + p1 + ")";
	}

	@Override
	public String getMeanComputationText() {
		return "p1";
	}

}
