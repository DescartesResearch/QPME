/**
 */
package edu.kit.ipd.descartes.qpme.simqpn.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Percentiles</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.Percentiles#getPercentile <em>Percentile</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getPercentiles()
 * @model extendedMetaData="name='percentiles' kind='elementOnly'"
 * @generated
 */
public interface Percentiles extends EObject {
	/**
	 * Returns the value of the '<em><b>Percentile</b></em>' containment reference list.
	 * The list contents are of type {@link edu.kit.ipd.descartes.qpme.simqpn.model.Percentile}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Percentile</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Percentile</em>' containment reference list.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getPercentiles_Percentile()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='percentile' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Percentile> getPercentile();

} // Percentiles
