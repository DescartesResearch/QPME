package de.tud.cs.qpe.utils;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;

public abstract class LabelColorProvider extends LabelProvider implements IColorProvider {
	public abstract Color getBackground(Object element);
	public abstract Color getForeground(Object element);
}
