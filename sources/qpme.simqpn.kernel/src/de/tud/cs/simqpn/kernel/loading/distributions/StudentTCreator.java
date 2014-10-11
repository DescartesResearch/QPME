package de.tud.cs.simqpn.kernel.loading.distributions;

import cern.jet.random.AbstractContinousDistribution;
import cern.jet.random.StudentT;
import de.tud.cs.simqpn.kernel.RandomNumberGenerator;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.util.LogUtil;

public class StudentTCreator extends DistributionCreator {

	double freedom = -1;
	
	@Override
	protected void loadParams() throws SimQPNException {
		freedom = this.loadPositiveDoubleParam("freedom");
	}

	@Override
	public AbstractContinousDistribution getDistribution()
			throws SimQPNException {
		return new StudentT(freedom, RandomNumberGenerator.nextRandNumGen());
	}

	@Override
	public double getMean() {
		//NOTE: The mean of the StudentT distribution is 0 for freedom > 1, otherwise it is undefined.	
		if (freedom <= 1) {
			log.warn(LogUtil.formatDetailMessage(
					"meanServTimes for " + this.getClass().getName() + " not initialized! Might experience problems if indrStats is set to true!")); 
		}
		
		return 0;
	}

	@Override
	public String getConstructionText() {
		return "(" + freedom + ", Simulator.nextRandNumGen()";
	}

	@Override
	public String getMeanComputationText() {
		if (freedom <= 1) {
			return "Undefined, used 0.0";
		}
		
		return "0";
	}

}
