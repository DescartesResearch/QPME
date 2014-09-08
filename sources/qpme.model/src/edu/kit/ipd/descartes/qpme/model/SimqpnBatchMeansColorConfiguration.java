/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.kit.ipd.descartes.qpme.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simqpn Batch Means Color Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getBatchSize <em>Batch Size</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getBucketSize <em>Bucket Size</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getMaxBuckets <em>Max Buckets</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getMinBatches <em>Min Batches</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getNumBMeansCorlTested <em>Num BMeans Corl Tested</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getReqAbsPrc <em>Req Abs Prc</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getReqRelPrc <em>Req Rel Prc</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getSignLev <em>Sign Lev</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnBatchMeansColorConfiguration()
 * @model extendedMetaData="name='simqpn-batch-means-color-configuration' kind='empty'"
 * @generated
 */
public interface SimqpnBatchMeansColorConfiguration extends SimqpnMetaAttribute, ColorReferenceMetaAttribute {
	/**
	 * Returns the value of the '<em><b>Batch Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Batch Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Batch Size</em>' attribute.
	 * @see #isSetBatchSize()
	 * @see #unsetBatchSize()
	 * @see #setBatchSize(long)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnBatchMeansColorConfiguration_BatchSize()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.UnsignedInt" required="true"
	 *        extendedMetaData="kind='attribute' name='batchSize' namespace='##targetNamespace'"
	 * @generated
	 */
	long getBatchSize();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getBatchSize <em>Batch Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Batch Size</em>' attribute.
	 * @see #isSetBatchSize()
	 * @see #unsetBatchSize()
	 * @see #getBatchSize()
	 * @generated
	 */
	void setBatchSize(long value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getBatchSize <em>Batch Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetBatchSize()
	 * @see #getBatchSize()
	 * @see #setBatchSize(long)
	 * @generated
	 */
	void unsetBatchSize();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getBatchSize <em>Batch Size</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Batch Size</em>' attribute is set.
	 * @see #unsetBatchSize()
	 * @see #getBatchSize()
	 * @see #setBatchSize(long)
	 * @generated
	 */
	boolean isSetBatchSize();

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
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnBatchMeansColorConfiguration_BucketSize()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
	 *        extendedMetaData="kind='attribute' name='bucketSize' namespace='##targetNamespace'"
	 * @generated
	 */
	double getBucketSize();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getBucketSize <em>Bucket Size</em>}' attribute.
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
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getBucketSize <em>Bucket Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetBucketSize()
	 * @see #getBucketSize()
	 * @see #setBucketSize(double)
	 * @generated
	 */
	void unsetBucketSize();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getBucketSize <em>Bucket Size</em>}' attribute is set.
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
	 * Returns the value of the '<em><b>Max Buckets</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Buckets</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Buckets</em>' attribute.
	 * @see #isSetMaxBuckets()
	 * @see #unsetMaxBuckets()
	 * @see #setMaxBuckets(long)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnBatchMeansColorConfiguration_MaxBuckets()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.UnsignedInt" required="true"
	 *        extendedMetaData="kind='attribute' name='maxBuckets' namespace='##targetNamespace'"
	 * @generated
	 */
	long getMaxBuckets();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getMaxBuckets <em>Max Buckets</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Buckets</em>' attribute.
	 * @see #isSetMaxBuckets()
	 * @see #unsetMaxBuckets()
	 * @see #getMaxBuckets()
	 * @generated
	 */
	void setMaxBuckets(long value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getMaxBuckets <em>Max Buckets</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMaxBuckets()
	 * @see #getMaxBuckets()
	 * @see #setMaxBuckets(long)
	 * @generated
	 */
	void unsetMaxBuckets();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getMaxBuckets <em>Max Buckets</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Max Buckets</em>' attribute is set.
	 * @see #unsetMaxBuckets()
	 * @see #getMaxBuckets()
	 * @see #setMaxBuckets(long)
	 * @generated
	 */
	boolean isSetMaxBuckets();

	/**
	 * Returns the value of the '<em><b>Min Batches</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Min Batches</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Min Batches</em>' attribute.
	 * @see #isSetMinBatches()
	 * @see #unsetMinBatches()
	 * @see #setMinBatches(long)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnBatchMeansColorConfiguration_MinBatches()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.UnsignedInt" required="true"
	 *        extendedMetaData="kind='attribute' name='minBatches' namespace='##targetNamespace'"
	 * @generated
	 */
	long getMinBatches();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getMinBatches <em>Min Batches</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Batches</em>' attribute.
	 * @see #isSetMinBatches()
	 * @see #unsetMinBatches()
	 * @see #getMinBatches()
	 * @generated
	 */
	void setMinBatches(long value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getMinBatches <em>Min Batches</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMinBatches()
	 * @see #getMinBatches()
	 * @see #setMinBatches(long)
	 * @generated
	 */
	void unsetMinBatches();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getMinBatches <em>Min Batches</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Min Batches</em>' attribute is set.
	 * @see #unsetMinBatches()
	 * @see #getMinBatches()
	 * @see #setMinBatches(long)
	 * @generated
	 */
	boolean isSetMinBatches();

	/**
	 * Returns the value of the '<em><b>Num BMeans Corl Tested</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Num BMeans Corl Tested</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Num BMeans Corl Tested</em>' attribute.
	 * @see #isSetNumBMeansCorlTested()
	 * @see #unsetNumBMeansCorlTested()
	 * @see #setNumBMeansCorlTested(long)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnBatchMeansColorConfiguration_NumBMeansCorlTested()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.UnsignedInt" required="true"
	 *        extendedMetaData="kind='attribute' name='numBMeansCorlTested' namespace='##targetNamespace'"
	 * @generated
	 */
	long getNumBMeansCorlTested();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getNumBMeansCorlTested <em>Num BMeans Corl Tested</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Num BMeans Corl Tested</em>' attribute.
	 * @see #isSetNumBMeansCorlTested()
	 * @see #unsetNumBMeansCorlTested()
	 * @see #getNumBMeansCorlTested()
	 * @generated
	 */
	void setNumBMeansCorlTested(long value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getNumBMeansCorlTested <em>Num BMeans Corl Tested</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetNumBMeansCorlTested()
	 * @see #getNumBMeansCorlTested()
	 * @see #setNumBMeansCorlTested(long)
	 * @generated
	 */
	void unsetNumBMeansCorlTested();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getNumBMeansCorlTested <em>Num BMeans Corl Tested</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Num BMeans Corl Tested</em>' attribute is set.
	 * @see #unsetNumBMeansCorlTested()
	 * @see #getNumBMeansCorlTested()
	 * @see #setNumBMeansCorlTested(long)
	 * @generated
	 */
	boolean isSetNumBMeansCorlTested();

	/**
	 * Returns the value of the '<em><b>Req Abs Prc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Req Abs Prc</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Req Abs Prc</em>' attribute.
	 * @see #isSetReqAbsPrc()
	 * @see #unsetReqAbsPrc()
	 * @see #setReqAbsPrc(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnBatchMeansColorConfiguration_ReqAbsPrc()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
	 *        extendedMetaData="kind='attribute' name='reqAbsPrc' namespace='##targetNamespace'"
	 * @generated
	 */
	double getReqAbsPrc();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getReqAbsPrc <em>Req Abs Prc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Req Abs Prc</em>' attribute.
	 * @see #isSetReqAbsPrc()
	 * @see #unsetReqAbsPrc()
	 * @see #getReqAbsPrc()
	 * @generated
	 */
	void setReqAbsPrc(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getReqAbsPrc <em>Req Abs Prc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetReqAbsPrc()
	 * @see #getReqAbsPrc()
	 * @see #setReqAbsPrc(double)
	 * @generated
	 */
	void unsetReqAbsPrc();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getReqAbsPrc <em>Req Abs Prc</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Req Abs Prc</em>' attribute is set.
	 * @see #unsetReqAbsPrc()
	 * @see #getReqAbsPrc()
	 * @see #setReqAbsPrc(double)
	 * @generated
	 */
	boolean isSetReqAbsPrc();

	/**
	 * Returns the value of the '<em><b>Req Rel Prc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Req Rel Prc</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Req Rel Prc</em>' attribute.
	 * @see #isSetReqRelPrc()
	 * @see #unsetReqRelPrc()
	 * @see #setReqRelPrc(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnBatchMeansColorConfiguration_ReqRelPrc()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
	 *        extendedMetaData="kind='attribute' name='reqRelPrc' namespace='##targetNamespace'"
	 * @generated
	 */
	double getReqRelPrc();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getReqRelPrc <em>Req Rel Prc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Req Rel Prc</em>' attribute.
	 * @see #isSetReqRelPrc()
	 * @see #unsetReqRelPrc()
	 * @see #getReqRelPrc()
	 * @generated
	 */
	void setReqRelPrc(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getReqRelPrc <em>Req Rel Prc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetReqRelPrc()
	 * @see #getReqRelPrc()
	 * @see #setReqRelPrc(double)
	 * @generated
	 */
	void unsetReqRelPrc();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getReqRelPrc <em>Req Rel Prc</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Req Rel Prc</em>' attribute is set.
	 * @see #unsetReqRelPrc()
	 * @see #getReqRelPrc()
	 * @see #setReqRelPrc(double)
	 * @generated
	 */
	boolean isSetReqRelPrc();

	/**
	 * Returns the value of the '<em><b>Sign Lev</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sign Lev</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sign Lev</em>' attribute.
	 * @see #isSetSignLev()
	 * @see #unsetSignLev()
	 * @see #setSignLev(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnBatchMeansColorConfiguration_SignLev()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
	 *        extendedMetaData="kind='attribute' name='signLev' namespace='##targetNamespace'"
	 * @generated
	 */
	double getSignLev();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getSignLev <em>Sign Lev</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sign Lev</em>' attribute.
	 * @see #isSetSignLev()
	 * @see #unsetSignLev()
	 * @see #getSignLev()
	 * @generated
	 */
	void setSignLev(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getSignLev <em>Sign Lev</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetSignLev()
	 * @see #getSignLev()
	 * @see #setSignLev(double)
	 * @generated
	 */
	void unsetSignLev();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration#getSignLev <em>Sign Lev</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Sign Lev</em>' attribute is set.
	 * @see #unsetSignLev()
	 * @see #getSignLev()
	 * @see #setSignLev(double)
	 * @generated
	 */
	boolean isSetSignLev();

} // SimqpnBatchMeansColorConfiguration
