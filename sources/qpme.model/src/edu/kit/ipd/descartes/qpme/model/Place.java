/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.kit.ipd.descartes.qpme.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Place</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Place#getColorReferences <em>Color References</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Place#getMetaAttributes <em>Meta Attributes</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Place#getDepartureDiscipline <em>Departure Discipline</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Place#isLocked <em>Locked</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Place#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getPlace()
 * @model abstract="true"
 *        extendedMetaData="name='place' kind='elementOnly'"
 * @generated
 */
public interface Place extends PlaceTransitionElement {
	/**
	 * Returns the value of the '<em><b>Color References</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Color References</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Color References</em>' containment reference.
	 * @see #setColorReferences(ColorReferencesContainer)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getPlace_ColorReferences()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='color-refs' namespace='##targetNamespace'"
	 * @generated
	 */
	ColorReferencesContainer getColorReferences();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Place#getColorReferences <em>Color References</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Color References</em>' containment reference.
	 * @see #getColorReferences()
	 * @generated
	 */
	void setColorReferences(ColorReferencesContainer value);

	/**
	 * Returns the value of the '<em><b>Meta Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Meta Attributes</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Meta Attributes</em>' containment reference.
	 * @see #setMetaAttributes(PlaceMetaAttributesContainer)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getPlace_MetaAttributes()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='meta-attributes' namespace='##targetNamespace'"
	 * @generated
	 */
	PlaceMetaAttributesContainer getMetaAttributes();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Place#getMetaAttributes <em>Meta Attributes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Meta Attributes</em>' containment reference.
	 * @see #getMetaAttributes()
	 * @generated
	 */
	void setMetaAttributes(PlaceMetaAttributesContainer value);

	/**
	 * Returns the value of the '<em><b>Departure Discipline</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.kit.ipd.descartes.qpme.model.DepartureDiscipline}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Departure Discipline</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Departure Discipline</em>' attribute.
	 * @see edu.kit.ipd.descartes.qpme.model.DepartureDiscipline
	 * @see #isSetDepartureDiscipline()
	 * @see #unsetDepartureDiscipline()
	 * @see #setDepartureDiscipline(DepartureDiscipline)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getPlace_DepartureDiscipline()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='attribute' name='departure-discipline' namespace='##targetNamespace'"
	 * @generated
	 */
	DepartureDiscipline getDepartureDiscipline();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Place#getDepartureDiscipline <em>Departure Discipline</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Departure Discipline</em>' attribute.
	 * @see edu.kit.ipd.descartes.qpme.model.DepartureDiscipline
	 * @see #isSetDepartureDiscipline()
	 * @see #unsetDepartureDiscipline()
	 * @see #getDepartureDiscipline()
	 * @generated
	 */
	void setDepartureDiscipline(DepartureDiscipline value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Place#getDepartureDiscipline <em>Departure Discipline</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDepartureDiscipline()
	 * @see #getDepartureDiscipline()
	 * @see #setDepartureDiscipline(DepartureDiscipline)
	 * @generated
	 */
	void unsetDepartureDiscipline();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.Place#getDepartureDiscipline <em>Departure Discipline</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Departure Discipline</em>' attribute is set.
	 * @see #unsetDepartureDiscipline()
	 * @see #getDepartureDiscipline()
	 * @see #setDepartureDiscipline(DepartureDiscipline)
	 * @generated
	 */
	boolean isSetDepartureDiscipline();

	/**
	 * Returns the value of the '<em><b>Locked</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Locked</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Locked</em>' attribute.
	 * @see #isSetLocked()
	 * @see #unsetLocked()
	 * @see #setLocked(boolean)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getPlace_Locked()
	 * @model default="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='locked' namespace='##targetNamespace'"
	 * @generated
	 */
	boolean isLocked();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Place#isLocked <em>Locked</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Locked</em>' attribute.
	 * @see #isSetLocked()
	 * @see #unsetLocked()
	 * @see #isLocked()
	 * @generated
	 */
	void setLocked(boolean value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Place#isLocked <em>Locked</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetLocked()
	 * @see #isLocked()
	 * @see #setLocked(boolean)
	 * @generated
	 */
	void unsetLocked();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.Place#isLocked <em>Locked</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Locked</em>' attribute is set.
	 * @see #unsetLocked()
	 * @see #isLocked()
	 * @see #setLocked(boolean)
	 * @generated
	 */
	boolean isSetLocked();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getPlace_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='name' namespace='##targetNamespace'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Place#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // Place
