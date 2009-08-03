package de.tud.cs.qpe.editors.visualization;

import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;


public class VisualizationEditorInput implements IEditorInput {

	private final List<ChartGroup> chartGroups;

	public VisualizationEditorInput(List<ChartGroup> chartGroups) {
		this.chartGroups = chartGroups;
	}
	
	public List<ChartGroup> getChartGroups() {
		return this.chartGroups;
	}

	@Override
	public boolean exists() {
		return true;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return "Chart Visualization";
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return "";
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object getAdapter(Class adapter) {
		return null;
	}
}
