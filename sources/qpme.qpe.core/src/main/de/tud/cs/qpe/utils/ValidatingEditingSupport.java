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
package de.tud.cs.qpe.utils;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public abstract class ValidatingEditingSupport extends EditingSupport {
	
	protected ControlDecoration validateMessages;
	protected CellEditor cellEditor;
	
	public ValidatingEditingSupport(ColumnViewer viewer) {
		super(viewer);
		this.cellEditor = createCellEditor((Composite) getViewer().getControl());
		this.cellEditor.addListener(new ICellEditorListener() {

			@Override
			public void applyEditorValue() {
				validateMessages.hide();
			}

			@Override
			public void cancelEditor() {
				validateMessages.hide();
			}

			@Override
			public void editorValueChanged(boolean oldValidState,
					boolean newValidState) {
				
				if (cellEditor.getErrorMessage() != null) {
					validateMessages.setDescriptionText(cellEditor.getErrorMessage());
					validateMessages.show();
				} else {
					validateMessages.hide();
				}
			}
			
		});
		
		validateMessages = new ControlDecoration(cellEditor.getControl(), SWT.TRAIL | SWT.TOP);
		FieldDecoration decoration = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);
		validateMessages.setImage(decoration.getImage());
		validateMessages.hide();
	}
	
	protected abstract CellEditor createCellEditor(Composite parent);

	@Override
	protected CellEditor getCellEditor(Object element) {
		return cellEditor;
	}
	
	public void setValidator(ICellEditorValidator validator) {
		this.cellEditor.setValidator(validator);
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}
}
