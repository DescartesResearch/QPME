/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.kit.ipd.descartes.qpme.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Transition#getModes <em>Modes</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Transition#getConnections <em>Connections</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Transition#getMetaAttributes <em>Meta Attributes</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Transition#getName <em>Name</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Transition#getPriority <em>Priority</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getTransition()
 * @model abstract="true"
 *        extendedMetaData="name='transition' kind='elementOnly'"
 * @generated
 */
public interface Transition extends PlaceTransitionElement {
	/**
	 * Returns the value of the '<em><b>Modes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Modes</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Modes</em>' containment reference.
	 * @see #setModes(ModesContainer)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getTransition_Modes()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='modes' namespace='##targetNamespace'"
	 * @generated
	 */
	ModesContainer getModes();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Transition#getModes <em>Modes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Modes</em>' containment reference.
	 * @see #getModes()
	 * @generated
	 */
	void setModes(ModesContainer value);

	/**
	 * Returns the value of the '<em><b>Connections</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Connections</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Connections</em>' containment reference.
	 * @see #setConnections(IncidenceFunctionConnectionsContainer)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getTransition_Connections()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='connections' namespace='##targetNamespace'"
	 * @generated
	 */
	IncidenceFunctionConnectionsContainer getConnections();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Transition#getConnections <em>Connections</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Connections</em>' containment reference.
	 * @see #getConnections()
	 * @generated
	 */
	void setConnections(IncidenceFunctionConnectionsContainer value);

	/**
	 * Returns the value of the '<em><b>Meta Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Meta Attributes</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Meta Attributes</em>' containment reference.
	 * @see #setMetaAttributes(TransitionMetaAttributesContainer)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getTransition_MetaAttributes()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='meta-attributes' namespace='##targetNamespace'"
	 * @generated
	 */
	TransitionMetaAttributesContainer getMetaAttributes();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Transition#getMetaAttributes <em>Meta Attributes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Meta Attributes</em>' containment reference.
	 * @see #getMetaAttributes()
	 * @generated
	 */
	void setMetaAttributes(TransitionMetaAttributesContainer value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getTransition_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='name' namespace='##targetNamespace'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Transition#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Priority</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Priority</em>' attribute.
	 * @see #isSetPriority()
	 * @see #unsetPriority()
	 * @see #setPriority(long)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getTransition_Priority()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.UnsignedInt" required="true"
	 *        extendedMetaData="kind='attribute' name='priority' namespace='##targetNamespace'"
	 * @generated
	 */
	long getPriority();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Transition#getPriority <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Priority</em>' attribute.
	 * @see #isSetPriority()
	 * @see #unsetPriority()
	 * @see #getPriority()
	 * @generated
	 */
	void setPriority(long value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Transition#getPriority <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetPriority()
	 * @see #getPriority()
	 * @see #setPriority(long)
	 * @generated
	 */
	void unsetPriority();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.Transition#getPriority <em>Priority</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Priority</em>' attribute is set.
	 * @see #unsetPriority()
	 * @see #getPriority()
	 * @see #setPriority(long)
	 * @generated
	 */
	boolean isSetPriority();

} // Transition
