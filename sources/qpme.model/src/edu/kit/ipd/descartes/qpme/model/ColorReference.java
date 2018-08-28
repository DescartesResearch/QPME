/**
 */
package edu.kit.ipd.descartes.qpme.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Color Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.ColorReference#getMetaAttributes <em>Meta Attributes</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.ColorReference#getColor <em>Color</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getColorReference()
 * @model abstract="true"
 *        extendedMetaData="name='color-reference' kind='elementOnly'"
 * @generated
 */
public interface ColorReference extends IncidenceFunctionElement {
	/**
	 * Returns the value of the '<em><b>Meta Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Meta Attributes</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Meta Attributes</em>' containment reference.
	 * @see #setMetaAttributes(ColorReferencesMetaAttributesContainer)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getColorReference_MetaAttributes()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='meta-attributes' namespace='##targetNamespace'"
	 * @generated
	 */
	ColorReferencesMetaAttributesContainer getMetaAttributes();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.ColorReference#getMetaAttributes <em>Meta Attributes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Meta Attributes</em>' containment reference.
	 * @see #getMetaAttributes()
	 * @generated
	 */
	void setMetaAttributes(ColorReferencesMetaAttributesContainer value);

	/**
	 * Returns the value of the '<em><b>Color</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Color</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Color</em>' reference.
	 * @see #setColor(Color)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getColorReference_Color()
	 * @model resolveProxies="false" required="true"
	 *        extendedMetaData="kind='attribute' name='color-id' namespace='##targetNamespace'"
	 * @generated
	 */
	Color getColor();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.ColorReference#getColor <em>Color</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Color</em>' reference.
	 * @see #getColor()
	 * @generated
	 */
	void setColor(Color value);

} // ColorReference
