/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.kit.ipd.descartes.qpme.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simqpn Welch Color Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnWelchColorConfiguration#getMaxObsrv <em>Max Obsrv</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnWelchColorConfiguration#getMinObsrv <em>Min Obsrv</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnWelchColorConfiguration()
 * @model extendedMetaData="name='simqpn-welch-color-configuration' kind='empty'"
 * @generated
 */
public interface SimqpnWelchColorConfiguration extends SimqpnMetaAttribute, ColorReferenceMetaAttribute {
	/**
	 * Returns the value of the '<em><b>Max Obsrv</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Obsrv</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Obsrv</em>' attribute.
	 * @see #isSetMaxObsrv()
	 * @see #unsetMaxObsrv()
	 * @see #setMaxObsrv(long)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnWelchColorConfiguration_MaxObsrv()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.UnsignedInt" required="true"
	 *        extendedMetaData="kind='attribute' name='maxObsrv' namespace='##targetNamespace'"
	 * @generated
	 */
	long getMaxObsrv();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnWelchColorConfiguration#getMaxObsrv <em>Max Obsrv</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Obsrv</em>' attribute.
	 * @see #isSetMaxObsrv()
	 * @see #unsetMaxObsrv()
	 * @see #getMaxObsrv()
	 * @generated
	 */
	void setMaxObsrv(long value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnWelchColorConfiguration#getMaxObsrv <em>Max Obsrv</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMaxObsrv()
	 * @see #getMaxObsrv()
	 * @see #setMaxObsrv(long)
	 * @generated
	 */
	void unsetMaxObsrv();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnWelchColorConfiguration#getMaxObsrv <em>Max Obsrv</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Max Obsrv</em>' attribute is set.
	 * @see #unsetMaxObsrv()
	 * @see #getMaxObsrv()
	 * @see #setMaxObsrv(long)
	 * @generated
	 */
	boolean isSetMaxObsrv();

	/**
	 * Returns the value of the '<em><b>Min Obsrv</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Min Obsrv</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Min Obsrv</em>' attribute.
	 * @see #isSetMinObsrv()
	 * @see #unsetMinObsrv()
	 * @see #setMinObsrv(long)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnWelchColorConfiguration_MinObsrv()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.UnsignedInt" required="true"
	 *        extendedMetaData="kind='attribute' name='minObsrv' namespace='##targetNamespace'"
	 * @generated
	 */
	long getMinObsrv();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnWelchColorConfiguration#getMinObsrv <em>Min Obsrv</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Obsrv</em>' attribute.
	 * @see #isSetMinObsrv()
	 * @see #unsetMinObsrv()
	 * @see #getMinObsrv()
	 * @generated
	 */
	void setMinObsrv(long value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnWelchColorConfiguration#getMinObsrv <em>Min Obsrv</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMinObsrv()
	 * @see #getMinObsrv()
	 * @see #setMinObsrv(long)
	 * @generated
	 */
	void unsetMinObsrv();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnWelchColorConfiguration#getMinObsrv <em>Min Obsrv</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Min Obsrv</em>' attribute is set.
	 * @see #unsetMinObsrv()
	 * @see #getMinObsrv()
	 * @see #setMinObsrv(long)
	 * @generated
	 */
	boolean isSetMinObsrv();

} // SimqpnWelchColorConfiguration
