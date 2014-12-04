package de.tud.cs.simqpn.kernel.loading.loader;

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
 * Original Author(s):  Jürgen Walter
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2014/12/04  Jürgen Walter     Created. Code has been taken from other classes during refactoring.
 * 
 */
import static de.tud.cs.simqpn.kernel.util.LogUtil.formatDetailMessage;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.XPath;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.Probe;
import de.tud.cs.simqpn.kernel.loading.NetLoader;
import de.tud.cs.simqpn.kernel.loading.XMLHelper;

public class ProbeLoader {
	private static Logger log = Logger.getLogger(NetLoader.class);
	
	public static int getNumberOfProbes(Element netXML){
		XPath xpathSelector = XMLHelper.createXPath("//probe");
		List<Element> probeList = (List<Element>) xpathSelector.selectNodes(netXML);
		return probeList.size();
	}

	public static void configureProbes(Element netXML,List<Element> placeList, Net net,
			SimQPNConfiguration configuration) throws SimQPNException {
		XPath xpathSelector;
		log.debug("/////////////////////////////////////////////");
		log.debug("// Create probes");
		List<Element> probeList;
		xpathSelector = XMLHelper.createXPath("//probe");
		probeList = (List<Element>) xpathSelector.selectNodes(netXML);

		// Allocate an array able to contain the places.
		int numProbes = net.getNumProbes();
		log.debug("probes = new Probe[" + numProbes + "];");
		Probe[] probes = new Probe[numProbes];

		for (int i = 0; i < numProbes; i++) {
			Element probe = probeList.get(i);

			// Extract the names of the colors that are tracked by this probe
			Element colorRefsElem = probe.element("color-refs");
			if (colorRefsElem == null) {
				log.error(formatDetailMessage("Missing color references!",
						"probe-num", Integer.toString(i), "probe.id",
						probe.attributeValue("id"), "probe.name",
						probe.attributeValue("name")));
				throw new SimQPNException();
			}
			@SuppressWarnings("unchecked")
			List<Element> colorRefs = colorRefsElem.elements("color-ref");
			if (colorRefs.size() == 0) {
				log.error(formatDetailMessage("Missing color references!",
						"probe-num", Integer.toString(i), "probe.id",
						probe.attributeValue("id"), "probe.name",
						probe.attributeValue("name")));
				throw new SimQPNException();
			}
			String[] colors = new String[colorRefs.size()];
			Iterator<Element> colorRefIterator = colorRefs.iterator();
			for (int c = 0; colorRefIterator.hasNext(); c++) {
				Element colorRef = colorRefIterator.next();
				xpathSelector = XMLHelper.createXPath("colors/color[(@id='"
						+ colorRef.attributeValue("color-id") + "')]");
				Element color = (Element) xpathSelector
						.selectSingleNode(netXML);
				colors[c] = color.attributeValue("name");
			}

			// Extract start place of probe
			xpathSelector = XMLHelper.createXPath("//place[@id = '"
					+ probe.attributeValue("start-place-id") + "']");
			Element startElement = (Element) xpathSelector
					.selectSingleNode(netXML);
			if (startElement == null) {
				log.error(formatDetailMessage("Start place reference invalid!",
						"probe-num", Integer.toString(i), "probe.id",
						probe.attributeValue("id"), "probe.name",
						probe.attributeValue("name")));
				throw new SimQPNException();
			}
			Place startPlace = null;
			for (int p = 0; p < net.getNumPlaces(); p++) {
				if (placeList.get(p).equals(startElement)) {
					startPlace = net.getPlace(p);
				}
			}
			int startTrigger = Probe.ON_ENTRY;
			if ("entry".equals(probe.attributeValue("start-trigger"))) {
				startTrigger = Probe.ON_ENTRY;
			} else if ("exit".equals(probe.attributeValue("start-trigger"))) {
				startTrigger = Probe.ON_EXIT;
			} else {
				log.error(formatDetailMessage(
						"Invalid or missing 'start-trigger' setting!",
						"probe-num", Integer.toString(i), "probe.id",
						probe.attributeValue("id"), "probe.name",
						probe.attributeValue("name"), "probe.start-trigger",
						probe.attributeValue("start-trigger")));
				throw new SimQPNException();
			}

			// Extract end place of probe
			xpathSelector = XMLHelper.createXPath("//place[@id = '"
					+ probe.attributeValue("end-place-id") + "']");
			Element endElement = (Element) xpathSelector
					.selectSingleNode(netXML);
			if (endElement == null) {
				log.error(formatDetailMessage("End place reference invalid!",
						"probe-num", Integer.toString(i), "probe.id",
						probe.attributeValue("id"), "probe.name",
						probe.attributeValue("name")));
				throw new SimQPNException();
			}
			Place endPlace = null;
			for (int p = 0; p < net.getNumPlaces(); p++) {
				if (placeList.get(p).equals(endElement)) {
					endPlace = net.getPlace(p);
				}
			}
			int endTrigger = Probe.ON_ENTRY;
			if ("entry".equals(probe.attributeValue("end-trigger"))) {
				endTrigger = Probe.ON_ENTRY;
			} else if ("exit".equals(probe.attributeValue("end-trigger"))) {
				endTrigger = Probe.ON_EXIT;
			} else {
				log.error(formatDetailMessage(
						"Invalid or missing \"end-trigger\" setting!",
						"probe-num", Integer.toString(i), "probe.id",
						probe.attributeValue("id"), "probe.name",
						probe.attributeValue("name"), "probe.end-trigger",
						probe.attributeValue("end-trigger")));
				throw new SimQPNException();
			}

			Element metaAttribute = XMLHelper.getSettings(probe,
					net.getConfigurationName());
			int statsLevel = 0;
			if (metaAttribute != null) {
				if (metaAttribute.attributeValue("statsLevel") == null) {
					log.error(formatDetailMessage(
							"statsLevel parameter not set!", "probe-num",
							Integer.toString(i), "probe.id",
							probe.attributeValue("id"), "probe.name",
							probe.attributeValue("name")));
					throw new SimQPNException();
				}
				statsLevel = Integer.parseInt(metaAttribute
						.attributeValue("statsLevel"));
			} else {
				log.error(formatDetailMessage(
						"meta-attribute element not set!", "probe-num",
						Integer.toString(i), "probe.id",
						probe.attributeValue("id"), "probe.name",
						probe.attributeValue("name")));
				throw new SimQPNException();
			}

			probes[i] = new Probe(
					i, // index
					probe.attributeValue("id"), // xml id
					probe.attributeValue("name"), // name
					colors, startPlace, startTrigger, endPlace, endTrigger,
					statsLevel, probe, configuration);
			if (log.isDebugEnabled()) {
				log.debug("probes[" + i + "] = new Probe(" + i + ", '"
						+ probe.attributeValue("name") + "', " + colors + ", "
						+ startPlace.name + ", " + startTrigger + ", "
						+ endPlace.name + ", " + endTrigger + ", " + statsLevel
						+ ", " + probe + ")");
			}
		}
		net.setProbes(probes);
		net.initializeProbes();
	}

}
