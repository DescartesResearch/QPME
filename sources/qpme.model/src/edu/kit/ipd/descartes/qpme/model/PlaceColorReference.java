/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.kit.ipd.descartes.qpme.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Place Color Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.PlaceColorReference#getInitialPopulation <em>Initial Population</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.PlaceColorReference#getMaximumCapacity <em>Maximum Capacity</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getPlaceColorReference()
 * @model abstract="true"
 *        extendedMetaData="name='place-color-reference' kind='elementOnly'"
 * @generated
 */
public interface PlaceColorReference extends ColorReference {
	/**
	 * Returns the value of the '<em><b>Initial Population</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Initial Population</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initial Population</em>' attribute.
	 * @see #isSetInitialPopulation()
	 * @see #unsetInitialPopulation()
	 * @see #setInitialPopulation(long)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getPlaceColorReference_InitialPopulation()
	 * @model default="0" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.UnsignedInt"
	 *        extendedMetaData="kind='attribute' name='initial-population' namespace='##targetNamespace'"
	 * @generated
	 */
	long getInitialPopulation();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.PlaceColorReference#getInitialPopulation <em>Initial Population</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initial Population</em>' attribute.
	 * @see #isSetInitialPopulation()
	 * @see #unsetInitialPopulation()
	 * @see #getInitialPopulation()
	 * @generated
	 */
	void setInitialPopulation(long value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.PlaceColorReference#getInitialPopulation <em>Initial Population</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetInitialPopulation()
	 * @see #getInitialPopulation()
	 * @see #setInitialPopulation(long)
	 * @generated
	 */
	void unsetInitialPopulation();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.PlaceColorReference#getInitialPopulation <em>Initial Population</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Initial Population</em>' attribute is set.
	 * @see #unsetInitialPopulation()
	 * @see #getInitialPopulation()
	 * @see #setInitialPopulation(long)
	 * @generated
	 */
	boolean isSetInitialPopulation();

	/**
	 * Returns the value of the '<em><b>Maximum Capacity</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Maximum Capacity</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Maximum Capacity</em>' attribute.
	 * @see #isSetMaximumCapacity()
	 * @see #unsetMaximumCapacity()
	 * @see #setMaximumCapacity(long)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getPlaceColorReference_MaximumCapacity()
	 * @model default="0" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.UnsignedInt"
	 *        extendedMetaData="kind='attribute' name='maximum-capacity' namespace='##targetNamespace'"
	 * @generated
	 */
	long getMaximumCapacity();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.PlaceColorReference#getMaximumCapacity <em>Maximum Capacity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Maximum Capacity</em>' attribute.
	 * @see #isSetMaximumCapacity()
	 * @see #unsetMaximumCapacity()
	 * @see #getMaximumCapacity()
	 * @generated
	 */
	void setMaximumCapacity(long value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.PlaceColorReference#getMaximumCapacity <em>Maximum Capacity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMaximumCapacity()
	 * @see #getMaximumCapacity()
	 * @see #setMaximumCapacity(long)
	 * @generated
	 */
	void unsetMaximumCapacity();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.PlaceColorReference#getMaximumCapacity <em>Maximum Capacity</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Maximum Capacity</em>' attribute is set.
	 * @see #unsetMaximumCapacity()
	 * @see #getMaximumCapacity()
	 * @see #setMaximumCapacity(long)
	 * @generated
	 */
	boolean isSetMaximumCapacity();

} // PlaceColorReference
