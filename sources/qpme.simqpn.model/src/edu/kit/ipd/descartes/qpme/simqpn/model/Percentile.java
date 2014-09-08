/**
 */
package edu.kit.ipd.descartes.qpme.simqpn.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Percentile</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.Percentile#getValue <em>Value</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.Percentile#getFor <em>For</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getPercentile()
 * @model extendedMetaData="name='percentile' kind='simple'"
 * @generated
 */
public interface Percentile extends EObject {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #isSetValue()
	 * @see #unsetValue()
	 * @see #setValue(double)
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getPercentile_Value()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
	 *        extendedMetaData="name=':0' kind='simple'"
	 * @generated
	 */
	double getValue();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Percentile#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #isSetValue()
	 * @see #unsetValue()
	 * @see #getValue()
	 * @generated
	 */
	void setValue(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Percentile#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetValue()
	 * @see #getValue()
	 * @see #setValue(double)
	 * @generated
	 */
	void unsetValue();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Percentile#getValue <em>Value</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Value</em>' attribute is set.
	 * @see #unsetValue()
	 * @see #getValue()
	 * @see #setValue(double)
	 * @generated
	 */
	boolean isSetValue();

	/**
	 * Returns the value of the '<em><b>For</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>For</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>For</em>' attribute.
	 * @see #isSetFor()
	 * @see #unsetFor()
	 * @see #setFor(double)
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getPercentile_For()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
	 *        extendedMetaData="kind='attribute' name='for' namespace='##targetNamespace'"
	 * @generated
	 */
	double getFor();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Percentile#getFor <em>For</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>For</em>' attribute.
	 * @see #isSetFor()
	 * @see #unsetFor()
	 * @see #getFor()
	 * @generated
	 */
	void setFor(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Percentile#getFor <em>For</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetFor()
	 * @see #getFor()
	 * @see #setFor(double)
	 * @generated
	 */
	void unsetFor();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Percentile#getFor <em>For</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>For</em>' attribute is set.
	 * @see #unsetFor()
	 * @see #getFor()
	 * @see #setFor(double)
	 * @generated
	 */
	boolean isSetFor();

} // Percentile
