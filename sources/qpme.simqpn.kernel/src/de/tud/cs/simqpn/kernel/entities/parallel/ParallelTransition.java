package de.tud.cs.simqpn.kernel.entities.parallel;

import java.util.List;

import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Transition;

public class ParallelTransition extends Transition {
	/**
	 * Ideas:
	 * 
	 * Branching decisions will be generated "live", because this is depends on
	 * the enabled modes and we see no benefit in previous calculation
	 */

	/** branchingDecissions */
	List<Double>[] futureArray;

	boolean[] futureListInit; // TODO make this a bitmap, because boolean is
								// internally mapped to int in java

	public ParallelTransition(int id, String name, int numModes,
			int numInPlaces, int numOutPlaces, int numProbes, double transWeight)
			throws SimQPNException {
		super(id, name, numModes, numInPlaces, numOutPlaces, numProbes,
				transWeight);
	}

	void fire() {
		int mode;
		int nM = numModes;
		// Choose mode to fire based on weights
		if (enModesCnt == 1) {
			mode = -1;
			for (int m = 0; m < nM; m++) {
				if (modeStatus[m]) {
					mode = m;
					break;
				}
			}
		} else {
			double[] pdf = new double[enModesCnt];
			int[] enModesIDs = new int[enModesCnt];
			for (int m = 0, e = 0; m < nM; m++) {
				if (modeStatus[m]) {
					pdf[e] = modeWeights[m];
					enModesIDs[e] = m;
					e++;
				}
			}
			randModeGen.setState2(pdf);
			mode = enModesIDs[randModeGen.nextInt()];
		}
		// TODO do the rest
	}

	public void initFutureList() {
		for (int i = 0; i < this.numModes; i++) {
			futureArray[i].add(Math.random());
		}
	}

	private boolean futureListFilled() {
		for (int i = 0; i < this.numModes; i++) {
			if (!futureListInit[i]) {
				return false;
			}
		}
		return true;
	}

}
