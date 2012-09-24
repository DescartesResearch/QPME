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
�* http://www.eclipse.org/legal/epl-v10.html
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
 * Original Author(s):  Simon Spinner
 * Contributor(s):      
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2011/03/22  Simon Spinner     Created.
 */
package de.tud.cs.simqpn.ui.model;

import org.dom4j.Element;
import org.eclipse.jface.viewers.ICellEditorValidator;

import de.tud.cs.qpe.model.ColorReferenceHelper;
import de.tud.cs.qpe.model.NetHelper;
import de.tud.cs.qpe.utils.CellValidators;

public class WelchConfiguration extends Configuration {

	private static final String MIN_OBSRV = "minObsrv";
	private static final String MAX_OBSRV = "maxObsrv";
	private static final String QUEUE_MIN_OBSRV = "queueMinObsrv";
	private static final String QUEUE_MAX_OBSRV = "queueMaxObsrv";
	
	@Override
	protected String[] initAttributeNames() {
		String[] attributeNames = new String[MAX_NUM_ATTRIBUTES];
		attributeNames[0] = null;
		attributeNames[1] = STATS_LEVEL;
		attributeNames[2] = MIN_OBSRV;
		attributeNames[3] = MAX_OBSRV;
		attributeNames[4] = null;
		attributeNames[5] = null;
		attributeNames[6] = null;
		attributeNames[7] = null;
		attributeNames[8] = null;
		attributeNames[9] = null;
		return attributeNames;
	}
	
	@Override
	protected String[] initQueueAttributeNames() {
		String[] attributeNames = new String[MAX_NUM_ATTRIBUTES];
		attributeNames[0] = null;
		attributeNames[1] = null;
		attributeNames[2] = QUEUE_MIN_OBSRV;
		attributeNames[3] = QUEUE_MAX_OBSRV;
		attributeNames[4] = null;
		attributeNames[5] = null;
		attributeNames[6] = null;
		attributeNames[7] = null;
		attributeNames[8] = null;
		attributeNames[9] = null;
		return attributeNames;
	}
	
	@Override
	public ICellEditorValidator getValidator(int colIndex) {
		switch(colIndex) {
		case 2:
			return CellValidators.newNonNegativeIntegerValidator();
		case 3:
			return CellValidators.newNonNegativeIntegerValidator();
		}
		return null;
	}
	
	@Override
	public void createSimulationConfiguration(Element net, String configName) {
		if (getMetadata() == null) {
			Element metadata = NetHelper.createSimqpnConfigurationMetadata(net, configName);
			metadata.addAttribute("scenario", "3");
			metadata.addAttribute("number-of-runs", "100");
			metadata.addAttribute("total-run-length", "10000000");
			metadata.addAttribute("stopping-rule", "FIXEDLEN");
			metadata.addAttribute("time-before-initial-heart-beat", "100000");
			metadata.addAttribute("seconds-between-heart-beats", "60");
			metadata.addAttribute("verbosity-level", "0");
			metadata.addAttribute("output-directory", ".");
			setMetadata(metadata);
		}
	}
	
	@Override
	public Element createColorRefMetadata(Element colorRef, String configName) {
		return ColorReferenceHelper.createSimqpnWelchColoRefConfigurationMetadata(colorRef, configName);
	}
	
	@Override
	public void initColorRefDepositoryMetadata(Element metaAttribute) {
		setAttributeIfNotExists(metaAttribute, MIN_OBSRV, "500");
		setAttributeIfNotExists(metaAttribute, MAX_OBSRV, "10000");
	}
	
	@Override
	public void initColorRefQueueMetadata(Element metaAttribute) {
		setAttributeIfNotExists(metaAttribute, QUEUE_MIN_OBSRV, "500");
		setAttributeIfNotExists(metaAttribute, QUEUE_MAX_OBSRV, "10000");
	}
}
