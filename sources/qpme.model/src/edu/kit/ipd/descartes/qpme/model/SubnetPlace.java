/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.kit.ipd.descartes.qpme.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Subnet Place</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SubnetPlace#getSubnet <em>Subnet</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSubnetPlace()
 * @model extendedMetaData="name='subnet-place' kind='elementOnly'"
 * @generated
 */
public interface SubnetPlace extends Place {
	/**
	 * Returns the value of the '<em><b>Subnet</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subnet</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subnet</em>' containment reference.
	 * @see #setSubnet(Subnet)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSubnetPlace_Subnet()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='subnet' namespace='##targetNamespace'"
	 * @generated
	 */
	Subnet getSubnet();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SubnetPlace#getSubnet <em>Subnet</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subnet</em>' containment reference.
	 * @see #getSubnet()
	 * @generated
	 */
	void setSubnet(Subnet value);

} // SubnetPlace
