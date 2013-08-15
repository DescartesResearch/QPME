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
 * Implementation of a static tree barrier algorithm. The static tree barrier
 * was introduced in the following article:
 * <p>
 * J. M. Mellor-Crummey and M. L. Scott. "Algorithms for Scalable
 * Synchronization on Shared-Memory Multiprocessors". In <i>ACM Transactions on
 * Computer Systems</i>, volume 9, pages 21-65, 1991.
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
public class StaticTreeBarrier extends AbstractBarrier {
	/**
	 * The barrier data associated to each party.
	 */
	protected StaticTreeBarrierParty[] parties_;

	/**
	 * The synchronization flags (one for each party).
	 */
	protected final AtomicBoolean[] flags_;

	/**
	 * Out flag set by the winner.
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
	public StaticTreeBarrier(int numParties, Runnable barrierAction,
		GenericReductor genericReductor) {
		super(numParties, barrierAction, genericReductor);
		if (!Utils.isPowerOfTwo(numParties)) {
			throw new IllegalArgumentException(
				"Static tree barrier currently requires the number of parties to be a power of two!");
		}
		flagOut_ = false;
		flags_ = new AtomicBoolean[numParties_];
		for (int i = 0; i < numParties_; i++) {
			flags_[i] = new AtomicBoolean(false);
		}
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
	public StaticTreeBarrier(int numParties, Runnable barrierAction) {
		this(numParties, barrierAction, null);
	}

	/**
	 * Constructor.
	 * 
	 * @param numParties
	 *            the number of parties that must reach the barrier before the
	 *            barrier is tripped
	 * @throws IllegalArgumentException
	 *             if <code>numParties</code> is not a power of two
	 */
	public StaticTreeBarrier(int numParties) {
		this(numParties, null);
	}

	/**
	 * Sets up the parties array, intended to be overridden in subclasses.
	 */
	protected void setUpParties() {
		parties_ = new StaticTreeBarrierParty[numParties_];
		for (int i = 0; i < numParties_; i++) {
			parties_[i] = new StaticTreeBarrierParty(i);
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
	protected class StaticTreeBarrierParty {
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
		 * Pre-computed constant that indicates the first leaf node.
		 */
		protected final int THRESH;

		/**
		 * Pre-computed constant that represents the ID of my left child in the
		 * tree.
		 */
		protected final int LEFT_CHILD;

		/**
		 * Pre-computed constant that represents the ID of my right child in the
		 * tree.
		 */
		protected final int RIGHT_CHILD;

		/**
		 * Constructor.
		 * 
		 * @param id
		 *            the numeric id of this thread within the barrier.
		 */
		protected StaticTreeBarrierParty(int id) {
			id_ = id;
			sense_ = false;
			THRESH = (numParties_ - 1) / 2;
			LEFT_CHILD = 2 * id_ + 1;
			RIGHT_CHILD = 2 * id_ + 2;
		}

		/**
		 * Called when this party reaches the barrier.
		 */
		protected void await() {
			sense_ = !sense_;
			if (id_ == 0) {
				while (flags_[1].get() != sense_)
					;
				if (genericReductor_ != null) {
					genericReductor_.reduce(id_, 1);
				}
				if (numParties_ > 2) {
					while (flags_[2].get() != sense_)
						;
					if (genericReductor_ != null) {
						genericReductor_.reduce(id_, 2);
					}
					if (numParties_ > 3) {
						while (flags_[numParties_ - 1].get() != sense_)
							;
						if (genericReductor_ != null) {
							genericReductor_.reduce(id_, numParties_ - 1);
						}
					}
				}
				if (action_ != null) {
					action_.run();
				}
				flagOut_ = sense_;
			} else if (id_ < THRESH) {
				while (flags_[LEFT_CHILD].get() != sense_)
					;
				if (genericReductor_ != null) {
					genericReductor_.reduce(id_, LEFT_CHILD);
				}
				while (flags_[RIGHT_CHILD].get() != sense_)
					;
				if (genericReductor_ != null) {
					genericReductor_.reduce(id_, RIGHT_CHILD);
				}
				flags_[id_].set(sense_);
				while (flagOut_ != sense_)
					;
			} else {
				flags_[id_].set(sense_);
				while (flagOut_ != sense_)
					;
			}
		}
	}
}
