/**
 */
package edu.kit.ipd.descartes.qpme.simqpn.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Histogram</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getMean <em>Mean</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getPercentiles <em>Percentiles</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getBuckets <em>Buckets</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getBucketSize <em>Bucket Size</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getNumBuckets <em>Num Buckets</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getHistogram()
 * @model extendedMetaData="name='histogram' kind='elementOnly'"
 * @generated
 */
public interface Histogram extends EObject {
	/**
	 * Returns the value of the '<em><b>Mean</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mean</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mean</em>' attribute.
	 * @see #isSetMean()
	 * @see #unsetMean()
	 * @see #setMean(double)
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getHistogram_Mean()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
	 *        extendedMetaData="kind='element' name='mean' namespace='##targetNamespace'"
	 * @generated
	 */
	double getMean();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getMean <em>Mean</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mean</em>' attribute.
	 * @see #isSetMean()
	 * @see #unsetMean()
	 * @see #getMean()
	 * @generated
	 */
	void setMean(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getMean <em>Mean</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMean()
	 * @see #getMean()
	 * @see #setMean(double)
	 * @generated
	 */
	void unsetMean();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getMean <em>Mean</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Mean</em>' attribute is set.
	 * @see #unsetMean()
	 * @see #getMean()
	 * @see #setMean(double)
	 * @generated
	 */
	boolean isSetMean();

	/**
	 * Returns the value of the '<em><b>Percentiles</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Percentiles</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Percentiles</em>' containment reference.
	 * @see #setPercentiles(Percentiles)
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getHistogram_Percentiles()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='percentiles' namespace='##targetNamespace'"
	 * @generated
	 */
	Percentiles getPercentiles();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getPercentiles <em>Percentiles</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Percentiles</em>' containment reference.
	 * @see #getPercentiles()
	 * @generated
	 */
	void setPercentiles(Percentiles value);

	/**
	 * Returns the value of the '<em><b>Buckets</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Buckets</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Buckets</em>' containment reference.
	 * @see #setBuckets(Buckets)
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getHistogram_Buckets()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='buckets' namespace='##targetNamespace'"
	 * @generated
	 */
	Buckets getBuckets();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getBuckets <em>Buckets</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Buckets</em>' containment reference.
	 * @see #getBuckets()
	 * @generated
	 */
	void setBuckets(Buckets value);

	/**
	 * Returns the value of the '<em><b>Bucket Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bucket Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bucket Size</em>' attribute.
	 * @see #isSetBucketSize()
	 * @see #unsetBucketSize()
	 * @see #setBucketSize(double)
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getHistogram_BucketSize()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
	 *        extendedMetaData="kind='attribute' name='bucket-size' namespace='##targetNamespace'"
	 * @generated
	 */
	double getBucketSize();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getBucketSize <em>Bucket Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bucket Size</em>' attribute.
	 * @see #isSetBucketSize()
	 * @see #unsetBucketSize()
	 * @see #getBucketSize()
	 * @generated
	 */
	void setBucketSize(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getBucketSize <em>Bucket Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetBucketSize()
	 * @see #getBucketSize()
	 * @see #setBucketSize(double)
	 * @generated
	 */
	void unsetBucketSize();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getBucketSize <em>Bucket Size</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Bucket Size</em>' attribute is set.
	 * @see #unsetBucketSize()
	 * @see #getBucketSize()
	 * @see #setBucketSize(double)
	 * @generated
	 */
	boolean isSetBucketSize();

	/**
	 * Returns the value of the '<em><b>Num Buckets</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Num Buckets</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Num Buckets</em>' attribute.
	 * @see #isSetNumBuckets()
	 * @see #unsetNumBuckets()
	 * @see #setNumBuckets(long)
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getHistogram_NumBuckets()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Long"
	 *        extendedMetaData="kind='attribute' name='num-buckets' namespace='##targetNamespace'"
	 * @generated
	 */
	long getNumBuckets();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getNumBuckets <em>Num Buckets</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Num Buckets</em>' attribute.
	 * @see #isSetNumBuckets()
	 * @see #unsetNumBuckets()
	 * @see #getNumBuckets()
	 * @generated
	 */
	void setNumBuckets(long value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getNumBuckets <em>Num Buckets</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetNumBuckets()
	 * @see #getNumBuckets()
	 * @see #setNumBuckets(long)
	 * @generated
	 */
	void unsetNumBuckets();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getNumBuckets <em>Num Buckets</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Num Buckets</em>' attribute is set.
	 * @see #unsetNumBuckets()
	 * @see #getNumBuckets()
	 * @see #setNumBuckets(long)
	 * @generated
	 */
	boolean isSetNumBuckets();

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
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getHistogram_Type()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='type' namespace='##targetNamespace'"
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

} // Histogram
