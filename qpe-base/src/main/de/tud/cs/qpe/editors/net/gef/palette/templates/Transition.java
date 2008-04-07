package de.tud.cs.qpe.editors.net.gef.palette.templates;

public abstract class Transition extends PlaceTransition {
	public Transition() {
		super("transition");
		addAttribute("priority", "0");
	}
}
