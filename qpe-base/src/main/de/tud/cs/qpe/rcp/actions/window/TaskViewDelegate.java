package de.tud.cs.qpe.rcp.actions.window;

public class TaskViewDelegate extends AbstractBaseViewDelegate {
	protected String getViewId() {
		return "org.eclipse.ui.views.TaskList";
	}

	protected String getPartName() {
		return "Tasks";
	}
}
