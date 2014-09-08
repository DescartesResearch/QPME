/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.kit.ipd.descartes.qpme.model.util;

import edu.kit.ipd.descartes.qpme.model.*;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.descartes.qpme.model.Color;
import edu.kit.ipd.descartes.qpme.model.ColorReference;
import edu.kit.ipd.descartes.qpme.model.ColorReferenceMetaAttribute;
import edu.kit.ipd.descartes.qpme.model.ColorReferencesContainer;
import edu.kit.ipd.descartes.qpme.model.ColorReferencesMetaAttributesContainer;
import edu.kit.ipd.descartes.qpme.model.ColorsContainer;
import edu.kit.ipd.descartes.qpme.model.IdentifiableElement;
import edu.kit.ipd.descartes.qpme.model.ImmediateTransition;
import edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnection;
import edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnectionsContainer;
import edu.kit.ipd.descartes.qpme.model.IncidenceFunctionElement;
import edu.kit.ipd.descartes.qpme.model.LocationAttribute;
import edu.kit.ipd.descartes.qpme.model.Mode;
import edu.kit.ipd.descartes.qpme.model.ModelPackage;
import edu.kit.ipd.descartes.qpme.model.ModesContainer;
import edu.kit.ipd.descartes.qpme.model.NetMetaAttribute;
import edu.kit.ipd.descartes.qpme.model.NetMetaAttributesContainer;
import edu.kit.ipd.descartes.qpme.model.OrdinaryColorReference;
import edu.kit.ipd.descartes.qpme.model.OrdinaryPlace;
import edu.kit.ipd.descartes.qpme.model.Place;
import edu.kit.ipd.descartes.qpme.model.PlaceColorReference;
import edu.kit.ipd.descartes.qpme.model.PlaceMetaAttribute;
import edu.kit.ipd.descartes.qpme.model.PlaceMetaAttributesContainer;
import edu.kit.ipd.descartes.qpme.model.PlaceTransitionConnection;
import edu.kit.ipd.descartes.qpme.model.PlaceTransitionConnectionsContainer;
import edu.kit.ipd.descartes.qpme.model.PlaceTransitionElement;
import edu.kit.ipd.descartes.qpme.model.PlacesContainer;
import edu.kit.ipd.descartes.qpme.model.Probe;
import edu.kit.ipd.descartes.qpme.model.ProbeColorReference;
import edu.kit.ipd.descartes.qpme.model.ProbeMetaAttribute;
import edu.kit.ipd.descartes.qpme.model.ProbeMetaAttributesContainer;
import edu.kit.ipd.descartes.qpme.model.ProbesContainer;
import edu.kit.ipd.descartes.qpme.model.QpmeDocument;
import edu.kit.ipd.descartes.qpme.model.Queue;
import edu.kit.ipd.descartes.qpme.model.QueueingColorReference;
import edu.kit.ipd.descartes.qpme.model.QueueingPetriNet;
import edu.kit.ipd.descartes.qpme.model.QueueingPlace;
import edu.kit.ipd.descartes.qpme.model.QueuesContainer;
import edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration;
import edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration;
import edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration;
import edu.kit.ipd.descartes.qpme.model.SimqpnMetaAttribute;
import edu.kit.ipd.descartes.qpme.model.SimqpnPlaceConfiguration;
import edu.kit.ipd.descartes.qpme.model.SimqpnReplDelColorConfiguration;
import edu.kit.ipd.descartes.qpme.model.SimqpnReplDelQueueingColorConfiguration;
import edu.kit.ipd.descartes.qpme.model.SimqpnWelchColorConfiguration;
import edu.kit.ipd.descartes.qpme.model.SimqpnWelchQueueingColorConfiguration;
import edu.kit.ipd.descartes.qpme.model.Subnet;
import edu.kit.ipd.descartes.qpme.model.SubnetColorReference;
import edu.kit.ipd.descartes.qpme.model.SubnetPlace;
import edu.kit.ipd.descartes.qpme.model.TimedTransition;
import edu.kit.ipd.descartes.qpme.model.Transition;
import edu.kit.ipd.descartes.qpme.model.TransitionMetaAttribute;
import edu.kit.ipd.descartes.qpme.model.TransitionMetaAttributesContainer;
import edu.kit.ipd.descartes.qpme.model.TransitionsContainer;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage
 * @generated
 */
public class ModelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ModelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ModelPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModelSwitch<Adapter> modelSwitch =
		new ModelSwitch<Adapter>() {
			@Override
			public Adapter caseColor(Color object) {
				return createColorAdapter();
			}
			@Override
			public Adapter caseColorReference(ColorReference object) {
				return createColorReferenceAdapter();
			}
			@Override
			public Adapter caseColorReferenceMetaAttribute(ColorReferenceMetaAttribute object) {
				return createColorReferenceMetaAttributeAdapter();
			}
			@Override
			public Adapter caseColorReferencesContainer(ColorReferencesContainer object) {
				return createColorReferencesContainerAdapter();
			}
			@Override
			public Adapter caseColorReferencesMetaAttributesContainer(ColorReferencesMetaAttributesContainer object) {
				return createColorReferencesMetaAttributesContainerAdapter();
			}
			@Override
			public Adapter caseColorsContainer(ColorsContainer object) {
				return createColorsContainerAdapter();
			}
			@Override
			public Adapter caseIdentifiableElement(IdentifiableElement object) {
				return createIdentifiableElementAdapter();
			}
			@Override
			public Adapter caseImmediateTransition(ImmediateTransition object) {
				return createImmediateTransitionAdapter();
			}
			@Override
			public Adapter caseIncidenceFunctionConnection(IncidenceFunctionConnection object) {
				return createIncidenceFunctionConnectionAdapter();
			}
			@Override
			public Adapter caseIncidenceFunctionConnectionsContainer(IncidenceFunctionConnectionsContainer object) {
				return createIncidenceFunctionConnectionsContainerAdapter();
			}
			@Override
			public Adapter caseIncidenceFunctionElement(IncidenceFunctionElement object) {
				return createIncidenceFunctionElementAdapter();
			}
			@Override
			public Adapter caseLocationAttribute(LocationAttribute object) {
				return createLocationAttributeAdapter();
			}
			@Override
			public Adapter caseMode(Mode object) {
				return createModeAdapter();
			}
			@Override
			public Adapter caseModesContainer(ModesContainer object) {
				return createModesContainerAdapter();
			}
			@Override
			public Adapter caseNetMetaAttribute(NetMetaAttribute object) {
				return createNetMetaAttributeAdapter();
			}
			@Override
			public Adapter caseNetMetaAttributesContainer(NetMetaAttributesContainer object) {
				return createNetMetaAttributesContainerAdapter();
			}
			@Override
			public Adapter caseOrdinaryColorReference(OrdinaryColorReference object) {
				return createOrdinaryColorReferenceAdapter();
			}
			@Override
			public Adapter caseOrdinaryPlace(OrdinaryPlace object) {
				return createOrdinaryPlaceAdapter();
			}
			@Override
			public Adapter casePlace(Place object) {
				return createPlaceAdapter();
			}
			@Override
			public Adapter casePlaceColorReference(PlaceColorReference object) {
				return createPlaceColorReferenceAdapter();
			}
			@Override
			public Adapter casePlaceMetaAttribute(PlaceMetaAttribute object) {
				return createPlaceMetaAttributeAdapter();
			}
			@Override
			public Adapter casePlaceMetaAttributesContainer(PlaceMetaAttributesContainer object) {
				return createPlaceMetaAttributesContainerAdapter();
			}
			@Override
			public Adapter casePlacesContainer(PlacesContainer object) {
				return createPlacesContainerAdapter();
			}
			@Override
			public Adapter casePlaceTransitionConnection(PlaceTransitionConnection object) {
				return createPlaceTransitionConnectionAdapter();
			}
			@Override
			public Adapter casePlaceTransitionConnectionsContainer(PlaceTransitionConnectionsContainer object) {
				return createPlaceTransitionConnectionsContainerAdapter();
			}
			@Override
			public Adapter casePlaceTransitionElement(PlaceTransitionElement object) {
				return createPlaceTransitionElementAdapter();
			}
			@Override
			public Adapter caseProbe(Probe object) {
				return createProbeAdapter();
			}
			@Override
			public Adapter caseProbeColorReference(ProbeColorReference object) {
				return createProbeColorReferenceAdapter();
			}
			@Override
			public Adapter caseProbeMetaAttribute(ProbeMetaAttribute object) {
				return createProbeMetaAttributeAdapter();
			}
			@Override
			public Adapter caseProbeMetaAttributesContainer(ProbeMetaAttributesContainer object) {
				return createProbeMetaAttributesContainerAdapter();
			}
			@Override
			public Adapter caseProbesContainer(ProbesContainer object) {
				return createProbesContainerAdapter();
			}
			@Override
			public Adapter caseQpmeDocument(QpmeDocument object) {
				return createQpmeDocumentAdapter();
			}
			@Override
			public Adapter caseQueue(Queue object) {
				return createQueueAdapter();
			}
			@Override
			public Adapter caseQueueingColorReference(QueueingColorReference object) {
				return createQueueingColorReferenceAdapter();
			}
			@Override
			public Adapter caseQueueingPetriNet(QueueingPetriNet object) {
				return createQueueingPetriNetAdapter();
			}
			@Override
			public Adapter caseQueueingPlace(QueueingPlace object) {
				return createQueueingPlaceAdapter();
			}
			@Override
			public Adapter caseQueuesContainer(QueuesContainer object) {
				return createQueuesContainerAdapter();
			}
			@Override
			public Adapter caseSimqpnBatchMeansColorConfiguration(SimqpnBatchMeansColorConfiguration object) {
				return createSimqpnBatchMeansColorConfigurationAdapter();
			}
			@Override
			public Adapter caseSimqpnBatchMeansQueueingColorConfiguration(SimqpnBatchMeansQueueingColorConfiguration object) {
				return createSimqpnBatchMeansQueueingColorConfigurationAdapter();
			}
			@Override
			public Adapter caseSimqpnConfiguration(SimqpnConfiguration object) {
				return createSimqpnConfigurationAdapter();
			}
			@Override
			public Adapter caseSimqpnMetaAttribute(SimqpnMetaAttribute object) {
				return createSimqpnMetaAttributeAdapter();
			}
			@Override
			public Adapter caseSimqpnPlaceConfiguration(SimqpnPlaceConfiguration object) {
				return createSimqpnPlaceConfigurationAdapter();
			}
			@Override
			public Adapter caseSimqpnReplDelColorConfiguration(SimqpnReplDelColorConfiguration object) {
				return createSimqpnReplDelColorConfigurationAdapter();
			}
			@Override
			public Adapter caseSimqpnReplDelQueueingColorConfiguration(SimqpnReplDelQueueingColorConfiguration object) {
				return createSimqpnReplDelQueueingColorConfigurationAdapter();
			}
			@Override
			public Adapter caseSimqpnWelchColorConfiguration(SimqpnWelchColorConfiguration object) {
				return createSimqpnWelchColorConfigurationAdapter();
			}
			@Override
			public Adapter caseSimqpnWelchQueueingColorConfiguration(SimqpnWelchQueueingColorConfiguration object) {
				return createSimqpnWelchQueueingColorConfigurationAdapter();
			}
			@Override
			public Adapter caseSubnet(Subnet object) {
				return createSubnetAdapter();
			}
			@Override
			public Adapter caseSubnetColorReference(SubnetColorReference object) {
				return createSubnetColorReferenceAdapter();
			}
			@Override
			public Adapter caseSubnetPlace(SubnetPlace object) {
				return createSubnetPlaceAdapter();
			}
			@Override
			public Adapter caseTimedTransition(TimedTransition object) {
				return createTimedTransitionAdapter();
			}
			@Override
			public Adapter caseTransition(Transition object) {
				return createTransitionAdapter();
			}
			@Override
			public Adapter caseTransitionMetaAttribute(TransitionMetaAttribute object) {
				return createTransitionMetaAttributeAdapter();
			}
			@Override
			public Adapter caseTransitionMetaAttributesContainer(TransitionMetaAttributesContainer object) {
				return createTransitionMetaAttributesContainerAdapter();
			}
			@Override
			public Adapter caseTransitionsContainer(TransitionsContainer object) {
				return createTransitionsContainerAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.Color <em>Color</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.Color
	 * @generated
	 */
	public Adapter createColorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.ColorReference <em>Color Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.ColorReference
	 * @generated
	 */
	public Adapter createColorReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.ColorReferenceMetaAttribute <em>Color Reference Meta Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.ColorReferenceMetaAttribute
	 * @generated
	 */
	public Adapter createColorReferenceMetaAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.ColorReferencesContainer <em>Color References Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.ColorReferencesContainer
	 * @generated
	 */
	public Adapter createColorReferencesContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.ColorReferencesMetaAttributesContainer <em>Color References Meta Attributes Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.ColorReferencesMetaAttributesContainer
	 * @generated
	 */
	public Adapter createColorReferencesMetaAttributesContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.ColorsContainer <em>Colors Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.ColorsContainer
	 * @generated
	 */
	public Adapter createColorsContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.IdentifiableElement <em>Identifiable Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.IdentifiableElement
	 * @generated
	 */
	public Adapter createIdentifiableElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.ImmediateTransition <em>Immediate Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.ImmediateTransition
	 * @generated
	 */
	public Adapter createImmediateTransitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnection <em>Incidence Function Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnection
	 * @generated
	 */
	public Adapter createIncidenceFunctionConnectionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnectionsContainer <em>Incidence Function Connections Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnectionsContainer
	 * @generated
	 */
	public Adapter createIncidenceFunctionConnectionsContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.IncidenceFunctionElement <em>Incidence Function Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.IncidenceFunctionElement
	 * @generated
	 */
	public Adapter createIncidenceFunctionElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.LocationAttribute <em>Location Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.LocationAttribute
	 * @generated
	 */
	public Adapter createLocationAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.Mode <em>Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.Mode
	 * @generated
	 */
	public Adapter createModeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.ModesContainer <em>Modes Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.ModesContainer
	 * @generated
	 */
	public Adapter createModesContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.NetMetaAttribute <em>Net Meta Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.NetMetaAttribute
	 * @generated
	 */
	public Adapter createNetMetaAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.NetMetaAttributesContainer <em>Net Meta Attributes Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.NetMetaAttributesContainer
	 * @generated
	 */
	public Adapter createNetMetaAttributesContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.OrdinaryColorReference <em>Ordinary Color Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.OrdinaryColorReference
	 * @generated
	 */
	public Adapter createOrdinaryColorReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.OrdinaryPlace <em>Ordinary Place</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.OrdinaryPlace
	 * @generated
	 */
	public Adapter createOrdinaryPlaceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.Place <em>Place</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.Place
	 * @generated
	 */
	public Adapter createPlaceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.PlaceColorReference <em>Place Color Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.PlaceColorReference
	 * @generated
	 */
	public Adapter createPlaceColorReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.PlaceMetaAttribute <em>Place Meta Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.PlaceMetaAttribute
	 * @generated
	 */
	public Adapter createPlaceMetaAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.PlaceMetaAttributesContainer <em>Place Meta Attributes Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.PlaceMetaAttributesContainer
	 * @generated
	 */
	public Adapter createPlaceMetaAttributesContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.PlacesContainer <em>Places Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.PlacesContainer
	 * @generated
	 */
	public Adapter createPlacesContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.PlaceTransitionConnection <em>Place Transition Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.PlaceTransitionConnection
	 * @generated
	 */
	public Adapter createPlaceTransitionConnectionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.PlaceTransitionConnectionsContainer <em>Place Transition Connections Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.PlaceTransitionConnectionsContainer
	 * @generated
	 */
	public Adapter createPlaceTransitionConnectionsContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.PlaceTransitionElement <em>Place Transition Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.PlaceTransitionElement
	 * @generated
	 */
	public Adapter createPlaceTransitionElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.Probe <em>Probe</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.Probe
	 * @generated
	 */
	public Adapter createProbeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.ProbeColorReference <em>Probe Color Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.ProbeColorReference
	 * @generated
	 */
	public Adapter createProbeColorReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.ProbeMetaAttribute <em>Probe Meta Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.ProbeMetaAttribute
	 * @generated
	 */
	public Adapter createProbeMetaAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.ProbeMetaAttributesContainer <em>Probe Meta Attributes Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.ProbeMetaAttributesContainer
	 * @generated
	 */
	public Adapter createProbeMetaAttributesContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.ProbesContainer <em>Probes Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.ProbesContainer
	 * @generated
	 */
	public Adapter createProbesContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.QpmeDocument <em>Qpme Document</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.QpmeDocument
	 * @generated
	 */
	public Adapter createQpmeDocumentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.Queue <em>Queue</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.Queue
	 * @generated
	 */
	public Adapter createQueueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference <em>Queueing Color Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingColorReference
	 * @generated
	 */
	public Adapter createQueueingColorReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.QueueingPetriNet <em>Queueing Petri Net</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingPetriNet
	 * @generated
	 */
	public Adapter createQueueingPetriNetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.QueueingPlace <em>Queueing Place</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingPlace
	 * @generated
	 */
	public Adapter createQueueingPlaceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.QueuesContainer <em>Queues Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.QueuesContainer
	 * @generated
	 */
	public Adapter createQueuesContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration <em>Simqpn Batch Means Color Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration
	 * @generated
	 */
	public Adapter createSimqpnBatchMeansColorConfigurationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration <em>Simqpn Batch Means Queueing Color Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration
	 * @generated
	 */
	public Adapter createSimqpnBatchMeansQueueingColorConfigurationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration <em>Simqpn Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration
	 * @generated
	 */
	public Adapter createSimqpnConfigurationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.SimqpnMetaAttribute <em>Simqpn Meta Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnMetaAttribute
	 * @generated
	 */
	public Adapter createSimqpnMetaAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.SimqpnPlaceConfiguration <em>Simqpn Place Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnPlaceConfiguration
	 * @generated
	 */
	public Adapter createSimqpnPlaceConfigurationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.SimqpnReplDelColorConfiguration <em>Simqpn Repl Del Color Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnReplDelColorConfiguration
	 * @generated
	 */
	public Adapter createSimqpnReplDelColorConfigurationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.SimqpnReplDelQueueingColorConfiguration <em>Simqpn Repl Del Queueing Color Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnReplDelQueueingColorConfiguration
	 * @generated
	 */
	public Adapter createSimqpnReplDelQueueingColorConfigurationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.SimqpnWelchColorConfiguration <em>Simqpn Welch Color Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnWelchColorConfiguration
	 * @generated
	 */
	public Adapter createSimqpnWelchColorConfigurationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.SimqpnWelchQueueingColorConfiguration <em>Simqpn Welch Queueing Color Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnWelchQueueingColorConfiguration
	 * @generated
	 */
	public Adapter createSimqpnWelchQueueingColorConfigurationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.Subnet <em>Subnet</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.Subnet
	 * @generated
	 */
	public Adapter createSubnetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.SubnetColorReference <em>Subnet Color Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.SubnetColorReference
	 * @generated
	 */
	public Adapter createSubnetColorReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.SubnetPlace <em>Subnet Place</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.SubnetPlace
	 * @generated
	 */
	public Adapter createSubnetPlaceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.TimedTransition <em>Timed Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.TimedTransition
	 * @generated
	 */
	public Adapter createTimedTransitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.Transition <em>Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.Transition
	 * @generated
	 */
	public Adapter createTransitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.TransitionMetaAttribute <em>Transition Meta Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.TransitionMetaAttribute
	 * @generated
	 */
	public Adapter createTransitionMetaAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.TransitionMetaAttributesContainer <em>Transition Meta Attributes Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.TransitionMetaAttributesContainer
	 * @generated
	 */
	public Adapter createTransitionMetaAttributesContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link edu.kit.ipd.descartes.qpme.model.TransitionsContainer <em>Transitions Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see edu.kit.ipd.descartes.qpme.model.TransitionsContainer
	 * @generated
	 */
	public Adapter createTransitionsContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //ModelAdapterFactory
