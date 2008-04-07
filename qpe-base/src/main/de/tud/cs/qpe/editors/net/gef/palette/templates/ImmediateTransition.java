package de.tud.cs.qpe.editors.net.gef.palette.templates;

public class ImmediateTransition extends Transition {
	private static final long serialVersionUID = 3777812964704794964L;
	
	public ImmediateTransition() {
		super();
		addAttribute("type", "immediate-transition");
		addAttribute("weight", "1.0");
	}
}
