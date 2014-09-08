/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.kit.ipd.descartes.qpme.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Subnet Color Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SubnetColorReference#getDirection <em>Direction</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSubnetColorReference()
 * @model extendedMetaData="name='subnet-color-reference' kind='elementOnly'"
 * @generated
 */
public interface SubnetColorReference extends PlaceColorReference {
	/**
	 * Returns the value of the '<em><b>Direction</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.kit.ipd.descartes.qpme.model.FlowDirection}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Direction</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Direction</em>' attribute.
	 * @see edu.kit.ipd.descartes.qpme.model.FlowDirection
	 * @see #isSetDirection()
	 * @see #unsetDirection()
	 * @see #setDirection(FlowDirection)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSubnetColorReference_Direction()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='attribute' name='direction' namespace='##targetNamespace'"
	 * @generated
	 */
	FlowDirection getDirection();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SubnetColorReference#getDirection <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Direction</em>' attribute.
	 * @see edu.kit.ipd.descartes.qpme.model.FlowDirection
	 * @see #isSetDirection()
	 * @see #unsetDirection()
	 * @see #getDirection()
	 * @generated
	 */
	void setDirection(FlowDirection value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SubnetColorReference#getDirection <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDirection()
	 * @see #getDirection()
	 * @see #setDirection(FlowDirection)
	 * @generated
	 */
	void unsetDirection();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SubnetColorReference#getDirection <em>Direction</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Direction</em>' attribute is set.
	 * @see #unsetDirection()
	 * @see #getDirection()
	 * @see #setDirection(FlowDirection)
	 * @generated
	 */
	boolean isSetDirection();

} // SubnetColorReference
