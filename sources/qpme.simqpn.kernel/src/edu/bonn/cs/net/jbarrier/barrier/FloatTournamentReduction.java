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
 * Implementation of a tournament barrier algorithm including a float reduction.
 * 
 * @version 1.0
 * 
 * @author Patrick Peschlow
 * @author Ivan Castilla Rodriguez
 */
public class FloatTournamentReduction extends TournamentBarrier implements
	FloatReduction {
	/**
	 * The reduction operator used.
	 */
	private final ReductionOperator reductor_;

	/**
	 * The barrier data associated to each party.
	 */
	@SuppressWarnings("hiding")
	private TournamentReductionParty[] parties_;

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
	 */
	public FloatTournamentReduction(int numParties, Runnable barrierAction,
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
	 */
	public FloatTournamentReduction(int numParties, ReductionOperator reductor) {
		this(numParties, null, reductor);
	}

	/**
	 * Sets up the parties array required for this reduction subclass.
	 */
	@Override
	protected void setUpParties() {
		parties_ = new TournamentReductionParty[numParties_];
		for (int i = 0; i < numParties_; i++) {
			parties_[i] = new TournamentReductionParty(i);
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
	private class TournamentReductionParty extends TournamentBarrierParty {
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
		private TournamentReductionParty(int id) {
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
			int currentRound = 0;
			for (;;) {
				final Round roundObj = rounds_[currentRound];
				switch (roundObj.role_) {
					case WINNER:
						while (flags_[currentRound].get() != sense_)
							;
						value_ =
							reductor_.operator(value_,
								parties_[roundObj.partnerId_].value_);
						++currentRound;
						// Continue to next round.
						continue;
					case WILDCARD:
						++currentRound;
						// Continue to next round.
						continue;
					case LOSER:
						parties_[roundObj.partnerId_].flags_[currentRound]
							.set(sense_);
						// Wait for the tournament winner (root).
						while (flagOut_ != sense_)
							;
						// Exit switch statement (and thus the for loop).
						break;
					case ROOT:
						while (flags_[currentRound].get() != sense_)
							;
						result_ =
							reductor_.operator(value_,
								parties_[roundObj.partnerId_].value_);
						// If there is a barrier action, execute it.
						if (action_ != null) {
							action_.run();
						}
						flagOut_ = sense_;
						// Exit switch statement (and thus the for loop).
						break;
				}
				// Exit for loop.
				break;
			}
			return result_;
		}
	}
}
