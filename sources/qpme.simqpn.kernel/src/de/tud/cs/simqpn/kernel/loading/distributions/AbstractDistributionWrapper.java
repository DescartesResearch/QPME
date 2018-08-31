package de.tud.cs.simqpn.kernel.loading.distributions;

import de.tud.cs.simqpn.kernel.entities.QPlace;

public class AbstractDistributionWrapper implements AbstractDistribution {

	private cern.jet.random.AbstractDistribution innerDistribution;

	public AbstractDistributionWrapper(cern.jet.random.AbstractDistribution innerDistribution) {
		this.innerDistribution = innerDistribution;
	}

	@Override
	public double nextDouble(QPlace qplace, int color) {
		return innerDistribution.nextDouble();
	}


}
