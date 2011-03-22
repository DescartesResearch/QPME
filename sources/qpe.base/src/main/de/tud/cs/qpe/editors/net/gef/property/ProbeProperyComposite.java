package de.tud.cs.qpe.editors.net.gef.property;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.dom4j.Element;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import de.tud.cs.qpe.model.NetHelper;
import de.tud.cs.qpe.model.ProbeHelper;

public class ProbeProperyComposite extends ElementPropertyComposite implements IColorRefTableListener {

	protected ColorRefTable colorTable;
	
	public ProbeProperyComposite(Composite parent) {
		super(parent);
		setLayout(new GridLayout());
		initColorTable();
	}
	
	public void activate() {
		super.activate();
		colorTable.activate();
	}
	
	public void deactivate() {
		colorTable.deactivate();
		super.deactivate();
	}
	
	protected void initColorTable() {
		colorTable = new ColorRefTable(this) {
			@Override
			protected List<Element> getAvailableColors() {
				return NetHelper.listColors(getModel().getDocument().getRootElement());
			}
			
			@Override
			protected List<Element> getColorReferences() {
				return ProbeHelper.listColorReferences(getModel());
			}			
		};
		colorTable.addColorRefTableListener(this);
	}
	
	public void colorRefAdded(Element colorRef) {		
		ProbeHelper.addColorReference(getModel(), colorRef);
	}
	
	public void colorRefRemoved(Element colorRef) {
		ProbeHelper.removeColorReference(getModel(), colorRef);
	}
	
	public void colorRefModified(String oldColorId, Element colorRef) {
	}
}
