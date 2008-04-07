package de.tud.cs.qpe.editors.net.view.figure;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.handles.HandleBounds;

public abstract class BaseFigure extends Figure implements
		HandleBounds {
	protected Figure figure;

	private Label name;
	
	public BaseFigure() {
		ToolbarLayout layout = new ToolbarLayout();
		layout.setStretchMinorAxis(false);
		layout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);
		layout.setSpacing(2);
		setLayoutManager(layout);

		figure = createFigure();
		name = new Label();

		baseAdd(figure);
		baseAdd(name);
	}
	
	protected abstract Figure createFigure();

	public abstract ConnectionAnchor getConnectionAnchor();

	public void setName(String name) {
		this.name.setText(name);
	}

	public String getName() {
		return name.getText();
	}

	public void setBounds(Rectangle newBounds) {
		super.setBounds(newBounds);
		
		// Make Eclipse repaint the current figure
		invalidate();
		// If this is missing the relics of the figure
		// remain at the old figures position.
		if(getParent() != null) {
			getParent().repaint();
		}
		// TODO: Something is seting the figures bounds without the setBounds methods. This is why I have to manually fire a fígureMoved.
		fireFigureMoved();
	}
	
	public void baseAdd(IFigure figure) {
		super.add(figure, null, -1);
	}

	public Rectangle getHandleBounds() {
		return figure.getBounds();
	}
}
