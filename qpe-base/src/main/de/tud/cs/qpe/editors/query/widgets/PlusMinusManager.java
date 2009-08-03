package de.tud.cs.qpe.editors.query.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;


public class PlusMinusManager<T extends IPlusMinusEntry> {

	private final IPlusMinusEntryProvider<T> entryProvider;
	private List<T> entries;
	private final Composite parent;
	private final Composite layoutMaster;

	public PlusMinusManager(Composite parent, Composite layoutMaster, IPlusMinusEntryProvider<T> entryProvider) {
		this.parent = parent;
		this.layoutMaster = layoutMaster;
		this.entries = new ArrayList<T>();
		this.entryProvider = entryProvider;
		addEntry();
	}

	public T addEntry() {
		return addEntry(this.entryProvider.createEntry());
	}

	public T addEntry(final T entry) {
		entry.addWidgets(this.parent);
		entry.getPlusMinus().addListener(new IPlusMinusListener() {
			@Override
			public void onMinusEvent() {
				removeEntry(entry);
			}

			@Override
			public void onPlusEvent() {
				addEntry();
			}
		});
		this.entries.add(entry);
		update();
		return entry;
	}

	private void removeEntry(IPlusMinusEntry entry) {
		this.entries.remove(entry);
		entry.removeWidgets();
		update();
	}

	private void update() {
		updatePlusMinusVisibilities();
		this.layoutMaster.layout(true, true);
	}
	
	private void updatePlusMinusVisibilities() {
		for (IPlusMinusEntry entry : this.entries) {
			entry.getPlusMinus().setMinusEnabled(true);
			entry.getPlusMinus().setPlusEnabled(false);
		}
		if (this.entries.size() > 0) {
			getLastEntry().getPlusMinus().setPlusEnabled(true);
		}
		if (this.entries.size() == 1) {
			getLastEntry().getPlusMinus().setMinusEnabled(false);
		}
	}
	
	private IPlusMinusEntry getLastEntry() {
		return this.entries.get(this.entries.size() - 1);
	}
	
	public List<T> getEntries() {
		return this.entries;
	}

	public void clear() {
		for (T entry : this.entries) {
			entry.removeWidgets();
		}
		this.entries.clear();
		update();
	}
}
