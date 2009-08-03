package de.tud.cs.qpe.editors.query.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tud.cs.qpe.editors.query.model.FilterGroup;

public class FilterGroupPlusMinusEntry<T> implements IPlusMinusEntry {

	private SelectorComposite<T> selector;
	private PlusMinusComposite plusMinus;
	private final FilterGroup<T> filterGroup;
	
	public FilterGroupPlusMinusEntry(FilterGroup<T> filterGroup) {
		this.filterGroup = filterGroup;
	}

	@Override
	public void addWidgets(Composite parent) {
		this.selector = new SelectorComposite<T>(parent, SWT.NONE, this.filterGroup.getItems(), true);
		this.plusMinus = new PlusMinusComposite(parent, SWT.NONE);
	}

	@Override
	public void removeWidgets() {
		this.selector.dispose();
		this.plusMinus.dispose();
	}

	@Override
	public PlusMinusComposite getPlusMinus() {
		return this.plusMinus;
	}

	public T getValue() {
		return this.selector.getSelection();
	}
	
	public void setValue(T value) {
		this.selector.setSelection(value);
	}
	
	public boolean isAllSelected() {
		return this.selector.isAllSelected();
	}

	public static class Provider<T> implements IPlusMinusEntryProvider<FilterGroupPlusMinusEntry<T>> {

		private final FilterGroup<T> filterGroup;

		public Provider(FilterGroup<T> filterGroup) {
			this.filterGroup = filterGroup;
		}
		
		@Override
		public FilterGroupPlusMinusEntry<T> createEntry() {
			return new FilterGroupPlusMinusEntry<T>(this.filterGroup);
		}
	}
}
