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
 * Abstract base class for reduction operators.
 * 
 * @version 1.0
 * 
 * @author Patrick Peschlow
 * @author Ivan Castilla Rodriguez
 */
public abstract class ReductionOperator {
	/**
	 * Operator on operands of type <code>int</code>.
	 * 
	 * @param value1
	 *            the first operand
	 * @param value2
	 *            the second operand
	 * @return the result of the operator applied to the two operands
	 */
	protected abstract int operator(int value1, int value2);

	/**
	 * Operator on operands of type <code>long</code>.
	 * 
	 * @param value1
	 *            the first operand
	 * @param value2
	 *            the second operand
	 * @return the result of the operator applied to the two operands
	 */
	protected abstract long operator(long value1, long value2);

	/**
	 * Operator on operands of type <code>float</code>.
	 * 
	 * @param value1
	 *            the first operand
	 * @param value2
	 *            the second operand
	 * @return the result of the operator applied to the two operands
	 */
	protected abstract float operator(float value1, float value2);

	/**
	 * Operator on operands of type <code>double</code>.
	 * 
	 * @param value1
	 *            the first operand
	 * @param value2
	 *            the second operand
	 * @return the result of the operator applied to the two operands
	 */
	protected abstract double operator(double value1, double value2);

	/**
	 * Minimum reduction operator.
	 * 
	 * @version 1.0
	 * 
	 * @author Patrick Peschlow
	 * @author Ivan Castilla Rodriguez
	 */
	public static class MinimumReduction extends ReductionOperator {
		/**
		 * Minimum operator on operands of type <code>int</code>.
		 * 
		 * @param value1
		 *            the first operand
		 * @param value2
		 *            the second operand
		 * @return the minimum of the two operands
		 */
		protected int operator(int value1, int value2) {
			return value1 <= value2 ? value1 : value2;
		}

		/**
		 * Minimum operator on operands of type <code>long</code>.
		 * 
		 * @param value1
		 *            the first operand
		 * @param value2
		 *            the second operand
		 * @return the minimum of the two operands
		 */
		protected long operator(long value1, long value2) {
			return value1 <= value2 ? value1 : value2;
		}

		/**
		 * Minimum operator on operands of type <code>float</code>.
		 * 
		 * @param value1
		 *            the first operand
		 * @param value2
		 *            the second operand
		 * @return the minimum of the two operands
		 */
		protected float operator(float value1, float value2) {
			return value1 <= value2 ? value1 : value2;
		}

		/**
		 * Minimum operator on operands of type <code>double</code>.
		 * 
		 * @param value1
		 *            the first operand
		 * @param value2
		 *            the second operand
		 * @return the minimum of the two operands
		 */
		protected double operator(double value1, double value2) {
			return value1 <= value2 ? value1 : value2;
		}
	}

	/**
	 * Maximum reduction operator.
	 * 
	 * @version 1.0
	 * 
	 * @author Patrick Peschlow
	 * @author Ivan Castilla Rodriguez
	 */
	public static class MaximumReduction extends ReductionOperator {
		/**
		 * Maximum operator on operands of type <code>int</code>.
		 * 
		 * @param value1
		 *            the first operand
		 * @param value2
		 *            the second operand
		 * @return the maximum of the two operands
		 */
		protected int operator(int value1, int value2) {
			return value1 >= value2 ? value1 : value2;
		}

		/**
		 * Maximum operator on operands of type <code>long</code>.
		 * 
		 * @param value1
		 *            the first operand
		 * @param value2
		 *            the second operand
		 * @return the maximum of the two operands
		 */
		protected long operator(long value1, long value2) {
			return value1 >= value2 ? value1 : value2;
		}

		/**
		 * Maximum operator on operands of type <code>float</code>.
		 * 
		 * @param value1
		 *            the first operand
		 * @param value2
		 *            the second operand
		 * @return the maximum of the two operands
		 */
		protected float operator(float value1, float value2) {
			return value1 >= value2 ? value1 : value2;
		}

		/**
		 * Maximum operator on operands of type <code>double</code>.
		 * 
		 * @param value1
		 *            the first operand
		 * @param value2
		 *            the second operand
		 * @return the maximum of the two operands
		 */
		protected double operator(double value1, double value2) {
			return value1 >= value2 ? value1 : value2;
		}
	}

	/**
	 * Sum reduction operator.
	 * 
	 * @version 1.0
	 * 
	 * @author Patrick Peschlow
	 * @author Ivan Castilla Rodriguez
	 */
	public static class SumReduction extends ReductionOperator {
		/**
		 * Sum operator on operands of type <code>int</code>.
		 * 
		 * @param value1
		 *            the first operand
		 * @param value2
		 *            the second operand
		 * @return the sum of the two operands
		 */
		protected int operator(int value1, int value2) {
			return value1 + value2;
		}

		/**
		 * Sum operator on operands of type <code>long</code>.
		 * 
		 * @param value1
		 *            the first operand
		 * @param value2
		 *            the second operand
		 * @return the sum of the two operands
		 */
		protected long operator(long value1, long value2) {
			return value1 + value2;
		}

		/**
		 * Sum operator on operands of type <code>float</code>.
		 * 
		 * @param value1
		 *            the first operand
		 * @param value2
		 *            the second operand
		 * @return the sum of the two operands
		 */
		protected float operator(float value1, float value2) {
			return value1 + value2;
		}

		/**
		 * Sum operator on operands of type <code>double</code>.
		 * 
		 * @param value1
		 *            the first operand
		 * @param value2
		 *            the second operand
		 * @return the sum of the two operands
		 */
		protected double operator(double value1, double value2) {
			return value1 + value2;
		}
	}
}
