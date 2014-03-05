package de.tud.cs.simqpn.kernel.executor.parallel.decomposition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.tud.cs.simqpn.kernel.executor.parallel.LP;

public class RecursionDepthCalculator {

	/**
	 * Searches the longest path in passed LP tree. Considers multiple roots if
	 * existent.
	 * 
	 * @param lps
	 * @return The length
	 */
	public static int getRecursionDepth(LP[] lps) {
		int maxPath = 0;
		for (LP lp : lps) {
			if (lp.isWorkloadGenerator()) {
				int length = getRecursionDepth(lp);
				maxPath = (length > maxPath) ? length : maxPath;
			}
		}
		return maxPath;
	}

	private static int getRecursionDepth(LP lp) {
		List<Integer> pathLengths = new ArrayList<>();
		if (!lp.hasSuccessor()) {
			return 1;
		} else {
			for (LP successor : lp.getSuccessors()) {
				pathLengths.add(1 + getRecursionDepth(successor));
			}
		}
		return Collections.max(pathLengths);
	}

}
