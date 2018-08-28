/**
 */
package edu.kit.ipd.descartes.qpme.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Subnet</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Subnet#getColors <em>Colors</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Subnet#getPlaces <em>Places</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Subnet#getTransitions <em>Transitions</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Subnet#getConnections <em>Connections</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSubnet()
 * @model extendedMetaData="name='subnet' kind='elementOnly'"
 * @generated
 */
public interface Subnet extends EObject {
	/**
	 * Returns the value of the '<em><b>Colors</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Colors</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Colors</em>' containment reference.
	 * @see #setColors(ColorsContainer)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSubnet_Colors()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='colors' namespace='##targetNamespace'"
	 * @generated
	 */
	ColorsContainer getColors();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Subnet#getColors <em>Colors</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Colors</em>' containment reference.
	 * @see #getColors()
	 * @generated
	 */
	void setColors(ColorsContainer value);

	/**
	 * Returns the value of the '<em><b>Places</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Places</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Places</em>' containment reference.
	 * @see #setPlaces(PlacesContainer)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSubnet_Places()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='places' namespace='##targetNamespace'"
	 * @generated
	 */
	PlacesContainer getPlaces();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Subnet#getPlaces <em>Places</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Places</em>' containment reference.
	 * @see #getPlaces()
	 * @generated
	 */
	void setPlaces(PlacesContainer value);

	/**
	 * Returns the value of the '<em><b>Transitions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transitions</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transitions</em>' containment reference.
	 * @see #setTransitions(TransitionsContainer)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSubnet_Transitions()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='transitions' namespace='##targetNamespace'"
	 * @generated
	 */
	TransitionsContainer getTransitions();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Subnet#getTransitions <em>Transitions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transitions</em>' containment reference.
	 * @see #getTransitions()
	 * @generated
	 */
	void setTransitions(TransitionsContainer value);

	/**
	 * Returns the value of the '<em><b>Connections</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Connections</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Connections</em>' containment reference.
	 * @see #setConnections(PlaceTransitionConnectionsContainer)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSubnet_Connections()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='connections' namespace='##targetNamespace'"
	 * @generated
	 */
	PlaceTransitionConnectionsContainer getConnections();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Subnet#getConnections <em>Connections</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Connections</em>' containment reference.
	 * @see #getConnections()
	 * @generated
	 */
	void setConnections(PlaceTransitionConnectionsContainer value);

} // Subnet
