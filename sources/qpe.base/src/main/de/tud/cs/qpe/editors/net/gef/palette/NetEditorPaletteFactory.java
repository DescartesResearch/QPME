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
 *  2006        Christofer Dutz   Created.
 *  2008/12/17  Frederik Zipp     Fixed warnings.
 * 
 */

/*******************************************************************************
 * Copyright (c) 2004 Elias Volanakis.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Elias Volanakis - initial API and implementation
 *    IBM Corporation
 *******************************************************************************/
package de.tud.cs.qpe.editors.net.gef.palette;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.Graphics;
import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
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
import org.osgi.framework.Bundle;

import de.tud.cs.qpe.QPEBasePlugin;
import de.tud.cs.qpe.editors.net.controller.factory.DomElementFactory;
import de.tud.cs.qpe.editors.net.gef.palette.templates.ImmediateTransition;
import de.tud.cs.qpe.editors.net.gef.palette.templates.OrdinaryPlace;
import de.tud.cs.qpe.editors.net.gef.palette.templates.QueueingPlace;
import de.tud.cs.qpe.editors.net.gef.palette.templates.SubnetPlace;
import de.tud.cs.qpe.editors.net.gef.palette.templates.TimedTransition;

/**
 * Utility class that can create a GEF Palette.
 * 
 * @see #createPalette()
 * @author Elias Volanakis
 */
public final class NetEditorPaletteFactory {

	/** Preference ID used to persist the palette location. */
	private static final String PALETTE_DOCK_LOCATION = "NetEditorPaletteFactory.Location";

	/** Preference ID used to persist the palette size. */
	private static final String PALETTE_SIZE = "NetEditorPaletteFactory.Size";

	/** Preference ID used to persist the flyout palette's state. */
	private static final String PALETTE_STATE = "NetEditorPaletteFactory.State";

	/** Create the "Places" drawer. */
	private static PaletteContainer createPlacesDrawer() {
		PaletteDrawer componentsDrawer = new PaletteDrawer("Places");

		// Add the place tools.
		componentsDrawer.add(new CombinedTemplateCreationEntry(
				"Ordinary Place", "Create an Ordinary Place",
				OrdinaryPlace.class,
				new DomElementFactory(OrdinaryPlace.class),
				getImageDescriptor("images/OrdinaryPlace.gif"),
				getImageDescriptor("images/OrdinaryPlace.gif")));
		componentsDrawer.add(new CombinedTemplateCreationEntry(
				"Queueing Place", "Create a Queueing Place",
				QueueingPlace.class,
				new DomElementFactory(QueueingPlace.class),
				getImageDescriptor("images/QueueingPlace.gif"),
				getImageDescriptor("images/QueueingPlace.gif")));
		componentsDrawer.add(new CombinedTemplateCreationEntry("Subnet Place",
				"Create a Subnet Place", SubnetPlace.class,
				new DomElementFactory(SubnetPlace.class),
				getImageDescriptor("images/SubnetPlace.gif"),
				getImageDescriptor("images/SubnetPlace.gif")));

		return componentsDrawer;
	}

	/** Create the "Transitions" drawer. */
	private static PaletteContainer createTransitionsDrawer() {
		PaletteDrawer componentsDrawer = new PaletteDrawer("Transitions");

		// Add the transition tools.
		componentsDrawer.add(new CombinedTemplateCreationEntry(
				"Immediate Transition", "Create a Immediate Transition",
				ImmediateTransition.class, new DomElementFactory(
						ImmediateTransition.class),
				getImageDescriptor("images/ImmediateTransition.gif"),
				getImageDescriptor("images/ImmediateTransition.gif")));
		componentsDrawer.add(new CombinedTemplateCreationEntry(
				"Timed Transition", "Create a Timed Transition",
				TimedTransition.class, new DomElementFactory(
						TimedTransition.class),
				getImageDescriptor("images/TimedTransition.gif"),
				getImageDescriptor("images/TimedTransition.gif")));

		return componentsDrawer;
	}

	private static PaletteContainer createConnectionsDrawer() {
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
				}, getImageDescriptor("images/Connection.gif"),
				getImageDescriptor("images/Connection.gif"));
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
		palette.add(createPlacesDrawer());
		palette.add(createTransitionsDrawer());
		palette.add(createConnectionsDrawer());
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
	private NetEditorPaletteFactory() {
		// Utility class
	}

	public static ImageDescriptor getImageDescriptor(final String fileName) {
		Bundle bundle = Platform.getBundle(QPEBasePlugin.PLUGIN_ID);
		final URL installURL = FileLocator.find(bundle, new Path("/"), null);

		try {
			final URL url = new URL(installURL, fileName);
			return ImageDescriptor.createFromURL(url);
		} catch (MalformedURLException mue) {
			return null;
		}
	}

}