/**
 */
package edu.kit.ipd.descartes.qpme.model.impl;

import edu.kit.ipd.descartes.qpme.model.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ModelFactoryImpl extends EFactoryImpl implements ModelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ModelFactory init() {
		try {
			ModelFactory theModelFactory = (ModelFactory)EPackage.Registry.INSTANCE.getEFactory(ModelPackage.eNS_URI);
			if (theModelFactory != null) {
				return theModelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ModelPackage.COLOR: return createColor();
			case ModelPackage.COLOR_REFERENCES_CONTAINER: return createColorReferencesContainer();
			case ModelPackage.COLOR_REFERENCES_META_ATTRIBUTES_CONTAINER: return createColorReferencesMetaAttributesContainer();
			case ModelPackage.COLORS_CONTAINER: return createColorsContainer();
			case ModelPackage.IMMEDIATE_TRANSITION: return createImmediateTransition();
			case ModelPackage.INCIDENCE_FUNCTION_CONNECTION: return createIncidenceFunctionConnection();
			case ModelPackage.INCIDENCE_FUNCTION_CONNECTIONS_CONTAINER: return createIncidenceFunctionConnectionsContainer();
			case ModelPackage.LOCATION_ATTRIBUTE: return createLocationAttribute();
			case ModelPackage.MODE: return createMode();
			case ModelPackage.MODES_CONTAINER: return createModesContainer();
			case ModelPackage.NET_META_ATTRIBUTES_CONTAINER: return createNetMetaAttributesContainer();
			case ModelPackage.ORDINARY_COLOR_REFERENCE: return createOrdinaryColorReference();
			case ModelPackage.ORDINARY_PLACE: return createOrdinaryPlace();
			case ModelPackage.PLACE_META_ATTRIBUTES_CONTAINER: return createPlaceMetaAttributesContainer();
			case ModelPackage.PLACES_CONTAINER: return createPlacesContainer();
			case ModelPackage.PLACE_TRANSITION_CONNECTION: return createPlaceTransitionConnection();
			case ModelPackage.PLACE_TRANSITION_CONNECTIONS_CONTAINER: return createPlaceTransitionConnectionsContainer();
			case ModelPackage.PROBE: return createProbe();
			case ModelPackage.PROBE_COLOR_REFERENCE: return createProbeColorReference();
			case ModelPackage.PROBE_META_ATTRIBUTES_CONTAINER: return createProbeMetaAttributesContainer();
			case ModelPackage.PROBES_CONTAINER: return createProbesContainer();
			case ModelPackage.QPME_DOCUMENT: return createQpmeDocument();
			case ModelPackage.QUEUE: return createQueue();
			case ModelPackage.QUEUEING_COLOR_REFERENCE: return createQueueingColorReference();
			case ModelPackage.QUEUEING_PETRI_NET: return createQueueingPetriNet();
			case ModelPackage.QUEUEING_PLACE: return createQueueingPlace();
			case ModelPackage.QUEUES_CONTAINER: return createQueuesContainer();
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION: return createSimqpnBatchMeansColorConfiguration();
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION: return createSimqpnBatchMeansQueueingColorConfiguration();
			case ModelPackage.SIMQPN_CONFIGURATION: return createSimqpnConfiguration();
			case ModelPackage.SIMQPN_PLACE_CONFIGURATION: return createSimqpnPlaceConfiguration();
			case ModelPackage.SIMQPN_REPL_DEL_COLOR_CONFIGURATION: return createSimqpnReplDelColorConfiguration();
			case ModelPackage.SIMQPN_REPL_DEL_QUEUEING_COLOR_CONFIGURATION: return createSimqpnReplDelQueueingColorConfiguration();
			case ModelPackage.SIMQPN_WELCH_COLOR_CONFIGURATION: return createSimqpnWelchColorConfiguration();
			case ModelPackage.SIMQPN_WELCH_QUEUEING_COLOR_CONFIGURATION: return createSimqpnWelchQueueingColorConfiguration();
			case ModelPackage.SUBNET: return createSubnet();
			case ModelPackage.SUBNET_COLOR_REFERENCE: return createSubnetColorReference();
			case ModelPackage.SUBNET_PLACE: return createSubnetPlace();
			case ModelPackage.TIMED_TRANSITION: return createTimedTransition();
			case ModelPackage.TRANSITION_META_ATTRIBUTES_CONTAINER: return createTransitionMetaAttributesContainer();
			case ModelPackage.TRANSITIONS_CONTAINER: return createTransitionsContainer();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case ModelPackage.DEPARTURE_DISCIPLINE:
				return createDepartureDisciplineFromString(eDataType, initialValue);
			case ModelPackage.DISTRIBUTION_FUNCTION:
				return createDistributionFunctionFromString(eDataType, initialValue);
			case ModelPackage.FLOW_DIRECTION:
				return createFlowDirectionFromString(eDataType, initialValue);
			case ModelPackage.PROBE_TRIGGER:
				return createProbeTriggerFromString(eDataType, initialValue);
			case ModelPackage.QUEUEING_STRATEGY:
				return createQueueingStrategyFromString(eDataType, initialValue);
			case ModelPackage.SIMQPN_SIMULATION_SCENARIO:
				return createSimqpnSimulationScenarioFromString(eDataType, initialValue);
			case ModelPackage.SIMQPN_STOPPING_RULE:
				return createSimqpnStoppingRuleFromString(eDataType, initialValue);
			case ModelPackage.DEPARTURE_DISCIPLINE_OBJECT:
				return createDepartureDisciplineObjectFromString(eDataType, initialValue);
			case ModelPackage.DISTRIBUTION_FUNCTION_OBJECT:
				return createDistributionFunctionObjectFromString(eDataType, initialValue);
			case ModelPackage.FLOW_DIRECTION_OBJECT:
				return createFlowDirectionObjectFromString(eDataType, initialValue);
			case ModelPackage.PROBE_TRIGGER_OBJECT:
				return createProbeTriggerObjectFromString(eDataType, initialValue);
			case ModelPackage.QUEUEING_STRATEGY_OBJECT:
				return createQueueingStrategyObjectFromString(eDataType, initialValue);
			case ModelPackage.RGB_VALUE:
				return createRgbValueFromString(eDataType, initialValue);
			case ModelPackage.SIMQPN_SIMULATION_SCENARIO_OBJECT:
				return createSimqpnSimulationScenarioObjectFromString(eDataType, initialValue);
			case ModelPackage.SIMQPN_STOPPING_RULE_OBJECT:
				return createSimqpnStoppingRuleObjectFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case ModelPackage.DEPARTURE_DISCIPLINE:
				return convertDepartureDisciplineToString(eDataType, instanceValue);
			case ModelPackage.DISTRIBUTION_FUNCTION:
				return convertDistributionFunctionToString(eDataType, instanceValue);
			case ModelPackage.FLOW_DIRECTION:
				return convertFlowDirectionToString(eDataType, instanceValue);
			case ModelPackage.PROBE_TRIGGER:
				return convertProbeTriggerToString(eDataType, instanceValue);
			case ModelPackage.QUEUEING_STRATEGY:
				return convertQueueingStrategyToString(eDataType, instanceValue);
			case ModelPackage.SIMQPN_SIMULATION_SCENARIO:
				return convertSimqpnSimulationScenarioToString(eDataType, instanceValue);
			case ModelPackage.SIMQPN_STOPPING_RULE:
				return convertSimqpnStoppingRuleToString(eDataType, instanceValue);
			case ModelPackage.DEPARTURE_DISCIPLINE_OBJECT:
				return convertDepartureDisciplineObjectToString(eDataType, instanceValue);
			case ModelPackage.DISTRIBUTION_FUNCTION_OBJECT:
				return convertDistributionFunctionObjectToString(eDataType, instanceValue);
			case ModelPackage.FLOW_DIRECTION_OBJECT:
				return convertFlowDirectionObjectToString(eDataType, instanceValue);
			case ModelPackage.PROBE_TRIGGER_OBJECT:
				return convertProbeTriggerObjectToString(eDataType, instanceValue);
			case ModelPackage.QUEUEING_STRATEGY_OBJECT:
				return convertQueueingStrategyObjectToString(eDataType, instanceValue);
			case ModelPackage.RGB_VALUE:
				return convertRgbValueToString(eDataType, instanceValue);
			case ModelPackage.SIMQPN_SIMULATION_SCENARIO_OBJECT:
				return convertSimqpnSimulationScenarioObjectToString(eDataType, instanceValue);
			case ModelPackage.SIMQPN_STOPPING_RULE_OBJECT:
				return convertSimqpnStoppingRuleObjectToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Color createColor() {
		ColorImpl color = new ColorImpl();
		return color;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ColorReferencesContainer createColorReferencesContainer() {
		ColorReferencesContainerImpl colorReferencesContainer = new ColorReferencesContainerImpl();
		return colorReferencesContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ColorReferencesMetaAttributesContainer createColorReferencesMetaAttributesContainer() {
		ColorReferencesMetaAttributesContainerImpl colorReferencesMetaAttributesContainer = new ColorReferencesMetaAttributesContainerImpl();
		return colorReferencesMetaAttributesContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ColorsContainer createColorsContainer() {
		ColorsContainerImpl colorsContainer = new ColorsContainerImpl();
		return colorsContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImmediateTransition createImmediateTransition() {
		ImmediateTransitionImpl immediateTransition = new ImmediateTransitionImpl();
		return immediateTransition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IncidenceFunctionConnection createIncidenceFunctionConnection() {
		IncidenceFunctionConnectionImpl incidenceFunctionConnection = new IncidenceFunctionConnectionImpl();
		return incidenceFunctionConnection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IncidenceFunctionConnectionsContainer createIncidenceFunctionConnectionsContainer() {
		IncidenceFunctionConnectionsContainerImpl incidenceFunctionConnectionsContainer = new IncidenceFunctionConnectionsContainerImpl();
		return incidenceFunctionConnectionsContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocationAttribute createLocationAttribute() {
		LocationAttributeImpl locationAttribute = new LocationAttributeImpl();
		return locationAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Mode createMode() {
		ModeImpl mode = new ModeImpl();
		return mode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModesContainer createModesContainer() {
		ModesContainerImpl modesContainer = new ModesContainerImpl();
		return modesContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NetMetaAttributesContainer createNetMetaAttributesContainer() {
		NetMetaAttributesContainerImpl netMetaAttributesContainer = new NetMetaAttributesContainerImpl();
		return netMetaAttributesContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OrdinaryColorReference createOrdinaryColorReference() {
		OrdinaryColorReferenceImpl ordinaryColorReference = new OrdinaryColorReferenceImpl();
		return ordinaryColorReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OrdinaryPlace createOrdinaryPlace() {
		OrdinaryPlaceImpl ordinaryPlace = new OrdinaryPlaceImpl();
		return ordinaryPlace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlaceMetaAttributesContainer createPlaceMetaAttributesContainer() {
		PlaceMetaAttributesContainerImpl placeMetaAttributesContainer = new PlaceMetaAttributesContainerImpl();
		return placeMetaAttributesContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlacesContainer createPlacesContainer() {
		PlacesContainerImpl placesContainer = new PlacesContainerImpl();
		return placesContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlaceTransitionConnection createPlaceTransitionConnection() {
		PlaceTransitionConnectionImpl placeTransitionConnection = new PlaceTransitionConnectionImpl();
		return placeTransitionConnection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlaceTransitionConnectionsContainer createPlaceTransitionConnectionsContainer() {
		PlaceTransitionConnectionsContainerImpl placeTransitionConnectionsContainer = new PlaceTransitionConnectionsContainerImpl();
		return placeTransitionConnectionsContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Probe createProbe() {
		ProbeImpl probe = new ProbeImpl();
		return probe;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProbeColorReference createProbeColorReference() {
		ProbeColorReferenceImpl probeColorReference = new ProbeColorReferenceImpl();
		return probeColorReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProbeMetaAttributesContainer createProbeMetaAttributesContainer() {
		ProbeMetaAttributesContainerImpl probeMetaAttributesContainer = new ProbeMetaAttributesContainerImpl();
		return probeMetaAttributesContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProbesContainer createProbesContainer() {
		ProbesContainerImpl probesContainer = new ProbesContainerImpl();
		return probesContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public QpmeDocument createQpmeDocument() {
		QpmeDocumentImpl qpmeDocument = new QpmeDocumentImpl();
		return qpmeDocument;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Queue createQueue() {
		QueueImpl queue = new QueueImpl();
		return queue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public QueueingColorReference createQueueingColorReference() {
		QueueingColorReferenceImpl queueingColorReference = new QueueingColorReferenceImpl();
		return queueingColorReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public QueueingPetriNet createQueueingPetriNet() {
		QueueingPetriNetImpl queueingPetriNet = new QueueingPetriNetImpl();
		return queueingPetriNet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public QueueingPlace createQueueingPlace() {
		QueueingPlaceImpl queueingPlace = new QueueingPlaceImpl();
		return queueingPlace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public QueuesContainer createQueuesContainer() {
		QueuesContainerImpl queuesContainer = new QueuesContainerImpl();
		return queuesContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimqpnBatchMeansColorConfiguration createSimqpnBatchMeansColorConfiguration() {
		SimqpnBatchMeansColorConfigurationImpl simqpnBatchMeansColorConfiguration = new SimqpnBatchMeansColorConfigurationImpl();
		return simqpnBatchMeansColorConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimqpnBatchMeansQueueingColorConfiguration createSimqpnBatchMeansQueueingColorConfiguration() {
		SimqpnBatchMeansQueueingColorConfigurationImpl simqpnBatchMeansQueueingColorConfiguration = new SimqpnBatchMeansQueueingColorConfigurationImpl();
		return simqpnBatchMeansQueueingColorConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimqpnConfiguration createSimqpnConfiguration() {
		SimqpnConfigurationImpl simqpnConfiguration = new SimqpnConfigurationImpl();
		return simqpnConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimqpnPlaceConfiguration createSimqpnPlaceConfiguration() {
		SimqpnPlaceConfigurationImpl simqpnPlaceConfiguration = new SimqpnPlaceConfigurationImpl();
		return simqpnPlaceConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimqpnReplDelColorConfiguration createSimqpnReplDelColorConfiguration() {
		SimqpnReplDelColorConfigurationImpl simqpnReplDelColorConfiguration = new SimqpnReplDelColorConfigurationImpl();
		return simqpnReplDelColorConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimqpnReplDelQueueingColorConfiguration createSimqpnReplDelQueueingColorConfiguration() {
		SimqpnReplDelQueueingColorConfigurationImpl simqpnReplDelQueueingColorConfiguration = new SimqpnReplDelQueueingColorConfigurationImpl();
		return simqpnReplDelQueueingColorConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimqpnWelchColorConfiguration createSimqpnWelchColorConfiguration() {
		SimqpnWelchColorConfigurationImpl simqpnWelchColorConfiguration = new SimqpnWelchColorConfigurationImpl();
		return simqpnWelchColorConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimqpnWelchQueueingColorConfiguration createSimqpnWelchQueueingColorConfiguration() {
		SimqpnWelchQueueingColorConfigurationImpl simqpnWelchQueueingColorConfiguration = new SimqpnWelchQueueingColorConfigurationImpl();
		return simqpnWelchQueueingColorConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Subnet createSubnet() {
		SubnetImpl subnet = new SubnetImpl();
		return subnet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SubnetColorReference createSubnetColorReference() {
		SubnetColorReferenceImpl subnetColorReference = new SubnetColorReferenceImpl();
		return subnetColorReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SubnetPlace createSubnetPlace() {
		SubnetPlaceImpl subnetPlace = new SubnetPlaceImpl();
		return subnetPlace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TimedTransition createTimedTransition() {
		TimedTransitionImpl timedTransition = new TimedTransitionImpl();
		return timedTransition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransitionMetaAttributesContainer createTransitionMetaAttributesContainer() {
		TransitionMetaAttributesContainerImpl transitionMetaAttributesContainer = new TransitionMetaAttributesContainerImpl();
		return transitionMetaAttributesContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransitionsContainer createTransitionsContainer() {
		TransitionsContainerImpl transitionsContainer = new TransitionsContainerImpl();
		return transitionsContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DepartureDiscipline createDepartureDisciplineFromString(EDataType eDataType, String initialValue) {
		DepartureDiscipline result = DepartureDiscipline.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDepartureDisciplineToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DistributionFunction createDistributionFunctionFromString(EDataType eDataType, String initialValue) {
		DistributionFunction result = DistributionFunction.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDistributionFunctionToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FlowDirection createFlowDirectionFromString(EDataType eDataType, String initialValue) {
		FlowDirection result = FlowDirection.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertFlowDirectionToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProbeTrigger createProbeTriggerFromString(EDataType eDataType, String initialValue) {
		ProbeTrigger result = ProbeTrigger.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertProbeTriggerToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public QueueingStrategy createQueueingStrategyFromString(EDataType eDataType, String initialValue) {
		QueueingStrategy result = QueueingStrategy.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertQueueingStrategyToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimqpnSimulationScenario createSimqpnSimulationScenarioFromString(EDataType eDataType, String initialValue) {
		SimqpnSimulationScenario result = SimqpnSimulationScenario.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSimqpnSimulationScenarioToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimqpnStoppingRule createSimqpnStoppingRuleFromString(EDataType eDataType, String initialValue) {
		SimqpnStoppingRule result = SimqpnStoppingRule.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSimqpnStoppingRuleToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DepartureDiscipline createDepartureDisciplineObjectFromString(EDataType eDataType, String initialValue) {
		return createDepartureDisciplineFromString(ModelPackage.Literals.DEPARTURE_DISCIPLINE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDepartureDisciplineObjectToString(EDataType eDataType, Object instanceValue) {
		return convertDepartureDisciplineToString(ModelPackage.Literals.DEPARTURE_DISCIPLINE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DistributionFunction createDistributionFunctionObjectFromString(EDataType eDataType, String initialValue) {
		return createDistributionFunctionFromString(ModelPackage.Literals.DISTRIBUTION_FUNCTION, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDistributionFunctionObjectToString(EDataType eDataType, Object instanceValue) {
		return convertDistributionFunctionToString(ModelPackage.Literals.DISTRIBUTION_FUNCTION, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FlowDirection createFlowDirectionObjectFromString(EDataType eDataType, String initialValue) {
		return createFlowDirectionFromString(ModelPackage.Literals.FLOW_DIRECTION, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertFlowDirectionObjectToString(EDataType eDataType, Object instanceValue) {
		return convertFlowDirectionToString(ModelPackage.Literals.FLOW_DIRECTION, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProbeTrigger createProbeTriggerObjectFromString(EDataType eDataType, String initialValue) {
		return createProbeTriggerFromString(ModelPackage.Literals.PROBE_TRIGGER, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertProbeTriggerObjectToString(EDataType eDataType, Object instanceValue) {
		return convertProbeTriggerToString(ModelPackage.Literals.PROBE_TRIGGER, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public QueueingStrategy createQueueingStrategyObjectFromString(EDataType eDataType, String initialValue) {
		return createQueueingStrategyFromString(ModelPackage.Literals.QUEUEING_STRATEGY, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertQueueingStrategyObjectToString(EDataType eDataType, Object instanceValue) {
		return convertQueueingStrategyToString(ModelPackage.Literals.QUEUEING_STRATEGY, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createRgbValueFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRgbValueToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimqpnSimulationScenario createSimqpnSimulationScenarioObjectFromString(EDataType eDataType, String initialValue) {
		return createSimqpnSimulationScenarioFromString(ModelPackage.Literals.SIMQPN_SIMULATION_SCENARIO, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSimqpnSimulationScenarioObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSimqpnSimulationScenarioToString(ModelPackage.Literals.SIMQPN_SIMULATION_SCENARIO, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimqpnStoppingRule createSimqpnStoppingRuleObjectFromString(EDataType eDataType, String initialValue) {
		return createSimqpnStoppingRuleFromString(ModelPackage.Literals.SIMQPN_STOPPING_RULE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSimqpnStoppingRuleObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSimqpnStoppingRuleToString(ModelPackage.Literals.SIMQPN_STOPPING_RULE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelPackage getModelPackage() {
		return (ModelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ModelPackage getPackage() {
		return ModelPackage.eINSTANCE;
	}

} //ModelFactoryImpl
