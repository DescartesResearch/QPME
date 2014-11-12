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
 * Original Author(s):  Frederik Zipp and Samuel Kounev
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2009/03/27  Frederik Zipp     Created.
 * 
 */
package de.tud.cs.qpe.editors.visualization;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;


public class VisualizationEditor extends EditorPart {

	public static final String ID = "de.tud.cs.qpe.editors.visualization";

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		if (!(input instanceof VisualizationEditorInput)) {
			throw new PartInitException("Invalid Input: Must be VisualizationEditorInput");
		}

		setSite(site);
		setInput(input);
	}

	public void createPartControl(Composite parent) {
		ScrolledComposite scroll = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		Composite composite = new Composite(scroll, SWT.NONE);
		RowLayout layout = new RowLayout();
		layout.marginTop = 20;
		layout.marginLeft = 20;
		layout.spacing = 20;
		composite.setLayout(layout);
		for (ChartGroup chartGroup : getChartGroups()) {
			for (SizedVisualizationComponent visu : chartGroup) {
				Composite c = visu.getVisualizationComponent().realize(composite);
				c.setLayoutData(new RowData(visu.getSize().width, visu.getSize().height));
			}
		}
		scroll.setContent(composite);
		scroll.setExpandHorizontal(true);
		scroll.setExpandVertical(true);
		scroll.setMinSize(composite.computeSize(parent.getSize().x, SWT.DEFAULT));
	}

//	public void createPartControl(Composite parent) {
//		Font font = new Font(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().getDisplay(), new FontData("Arial", 12, SWT.BOLD));
//		ScrolledComposite scroll = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
//		Composite composite = new Composite(scroll, SWT.NONE);
//		RowLayout layout = new RowLayout(SWT.VERTICAL);
//		layout.marginTop = 20;
//		layout.marginLeft = 20;
//		layout.spacing = 20;
//		layout.fill = true;
//		composite.setLayout(layout);
//		for (ChartGroup chartGroup : getChartGroups()) {
//			Label label = new Label(composite, SWT.NONE);
//			label.setText(chartGroup.getTitle());
//			label.setFont(font);
//			Composite groupComposite = new Composite(composite, SWT.NONE);
//			RowLayout groupLayout = new RowLayout(SWT.HORIZONTAL);
//			groupLayout.spacing = 20;
//			groupLayout.wrap = true;
//			groupComposite.setLayout(groupLayout);
//			for (SizedVisualizationComponent visu : chartGroup) {
//				Composite c = visu.getVisualizationComponent().realize(groupComposite);
//				c.setLayoutData(new RowData(visu.getSize().width, visu.getSize().height));
//			}
//		}
//		scroll.setContent(composite);
//		scroll.setExpandHorizontal(true);
//		scroll.setExpandVertical(true);
//		scroll.setMinSize(composite.computeSize(parent.getSize().x, SWT.DEFAULT));
//	}

	public void setFocus() {
		// do nothing
	}


	@Override
	public void doSave(IProgressMonitor monitor) {
		// do nothing
	}

	@Override
	public void doSaveAs() {
		// do nothing
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}
	
	private List<ChartGroup> getChartGroups() {
		return ((VisualizationEditorInput) getEditorInput()).getChartGroups(); 
	}
}
