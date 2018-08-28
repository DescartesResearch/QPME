/**
 */
package edu.kit.ipd.descartes.qpme.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simqpn Batch Means Queueing Color Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueBatchSize <em>Queue Batch Size</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueBucketSize <em>Queue Bucket Size</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueMaxBuckets <em>Queue Max Buckets</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueMinBatches <em>Queue Min Batches</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueNumBMeansCorlTested <em>Queue Num BMeans Corl Tested</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueReqAbsPrc <em>Queue Req Abs Prc</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueReqRelPrc <em>Queue Req Rel Prc</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueSignLev <em>Queue Sign Lev</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnBatchMeansQueueingColorConfiguration()
 * @model extendedMetaData="name='simqpn-batch-means-queueing-color-configuration' kind='empty'"
 * @generated
 */
public interface SimqpnBatchMeansQueueingColorConfiguration extends SimqpnBatchMeansColorConfiguration {
	/**
	 * Returns the value of the '<em><b>Queue Batch Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Queue Batch Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Queue Batch Size</em>' attribute.
	 * @see #isSetQueueBatchSize()
	 * @see #unsetQueueBatchSize()
	 * @see #setQueueBatchSize(long)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnBatchMeansQueueingColorConfiguration_QueueBatchSize()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.UnsignedInt" required="true"
	 *        extendedMetaData="kind='attribute' name='queueBatchSize' namespace='##targetNamespace'"
	 * @generated
	 */
	long getQueueBatchSize();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueBatchSize <em>Queue Batch Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Queue Batch Size</em>' attribute.
	 * @see #isSetQueueBatchSize()
	 * @see #unsetQueueBatchSize()
	 * @see #getQueueBatchSize()
	 * @generated
	 */
	void setQueueBatchSize(long value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueBatchSize <em>Queue Batch Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetQueueBatchSize()
	 * @see #getQueueBatchSize()
	 * @see #setQueueBatchSize(long)
	 * @generated
	 */
	void unsetQueueBatchSize();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueBatchSize <em>Queue Batch Size</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Queue Batch Size</em>' attribute is set.
	 * @see #unsetQueueBatchSize()
	 * @see #getQueueBatchSize()
	 * @see #setQueueBatchSize(long)
	 * @generated
	 */
	boolean isSetQueueBatchSize();

	/**
	 * Returns the value of the '<em><b>Queue Bucket Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Queue Bucket Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Queue Bucket Size</em>' attribute.
	 * @see #isSetQueueBucketSize()
	 * @see #unsetQueueBucketSize()
	 * @see #setQueueBucketSize(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnBatchMeansQueueingColorConfiguration_QueueBucketSize()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
	 *        extendedMetaData="kind='attribute' name='queueBucketSize' namespace='##targetNamespace'"
	 * @generated
	 */
	double getQueueBucketSize();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueBucketSize <em>Queue Bucket Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Queue Bucket Size</em>' attribute.
	 * @see #isSetQueueBucketSize()
	 * @see #unsetQueueBucketSize()
	 * @see #getQueueBucketSize()
	 * @generated
	 */
	void setQueueBucketSize(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueBucketSize <em>Queue Bucket Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetQueueBucketSize()
	 * @see #getQueueBucketSize()
	 * @see #setQueueBucketSize(double)
	 * @generated
	 */
	void unsetQueueBucketSize();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueBucketSize <em>Queue Bucket Size</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Queue Bucket Size</em>' attribute is set.
	 * @see #unsetQueueBucketSize()
	 * @see #getQueueBucketSize()
	 * @see #setQueueBucketSize(double)
	 * @generated
	 */
	boolean isSetQueueBucketSize();

	/**
	 * Returns the value of the '<em><b>Queue Max Buckets</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Queue Max Buckets</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Queue Max Buckets</em>' attribute.
	 * @see #isSetQueueMaxBuckets()
	 * @see #unsetQueueMaxBuckets()
	 * @see #setQueueMaxBuckets(long)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnBatchMeansQueueingColorConfiguration_QueueMaxBuckets()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.UnsignedInt" required="true"
	 *        extendedMetaData="kind='attribute' name='queueMaxBuckets' namespace='##targetNamespace'"
	 * @generated
	 */
	long getQueueMaxBuckets();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueMaxBuckets <em>Queue Max Buckets</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Queue Max Buckets</em>' attribute.
	 * @see #isSetQueueMaxBuckets()
	 * @see #unsetQueueMaxBuckets()
	 * @see #getQueueMaxBuckets()
	 * @generated
	 */
	void setQueueMaxBuckets(long value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueMaxBuckets <em>Queue Max Buckets</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetQueueMaxBuckets()
	 * @see #getQueueMaxBuckets()
	 * @see #setQueueMaxBuckets(long)
	 * @generated
	 */
	void unsetQueueMaxBuckets();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueMaxBuckets <em>Queue Max Buckets</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Queue Max Buckets</em>' attribute is set.
	 * @see #unsetQueueMaxBuckets()
	 * @see #getQueueMaxBuckets()
	 * @see #setQueueMaxBuckets(long)
	 * @generated
	 */
	boolean isSetQueueMaxBuckets();

	/**
	 * Returns the value of the '<em><b>Queue Min Batches</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Queue Min Batches</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Queue Min Batches</em>' attribute.
	 * @see #isSetQueueMinBatches()
	 * @see #unsetQueueMinBatches()
	 * @see #setQueueMinBatches(long)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnBatchMeansQueueingColorConfiguration_QueueMinBatches()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.UnsignedInt" required="true"
	 *        extendedMetaData="kind='attribute' name='queueMinBatches' namespace='##targetNamespace'"
	 * @generated
	 */
	long getQueueMinBatches();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueMinBatches <em>Queue Min Batches</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Queue Min Batches</em>' attribute.
	 * @see #isSetQueueMinBatches()
	 * @see #unsetQueueMinBatches()
	 * @see #getQueueMinBatches()
	 * @generated
	 */
	void setQueueMinBatches(long value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueMinBatches <em>Queue Min Batches</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetQueueMinBatches()
	 * @see #getQueueMinBatches()
	 * @see #setQueueMinBatches(long)
	 * @generated
	 */
	void unsetQueueMinBatches();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueMinBatches <em>Queue Min Batches</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Queue Min Batches</em>' attribute is set.
	 * @see #unsetQueueMinBatches()
	 * @see #getQueueMinBatches()
	 * @see #setQueueMinBatches(long)
	 * @generated
	 */
	boolean isSetQueueMinBatches();

	/**
	 * Returns the value of the '<em><b>Queue Num BMeans Corl Tested</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Queue Num BMeans Corl Tested</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Queue Num BMeans Corl Tested</em>' attribute.
	 * @see #isSetQueueNumBMeansCorlTested()
	 * @see #unsetQueueNumBMeansCorlTested()
	 * @see #setQueueNumBMeansCorlTested(long)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnBatchMeansQueueingColorConfiguration_QueueNumBMeansCorlTested()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.UnsignedInt" required="true"
	 *        extendedMetaData="kind='attribute' name='queueNumBMeansCorlTested' namespace='##targetNamespace'"
	 * @generated
	 */
	long getQueueNumBMeansCorlTested();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueNumBMeansCorlTested <em>Queue Num BMeans Corl Tested</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Queue Num BMeans Corl Tested</em>' attribute.
	 * @see #isSetQueueNumBMeansCorlTested()
	 * @see #unsetQueueNumBMeansCorlTested()
	 * @see #getQueueNumBMeansCorlTested()
	 * @generated
	 */
	void setQueueNumBMeansCorlTested(long value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueNumBMeansCorlTested <em>Queue Num BMeans Corl Tested</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetQueueNumBMeansCorlTested()
	 * @see #getQueueNumBMeansCorlTested()
	 * @see #setQueueNumBMeansCorlTested(long)
	 * @generated
	 */
	void unsetQueueNumBMeansCorlTested();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueNumBMeansCorlTested <em>Queue Num BMeans Corl Tested</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Queue Num BMeans Corl Tested</em>' attribute is set.
	 * @see #unsetQueueNumBMeansCorlTested()
	 * @see #getQueueNumBMeansCorlTested()
	 * @see #setQueueNumBMeansCorlTested(long)
	 * @generated
	 */
	boolean isSetQueueNumBMeansCorlTested();

	/**
	 * Returns the value of the '<em><b>Queue Req Abs Prc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Queue Req Abs Prc</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Queue Req Abs Prc</em>' attribute.
	 * @see #isSetQueueReqAbsPrc()
	 * @see #unsetQueueReqAbsPrc()
	 * @see #setQueueReqAbsPrc(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnBatchMeansQueueingColorConfiguration_QueueReqAbsPrc()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
	 *        extendedMetaData="kind='attribute' name='queueReqAbsPrc' namespace='##targetNamespace'"
	 * @generated
	 */
	double getQueueReqAbsPrc();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueReqAbsPrc <em>Queue Req Abs Prc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Queue Req Abs Prc</em>' attribute.
	 * @see #isSetQueueReqAbsPrc()
	 * @see #unsetQueueReqAbsPrc()
	 * @see #getQueueReqAbsPrc()
	 * @generated
	 */
	void setQueueReqAbsPrc(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueReqAbsPrc <em>Queue Req Abs Prc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetQueueReqAbsPrc()
	 * @see #getQueueReqAbsPrc()
	 * @see #setQueueReqAbsPrc(double)
	 * @generated
	 */
	void unsetQueueReqAbsPrc();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueReqAbsPrc <em>Queue Req Abs Prc</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Queue Req Abs Prc</em>' attribute is set.
	 * @see #unsetQueueReqAbsPrc()
	 * @see #getQueueReqAbsPrc()
	 * @see #setQueueReqAbsPrc(double)
	 * @generated
	 */
	boolean isSetQueueReqAbsPrc();

	/**
	 * Returns the value of the '<em><b>Queue Req Rel Prc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Queue Req Rel Prc</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Queue Req Rel Prc</em>' attribute.
	 * @see #isSetQueueReqRelPrc()
	 * @see #unsetQueueReqRelPrc()
	 * @see #setQueueReqRelPrc(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnBatchMeansQueueingColorConfiguration_QueueReqRelPrc()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
	 *        extendedMetaData="kind='attribute' name='queueReqRelPrc' namespace='##targetNamespace'"
	 * @generated
	 */
	double getQueueReqRelPrc();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueReqRelPrc <em>Queue Req Rel Prc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Queue Req Rel Prc</em>' attribute.
	 * @see #isSetQueueReqRelPrc()
	 * @see #unsetQueueReqRelPrc()
	 * @see #getQueueReqRelPrc()
	 * @generated
	 */
	void setQueueReqRelPrc(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueReqRelPrc <em>Queue Req Rel Prc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetQueueReqRelPrc()
	 * @see #getQueueReqRelPrc()
	 * @see #setQueueReqRelPrc(double)
	 * @generated
	 */
	void unsetQueueReqRelPrc();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueReqRelPrc <em>Queue Req Rel Prc</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Queue Req Rel Prc</em>' attribute is set.
	 * @see #unsetQueueReqRelPrc()
	 * @see #getQueueReqRelPrc()
	 * @see #setQueueReqRelPrc(double)
	 * @generated
	 */
	boolean isSetQueueReqRelPrc();

	/**
	 * Returns the value of the '<em><b>Queue Sign Lev</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Queue Sign Lev</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Queue Sign Lev</em>' attribute.
	 * @see #isSetQueueSignLev()
	 * @see #unsetQueueSignLev()
	 * @see #setQueueSignLev(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnBatchMeansQueueingColorConfiguration_QueueSignLev()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
	 *        extendedMetaData="kind='attribute' name='queueSignLev' namespace='##targetNamespace'"
	 * @generated
	 */
	double getQueueSignLev();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueSignLev <em>Queue Sign Lev</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Queue Sign Lev</em>' attribute.
	 * @see #isSetQueueSignLev()
	 * @see #unsetQueueSignLev()
	 * @see #getQueueSignLev()
	 * @generated
	 */
	void setQueueSignLev(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueSignLev <em>Queue Sign Lev</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetQueueSignLev()
	 * @see #getQueueSignLev()
	 * @see #setQueueSignLev(double)
	 * @generated
	 */
	void unsetQueueSignLev();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration#getQueueSignLev <em>Queue Sign Lev</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Queue Sign Lev</em>' attribute is set.
	 * @see #unsetQueueSignLev()
	 * @see #getQueueSignLev()
	 * @see #setQueueSignLev(double)
	 * @generated
	 */
	boolean isSetQueueSignLev();

} // SimqpnBatchMeansQueueingColorConfiguration
