/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.kit.ipd.descartes.qpme.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mode</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Mode#getFiringWeight <em>Firing Weight</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Mode#getMeanFiringDelay <em>Mean Firing Delay</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Mode#getName <em>Name</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Mode#getRealColor <em>Real Color</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getMode()
 * @model extendedMetaData="name='mode' kind='empty'"
 * @generated
 */
public interface Mode extends IncidenceFunctionElement {
	/**
	 * Returns the value of the '<em><b>Firing Weight</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Firing Weight</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Firing Weight</em>' attribute.
	 * @see #isSetFiringWeight()
	 * @see #unsetFiringWeight()
	 * @see #setFiringWeight(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getMode_FiringWeight()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
	 *        extendedMetaData="kind='attribute' name='firing-weight' namespace='##targetNamespace'"
	 * @generated
	 */
	double getFiringWeight();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Mode#getFiringWeight <em>Firing Weight</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Firing Weight</em>' attribute.
	 * @see #isSetFiringWeight()
	 * @see #unsetFiringWeight()
	 * @see #getFiringWeight()
	 * @generated
	 */
	void setFiringWeight(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Mode#getFiringWeight <em>Firing Weight</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetFiringWeight()
	 * @see #getFiringWeight()
	 * @see #setFiringWeight(double)
	 * @generated
	 */
	void unsetFiringWeight();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.Mode#getFiringWeight <em>Firing Weight</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Firing Weight</em>' attribute is set.
	 * @see #unsetFiringWeight()
	 * @see #getFiringWeight()
	 * @see #setFiringWeight(double)
	 * @generated
	 */
	boolean isSetFiringWeight();

	/**
	 * Returns the value of the '<em><b>Mean Firing Delay</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mean Firing Delay</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mean Firing Delay</em>' attribute.
	 * @see #isSetMeanFiringDelay()
	 * @see #unsetMeanFiringDelay()
	 * @see #setMeanFiringDelay(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getMode_MeanFiringDelay()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
	 *        extendedMetaData="kind='attribute' name='mean-firing-delay' namespace='##targetNamespace'"
	 * @generated
	 */
	double getMeanFiringDelay();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Mode#getMeanFiringDelay <em>Mean Firing Delay</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mean Firing Delay</em>' attribute.
	 * @see #isSetMeanFiringDelay()
	 * @see #unsetMeanFiringDelay()
	 * @see #getMeanFiringDelay()
	 * @generated
	 */
	void setMeanFiringDelay(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Mode#getMeanFiringDelay <em>Mean Firing Delay</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMeanFiringDelay()
	 * @see #getMeanFiringDelay()
	 * @see #setMeanFiringDelay(double)
	 * @generated
	 */
	void unsetMeanFiringDelay();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.Mode#getMeanFiringDelay <em>Mean Firing Delay</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Mean Firing Delay</em>' attribute is set.
	 * @see #unsetMeanFiringDelay()
	 * @see #getMeanFiringDelay()
	 * @see #setMeanFiringDelay(double)
	 * @generated
	 */
	boolean isSetMeanFiringDelay();

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
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getMode_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='name' namespace='##targetNamespace'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Mode#getName <em>Name</em>}' attribute.
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
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getMode_RealColor()
	 * @model dataType="edu.kit.ipd.descartes.qpme.model.RgbValue" required="true"
	 *        extendedMetaData="kind='attribute' name='real-color' namespace='##targetNamespace'"
	 * @generated
	 */
	String getRealColor();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Mode#getRealColor <em>Real Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Real Color</em>' attribute.
	 * @see #getRealColor()
	 * @generated
	 */
	void setRealColor(String value);

} // Mode
