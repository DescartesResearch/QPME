/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.kit.ipd.descartes.qpme.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Place Transition Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.PlaceTransitionElement#getIncomingConnections <em>Incoming Connections</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.PlaceTransitionElement#getOutgoingConnections <em>Outgoing Connections</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getPlaceTransitionElement()
 * @model abstract="true"
 *        extendedMetaData="name='place-transition-element' kind='empty'"
 * @generated
 */
public interface PlaceTransitionElement extends IdentifiableElement {
	/**
	 * Returns the value of the '<em><b>Incoming Connections</b></em>' reference list.
	 * The list contents are of type {@link edu.kit.ipd.descartes.qpme.model.PlaceTransitionConnection}.
	 * It is bidirectional and its opposite is '{@link edu.kit.ipd.descartes.qpme.model.PlaceTransitionConnection#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Incoming Connections</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming Connections</em>' reference list.
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getPlaceTransitionElement_IncomingConnections()
	 * @see edu.kit.ipd.descartes.qpme.model.PlaceTransitionConnection#getTarget
	 * @model opposite="target" resolveProxies="false" transient="true" derived="true"
	 *        extendedMetaData="kind='attribute' name='incoming-connections' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<PlaceTransitionConnection> getIncomingConnections();

	/**
	 * Returns the value of the '<em><b>Outgoing Connections</b></em>' reference list.
	 * The list contents are of type {@link edu.kit.ipd.descartes.qpme.model.PlaceTransitionConnection}.
	 * It is bidirectional and its opposite is '{@link edu.kit.ipd.descartes.qpme.model.PlaceTransitionConnection#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outgoing Connections</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing Connections</em>' reference list.
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getPlaceTransitionElement_OutgoingConnections()
	 * @see edu.kit.ipd.descartes.qpme.model.PlaceTransitionConnection#getSource
	 * @model opposite="source" resolveProxies="false" transient="true" derived="true"
	 *        extendedMetaData="kind='attribute' name='outgoing-connections' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<PlaceTransitionConnection> getOutgoingConnections();

} // PlaceTransitionElement
