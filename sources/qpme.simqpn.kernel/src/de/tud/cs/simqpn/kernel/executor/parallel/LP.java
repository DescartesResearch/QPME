package de.tud.cs.simqpn.kernel.executor.parallel;


/**
 * LogicalProcess
 */
public class LP {

	private double clock = 0;

	public double getTimeSaveToProcess(LP sucessor) {
		return clock + getLookahead(sucessor);
	}

	private double getLookahead(LP sucessor) {
		double loookahead = 0;
		//sucessor.place.;
		
		return loookahead;
	}

}
