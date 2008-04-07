package de.tud.cs.qpe.rcp.actions.window;

public class ConsoleViewDelegate extends AbstractBaseViewDelegate {
	protected String getViewId() {
		return "org.eclipse.ui.console.ConsoleView";
	}

	protected String getPartName() {
		return "Console";
	}
}
