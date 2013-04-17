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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;


public class PlusMinusManager<T extends IPlusMinusEntry> {

	private final IPlusMinusEntryProvider<T> entryProvider;
	private List<T> entries;
	private final Composite parent;
	private final Composite layoutMaster;

	public PlusMinusManager(Composite parent, Composite layoutMaster, IPlusMinusEntryProvider<T> entryProvider) {
		this.parent = parent;
		this.layoutMaster = layoutMaster;
		this.entries = new ArrayList<T>();
		this.entryProvider = entryProvider;
		addEntry();
	}

	public T addEntry() {
		return addEntry(this.entryProvider.createEntry());
	}

	public T addEntry(final T entry) {
		entry.addWidgets(this.parent);
		entry.getPlusMinus().addListener(new IPlusMinusListener() {
			@Override
			public void onMinusEvent() {
				removeEntry(entry);
			}

			@Override
			public void onPlusEvent() {
				addEntry();
			}
		});
		this.entries.add(entry);
		update();
		return entry;
	}

	private void removeEntry(IPlusMinusEntry entry) {
		this.entries.remove(entry);
		entry.removeWidgets();
		update();
	}

	private void update() {
		updatePlusMinusVisibilities();
		this.layoutMaster.layout(true, true);
	}
	
	private void updatePlusMinusVisibilities() {
		for (IPlusMinusEntry entry : this.entries) {
			entry.getPlusMinus().setMinusEnabled(true);
			entry.getPlusMinus().setPlusEnabled(false);
		}
		if (this.entries.size() > 0) {
			getLastEntry().getPlusMinus().setPlusEnabled(true);
		}
		if (this.entries.size() == 1) {
			getLastEntry().getPlusMinus().setMinusEnabled(false);
		}
	}
	
	private IPlusMinusEntry getLastEntry() {
		return this.entries.get(this.entries.size() - 1);
	}
	
	public List<T> getEntries() {
		return this.entries;
	}

	public void clear() {
		for (T entry : this.entries) {
			entry.removeWidgets();
		}
		this.entries.clear();
		update();
	}
}
