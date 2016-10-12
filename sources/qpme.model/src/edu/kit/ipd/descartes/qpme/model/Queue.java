/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.kit.ipd.descartes.qpme.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Queue</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Queue#getName <em>Name</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Queue#getNumberOfServers <em>Number Of Servers</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Queue#getStrategy <em>Strategy</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueue()
 * @model extendedMetaData="name='queue' kind='empty'"
 * @generated
 */
public interface Queue extends IdentifiableElement {
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
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueue_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='name' namespace='##targetNamespace'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Queue#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Number Of Servers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Number Of Servers</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number Of Servers</em>' attribute.
	 * @see #isSetNumberOfServers()
	 * @see #unsetNumberOfServers()
	 * @see #setNumberOfServers(int)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueue_NumberOfServers()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 *        extendedMetaData="kind='attribute' name='number-of-servers' namespace='##targetNamespace'"
	 * @generated
	 */
	int getNumberOfServers();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Queue#getNumberOfServers <em>Number Of Servers</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number Of Servers</em>' attribute.
	 * @see #isSetNumberOfServers()
	 * @see #unsetNumberOfServers()
	 * @see #getNumberOfServers()
	 * @generated
	 */
	void setNumberOfServers(int value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Queue#getNumberOfServers <em>Number Of Servers</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetNumberOfServers()
	 * @see #getNumberOfServers()
	 * @see #setNumberOfServers(int)
	 * @generated
	 */
	void unsetNumberOfServers();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.Queue#getNumberOfServers <em>Number Of Servers</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Number Of Servers</em>' attribute is set.
	 * @see #unsetNumberOfServers()
	 * @see #getNumberOfServers()
	 * @see #setNumberOfServers(int)
	 * @generated
	 */
	boolean isSetNumberOfServers();

	/**
	 * Returns the value of the '<em><b>Strategy</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.kit.ipd.descartes.qpme.model.QueueingStrategy}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Strategy</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Strategy</em>' attribute.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingStrategy
	 * @see #isSetStrategy()
	 * @see #unsetStrategy()
	 * @see #setStrategy(QueueingStrategy)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueue_Strategy()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='attribute' name='strategy' namespace='##targetNamespace'"
	 * @generated
	 */
	QueueingStrategy getStrategy();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Queue#getStrategy <em>Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Strategy</em>' attribute.
	 * @see edu.kit.ipd.descartes.qpme.model.QueueingStrategy
	 * @see #isSetStrategy()
	 * @see #unsetStrategy()
	 * @see #getStrategy()
	 * @generated
	 */
	void setStrategy(QueueingStrategy value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Queue#getStrategy <em>Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetStrategy()
	 * @see #getStrategy()
	 * @see #setStrategy(QueueingStrategy)
	 * @generated
	 */
	void unsetStrategy();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.Queue#getStrategy <em>Strategy</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Strategy</em>' attribute is set.
	 * @see #unsetStrategy()
	 * @see #getStrategy()
	 * @see #setStrategy(QueueingStrategy)
	 * @generated
	 */
	boolean isSetStrategy();

} // Queue
