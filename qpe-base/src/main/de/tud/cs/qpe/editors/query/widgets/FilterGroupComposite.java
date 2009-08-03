package de.tud.cs.qpe.editors.query.widgets;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import de.tud.cs.qpe.editors.query.model.FilterGroup;

public class FilterGroupComposite<T> extends Composite {

	private PlusMinusManager<FilterGroupPlusMinusEntry<T>> plusMinusManager;
	private final FilterGroup<T> filterGroup;

	public FilterGroupComposite(Composite parent, int style, FilterGroup<T> filterGroup) {
		super(parent, style);
		this.filterGroup = filterGroup;
		setLayout(new GridLayout(2, false));
		this.plusMinusManager = new PlusMinusManager<FilterGroupPlusMinusEntry<T>>(this, parent.getParent(), new FilterGroupPlusMinusEntry.Provider<T>(filterGroup));
	}

	public Set<T> getFilterSet() {
		List<FilterGroupPlusMinusEntry<T>> entries = this.plusMinusManager.getEntries();
		Set<T> filterSet = new HashSet<T>();
		for (FilterGroupPlusMinusEntry<T> entry : entries) {
			if (entry.isAllSelected()) {
				for (T value : this.filterGroup.getItems()) {
					filterSet.add(value);
				}
				return filterSet;
			}
			if (null != entry.getValue()) {
				filterSet.add(entry.getValue());
			}
		}
		return filterSet;
	}
	
	public void setFilterSet(Set<T> filterSet) {
		this.plusMinusManager.clear();
		for (T item : filterSet) {
			FilterGroupPlusMinusEntry<T> entry = this.plusMinusManager.addEntry();
			entry.setValue(item);
		}
		if (filterSet.isEmpty()) {
			this.plusMinusManager.addEntry();
		}
	}
}
