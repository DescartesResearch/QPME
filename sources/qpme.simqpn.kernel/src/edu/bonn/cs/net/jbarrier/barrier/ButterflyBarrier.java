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
 * Implementation of a butterfly barrier algorithm. The butterfly barrier was
 * introduced in the following article:
 * <p>
 * E. D. Brooks III. "The Butterfly Barrier". In <i>International Journal of
 * Parallel Programming</i>, volume 15, pages 295-307, 1986.
 * <p>
 * A good introduction to different barrier synchronization algorithms an be
 * found in the following technical report:
 * <p>
 * C. Ball and M. Bull. "Barrier Synchronisation in Java".
 * <p>
 * At the time of writing the report is available online at
 * www.ukhec.ac.uk/publications/reports/synch_java.pdf
 * 
 * @version 1.0
 * 
 * @author Patrick Peschlow
 * @author Ivan Castilla Rodriguez
 */
public class ButterflyBarrier extends AbstractBarrier {
	/**
	 * The number of rounds used for the barrier.
	 */
	protected final int numRounds_;

	/**
	 * The barrier information associated to each competitor thread.
	 */
	protected ButterflyBarrierParty[] parties_;

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
	public ButterflyBarrier(int numParties, Runnable barrierAction,
		GenericReductor genericReductor) {
		super(numParties, barrierAction, genericReductor);
		if (!Utils.isPowerOfTwo(numParties_)) {
			throw new IllegalArgumentException(
				"Butterfly barrier currently requires the number of parties to be a power of two!");
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
	 * @param action
	 *            the command to execute when the barrier is tripped, or
	 *            <code>null</code> if there is no action
	 * @throws IllegalArgumentException
	 *             if <code>numParties</code> is not a power of two
	 */
	public ButterflyBarrier(int numParties, Runnable action) {
		this(numParties, action, null);
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
	public ButterflyBarrier(int numParties) {
		this(numParties, null);
	}

	/**
	 * Sets up the parties array, intended to be overridden in subclasses.
	 */
	protected void setUpParties() {
		parties_ = new ButterflyBarrierParty[numParties_];
		for (int i = 0; i < numParties_; i++) {
			parties_[i] = new ButterflyBarrierParty(i);
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
	protected class ButterflyBarrierParty {
		/**
		 * Unique id of this party [0 .. numParties-1].
		 */
		protected final int id_;

		/**
		 * Array of incoming flags for this thread for each round. First key:
		 * parity (0 or 1). Second key: round number. (Note that a volatile
		 * boolean[][] is not enough here, because in that case updates would
		 * only be triggered when the array reference itself is changed but not
		 * when single array elements are modified.)
		 */
		protected final AtomicBoolean[][] flagsIn_;

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
		 * Pre-computed array of references to the partners of this party in
		 * each round.
		 */
		protected ButterflyBarrierParty[] partners_;

		/**
		 * Constructor.
		 * 
		 * @param id
		 *            the numeric id of this party within the barrier.
		 */
		protected ButterflyBarrierParty(int id) {
			id_ = id;
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
			partners_ = new ButterflyBarrierParty[numRounds_];
		}

		/**
		 * Sets up this party for the barrier algorithm.
		 */
		protected void setupBarrier() {
			for (int round = 0; round < numRounds_; round++) {
				final int partner =
					(id_ ^ Utils.powerOfTwo(round)) % numParties_;
				partners_[round] = parties_[partner];
			}
		}

		/**
		 * Called when this party reaches the barrier.
		 */
		protected void await() {
			for (int round = 0; round < numRounds_; round++) {
				partners_[round].flagsIn_[parity_][round].set(sense_);
				while (flagsIn_[parity_][round].get() != sense_)
					;
				if (genericReductor_ != null) {
					genericReductor_.reduce(id_, partners_[round].id_);
				}
			}
			if (parity_ == 1) {
				sense_ = !sense_;
			}
			parity_ = 1 - parity_;
			// If there is a barrier action, let thread 0 execute it.
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
