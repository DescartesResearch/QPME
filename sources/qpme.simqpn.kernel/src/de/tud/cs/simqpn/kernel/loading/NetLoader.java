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
 * Original Author(s):  J?rgen Walter
 * Contributor(s):   
 * 
 * NOTE: The above list of contributors lists only the people that have
 * contributed to this source file - for a list of ALL contributors to 
 * the project, please see the README.txt file.
 * 
 *  History:
 *  Date        ID                Description
 *  ----------  ----------------  ------------------------------------------------------------------  
 *  2013/05/08  J?rgen Walter     Created. Code has been taken from other classes during refactoring.
 * 
 */
package de.tud.cs.simqpn.kernel.loading;

import static de.tud.cs.simqpn.kernel.util.LogUtil.formatDetailMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.xml.XMLConstants;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.XPath;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.ColorReference;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.Transition;
import de.tud.cs.simqpn.kernel.entities.queue.Queue;
import de.tud.cs.simqpn.kernel.loading.loader.PlaceLoader;
import de.tud.cs.simqpn.kernel.loading.loader.ProbeLoader;
import de.tud.cs.simqpn.kernel.loading.loader.QueueLoader;

public class NetLoader {

	private List<Element> placeList;
	private List<Element> transitionList;

	// hashmaps to allow fast lookup of array index for a given element
	public Map<Element, Integer> placeToIndexMap;
	private Map<Element, Integer> transitionToIndexMap;
	private Map<Element, Integer> colorToIndexMap;
	private Map<String, Element> idToElementMap;
	// hashmaps to allow fast lookup of number of incoming and outgoing
	// connections
	private Map<String, Integer> sourceIdToNumConnectionsMap = new HashMap<String, Integer>();
	private Map<String, Integer> targetIdToNumConnectionsMap = new HashMap<String, Integer>();

	public static final QName XSI_TYPE_ATTRIBUTE = new QName("type",
			new Namespace("xsi", XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI));

	private static Logger log = Logger.getLogger(NetLoader.class);

	private Net net;
	private Element netXML;
	private SimQPNConfiguration configuration;
	private String configurationName;

	public NetLoader(Element netXML, String configurationName,
			SimQPNConfiguration configuration) {
		this.netXML = netXML;
		this.configurationName = configurationName;
		this.configuration = configuration;
	}

	/**
	 * Loads a {@link Net} from XML.
	 * 
	 * @param netXML
	 * @return
	 * @throws SimQPNException
	 */
	public static Net load(Element netXML, String configurationName,
			SimQPNConfiguration configuration) throws SimQPNException {
		NetLoader netLoader = new NetLoader(netXML, configurationName,
				configuration);
		netLoader.initElementLists();
		return netLoader.loadNet();
	}

	/**
	 * Initialization for NetLoader
	 * 
	 * @param netXML
	 * @return
	 * @throws SimQPNException
	 */
	private void initElementLists() throws SimQPNException {
		placeToIndexMap = new HashMap<Element, Integer>();
		transitionToIndexMap = new HashMap<Element, Integer>();
		colorToIndexMap = new HashMap<Element, Integer>();
		sourceIdToNumConnectionsMap = new HashMap<String, Integer>();
		targetIdToNumConnectionsMap = new HashMap<String, Integer>();
		idToElementMap = new HashMap<String, Element>();

		XPath xpathSelector = XMLHelper.createXPath("//place");
		placeList = xpathSelector.selectNodes(netXML);
		xpathSelector = XMLHelper.createXPath("//transition");
		transitionList = xpathSelector.selectNodes(netXML);
	}

	/**
	 * Loads a {@link Net} from XML.
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	private Net loadNet() throws SimQPNException {
		net = new Net();
		net.setConfigurationName(configurationName);
		net.setNumProbes(ProbeLoader.getNumberOfProbes(netXML));

		extractConnections(netXML);
		checkColorDefinitions(netXML);
		net.setQueues(QueueLoader.createQueues(netXML));
		net.setPlaces(PlaceLoader.createPlaces(this)); // TODO placeList
		net.setTransitions(createTransitions());
		configureInputOutputRelationships(netXML, net);
		configureTransitionModeWeights(net);
		configureTransitionInputOutputFunctions(net);
		QueueLoader.configureQueueServiceTimeDistributions(net, placeList);
		ProbeLoader.configureProbes(netXML, placeList, net, configuration);
		configureInitialMarking(net);
		XMLWelch.configurePlaceStats(net.getPlaces(), netXML, configuration.getAnalMethod(), configurationName);

		return net;
	}

	/**
	 * Checks if there exist identical color definitions
	 * 
	 * @param netXML
	 * @throws SimQPNException
	 */
	private void checkColorDefinitions(Element netXML)
			throws SimQPNException {
		XPath colorSelector = XMLHelper.createXPath("//color");
		List<Element> colorList = colorSelector.selectNodes(netXML);
		// Set for checking the uniqueness of color names
		HashSet<String> colorNames = new HashSet<String>();

		for (int i = 0; i < colorList.size(); i++) {
			Element col = colorList.get(i);
			colorToIndexMap.put(col, i);

			String name = col.attributeValue("name");
			if (colorNames.contains(name)) {
				log.error(formatDetailMessage(
						"Another color definition with the same name does already exist!",
						"color-num", Integer.toString(i), "color.id",
						col.attributeValue("id"), "color.name", name));
				throw new SimQPNException();
			} else {
				colorNames.add(name);
			}
		}
	}

	private void extractConnections(Element netXML) {
		XPath xpathSelector;
		xpathSelector = XMLHelper.createXPath("//connection");
		for (Object o : xpathSelector.selectNodes(netXML)) {
			if (o instanceof Element) {
				Element e = (Element) o;
				String sourceId = e.attributeValue("source-id");
				String targetId = e.attributeValue("target-id");

				Integer numSourceIdConnections = getNumConnectionsWithSourceId(sourceId);

				if (numSourceIdConnections == null) {
					numSourceIdConnections = new Integer(1);
				} else {
					numSourceIdConnections++;
				}
				sourceIdToNumConnectionsMap.put(sourceId,
						numSourceIdConnections);

				Integer numTargetIdConnections = getNumConnectionsWithTargetId(targetId);

				if (numTargetIdConnections == null) {
					numTargetIdConnections = new Integer(1);
				} else {
					numTargetIdConnections++;
				}
				targetIdToNumConnectionsMap.put(targetId,
						numTargetIdConnections);
			}
		}

		xpathSelector = XMLHelper.createXPath("//*");
		for (Object o : xpathSelector.selectNodes(netXML)) {
			if (o instanceof Element) {
				Element e = (Element) o;

				String eId = e.attributeValue("id");
				if (eId != null) {
					idToElementMap.put(eId, e);
				}
			}
		}
	}

	private Transition[] createTransitions() throws SimQPNException {
		/*
		 * Validate input parameters provided by the user to make sure that they
		 * make sense!!! For e.g. transWeight >= 0
		 * 
		 * IMPORTANT: Timed transitions and Subnet places are currently not
		 * supported!!! If the net uses them, just print an error message and
		 * exit.
		 * 
		 * IMPORTANT: trans id must be equal to its index in the transition
		 * array!!!
		 */

		log.debug("/////////////////////////////////////////////");
		log.debug("// Create transitions");
		// Allocate an array able to contain the transitions.
		log.debug("trans = new Transition[" + transitionList.size() + "];");

		Transition[] transitions = new Transition[transitionList.size()];
		// Set for checking the uniqueness of transition names
		HashSet<String> transNames = new HashSet<String>();
		// Create the transition-objects of every transition.
		Iterator<Element> transitionIterator = transitionList.iterator();
		for (int i = 0; transitionIterator.hasNext(); i++) {
			Element transition = transitionIterator.next();

			String name = transition.attributeValue("name");
			if (transNames.contains(name)) {
				log.error(formatDetailMessage(
						"Another transition with the same name does already exist!",
						"transition-num", Integer.toString(i), "transition.id",
						transition.attributeValue("id"), "transition.name",
						transition.attributeValue("name")));
				throw new SimQPNException();
			} else {
				transNames.add(name);
			}

			if (!"immediate-transition".equals(transition
					.attributeValue(XSI_TYPE_ATTRIBUTE))) {
				log.error(formatDetailMessage(
						"Invalid or missing transition type setting! Only \"immediate-transition\" is currently supported.",
						"transition-num", Integer.toString(i), "transition.id",
						transition.attributeValue("id"), "transition.name",
						transition.attributeValue("name"), "transition.type",
						transition.attributeValue(XSI_TYPE_ATTRIBUTE)));
				throw new SimQPNException();
			}
			XPath xpathSelector = XMLHelper.createXPath("modes/mode");
			int numModes = xpathSelector.selectNodes(transition).size();
			if (numModes == 0) {
				log.error(formatDetailMessage(
						"Incidence function not defined!", "transition-num",
						Integer.toString(i), "transition.id",
						transition.attributeValue("id"), "transition.name",
						transition.attributeValue("name"), "transition.type",
						transition.attributeValue(XSI_TYPE_ATTRIBUTE)));
				throw new SimQPNException();
			}

			int numOutgoingConnections = getNumConnectionsWithSourceId(transition
					.attributeValue("id"));
			int numIncomingConnections = getNumConnectionsWithTargetId(transition
					.attributeValue("id"));

			if (transition.attributeValue("weight") == null) {
				log.error(formatDetailMessage("Transition weight not set!",
						"transition-num", Integer.toString(i), "transition.id",
						transition.attributeValue("id"), "transition.name",
						transition.attributeValue("name"), "transition.type",
						transition.attributeValue(XSI_TYPE_ATTRIBUTE)));
				throw new SimQPNException();
			}
			double transitionWeight = Double.parseDouble(transition
					.attributeValue("weight"));

			transitions[i] = new Transition(i, // id
					transition.attributeValue("name"), // name
					numModes, // # modes
					numIncomingConnections, // # incoming connections
					numOutgoingConnections, // # outgoing connections
					net.getNumProbes(), transitionWeight); // transition weight
			transitionToIndexMap.put(transition, i);
			if (log.isDebugEnabled()) {
				log.debug("trans[" + i + "] = new Transition(" + i + ", '"
						+ transition.attributeValue("name") + "', " + numModes
						+ ", " + numIncomingConnections + ", "
						+ numOutgoingConnections + ", " + transitionWeight
						+ ");       transition-element = " + transition);
			}
		}

		return transitions;
	}


	private void configureInputOutputRelationships(Element netXML, Net net)
			throws SimQPNException {
		log.debug("/////////////////////////////////////////////");
		log.debug("// Configure input/output relationships");
		// Initialize the place-transition and transition-place connections.
		XPath xpathSelector = XMLHelper
				.createXPath("/net/connections/connection");
		Iterator<Element> connectionIterator = xpathSelector
				.selectNodes(netXML).iterator();

		while (connectionIterator.hasNext()) {
			// Get the next connection
			Element connection = connectionIterator.next();

			// Select the source and target of this connection
			Element sourceElement = idToElementMap.get(connection
					.attributeValue("source-id"));
			Element targetElement = idToElementMap.get(connection
					.attributeValue("target-id"));

			if (sourceElement == null) {
				log.error(formatDetailMessage(
						"Source of connection not found!", "connection.id",
						connection.attributeValue("id"),
						"connection.source-id",
						connection.attributeValue("source-id"),
						"connection.target-id",
						connection.attributeValue("target-id")));
				throw new SimQPNException();
			}
			if (targetElement == null) {
				log.error(formatDetailMessage(
						"Target of connection not found!", "connection.id",
						connection.attributeValue("id"),
						"connection.source-id",
						connection.attributeValue("source-id"),
						"connection.target-id",
						connection.attributeValue("target-id")));
				throw new SimQPNException();
			}

			// if the source is a place, then select the Place object which it
			// is assigned to.
			if ("place".equals(sourceElement.getName())) {

				// source place index
				int p = placeToIndexMap.get(sourceElement);
				// target transition index
				int t = transitionToIndexMap.get(targetElement);

				// Connect place and transition
				for (int ot = 0; ot < net.getPlace(p).outTrans.length; ot++) {
					if (net.getPlace(p).outTrans[ot] == null) {
						net.getPlace(p).outTrans[ot] = net.getTrans(t);
						log.debug("places[" + p + "].outTrans[" + ot
								+ "] = trans[" + t + "];");
						break;
					}
				}
				for (int ip = 0; ip < net.getTrans(t).inPlaces.length; ip++) {
					if (net.getTrans(t).inPlaces[ip] == null) {
						net.getTrans(t).inPlaces[ip] = net.getPlace(p);
						log.debug("trans[" + t + "].inPlaces[" + ip
								+ "] = places[" + p + "];");
						break;
					}
				}

			}
			// if the source is a transition, then select the Transition object
			// which it is assigned to.
			else if ("transition".equals(sourceElement.getName())) {

				// source transition index
				int t = transitionToIndexMap.get(sourceElement);
				// target place index
				int p = placeToIndexMap.get(targetElement);

				// Connect transition and place.
				for (int op = 0; op < net.getTrans(t).outPlaces.length; op++) {
					if (net.getTrans(t).outPlaces[op] == null) {
						net.getTrans(t).outPlaces[op] = net.getPlace(p);
						log.debug("trans[" + t + "].outPlaces[" + op
								+ "] = places[" + p + "];");
						break;
					}
				}
				for (int it = 0; it < net.getPlace(p).inTrans.length; it++) {
					if (net.getPlace(p).inTrans[it] == null) {
						net.getPlace(p).inTrans[it] = net.getTrans(t);
						log.debug("places[" + p + "].inTrans[" + it
								+ "] = trans[" + t + "];");
						break;
					}
				}
			}
		}
	}

	private void configureTransitionModeWeights(Net net) throws SimQPNException {
		log.debug("/////////////////////////////////////////////");
		log.debug("// Configure transition mode weights");
		Iterator<Element> transitionIterator = this.transitionList.iterator();
		for (int i = 0; transitionIterator.hasNext(); i++) {
			Element transition = transitionIterator.next();
			XPath xpathSelector = XMLHelper.createXPath("modes/mode");
			Iterator<Element> modeIterator = xpathSelector.selectNodes(
					transition).iterator();
		
			net.getTrans(i).initDynamicModeWeights();
			for (int j = 0; modeIterator.hasNext(); j++) {
				Element mode = modeIterator.next();
				if (mode.attributeValue("firing-weight") == null) {
					log.error(formatDetailMessage(
							"Transition mode' \"firing-weight\" not set",
							"transition-num", Integer.toString(i),
							"transition.id", transition.attributeValue("id"),
							"transition.name",
							transition.attributeValue("name"), "mode-num",
							Integer.toString(j), "mode.id",
							mode.attributeValue("id"), "mode.name",
							mode.attributeValue("name")));
					throw new SimQPNException();
				}

				net.getTrans(i).modeWeights[j] = Double.parseDouble(mode
						.attributeValue("firing-weight"));
				List<ColorReference> dynamicModeWeights = getColorRefsForDynamicModeWeights(mode, j);
				net.getTrans(i).setDynamicModeWeights(j, dynamicModeWeights);
	
				log.debug("trans[" + i + "].modeWeights[" + j + "] = "
						+ net.getTrans(i).modeWeights[j]);
			}
		}
		//TODO net.getTrans(i).setDynamicModeWeightFlags(isDynamicModeWeight)
	}

	
	/**
	 * To add dynamic firing weights to your model use the following XML schema
	 * <pre>
	 * {@code
     * <mode ...>
     * 	<dynamic-firing-weights>
     * 		<dynamic-firing-weight place-id="_somePlaceID1" color-ref-id="_someColorID2"/>
     * 		<dynamic-firing-weight place-id="_somePlaceID2" color-ref-id="_someColorID3"/>
     * 	</dynamic-firing-weights>
     * </mode>	
	 * }
	 * </pre>
	 */
	private List<ColorReference> getColorRefsForDynamicModeWeights(Element mode, int modeID) {

		Element dynamicWeights = mode.element("dynamic-firing-weights");
		if (dynamicWeights != null) {
			log.info("firing mode "+modeID + " is dynamic and depends on the following color references");
			List<ColorReference> colorReferences = new ArrayList<ColorReference>();
			Iterator<Element> dynamicWeightIterator = dynamicWeights.elementIterator("dynamic-firing-weight");
			for (int i = 0; dynamicWeightIterator.hasNext(); i++) {
				Element dynamicWeight = dynamicWeightIterator.next();
				String colorRefID = dynamicWeight.attributeValue("color-ref-id");
				Element colorRef = idToElementMap.get(colorRefID);
				Element color = idToElementMap.get(colorRef.attributeValue("color-id"));
				String colorName = color.attributeValue("name");
				
				String placeIDXML = dynamicWeight.attributeValue("place-id");
				Element placeXML = idToElementMap.get(placeIDXML);
				int placeID = placeToIndexMap.get(placeXML);
				Place place = net.getPlace(placeID);
				int colorID = -1;
				for(int j=0; j<place.colors.length; j++){
					if(place.colors[j].equals(colorName)){
						colorID=j;
						break;
					}
				}
				ColorReference colorReference = new ColorReference(place, colorID);
				colorReferences.add(colorReference);
				log.info("place["+ placeID+"]   color["+colorID+"]");
			}
			return colorReferences;
		}else{
			return null;
		}
	}
	
	private Element helper(String id){
		Element place = null;
		Iterator<Element> allPlacesIterator = placeList.iterator();
		for (int j = 0; allPlacesIterator.hasNext(); j++) {
			place = allPlacesIterator.next();
			System.out.println(place.attributeValue("id"));
			if(place.attributeValue("id").equals(id)){
				System.out.println("found place "+place);
				System.out.println(placeToIndexMap.get(place));
				return place;
			}
		}
		return null;
	}

	/** CONFIGURE TRANSITION INPUT/OUTPUT FUNCTIONS [mode, inPlace, color] **/
	private void configureTransitionInputOutputFunctions(Net net)
			throws SimQPNException {
		/*
		 * For each transition for every input/output place must specify how
		 * many tokens of each color of the respective place are
		 * destroyed/created.
		 */

		log.debug("/////////////////////////////////////////////");
		log.debug("// Configure transition input/output functions [mode, inPlace, color]");
		// trans[{transition-number}].inFunc[{place-number}][{color-ref-number}][{mode-number}]
		// = {number-of-tokens};

		for (int t = 0; t < net.getNumTrans(); t++) {
			// Select the element for the current transition.
			Element transition = this.transitionList.get(t);

			XPath xpathSelector = XMLHelper.createXPath("modes/mode");
			List<Element> modes = xpathSelector.selectNodes(transition);

			// Iterate through all modes
			for (int m = 0; m < net.getTrans(t).numModes; m++) {
				int numInputTokens = 0;
				// Iterate through all input-places.
				for (int p = 0; p < net.getTrans(t).inPlaces.length; p++) {
					Element inputPlace = net.getTrans(t).inPlaces[p].element;
					// Go through all color-refs for the current place.
					xpathSelector = XMLHelper
							.createXPath("color-refs/color-ref");
					List<Element> colorRefs = xpathSelector
							.selectNodes(inputPlace);

					// Allocate an array saving the number of tokens for each
					// color-ref to the current mode for the
					// current input place. If there is no connection, then this
					// value is 0.
					net.getTrans(t).inFunc[m][p] = new int[colorRefs.size()];
					log.debug("trans[" + t + "].inFunc[" + m + "][" + p
							+ "] = new int[" + colorRefs.size() + "];");

					Iterator<Element> colorRefIterator = colorRefs.iterator();
					for (int con = 0; colorRefIterator.hasNext(); con++) {
						Element colorRef = colorRefIterator.next();

						String colorRefId = colorRef.attributeValue("id");
						String modeId = modes.get(m).attributeValue("id");

						xpathSelector = XMLHelper
								.createXPath("connections/connection[(@source-id='"
										+ colorRefId
										+ "') and (@target-id='"
										+ modeId + "')]");
						Element connection = (Element) xpathSelector
								.selectSingleNode(transition);
						if (connection != null) {
							if (connection.attributeValue("count") == null) {
								log.error(formatDetailMessage(
										"Incidence function connection' \"count\" (arc weight) attribute not set!",
										"transition-num", Integer.toString(t),
										"transition.id", transition
												.attributeValue("id"),
										"transition.name", transition
												.attributeValue("name"),
										"mode-num", Integer.toString(m),
										"mode.id",
										modes.get(m).attributeValue("id"),
										"mode.name", modes.get(m)
												.attributeValue("name"),
										"inPlace.id", inputPlace
												.attributeValue("id"),
										"inPlace.name", inputPlace
												.attributeValue("name"),
										"colorRef.id", colorRef
												.attributeValue("id"),
										"colorRef.color-id", colorRef
												.attributeValue("color-id")));
								throw new SimQPNException();
							}
							int numTokens = Integer.parseInt(connection
									.attributeValue("count"));
							// SDK-DEBUG: Attribute "count" is usually missing
							// in the XML file.
							// CHRIS: fixed that.
							net.getTrans(t).inFunc[m][p][con] = numTokens;
							log.debug("trans[" + t + "].inFunc[" + m + "][" + p
									+ "][" + con + "] = " + numTokens);
							numInputTokens += numTokens;
						} else {
							net.getTrans(t).inFunc[m][p][con] = 0;
							log.debug("trans[" + t + "].inFunc[" + m + "][" + p
									+ "][" + con + "] = 0");
						}
					}
				}
				if (numInputTokens == 0) {
					log.error(formatDetailMessage(
							"An immediate transition with a mode that requires no input tokens found! This would cause a simulation deadlock.",
							"transition-num" + Integer.toString(t),
							"transition.id" + transition.attributeValue("id"),
							"transition.name"
									+ transition.attributeValue("name"),
							"mode-num" + Integer.toString(m), "mode.id"
									+ modes.get(m).attributeValue("id"),
							"mode.name" + modes.get(m).attributeValue("name")));
					throw new SimQPNException();
				}

				// Iterate through all output-places.
				for (int p = 0; p < net.getTrans(t).outPlaces.length; p++) {
					Element outputPlace = net.getTrans(t).outPlaces[p].element;
					// Go through all color-refs for the current place.
					xpathSelector = XMLHelper
							.createXPath("color-refs/color-ref");
					List<Element> colorRefs = xpathSelector
							.selectNodes(outputPlace);

					// Allocate an array saving the number of tokens for each
					// color-ref to mode connection.
					// If there is no connection, then this value is 0.
					net.getTrans(t).outFunc[m][p] = new int[colorRefs.size()];
					log.debug("trans[" + t + "].outFunc[" + m + "][" + p
							+ "] = new int[" + colorRefs.size() + "];");

					Iterator<Element> colorRefIterator = colorRefs.iterator();
					for (int con = 0; colorRefIterator.hasNext(); con++) {
						Element colorRef = colorRefIterator.next();

						String colorRefId = colorRef.attributeValue("id");
						String modeId = modes.get(m).attributeValue("id");

						xpathSelector = XMLHelper
								.createXPath("connections/connection[(@source-id='"
										+ modeId
										+ "') and (@target-id='"
										+ colorRefId + "')]");
						Element connection = (Element) xpathSelector
								.selectSingleNode(transition);
						if (connection != null) {
							if (connection.attributeValue("count") == null) {
								log.error(formatDetailMessage(
										"Incidence function connection \"count\" (arc weight) attribute not set!",
										"transition-num", Integer.toString(t),
										"transition.id", transition
												.attributeValue("id"),
										"transition.name", transition
												.attributeValue("name"),
										"mode-num", Integer.toString(m),
										"mode.id",
										modes.get(m).attributeValue("id"),
										"mode.name", modes.get(m)
												.attributeValue("name"),
										"outPlace.id", outputPlace
												.attributeValue("id"),
										"outPlace.name", outputPlace
												.attributeValue("name"),
										"colorRef.id", colorRef
												.attributeValue("id"),
										"colorRef.color-id", colorRef
												.attributeValue("color-id")));
								throw new SimQPNException();
							}
							int numTokens = Integer.parseInt(connection
									.attributeValue("count"));
							net.getTrans(t).outFunc[m][p][con] = numTokens;
							log.debug("trans[" + t + "].outFunc[" + m + "]["
									+ p + "][" + con + "] = " + numTokens + ";");
						} else {
							net.getTrans(t).outFunc[m][p][con] = 0;
							log.debug("trans[" + t + "].outFunc[" + m + "]["
									+ p + "][" + con + "] = 0;");
						}
					}
				}
			}
		}
	}


	private void configureInitialMarking(Net net) throws SimQPNException {
		// Note: All initial tokens should be in ordianary places or
		// depositories.
		// No initial tokens are allowed in the queues.
		log.debug("/////////////////////////////////////////////");
		log.debug("// Configure Initial Marking");
		Iterator<Element> placeIterator = placeList.iterator();
		for (int i = 0; placeIterator.hasNext(); i++) {
			Element place = placeIterator.next();
			XPath xpathSelector = XMLHelper.createXPath("color-refs/color-ref");
			Iterator<Element> colorRefIterator = xpathSelector.selectNodes(
					place).iterator();
			for (int j = 0; colorRefIterator.hasNext(); j++) {
				Element colorRef = colorRefIterator.next();
				if (colorRef.attributeValue("initial-population") == null) {
					log.error(formatDetailMessage(
							"Queueing place' \"initial-population\" parameter not set!",
							"place-num", Integer.toString(i), "place.id",
							place.attributeValue("id"), "place.name",
							place.attributeValue("name"), "colorRef-num",
							Integer.toString(j), "colorRef.id",
							colorRef.attributeValue("id"), "colorRef.color-id",
							colorRef.attributeValue("color-id")));
					throw new SimQPNException();
				}
				net.getPlace(i).tokenPop[j] = Integer.parseInt(colorRef
						.attributeValue("initial-population"));
				log.debug("places[" + i + "].tokenPop[" + j + "] = "
						+ net.getPlace(i).tokenPop[j] + ";");
			}
		}
	}

	public Queue findQueueByXmlId(String xmlId, Queue[] queues) {
		for (int i = 0; i < queues.length; i++) {
			Queue queue = queues[i];
			if (xmlId.equals(queue.xmlId)) {
				return queue;
			}
		}
		throw new NoSuchElementException();
	}

	public int getNumConnectionsWithSourceId(String id) {
		Integer num = sourceIdToNumConnectionsMap.get(id);
		if (num != null) {
			return num.intValue();
		}
		return 0;
	}

	public int getNumConnectionsWithTargetId(String id) {
		Integer num = targetIdToNumConnectionsMap.get(id);
		if (num != null) {
			return num.intValue();
		}
		return 0;
	}

	public Net getNet() {
		return net;
	}

	public List<Element> getPlaceList() {
		return placeList;
	}

	public SimQPNConfiguration getConfiguration() {
		return configuration;
	}

	public Element getNetXML() {
		return netXML;
	}

}
