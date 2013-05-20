package de.tud.cs.simqpn.kernel.loader;

import static de.tud.cs.simqpn.kernel.util.LogUtil.formatDetailMessage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

import cern.jet.random.AbstractContinousDistribution;
import cern.jet.random.Beta;
import cern.jet.random.BreitWigner;
import cern.jet.random.BreitWignerMeanSquare;
import cern.jet.random.ChiSquare;
import cern.jet.random.Empirical;
import cern.jet.random.Exponential;
import cern.jet.random.ExponentialPower;
import cern.jet.random.Gamma;
import cern.jet.random.Hyperbolic;
import cern.jet.random.Logarithmic;
import cern.jet.random.Normal;
import cern.jet.random.StudentT;
import cern.jet.random.Uniform;
import cern.jet.random.VonMises;
import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.Probe;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.Queue;
import de.tud.cs.simqpn.kernel.entities.Transition;
import de.tud.cs.simqpn.kernel.random.Deterministic;
import de.tud.cs.simqpn.kernel.random.RandomNumberGenerator;

public class NetLoader {

	private List<Element> placeList;
	private List<Element> transitionList;
	private List<Element> queueList;
	private List<Element> probeList;

	// hashmaps to allow fast lookup of array index for a given element
	private Map<Element, Integer> placeToIndexMap;
	private Map<Element, Integer> transitionToIndexMap;
	private Map<Element, Integer> queueToIndexMap;
	private Map<String, Element> idToElementMap;

	// hashmaps to allow fast lookup of number of incoming and outgoing
	// connections
	private Map<String, Integer> sourceIdToNumConnectionsMap = new HashMap<String, Integer>();
	private Map<String, Integer> targetIdToNumConnectionsMap = new HashMap<String, Integer>();

	private static final QName XSI_TYPE_ATTRIBUTE = new QName("type",
			new Namespace("xsi", XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI));

	private static Logger log = Logger.getLogger(NetLoader.class);

	/**
	 * TODO Calls parse, flatten, validate methods
	 * 
	 * @param netXML
	 * @return
	 * @throws SimQPNException
	 */
	public Net load(Element netXML, String configurationName, SimQPNConfiguration configuration)
			throws SimQPNException {
		placeToIndexMap = new HashMap<Element, Integer>();
		transitionToIndexMap = new HashMap<Element, Integer>();
		queueToIndexMap = new HashMap<Element, Integer>();

		sourceIdToNumConnectionsMap = new HashMap<String, Integer>();
		targetIdToNumConnectionsMap = new HashMap<String, Integer>();

		Net net = parse(netXML, configurationName, configuration);

		return net;
	}

	/**
	 * Parses {@link Net}
	 * 
	 * @param netXML
	 * @return
	 * @throws SimQPNException
	 */
	private Net parse(Element netXML, String configurationName, SimQPNConfiguration configuration)
			throws SimQPNException {
		Net net = new Net();
		net.setConfiguration(configurationName);
		idToElementMap = new HashMap<String, Element>();

		XPath xpathSelector = XMLHelper.createXPath("//place");
		this.placeList = xpathSelector.selectNodes(netXML);
		xpathSelector = XMLHelper.createXPath("//transition");
		this.transitionList = xpathSelector.selectNodes(netXML);
		xpathSelector = XMLHelper.createXPath("//queue");
		this.queueList = xpathSelector.selectNodes(netXML);
		xpathSelector = XMLHelper.createXPath("//probe");
		this.probeList = xpathSelector.selectNodes(netXML);

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
		net = getReady(netXML, net, configuration);
		return net;
	}

	/**
	 * Method getReady - prepares for the simulation run
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	private Net getReady(Element netXML, Net net, SimQPNConfiguration configuration)
			throws SimQPNException {
		/*
		 * This is the method where the QPN to be simulated is defined. The QPN
		 * specification is currently hard-coded.
		 * 
		 * IMPORTANT: Remember to keep the order of statements unchanged!!!
		 */

		// *****************************************************************************************************************
		// BEGIN-MODEL-DEFINITION
		// *****************************************************************************************************************

		// -----------------------------------------------------------------------------------------------------------
		// CHECK COLOR DEFINITIONS
		// -----------------------------------------------------------------------------------------------------------
		checkColorDefinitions(netXML);
		// -----------------------------------------------------------------------------------------------------------
		// CREATE PLACES
		// -----------------------------------------------------------------------------------------------------------
		net = createPlaces(netXML, net, configuration);
		// -----------------------------------------------------------------------------------------------------------
		// CREATE TRANSITIONS
		// -----------------------------------------------------------------------------------------------------------
		net = createTransitions(net);
		// -----------------------------------------------------------------------------------------------------------
		// CONFIGURE INPUT/OUTPUT RELATIONSHIPS
		// -----------------------------------------------------------------------------------------------------------
		net = configureInputOutputRelationships(netXML, net);
		// -----------------------------------------------------------------------------------------------------------
		// CONFIGURE TRANSITION MODE WEIGHTS
		// -----------------------------------------------------------------------------------------------------------
		net = configureTransitionModeWeights(net);
		// -----------------------------------------------------------------------------------------------------------
		// CONFIGURE TRANSITION INPUT/OUTPUT FUNCTIONS [mode, inPlace, color]
		// -----------------------------------------------------------------------------------------------------------
		net = configureTransitionInputOutputFunctions(net);

		// -----------------------------------------------------------------------------------------------------------
		// CONFIGURE QUEUE SERVICE TIME DISTRIBUTIONS (times normally in
		// milliseconds)
		// -----------------------------------------------------------------------------------------------------------
		net = configureQueueServiceTimeDistributions(net);

		// -----------------------------------------------------------------------------------------------------------
		// CREATE PROBES
		// -----------------------------------------------------------------------------------------------------------
		net = createProbes(netXML, net, configuration);
		// --------------------------------------------------------------------------------------------------------
		// CONFIGURE PROBE ATTACHMENT TO PLACES
		// --------------------------------------------------------------------------------------------------------
		net.configureProbes();

		// --------------------------------------------------------------------------------------------------------
		// CONFIGURE INITIAL MARKING
		// --------------------------------------------------------------------------------------------------------
		// Note: All initial tokens should be in ordianary places or
		// depositories.
		// No initial tokens are allowed in the queues.
		configureInitialMarking(net);
		// *****************************************************************************************************************
		// END-MODEL-DEFINITION
		// *****************************************************************************************************************
		return net;
	}

	/**
	 * Checks if there exist same color definitions
	 * 
	 * @param netXML
	 * @throws SimQPNException
	 */
	private static void checkColorDefinitions(Element netXML)
			throws SimQPNException {
		XPath colorSelector = XMLHelper.createXPath("//color");
		List colorList = colorSelector.selectNodes(netXML);
		// Set for checking the uniqueness of color names
		HashSet<String> colorNames = new HashSet<String>();

		for (int i = 0; i < colorList.size(); i++) {
			Element col = (Element) colorList.get(i);

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

	/**
	 * Creates places and queues
	 * 
	 * @param netXML
	 * @param net
	 * @return
	 * @throws SimQPNException
	 */
	private Net createPlaces(Element netXML, Net net, SimQPNConfiguration configuration)
			throws SimQPNException {
		/**
		 * @param int id - global id of the place - IMPORTANT: must be equal to
		 *        the index in the array!!!!!!!
		 * @param String
		 *            name - name of the place
		 * @param int numColors - number of colors
		 * @param int numInTrans - number of input transitions
		 * @param int numOutTrans - number of output transitions
		 * @param int[1..4] statsLevel - determines the amount of statistics to
		 *        be gathered during the run Level 1: Token Throughput
		 *        (Arrival/Departure Rates) Level 2: + Token Population, Token
		 *        Occupancy, Queue Utilization Level 3: + Token Sojourn Times
		 *        (sample mean and variance + steady state point estimates and
		 *        confidence intervals) Level 4: + Token Sojourn Time Histograms
		 *        Level 5: + Record Sojourn Times in a file
		 * @param int depDiscip/dDis - determines the departure discipline:
		 *        Place.NORMAL or Place.FIFO
		 * 
		 *        For QPlace two extra parameters:
		 * 
		 * @param int queueDiscip/qDis - queueing discipline is Queue.IS,
		 *        Queue.FCFS or Queue.PS. NOTE: if a different queueing
		 *        discipline is specified (e.g. RANDOM) print WARNING and use
		 *        FCFS instead.
		 * @param int numServers - number of servers in queueing station - NOTE:
		 *        for IS set to 0
		 * 
		 *        IMPORTANT: Pay attention to variable types!!! Validate input
		 *        parameters provided by the user to make sure that they make
		 *        sense! For e.g. Number of servers should be an integer >= 0.
		 * 
		 *        IMPORTANT: In general, you should provide a way to add
		 *        "help information" associated with different items in the
		 *        tool. For example, if one wants to know what a configuration
		 *        option means, one should be able to somehow click on it and
		 *        ask for help. Ballons that appear when moving the cursor over
		 *        the option is one possibility. A more general solution would
		 *        be to have a help page associated with each input screen in
		 *        the QPE. The page could be displayed by pressing F1 for
		 *        example.
		 * 
		 */

		/*
		 * SDK-DEBUG: A general question: Does your code guarantee that all id's
		 * assigned to elements (places, transitions, colors, connections,
		 * modes) are globally unique across all element types? CHRIS: Yes it
		 * does. The id generator is initialized with the current system time
		 * from there on is allways increased by one for every id generated. Id
		 * generation is synchronized, so there should be no way of creating
		 * double ids (ok ... except if the user messes with the system time or
		 * works during daylightsaving time changes and starts the program the
		 * same second twice, but I thought the chance for that would be quite
		 * slim) ;)
		 */

		log.debug("/////////////////////////////////////////////");
		log.debug("// Create places");
		// Allocate an array able to contain the places.
		log.debug("places = new Place[" + net.getNumPlaces() + "];");
		int numPlaces = this.placeList.size();
		int numQueues = this.queueList.size();

		Place[] places = new Place[numPlaces];
		Queue[] queues = new Queue[numQueues];

		// Set for checking the uniqueness of queue names
		HashSet<String> queueNames = new HashSet<String>();

		for (int i = 0; i < numQueues; i++) {
			Element queue = (Element) this.queueList.get(i);

			int numberOfServers;
			int queueingStrategy = Queue.FCFS;

			String name = queue.attributeValue("name");
			if (queueNames.contains(name)) {
				log.error(formatDetailMessage(
						"Another queue definition with the same name does already exist!",
						"queue-num", Integer.toString(i), "queue.id",
						queue.attributeValue("id"), "queue.name", name));
				throw new SimQPNException();
			} else {
				queueNames.add(name);
			}

			if ("IS".equals(queue.attributeValue("strategy"))) {
				queueingStrategy = Queue.IS;
				numberOfServers = 0; // NOTE: This is assumed in
										// QPlaceQueueStats.updateTkPopStats()!
			} else {
				if ("FCFS".equals(queue.attributeValue("strategy"))) {
					queueingStrategy = Queue.FCFS;
				} else if ("PS".equals(queue.attributeValue("strategy"))) {
					queueingStrategy = Queue.PS;
				} else {
					log.error(formatDetailMessage(
							"Invalid or missing \"strategy\" (queueing discipline) setting!",
							"queue-num", Integer.toString(i), "queue.id",
							queue.attributeValue("id"), "queue.name", name,
							"queue.strategy", queue.attributeValue("strategy")));
					throw new SimQPNException();
				}
				if (queue.attributeValue("number-of-servers") == null) {
					log.error(formatDetailMessage(
							"\"number-of-servers\" parameter not set!",
							"queue-num", Integer.toString(i), "queue.id",
							queue.attributeValue("id"), "queue.name", name));
					throw new SimQPNException();
				}
				numberOfServers = Integer.parseInt(queue
						.attributeValue("number-of-servers"));
			}

			queues[i] = new Queue(i, // index
					queue.attributeValue("id"), // xml-id
					queue.attributeValue("name"), // name
					queueingStrategy, // queueing d
					numberOfServers // # servers
			);
			queueToIndexMap.put(queue, i);
			if (log.isDebugEnabled()) {
				log.debug("queues[" + i + "] = new Queue(" + i + ", '"
						+ queue.attributeValue("name") + "', "
						+ queueingStrategy + ", " + numberOfServers + ")");
			}
		}

		// Create the place-objects of every-place. Depending on its
		// type-attribute create Place or QPlace objects.
		Iterator placeIterator = this.placeList.iterator();
		// Set for checking the uniqueness of place names
		HashSet<String> placeNames = new HashSet<String>();

		for (int i = 0; placeIterator.hasNext(); i++) {
			Element place = (Element) placeIterator.next();

			String name = place.attributeValue("name");
			if (placeNames.contains(name)) {
				log.error(formatDetailMessage(
						"Another place with the same name does already exist!",
						"place-num", Integer.toString(i), "place.id",
						place.attributeValue("id"), "place.name", name));
				throw new SimQPNException();
			} else {
				placeNames.add(name);
			}

			// SDK-DEBUG: Are you sure the XPath expression below selects the
			// right connections?
			// <connection> is a child of the <connections> child element of
			// <net> <connections> inside transitions
			// should not be selected here!
			// CHRIS: Since I specify the source-id attribute and specify the id
			// of a place, only inter place/tansition
			// connections can be selected, the incidence function connections
			// are between color-refs and modes and since
			// the ids are concidered unique, the correct connection element
			// will be selected.

			int numOutgoingConnections = getNumConnectionsWithSourceId(place
					.attributeValue("id"));
			int numIncomingConnections = getNumConnectionsWithTargetId(place
					.attributeValue("id"));

			Element metaAttribute = XMLHelper.getSettings(place,
					net.getConfiguration());
			// Stats-level should be configurable as above.
			if (metaAttribute.attributeValue("statsLevel") == null) {
				log.error(formatDetailMessage("statsLevel parameter not set!",
						"place-num", Integer.toString(i), "place.id",
						place.attributeValue("id"), "place.name",
						place.attributeValue("name")));
				throw new SimQPNException();
			}
			int statsLevel = Integer.parseInt(metaAttribute
					.attributeValue("statsLevel"));

			int dDis;

			// This is a user-defined config-parameter for both ordinary- and
			// queueing-place.
			if (place.attributeValue("departure-discipline") == null) {
				log.error(formatDetailMessage(
						"departure-discipline parameter not set!", "place-num",
						Integer.toString(i), "place.id",
						place.attributeValue("id"), "place.name",
						place.attributeValue("name")));
				throw new SimQPNException();
			}
			if ("NORMAL".equals(place.attributeValue("departure-discipline"))) {
				dDis = Place.NORMAL;
			} else if ("FIFO".equals(place
					.attributeValue("departure-discipline"))) {
				dDis = Place.FIFO;
			} else {
				log.error(formatDetailMessage(
						"Invalid departure-discipline setting!", "place-num",
						Integer.toString(i), "place.id",
						place.attributeValue("id"), "place.name",
						place.attributeValue("name"),
						"place.departure-discipline",
						place.attributeValue("departure-discipline")));
				throw new SimQPNException();
			}
			/*
			 * IMPORTANT: My understanding was that all attributes should be
			 * included in the XML document even if they are set to their
			 * default values. Under this assumption, default values are dealt
			 * with in QPE and here when mapping the model to SimQPN it is
			 * assumed that all attributes are set to valid values. So if that's
			 * not the case we should print an error message and stop. This
			 * would make it easier to shake out potential bugs in the mapping.
			 * 
			 * CHRIS: I had changed that in the editor. Unfortunately this only
			 * applys to newly created nodes.
			 * 
			 * SDK-DEBUG2: I have noticed that still some parameters (e.g.
			 * minObsrv, maxObsrv, queueMinObsrv, queueMaxObsrv,
			 * distribution-function) are not included in the XML file when the
			 * default value is chosen. All attributes must be included in the
			 * XML even if they were not changed from their default values,
			 * i.e., the XML file should be complete! CHRIS: Fixed that now too.
			 */

			// Extract the names of the colors that can reside in this place
			Element colorRefsElem = place.element("color-refs");
			if (colorRefsElem == null) {
				log.error(formatDetailMessage("Missing color references!",
						"place-num", Integer.toString(i), "place.id",
						place.attributeValue("id"), "place.name",
						place.attributeValue("name"),
						"place.departure-discipline",
						place.attributeValue("departure-discipline")));
				throw new SimQPNException();
			}
			List colorRefs = colorRefsElem.elements("color-ref");
			if (colorRefs.size() == 0) {
				log.error(formatDetailMessage("Missing color references!",
						"place-num", Integer.toString(i), "place.id",
						place.attributeValue("id"), "place.name",
						place.attributeValue("name"),
						"place.departure-discipline",
						place.attributeValue("departure-discipline")));
				throw new SimQPNException();
			}
			String[] colors = new String[colorRefs.size()];
			Iterator colorRefIterator = colorRefs.iterator();
			for (int c = 0; colorRefIterator.hasNext(); c++) {
				Element colorRef = (Element) colorRefIterator.next();
				XPath xpathSelector = XMLHelper
						.createXPath("colors/color[(@id='"
								+ colorRef.attributeValue("color-id") + "')]");
				Element color = (Element) xpathSelector
						.selectSingleNode(netXML);
				colors[c] = color.attributeValue("name");
			}

			if ("ordinary-place".equals(place
					.attributeValue(XSI_TYPE_ATTRIBUTE))) {
				places[i] = new Place(i, // id
						place.attributeValue("name"), // name
						colors, // color names
						numIncomingConnections, // # incoming connections
						numOutgoingConnections, // # outgoing connections
						net.getNumProbes(), statsLevel, // stats level
						dDis, // departure discipline
						place, configuration); // Reference to the place' XML element
				placeToIndexMap.put(place, i);
				if (log.isDebugEnabled()) {
					log.debug("places[" + i + "] = new Place(" + i + ", '"
							+ place.attributeValue("name") + "', " + colors
							+ ", " + numIncomingConnections + ", "
							+ numOutgoingConnections + ", " + statsLevel + ", "
							+ dDis + ", " + place + ")");
				}
			} else if ("queueing-place".equals(place
					.attributeValue(XSI_TYPE_ATTRIBUTE))) {
				try {
					String queueRef = place.attributeValue("queue-ref");
					Queue queue = findQueueByXmlId(queueRef, queues);
					places[i] = new QPlace(i, // id
							place.attributeValue("name"), // name
							colors, // color names
							numIncomingConnections, // # incoming connections
							numOutgoingConnections, // # outgoing connections
							net.getNumProbes(), statsLevel, // stats level
							dDis, // departure discipline
							queue, // Reference to the integrated Queue
							place, configuration); // Reference to the place' XML element
					placeToIndexMap.put(place, i);
					if (log.isDebugEnabled()) {
						log.debug("places[" + i + "] = new QPlace(" + i + ", '"
								+ place.attributeValue("name") + "', " + colors
								+ ", " + numIncomingConnections + ", "
								+ numOutgoingConnections + ", " + statsLevel
								+ ", " + dDis + ", " + queue + ", " + place
								+ ")");
					}
					queue.addQPlace((QPlace) places[i]);
				} catch (NoSuchElementException ex) {
					log.error(formatDetailMessage("No queue defined!",
							"place-num", Integer.toString(i), "place.id",
							place.attributeValue("id"), "place.name",
							place.attributeValue("name"), "place.type",
							place.attributeValue(XSI_TYPE_ATTRIBUTE)));
					throw new SimQPNException();
				}
			} else {
				log.error(formatDetailMessage(
						"Invalid or missing place type setting! Currently only 'ordinary-place' and 'queueing-place' are supported.",
						"place-num", Integer.toString(i), "place.id",
						place.attributeValue("id"), "place.name",
						place.attributeValue("name"), "place.type",
						place.attributeValue(XSI_TYPE_ATTRIBUTE)));
				throw new SimQPNException();
			}
		}
		net.setPlaces(places);
		net.setQueues(queues);
		return net;
	}

	private Net createTransitions(Net net) throws SimQPNException {
		/*
		 * public Transition(int id, String name, int numModes, int numInPlaces,
		 * int numOutPlaces, double transWeight)
		 * 
		 * @param int id - global id of the transition
		 * 
		 * @param String name - name of the transition
		 * 
		 * @param int numModes - number of modes
		 * 
		 * @param int numInPlaces - number of input places
		 * 
		 * @param int numOutPlaces - number of output places
		 * 
		 * @param double transWeight - transition weight
		 * 
		 * Validate input parameters provided by the user to make sure that they
		 * make sense!!! For e.g. transWeight >= 0
		 * 
		 * IMPORTANT: Timed transitions and Subnet places are currently not
		 * supported!!! If the net uses them, just print an error message and
		 * exit.
		 * 
		 * IMPORTANT: trans id must be equal to its index in the trans array!!!
		 */

		log.debug("/////////////////////////////////////////////");
		log.debug("// Create transitions");
		// Allocate an array able to contain the transitions.
		log.debug("trans = new Transition[" + net.getNumTrans() + "];");

		Transition[] trans = new Transition[transitionList.size()];
		// Set for checking the uniqueness of transition names
		HashSet<String> transNames = new HashSet<String>();
		// Create the transition-objects of every transition.
		Iterator transitionIterator = this.transitionList.iterator();
		for (int i = 0; transitionIterator.hasNext(); i++) {
			Element transition = (Element) transitionIterator.next();

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

			int numOutgoingConnections = getNumConnectionsWithSourceId(transition.attributeValue("id"));
			int numIncomingConnections = getNumConnectionsWithTargetId(transition.attributeValue("id"));

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

			trans[i] = new Transition(i, // id
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

		net.setTransitions(trans);
		return net;
	}

	private Net createProbes(Element netXML, Net net, SimQPNConfiguration configuration)
			throws SimQPNException {
		XPath xpathSelector;

		log.debug("/////////////////////////////////////////////");
		log.debug("// Create probes");
		// Allocate an array able to contain the places.
		int numProbes = net.getNumProbes();
		log.debug("probes = new Probe[" + numProbes + "];");
		Probe[] probes = new Probe[numProbes];

		for (int i = 0; i < numProbes; i++) {
			Element probe = (Element) this.probeList.get(i);

			// Extract the names of the colors that are tracked by this probe
			Element colorRefsElem = probe.element("color-refs");
			if (colorRefsElem == null) {
				log.error(formatDetailMessage("Missing color references!",
						"probe-num", Integer.toString(i), "probe.id",
						probe.attributeValue("id"), "probe.name",
						probe.attributeValue("name")));
				throw new SimQPNException();
			}
			List colorRefs = colorRefsElem.elements("color-ref");
			if (colorRefs.size() == 0) {
				log.error(formatDetailMessage("Missing color references!",
						"probe-num", Integer.toString(i), "probe.id",
						probe.attributeValue("id"), "probe.name",
						probe.attributeValue("name")));
				throw new SimQPNException();
			}
			String[] colors = new String[colorRefs.size()];
			Iterator colorRefIterator = colorRefs.iterator();
			for (int c = 0; colorRefIterator.hasNext(); c++) {
				Element colorRef = (Element) colorRefIterator.next();
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
				if (this.placeList.get(p).equals(startElement)) {
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
				if (this.placeList.get(p).equals(endElement)) {
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
					net.getConfiguration());
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
		return net;
	}

	private Net configureInputOutputRelationships(Element netXML, Net net)
			throws SimQPNException {
		log.debug("/////////////////////////////////////////////");
		log.debug("// Configure input/output relationships");
		// Initialize the place-transition and transition-place connections.
		XPath xpathSelector = XMLHelper
				.createXPath("/net/connections/connection");
		Iterator connectionIterator = xpathSelector.selectNodes(netXML)
				.iterator();

		while (connectionIterator.hasNext()) {
			// Get the next connection
			Element connection = (Element) connectionIterator.next();

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
		return net;
	}

	private Net configureTransitionModeWeights(Net net) throws SimQPNException {
		log.debug("/////////////////////////////////////////////");
		log.debug("// Configure transition mode weights");
		Iterator transitionIterator = this.transitionList.iterator();
		for (int i = 0; transitionIterator.hasNext(); i++) {
			Element transition = (Element) transitionIterator.next();
			XPath xpathSelector = XMLHelper.createXPath("modes/mode");
			Iterator modeIterator = xpathSelector.selectNodes(transition)
					.iterator();
			for (int j = 0; modeIterator.hasNext(); j++) {
				Element mode = (Element) modeIterator.next();
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
				log.debug("trans[" + i + "].modeWeights[" + j + "] = "
						+ net.getTrans(i).modeWeights[j]);
			}
		}
		return net;
	}

	private Net configureTransitionInputOutputFunctions(Net net)
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
			Element transition = (Element) this.transitionList.get(t);

			// HIER HIER OBEN FR
			XPath xpathSelector = XMLHelper.createXPath("modes/mode");
			List modes = xpathSelector.selectNodes(transition);

			// Iterate through all modes
			for (int m = 0; m < net.getTrans(t).numModes; m++) {
				int numInputTokens = 0;
				// Iterate through all input-places.
				for (int p = 0; p < net.getTrans(t).inPlaces.length; p++) {
					Element inputPlace = net.getTrans(t).inPlaces[p].element;
					// Go through all color-refs for the current place.
					xpathSelector = XMLHelper
							.createXPath("color-refs/color-ref");
					List colorRefs = xpathSelector.selectNodes(inputPlace);

					// Allocate an array saving the number of tokens for each
					// color-ref to the current mode for the
					// current input place. If there is no connection, then this
					// value is 0.
					net.getTrans(t).inFunc[m][p] = new int[colorRefs.size()];
					log.debug("trans[" + t + "].inFunc[" + m + "][" + p
							+ "] = new int[" + colorRefs.size() + "];");

					Iterator colorRefIterator = colorRefs.iterator();
					for (int con = 0; colorRefIterator.hasNext(); con++) {
						Element colorRef = (Element) colorRefIterator.next();

						String colorRefId = colorRef.attributeValue("id");
						String modeId = ((Element) modes.get(m))
								.attributeValue("id");

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
										"mode.id", ((Element) modes.get(m))
												.attributeValue("id"),
										"mode.name", ((Element) modes.get(m))
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
							"mode-num" + Integer.toString(m),
							"mode.id"
									+ ((Element) modes.get(m))
											.attributeValue("id"),
							"mode.name"
									+ ((Element) modes.get(m))
											.attributeValue("name")));
					throw new SimQPNException();
				}

				// Iterate through all output-places.
				for (int p = 0; p < net.getTrans(t).outPlaces.length; p++) {
					Element outputPlace = net.getTrans(t).outPlaces[p].element;
					// Go through all color-refs for the current place.
					xpathSelector = XMLHelper
							.createXPath("color-refs/color-ref");
					List colorRefs = xpathSelector.selectNodes(outputPlace);

					// Allocate an array saving the number of tokens for each
					// color-ref to mode connection.
					// If there is no connection, then this value is 0.
					net.getTrans(t).outFunc[m][p] = new int[colorRefs.size()];
					log.debug("trans[" + t + "].outFunc[" + m + "][" + p
							+ "] = new int[" + colorRefs.size() + "];");

					Iterator colorRefIterator = colorRefs.iterator();
					for (int con = 0; colorRefIterator.hasNext(); con++) {
						Element colorRef = (Element) colorRefIterator.next();

						String colorRefId = colorRef.attributeValue("id");
						String modeId = ((Element) modes.get(m))
								.attributeValue("id");

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
										"mode.id", ((Element) modes.get(m))
												.attributeValue("id"),
										"mode.name", ((Element) modes.get(m))
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
		return net;
	}

	private Net configureQueueServiceTimeDistributions(Net net)
			throws SimQPNException {
		/*
		 * Distribution Name (Initialization Parameters)
		 * ------------------------
		 * ---------------------------------------------------------------------
		 * - Beta (double alpha, double beta) - BreitWigner (double mean, double
		 * gamma, double cut) - BreitWignerMeanSquare (double mean, double
		 * gamma, double cut) - ChiSquare (double freedom) - Gamma (double
		 * alpha, double lambda) - Hyperbolic (double alpha, double beta) -
		 * Exponential (double lambda) - ExponentialPower (double tau) -
		 * Logarithmic (double p) - Normal (double mean, double stdDev) -
		 * StudentT (double freedom) - Uniform (double min, double max) -
		 * VonMises (double freedom) - Empirical (String pdf_filename)
		 * 
		 * For each distribution, additional initialization parameters needed
		 * are shown in the brackets. The default distribution should be
		 * Exponential. Note that the last distribution has a String parameter
		 * containing a file name.
		 * 
		 * Three parameters p1, p2 and p3 of type double in the data model are
		 * used here to initialize the distribution function.
		 * 
		 * TODO: make the editor display the real names of the expected
		 * parameters after the user has chosen the distribution, e.g. for
		 * Exponential a single field labeled "lambda" should be displayed.
		 */

		log.debug("/////////////////////////////////////////////");
		log.debug("// Configure Queue Service Time Distributions (times in milliseconds)");
		Iterator placeIterator = this.placeList.iterator();
		for (int i = 0; placeIterator.hasNext(); i++) {
			Element place = (Element) placeIterator.next();
			if ("queueing-place".equals(place
					.attributeValue(XSI_TYPE_ATTRIBUTE))) {
				QPlace qPl = (QPlace) net.getPlace(i);

				// BEGIN-CONFIG
				if (qPl.queue.queueDiscip == Queue.PS)
					qPl.queue.expPS = false;
				// END-CONFIG

				if (!(qPl.queue.queueDiscip == Queue.PS && qPl.queue.expPS))
					qPl.randServTimeGen = new AbstractContinousDistribution[qPl.numColors];

				XPath xpathSelector = XMLHelper
						.createXPath("color-refs/color-ref");
				Iterator colorRefIterator = xpathSelector.selectNodes(place)
						.iterator();
				for (int j = 0; colorRefIterator.hasNext(); j++) {
					Element colorRef = (Element) colorRefIterator.next();

					if (colorRef.attributeValue("distribution-function") == null) {
						log.error(formatDetailMessage(
								"Queueing place' \"distribution-function\" parameter not set!",
								"place-num", Integer.toString(i), "place.id",
								place.attributeValue("id"), "place.name",
								place.attributeValue("name"), "colorRef-num",
								Integer.toString(j), "colorRef.id",
								colorRef.attributeValue("id"),
								"colorRef.color-id",
								colorRef.attributeValue("color-id")));
						throw new SimQPNException();
					}
					String distributionFunction = colorRef
							.attributeValue("distribution-function");

					if (qPl.queue.queueDiscip == Queue.PS && qPl.queue.expPS) {
						log.info("expPS parameter of a queueing place with PS scheduling strategy set to true!");
						if (!"Exponential".equals(distributionFunction)) {
							log.error(formatDetailMessage(
									"Distribution function is configured as \""
											+ distributionFunction
											+ "\". "
											+ "Distribution function must be set to \"Exponential\" since (expPS == true) !",
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
					}

					/*
					 * The code below does the following: - checks the chosen
					 * distribution function - checks that all required
					 * distribution input parameters have been set - validates
					 * the values of the distribution input parameters -
					 * initializes the random number generators for service
					 * times - initializes the meanServTimes array based on the
					 * chosen distribution
					 * 
					 * The actual values in the meanServTimes array are
					 * currently only used in three cases 1. QPlace.expPS ==
					 * true (Exponential distribution) 2.
					 * QPlace.qPlaceQueueStats.indrStats == true 3.
					 * distribution-function == Deterministic
					 * 
					 * Note: Service time distributions are truncated at 0 to
					 * avoid negative values for service times, i.e.
					 * "if (servTime < 0) servTime = 0;"
					 */

					if ("Beta".equals(distributionFunction)) {
						if (colorRef.attributeValue("alpha") == null
								|| colorRef.attributeValue("beta") == null) {
							log.error(formatDetailMessage(
									"Parameter \"alpha\" or \"beta\" of Beta distribution function not set!",
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						double alpha = Double.parseDouble(colorRef
								.attributeValue("alpha"));
						double beta = Double.parseDouble(colorRef
								.attributeValue("beta"));
						// Validate input parameters
						if (!(alpha > 0 && beta > 0)) {
							log.error(formatDetailMessage(
									"Invalid \"alpha\" or \"beta\" parameter of Beta distribution!",
									"alpha, beta", alpha + ", " + beta,
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						// Initialize random number generator and meanServTimes
						qPl.randServTimeGen[j] = new Beta(alpha, beta,
								RandomNumberGenerator.nextRandNumGen());
						log.debug("((QPlace) places[" + i
								+ "]).randServTimeGen[" + j + "] = new Beta("
								+ alpha + ", " + beta
								+ ", RandomNumberGenerator.nextRandNumGen())");
						qPl.meanServTimes[j] = (double) alpha / (alpha + beta);
						log.debug("((QPlace) places[" + i + "]).meanServTimes["
								+ j + "] = alpha / (alpha + beta) = "
								+ qPl.meanServTimes[j]);
					} else if ("BreitWigner".equals(distributionFunction)) {
						if (colorRef.attributeValue("mean") == null
								|| colorRef.attributeValue("gamma") == null
								|| colorRef.attributeValue("cut") == null) {
							log.error(formatDetailMessage(
									"Parameter \"mean\", \"gamma\" or \"cut\" of BreitWigner distribution function not set!",
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						double mean = Double.parseDouble(colorRef
								.attributeValue("mean"));
						double gamma = Double.parseDouble(colorRef
								.attributeValue("gamma"));
						double cut = Double.parseDouble(colorRef
								.attributeValue("cut"));
						// Validate input parameters
						if (gamma <= 0) {
							log.error(formatDetailMessage(
									"Invalid \"gamma\" parameter of BreitWigner distribution!",
									"gamma", Double.toString(gamma),
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						// Initialize random number generator and meanServTimes
						qPl.randServTimeGen[j] = new BreitWigner(mean, gamma,
								cut, RandomNumberGenerator.nextRandNumGen());
						log.debug("((QPlace) places[" + i
								+ "]).randServTimeGen[" + j
								+ "] = new BreitWigner(" + mean + ", " + gamma
								+ ", " + cut
								+ ", RandomNumberGenerator.nextRandNumGen())");
						// NOTE: BreitWigner does not have a mean value! It is
						// undefined.
					} else if ("BreitWignerMeanSquare"
							.equals(distributionFunction)) {
						if (colorRef.attributeValue("mean") == null
								|| colorRef.attributeValue("gamma") == null
								|| colorRef.attributeValue("cut") == null) {
							log.error(formatDetailMessage(
									"Parameter \"mean\", \"gamma\" or \"cut\" of BreitWignerMeanSquare distribution function not set!",
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						double mean = Double.parseDouble(colorRef
								.attributeValue("mean"));
						double gamma = Double.parseDouble(colorRef
								.attributeValue("gamma"));
						double cut = Double.parseDouble(colorRef
								.attributeValue("cut"));
						// Validate input parameters
						if (gamma <= 0) {
							log.error(formatDetailMessage(
									"Invalid \"gamma\" parameter of BreitWignerMeanSquare distribution!",
									"gamma", Double.toString(gamma),
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						// Initialize random number generator and meanServTimes
						qPl.randServTimeGen[j] = new BreitWignerMeanSquare(
								mean, gamma, cut,
								RandomNumberGenerator.nextRandNumGen());
						log.debug("((QPlace) places[" + i
								+ "]).randServTimeGen[" + j
								+ "] = new BreitWignerMeanSquare(" + mean
								+ ", " + gamma + ", " + cut
								+ ", RandomNumberGenerator.nextRandNumGen())");
						// NOTE: BreitWigner does not have a mean value! It is
						// undefined.
					} else if ("ChiSquare".equals(distributionFunction)) {
						if (colorRef.attributeValue("freedom") == null) {
							log.error(formatDetailMessage(
									"Parameter \"freedom\" of ChiSquare distribution function not set!",
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						double freedom = Double.parseDouble(colorRef
								.attributeValue("freedom"));
						// Validate input parameters
						if (!(freedom > 0)) {
							log.error(formatDetailMessage(
									"Invalid \"freedom\" parameter of ChiSquare distribution!",
									"freedom", Double.toString(freedom),
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						// Initialize random number generator and meanServTimes
						qPl.randServTimeGen[j] = new ChiSquare(freedom,
								RandomNumberGenerator.nextRandNumGen());
						log.debug("((QPlace) places[" + i
								+ "]).randServTimeGen[" + j
								+ "] = new ChiSquare(" + freedom
								+ ", RandomNumberGenerator.nextRandNumGen())");
						qPl.meanServTimes[j] = freedom;
						log.debug("((QPlace) places[" + i + "]).meanServTimes["
								+ j + "] = freedom = " + qPl.meanServTimes[j]);
					} else if ("Gamma".equals(distributionFunction)) {
						if (colorRef.attributeValue("alpha") == null
								|| colorRef.attributeValue("lambda") == null) {
							log.error(formatDetailMessage(
									"Parameter \"alpha\" or \"lambda\" of Gamma distribution function not set!",
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						double alpha = Double.parseDouble(colorRef
								.attributeValue("alpha"));
						double lambda = Double.parseDouble(colorRef
								.attributeValue("lambda"));
						// Validate input parameters
						if (!(alpha > 0 && lambda > 0)) {
							log.error(formatDetailMessage(
									"Invalid \"alpha\" or \"lambda\" parameter of Gamma distribution!",
									"alpha, lambda", alpha + ", " + lambda,
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						// Initialize random number generator and meanServTimes
						qPl.randServTimeGen[j] = new Gamma(alpha, lambda,
								RandomNumberGenerator.nextRandNumGen());
						log.debug("((QPlace) places[" + i
								+ "]).randServTimeGen[" + j + "] = new Gamma("
								+ alpha + ", " + lambda
								+ ", RandomNumberGenerator.nextRandNumGen())");
						qPl.meanServTimes[j] = alpha * lambda;
						log.debug("((QPlace) places[" + i + "]).meanServTimes["
								+ j + "] = alpha * lambda = "
								+ qPl.meanServTimes[j]);
					} else if ("Hyperbolic".equals(distributionFunction)) {
						if (colorRef.attributeValue("alpha") == null
								|| colorRef.attributeValue("beta") == null) {
							log.error(formatDetailMessage(
									"Parameter \"alpha\" or \"beta\" of Hyperbolic distribution function not set!",
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						double alpha = Double.parseDouble(colorRef
								.attributeValue("alpha"));
						double beta = Double.parseDouble(colorRef
								.attributeValue("beta"));
						// Validate input parameters
						if (!(alpha > 0 && beta > 0)) {
							log.error(formatDetailMessage(
									"Invalid \"alpha\" or \"beta\" parameter of Hyperbolic distribution!",
									"alpha, beta", alpha + ", " + beta,
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						// Initialize random number generator and meanServTimes
						qPl.randServTimeGen[j] = new Hyperbolic(alpha, beta,
								RandomNumberGenerator.nextRandNumGen());
						log.debug("((QPlace) places[" + i
								+ "]).randServTimeGen[" + j
								+ "] = new Hyperbolic(" + alpha + ", " + beta
								+ ", RandomNumberGenerator.nextRandNumGen())");
						// SDK-TODO: find out how meanServTimes is computed?
						// qPl.meanServTimes[j] = (double) ???;
						// logln(2, "((QPlace) places[" + i +
						// "]).meanServTimes[" + j + "] = ??? = " +
						// qPl.meanServTimes[j]);
						log.warn(formatDetailMessage(
								"meanServTimes for Hyperbolic distribution not initialized! Might experience problems if indrStats is set to true!",
								"place-num", Integer.toString(i), "place.id",
								place.attributeValue("id"), "place.name",
								place.attributeValue("name"), "colorRef-num",
								Integer.toString(j), "colorRef.id",
								colorRef.attributeValue("id"),
								"colorRef.color-id",
								colorRef.attributeValue("color-id")));
					} else if ("Exponential".equals(distributionFunction)) {
						if (colorRef.attributeValue("lambda") == null) {
							log.error(formatDetailMessage(
									"Parameter \"lambda\" of Exponential distribution function not set!",
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						double lambda = Double.parseDouble(colorRef
								.attributeValue("lambda"));
						// Validate input parameters
						if (!(lambda > 0)) {
							log.error(formatDetailMessage(
									"Invalid \"lambda\" parameter of Exponential distribution!",
									"lambda", Double.toString(lambda),
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						// Initialize random number generator and meanServTimes
						if (!(qPl.queue.queueDiscip == Queue.PS && qPl.queue.expPS)) {
							qPl.randServTimeGen[j] = new Exponential(lambda,
									RandomNumberGenerator.nextRandNumGen());
							log.debug("((QPlace) places["
									+ i
									+ "]).randServTimeGen["
									+ j
									+ "] = new Exponential("
									+ lambda
									+ ", RandomNumberGenerator.nextRandNumGen())");
						}
						qPl.meanServTimes[j] = (double) 1 / lambda;
						log.debug("((QPlace) places[" + i + "]).meanServTimes["
								+ j + "] = 1 / lambda = "
								+ qPl.meanServTimes[j]);
					} else if ("ExponentialPower".equals(distributionFunction)) {
						if (colorRef.attributeValue("tau") == null) {
							log.error(formatDetailMessage(
									"Parameter \"tau\" of ExponentialPower distribution function not set!",
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						double tau = Double.parseDouble(colorRef
								.attributeValue("tau"));
						// Validate input parameters
						if (!(tau >= 1)) {
							log.error(formatDetailMessage(
									"Invalid \"tau\" parameter of ExponentialPower distribution!",
									"tau", Double.toString(tau), "place-num",
									Integer.toString(i), "place.id",
									place.attributeValue("id"), "place.name",
									place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						// Initialize random number generator and meanServTimes
						qPl.randServTimeGen[j] = new ExponentialPower(tau,
								RandomNumberGenerator.nextRandNumGen());
						log.debug("((QPlace) places[" + i
								+ "]).randServTimeGen[" + j
								+ "] = new ExponentialPower(" + tau
								+ ", RandomNumberGenerator.nextRandNumGen())");
						// SDK-TODO: find out how meanServTimes is computed?
						// qPl.meanServTimes[j] = (double) ???;
						// logln(2, "((QPlace) places[" + i +
						// "]).meanServTimes[" + j + "] = ??? = " +
						// qPl.meanServTimes[j]);
						log.warn(formatDetailMessage(
								"meanServTimes for ExponentialPower distribution not initialized! Might experience problems if indrStats is set to true!",
								"place-num", Integer.toString(i), "place.id",
								place.attributeValue("id"), "place.name",
								place.attributeValue("name"), "colorRef-num",
								Integer.toString(j), "colorRef.id",
								colorRef.attributeValue("id"),
								"colorRef.color-id",
								colorRef.attributeValue("color-id")));
					} else if ("Logarithmic".equals(distributionFunction)) {
						if (colorRef.attributeValue("p") == null) {
							log.error(formatDetailMessage(
									"Parameter \"p\" of Logarithmic distribution function not set!",
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						double p = Double.parseDouble(colorRef
								.attributeValue("p"));
						// Validate input parameters
						if (!(0 < p && p < 1)) {
							log.error(formatDetailMessage(
									"Invalid \"p\" parameter of Logarithmic distribution!",
									"p", Double.toString(p), "place-num",
									Integer.toString(i), "place.id",
									place.attributeValue("id"), "place.name",
									place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						// Initialize random number generator and meanServTimes
						qPl.randServTimeGen[j] = new Logarithmic(p,
								RandomNumberGenerator.nextRandNumGen());
						log.debug("((QPlace) places[" + i
								+ "]).randServTimeGen[" + j
								+ "] = new Logarithmic(" + p
								+ ", RandomNumberGenerator.nextRandNumGen())");
						qPl.meanServTimes[j] = (double) ((-1) * p)
								/ (Math.log(1 - p) * (1 - p));
						log.debug("((QPlace) places[" + i + "]).meanServTimes["
								+ j
								+ "] = ((-1) * p) / (Math.log(1-p) * (1-p)) = "
								+ qPl.meanServTimes[j]);
					} else if ("Normal".equals(distributionFunction)) {
						if (colorRef.attributeValue("mean") == null
								|| colorRef.attributeValue("stdDev") == null) {
							log.error(formatDetailMessage(
									"Parameter \"mean\" or \"stdDev\" of Normal distribution function not set!",
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						double mean = Double.parseDouble(colorRef
								.attributeValue("mean"));
						double stdDev = Double.parseDouble(colorRef
								.attributeValue("stdDev"));
						// Validate input parameters
						if (!(stdDev > 0)) {
							log.error(formatDetailMessage(
									"Invalid \"stdDev\" parameter of Normal distribution!",
									"stdDev", Double.toString(stdDev),
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						// Initialize random number generator and meanServTimes
						qPl.randServTimeGen[j] = new Normal(mean, stdDev,
								RandomNumberGenerator.nextRandNumGen());
						log.debug("((QPlace) places[" + i
								+ "]).randServTimeGen[" + j + "] = new Normal("
								+ mean + ", " + stdDev
								+ ", RandomNumberGenerator.nextRandNumGen())");
						qPl.meanServTimes[j] = mean;
						log.debug("((QPlace) places[" + i + "]).meanServTimes["
								+ j + "] = mean = " + qPl.meanServTimes[j]);
					} else if ("StudentT".equals(distributionFunction)) {
						if (colorRef.attributeValue("freedom") == null) {
							log.error(formatDetailMessage(
									"Parameter \"freedom\" of StudentT distribution function not set!",
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						double freedom = Double.parseDouble(colorRef
								.attributeValue("freedom"));
						// Validate input parameters
						if (!(freedom > 0)) {
							log.error(formatDetailMessage(
									"Invalid \"freedom\" parameter of StudentT distribution!",
									"freedom", Double.toString(freedom),
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						// Initialize random number generator and meanServTimes
						qPl.randServTimeGen[j] = new StudentT(freedom,
								RandomNumberGenerator.nextRandNumGen());
						log.debug("((QPlace) places[" + i
								+ "]).randServTimeGen[" + j
								+ "] = new StudentT(" + freedom
								+ ", RandomNumberGenerator.nextRandNumGen())");
						// NOTE: The mean of the StudentT distribution is 0 for
						// freedom > 1, otherwise it is undefined.
						if (freedom > 1) {
							qPl.meanServTimes[j] = 0;
							log.debug("((QPlace) places[" + i
									+ "]).meanServTimes[" + j + "] = "
									+ qPl.meanServTimes[j]);
						} else {
							log.warn(formatDetailMessage(
									"meanServTimes for StudentT distribution not initialized! Might experience problems if indrStats is set to true!",
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
						}
					} else if ("Uniform".equals(distributionFunction)) {
						if (colorRef.attributeValue("min") == null
								|| colorRef.attributeValue("max") == null) {
							log.error(formatDetailMessage(
									"Parameter \"min\" or \"max\" of Uniform distribution function not set!",
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						double min = Double.parseDouble(colorRef
								.attributeValue("min"));
						double max = Double.parseDouble(colorRef
								.attributeValue("max"));
						if (!(min < max)) {
							log.error(formatDetailMessage(
									"Invalid \"min\" or \"max\" parameter of Uniform distribution!",
									"min,max", min + "," + max, "place-num",
									Integer.toString(i), "place.id",
									place.attributeValue("id"), "place.name",
									place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						// Initialize random number generator and meanServTimes
						qPl.randServTimeGen[j] = new Uniform(min, max,
								RandomNumberGenerator.nextRandNumGen());
						log.debug("((QPlace) places[" + i
								+ "]).randServTimeGen[" + j
								+ "] = new Uniform(" + min + ", " + max
								+ ", RandomNumberGenerator.nextRandNumGen())");
						qPl.meanServTimes[j] = (double) (min + max) / 2;
						log.debug("((QPlace) places[" + i + "]).meanServTimes["
								+ j + "] = (min + max) / 2 = "
								+ qPl.meanServTimes[j]);
					} else if ("VonMises".equals(distributionFunction)) {
						if (colorRef.attributeValue("freedom") == null) {
							log.error(formatDetailMessage(
									"Parameter \"freedom\" of VonMises distribution function not set!",
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						double freedom = Double.parseDouble(colorRef
								.attributeValue("freedom"));
						if (!(freedom > 0)) {
							log.error(formatDetailMessage(
									"Invalid \"k\" parameter of VonMises distribution!",
									"k", Double.toString(freedom), "place-num",
									Integer.toString(i), "place.id",
									place.attributeValue("id"), "place.name",
									place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						// TODO: Check parameters. Rename "freedom" to "k" to
						// avoid confusion.
						// Initialize random number generator and meanServTimes
						qPl.randServTimeGen[j] = new VonMises(freedom,
								RandomNumberGenerator.nextRandNumGen());
						log.debug("((QPlace) places[" + i
								+ "]).randServTimeGen[" + j
								+ "] = new VonMises(" + freedom
								+ ", RandomNumberGenerator.nextRandNumGen())");
						// SDK-TODO: find out how meanServTimes is computed?
						// qPl.meanServTimes[j] = (double) ???;
						// logln(2, "((QPlace) places[" + i +
						// "]).meanServTimes[" + j + "] = ??? = " +
						// qPl.meanServTimes[j]);

						log.warn(formatDetailMessage(
								" meanServTimes for VonMises distribution not initialized! Might experience problems if indrStats is set to true!",
								"place-num", Integer.toString(i), "place.id",
								place.attributeValue("id"), "place.name",
								place.attributeValue("name"), "colorRef-num",
								Integer.toString(j), "colorRef.id",
								colorRef.attributeValue("id"),
								"colorRef.color-id",
								colorRef.attributeValue("color-id")));
					} else if ("Empirical".equals(distributionFunction)) {
						if (colorRef.attributeValue("pdf_filename") == null) {
							log.error(formatDetailMessage(
									"Parameter \"pdf_filename\" of Empirical distribution function not set!",
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						String pdf_filename = colorRef
								.attributeValue("pdf_filename");
						double[] pdf;
						File pdfFile = new File(pdf_filename);
						if (!pdfFile.exists()) {
							log.error(formatDetailMessage(
									"PDF file of Empirical distribution does not exist!",
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id"),
									"colorRef.pdf_filename", pdf_filename));
							throw new SimQPNException();
						}
						BufferedReader input = null;
						try {
							input = new BufferedReader(new FileReader(pdfFile));
							String line = null;
							if ((line = input.readLine()) == null) {
								log.error(formatDetailMessage(
										"Invalid PDF file of Empirical distribution!",
										"place-num", Integer.toString(i),
										"place.id", place.attributeValue("id"),
										"place.name",
										place.attributeValue("name"),
										"colorRef-num", Integer.toString(j),
										"colorRef.id",
										colorRef.attributeValue("id"),
										"colorRef.color-id",
										colorRef.attributeValue("color-id"),
										"colorRef.pdf_filename", pdf_filename));
								throw new SimQPNException();
							}
							// SDK-TODO: See if it would be better to have
							// values on separate lines?
							String[] params = line.split(";");
							pdf = new double[params.length];
							for (int x = 0; x < params.length; x++)
								pdf[x] = Double.parseDouble(params[x]);
						} catch (IOException ex) {
							log.error(
									formatDetailMessage(
											"Invalid PDF file of Empirical distribution!",
											"place-num",
											Integer.toString(i),
											"place.id",
											place.attributeValue("id"),
											"place.name",
											place.attributeValue("name"),
											"colorRef-num",
											Integer.toString(j),
											"colorRef.id",
											colorRef.attributeValue("id"),
											"colorRef.color-id",
											colorRef.attributeValue("color-id"),
											"colorRef.pdf_filename",
											pdf_filename), ex);
							throw new SimQPNException();
						} finally {
							try {
								if (input != null)
									input.close();
							} catch (IOException ex) {
								log.error("ERROR: Cannot close PDF file "
										+ pdf_filename, ex);
								throw new SimQPNException();
							}
						}
						// Initialize random number generator and meanServTimes
						qPl.randServTimeGen[j] = new Empirical(pdf,
								cern.jet.random.Empirical.LINEAR_INTERPOLATION,
								RandomNumberGenerator.nextRandNumGen());
						log.debug("((QPlace) places["
								+ i
								+ "]).randServTimeGen["
								+ j
								+ "] = new Empirical("
								+ pdf_filename
								+ ", LINEAR_INTERPOLATION, RandomNumberGenerator.nextRandNumGen())");
						// SDK-TODO: find out how meanServTimes is computed?
						// qPl.meanServTimes[j] = (double) ???;
						// logln(2, "((QPlace) places[" + i +
						// "]).meanServTimes[" + j + "] = ??? = " +
						// qPl.meanServTimes[j]);
						log.warn(formatDetailMessage(
								"meanServTimes for Empirical distribution not initialized! Might experience problems if indrStats is set to true!",
								"place-num", Integer.toString(i), "place.id",
								place.attributeValue("id"), "place.name",
								place.attributeValue("name"), "colorRef-num",
								Integer.toString(j), "colorRef.id",
								colorRef.attributeValue("id"),
								"colorRef.color-id",
								colorRef.attributeValue("color-id")));
					} else if ("Deterministic".equals(distributionFunction)) {
						if (colorRef.attributeValue("p1") == null) {
							log.error(formatDetailMessage(
									"Parameter \"p1\" of Deterministic distribution function not set!",
									"place-num", Integer.toString(i),
									"place.id", place.attributeValue("id"),
									"place.name", place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}
						double p1 = Double.parseDouble(colorRef
								.attributeValue("p1"));
						// Validate input parameters
						if (!(p1 >= 0)) {
							log.error(formatDetailMessage(
									"Invalid \"p1\" parameter of Exponential distribution!",
									"p1", Double.toString(p1), "place-num",
									Integer.toString(i), "place.id",
									place.attributeValue("id"), "place.name",
									place.attributeValue("name"),
									"colorRef-num", Integer.toString(j),
									"colorRef.id",
									colorRef.attributeValue("id"),
									"colorRef.color-id",
									colorRef.attributeValue("color-id")));
							throw new SimQPNException();
						}

						qPl.randServTimeGen[j] = new Deterministic(p1);
						log.debug("((QPlace) places[" + i
								+ "]).randServTimeGen[" + j
								+ "] = new Deterministic(" + p1 + ")");
						qPl.meanServTimes[j] = p1;
						log.debug("((QPlace) places[" + i + "]).meanServTimes["
								+ j + "] = p1 = " + qPl.meanServTimes[j]);
					}
				}
			}
		}
		return net;
	}

	private void configureInitialMarking(Net net) throws SimQPNException {
		log.debug("/////////////////////////////////////////////");
		log.debug("// Configure Initial Marking");
		Iterator placeIterator = this.placeList.iterator();
		for (int i = 0; placeIterator.hasNext(); i++) {
			Element place = (Element) placeIterator.next();
			XPath xpathSelector = XMLHelper.createXPath("color-refs/color-ref");
			Iterator colorRefIterator = xpathSelector.selectNodes(place)
					.iterator();
			for (int j = 0; colorRefIterator.hasNext(); j++) {
				Element colorRef = (Element) colorRefIterator.next();
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

	private static Queue findQueueByXmlId(String xmlId, Queue[] queues) {
		for (int i = 0; i < queues.length; i++) {
			Queue queue = queues[i];
			if (xmlId.equals(queue.xmlId)) {
				return queue;
			}
		}
		throw new NoSuchElementException();
	}
	
	protected int getNumConnectionsWithSourceId(String id) {
		Integer num = sourceIdToNumConnectionsMap.get(id);
		if (num != null) {
			return num.intValue();
		}
		return 0;
	}
	
	protected int getNumConnectionsWithTargetId(String id) {
		Integer num = targetIdToNumConnectionsMap.get(id);
		if (num != null) {
			return num.intValue();
		}
		return 0;
	}


}
