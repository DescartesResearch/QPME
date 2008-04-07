package de.tud.cs.qpe.utils;

import java.text.MessageFormat;

import org.eclipse.jface.util.Assert;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.widgets.Composite;

public class DoubleCellEditor extends IntegerCellEditor {    /**
    /**
     * Creates a new text string cell editor parented under the given control.
     * The cell editor value is the string itself, which is initially the empty string. 
     * Initially, the cell editor has no cell validator.
     *
     * @param parent the parent control
     */
    public DoubleCellEditor(Composite parent) {
        this(parent, defaultStyle);
    }

    /**
     * Creates a new text string cell editor parented under the given control.
     * The cell editor value is the string itself, which is initially the empty string. 
     * Initially, the cell editor has no cell validator.
     *
     * @param parent the parent control
     * @param style the style bits
     * @since 2.1
     */
    public DoubleCellEditor(Composite parent, int style) {
        super(parent, style);
    }

	protected Object doGetValue() {
		try {
			return Double.parseDouble(text.getText());
		} catch(NumberFormatException nfe) {
			return new Double(0);
		}
	}

	protected void doSetValue(Object value) {
		Assert.isTrue(text != null && (value instanceof Double));
		text.removeModifyListener(getModifyListener());
		text.setText(((Double) value).toString());
		text.addModifyListener(getModifyListener());
	}

	protected void editOccured(ModifyEvent e) {
		double value = 0;
		try {
			value = Double.parseDouble(text.getText());
		} catch (Exception ex) {
			value = 0;
		}
		// TODO: Hהההה? (int casted to object?)
		Object typedValue = value;
		boolean oldValidState = isValueValid();
		boolean newValidState = isCorrect(typedValue);
		if (typedValue == null && newValidState)
			Assert.isTrue(false,
					"Validator isn't limiting the cell editor's type range");//$NON-NLS-1$
		if (!newValidState) {
			// try to insert the current value into the error message.
			setErrorMessage(MessageFormat.format(getErrorMessage(),
					new Object[] { value }));
		}
		valueChanged(oldValidState, newValidState);
	}
}
