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
 * Implementation of a tournament barrier algorithm. A tournament barrier
 * algorithm, which makes the parties compete in a series of game "rounds",
 * arranged in a tournament structure. The winning party advances to the next
 * level and "plays" against other winning parties until there is only a single
 * "champion" left. The loser parties simply wait for the tournament to finish
 * (when they are finally woken up by the champion). Since this is not a real
 * competition, the winners and losers of each round are selected in advance to
 * improve performance. The tournament barrier was introduced in the following
 * article:
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
public class TournamentBarrier extends AbstractBarrier {
	/**
	 * The barrier information associated to each competitor thread.
	 */
	protected TournamentBarrierParty[] parties_;

	/**
	 * The number of rounds used for the barrier.
	 */
	protected final int numRounds_;

	/**
	 * Out flag set by the winner of the tournament.
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
	 */
	public TournamentBarrier(int numParties, Runnable barrierAction,
		GenericReductor genericReductor) {
		super(numParties, barrierAction, genericReductor);
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
	 */
	public TournamentBarrier(int numParties, Runnable barrierAction) {
		this(numParties, barrierAction, null);
	}

	/**
	 * Constructor (if no action is used).
	 * 
	 * @param numParties
	 *            the number of parties that must reach the barrier before the
	 *            barrier is tripped
	 */
	public TournamentBarrier(int numParties) {
		this(numParties, null);
	}

	/**
	 * Sets up the parties array, intended to be overridden in subclasses.
	 */
	protected void setUpParties() {
		parties_ = new TournamentBarrierParty[numParties_];
		for (int i = 0; i < numParties_; i++) {
			parties_[i] = new TournamentBarrierParty(i);
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
	 * @version 1.0
	 * 
	 * @author Patrick Peschlow
	 * @author Ivan Castilla Rodriguez
	 */
	protected class TournamentBarrierParty {
		/**
		 * Unique id of this party [0 .. numParties-1].
		 */
		protected final int id_;

		/**
		 * Sense flag that switches between <code>true</code> and
		 * <code>false</code>.
		 */
		protected boolean sense_;

		/**
		 * Pre-computed information about the rounds of the barrier.
		 */
		protected final Round[] rounds_;

		/**
		 * My set of flags to be set/queried during the barrier.
		 */
		protected final AtomicBoolean[] flags_;

		/**
		 * Constructor.
		 * 
		 * @param id
		 *            the numeric id of this thread within the barrier.
		 */
		protected TournamentBarrierParty(int id) {
			id_ = id;
			sense_ = false;
			flags_ = new AtomicBoolean[numRounds_];
			rounds_ = new Round[numRounds_];
		}

		/**
		 * Sets up this party for the barrier algorithm.
		 */
		protected void setupBarrier() {
			// Compute the next higher power of two to build the tree.
			final int numVirtualThreads =
				Utils.nextHigherPowerOfTwo(numParties_);
			for (int round = 0; round < numRounds_; round++) {
				int partnerId =
					(id_ ^ Utils.powerOfTwo(round)) % numVirtualThreads;
				final boolean isWinner =
					(id_ % Utils.powerOfTwo(round + 1) == 0);
				Role role;
				if (partnerId >= numParties_) {
					role = Role.WILDCARD;
					partnerId = -1;
				} else {
					if (isWinner) {
						if (id_ == 0 && round == numRounds_ - 1) {
							role = Role.ROOT;
						} else {
							role = Role.WINNER;
						}
					} else {
						role = Role.LOSER;
					}
				}
				Round roundObj = new Round(partnerId, role);
				rounds_[round] = roundObj;
				flags_[round] = new AtomicBoolean(false);
			}
		}

		/**
		 * Called when this party reaches the barrier.
		 */
		protected void await() {
			sense_ = !sense_;
			int currentRound = 0;
			for (;;) {
				final Round roundObj = rounds_[currentRound];
				switch (roundObj.role_) {
					case WINNER:
						while (flags_[currentRound].get() != sense_)
							;
						if (genericReductor_ != null) {
							genericReductor_.reduce(id_, roundObj.partnerId_);
						}
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
						if (genericReductor_ != null) {
							genericReductor_.reduce(id_, roundObj.partnerId_);
						}
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
		}
	}

	/**
	 * Possible roles of the different parties in the tournament barrier.
	 * 
	 * @version 1.0
	 * 
	 * @author Patrick Peschlow
	 * @author Ivan Castilla Rodriguez
	 */
	protected static enum Role {
		/**
		 * Winner. Advances to the next round.
		 */
		WINNER,
		/**
		 * Loser. Out of the tournament.
		 */
		LOSER,
		/**
		 * Wildcard. Advances to the next round without having a partner.
		 */
		WILDCARD,
		/**
		 * Root. Overall winner of the tournament.
		 */
		ROOT;
	}

	/**
	 * Stores pre-computed information about a round of the tournament barrier.
	 * 
	 * @version 1.0
	 * 
	 * @author Patrick Peschlow
	 * @author Ivan Castilla Rodriguez
	 */
	protected static class Round {
		/**
		 * ID of my "competitor" party in this round.
		 */
		protected final int partnerId_;

		/**
		 * My role in this round.
		 */
		protected final Role role_;

		/**
		 * Constructor.
		 * 
		 * @param partnerId
		 *            the ID of the "competitor" party in the current round
		 * @param role
		 *            the role of this party in the current round
		 */
		private Round(int partnerId, Role role) {
			partnerId_ = partnerId;
			role_ = role;
		}
	}
}
