package de.tud.cs.qpe.editors.net.gef.palette.templates;

public class TimedTransition extends Transition {
	private static final long serialVersionUID = 1147062325019050602L;
	
	public TimedTransition() {
		super();
		addAttribute("type", "timed-transition");
	}
}
