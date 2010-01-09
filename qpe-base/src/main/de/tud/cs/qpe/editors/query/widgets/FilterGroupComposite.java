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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import de.tud.cs.qpe.editors.query.model.FilterGroup;

public class FilterGroupComposite<T> extends Composite {

	private PlusMinusManager<FilterGroupPlusMinusEntry<T>> plusMinusManager;
	private final FilterGroup<T> filterGroup;

	public FilterGroupComposite(Composite parent, int style, FilterGroup<T> filterGroup) {
		super(parent, style);
		this.filterGroup = filterGroup;
		setLayout(new GridLayout(2, false));
		this.plusMinusManager = new PlusMinusManager<FilterGroupPlusMinusEntry<T>>(this, parent.getParent(), new FilterGroupPlusMinusEntry.Provider<T>(filterGroup));
	}

	public Set<T> getFilterSet() {
		List<FilterGroupPlusMinusEntry<T>> entries = this.plusMinusManager.getEntries();
		Set<T> filterSet = new HashSet<T>();
		for (FilterGroupPlusMinusEntry<T> entry : entries) {
			if (entry.isAllSelected()) {
				for (T value : this.filterGroup.getItems()) {
					filterSet.add(value);
				}
				return filterSet;
			}
			if (null != entry.getValue()) {
				filterSet.add(entry.getValue());
			}
		}
		return filterSet;
	}
	
	public void setFilterSet(Set<T> filterSet) {
		this.plusMinusManager.clear();
		for (T item : filterSet) {
			FilterGroupPlusMinusEntry<T> entry = this.plusMinusManager.addEntry();
			entry.setValue(item);
		}
		if (filterSet.isEmpty()) {
			this.plusMinusManager.addEntry();
		}
	}
}
