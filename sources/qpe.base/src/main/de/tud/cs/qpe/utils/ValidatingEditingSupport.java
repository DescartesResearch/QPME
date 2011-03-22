package de.tud.cs.qpe.utils;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public abstract class ValidatingEditingSupport extends EditingSupport {
	
	protected ControlDecoration validateMessages;
	protected CellEditor cellEditor;
	
	public ValidatingEditingSupport(ColumnViewer viewer) {
		super(viewer);
		this.cellEditor = createCellEditor((Composite) getViewer().getControl());
		this.cellEditor.addListener(new ICellEditorListener() {

			@Override
			public void applyEditorValue() {
				validateMessages.hide();
			}

			@Override
			public void cancelEditor() {
				validateMessages.hide();
			}

			@Override
			public void editorValueChanged(boolean oldValidState,
					boolean newValidState) {
				
				if (cellEditor.getErrorMessage() != null) {
					validateMessages.setDescriptionText(cellEditor.getErrorMessage());
					validateMessages.show();
				} else {
					validateMessages.hide();
				}
			}
			
		});
		
		validateMessages = new ControlDecoration(cellEditor.getControl(), SWT.TRAIL | SWT.TOP);
		FieldDecoration decoration = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);
		validateMessages.setImage(decoration.getImage());
		validateMessages.hide();
	}
	
	protected abstract CellEditor createCellEditor(Composite parent);

	@Override
	protected CellEditor getCellEditor(Object element) {
		return cellEditor;
	}
	
	public void setValidator(ICellEditorValidator validator) {
		this.cellEditor.setValidator(validator);
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}
}
