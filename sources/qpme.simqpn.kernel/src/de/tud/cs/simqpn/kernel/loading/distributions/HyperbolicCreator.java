package de.tud.cs.simqpn.kernel.loading.distributions;

import cern.jet.random.AbstractContinousDistribution;
import cern.jet.random.Hyperbolic;
import de.tud.cs.simqpn.kernel.RandomNumberGenerator;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.util.LogUtil;

public class HyperbolicCreator extends DistributionCreator {

	double alpha = -1;
	double beta = -1;
	
	@Override
	protected void loadParams() throws SimQPNException {
		this.alpha = this.loadPositiveDoubleParam("alpha");
		this.beta = this.loadPositiveDoubleParam("beta");
	}

	@Override
	public AbstractContinousDistribution getDistribution()
			throws SimQPNException {
		return new Hyperbolic(alpha, beta, RandomNumberGenerator.nextRandNumGen());
	}

	@Override
	public double getMean() {
		//SDK-TODO: find out how meanServTimes is computed?						
		//qPl.meanServTimes[j] = (double) ???;

		log.warn(LogUtil.formatDetailMessage(
				"meanServTimes for Hyperbolic distribution not initialized!" +
				"Might experience problems if indrStats is set to true!"));		
		
		return 0;
	}

	@Override
	public String getConstructionText() {
		return "(" + alpha + ", " + beta + ", Simulator.nextRandNumGen())";
	}

	@Override
	public String getMeanComputationText() {
		return "Undefined, used 0";
	}

}
