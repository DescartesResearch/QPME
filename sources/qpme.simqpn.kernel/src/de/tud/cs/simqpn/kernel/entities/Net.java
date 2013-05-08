package de.tud.cs.simqpn.kernel.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;

import de.tud.cs.simqpn.kernel.SimQPNControler;
import de.tud.cs.simqpn.kernel.SimQPNException;

public class Net {
	// XML Configuration
	private String configuration;
	protected Element net;
	private List<Place> placeList;
	private List<Transition> transitionList;
	private List<Queue> queueList;
	private List<Probe> probeList;

	private int numPlaces;
	private int numTrans;
	private int numQueues;
	private int numProbes;

	private Place[] places;
	private Transition[] trans;
	private Queue[] queues;
	private Probe[] probes;

	// hashmaps to allow fast lookup of array index for a given element
	public Map<Element, Integer> placeToIndexMap;
	public Map<Element, Integer> transitionToIndexMap;
	public Map<Element, Integer> queueToIndexMap;

	// hashmaps to allow fast lookup of number of incoming and outgoing
	// connections
	private Map<String, Integer> sourceIdToNumConnectionsMap = new HashMap<String, Integer>();
	private Map<String, Integer> targetIdToNumConnectionsMap = new HashMap<String, Integer>();

	public Map<String, Element> idToElementMap;

	private static Logger log = Logger.getLogger(SimQPNControler.class);

	/**
	 * Constructor
	 * @param netXML
	 * @throws SimQPNException
	 */
	public Net() throws SimQPNException {
		 placeToIndexMap = new HashMap<Element, Integer>();
		 transitionToIndexMap = new HashMap<Element, Integer>();
		 queueToIndexMap = new HashMap<Element, Integer>();
		 
		 sourceIdToNumConnectionsMap = new HashMap<String, Integer>();
		 targetIdToNumConnectionsMap = new HashMap<String, Integer>();
		 idToElementMap = new HashMap<String, Element>();
	}

	public Element getElement(String id){
		return idToElementMap.get(id);		
	}
	
	public void initializePlaceAndTransitionSizesAndArrays() {
		setNumPlaces(placeList.size());
		setNumTrans(transitionList.size());
		setNumQueues(queueList.size());
		setNumProbes(probeList.size());
		places = new Place[numPlaces];
		trans = new Transition[numTrans];
		queues = new Queue[numQueues];
		probes = new Probe[numProbes];
	}


	public void addQueue(int id, Queue queue){
		queues[id] = queue;
	}

	public void addPlace(int id, Place place){
		places[id] = place;
	}

	
	public Element getTransElement(int id){
		return (Element) transitionList.get(id);
	}
	
	public Element getProbeElement(int id){
		return (Element) probeList.get(id);
	}

	public Element getPlaceElement(int id){
		return (Element) placeList.get(id);
	}

	public Element getQueueElement(int id){
		return (Element) queueList.get(id);
	}

	public Queue getQueue(int id){
		return queues[id];
	}

	public Place getPlace(int id){
		return places[id];
	}

	public Transition getTrans(int id){
		return trans[id];
	}

	public Probe getProbe(int id){
		return probes[id];
	}

	
	public void configureProbes() throws SimQPNException {
		for (int pr = 0; pr < getNumProbes(); pr++) {
			probes[pr].instrument();
		}
	}

	public Map<String, Integer> getSourceIdToNumConnectionsMap() {
		return sourceIdToNumConnectionsMap;
	}
	
	/**
	 * Adds tuple to SourceIdToNumConnectionsMap
	 * @param sourceId
	 * @param numSourceIdConnections
	 */
	public void addSourceIdToNumConnectionsTuple(String sourceId, int numSourceIdConnections) {
		sourceIdToNumConnectionsMap.put(sourceId, numSourceIdConnections);
	}
	
	public void addIdToElementTuple(String eId, Element e){
		idToElementMap.put(eId, e);
	}
	
	public Integer getNumConnections(String sourceId) {
		return sourceIdToNumConnectionsMap.get(sourceId);
	}

	public void setSourceIdToNumConnectionsMap(
			Map<String, Integer> sourceIdToNumConnectionsMap) {
		this.sourceIdToNumConnectionsMap = sourceIdToNumConnectionsMap;
	}

	public Map<String, Integer> getTargetIdToNumConnectionsMap() {
		return targetIdToNumConnectionsMap;
	}

	public void setTargetIdToNumConnectionsMap(
			Map<String, Integer> targetIdToNumConnectionsMap) {
		this.targetIdToNumConnectionsMap = targetIdToNumConnectionsMap;
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

	public List<Place> getPlaceList() {
		return placeList;
	}

	public void setPlaceList(List<Place> placeList) {
		this.placeList = placeList;
	}

	public List<Transition> getTransitionList() {
		return transitionList;
	}

	public void setTransitionList(List<Transition> transitionList) {
		this.transitionList = transitionList;
	}

	public List<Queue> getQueueList() {
		return queueList;
	}

	public void setQueueList(List<Queue> queueList) {
		this.queueList = queueList;
	}

	public List<Probe> getProbeList() {
		return probeList;
	}

	public void setProbeList(List<Probe> probeList) {
		this.probeList = probeList;
	}
	
	public Probe[] getProbeArray() {
		return probes;
	}

	public Place[] getPlaceArray() {
		return places;
	}

	public Queue[] getQueueArray() {
		return queues;
	}


	public int getNumPlaces() {
		return numPlaces;
	}

	public void setNumPlaces(int numPlaces) {
		this.numPlaces = numPlaces;
	}

	public int getNumTrans() {
		return numTrans;
	}

	public void setNumTrans(int numTrans) {
		this.numTrans = numTrans;
	}

	public int getNumQueues() {
		return numQueues;
	}

	public void setNumQueues(int numQueues) {
		this.numQueues = numQueues;
	}

	public int getNumProbes() {
		return numProbes;
	}

	public void setNumProbes(int numProbes) {
		this.numProbes = numProbes;
	}

	public void setTransitionArray(Transition[] transitions) {
		this.trans = transitions;
	}

	
	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	


}
