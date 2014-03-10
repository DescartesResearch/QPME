package de.tud.cs.simqpn.kernel.loader.distributions;

import cern.jet.random.AbstractContinousDistribution;
import cern.jet.random.Logarithmic;
import de.tud.cs.simqpn.kernel.RandomNumberGenerator;
import de.tud.cs.simqpn.kernel.SimQPNException;

public class LogarithmicCreator extends DistributionCreator {

	double p = -1;
	
	@Override
	protected void loadParams() throws SimQPNException {
		p = this.loadDoubleParam("p");
		
		if (!(0 < p && p < 1)) {		
			throw new SimQPNException("Invalid \"p\" parameter for" + this.getClass().getName() + "!");
		}
	}

	@Override
	public AbstractContinousDistribution getDistribution()
			throws SimQPNException {
		
		return new Logarithmic(p, RandomNumberGenerator.nextRandNumGen());
	}

	@Override
	public double getMean() {
		return (double) ((-1) * p) / (Math.log(1-p) * (1-p));
	}

	@Override
	public String getConstructionText() {
		return "(" + p + ", Simulator.nextRandNumGen())";
	}

	@Override
	public String getMeanComputationText() {
		return "((-1) * p) / (Math.log(1-p) * (1-p))";
	}

}
