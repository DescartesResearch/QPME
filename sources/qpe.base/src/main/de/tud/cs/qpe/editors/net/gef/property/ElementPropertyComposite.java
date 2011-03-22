package de.tud.cs.qpe.editors.net.gef.property;

import org.dom4j.Element;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public abstract class ElementPropertyComposite extends Composite {
	
	private Element model;

	public ElementPropertyComposite(Composite parent) {
		super(parent, SWT.BORDER);
	}
	
	public void setModel(Element model) {
		this.model = model;
	}

	public Element getModel() {
		return model;
	}
	
	public void activate() {
	}

	public void deactivate() {
	}

}
