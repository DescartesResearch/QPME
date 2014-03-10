package de.tud.cs.simqpn.kernel.loader.distributions;

import cern.jet.random.AbstractContinousDistribution;
import cern.jet.random.ChiSquare;
import de.tud.cs.simqpn.kernel.RandomNumberGenerator;
import de.tud.cs.simqpn.kernel.SimQPNException;

public class ChiSquareCreator extends DistributionCreator {

	double freedom = -1;
	
	@Override
	protected void loadParams() throws SimQPNException {
		freedom = this.loadPositiveDoubleParam("freedom");
	}

	@Override
	public AbstractContinousDistribution getDistribution()
			throws SimQPNException {
		return new ChiSquare(freedom, RandomNumberGenerator.nextRandNumGen());
	}

	@Override
	public double getMean() {
		return freedom;
	}

	@Override
	public String getConstructionText() {
		return "(" + freedom + ", Simulator.nextRandNumGen())";
	}

	@Override
	public String getMeanComputationText() {
		return "freedom";
	}

}
