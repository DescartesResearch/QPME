/**
 */
package edu.kit.ipd.descartes.qpme.model.impl;

import edu.kit.ipd.descartes.qpme.model.ModelPackage;
import edu.kit.ipd.descartes.qpme.model.SimqpnBatchMeansColorConfiguration;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Simqpn Batch Means Color Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnBatchMeansColorConfigurationImpl#getBatchSize <em>Batch Size</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnBatchMeansColorConfigurationImpl#getBucketSize <em>Bucket Size</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnBatchMeansColorConfigurationImpl#getMaxBuckets <em>Max Buckets</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnBatchMeansColorConfigurationImpl#getMinBatches <em>Min Batches</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnBatchMeansColorConfigurationImpl#getNumBMeansCorlTested <em>Num BMeans Corl Tested</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnBatchMeansColorConfigurationImpl#getReqAbsPrc <em>Req Abs Prc</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnBatchMeansColorConfigurationImpl#getReqRelPrc <em>Req Rel Prc</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnBatchMeansColorConfigurationImpl#getSignLev <em>Sign Lev</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SimqpnBatchMeansColorConfigurationImpl extends SimqpnMetaAttributeImpl implements SimqpnBatchMeansColorConfiguration {
	/**
	 * The default value of the '{@link #getBatchSize() <em>Batch Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBatchSize()
	 * @generated
	 * @ordered
	 */
	protected static final long BATCH_SIZE_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getBatchSize() <em>Batch Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBatchSize()
	 * @generated
	 * @ordered
	 */
	protected long batchSize = BATCH_SIZE_EDEFAULT;

	/**
	 * This is true if the Batch Size attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean batchSizeESet;

	/**
	 * The default value of the '{@link #getBucketSize() <em>Bucket Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBucketSize()
	 * @generated
	 * @ordered
	 */
	protected static final double BUCKET_SIZE_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getBucketSize() <em>Bucket Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBucketSize()
	 * @generated
	 * @ordered
	 */
	protected double bucketSize = BUCKET_SIZE_EDEFAULT;

	/**
	 * This is true if the Bucket Size attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean bucketSizeESet;

	/**
	 * The default value of the '{@link #getMaxBuckets() <em>Max Buckets</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxBuckets()
	 * @generated
	 * @ordered
	 */
	protected static final long MAX_BUCKETS_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getMaxBuckets() <em>Max Buckets</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxBuckets()
	 * @generated
	 * @ordered
	 */
	protected long maxBuckets = MAX_BUCKETS_EDEFAULT;

	/**
	 * This is true if the Max Buckets attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean maxBucketsESet;

	/**
	 * The default value of the '{@link #getMinBatches() <em>Min Batches</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinBatches()
	 * @generated
	 * @ordered
	 */
	protected static final long MIN_BATCHES_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getMinBatches() <em>Min Batches</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinBatches()
	 * @generated
	 * @ordered
	 */
	protected long minBatches = MIN_BATCHES_EDEFAULT;

	/**
	 * This is true if the Min Batches attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean minBatchesESet;

	/**
	 * The default value of the '{@link #getNumBMeansCorlTested() <em>Num BMeans Corl Tested</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumBMeansCorlTested()
	 * @generated
	 * @ordered
	 */
	protected static final long NUM_BMEANS_CORL_TESTED_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getNumBMeansCorlTested() <em>Num BMeans Corl Tested</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumBMeansCorlTested()
	 * @generated
	 * @ordered
	 */
	protected long numBMeansCorlTested = NUM_BMEANS_CORL_TESTED_EDEFAULT;

	/**
	 * This is true if the Num BMeans Corl Tested attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean numBMeansCorlTestedESet;

	/**
	 * The default value of the '{@link #getReqAbsPrc() <em>Req Abs Prc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReqAbsPrc()
	 * @generated
	 * @ordered
	 */
	protected static final double REQ_ABS_PRC_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getReqAbsPrc() <em>Req Abs Prc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReqAbsPrc()
	 * @generated
	 * @ordered
	 */
	protected double reqAbsPrc = REQ_ABS_PRC_EDEFAULT;

	/**
	 * This is true if the Req Abs Prc attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean reqAbsPrcESet;

	/**
	 * The default value of the '{@link #getReqRelPrc() <em>Req Rel Prc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReqRelPrc()
	 * @generated
	 * @ordered
	 */
	protected static final double REQ_REL_PRC_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getReqRelPrc() <em>Req Rel Prc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReqRelPrc()
	 * @generated
	 * @ordered
	 */
	protected double reqRelPrc = REQ_REL_PRC_EDEFAULT;

	/**
	 * This is true if the Req Rel Prc attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean reqRelPrcESet;

	/**
	 * The default value of the '{@link #getSignLev() <em>Sign Lev</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSignLev()
	 * @generated
	 * @ordered
	 */
	protected static final double SIGN_LEV_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getSignLev() <em>Sign Lev</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSignLev()
	 * @generated
	 * @ordered
	 */
	protected double signLev = SIGN_LEV_EDEFAULT;

	/**
	 * This is true if the Sign Lev attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean signLevESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SimqpnBatchMeansColorConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getBatchSize() {
		return batchSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBatchSize(long newBatchSize) {
		long oldBatchSize = batchSize;
		batchSize = newBatchSize;
		boolean oldBatchSizeESet = batchSizeESet;
		batchSizeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__BATCH_SIZE, oldBatchSize, batchSize, !oldBatchSizeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetBatchSize() {
		long oldBatchSize = batchSize;
		boolean oldBatchSizeESet = batchSizeESet;
		batchSize = BATCH_SIZE_EDEFAULT;
		batchSizeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__BATCH_SIZE, oldBatchSize, BATCH_SIZE_EDEFAULT, oldBatchSizeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetBatchSize() {
		return batchSizeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getBucketSize() {
		return bucketSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBucketSize(double newBucketSize) {
		double oldBucketSize = bucketSize;
		bucketSize = newBucketSize;
		boolean oldBucketSizeESet = bucketSizeESet;
		bucketSizeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__BUCKET_SIZE, oldBucketSize, bucketSize, !oldBucketSizeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetBucketSize() {
		double oldBucketSize = bucketSize;
		boolean oldBucketSizeESet = bucketSizeESet;
		bucketSize = BUCKET_SIZE_EDEFAULT;
		bucketSizeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__BUCKET_SIZE, oldBucketSize, BUCKET_SIZE_EDEFAULT, oldBucketSizeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetBucketSize() {
		return bucketSizeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getMaxBuckets() {
		return maxBuckets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxBuckets(long newMaxBuckets) {
		long oldMaxBuckets = maxBuckets;
		maxBuckets = newMaxBuckets;
		boolean oldMaxBucketsESet = maxBucketsESet;
		maxBucketsESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__MAX_BUCKETS, oldMaxBuckets, maxBuckets, !oldMaxBucketsESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetMaxBuckets() {
		long oldMaxBuckets = maxBuckets;
		boolean oldMaxBucketsESet = maxBucketsESet;
		maxBuckets = MAX_BUCKETS_EDEFAULT;
		maxBucketsESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__MAX_BUCKETS, oldMaxBuckets, MAX_BUCKETS_EDEFAULT, oldMaxBucketsESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetMaxBuckets() {
		return maxBucketsESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getMinBatches() {
		return minBatches;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinBatches(long newMinBatches) {
		long oldMinBatches = minBatches;
		minBatches = newMinBatches;
		boolean oldMinBatchesESet = minBatchesESet;
		minBatchesESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__MIN_BATCHES, oldMinBatches, minBatches, !oldMinBatchesESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetMinBatches() {
		long oldMinBatches = minBatches;
		boolean oldMinBatchesESet = minBatchesESet;
		minBatches = MIN_BATCHES_EDEFAULT;
		minBatchesESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__MIN_BATCHES, oldMinBatches, MIN_BATCHES_EDEFAULT, oldMinBatchesESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetMinBatches() {
		return minBatchesESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getNumBMeansCorlTested() {
		return numBMeansCorlTested;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumBMeansCorlTested(long newNumBMeansCorlTested) {
		long oldNumBMeansCorlTested = numBMeansCorlTested;
		numBMeansCorlTested = newNumBMeansCorlTested;
		boolean oldNumBMeansCorlTestedESet = numBMeansCorlTestedESet;
		numBMeansCorlTestedESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__NUM_BMEANS_CORL_TESTED, oldNumBMeansCorlTested, numBMeansCorlTested, !oldNumBMeansCorlTestedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetNumBMeansCorlTested() {
		long oldNumBMeansCorlTested = numBMeansCorlTested;
		boolean oldNumBMeansCorlTestedESet = numBMeansCorlTestedESet;
		numBMeansCorlTested = NUM_BMEANS_CORL_TESTED_EDEFAULT;
		numBMeansCorlTestedESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__NUM_BMEANS_CORL_TESTED, oldNumBMeansCorlTested, NUM_BMEANS_CORL_TESTED_EDEFAULT, oldNumBMeansCorlTestedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetNumBMeansCorlTested() {
		return numBMeansCorlTestedESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getReqAbsPrc() {
		return reqAbsPrc;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReqAbsPrc(double newReqAbsPrc) {
		double oldReqAbsPrc = reqAbsPrc;
		reqAbsPrc = newReqAbsPrc;
		boolean oldReqAbsPrcESet = reqAbsPrcESet;
		reqAbsPrcESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__REQ_ABS_PRC, oldReqAbsPrc, reqAbsPrc, !oldReqAbsPrcESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetReqAbsPrc() {
		double oldReqAbsPrc = reqAbsPrc;
		boolean oldReqAbsPrcESet = reqAbsPrcESet;
		reqAbsPrc = REQ_ABS_PRC_EDEFAULT;
		reqAbsPrcESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__REQ_ABS_PRC, oldReqAbsPrc, REQ_ABS_PRC_EDEFAULT, oldReqAbsPrcESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetReqAbsPrc() {
		return reqAbsPrcESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getReqRelPrc() {
		return reqRelPrc;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReqRelPrc(double newReqRelPrc) {
		double oldReqRelPrc = reqRelPrc;
		reqRelPrc = newReqRelPrc;
		boolean oldReqRelPrcESet = reqRelPrcESet;
		reqRelPrcESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__REQ_REL_PRC, oldReqRelPrc, reqRelPrc, !oldReqRelPrcESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetReqRelPrc() {
		double oldReqRelPrc = reqRelPrc;
		boolean oldReqRelPrcESet = reqRelPrcESet;
		reqRelPrc = REQ_REL_PRC_EDEFAULT;
		reqRelPrcESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__REQ_REL_PRC, oldReqRelPrc, REQ_REL_PRC_EDEFAULT, oldReqRelPrcESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetReqRelPrc() {
		return reqRelPrcESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getSignLev() {
		return signLev;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSignLev(double newSignLev) {
		double oldSignLev = signLev;
		signLev = newSignLev;
		boolean oldSignLevESet = signLevESet;
		signLevESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__SIGN_LEV, oldSignLev, signLev, !oldSignLevESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetSignLev() {
		double oldSignLev = signLev;
		boolean oldSignLevESet = signLevESet;
		signLev = SIGN_LEV_EDEFAULT;
		signLevESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__SIGN_LEV, oldSignLev, SIGN_LEV_EDEFAULT, oldSignLevESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetSignLev() {
		return signLevESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__BATCH_SIZE:
				return getBatchSize();
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__BUCKET_SIZE:
				return getBucketSize();
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__MAX_BUCKETS:
				return getMaxBuckets();
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__MIN_BATCHES:
				return getMinBatches();
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__NUM_BMEANS_CORL_TESTED:
				return getNumBMeansCorlTested();
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__REQ_ABS_PRC:
				return getReqAbsPrc();
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__REQ_REL_PRC:
				return getReqRelPrc();
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__SIGN_LEV:
				return getSignLev();
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
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__BATCH_SIZE:
				setBatchSize((Long)newValue);
				return;
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__BUCKET_SIZE:
				setBucketSize((Double)newValue);
				return;
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__MAX_BUCKETS:
				setMaxBuckets((Long)newValue);
				return;
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__MIN_BATCHES:
				setMinBatches((Long)newValue);
				return;
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__NUM_BMEANS_CORL_TESTED:
				setNumBMeansCorlTested((Long)newValue);
				return;
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__REQ_ABS_PRC:
				setReqAbsPrc((Double)newValue);
				return;
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__REQ_REL_PRC:
				setReqRelPrc((Double)newValue);
				return;
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__SIGN_LEV:
				setSignLev((Double)newValue);
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
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__BATCH_SIZE:
				unsetBatchSize();
				return;
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__BUCKET_SIZE:
				unsetBucketSize();
				return;
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__MAX_BUCKETS:
				unsetMaxBuckets();
				return;
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__MIN_BATCHES:
				unsetMinBatches();
				return;
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__NUM_BMEANS_CORL_TESTED:
				unsetNumBMeansCorlTested();
				return;
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__REQ_ABS_PRC:
				unsetReqAbsPrc();
				return;
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__REQ_REL_PRC:
				unsetReqRelPrc();
				return;
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__SIGN_LEV:
				unsetSignLev();
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
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__BATCH_SIZE:
				return isSetBatchSize();
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__BUCKET_SIZE:
				return isSetBucketSize();
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__MAX_BUCKETS:
				return isSetMaxBuckets();
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__MIN_BATCHES:
				return isSetMinBatches();
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__NUM_BMEANS_CORL_TESTED:
				return isSetNumBMeansCorlTested();
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__REQ_ABS_PRC:
				return isSetReqAbsPrc();
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__REQ_REL_PRC:
				return isSetReqRelPrc();
			case ModelPackage.SIMQPN_BATCH_MEANS_COLOR_CONFIGURATION__SIGN_LEV:
				return isSetSignLev();
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
		result.append(" (batchSize: ");
		if (batchSizeESet) result.append(batchSize); else result.append("<unset>");
		result.append(", bucketSize: ");
		if (bucketSizeESet) result.append(bucketSize); else result.append("<unset>");
		result.append(", maxBuckets: ");
		if (maxBucketsESet) result.append(maxBuckets); else result.append("<unset>");
		result.append(", minBatches: ");
		if (minBatchesESet) result.append(minBatches); else result.append("<unset>");
		result.append(", numBMeansCorlTested: ");
		if (numBMeansCorlTestedESet) result.append(numBMeansCorlTested); else result.append("<unset>");
		result.append(", reqAbsPrc: ");
		if (reqAbsPrcESet) result.append(reqAbsPrc); else result.append("<unset>");
		result.append(", reqRelPrc: ");
		if (reqRelPrcESet) result.append(reqRelPrc); else result.append("<unset>");
		result.append(", signLev: ");
		if (signLevESet) result.append(signLev); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //SimqpnBatchMeansColorConfigurationImpl
