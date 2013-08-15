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

/**
 * Implementation of a static tree barrier algorithm including a float
 * reduction.
 * 
 * @version 1.0
 * 
 * @author Patrick Peschlow
 * @author Ivan Castilla Rodriguez
 */
public class FloatStaticTreeReduction extends StaticTreeBarrier implements
	FloatReduction {
	/**
	 * The reduction operator used.
	 */
	private final ReductionOperator reductor_;

	/**
	 * The barrier data associated to each party.
	 */
	@SuppressWarnings("hiding")
	private StaticTreeReductionParty[] parties_;

	/**
	 * Stores the result of the reduction.
	 */
	private float result_;

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
	public FloatStaticTreeReduction(int numParties, Runnable barrierAction,
		ReductionOperator reductor) {
		super(numParties, barrierAction);
		reductor_ = reductor;
		setUpParties();
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
	public FloatStaticTreeReduction(int numParties, ReductionOperator reductor) {
		this(numParties, null, reductor);
	}

	/**
	 * Sets up the parties array required for this reduction subclass.
	 */
	@Override
	protected void setUpParties() {
		parties_ = new StaticTreeReductionParty[numParties_];
		for (int i = 0; i < numParties_; i++) {
			parties_[i] = new StaticTreeReductionParty(i);
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
	private class StaticTreeReductionParty extends StaticTreeBarrierParty {
		/**
		 * Intermediate value used during the reduction.
		 */
		private float value_;

		/**
		 * Constructor.
		 * 
		 * @param id
		 *            the numeric id of this thread within the barrier.
		 */
		private StaticTreeReductionParty(int id) {
			super(id);
		}

		/**
		 * Called when this party reaches the barrier.
		 * 
		 * @param value
		 *            the value contributed to the reduction by this thread
		 * @return the result of the reduction
		 */
		private float await(float value) {
			value_ = value;
			sense_ = !sense_;
			if (id_ == 0) {
				while (flags_[1].get() != sense_)
					;
				value_ = reductor_.operator(value_, parties_[1].value_);
				if (numParties_ > 2) {
					while (flags_[2].get() != sense_)
						;
					value_ = reductor_.operator(value_, parties_[2].value_);
					if (numParties_ > 3) {
						while (flags_[numParties_ - 1].get() != sense_)
							;
						result_ =
							reductor_.operator(value_,
								parties_[numParties_ - 1].value_);
					}
				}
				// If there is a barrier action, execute it.
				if (action_ != null) {
					action_.run();
				}
				flagOut_ = sense_;
			} else if (id_ < THRESH) {
				while (flags_[LEFT_CHILD].get() != sense_)
					;
				value_ =
					reductor_.operator(value_, parties_[LEFT_CHILD].value_);
				while (flags_[RIGHT_CHILD].get() != sense_)
					;
				value_ =
					reductor_.operator(value_, parties_[RIGHT_CHILD].value_);
				flags_[id_].set(sense_);
				while (flagOut_ != sense_)
					;
			} else {
				flags_[id_].set(sense_);
				while (flagOut_ != sense_)
					;
			}
			return result_;
		}
	}
}
