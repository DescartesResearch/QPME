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
 * Original Author(s):  Simon Spinner
 * Contributor(s):      
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2011/03/22  Simon Spinner     Created.
 */
package de.tud.cs.qpe.utils;

import org.eclipse.jface.viewers.ICellEditorValidator;

public class CellValidators {
	
	public static ICellEditorValidator newIntegerValidator() {
		return new ICellEditorValidator() {			
			@Override
			public String isValid(Object value) {
				try {
					Integer.parseInt(value.toString());
					return null;
				} catch(NumberFormatException ex) {
					return "Wrong number format";
				}
			}
		};
	}
	
	public static ICellEditorValidator newNonNegativeIntegerValidator() {
		return new ICellEditorValidator() {			
			@Override
			public String isValid(Object value) {
				try {
					int i = Integer.parseInt(value.toString());
					if (i < 0) {
						return "Negative numbers not allowed";
					}
					return null;
				} catch(NumberFormatException ex) {
					return "Wrong number format";
				}
			}
		};
	}
	
	public static ICellEditorValidator newNonNegativeEvenIntegerValidator() {
		return new ICellEditorValidator() {			
			@Override
			public String isValid(Object value) {
				try {
					int i = Integer.parseInt(value.toString());
					if ((i < 0) || (i % 2 == 1)) {
						return "Number must be a non-negative and even";
					}
					return null;
				} catch(NumberFormatException ex) {
					return "Wrong number format";
				}
			}
		};
	}
	
	public static ICellEditorValidator newGreaterThanOrEqualIntegerValidator(
			final int barrier) {
		return new ICellEditorValidator() {			
			@Override
			public String isValid(Object value) {
				try {
					int i = Integer.parseInt(value.toString());
					if (i < barrier) {
						return "Number must be greater than or equal to " + barrier;
					}
					return null;
				} catch(NumberFormatException ex) {
					return "Wrong number format";
				}
			}
		};
	}
	
	public static ICellEditorValidator newDoubleValidator() {
		return new ICellEditorValidator() {			
			@Override
			public String isValid(Object value) {
				try {
					Double.parseDouble(value.toString());
					return null;
				} catch(NumberFormatException ex) {
					return "Wrong number format";
				}
			}
		};
	}
	
	public static ICellEditorValidator newNonNegativeDoubleValidator() {
		return new ICellEditorValidator() {			
			@Override
			public String isValid(Object value) {
				try {
					double d = Double.parseDouble(value.toString());
					if (d < 0.0) {
						return "Negative numbers not allowed";
					}
					return null;
				} catch(NumberFormatException ex) {
					return "Wrong number format";
				}
			}
		};
	}

	public static ICellEditorValidator newPositiveDoubleValidator() {
		return new ICellEditorValidator() {			
			@Override
			public String isValid(Object value) {
				try {
					double d = Double.parseDouble(value.toString());
					if (d < 0.0) {
						return "Number must be positive";
					}
					return null;
				} catch(NumberFormatException ex) {
					return "Wrong number format";
				}
			}
		};
	}

	public static ICellEditorValidator newPositiveIntegerValidator() {
		return new ICellEditorValidator() {			
			@Override
			public String isValid(Object value) {
				try {
					int i = Integer.parseInt(value.toString());
					if (i <= 0) {
						return "Number must be positive";
					}
					return null;
				} catch(NumberFormatException ex) {
					return "Wrong number format";
				}
			}
		};
	}

	public static ICellEditorValidator newIntegerRangeValidator(final int min, final int max) {
		return new ICellEditorValidator() {			
			@Override
			public String isValid(Object value) {
				try {
					int i = Integer.parseInt(value.toString());
					if ((i < min) || (i > max)) {
						return "Number must be between " + min + " and " + max;
					}
					return null;
				} catch(NumberFormatException ex) {
					return "Wrong number format";
				}
			}
		};
	}
}
