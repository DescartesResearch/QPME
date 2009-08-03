package de.tud.cs.qpe.editors.query;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import de.tud.cs.qpe.editors.query.model.Color;
import de.tud.cs.qpe.editors.query.model.Metric;
import de.tud.cs.qpe.editors.query.model.MetricValue;
import de.tud.cs.qpe.editors.query.model.Place;
import de.tud.cs.qpe.editors.query.model.SimulationResults;

public class PlacesColorsTable extends Composite {

	private final SimulationResults simulationResults;
	private final List<Metric> metrics;
	private final List<Place> places;
	private final Tree tree;
	private final String[] types;
	private final String title;

	public PlacesColorsTable(Composite parent, int style, SimulationResults simulationResults, String[] types) {
		this(parent, style, simulationResults, types, "Place/Color");
	}
	
	public PlacesColorsTable(Composite parent, int style, SimulationResults simulationResults, String[] types, String title) {
		super(parent, style);
		setLayout(new FillLayout());

		this.simulationResults = simulationResults;
		this.types = types;
		this.title = title;

		this.places = Arrays.asList(this.simulationResults.getPlacesFiltered(this.types));
		this.metrics = new ArrayList<Metric>();
		this.metrics.addAll(Arrays.asList(this.simulationResults.getAvailablePlaceMetrics(this.places)));
		this.metrics.addAll(Arrays.asList(this.simulationResults.getAvailableColorMetrics(this.places)));
		
		this.tree = new Tree(this, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		this.tree.setLinesVisible(true);
		this.tree.setHeaderVisible(true);
		this.tree.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// do nothing
			}
			@Override
			public void widgetSelected(SelectionEvent e) {
				TreeItem item = ((TreeItem) e.item);
				if (item.getData() instanceof Place) {
					if (isSelected(item)) {
						selectAllChildren(item);
					}
				}
			}
		});
		
		createColumns();

		TreeViewer treeViewer = new TreeViewer(this.tree);
		treeViewer.setContentProvider(new ContentProvider());
		treeViewer.setLabelProvider(new LabelProvider());
		treeViewer.setInput(this.simulationResults);
		
		this.tree.getColumn(0).pack();
		treeViewer.expandAll();
		treeViewer.collapseAll();
	}

	private void createColumns() {
		// First column
		TreeColumn column = new TreeColumn(this.tree, SWT.LEFT, 0);
		column.setWidth(170);
		column.setText(this.title);
		// Metric columns
		for (int i = 0; i < this.metrics.size(); i++) {
			column = new TreeColumn(this.tree, SWT.LEFT, i + 1);
			column.setText(this.metrics.get(i).getName());
			column.pack();
			column.setWidth(column.getWidth() + 5);
		}
	}
	
	private boolean isSelected(TreeItem item) {
		for (TreeItem selectedItem : this.tree.getSelection()) {
			if (item == selectedItem) {
				return true;
			}
		}
		return false;
	}

	private void selectAllChildren(TreeItem item) {
		for (TreeItem child : item.getItems()) {
			this.tree.select(child);
		}		
	}
	
	public Set<Place> getSelectedPlaces() {
		Set<Place> places = new HashSet<Place>();
		TreeItem[] selection = this.tree.getSelection();
		for (TreeItem treeItem : selection) {
			Object data = treeItem.getData();
			if (data instanceof Place) {
				places.add((Place) data);
			}
		}
		return places;
	}
	
	public Set<Color> getSelectedColors() {
		Set<Color> colors = new HashSet<Color>();
		TreeItem[] selection = this.tree.getSelection();
		for (TreeItem treeItem : selection) {
			Object data = treeItem.getData();
			if (data instanceof PlaceColor) {
				colors.add(((PlaceColor) data).getColor());
			}
		}
		return colors;
	}
	
	public List<Metric> getMetrics() {
		return this.metrics;
	}

	@Override
	public void setMenu(Menu menu) {
		this.tree.setMenu(menu);
	}
	
	private final class LabelProvider implements ITableLabelProvider {
		
		private final NumberFormat NUMBER_FORMAT = new DecimalFormat();
		private final String NOT_AVAILABLE = null;

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (columnIndex == 0) {
				return element.toString();
			}
			if (element instanceof Place) {
				Place p = (Place) element;
				MetricValue metricValue = simulationResults.getMetricValue(p, metrics.get(columnIndex - 1));
				if (metricValue != null) {
					return NUMBER_FORMAT.format(metricValue.getValue());
				}
				return NOT_AVAILABLE;
			}
			if (element instanceof PlaceColor) {
				PlaceColor pc = (PlaceColor) element;
				MetricValue metricValue = simulationResults.getMetricValue(pc.getPlace(), pc.getColor(), metrics.get(columnIndex - 1));
				if (metricValue != null) {
					return NUMBER_FORMAT.format(metricValue.getValue());
				}
				return NOT_AVAILABLE;
			}
			return null;
		}

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public void addListener(ILabelProviderListener listener) {
			// do nothing
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
			// do nothing
		}

		@Override
		public void dispose() {
			// do nothing
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			return true;
		}
	}

	private final class ContentProvider implements ITreeContentProvider {
		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof Place) {
				Place place = (Place) parentElement;
				Set<Color> colors = place.getColors();
				PlaceColor[] placeColors = new PlaceColor[colors.size()];
				int i = 0;
				for (Color color : colors) {
					placeColors[i] = new PlaceColor(place, color);
					i++;
				}
				return placeColors;
			}
			return null;
		}

		@Override
		public Object getParent(Object element) {
			if (element instanceof PlaceColor) {
				return ((PlaceColor) element).getPlace();
			}
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			if (element instanceof Place) {
				return ((Place) element).hasColors();
			}
			return false;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			SimulationResults simulationResults = (SimulationResults) inputElement;
			return simulationResults.getPlacesFiltered(types);
		}

		@Override
		public void dispose() {
			// do nothing
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// do nothing
		}
	}

	private static class PlaceColor {
		private Place place;
		private Color color;
		
		public PlaceColor(Place place, Color color) {
			this.place = place;
			this.color = color;
		}
		
		public Place getPlace() {
			return this.place;
		}

		public Color getColor() {
			return this.color;
		}
		
		@Override
		public String toString() {
			return this.color.toString();
		}
	}
}
