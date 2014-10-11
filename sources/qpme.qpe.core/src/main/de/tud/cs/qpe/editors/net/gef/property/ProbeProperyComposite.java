/* ==============================================
 * QPME : Queueing Petri net Modeling Environment
 * ==============================================
 *
 * (c) Copyright 2003-2011, by Samuel Kounev and Contributors.
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
 * Original Author(s):  Simon Spinner
 * Contributor(s):      
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2011/03/22  Simon Spinner     Created.
 */
package de.tud.cs.qpe.editors.net.gef.property;

import java.util.List;

import javax.xml.XMLConstants;

import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
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
		colorRef.addAttribute(new QName("type", new Namespace("xsi", XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI)), "probe-color-reference");
		
		ProbeHelper.addColorReference(getModel(), colorRef);
	}
	
	public void colorRefRemoved(Element colorRef) {
		ProbeHelper.removeColorReference(getModel(), colorRef);
	}
	
	public void colorRefModified(String oldColorId, Element colorRef) {
	}
}
