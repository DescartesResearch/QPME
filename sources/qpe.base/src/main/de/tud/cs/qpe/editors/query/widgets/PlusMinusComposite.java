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

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class PlusMinusComposite extends Composite {

	private List<IPlusMinusListener> listeners;

	private Button minusButton;
	private Button plusButton;

	public PlusMinusComposite(Composite parent, int style) {
		super(parent, style);
		this.listeners = new ArrayList<IPlusMinusListener>();
		
		this.minusButton = new Button(this, SWT.NONE);
		this.minusButton.setText("-");
		this.minusButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				fireMinusEvent();
			}
		});
		this.plusButton = new Button(this, SWT.NONE);
		this.plusButton.setText("+");
		this.plusButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				firePlusEvent();
			}
		});
		setLayout(new FillLayout());
	}

	public void addListener(IPlusMinusListener listener) {
		synchronized (this.listeners) {
			if (listener != null) {
				this.listeners.add(listener);
			}
		}
	}
	
	public void removeListener(IPlusMinusListener listener) {
		synchronized (this.listeners) {
			if (listener != null) {
				this.listeners.remove(listener);
			}
		}
	}
	
	private void firePlusEvent() {
		for (IPlusMinusListener listener : this.listeners) {
			listener.onPlusEvent();
		}
	}

	private void fireMinusEvent() {
		for (IPlusMinusListener listener : this.listeners) {
			listener.onMinusEvent();
		}
	}

	public void setPlusEnabled(boolean b) {
		this.plusButton.setVisible(b);
	}
	
	public void setMinusEnabled(boolean b) {
		this.minusButton.setVisible(b);
	}
}
