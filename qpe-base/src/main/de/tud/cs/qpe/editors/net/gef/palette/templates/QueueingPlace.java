package de.tud.cs.qpe.editors.net.gef.palette.templates;

public class QueueingPlace extends Place {
	private static final long serialVersionUID = -4386671664737573811L;
	
	public QueueingPlace() {
		super();
		addAttribute("type", "queueing-place");
		addAttribute("strategy", "FCFS");
		addAttribute("number-of-servers", "1");
	}
}
