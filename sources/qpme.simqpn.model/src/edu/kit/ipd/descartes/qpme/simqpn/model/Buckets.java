/**
 */
package edu.kit.ipd.descartes.qpme.simqpn.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Buckets</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.Buckets#getBucket <em>Bucket</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getBuckets()
 * @model extendedMetaData="name='buckets' kind='elementOnly'"
 * @generated
 */
public interface Buckets extends EObject {
	/**
	 * Returns the value of the '<em><b>Bucket</b></em>' containment reference list.
	 * The list contents are of type {@link edu.kit.ipd.descartes.qpme.simqpn.model.Bucket}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bucket</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bucket</em>' containment reference list.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getBuckets_Bucket()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='bucket' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Bucket> getBucket();

} // Buckets
