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
 * Interface for barriers supporting primitive float reductions.
 * 
 * @version 1.0
 * 
 * @author Patrick Peschlow
 * @author Ivan Castilla Rodriguez
 */
public interface FloatReduction {
	/**
	 * Called by a party that reaches the barrier.
	 * 
	 * @param threadId
	 *            the ID of the party
	 * @param value
	 *            the value subject to the reduction
	 * @return the result of the reduction
	 */
	public float await(int threadId, float value);
}
