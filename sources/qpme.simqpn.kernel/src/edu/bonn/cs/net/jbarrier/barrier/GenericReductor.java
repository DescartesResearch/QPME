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
 * Interface for generic reductors. A call to {@link #reduce(int, int)} is meant
 * to perform an arbitrary binary reduction task between two parties. In order
 * to use a generic reductor, it is required to let the threads store the
 * intermediate results of each binary reduction as well as the final result of
 * the global reduction.
 * 
 * @version 1.0
 * 
 * @author Patrick Peschlow
 * @author Ivan Castilla Rodriguez
 */
public interface GenericReductor {
	/**
	 * Performs a binary reduction for two threads participating in the barrier.
	 * The barrier implementations make the assumption that the first thread
	 * receives the result of the reduction, i.e., the first (= the calling)
	 * thread does not affect any local variable of the second thread.
	 * 
	 * @param threadId1
	 *            the ID of the first thread involved in the reduction
	 * @param threadId2
	 *            the ID of the second thread involved in the reduction
	 */
	void reduce(int threadId1, int threadId2);
}
