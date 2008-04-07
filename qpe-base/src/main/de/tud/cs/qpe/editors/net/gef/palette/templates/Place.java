package de.tud.cs.qpe.editors.net.gef.palette.templates;

public abstract class Place extends PlaceTransition {
	public Place() {
		super("place");
		addAttribute("departure-discipline", "NORMAL");
	}
}
