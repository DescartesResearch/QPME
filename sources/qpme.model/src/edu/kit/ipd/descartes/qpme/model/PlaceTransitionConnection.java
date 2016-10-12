/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.kit.ipd.descartes.qpme.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Place Transition Connection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.PlaceTransitionConnection#getSource <em>Source</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.PlaceTransitionConnection#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getPlaceTransitionConnection()
 * @model extendedMetaData="name='place-transition-connection' kind='empty'"
 * @generated
 */
public interface PlaceTransitionConnection extends IdentifiableElement {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link edu.kit.ipd.descartes.qpme.model.PlaceTransitionElement#getOutgoingConnections <em>Outgoing Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(PlaceTransitionElement)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getPlaceTransitionConnection_Source()
	 * @see edu.kit.ipd.descartes.qpme.model.PlaceTransitionElement#getOutgoingConnections
	 * @model opposite="outgoingConnections" resolveProxies="false" required="true"
	 *        extendedMetaData="kind='attribute' name='source-id' namespace='##targetNamespace'"
	 * @generated
	 */
	PlaceTransitionElement getSource();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.PlaceTransitionConnection#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(PlaceTransitionElement value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link edu.kit.ipd.descartes.qpme.model.PlaceTransitionElement#getIncomingConnections <em>Incoming Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(PlaceTransitionElement)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getPlaceTransitionConnection_Target()
	 * @see edu.kit.ipd.descartes.qpme.model.PlaceTransitionElement#getIncomingConnections
	 * @model opposite="incomingConnections" resolveProxies="false" required="true"
	 *        extendedMetaData="kind='attribute' name='target-id' namespace='##targetNamespace'"
	 * @generated
	 */
	PlaceTransitionElement getTarget();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.PlaceTransitionConnection#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(PlaceTransitionElement value);

} // PlaceTransitionConnection
