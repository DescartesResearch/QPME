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

import de.tud.cs.simqpn.kernel.entities.QPlace;

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
	public double nextDouble(QPlace qplace, int color) {
		if (functions == null)
			createFunctions(qplace.colors);
		double result = constant;
		for (int i = 0; i < functions.length; i++) {
			result += functions[i].calculate(qplace.getQueueTokenPop());
		}
		return result;
	}

	private void createFunctions(String[] colors) {
		functions = new Function[colorIds.length];
		int id = 0;
		for (int i = 0; i < colors.length; i++) {
			for (int j = 0; j < colorIds.length; j++) {
				if (colors[i].equals(colorIds[j])) {
					if (sides[j] == 0)
						functions[id] = new LeftFunction(knots[j], coefficients[j], colorIds[j], colors);
					else
						functions[id] = new RightFunction(knots[j], coefficients[j], colorIds[j], colors);
					id = id + 1;
				}
			}
		}
	}

	interface Function {
		public double calculate(int[] tokenNumbers);
	}

	class LeftFunction implements Function {

		private double knot;
		private double coefficient;
		private int colorId;

		public LeftFunction(double knot, double coefficient, String colorId, String[] colors) {
			this.knot = knot;
			this.coefficient = coefficient;
			for (int i = 0; i < colors.length; i++)
				if (colors[i].equals(colorId))
					this.colorId = i;
		}

		public double calculate(int[] tokenNumbers) {
			int tokenNumber = tokenNumbers[colorId];
			if (tokenNumber >= knot)
				return 0.0;
			return coefficient * (knot - tokenNumber);
		}

		public String toString() {
			return coefficient + "*max(0," + knot + "-WC" + colorId + ")";
		}
	}

	class RightFunction implements Function {

		private double knot;
		private double coefficient;
		private int colorId;

		public RightFunction(double knot, double coefficient, String colorId, String[] colors) {
			this.knot = knot;
			this.coefficient = coefficient;
			for (int i = 0; i < colors.length; i++)
				if (colors[i].equals(colorId))
					this.colorId = i;
		}

		public double calculate(int[] tokenNumbers) {
			int tokenNumber = tokenNumbers[colorId];
			if (tokenNumber <= knot)
				return 0.0;
			return coefficient * (tokenNumber - knot);
		}

		public String toString() {
			return coefficient + "*max(0,WC" + colorId + "-" + knot + ")";
		}
	}
}
