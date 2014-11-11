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
 * 
 */
package de.tud.cs.qpe.editors.net.gef.property.transition;

import org.dom4j.Element;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tud.cs.qpe.utils.CellValidators;
import de.tud.cs.qpe.utils.XmlAttributeEditingSupport;
import de.tud.cs.qpe.utils.XmlAttributeLabelProvider;

public class TimedTransitionPropertyComposite extends TransitionPropertyComposite {

	public TimedTransitionPropertyComposite(Element net, Composite parent) {
		super(net, parent);
		initProperties();
		initColorTable();
	}

	public void updatePropertyFields() {
		super.updatePropertyFields();
	}

	protected void initProperties() {
		super.initProperties();
	}
	
	@Override
	protected Element createMode() {
		Element mode = super.createMode();
		mode.addAttribute("mean-firing-delay", "1.0");
		return mode;
	}

	protected void initTableColumns() {
		super.initTableColumns();
		
		TableViewerColumn col = new TableViewerColumn(modeTableViewer, SWT.LEFT);
		col.getColumn().setText("Mean Firing Delay");
		col.getColumn().setWidth(130);
		col.setLabelProvider(new XmlAttributeLabelProvider("mean-firing-delay", "1.0"));
		XmlAttributeEditingSupport editor = new XmlAttributeEditingSupport(col.getViewer(), "mean-firing-delay");
		editor.setValidator(CellValidators.newNonNegativeDoubleValidator());
		col.setEditingSupport(editor);
	}
}
