/*
 * Copyright 2010 Patrick Peschlow
 * 
 * This file is part of the jbarrier library.
 * 
 * The jbarrier library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or (at your
 * option) any later version.
 * 
 * The jbarrier library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with the jbarrier library; if not, see <http://www.gnu.org/licenses>.
 */
package edu.bonn.cs.net.jbarrier.barrier;

import java.util.concurrent.atomic.AtomicBoolean;

import edu.bonn.cs.net.jbarrier.util.Utils;

/**
 * Implementation of a dissemination barrier algorithm. The dissemination
 * barrier was introduced in the following article:
 * <p>
 * D. Hensgen, R. Finkel, and U. Manber.
 * "Two Algorithms for Barrier Synchronization". In <i>International Journal of
 * Parallel Programming</i>, volume 17, pages 1-17, 1988.
 * <p>
 * The following two technical reports may server as good a introduction to
 * different barrier synchronization algorithms:
 * <ul>
 * <li>C. Ball and M. Bull. "Barrier Synchronisation in Java". 2003. (available
 * online at www.ukhec.ac.uk/publications/reports/synch_java.pdf at the time of
 * writing)
 * <li>T. Hoefler, T. Mehlan, F. Mietke, and W. Rehm.
 * "A Survey of Barrier Algorithms for Coarse Grained Supercomputers". Technical
 * University of Chemnitz, 2004
 * </ul>
 * 
 * @version 1.0
 * 
 * @author Patrick Peschlow
 * @author Ivan Castilla Rodriguez
 */
public class DisseminationBarrier extends AbstractBarrier {
	/**
	 * The number of rounds used for the barrier.
	 */
	protected final int numRounds_;

	/**
	 * The barrier information associated to each competitor thread.
	 */
	protected DisseminationBarrierParty[] parties_;

	/**
	 * Global out flag (only used if there is a barrier action).
	 */
	protected volatile boolean flagOut_;

	/**
	 * Constructor.
	 * 
	 * @param numParties
	 *            the number of parties that must reach the barrier before the
	 *            barrier is tripped
	 * @param barrierAction
	 *            the command to execute when the barrier is tripped, or
	 *            <code>null</code> if there is no action
	 * @param genericReductor
	 *            an optional generic reductor
	 * @throws IllegalArgumentException
	 *             if <code>numParties</code> is not a power of two
	 */
	public DisseminationBarrier(int numParties, Runnable barrierAction,
		GenericReductor genericReductor) {
		super(numParties, barrierAction, genericReductor);
		if (!Utils.isPowerOfTwo(numParties_)) {
			throw new IllegalArgumentException(
				"Dissemination barrier currently requires the number of parties to be a power of two!");
		}
		numRounds_ = (int) Math.ceil(Math.log(numParties_) / Math.log(2.0));
		flagOut_ = false;
		setUpParties();
	}

	/**
	 * Constructor.
	 * 
	 * @param numParties
	 *            the number of parties that must reach the barrier before the
	 *            barrier is tripped
	 * @param barrierAction
	 *            the command to execute when the barrier is tripped, or
	 *            <code>null</code> if there is no action
	 * @throws IllegalArgumentException
	 *             if <code>numParties</code> is not a power of two
	 */
	public DisseminationBarrier(int numParties, Runnable barrierAction) {
		this(numParties, barrierAction, null);
	}

	/**
	 * Constructor (if no action is used).
	 * 
	 * @param numParties
	 *            the number of parties that must reach the barrier before the
	 *            barrier is tripped
	 * @throws IllegalArgumentException
	 *             if <code>numParties</code> is not a power of two
	 */
	public DisseminationBarrier(int numParties) {
		this(numParties, null);
	}

	/**
	 * Sets up the parties array, intended to be overridden in subclasses.
	 */
	protected void setUpParties() {
		parties_ = new DisseminationBarrierParty[numParties_];
		for (int i = 0; i < numParties_; i++) {
			parties_[i] = new DisseminationBarrierParty(i);
		}
		for (int i = 0; i < numParties_; i++) {
			parties_[i].setupBarrier();
		}
	}

	/**
	 * Called by a party that reaches the barrier.
	 * 
	 * @param threadId
	 *            the ID of the party
	 */
	@Override
	public void await(int threadId) {
		parties_[threadId].await();
	}

	/**
	 * Stores data required by each party that uses the barrier.
	 * 
	 * @author Patrick Peschlow
	 * @author Ivan Castilla Rodriguez
	 */
	protected class DisseminationBarrierParty {
		/**
		 * Unique id of this party [0 .. numParties-1].
		 */
		protected final int id_;

		/**
		 * Parity bit for alternating barrier episodes.
		 */
		protected int parity_;

		/**
		 * Sense flag that switches between <code>true</code> and
		 * <code>false</code>.
		 */
		protected boolean sense_;

		/**
		 * Sense flag for the global out flag (only used if there is a barrier
		 * action).
		 */
		protected boolean outSense_;

		/**
		 * Array of incoming flags for this thread for each round. First key:
		 * parity (0 or 1). Second key: round number. (Note that a volatile
		 * boolean[][] is not enough here, because in that case updates would
		 * only be triggered when the array reference itself is changed but not
		 * when single array elements are modified.)
		 */
		protected final AtomicBoolean[][] flagsIn_;

		/**
		 * Pre-computed array of references to my outgoing partners (whose flag
		 * I set) in every round.
		 */
		protected DisseminationBarrierParty[] partnersOut_;

		/**
		 * Pre-computed array of thread IDs of my incoming partners (those that
		 * set my flag) in every round. Used for generic reduction only.
		 */
		private int[] partnersIn_;

		/**
		 * Constructor.
		 * 
		 * @param threadId
		 *            the numeric id of this party within the barrier.
		 */
		protected DisseminationBarrierParty(int threadId) {
			id_ = threadId;
			parity_ = 0;
			sense_ = false;
			outSense_ = false;
			flagsIn_ = new AtomicBoolean[2][numRounds_];
			for (int round = 0; round < numRounds_; round++) {
				flagsIn_[0][round] = new AtomicBoolean(!sense_);
				flagsIn_[1][round] = new AtomicBoolean(!sense_);
			}
			setUpPartyData();
		}

		/**
		 * Sets up the partners array, intended to be overridden in subclasses.
		 */
		protected void setUpPartyData() {
			partnersOut_ = new DisseminationBarrierParty[numRounds_];
			partnersIn_ = new int[numRounds_];
		}

		/**
		 * Sets up this party for the barrier algorithm.
		 */
		protected void setupBarrier() {
			for (int round = 0; round < numRounds_; round++) {
				final int outPartner =
					(id_ + Utils.powerOfTwo(round)) % numParties_;
				partnersOut_[round] = parties_[outPartner];
				int inPartner = (id_ - Utils.powerOfTwo(round)) % numParties_;
				if (inPartner < 0) {
					inPartner += numParties_;
				}
				partnersIn_[round] = inPartner;
			}
		}

		/**
		 * Called when this party reaches the barrier.
		 */
		protected void await() {
			for (int round = 0; round < numRounds_; round++) {
				partnersOut_[round].flagsIn_[parity_][round].set(sense_);
				while (flagsIn_[parity_][round].get() != sense_)
					;
				if (genericReductor_ != null) {
					genericReductor_.reduce(id_, partnersIn_[round]);
				}
			}
			if (parity_ == 1) {
				sense_ = !sense_;
			}
			parity_ = 1 - parity_;
			if (action_ != null) {
				outSense_ = !outSense_;
				if (id_ == 0) {
					action_.run();
					flagOut_ = outSense_;
				} else {
					while (flagOut_ != outSense_)
						;
				}
			}
		}
	}
}
