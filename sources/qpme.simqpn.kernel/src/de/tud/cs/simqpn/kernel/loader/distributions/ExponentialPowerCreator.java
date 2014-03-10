package de.tud.cs.simqpn.kernel.loader.distributions;

import cern.jet.random.AbstractContinousDistribution;
import cern.jet.random.ExponentialPower;
import de.tud.cs.simqpn.kernel.RandomNumberGenerator;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.util.LogUtil;

public class ExponentialPowerCreator extends DistributionCreator {

	double tau = -1;
	
	@Override
	protected void loadParams() throws SimQPNException {
		tau = this.loadAtLeastOneDoubleParam("tau");
	}

	@Override
	public AbstractContinousDistribution getDistribution()
			throws SimQPNException {
		return new ExponentialPower(tau, RandomNumberGenerator.nextRandNumGen());
	}

	@Override
	public double getMean() {
		//SDK-TODO: find out how meanServTimes is computed?	
		log.warn(LogUtil.formatDetailMessage(
				"meanServTimes for " + this.getClass().getName() + " not initialized!" +
				"Might experience problems if indrStats is set to true!"));		

		return 0;
	}

	@Override
	public String getConstructionText() {
		return "(" + tau + ", Simulator.nextRandNumGen())";
	}

	@Override
	public String getMeanComputationText() {
		return "Undefined, used 0.0";
	}

}
