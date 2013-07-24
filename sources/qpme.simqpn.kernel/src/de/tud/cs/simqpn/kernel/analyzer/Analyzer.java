package de.tud.cs.simqpn.kernel.analyzer;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;
import de.tud.cs.simqpn.kernel.stats.Stats;

public interface Analyzer {

	public Stats[] analyze(Net net, SimQPNConfiguration configuration,
			SimulatorProgress monitor)
			throws SimQPNException;

}
