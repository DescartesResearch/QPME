/**
 */
package edu.kit.ipd.descartes.qpme.model.impl;

import edu.kit.ipd.descartes.qpme.model.ModelPackage;
import edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansQueueingColorConfiguration;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Simqpn Batch Means Queueing Color Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnBatchMeansQueueingColorConfigurationImpl#getQueueBatchSize <em>Queue Batch Size</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnBatchMeansQueueingColorConfigurationImpl#getQueueBucketSize <em>Queue Bucket Size</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnBatchMeansQueueingColorConfigurationImpl#getQueueMaxBuckets <em>Queue Max Buckets</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnBatchMeansQueueingColorConfigurationImpl#getQueueMinBatches <em>Queue Min Batches</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnBatchMeansQueueingColorConfigurationImpl#getQueueNumBMeansCorlTested <em>Queue Num BMeans Corl Tested</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnBatchMeansQueueingColorConfigurationImpl#getQueueReqAbsPrc <em>Queue Req Abs Prc</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnBatchMeansQueueingColorConfigurationImpl#getQueueReqRelPrc <em>Queue Req Rel Prc</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnBatchMeansQueueingColorConfigurationImpl#getQueueSignLev <em>Queue Sign Lev</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SimqpnBatchMeansQueueingColorConfigurationImpl extends SimqpnBatchMeansColorConfigurationImpl implements SimqpnBatchMeansQueueingColorConfiguration {
	/**
	 * The default value of the '{@link #getQueueBatchSize() <em>Queue Batch Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueueBatchSize()
	 * @generated
	 * @ordered
	 */
	protected static final long QUEUE_BATCH_SIZE_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getQueueBatchSize() <em>Queue Batch Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueueBatchSize()
	 * @generated
	 * @ordered
	 */
	protected long queueBatchSize = QUEUE_BATCH_SIZE_EDEFAULT;

	/**
	 * This is true if the Queue Batch Size attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean queueBatchSizeESet;

	/**
	 * The default value of the '{@link #getQueueBucketSize() <em>Queue Bucket Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueueBucketSize()
	 * @generated
	 * @ordered
	 */
	protected static final double QUEUE_BUCKET_SIZE_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getQueueBucketSize() <em>Queue Bucket Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueueBucketSize()
	 * @generated
	 * @ordered
	 */
	protected double queueBucketSize = QUEUE_BUCKET_SIZE_EDEFAULT;

	/**
	 * This is true if the Queue Bucket Size attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean queueBucketSizeESet;

	/**
	 * The default value of the '{@link #getQueueMaxBuckets() <em>Queue Max Buckets</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueueMaxBuckets()
	 * @generated
	 * @ordered
	 */
	protected static final long QUEUE_MAX_BUCKETS_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getQueueMaxBuckets() <em>Queue Max Buckets</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueueMaxBuckets()
	 * @generated
	 * @ordered
	 */
	protected long queueMaxBuckets = QUEUE_MAX_BUCKETS_EDEFAULT;

	/**
	 * This is true if the Queue Max Buckets attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean queueMaxBucketsESet;

	/**
	 * The default value of the '{@link #getQueueMinBatches() <em>Queue Min Batches</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueueMinBatches()
	 * @generated
	 * @ordered
	 */
	protected static final long QUEUE_MIN_BATCHES_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getQueueMinBatches() <em>Queue Min Batches</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueueMinBatches()
	 * @generated
	 * @ordered
	 */
	protected long queueMinBatches = QUEUE_MIN_BATCHES_EDEFAULT;

	/**
	 * This is true if the Queue Min Batches attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean queueMinBatchesESet;

	/**
	 * The default value of the '{@link #getQueueNumBMeansCorlTested() <em>Queue Num BMeans Corl Tested</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueueNumBMeansCorlTested()
	 * @generated
	 * @ordered
	 */
	protected static final long QUEUE_NUM_BMEANS_CORL_TESTED_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getQueueNumBMeansCorlTested() <em>Queue Num BMeans Corl Tested</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueueNumBMeansCorlTested()
	 * @generated
	 * @ordered
	 */
	protected long queueNumBMeansCorlTested = QUEUE_NUM_BMEANS_CORL_TESTED_EDEFAULT;

	/**
	 * This is true if the Queue Num BMeans Corl Tested attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean queueNumBMeansCorlTestedESet;

	/**
	 * The default value of the '{@link #getQueueReqAbsPrc() <em>Queue Req Abs Prc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueueReqAbsPrc()
	 * @generated
	 * @ordered
	 */
	protected static final double QUEUE_REQ_ABS_PRC_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getQueueReqAbsPrc() <em>Queue Req Abs Prc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueueReqAbsPrc()
	 * @generated
	 * @ordered
	 */
	protected double queueReqAbsPrc = QUEUE_REQ_ABS_PRC_EDEFAULT;

	/**
	 * This is true if the Queue Req Abs Prc attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean queueReqAbsPrcESet;

	/**
	 * The default value of the '{@link #getQueueReqRelPrc() <em>Queue Req Rel Prc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueueReqRelPrc()
	 * @generated
	 * @ordered
	 */
	protected static final double QUEUE_REQ_REL_PRC_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getQueueReqRelPrc() <em>Queue Req Rel Prc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueueReqRelPrc()
	 * @generated
	 * @ordered
	 */
	protected double queueReqRelPrc = QUEUE_REQ_REL_PRC_EDEFAULT;

	/**
	 * This is true if the Queue Req Rel Prc attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean queueReqRelPrcESet;

	/**
	 * The default value of the '{@link #getQueueSignLev() <em>Queue Sign Lev</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueueSignLev()
	 * @generated
	 * @ordered
	 */
	protected static final double QUEUE_SIGN_LEV_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getQueueSignLev() <em>Queue Sign Lev</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueueSignLev()
	 * @generated
	 * @ordered
	 */
	protected double queueSignLev = QUEUE_SIGN_LEV_EDEFAULT;

	/**
	 * This is true if the Queue Sign Lev attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean queueSignLevESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SimqpnBatchMeansQueueingColorConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getQueueBatchSize() {
		return queueBatchSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQueueBatchSize(long newQueueBatchSize) {
		long oldQueueBatchSize = queueBatchSize;
		queueBatchSize = newQueueBatchSize;
		boolean oldQueueBatchSizeESet = queueBatchSizeESet;
		queueBatchSizeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_BATCH_SIZE, oldQueueBatchSize, queueBatchSize, !oldQueueBatchSizeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetQueueBatchSize() {
		long oldQueueBatchSize = queueBatchSize;
		boolean oldQueueBatchSizeESet = queueBatchSizeESet;
		queueBatchSize = QUEUE_BATCH_SIZE_EDEFAULT;
		queueBatchSizeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_BATCH_SIZE, oldQueueBatchSize, QUEUE_BATCH_SIZE_EDEFAULT, oldQueueBatchSizeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetQueueBatchSize() {
		return queueBatchSizeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getQueueBucketSize() {
		return queueBucketSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQueueBucketSize(double newQueueBucketSize) {
		double oldQueueBucketSize = queueBucketSize;
		queueBucketSize = newQueueBucketSize;
		boolean oldQueueBucketSizeESet = queueBucketSizeESet;
		queueBucketSizeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_BUCKET_SIZE, oldQueueBucketSize, queueBucketSize, !oldQueueBucketSizeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetQueueBucketSize() {
		double oldQueueBucketSize = queueBucketSize;
		boolean oldQueueBucketSizeESet = queueBucketSizeESet;
		queueBucketSize = QUEUE_BUCKET_SIZE_EDEFAULT;
		queueBucketSizeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_BUCKET_SIZE, oldQueueBucketSize, QUEUE_BUCKET_SIZE_EDEFAULT, oldQueueBucketSizeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetQueueBucketSize() {
		return queueBucketSizeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getQueueMaxBuckets() {
		return queueMaxBuckets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQueueMaxBuckets(long newQueueMaxBuckets) {
		long oldQueueMaxBuckets = queueMaxBuckets;
		queueMaxBuckets = newQueueMaxBuckets;
		boolean oldQueueMaxBucketsESet = queueMaxBucketsESet;
		queueMaxBucketsESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_MAX_BUCKETS, oldQueueMaxBuckets, queueMaxBuckets, !oldQueueMaxBucketsESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetQueueMaxBuckets() {
		long oldQueueMaxBuckets = queueMaxBuckets;
		boolean oldQueueMaxBucketsESet = queueMaxBucketsESet;
		queueMaxBuckets = QUEUE_MAX_BUCKETS_EDEFAULT;
		queueMaxBucketsESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_MAX_BUCKETS, oldQueueMaxBuckets, QUEUE_MAX_BUCKETS_EDEFAULT, oldQueueMaxBucketsESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetQueueMaxBuckets() {
		return queueMaxBucketsESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getQueueMinBatches() {
		return queueMinBatches;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQueueMinBatches(long newQueueMinBatches) {
		long oldQueueMinBatches = queueMinBatches;
		queueMinBatches = newQueueMinBatches;
		boolean oldQueueMinBatchesESet = queueMinBatchesESet;
		queueMinBatchesESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_MIN_BATCHES, oldQueueMinBatches, queueMinBatches, !oldQueueMinBatchesESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetQueueMinBatches() {
		long oldQueueMinBatches = queueMinBatches;
		boolean oldQueueMinBatchesESet = queueMinBatchesESet;
		queueMinBatches = QUEUE_MIN_BATCHES_EDEFAULT;
		queueMinBatchesESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_MIN_BATCHES, oldQueueMinBatches, QUEUE_MIN_BATCHES_EDEFAULT, oldQueueMinBatchesESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetQueueMinBatches() {
		return queueMinBatchesESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getQueueNumBMeansCorlTested() {
		return queueNumBMeansCorlTested;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQueueNumBMeansCorlTested(long newQueueNumBMeansCorlTested) {
		long oldQueueNumBMeansCorlTested = queueNumBMeansCorlTested;
		queueNumBMeansCorlTested = newQueueNumBMeansCorlTested;
		boolean oldQueueNumBMeansCorlTestedESet = queueNumBMeansCorlTestedESet;
		queueNumBMeansCorlTestedESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_NUM_BMEANS_CORL_TESTED, oldQueueNumBMeansCorlTested, queueNumBMeansCorlTested, !oldQueueNumBMeansCorlTestedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetQueueNumBMeansCorlTested() {
		long oldQueueNumBMeansCorlTested = queueNumBMeansCorlTested;
		boolean oldQueueNumBMeansCorlTestedESet = queueNumBMeansCorlTestedESet;
		queueNumBMeansCorlTested = QUEUE_NUM_BMEANS_CORL_TESTED_EDEFAULT;
		queueNumBMeansCorlTestedESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_NUM_BMEANS_CORL_TESTED, oldQueueNumBMeansCorlTested, QUEUE_NUM_BMEANS_CORL_TESTED_EDEFAULT, oldQueueNumBMeansCorlTestedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetQueueNumBMeansCorlTested() {
		return queueNumBMeansCorlTestedESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getQueueReqAbsPrc() {
		return queueReqAbsPrc;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQueueReqAbsPrc(double newQueueReqAbsPrc) {
		double oldQueueReqAbsPrc = queueReqAbsPrc;
		queueReqAbsPrc = newQueueReqAbsPrc;
		boolean oldQueueReqAbsPrcESet = queueReqAbsPrcESet;
		queueReqAbsPrcESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_REQ_ABS_PRC, oldQueueReqAbsPrc, queueReqAbsPrc, !oldQueueReqAbsPrcESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetQueueReqAbsPrc() {
		double oldQueueReqAbsPrc = queueReqAbsPrc;
		boolean oldQueueReqAbsPrcESet = queueReqAbsPrcESet;
		queueReqAbsPrc = QUEUE_REQ_ABS_PRC_EDEFAULT;
		queueReqAbsPrcESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_REQ_ABS_PRC, oldQueueReqAbsPrc, QUEUE_REQ_ABS_PRC_EDEFAULT, oldQueueReqAbsPrcESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetQueueReqAbsPrc() {
		return queueReqAbsPrcESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getQueueReqRelPrc() {
		return queueReqRelPrc;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQueueReqRelPrc(double newQueueReqRelPrc) {
		double oldQueueReqRelPrc = queueReqRelPrc;
		queueReqRelPrc = newQueueReqRelPrc;
		boolean oldQueueReqRelPrcESet = queueReqRelPrcESet;
		queueReqRelPrcESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_REQ_REL_PRC, oldQueueReqRelPrc, queueReqRelPrc, !oldQueueReqRelPrcESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetQueueReqRelPrc() {
		double oldQueueReqRelPrc = queueReqRelPrc;
		boolean oldQueueReqRelPrcESet = queueReqRelPrcESet;
		queueReqRelPrc = QUEUE_REQ_REL_PRC_EDEFAULT;
		queueReqRelPrcESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_REQ_REL_PRC, oldQueueReqRelPrc, QUEUE_REQ_REL_PRC_EDEFAULT, oldQueueReqRelPrcESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetQueueReqRelPrc() {
		return queueReqRelPrcESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getQueueSignLev() {
		return queueSignLev;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQueueSignLev(double newQueueSignLev) {
		double oldQueueSignLev = queueSignLev;
		queueSignLev = newQueueSignLev;
		boolean oldQueueSignLevESet = queueSignLevESet;
		queueSignLevESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_SIGN_LEV, oldQueueSignLev, queueSignLev, !oldQueueSignLevESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetQueueSignLev() {
		double oldQueueSignLev = queueSignLev;
		boolean oldQueueSignLevESet = queueSignLevESet;
		queueSignLev = QUEUE_SIGN_LEV_EDEFAULT;
		queueSignLevESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_SIGN_LEV, oldQueueSignLev, QUEUE_SIGN_LEV_EDEFAULT, oldQueueSignLevESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetQueueSignLev() {
		return queueSignLevESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_BATCH_SIZE:
				return getQueueBatchSize();
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_BUCKET_SIZE:
				return getQueueBucketSize();
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_MAX_BUCKETS:
				return getQueueMaxBuckets();
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_MIN_BATCHES:
				return getQueueMinBatches();
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_NUM_BMEANS_CORL_TESTED:
				return getQueueNumBMeansCorlTested();
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_REQ_ABS_PRC:
				return getQueueReqAbsPrc();
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_REQ_REL_PRC:
				return getQueueReqRelPrc();
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_SIGN_LEV:
				return getQueueSignLev();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_BATCH_SIZE:
				setQueueBatchSize((Long)newValue);
				return;
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_BUCKET_SIZE:
				setQueueBucketSize((Double)newValue);
				return;
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_MAX_BUCKETS:
				setQueueMaxBuckets((Long)newValue);
				return;
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_MIN_BATCHES:
				setQueueMinBatches((Long)newValue);
				return;
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_NUM_BMEANS_CORL_TESTED:
				setQueueNumBMeansCorlTested((Long)newValue);
				return;
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_REQ_ABS_PRC:
				setQueueReqAbsPrc((Double)newValue);
				return;
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_REQ_REL_PRC:
				setQueueReqRelPrc((Double)newValue);
				return;
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_SIGN_LEV:
				setQueueSignLev((Double)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_BATCH_SIZE:
				unsetQueueBatchSize();
				return;
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_BUCKET_SIZE:
				unsetQueueBucketSize();
				return;
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_MAX_BUCKETS:
				unsetQueueMaxBuckets();
				return;
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_MIN_BATCHES:
				unsetQueueMinBatches();
				return;
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_NUM_BMEANS_CORL_TESTED:
				unsetQueueNumBMeansCorlTested();
				return;
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_REQ_ABS_PRC:
				unsetQueueReqAbsPrc();
				return;
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_REQ_REL_PRC:
				unsetQueueReqRelPrc();
				return;
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_SIGN_LEV:
				unsetQueueSignLev();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_BATCH_SIZE:
				return isSetQueueBatchSize();
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_BUCKET_SIZE:
				return isSetQueueBucketSize();
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_MAX_BUCKETS:
				return isSetQueueMaxBuckets();
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_MIN_BATCHES:
				return isSetQueueMinBatches();
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_NUM_BMEANS_CORL_TESTED:
				return isSetQueueNumBMeansCorlTested();
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_REQ_ABS_PRC:
				return isSetQueueReqAbsPrc();
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_REQ_REL_PRC:
				return isSetQueueReqRelPrc();
			case ModelPackage.SIMQPN_BATCH_MEANS_QUEUEING_COLOR_CONFIGURATION__QUEUE_SIGN_LEV:
				return isSetQueueSignLev();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (queueBatchSize: ");
		if (queueBatchSizeESet) result.append(queueBatchSize); else result.append("<unset>");
		result.append(", queueBucketSize: ");
		if (queueBucketSizeESet) result.append(queueBucketSize); else result.append("<unset>");
		result.append(", queueMaxBuckets: ");
		if (queueMaxBucketsESet) result.append(queueMaxBuckets); else result.append("<unset>");
		result.append(", queueMinBatches: ");
		if (queueMinBatchesESet) result.append(queueMinBatches); else result.append("<unset>");
		result.append(", queueNumBMeansCorlTested: ");
		if (queueNumBMeansCorlTestedESet) result.append(queueNumBMeansCorlTested); else result.append("<unset>");
		result.append(", queueReqAbsPrc: ");
		if (queueReqAbsPrcESet) result.append(queueReqAbsPrc); else result.append("<unset>");
		result.append(", queueReqRelPrc: ");
		if (queueReqRelPrcESet) result.append(queueReqRelPrc); else result.append("<unset>");
		result.append(", queueSignLev: ");
		if (queueSignLevESet) result.append(queueSignLev); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //SimqpnBatchMeansQueueingColorConfigurationImpl
