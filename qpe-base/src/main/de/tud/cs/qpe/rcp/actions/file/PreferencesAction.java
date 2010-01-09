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
 *  2009/08/03  Frederik Zipp     Created.
 * 
 */
package de.tud.cs.qpe.rcp.actions.file;

import java.io.IOException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.ui.actions.ActionFactory;

import de.tud.cs.qpe.rcp.QpmePreferencePage;

public class PreferencesAction extends Action implements ActionFactory.IWorkbenchAction {
	public PreferencesAction() {
		setId(ActionFactory.NEW.getId());
		setText("Preferences");
		setToolTipText("Preferences");
	}

	public void run() {
		PreferenceManager mgr = new PreferenceManager();
		
		PreferenceNode pref = new PreferenceNode("general", "General Settings", null,
				QpmePreferencePage.class.getName());

		// Add the node
		mgr.addToRoot(pref);
		
		PreferenceDialog dlg = new PreferenceDialog(null, mgr);
		PreferenceStore ps = new PreferenceStore("qpme.properties");
		ps.setDefault("RDirectory", "");
	    try {
	    	ps.load();
	    } catch (IOException e) {
	    	// Ignore
	    }
	    dlg.setPreferenceStore(ps);
		dlg.open();

	    try {
	      // Save the preferences
	      ps.save();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}

	public void dispose() {
	}

}
