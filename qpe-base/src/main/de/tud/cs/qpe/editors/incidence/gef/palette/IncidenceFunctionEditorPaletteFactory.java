/*******************************************************************************
 * Copyright (c) 2004 Elias Volanakis.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
  * which accompanies this distribution, and is available at
  * http://www.eclipse.org/legal/epl-v10.html
  *
  * Contributors:
  *    Elias Volanakis - initial API and implementation
 *    IBM Corporation
  *******************************************************************************/
package de.tud.cs.qpe.editors.incidence.gef.palette;

import org.eclipse.draw2d.Graphics;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PanningSelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;

import de.tud.cs.qpe.QPEBasePlugin;

/**
 * Utility class that can create a GEF Palette.
 * 
 * @see #createPalette()
 * @author Elias Volanakis
 */
public final class IncidenceFunctionEditorPaletteFactory {

	/** Preference ID used to persist the palette location. */
	private static final String PALETTE_DOCK_LOCATION = "IncidenceFunctionEditorPaletteFactory.Location";

	/** Preference ID used to persist the palette size. */
	private static final String PALETTE_SIZE = "IncidenceFunctionEditorPaletteFactory.Size";

	/** Preference ID used to persist the flyout palette's state. */
	private static final String PALETTE_STATE = "IncidenceFunctionEditorPaletteFactory.State";

	private static PaletteContainer createArcsDrawer() {
		// TODO: The count parameter has to be initialized to 1
		
		PaletteDrawer componentsDrawer = new PaletteDrawer("Connections");

		// Add the connection tools.
		ToolEntry tool = new ConnectionCreationToolEntry("Connection",
				"Create a Connection", new CreationFactory() {
					public Object getNewObject() {
						return null;
					}

					// see ShapeEditPart#createEditPolicies()
					// this is abused to transmit the desired line style
					public Object getObjectType() {
						return new Integer(Graphics.LINE_SOLID);
					}
				}, ImageDescriptor.createFromFile(QPEBasePlugin.class,
						"icons/connection_s16.gif"), ImageDescriptor
						.createFromFile(QPEBasePlugin.class,
								"icons/connection_s24.gif"));
		componentsDrawer.add(tool);

		return componentsDrawer;
	}

	/**
	 * Creates the PaletteRoot and adds all palette elements. Use this factory
	 * method to create a new palette for your graphical editor.
	 * 
	 * @return a new PaletteRoot
	 */
	public static PaletteRoot createPalette() {
		PaletteRoot palette = new PaletteRoot();
		palette.add(createToolsGroup(palette));
		palette.add(createArcsDrawer());
		return palette;
	}

	/**
	 * Return a FlyoutPreferences instance used to save/load the preferences of
	 * a flyout palette.
	 */
	public static FlyoutPreferences createPalettePreferences() {
		return new FlyoutPreferences() {
			private IPreferenceStore getPreferenceStore() {
				return QPEBasePlugin.getDefault().getPreferenceStore();
			}

			public int getDockLocation() {
				return getPreferenceStore().getInt(PALETTE_DOCK_LOCATION);
			}

			public int getPaletteState() {
				return getPreferenceStore().getInt(PALETTE_STATE);
			}

			public int getPaletteWidth() {
				return getPreferenceStore().getInt(PALETTE_SIZE);
			}

			public void setDockLocation(int location) {
				getPreferenceStore().setValue(PALETTE_DOCK_LOCATION, location);
			}

			public void setPaletteState(int state) {
				getPreferenceStore().setValue(PALETTE_STATE, state);
			}

			public void setPaletteWidth(int width) {
				getPreferenceStore().setValue(PALETTE_SIZE, width);
			}
		};
	}

	/** Create the "Tools" group. */
	private static PaletteContainer createToolsGroup(PaletteRoot palette) {
		PaletteGroup toolGroup = new PaletteGroup("Tools");

		// Add a selection tool to the group
		ToolEntry tool = new PanningSelectionToolEntry();
		toolGroup.add(tool);
		palette.setDefaultEntry(tool);

		// Add a marquee tool to the group
		// toolGroup.add(new MarqueeToolEntry());

		// Add a (unnamed) separator to the group
		// toolGroup.add(new PaletteSeparator());

		return toolGroup;
	}

	/** Utility class. */
	private IncidenceFunctionEditorPaletteFactory() {
		// Utility class
	}

}