/**
 */
package edu.kit.ipd.descartes.qpme.simqpn.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Observed Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement#getMetric <em>Metric</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement#getColor <em>Color</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement#getId <em>Id</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement#getName <em>Name</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement#getStatsLevel <em>Stats Level</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getObservedElement()
 * @model extendedMetaData="name='observed-element' kind='elementOnly'"
 * @generated
 */
public interface ObservedElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Metric</b></em>' containment reference list.
	 * The list contents are of type {@link edu.kit.ipd.descartes.qpme.simqpn.model.Metric}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metric</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metric</em>' containment reference list.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getObservedElement_Metric()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='metric' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Metric> getMetric();

	/**
	 * Returns the value of the '<em><b>Color</b></em>' containment reference list.
	 * The list contents are of type {@link edu.kit.ipd.descartes.qpme.simqpn.model.Color}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Color</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Color</em>' containment reference list.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getObservedElement_Color()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='color' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Color> getColor();

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getObservedElement_Id()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='id' namespace='##targetNamespace'"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

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
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getObservedElement_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='name' namespace='##targetNamespace'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Stats Level</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Stats Level</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Stats Level</em>' attribute.
	 * @see #isSetStatsLevel()
	 * @see #unsetStatsLevel()
	 * @see #setStatsLevel(int)
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getObservedElement_StatsLevel()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 *        extendedMetaData="kind='attribute' name='stats-level' namespace='##targetNamespace'"
	 * @generated
	 */
	int getStatsLevel();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement#getStatsLevel <em>Stats Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Stats Level</em>' attribute.
	 * @see #isSetStatsLevel()
	 * @see #unsetStatsLevel()
	 * @see #getStatsLevel()
	 * @generated
	 */
	void setStatsLevel(int value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement#getStatsLevel <em>Stats Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetStatsLevel()
	 * @see #getStatsLevel()
	 * @see #setStatsLevel(int)
	 * @generated
	 */
	void unsetStatsLevel();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement#getStatsLevel <em>Stats Level</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Stats Level</em>' attribute is set.
	 * @see #unsetStatsLevel()
	 * @see #getStatsLevel()
	 * @see #setStatsLevel(int)
	 * @generated
	 */
	boolean isSetStatsLevel();

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getObservedElement_Type()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='type' namespace='##targetNamespace'"
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

} // ObservedElement
