package de.tud.cs.qpe.editors.query;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import de.tud.cs.qpe.editors.query.model.Color;
import de.tud.cs.qpe.editors.query.model.MetricQuery;
import de.tud.cs.qpe.editors.query.model.Place;
import de.tud.cs.qpe.editors.query.model.Query;
import de.tud.cs.qpe.editors.query.model.SimulationResults;
import de.tud.cs.qpe.editors.visualization.ChartGroup;
import de.tud.cs.qpe.editors.visualization.QueryVisualizer;
import de.tud.cs.qpe.editors.visualization.VisualizationEditor;
import de.tud.cs.qpe.editors.visualization.VisualizationEditorInput;

public abstract class AbstractQueryEditor extends EditorPart {

	private QueryVisualizer queryVisualizer;

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		if (!(input instanceof QueryEditorInput)) {
			throw new PartInitException("Invalid Input: Must be QueryEditorInput");
		}
		
		setSite(site);
		setInput(input);
		setPartName("Query Editor");
		
		this.queryVisualizer = new QueryVisualizer(getSimulationResults());
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// do nothing
	}

	@Override
	public void doSaveAs() {
		// do nothing
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void setFocus() {
		// do nothing
	}
	
	public abstract Query<Place, Color> getQuery();
	public abstract void setQuery(Query<Place, Color> query);

	protected SimulationResults getSimulationResults() {
		return ((QueryEditorInput) getEditorInput()).getSimulationResults(); 
	}

	protected QueryVisualizer getQueryVisualizer() {
		return this.queryVisualizer;
	}

	protected void addMetaInformation(Composite composite) {
		Composite c = new Composite(composite, SWT.NONE);
		RowLayout layout = new RowLayout(SWT.VERTICAL);
		layout.marginTop = 20;
		layout.marginLeft = 20;
		c.setLayout(layout);
		Font font = new Font(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().getDisplay(), new FontData("Arial", 12, SWT.BOLD));
		Label label = new Label(c, SWT.NONE);
		label.setText("Configuration: " + getSimulationResults().getConfigurationName());
		label.setFont(font);
		label = new Label(c, SWT.NONE);
		label.setText("Date: " + getSimulationResults().getDate());
		label.setFont(font);
	}

	protected IWorkbenchPage getActivePage() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
	}

	protected void visualize(Query<Place, Color> query) {
		checkMetricAvailabilities(query);
		showCharts(getQueryVisualizer().visualize(query));
	}

	private void checkMetricAvailabilities(Query<Place, Color> query) {
		System.out.println("--- Visualization ---");
		for (MetricQuery metricQuery : query.getMetricQueries()) {
			for (Place place : getSimulationResults().getPlaces()) {
				if (!place.hasMetric(metricQuery.getMetric())) {
					System.out.println("Warning: Place " + place + " has no metric " + metricQuery.getMetric());
				}
			}
		}
	}

	protected void showCharts(List<ChartGroup> charts) {
		try {
			getActivePage().openEditor(new VisualizationEditorInput(charts), VisualizationEditor.ID, true);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}
}
