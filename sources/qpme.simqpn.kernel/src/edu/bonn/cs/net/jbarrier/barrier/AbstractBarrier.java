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
 * Abstract base class for all barrier implementations of this package. Barriers
 * extending this class can have an associated (global) action, specified as a
 * {@link Runnable}, which will be executed by one party when all parties have
 * reached the barrier. Also, arbitrary global reductions are supported with the
 * help of a {@link GenericReductor} object.
 * <p>
 * With respect to memory consistency, just like with the
 * {@link java.util.concurrent.CyclicBarrier}, actions in a thread prior to
 * calling {@link #await(int)} happen-before actions that are part of the
 * barrier action, which in turn happen-before actions following a successful
 * return from {@link #await(int)} in other threads.
 * <p>
 * At this point, there is not much of an error handling if, e.g., threads get
 * interrupted during the barrier. A well-defined error handling, such as the
 * {@link java.util.concurrent.BrokenBarrierException} used by the
 * {@link java.util.concurrent.CyclicBarrier}, may be added in future versions.
 * 
 * @version 1.0
 * 
 * @author Patrick Peschlow
 * @author Ivan Castilla Rodriguez
 */
public abstract class AbstractBarrier implements Barrier {
	/**
	 * The number of parties taking part in the barrier.
	 */
	protected final int numParties_;

	/**
	 * The command to execute when the barrier is tripped, or <code>null</code>
	 * if there is no action.
	 */
	protected final Runnable action_;

	/**
	 * An optional generic reduction operator.
	 */
	protected final GenericReductor genericReductor_;

	/**
	 * Creates a new <code>AbstractBarrier</code> that will trip when the given
	 * number of parties are waiting upon it.
	 * 
	 * @param numParties
	 *            the number of parties that must reach the barrier before the
	 *            barrier is tripped
	 * @param action
	 *            the command to execute when the barrier is tripped, or
	 *            <code>null</code> if there is no action
	 * @param genericReductor
	 *            an optional generic reductor
	 * @throws IllegalArgumentException
	 *             if <code>numParties</code> is less than 2
	 */
	protected AbstractBarrier(int numParties, Runnable action,
		GenericReductor genericReductor) {
		if (numParties < 2) {
			throw new IllegalArgumentException(
				"Number of parties has to be larger than one!");
		}
		numParties_ = numParties;
		action_ = action;
		genericReductor_ = genericReductor;
	}

	/**
	 * Called by a party that reaches the barrier.
	 * 
	 * @param threadId
	 *            the ID of the party
	 */
	public abstract void await(int threadId);
}
