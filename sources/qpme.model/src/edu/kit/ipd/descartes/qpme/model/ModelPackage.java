/**
 */
package edu.kit.ipd.descartes.qpme.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.descartes.qpme.model.ModelFactory
 * @model kind="package"
 *        extendedMetaData="qualified='false'"
 * @generated
 */
public interface ModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "model";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.descartes-research.net/qpme/model/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "qpme";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ModelPackage eINSTANCE = edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.IdentifiableElementImpl <em>Identifiable Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.IdentifiableElementImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getIdentifiableElement()
	 * @generated
	 */
	int IDENTIFIABLE_ELEMENT = 6;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIABLE_ELEMENT__ID = 0;

	/**
	 * The number of structural features of the '<em>Identifiable Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIABLE_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.ColorImpl <em>Color</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ColorImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getColor()
	 * @generated
	 */
	int COLOR = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR__ID = IDENTIFIABLE_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR__DESCRIPTION = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR__NAME = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Real Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR__REAL_COLOR = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Color</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR_FEATURE_COUNT = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.IncidenceFunctionElementImpl <em>Incidence Function Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.IncidenceFunctionElementImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getIncidenceFunctionElement()
	 * @generated
	 */
	int INCIDENCE_FUNCTION_ELEMENT = 10;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCIDENCE_FUNCTION_ELEMENT__ID = IDENTIFIABLE_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Incoming Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCIDENCE_FUNCTION_ELEMENT__INCOMING_CONNECTIONS = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Outgoing Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCIDENCE_FUNCTION_ELEMENT__OUTGOING_CONNECTIONS = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Incidence Function Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCIDENCE_FUNCTION_ELEMENT_FEATURE_COUNT = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.ColorReferenceImpl <em>Color Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ColorReferenceImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getColorReference()
	 * @generated
	 */
	int COLOR_REFERENCE = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR_REFERENCE__ID = INCIDENCE_FUNCTION_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Incoming Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR_REFERENCE__INCOMING_CONNECTIONS = INCIDENCE_FUNCTION_ELEMENT__INCOMING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Outgoing Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR_REFERENCE__OUTGOING_CONNECTIONS = INCIDENCE_FUNCTION_ELEMENT__OUTGOING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Meta Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR_REFERENCE__META_ATTRIBUTES = INCIDENCE_FUNCTION_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Color</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR_REFERENCE__COLOR = INCIDENCE_FUNCTION_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Color Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR_REFERENCE_FEATURE_COUNT = INCIDENCE_FUNCTION_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.ColorReferenceMetaAttributeImpl <em>Color Reference Meta Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ColorReferenceMetaAttributeImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getColorReferenceMetaAttribute()
	 * @generated
	 */
	int COLOR_REFERENCE_META_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Color Reference Meta Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR_REFERENCE_META_ATTRIBUTE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.ColorReferencesContainerImpl <em>Color References Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ColorReferencesContainerImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getColorReferencesContainer()
	 * @generated
	 */
	int COLOR_REFERENCES_CONTAINER = 3;

	/**
	 * The feature id for the '<em><b>Definitions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR_REFERENCES_CONTAINER__DEFINITIONS = 0;

	/**
	 * The number of structural features of the '<em>Color References Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR_REFERENCES_CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.ColorReferencesMetaAttributesContainerImpl <em>Color References Meta Attributes Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ColorReferencesMetaAttributesContainerImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getColorReferencesMetaAttributesContainer()
	 * @generated
	 */
	int COLOR_REFERENCES_META_ATTRIBUTES_CONTAINER = 4;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR_REFERENCES_META_ATTRIBUTES_CONTAINER__ENTRIES = 0;

	/**
	 * The number of structural features of the '<em>Color References Meta Attributes Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR_REFERENCES_META_ATTRIBUTES_CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.ColorsContainerImpl <em>Colors Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ColorsContainerImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getColorsContainer()
	 * @generated
	 */
	int COLORS_CONTAINER = 5;

	/**
	 * The feature id for the '<em><b>Definitions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLORS_CONTAINER__DEFINITIONS = 0;

	/**
	 * The number of structural features of the '<em>Colors Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLORS_CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.PlaceTransitionElementImpl <em>Place Transition Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.PlaceTransitionElementImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getPlaceTransitionElement()
	 * @generated
	 */
	int PLACE_TRANSITION_ELEMENT = 25;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE_TRANSITION_ELEMENT__ID = IDENTIFIABLE_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Incoming Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE_TRANSITION_ELEMENT__INCOMING_CONNECTIONS = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Outgoing Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE_TRANSITION_ELEMENT__OUTGOING_CONNECTIONS = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Place Transition Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE_TRANSITION_ELEMENT_FEATURE_COUNT = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.TransitionImpl <em>Transition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.TransitionImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getTransition()
	 * @generated
	 */
	int TRANSITION = 50;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__ID = PLACE_TRANSITION_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Incoming Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__INCOMING_CONNECTIONS = PLACE_TRANSITION_ELEMENT__INCOMING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Outgoing Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__OUTGOING_CONNECTIONS = PLACE_TRANSITION_ELEMENT__OUTGOING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Modes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__MODES = PLACE_TRANSITION_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Connections</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__CONNECTIONS = PLACE_TRANSITION_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Meta Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__META_ATTRIBUTES = PLACE_TRANSITION_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__NAME = PLACE_TRANSITION_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__PRIORITY = PLACE_TRANSITION_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Transition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_FEATURE_COUNT = PLACE_TRANSITION_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.ImmediateTransitionImpl <em>Immediate Transition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ImmediateTransitionImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getImmediateTransition()
	 * @generated
	 */
	int IMMEDIATE_TRANSITION = 7;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMMEDIATE_TRANSITION__ID = TRANSITION__ID;

	/**
	 * The feature id for the '<em><b>Incoming Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMMEDIATE_TRANSITION__INCOMING_CONNECTIONS = TRANSITION__INCOMING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Outgoing Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMMEDIATE_TRANSITION__OUTGOING_CONNECTIONS = TRANSITION__OUTGOING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Modes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMMEDIATE_TRANSITION__MODES = TRANSITION__MODES;

	/**
	 * The feature id for the '<em><b>Connections</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMMEDIATE_TRANSITION__CONNECTIONS = TRANSITION__CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Meta Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMMEDIATE_TRANSITION__META_ATTRIBUTES = TRANSITION__META_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMMEDIATE_TRANSITION__NAME = TRANSITION__NAME;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMMEDIATE_TRANSITION__PRIORITY = TRANSITION__PRIORITY;

	/**
	 * The feature id for the '<em><b>Weight</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMMEDIATE_TRANSITION__WEIGHT = TRANSITION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Immediate Transition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMMEDIATE_TRANSITION_FEATURE_COUNT = TRANSITION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.IncidenceFunctionConnectionImpl <em>Incidence Function Connection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.IncidenceFunctionConnectionImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getIncidenceFunctionConnection()
	 * @generated
	 */
	int INCIDENCE_FUNCTION_CONNECTION = 8;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCIDENCE_FUNCTION_CONNECTION__ID = IDENTIFIABLE_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCIDENCE_FUNCTION_CONNECTION__COUNT = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCIDENCE_FUNCTION_CONNECTION__SOURCE = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCIDENCE_FUNCTION_CONNECTION__TARGET = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Incidence Function Connection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCIDENCE_FUNCTION_CONNECTION_FEATURE_COUNT = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.IncidenceFunctionConnectionsContainerImpl <em>Incidence Function Connections Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.IncidenceFunctionConnectionsContainerImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getIncidenceFunctionConnectionsContainer()
	 * @generated
	 */
	int INCIDENCE_FUNCTION_CONNECTIONS_CONTAINER = 9;

	/**
	 * The feature id for the '<em><b>Definitions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCIDENCE_FUNCTION_CONNECTIONS_CONTAINER__DEFINITIONS = 0;

	/**
	 * The number of structural features of the '<em>Incidence Function Connections Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INCIDENCE_FUNCTION_CONNECTIONS_CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.PlaceMetaAttributeImpl <em>Place Meta Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.PlaceMetaAttributeImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getPlaceMetaAttribute()
	 * @generated
	 */
	int PLACE_META_ATTRIBUTE = 20;

	/**
	 * The number of structural features of the '<em>Place Meta Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE_META_ATTRIBUTE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.LocationAttributeImpl <em>Location Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.LocationAttributeImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getLocationAttribute()
	 * @generated
	 */
	int LOCATION_ATTRIBUTE = 11;

	/**
	 * The feature id for the '<em><b>Location X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION_ATTRIBUTE__LOCATION_X = PLACE_META_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Location Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION_ATTRIBUTE__LOCATION_Y = PLACE_META_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Location Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION_ATTRIBUTE_FEATURE_COUNT = PLACE_META_ATTRIBUTE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.ModeImpl <em>Mode</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModeImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getMode()
	 * @generated
	 */
	int MODE = 12;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODE__ID = INCIDENCE_FUNCTION_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Incoming Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODE__INCOMING_CONNECTIONS = INCIDENCE_FUNCTION_ELEMENT__INCOMING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Outgoing Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODE__OUTGOING_CONNECTIONS = INCIDENCE_FUNCTION_ELEMENT__OUTGOING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Firing Weight</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODE__FIRING_WEIGHT = INCIDENCE_FUNCTION_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Mean Firing Delay</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODE__MEAN_FIRING_DELAY = INCIDENCE_FUNCTION_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODE__NAME = INCIDENCE_FUNCTION_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Real Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODE__REAL_COLOR = INCIDENCE_FUNCTION_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Mode</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODE_FEATURE_COUNT = INCIDENCE_FUNCTION_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.ModesContainerImpl <em>Modes Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModesContainerImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getModesContainer()
	 * @generated
	 */
	int MODES_CONTAINER = 13;

	/**
	 * The feature id for the '<em><b>Definitions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODES_CONTAINER__DEFINITIONS = 0;

	/**
	 * The number of structural features of the '<em>Modes Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODES_CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.NetMetaAttributeImpl <em>Net Meta Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.NetMetaAttributeImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getNetMetaAttribute()
	 * @generated
	 */
	int NET_META_ATTRIBUTE = 14;

	/**
	 * The number of structural features of the '<em>Net Meta Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NET_META_ATTRIBUTE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.NetMetaAttributesContainerImpl <em>Net Meta Attributes Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.NetMetaAttributesContainerImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getNetMetaAttributesContainer()
	 * @generated
	 */
	int NET_META_ATTRIBUTES_CONTAINER = 15;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NET_META_ATTRIBUTES_CONTAINER__ENTRIES = 0;

	/**
	 * The number of structural features of the '<em>Net Meta Attributes Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NET_META_ATTRIBUTES_CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.PlaceColorReferenceImpl <em>Place Color Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.PlaceColorReferenceImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getPlaceColorReference()
	 * @generated
	 */
	int PLACE_COLOR_REFERENCE = 19;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE_COLOR_REFERENCE__ID = COLOR_REFERENCE__ID;

	/**
	 * The feature id for the '<em><b>Incoming Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE_COLOR_REFERENCE__INCOMING_CONNECTIONS = COLOR_REFERENCE__INCOMING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Outgoing Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE_COLOR_REFERENCE__OUTGOING_CONNECTIONS = COLOR_REFERENCE__OUTGOING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Meta Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE_COLOR_REFERENCE__META_ATTRIBUTES = COLOR_REFERENCE__META_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Color</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE_COLOR_REFERENCE__COLOR = COLOR_REFERENCE__COLOR;

	/**
	 * The feature id for the '<em><b>Initial Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE_COLOR_REFERENCE__INITIAL_POPULATION = COLOR_REFERENCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Maximum Capacity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE_COLOR_REFERENCE__MAXIMUM_CAPACITY = COLOR_REFERENCE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Place Color Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE_COLOR_REFERENCE_FEATURE_COUNT = COLOR_REFERENCE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.OrdinaryColorReferenceImpl <em>Ordinary Color Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.OrdinaryColorReferenceImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getOrdinaryColorReference()
	 * @generated
	 */
	int ORDINARY_COLOR_REFERENCE = 16;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORDINARY_COLOR_REFERENCE__ID = PLACE_COLOR_REFERENCE__ID;

	/**
	 * The feature id for the '<em><b>Incoming Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORDINARY_COLOR_REFERENCE__INCOMING_CONNECTIONS = PLACE_COLOR_REFERENCE__INCOMING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Outgoing Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORDINARY_COLOR_REFERENCE__OUTGOING_CONNECTIONS = PLACE_COLOR_REFERENCE__OUTGOING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Meta Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORDINARY_COLOR_REFERENCE__META_ATTRIBUTES = PLACE_COLOR_REFERENCE__META_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Color</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORDINARY_COLOR_REFERENCE__COLOR = PLACE_COLOR_REFERENCE__COLOR;

	/**
	 * The feature id for the '<em><b>Initial Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORDINARY_COLOR_REFERENCE__INITIAL_POPULATION = PLACE_COLOR_REFERENCE__INITIAL_POPULATION;

	/**
	 * The feature id for the '<em><b>Maximum Capacity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORDINARY_COLOR_REFERENCE__MAXIMUM_CAPACITY = PLACE_COLOR_REFERENCE__MAXIMUM_CAPACITY;

	/**
	 * The number of structural features of the '<em>Ordinary Color Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORDINARY_COLOR_REFERENCE_FEATURE_COUNT = PLACE_COLOR_REFERENCE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.PlaceImpl <em>Place</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.PlaceImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getPlace()
	 * @generated
	 */
	int PLACE = 18;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE__ID = PLACE_TRANSITION_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Incoming Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE__INCOMING_CONNECTIONS = PLACE_TRANSITION_ELEMENT__INCOMING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Outgoing Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE__OUTGOING_CONNECTIONS = PLACE_TRANSITION_ELEMENT__OUTGOING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Color References</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE__COLOR_REFERENCES = PLACE_TRANSITION_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Meta Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE__META_ATTRIBUTES = PLACE_TRANSITION_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Departure Discipline</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE__DEPARTURE_DISCIPLINE = PLACE_TRANSITION_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Locked</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE__LOCKED = PLACE_TRANSITION_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE__NAME = PLACE_TRANSITION_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Place</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE_FEATURE_COUNT = PLACE_TRANSITION_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.OrdinaryPlaceImpl <em>Ordinary Place</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.OrdinaryPlaceImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getOrdinaryPlace()
	 * @generated
	 */
	int ORDINARY_PLACE = 17;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORDINARY_PLACE__ID = PLACE__ID;

	/**
	 * The feature id for the '<em><b>Incoming Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORDINARY_PLACE__INCOMING_CONNECTIONS = PLACE__INCOMING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Outgoing Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORDINARY_PLACE__OUTGOING_CONNECTIONS = PLACE__OUTGOING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Color References</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORDINARY_PLACE__COLOR_REFERENCES = PLACE__COLOR_REFERENCES;

	/**
	 * The feature id for the '<em><b>Meta Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORDINARY_PLACE__META_ATTRIBUTES = PLACE__META_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Departure Discipline</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORDINARY_PLACE__DEPARTURE_DISCIPLINE = PLACE__DEPARTURE_DISCIPLINE;

	/**
	 * The feature id for the '<em><b>Locked</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORDINARY_PLACE__LOCKED = PLACE__LOCKED;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORDINARY_PLACE__NAME = PLACE__NAME;

	/**
	 * The number of structural features of the '<em>Ordinary Place</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORDINARY_PLACE_FEATURE_COUNT = PLACE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.PlaceMetaAttributesContainerImpl <em>Place Meta Attributes Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.PlaceMetaAttributesContainerImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getPlaceMetaAttributesContainer()
	 * @generated
	 */
	int PLACE_META_ATTRIBUTES_CONTAINER = 21;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE_META_ATTRIBUTES_CONTAINER__ENTRIES = 0;

	/**
	 * The number of structural features of the '<em>Place Meta Attributes Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE_META_ATTRIBUTES_CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.PlacesContainerImpl <em>Places Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.PlacesContainerImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getPlacesContainer()
	 * @generated
	 */
	int PLACES_CONTAINER = 22;

	/**
	 * The feature id for the '<em><b>Definitions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACES_CONTAINER__DEFINITIONS = 0;

	/**
	 * The number of structural features of the '<em>Places Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACES_CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.PlaceTransitionConnectionImpl <em>Place Transition Connection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.PlaceTransitionConnectionImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getPlaceTransitionConnection()
	 * @generated
	 */
	int PLACE_TRANSITION_CONNECTION = 23;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE_TRANSITION_CONNECTION__ID = IDENTIFIABLE_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE_TRANSITION_CONNECTION__SOURCE = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE_TRANSITION_CONNECTION__TARGET = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Place Transition Connection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE_TRANSITION_CONNECTION_FEATURE_COUNT = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.PlaceTransitionConnectionsContainerImpl <em>Place Transition Connections Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.PlaceTransitionConnectionsContainerImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getPlaceTransitionConnectionsContainer()
	 * @generated
	 */
	int PLACE_TRANSITION_CONNECTIONS_CONTAINER = 24;

	/**
	 * The feature id for the '<em><b>Definitions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE_TRANSITION_CONNECTIONS_CONTAINER__DEFINITIONS = 0;

	/**
	 * The number of structural features of the '<em>Place Transition Connections Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE_TRANSITION_CONNECTIONS_CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.ProbeImpl <em>Probe</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ProbeImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getProbe()
	 * @generated
	 */
	int PROBE = 26;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBE__ID = IDENTIFIABLE_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Color References</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBE__COLOR_REFERENCES = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Meta Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBE__META_ATTRIBUTES = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>End Place</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBE__END_PLACE = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>End Trigger</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBE__END_TRIGGER = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBE__NAME = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Start Place</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBE__START_PLACE = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Start Trigger</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBE__START_TRIGGER = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Probe</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBE_FEATURE_COUNT = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.ProbeColorReferenceImpl <em>Probe Color Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ProbeColorReferenceImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getProbeColorReference()
	 * @generated
	 */
	int PROBE_COLOR_REFERENCE = 27;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBE_COLOR_REFERENCE__ID = COLOR_REFERENCE__ID;

	/**
	 * The feature id for the '<em><b>Incoming Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBE_COLOR_REFERENCE__INCOMING_CONNECTIONS = COLOR_REFERENCE__INCOMING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Outgoing Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBE_COLOR_REFERENCE__OUTGOING_CONNECTIONS = COLOR_REFERENCE__OUTGOING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Meta Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBE_COLOR_REFERENCE__META_ATTRIBUTES = COLOR_REFERENCE__META_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Color</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBE_COLOR_REFERENCE__COLOR = COLOR_REFERENCE__COLOR;

	/**
	 * The number of structural features of the '<em>Probe Color Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBE_COLOR_REFERENCE_FEATURE_COUNT = COLOR_REFERENCE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.ProbeMetaAttributeImpl <em>Probe Meta Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ProbeMetaAttributeImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getProbeMetaAttribute()
	 * @generated
	 */
	int PROBE_META_ATTRIBUTE = 28;

	/**
	 * The number of structural features of the '<em>Probe Meta Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBE_META_ATTRIBUTE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.ProbeMetaAttributesContainerImpl <em>Probe Meta Attributes Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ProbeMetaAttributesContainerImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getProbeMetaAttributesContainer()
	 * @generated
	 */
	int PROBE_META_ATTRIBUTES_CONTAINER = 29;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBE_META_ATTRIBUTES_CONTAINER__ENTRIES = 0;

	/**
	 * The number of structural features of the '<em>Probe Meta Attributes Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBE_META_ATTRIBUTES_CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.ProbesContainerImpl <em>Probes Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ProbesContainerImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getProbesContainer()
	 * @generated
	 */
	int PROBES_CONTAINER = 30;

	/**
	 * The feature id for the '<em><b>Definitions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBES_CONTAINER__DEFINITIONS = 0;

	/**
	 * The number of structural features of the '<em>Probes Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROBES_CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.QpmeDocumentImpl <em>Qpme Document</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.QpmeDocumentImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getQpmeDocument()
	 * @generated
	 */
	int QPME_DOCUMENT = 31;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QPME_DOCUMENT__MIXED = 0;

	/**
	 * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QPME_DOCUMENT__XMLNS_PREFIX_MAP = 1;

	/**
	 * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QPME_DOCUMENT__XSI_SCHEMA_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Net</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QPME_DOCUMENT__NET = 3;

	/**
	 * The number of structural features of the '<em>Qpme Document</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QPME_DOCUMENT_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.QueueImpl <em>Queue</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.QueueImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getQueue()
	 * @generated
	 */
	int QUEUE = 32;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUE__ID = IDENTIFIABLE_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUE__NAME = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Number Of Servers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUE__NUMBER_OF_SERVERS = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUE__STRATEGY = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Queue</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUE_FEATURE_COUNT = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingColorReferenceImpl <em>Queueing Color Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.QueueingColorReferenceImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getQueueingColorReference()
	 * @generated
	 */
	int QUEUEING_COLOR_REFERENCE = 33;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__ID = PLACE_COLOR_REFERENCE__ID;

	/**
	 * The feature id for the '<em><b>Incoming Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__INCOMING_CONNECTIONS = PLACE_COLOR_REFERENCE__INCOMING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Outgoing Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__OUTGOING_CONNECTIONS = PLACE_COLOR_REFERENCE__OUTGOING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Meta Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__META_ATTRIBUTES = PLACE_COLOR_REFERENCE__META_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Color</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__COLOR = PLACE_COLOR_REFERENCE__COLOR;

	/**
	 * The feature id for the '<em><b>Initial Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__INITIAL_POPULATION = PLACE_COLOR_REFERENCE__INITIAL_POPULATION;

	/**
	 * The feature id for the '<em><b>Maximum Capacity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__MAXIMUM_CAPACITY = PLACE_COLOR_REFERENCE__MAXIMUM_CAPACITY;

	/**
	 * The feature id for the '<em><b>Alpha</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__ALPHA = PLACE_COLOR_REFERENCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Beta</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__BETA = PLACE_COLOR_REFERENCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Cut</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__CUT = PLACE_COLOR_REFERENCE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Distribution Function</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__DISTRIBUTION_FUNCTION = PLACE_COLOR_REFERENCE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Freedom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__FREEDOM = PLACE_COLOR_REFERENCE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Gamma</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__GAMMA = PLACE_COLOR_REFERENCE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Input File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__INPUT_FILE = PLACE_COLOR_REFERENCE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Lambda</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__LAMBDA = PLACE_COLOR_REFERENCE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Max</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__MAX = PLACE_COLOR_REFERENCE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Mean</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__MEAN = PLACE_COLOR_REFERENCE_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Min</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__MIN = PLACE_COLOR_REFERENCE_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>P</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__P = PLACE_COLOR_REFERENCE_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>C</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__C = PLACE_COLOR_REFERENCE_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__PRIORITY = PLACE_COLOR_REFERENCE_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Ranking</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__RANKING = PLACE_COLOR_REFERENCE_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Std Dev</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__STD_DEV = PLACE_COLOR_REFERENCE_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Tau</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__TAU = PLACE_COLOR_REFERENCE_FEATURE_COUNT + 16;

	/**
	 * The feature id for the '<em><b>Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__OFFSET = PLACE_COLOR_REFERENCE_FEATURE_COUNT + 17;

	/**
	 * The feature id for the '<em><b>Scale</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__SCALE = PLACE_COLOR_REFERENCE_FEATURE_COUNT + 18;

	/**
	 * The feature id for the '<em><b>Replay File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__REPLAY_FILE = PLACE_COLOR_REFERENCE_FEATURE_COUNT + 19;

	/**
	 * The feature id for the '<em><b>Values File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__VALUES_FILE = PLACE_COLOR_REFERENCE_FEATURE_COUNT + 20;

	/**
	 * The feature id for the '<em><b>Probabilities File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__PROBABILITIES_FILE = PLACE_COLOR_REFERENCE_FEATURE_COUNT + 21;

	/**
	 * The feature id for the '<em><b>Concurrencies File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__CONCURRENCIES_FILE = PLACE_COLOR_REFERENCE_FEATURE_COUNT + 22;

	/**
	 * The feature id for the '<em><b>Responsetimes File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__RESPONSETIMES_FILE = PLACE_COLOR_REFERENCE_FEATURE_COUNT + 23;

	/**
	 * The feature id for the '<em><b>Mars File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE__MARS_FILE = PLACE_COLOR_REFERENCE_FEATURE_COUNT + 24;

	/**
	 * The number of structural features of the '<em>Queueing Color Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_COLOR_REFERENCE_FEATURE_COUNT = PLACE_COLOR_REFERENCE_FEATURE_COUNT + 25;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingPetriNetImpl <em>Queueing Petri Net</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.QueueingPetriNetImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getQueueingPetriNet()
	 * @generated
	 */
	int QUEUEING_PETRI_NET = 34;

	/**
	 * The feature id for the '<em><b>Colors</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_PETRI_NET__COLORS = 0;

	/**
	 * The feature id for the '<em><b>Queues</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_PETRI_NET__QUEUES = 1;

	/**
	 * The feature id for the '<em><b>Places</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_PETRI_NET__PLACES = 2;

	/**
	 * The feature id for the '<em><b>Transitions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_PETRI_NET__TRANSITIONS = 3;

	/**
	 * The feature id for the '<em><b>Connections</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_PETRI_NET__CONNECTIONS = 4;

	/**
	 * The feature id for the '<em><b>Probes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_PETRI_NET__PROBES = 5;

	/**
	 * The feature id for the '<em><b>Meta Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_PETRI_NET__META_ATTRIBUTES = 6;

	/**
	 * The feature id for the '<em><b>Qpme Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_PETRI_NET__QPME_VERSION = 7;

	/**
	 * The number of structural features of the '<em>Queueing Petri Net</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_PETRI_NET_FEATURE_COUNT = 8;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingPlaceImpl <em>Queueing Place</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.QueueingPlaceImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getQueueingPlace()
	 * @generated
	 */
	int QUEUEING_PLACE = 35;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_PLACE__ID = PLACE__ID;

	/**
	 * The feature id for the '<em><b>Incoming Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_PLACE__INCOMING_CONNECTIONS = PLACE__INCOMING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Outgoing Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_PLACE__OUTGOING_CONNECTIONS = PLACE__OUTGOING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Color References</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_PLACE__COLOR_REFERENCES = PLACE__COLOR_REFERENCES;

	/**
	 * The feature id for the '<em><b>Meta Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_PLACE__META_ATTRIBUTES = PLACE__META_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Departure Discipline</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_PLACE__DEPARTURE_DISCIPLINE = PLACE__DEPARTURE_DISCIPLINE;

	/**
	 * The feature id for the '<em><b>Locked</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_PLACE__LOCKED = PLACE__LOCKED;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_PLACE__NAME = PLACE__NAME;

	/**
	 * The feature id for the '<em><b>Queue</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_PLACE__QUEUE = PLACE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Queueing Place</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUEING_PLACE_FEATURE_COUNT = PLACE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.QueuesContainerImpl <em>Queues Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.QueuesContainerImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getQueuesContainer()
	 * @generated
	 */
	int QUEUES_CONTAINER = 36;

	/**
	 * The feature id for the '<em><b>Definitions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUES_CONTAINER__DEFINITIONS = 0;

	/**
	 * The number of structural features of the '<em>Queues Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUEUES_CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnMetaAttributeImpl <em>Simqpn Meta Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.SimqpnMetaAttributeImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSimqpnMetaAttribute()
	 * @generated
	 */
	int SIMQPN_META_ATTRIBUTE = 40;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_META_ATTRIBUTE__ID = IDENTIFIABLE_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Configuration Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_META_ATTRIBUTE__CONFIGURATION_NAME = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Simqpn Meta Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_META_ATTRIBUTE_FEATURE_COUNT = IDENTIFIABLE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnBatchMeansColorConfigurationImpl <em>Simqpn Batch Means Color Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.SimqpnBatchMeansColorConfigurationImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSimqpnBatchMeansColorConfiguration()
	 * @generated
	 */
	int SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION = 37;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__ID = SIMQPN_META_ATTRIBUTE__ID;

	/**
	 * The feature id for the '<em><b>Configuration Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__CONFIGURATION_NAME = SIMQPN_META_ATTRIBUTE__CONFIGURATION_NAME;

	/**
	 * The feature id for the '<em><b>Batch Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__BATCH_SIZE = SIMQPN_META_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Bucket Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__BUCKET_SIZE = SIMQPN_META_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Max Buckets</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__MAX_BUCKETS = SIMQPN_META_ATTRIBUTE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Min Batches</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__MIN_BATCHES = SIMQPN_META_ATTRIBUTE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Num BMeans Corl Tested</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__NUM_BMEANS_CORL_TESTED = SIMQPN_META_ATTRIBUTE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Req Abs Prc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__REQ_ABS_PRC = SIMQPN_META_ATTRIBUTE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Req Rel Prc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__REQ_REL_PRC = SIMQPN_META_ATTRIBUTE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Sign Lev</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__SIGN_LEV = SIMQPN_META_ATTRIBUTE_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Simqpn Batch Means Color Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION_FEATURE_COUNT = SIMQPN_META_ATTRIBUTE_FEATURE_COUNT + 8;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnBatchMeansQueueingColorConfigurationImpl <em>Simqpn Batch Means Queueing Color Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.SimqpnBatchMeansQueueingColorConfigurationImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSimqpnBatchMeansQueueingColorConfiguration()
	 * @generated
	 */
	int SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION = 38;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__ID = SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__ID;

	/**
	 * The feature id for the '<em><b>Configuration Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__CONFIGURATION_NAME = SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__CONFIGURATION_NAME;

	/**
	 * The feature id for the '<em><b>Batch Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__BATCH_SIZE = SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__BATCH_SIZE;

	/**
	 * The feature id for the '<em><b>Bucket Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__BUCKET_SIZE = SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__BUCKET_SIZE;

	/**
	 * The feature id for the '<em><b>Max Buckets</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__MAX_BUCKETS = SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__MAX_BUCKETS;

	/**
	 * The feature id for the '<em><b>Min Batches</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__MIN_BATCHES = SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__MIN_BATCHES;

	/**
	 * The feature id for the '<em><b>Num BMeans Corl Tested</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__NUM_BMEANS_CORL_TESTED = SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__NUM_BMEANS_CORL_TESTED;

	/**
	 * The feature id for the '<em><b>Req Abs Prc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__REQ_ABS_PRC = SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__REQ_ABS_PRC;

	/**
	 * The feature id for the '<em><b>Req Rel Prc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__REQ_REL_PRC = SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__REQ_REL_PRC;

	/**
	 * The feature id for the '<em><b>Sign Lev</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__SIGN_LEV = SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__SIGN_LEV;

	/**
	 * The feature id for the '<em><b>Queue Batch Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_BATCH_SIZE = SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Queue Bucket Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_BUCKET_SIZE = SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Queue Max Buckets</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_MAX_BUCKETS = SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Queue Min Batches</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_MIN_BATCHES = SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Queue Num BMeans Corl Tested</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_NUM_BMEANS_CORL_TESTED = SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Queue Req Abs Prc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_REQ_ABS_PRC = SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Queue Req Rel Prc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_REQ_REL_PRC = SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Queue Sign Lev</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_SIGN_LEV = SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Simqpn Batch Means Queueing Color Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION_FEATURE_COUNT = SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION_FEATURE_COUNT + 8;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnConfigurationImpl <em>Simqpn Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.SimqpnConfigurationImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSimqpnConfiguration()
	 * @generated
	 */
	int SIMQPN_CONFIGURATION = 39;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_CONFIGURATION__ID = SIMQPN_META_ATTRIBUTE__ID;

	/**
	 * The feature id for the '<em><b>Configuration Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_CONFIGURATION__CONFIGURATION_NAME = SIMQPN_META_ATTRIBUTE__CONFIGURATION_NAME;

	/**
	 * The feature id for the '<em><b>Configuration Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_CONFIGURATION__CONFIGURATION_DESCRIPTION = SIMQPN_META_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Number Of Runs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_CONFIGURATION__NUMBER_OF_RUNS = SIMQPN_META_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Output Directory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_CONFIGURATION__OUTPUT_DIRECTORY = SIMQPN_META_ATTRIBUTE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Ramp Up Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_CONFIGURATION__RAMP_UP_LENGTH = SIMQPN_META_ATTRIBUTE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Scenario</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_CONFIGURATION__SCENARIO = SIMQPN_META_ATTRIBUTE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Seconds Between Heart Beats</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_CONFIGURATION__SECONDS_BETWEEN_HEART_BEATS = SIMQPN_META_ATTRIBUTE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Seconds Between Stop Checks</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_CONFIGURATION__SECONDS_BETWEEN_STOP_CHECKS = SIMQPN_META_ATTRIBUTE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Stopping Rule</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_CONFIGURATION__STOPPING_RULE = SIMQPN_META_ATTRIBUTE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Time Before Initial Heart Beat</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_CONFIGURATION__TIME_BEFORE_INITIAL_HEART_BEAT = SIMQPN_META_ATTRIBUTE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Time Between Stop Checks</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_CONFIGURATION__TIME_BETWEEN_STOP_CHECKS = SIMQPN_META_ATTRIBUTE_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Total Run Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_CONFIGURATION__TOTAL_RUN_LENGTH = SIMQPN_META_ATTRIBUTE_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Verbosity Level</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_CONFIGURATION__VERBOSITY_LEVEL = SIMQPN_META_ATTRIBUTE_FEATURE_COUNT + 11;

	/**
	 * The number of structural features of the '<em>Simqpn Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_CONFIGURATION_FEATURE_COUNT = SIMQPN_META_ATTRIBUTE_FEATURE_COUNT + 12;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnPlaceConfigurationImpl <em>Simqpn Place Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.SimqpnPlaceConfigurationImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSimqpnPlaceConfiguration()
	 * @generated
	 */
	int SIMQPN_PLACE_CONFIGURATION = 41;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_PLACE_CONFIGURATION__ID = SIMQPN_META_ATTRIBUTE__ID;

	/**
	 * The feature id for the '<em><b>Configuration Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_PLACE_CONFIGURATION__CONFIGURATION_NAME = SIMQPN_META_ATTRIBUTE__CONFIGURATION_NAME;

	/**
	 * The feature id for the '<em><b>Stats Level</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_PLACE_CONFIGURATION__STATS_LEVEL = SIMQPN_META_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Simqpn Place Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_PLACE_CONFIGURATION_FEATURE_COUNT = SIMQPN_META_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnReplDelColorConfigurationImpl <em>Simqpn Repl Del Color Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.SimqpnReplDelColorConfigurationImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSimqpnReplDelColorConfiguration()
	 * @generated
	 */
	int SIMQPN_REPL_DEL_COLOR_CONFIGURATION = 42;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_REPL_DEL_COLOR_CONFIGURATION__ID = SIMQPN_META_ATTRIBUTE__ID;

	/**
	 * The feature id for the '<em><b>Configuration Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_REPL_DEL_COLOR_CONFIGURATION__CONFIGURATION_NAME = SIMQPN_META_ATTRIBUTE__CONFIGURATION_NAME;

	/**
	 * The feature id for the '<em><b>Sign Lev Avg ST</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_REPL_DEL_COLOR_CONFIGURATION__SIGN_LEV_AVG_ST = SIMQPN_META_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Simqpn Repl Del Color Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_REPL_DEL_COLOR_CONFIGURATION_FEATURE_COUNT = SIMQPN_META_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnReplDelQueueingColorConfigurationImpl <em>Simqpn Repl Del Queueing Color Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.SimqpnReplDelQueueingColorConfigurationImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSimqpnReplDelQueueingColorConfiguration()
	 * @generated
	 */
	int SIMQPN_REPL_DEL_QUEUEING_COLOR_CONFIGURATION = 43;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_REPL_DEL_QUEUEING_COLOR_CONFIGURATION__ID = SIMQPN_REPL_DEL_COLOR_CONFIGURATION__ID;

	/**
	 * The feature id for the '<em><b>Configuration Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_REPL_DEL_QUEUEING_COLOR_CONFIGURATION__CONFIGURATION_NAME = SIMQPN_REPL_DEL_COLOR_CONFIGURATION__CONFIGURATION_NAME;

	/**
	 * The feature id for the '<em><b>Sign Lev Avg ST</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_REPL_DEL_QUEUEING_COLOR_CONFIGURATION__SIGN_LEV_AVG_ST = SIMQPN_REPL_DEL_COLOR_CONFIGURATION__SIGN_LEV_AVG_ST;

	/**
	 * The feature id for the '<em><b>Queue Sign Lev Avg ST</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_REPL_DEL_QUEUEING_COLOR_CONFIGURATION__QUEUE_SIGN_LEV_AVG_ST = SIMQPN_REPL_DEL_COLOR_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Simqpn Repl Del Queueing Color Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_REPL_DEL_QUEUEING_COLOR_CONFIGURATION_FEATURE_COUNT = SIMQPN_REPL_DEL_COLOR_CONFIGURATION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnWelchColorConfigurationImpl <em>Simqpn Welch Color Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.SimqpnWelchColorConfigurationImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSimqpnWelchColorConfiguration()
	 * @generated
	 */
	int SIMQPN_WELCH_COLOR_CONFIGURATION = 44;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_WELCH_COLOR_CONFIGURATION__ID = SIMQPN_META_ATTRIBUTE__ID;

	/**
	 * The feature id for the '<em><b>Configuration Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_WELCH_COLOR_CONFIGURATION__CONFIGURATION_NAME = SIMQPN_META_ATTRIBUTE__CONFIGURATION_NAME;

	/**
	 * The feature id for the '<em><b>Max Obsrv</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_WELCH_COLOR_CONFIGURATION__MAX_OBSRV = SIMQPN_META_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Min Obsrv</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_WELCH_COLOR_CONFIGURATION__MIN_OBSRV = SIMQPN_META_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Simqpn Welch Color Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_WELCH_COLOR_CONFIGURATION_FEATURE_COUNT = SIMQPN_META_ATTRIBUTE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnWelchQueueingColorConfigurationImpl <em>Simqpn Welch Queueing Color Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.SimqpnWelchQueueingColorConfigurationImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSimqpnWelchQueueingColorConfiguration()
	 * @generated
	 */
	int SIMQPN_WELCH_QUEUEING_COLOR_CONFIGURATION = 45;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_WELCH_QUEUEING_COLOR_CONFIGURATION__ID = SIMQPN_WELCH_COLOR_CONFIGURATION__ID;

	/**
	 * The feature id for the '<em><b>Configuration Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_WELCH_QUEUEING_COLOR_CONFIGURATION__CONFIGURATION_NAME = SIMQPN_WELCH_COLOR_CONFIGURATION__CONFIGURATION_NAME;

	/**
	 * The feature id for the '<em><b>Max Obsrv</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_WELCH_QUEUEING_COLOR_CONFIGURATION__MAX_OBSRV = SIMQPN_WELCH_COLOR_CONFIGURATION__MAX_OBSRV;

	/**
	 * The feature id for the '<em><b>Min Obsrv</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_WELCH_QUEUEING_COLOR_CONFIGURATION__MIN_OBSRV = SIMQPN_WELCH_COLOR_CONFIGURATION__MIN_OBSRV;

	/**
	 * The feature id for the '<em><b>Queue Max Obsrv</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_WELCH_QUEUEING_COLOR_CONFIGURATION__QUEUE_MAX_OBSRV = SIMQPN_WELCH_COLOR_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Queue Min Obsrv</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_WELCH_QUEUEING_COLOR_CONFIGURATION__QUEUE_MIN_OBSRV = SIMQPN_WELCH_COLOR_CONFIGURATION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Simqpn Welch Queueing Color Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_WELCH_QUEUEING_COLOR_CONFIGURATION_FEATURE_COUNT = SIMQPN_WELCH_COLOR_CONFIGURATION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.SubnetImpl <em>Subnet</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.SubnetImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSubnet()
	 * @generated
	 */
	int SUBNET = 46;

	/**
	 * The feature id for the '<em><b>Colors</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBNET__COLORS = 0;

	/**
	 * The feature id for the '<em><b>Places</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBNET__PLACES = 1;

	/**
	 * The feature id for the '<em><b>Transitions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBNET__TRANSITIONS = 2;

	/**
	 * The feature id for the '<em><b>Connections</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBNET__CONNECTIONS = 3;

	/**
	 * The number of structural features of the '<em>Subnet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBNET_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.SubnetColorReferenceImpl <em>Subnet Color Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.SubnetColorReferenceImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSubnetColorReference()
	 * @generated
	 */
	int SUBNET_COLOR_REFERENCE = 47;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBNET_COLOR_REFERENCE__ID = PLACE_COLOR_REFERENCE__ID;

	/**
	 * The feature id for the '<em><b>Incoming Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBNET_COLOR_REFERENCE__INCOMING_CONNECTIONS = PLACE_COLOR_REFERENCE__INCOMING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Outgoing Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBNET_COLOR_REFERENCE__OUTGOING_CONNECTIONS = PLACE_COLOR_REFERENCE__OUTGOING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Meta Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBNET_COLOR_REFERENCE__META_ATTRIBUTES = PLACE_COLOR_REFERENCE__META_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Color</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBNET_COLOR_REFERENCE__COLOR = PLACE_COLOR_REFERENCE__COLOR;

	/**
	 * The feature id for the '<em><b>Initial Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBNET_COLOR_REFERENCE__INITIAL_POPULATION = PLACE_COLOR_REFERENCE__INITIAL_POPULATION;

	/**
	 * The feature id for the '<em><b>Maximum Capacity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBNET_COLOR_REFERENCE__MAXIMUM_CAPACITY = PLACE_COLOR_REFERENCE__MAXIMUM_CAPACITY;

	/**
	 * The feature id for the '<em><b>Direction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBNET_COLOR_REFERENCE__DIRECTION = PLACE_COLOR_REFERENCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Subnet Color Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBNET_COLOR_REFERENCE_FEATURE_COUNT = PLACE_COLOR_REFERENCE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.SubnetPlaceImpl <em>Subnet Place</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.SubnetPlaceImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSubnetPlace()
	 * @generated
	 */
	int SUBNET_PLACE = 48;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBNET_PLACE__ID = PLACE__ID;

	/**
	 * The feature id for the '<em><b>Incoming Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBNET_PLACE__INCOMING_CONNECTIONS = PLACE__INCOMING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Outgoing Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBNET_PLACE__OUTGOING_CONNECTIONS = PLACE__OUTGOING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Color References</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBNET_PLACE__COLOR_REFERENCES = PLACE__COLOR_REFERENCES;

	/**
	 * The feature id for the '<em><b>Meta Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBNET_PLACE__META_ATTRIBUTES = PLACE__META_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Departure Discipline</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBNET_PLACE__DEPARTURE_DISCIPLINE = PLACE__DEPARTURE_DISCIPLINE;

	/**
	 * The feature id for the '<em><b>Locked</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBNET_PLACE__LOCKED = PLACE__LOCKED;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBNET_PLACE__NAME = PLACE__NAME;

	/**
	 * The feature id for the '<em><b>Subnet</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBNET_PLACE__SUBNET = PLACE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Subnet Place</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBNET_PLACE_FEATURE_COUNT = PLACE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.TimedTransitionImpl <em>Timed Transition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.TimedTransitionImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getTimedTransition()
	 * @generated
	 */
	int TIMED_TRANSITION = 49;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIMED_TRANSITION__ID = TRANSITION__ID;

	/**
	 * The feature id for the '<em><b>Incoming Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIMED_TRANSITION__INCOMING_CONNECTIONS = TRANSITION__INCOMING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Outgoing Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIMED_TRANSITION__OUTGOING_CONNECTIONS = TRANSITION__OUTGOING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Modes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIMED_TRANSITION__MODES = TRANSITION__MODES;

	/**
	 * The feature id for the '<em><b>Connections</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIMED_TRANSITION__CONNECTIONS = TRANSITION__CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Meta Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIMED_TRANSITION__META_ATTRIBUTES = TRANSITION__META_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIMED_TRANSITION__NAME = TRANSITION__NAME;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIMED_TRANSITION__PRIORITY = TRANSITION__PRIORITY;

	/**
	 * The number of structural features of the '<em>Timed Transition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIMED_TRANSITION_FEATURE_COUNT = TRANSITION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.TransitionMetaAttributeImpl <em>Transition Meta Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.TransitionMetaAttributeImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getTransitionMetaAttribute()
	 * @generated
	 */
	int TRANSITION_META_ATTRIBUTE = 51;

	/**
	 * The number of structural features of the '<em>Transition Meta Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_META_ATTRIBUTE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.TransitionMetaAttributesContainerImpl <em>Transition Meta Attributes Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.TransitionMetaAttributesContainerImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getTransitionMetaAttributesContainer()
	 * @generated
	 */
	int TRANSITION_META_ATTRIBUTES_CONTAINER = 52;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_META_ATTRIBUTES_CONTAINER__ENTRIES = 0;

	/**
	 * The number of structural features of the '<em>Transition Meta Attributes Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_META_ATTRIBUTES_CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.impl.TransitionsContainerImpl <em>Transitions Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.impl.TransitionsContainerImpl
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getTransitionsContainer()
	 * @generated
	 */
	int TRANSITIONS_CONTAINER = 53;

	/**
	 * The feature id for the '<em><b>Definitions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITIONS_CONTAINER__DEFINITIONS = 0;

	/**
	 * The number of structural features of the '<em>Transitions Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITIONS_CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.DepartureDiscipline <em>Departure Discipline</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.DepartureDiscipline
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getDepartureDiscipline()
	 * @generated
	 */
	int DEPARTURE_DISCIPLINE = 54;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.DistributionFunction <em>Distribution Function</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.DistributionFunction
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getDistributionFunction()
	 * @generated
	 */
	int DISTRIBUTION_FUNCTION = 55;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.FlowDirection <em>Flow Direction</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.FlowDirection
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getFlowDirection()
	 * @generated
	 */
	int FLOW_DIRECTION = 56;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.ProbeTrigger <em>Probe Trigger</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.ProbeTrigger
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getProbeTrigger()
	 * @generated
	 */
	int PROBE_TRIGGER = 57;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.QueueingStrategy <em>Queueing Strategy</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingStrategy
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getQueueingStrategy()
	 * @generated
	 */
	int QUEUEING_STRATEGY = 58;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnSimulationScenario <em>Simqpn Simulation Scenario</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnSimulationScenario
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSimqpnSimulationScenario()
	 * @generated
	 */
	int SIMQPN_SIMULATION_SCENARIO = 59;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnStoppingRule <em>Simqpn Stopping Rule</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnStoppingRule
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSimqpnStoppingRule()
	 * @generated
	 */
	int SIMQPN_STOPPING_RULE = 60;

	/**
	 * The meta object id for the '<em>Departure Discipline Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.DepartureDiscipline
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getDepartureDisciplineObject()
	 * @generated
	 */
	int DEPARTURE_DISCIPLINE_OBJECT = 61;

	/**
	 * The meta object id for the '<em>Distribution Function Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.DistributionFunction
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getDistributionFunctionObject()
	 * @generated
	 */
	int DISTRIBUTION_FUNCTION_OBJECT = 62;

	/**
	 * The meta object id for the '<em>Flow Direction Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.FlowDirection
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getFlowDirectionObject()
	 * @generated
	 */
	int FLOW_DIRECTION_OBJECT = 63;

	/**
	 * The meta object id for the '<em>Probe Trigger Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.ProbeTrigger
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getProbeTriggerObject()
	 * @generated
	 */
	int PROBE_TRIGGER_OBJECT = 64;

	/**
	 * The meta object id for the '<em>Queueing Strategy Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingStrategy
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getQueueingStrategyObject()
	 * @generated
	 */
	int QUEUEING_STRATEGY_OBJECT = 65;

	/**
	 * The meta object id for the '<em>Rgb Value</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getRgbValue()
	 * @generated
	 */
	int RGB_VALUE = 66;

	/**
	 * The meta object id for the '<em>Simqpn Simulation Scenario Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnSimulationScenario
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSimqpnSimulationScenarioObject()
	 * @generated
	 */
	int SIMQPN_SIMULATION_SCENARIO_OBJECT = 67;

	/**
	 * The meta object id for the '<em>Simqpn Stopping Rule Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnStoppingRule
	 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSimqpnStoppingRuleObject()
	 * @generated
	 */
	int SIMQPN_STOPPING_RULE_OBJECT = 68;


	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.Color <em>Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Color</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Color
	 * @generated
	 */
	EClass getColor();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.Color#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Color#getDescription()
	 * @see #getColor()
	 * @generated
	 */
	EAttribute getColor_Description();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.Color#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Color#getName()
	 * @see #getColor()
	 * @generated
	 */
	EAttribute getColor_Name();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.Color#getRealColor <em>Real Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Real Color</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Color#getRealColor()
	 * @see #getColor()
	 * @generated
	 */
	EAttribute getColor_RealColor();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.ColorReference <em>Color Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Color Reference</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.ColorReference
	 * @generated
	 */
	EClass getColorReference();

	/**
	 * Returns the meta object for the containment reference '{@link edu.kit.ipd.descartes.qpme.model.ColorReference#getMetaAttributes <em>Meta Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Meta Attributes</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.ColorReference#getMetaAttributes()
	 * @see #getColorReference()
	 * @generated
	 */
	EReference getColorReference_MetaAttributes();

	/**
	 * Returns the meta object for the reference '{@link edu.kit.ipd.descartes.qpme.model.ColorReference#getColor <em>Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Color</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.ColorReference#getColor()
	 * @see #getColorReference()
	 * @generated
	 */
	EReference getColorReference_Color();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.ColorReferenceMetaAttribute <em>Color Reference Meta Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Color Reference Meta Attribute</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.ColorReferenceMetaAttribute
	 * @generated
	 */
	EClass getColorReferenceMetaAttribute();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.ColorReferencesContainer <em>Color References Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Color References Container</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.ColorReferencesContainer
	 * @generated
	 */
	EClass getColorReferencesContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.ipd.descartes.qpme.model.ColorReferencesContainer#getDefinitions <em>Definitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Definitions</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.ColorReferencesContainer#getDefinitions()
	 * @see #getColorReferencesContainer()
	 * @generated
	 */
	EReference getColorReferencesContainer_Definitions();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.ColorReferencesMetaAttributesContainer <em>Color References Meta Attributes Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Color References Meta Attributes Container</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.ColorReferencesMetaAttributesContainer
	 * @generated
	 */
	EClass getColorReferencesMetaAttributesContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.ipd.descartes.qpme.model.ColorReferencesMetaAttributesContainer#getEntries <em>Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entries</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.ColorReferencesMetaAttributesContainer#getEntries()
	 * @see #getColorReferencesMetaAttributesContainer()
	 * @generated
	 */
	EReference getColorReferencesMetaAttributesContainer_Entries();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.ColorsContainer <em>Colors Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Colors Container</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.ColorsContainer
	 * @generated
	 */
	EClass getColorsContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.ipd.descartes.qpme.model.ColorsContainer#getDefinitions <em>Definitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Definitions</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.ColorsContainer#getDefinitions()
	 * @see #getColorsContainer()
	 * @generated
	 */
	EReference getColorsContainer_Definitions();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.IdentifiableElement <em>Identifiable Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Identifiable Element</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.IdentifiableElement
	 * @generated
	 */
	EClass getIdentifiableElement();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.IdentifiableElement#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.IdentifiableElement#getId()
	 * @see #getIdentifiableElement()
	 * @generated
	 */
	EAttribute getIdentifiableElement_Id();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.ImmediateTransition <em>Immediate Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Immediate Transition</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.ImmediateTransition
	 * @generated
	 */
	EClass getImmediateTransition();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.ImmediateTransition#getWeight <em>Weight</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Weight</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.ImmediateTransition#getWeight()
	 * @see #getImmediateTransition()
	 * @generated
	 */
	EAttribute getImmediateTransition_Weight();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnection <em>Incidence Function Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Incidence Function Connection</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnection
	 * @generated
	 */
	EClass getIncidenceFunctionConnection();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnection#getCount <em>Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Count</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnection#getCount()
	 * @see #getIncidenceFunctionConnection()
	 * @generated
	 */
	EAttribute getIncidenceFunctionConnection_Count();

	/**
	 * Returns the meta object for the reference '{@link edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnection#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnection#getSource()
	 * @see #getIncidenceFunctionConnection()
	 * @generated
	 */
	EReference getIncidenceFunctionConnection_Source();

	/**
	 * Returns the meta object for the reference '{@link edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnection#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnection#getTarget()
	 * @see #getIncidenceFunctionConnection()
	 * @generated
	 */
	EReference getIncidenceFunctionConnection_Target();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnectionsContainer <em>Incidence Function Connections Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Incidence Function Connections Container</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnectionsContainer
	 * @generated
	 */
	EClass getIncidenceFunctionConnectionsContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnectionsContainer#getDefinitions <em>Definitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Definitions</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnectionsContainer#getDefinitions()
	 * @see #getIncidenceFunctionConnectionsContainer()
	 * @generated
	 */
	EReference getIncidenceFunctionConnectionsContainer_Definitions();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.IncidenceFunctionElement <em>Incidence Function Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Incidence Function Element</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.IncidenceFunctionElement
	 * @generated
	 */
	EClass getIncidenceFunctionElement();

	/**
	 * Returns the meta object for the reference list '{@link edu.kit.ipd.descartes.qpme.model.IncidenceFunctionElement#getIncomingConnections <em>Incoming Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Incoming Connections</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.IncidenceFunctionElement#getIncomingConnections()
	 * @see #getIncidenceFunctionElement()
	 * @generated
	 */
	EReference getIncidenceFunctionElement_IncomingConnections();

	/**
	 * Returns the meta object for the reference list '{@link edu.kit.ipd.descartes.qpme.model.IncidenceFunctionElement#getOutgoingConnections <em>Outgoing Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Outgoing Connections</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.IncidenceFunctionElement#getOutgoingConnections()
	 * @see #getIncidenceFunctionElement()
	 * @generated
	 */
	EReference getIncidenceFunctionElement_OutgoingConnections();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.LocationAttribute <em>Location Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Location Attribute</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.LocationAttribute
	 * @generated
	 */
	EClass getLocationAttribute();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.LocationAttribute#getLocationX <em>Location X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Location X</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.LocationAttribute#getLocationX()
	 * @see #getLocationAttribute()
	 * @generated
	 */
	EAttribute getLocationAttribute_LocationX();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.LocationAttribute#getLocationY <em>Location Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Location Y</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.LocationAttribute#getLocationY()
	 * @see #getLocationAttribute()
	 * @generated
	 */
	EAttribute getLocationAttribute_LocationY();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.Mode <em>Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mode</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Mode
	 * @generated
	 */
	EClass getMode();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.Mode#getFiringWeight <em>Firing Weight</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Firing Weight</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Mode#getFiringWeight()
	 * @see #getMode()
	 * @generated
	 */
	EAttribute getMode_FiringWeight();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.Mode#getMeanFiringDelay <em>Mean Firing Delay</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mean Firing Delay</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Mode#getMeanFiringDelay()
	 * @see #getMode()
	 * @generated
	 */
	EAttribute getMode_MeanFiringDelay();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.Mode#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Mode#getName()
	 * @see #getMode()
	 * @generated
	 */
	EAttribute getMode_Name();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.Mode#getRealColor <em>Real Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Real Color</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Mode#getRealColor()
	 * @see #getMode()
	 * @generated
	 */
	EAttribute getMode_RealColor();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.ModesContainer <em>Modes Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Modes Container</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.ModesContainer
	 * @generated
	 */
	EClass getModesContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.ipd.descartes.qpme.model.ModesContainer#getDefinitions <em>Definitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Definitions</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.ModesContainer#getDefinitions()
	 * @see #getModesContainer()
	 * @generated
	 */
	EReference getModesContainer_Definitions();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.NetMetaAttribute <em>Net Meta Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Net Meta Attribute</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.NetMetaAttribute
	 * @generated
	 */
	EClass getNetMetaAttribute();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.NetMetaAttributesContainer <em>Net Meta Attributes Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Net Meta Attributes Container</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.NetMetaAttributesContainer
	 * @generated
	 */
	EClass getNetMetaAttributesContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.ipd.descartes.qpme.model.NetMetaAttributesContainer#getEntries <em>Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entries</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.NetMetaAttributesContainer#getEntries()
	 * @see #getNetMetaAttributesContainer()
	 * @generated
	 */
	EReference getNetMetaAttributesContainer_Entries();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.OrdinaryColorReference <em>Ordinary Color Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ordinary Color Reference</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.OrdinaryColorReference
	 * @generated
	 */
	EClass getOrdinaryColorReference();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.OrdinaryPlace <em>Ordinary Place</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ordinary Place</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.OrdinaryPlace
	 * @generated
	 */
	EClass getOrdinaryPlace();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.Place <em>Place</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Place</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Place
	 * @generated
	 */
	EClass getPlace();

	/**
	 * Returns the meta object for the containment reference '{@link edu.kit.ipd.descartes.qpme.model.Place#getColorReferences <em>Color References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Color References</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Place#getColorReferences()
	 * @see #getPlace()
	 * @generated
	 */
	EReference getPlace_ColorReferences();

	/**
	 * Returns the meta object for the containment reference '{@link edu.kit.ipd.descartes.qpme.model.Place#getMetaAttributes <em>Meta Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Meta Attributes</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Place#getMetaAttributes()
	 * @see #getPlace()
	 * @generated
	 */
	EReference getPlace_MetaAttributes();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.Place#getDepartureDiscipline <em>Departure Discipline</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Departure Discipline</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Place#getDepartureDiscipline()
	 * @see #getPlace()
	 * @generated
	 */
	EAttribute getPlace_DepartureDiscipline();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.Place#isLocked <em>Locked</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Locked</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Place#isLocked()
	 * @see #getPlace()
	 * @generated
	 */
	EAttribute getPlace_Locked();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.Place#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Place#getName()
	 * @see #getPlace()
	 * @generated
	 */
	EAttribute getPlace_Name();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.PlaceColorReference <em>Place Color Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Place Color Reference</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.PlaceColorReference
	 * @generated
	 */
	EClass getPlaceColorReference();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.PlaceColorReference#getInitialPopulation <em>Initial Population</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Initial Population</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.PlaceColorReference#getInitialPopulation()
	 * @see #getPlaceColorReference()
	 * @generated
	 */
	EAttribute getPlaceColorReference_InitialPopulation();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.PlaceColorReference#getMaximumCapacity <em>Maximum Capacity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Maximum Capacity</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.PlaceColorReference#getMaximumCapacity()
	 * @see #getPlaceColorReference()
	 * @generated
	 */
	EAttribute getPlaceColorReference_MaximumCapacity();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.PlaceMetaAttribute <em>Place Meta Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Place Meta Attribute</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.PlaceMetaAttribute
	 * @generated
	 */
	EClass getPlaceMetaAttribute();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.PlaceMetaAttributesContainer <em>Place Meta Attributes Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Place Meta Attributes Container</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.PlaceMetaAttributesContainer
	 * @generated
	 */
	EClass getPlaceMetaAttributesContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.ipd.descartes.qpme.model.PlaceMetaAttributesContainer#getEntries <em>Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entries</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.PlaceMetaAttributesContainer#getEntries()
	 * @see #getPlaceMetaAttributesContainer()
	 * @generated
	 */
	EReference getPlaceMetaAttributesContainer_Entries();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.PlacesContainer <em>Places Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Places Container</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.PlacesContainer
	 * @generated
	 */
	EClass getPlacesContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.ipd.descartes.qpme.model.PlacesContainer#getDefinitions <em>Definitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Definitions</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.PlacesContainer#getDefinitions()
	 * @see #getPlacesContainer()
	 * @generated
	 */
	EReference getPlacesContainer_Definitions();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.PlaceTransitionConnection <em>Place Transition Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Place Transition Connection</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.PlaceTransitionConnection
	 * @generated
	 */
	EClass getPlaceTransitionConnection();

	/**
	 * Returns the meta object for the reference '{@link edu.kit.ipd.descartes.qpme.model.PlaceTransitionConnection#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.PlaceTransitionConnection#getSource()
	 * @see #getPlaceTransitionConnection()
	 * @generated
	 */
	EReference getPlaceTransitionConnection_Source();

	/**
	 * Returns the meta object for the reference '{@link edu.kit.ipd.descartes.qpme.model.PlaceTransitionConnection#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.PlaceTransitionConnection#getTarget()
	 * @see #getPlaceTransitionConnection()
	 * @generated
	 */
	EReference getPlaceTransitionConnection_Target();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.PlaceTransitionConnectionsContainer <em>Place Transition Connections Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Place Transition Connections Container</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.PlaceTransitionConnectionsContainer
	 * @generated
	 */
	EClass getPlaceTransitionConnectionsContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.ipd.descartes.qpme.model.PlaceTransitionConnectionsContainer#getDefinitions <em>Definitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Definitions</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.PlaceTransitionConnectionsContainer#getDefinitions()
	 * @see #getPlaceTransitionConnectionsContainer()
	 * @generated
	 */
	EReference getPlaceTransitionConnectionsContainer_Definitions();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.PlaceTransitionElement <em>Place Transition Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Place Transition Element</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.PlaceTransitionElement
	 * @generated
	 */
	EClass getPlaceTransitionElement();

	/**
	 * Returns the meta object for the reference list '{@link edu.kit.ipd.descartes.qpme.model.PlaceTransitionElement#getIncomingConnections <em>Incoming Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Incoming Connections</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.PlaceTransitionElement#getIncomingConnections()
	 * @see #getPlaceTransitionElement()
	 * @generated
	 */
	EReference getPlaceTransitionElement_IncomingConnections();

	/**
	 * Returns the meta object for the reference list '{@link edu.kit.ipd.descartes.qpme.model.PlaceTransitionElement#getOutgoingConnections <em>Outgoing Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Outgoing Connections</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.PlaceTransitionElement#getOutgoingConnections()
	 * @see #getPlaceTransitionElement()
	 * @generated
	 */
	EReference getPlaceTransitionElement_OutgoingConnections();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.Probe <em>Probe</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Probe</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Probe
	 * @generated
	 */
	EClass getProbe();

	/**
	 * Returns the meta object for the containment reference '{@link edu.kit.ipd.descartes.qpme.model.Probe#getColorReferences <em>Color References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Color References</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Probe#getColorReferences()
	 * @see #getProbe()
	 * @generated
	 */
	EReference getProbe_ColorReferences();

	/**
	 * Returns the meta object for the containment reference '{@link edu.kit.ipd.descartes.qpme.model.Probe#getMetaAttributes <em>Meta Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Meta Attributes</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Probe#getMetaAttributes()
	 * @see #getProbe()
	 * @generated
	 */
	EReference getProbe_MetaAttributes();

	/**
	 * Returns the meta object for the reference '{@link edu.kit.ipd.descartes.qpme.model.Probe#getEndPlace <em>End Place</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>End Place</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Probe#getEndPlace()
	 * @see #getProbe()
	 * @generated
	 */
	EReference getProbe_EndPlace();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.Probe#getEndTrigger <em>End Trigger</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>End Trigger</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Probe#getEndTrigger()
	 * @see #getProbe()
	 * @generated
	 */
	EAttribute getProbe_EndTrigger();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.Probe#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Probe#getName()
	 * @see #getProbe()
	 * @generated
	 */
	EAttribute getProbe_Name();

	/**
	 * Returns the meta object for the reference '{@link edu.kit.ipd.descartes.qpme.model.Probe#getStartPlace <em>Start Place</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Start Place</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Probe#getStartPlace()
	 * @see #getProbe()
	 * @generated
	 */
	EReference getProbe_StartPlace();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.Probe#getStartTrigger <em>Start Trigger</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Start Trigger</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Probe#getStartTrigger()
	 * @see #getProbe()
	 * @generated
	 */
	EAttribute getProbe_StartTrigger();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.ProbeColorReference <em>Probe Color Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Probe Color Reference</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.ProbeColorReference
	 * @generated
	 */
	EClass getProbeColorReference();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.ProbeMetaAttribute <em>Probe Meta Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Probe Meta Attribute</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.ProbeMetaAttribute
	 * @generated
	 */
	EClass getProbeMetaAttribute();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.ProbeMetaAttributesContainer <em>Probe Meta Attributes Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Probe Meta Attributes Container</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.ProbeMetaAttributesContainer
	 * @generated
	 */
	EClass getProbeMetaAttributesContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.ipd.descartes.qpme.model.ProbeMetaAttributesContainer#getEntries <em>Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entries</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.ProbeMetaAttributesContainer#getEntries()
	 * @see #getProbeMetaAttributesContainer()
	 * @generated
	 */
	EReference getProbeMetaAttributesContainer_Entries();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.ProbesContainer <em>Probes Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Probes Container</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.ProbesContainer
	 * @generated
	 */
	EClass getProbesContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.ipd.descartes.qpme.model.ProbesContainer#getDefinitions <em>Definitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Definitions</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.ProbesContainer#getDefinitions()
	 * @see #getProbesContainer()
	 * @generated
	 */
	EReference getProbesContainer_Definitions();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.QpmeDocument <em>Qpme Document</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Qpme Document</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QpmeDocument
	 * @generated
	 */
	EClass getQpmeDocument();

	/**
	 * Returns the meta object for the attribute list '{@link edu.kit.ipd.descartes.qpme.model.QpmeDocument#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QpmeDocument#getMixed()
	 * @see #getQpmeDocument()
	 * @generated
	 */
	EAttribute getQpmeDocument_Mixed();

	/**
	 * Returns the meta object for the map '{@link edu.kit.ipd.descartes.qpme.model.QpmeDocument#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QpmeDocument#getXMLNSPrefixMap()
	 * @see #getQpmeDocument()
	 * @generated
	 */
	EReference getQpmeDocument_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link edu.kit.ipd.descartes.qpme.model.QpmeDocument#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QpmeDocument#getXSISchemaLocation()
	 * @see #getQpmeDocument()
	 * @generated
	 */
	EReference getQpmeDocument_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.ipd.descartes.qpme.model.QpmeDocument#getNet <em>Net</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Net</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QpmeDocument#getNet()
	 * @see #getQpmeDocument()
	 * @generated
	 */
	EReference getQpmeDocument_Net();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.Queue <em>Queue</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Queue</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Queue
	 * @generated
	 */
	EClass getQueue();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.Queue#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Queue#getName()
	 * @see #getQueue()
	 * @generated
	 */
	EAttribute getQueue_Name();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.Queue#getNumberOfServers <em>Number Of Servers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Of Servers</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Queue#getNumberOfServers()
	 * @see #getQueue()
	 * @generated
	 */
	EAttribute getQueue_NumberOfServers();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.Queue#getStrategy <em>Strategy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Strategy</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Queue#getStrategy()
	 * @see #getQueue()
	 * @generated
	 */
	EAttribute getQueue_Strategy();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference <em>Queueing Color Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Queueing Color Reference</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingColorReference
	 * @generated
	 */
	EClass getQueueingColorReference();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getAlpha <em>Alpha</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Alpha</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getAlpha()
	 * @see #getQueueingColorReference()
	 * @generated
	 */
	EAttribute getQueueingColorReference_Alpha();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getBeta <em>Beta</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Beta</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getBeta()
	 * @see #getQueueingColorReference()
	 * @generated
	 */
	EAttribute getQueueingColorReference_Beta();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getCut <em>Cut</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cut</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getCut()
	 * @see #getQueueingColorReference()
	 * @generated
	 */
	EAttribute getQueueingColorReference_Cut();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getDistributionFunction <em>Distribution Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Distribution Function</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getDistributionFunction()
	 * @see #getQueueingColorReference()
	 * @generated
	 */
	EAttribute getQueueingColorReference_DistributionFunction();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getFreedom <em>Freedom</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Freedom</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getFreedom()
	 * @see #getQueueingColorReference()
	 * @generated
	 */
	EAttribute getQueueingColorReference_Freedom();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getGamma <em>Gamma</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Gamma</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getGamma()
	 * @see #getQueueingColorReference()
	 * @generated
	 */
	EAttribute getQueueingColorReference_Gamma();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getInputFile <em>Input File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Input File</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getInputFile()
	 * @see #getQueueingColorReference()
	 * @generated
	 */
	EAttribute getQueueingColorReference_InputFile();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getLambda <em>Lambda</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lambda</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getLambda()
	 * @see #getQueueingColorReference()
	 * @generated
	 */
	EAttribute getQueueingColorReference_Lambda();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getMax <em>Max</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getMax()
	 * @see #getQueueingColorReference()
	 * @generated
	 */
	EAttribute getQueueingColorReference_Max();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getMean <em>Mean</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mean</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getMean()
	 * @see #getQueueingColorReference()
	 * @generated
	 */
	EAttribute getQueueingColorReference_Mean();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getMin <em>Min</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getMin()
	 * @see #getQueueingColorReference()
	 * @generated
	 */
	EAttribute getQueueingColorReference_Min();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getP <em>P</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>P</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getP()
	 * @see #getQueueingColorReference()
	 * @generated
	 */
	EAttribute getQueueingColorReference_P();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getC <em>C</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>C</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getC()
	 * @see #getQueueingColorReference()
	 * @generated
	 */
	EAttribute getQueueingColorReference_C();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getPriority <em>Priority</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Priority</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getPriority()
	 * @see #getQueueingColorReference()
	 * @generated
	 */
	EAttribute getQueueingColorReference_Priority();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getRanking <em>Ranking</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ranking</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getRanking()
	 * @see #getQueueingColorReference()
	 * @generated
	 */
	EAttribute getQueueingColorReference_Ranking();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getStdDev <em>Std Dev</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Std Dev</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getStdDev()
	 * @see #getQueueingColorReference()
	 * @generated
	 */
	EAttribute getQueueingColorReference_StdDev();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getTau <em>Tau</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tau</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getTau()
	 * @see #getQueueingColorReference()
	 * @generated
	 */
	EAttribute getQueueingColorReference_Tau();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getOffset <em>Offset</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Offset</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getOffset()
	 * @see #getQueueingColorReference()
	 * @generated
	 */
	EAttribute getQueueingColorReference_Offset();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getScale <em>Scale</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Scale</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getScale()
	 * @see #getQueueingColorReference()
	 * @generated
	 */
	EAttribute getQueueingColorReference_Scale();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getReplayFile <em>Replay File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Replay File</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getReplayFile()
	 * @see #getQueueingColorReference()
	 * @generated
	 */
	EAttribute getQueueingColorReference_ReplayFile();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getValuesFile <em>Values File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Values File</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getValuesFile()
	 * @see #getQueueingColorReference()
	 * @generated
	 */
	EAttribute getQueueingColorReference_ValuesFile();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getProbabilitiesFile <em>Probabilities File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Probabilities File</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getProbabilitiesFile()
	 * @see #getQueueingColorReference()
	 * @generated
	 */
	EAttribute getQueueingColorReference_ProbabilitiesFile();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getConcurrenciesFile <em>Concurrencies File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Concurrencies File</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getConcurrenciesFile()
	 * @see #getQueueingColorReference()
	 * @generated
	 */
	EAttribute getQueueingColorReference_ConcurrenciesFile();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getResponsetimesFile <em>Responsetimes File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Responsetimes File</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getResponsetimesFile()
	 * @see #getQueueingColorReference()
	 * @generated
	 */
	EAttribute getQueueingColorReference_ResponsetimesFile();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getMarsFile <em>Mars File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mars File</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getMarsFile()
	 * @see #getQueueingColorReference()
	 * @generated
	 */
	EAttribute getQueueingColorReference_MarsFile();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.QueueingPetriNet <em>Queueing Petri Net</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Queueing Petri Net</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingPetriNet
	 * @generated
	 */
	EClass getQueueingPetriNet();

	/**
	 * Returns the meta object for the containment reference '{@link edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getColors <em>Colors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Colors</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getColors()
	 * @see #getQueueingPetriNet()
	 * @generated
	 */
	EReference getQueueingPetriNet_Colors();

	/**
	 * Returns the meta object for the containment reference '{@link edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getQueues <em>Queues</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Queues</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getQueues()
	 * @see #getQueueingPetriNet()
	 * @generated
	 */
	EReference getQueueingPetriNet_Queues();

	/**
	 * Returns the meta object for the containment reference '{@link edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getPlaces <em>Places</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Places</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getPlaces()
	 * @see #getQueueingPetriNet()
	 * @generated
	 */
	EReference getQueueingPetriNet_Places();

	/**
	 * Returns the meta object for the containment reference '{@link edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getTransitions <em>Transitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Transitions</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getTransitions()
	 * @see #getQueueingPetriNet()
	 * @generated
	 */
	EReference getQueueingPetriNet_Transitions();

	/**
	 * Returns the meta object for the containment reference '{@link edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getConnections <em>Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Connections</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getConnections()
	 * @see #getQueueingPetriNet()
	 * @generated
	 */
	EReference getQueueingPetriNet_Connections();

	/**
	 * Returns the meta object for the containment reference '{@link edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getProbes <em>Probes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Probes</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getProbes()
	 * @see #getQueueingPetriNet()
	 * @generated
	 */
	EReference getQueueingPetriNet_Probes();

	/**
	 * Returns the meta object for the containment reference '{@link edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getMetaAttributes <em>Meta Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Meta Attributes</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getMetaAttributes()
	 * @see #getQueueingPetriNet()
	 * @generated
	 */
	EReference getQueueingPetriNet_MetaAttributes();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getQpmeVersion <em>Qpme Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Qpme Version</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getQpmeVersion()
	 * @see #getQueueingPetriNet()
	 * @generated
	 */
	EAttribute getQueueingPetriNet_QpmeVersion();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.QueueingPlace <em>Queueing Place</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Queueing Place</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingPlace
	 * @generated
	 */
	EClass getQueueingPlace();

	/**
	 * Returns the meta object for the reference '{@link edu.kit.ipd.descartes.qpme.model.QueueingPlace#getQueue <em>Queue</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Queue</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingPlace#getQueue()
	 * @see #getQueueingPlace()
	 * @generated
	 */
	EReference getQueueingPlace_Queue();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.QueuesContainer <em>Queues Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Queues Container</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueuesContainer
	 * @generated
	 */
	EClass getQueuesContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.ipd.descartes.qpme.model.QueuesContainer#getDefinitions <em>Definitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Definitions</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueuesContainer#getDefinitions()
	 * @see #getQueuesContainer()
	 * @generated
	 */
	EReference getQueuesContainer_Definitions();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration <em>Simqpn Batch Means Color Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simqpn Batch Means Color Configuration</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration
	 * @generated
	 */
	EClass getSimqpnBatchMeansColorConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getBatchSize <em>Batch Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Batch Size</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getBatchSize()
	 * @see #getSimqpnBatchMeansColorConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnBatchMeansColorConfiguration_BatchSize();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getBucketSize <em>Bucket Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Bucket Size</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getBucketSize()
	 * @see #getSimqpnBatchMeansColorConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnBatchMeansColorConfiguration_BucketSize();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getMaxBuckets <em>Max Buckets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Buckets</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getMaxBuckets()
	 * @see #getSimqpnBatchMeansColorConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnBatchMeansColorConfiguration_MaxBuckets();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getMinBatches <em>Min Batches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min Batches</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getMinBatches()
	 * @see #getSimqpnBatchMeansColorConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnBatchMeansColorConfiguration_MinBatches();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getNumBMeansCorlTested <em>Num BMeans Corl Tested</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Num BMeans Corl Tested</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getNumBMeansCorlTested()
	 * @see #getSimqpnBatchMeansColorConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnBatchMeansColorConfiguration_NumBMeansCorlTested();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getReqAbsPrc <em>Req Abs Prc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Req Abs Prc</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getReqAbsPrc()
	 * @see #getSimqpnBatchMeansColorConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnBatchMeansColorConfiguration_ReqAbsPrc();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getReqRelPrc <em>Req Rel Prc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Req Rel Prc</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getReqRelPrc()
	 * @see #getSimqpnBatchMeansColorConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnBatchMeansColorConfiguration_ReqRelPrc();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getSignLev <em>Sign Lev</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sign Lev</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getSignLev()
	 * @see #getSimqpnBatchMeansColorConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnBatchMeansColorConfiguration_SignLev();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration <em>Simqpn Batch Means Queueing Color Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simqpn Batch Means Queueing Color Configuration</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration
	 * @generated
	 */
	EClass getSimqpnBatchMeansQueueingColorConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueBatchSize <em>Queue Batch Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Queue Batch Size</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueBatchSize()
	 * @see #getSimqpnBatchMeansQueueingColorConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnBatchMeansQueueingColorConfiguration_QueueBatchSize();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueBucketSize <em>Queue Bucket Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Queue Bucket Size</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueBucketSize()
	 * @see #getSimqpnBatchMeansQueueingColorConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnBatchMeansQueueingColorConfiguration_QueueBucketSize();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueMaxBuckets <em>Queue Max Buckets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Queue Max Buckets</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueMaxBuckets()
	 * @see #getSimqpnBatchMeansQueueingColorConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnBatchMeansQueueingColorConfiguration_QueueMaxBuckets();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueMinBatches <em>Queue Min Batches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Queue Min Batches</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueMinBatches()
	 * @see #getSimqpnBatchMeansQueueingColorConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnBatchMeansQueueingColorConfiguration_QueueMinBatches();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueNumBMeansCorlTested <em>Queue Num BMeans Corl Tested</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Queue Num BMeans Corl Tested</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueNumBMeansCorlTested()
	 * @see #getSimqpnBatchMeansQueueingColorConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnBatchMeansQueueingColorConfiguration_QueueNumBMeansCorlTested();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueReqAbsPrc <em>Queue Req Abs Prc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Queue Req Abs Prc</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueReqAbsPrc()
	 * @see #getSimqpnBatchMeansQueueingColorConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnBatchMeansQueueingColorConfiguration_QueueReqAbsPrc();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueReqRelPrc <em>Queue Req Rel Prc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Queue Req Rel Prc</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueReqRelPrc()
	 * @see #getSimqpnBatchMeansQueueingColorConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnBatchMeansQueueingColorConfiguration_QueueReqRelPrc();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueSignLev <em>Queue Sign Lev</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Queue Sign Lev</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueSignLev()
	 * @see #getSimqpnBatchMeansQueueingColorConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnBatchMeansQueueingColorConfiguration_QueueSignLev();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration <em>Simqpn Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simqpn Configuration</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration
	 * @generated
	 */
	EClass getSimqpnConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getConfigurationDescription <em>Configuration Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Configuration Description</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getConfigurationDescription()
	 * @see #getSimqpnConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnConfiguration_ConfigurationDescription();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getNumberOfRuns <em>Number Of Runs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Of Runs</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getNumberOfRuns()
	 * @see #getSimqpnConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnConfiguration_NumberOfRuns();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getOutputDirectory <em>Output Directory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Output Directory</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getOutputDirectory()
	 * @see #getSimqpnConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnConfiguration_OutputDirectory();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getRampUpLength <em>Ramp Up Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ramp Up Length</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getRampUpLength()
	 * @see #getSimqpnConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnConfiguration_RampUpLength();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getScenario <em>Scenario</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Scenario</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getScenario()
	 * @see #getSimqpnConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnConfiguration_Scenario();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getSecondsBetweenHeartBeats <em>Seconds Between Heart Beats</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Seconds Between Heart Beats</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getSecondsBetweenHeartBeats()
	 * @see #getSimqpnConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnConfiguration_SecondsBetweenHeartBeats();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getSecondsBetweenStopChecks <em>Seconds Between Stop Checks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Seconds Between Stop Checks</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getSecondsBetweenStopChecks()
	 * @see #getSimqpnConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnConfiguration_SecondsBetweenStopChecks();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getStoppingRule <em>Stopping Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Stopping Rule</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getStoppingRule()
	 * @see #getSimqpnConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnConfiguration_StoppingRule();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getTimeBeforeInitialHeartBeat <em>Time Before Initial Heart Beat</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time Before Initial Heart Beat</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getTimeBeforeInitialHeartBeat()
	 * @see #getSimqpnConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnConfiguration_TimeBeforeInitialHeartBeat();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getTimeBetweenStopChecks <em>Time Between Stop Checks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time Between Stop Checks</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getTimeBetweenStopChecks()
	 * @see #getSimqpnConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnConfiguration_TimeBetweenStopChecks();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getTotalRunLength <em>Total Run Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Total Run Length</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getTotalRunLength()
	 * @see #getSimqpnConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnConfiguration_TotalRunLength();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getVerbosityLevel <em>Verbosity Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Verbosity Level</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getVerbosityLevel()
	 * @see #getSimqpnConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnConfiguration_VerbosityLevel();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.SimqpnMetaAttribute <em>Simqpn Meta Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simqpn Meta Attribute</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnMetaAttribute
	 * @generated
	 */
	EClass getSimqpnMetaAttribute();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnMetaAttribute#getConfigurationName <em>Configuration Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Configuration Name</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnMetaAttribute#getConfigurationName()
	 * @see #getSimqpnMetaAttribute()
	 * @generated
	 */
	EAttribute getSimqpnMetaAttribute_ConfigurationName();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.SimqpnPlaceConfiguration <em>Simqpn Place Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simqpn Place Configuration</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnPlaceConfiguration
	 * @generated
	 */
	EClass getSimqpnPlaceConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnPlaceConfiguration#getStatsLevel <em>Stats Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Stats Level</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnPlaceConfiguration#getStatsLevel()
	 * @see #getSimqpnPlaceConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnPlaceConfiguration_StatsLevel();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.SimqpnReplDelColorConfiguration <em>Simqpn Repl Del Color Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simqpn Repl Del Color Configuration</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnReplDelColorConfiguration
	 * @generated
	 */
	EClass getSimqpnReplDelColorConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnReplDelColorConfiguration#getSignLevAvgST <em>Sign Lev Avg ST</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sign Lev Avg ST</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnReplDelColorConfiguration#getSignLevAvgST()
	 * @see #getSimqpnReplDelColorConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnReplDelColorConfiguration_SignLevAvgST();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.SimqpnReplDelQueueingColorConfiguration <em>Simqpn Repl Del Queueing Color Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simqpn Repl Del Queueing Color Configuration</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnReplDelQueueingColorConfiguration
	 * @generated
	 */
	EClass getSimqpnReplDelQueueingColorConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnReplDelQueueingColorConfiguration#getQueueSignLevAvgST <em>Queue Sign Lev Avg ST</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Queue Sign Lev Avg ST</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnReplDelQueueingColorConfiguration#getQueueSignLevAvgST()
	 * @see #getSimqpnReplDelQueueingColorConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnReplDelQueueingColorConfiguration_QueueSignLevAvgST();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.SimqpnWelchColorConfiguration <em>Simqpn Welch Color Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simqpn Welch Color Configuration</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnWelchColorConfiguration
	 * @generated
	 */
	EClass getSimqpnWelchColorConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnWelchColorConfiguration#getMaxObsrv <em>Max Obsrv</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Obsrv</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnWelchColorConfiguration#getMaxObsrv()
	 * @see #getSimqpnWelchColorConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnWelchColorConfiguration_MaxObsrv();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnWelchColorConfiguration#getMinObsrv <em>Min Obsrv</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min Obsrv</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnWelchColorConfiguration#getMinObsrv()
	 * @see #getSimqpnWelchColorConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnWelchColorConfiguration_MinObsrv();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.SimqpnWelchQueueingColorConfiguration <em>Simqpn Welch Queueing Color Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simqpn Welch Queueing Color Configuration</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnWelchQueueingColorConfiguration
	 * @generated
	 */
	EClass getSimqpnWelchQueueingColorConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnWelchQueueingColorConfiguration#getQueueMaxObsrv <em>Queue Max Obsrv</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Queue Max Obsrv</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnWelchQueueingColorConfiguration#getQueueMaxObsrv()
	 * @see #getSimqpnWelchQueueingColorConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnWelchQueueingColorConfiguration_QueueMaxObsrv();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SimqpnWelchQueueingColorConfiguration#getQueueMinObsrv <em>Queue Min Obsrv</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Queue Min Obsrv</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnWelchQueueingColorConfiguration#getQueueMinObsrv()
	 * @see #getSimqpnWelchQueueingColorConfiguration()
	 * @generated
	 */
	EAttribute getSimqpnWelchQueueingColorConfiguration_QueueMinObsrv();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.Subnet <em>Subnet</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Subnet</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Subnet
	 * @generated
	 */
	EClass getSubnet();

	/**
	 * Returns the meta object for the containment reference '{@link edu.kit.ipd.descartes.qpme.model.Subnet#getColors <em>Colors</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Colors</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Subnet#getColors()
	 * @see #getSubnet()
	 * @generated
	 */
	EReference getSubnet_Colors();

	/**
	 * Returns the meta object for the containment reference '{@link edu.kit.ipd.descartes.qpme.model.Subnet#getPlaces <em>Places</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Places</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Subnet#getPlaces()
	 * @see #getSubnet()
	 * @generated
	 */
	EReference getSubnet_Places();

	/**
	 * Returns the meta object for the containment reference '{@link edu.kit.ipd.descartes.qpme.model.Subnet#getTransitions <em>Transitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Transitions</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Subnet#getTransitions()
	 * @see #getSubnet()
	 * @generated
	 */
	EReference getSubnet_Transitions();

	/**
	 * Returns the meta object for the containment reference '{@link edu.kit.ipd.descartes.qpme.model.Subnet#getConnections <em>Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Connections</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Subnet#getConnections()
	 * @see #getSubnet()
	 * @generated
	 */
	EReference getSubnet_Connections();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.SubnetColorReference <em>Subnet Color Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Subnet Color Reference</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SubnetColorReference
	 * @generated
	 */
	EClass getSubnetColorReference();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.SubnetColorReference#getDirection <em>Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Direction</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SubnetColorReference#getDirection()
	 * @see #getSubnetColorReference()
	 * @generated
	 */
	EAttribute getSubnetColorReference_Direction();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.SubnetPlace <em>Subnet Place</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Subnet Place</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SubnetPlace
	 * @generated
	 */
	EClass getSubnetPlace();

	/**
	 * Returns the meta object for the containment reference '{@link edu.kit.ipd.descartes.qpme.model.SubnetPlace#getSubnet <em>Subnet</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Subnet</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SubnetPlace#getSubnet()
	 * @see #getSubnetPlace()
	 * @generated
	 */
	EReference getSubnetPlace_Subnet();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.TimedTransition <em>Timed Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Timed Transition</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.TimedTransition
	 * @generated
	 */
	EClass getTimedTransition();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.Transition <em>Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transition</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Transition
	 * @generated
	 */
	EClass getTransition();

	/**
	 * Returns the meta object for the containment reference '{@link edu.kit.ipd.descartes.qpme.model.Transition#getModes <em>Modes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Modes</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Transition#getModes()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_Modes();

	/**
	 * Returns the meta object for the containment reference '{@link edu.kit.ipd.descartes.qpme.model.Transition#getConnections <em>Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Connections</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Transition#getConnections()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_Connections();

	/**
	 * Returns the meta object for the containment reference '{@link edu.kit.ipd.descartes.qpme.model.Transition#getMetaAttributes <em>Meta Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Meta Attributes</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Transition#getMetaAttributes()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_MetaAttributes();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.Transition#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Transition#getName()
	 * @see #getTransition()
	 * @generated
	 */
	EAttribute getTransition_Name();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.model.Transition#getPriority <em>Priority</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Priority</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.Transition#getPriority()
	 * @see #getTransition()
	 * @generated
	 */
	EAttribute getTransition_Priority();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.TransitionMetaAttribute <em>Transition Meta Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transition Meta Attribute</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.TransitionMetaAttribute
	 * @generated
	 */
	EClass getTransitionMetaAttribute();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.TransitionMetaAttributesContainer <em>Transition Meta Attributes Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transition Meta Attributes Container</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.TransitionMetaAttributesContainer
	 * @generated
	 */
	EClass getTransitionMetaAttributesContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.ipd.descartes.qpme.model.TransitionMetaAttributesContainer#getEntries <em>Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entries</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.TransitionMetaAttributesContainer#getEntries()
	 * @see #getTransitionMetaAttributesContainer()
	 * @generated
	 */
	EReference getTransitionMetaAttributesContainer_Entries();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.model.TransitionsContainer <em>Transitions Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transitions Container</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.TransitionsContainer
	 * @generated
	 */
	EClass getTransitionsContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.ipd.descartes.qpme.model.TransitionsContainer#getDefinitions <em>Definitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Definitions</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.TransitionsContainer#getDefinitions()
	 * @see #getTransitionsContainer()
	 * @generated
	 */
	EReference getTransitionsContainer_Definitions();

	/**
	 * Returns the meta object for enum '{@link edu.kit.ipd.descartes.qpme.model.DepartureDiscipline <em>Departure Discipline</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Departure Discipline</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.DepartureDiscipline
	 * @generated
	 */
	EEnum getDepartureDiscipline();

	/**
	 * Returns the meta object for enum '{@link edu.kit.ipd.descartes.qpme.model.DistributionFunction <em>Distribution Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Distribution Function</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.DistributionFunction
	 * @generated
	 */
	EEnum getDistributionFunction();

	/**
	 * Returns the meta object for enum '{@link edu.kit.ipd.descartes.qpme.model.FlowDirection <em>Flow Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Flow Direction</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.FlowDirection
	 * @generated
	 */
	EEnum getFlowDirection();

	/**
	 * Returns the meta object for enum '{@link edu.kit.ipd.descartes.qpme.model.ProbeTrigger <em>Probe Trigger</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Probe Trigger</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.ProbeTrigger
	 * @generated
	 */
	EEnum getProbeTrigger();

	/**
	 * Returns the meta object for enum '{@link edu.kit.ipd.descartes.qpme.model.QueueingStrategy <em>Queueing Strategy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Queueing Strategy</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingStrategy
	 * @generated
	 */
	EEnum getQueueingStrategy();

	/**
	 * Returns the meta object for enum '{@link edu.kit.ipd.descartes.qpme.model.SimqpnSimulationScenario <em>Simqpn Simulation Scenario</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Simqpn Simulation Scenario</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnSimulationScenario
	 * @generated
	 */
	EEnum getSimqpnSimulationScenario();

	/**
	 * Returns the meta object for enum '{@link edu.kit.ipd.descartes.qpme.model.SimqpnStoppingRule <em>Simqpn Stopping Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Simqpn Stopping Rule</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnStoppingRule
	 * @generated
	 */
	EEnum getSimqpnStoppingRule();

	/**
	 * Returns the meta object for data type '{@link edu.kit.ipd.descartes.qpme.model.DepartureDiscipline <em>Departure Discipline Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Departure Discipline Object</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.DepartureDiscipline
	 * @model instanceClass="edu.kit.ipd.descartes.qpme.model.DepartureDiscipline"
	 *        extendedMetaData="name='departure-discipline:Object' baseType='departure-discipline'"
	 * @generated
	 */
	EDataType getDepartureDisciplineObject();

	/**
	 * Returns the meta object for data type '{@link edu.kit.ipd.descartes.qpme.model.DistributionFunction <em>Distribution Function Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Distribution Function Object</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.DistributionFunction
	 * @model instanceClass="edu.kit.ipd.descartes.qpme.model.DistributionFunction"
	 *        extendedMetaData="name='distribution-function:Object' baseType='distribution-function'"
	 * @generated
	 */
	EDataType getDistributionFunctionObject();

	/**
	 * Returns the meta object for data type '{@link edu.kit.ipd.descartes.qpme.model.FlowDirection <em>Flow Direction Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Flow Direction Object</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.FlowDirection
	 * @model instanceClass="edu.kit.ipd.descartes.qpme.model.FlowDirection"
	 *        extendedMetaData="name='flow-direction:Object' baseType='flow-direction'"
	 * @generated
	 */
	EDataType getFlowDirectionObject();

	/**
	 * Returns the meta object for data type '{@link edu.kit.ipd.descartes.qpme.model.ProbeTrigger <em>Probe Trigger Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Probe Trigger Object</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.ProbeTrigger
	 * @model instanceClass="edu.kit.ipd.descartes.qpme.model.ProbeTrigger"
	 *        extendedMetaData="name='probe-trigger:Object' baseType='probe-trigger'"
	 * @generated
	 */
	EDataType getProbeTriggerObject();

	/**
	 * Returns the meta object for data type '{@link edu.kit.ipd.descartes.qpme.model.QueueingStrategy <em>Queueing Strategy Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Queueing Strategy Object</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingStrategy
	 * @model instanceClass="edu.kit.ipd.descartes.qpme.model.QueueingStrategy"
	 *        extendedMetaData="name='queueing-strategy:Object' baseType='queueing-strategy'"
	 * @generated
	 */
	EDataType getQueueingStrategyObject();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Rgb Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Rgb Value</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='rgb-value' baseType='http://www.eclipse.org/emf/2003/XMLType#string'"
	 * @generated
	 */
	EDataType getRgbValue();

	/**
	 * Returns the meta object for data type '{@link edu.kit.ipd.descartes.qpme.model.SimqpnSimulationScenario <em>Simqpn Simulation Scenario Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Simqpn Simulation Scenario Object</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnSimulationScenario
	 * @model instanceClass="edu.kit.ipd.descartes.qpme.model.SimqpnSimulationScenario"
	 *        extendedMetaData="name='simqpn-simulation-scenario:Object' baseType='simqpn-simulation-scenario'"
	 * @generated
	 */
	EDataType getSimqpnSimulationScenarioObject();

	/**
	 * Returns the meta object for data type '{@link edu.kit.ipd.descartes.qpme.model.SimqpnStoppingRule <em>Simqpn Stopping Rule Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Simqpn Stopping Rule Object</em>'.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnStoppingRule
	 * @model instanceClass="edu.kit.ipd.descartes.qpme.model.SimqpnStoppingRule"
	 *        extendedMetaData="name='simqpn-stopping-rule:Object' baseType='simqpn-stopping-rule'"
	 * @generated
	 */
	EDataType getSimqpnStoppingRuleObject();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ModelFactory getModelFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.ColorImpl <em>Color</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ColorImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getColor()
		 * @generated
		 */
		EClass COLOR = eINSTANCE.getColor();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLOR__DESCRIPTION = eINSTANCE.getColor_Description();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLOR__NAME = eINSTANCE.getColor_Name();

		/**
		 * The meta object literal for the '<em><b>Real Color</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLOR__REAL_COLOR = eINSTANCE.getColor_RealColor();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.ColorReferenceImpl <em>Color Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ColorReferenceImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getColorReference()
		 * @generated
		 */
		EClass COLOR_REFERENCE = eINSTANCE.getColorReference();

		/**
		 * The meta object literal for the '<em><b>Meta Attributes</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COLOR_REFERENCE__META_ATTRIBUTES = eINSTANCE.getColorReference_MetaAttributes();

		/**
		 * The meta object literal for the '<em><b>Color</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COLOR_REFERENCE__COLOR = eINSTANCE.getColorReference_Color();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.ColorReferenceMetaAttributeImpl <em>Color Reference Meta Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ColorReferenceMetaAttributeImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getColorReferenceMetaAttribute()
		 * @generated
		 */
		EClass COLOR_REFERENCE_META_ATTRIBUTE = eINSTANCE.getColorReferenceMetaAttribute();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.ColorReferencesContainerImpl <em>Color References Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ColorReferencesContainerImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getColorReferencesContainer()
		 * @generated
		 */
		EClass COLOR_REFERENCES_CONTAINER = eINSTANCE.getColorReferencesContainer();

		/**
		 * The meta object literal for the '<em><b>Definitions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COLOR_REFERENCES_CONTAINER__DEFINITIONS = eINSTANCE.getColorReferencesContainer_Definitions();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.ColorReferencesMetaAttributesContainerImpl <em>Color References Meta Attributes Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ColorReferencesMetaAttributesContainerImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getColorReferencesMetaAttributesContainer()
		 * @generated
		 */
		EClass COLOR_REFERENCES_META_ATTRIBUTES_CONTAINER = eINSTANCE.getColorReferencesMetaAttributesContainer();

		/**
		 * The meta object literal for the '<em><b>Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COLOR_REFERENCES_META_ATTRIBUTES_CONTAINER__ENTRIES = eINSTANCE.getColorReferencesMetaAttributesContainer_Entries();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.ColorsContainerImpl <em>Colors Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ColorsContainerImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getColorsContainer()
		 * @generated
		 */
		EClass COLORS_CONTAINER = eINSTANCE.getColorsContainer();

		/**
		 * The meta object literal for the '<em><b>Definitions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COLORS_CONTAINER__DEFINITIONS = eINSTANCE.getColorsContainer_Definitions();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.IdentifiableElementImpl <em>Identifiable Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.IdentifiableElementImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getIdentifiableElement()
		 * @generated
		 */
		EClass IDENTIFIABLE_ELEMENT = eINSTANCE.getIdentifiableElement();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTIFIABLE_ELEMENT__ID = eINSTANCE.getIdentifiableElement_Id();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.ImmediateTransitionImpl <em>Immediate Transition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ImmediateTransitionImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getImmediateTransition()
		 * @generated
		 */
		EClass IMMEDIATE_TRANSITION = eINSTANCE.getImmediateTransition();

		/**
		 * The meta object literal for the '<em><b>Weight</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMMEDIATE_TRANSITION__WEIGHT = eINSTANCE.getImmediateTransition_Weight();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.IncidenceFunctionConnectionImpl <em>Incidence Function Connection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.IncidenceFunctionConnectionImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getIncidenceFunctionConnection()
		 * @generated
		 */
		EClass INCIDENCE_FUNCTION_CONNECTION = eINSTANCE.getIncidenceFunctionConnection();

		/**
		 * The meta object literal for the '<em><b>Count</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INCIDENCE_FUNCTION_CONNECTION__COUNT = eINSTANCE.getIncidenceFunctionConnection_Count();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INCIDENCE_FUNCTION_CONNECTION__SOURCE = eINSTANCE.getIncidenceFunctionConnection_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INCIDENCE_FUNCTION_CONNECTION__TARGET = eINSTANCE.getIncidenceFunctionConnection_Target();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.IncidenceFunctionConnectionsContainerImpl <em>Incidence Function Connections Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.IncidenceFunctionConnectionsContainerImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getIncidenceFunctionConnectionsContainer()
		 * @generated
		 */
		EClass INCIDENCE_FUNCTION_CONNECTIONS_CONTAINER = eINSTANCE.getIncidenceFunctionConnectionsContainer();

		/**
		 * The meta object literal for the '<em><b>Definitions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INCIDENCE_FUNCTION_CONNECTIONS_CONTAINER__DEFINITIONS = eINSTANCE.getIncidenceFunctionConnectionsContainer_Definitions();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.IncidenceFunctionElementImpl <em>Incidence Function Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.IncidenceFunctionElementImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getIncidenceFunctionElement()
		 * @generated
		 */
		EClass INCIDENCE_FUNCTION_ELEMENT = eINSTANCE.getIncidenceFunctionElement();

		/**
		 * The meta object literal for the '<em><b>Incoming Connections</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INCIDENCE_FUNCTION_ELEMENT__INCOMING_CONNECTIONS = eINSTANCE.getIncidenceFunctionElement_IncomingConnections();

		/**
		 * The meta object literal for the '<em><b>Outgoing Connections</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INCIDENCE_FUNCTION_ELEMENT__OUTGOING_CONNECTIONS = eINSTANCE.getIncidenceFunctionElement_OutgoingConnections();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.LocationAttributeImpl <em>Location Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.LocationAttributeImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getLocationAttribute()
		 * @generated
		 */
		EClass LOCATION_ATTRIBUTE = eINSTANCE.getLocationAttribute();

		/**
		 * The meta object literal for the '<em><b>Location X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCATION_ATTRIBUTE__LOCATION_X = eINSTANCE.getLocationAttribute_LocationX();

		/**
		 * The meta object literal for the '<em><b>Location Y</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCATION_ATTRIBUTE__LOCATION_Y = eINSTANCE.getLocationAttribute_LocationY();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.ModeImpl <em>Mode</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModeImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getMode()
		 * @generated
		 */
		EClass MODE = eINSTANCE.getMode();

		/**
		 * The meta object literal for the '<em><b>Firing Weight</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODE__FIRING_WEIGHT = eINSTANCE.getMode_FiringWeight();

		/**
		 * The meta object literal for the '<em><b>Mean Firing Delay</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODE__MEAN_FIRING_DELAY = eINSTANCE.getMode_MeanFiringDelay();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODE__NAME = eINSTANCE.getMode_Name();

		/**
		 * The meta object literal for the '<em><b>Real Color</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODE__REAL_COLOR = eINSTANCE.getMode_RealColor();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.ModesContainerImpl <em>Modes Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModesContainerImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getModesContainer()
		 * @generated
		 */
		EClass MODES_CONTAINER = eINSTANCE.getModesContainer();

		/**
		 * The meta object literal for the '<em><b>Definitions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODES_CONTAINER__DEFINITIONS = eINSTANCE.getModesContainer_Definitions();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.NetMetaAttributeImpl <em>Net Meta Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.NetMetaAttributeImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getNetMetaAttribute()
		 * @generated
		 */
		EClass NET_META_ATTRIBUTE = eINSTANCE.getNetMetaAttribute();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.NetMetaAttributesContainerImpl <em>Net Meta Attributes Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.NetMetaAttributesContainerImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getNetMetaAttributesContainer()
		 * @generated
		 */
		EClass NET_META_ATTRIBUTES_CONTAINER = eINSTANCE.getNetMetaAttributesContainer();

		/**
		 * The meta object literal for the '<em><b>Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NET_META_ATTRIBUTES_CONTAINER__ENTRIES = eINSTANCE.getNetMetaAttributesContainer_Entries();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.OrdinaryColorReferenceImpl <em>Ordinary Color Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.OrdinaryColorReferenceImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getOrdinaryColorReference()
		 * @generated
		 */
		EClass ORDINARY_COLOR_REFERENCE = eINSTANCE.getOrdinaryColorReference();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.OrdinaryPlaceImpl <em>Ordinary Place</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.OrdinaryPlaceImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getOrdinaryPlace()
		 * @generated
		 */
		EClass ORDINARY_PLACE = eINSTANCE.getOrdinaryPlace();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.PlaceImpl <em>Place</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.PlaceImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getPlace()
		 * @generated
		 */
		EClass PLACE = eINSTANCE.getPlace();

		/**
		 * The meta object literal for the '<em><b>Color References</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLACE__COLOR_REFERENCES = eINSTANCE.getPlace_ColorReferences();

		/**
		 * The meta object literal for the '<em><b>Meta Attributes</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLACE__META_ATTRIBUTES = eINSTANCE.getPlace_MetaAttributes();

		/**
		 * The meta object literal for the '<em><b>Departure Discipline</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLACE__DEPARTURE_DISCIPLINE = eINSTANCE.getPlace_DepartureDiscipline();

		/**
		 * The meta object literal for the '<em><b>Locked</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLACE__LOCKED = eINSTANCE.getPlace_Locked();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLACE__NAME = eINSTANCE.getPlace_Name();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.PlaceColorReferenceImpl <em>Place Color Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.PlaceColorReferenceImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getPlaceColorReference()
		 * @generated
		 */
		EClass PLACE_COLOR_REFERENCE = eINSTANCE.getPlaceColorReference();

		/**
		 * The meta object literal for the '<em><b>Initial Population</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLACE_COLOR_REFERENCE__INITIAL_POPULATION = eINSTANCE.getPlaceColorReference_InitialPopulation();

		/**
		 * The meta object literal for the '<em><b>Maximum Capacity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLACE_COLOR_REFERENCE__MAXIMUM_CAPACITY = eINSTANCE.getPlaceColorReference_MaximumCapacity();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.PlaceMetaAttributeImpl <em>Place Meta Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.PlaceMetaAttributeImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getPlaceMetaAttribute()
		 * @generated
		 */
		EClass PLACE_META_ATTRIBUTE = eINSTANCE.getPlaceMetaAttribute();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.PlaceMetaAttributesContainerImpl <em>Place Meta Attributes Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.PlaceMetaAttributesContainerImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getPlaceMetaAttributesContainer()
		 * @generated
		 */
		EClass PLACE_META_ATTRIBUTES_CONTAINER = eINSTANCE.getPlaceMetaAttributesContainer();

		/**
		 * The meta object literal for the '<em><b>Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLACE_META_ATTRIBUTES_CONTAINER__ENTRIES = eINSTANCE.getPlaceMetaAttributesContainer_Entries();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.PlacesContainerImpl <em>Places Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.PlacesContainerImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getPlacesContainer()
		 * @generated
		 */
		EClass PLACES_CONTAINER = eINSTANCE.getPlacesContainer();

		/**
		 * The meta object literal for the '<em><b>Definitions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLACES_CONTAINER__DEFINITIONS = eINSTANCE.getPlacesContainer_Definitions();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.PlaceTransitionConnectionImpl <em>Place Transition Connection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.PlaceTransitionConnectionImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getPlaceTransitionConnection()
		 * @generated
		 */
		EClass PLACE_TRANSITION_CONNECTION = eINSTANCE.getPlaceTransitionConnection();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLACE_TRANSITION_CONNECTION__SOURCE = eINSTANCE.getPlaceTransitionConnection_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLACE_TRANSITION_CONNECTION__TARGET = eINSTANCE.getPlaceTransitionConnection_Target();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.PlaceTransitionConnectionsContainerImpl <em>Place Transition Connections Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.PlaceTransitionConnectionsContainerImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getPlaceTransitionConnectionsContainer()
		 * @generated
		 */
		EClass PLACE_TRANSITION_CONNECTIONS_CONTAINER = eINSTANCE.getPlaceTransitionConnectionsContainer();

		/**
		 * The meta object literal for the '<em><b>Definitions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLACE_TRANSITION_CONNECTIONS_CONTAINER__DEFINITIONS = eINSTANCE.getPlaceTransitionConnectionsContainer_Definitions();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.PlaceTransitionElementImpl <em>Place Transition Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.PlaceTransitionElementImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getPlaceTransitionElement()
		 * @generated
		 */
		EClass PLACE_TRANSITION_ELEMENT = eINSTANCE.getPlaceTransitionElement();

		/**
		 * The meta object literal for the '<em><b>Incoming Connections</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLACE_TRANSITION_ELEMENT__INCOMING_CONNECTIONS = eINSTANCE.getPlaceTransitionElement_IncomingConnections();

		/**
		 * The meta object literal for the '<em><b>Outgoing Connections</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLACE_TRANSITION_ELEMENT__OUTGOING_CONNECTIONS = eINSTANCE.getPlaceTransitionElement_OutgoingConnections();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.ProbeImpl <em>Probe</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ProbeImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getProbe()
		 * @generated
		 */
		EClass PROBE = eINSTANCE.getProbe();

		/**
		 * The meta object literal for the '<em><b>Color References</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROBE__COLOR_REFERENCES = eINSTANCE.getProbe_ColorReferences();

		/**
		 * The meta object literal for the '<em><b>Meta Attributes</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROBE__META_ATTRIBUTES = eINSTANCE.getProbe_MetaAttributes();

		/**
		 * The meta object literal for the '<em><b>End Place</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROBE__END_PLACE = eINSTANCE.getProbe_EndPlace();

		/**
		 * The meta object literal for the '<em><b>End Trigger</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROBE__END_TRIGGER = eINSTANCE.getProbe_EndTrigger();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROBE__NAME = eINSTANCE.getProbe_Name();

		/**
		 * The meta object literal for the '<em><b>Start Place</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROBE__START_PLACE = eINSTANCE.getProbe_StartPlace();

		/**
		 * The meta object literal for the '<em><b>Start Trigger</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROBE__START_TRIGGER = eINSTANCE.getProbe_StartTrigger();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.ProbeColorReferenceImpl <em>Probe Color Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ProbeColorReferenceImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getProbeColorReference()
		 * @generated
		 */
		EClass PROBE_COLOR_REFERENCE = eINSTANCE.getProbeColorReference();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.ProbeMetaAttributeImpl <em>Probe Meta Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ProbeMetaAttributeImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getProbeMetaAttribute()
		 * @generated
		 */
		EClass PROBE_META_ATTRIBUTE = eINSTANCE.getProbeMetaAttribute();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.ProbeMetaAttributesContainerImpl <em>Probe Meta Attributes Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ProbeMetaAttributesContainerImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getProbeMetaAttributesContainer()
		 * @generated
		 */
		EClass PROBE_META_ATTRIBUTES_CONTAINER = eINSTANCE.getProbeMetaAttributesContainer();

		/**
		 * The meta object literal for the '<em><b>Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROBE_META_ATTRIBUTES_CONTAINER__ENTRIES = eINSTANCE.getProbeMetaAttributesContainer_Entries();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.ProbesContainerImpl <em>Probes Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ProbesContainerImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getProbesContainer()
		 * @generated
		 */
		EClass PROBES_CONTAINER = eINSTANCE.getProbesContainer();

		/**
		 * The meta object literal for the '<em><b>Definitions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROBES_CONTAINER__DEFINITIONS = eINSTANCE.getProbesContainer_Definitions();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.QpmeDocumentImpl <em>Qpme Document</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.QpmeDocumentImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getQpmeDocument()
		 * @generated
		 */
		EClass QPME_DOCUMENT = eINSTANCE.getQpmeDocument();

		/**
		 * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QPME_DOCUMENT__MIXED = eINSTANCE.getQpmeDocument_Mixed();

		/**
		 * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QPME_DOCUMENT__XMLNS_PREFIX_MAP = eINSTANCE.getQpmeDocument_XMLNSPrefixMap();

		/**
		 * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QPME_DOCUMENT__XSI_SCHEMA_LOCATION = eINSTANCE.getQpmeDocument_XSISchemaLocation();

		/**
		 * The meta object literal for the '<em><b>Net</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QPME_DOCUMENT__NET = eINSTANCE.getQpmeDocument_Net();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.QueueImpl <em>Queue</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.QueueImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getQueue()
		 * @generated
		 */
		EClass QUEUE = eINSTANCE.getQueue();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUEUE__NAME = eINSTANCE.getQueue_Name();

		/**
		 * The meta object literal for the '<em><b>Number Of Servers</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUEUE__NUMBER_OF_SERVERS = eINSTANCE.getQueue_NumberOfServers();

		/**
		 * The meta object literal for the '<em><b>Strategy</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUEUE__STRATEGY = eINSTANCE.getQueue_Strategy();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingColorReferenceImpl <em>Queueing Color Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.QueueingColorReferenceImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getQueueingColorReference()
		 * @generated
		 */
		EClass QUEUEING_COLOR_REFERENCE = eINSTANCE.getQueueingColorReference();

		/**
		 * The meta object literal for the '<em><b>Alpha</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUEUEING_COLOR_REFERENCE__ALPHA = eINSTANCE.getQueueingColorReference_Alpha();

		/**
		 * The meta object literal for the '<em><b>Beta</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUEUEING_COLOR_REFERENCE__BETA = eINSTANCE.getQueueingColorReference_Beta();

		/**
		 * The meta object literal for the '<em><b>Cut</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUEUEING_COLOR_REFERENCE__CUT = eINSTANCE.getQueueingColorReference_Cut();

		/**
		 * The meta object literal for the '<em><b>Distribution Function</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUEUEING_COLOR_REFERENCE__DISTRIBUTION_FUNCTION = eINSTANCE.getQueueingColorReference_DistributionFunction();

		/**
		 * The meta object literal for the '<em><b>Freedom</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUEUEING_COLOR_REFERENCE__FREEDOM = eINSTANCE.getQueueingColorReference_Freedom();

		/**
		 * The meta object literal for the '<em><b>Gamma</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUEUEING_COLOR_REFERENCE__GAMMA = eINSTANCE.getQueueingColorReference_Gamma();

		/**
		 * The meta object literal for the '<em><b>Input File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUEUEING_COLOR_REFERENCE__INPUT_FILE = eINSTANCE.getQueueingColorReference_InputFile();

		/**
		 * The meta object literal for the '<em><b>Lambda</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUEUEING_COLOR_REFERENCE__LAMBDA = eINSTANCE.getQueueingColorReference_Lambda();

		/**
		 * The meta object literal for the '<em><b>Max</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUEUEING_COLOR_REFERENCE__MAX = eINSTANCE.getQueueingColorReference_Max();

		/**
		 * The meta object literal for the '<em><b>Mean</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUEUEING_COLOR_REFERENCE__MEAN = eINSTANCE.getQueueingColorReference_Mean();

		/**
		 * The meta object literal for the '<em><b>Min</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUEUEING_COLOR_REFERENCE__MIN = eINSTANCE.getQueueingColorReference_Min();

		/**
		 * The meta object literal for the '<em><b>P</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUEUEING_COLOR_REFERENCE__P = eINSTANCE.getQueueingColorReference_P();

		/**
		 * The meta object literal for the '<em><b>C</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUEUEING_COLOR_REFERENCE__C = eINSTANCE.getQueueingColorReference_C();

		/**
		 * The meta object literal for the '<em><b>Priority</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUEUEING_COLOR_REFERENCE__PRIORITY = eINSTANCE.getQueueingColorReference_Priority();

		/**
		 * The meta object literal for the '<em><b>Ranking</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUEUEING_COLOR_REFERENCE__RANKING = eINSTANCE.getQueueingColorReference_Ranking();

		/**
		 * The meta object literal for the '<em><b>Std Dev</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUEUEING_COLOR_REFERENCE__STD_DEV = eINSTANCE.getQueueingColorReference_StdDev();

		/**
		 * The meta object literal for the '<em><b>Tau</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUEUEING_COLOR_REFERENCE__TAU = eINSTANCE.getQueueingColorReference_Tau();

		/**
		 * The meta object literal for the '<em><b>Offset</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUEUEING_COLOR_REFERENCE__OFFSET = eINSTANCE.getQueueingColorReference_Offset();

		/**
		 * The meta object literal for the '<em><b>Scale</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUEUEING_COLOR_REFERENCE__SCALE = eINSTANCE.getQueueingColorReference_Scale();

		/**
		 * The meta object literal for the '<em><b>Replay File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUEUEING_COLOR_REFERENCE__REPLAY_FILE = eINSTANCE.getQueueingColorReference_ReplayFile();

		/**
		 * The meta object literal for the '<em><b>Values File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUEUEING_COLOR_REFERENCE__VALUES_FILE = eINSTANCE.getQueueingColorReference_ValuesFile();

		/**
		 * The meta object literal for the '<em><b>Probabilities File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUEUEING_COLOR_REFERENCE__PROBABILITIES_FILE = eINSTANCE.getQueueingColorReference_ProbabilitiesFile();

		/**
		 * The meta object literal for the '<em><b>Concurrencies File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUEUEING_COLOR_REFERENCE__CONCURRENCIES_FILE = eINSTANCE.getQueueingColorReference_ConcurrenciesFile();

		/**
		 * The meta object literal for the '<em><b>Responsetimes File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUEUEING_COLOR_REFERENCE__RESPONSETIMES_FILE = eINSTANCE.getQueueingColorReference_ResponsetimesFile();

		/**
		 * The meta object literal for the '<em><b>Mars File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUEUEING_COLOR_REFERENCE__MARS_FILE = eINSTANCE.getQueueingColorReference_MarsFile();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingPetriNetImpl <em>Queueing Petri Net</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.QueueingPetriNetImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getQueueingPetriNet()
		 * @generated
		 */
		EClass QUEUEING_PETRI_NET = eINSTANCE.getQueueingPetriNet();

		/**
		 * The meta object literal for the '<em><b>Colors</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUEUEING_PETRI_NET__COLORS = eINSTANCE.getQueueingPetriNet_Colors();

		/**
		 * The meta object literal for the '<em><b>Queues</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUEUEING_PETRI_NET__QUEUES = eINSTANCE.getQueueingPetriNet_Queues();

		/**
		 * The meta object literal for the '<em><b>Places</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUEUEING_PETRI_NET__PLACES = eINSTANCE.getQueueingPetriNet_Places();

		/**
		 * The meta object literal for the '<em><b>Transitions</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUEUEING_PETRI_NET__TRANSITIONS = eINSTANCE.getQueueingPetriNet_Transitions();

		/**
		 * The meta object literal for the '<em><b>Connections</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUEUEING_PETRI_NET__CONNECTIONS = eINSTANCE.getQueueingPetriNet_Connections();

		/**
		 * The meta object literal for the '<em><b>Probes</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUEUEING_PETRI_NET__PROBES = eINSTANCE.getQueueingPetriNet_Probes();

		/**
		 * The meta object literal for the '<em><b>Meta Attributes</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUEUEING_PETRI_NET__META_ATTRIBUTES = eINSTANCE.getQueueingPetriNet_MetaAttributes();

		/**
		 * The meta object literal for the '<em><b>Qpme Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUEUEING_PETRI_NET__QPME_VERSION = eINSTANCE.getQueueingPetriNet_QpmeVersion();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingPlaceImpl <em>Queueing Place</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.QueueingPlaceImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getQueueingPlace()
		 * @generated
		 */
		EClass QUEUEING_PLACE = eINSTANCE.getQueueingPlace();

		/**
		 * The meta object literal for the '<em><b>Queue</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUEUEING_PLACE__QUEUE = eINSTANCE.getQueueingPlace_Queue();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.QueuesContainerImpl <em>Queues Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.QueuesContainerImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getQueuesContainer()
		 * @generated
		 */
		EClass QUEUES_CONTAINER = eINSTANCE.getQueuesContainer();

		/**
		 * The meta object literal for the '<em><b>Definitions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUEUES_CONTAINER__DEFINITIONS = eINSTANCE.getQueuesContainer_Definitions();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnBatchMeansColorConfigurationImpl <em>Simqpn Batch Means Color Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.SimqpnBatchMeansColorConfigurationImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSimqpnBatchMeansColorConfiguration()
		 * @generated
		 */
		EClass SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION = eINSTANCE.getSimqpnBatchMeansColorConfiguration();

		/**
		 * The meta object literal for the '<em><b>Batch Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__BATCH_SIZE = eINSTANCE.getSimqpnBatchMeansColorConfiguration_BatchSize();

		/**
		 * The meta object literal for the '<em><b>Bucket Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__BUCKET_SIZE = eINSTANCE.getSimqpnBatchMeansColorConfiguration_BucketSize();

		/**
		 * The meta object literal for the '<em><b>Max Buckets</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__MAX_BUCKETS = eINSTANCE.getSimqpnBatchMeansColorConfiguration_MaxBuckets();

		/**
		 * The meta object literal for the '<em><b>Min Batches</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__MIN_BATCHES = eINSTANCE.getSimqpnBatchMeansColorConfiguration_MinBatches();

		/**
		 * The meta object literal for the '<em><b>Num BMeans Corl Tested</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__NUM_BMEANS_CORL_TESTED = eINSTANCE.getSimqpnBatchMeansColorConfiguration_NumBMeansCorlTested();

		/**
		 * The meta object literal for the '<em><b>Req Abs Prc</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__REQ_ABS_PRC = eINSTANCE.getSimqpnBatchMeansColorConfiguration_ReqAbsPrc();

		/**
		 * The meta object literal for the '<em><b>Req Rel Prc</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__REQ_REL_PRC = eINSTANCE.getSimqpnBatchMeansColorConfiguration_ReqRelPrc();

		/**
		 * The meta object literal for the '<em><b>Sign Lev</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__SIGN_LEV = eINSTANCE.getSimqpnBatchMeansColorConfiguration_SignLev();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnBatchMeansQueueingColorConfigurationImpl <em>Simqpn Batch Means Queueing Color Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.SimqpnBatchMeansQueueingColorConfigurationImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSimqpnBatchMeansQueueingColorConfiguration()
		 * @generated
		 */
		EClass SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION = eINSTANCE.getSimqpnBatchMeansQueueingColorConfiguration();

		/**
		 * The meta object literal for the '<em><b>Queue Batch Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_BATCH_SIZE = eINSTANCE.getSimqpnBatchMeansQueueingColorConfiguration_QueueBatchSize();

		/**
		 * The meta object literal for the '<em><b>Queue Bucket Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_BUCKET_SIZE = eINSTANCE.getSimqpnBatchMeansQueueingColorConfiguration_QueueBucketSize();

		/**
		 * The meta object literal for the '<em><b>Queue Max Buckets</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_MAX_BUCKETS = eINSTANCE.getSimqpnBatchMeansQueueingColorConfiguration_QueueMaxBuckets();

		/**
		 * The meta object literal for the '<em><b>Queue Min Batches</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_MIN_BATCHES = eINSTANCE.getSimqpnBatchMeansQueueingColorConfiguration_QueueMinBatches();

		/**
		 * The meta object literal for the '<em><b>Queue Num BMeans Corl Tested</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_NUM_BMEANS_CORL_TESTED = eINSTANCE.getSimqpnBatchMeansQueueingColorConfiguration_QueueNumBMeansCorlTested();

		/**
		 * The meta object literal for the '<em><b>Queue Req Abs Prc</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_REQ_ABS_PRC = eINSTANCE.getSimqpnBatchMeansQueueingColorConfiguration_QueueReqAbsPrc();

		/**
		 * The meta object literal for the '<em><b>Queue Req Rel Prc</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_REQ_REL_PRC = eINSTANCE.getSimqpnBatchMeansQueueingColorConfiguration_QueueReqRelPrc();

		/**
		 * The meta object literal for the '<em><b>Queue Sign Lev</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_SIGN_LEV = eINSTANCE.getSimqpnBatchMeansQueueingColorConfiguration_QueueSignLev();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnConfigurationImpl <em>Simqpn Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.SimqpnConfigurationImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSimqpnConfiguration()
		 * @generated
		 */
		EClass SIMQPN_CONFIGURATION = eINSTANCE.getSimqpnConfiguration();

		/**
		 * The meta object literal for the '<em><b>Configuration Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_CONFIGURATION__CONFIGURATION_DESCRIPTION = eINSTANCE.getSimqpnConfiguration_ConfigurationDescription();

		/**
		 * The meta object literal for the '<em><b>Number Of Runs</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_CONFIGURATION__NUMBER_OF_RUNS = eINSTANCE.getSimqpnConfiguration_NumberOfRuns();

		/**
		 * The meta object literal for the '<em><b>Output Directory</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_CONFIGURATION__OUTPUT_DIRECTORY = eINSTANCE.getSimqpnConfiguration_OutputDirectory();

		/**
		 * The meta object literal for the '<em><b>Ramp Up Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_CONFIGURATION__RAMP_UP_LENGTH = eINSTANCE.getSimqpnConfiguration_RampUpLength();

		/**
		 * The meta object literal for the '<em><b>Scenario</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_CONFIGURATION__SCENARIO = eINSTANCE.getSimqpnConfiguration_Scenario();

		/**
		 * The meta object literal for the '<em><b>Seconds Between Heart Beats</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_CONFIGURATION__SECONDS_BETWEEN_HEART_BEATS = eINSTANCE.getSimqpnConfiguration_SecondsBetweenHeartBeats();

		/**
		 * The meta object literal for the '<em><b>Seconds Between Stop Checks</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_CONFIGURATION__SECONDS_BETWEEN_STOP_CHECKS = eINSTANCE.getSimqpnConfiguration_SecondsBetweenStopChecks();

		/**
		 * The meta object literal for the '<em><b>Stopping Rule</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_CONFIGURATION__STOPPING_RULE = eINSTANCE.getSimqpnConfiguration_StoppingRule();

		/**
		 * The meta object literal for the '<em><b>Time Before Initial Heart Beat</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_CONFIGURATION__TIME_BEFORE_INITIAL_HEART_BEAT = eINSTANCE.getSimqpnConfiguration_TimeBeforeInitialHeartBeat();

		/**
		 * The meta object literal for the '<em><b>Time Between Stop Checks</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_CONFIGURATION__TIME_BETWEEN_STOP_CHECKS = eINSTANCE.getSimqpnConfiguration_TimeBetweenStopChecks();

		/**
		 * The meta object literal for the '<em><b>Total Run Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_CONFIGURATION__TOTAL_RUN_LENGTH = eINSTANCE.getSimqpnConfiguration_TotalRunLength();

		/**
		 * The meta object literal for the '<em><b>Verbosity Level</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_CONFIGURATION__VERBOSITY_LEVEL = eINSTANCE.getSimqpnConfiguration_VerbosityLevel();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnMetaAttributeImpl <em>Simqpn Meta Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.SimqpnMetaAttributeImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSimqpnMetaAttribute()
		 * @generated
		 */
		EClass SIMQPN_META_ATTRIBUTE = eINSTANCE.getSimqpnMetaAttribute();

		/**
		 * The meta object literal for the '<em><b>Configuration Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_META_ATTRIBUTE__CONFIGURATION_NAME = eINSTANCE.getSimqpnMetaAttribute_ConfigurationName();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnPlaceConfigurationImpl <em>Simqpn Place Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.SimqpnPlaceConfigurationImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSimqpnPlaceConfiguration()
		 * @generated
		 */
		EClass SIMQPN_PLACE_CONFIGURATION = eINSTANCE.getSimqpnPlaceConfiguration();

		/**
		 * The meta object literal for the '<em><b>Stats Level</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_PLACE_CONFIGURATION__STATS_LEVEL = eINSTANCE.getSimqpnPlaceConfiguration_StatsLevel();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnReplDelColorConfigurationImpl <em>Simqpn Repl Del Color Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.SimqpnReplDelColorConfigurationImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSimqpnReplDelColorConfiguration()
		 * @generated
		 */
		EClass SIMQPN_REPL_DEL_COLOR_CONFIGURATION = eINSTANCE.getSimqpnReplDelColorConfiguration();

		/**
		 * The meta object literal for the '<em><b>Sign Lev Avg ST</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_REPL_DEL_COLOR_CONFIGURATION__SIGN_LEV_AVG_ST = eINSTANCE.getSimqpnReplDelColorConfiguration_SignLevAvgST();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnReplDelQueueingColorConfigurationImpl <em>Simqpn Repl Del Queueing Color Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.SimqpnReplDelQueueingColorConfigurationImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSimqpnReplDelQueueingColorConfiguration()
		 * @generated
		 */
		EClass SIMQPN_REPL_DEL_QUEUEING_COLOR_CONFIGURATION = eINSTANCE.getSimqpnReplDelQueueingColorConfiguration();

		/**
		 * The meta object literal for the '<em><b>Queue Sign Lev Avg ST</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_REPL_DEL_QUEUEING_COLOR_CONFIGURATION__QUEUE_SIGN_LEV_AVG_ST = eINSTANCE.getSimqpnReplDelQueueingColorConfiguration_QueueSignLevAvgST();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnWelchColorConfigurationImpl <em>Simqpn Welch Color Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.SimqpnWelchColorConfigurationImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSimqpnWelchColorConfiguration()
		 * @generated
		 */
		EClass SIMQPN_WELCH_COLOR_CONFIGURATION = eINSTANCE.getSimqpnWelchColorConfiguration();

		/**
		 * The meta object literal for the '<em><b>Max Obsrv</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_WELCH_COLOR_CONFIGURATION__MAX_OBSRV = eINSTANCE.getSimqpnWelchColorConfiguration_MaxObsrv();

		/**
		 * The meta object literal for the '<em><b>Min Obsrv</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_WELCH_COLOR_CONFIGURATION__MIN_OBSRV = eINSTANCE.getSimqpnWelchColorConfiguration_MinObsrv();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnWelchQueueingColorConfigurationImpl <em>Simqpn Welch Queueing Color Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.SimqpnWelchQueueingColorConfigurationImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSimqpnWelchQueueingColorConfiguration()
		 * @generated
		 */
		EClass SIMQPN_WELCH_QUEUEING_COLOR_CONFIGURATION = eINSTANCE.getSimqpnWelchQueueingColorConfiguration();

		/**
		 * The meta object literal for the '<em><b>Queue Max Obsrv</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_WELCH_QUEUEING_COLOR_CONFIGURATION__QUEUE_MAX_OBSRV = eINSTANCE.getSimqpnWelchQueueingColorConfiguration_QueueMaxObsrv();

		/**
		 * The meta object literal for the '<em><b>Queue Min Obsrv</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_WELCH_QUEUEING_COLOR_CONFIGURATION__QUEUE_MIN_OBSRV = eINSTANCE.getSimqpnWelchQueueingColorConfiguration_QueueMinObsrv();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.SubnetImpl <em>Subnet</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.SubnetImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSubnet()
		 * @generated
		 */
		EClass SUBNET = eINSTANCE.getSubnet();

		/**
		 * The meta object literal for the '<em><b>Colors</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUBNET__COLORS = eINSTANCE.getSubnet_Colors();

		/**
		 * The meta object literal for the '<em><b>Places</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUBNET__PLACES = eINSTANCE.getSubnet_Places();

		/**
		 * The meta object literal for the '<em><b>Transitions</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUBNET__TRANSITIONS = eINSTANCE.getSubnet_Transitions();

		/**
		 * The meta object literal for the '<em><b>Connections</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUBNET__CONNECTIONS = eINSTANCE.getSubnet_Connections();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.SubnetColorReferenceImpl <em>Subnet Color Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.SubnetColorReferenceImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSubnetColorReference()
		 * @generated
		 */
		EClass SUBNET_COLOR_REFERENCE = eINSTANCE.getSubnetColorReference();

		/**
		 * The meta object literal for the '<em><b>Direction</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUBNET_COLOR_REFERENCE__DIRECTION = eINSTANCE.getSubnetColorReference_Direction();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.SubnetPlaceImpl <em>Subnet Place</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.SubnetPlaceImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSubnetPlace()
		 * @generated
		 */
		EClass SUBNET_PLACE = eINSTANCE.getSubnetPlace();

		/**
		 * The meta object literal for the '<em><b>Subnet</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUBNET_PLACE__SUBNET = eINSTANCE.getSubnetPlace_Subnet();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.TimedTransitionImpl <em>Timed Transition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.TimedTransitionImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getTimedTransition()
		 * @generated
		 */
		EClass TIMED_TRANSITION = eINSTANCE.getTimedTransition();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.TransitionImpl <em>Transition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.TransitionImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getTransition()
		 * @generated
		 */
		EClass TRANSITION = eINSTANCE.getTransition();

		/**
		 * The meta object literal for the '<em><b>Modes</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__MODES = eINSTANCE.getTransition_Modes();

		/**
		 * The meta object literal for the '<em><b>Connections</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__CONNECTIONS = eINSTANCE.getTransition_Connections();

		/**
		 * The meta object literal for the '<em><b>Meta Attributes</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__META_ATTRIBUTES = eINSTANCE.getTransition_MetaAttributes();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSITION__NAME = eINSTANCE.getTransition_Name();

		/**
		 * The meta object literal for the '<em><b>Priority</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSITION__PRIORITY = eINSTANCE.getTransition_Priority();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.TransitionMetaAttributeImpl <em>Transition Meta Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.TransitionMetaAttributeImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getTransitionMetaAttribute()
		 * @generated
		 */
		EClass TRANSITION_META_ATTRIBUTE = eINSTANCE.getTransitionMetaAttribute();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.TransitionMetaAttributesContainerImpl <em>Transition Meta Attributes Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.TransitionMetaAttributesContainerImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getTransitionMetaAttributesContainer()
		 * @generated
		 */
		EClass TRANSITION_META_ATTRIBUTES_CONTAINER = eINSTANCE.getTransitionMetaAttributesContainer();

		/**
		 * The meta object literal for the '<em><b>Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION_META_ATTRIBUTES_CONTAINER__ENTRIES = eINSTANCE.getTransitionMetaAttributesContainer_Entries();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.impl.TransitionsContainerImpl <em>Transitions Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.impl.TransitionsContainerImpl
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getTransitionsContainer()
		 * @generated
		 */
		EClass TRANSITIONS_CONTAINER = eINSTANCE.getTransitionsContainer();

		/**
		 * The meta object literal for the '<em><b>Definitions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITIONS_CONTAINER__DEFINITIONS = eINSTANCE.getTransitionsContainer_Definitions();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.DepartureDiscipline <em>Departure Discipline</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.DepartureDiscipline
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getDepartureDiscipline()
		 * @generated
		 */
		EEnum DEPARTURE_DISCIPLINE = eINSTANCE.getDepartureDiscipline();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.DistributionFunction <em>Distribution Function</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.DistributionFunction
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getDistributionFunction()
		 * @generated
		 */
		EEnum DISTRIBUTION_FUNCTION = eINSTANCE.getDistributionFunction();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.FlowDirection <em>Flow Direction</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.FlowDirection
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getFlowDirection()
		 * @generated
		 */
		EEnum FLOW_DIRECTION = eINSTANCE.getFlowDirection();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.ProbeTrigger <em>Probe Trigger</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.ProbeTrigger
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getProbeTrigger()
		 * @generated
		 */
		EEnum PROBE_TRIGGER = eINSTANCE.getProbeTrigger();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.QueueingStrategy <em>Queueing Strategy</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.QueueingStrategy
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getQueueingStrategy()
		 * @generated
		 */
		EEnum QUEUEING_STRATEGY = eINSTANCE.getQueueingStrategy();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnSimulationScenario <em>Simqpn Simulation Scenario</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.SimqpnSimulationScenario
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSimqpnSimulationScenario()
		 * @generated
		 */
		EEnum SIMQPN_SIMULATION_SCENARIO = eINSTANCE.getSimqpnSimulationScenario();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnStoppingRule <em>Simqpn Stopping Rule</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.SimqpnStoppingRule
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSimqpnStoppingRule()
		 * @generated
		 */
		EEnum SIMQPN_STOPPING_RULE = eINSTANCE.getSimqpnStoppingRule();

		/**
		 * The meta object literal for the '<em>Departure Discipline Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.DepartureDiscipline
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getDepartureDisciplineObject()
		 * @generated
		 */
		EDataType DEPARTURE_DISCIPLINE_OBJECT = eINSTANCE.getDepartureDisciplineObject();

		/**
		 * The meta object literal for the '<em>Distribution Function Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.DistributionFunction
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getDistributionFunctionObject()
		 * @generated
		 */
		EDataType DISTRIBUTION_FUNCTION_OBJECT = eINSTANCE.getDistributionFunctionObject();

		/**
		 * The meta object literal for the '<em>Flow Direction Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.FlowDirection
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getFlowDirectionObject()
		 * @generated
		 */
		EDataType FLOW_DIRECTION_OBJECT = eINSTANCE.getFlowDirectionObject();

		/**
		 * The meta object literal for the '<em>Probe Trigger Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.ProbeTrigger
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getProbeTriggerObject()
		 * @generated
		 */
		EDataType PROBE_TRIGGER_OBJECT = eINSTANCE.getProbeTriggerObject();

		/**
		 * The meta object literal for the '<em>Queueing Strategy Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.QueueingStrategy
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getQueueingStrategyObject()
		 * @generated
		 */
		EDataType QUEUEING_STRATEGY_OBJECT = eINSTANCE.getQueueingStrategyObject();

		/**
		 * The meta object literal for the '<em>Rgb Value</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getRgbValue()
		 * @generated
		 */
		EDataType RGB_VALUE = eINSTANCE.getRgbValue();

		/**
		 * The meta object literal for the '<em>Simqpn Simulation Scenario Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.SimqpnSimulationScenario
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSimqpnSimulationScenarioObject()
		 * @generated
		 */
		EDataType SIMQPN_SIMULATION_SCENARIO_OBJECT = eINSTANCE.getSimqpnSimulationScenarioObject();

		/**
		 * The meta object literal for the '<em>Simqpn Stopping Rule Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.model.SimqpnStoppingRule
		 * @see edu.kit.ipd.descartes.qpme.model.impl.ModelPackageImpl#getSimqpnStoppingRuleObject()
		 * @generated
		 */
		EDataType SIMQPN_STOPPING_RULE_OBJECT = eINSTANCE.getSimqpnStoppingRuleObject();

	}

} //ModelPackage
