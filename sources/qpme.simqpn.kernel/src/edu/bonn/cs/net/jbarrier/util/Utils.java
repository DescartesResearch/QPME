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
package edu.bonn.cs.net.jbarrier.util;

/**
 * Utility functions for computing powers of two.
 * 
 * @version 1.0
 * 
 * @author Patrick Peschlow
 * @author Ivan Castilla Rodriguez
 */
public class Utils {

	/**
	 * Computes the next higher power of two of the provided integer.
	 * 
	 * @param k
	 *            the provided integer
	 * @return the next higher power of two of <code>k</code>
	 */
	public static int nextHigherPowerOfTwo(int k) {
		k--;
		for (int i = 1; i < 32; i <<= 1) {
			k = k | k >> i;
		}
		return k + 1;
	}

	/**
	 * Computes the <code>n</code>-th integer power of two.
	 * 
	 * @param n
	 *            the provided integer
	 * @return the <code>n</code>-th power of two
	 */
	public static int powerOfTwo(int n) {
		return 1 << n;
	}

	/**
	 * Checks whether the provided integer is a power of two.
	 * @param k
	 *            the provided integer
	 * @return <code>true</code> if the provided integer is a power of two,
	 *         otherwise <code>false</code>
	 */
	public static boolean isPowerOfTwo(int k) {
		return (k & (k - 1)) == 0;
	}
}
