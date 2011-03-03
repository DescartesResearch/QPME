/* ==============================================
 * QPME : Queueing Petri net Modeling Environment
 * ==============================================
 *
 * (c) Copyright 2003-2010, by Samuel Kounev and Contributors.
 * 
 * Project Info:   http://descartes.ipd.kit.edu/projects/qpme/
 *                 http://www.descartes-research.net/
 *    
 * All rights reserved. This software is made available under the terms of the 
 * Eclipse Public License (EPL) v1.0 as published by the Eclipse Foundation
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * This software is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the Eclipse Public License (EPL)
 * for more details.
 *
 * You should have received a copy of the Eclipse Public License (EPL)
 * along with this software; if not visit http://www.eclipse.org or write to
 * Eclipse Foundation, Inc., 308 SW First Avenue, Suite 110, Portland, 97204 USA
 * Email: license (at) eclipse.org 
 *  
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *                                
 * =============================================
 *
 * Original Author(s):  Samuel Kounev and Christofer Dutz
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2006        Christofer Dutz   Created.
 *  2008/02/03  Frederik Zipp     Added 'queues' XML element. 
 *  2008/02/27  Frederik Zipp     Fixed file loading error.  
 * 
 */
package de.tud.cs.qpe.editors.net;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
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
			content.addElement("queues");
			content.addElement("places");
			content.addElement("transitions");
			content.addElement("connections");
		}
		// Read the file specified by the path parameter.
		else {
			this.path = path;
			try {
				SAXReader xmlReader = new SAXReader();
				Document netDiagram = xmlReader.read(path.toFile());
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
		IPath p = getPath();
		return (p == null) ? false : p.toFile().exists();
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
		IPath p = getPath();
		if (p != null) {
			return p.toString();
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
		if(path == null) {
			String pathValue = content.attributeValue("path");
			if(pathValue != null) {
				path = new Path(pathValue);
			}
		}
		return path;
	}

	public Document getDocument() {
		return content.getDocument();
	}
}
