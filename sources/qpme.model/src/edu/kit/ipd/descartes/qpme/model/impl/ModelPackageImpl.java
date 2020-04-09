/**
 */
package edu.kit.ipd.descartes.qpme.model.impl;

import edu.kit.ipd.descartes.qpme.model.Color;
import edu.kit.ipd.descartes.qpme.model.ColorReference;
import edu.kit.ipd.descartes.qpme.model.ColorReferenceMetaAttribute;
import edu.kit.ipd.descartes.qpme.model.ColorReferencesContainer;
import edu.kit.ipd.descartes.qpme.model.ColorReferencesMetaAttributesContainer;
import edu.kit.ipd.descartes.qpme.model.ColorsContainer;
import edu.kit.ipd.descartes.qpme.model.DepartureDiscipline;
import edu.kit.ipd.descartes.qpme.model.DistributionFunction;
import edu.kit.ipd.descartes.qpme.model.FlowDirection;
import edu.kit.ipd.descartes.qpme.model.IdentifiableElement;
import edu.kit.ipd.descartes.qpme.model.ImmediateTransition;
import edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnection;
import edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnectionsContainer;
import edu.kit.ipd.descartes.qpme.model.IncidenceFunctionElement;
import edu.kit.ipd.descartes.qpme.model.LocationAttribute;
import edu.kit.ipd.descartes.qpme.model.Mode;
import edu.kit.ipd.descartes.qpme.model.ModelFactory;
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
import edu.kit.ipd.descartes.qpme.model.ProbeTrigger;
import edu.kit.ipd.descartes.qpme.model.ProbesContainer;
import edu.kit.ipd.descartes.qpme.model.QpmeDocument;
import edu.kit.ipd.descartes.qpme.model.Queue;
import edu.kit.ipd.descartes.qpme.model.QueueingColorReference;
import edu.kit.ipd.descartes.qpme.model.QueueingPetriNet;
import edu.kit.ipd.descartes.qpme.model.QueueingPlace;
import edu.kit.ipd.descartes.qpme.model.QueueingStrategy;
import edu.kit.ipd.descartes.qpme.model.QueuesContainer;
import edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration;
import edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration;
import edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration;
import edu.kit.ipd.descartes.qpme.model.SimqpnMetaAttribute;
import edu.kit.ipd.descartes.qpme.model.SimqpnPlaceConfiguration;
import edu.kit.ipd.descartes.qpme.model.SimqpnReplDelColorConfiguration;
import edu.kit.ipd.descartes.qpme.model.SimqpnReplDelQueueingColorConfiguration;
import edu.kit.ipd.descartes.qpme.model.SimqpnSimulationScenario;
import edu.kit.ipd.descartes.qpme.model.SimqpnStoppingRule;
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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ModelPackageImpl extends EPackageImpl implements ModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass colorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass colorReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass colorReferenceMetaAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass colorReferencesContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass colorReferencesMetaAttributesContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass colorsContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass identifiableElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass immediateTransitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass incidenceFunctionConnectionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass incidenceFunctionConnectionsContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass incidenceFunctionElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass locationAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass modeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass modesContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass netMetaAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass netMetaAttributesContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ordinaryColorReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ordinaryPlaceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass placeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass placeColorReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass placeMetaAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass placeMetaAttributesContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass placesContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass placeTransitionConnectionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass placeTransitionConnectionsContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass placeTransitionElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass probeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass probeColorReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass probeMetaAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass probeMetaAttributesContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass probesContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass qpmeDocumentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass queueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass queueingColorReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass queueingPetriNetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass queueingPlaceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass queuesContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simqpnBatchMeansColorConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simqpnBatchMeansQueueingColorConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simqpnConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simqpnMetaAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simqpnPlaceConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simqpnReplDelColorConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simqpnReplDelQueueingColorConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simqpnWelchColorConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simqpnWelchQueueingColorConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass subnetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass subnetColorReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass subnetPlaceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass timedTransitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass transitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass transitionMetaAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass transitionMetaAttributesContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass transitionsContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum departureDisciplineEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum distributionFunctionEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum flowDirectionEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum probeTriggerEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum queueingStrategyEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum simqpnSimulationScenarioEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum simqpnStoppingRuleEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType departureDisciplineObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType distributionFunctionObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType flowDirectionObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType probeTriggerObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType queueingStrategyObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType rgbValueEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType simqpnSimulationScenarioObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType simqpnStoppingRuleObjectEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ModelPackageImpl() {
		super(eNS_URI, ModelFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link ModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ModelPackage init() {
		if (isInited) return (ModelPackage)EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredModelPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		ModelPackageImpl theModelPackage = registeredModelPackage instanceof ModelPackageImpl ? (ModelPackageImpl)registeredModelPackage : new ModelPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theModelPackage.createPackageContents();

		// Initialize created meta-data
		theModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theModelPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ModelPackage.eNS_URI, theModelPackage);
		return theModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getColor() {
		return colorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getColor_Description() {
		return (EAttribute)colorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getColor_Name() {
		return (EAttribute)colorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getColor_RealColor() {
		return (EAttribute)colorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getColorReference() {
		return colorReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getColorReference_MetaAttributes() {
		return (EReference)colorReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getColorReference_Color() {
		return (EReference)colorReferenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getColorReferenceMetaAttribute() {
		return colorReferenceMetaAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getColorReferencesContainer() {
		return colorReferencesContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getColorReferencesContainer_Definitions() {
		return (EReference)colorReferencesContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getColorReferencesMetaAttributesContainer() {
		return colorReferencesMetaAttributesContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getColorReferencesMetaAttributesContainer_Entries() {
		return (EReference)colorReferencesMetaAttributesContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getColorsContainer() {
		return colorsContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getColorsContainer_Definitions() {
		return (EReference)colorsContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIdentifiableElement() {
		return identifiableElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIdentifiableElement_Id() {
		return (EAttribute)identifiableElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getImmediateTransition() {
		return immediateTransitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImmediateTransition_Weight() {
		return (EAttribute)immediateTransitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIncidenceFunctionConnection() {
		return incidenceFunctionConnectionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIncidenceFunctionConnection_Count() {
		return (EAttribute)incidenceFunctionConnectionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIncidenceFunctionConnection_Source() {
		return (EReference)incidenceFunctionConnectionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIncidenceFunctionConnection_Target() {
		return (EReference)incidenceFunctionConnectionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIncidenceFunctionConnectionsContainer() {
		return incidenceFunctionConnectionsContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIncidenceFunctionConnectionsContainer_Definitions() {
		return (EReference)incidenceFunctionConnectionsContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIncidenceFunctionElement() {
		return incidenceFunctionElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIncidenceFunctionElement_IncomingConnections() {
		return (EReference)incidenceFunctionElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIncidenceFunctionElement_OutgoingConnections() {
		return (EReference)incidenceFunctionElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLocationAttribute() {
		return locationAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocationAttribute_LocationX() {
		return (EAttribute)locationAttributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocationAttribute_LocationY() {
		return (EAttribute)locationAttributeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMode() {
		return modeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMode_FiringWeight() {
		return (EAttribute)modeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMode_MeanFiringDelay() {
		return (EAttribute)modeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMode_Name() {
		return (EAttribute)modeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMode_RealColor() {
		return (EAttribute)modeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getModesContainer() {
		return modesContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModesContainer_Definitions() {
		return (EReference)modesContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNetMetaAttribute() {
		return netMetaAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNetMetaAttributesContainer() {
		return netMetaAttributesContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNetMetaAttributesContainer_Entries() {
		return (EReference)netMetaAttributesContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOrdinaryColorReference() {
		return ordinaryColorReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOrdinaryPlace() {
		return ordinaryPlaceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPlace() {
		return placeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPlace_ColorReferences() {
		return (EReference)placeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPlace_MetaAttributes() {
		return (EReference)placeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPlace_DepartureDiscipline() {
		return (EAttribute)placeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPlace_Locked() {
		return (EAttribute)placeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPlace_Name() {
		return (EAttribute)placeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPlaceColorReference() {
		return placeColorReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPlaceColorReference_InitialPopulation() {
		return (EAttribute)placeColorReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPlaceColorReference_MaximumCapacity() {
		return (EAttribute)placeColorReferenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPlaceMetaAttribute() {
		return placeMetaAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPlaceMetaAttributesContainer() {
		return placeMetaAttributesContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPlaceMetaAttributesContainer_Entries() {
		return (EReference)placeMetaAttributesContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPlacesContainer() {
		return placesContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPlacesContainer_Definitions() {
		return (EReference)placesContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPlaceTransitionConnection() {
		return placeTransitionConnectionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPlaceTransitionConnection_Source() {
		return (EReference)placeTransitionConnectionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPlaceTransitionConnection_Target() {
		return (EReference)placeTransitionConnectionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPlaceTransitionConnectionsContainer() {
		return placeTransitionConnectionsContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPlaceTransitionConnectionsContainer_Definitions() {
		return (EReference)placeTransitionConnectionsContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPlaceTransitionElement() {
		return placeTransitionElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPlaceTransitionElement_IncomingConnections() {
		return (EReference)placeTransitionElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPlaceTransitionElement_OutgoingConnections() {
		return (EReference)placeTransitionElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProbe() {
		return probeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProbe_ColorReferences() {
		return (EReference)probeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProbe_MetaAttributes() {
		return (EReference)probeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProbe_EndPlace() {
		return (EReference)probeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProbe_EndTrigger() {
		return (EAttribute)probeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProbe_Name() {
		return (EAttribute)probeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProbe_StartPlace() {
		return (EReference)probeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProbe_StartTrigger() {
		return (EAttribute)probeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProbeColorReference() {
		return probeColorReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProbeMetaAttribute() {
		return probeMetaAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProbeMetaAttributesContainer() {
		return probeMetaAttributesContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProbeMetaAttributesContainer_Entries() {
		return (EReference)probeMetaAttributesContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProbesContainer() {
		return probesContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProbesContainer_Definitions() {
		return (EReference)probesContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getQpmeDocument() {
		return qpmeDocumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQpmeDocument_Mixed() {
		return (EAttribute)qpmeDocumentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQpmeDocument_XMLNSPrefixMap() {
		return (EReference)qpmeDocumentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQpmeDocument_XSISchemaLocation() {
		return (EReference)qpmeDocumentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQpmeDocument_Net() {
		return (EReference)qpmeDocumentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getQueue() {
		return queueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueue_Name() {
		return (EAttribute)queueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueue_NumberOfServers() {
		return (EAttribute)queueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueue_Strategy() {
		return (EAttribute)queueEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getQueueingColorReference() {
		return queueingColorReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueueingColorReference_Alpha() {
		return (EAttribute)queueingColorReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueueingColorReference_Beta() {
		return (EAttribute)queueingColorReferenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueueingColorReference_Cut() {
		return (EAttribute)queueingColorReferenceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueueingColorReference_DistributionFunction() {
		return (EAttribute)queueingColorReferenceEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueueingColorReference_Freedom() {
		return (EAttribute)queueingColorReferenceEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueueingColorReference_Gamma() {
		return (EAttribute)queueingColorReferenceEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueueingColorReference_InputFile() {
		return (EAttribute)queueingColorReferenceEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueueingColorReference_Lambda() {
		return (EAttribute)queueingColorReferenceEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueueingColorReference_Max() {
		return (EAttribute)queueingColorReferenceEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueueingColorReference_Mean() {
		return (EAttribute)queueingColorReferenceEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueueingColorReference_Min() {
		return (EAttribute)queueingColorReferenceEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueueingColorReference_P() {
		return (EAttribute)queueingColorReferenceEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueueingColorReference_C() {
		return (EAttribute)queueingColorReferenceEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueueingColorReference_Priority() {
		return (EAttribute)queueingColorReferenceEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueueingColorReference_Ranking() {
		return (EAttribute)queueingColorReferenceEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueueingColorReference_StdDev() {
		return (EAttribute)queueingColorReferenceEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueueingColorReference_Tau() {
		return (EAttribute)queueingColorReferenceEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueueingColorReference_Offset() {
		return (EAttribute)queueingColorReferenceEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueueingColorReference_Scale() {
		return (EAttribute)queueingColorReferenceEClass.getEStructuralFeatures().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueueingColorReference_ReplayFile() {
		return (EAttribute)queueingColorReferenceEClass.getEStructuralFeatures().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueueingColorReference_ValuesFile() {
		return (EAttribute)queueingColorReferenceEClass.getEStructuralFeatures().get(20);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueueingColorReference_ProbabilitiesFile() {
		return (EAttribute)queueingColorReferenceEClass.getEStructuralFeatures().get(21);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueueingColorReference_ConcurrenciesFile() {
		return (EAttribute)queueingColorReferenceEClass.getEStructuralFeatures().get(22);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueueingColorReference_ResponsetimesFile() {
		return (EAttribute)queueingColorReferenceEClass.getEStructuralFeatures().get(23);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueueingColorReference_MarsFile() {
		return (EAttribute)queueingColorReferenceEClass.getEStructuralFeatures().get(24);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueueingColorReference_WekaFile() {
		return (EAttribute)queueingColorReferenceEClass.getEStructuralFeatures().get(25);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getQueueingPetriNet() {
		return queueingPetriNetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQueueingPetriNet_Colors() {
		return (EReference)queueingPetriNetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQueueingPetriNet_Queues() {
		return (EReference)queueingPetriNetEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQueueingPetriNet_Places() {
		return (EReference)queueingPetriNetEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQueueingPetriNet_Transitions() {
		return (EReference)queueingPetriNetEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQueueingPetriNet_Connections() {
		return (EReference)queueingPetriNetEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQueueingPetriNet_Probes() {
		return (EReference)queueingPetriNetEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQueueingPetriNet_MetaAttributes() {
		return (EReference)queueingPetriNetEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQueueingPetriNet_QpmeVersion() {
		return (EAttribute)queueingPetriNetEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getQueueingPlace() {
		return queueingPlaceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQueueingPlace_Queue() {
		return (EReference)queueingPlaceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getQueuesContainer() {
		return queuesContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQueuesContainer_Definitions() {
		return (EReference)queuesContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSimqpnBatchMeansColorConfiguration() {
		return simqpnBatchMeansColorConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnBatchMeansColorConfiguration_BatchSize() {
		return (EAttribute)simqpnBatchMeansColorConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnBatchMeansColorConfiguration_BucketSize() {
		return (EAttribute)simqpnBatchMeansColorConfigurationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnBatchMeansColorConfiguration_MaxBuckets() {
		return (EAttribute)simqpnBatchMeansColorConfigurationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnBatchMeansColorConfiguration_MinBatches() {
		return (EAttribute)simqpnBatchMeansColorConfigurationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnBatchMeansColorConfiguration_NumBMeansCorlTested() {
		return (EAttribute)simqpnBatchMeansColorConfigurationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnBatchMeansColorConfiguration_ReqAbsPrc() {
		return (EAttribute)simqpnBatchMeansColorConfigurationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnBatchMeansColorConfiguration_ReqRelPrc() {
		return (EAttribute)simqpnBatchMeansColorConfigurationEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnBatchMeansColorConfiguration_SignLev() {
		return (EAttribute)simqpnBatchMeansColorConfigurationEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSimqpnBatchMeansQueueingColorConfiguration() {
		return simqpnBatchMeansQueueingColorConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnBatchMeansQueueingColorConfiguration_QueueBatchSize() {
		return (EAttribute)simqpnBatchMeansQueueingColorConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnBatchMeansQueueingColorConfiguration_QueueBucketSize() {
		return (EAttribute)simqpnBatchMeansQueueingColorConfigurationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnBatchMeansQueueingColorConfiguration_QueueMaxBuckets() {
		return (EAttribute)simqpnBatchMeansQueueingColorConfigurationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnBatchMeansQueueingColorConfiguration_QueueMinBatches() {
		return (EAttribute)simqpnBatchMeansQueueingColorConfigurationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnBatchMeansQueueingColorConfiguration_QueueNumBMeansCorlTested() {
		return (EAttribute)simqpnBatchMeansQueueingColorConfigurationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnBatchMeansQueueingColorConfiguration_QueueReqAbsPrc() {
		return (EAttribute)simqpnBatchMeansQueueingColorConfigurationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnBatchMeansQueueingColorConfiguration_QueueReqRelPrc() {
		return (EAttribute)simqpnBatchMeansQueueingColorConfigurationEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnBatchMeansQueueingColorConfiguration_QueueSignLev() {
		return (EAttribute)simqpnBatchMeansQueueingColorConfigurationEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSimqpnConfiguration() {
		return simqpnConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnConfiguration_ConfigurationDescription() {
		return (EAttribute)simqpnConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnConfiguration_NumberOfRuns() {
		return (EAttribute)simqpnConfigurationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnConfiguration_OutputDirectory() {
		return (EAttribute)simqpnConfigurationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnConfiguration_RampUpLength() {
		return (EAttribute)simqpnConfigurationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnConfiguration_Scenario() {
		return (EAttribute)simqpnConfigurationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnConfiguration_SecondsBetweenHeartBeats() {
		return (EAttribute)simqpnConfigurationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnConfiguration_SecondsBetweenStopChecks() {
		return (EAttribute)simqpnConfigurationEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnConfiguration_StoppingRule() {
		return (EAttribute)simqpnConfigurationEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnConfiguration_TimeBeforeInitialHeartBeat() {
		return (EAttribute)simqpnConfigurationEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnConfiguration_TimeBetweenStopChecks() {
		return (EAttribute)simqpnConfigurationEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnConfiguration_TotalRunLength() {
		return (EAttribute)simqpnConfigurationEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnConfiguration_VerbosityLevel() {
		return (EAttribute)simqpnConfigurationEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSimqpnMetaAttribute() {
		return simqpnMetaAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnMetaAttribute_ConfigurationName() {
		return (EAttribute)simqpnMetaAttributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSimqpnPlaceConfiguration() {
		return simqpnPlaceConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnPlaceConfiguration_StatsLevel() {
		return (EAttribute)simqpnPlaceConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSimqpnReplDelColorConfiguration() {
		return simqpnReplDelColorConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnReplDelColorConfiguration_SignLevAvgST() {
		return (EAttribute)simqpnReplDelColorConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSimqpnReplDelQueueingColorConfiguration() {
		return simqpnReplDelQueueingColorConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnReplDelQueueingColorConfiguration_QueueSignLevAvgST() {
		return (EAttribute)simqpnReplDelQueueingColorConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSimqpnWelchColorConfiguration() {
		return simqpnWelchColorConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnWelchColorConfiguration_MaxObsrv() {
		return (EAttribute)simqpnWelchColorConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnWelchColorConfiguration_MinObsrv() {
		return (EAttribute)simqpnWelchColorConfigurationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSimqpnWelchQueueingColorConfiguration() {
		return simqpnWelchQueueingColorConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnWelchQueueingColorConfiguration_QueueMaxObsrv() {
		return (EAttribute)simqpnWelchQueueingColorConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnWelchQueueingColorConfiguration_QueueMinObsrv() {
		return (EAttribute)simqpnWelchQueueingColorConfigurationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSubnet() {
		return subnetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSubnet_Colors() {
		return (EReference)subnetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSubnet_Places() {
		return (EReference)subnetEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSubnet_Transitions() {
		return (EReference)subnetEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSubnet_Connections() {
		return (EReference)subnetEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSubnetColorReference() {
		return subnetColorReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSubnetColorReference_Direction() {
		return (EAttribute)subnetColorReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSubnetPlace() {
		return subnetPlaceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSubnetPlace_Subnet() {
		return (EReference)subnetPlaceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTimedTransition() {
		return timedTransitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTransition() {
		return transitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTransition_Modes() {
		return (EReference)transitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTransition_Connections() {
		return (EReference)transitionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTransition_MetaAttributes() {
		return (EReference)transitionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTransition_Name() {
		return (EAttribute)transitionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTransition_Priority() {
		return (EAttribute)transitionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTransitionMetaAttribute() {
		return transitionMetaAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTransitionMetaAttributesContainer() {
		return transitionMetaAttributesContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTransitionMetaAttributesContainer_Entries() {
		return (EReference)transitionMetaAttributesContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTransitionsContainer() {
		return transitionsContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTransitionsContainer_Definitions() {
		return (EReference)transitionsContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getDepartureDiscipline() {
		return departureDisciplineEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getDistributionFunction() {
		return distributionFunctionEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getFlowDirection() {
		return flowDirectionEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getProbeTrigger() {
		return probeTriggerEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getQueueingStrategy() {
		return queueingStrategyEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getSimqpnSimulationScenario() {
		return simqpnSimulationScenarioEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getSimqpnStoppingRule() {
		return simqpnStoppingRuleEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getDepartureDisciplineObject() {
		return departureDisciplineObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getDistributionFunctionObject() {
		return distributionFunctionObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getFlowDirectionObject() {
		return flowDirectionObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getProbeTriggerObject() {
		return probeTriggerObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getQueueingStrategyObject() {
		return queueingStrategyObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getRgbValue() {
		return rgbValueEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getSimqpnSimulationScenarioObject() {
		return simqpnSimulationScenarioObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getSimqpnStoppingRuleObject() {
		return simqpnStoppingRuleObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelFactory getModelFactory() {
		return (ModelFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		colorEClass = createEClass(COLOR);
		createEAttribute(colorEClass, COLOR__DESCRIPTION);
		createEAttribute(colorEClass, COLOR__NAME);
		createEAttribute(colorEClass, COLOR__REAL_COLOR);

		colorReferenceEClass = createEClass(COLOR_REFERENCE);
		createEReference(colorReferenceEClass, COLOR_REFERENCE__META_ATTRIBUTES);
		createEReference(colorReferenceEClass, COLOR_REFERENCE__COLOR);

		colorReferenceMetaAttributeEClass = createEClass(COLOR_REFERENCE_META_ATTRIBUTE);

		colorReferencesContainerEClass = createEClass(COLOR_REFERENCES_CONTAINER);
		createEReference(colorReferencesContainerEClass, COLOR_REFERENCES_CONTAINER__DEFINITIONS);

		colorReferencesMetaAttributesContainerEClass = createEClass(COLOR_REFERENCES_META_ATTRIBUTES_CONTAINER);
		createEReference(colorReferencesMetaAttributesContainerEClass, COLOR_REFERENCES_META_ATTRIBUTES_CONTAINER__ENTRIES);

		colorsContainerEClass = createEClass(COLORS_CONTAINER);
		createEReference(colorsContainerEClass, COLORS_CONTAINER__DEFINITIONS);

		identifiableElementEClass = createEClass(IDENTIFIABLE_ELEMENT);
		createEAttribute(identifiableElementEClass, IDENTIFIABLE_ELEMENT__ID);

		immediateTransitionEClass = createEClass(IMMEDIATE_TRANSITION);
		createEAttribute(immediateTransitionEClass, IMMEDIATE_TRANSITION__WEIGHT);

		incidenceFunctionConnectionEClass = createEClass(INCIDENCE_FUNCTION_CONNECTION);
		createEAttribute(incidenceFunctionConnectionEClass, INCIDENCE_FUNCTION_CONNECTION__COUNT);
		createEReference(incidenceFunctionConnectionEClass, INCIDENCE_FUNCTION_CONNECTION__SOURCE);
		createEReference(incidenceFunctionConnectionEClass, INCIDENCE_FUNCTION_CONNECTION__TARGET);

		incidenceFunctionConnectionsContainerEClass = createEClass(INCIDENCE_FUNCTION_CONNECTIONS_CONTAINER);
		createEReference(incidenceFunctionConnectionsContainerEClass, INCIDENCE_FUNCTION_CONNECTIONS_CONTAINER__DEFINITIONS);

		incidenceFunctionElementEClass = createEClass(INCIDENCE_FUNCTION_ELEMENT);
		createEReference(incidenceFunctionElementEClass, INCIDENCE_FUNCTION_ELEMENT__INCOMING_CONNECTIONS);
		createEReference(incidenceFunctionElementEClass, INCIDENCE_FUNCTION_ELEMENT__OUTGOING_CONNECTIONS);

		locationAttributeEClass = createEClass(LOCATION_ATTRIBUTE);
		createEAttribute(locationAttributeEClass, LOCATION_ATTRIBUTE__LOCATION_X);
		createEAttribute(locationAttributeEClass, LOCATION_ATTRIBUTE__LOCATION_Y);

		modeEClass = createEClass(MODE);
		createEAttribute(modeEClass, MODE__FIRING_WEIGHT);
		createEAttribute(modeEClass, MODE__MEAN_FIRING_DELAY);
		createEAttribute(modeEClass, MODE__NAME);
		createEAttribute(modeEClass, MODE__REAL_COLOR);

		modesContainerEClass = createEClass(MODES_CONTAINER);
		createEReference(modesContainerEClass, MODES_CONTAINER__DEFINITIONS);

		netMetaAttributeEClass = createEClass(NET_META_ATTRIBUTE);

		netMetaAttributesContainerEClass = createEClass(NET_META_ATTRIBUTES_CONTAINER);
		createEReference(netMetaAttributesContainerEClass, NET_META_ATTRIBUTES_CONTAINER__ENTRIES);

		ordinaryColorReferenceEClass = createEClass(ORDINARY_COLOR_REFERENCE);

		ordinaryPlaceEClass = createEClass(ORDINARY_PLACE);

		placeEClass = createEClass(PLACE);
		createEReference(placeEClass, PLACE__COLOR_REFERENCES);
		createEReference(placeEClass, PLACE__META_ATTRIBUTES);
		createEAttribute(placeEClass, PLACE__DEPARTURE_DISCIPLINE);
		createEAttribute(placeEClass, PLACE__LOCKED);
		createEAttribute(placeEClass, PLACE__NAME);

		placeColorReferenceEClass = createEClass(PLACE_COLOR_REFERENCE);
		createEAttribute(placeColorReferenceEClass, PLACE_COLOR_REFERENCE__INITIAL_POPULATION);
		createEAttribute(placeColorReferenceEClass, PLACE_COLOR_REFERENCE__MAXIMUM_CAPACITY);

		placeMetaAttributeEClass = createEClass(PLACE_META_ATTRIBUTE);

		placeMetaAttributesContainerEClass = createEClass(PLACE_META_ATTRIBUTES_CONTAINER);
		createEReference(placeMetaAttributesContainerEClass, PLACE_META_ATTRIBUTES_CONTAINER__ENTRIES);

		placesContainerEClass = createEClass(PLACES_CONTAINER);
		createEReference(placesContainerEClass, PLACES_CONTAINER__DEFINITIONS);

		placeTransitionConnectionEClass = createEClass(PLACE_TRANSITION_CONNECTION);
		createEReference(placeTransitionConnectionEClass, PLACE_TRANSITION_CONNECTION__SOURCE);
		createEReference(placeTransitionConnectionEClass, PLACE_TRANSITION_CONNECTION__TARGET);

		placeTransitionConnectionsContainerEClass = createEClass(PLACE_TRANSITION_CONNECTIONS_CONTAINER);
		createEReference(placeTransitionConnectionsContainerEClass, PLACE_TRANSITION_CONNECTIONS_CONTAINER__DEFINITIONS);

		placeTransitionElementEClass = createEClass(PLACE_TRANSITION_ELEMENT);
		createEReference(placeTransitionElementEClass, PLACE_TRANSITION_ELEMENT__INCOMING_CONNECTIONS);
		createEReference(placeTransitionElementEClass, PLACE_TRANSITION_ELEMENT__OUTGOING_CONNECTIONS);

		probeEClass = createEClass(PROBE);
		createEReference(probeEClass, PROBE__COLOR_REFERENCES);
		createEReference(probeEClass, PROBE__META_ATTRIBUTES);
		createEReference(probeEClass, PROBE__END_PLACE);
		createEAttribute(probeEClass, PROBE__END_TRIGGER);
		createEAttribute(probeEClass, PROBE__NAME);
		createEReference(probeEClass, PROBE__START_PLACE);
		createEAttribute(probeEClass, PROBE__START_TRIGGER);

		probeColorReferenceEClass = createEClass(PROBE_COLOR_REFERENCE);

		probeMetaAttributeEClass = createEClass(PROBE_META_ATTRIBUTE);

		probeMetaAttributesContainerEClass = createEClass(PROBE_META_ATTRIBUTES_CONTAINER);
		createEReference(probeMetaAttributesContainerEClass, PROBE_META_ATTRIBUTES_CONTAINER__ENTRIES);

		probesContainerEClass = createEClass(PROBES_CONTAINER);
		createEReference(probesContainerEClass, PROBES_CONTAINER__DEFINITIONS);

		qpmeDocumentEClass = createEClass(QPME_DOCUMENT);
		createEAttribute(qpmeDocumentEClass, QPME_DOCUMENT__MIXED);
		createEReference(qpmeDocumentEClass, QPME_DOCUMENT__XMLNS_PREFIX_MAP);
		createEReference(qpmeDocumentEClass, QPME_DOCUMENT__XSI_SCHEMA_LOCATION);
		createEReference(qpmeDocumentEClass, QPME_DOCUMENT__NET);

		queueEClass = createEClass(QUEUE);
		createEAttribute(queueEClass, QUEUE__NAME);
		createEAttribute(queueEClass, QUEUE__NUMBER_OF_SERVERS);
		createEAttribute(queueEClass, QUEUE__STRATEGY);

		queueingColorReferenceEClass = createEClass(QUEUEING_COLOR_REFERENCE);
		createEAttribute(queueingColorReferenceEClass, QUEUEING_COLOR_REFERENCE__ALPHA);
		createEAttribute(queueingColorReferenceEClass, QUEUEING_COLOR_REFERENCE__BETA);
		createEAttribute(queueingColorReferenceEClass, QUEUEING_COLOR_REFERENCE__CUT);
		createEAttribute(queueingColorReferenceEClass, QUEUEING_COLOR_REFERENCE__DISTRIBUTION_FUNCTION);
		createEAttribute(queueingColorReferenceEClass, QUEUEING_COLOR_REFERENCE__FREEDOM);
		createEAttribute(queueingColorReferenceEClass, QUEUEING_COLOR_REFERENCE__GAMMA);
		createEAttribute(queueingColorReferenceEClass, QUEUEING_COLOR_REFERENCE__INPUT_FILE);
		createEAttribute(queueingColorReferenceEClass, QUEUEING_COLOR_REFERENCE__LAMBDA);
		createEAttribute(queueingColorReferenceEClass, QUEUEING_COLOR_REFERENCE__MAX);
		createEAttribute(queueingColorReferenceEClass, QUEUEING_COLOR_REFERENCE__MEAN);
		createEAttribute(queueingColorReferenceEClass, QUEUEING_COLOR_REFERENCE__MIN);
		createEAttribute(queueingColorReferenceEClass, QUEUEING_COLOR_REFERENCE__P);
		createEAttribute(queueingColorReferenceEClass, QUEUEING_COLOR_REFERENCE__C);
		createEAttribute(queueingColorReferenceEClass, QUEUEING_COLOR_REFERENCE__PRIORITY);
		createEAttribute(queueingColorReferenceEClass, QUEUEING_COLOR_REFERENCE__RANKING);
		createEAttribute(queueingColorReferenceEClass, QUEUEING_COLOR_REFERENCE__STD_DEV);
		createEAttribute(queueingColorReferenceEClass, QUEUEING_COLOR_REFERENCE__TAU);
		createEAttribute(queueingColorReferenceEClass, QUEUEING_COLOR_REFERENCE__OFFSET);
		createEAttribute(queueingColorReferenceEClass, QUEUEING_COLOR_REFERENCE__SCALE);
		createEAttribute(queueingColorReferenceEClass, QUEUEING_COLOR_REFERENCE__REPLAY_FILE);
		createEAttribute(queueingColorReferenceEClass, QUEUEING_COLOR_REFERENCE__VALUES_FILE);
		createEAttribute(queueingColorReferenceEClass, QUEUEING_COLOR_REFERENCE__PROBABILITIES_FILE);
		createEAttribute(queueingColorReferenceEClass, QUEUEING_COLOR_REFERENCE__CONCURRENCIES_FILE);
		createEAttribute(queueingColorReferenceEClass, QUEUEING_COLOR_REFERENCE__RESPONSETIMES_FILE);
		createEAttribute(queueingColorReferenceEClass, QUEUEING_COLOR_REFERENCE__MARS_FILE);
		createEAttribute(queueingColorReferenceEClass, QUEUEING_COLOR_REFERENCE__WEKA_FILE);

		queueingPetriNetEClass = createEClass(QUEUEING_PETRI_NET);
		createEReference(queueingPetriNetEClass, QUEUEING_PETRI_NET__COLORS);
		createEReference(queueingPetriNetEClass, QUEUEING_PETRI_NET__QUEUES);
		createEReference(queueingPetriNetEClass, QUEUEING_PETRI_NET__PLACES);
		createEReference(queueingPetriNetEClass, QUEUEING_PETRI_NET__TRANSITIONS);
		createEReference(queueingPetriNetEClass, QUEUEING_PETRI_NET__CONNECTIONS);
		createEReference(queueingPetriNetEClass, QUEUEING_PETRI_NET__PROBES);
		createEReference(queueingPetriNetEClass, QUEUEING_PETRI_NET__META_ATTRIBUTES);
		createEAttribute(queueingPetriNetEClass, QUEUEING_PETRI_NET__QPME_VERSION);

		queueingPlaceEClass = createEClass(QUEUEING_PLACE);
		createEReference(queueingPlaceEClass, QUEUEING_PLACE__QUEUE);

		queuesContainerEClass = createEClass(QUEUES_CONTAINER);
		createEReference(queuesContainerEClass, QUEUES_CONTAINER__DEFINITIONS);

		simqpnBatchMeansColorConfigurationEClass = createEClass(SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION);
		createEAttribute(simqpnBatchMeansColorConfigurationEClass, SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__BATCH_SIZE);
		createEAttribute(simqpnBatchMeansColorConfigurationEClass, SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__BUCKET_SIZE);
		createEAttribute(simqpnBatchMeansColorConfigurationEClass, SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__MAX_BUCKETS);
		createEAttribute(simqpnBatchMeansColorConfigurationEClass, SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__MIN_BATCHES);
		createEAttribute(simqpnBatchMeansColorConfigurationEClass, SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__NUM_BMEANS_CORL_TESTED);
		createEAttribute(simqpnBatchMeansColorConfigurationEClass, SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__REQ_ABS_PRC);
		createEAttribute(simqpnBatchMeansColorConfigurationEClass, SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__REQ_REL_PRC);
		createEAttribute(simqpnBatchMeansColorConfigurationEClass, SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__SIGN_LEV);

		simqpnBatchMeansQueueingColorConfigurationEClass = createEClass(SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION);
		createEAttribute(simqpnBatchMeansQueueingColorConfigurationEClass, SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_BATCH_SIZE);
		createEAttribute(simqpnBatchMeansQueueingColorConfigurationEClass, SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_BUCKET_SIZE);
		createEAttribute(simqpnBatchMeansQueueingColorConfigurationEClass, SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_MAX_BUCKETS);
		createEAttribute(simqpnBatchMeansQueueingColorConfigurationEClass, SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_MIN_BATCHES);
		createEAttribute(simqpnBatchMeansQueueingColorConfigurationEClass, SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_NUM_BMEANS_CORL_TESTED);
		createEAttribute(simqpnBatchMeansQueueingColorConfigurationEClass, SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_REQ_ABS_PRC);
		createEAttribute(simqpnBatchMeansQueueingColorConfigurationEClass, SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_REQ_REL_PRC);
		createEAttribute(simqpnBatchMeansQueueingColorConfigurationEClass, SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_SIGN_LEV);

		simqpnConfigurationEClass = createEClass(SIMQPN_CONFIGURATION);
		createEAttribute(simqpnConfigurationEClass, SIMQPN_CONFIGURATION__CONFIGURATION_DESCRIPTION);
		createEAttribute(simqpnConfigurationEClass, SIMQPN_CONFIGURATION__NUMBER_OF_RUNS);
		createEAttribute(simqpnConfigurationEClass, SIMQPN_CONFIGURATION__OUTPUT_DIRECTORY);
		createEAttribute(simqpnConfigurationEClass, SIMQPN_CONFIGURATION__RAMP_UP_LENGTH);
		createEAttribute(simqpnConfigurationEClass, SIMQPN_CONFIGURATION__SCENARIO);
		createEAttribute(simqpnConfigurationEClass, SIMQPN_CONFIGURATION__SECONDS_BETWEEN_HEART_BEATS);
		createEAttribute(simqpnConfigurationEClass, SIMQPN_CONFIGURATION__SECONDS_BETWEEN_STOP_CHECKS);
		createEAttribute(simqpnConfigurationEClass, SIMQPN_CONFIGURATION__STOPPING_RULE);
		createEAttribute(simqpnConfigurationEClass, SIMQPN_CONFIGURATION__TIME_BEFORE_INITIAL_HEART_BEAT);
		createEAttribute(simqpnConfigurationEClass, SIMQPN_CONFIGURATION__TIME_BETWEEN_STOP_CHECKS);
		createEAttribute(simqpnConfigurationEClass, SIMQPN_CONFIGURATION__TOTAL_RUN_LENGTH);
		createEAttribute(simqpnConfigurationEClass, SIMQPN_CONFIGURATION__VERBOSITY_LEVEL);

		simqpnMetaAttributeEClass = createEClass(SIMQPN_META_ATTRIBUTE);
		createEAttribute(simqpnMetaAttributeEClass, SIMQPN_META_ATTRIBUTE__CONFIGURATION_NAME);

		simqpnPlaceConfigurationEClass = createEClass(SIMQPN_PLACE_CONFIGURATION);
		createEAttribute(simqpnPlaceConfigurationEClass, SIMQPN_PLACE_CONFIGURATION__STATS_LEVEL);

		simqpnReplDelColorConfigurationEClass = createEClass(SIMQPN_REPL_DEL_COLOR_CONFIGURATION);
		createEAttribute(simqpnReplDelColorConfigurationEClass, SIMQPN_REPL_DEL_COLOR_CONFIGURATION__SIGN_LEV_AVG_ST);

		simqpnReplDelQueueingColorConfigurationEClass = createEClass(SIMQPN_REPL_DEL_QUEUEING_COLOR_CONFIGURATION);
		createEAttribute(simqpnReplDelQueueingColorConfigurationEClass, SIMQPN_REPL_DEL_QUEUEING_COLOR_CONFIGURATION__QUEUE_SIGN_LEV_AVG_ST);

		simqpnWelchColorConfigurationEClass = createEClass(SIMQPN_WELCH_COLOR_CONFIGURATION);
		createEAttribute(simqpnWelchColorConfigurationEClass, SIMQPN_WELCH_COLOR_CONFIGURATION__MAX_OBSRV);
		createEAttribute(simqpnWelchColorConfigurationEClass, SIMQPN_WELCH_COLOR_CONFIGURATION__MIN_OBSRV);

		simqpnWelchQueueingColorConfigurationEClass = createEClass(SIMQPN_WELCH_QUEUEING_COLOR_CONFIGURATION);
		createEAttribute(simqpnWelchQueueingColorConfigurationEClass, SIMQPN_WELCH_QUEUEING_COLOR_CONFIGURATION__QUEUE_MAX_OBSRV);
		createEAttribute(simqpnWelchQueueingColorConfigurationEClass, SIMQPN_WELCH_QUEUEING_COLOR_CONFIGURATION__QUEUE_MIN_OBSRV);

		subnetEClass = createEClass(SUBNET);
		createEReference(subnetEClass, SUBNET__COLORS);
		createEReference(subnetEClass, SUBNET__PLACES);
		createEReference(subnetEClass, SUBNET__TRANSITIONS);
		createEReference(subnetEClass, SUBNET__CONNECTIONS);

		subnetColorReferenceEClass = createEClass(SUBNET_COLOR_REFERENCE);
		createEAttribute(subnetColorReferenceEClass, SUBNET_COLOR_REFERENCE__DIRECTION);

		subnetPlaceEClass = createEClass(SUBNET_PLACE);
		createEReference(subnetPlaceEClass, SUBNET_PLACE__SUBNET);

		timedTransitionEClass = createEClass(TIMED_TRANSITION);

		transitionEClass = createEClass(TRANSITION);
		createEReference(transitionEClass, TRANSITION__MODES);
		createEReference(transitionEClass, TRANSITION__CONNECTIONS);
		createEReference(transitionEClass, TRANSITION__META_ATTRIBUTES);
		createEAttribute(transitionEClass, TRANSITION__NAME);
		createEAttribute(transitionEClass, TRANSITION__PRIORITY);

		transitionMetaAttributeEClass = createEClass(TRANSITION_META_ATTRIBUTE);

		transitionMetaAttributesContainerEClass = createEClass(TRANSITION_META_ATTRIBUTES_CONTAINER);
		createEReference(transitionMetaAttributesContainerEClass, TRANSITION_META_ATTRIBUTES_CONTAINER__ENTRIES);

		transitionsContainerEClass = createEClass(TRANSITIONS_CONTAINER);
		createEReference(transitionsContainerEClass, TRANSITIONS_CONTAINER__DEFINITIONS);

		// Create enums
		departureDisciplineEEnum = createEEnum(DEPARTURE_DISCIPLINE);
		distributionFunctionEEnum = createEEnum(DISTRIBUTION_FUNCTION);
		flowDirectionEEnum = createEEnum(FLOW_DIRECTION);
		probeTriggerEEnum = createEEnum(PROBE_TRIGGER);
		queueingStrategyEEnum = createEEnum(QUEUEING_STRATEGY);
		simqpnSimulationScenarioEEnum = createEEnum(SIMQPN_SIMULATION_SCENARIO);
		simqpnStoppingRuleEEnum = createEEnum(SIMQPN_STOPPING_RULE);

		// Create data types
		departureDisciplineObjectEDataType = createEDataType(DEPARTURE_DISCIPLINE_OBJECT);
		distributionFunctionObjectEDataType = createEDataType(DISTRIBUTION_FUNCTION_OBJECT);
		flowDirectionObjectEDataType = createEDataType(FLOW_DIRECTION_OBJECT);
		probeTriggerObjectEDataType = createEDataType(PROBE_TRIGGER_OBJECT);
		queueingStrategyObjectEDataType = createEDataType(QUEUEING_STRATEGY_OBJECT);
		rgbValueEDataType = createEDataType(RGB_VALUE);
		simqpnSimulationScenarioObjectEDataType = createEDataType(SIMQPN_SIMULATION_SCENARIO_OBJECT);
		simqpnStoppingRuleObjectEDataType = createEDataType(SIMQPN_STOPPING_RULE_OBJECT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		colorEClass.getESuperTypes().add(this.getIdentifiableElement());
		colorReferenceEClass.getESuperTypes().add(this.getIncidenceFunctionElement());
		immediateTransitionEClass.getESuperTypes().add(this.getTransition());
		incidenceFunctionConnectionEClass.getESuperTypes().add(this.getIdentifiableElement());
		incidenceFunctionElementEClass.getESuperTypes().add(this.getIdentifiableElement());
		locationAttributeEClass.getESuperTypes().add(this.getPlaceMetaAttribute());
		locationAttributeEClass.getESuperTypes().add(this.getTransitionMetaAttribute());
		modeEClass.getESuperTypes().add(this.getIncidenceFunctionElement());
		ordinaryColorReferenceEClass.getESuperTypes().add(this.getPlaceColorReference());
		ordinaryPlaceEClass.getESuperTypes().add(this.getPlace());
		placeEClass.getESuperTypes().add(this.getPlaceTransitionElement());
		placeColorReferenceEClass.getESuperTypes().add(this.getColorReference());
		placeTransitionConnectionEClass.getESuperTypes().add(this.getIdentifiableElement());
		placeTransitionElementEClass.getESuperTypes().add(this.getIdentifiableElement());
		probeEClass.getESuperTypes().add(this.getIdentifiableElement());
		probeColorReferenceEClass.getESuperTypes().add(this.getColorReference());
		queueEClass.getESuperTypes().add(this.getIdentifiableElement());
		queueingColorReferenceEClass.getESuperTypes().add(this.getPlaceColorReference());
		queueingPlaceEClass.getESuperTypes().add(this.getPlace());
		simqpnBatchMeansColorConfigurationEClass.getESuperTypes().add(this.getSimqpnMetaAttribute());
		simqpnBatchMeansColorConfigurationEClass.getESuperTypes().add(this.getColorReferenceMetaAttribute());
		simqpnBatchMeansQueueingColorConfigurationEClass.getESuperTypes().add(this.getSimqpnBatchMeansColorConfiguration());
		simqpnConfigurationEClass.getESuperTypes().add(this.getSimqpnMetaAttribute());
		simqpnConfigurationEClass.getESuperTypes().add(this.getNetMetaAttribute());
		simqpnMetaAttributeEClass.getESuperTypes().add(this.getIdentifiableElement());
		simqpnPlaceConfigurationEClass.getESuperTypes().add(this.getSimqpnMetaAttribute());
		simqpnPlaceConfigurationEClass.getESuperTypes().add(this.getPlaceMetaAttribute());
		simqpnPlaceConfigurationEClass.getESuperTypes().add(this.getProbeMetaAttribute());
		simqpnReplDelColorConfigurationEClass.getESuperTypes().add(this.getSimqpnMetaAttribute());
		simqpnReplDelColorConfigurationEClass.getESuperTypes().add(this.getColorReferenceMetaAttribute());
		simqpnReplDelQueueingColorConfigurationEClass.getESuperTypes().add(this.getSimqpnReplDelColorConfiguration());
		simqpnWelchColorConfigurationEClass.getESuperTypes().add(this.getSimqpnMetaAttribute());
		simqpnWelchColorConfigurationEClass.getESuperTypes().add(this.getColorReferenceMetaAttribute());
		simqpnWelchQueueingColorConfigurationEClass.getESuperTypes().add(this.getSimqpnWelchColorConfiguration());
		subnetColorReferenceEClass.getESuperTypes().add(this.getPlaceColorReference());
		subnetPlaceEClass.getESuperTypes().add(this.getPlace());
		timedTransitionEClass.getESuperTypes().add(this.getTransition());
		transitionEClass.getESuperTypes().add(this.getPlaceTransitionElement());

		// Initialize classes and features; add operations and parameters
		initEClass(colorEClass, Color.class, "Color", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getColor_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, Color.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColor_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, Color.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColor_RealColor(), this.getRgbValue(), "realColor", null, 1, 1, Color.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(colorReferenceEClass, ColorReference.class, "ColorReference", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getColorReference_MetaAttributes(), this.getColorReferencesMetaAttributesContainer(), null, "metaAttributes", null, 0, 1, ColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getColorReference_Color(), this.getColor(), null, "color", null, 1, 1, ColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(colorReferenceMetaAttributeEClass, ColorReferenceMetaAttribute.class, "ColorReferenceMetaAttribute", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(colorReferencesContainerEClass, ColorReferencesContainer.class, "ColorReferencesContainer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getColorReferencesContainer_Definitions(), this.getColorReference(), null, "definitions", null, 0, -1, ColorReferencesContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(colorReferencesMetaAttributesContainerEClass, ColorReferencesMetaAttributesContainer.class, "ColorReferencesMetaAttributesContainer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getColorReferencesMetaAttributesContainer_Entries(), this.getColorReferenceMetaAttribute(), null, "entries", null, 0, -1, ColorReferencesMetaAttributesContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(colorsContainerEClass, ColorsContainer.class, "ColorsContainer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getColorsContainer_Definitions(), this.getColor(), null, "definitions", null, 0, -1, ColorsContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(identifiableElementEClass, IdentifiableElement.class, "IdentifiableElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIdentifiableElement_Id(), theXMLTypePackage.getID(), "id", null, 1, 1, IdentifiableElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(immediateTransitionEClass, ImmediateTransition.class, "ImmediateTransition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getImmediateTransition_Weight(), theXMLTypePackage.getDouble(), "weight", null, 1, 1, ImmediateTransition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(incidenceFunctionConnectionEClass, IncidenceFunctionConnection.class, "IncidenceFunctionConnection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIncidenceFunctionConnection_Count(), theXMLTypePackage.getUnsignedInt(), "count", null, 1, 1, IncidenceFunctionConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIncidenceFunctionConnection_Source(), this.getIncidenceFunctionElement(), this.getIncidenceFunctionElement_OutgoingConnections(), "source", null, 1, 1, IncidenceFunctionConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIncidenceFunctionConnection_Target(), this.getIncidenceFunctionElement(), this.getIncidenceFunctionElement_IncomingConnections(), "target", null, 1, 1, IncidenceFunctionConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(incidenceFunctionConnectionsContainerEClass, IncidenceFunctionConnectionsContainer.class, "IncidenceFunctionConnectionsContainer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIncidenceFunctionConnectionsContainer_Definitions(), this.getIncidenceFunctionConnection(), null, "definitions", null, 0, -1, IncidenceFunctionConnectionsContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(incidenceFunctionElementEClass, IncidenceFunctionElement.class, "IncidenceFunctionElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIncidenceFunctionElement_IncomingConnections(), this.getIncidenceFunctionConnection(), this.getIncidenceFunctionConnection_Target(), "incomingConnections", null, 0, -1, IncidenceFunctionElement.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getIncidenceFunctionElement_OutgoingConnections(), this.getIncidenceFunctionConnection(), this.getIncidenceFunctionConnection_Source(), "outgoingConnections", null, 0, -1, IncidenceFunctionElement.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(locationAttributeEClass, LocationAttribute.class, "LocationAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLocationAttribute_LocationX(), theXMLTypePackage.getInt(), "locationX", null, 1, 1, LocationAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLocationAttribute_LocationY(), theXMLTypePackage.getInt(), "locationY", null, 1, 1, LocationAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(modeEClass, Mode.class, "Mode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMode_FiringWeight(), theXMLTypePackage.getDouble(), "firingWeight", null, 0, 1, Mode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMode_MeanFiringDelay(), theXMLTypePackage.getDouble(), "meanFiringDelay", null, 0, 1, Mode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMode_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, Mode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMode_RealColor(), this.getRgbValue(), "realColor", null, 1, 1, Mode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(modesContainerEClass, ModesContainer.class, "ModesContainer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getModesContainer_Definitions(), this.getMode(), null, "definitions", null, 0, -1, ModesContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(netMetaAttributeEClass, NetMetaAttribute.class, "NetMetaAttribute", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(netMetaAttributesContainerEClass, NetMetaAttributesContainer.class, "NetMetaAttributesContainer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNetMetaAttributesContainer_Entries(), this.getNetMetaAttribute(), null, "entries", null, 0, -1, NetMetaAttributesContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ordinaryColorReferenceEClass, OrdinaryColorReference.class, "OrdinaryColorReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(ordinaryPlaceEClass, OrdinaryPlace.class, "OrdinaryPlace", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(placeEClass, Place.class, "Place", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPlace_ColorReferences(), this.getColorReferencesContainer(), null, "colorReferences", null, 0, 1, Place.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPlace_MetaAttributes(), this.getPlaceMetaAttributesContainer(), null, "metaAttributes", null, 0, 1, Place.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPlace_DepartureDiscipline(), this.getDepartureDiscipline(), "departureDiscipline", null, 1, 1, Place.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPlace_Locked(), theXMLTypePackage.getBoolean(), "locked", "false", 0, 1, Place.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPlace_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, Place.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(placeColorReferenceEClass, PlaceColorReference.class, "PlaceColorReference", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPlaceColorReference_InitialPopulation(), theXMLTypePackage.getUnsignedInt(), "initialPopulation", "0", 0, 1, PlaceColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPlaceColorReference_MaximumCapacity(), theXMLTypePackage.getUnsignedInt(), "maximumCapacity", "0", 0, 1, PlaceColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(placeMetaAttributeEClass, PlaceMetaAttribute.class, "PlaceMetaAttribute", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(placeMetaAttributesContainerEClass, PlaceMetaAttributesContainer.class, "PlaceMetaAttributesContainer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPlaceMetaAttributesContainer_Entries(), this.getPlaceMetaAttribute(), null, "entries", null, 0, -1, PlaceMetaAttributesContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(placesContainerEClass, PlacesContainer.class, "PlacesContainer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPlacesContainer_Definitions(), this.getPlace(), null, "definitions", null, 0, -1, PlacesContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(placeTransitionConnectionEClass, PlaceTransitionConnection.class, "PlaceTransitionConnection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPlaceTransitionConnection_Source(), this.getPlaceTransitionElement(), this.getPlaceTransitionElement_OutgoingConnections(), "source", null, 1, 1, PlaceTransitionConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPlaceTransitionConnection_Target(), this.getPlaceTransitionElement(), this.getPlaceTransitionElement_IncomingConnections(), "target", null, 1, 1, PlaceTransitionConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(placeTransitionConnectionsContainerEClass, PlaceTransitionConnectionsContainer.class, "PlaceTransitionConnectionsContainer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPlaceTransitionConnectionsContainer_Definitions(), this.getPlaceTransitionConnection(), null, "definitions", null, 0, -1, PlaceTransitionConnectionsContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(placeTransitionElementEClass, PlaceTransitionElement.class, "PlaceTransitionElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPlaceTransitionElement_IncomingConnections(), this.getPlaceTransitionConnection(), this.getPlaceTransitionConnection_Target(), "incomingConnections", null, 0, -1, PlaceTransitionElement.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getPlaceTransitionElement_OutgoingConnections(), this.getPlaceTransitionConnection(), this.getPlaceTransitionConnection_Source(), "outgoingConnections", null, 0, -1, PlaceTransitionElement.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(probeEClass, Probe.class, "Probe", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProbe_ColorReferences(), this.getColorReferencesContainer(), null, "colorReferences", null, 0, 1, Probe.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProbe_MetaAttributes(), this.getProbeMetaAttributesContainer(), null, "metaAttributes", null, 0, 1, Probe.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProbe_EndPlace(), this.getPlace(), null, "endPlace", null, 1, 1, Probe.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProbe_EndTrigger(), this.getProbeTrigger(), "endTrigger", null, 1, 1, Probe.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProbe_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, Probe.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProbe_StartPlace(), this.getPlace(), null, "startPlace", null, 1, 1, Probe.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProbe_StartTrigger(), this.getProbeTrigger(), "startTrigger", null, 1, 1, Probe.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(probeColorReferenceEClass, ProbeColorReference.class, "ProbeColorReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(probeMetaAttributeEClass, ProbeMetaAttribute.class, "ProbeMetaAttribute", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(probeMetaAttributesContainerEClass, ProbeMetaAttributesContainer.class, "ProbeMetaAttributesContainer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProbeMetaAttributesContainer_Entries(), this.getProbeMetaAttribute(), null, "entries", null, 0, -1, ProbeMetaAttributesContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(probesContainerEClass, ProbesContainer.class, "ProbesContainer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProbesContainer_Definitions(), this.getProbe(), null, "definitions", null, 0, -1, ProbesContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(qpmeDocumentEClass, QpmeDocument.class, "QpmeDocument", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getQpmeDocument_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getQpmeDocument_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getQpmeDocument_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getQpmeDocument_Net(), this.getQueueingPetriNet(), null, "net", null, 0, -1, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(queueEClass, Queue.class, "Queue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getQueue_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, Queue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueue_NumberOfServers(), theXMLTypePackage.getInt(), "numberOfServers", null, 1, 1, Queue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueue_Strategy(), this.getQueueingStrategy(), "strategy", null, 1, 1, Queue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(queueingColorReferenceEClass, QueueingColorReference.class, "QueueingColorReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getQueueingColorReference_Alpha(), theXMLTypePackage.getDouble(), "alpha", null, 0, 1, QueueingColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueueingColorReference_Beta(), theXMLTypePackage.getDouble(), "beta", null, 0, 1, QueueingColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueueingColorReference_Cut(), theXMLTypePackage.getDouble(), "cut", null, 0, 1, QueueingColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueueingColorReference_DistributionFunction(), this.getDistributionFunction(), "distributionFunction", null, 1, 1, QueueingColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueueingColorReference_Freedom(), theXMLTypePackage.getDouble(), "freedom", null, 0, 1, QueueingColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueueingColorReference_Gamma(), theXMLTypePackage.getDouble(), "gamma", null, 0, 1, QueueingColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueueingColorReference_InputFile(), theXMLTypePackage.getString(), "inputFile", null, 0, 1, QueueingColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueueingColorReference_Lambda(), theXMLTypePackage.getDouble(), "lambda", null, 0, 1, QueueingColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueueingColorReference_Max(), theXMLTypePackage.getDouble(), "max", null, 0, 1, QueueingColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueueingColorReference_Mean(), theXMLTypePackage.getDouble(), "mean", null, 0, 1, QueueingColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueueingColorReference_Min(), theXMLTypePackage.getDouble(), "min", null, 0, 1, QueueingColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueueingColorReference_P(), theXMLTypePackage.getDouble(), "p", null, 0, 1, QueueingColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueueingColorReference_C(), theXMLTypePackage.getDouble(), "c", null, 0, 1, QueueingColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueueingColorReference_Priority(), theXMLTypePackage.getUnsignedInt(), "priority", null, 1, 1, QueueingColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueueingColorReference_Ranking(), theXMLTypePackage.getUnsignedInt(), "ranking", null, 1, 1, QueueingColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueueingColorReference_StdDev(), theXMLTypePackage.getDouble(), "stdDev", null, 0, 1, QueueingColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueueingColorReference_Tau(), theXMLTypePackage.getDouble(), "tau", null, 0, 1, QueueingColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueueingColorReference_Offset(), theXMLTypePackage.getDouble(), "offset", null, 0, 1, QueueingColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueueingColorReference_Scale(), theXMLTypePackage.getDouble(), "scale", null, 0, 1, QueueingColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueueingColorReference_ReplayFile(), theXMLTypePackage.getString(), "replayFile", null, 0, 1, QueueingColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueueingColorReference_ValuesFile(), theXMLTypePackage.getString(), "valuesFile", null, 0, 1, QueueingColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueueingColorReference_ProbabilitiesFile(), theXMLTypePackage.getString(), "probabilitiesFile", null, 0, 1, QueueingColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueueingColorReference_ConcurrenciesFile(), ecorePackage.getEString(), "concurrenciesFile", null, 0, 1, QueueingColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueueingColorReference_ResponsetimesFile(), ecorePackage.getEString(), "responsetimesFile", null, 0, 1, QueueingColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueueingColorReference_MarsFile(), ecorePackage.getEString(), "marsFile", null, 0, 1, QueueingColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueueingColorReference_WekaFile(), ecorePackage.getEString(), "wekaFile", null, 0, 1, QueueingColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(queueingPetriNetEClass, QueueingPetriNet.class, "QueueingPetriNet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getQueueingPetriNet_Colors(), this.getColorsContainer(), null, "colors", null, 0, 1, QueueingPetriNet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getQueueingPetriNet_Queues(), this.getQueuesContainer(), null, "queues", null, 0, 1, QueueingPetriNet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getQueueingPetriNet_Places(), this.getPlacesContainer(), null, "places", null, 0, 1, QueueingPetriNet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getQueueingPetriNet_Transitions(), this.getTransitionsContainer(), null, "transitions", null, 0, 1, QueueingPetriNet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getQueueingPetriNet_Connections(), this.getPlaceTransitionConnectionsContainer(), null, "connections", null, 0, 1, QueueingPetriNet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getQueueingPetriNet_Probes(), this.getProbesContainer(), null, "probes", null, 0, 1, QueueingPetriNet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getQueueingPetriNet_MetaAttributes(), this.getNetMetaAttributesContainer(), null, "metaAttributes", null, 0, 1, QueueingPetriNet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueueingPetriNet_QpmeVersion(), theXMLTypePackage.getString(), "qpmeVersion", null, 1, 1, QueueingPetriNet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(queueingPlaceEClass, QueueingPlace.class, "QueueingPlace", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getQueueingPlace_Queue(), this.getQueue(), null, "queue", null, 1, 1, QueueingPlace.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(queuesContainerEClass, QueuesContainer.class, "QueuesContainer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getQueuesContainer_Definitions(), this.getQueue(), null, "definitions", null, 0, -1, QueuesContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(simqpnBatchMeansColorConfigurationEClass, SimqpnBatchMeansColorConfiguration.class, "SimqpnBatchMeansColorConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSimqpnBatchMeansColorConfiguration_BatchSize(), theXMLTypePackage.getUnsignedInt(), "batchSize", null, 1, 1, SimqpnBatchMeansColorConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnBatchMeansColorConfiguration_BucketSize(), theXMLTypePackage.getDouble(), "bucketSize", null, 1, 1, SimqpnBatchMeansColorConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnBatchMeansColorConfiguration_MaxBuckets(), theXMLTypePackage.getUnsignedInt(), "maxBuckets", null, 1, 1, SimqpnBatchMeansColorConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnBatchMeansColorConfiguration_MinBatches(), theXMLTypePackage.getUnsignedInt(), "minBatches", null, 1, 1, SimqpnBatchMeansColorConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnBatchMeansColorConfiguration_NumBMeansCorlTested(), theXMLTypePackage.getUnsignedInt(), "numBMeansCorlTested", null, 1, 1, SimqpnBatchMeansColorConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnBatchMeansColorConfiguration_ReqAbsPrc(), theXMLTypePackage.getDouble(), "reqAbsPrc", null, 1, 1, SimqpnBatchMeansColorConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnBatchMeansColorConfiguration_ReqRelPrc(), theXMLTypePackage.getDouble(), "reqRelPrc", null, 1, 1, SimqpnBatchMeansColorConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnBatchMeansColorConfiguration_SignLev(), theXMLTypePackage.getDouble(), "signLev", null, 1, 1, SimqpnBatchMeansColorConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(simqpnBatchMeansQueueingColorConfigurationEClass, SimqpnBatchMeansQueueingColorConfiguration.class, "SimqpnBatchMeansQueueingColorConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSimqpnBatchMeansQueueingColorConfiguration_QueueBatchSize(), theXMLTypePackage.getUnsignedInt(), "queueBatchSize", null, 1, 1, SimqpnBatchMeansQueueingColorConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnBatchMeansQueueingColorConfiguration_QueueBucketSize(), theXMLTypePackage.getDouble(), "queueBucketSize", null, 1, 1, SimqpnBatchMeansQueueingColorConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnBatchMeansQueueingColorConfiguration_QueueMaxBuckets(), theXMLTypePackage.getUnsignedInt(), "queueMaxBuckets", null, 1, 1, SimqpnBatchMeansQueueingColorConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnBatchMeansQueueingColorConfiguration_QueueMinBatches(), theXMLTypePackage.getUnsignedInt(), "queueMinBatches", null, 1, 1, SimqpnBatchMeansQueueingColorConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnBatchMeansQueueingColorConfiguration_QueueNumBMeansCorlTested(), theXMLTypePackage.getUnsignedInt(), "queueNumBMeansCorlTested", null, 1, 1, SimqpnBatchMeansQueueingColorConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnBatchMeansQueueingColorConfiguration_QueueReqAbsPrc(), theXMLTypePackage.getDouble(), "queueReqAbsPrc", null, 1, 1, SimqpnBatchMeansQueueingColorConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnBatchMeansQueueingColorConfiguration_QueueReqRelPrc(), theXMLTypePackage.getDouble(), "queueReqRelPrc", null, 1, 1, SimqpnBatchMeansQueueingColorConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnBatchMeansQueueingColorConfiguration_QueueSignLev(), theXMLTypePackage.getDouble(), "queueSignLev", null, 1, 1, SimqpnBatchMeansQueueingColorConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(simqpnConfigurationEClass, SimqpnConfiguration.class, "SimqpnConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSimqpnConfiguration_ConfigurationDescription(), theXMLTypePackage.getString(), "configurationDescription", null, 0, 1, SimqpnConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnConfiguration_NumberOfRuns(), theXMLTypePackage.getInt(), "numberOfRuns", "1", 0, 1, SimqpnConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnConfiguration_OutputDirectory(), theXMLTypePackage.getString(), "outputDirectory", null, 1, 1, SimqpnConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnConfiguration_RampUpLength(), theXMLTypePackage.getDouble(), "rampUpLength", null, 1, 1, SimqpnConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnConfiguration_Scenario(), this.getSimqpnSimulationScenario(), "scenario", null, 1, 1, SimqpnConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnConfiguration_SecondsBetweenHeartBeats(), theXMLTypePackage.getDouble(), "secondsBetweenHeartBeats", null, 1, 1, SimqpnConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnConfiguration_SecondsBetweenStopChecks(), theXMLTypePackage.getDouble(), "secondsBetweenStopChecks", null, 1, 1, SimqpnConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnConfiguration_StoppingRule(), this.getSimqpnStoppingRule(), "stoppingRule", null, 1, 1, SimqpnConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnConfiguration_TimeBeforeInitialHeartBeat(), theXMLTypePackage.getDouble(), "timeBeforeInitialHeartBeat", null, 1, 1, SimqpnConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnConfiguration_TimeBetweenStopChecks(), theXMLTypePackage.getDouble(), "timeBetweenStopChecks", null, 1, 1, SimqpnConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnConfiguration_TotalRunLength(), theXMLTypePackage.getDouble(), "totalRunLength", null, 1, 1, SimqpnConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnConfiguration_VerbosityLevel(), theXMLTypePackage.getInt(), "verbosityLevel", null, 1, 1, SimqpnConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(simqpnMetaAttributeEClass, SimqpnMetaAttribute.class, "SimqpnMetaAttribute", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSimqpnMetaAttribute_ConfigurationName(), theXMLTypePackage.getString(), "configurationName", null, 1, 1, SimqpnMetaAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(simqpnPlaceConfigurationEClass, SimqpnPlaceConfiguration.class, "SimqpnPlaceConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSimqpnPlaceConfiguration_StatsLevel(), theXMLTypePackage.getInteger(), "statsLevel", null, 1, 1, SimqpnPlaceConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(simqpnReplDelColorConfigurationEClass, SimqpnReplDelColorConfiguration.class, "SimqpnReplDelColorConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSimqpnReplDelColorConfiguration_SignLevAvgST(), theXMLTypePackage.getDouble(), "signLevAvgST", null, 1, 1, SimqpnReplDelColorConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(simqpnReplDelQueueingColorConfigurationEClass, SimqpnReplDelQueueingColorConfiguration.class, "SimqpnReplDelQueueingColorConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSimqpnReplDelQueueingColorConfiguration_QueueSignLevAvgST(), theXMLTypePackage.getDouble(), "queueSignLevAvgST", null, 1, 1, SimqpnReplDelQueueingColorConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(simqpnWelchColorConfigurationEClass, SimqpnWelchColorConfiguration.class, "SimqpnWelchColorConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSimqpnWelchColorConfiguration_MaxObsrv(), theXMLTypePackage.getUnsignedInt(), "maxObsrv", null, 1, 1, SimqpnWelchColorConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnWelchColorConfiguration_MinObsrv(), theXMLTypePackage.getUnsignedInt(), "minObsrv", null, 1, 1, SimqpnWelchColorConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(simqpnWelchQueueingColorConfigurationEClass, SimqpnWelchQueueingColorConfiguration.class, "SimqpnWelchQueueingColorConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSimqpnWelchQueueingColorConfiguration_QueueMaxObsrv(), theXMLTypePackage.getUnsignedInt(), "queueMaxObsrv", null, 1, 1, SimqpnWelchQueueingColorConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnWelchQueueingColorConfiguration_QueueMinObsrv(), theXMLTypePackage.getUnsignedInt(), "queueMinObsrv", null, 1, 1, SimqpnWelchQueueingColorConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(subnetEClass, Subnet.class, "Subnet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSubnet_Colors(), this.getColorsContainer(), null, "colors", null, 0, 1, Subnet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSubnet_Places(), this.getPlacesContainer(), null, "places", null, 0, 1, Subnet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSubnet_Transitions(), this.getTransitionsContainer(), null, "transitions", null, 0, 1, Subnet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSubnet_Connections(), this.getPlaceTransitionConnectionsContainer(), null, "connections", null, 0, 1, Subnet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(subnetColorReferenceEClass, SubnetColorReference.class, "SubnetColorReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSubnetColorReference_Direction(), this.getFlowDirection(), "direction", null, 1, 1, SubnetColorReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(subnetPlaceEClass, SubnetPlace.class, "SubnetPlace", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSubnetPlace_Subnet(), this.getSubnet(), null, "subnet", null, 0, 1, SubnetPlace.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(timedTransitionEClass, TimedTransition.class, "TimedTransition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(transitionEClass, Transition.class, "Transition", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTransition_Modes(), this.getModesContainer(), null, "modes", null, 0, 1, Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTransition_Connections(), this.getIncidenceFunctionConnectionsContainer(), null, "connections", null, 0, 1, Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTransition_MetaAttributes(), this.getTransitionMetaAttributesContainer(), null, "metaAttributes", null, 0, 1, Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTransition_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTransition_Priority(), theXMLTypePackage.getUnsignedInt(), "priority", null, 1, 1, Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(transitionMetaAttributeEClass, TransitionMetaAttribute.class, "TransitionMetaAttribute", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(transitionMetaAttributesContainerEClass, TransitionMetaAttributesContainer.class, "TransitionMetaAttributesContainer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTransitionMetaAttributesContainer_Entries(), this.getTransitionMetaAttribute(), null, "entries", null, 0, -1, TransitionMetaAttributesContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(transitionsContainerEClass, TransitionsContainer.class, "TransitionsContainer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTransitionsContainer_Definitions(), this.getTransition(), null, "definitions", null, 0, -1, TransitionsContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(departureDisciplineEEnum, DepartureDiscipline.class, "DepartureDiscipline");
		addEEnumLiteral(departureDisciplineEEnum, DepartureDiscipline.NORMAL);
		addEEnumLiteral(departureDisciplineEEnum, DepartureDiscipline.FIFO);

		initEEnum(distributionFunctionEEnum, DistributionFunction.class, "DistributionFunction");
		addEEnumLiteral(distributionFunctionEEnum, DistributionFunction.BETA);
		addEEnumLiteral(distributionFunctionEEnum, DistributionFunction.BREIT_WIGNER);
		addEEnumLiteral(distributionFunctionEEnum, DistributionFunction.BREIT_WIGNER_SQUARE);
		addEEnumLiteral(distributionFunctionEEnum, DistributionFunction.CHI_SQUARE);
		addEEnumLiteral(distributionFunctionEEnum, DistributionFunction.GAMMA);
		addEEnumLiteral(distributionFunctionEEnum, DistributionFunction.HYPERBOLIC);
		addEEnumLiteral(distributionFunctionEEnum, DistributionFunction.EXPONENTIAL);
		addEEnumLiteral(distributionFunctionEEnum, DistributionFunction.EXPONENTIAL_POWER);
		addEEnumLiteral(distributionFunctionEEnum, DistributionFunction.LOGARITHMIC);
		addEEnumLiteral(distributionFunctionEEnum, DistributionFunction.NORMAL);
		addEEnumLiteral(distributionFunctionEEnum, DistributionFunction.STUDENT_T);
		addEEnumLiteral(distributionFunctionEEnum, DistributionFunction.UNIFORM);
		addEEnumLiteral(distributionFunctionEEnum, DistributionFunction.VON_MISES);
		addEEnumLiteral(distributionFunctionEEnum, DistributionFunction.EMPIRICAL);
		addEEnumLiteral(distributionFunctionEEnum, DistributionFunction.DETERMINISTIC);
		addEEnumLiteral(distributionFunctionEEnum, DistributionFunction.SCALED_EMPIRICAL);
		addEEnumLiteral(distributionFunctionEEnum, DistributionFunction.REPLAY);
		addEEnumLiteral(distributionFunctionEEnum, DistributionFunction.DISCRETE_EMPIRICAL);
		addEEnumLiteral(distributionFunctionEEnum, DistributionFunction.CONTINUOUS_EMPIRICAL);
		addEEnumLiteral(distributionFunctionEEnum, DistributionFunction.DETERMINISTIC_CONCURRENCY);
		addEEnumLiteral(distributionFunctionEEnum, DistributionFunction.MARS);
		addEEnumLiteral(distributionFunctionEEnum, DistributionFunction.WEKA);

		initEEnum(flowDirectionEEnum, FlowDirection.class, "FlowDirection");
		addEEnumLiteral(flowDirectionEEnum, FlowDirection.IN);
		addEEnumLiteral(flowDirectionEEnum, FlowDirection.OUT);
		addEEnumLiteral(flowDirectionEEnum, FlowDirection.BOTH);

		initEEnum(probeTriggerEEnum, ProbeTrigger.class, "ProbeTrigger");
		addEEnumLiteral(probeTriggerEEnum, ProbeTrigger.ENTRY);
		addEEnumLiteral(probeTriggerEEnum, ProbeTrigger.EXIT);

		initEEnum(queueingStrategyEEnum, QueueingStrategy.class, "QueueingStrategy");
		addEEnumLiteral(queueingStrategyEEnum, QueueingStrategy.PRIO);
		addEEnumLiteral(queueingStrategyEEnum, QueueingStrategy.PS);
		addEEnumLiteral(queueingStrategyEEnum, QueueingStrategy.FCFS);
		addEEnumLiteral(queueingStrategyEEnum, QueueingStrategy.IS);
		addEEnumLiteral(queueingStrategyEEnum, QueueingStrategy.RANDOM);

		initEEnum(simqpnSimulationScenarioEEnum, SimqpnSimulationScenario.class, "SimqpnSimulationScenario");
		addEEnumLiteral(simqpnSimulationScenarioEEnum, SimqpnSimulationScenario.BATCH_MEANS);
		addEEnumLiteral(simqpnSimulationScenarioEEnum, SimqpnSimulationScenario.REPLICATION_DELETION);
		addEEnumLiteral(simqpnSimulationScenarioEEnum, SimqpnSimulationScenario.METHOD_OF_WELCH);

		initEEnum(simqpnStoppingRuleEEnum, SimqpnStoppingRule.class, "SimqpnStoppingRule");
		addEEnumLiteral(simqpnStoppingRuleEEnum, SimqpnStoppingRule.ABSOLUTE_PRECISION);
		addEEnumLiteral(simqpnStoppingRuleEEnum, SimqpnStoppingRule.RELATIVE_PRECISION);
		addEEnumLiteral(simqpnStoppingRuleEEnum, SimqpnStoppingRule.FIXED_LENGTH);

		// Initialize data types
		initEDataType(departureDisciplineObjectEDataType, DepartureDiscipline.class, "DepartureDisciplineObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(distributionFunctionObjectEDataType, DistributionFunction.class, "DistributionFunctionObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(flowDirectionObjectEDataType, FlowDirection.class, "FlowDirectionObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(probeTriggerObjectEDataType, ProbeTrigger.class, "ProbeTriggerObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(queueingStrategyObjectEDataType, QueueingStrategy.class, "QueueingStrategyObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(rgbValueEDataType, String.class, "RgbValue", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(simqpnSimulationScenarioObjectEDataType, SimqpnSimulationScenario.class, "SimqpnSimulationScenarioObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(simqpnStoppingRuleObjectEDataType, SimqpnStoppingRule.class, "SimqpnStoppingRuleObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";
		addAnnotation
		  (this,
		   source,
		   new String[] {
			   "qualified", "false"
		   });
		addAnnotation
		  (colorEClass,
		   source,
		   new String[] {
			   "name", "color",
			   "kind", "empty"
		   });
		addAnnotation
		  (getColor_Description(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "description",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getColor_Name(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "name",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getColor_RealColor(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "real-color",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (colorReferenceEClass,
		   source,
		   new String[] {
			   "name", "color-reference",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getColorReference_MetaAttributes(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "meta-attributes",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getColorReference_Color(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "color-id",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (colorReferenceMetaAttributeEClass,
		   source,
		   new String[] {
			   "name", "color-reference-meta-attribute",
			   "kind", "empty"
		   });
		addAnnotation
		  (colorReferencesContainerEClass,
		   source,
		   new String[] {
			   "name", "color-references-container",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getColorReferencesContainer_Definitions(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "color-ref",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (colorReferencesMetaAttributesContainerEClass,
		   source,
		   new String[] {
			   "name", "color-reference-meta-attributes-container",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getColorReferencesMetaAttributesContainer_Entries(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "meta-attribute",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (colorsContainerEClass,
		   source,
		   new String[] {
			   "name", "colors-container",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getColorsContainer_Definitions(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "color",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (departureDisciplineEEnum,
		   source,
		   new String[] {
			   "name", "departure-discipline"
		   });
		addAnnotation
		  (departureDisciplineObjectEDataType,
		   source,
		   new String[] {
			   "name", "departure-discipline:Object",
			   "baseType", "departure-discipline"
		   });
		addAnnotation
		  (distributionFunctionEEnum,
		   source,
		   new String[] {
			   "name", "distribution-function"
		   });
		addAnnotation
		  (distributionFunctionObjectEDataType,
		   source,
		   new String[] {
			   "name", "distribution-function:Object",
			   "baseType", "distribution-function"
		   });
		addAnnotation
		  (flowDirectionEEnum,
		   source,
		   new String[] {
			   "name", "flow-direction"
		   });
		addAnnotation
		  (flowDirectionObjectEDataType,
		   source,
		   new String[] {
			   "name", "flow-direction:Object",
			   "baseType", "flow-direction"
		   });
		addAnnotation
		  (identifiableElementEClass,
		   source,
		   new String[] {
			   "name", "identifiable-element",
			   "kind", "empty"
		   });
		addAnnotation
		  (getIdentifiableElement_Id(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "id",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (immediateTransitionEClass,
		   source,
		   new String[] {
			   "name", "immediate-transition",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getImmediateTransition_Weight(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "weight",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (incidenceFunctionConnectionEClass,
		   source,
		   new String[] {
			   "name", "incidence-function-connection",
			   "kind", "empty"
		   });
		addAnnotation
		  (getIncidenceFunctionConnection_Count(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "count",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getIncidenceFunctionConnection_Source(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "source-id",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getIncidenceFunctionConnection_Target(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "target-id",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (incidenceFunctionConnectionsContainerEClass,
		   source,
		   new String[] {
			   "name", "incidence-function-connections-container",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getIncidenceFunctionConnectionsContainer_Definitions(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "connection",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (incidenceFunctionElementEClass,
		   source,
		   new String[] {
			   "name", "incidence-function-element",
			   "kind", "empty"
		   });
		addAnnotation
		  (getIncidenceFunctionElement_IncomingConnections(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "incoming-connections",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getIncidenceFunctionElement_OutgoingConnections(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "outgoing-connections",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (locationAttributeEClass,
		   source,
		   new String[] {
			   "name", "location-attribute",
			   "kind", "empty"
		   });
		addAnnotation
		  (getLocationAttribute_LocationX(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "location-x",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getLocationAttribute_LocationY(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "location-y",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (modeEClass,
		   source,
		   new String[] {
			   "name", "mode",
			   "kind", "empty"
		   });
		addAnnotation
		  (getMode_FiringWeight(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "firing-weight",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getMode_MeanFiringDelay(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "mean-firing-delay",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getMode_Name(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "name",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getMode_RealColor(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "real-color",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (modesContainerEClass,
		   source,
		   new String[] {
			   "name", "modes-container",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getModesContainer_Definitions(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "mode",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (netMetaAttributeEClass,
		   source,
		   new String[] {
			   "name", "net-meta-attribute",
			   "kind", "empty"
		   });
		addAnnotation
		  (netMetaAttributesContainerEClass,
		   source,
		   new String[] {
			   "name", "net-meta-attributes-container",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getNetMetaAttributesContainer_Entries(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "meta-attribute",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (ordinaryColorReferenceEClass,
		   source,
		   new String[] {
			   "name", "ordinary-color-reference",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (ordinaryPlaceEClass,
		   source,
		   new String[] {
			   "name", "ordinary-place",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (placeEClass,
		   source,
		   new String[] {
			   "name", "place",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getPlace_ColorReferences(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "color-refs",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getPlace_MetaAttributes(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "meta-attributes",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getPlace_DepartureDiscipline(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "departure-discipline",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getPlace_Locked(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "locked",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getPlace_Name(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "name",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (placeColorReferenceEClass,
		   source,
		   new String[] {
			   "name", "place-color-reference",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getPlaceColorReference_InitialPopulation(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "initial-population",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getPlaceColorReference_MaximumCapacity(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "maximum-capacity",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (placeMetaAttributeEClass,
		   source,
		   new String[] {
			   "name", "place-meta-attribute",
			   "kind", "empty"
		   });
		addAnnotation
		  (placeMetaAttributesContainerEClass,
		   source,
		   new String[] {
			   "name", "place-meta-attributes-container",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getPlaceMetaAttributesContainer_Entries(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "meta-attribute",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (placesContainerEClass,
		   source,
		   new String[] {
			   "name", "places-container",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getPlacesContainer_Definitions(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "place",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (placeTransitionConnectionEClass,
		   source,
		   new String[] {
			   "name", "place-transition-connection",
			   "kind", "empty"
		   });
		addAnnotation
		  (getPlaceTransitionConnection_Source(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "source-id",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getPlaceTransitionConnection_Target(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "target-id",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (placeTransitionConnectionsContainerEClass,
		   source,
		   new String[] {
			   "name", "place-transition-connections-container",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getPlaceTransitionConnectionsContainer_Definitions(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "connection",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (placeTransitionElementEClass,
		   source,
		   new String[] {
			   "name", "place-transition-element",
			   "kind", "empty"
		   });
		addAnnotation
		  (getPlaceTransitionElement_IncomingConnections(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "incoming-connections",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getPlaceTransitionElement_OutgoingConnections(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "outgoing-connections",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (probeEClass,
		   source,
		   new String[] {
			   "name", "probe",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getProbe_ColorReferences(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "color-refs",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getProbe_MetaAttributes(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "meta-attributes",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getProbe_EndPlace(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "end-place-id",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getProbe_EndTrigger(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "end-trigger",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getProbe_Name(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "name",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getProbe_StartPlace(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "start-place-id",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getProbe_StartTrigger(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "start-trigger",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (probeColorReferenceEClass,
		   source,
		   new String[] {
			   "name", "probe-color-reference",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (probeMetaAttributeEClass,
		   source,
		   new String[] {
			   "name", "probe-meta-attribute",
			   "kind", "empty"
		   });
		addAnnotation
		  (probeMetaAttributesContainerEClass,
		   source,
		   new String[] {
			   "name", "probe-meta-attributes-container",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getProbeMetaAttributesContainer_Entries(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "meta-attribute",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (probesContainerEClass,
		   source,
		   new String[] {
			   "name", "probes-container",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getProbesContainer_Definitions(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "probe",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (probeTriggerEEnum,
		   source,
		   new String[] {
			   "name", "probe-trigger"
		   });
		addAnnotation
		  (probeTriggerObjectEDataType,
		   source,
		   new String[] {
			   "name", "probe-trigger:Object",
			   "baseType", "probe-trigger"
		   });
		addAnnotation
		  (qpmeDocumentEClass,
		   source,
		   new String[] {
			   "name", "",
			   "kind", "mixed"
		   });
		addAnnotation
		  (getQpmeDocument_Mixed(),
		   source,
		   new String[] {
			   "kind", "elementWildcard",
			   "name", ":mixed"
		   });
		addAnnotation
		  (getQpmeDocument_XMLNSPrefixMap(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "xmlns:prefix"
		   });
		addAnnotation
		  (getQpmeDocument_XSISchemaLocation(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "xsi:schemaLocation"
		   });
		addAnnotation
		  (getQpmeDocument_Net(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "net",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (queueEClass,
		   source,
		   new String[] {
			   "name", "queue",
			   "kind", "empty"
		   });
		addAnnotation
		  (getQueue_Name(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "name",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getQueue_NumberOfServers(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "number-of-servers",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getQueue_Strategy(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "strategy",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (queueingColorReferenceEClass,
		   source,
		   new String[] {
			   "name", "queueing-color-reference",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getQueueingColorReference_Alpha(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "alpha",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getQueueingColorReference_Beta(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "beta",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getQueueingColorReference_Cut(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "cut",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getQueueingColorReference_DistributionFunction(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "distribution-function",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getQueueingColorReference_Freedom(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "freedom",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getQueueingColorReference_Gamma(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "gamma",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getQueueingColorReference_InputFile(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "pdf_filename",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getQueueingColorReference_Lambda(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "lambda",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getQueueingColorReference_Max(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "max",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getQueueingColorReference_Mean(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "mean",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getQueueingColorReference_Min(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "min",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getQueueingColorReference_P(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "p",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getQueueingColorReference_C(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "c",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getQueueingColorReference_Priority(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "priority",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getQueueingColorReference_Ranking(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "ranking",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getQueueingColorReference_StdDev(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "stdDev",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getQueueingColorReference_Tau(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "tau",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getQueueingColorReference_Offset(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "offset",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getQueueingColorReference_Scale(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "scale",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (queueingPetriNetEClass,
		   source,
		   new String[] {
			   "name", "queueing-petri-net",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getQueueingPetriNet_Colors(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "colors",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getQueueingPetriNet_Queues(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "queues",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getQueueingPetriNet_Places(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "places",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getQueueingPetriNet_Transitions(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "transitions",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getQueueingPetriNet_Connections(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "connections",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getQueueingPetriNet_Probes(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "probes",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getQueueingPetriNet_MetaAttributes(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "meta-attributes",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getQueueingPetriNet_QpmeVersion(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "qpme-version",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (queueingPlaceEClass,
		   source,
		   new String[] {
			   "name", "queueing-place",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getQueueingPlace_Queue(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "queue-ref",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (queueingStrategyEEnum,
		   source,
		   new String[] {
			   "name", "queueing-strategy"
		   });
		addAnnotation
		  (queueingStrategyObjectEDataType,
		   source,
		   new String[] {
			   "name", "queueing-strategy:Object",
			   "baseType", "queueing-strategy"
		   });
		addAnnotation
		  (queuesContainerEClass,
		   source,
		   new String[] {
			   "name", "queues-container",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getQueuesContainer_Definitions(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "queue",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (rgbValueEDataType,
		   source,
		   new String[] {
			   "name", "rgb-value",
			   "baseType", "http://www.eclipse.org/emf/2003/XMLType#string"
		   });
		addAnnotation
		  (simqpnBatchMeansColorConfigurationEClass,
		   source,
		   new String[] {
			   "name", "simqpn-batch-means-color-configuration",
			   "kind", "empty"
		   });
		addAnnotation
		  (getSimqpnBatchMeansColorConfiguration_BatchSize(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "batchSize",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimqpnBatchMeansColorConfiguration_BucketSize(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "bucketSize",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimqpnBatchMeansColorConfiguration_MaxBuckets(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "maxBuckets",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimqpnBatchMeansColorConfiguration_MinBatches(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "minBatches",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimqpnBatchMeansColorConfiguration_NumBMeansCorlTested(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "numBMeansCorlTested",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimqpnBatchMeansColorConfiguration_ReqAbsPrc(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "reqAbsPrc",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimqpnBatchMeansColorConfiguration_ReqRelPrc(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "reqRelPrc",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimqpnBatchMeansColorConfiguration_SignLev(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "signLev",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (simqpnBatchMeansQueueingColorConfigurationEClass,
		   source,
		   new String[] {
			   "name", "simqpn-batch-means-queueing-color-configuration",
			   "kind", "empty"
		   });
		addAnnotation
		  (getSimqpnBatchMeansQueueingColorConfiguration_QueueBatchSize(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "queueBatchSize",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimqpnBatchMeansQueueingColorConfiguration_QueueBucketSize(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "queueBucketSize",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimqpnBatchMeansQueueingColorConfiguration_QueueMaxBuckets(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "queueMaxBuckets",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimqpnBatchMeansQueueingColorConfiguration_QueueMinBatches(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "queueMinBatches",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimqpnBatchMeansQueueingColorConfiguration_QueueNumBMeansCorlTested(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "queueNumBMeansCorlTested",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimqpnBatchMeansQueueingColorConfiguration_QueueReqAbsPrc(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "queueReqAbsPrc",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimqpnBatchMeansQueueingColorConfiguration_QueueReqRelPrc(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "queueReqRelPrc",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimqpnBatchMeansQueueingColorConfiguration_QueueSignLev(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "queueSignLev",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (simqpnConfigurationEClass,
		   source,
		   new String[] {
			   "name", "simqpn-configuration",
			   "kind", "empty"
		   });
		addAnnotation
		  (getSimqpnConfiguration_ConfigurationDescription(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "configuration-description",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimqpnConfiguration_NumberOfRuns(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "number-of-runs",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimqpnConfiguration_OutputDirectory(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "output-directory",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimqpnConfiguration_RampUpLength(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "ramp-up-length",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimqpnConfiguration_Scenario(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "scenario",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimqpnConfiguration_SecondsBetweenHeartBeats(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "seconds-between-heart-beats",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimqpnConfiguration_SecondsBetweenStopChecks(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "seconds-between-stop-checks",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimqpnConfiguration_StoppingRule(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "stopping-rule",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimqpnConfiguration_TimeBeforeInitialHeartBeat(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "time-before-initial-heart-beat",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimqpnConfiguration_TimeBetweenStopChecks(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "time-between-stop-checks",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimqpnConfiguration_TotalRunLength(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "total-run-length",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimqpnConfiguration_VerbosityLevel(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "verbosity-level",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (simqpnMetaAttributeEClass,
		   source,
		   new String[] {
			   "name", "simqpn-meta-attribute",
			   "kind", "empty"
		   });
		addAnnotation
		  (getSimqpnMetaAttribute_ConfigurationName(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "configuration-name",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (simqpnPlaceConfigurationEClass,
		   source,
		   new String[] {
			   "name", "simqpn-place-configuration",
			   "kind", "empty"
		   });
		addAnnotation
		  (getSimqpnPlaceConfiguration_StatsLevel(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "statsLevel",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (simqpnReplDelColorConfigurationEClass,
		   source,
		   new String[] {
			   "name", "simqpn-replication-delection-color-configuration",
			   "kind", "empty"
		   });
		addAnnotation
		  (getSimqpnReplDelColorConfiguration_SignLevAvgST(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "signLevAvgST",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (simqpnReplDelQueueingColorConfigurationEClass,
		   source,
		   new String[] {
			   "name", "simqpn-replication-delection-queueing-color-configuration",
			   "kind", "empty"
		   });
		addAnnotation
		  (getSimqpnReplDelQueueingColorConfiguration_QueueSignLevAvgST(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "queueSignLevAvgST",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (simqpnSimulationScenarioEEnum,
		   source,
		   new String[] {
			   "name", "simqpn-simulation-scenario"
		   });
		addAnnotation
		  (simqpnSimulationScenarioObjectEDataType,
		   source,
		   new String[] {
			   "name", "simqpn-simulation-scenario:Object",
			   "baseType", "simqpn-simulation-scenario"
		   });
		addAnnotation
		  (simqpnStoppingRuleEEnum,
		   source,
		   new String[] {
			   "name", "simqpn-stopping-rule"
		   });
		addAnnotation
		  (simqpnStoppingRuleObjectEDataType,
		   source,
		   new String[] {
			   "name", "simqpn-stopping-rule:Object",
			   "baseType", "simqpn-stopping-rule"
		   });
		addAnnotation
		  (simqpnWelchColorConfigurationEClass,
		   source,
		   new String[] {
			   "name", "simqpn-welch-color-configuration",
			   "kind", "empty"
		   });
		addAnnotation
		  (getSimqpnWelchColorConfiguration_MaxObsrv(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "maxObsrv",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimqpnWelchColorConfiguration_MinObsrv(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "minObsrv",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (simqpnWelchQueueingColorConfigurationEClass,
		   source,
		   new String[] {
			   "name", "simqpn-welch-queueing-color-configuration",
			   "kind", "empty"
		   });
		addAnnotation
		  (getSimqpnWelchQueueingColorConfiguration_QueueMaxObsrv(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "queueMaxObsrv",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSimqpnWelchQueueingColorConfiguration_QueueMinObsrv(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "queueMinObsrv",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (subnetEClass,
		   source,
		   new String[] {
			   "name", "subnet",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getSubnet_Colors(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "colors",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSubnet_Places(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "places",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSubnet_Transitions(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "transitions",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getSubnet_Connections(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "connections",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (subnetColorReferenceEClass,
		   source,
		   new String[] {
			   "name", "subnet-color-reference",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getSubnetColorReference_Direction(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "direction",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (subnetPlaceEClass,
		   source,
		   new String[] {
			   "name", "subnet-place",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getSubnetPlace_Subnet(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "subnet",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (timedTransitionEClass,
		   source,
		   new String[] {
			   "name", "timed-transition",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (transitionEClass,
		   source,
		   new String[] {
			   "name", "transition",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getTransition_Modes(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "modes",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getTransition_Connections(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "connections",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getTransition_MetaAttributes(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "meta-attributes",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getTransition_Name(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "name",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (getTransition_Priority(),
		   source,
		   new String[] {
			   "kind", "attribute",
			   "name", "priority",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (transitionMetaAttributeEClass,
		   source,
		   new String[] {
			   "name", "transition-meta-attribute",
			   "kind", "empty"
		   });
		addAnnotation
		  (transitionMetaAttributesContainerEClass,
		   source,
		   new String[] {
			   "name", "transition-meta-attributes-container",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getTransitionMetaAttributesContainer_Entries(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "meta-attribute",
			   "namespace", "##targetNamespace"
		   });
		addAnnotation
		  (transitionsContainerEClass,
		   source,
		   new String[] {
			   "name", "transitions-container",
			   "kind", "elementOnly"
		   });
		addAnnotation
		  (getTransitionsContainer_Definitions(),
		   source,
		   new String[] {
			   "kind", "element",
			   "name", "transition",
			   "namespace", "##targetNamespace"
		   });
	}

} //ModelPackageImpl
