/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.kit.ipd.descartes.qpme.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simqpn Meta Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnMetaAttribute#getConfigurationName <em>Configuration Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnMetaAttribute()
 * @model abstract="true"
 *        extendedMetaData="name='simqpn-meta-attribute' kind='empty'"
 * @generated
 */
public interface SimqpnMetaAttribute extends IdentifiableElement {
	/**
	 * Returns the value of the '<em><b>Configuration Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Configuration Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Configuration Name</em>' attribute.
	 * @see #setConfigurationName(String)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnMetaAttribute_ConfigurationName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='configuration-name' namespace='##targetNamespace'"
	 * @generated
	 */
	String getConfigurationName();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnMetaAttribute#getConfigurationName <em>Configuration Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Configuration Name</em>' attribute.
	 * @see #getConfigurationName()
	 * @generated
	 */
	void setConfigurationName(String value);

} // SimqpnMetaAttribute
