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
package de.tud.cs.qpe.editors.query;


import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import de.tud.cs.qpe.editors.query.model.Aggregation;
import de.tud.cs.qpe.editors.query.model.Color;
import de.tud.cs.qpe.editors.query.model.FilterGroup;
import de.tud.cs.qpe.editors.query.model.Place;
import de.tud.cs.qpe.editors.query.model.Query;
import de.tud.cs.qpe.editors.query.widgets.QueryComposite;
import de.tud.cs.qpe.editors.query.widgets.QueryCompositeConfiguration;
import de.tud.cs.qpe.editors.visualization.Visualization;

public class AdvancedQueryEditor extends AbstractQueryEditor {

	/** Editor ID */
	public static final String ID = "de.tud.cs.qpe.editor.query.advanced";
	
	private QueryComposite<Place, Color> queryComposite;
	
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		setPartName("Advanced Query Editor");
	}
	
	@Override
	public void createPartControl(Composite parent) {
		ScrolledComposite scroll = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		Composite composite = new Composite(scroll, SWT.NONE);
		addMetaInformation(composite);
		this.queryComposite = new QueryComposite<Place, Color>(composite, SWT.NONE, createConfiguration());
		Composite buttons = new Composite(composite, SWT.NONE);
		Button visualizeButton = new Button(buttons, SWT.NONE);
		visualizeButton.setText("Visualize");
		visualizeButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				visualize(getQuery());
			}
		});
		RowLayout layout = new RowLayout();
		layout.type = SWT.VERTICAL;
		composite.setLayout(layout);
		buttons.setLayout(new GridLayout());
		scroll.setContent(composite);
		scroll.setExpandHorizontal(true);
		scroll.setExpandVertical(true);
		scroll.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	private QueryCompositeConfiguration<Place, Color> createConfiguration() {
		FilterGroup<Place> placesFilter = new FilterGroup<Place>("Places", getSimulationResults().getPlaces(), Aggregation.values());
		FilterGroup<Color> colorsFilter = new FilterGroup<Color>("Colors", getSimulationResults().getColors(), Aggregation.values());
		return new QueryCompositeConfiguration<Place, Color>(placesFilter, colorsFilter, getSimulationResults().getAvailableColorMetrics(), Visualization.VISUALIZATIONS);
	}

	@Override
	public Query<Place, Color> getQuery() {
		return this.queryComposite.getQuery();
	}

	@Override
	public void setQuery(Query<Place, Color> query) {
		this.queryComposite.setQuery(query);
	}
}