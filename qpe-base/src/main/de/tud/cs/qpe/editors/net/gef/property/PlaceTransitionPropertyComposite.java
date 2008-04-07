package de.tud.cs.qpe.editors.net.gef.property;

import java.beans.PropertyChangeListener;

import org.dom4j.Element;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.tud.cs.qpe.model.DocumentManager;

public abstract class PlaceTransitionPropertyComposite extends Composite implements PropertyChangeListener {
	protected Element _model;

	protected Text name;

	public PlaceTransitionPropertyComposite(Element model, Composite parent) {
		super(parent, SWT.BORDER);

		this._model = model;

		GridLayout layout = new GridLayout(1, false);
		layout.verticalSpacing = 8;
		layout.horizontalSpacing = 12;
		setLayout(layout);

		// Output a label for the name.
		new Label(this, SWT.NULL).setText("Name");

		// Output the editable textfield for the name.
		name = new Text(this, SWT.BORDER);
		name.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		// Update the Label in the editor if the value is changed.
		name.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String newValue = PlaceTransitionPropertyComposite.this.name.getText();
				DocumentManager.setAttribute(getModel(), "name", newValue);
			}
		});

		this.layout();
	}

	public void setModel(Element model) {
		this._model = model;
	}

	public Element getModel() {
		return _model;
	}

	public void activate() {
		DocumentManager.addPropertyChangeListener(getModel(), this);
	}

	public void deactivate() {
		DocumentManager.removePropertyChangeListener(getModel(), this);
	}

	protected void updatePropertyFields() {
		if (getModel() != null) {
			if(!name.getText().equals(getModel().attributeValue("name", "unamed-element"))) {
				name.setText(getModel().attributeValue("name", "unamed-element"));
			}
		}
	}
}
