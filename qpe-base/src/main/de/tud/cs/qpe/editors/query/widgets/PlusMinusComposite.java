package de.tud.cs.qpe.editors.query.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class PlusMinusComposite extends Composite {

	private List<IPlusMinusListener> listeners;

	private Button minusButton;
	private Button plusButton;

	public PlusMinusComposite(Composite parent, int style) {
		super(parent, style);
		this.listeners = new ArrayList<IPlusMinusListener>();
		
		this.minusButton = new Button(this, SWT.NONE);
		this.minusButton.setText("-");
		this.minusButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				fireMinusEvent();
			}
		});
		this.plusButton = new Button(this, SWT.NONE);
		this.plusButton.setText("+");
		this.plusButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				firePlusEvent();
			}
		});
		setLayout(new FillLayout());
	}

	public void addListener(IPlusMinusListener listener) {
		synchronized (this.listeners) {
			if (listener != null) {
				this.listeners.add(listener);
			}
		}
	}
	
	public void removeListener(IPlusMinusListener listener) {
		synchronized (this.listeners) {
			if (listener != null) {
				this.listeners.remove(listener);
			}
		}
	}
	
	private void firePlusEvent() {
		for (IPlusMinusListener listener : this.listeners) {
			listener.onPlusEvent();
		}
	}

	private void fireMinusEvent() {
		for (IPlusMinusListener listener : this.listeners) {
			listener.onMinusEvent();
		}
	}

	public void setPlusEnabled(boolean b) {
		this.plusButton.setVisible(b);
	}
	
	public void setMinusEnabled(boolean b) {
		this.minusButton.setVisible(b);
	}
}
