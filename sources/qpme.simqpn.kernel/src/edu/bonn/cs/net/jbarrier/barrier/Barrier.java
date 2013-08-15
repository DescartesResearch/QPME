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
 * Interface for all barrier implementations of this package. A barrier is a
 * well-known synchronization construct for two or more parties (here: threads).
 * No party may pass the barrier until all other parties have arrived at the
 * barrier. Only then the next phase of parallel computation is started.
 * 
 * A party reaches the barrier by calling the {@link #await(int)} method. There
 * are two differences between {@link #await(int)} and the corresponding method
 * of the {@link java.util.concurrent.CyclicBarrier}:
 * <ol>
 * <li>{@link #await(int)} has no return value.
 * <li>{@link #await(int)} takes as a parameter the logical ID of the thread
 * calling the method.
 * <ol>
 * With respect to logical IDs, it is assumed that, if <i>n</i> threads take
 * part in the barrier, they have logical IDs ranging from 0..<i>n</i>-1. Each
 * party has to know its ID and to specify it when calling {@link #await(int)}.
 * There are ways to implement the same behavior without having to specify a
 * logical ID, but we think our current implementation doesn't cause any
 * inconvenience to the user.
 * 
 * @version 1.0
 * 
 * @author Patrick Peschlow
 * @author Ivan Castilla Rodriguez
 */
public interface Barrier {
	/**
	 * Called by a party that reaches the barrier.
	 * 
	 * @param threadId
	 *            the ID of the party (if <i>n</i> threads take part in the
	 *            barrier, threadId must be one of 0..<i>n</i>-1)
	 */
	public void await(int threadId);
}
