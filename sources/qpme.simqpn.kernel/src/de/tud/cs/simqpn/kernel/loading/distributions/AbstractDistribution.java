package de.tud.cs.simqpn.kernel.loading.distributions;

import de.tud.cs.simqpn.kernel.entities.QPlace;

public interface AbstractDistribution {

	public double nextDouble(QPlace qplace, int color);

}
