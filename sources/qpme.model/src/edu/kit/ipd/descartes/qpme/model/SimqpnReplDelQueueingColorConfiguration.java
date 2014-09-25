/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.kit.ipd.descartes.qpme.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simqpn Repl Del Queueing Color Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnReplDelQueueingColorConfiguration#getQueueSignLevAvgST <em>Queue Sign Lev Avg ST</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnReplDelQueueingColorConfiguration()
 * @model extendedMetaData="name='simqpn-replication-delection-queueing-color-configuration' kind='empty'"
 * @generated
 */
public interface SimqpnReplDelQueueingColorConfiguration extends SimqpnReplDelColorConfiguration {
	/**
	 * Returns the value of the '<em><b>Queue Sign Lev Avg ST</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Queue Sign Lev Avg ST</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Queue Sign Lev Avg ST</em>' attribute.
	 * @see #isSetQueueSignLevAvgST()
	 * @see #unsetQueueSignLevAvgST()
	 * @see #setQueueSignLevAvgST(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnReplDelQueueingColorConfiguration_QueueSignLevAvgST()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
	 *        extendedMetaData="kind='attribute' name='queueSignLevAvgST' namespace='##targetNamespace'"
	 * @generated
	 */
	double getQueueSignLevAvgST();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnReplDelQueueingColorConfiguration#getQueueSignLevAvgST <em>Queue Sign Lev Avg ST</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Queue Sign Lev Avg ST</em>' attribute.
	 * @see #isSetQueueSignLevAvgST()
	 * @see #unsetQueueSignLevAvgST()
	 * @see #getQueueSignLevAvgST()
	 * @generated
	 */
	void setQueueSignLevAvgST(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnReplDelQueueingColorConfiguration#getQueueSignLevAvgST <em>Queue Sign Lev Avg ST</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetQueueSignLevAvgST()
	 * @see #getQueueSignLevAvgST()
	 * @see #setQueueSignLevAvgST(double)
	 * @generated
	 */
	void unsetQueueSignLevAvgST();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnReplDelQueueingColorConfiguration#getQueueSignLevAvgST <em>Queue Sign Lev Avg ST</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Queue Sign Lev Avg ST</em>' attribute is set.
	 * @see #unsetQueueSignLevAvgST()
	 * @see #getQueueSignLevAvgST()
	 * @see #setQueueSignLevAvgST(double)
	 * @generated
	 */
	boolean isSetQueueSignLevAvgST();

} // SimqpnReplDelQueueingColorConfiguration
