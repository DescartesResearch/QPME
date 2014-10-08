package de.tud.cs.simqpn.kernel.loading.distributions;

import cern.jet.random.AbstractContinousDistribution;
import cern.jet.random.BreitWignerMeanSquare;
import de.tud.cs.simqpn.kernel.RandomNumberGenerator;
import de.tud.cs.simqpn.kernel.SimQPNException;

public class BreitWignerMeanSquareCreator extends DistributionCreator {

	double mean = -1;
	double gamma = -1;
	double cut = -1;
	
	@Override
	protected void loadParams() throws SimQPNException {
		mean = this.loadDoubleParam("mean");
		gamma = this.loadPositiveDoubleParam("gamma");
		cut = this.loadDoubleParam("cut");
	}

	@Override
	public AbstractContinousDistribution getDistribution()
			throws SimQPNException {
		return new BreitWignerMeanSquare(mean, gamma, cut, RandomNumberGenerator.nextRandNumGen());
	}

	@Override
	public double getMean() {
		// NOTE: BreitWigner does not have a mean value! It is undefined. 
		return 0;
	}

	@Override
	public String getConstructionText() {
		return "(" + mean + ", " + gamma + ", " + cut + ", Simulator.nextRandNumGen())";
	}

	@Override
	public String getMeanComputationText() {
		return "Undefined, used 0.0";
	}

}
