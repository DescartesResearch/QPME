package de.tud.cs.qpe.editors.net;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.IPersistableElement;

import de.tud.cs.qpe.model.DocumentManager;

public class NetEditorInput implements IPathEditorInput {
	protected Element content;

	private IPath path;

	public NetEditorInput(IPath path) {
		if (path == null) {
			Document netDiagram = DocumentFactory.getInstance().createDocument();
			content = netDiagram.addElement("net");
			content.addElement("colors");
			content.addElement("places");
			content.addElement("transitions");
			content.addElement("connections");
		}
		// Read the file specified by the path parameter.
		else {
			this.path = path;
			try {
				SAXReader xmlReader = new SAXReader();
				Document netDiagram = xmlReader.read(path.toOSString());
				content = netDiagram.getRootElement();

				// Save the path so it can be used when saving.
				content.addAttribute("path", path.toOSString());
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}

		// register the new document with the
		// domcument manager. The document manager
		// is used to emulate property-change support
		// for non-event-based dom implementations.
		DocumentManager.registerInput(content.getDocument());
	}

	public boolean exists() {
		return path.toFile().exists();
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return content.attributeValue("name", "new net");
	}

	public IPersistableElement getPersistable() {
		return null;
	}

	public String getToolTipText() {
		if (path != null) {
			return path.toString();
		}
		return "";
	}

	public Object getAdapter(Class adapter) {
		return null;
	}

	public int hashCode() {
		return content.hashCode();
	}

	public Element getNetDiagram() {
		return content;
	}

	public IPath getPath() {
		return path;
	}

	public Document getDocument() {
		return content.getDocument();
	}
}
