/**
 */
package edu.kit.ipd.descartes.qpme.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage
 * @generated
 */
public interface ModelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ModelFactory eINSTANCE = edu.kit.ipd.descartes.qpme.model.impl.ModelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Color</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Color</em>'.
	 * @generated
	 */
	Color createColor();

	/**
	 * Returns a new object of class '<em>Color References Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Color References Container</em>'.
	 * @generated
	 */
	ColorReferencesContainer createColorReferencesContainer();

	/**
	 * Returns a new object of class '<em>Color References Meta Attributes Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Color References Meta Attributes Container</em>'.
	 * @generated
	 */
	ColorReferencesMetaAttributesContainer createColorReferencesMetaAttributesContainer();

	/**
	 * Returns a new object of class '<em>Colors Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Colors Container</em>'.
	 * @generated
	 */
	ColorsContainer createColorsContainer();

	/**
	 * Returns a new object of class '<em>Immediate Transition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Immediate Transition</em>'.
	 * @generated
	 */
	ImmediateTransition createImmediateTransition();

	/**
	 * Returns a new object of class '<em>Incidence Function Connection</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Incidence Function Connection</em>'.
	 * @generated
	 */
	IncidenceFunctionConnection createIncidenceFunctionConnection();

	/**
	 * Returns a new object of class '<em>Incidence Function Connections Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Incidence Function Connections Container</em>'.
	 * @generated
	 */
	IncidenceFunctionConnectionsContainer createIncidenceFunctionConnectionsContainer();

	/**
	 * Returns a new object of class '<em>Location Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Location Attribute</em>'.
	 * @generated
	 */
	LocationAttribute createLocationAttribute();

	/**
	 * Returns a new object of class '<em>Mode</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Mode</em>'.
	 * @generated
	 */
	Mode createMode();

	/**
	 * Returns a new object of class '<em>Modes Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Modes Container</em>'.
	 * @generated
	 */
	ModesContainer createModesContainer();

	/**
	 * Returns a new object of class '<em>Net Meta Attributes Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Net Meta Attributes Container</em>'.
	 * @generated
	 */
	NetMetaAttributesContainer createNetMetaAttributesContainer();

	/**
	 * Returns a new object of class '<em>Ordinary Color Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Ordinary Color Reference</em>'.
	 * @generated
	 */
	OrdinaryColorReference createOrdinaryColorReference();

	/**
	 * Returns a new object of class '<em>Ordinary Place</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Ordinary Place</em>'.
	 * @generated
	 */
	OrdinaryPlace createOrdinaryPlace();

	/**
	 * Returns a new object of class '<em>Place Meta Attributes Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Place Meta Attributes Container</em>'.
	 * @generated
	 */
	PlaceMetaAttributesContainer createPlaceMetaAttributesContainer();

	/**
	 * Returns a new object of class '<em>Places Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Places Container</em>'.
	 * @generated
	 */
	PlacesContainer createPlacesContainer();

	/**
	 * Returns a new object of class '<em>Place Transition Connection</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Place Transition Connection</em>'.
	 * @generated
	 */
	PlaceTransitionConnection createPlaceTransitionConnection();

	/**
	 * Returns a new object of class '<em>Place Transition Connections Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Place Transition Connections Container</em>'.
	 * @generated
	 */
	PlaceTransitionConnectionsContainer createPlaceTransitionConnectionsContainer();

	/**
	 * Returns a new object of class '<em>Probe</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Probe</em>'.
	 * @generated
	 */
	Probe createProbe();

	/**
	 * Returns a new object of class '<em>Probe Color Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Probe Color Reference</em>'.
	 * @generated
	 */
	ProbeColorReference createProbeColorReference();

	/**
	 * Returns a new object of class '<em>Probe Meta Attributes Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Probe Meta Attributes Container</em>'.
	 * @generated
	 */
	ProbeMetaAttributesContainer createProbeMetaAttributesContainer();

	/**
	 * Returns a new object of class '<em>Probes Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Probes Container</em>'.
	 * @generated
	 */
	ProbesContainer createProbesContainer();

	/**
	 * Returns a new object of class '<em>Qpme Document</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Qpme Document</em>'.
	 * @generated
	 */
	QpmeDocument createQpmeDocument();

	/**
	 * Returns a new object of class '<em>Queue</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Queue</em>'.
	 * @generated
	 */
	Queue createQueue();

	/**
	 * Returns a new object of class '<em>Queueing Color Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Queueing Color Reference</em>'.
	 * @generated
	 */
	QueueingColorReference createQueueingColorReference();

	/**
	 * Returns a new object of class '<em>Queueing Petri Net</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Queueing Petri Net</em>'.
	 * @generated
	 */
	QueueingPetriNet createQueueingPetriNet();

	/**
	 * Returns a new object of class '<em>Queueing Place</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Queueing Place</em>'.
	 * @generated
	 */
	QueueingPlace createQueueingPlace();

	/**
	 * Returns a new object of class '<em>Queues Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Queues Container</em>'.
	 * @generated
	 */
	QueuesContainer createQueuesContainer();

	/**
	 * Returns a new object of class '<em>Simqpn Batch Means Color Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Simqpn Batch Means Color Configuration</em>'.
	 * @generated
	 */
	SimqpnBatchMeansColorConfiguration createSimqpnBatchMeansColorConfiguration();

	/**
	 * Returns a new object of class '<em>Simqpn Batch Means Queueing Color Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Simqpn Batch Means Queueing Color Configuration</em>'.
	 * @generated
	 */
	SimqpnBatchMeansQueueingColorConfiguration createSimqpnBatchMeansQueueingColorConfiguration();

	/**
	 * Returns a new object of class '<em>Simqpn Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Simqpn Configuration</em>'.
	 * @generated
	 */
	SimqpnConfiguration createSimqpnConfiguration();

	/**
	 * Returns a new object of class '<em>Simqpn Place Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Simqpn Place Configuration</em>'.
	 * @generated
	 */
	SimqpnPlaceConfiguration createSimqpnPlaceConfiguration();

	/**
	 * Returns a new object of class '<em>Simqpn Repl Del Color Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Simqpn Repl Del Color Configuration</em>'.
	 * @generated
	 */
	SimqpnReplDelColorConfiguration createSimqpnReplDelColorConfiguration();

	/**
	 * Returns a new object of class '<em>Simqpn Repl Del Queueing Color Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Simqpn Repl Del Queueing Color Configuration</em>'.
	 * @generated
	 */
	SimqpnReplDelQueueingColorConfiguration createSimqpnReplDelQueueingColorConfiguration();

	/**
	 * Returns a new object of class '<em>Simqpn Welch Color Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Simqpn Welch Color Configuration</em>'.
	 * @generated
	 */
	SimqpnWelchColorConfiguration createSimqpnWelchColorConfiguration();

	/**
	 * Returns a new object of class '<em>Simqpn Welch Queueing Color Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Simqpn Welch Queueing Color Configuration</em>'.
	 * @generated
	 */
	SimqpnWelchQueueingColorConfiguration createSimqpnWelchQueueingColorConfiguration();

	/**
	 * Returns a new object of class '<em>Subnet</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Subnet</em>'.
	 * @generated
	 */
	Subnet createSubnet();

	/**
	 * Returns a new object of class '<em>Subnet Color Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Subnet Color Reference</em>'.
	 * @generated
	 */
	SubnetColorReference createSubnetColorReference();

	/**
	 * Returns a new object of class '<em>Subnet Place</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Subnet Place</em>'.
	 * @generated
	 */
	SubnetPlace createSubnetPlace();

	/**
	 * Returns a new object of class '<em>Timed Transition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Timed Transition</em>'.
	 * @generated
	 */
	TimedTransition createTimedTransition();

	/**
	 * Returns a new object of class '<em>Transition Meta Attributes Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Transition Meta Attributes Container</em>'.
	 * @generated
	 */
	TransitionMetaAttributesContainer createTransitionMetaAttributesContainer();

	/**
	 * Returns a new object of class '<em>Transitions Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Transitions Container</em>'.
	 * @generated
	 */
	TransitionsContainer createTransitionsContainer();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ModelPackage getModelPackage();

} //ModelFactory
