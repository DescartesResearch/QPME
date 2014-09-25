/**
 */
package edu.kit.ipd.descartes.qpme.simqpn.model.impl;

import edu.kit.ipd.descartes.qpme.simqpn.model.Buckets;
import edu.kit.ipd.descartes.qpme.simqpn.model.Histogram;
import edu.kit.ipd.descartes.qpme.simqpn.model.Percentiles;
import edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Histogram</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.HistogramImpl#getMean <em>Mean</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.HistogramImpl#getPercentiles <em>Percentiles</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.HistogramImpl#getBuckets <em>Buckets</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.HistogramImpl#getBucketSize <em>Bucket Size</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.HistogramImpl#getNumBuckets <em>Num Buckets</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.HistogramImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class HistogramImpl extends EObjectImpl implements Histogram {
	/**
	 * The default value of the '{@link #getMean() <em>Mean</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMean()
	 * @generated
	 * @ordered
	 */
	protected static final double MEAN_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getMean() <em>Mean</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMean()
	 * @generated
	 * @ordered
	 */
	protected double mean = MEAN_EDEFAULT;

	/**
	 * This is true if the Mean attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean meanESet;

	/**
	 * The cached value of the '{@link #getPercentiles() <em>Percentiles</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPercentiles()
	 * @generated
	 * @ordered
	 */
	protected Percentiles percentiles;

	/**
	 * The cached value of the '{@link #getBuckets() <em>Buckets</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuckets()
	 * @generated
	 * @ordered
	 */
	protected Buckets buckets;

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
	 * The default value of the '{@link #getNumBuckets() <em>Num Buckets</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumBuckets()
	 * @generated
	 * @ordered
	 */
	protected static final long NUM_BUCKETS_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getNumBuckets() <em>Num Buckets</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumBuckets()
	 * @generated
	 * @ordered
	 */
	protected long numBuckets = NUM_BUCKETS_EDEFAULT;

	/**
	 * This is true if the Num Buckets attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean numBucketsESet;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected String type = TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected HistogramImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SimqpnPackage.Literals.HISTOGRAM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getMean() {
		return mean;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMean(double newMean) {
		double oldMean = mean;
		mean = newMean;
		boolean oldMeanESet = meanESet;
		meanESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SimqpnPackage.HISTOGRAM__MEAN, oldMean, mean, !oldMeanESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetMean() {
		double oldMean = mean;
		boolean oldMeanESet = meanESet;
		mean = MEAN_EDEFAULT;
		meanESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, SimqpnPackage.HISTOGRAM__MEAN, oldMean, MEAN_EDEFAULT, oldMeanESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetMean() {
		return meanESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Percentiles getPercentiles() {
		return percentiles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPercentiles(Percentiles newPercentiles, NotificationChain msgs) {
		Percentiles oldPercentiles = percentiles;
		percentiles = newPercentiles;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SimqpnPackage.HISTOGRAM__PERCENTILES, oldPercentiles, newPercentiles);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPercentiles(Percentiles newPercentiles) {
		if (newPercentiles != percentiles) {
			NotificationChain msgs = null;
			if (percentiles != null)
				msgs = ((InternalEObject)percentiles).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SimqpnPackage.HISTOGRAM__PERCENTILES, null, msgs);
			if (newPercentiles != null)
				msgs = ((InternalEObject)newPercentiles).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SimqpnPackage.HISTOGRAM__PERCENTILES, null, msgs);
			msgs = basicSetPercentiles(newPercentiles, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SimqpnPackage.HISTOGRAM__PERCENTILES, newPercentiles, newPercentiles));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Buckets getBuckets() {
		return buckets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBuckets(Buckets newBuckets, NotificationChain msgs) {
		Buckets oldBuckets = buckets;
		buckets = newBuckets;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SimqpnPackage.HISTOGRAM__BUCKETS, oldBuckets, newBuckets);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBuckets(Buckets newBuckets) {
		if (newBuckets != buckets) {
			NotificationChain msgs = null;
			if (buckets != null)
				msgs = ((InternalEObject)buckets).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SimqpnPackage.HISTOGRAM__BUCKETS, null, msgs);
			if (newBuckets != null)
				msgs = ((InternalEObject)newBuckets).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SimqpnPackage.HISTOGRAM__BUCKETS, null, msgs);
			msgs = basicSetBuckets(newBuckets, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SimqpnPackage.HISTOGRAM__BUCKETS, newBuckets, newBuckets));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SimqpnPackage.HISTOGRAM__BUCKET_SIZE, oldBucketSize, bucketSize, !oldBucketSizeESet));
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, SimqpnPackage.HISTOGRAM__BUCKET_SIZE, oldBucketSize, BUCKET_SIZE_EDEFAULT, oldBucketSizeESet));
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
	public long getNumBuckets() {
		return numBuckets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumBuckets(long newNumBuckets) {
		long oldNumBuckets = numBuckets;
		numBuckets = newNumBuckets;
		boolean oldNumBucketsESet = numBucketsESet;
		numBucketsESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SimqpnPackage.HISTOGRAM__NUM_BUCKETS, oldNumBuckets, numBuckets, !oldNumBucketsESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetNumBuckets() {
		long oldNumBuckets = numBuckets;
		boolean oldNumBucketsESet = numBucketsESet;
		numBuckets = NUM_BUCKETS_EDEFAULT;
		numBucketsESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, SimqpnPackage.HISTOGRAM__NUM_BUCKETS, oldNumBuckets, NUM_BUCKETS_EDEFAULT, oldNumBucketsESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetNumBuckets() {
		return numBucketsESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(String newType) {
		String oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SimqpnPackage.HISTOGRAM__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SimqpnPackage.HISTOGRAM__PERCENTILES:
				return basicSetPercentiles(null, msgs);
			case SimqpnPackage.HISTOGRAM__BUCKETS:
				return basicSetBuckets(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SimqpnPackage.HISTOGRAM__MEAN:
				return getMean();
			case SimqpnPackage.HISTOGRAM__PERCENTILES:
				return getPercentiles();
			case SimqpnPackage.HISTOGRAM__BUCKETS:
				return getBuckets();
			case SimqpnPackage.HISTOGRAM__BUCKET_SIZE:
				return getBucketSize();
			case SimqpnPackage.HISTOGRAM__NUM_BUCKETS:
				return getNumBuckets();
			case SimqpnPackage.HISTOGRAM__TYPE:
				return getType();
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
			case SimqpnPackage.HISTOGRAM__MEAN:
				setMean((Double)newValue);
				return;
			case SimqpnPackage.HISTOGRAM__PERCENTILES:
				setPercentiles((Percentiles)newValue);
				return;
			case SimqpnPackage.HISTOGRAM__BUCKETS:
				setBuckets((Buckets)newValue);
				return;
			case SimqpnPackage.HISTOGRAM__BUCKET_SIZE:
				setBucketSize((Double)newValue);
				return;
			case SimqpnPackage.HISTOGRAM__NUM_BUCKETS:
				setNumBuckets((Long)newValue);
				return;
			case SimqpnPackage.HISTOGRAM__TYPE:
				setType((String)newValue);
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
			case SimqpnPackage.HISTOGRAM__MEAN:
				unsetMean();
				return;
			case SimqpnPackage.HISTOGRAM__PERCENTILES:
				setPercentiles((Percentiles)null);
				return;
			case SimqpnPackage.HISTOGRAM__BUCKETS:
				setBuckets((Buckets)null);
				return;
			case SimqpnPackage.HISTOGRAM__BUCKET_SIZE:
				unsetBucketSize();
				return;
			case SimqpnPackage.HISTOGRAM__NUM_BUCKETS:
				unsetNumBuckets();
				return;
			case SimqpnPackage.HISTOGRAM__TYPE:
				setType(TYPE_EDEFAULT);
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
			case SimqpnPackage.HISTOGRAM__MEAN:
				return isSetMean();
			case SimqpnPackage.HISTOGRAM__PERCENTILES:
				return percentiles != null;
			case SimqpnPackage.HISTOGRAM__BUCKETS:
				return buckets != null;
			case SimqpnPackage.HISTOGRAM__BUCKET_SIZE:
				return isSetBucketSize();
			case SimqpnPackage.HISTOGRAM__NUM_BUCKETS:
				return isSetNumBuckets();
			case SimqpnPackage.HISTOGRAM__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
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

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (mean: ");
		if (meanESet) result.append(mean); else result.append("<unset>");
		result.append(", bucketSize: ");
		if (bucketSizeESet) result.append(bucketSize); else result.append("<unset>");
		result.append(", numBuckets: ");
		if (numBucketsESet) result.append(numBuckets); else result.append("<unset>");
		result.append(", type: ");
		result.append(type);
		result.append(')');
		return result.toString();
	}

} //HistogramImpl
