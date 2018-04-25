package de.tud.cs.simqpn.kernel.loading.distributions;

public class AbstractDistributionWrapper implements AbstractDistribution {

	private cern.jet.random.AbstractDistribution innerDistribution;

	public AbstractDistributionWrapper(cern.jet.random.AbstractDistribution innerDistribution) {
		this.innerDistribution = innerDistribution;
	}

	@Override
	public double nextDouble(int concurrency) {
		return innerDistribution.nextDouble();
	}


}
