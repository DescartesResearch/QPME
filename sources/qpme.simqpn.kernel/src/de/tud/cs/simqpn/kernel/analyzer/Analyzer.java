package de.tud.cs.simqpn.kernel.analyzer;

import org.dom4j.Element;

import de.tud.cs.simqpn.kernel.SimQPNController;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;
import de.tud.cs.simqpn.kernel.stats.Stats;

public interface Analyzer {

	Stats[] analyze(Element XMLNet, String configuration, SimulatorProgress monitor, SimQPNController sim) throws SimQPNException;

}
