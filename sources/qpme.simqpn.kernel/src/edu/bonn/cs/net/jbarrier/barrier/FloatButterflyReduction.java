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

import edu.bonn.cs.net.jbarrier.util.Utils;

/**
 * Implementation of a butterfly barrier algorithm including a float reduction.
 * 
 * @version 1.0
 * 
 * @author Patrick Peschlow
 * @author Ivan Castilla Rodriguez
 */
public class FloatButterflyReduction extends ButterflyBarrier implements
	FloatReduction {
	/**
	 * The reduction operator used.
	 */
	private ReductionOperator reductor_;

	/**
	 * The barrier data associated to each party.
	 */
	@SuppressWarnings("hiding")
	private ButterflyReductionParty[] parties_;

	/**
	 * Constructor.
	 * 
	 * @param numParties
	 *            the number of parties that must reach the barrier before the
	 *            barrier is tripped
	 * @param barrierAction
	 *            the command to execute when the barrier is tripped, or
	 *            <code>null</code> if there is no action
	 * @param reductor
	 *            the reduction operator to use
	 * @throws IllegalArgumentException
	 *             if <code>numParties</code> is not a power of two
	 */
	public FloatButterflyReduction(int numParties, Runnable barrierAction,
		ReductionOperator reductor) {
		super(numParties, barrierAction);
		reductor_ = reductor;
	}

	/**
	 * Constructor.
	 * 
	 * @param numParties
	 *            the number of parties that must reach the barrier before the
	 *            barrier is tripped
	 * @param reductor
	 *            the reduction operator to use
	 * @throws IllegalArgumentException
	 *             if <code>numParties</code> is not a power of two
	 */
	public FloatButterflyReduction(int numParties, ReductionOperator reductor) {
		this(numParties, null, reductor);
	}

	/**
	 * Sets up the parties array required for this reduction subclass.
	 */
	@Override
	protected void setUpParties() {
		parties_ = new ButterflyReductionParty[numParties_];
		for (int i = 0; i < numParties_; i++) {
			parties_[i] = new ButterflyReductionParty(i);
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
	 * @param value
	 *            the value subject to the reduction
	 * @return the result of the reduction
	 */
	@Override
	public float await(int threadId, float value) {
		return parties_[threadId].await(value);
	}

	/**
	 * Stores data required by each party that uses the barrier.
	 * 
	 * @version 1.0
	 * 
	 * @author Patrick Peschlow
	 * @author Ivan Castilla Rodriguez
	 */
	private class ButterflyReductionParty extends ButterflyBarrierParty {
		/**
		 * Pre-computed array of references to the partners of this party in
		 * each round.
		 */
		@SuppressWarnings("hiding")
		private ButterflyReductionParty[] partners_;

		/**
		 * Intermediate values used during the reduction. First key: parity (0
		 * or 1). Second key: round number.
		 */
		private final float[][] values_;

		/**
		 * Constructor.
		 * 
		 * @param id
		 *            the numeric id of this party within the barrier
		 */
		private ButterflyReductionParty(int id) {
			super(id);
			values_ = new float[2][numRounds_ + 1];
		}

		/**
		 * Sets up the partners array for this reduction subclass.
		 */
		@Override
		protected void setUpPartyData() {
			partners_ = new ButterflyReductionParty[numRounds_];
		}

		/**
		 * Sets up the partners array for this reduction subclass.
		 */
		@Override
		protected void setupBarrier() {
			for (int round = 0; round < numRounds_; round++) {
				final int partner =
					(id_ ^ Utils.powerOfTwo(round)) % numParties_;
				partners_[round] = parties_[partner];
			}
		}

		/**
		 * Called when this party reaches the barrier.
		 * 
		 * @param value
		 *            the value contributed to the reduction by this thread
		 * @return the result of the reduction
		 */
		private float await(float value) {
			values_[parity_][0] = value;
			for (int round = 0; round < numRounds_; round++) {
				partners_[round].flagsIn_[parity_][round].set(sense_);
				while (flagsIn_[parity_][round].get() != sense_)
					;
				values_[parity_][round + 1] =
					reductor_.operator(values_[parity_][round],
						partners_[round].values_[parity_][round]);
			}
			final float result = values_[parity_][numRounds_];
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
			return result;
		}
	}
}
