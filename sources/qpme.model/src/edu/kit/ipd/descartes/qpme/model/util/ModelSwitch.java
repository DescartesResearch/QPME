/**
 */
package edu.kit.ipd.descartes.qpme.model.util;

import edu.kit.ipd.descartes.qpme.model.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage
 * @generated
 */
public class ModelSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ModelPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelSwitch() {
		if (modelPackage == null) {
			modelPackage = ModelPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case ModelPackage.COLOR: {
				Color color = (Color)theEObject;
				T result = caseColor(color);
				if (result == null) result = caseIdentifiableElement(color);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.COLOR_REFERENCE: {
				ColorReference colorReference = (ColorReference)theEObject;
				T result = caseColorReference(colorReference);
				if (result == null) result = caseIncidenceFunctionElement(colorReference);
				if (result == null) result = caseIdentifiableElement(colorReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.COLOR_REFERENCE_META_ATTRIBUTE: {
				ColorReferenceMetaAttribute colorReferenceMetaAttribute = (ColorReferenceMetaAttribute)theEObject;
				T result = caseColorReferenceMetaAttribute(colorReferenceMetaAttribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.COLOR_REFERENCES_CONTAINER: {
				ColorReferencesContainer colorReferencesContainer = (ColorReferencesContainer)theEObject;
				T result = caseColorReferencesContainer(colorReferencesContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.COLOR_REFERENCES_META_ATTRIBUTES_CONTAINER: {
				ColorReferencesMetaAttributesContainer colorReferencesMetaAttributesContainer = (ColorReferencesMetaAttributesContainer)theEObject;
				T result = caseColorReferencesMetaAttributesContainer(colorReferencesMetaAttributesContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.COLORS_CONTAINER: {
				ColorsContainer colorsContainer = (ColorsContainer)theEObject;
				T result = caseColorsContainer(colorsContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.IDENTIFIABLE_ELEMENT: {
				IdentifiableElement identifiableElement = (IdentifiableElement)theEObject;
				T result = caseIdentifiableElement(identifiableElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.IMMEDIATE_TRANSITION: {
				ImmediateTransition immediateTransition = (ImmediateTransition)theEObject;
				T result = caseImmediateTransition(immediateTransition);
				if (result == null) result = caseTransition(immediateTransition);
				if (result == null) result = casePlaceTransitionElement(immediateTransition);
				if (result == null) result = caseIdentifiableElement(immediateTransition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.INCIDENCE_FUNCTION_CONNECTION: {
				IncidenceFunctionConnection incidenceFunctionConnection = (IncidenceFunctionConnection)theEObject;
				T result = caseIncidenceFunctionConnection(incidenceFunctionConnection);
				if (result == null) result = caseIdentifiableElement(incidenceFunctionConnection);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.INCIDENCE_FUNCTION_CONNECTIONS_CONTAINER: {
				IncidenceFunctionConnectionsContainer incidenceFunctionConnectionsContainer = (IncidenceFunctionConnectionsContainer)theEObject;
				T result = caseIncidenceFunctionConnectionsContainer(incidenceFunctionConnectionsContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.INCIDENCE_FUNCTION_ELEMENT: {
				IncidenceFunctionElement incidenceFunctionElement = (IncidenceFunctionElement)theEObject;
				T result = caseIncidenceFunctionElement(incidenceFunctionElement);
				if (result == null) result = caseIdentifiableElement(incidenceFunctionElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.LOCATION_ATTRIBUTE: {
				LocationAttribute locationAttribute = (LocationAttribute)theEObject;
				T result = caseLocationAttribute(locationAttribute);
				if (result == null) result = casePlaceMetaAttribute(locationAttribute);
				if (result == null) result = caseTransitionMetaAttribute(locationAttribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.MODE: {
				Mode mode = (Mode)theEObject;
				T result = caseMode(mode);
				if (result == null) result = caseIncidenceFunctionElement(mode);
				if (result == null) result = caseIdentifiableElement(mode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.MODES_CONTAINER: {
				ModesContainer modesContainer = (ModesContainer)theEObject;
				T result = caseModesContainer(modesContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.NET_META_ATTRIBUTE: {
				NetMetaAttribute netMetaAttribute = (NetMetaAttribute)theEObject;
				T result = caseNetMetaAttribute(netMetaAttribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.NET_META_ATTRIBUTES_CONTAINER: {
				NetMetaAttributesContainer netMetaAttributesContainer = (NetMetaAttributesContainer)theEObject;
				T result = caseNetMetaAttributesContainer(netMetaAttributesContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.ORDINARY_COLOR_REFERENCE: {
				OrdinaryColorReference ordinaryColorReference = (OrdinaryColorReference)theEObject;
				T result = caseOrdinaryColorReference(ordinaryColorReference);
				if (result == null) result = casePlaceColorReference(ordinaryColorReference);
				if (result == null) result = caseColorReference(ordinaryColorReference);
				if (result == null) result = caseIncidenceFunctionElement(ordinaryColorReference);
				if (result == null) result = caseIdentifiableElement(ordinaryColorReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.ORDINARY_PLACE: {
				OrdinaryPlace ordinaryPlace = (OrdinaryPlace)theEObject;
				T result = caseOrdinaryPlace(ordinaryPlace);
				if (result == null) result = casePlace(ordinaryPlace);
				if (result == null) result = casePlaceTransitionElement(ordinaryPlace);
				if (result == null) result = caseIdentifiableElement(ordinaryPlace);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.PLACE: {
				Place place = (Place)theEObject;
				T result = casePlace(place);
				if (result == null) result = casePlaceTransitionElement(place);
				if (result == null) result = caseIdentifiableElement(place);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.PLACE_COLOR_REFERENCE: {
				PlaceColorReference placeColorReference = (PlaceColorReference)theEObject;
				T result = casePlaceColorReference(placeColorReference);
				if (result == null) result = caseColorReference(placeColorReference);
				if (result == null) result = caseIncidenceFunctionElement(placeColorReference);
				if (result == null) result = caseIdentifiableElement(placeColorReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.PLACE_META_ATTRIBUTE: {
				PlaceMetaAttribute placeMetaAttribute = (PlaceMetaAttribute)theEObject;
				T result = casePlaceMetaAttribute(placeMetaAttribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.PLACE_META_ATTRIBUTES_CONTAINER: {
				PlaceMetaAttributesContainer placeMetaAttributesContainer = (PlaceMetaAttributesContainer)theEObject;
				T result = casePlaceMetaAttributesContainer(placeMetaAttributesContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.PLACES_CONTAINER: {
				PlacesContainer placesContainer = (PlacesContainer)theEObject;
				T result = casePlacesContainer(placesContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.PLACE_TRANSITION_CONNECTION: {
				PlaceTransitionConnection placeTransitionConnection = (PlaceTransitionConnection)theEObject;
				T result = casePlaceTransitionConnection(placeTransitionConnection);
				if (result == null) result = caseIdentifiableElement(placeTransitionConnection);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.PLACE_TRANSITION_CONNECTIONS_CONTAINER: {
				PlaceTransitionConnectionsContainer placeTransitionConnectionsContainer = (PlaceTransitionConnectionsContainer)theEObject;
				T result = casePlaceTransitionConnectionsContainer(placeTransitionConnectionsContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.PLACE_TRANSITION_ELEMENT: {
				PlaceTransitionElement placeTransitionElement = (PlaceTransitionElement)theEObject;
				T result = casePlaceTransitionElement(placeTransitionElement);
				if (result == null) result = caseIdentifiableElement(placeTransitionElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.PROBE: {
				Probe probe = (Probe)theEObject;
				T result = caseProbe(probe);
				if (result == null) result = caseIdentifiableElement(probe);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.PROBE_COLOR_REFERENCE: {
				ProbeColorReference probeColorReference = (ProbeColorReference)theEObject;
				T result = caseProbeColorReference(probeColorReference);
				if (result == null) result = caseColorReference(probeColorReference);
				if (result == null) result = caseIncidenceFunctionElement(probeColorReference);
				if (result == null) result = caseIdentifiableElement(probeColorReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.PROBE_META_ATTRIBUTE: {
				ProbeMetaAttribute probeMetaAttribute = (ProbeMetaAttribute)theEObject;
				T result = caseProbeMetaAttribute(probeMetaAttribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.PROBE_META_ATTRIBUTES_CONTAINER: {
				ProbeMetaAttributesContainer probeMetaAttributesContainer = (ProbeMetaAttributesContainer)theEObject;
				T result = caseProbeMetaAttributesContainer(probeMetaAttributesContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.PROBES_CONTAINER: {
				ProbesContainer probesContainer = (ProbesContainer)theEObject;
				T result = caseProbesContainer(probesContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.QPME_DOCUMENT: {
				QpmeDocument qpmeDocument = (QpmeDocument)theEObject;
				T result = caseQpmeDocument(qpmeDocument);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.QUEUE: {
				Queue queue = (Queue)theEObject;
				T result = caseQueue(queue);
				if (result == null) result = caseIdentifiableElement(queue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.QUEUEING_COLOR_REFERENCE: {
				QueueingColorReference queueingColorReference = (QueueingColorReference)theEObject;
				T result = caseQueueingColorReference(queueingColorReference);
				if (result == null) result = casePlaceColorReference(queueingColorReference);
				if (result == null) result = caseColorReference(queueingColorReference);
				if (result == null) result = caseIncidenceFunctionElement(queueingColorReference);
				if (result == null) result = caseIdentifiableElement(queueingColorReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.QUEUEING_PETRI_NET: {
				QueueingPetriNet queueingPetriNet = (QueueingPetriNet)theEObject;
				T result = caseQueueingPetriNet(queueingPetriNet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.QUEUEING_PLACE: {
				QueueingPlace queueingPlace = (QueueingPlace)theEObject;
				T result = caseQueueingPlace(queueingPlace);
				if (result == null) result = casePlace(queueingPlace);
				if (result == null) result = casePlaceTransitionElement(queueingPlace);
				if (result == null) result = caseIdentifiableElement(queueingPlace);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.QUEUES_CONTAINER: {
				QueuesContainer queuesContainer = (QueuesContainer)theEObject;
				T result = caseQueuesContainer(queuesContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION: {
				SimqpnBatchMeansColorConfiguration simqpnBatchMeansColorConfiguration = (SimqpnBatchMeansColorConfiguration)theEObject;
				T result = caseSimqpnBatchMeansColorConfiguration(simqpnBatchMeansColorConfiguration);
				if (result == null) result = caseSimqpnMetaAttribute(simqpnBatchMeansColorConfiguration);
				if (result == null) result = caseColorReferenceMetaAttribute(simqpnBatchMeansColorConfiguration);
				if (result == null) result = caseIdentifiableElement(simqpnBatchMeansColorConfiguration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION: {
				SimqpnBatchMeansQueueingColorConfiguration simqpnBatchMeansQueueingColorConfiguration = (SimqpnBatchMeansQueueingColorConfiguration)theEObject;
				T result = caseSimqpnBatchMeansQueueingColorConfiguration(simqpnBatchMeansQueueingColorConfiguration);
				if (result == null) result = caseSimqpnBatchMeansColorConfiguration(simqpnBatchMeansQueueingColorConfiguration);
				if (result == null) result = caseSimqpnMetaAttribute(simqpnBatchMeansQueueingColorConfiguration);
				if (result == null) result = caseColorReferenceMetaAttribute(simqpnBatchMeansQueueingColorConfiguration);
				if (result == null) result = caseIdentifiableElement(simqpnBatchMeansQueueingColorConfiguration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.SIMQPN_CONFIGURATION: {
				SimqpnConfiguration simqpnConfiguration = (SimqpnConfiguration)theEObject;
				T result = caseSimqpnConfiguration(simqpnConfiguration);
				if (result == null) result = caseSimqpnMetaAttribute(simqpnConfiguration);
				if (result == null) result = caseNetMetaAttribute(simqpnConfiguration);
				if (result == null) result = caseIdentifiableElement(simqpnConfiguration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.SIMQPN_META_ATTRIBUTE: {
				SimqpnMetaAttribute simqpnMetaAttribute = (SimqpnMetaAttribute)theEObject;
				T result = caseSimqpnMetaAttribute(simqpnMetaAttribute);
				if (result == null) result = caseIdentifiableElement(simqpnMetaAttribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.SIMQPN_PLACE_CONFIGURATION: {
				SimqpnPlaceConfiguration simqpnPlaceConfiguration = (SimqpnPlaceConfiguration)theEObject;
				T result = caseSimqpnPlaceConfiguration(simqpnPlaceConfiguration);
				if (result == null) result = caseSimqpnMetaAttribute(simqpnPlaceConfiguration);
				if (result == null) result = casePlaceMetaAttribute(simqpnPlaceConfiguration);
				if (result == null) result = caseProbeMetaAttribute(simqpnPlaceConfiguration);
				if (result == null) result = caseIdentifiableElement(simqpnPlaceConfiguration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.SIMQPN_REPL_DEL_COLOR_CONFIGURATION: {
				SimqpnReplDelColorConfiguration simqpnReplDelColorConfiguration = (SimqpnReplDelColorConfiguration)theEObject;
				T result = caseSimqpnReplDelColorConfiguration(simqpnReplDelColorConfiguration);
				if (result == null) result = caseSimqpnMetaAttribute(simqpnReplDelColorConfiguration);
				if (result == null) result = caseColorReferenceMetaAttribute(simqpnReplDelColorConfiguration);
				if (result == null) result = caseIdentifiableElement(simqpnReplDelColorConfiguration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.SIMQPN_REPL_DEL_QUEUEING_COLOR_CONFIGURATION: {
				SimqpnReplDelQueueingColorConfiguration simqpnReplDelQueueingColorConfiguration = (SimqpnReplDelQueueingColorConfiguration)theEObject;
				T result = caseSimqpnReplDelQueueingColorConfiguration(simqpnReplDelQueueingColorConfiguration);
				if (result == null) result = caseSimqpnReplDelColorConfiguration(simqpnReplDelQueueingColorConfiguration);
				if (result == null) result = caseSimqpnMetaAttribute(simqpnReplDelQueueingColorConfiguration);
				if (result == null) result = caseColorReferenceMetaAttribute(simqpnReplDelQueueingColorConfiguration);
				if (result == null) result = caseIdentifiableElement(simqpnReplDelQueueingColorConfiguration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.SIMQPN_WELCH_COLOR_CONFIGURATION: {
				SimqpnWelchColorConfiguration simqpnWelchColorConfiguration = (SimqpnWelchColorConfiguration)theEObject;
				T result = caseSimqpnWelchColorConfiguration(simqpnWelchColorConfiguration);
				if (result == null) result = caseSimqpnMetaAttribute(simqpnWelchColorConfiguration);
				if (result == null) result = caseColorReferenceMetaAttribute(simqpnWelchColorConfiguration);
				if (result == null) result = caseIdentifiableElement(simqpnWelchColorConfiguration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.SIMQPN_WELCH_QUEUEING_COLOR_CONFIGURATION: {
				SimqpnWelchQueueingColorConfiguration simqpnWelchQueueingColorConfiguration = (SimqpnWelchQueueingColorConfiguration)theEObject;
				T result = caseSimqpnWelchQueueingColorConfiguration(simqpnWelchQueueingColorConfiguration);
				if (result == null) result = caseSimqpnWelchColorConfiguration(simqpnWelchQueueingColorConfiguration);
				if (result == null) result = caseSimqpnMetaAttribute(simqpnWelchQueueingColorConfiguration);
				if (result == null) result = caseColorReferenceMetaAttribute(simqpnWelchQueueingColorConfiguration);
				if (result == null) result = caseIdentifiableElement(simqpnWelchQueueingColorConfiguration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.SUBNET: {
				Subnet subnet = (Subnet)theEObject;
				T result = caseSubnet(subnet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.SUBNET_COLOR_REFERENCE: {
				SubnetColorReference subnetColorReference = (SubnetColorReference)theEObject;
				T result = caseSubnetColorReference(subnetColorReference);
				if (result == null) result = casePlaceColorReference(subnetColorReference);
				if (result == null) result = caseColorReference(subnetColorReference);
				if (result == null) result = caseIncidenceFunctionElement(subnetColorReference);
				if (result == null) result = caseIdentifiableElement(subnetColorReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.SUBNET_PLACE: {
				SubnetPlace subnetPlace = (SubnetPlace)theEObject;
				T result = caseSubnetPlace(subnetPlace);
				if (result == null) result = casePlace(subnetPlace);
				if (result == null) result = casePlaceTransitionElement(subnetPlace);
				if (result == null) result = caseIdentifiableElement(subnetPlace);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.TIMED_TRANSITION: {
				TimedTransition timedTransition = (TimedTransition)theEObject;
				T result = caseTimedTransition(timedTransition);
				if (result == null) result = caseTransition(timedTransition);
				if (result == null) result = casePlaceTransitionElement(timedTransition);
				if (result == null) result = caseIdentifiableElement(timedTransition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.TRANSITION: {
				Transition transition = (Transition)theEObject;
				T result = caseTransition(transition);
				if (result == null) result = casePlaceTransitionElement(transition);
				if (result == null) result = caseIdentifiableElement(transition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.TRANSITION_META_ATTRIBUTE: {
				TransitionMetaAttribute transitionMetaAttribute = (TransitionMetaAttribute)theEObject;
				T result = caseTransitionMetaAttribute(transitionMetaAttribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.TRANSITION_META_ATTRIBUTES_CONTAINER: {
				TransitionMetaAttributesContainer transitionMetaAttributesContainer = (TransitionMetaAttributesContainer)theEObject;
				T result = caseTransitionMetaAttributesContainer(transitionMetaAttributesContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ModelPackage.TRANSITIONS_CONTAINER: {
				TransitionsContainer transitionsContainer = (TransitionsContainer)theEObject;
				T result = caseTransitionsContainer(transitionsContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Color</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Color</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseColor(Color object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Color Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Color Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseColorReference(ColorReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Color Reference Meta Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Color Reference Meta Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseColorReferenceMetaAttribute(ColorReferenceMetaAttribute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Color References Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Color References Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseColorReferencesContainer(ColorReferencesContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Color References Meta Attributes Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Color References Meta Attributes Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseColorReferencesMetaAttributesContainer(ColorReferencesMetaAttributesContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Colors Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Colors Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseColorsContainer(ColorsContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Identifiable Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Identifiable Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIdentifiableElement(IdentifiableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Immediate Transition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Immediate Transition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseImmediateTransition(ImmediateTransition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Incidence Function Connection</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Incidence Function Connection</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIncidenceFunctionConnection(IncidenceFunctionConnection object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Incidence Function Connections Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Incidence Function Connections Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIncidenceFunctionConnectionsContainer(IncidenceFunctionConnectionsContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Incidence Function Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Incidence Function Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIncidenceFunctionElement(IncidenceFunctionElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Location Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Location Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLocationAttribute(LocationAttribute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mode</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mode</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMode(Mode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Modes Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Modes Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModesContainer(ModesContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Net Meta Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Net Meta Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNetMetaAttribute(NetMetaAttribute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Net Meta Attributes Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Net Meta Attributes Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNetMetaAttributesContainer(NetMetaAttributesContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ordinary Color Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ordinary Color Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOrdinaryColorReference(OrdinaryColorReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ordinary Place</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ordinary Place</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOrdinaryPlace(OrdinaryPlace object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Place</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Place</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePlace(Place object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Place Color Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Place Color Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePlaceColorReference(PlaceColorReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Place Meta Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Place Meta Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePlaceMetaAttribute(PlaceMetaAttribute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Place Meta Attributes Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Place Meta Attributes Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePlaceMetaAttributesContainer(PlaceMetaAttributesContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Places Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Places Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePlacesContainer(PlacesContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Place Transition Connection</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Place Transition Connection</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePlaceTransitionConnection(PlaceTransitionConnection object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Place Transition Connections Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Place Transition Connections Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePlaceTransitionConnectionsContainer(PlaceTransitionConnectionsContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Place Transition Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Place Transition Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePlaceTransitionElement(PlaceTransitionElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Probe</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Probe</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProbe(Probe object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Probe Color Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Probe Color Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProbeColorReference(ProbeColorReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Probe Meta Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Probe Meta Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProbeMetaAttribute(ProbeMetaAttribute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Probe Meta Attributes Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Probe Meta Attributes Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProbeMetaAttributesContainer(ProbeMetaAttributesContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Probes Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Probes Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProbesContainer(ProbesContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Qpme Document</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Qpme Document</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseQpmeDocument(QpmeDocument object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Queue</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Queue</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseQueue(Queue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Queueing Color Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Queueing Color Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseQueueingColorReference(QueueingColorReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Queueing Petri Net</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Queueing Petri Net</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseQueueingPetriNet(QueueingPetriNet object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Queueing Place</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Queueing Place</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseQueueingPlace(QueueingPlace object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Queues Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Queues Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseQueuesContainer(QueuesContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Simqpn Batch Means Color Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simqpn Batch Means Color Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSimqpnBatchMeansColorConfiguration(SimqpnBatchMeansColorConfiguration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Simqpn Batch Means Queueing Color Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simqpn Batch Means Queueing Color Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSimqpnBatchMeansQueueingColorConfiguration(SimqpnBatchMeansQueueingColorConfiguration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Simqpn Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simqpn Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSimqpnConfiguration(SimqpnConfiguration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Simqpn Meta Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simqpn Meta Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSimqpnMetaAttribute(SimqpnMetaAttribute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Simqpn Place Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simqpn Place Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSimqpnPlaceConfiguration(SimqpnPlaceConfiguration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Simqpn Repl Del Color Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simqpn Repl Del Color Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSimqpnReplDelColorConfiguration(SimqpnReplDelColorConfiguration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Simqpn Repl Del Queueing Color Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simqpn Repl Del Queueing Color Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSimqpnReplDelQueueingColorConfiguration(SimqpnReplDelQueueingColorConfiguration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Simqpn Welch Color Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simqpn Welch Color Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSimqpnWelchColorConfiguration(SimqpnWelchColorConfiguration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Simqpn Welch Queueing Color Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simqpn Welch Queueing Color Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSimqpnWelchQueueingColorConfiguration(SimqpnWelchQueueingColorConfiguration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Subnet</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Subnet</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSubnet(Subnet object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Subnet Color Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Subnet Color Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSubnetColorReference(SubnetColorReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Subnet Place</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Subnet Place</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSubnetPlace(SubnetPlace object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Timed Transition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Timed Transition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTimedTransition(TimedTransition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Transition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Transition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTransition(Transition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Transition Meta Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Transition Meta Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTransitionMetaAttribute(TransitionMetaAttribute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Transition Meta Attributes Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Transition Meta Attributes Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTransitionMetaAttributesContainer(TransitionMetaAttributesContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Transitions Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Transitions Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTransitionsContainer(TransitionsContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //ModelSwitch
