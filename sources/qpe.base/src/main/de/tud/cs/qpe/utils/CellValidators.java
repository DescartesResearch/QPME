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
