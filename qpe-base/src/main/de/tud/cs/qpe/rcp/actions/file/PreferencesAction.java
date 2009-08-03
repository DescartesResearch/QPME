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
