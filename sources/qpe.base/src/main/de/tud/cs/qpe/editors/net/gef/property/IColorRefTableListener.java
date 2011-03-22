package de.tud.cs.qpe.editors.net.gef.property;

import org.dom4j.Element;

public interface IColorRefTableListener {
	
	public void colorRefAdded(Element colorRef);
	
	public void colorRefRemoved(Element colorRef);
	
	public void colorRefModified(String oldColorId, Element colorRef);

}
