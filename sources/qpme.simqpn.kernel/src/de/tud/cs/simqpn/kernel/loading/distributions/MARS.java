/* ==============================================
 * QPME : Queueing Petri net Modeling Environment
 * ==============================================
 *
 * (c) Copyright 2003-2011, by Samuel Kounev and Contributors.
 * 
 * Project Info:   http://descartes.ipd.kit.edu/projects/qpme/
 *                 http://www.descartes-research.net/
 *    
 * All rights reserved. This software is made available under the terms of the 
 * Eclipse Public License (EPL) v1.0 as published by the Eclipse Foundation
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * This software is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the Eclipse Public License (EPL)
 * for more details.
 *
 * You should have received a copy of the Eclipse Public License (EPL)
 * along with this software; if not visit http://www.eclipse.org or write to
 * Eclipse Foundation, Inc., 308 SW First Avenue, Suite 110, Portland, 97204 USA
 * Email: license (at) eclipse.org 
 *  
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *                                
 * =============================================
 *
 * Original Author(s):  Fabian Brosig
 * Contributor(s):      
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2013	    Fabian Brosig     Created.
 */
package de.tud.cs.simqpn.kernel.loading.distributions;

public class MARS implements AbstractDistribution {

	private Function[] functions = null;
	private Double constant;
	private Double[] coefficients;
	private Double[] knots;
	private Integer[] sides;
	private String[] colorIds;
	
	public MARS(double constant, Double[] doubles, Double[] doubles2, Integer[] integers, String[] strings) {
		this.constant = constant;
		this.coefficients = doubles;
		this.knots = doubles2;
		this.sides = integers;
		this.colorIds = strings;
	}

	@Override
	public double nextDouble(int concurrency, String[] colors, int[] tokenNumbers) {
		if (functions == null)
			createFunctions(colors);
		double result = constant;
		for (int i = 0; i < functions.length; i++)
			result += functions[i].calculate(tokenNumbers[i]);
		return result;
	}

	private void createFunctions(String[] colors) {
		System.out.println(colors[0]);
		functions = new Function[colors.length];
		for (int i = 0; i < colors.length; i++) {
			for (int j = 0; j < colorIds.length; j++) {
				if (colors[i].equals(colorIds[j])) {
					if (sides[j] == 0)
						functions[i] = new LeftFunction(knots[j], coefficients[j]);
					else
						functions[i] = new RightFunction(knots[j], coefficients[j]);
				}
			}
		}
	}

	interface Function {
		public double calculate(int tokenNumber);
	}

	class LeftFunction implements Function {

		private double knot;
		private double coefficient;

		public LeftFunction(double knot, double coefficient) {
			this.knot = knot;
			this.coefficient = coefficient;
		}

		public double calculate(int tokenNumber) {
			if (tokenNumber >= knot)
				return 0.0;
			return coefficient * (knot - tokenNumber);
		}
	}

	class RightFunction implements Function {

		private double knot;
		private double coefficient;

		public RightFunction(double knot, double coefficient) {
			this.knot = knot;
			this.coefficient = coefficient;
		}

		public double calculate(int tokenNumber) {
			if (tokenNumber < knot)
				return 0.0;
			return coefficient * (tokenNumber - knot);
		}
	}
}
