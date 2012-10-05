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
 *  2009/02/27  Frederik Zipp     Created.
 * 
 */
package de.tud.cs.qpe.editors.query.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tud.cs.qpe.editors.query.model.FilterGroup;

public class FilterGroupPlusMinusEntry<T> implements IPlusMinusEntry {

	private SelectorComposite<T> selector;
	private PlusMinusComposite plusMinus;
	private final FilterGroup<T> filterGroup;
	
	public FilterGroupPlusMinusEntry(FilterGroup<T> filterGroup) {
		this.filterGroup = filterGroup;
	}

	@Override
	public void addWidgets(Composite parent) {
		this.selector = new SelectorComposite<T>(parent, SWT.NONE, this.filterGroup.getItems(), true);
		this.plusMinus = new PlusMinusComposite(parent, SWT.NONE);
	}

	@Override
	public void removeWidgets() {
		this.selector.dispose();
		this.plusMinus.dispose();
	}

	@Override
	public PlusMinusComposite getPlusMinus() {
		return this.plusMinus;
	}

	public T getValue() {
		return this.selector.getSelection();
	}
	
	public void setValue(T value) {
		this.selector.setSelection(value);
	}
	
	public boolean isAllSelected() {
		return this.selector.isAllSelected();
	}

	public static class Provider<T> implements IPlusMinusEntryProvider<FilterGroupPlusMinusEntry<T>> {

		private final FilterGroup<T> filterGroup;

		public Provider(FilterGroup<T> filterGroup) {
			this.filterGroup = filterGroup;
		}
		
		@Override
		public FilterGroupPlusMinusEntry<T> createEntry() {
			return new FilterGroupPlusMinusEntry<T>(this.filterGroup);
		}
	}
}
