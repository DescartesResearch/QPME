/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.kit.ipd.descartes.qpme.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Incidence Function Connection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnection#getCount <em>Count</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnection#getSource <em>Source</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnection#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getIncidenceFunctionConnection()
 * @model extendedMetaData="name='incidence-function-connection' kind='empty'"
 * @generated
 */
public interface IncidenceFunctionConnection extends IdentifiableElement {
	/**
	 * Returns the value of the '<em><b>Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Count</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Count</em>' attribute.
	 * @see #isSetCount()
	 * @see #unsetCount()
	 * @see #setCount(long)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getIncidenceFunctionConnection_Count()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.UnsignedInt" required="true"
	 *        extendedMetaData="kind='attribute' name='count' namespace='##targetNamespace'"
	 * @generated
	 */
	long getCount();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnection#getCount <em>Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Count</em>' attribute.
	 * @see #isSetCount()
	 * @see #unsetCount()
	 * @see #getCount()
	 * @generated
	 */
	void setCount(long value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnection#getCount <em>Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetCount()
	 * @see #getCount()
	 * @see #setCount(long)
	 * @generated
	 */
	void unsetCount();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnection#getCount <em>Count</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Count</em>' attribute is set.
	 * @see #unsetCount()
	 * @see #getCount()
	 * @see #setCount(long)
	 * @generated
	 */
	boolean isSetCount();

	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link edu.kit.ipd.descartes.qpme.model.IncidenceFunctionElement#getOutgoingConnections <em>Outgoing Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(IncidenceFunctionElement)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getIncidenceFunctionConnection_Source()
	 * @see edu.kit.ipd.descartes.qpme.model.IncidenceFunctionElement#getOutgoingConnections
	 * @model opposite="outgoingConnections" resolveProxies="false" required="true"
	 *        extendedMetaData="kind='attribute' name='source-id' namespace='##targetNamespace'"
	 * @generated
	 */
	IncidenceFunctionElement getSource();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnection#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(IncidenceFunctionElement value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link edu.kit.ipd.descartes.qpme.model.IncidenceFunctionElement#getIncomingConnections <em>Incoming Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(IncidenceFunctionElement)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getIncidenceFunctionConnection_Target()
	 * @see edu.kit.ipd.descartes.qpme.model.IncidenceFunctionElement#getIncomingConnections
	 * @model opposite="incomingConnections" resolveProxies="false" required="true"
	 *        extendedMetaData="kind='attribute' name='target-id' namespace='##targetNamespace'"
	 * @generated
	 */
	IncidenceFunctionElement getTarget();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnection#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(IncidenceFunctionElement value);

} // IncidenceFunctionConnection
