/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.kit.ipd.descartes.qpme.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simqpn Repl Del Color Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnReplDelColorConfiguration#getSignLevAvgST <em>Sign Lev Avg ST</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnReplDelColorConfiguration()
 * @model extendedMetaData="name='simqpn-replication-delection-color-configuration' kind='empty'"
 * @generated
 */
public interface SimqpnReplDelColorConfiguration extends SimqpnMetaAttribute, ColorReferenceMetaAttribute {
	/**
	 * Returns the value of the '<em><b>Sign Lev Avg ST</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sign Lev Avg ST</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sign Lev Avg ST</em>' attribute.
	 * @see #isSetSignLevAvgST()
	 * @see #unsetSignLevAvgST()
	 * @see #setSignLevAvgST(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnReplDelColorConfiguration_SignLevAvgST()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
	 *        extendedMetaData="kind='attribute' name='signLevAvgST' namespace='##targetNamespace'"
	 * @generated
	 */
	double getSignLevAvgST();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnReplDelColorConfiguration#getSignLevAvgST <em>Sign Lev Avg ST</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sign Lev Avg ST</em>' attribute.
	 * @see #isSetSignLevAvgST()
	 * @see #unsetSignLevAvgST()
	 * @see #getSignLevAvgST()
	 * @generated
	 */
	void setSignLevAvgST(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnReplDelColorConfiguration#getSignLevAvgST <em>Sign Lev Avg ST</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetSignLevAvgST()
	 * @see #getSignLevAvgST()
	 * @see #setSignLevAvgST(double)
	 * @generated
	 */
	void unsetSignLevAvgST();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnReplDelColorConfiguration#getSignLevAvgST <em>Sign Lev Avg ST</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Sign Lev Avg ST</em>' attribute is set.
	 * @see #unsetSignLevAvgST()
	 * @see #getSignLevAvgST()
	 * @see #setSignLevAvgST(double)
	 * @generated
	 */
	boolean isSetSignLevAvgST();

} // SimqpnReplDelColorConfiguration
