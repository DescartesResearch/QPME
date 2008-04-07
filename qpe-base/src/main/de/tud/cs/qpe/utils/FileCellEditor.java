package de.tud.cs.qpe.utils;

import java.io.File;

import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;

public class FileCellEditor extends DialogCellEditor {

	/**
	 * Creates a new color cell editor parented under the given control. The
	 * cell editor value is black (<code>RGB(0,0,0)</code>) initially, and
	 * has no validator.
	 * 
	 * @param parent
	 *            the parent control
	 */
	public FileCellEditor(Composite parent) {
		this(parent, SWT.NONE);
	}

	/**
	 * Creates a new color cell editor parented under the given control. The
	 * cell editor value is black (<code>RGB(0,0,0)</code>) initially, and
	 * has no validator.
	 * 
	 * @param parent
	 *            the parent control
	 * @param style
	 *            the style bits
	 * @since 2.1
	 */
	public FileCellEditor(Composite parent, int style) {
		super(parent, style);
		doSetValue("");
	}

	/*
	 * (non-Javadoc) Method declared on DialogCellEditor.
	 */
	/*
	 * protected Control createContents(Composite cell) { Color bg =
	 * cell.getBackground(); composite = new Composite(cell, getStyle());
	 * composite.setBackground(bg); composite.setLayout(new ColorCellLayout());
	 * colorLabel = new Label(composite, SWT.LEFT);
	 * colorLabel.setBackground(bg); rgbLabel = new Label(composite, SWT.LEFT);
	 * rgbLabel.setBackground(bg); rgbLabel.setFont(cell.getFont()); return
	 * composite; }
	 */

	/*
	 * (non-Javadoc) Method declared on DialogCellEditor.
	 */
	protected Object openDialogBox(Control cellEditorWindow) {
		FileDialog dialog = new FileDialog(cellEditorWindow.getShell());
		Object value = getValue();
		if (value != null)
			dialog.setFileName((String) value);
		value = dialog.open();
		return dialog.getFilterPath() + File.separator + dialog.getFileName();
	}
}
