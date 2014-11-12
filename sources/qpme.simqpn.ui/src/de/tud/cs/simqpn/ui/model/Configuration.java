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

import java.util.List;

import org.dom4j.Element;
import org.eclipse.jface.viewers.ICellEditorValidator;

import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.model.NetHelper;
import de.tud.cs.qpe.model.PlaceHelper;
import de.tud.cs.qpe.model.ProbeHelper;


public abstract class Configuration {
	
	public static final int MAX_NUM_ATTRIBUTES = 10;
	
	public static final int BATCH_MEANS_SCENARIO = 1;
	public static final int REPLICATION_DELETION_SCENARIO = 2;
	public static final int WELCH_SCENARIO = 3;
	
	public static final String ABSOLUTE_PRECISION_STOPPING_RULE = "ABSPRC";
	public static final String RELATIVE_PRECISION_STOPPING_RULE = "RELPRC";
	public static final String FIXED_LENGTH_STOPPING_RULE = "FIXEDLEN";
	
	protected static final String STATS_LEVEL = "statsLevel";
	
	private String[] attributeNames;
	private String[] queueAttributeNames;
	
	private Element metadata = null;
	
	public Configuration() {
		attributeNames = initAttributeNames();
		queueAttributeNames = initQueueAttributeNames();
	}
	
	public Configuration(Element metadata) {
		this();
		this.metadata = metadata;
	}
	
	public String[] getColumnTitles() {
		String[] titles = new String[MAX_NUM_ATTRIBUTES];
		for (int i = 0; i < titles.length; i++) {
			if (attributeNames[i] == null) {
				titles[i] = "";
			} else {
				titles[i] = attributeNames[i];
			}			
		}
		titles[0] = "Name";
		return titles;
	}
	
	public Element getMetadata() {
		return metadata;
	}
	
	public void setMetadata(Element metadata) {
		this.metadata = metadata;
	}
	
	public String getName() {
		return metadata.attributeValue("configuration-name");
	}
	

	public void setName(String value) {
		// Get all meta-attributes of the current configuration.
		String oldName = metadata.attributeValue("configuration-name");
		List<Element> metadatas = NetHelper.listAllMetadata(metadata, oldName);
		// Iterate through them and change their names.
		for (Element m : metadatas) {
			DocumentManager.setAttribute(m,
					"configuration-name", (String) value);
		}
	}
	
	public int getScenario() {
		return Integer.parseInt(metadata.attributeValue("scenario", "1"));
	}
	
	public int getNumberOfRuns() {
		return Integer.parseInt(metadata.attributeValue("number-of-runs", "1"));
	}
	
	public void setNumberOfRuns(int number) {
		DocumentManager.setAttribute(metadata, "number-of-runs", Integer.toString(number));
	}
	
	public double getRampUpLength() {
		return Double.parseDouble(metadata.attributeValue("ramp-up-length", "0.0"));
	}
	
	public void setRampUpLength(double length) {
		DocumentManager.setAttribute(metadata, "ramp-up-length", Double.toString(length));
	}
	
	public double getTotalRunLength() {
		return Double.parseDouble(metadata.attributeValue("total-run-length", "0.0"));
	}
	
	public void setTotalRunLength(double runLength) {
		DocumentManager.setAttribute(metadata, "total-run-length", Double.toString(runLength));
	}	

	public String getStoppingRule() {
		return metadata.attributeValue("stopping-rule");
	}
	
	public void setStoppingRule(String rule) {
		DocumentManager.setAttribute(metadata, "stopping-rule", rule);
	}
	
	public double getTimeBetweenStopChecks() {
		return Double.parseDouble(metadata.attributeValue("time-between-stop-checks", "100000"));
	}
	
	public void setTimeBetweenStopChecks(double time) {
		DocumentManager.setAttribute(metadata, "time-between-stop-checks", Double.toString(time));
	}
	
	public double getSecondsBetweenStopChecks() {
		return Double.parseDouble(metadata.attributeValue("seconds-between-stop-checks", "60"));
	}
	
	public void setSecondsBetweenStopChecks(double seconds) {
		DocumentManager.setAttribute(metadata, "seconds-between-stop-checks", Double.toString(seconds));
	}

	public int getVerbosityLevel() {
		return Integer.parseInt(metadata.attributeValue("verbosity-level", "0"));
	}

	public void setVerbosityLevel(int level) {
		DocumentManager.setAttribute(metadata, "verbosity-level", Integer.toString(level));
	}	

	public String getOutputDirectory() {
		return metadata.attributeValue("output-directory", "");
	}
	
	public void setOutputDirectory(String dir) {
		DocumentManager.setAttribute(metadata, "output-directory", dir);
	}
	
	public String getDescription() {
		return metadata.attributeValue("configuration-description", "");
	}
	
	public void setDescription(String description) {
		metadata.addAttribute("configuration-description", description);
	}
	
	public abstract ICellEditorValidator getValidator(int colIndex);
	
	public abstract void createSimulationConfiguration(Element net, String configName);
	
	public abstract Element createColorRefMetadata(Element colorRef, String configName);
	
	public void init(Element net) {
		List<Element> places = NetHelper.listAllPlaces(net);
		String configName = getName();
		
		for (Element place : places) {
			Element placeMeta = NetHelper.getMetadata(place, configName);
			if (placeMeta == null) {
				placeMeta = PlaceHelper.createSimqpnPlaceConfigurationMetadata(place, configName);
			}
			if (placeMeta.attribute(STATS_LEVEL) == null) {
				placeMeta.addAttribute(STATS_LEVEL, "1");
			}
			
			int statsLevel = Integer.parseInt(placeMeta.attributeValue(STATS_LEVEL));
			if (statsLevel >= 3) {
				List<Element> colorRefs = PlaceHelper.listColorReferences(place);
				for (Element colorRef : colorRefs) {
					Element colorRefMeta = NetHelper.getMetadata(colorRef, configName);
					if (colorRefMeta == null) {
						colorRefMeta = createColorRefMetadata(colorRef, configName);						
					}
					
					initColorRefDepositoryMetadata(colorRefMeta);
					if ("queueing-place".equals(place.attributeValue("type"))) {
						initColorRefQueueMetadata(colorRefMeta);
					}
				}
			}
			
		}
		
		List<Element> probes = NetHelper.listProbes(net);
		for (Element probe : probes) {
			Element probeMeta = NetHelper.getMetadata(probe, configName);
			if (probeMeta == null) {
				probeMeta = ProbeHelper.createSimqpnProbeConfigurationMetadata(probe, configName);
			}
			if (probeMeta.attribute(STATS_LEVEL) == null) {
				probeMeta.addAttribute(STATS_LEVEL, "1");
			}
				
			int statsLevel = Integer.parseInt(probeMeta.attributeValue(STATS_LEVEL));
			if (statsLevel >= 3) {
				List<Element> colorRefs = PlaceHelper.listColorReferences(probe);
				
				for (Element colorRef : colorRefs) {
					Element colorRefMeta = NetHelper.getMetadata(colorRef, configName);
					if (colorRefMeta == null) {
						colorRefMeta = createColorRefMetadata(colorRef, configName);						
					}
					
					initColorRefDepositoryMetadata(colorRefMeta);
				}
			}
		}
	}
	
	public abstract void initColorRefDepositoryMetadata(Element metaAttribute);
	
	public abstract void initColorRefQueueMetadata(Element metaAttribute);
	
	protected abstract String[] initAttributeNames();
	
	protected abstract String[] initQueueAttributeNames();
	
	protected void setAttributeIfNotExists(Element element, String attributeName, String defaultValue) {
		if (element.attribute(attributeName) == null) {
			element.addAttribute(attributeName, defaultValue);
		}
	}

	public String getAttributeName(int colIdx) {
		return attributeNames[colIdx];
	}
	
	public String getQueueAttributeName(int colIdx) {
		return queueAttributeNames[colIdx];
	}
}
