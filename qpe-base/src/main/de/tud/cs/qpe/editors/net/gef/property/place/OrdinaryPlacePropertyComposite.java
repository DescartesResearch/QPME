package de.tud.cs.qpe.editors.net.gef.property.place;

import org.dom4j.Element;
import org.eclipse.swt.widgets.Composite;

public class OrdinaryPlacePropertyComposite extends PlacePropertyComposite {

	public OrdinaryPlacePropertyComposite(Element net, Composite parent) {
		super(net, parent);
		columnNames = new String[] { "Color", "Initial", "Max" };
		initProperties();
		initColorTable();
	}
}
