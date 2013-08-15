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

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Implementation of a central barrier algorithm including a float reduction.
 * 
 * @version 1.0
 * 
 * @author Patrick Peschlow
 * @author Ivan Castilla Rodriguez
 */
public class FloatCentralReduction extends CentralBarrier implements
	FloatReduction {
	/**
	 * The reduction operator used.
	 */
	private final ReductionOperator reductor_;

	/**
	 * The barrier data associated to each party.
	 */
	private final CentralReductionParty[] parties_;

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
	public FloatCentralReduction(int numParties, Runnable barrierAction,
		ReductionOperator reductor) {
		super(numParties, barrierAction);
		reductor_ = reductor;
		parties_ = new CentralReductionParty[numParties_];
		for (int i = 0; i < numParties_; i++) {
			parties_[i] = new CentralReductionParty(i);
		}
		counter_ = new AtomicInteger(0);
		go_ = false;
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
	public FloatCentralReduction(int numParties, ReductionOperator reductor) {
		this(numParties, null, reductor);
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
	private class CentralReductionParty {
		/**
		 * Unique id of this party [0 .. numParties-1].
		 */
		@SuppressWarnings("unused")
		private final int id_;

		/**
		 * Intermediate value used during the reduction.
		 */
		private float value_;

		/**
		 * Constructor.
		 * 
		 * @param id
		 *            the numeric id of this party within the barrier.
		 */
		private CentralReductionParty(int id) {
			id_ = id;
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
			boolean localGo = go_;
			if (counter_.incrementAndGet() == numParties_) {
				counter_.set(0);
				float result = parties_[0].value_;
				for (int i = 1; i < numParties_; i++) {
					result = reductor_.operator(result, parties_[i].value_);
				}
				result_ = result;
				// If there is a barrier action, execute it.
				if (action_ != null) {
					action_.run();
				}
				go_ = !go_;
			} else {
				while (go_ == localGo)
					;
			}
			return result_;
		}
	}
}
