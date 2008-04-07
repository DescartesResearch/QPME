package de.tud.cs.qpe.editors.incidence.model;

import org.dom4j.Element;

public abstract class PlaceWrapper {
	protected Element model;
	
	public PlaceWrapper(Element model) {
		this.model = model;
	}
	
	public Element getElement() {
		return model;
	}
}
