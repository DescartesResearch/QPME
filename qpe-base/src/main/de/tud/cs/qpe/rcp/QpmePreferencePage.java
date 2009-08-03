package de.tud.cs.qpe.rcp;

import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;

public class QpmePreferencePage extends FieldEditorPreferencePage {

	public QpmePreferencePage() {
		// Use the "flat" layout
		super(FLAT);
	}

	@Override
	protected void createFieldEditors() {
		// Add a directory field
		DirectoryFieldEditor dfe = new DirectoryFieldEditor("RDirectory",
				"R Directory:", getFieldEditorParent());
		addField(dfe);
	}

}
