package de.tud.cs.qpe.editors.net.gef.palette.templates;

import org.dom4j.tree.DefaultElement;

import de.tud.cs.qpe.utils.IdGenerator;

public abstract class PlaceTransition extends DefaultElement {
	public PlaceTransition(String arg0) {
		super(arg0);
		addAttribute("id", IdGenerator.get());
	}
}
