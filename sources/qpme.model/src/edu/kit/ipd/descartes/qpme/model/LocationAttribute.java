/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.kit.ipd.descartes.qpme.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Location Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.LocationAttribute#getLocationX <em>Location X</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.LocationAttribute#getLocationY <em>Location Y</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getLocationAttribute()
 * @model extendedMetaData="name='location-attribute' kind='empty'"
 * @generated
 */
public interface LocationAttribute extends PlaceMetaAttribute, TransitionMetaAttribute {
	/**
	 * Returns the value of the '<em><b>Location X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Location X</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Location X</em>' attribute.
	 * @see #isSetLocationX()
	 * @see #unsetLocationX()
	 * @see #setLocationX(int)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getLocationAttribute_LocationX()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 *        extendedMetaData="kind='attribute' name='location-x' namespace='##targetNamespace'"
	 * @generated
	 */
	int getLocationX();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.LocationAttribute#getLocationX <em>Location X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location X</em>' attribute.
	 * @see #isSetLocationX()
	 * @see #unsetLocationX()
	 * @see #getLocationX()
	 * @generated
	 */
	void setLocationX(int value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.LocationAttribute#getLocationX <em>Location X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetLocationX()
	 * @see #getLocationX()
	 * @see #setLocationX(int)
	 * @generated
	 */
	void unsetLocationX();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.LocationAttribute#getLocationX <em>Location X</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Location X</em>' attribute is set.
	 * @see #unsetLocationX()
	 * @see #getLocationX()
	 * @see #setLocationX(int)
	 * @generated
	 */
	boolean isSetLocationX();

	/**
	 * Returns the value of the '<em><b>Location Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Location Y</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Location Y</em>' attribute.
	 * @see #isSetLocationY()
	 * @see #unsetLocationY()
	 * @see #setLocationY(int)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getLocationAttribute_LocationY()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 *        extendedMetaData="kind='attribute' name='location-y' namespace='##targetNamespace'"
	 * @generated
	 */
	int getLocationY();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.LocationAttribute#getLocationY <em>Location Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location Y</em>' attribute.
	 * @see #isSetLocationY()
	 * @see #unsetLocationY()
	 * @see #getLocationY()
	 * @generated
	 */
	void setLocationY(int value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.LocationAttribute#getLocationY <em>Location Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetLocationY()
	 * @see #getLocationY()
	 * @see #setLocationY(int)
	 * @generated
	 */
	void unsetLocationY();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.LocationAttribute#getLocationY <em>Location Y</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Location Y</em>' attribute is set.
	 * @see #unsetLocationY()
	 * @see #getLocationY()
	 * @see #setLocationY(int)
	 * @generated
	 */
	boolean isSetLocationY();

} // LocationAttribute
