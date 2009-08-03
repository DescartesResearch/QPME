package de.tud.cs.qpe.editors.query;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.IPersistableElement;

import de.tud.cs.qpe.editors.query.model.SimulationResults;

public class QueryEditorInput implements IPathEditorInput {

	private final IPath path;
	private Element data;
	private SimulationResults simulationResults;

	public QueryEditorInput(IPath path) {
		this.path = path;
		if (path != null) {
			try {
				SAXReader xmlReader = new SAXReader();
				Document doc = xmlReader.read(path.toFile());
				this.data = doc.getRootElement();
				this.simulationResults = new SimulationResults(this.data);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public IPath getPath() {
		return this.path;
	}

	@Override
	public boolean exists() {
		return this.path.toFile().exists();
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return "Input name";
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		if (this.path != null) {
			return this.path.toString();
		}
		return "";
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object getAdapter(Class adapter) {
		return null;
	}

	public SimulationResults getSimulationResults() {
		return this.simulationResults;
	}
	
	public Element getData() {
		return this.data;
	}
}
