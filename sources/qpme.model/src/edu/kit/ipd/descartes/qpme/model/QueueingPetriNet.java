/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.kit.ipd.descartes.qpme.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Queueing Petri Net</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getColors <em>Colors</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getQueues <em>Queues</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getPlaces <em>Places</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getTransitions <em>Transitions</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getConnections <em>Connections</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getProbes <em>Probes</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getMetaAttributes <em>Meta Attributes</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getQpmeVersion <em>Qpme Version</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueueingPetriNet()
 * @model extendedMetaData="name='queueing-petri-net' kind='elementOnly'"
 * @generated
 */
public interface QueueingPetriNet extends EObject {
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
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueueingPetriNet_Colors()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='colors' namespace='##targetNamespace'"
	 * @generated
	 */
	ColorsContainer getColors();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getColors <em>Colors</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Colors</em>' containment reference.
	 * @see #getColors()
	 * @generated
	 */
	void setColors(ColorsContainer value);

	/**
	 * Returns the value of the '<em><b>Queues</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Queues</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Queues</em>' containment reference.
	 * @see #setQueues(QueuesContainer)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueueingPetriNet_Queues()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='queues' namespace='##targetNamespace'"
	 * @generated
	 */
	QueuesContainer getQueues();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getQueues <em>Queues</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Queues</em>' containment reference.
	 * @see #getQueues()
	 * @generated
	 */
	void setQueues(QueuesContainer value);

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
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueueingPetriNet_Places()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='places' namespace='##targetNamespace'"
	 * @generated
	 */
	PlacesContainer getPlaces();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getPlaces <em>Places</em>}' containment reference.
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
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueueingPetriNet_Transitions()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='transitions' namespace='##targetNamespace'"
	 * @generated
	 */
	TransitionsContainer getTransitions();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getTransitions <em>Transitions</em>}' containment reference.
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
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueueingPetriNet_Connections()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='connections' namespace='##targetNamespace'"
	 * @generated
	 */
	PlaceTransitionConnectionsContainer getConnections();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getConnections <em>Connections</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Connections</em>' containment reference.
	 * @see #getConnections()
	 * @generated
	 */
	void setConnections(PlaceTransitionConnectionsContainer value);

	/**
	 * Returns the value of the '<em><b>Probes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Probes</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Probes</em>' containment reference.
	 * @see #setProbes(ProbesContainer)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueueingPetriNet_Probes()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='probes' namespace='##targetNamespace'"
	 * @generated
	 */
	ProbesContainer getProbes();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getProbes <em>Probes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Probes</em>' containment reference.
	 * @see #getProbes()
	 * @generated
	 */
	void setProbes(ProbesContainer value);

	/**
	 * Returns the value of the '<em><b>Meta Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Meta Attributes</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Meta Attributes</em>' containment reference.
	 * @see #setMetaAttributes(NetMetaAttributesContainer)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueueingPetriNet_MetaAttributes()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='meta-attributes' namespace='##targetNamespace'"
	 * @generated
	 */
	NetMetaAttributesContainer getMetaAttributes();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getMetaAttributes <em>Meta Attributes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Meta Attributes</em>' containment reference.
	 * @see #getMetaAttributes()
	 * @generated
	 */
	void setMetaAttributes(NetMetaAttributesContainer value);

	/**
	 * Returns the value of the '<em><b>Qpme Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Qpme Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Qpme Version</em>' attribute.
	 * @see #setQpmeVersion(String)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueueingPetriNet_QpmeVersion()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='qpme-version' namespace='##targetNamespace'"
	 * @generated
	 */
	String getQpmeVersion();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingPetriNet#getQpmeVersion <em>Qpme Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Qpme Version</em>' attribute.
	 * @see #getQpmeVersion()
	 * @generated
	 */
	void setQpmeVersion(String value);

} // QueueingPetriNet
