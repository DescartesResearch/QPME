/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.kit.ipd.descartes.qpme.model;

import java.math.BigInteger;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simqpn Place Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnPlaceConfiguration#getStatsLevel <em>Stats Level</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnPlaceConfiguration()
 * @model extendedMetaData="name='simqpn-place-configuration' kind='empty'"
 * @generated
 */
public interface SimqpnPlaceConfiguration extends SimqpnMetaAttribute, PlaceMetaAttribute, ProbeMetaAttribute {
	/**
	 * Returns the value of the '<em><b>Stats Level</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Stats Level</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Stats Level</em>' attribute.
	 * @see #setStatsLevel(BigInteger)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnPlaceConfiguration_StatsLevel()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Integer" required="true"
	 *        extendedMetaData="kind='attribute' name='statsLevel' namespace='##targetNamespace'"
	 * @generated
	 */
	BigInteger getStatsLevel();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnPlaceConfiguration#getStatsLevel <em>Stats Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Stats Level</em>' attribute.
	 * @see #getStatsLevel()
	 * @generated
	 */
	void setStatsLevel(BigInteger value);

} // SimqpnPlaceConfiguration
