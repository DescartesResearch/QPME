package de.tud.cs.qpe.editors.query;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import de.tud.cs.qpe.editors.query.model.Aggregation;
import de.tud.cs.qpe.editors.query.model.Color;
import de.tud.cs.qpe.editors.query.model.HistogramType;
import de.tud.cs.qpe.editors.query.model.Metric;
import de.tud.cs.qpe.editors.query.model.MetricQuery;
import de.tud.cs.qpe.editors.query.model.Place;
import de.tud.cs.qpe.editors.query.model.Query;
import de.tud.cs.qpe.editors.r.REditor;
import de.tud.cs.qpe.editors.r.REditorInput;
import de.tud.cs.qpe.editors.visualization.PieChartVisualization;
import de.tud.cs.qpe.editors.visualization.Visualization;

public class SimpleQueryEditor extends AbstractQueryEditor {

	/** Editor ID */
	public static final String ID = "de.tud.cs.qpe.editor.query.simple";

	private static final Dimension DEFAULT_SIZE = new Dimension(640, 480);
	
	private static final String[] nonQueuesFilter = { "place", "qplace:queue", "qplace:depository" };
	private static final String[] queuesFilter = { "queue" };
	
	private PlacesColorsTable placesColorsTable;
	private PlacesColorsTable queuesTable;
	
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		setPartName("Simple Query Editor");
	}
	
	@Override
	public void createPartControl(Composite parent) {
		ScrolledComposite scroll = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		Composite composite = new Composite(scroll, SWT.NONE);
		addMetaInformation(composite);
		this.placesColorsTable = new PlacesColorsTable(composite, SWT.NONE, getSimulationResults(), nonQueuesFilter);
		Menu popupMenu = createPopupMenu(this.placesColorsTable);
		addHistogramMenuItems(popupMenu, this.placesColorsTable);
		this.placesColorsTable.setMenu(popupMenu);
		this.queuesTable = new PlacesColorsTable(composite, SWT.NONE, getSimulationResults(), queuesFilter, "Queue");
		this.queuesTable.setMenu(createPopupMenu(this.queuesTable));
		Composite buttons = new Composite(composite, SWT.NONE);
		buttons.setLayout(new RowLayout(SWT.HORIZONTAL));
		Button advancedButton = new Button(buttons, SWT.NONE);
		advancedButton.setText("Open Advanced Query Editor");
		advancedButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				enterAdvancedQueryEditor();
			}
		});
		Button rButton = new Button(buttons, SWT.NONE);
		rButton.setText("Open R Editor");
		rButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				enterREditor();
			}
		});
		RowLayout layout = new RowLayout();
		layout.type = SWT.VERTICAL;
		layout.spacing = 20;
		composite.setLayout(layout);
		buttons.setLayout(new GridLayout());
		scroll.setContent(composite);
		scroll.setExpandHorizontal(true);
		scroll.setExpandVertical(true);
		scroll.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	private Menu createPopupMenu(PlacesColorsTable table) {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		Menu menu = new Menu(shell, SWT.POP_UP);
		for (Metric metric : table.getMetrics()) {
			MenuItem menuItem = new MenuItem(menu, SWT.CASCADE);
			menuItem.setText(metric.getName());
			Menu subMenu = new Menu(menu);
			menuItem.setMenu(subMenu);
			for (Visualization visualization : Visualization.VISUALIZATIONS) {
				if (visualization.isAvailableForMetric(metric)) {
					MenuItem subMenuItem = new MenuItem(subMenu, SWT.PUSH);
					subMenuItem.setText(visualization.getName());
					subMenuItem.addListener(SWT.Selection, new PopupMenuSelectionListener(table, metric, visualization));
				}
			}
		}
		return menu;
	}

	private void addHistogramMenuItems(Menu menu, final PlacesColorsTable table) {
		for (final HistogramType histogramType : getSimulationResults().getAvailableHistogramTypes()) {
			MenuItem menuItem = new MenuItem(menu, SWT.CASCADE);
			menuItem.setText(histogramType.getName());
			Menu subMenu = new Menu(menu);
			menuItem.setMenu(subMenu);
			MenuItem subMenuItem = new MenuItem(subMenu, SWT.PUSH);
			subMenuItem.setText("Histogram");
			subMenuItem.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event event) {
					showCharts(getQueryVisualizer().visualizeHistograms(histogramType, table.getSelectedPlaces(), table.getSelectedColors(), DEFAULT_SIZE));
				}
			});
		}
	}

	private void enterAdvancedQueryEditor() {
		String editor = AdvancedQueryEditor.ID;
		IEditorInput input = new QueryEditorInput(((QueryEditorInput) getEditorInput()).getPath()); 
		try {
			AdvancedQueryEditor queryEditor = (AdvancedQueryEditor) getActivePage().openEditor(input, editor, true);
			queryEditor.setQuery(getQuery());
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	private void enterREditor() {
		try {
			getActivePage().openEditor(new REditorInput(), REditor.ID, true);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Query<Place, Color> getQuery() {
		return getQuery(this.placesColorsTable, getSelectedMetric(), getSelectedVisualization());
	}

	private Query<Place, Color> getQuery(PlacesColorsTable table, Metric metric, Visualization visualization) {
		List<MetricQuery> metricQueries = new ArrayList<MetricQuery>();
		metricQueries.add(new MetricQuery("", metric, Aggregation.FOR_EACH, Aggregation.FOR_EACH, visualization, visualization, DEFAULT_SIZE));
		return new Query<Place, Color>(
				table.getSelectedPlaces(),
				table.getSelectedColors(),
				metricQueries);
	}

	private Visualization getSelectedVisualization() {
		// TODO
		return new PieChartVisualization();
	}

	private Metric getSelectedMetric() {
		// TODO
		return Metric.ARRIV_THR_PUT;
	}

	@Override
	public void setQuery(Query<Place, Color> query) {
		throw new NotImplementedException();
	}
	
	private class PopupMenuSelectionListener implements Listener {

		private final Metric metric;
		private final Visualization visualization;
		private final PlacesColorsTable table;

		public PopupMenuSelectionListener(PlacesColorsTable table, Metric metric, Visualization visualization) {
			this.table = table;
			this.metric = metric;
			this.visualization = visualization;
		}
		
		@Override
		public void handleEvent(Event event) {
			visualize(getQuery(this.table, this.metric, this.visualization));
		}
	}
}
