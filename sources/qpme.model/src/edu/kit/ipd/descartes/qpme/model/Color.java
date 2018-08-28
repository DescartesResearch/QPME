/**
 */
package edu.kit.ipd.descartes.qpme.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Color</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Color#getDescription <em>Description</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Color#getName <em>Name</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Color#getRealColor <em>Real Color</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getColor()
 * @model extendedMetaData="name='color' kind='empty'"
 * @generated
 */
public interface Color extends IdentifiableElement {
	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getColor_Description()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='description' namespace='##targetNamespace'"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Color#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

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
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getColor_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='name' namespace='##targetNamespace'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Color#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Real Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Real Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Real Color</em>' attribute.
	 * @see #setRealColor(String)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getColor_RealColor()
	 * @model dataType="edu.kit.ipd.descartes.qpme.model.RgbValue" required="true"
	 *        extendedMetaData="kind='attribute' name='real-color' namespace='##targetNamespace'"
	 * @generated
	 */
	String getRealColor();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Color#getRealColor <em>Real Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Real Color</em>' attribute.
	 * @see #getRealColor()
	 * @generated
	 */
	void setRealColor(String value);

} // Color
