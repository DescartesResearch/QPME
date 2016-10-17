/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.kit.ipd.descartes.qpme.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Queueing Place</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.QueueingPlace#getQueue <em>Queue</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueueingPlace()
 * @model extendedMetaData="name='queueing-place' kind='elementOnly'"
 * @generated
 */
public interface QueueingPlace extends Place {
	/**
	 * Returns the value of the '<em><b>Queue</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Queue</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Queue</em>' reference.
	 * @see #setQueue(Queue)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueueingPlace_Queue()
	 * @model resolveProxies="false" required="true"
	 *        extendedMetaData="kind='attribute' name='queue-ref' namespace='##targetNamespace'"
	 * @generated
	 */
	Queue getQueue();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingPlace#getQueue <em>Queue</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Queue</em>' reference.
	 * @see #getQueue()
	 * @generated
	 */
	void setQueue(Queue value);

} // QueueingPlace
