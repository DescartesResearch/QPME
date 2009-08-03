package de.tud.cs.qpe.editors.query.widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class SelectorComposite<T> extends Composite {

	private Combo combo;
	private List<T> possibleItems;
	private List<T> shownItems;
	private boolean selectAllEnabled;
	private List<ISelectionListener> listeners;

	public SelectorComposite(Composite parent, int style, T[] items) {
		this(parent, style, items, false);
	}
	
	public SelectorComposite(Composite parent, int style, T[] items, boolean selectAllEnabled) {
		super(parent, style);
		this.listeners = new ArrayList<ISelectionListener>();
		this.possibleItems = Arrays.asList(items);
		this.shownItems = new ArrayList<T>(this.possibleItems);
		this.selectAllEnabled = selectAllEnabled;
		setLayout(new FillLayout());
		this.combo = new Combo(this, SWT.READ_ONLY | SWT.DROP_DOWN);		
		this.combo.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				fireSelectionEvent();
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				fireSelectionEvent();
			}
		});
		updateCombo();
		this.combo.select(0);
		setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}
	
	public T getSelection() {
		if (isNoneSelected() || isAllSelected()) {
			return null;
		}
		String selectedItem = this.combo.getItem(this.combo.getSelectionIndex());
		return getItem(selectedItem);
	}
	

	public void setSelection(T item) {
		int index = this.combo.indexOf(item.toString());
		this.combo.select((index == -1) ? 0 : index);
		fireSelectionEvent();
	}

	private T getItem(String name) {
		for (T item : this.possibleItems) {
			if (item.toString().equals(name)) {
				return item;
			}
		}
		return null;
	}
	
	public boolean isSelectAllEnabled() {
		return this.selectAllEnabled;
	}
	
	public boolean hasSelection() {
		return getSelection() != null || isAllSelected();
	}
	
	public boolean isNoneSelected() {
		return this.combo.getSelectionIndex() == 0;
	}

	public boolean isAllSelected() {
		return isSelectAllEnabled() && this.combo.getSelectionIndex() == 1;
	}
	
	public void filterItems(List<T> shown) {
		T previousSelected = getSelection();
		this.shownItems.clear();
		for (T item : this.possibleItems) {
			if (shown.contains(item)) {
				this.shownItems.add(item);
			}
		}
		updateCombo();
		setSelection(previousSelected);
	}
	
	private void updateCombo() {
		this.combo.add("- select -");
		if (this.selectAllEnabled) {
			this.combo.add("All");
		}
		for (T item : this.shownItems) {
			this.combo.add(item.toString());
		}
	}
	
	public void addListener(ISelectionListener listener) {
		synchronized (this.listeners) {
			if (listener != null) {
				this.listeners.add(listener);
			}
		}
	}
	
	public void removeListener(ISelectionListener listener) {
		synchronized (this.listeners) {
			if (listener != null) {
				this.listeners.remove(listener);
			}
		}
	}
	
	private void fireSelectionEvent() {
		for (ISelectionListener listener : this.listeners) {
			listener.onSelectionEvent();
		}
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		this.combo.setEnabled(enabled);
	}
}
