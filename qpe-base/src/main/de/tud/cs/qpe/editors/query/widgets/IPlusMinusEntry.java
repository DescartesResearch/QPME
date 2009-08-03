package de.tud.cs.qpe.editors.query.widgets;

import org.eclipse.swt.widgets.Composite;

public interface IPlusMinusEntry {

	public void addWidgets(Composite parent);

	public void removeWidgets();

	public PlusMinusComposite getPlusMinus();
	
}
