package de.tud.cs.simqpn.kernel.loading.loader;

import static de.tud.cs.simqpn.kernel.util.LogUtil.formatDetailMessage;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.XPath;

import de.tud.cs.simqpn.kernel.SimQPNConfiguration;
import de.tud.cs.simqpn.kernel.SimQPNException;
import de.tud.cs.simqpn.kernel.entities.Net;
import de.tud.cs.simqpn.kernel.entities.Place;
import de.tud.cs.simqpn.kernel.entities.QPlace;
import de.tud.cs.simqpn.kernel.entities.Place.DepartureDiscipline;
import de.tud.cs.simqpn.kernel.entities.queue.Queue;
import de.tud.cs.simqpn.kernel.entities.queue.QueuingDiscipline;
import de.tud.cs.simqpn.kernel.loading.NetLoader;
import de.tud.cs.simqpn.kernel.loading.XMLHelper;

public class PlaceLoader {
	private static Logger log = Logger.getLogger(PlaceLoader.class);

	/**
	 * Creates places
	 * 
	 * @param netXML
	 * @param net
	 * @return
	 * @throws SimQPNException
	 */
	public static Place[] createPlaces(NetLoader netloader)
			throws SimQPNException {
		// List<Element> placeList, Element netXML, Net net, SimQPNConfiguration
		// configuration
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
		Net net = netloader.getNet();
		List<Element> placeList = netloader.getPlaceList();
		SimQPNConfiguration configuration = netloader.getConfiguration();
		Element netXML = netloader.getNetXML();
		log.debug("/////////////////////////////////////////////");
		log.debug("// Create places");
		// Allocate an array able to contain the places.
		log.debug("places = new Place[" + net.getNumPlaces() + "];");
		int numPlaces = placeList.size();
		Place[] places = new Place[numPlaces];

		// Create the place-objects of every-place. Depending on its
		// type-attribute create Place or QPlace objects.
		Iterator<Element> placeIterator = placeList.iterator();
		// Set for checking the uniqueness of place names
		HashSet<String> placeNames = new HashSet<String>();

		for (int i = 0; placeIterator.hasNext(); i++) {
			Element place = placeIterator.next();

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

			int numOutgoingConnections = netloader
					.getNumConnectionsWithSourceId(place.attributeValue("id"));
			int numIncomingConnections = netloader
					.getNumConnectionsWithTargetId(place.attributeValue("id"));

			Element metaAttribute = XMLHelper.getSettings(place,
					net.getConfigurationName());
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

			DepartureDiscipline departureDiscipline;

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
				departureDiscipline = Place.DepartureDiscipline.NORMAL;
			} else if ("FIFO".equals(place
					.attributeValue("departure-discipline"))) {
				departureDiscipline = Place.DepartureDiscipline.FIFO;
			} else if ("RANDOM".equals(place
					.attributeValue("departure-discipline"))) {
				departureDiscipline = Place.DepartureDiscipline.RANDOM;
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
			List<Element> colorRefs = colorRefsElem.elements("color-ref");
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
			int[] priorities = new int[colorRefs.size()];
			Iterator<Element> colorRefIterator = colorRefs.iterator();
			for (int c = 0; colorRefIterator.hasNext(); c++) {
				Element colorRef = colorRefIterator.next();
				XPath xpathSelector = XMLHelper
						.createXPath("colors/color[(@id='"
								+ colorRef.attributeValue("color-id") + "')]");
				Element color = (Element) xpathSelector
						.selectSingleNode(netXML);
				colors[c] = color.attributeValue("name");
				priorities[c] = (colorRef.attributeValue("priority") == null) ? 0
						: Integer.valueOf(colorRef.attributeValue("priority"));
			}

			if ("ordinary-place".equals(place
					.attributeValue(NetLoader.XSI_TYPE_ATTRIBUTE))) {
				places[i] = new Place(i, // id
						place.attributeValue("name"), // name
						colors, // color names
						numIncomingConnections, // # incoming connections
						numOutgoingConnections, // # outgoing connections
						net.getNumProbes(), statsLevel, // stats level
						departureDiscipline, // departure discipline
						place, configuration); // Reference to the place' XML
												// element
				netloader.placeToIndexMap.put(place, i);
				if (log.isDebugEnabled()) {
					log.debug("places[" + i + "] = new Place(" + i + ", '"
							+ place.attributeValue("name") + "', " + colors
							+ ", " + numIncomingConnections + ", "
							+ numOutgoingConnections + ", " + statsLevel + ", "
							+ departureDiscipline + ", " + place + ")");
				}
			} else if ("queueing-place".equals(place
					.attributeValue(NetLoader.XSI_TYPE_ATTRIBUTE))) {
				try {
					String queueRef = place.attributeValue("queue-ref");
					Queue queue = netloader.findQueueByXmlId(queueRef,
							net.getQueues());
					places[i] = new QPlace(i, // id
							place.attributeValue("name"), // name
							colors, // color names
							numIncomingConnections, // # incoming connections
							numOutgoingConnections, // # outgoing connections
							net.getNumProbes(), statsLevel, // stats level
							departureDiscipline, // departure discipline
							queue, // Reference to the integrated Queue
							place, configuration); // Reference to the place'
													// XML element
					if(queue.queueDiscip == QueuingDiscipline.PRIO){
						for(int j=0; j<priorities.length; j++){
							if(priorities[j]==0){
								log.warn("Priority for color "+colors[j]+" at queueing place "+place.attributeValue("name")+" has not been specified");
								log.warn("Set priority to 0.");
							}
						}
						((QPlace) places[i]).setPriorities(priorities);
					}
					netloader.placeToIndexMap.put(place, i);
					if (log.isDebugEnabled()) {
						log.debug("places[" + i + "] = new QPlace(" + i + ", '"
								+ place.attributeValue("name") + "', " + colors
								+ ", " + numIncomingConnections + ", "
								+ numOutgoingConnections + ", " + statsLevel
								+ ", " + departureDiscipline + ", " + queue
								+ ", " + place + ")");
					}
					queue.addQPlace((QPlace) places[i]);
				} catch (NoSuchElementException ex) {
					log.error(formatDetailMessage("No queue defined!",
							"place-num", Integer.toString(i), "place.id",
							place.attributeValue("id"), "place.name",
							place.attributeValue("name"), "place.type",
							place.attributeValue(NetLoader.XSI_TYPE_ATTRIBUTE)));
					throw new SimQPNException();
				}
			} else {
				log.error(formatDetailMessage(
						"Invalid or missing place type setting! Currently only 'ordinary-place' and 'queueing-place' are supported.",
						"place-num", Integer.toString(i), "place.id",
						place.attributeValue("id"), "place.name",
						place.attributeValue("name"), "place.type",
						place.attributeValue(NetLoader.XSI_TYPE_ATTRIBUTE)));
				throw new SimQPNException();
			}
		}
		return places;
	}

}
