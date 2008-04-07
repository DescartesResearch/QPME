package de.tud.cs.qpe.editors.incidence.view.figure;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;

public class NamedConnectionFigure extends PolylineConnection {
	protected Label name;

	public NamedConnectionFigure() {
		setTargetDecoration(new PolygonDecoration());
		MidpointLocator labelLocator = new MidpointLocator(this, 0);
		name = new Label("1");
		name.setOpaque(true);
		add(name, labelLocator);
	}

	public void setName(String name) {
		this.name.setText(name);
	}

	public String getName() {
		return name.getText();
	}
}
