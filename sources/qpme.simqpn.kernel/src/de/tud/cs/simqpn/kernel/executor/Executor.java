package de.tud.cs.simqpn.kernel.executor;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Queue;
import de.tud.cs.simqpn.kernel.entities.Token;
import de.tud.cs.simqpn.kernel.monitor.SimulatorProgress;

public interface Executor {
	public double getClock();
	public void scheduleEvent(double serviceTime, Queue queue, Token token);
	public void removeEvent(QueueEvent event);
	public SimQPNConfiguration getConfiguration();
	public SimulatorProgress getProgressMonitor();
	public void run()throws SimQPNException;
}
