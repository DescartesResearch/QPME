package de.tud.cs.qpe.utils;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;

public abstract class XmlEnumerationAttributeEditingSupport extends
		XmlAttributeEditingSupport {

	public XmlEnumerationAttributeEditingSupport(ColumnViewer column,
			String attribute) {
		super(column, attribute);
	}
	
	@Override
	protected CellEditor createCellEditor(Composite parent) {
		ComboBoxViewerCellEditor editor = new ComboBoxViewerCellEditor(parent);
		editor.setContenProvider(new ArrayContentProvider());
		editor.setLabelProvider(new LabelProvider());
		return editor;
	}
	
	@Override
	protected CellEditor getCellEditor(Object element) {
		ComboBoxViewerCellEditor editor = (ComboBoxViewerCellEditor)super.getCellEditor(element);
		editor.setInput(getItems());
		return editor;
	}
	
	protected abstract Object[] getItems();

}
