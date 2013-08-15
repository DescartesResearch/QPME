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
 * Implementation of a central barrier algorithm based on a shared counter. Of
 * all algorithms in this package, this one is most similar to the
 * {@link java.util.concurrent.CyclicBarrier}.
 * 
 * @version 1.0
 * 
 * @author Patrick Peschlow
 * @author Ivan Castilla Rodriguez
 */
public class CentralBarrier extends AbstractBarrier {
	/**
	 * The central counter variable.
	 */
	protected AtomicInteger counter_;

	/**
	 * Global out flag.
	 */
	protected volatile boolean go_;

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
	public CentralBarrier(int numParties, Runnable barrierAction,
		GenericReductor genericReductor) {
		super(numParties, barrierAction, genericReductor);
		counter_ = new AtomicInteger(0);
		go_ = false;
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
	public CentralBarrier(int numParties, Runnable barrierAction) {
		this(numParties, barrierAction, null);
	}

	/**
	 * Constructor (if no action is used).
	 * 
	 * @param numParties
	 *            the number of parties that must reach the barrier before the
	 *            barrier is tripped
	 */
	public CentralBarrier(int numParties) {
		this(numParties, null);
	}

	/**
	 * Called by a party that reaches the barrier.
	 * 
	 * @param threadId
	 *            the ID of the party
	 */
	@Override
	public void await(int threadId) {
		boolean localGo = go_;
		if (counter_.incrementAndGet() == numParties_) {
			counter_.set(0);
			if (genericReductor_ != null) {
				for (int i = 1; i < numParties_; i++) {
					genericReductor_.reduce(0, i);
				}
			}
			if (action_ != null) {
				action_.run();
			}
			go_ = !go_;
		} else {
			while (go_ == localGo)
				;
		}
	}
}
